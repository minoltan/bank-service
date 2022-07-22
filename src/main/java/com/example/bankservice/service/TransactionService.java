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

    public void depositMoney(DepositResource depositResource){
        // can record the depositor's detail, here I omit that
        Account account =  accountFactory.getAccountType(depositResource.getAccType());
        account.deposit(depositResource.getAccType(), depositResource.getMoneyType(), depositResource.getAccountNum(), depositResource.getAmount());
    }

    public void withdrawMoney(WithdrawResource withdrawResource){
        Account account =  accountFactory.getAccountType(withdrawResource.getAccType());
        account.withdraw(withdrawResource.getAccType(), withdrawResource.getMoneyType(), withdrawResource.getAccountNum(), withdrawResource.getAmount(), withdrawResource.getPassword());
    }

}
