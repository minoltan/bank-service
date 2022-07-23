package com.example.bankservice.account_flow;

import com.example.bankservice.account_flow.current.CurrentAccount;
import com.example.bankservice.account_flow.savings.SavingsAccount;
import com.example.bankservice.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountFactory {
    @Autowired
    private CurrentAccount currentAccount;
    @Autowired
    private SavingsAccount savingsAccount;


    public Account getAccountType(String type) {
        switch (type){
            //Todo: Use the parameter
            case "CURRENT":
                return currentAccount;
            case "SAVINGS":
                return savingsAccount;
            default:
                throw new DataNotFoundException("The Required Account type is not present!");
        }
    }

}
