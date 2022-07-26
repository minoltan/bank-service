package com.example.bankservice.account_flow.current;

import com.example.bankservice.account_flow.Account;
import com.example.bankservice.exception.DataConflictException;
import com.example.bankservice.exception.DataNotFoundException;
import com.example.bankservice.modal.AccountInfo;
import com.example.bankservice.modal.Transaction;
import com.example.bankservice.modal.UserInfo;
import com.example.bankservice.repository.AccountRepository;
import com.example.bankservice.repository.TransactionRepository;
import com.example.bankservice.repository.UserRepository;
import com.example.bankservice.resource.UserInfoResource;
import com.example.bankservice.util.CurrencyValidator;
import com.example.bankservice.util.MoneyConverter;
import com.example.bankservice.util.UUIDGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
public class CurrentAccount implements Account {

    @Autowired
    MoneyConverter moneyConverter;

    @Autowired
    UserRepository userRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    CurrencyValidator currencyValidator;


    private final double creditLimit = 1000.00;

    @Override
    @Transactional
    public String registerAccount(UserInfoResource userInfoResource) {
        Optional<UserInfo> existUser = userRepository.findByIdentityNo(userInfoResource.getIdentityNo());
        if(existUser.isPresent()){
            List<AccountInfo> accountInfoList = accountRepository.findByUser(existUser.get());
            for(AccountInfo singleAccount: accountInfoList){
                if(!singleAccount.getType().equals("CURRENT")){
                    saveAccount(existUser.get(), userInfoResource);
                }else{
                    throw new DataConflictException("The User Already have Current account!");
                }
            }

        }else{
            if(!currencyValidator.validateCurrency(userInfoResource.getCurrency())){
                throw new DataNotFoundException("Currency Not Available");
            }
            UserInfo userInfo = saveUser(userInfoResource);
            saveAccount(userInfo,userInfoResource);
        }
        return "Registered Success";
    }

    @Override
    public UserInfo saveUser(UserInfoResource userInfoResource){
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userInfoResource, userInfo);
        userInfo.setPassword("1234");
        return userRepository.save(userInfo);
    }

    @Override
    public void saveAccount(UserInfo userInfo,UserInfoResource userInfoResource){
        String accountNumber = UUIDGenerator.generateUUID();
        AccountInfo savingsAccount = new AccountInfo(0L, 0.0, "CURRENT", creditLimit,accountNumber, userInfo, "1120", userInfoResource.getCurrency());
        accountRepository.save(savingsAccount);
    }

    @Override
    public boolean userExist(String identityNo) {
        if(userRepository.findByIdentityNo(identityNo).isPresent()){
            return true;
        }
        return false;
    }

    @Override
    public String deposit(String accType, String moneyType, String accountNum, double amount) {
         Optional<AccountInfo> accountInfo = accountRepository.findByAccountNumber(accountNum);
         if(!accountExist(accountNum)){
             throw new DataNotFoundException("The account Number not exist");
         }else if(!accountInfo.get().getType().equals("CURRENT")){
             throw new DataNotFoundException("The account holder not registered with this account");
         }
         double convertedAmount = moneyConverter.convert(moneyType, amount);
         accountInfo.get().setCurrentBalance(accountInfo.get().getCurrentBalance()+convertedAmount);
         Instant instant = Instant.now();
         Timestamp timestamp = Timestamp.from(instant);
         Transaction transaction = new Transaction(0L, "DEPOSIT",timestamp, amount, true, accountInfo.get());
         accountRepository.save(accountInfo.get());
         transactionRepository.save(transaction);
         return "Transaction Success";
    }

    @Override
    public String withdraw(String accType, String moneyType, String accountNum, double amount, String password) {
        Optional<AccountInfo> accountInfo = accountRepository.findByAccountNumber(accountNum);
        if(!accountExist(accountNum)){
            throw new DataNotFoundException("The account Number not exist");
        }else if(!accountInfo.get().getType().equals("CURRENT")){
            throw new DataNotFoundException("The account holder not registered with this account");
        }
        if(!password.equals(accountInfo.get().getUser().getPassword())){
            throw new DataConflictException("Password is Wrong!");
        }

        if(creditLimitExceed(accType,accountNum,amount)){
            throw new DataConflictException("No Balance");
        }
        double convertedAmount = moneyConverter.convert(moneyType, amount);
        accountInfo.get().setCurrentBalance(accountInfo.get().getCurrentBalance()-convertedAmount);
        Instant instant = Instant.now();
        Timestamp timestamp = Timestamp.from(instant);
        Transaction transaction = new Transaction(0L, "WITHDRAW",timestamp, amount, true, accountInfo.get());
        accountRepository.save(accountInfo.get());
        transactionRepository.save(transaction);
        return "Transaction Success";
    }

    @Override
    public boolean creditLimitExceed(String accType, String accountNum, double amount) {
        Optional<AccountInfo> accountInfo = accountRepository.findByAccountNumber(accountNum);
        if(accountInfo.get().getCurrentBalance() <= amount){
            return true;
        }
        return false;
    }

    @Override
    public boolean accountExist(String accountNum) {
       if(accountRepository.findByAccountNumber(accountNum).isPresent()){
           return true;
       }
       return false;
    }

}
