package com.pismo.paymentpractice.service;

import com.pismo.paymentpractice.domain.Account;
import com.pismo.paymentpractice.exception.CreateAccountErrorException;
import com.pismo.paymentpractice.exception.NotFoundErrorException;
import com.pismo.paymentpractice.repository.AccountRepository;
import com.pismo.paymentpractice.repository.entity.AccountModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class AccountServiceTest {
    private AccountService accountService;
    @MockBean
    private AccountRepository accountRepository;

    @BeforeEach
    public void init() {
        this.accountService = new AccountService(accountRepository);
    }

    @Test
    void whenTrySaveOneNewAccountWithValidIdAndDocumentNumberThenReturnNewAccountCreated() {
        var account = new AccountModel("account1", "doc1");
        when(accountRepository.save(any(AccountModel.class))).thenReturn(account);

        var response = accountService.saveAccount(new Account("account1", "doc1"));

        assertEquals(response.id(), account.getId());
        assertEquals(response.documentNumber(), account.getDocumentNumber());

        verify(accountRepository, times(1)).save(any(AccountModel.class));
    }

    @Test
    void whenTrySaveOneNewAccountWithInvalidIdOrDocumentNumberThenThrowException() {
        var account = new Account("id", "documentNumber");
        when(accountRepository.save(any(AccountModel.class))).thenThrow(
                new CreateAccountErrorException("Error creating save invalid account"));
        assertThrows(CreateAccountErrorException.class, () -> accountService.saveAccount(account));
        verify(accountRepository, times(1)).save(any(AccountModel.class));
    }

    @Test
    void whenTryGetOneNewAccountWithValidAccountIdThenReturnAccount() {
        var responseAccountModel = new AccountModel("account1", "doc1");
        when(accountRepository.findById(anyString())).thenReturn(Optional.of(responseAccountModel));

        var response = accountService.getUserAccountById("account1");

        assertEquals(response.id(), responseAccountModel.getId());
        assertEquals(response.documentNumber(), responseAccountModel.getDocumentNumber());

        verify(accountRepository, times(1)).findById(anyString());
    }

    @Test
    void whenTryGetOneNewAccountWithNonExistedAccountThenThrowUserAccountNotFound() {
        when(accountRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(NotFoundErrorException.class, () -> accountService.getUserAccountById("any Id"));
        verify(accountRepository, times(1)).findById(anyString());
    }
}