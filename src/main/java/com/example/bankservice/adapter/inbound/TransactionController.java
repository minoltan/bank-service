package com.example.bankservice.adapter.inbound;

import com.example.bankservice.resource.DepositResource;
import com.example.bankservice.resource.WithdrawResource;
import com.example.bankservice.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(UserRegistrationController.class);

    @PostMapping("/deposit")
    public void depositMoney(@RequestBody DepositResource depositResource){
        logger.info("request - depositMoney | (URL - /api/v1/transaction/deposit");
        transactionService.depositMoney(depositResource);
    }

    @PostMapping("/withdraw")
    public void withdrawMoney(@RequestBody WithdrawResource withdrawResource){
       transactionService.withdrawMoney(withdrawResource);
    }


}
