package com.example.bankservice.account_flow.savings;

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
import java.util.Optional;

@Component
public class SavingsAccount implements Account {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    MoneyConverter moneyConverter;

    @Autowired
    CurrencyValidator currencyValidator;

    private final double creditLimit = 00.00;

    @Override
    @Transactional
    public void registerAccount(UserInfoResource userInfoResource) {
        Optional<UserInfo> existUser = userRepository.findByIdentityNo(userInfoResource.getIdentityNo());
        if(existUser.isPresent()){
            Optional<AccountInfo> accountInfo = accountRepository.findByUser(existUser.get());
            if(accountInfo.isPresent() && !accountInfo.get().getType().equals("SAVINGS")){
                saveAccount(existUser.get(), userInfoResource);
            }else{
                throw new DataConflictException("The User Already have Savings account!");
            }
        }else{
            if(!currencyValidator.validateCurrency(userInfoResource.getCurrency())){
                throw new DataNotFoundException("Currency Not Available");
            }
            UserInfo userInfo = saveUser(userInfoResource);
            saveAccount(userInfo,userInfoResource);
        }

    }

    @Override
    public UserInfo saveUser(UserInfoResource userInfoResource){
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userInfoResource, userInfo);
        userInfo.setPassword("1234");
        return userRepository.save(userInfo);
    }

    @Override
    public void saveAccount(UserInfo userInfo, UserInfoResource userInfoResource){
        String accountNumber = UUIDGenerator.generateUUID();
        AccountInfo savingsAccount = new AccountInfo(0L, 0.0, "SAVINGS", creditLimit,accountNumber, userInfo, "1120",userInfoResource.getCurrency());
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
    public void deposit(String accType, String moneyType, String accountNum, double amount) {
        if(!accountExist(accountNum)){
            throw new DataNotFoundException("The account Number not exist");
        }
        double convertedAmount = moneyConverter.convert(moneyType, amount);
        Optional<AccountInfo> account = accountRepository.findByAccountNumber(accountNum);
        account.get().setCurrentBalance(account.get().getCurrentBalance()+convertedAmount);
        Instant instant = Instant.now();
        Timestamp timestamp = Timestamp.from(instant);
        Transaction transaction = new Transaction(0L, "DEPOSIT",timestamp, amount, true);
        accountRepository.save(account.get());
        transactionRepository.save(transaction);
    }

    @Override
    public void withdraw(String accType, String moneyType, String accountNum, double amount, String password) {
        if(!accountExist(accountNum)){
            throw new DataNotFoundException("The account Number not exist");
        }
        Optional<AccountInfo> account = accountRepository.findByAccountNumber(accountNum);
        if(!password.equals(account.get().getUser().getPassword())){
            throw new DataConflictException("Password is Wrong!");
        }

        if(creditLimitExceed(accType,accountNum,amount)){
            throw new DataConflictException("No Balance");
        }
        double convertedAmount = moneyConverter.convert(moneyType, amount);
        account.get().setCurrentBalance(account.get().getCurrentBalance()-convertedAmount);
        Instant instant = Instant.now();
        Timestamp timestamp = Timestamp.from(instant);
        Transaction transaction = new Transaction(0L, "WITHDRAW",timestamp, amount, true);
        accountRepository.save(account.get());
        transactionRepository.save(transaction);
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
