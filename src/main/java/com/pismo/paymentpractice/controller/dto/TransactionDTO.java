package com.pismo.paymentpractice.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pismo.paymentpractice.domain.Transaction;
import jakarta.validation.constraints.Min;

public record TransactionDTO(@JsonProperty("account_id") String accountId,
                             @JsonProperty("operation_type_id") Integer operationTypeId,
                             @JsonProperty("amount") @Min(value = 0L, message = "Amount must be positive.")
                             Double amount) {

    public Transaction toTransaction() {
        return new Transaction(this.accountId, this.operationTypeId, this.amount);
    }

    @Override
    public String toString() {
        return "TransactionRequestDTO{" +
                "accountId='" + accountId + '\'' +
                ", operationTypeId=" + operationTypeId +
                ", amount=" + amount +
                '}';
    }
}
