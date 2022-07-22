package com.example.bankservice.service;

import com.example.bankservice.account_flow.Account;
import com.example.bankservice.account_flow.AccountFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    @Autowired
    AccountFactory accountFactory;

    public void depositMoney(String accType, String moneyType, String accountNum, double amount){
        // can record the depositor's detail, here I omit that
        Account account =  accountFactory.getAccountType(accType);
        account.deposit(accType,moneyType,accountNum, amount);
    }

    public void withdrawMoney(String accType, String moneyType, String accountNum, double amount, String password){
        Account account =  accountFactory.getAccountType(accType);
        account.withdraw(accType,moneyType, accountNum, amount, password);
    }

}
