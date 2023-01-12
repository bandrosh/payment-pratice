package com.pismo.paymentpractice.domain;

public record Transaction(String accountId, Integer operationTypeId, Double amount) {
}
