package com.example.bankservice.adapter.inbound;

import com.example.bankservice.resource.DepositResource;
import com.example.bankservice.resource.WithdrawResource;
import com.example.bankservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/deposit")
    public void depositMoney(@RequestBody DepositResource depositResource){
       transactionService.depositMoney(depositResource);
    }

    @PostMapping("/withdraw")
    public void withdrawMoney(@RequestBody WithdrawResource withdrawResource){
       transactionService.withdrawMoney(withdrawResource);
    }


}
