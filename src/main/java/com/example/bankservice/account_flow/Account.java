package com.example.bankservice.account_flow;

import com.example.bankservice.modal.UserInfo;
import com.example.bankservice.resource.UserInfoResource;

public interface Account {

    public String registerAccount(UserInfoResource userInfoResource);

    public boolean userExist(String identityNo);

    public String deposit(String accType, String moneyType, String accountNum, double amount);

    public String withdraw(String accType,String moneyType, String accountNum, double amount, String password);

    public boolean creditLimitExceed(String accType, String accountNum, double amount);

    public UserInfo saveUser(UserInfoResource userInfoResource);

    public void saveAccount(UserInfo userInfo, UserInfoResource userInfoResource);

    public boolean accountExist(String accountNum);
}
