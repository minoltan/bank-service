package com.example.bankservice.repository;

import com.example.bankservice.modal.AccountInfo;
import com.example.bankservice.modal.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountInfo, Long> {
    Optional<AccountInfo> findByAccountNumber(String accNo);

    Optional<AccountInfo> findByUser(UserInfo userInfo);
}
