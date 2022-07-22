package com.example.bankservice.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserInfoResource {

    private String name;
    private String email;
    private String type;
    private String currency;
    @JsonProperty("identity_no")
    private String identityNo;

}
