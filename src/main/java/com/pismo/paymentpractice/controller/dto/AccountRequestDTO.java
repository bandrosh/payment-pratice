package com.pismo.paymentpractice.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pismo.paymentpractice.domain.Account;

public record AccountRequestDTO(@JsonProperty("document_number") String documentNumber) {
    public Account toAccount(String accountId) {
        return new Account(accountId, this.documentNumber);
    }
}
