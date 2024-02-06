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

    public Account registerAccount(Account account){
        Optional<Account> anAccount = accountRepository.findByUsername(account.getUsername());
        if((account.getPassword().length() >= 4) && (account.getUsername().length() > 0) && (anAccount.isEmpty())){
            return accountRepository.save(account);
        }
        return null;
    }

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
