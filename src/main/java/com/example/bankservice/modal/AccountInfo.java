package com.example.bankservice.modal;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class AccountInfo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private double currentBalance;
    private String type;
    private double creditLimit;
    private String accountNumber;
    @ManyToOne
    private UserInfo user;
    // It can be ManyToOne but I simplified with random ID
    private String branchId;
    private String currencyType;
}
