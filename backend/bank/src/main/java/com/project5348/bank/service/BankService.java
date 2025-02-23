package com.project5348.bank.service;


import com.project5348.bank.model.Account;
import com.project5348.bank.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankService {


    @Autowired
    AccountRepository accountRepository;

    public BankService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public boolean processRefund(Long buyerId, Long sellerId, double amount) {
        Account buyerAccount = accountRepository.findAccountByCustomerId(buyerId);
        Account sellerAccount = accountRepository.findAccountByCustomerId(sellerId);

        //check if both users has account
        if (buyerAccount == null || sellerAccount == null){
            return false;
        }

        Double sellerBalance = sellerAccount.getBalance();
        if (sellerBalance - amount < 0){
            return false; // Reject refund due to insufficient balance
        }
        buyerAccount.setBalance(buyerAccount.getBalance() + amount);
        sellerAccount.setBalance(sellerBalance - amount);

        accountRepository.save(buyerAccount);
        accountRepository.save(sellerAccount);
        return true;

    }
    @Transactional
    public boolean checkSufficientFundForCustomer(Long customerId, Double price){
        Account account = accountRepository.findAccountByCustomerId(customerId);
        return account.getBalance() >= price;
    }

}
