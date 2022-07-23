package com.example.bankservice.service;

import com.example.bankservice.account_flow.Account;
import com.example.bankservice.account_flow.AccountFactory;
import com.example.bankservice.resource.DepositResource;
import com.example.bankservice.resource.WithdrawResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    @Autowired
    AccountFactory accountFactory;

    public String depositMoney(DepositResource depositResource){
        // can record the depositor's detail, here I omit that
        Account account =  accountFactory.getAccountType(depositResource.getAccType());
        return account.deposit(depositResource.getAccType(), depositResource.getMoneyType(), depositResource.getAccountNum(), depositResource.getAmount());
    }

    public String withdrawMoney(WithdrawResource withdrawResource){
        Account account =  accountFactory.getAccountType(withdrawResource.getAccType());
        return account.withdraw(withdrawResource.getAccType(), withdrawResource.getMoneyType(), withdrawResource.getAccountNum(), withdrawResource.getAmount(), withdrawResource.getPassword());
    }



}
