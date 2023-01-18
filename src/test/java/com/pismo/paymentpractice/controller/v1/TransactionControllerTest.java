package com.pismo.paymentpractice.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pismo.paymentpractice.controller.dto.TransactionDTO;
import com.pismo.paymentpractice.domain.Transaction;
import com.pismo.paymentpractice.exception.NotFoundErrorException;
import com.pismo.paymentpractice.exception.NotProcessableException;
import com.pismo.paymentpractice.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static java.lang.String.format;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransactionService transactionService;


    @Test
    void whenTrySaveNewUserTransactionWithValidValuesThenReturn200() throws Exception {
        var transactionDTO = new TransactionDTO("accountId", 1, 10.0);
        var transaction = new Transaction("accountId", 1, 10.0);

        when(transactionService.publishTransaction(any(Transaction.class))).thenReturn(transaction);

        mockMvc.perform(post("/api/v1/transactions")
                       .contentType(MediaType.APPLICATION_JSON)
                       .accept(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(transactionDTO))
               )
               .andExpect(status().isOk());
    }

    @Test
    void whenTrySaveNewUserTransactionWithNotValidBodyThenReturn400() throws Exception {
        mockMvc.perform(post("/api/v1/transactions")
                       .contentType(MediaType.APPLICATION_JSON)
                       .accept(MediaType.APPLICATION_JSON)
               )
               .andExpect(status().isBadRequest());

    }

    @Test
    void whenTrySaveNewTransactionWithNotExistedUserThenReturn404() throws Exception {
        var accountId = "accountId";
        var transactionDTO = new TransactionDTO("accountId", 1, 10.0);

        doThrow(new NotFoundErrorException(format("Account is id not found. Id %s", accountId))).when(transactionService)
                                                                                                .publishTransaction(any(Transaction.class));

        mockMvc.perform(post("/api/v1/transactions")
                       .contentType(MediaType.APPLICATION_JSON)
                       .accept(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(transactionDTO))
               )
               .andExpect(status().isNotFound())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("errorCode").value("404 Not Found"))
               .andExpect(jsonPath("message").value(format("Account is id not found. Id %s", accountId)));
    }

    @Test
    void whenTrySaveNewTransactionAndCannotProcessThenReturn422() throws Exception {
        var transactionDTO = new TransactionDTO("accountId", 1, 10.0);

        doThrow(new NotProcessableException("422 Unprocessable Request")).when(transactionService)
                                                                         .publishTransaction(any(Transaction.class));

        mockMvc.perform(post("/api/v1/transactions")
                       .contentType(MediaType.APPLICATION_JSON)
                       .accept(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(transactionDTO))
               )
               .andExpect(status().isUnprocessableEntity())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("errorCode").value("422 Unprocessable Request"));
    }
}
