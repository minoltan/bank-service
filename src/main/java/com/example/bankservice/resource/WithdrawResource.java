package com.example.bankservice.resource;

import lombok.*;

/**
 * @author Minoltan Issack on 7/22/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WithdrawResource {
    private String accType;
    private String moneyType;
    private String accountNum;
    private double amount;
    private String password;
}
