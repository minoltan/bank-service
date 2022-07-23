package com.example.bankservice.adapter.inbound;

import com.example.bankservice.resource.UserInfoResource;
import com.example.bankservice.service.UserRegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/register")
@EnableTransactionManagement
public class UserRegistrationController {

    private Logger logger = LoggerFactory.getLogger(UserRegistrationController.class);

    @Autowired
    UserRegistrationService userRegistrationService;

    /**
     * This method is used to register for public user
     * @param userInfoResource
     */
    @PostMapping("/user")
    @CrossOrigin(origins = "*")
    public String registerAccount(@RequestBody UserInfoResource userInfoResource){
        logger.info("request - registerAccount | (URL - /api/v1/register/user");
        return userRegistrationService.registerAccount(userInfoResource);
    }


}
