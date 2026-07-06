package com.modernjava.payment;

import com.modernjava.domain.Card;
import com.modernjava.domain.PaymentResponse;

public final class CreditCardPayment extends PaymentGateway {
    @Override
    public PaymentResponse makePayment(Card card, double amount) {
        System.out.println("Processing credit card payment for card number: " + card.cardNumber() + " with amount: " + amount);
        return PaymentResponse.SUCCESS;
    }
}
