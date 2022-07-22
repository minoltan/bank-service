package com.example.bankservice.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("acc_type")
    private String accType;
    @JsonProperty("money_type")
    private String moneyType;
    @JsonProperty("account_num")
    private String accountNum;
    private String password;
}
