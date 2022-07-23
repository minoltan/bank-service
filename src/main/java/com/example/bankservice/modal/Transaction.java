package com.example.bankservice.modal;


import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String type;
    private Timestamp timestamp;
    private double amount;
    private boolean status;
    @ManyToOne
    private AccountInfo accountInfo;
}
