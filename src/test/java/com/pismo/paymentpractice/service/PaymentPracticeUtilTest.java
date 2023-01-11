package com.pismo.paymentpractice.service;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentPracticeUtilTest {
    @Test
    void generateUserAccountIdBasedInUUIDStringify() {
        var verifyId = PaymentPracticeUtil.generateAccountId();
        assertEquals(UUID.fromString(verifyId).toString(), verifyId);
    }
}