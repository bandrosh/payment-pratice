package com.pismo.paymentpractice.controller.v1;

import com.pismo.paymentpractice.controller.dto.TransactionRequestDTO;
import com.pismo.paymentpractice.service.TransactionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.String.format;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {
    private static final Logger logger = LogManager.getLogger(TransactionController.class.getName());

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transactions")
    public ResponseEntity<TransactionRequestDTO> publishTransaction(@RequestBody TransactionRequestDTO transactionRequestDTO) {
        logger.debug(() ->
                format("Trying publish new transaction %s", transactionRequestDTO.toString()));

        var response = transactionService.publishTransaction(transactionRequestDTO.toTransaction());

        logger.debug(() ->
                format("Transaction performed: %s", response.toString()));
        return ResponseEntity.ok(new TransactionRequestDTO(response.accountId(), response.operationTypeId(),
                response.amount()));
    }
}
