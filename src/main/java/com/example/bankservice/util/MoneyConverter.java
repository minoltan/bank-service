package com.example.bankservice.util;

import com.example.bankservice.exception.DataConflictException;
import com.example.bankservice.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MoneyConverter {

    @Autowired
    CurrencyValidator currencyValidator;

    private double EURO_RATE = 1.25;
    // Use any current currency rate api and get the rate and convert it to USD
    public double convert(String type, double amount){
        if(!currencyValidator.validateCurrency(type)){
           throw new DataNotFoundException("Currency Not Available");
        }
        switch (type){
            case "USD":{return amount;}
            case "EURO":{return EURO_RATE* amount;}
            default: return 0.0;
        }

    }



}
