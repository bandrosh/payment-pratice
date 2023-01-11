package com.pismo.paymentpractice.service;

import com.pismo.paymentpractice.domain.Account;
import com.pismo.paymentpractice.exception.CreateAccountErrorException;
import com.pismo.paymentpractice.exception.NotFoundErrorException;
import com.pismo.paymentpractice.repository.AccountRepository;
import com.pismo.paymentpractice.repository.entity.AccountModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class AccountService {
    private static final Logger logger = LogManager.getLogger(AccountService.class.getName());
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account saveAccount(Account account) {
        var accountModel = new AccountModel();
        try {
            var databaseAccountCreated = accountRepository.save(accountModel.toAccountModel(account));
            logger.debug(() -> format("User account created. Document Number %s", account.documentNumber()));
            return accountModel.toAccount(databaseAccountCreated);
        } catch (Exception e) {
            logger.error(() -> format("Error creating user account. Document number %s.", account.documentNumber()));
            throw new CreateAccountErrorException(e.getMessage());
        }
    }

    public Account getUserAccountById(String accountId) {
        var accountModel = new AccountModel();
        var account = accountRepository.findById(accountId);

        if (account.isPresent()) {
            logger.debug(() -> format("Get user account. Account: %s", account.get()));
        } else {
            logger.error(() -> format("User account does not exists. Id: %s", accountId));
        }

        return accountModel.toAccount(account.orElseThrow(
                () -> new NotFoundErrorException(format("Account is id not found. Id %s", accountId))));
    }
}
