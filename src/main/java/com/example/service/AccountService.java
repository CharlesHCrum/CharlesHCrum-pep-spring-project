package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
@Component
public class AccountService {
    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /*
     * Registers a new account 
     * If username is duplicate returns null and registration will fail in controller
     * Attempts to persist new account to database if not duplicate. 
     * Other requirements for account are checked in the controller
     */
    public Account registerAccount(Account account){
        Optional<Account> anAccount = accountRepository.findByUsername(account.getUsername());
        if(anAccount.isEmpty()){
            return accountRepository.save(account);
        }
        return null;
    }

    /*
     * Login an account. Only logs in if username matches an existing account and the password matches that account
     * @param account to be logged in 
     * @return the account
     */
    public Account loginAccount(Account account){
        Optional<Account> anAccount = accountRepository.findByUsername(account.getUsername());
        if(anAccount.isPresent()){
            if(anAccount.get().getPassword().equals(account.getPassword())){
                return anAccount.get();
            }
            return null;
        }
        return null;
    }
}
