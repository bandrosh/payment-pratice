package com.pismo.paymentpractice.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccountDTO(@JsonProperty("account_id") String accountId,
                         @JsonProperty("document_number") String documentNumber) {
}
