package com.pismo.paymentpractice.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pismo.paymentpractice.controller.dto.AccountRequestDTO;
import com.pismo.paymentpractice.domain.Account;
import com.pismo.paymentpractice.exception.NotFoundErrorException;
import com.pismo.paymentpractice.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static java.lang.String.format;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AccountService accountService;

    @Test
    void whenTrySaveNewUserWithValidDocumentNumberThenReturn200() throws Exception {
        var accountDTO = new AccountRequestDTO("document1");
        var account = new Account("account1", "document1");
        when(accountService.saveAccount(any(Account.class))).thenReturn(account);

        mockMvc.perform(post("/api/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountDTO))
                )
                .andExpect(status().isOk());

    }

    @Test
    void whenTrySaveNewUserWithoutDocumentNumberThenReturn400() throws Exception {
        mockMvc.perform(post("/api/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());

    }

    @Test
    void whenTryGetUserWithValidIdAndDocumentNumberThenReturnUserAccountAnd200() throws Exception {
        var account = new Account("account1", "document1");
        when(accountService.getUserAccountById(anyString())).thenReturn(account);

        mockMvc.perform(get("/api/v1/accounts/account1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("account_id").value("account1"))
                .andExpect(jsonPath("document_number").value("document1"));

    }

    @Test
    void whenTryGetUserWithNonExistedIdOrDocumentNumberThenReturn404() throws Exception {
        var accountId = "account1";
        doThrow(new NotFoundErrorException(format("Account is id not found. Id %s", accountId))).when(accountService)
                .getUserAccountById(anyString());

        mockMvc.perform(get("/api/v1/accounts/account1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("errorCode").value("404 Not Found"))
                .andExpect(jsonPath("message").value(format("Account is id not found. Id %s", accountId)));
    }
}
