package com.example.bankservice.modal;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Currency {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String type;
    private double local_buying_rate;
    private double local_selling_rate;

    // Here buying rate and selling rate considerate with USD
    // Eg: type = USD , local_buying_rate = 1 local_selling_rate = 1
    // Eg: type = EURO, local_buying_rate = 1.25 local_selling_rate = 1.35

}
