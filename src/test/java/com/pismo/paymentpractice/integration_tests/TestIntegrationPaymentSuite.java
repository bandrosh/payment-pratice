package com.pismo.paymentpractice.integration_tests;

import com.pismo.paymentpractice.controller.dto.AccountDTO;
import com.pismo.paymentpractice.controller.dto.AccountRequestDTO;
import com.pismo.paymentpractice.controller.dto.TransactionDTO;
import com.pismo.paymentpractice.domain.OperationsType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestIntegrationPaymentSuite {
    public static final int NEGATIVE_MULTIPLICATIVE = -1;
    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "/api/v1";

    @Test
    void createNewAccountIntegrationTest() {
        var accountURL = format("%s/accounts", BASE_URL);

        var accountRequestDTO = new AccountRequestDTO("1234-5678");
        var responseCreatingAccount = restTemplate.postForEntity(accountURL, accountRequestDTO, AccountDTO.class);

        var statusCreatingAccount = responseCreatingAccount.getStatusCode().value();
        var resultBodyCreatingAccount = responseCreatingAccount.getBody();

        assert resultBodyCreatingAccount != null;
        var responseFindAccount = restTemplate.getForEntity(
                format("%s/%s", accountURL, resultBodyCreatingAccount.accountId()), AccountDTO.class);

        var statusFindingNewAccount = responseFindAccount.getStatusCode().value();
        var resultBodyFoundNewAccount = responseFindAccount.getBody();

        assertEquals(HttpStatus.OK.value(), statusCreatingAccount);
        assertEquals(HttpStatus.OK.value(), statusFindingNewAccount);
        assertInstanceOf(AccountDTO.class, resultBodyCreatingAccount);
        assertInstanceOf(AccountDTO.class, resultBodyFoundNewAccount);
        assertEquals(resultBodyCreatingAccount, resultBodyFoundNewAccount);
    }

    @Test
    void createNewTransactionIntegrationTest() {
        var accountURL = format("%s/accounts", BASE_URL);

        var accountRequestDTO = new AccountRequestDTO("1234-5678");
        var responseCreatingAccount = restTemplate.postForEntity(accountURL, accountRequestDTO, AccountDTO.class);

        var statusCreatingAccount = responseCreatingAccount.getStatusCode().value();
        var resultBodyCreatingAccount = responseCreatingAccount.getBody();

        assert resultBodyCreatingAccount != null;

        var transactionUrl = format("%s/transactions", BASE_URL);
        var createTransactionDTO = new TransactionDTO(
                resultBodyCreatingAccount.accountId(), OperationsType.CASH_PURCHASES.getValue(), 11.0);

        var responseCreateTransaction = restTemplate.postForEntity(
                transactionUrl, createTransactionDTO, TransactionDTO.class);

        var statusPublishTransaction = responseCreateTransaction.getStatusCode().value();
        var resultBodyPublishTransaction = responseCreateTransaction.getBody();

        assertEquals(HttpStatus.OK.value(), statusCreatingAccount);
        assertEquals(HttpStatus.OK.value(), statusPublishTransaction);

        assertInstanceOf(AccountDTO.class, resultBodyCreatingAccount);
        assertInstanceOf(TransactionDTO.class, resultBodyPublishTransaction);

        assert resultBodyPublishTransaction != null;
        assertEquals(resultBodyPublishTransaction.accountId(), createTransactionDTO.accountId());
        assertEquals(resultBodyPublishTransaction.operationTypeId(), createTransactionDTO.operationTypeId());
        assertEquals(resultBodyPublishTransaction.amount() * NEGATIVE_MULTIPLICATIVE, createTransactionDTO.amount());
    }

}
