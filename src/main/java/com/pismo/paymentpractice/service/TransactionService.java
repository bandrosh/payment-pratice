package com.pismo.paymentpractice.service;

import com.pismo.paymentpractice.domain.OperationsType;
import com.pismo.paymentpractice.domain.Transaction;
import com.pismo.paymentpractice.exception.NotProcessableException;
import com.pismo.paymentpractice.repository.TransactionRepository;
import com.pismo.paymentpractice.repository.entity.AccountModel;
import com.pismo.paymentpractice.repository.entity.TransactionModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class TransactionService {
    private static final Logger logger = LogManager.getLogger(TransactionService.class.getName());
    public static final int NEGATIVE_MULTIPLICATIVE = -1;

    private final AccountService accountService;
    private final OperationsTypeService operationsTypeService;
    private final TransactionRepository transactionRepository;

    public TransactionService(AccountService accountService, OperationsTypeService operationsTypeService, TransactionRepository transactionRepository) {
        this.accountService = accountService;
        this.operationsTypeService = operationsTypeService;
        this.transactionRepository = transactionRepository;
    }

    public Transaction publishTransaction(Transaction transaction) {
        logger.debug(() -> format("Trying publish transaction. %s", transaction.toString()));

        logger.debug(() -> format("Check if valid operations by if. %d", transaction.operationTypeId()));
        var operation = operationsTypeService.getOperationTypeById(transaction.operationTypeId());

        logger.debug(() -> format("Check if exists user account by id. %s", transaction.accountId()));
        var userAccount = accountService.getUserAccountById(transaction.accountId());

        var transactionModel = TransactionModel.toTransactionModel(transaction);

        transactionModel.setAccount(AccountModel.toAccountModel(userAccount));

        if (operation != OperationsType.PAYMENT) {
            transactionModel.setAmount(transactionModel.getAmount() * NEGATIVE_MULTIPLICATIVE);
        }

        try {
            var savedTransaction = transactionRepository.save(transactionModel);
            logger.debug(() -> format("Transaction Persisted. %s", savedTransaction));

            return savedTransaction.toTransaction();
        } catch (Exception e) {
            logger.error(() -> format("Error persisting transaction. %s", transactionModel));

            throw new NotProcessableException(e.getMessage());
        }
    }
}
