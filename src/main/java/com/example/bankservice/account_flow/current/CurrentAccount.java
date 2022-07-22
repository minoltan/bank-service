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
import com.example.bankservice.util.MoneyConverter;
import com.example.bankservice.util.UUIDGenerator;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
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


    private final double creditLimit = 1000.00;

    @Override
    @Transactional
    public void registerAccount(UserInfoResource userInfoResource) {
        if(userExist(userInfoResource.getIdentityNo())){
            throw new DataConflictException("User Already Exist!");
        }
        UserInfo userInfo = new UserInfo();
        String accountNumber = UUIDGenerator.generateUUID();
        AccountInfo currentAccount = new AccountInfo(0L, 0.0, "CURRENT", creditLimit,accountNumber, userInfo, "1120");
        BeanUtils.copyProperties(userInfoResource, userInfo);
        userInfo.setPassword("1120");
        userRepository.save(userInfo);
        accountRepository.save(currentAccount);
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
         double convertedAmount = moneyConverter.moneyConverter(moneyType, amount);
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
        if(accountExist(accountNum)){
            throw new DataNotFoundException("The account Number not exist");
        }
        Optional<AccountInfo> account = accountRepository.findByAccountNumber(accountNum);
        if(!password.equals(account.get().getUser().getPassword())){
           throw new DataConflictException("Password is Wrong!");
        }

        if(creditLimitExceed(accType,accountNum,amount)){
            throw new DataConflictException("No Balance");
        }
        double convertedAmount = moneyConverter.moneyConverter(moneyType, amount);
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
