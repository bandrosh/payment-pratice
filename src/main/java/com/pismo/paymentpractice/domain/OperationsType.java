package com.pismo.paymentpractice.domain;

public enum OperationsType {
    CASH_PURCHASES, INSTALLMENT_PURCHASES, WITHDRAW, PAYMENT, INVALID_OPERATION_TYPE;

    public int getValue() {
        return ordinal() + 1;
    }
}
