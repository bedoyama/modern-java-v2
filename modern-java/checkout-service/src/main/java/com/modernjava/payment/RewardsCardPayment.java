package com.modernjava.payment;

import com.modernjava.domain.Card;
import com.modernjava.domain.PaymentResponse;

public final class RewardsCardPayment extends PaymentGateway {
    @Override
    public PaymentResponse makePayment(Card card, double amount) {
        System.out.println("Processing rewards card payment for card number: " + card.cardNumber() + " with amount: " + amount);
        return PaymentResponse.SUCCESS;
    }
}
