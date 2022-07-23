package com.example.bankservice.util;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Minoltan Issack on 7/23/2022
 */

@Component
public class CurrencyValidator {
    public boolean validateCurrency(String type){
        List<String> currencyList = new ArrayList<>();
        currencyList.add("USD");
        currencyList.add("LKR");
        currencyList.add("EURO");
        if(currencyList.contains(type)){
            return true;
        }
        return false;
    }
}
