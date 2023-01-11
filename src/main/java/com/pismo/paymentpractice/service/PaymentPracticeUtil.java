package com.pismo.paymentpractice.service;

import java.util.UUID;

public class PaymentPracticeUtil {
    private PaymentPracticeUtil() {
    }

    public static String generateAccountId() {
        return UUID.randomUUID().toString();
    }

}
