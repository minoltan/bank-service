package com.example.bankservice.util;

import org.springframework.stereotype.Component;

@Component
public class MoneyConverter {
    private double EURO_RATE = 1.25;
    // Use any current currency rate api and get the rate and convert it to USD
    public double moneyConverter(String type, double amount){
        switch (type){
            case "USD":{return amount;}
            case "EURO":{return EURO_RATE* amount;}
            default: return 0.0;
        }
    }
}
