package com.example.bankservice.account_flow.savings;

import com.example.bankservice.account_flow.Account;
import com.example.bankservice.exception.DataConflictException;
import com.example.bankservice.exception.DataNotFoundException;
import com.example.bankservice.modal.AccountInfo;
import com.example.bankservice.modal.UserInfo;
import com.example.bankservice.repository.AccountRepository;
import com.example.bankservice.repository.UserRepository;
import com.example.bankservice.resource.UserInfoResource;
import com.example.bankservice.util.MoneyConverter;
import com.example.bankservice.util.UUIDGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class SavingsAccount implements Account {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    MoneyConverter moneyConverter;

    private final double creditLimit = 00.00;
    @Override
    @Transactional
    public void registerAccount(UserInfoResource userInfoResource) {
        if(userExist(userInfoResource.getIdentityNo())){
            throw new DataConflictException("User Already Exist!");
        }
        UserInfo userInfo = new UserInfo();
        String accountNumber = UUIDGenerator.generateUUID();
        AccountInfo currentAccount = new AccountInfo(0L, 0.0, "SAVINGS", creditLimit,accountNumber, userInfo, "1120");
        BeanUtils.copyProperties(userInfoResource, userInfo);
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
        if(accountExist(accountNum)){
            throw new DataNotFoundException("The account Number not exist");
        }
        moneyConverter.moneyConverter(moneyType, amount);
    }

    @Override
    public void withdraw(String accType, String moneyType, String accountNum, double amount, String password) {

    }

    @Override
    public boolean creditLimitExceed(String accType, String accountNum, double amount) {
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
