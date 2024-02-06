package com.example.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer>{
    //gets an account by username if one exists
    Optional<Account> findByUsername(String username);
}
