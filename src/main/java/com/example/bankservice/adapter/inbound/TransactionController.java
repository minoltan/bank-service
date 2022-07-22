package com.example.bankservice.adapter.inbound;

import com.example.bankservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/deposit")
    public void depositMoney(String accType, String moneyType, String accountNum, double amount){
       transactionService.depositMoney(accType,moneyType, accountNum, amount);
    }

    @PostMapping("/withdraw")
    public void withdrawMoney(String accType, String moneyType, String accountNum, double amount, String password){
       transactionService.withdrawMoney(accType,moneyType, accountNum, amount, password);
    }


}
