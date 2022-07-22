package com.example.bankservice.service;

import com.example.bankservice.account_flow.Account;
import com.example.bankservice.account_flow.AccountFactory;
import com.example.bankservice.resource.UserInfoResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {
    @Autowired
    AccountFactory accountFactory;

    public void registerAccount(UserInfoResource userInfoResource){
        Account account =  accountFactory.getAccountType(userInfoResource.getType());
        account.registerAccount(userInfoResource);
    }

    // save all currencies in USD
    // transaction table (transaction_id, type, status, amount)



}
