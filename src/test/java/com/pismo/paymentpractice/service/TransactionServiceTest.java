package com.pismo.paymentpractice.service;

import com.pismo.paymentpractice.domain.Account;
import com.pismo.paymentpractice.domain.OperationsType;
import com.pismo.paymentpractice.domain.Transaction;
import com.pismo.paymentpractice.exception.NotFoundErrorException;
import com.pismo.paymentpractice.exception.NotProcessableException;
import com.pismo.paymentpractice.repository.TransactionRepository;
import com.pismo.paymentpractice.repository.entity.AccountModel;
import com.pismo.paymentpractice.repository.entity.TransactionModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class TransactionServiceTest {
    public static final int NEGATIVE_MULTIPLICATIVE = -1;
    private TransactionService transactionService;

    @MockBean
    private AccountService accountService;
    @MockBean
    private OperationsTypeService operationsTypeService;

    @MockBean
    private TransactionRepository transactionRepository;

    @BeforeEach
    void init() {
        this.transactionService = new TransactionService(accountService, operationsTypeService, transactionRepository);
    }

    @Test
    void whenTryPublishValidTransactionThenReturnPersistedTransaction() {
        var validAccount = new Account("ValidAccountId", "ValidDocumentNumber");

        var responseTransaction = new TransactionModel(OperationsType.CASH_PURCHASES.getValue(), -100.0);
        responseTransaction.setId(1L);
        responseTransaction.setEventDate(Timestamp.valueOf(
                LocalDateTime.of(2022, 2, 10, 0, 1, 1, 1)));
        responseTransaction.setAccount(AccountModel.toAccountModel(validAccount));

        when(operationsTypeService.getOperationTypeById(anyInt())).thenReturn(OperationsType.CASH_PURCHASES);
        when(accountService.getUserAccountById(anyString())).thenReturn(validAccount);
        when(transactionRepository.save(any(TransactionModel.class))).thenReturn(responseTransaction);

        var transaction = new Transaction("ValidAccountId", 1, 100.0);

        var response = transactionService.publishTransaction(transaction);

        assertEquals(OperationsType.CASH_PURCHASES.getValue(), response.operationTypeId());
        assertEquals(transaction.amount() * NEGATIVE_MULTIPLICATIVE, response.amount());
        assertEquals(transaction.accountId(), response.accountId());

        verify(operationsTypeService, times(1)).getOperationTypeById(anyInt());
        verify(accountService, times(1)).getUserAccountById(anyString());
        verify(transactionRepository, times(1)).save(any(TransactionModel.class));
    }

    @Test
    void whenTryPublishInvalidTransactionThenThrowNotProcessedError() {
        var validAccount = new Account("ValidAccountId", "ValidDocumentNumber");

        when(operationsTypeService.getOperationTypeById(anyInt())).thenReturn(OperationsType.CASH_PURCHASES);
        when(accountService.getUserAccountById(anyString())).thenReturn(validAccount);
        when(transactionRepository.save(any(TransactionModel.class))).
                thenThrow(new IllegalArgumentException("Any generic database error"));

        var transaction = new Transaction("ValidAccountId", 1, 100.0);

        assertThrows(
                NotProcessableException.class,
                () -> transactionService.publishTransaction(transaction));

        verify(operationsTypeService, times(1)).getOperationTypeById(anyInt());
        verify(accountService, times(1)).getUserAccountById(anyString());
        verify(transactionRepository, times(1)).save(any(TransactionModel.class));
    }

    @Test
    void whenTryPublishOneTransactionWithOperationInvalidThenThrowNotFoundErrorException() {
        when(operationsTypeService.getOperationTypeById(anyInt())).
                thenThrow(new NotFoundErrorException("Operational Type id invalid"));

        var transaction = new Transaction("ValidAccountId", 1, 100.0);

        assertThrows(NotFoundErrorException.class,
                () -> transactionService.publishTransaction(transaction));

        verify(operationsTypeService, times(1)).getOperationTypeById(anyInt());
    }

    @Test
    void whenTryPublishOneTransactionWithNotFoundUserThenThrowNotFoundErrorException() {
        when(operationsTypeService.getOperationTypeById(anyInt())).thenReturn(OperationsType.CASH_PURCHASES);
        when(accountService.getUserAccountById(anyString())).
                thenThrow(new NotFoundErrorException("AccountId not valid"));

        var transaction = new Transaction("ValidAccountId", 1, 100.0);

        assertThrows(NotFoundErrorException.class,
                () -> transactionService.publishTransaction(transaction));

        verify(operationsTypeService, times(1)).getOperationTypeById(anyInt());
        verify(accountService, times(1)).getUserAccountById(anyString());
    }
}