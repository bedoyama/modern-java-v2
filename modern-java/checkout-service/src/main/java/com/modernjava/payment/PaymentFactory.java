package com.modernjava.payment;

import com.modernjava.domain.CardType;

public class PaymentFactory {

    public static PaymentGateway paymentGateway(CardType cardType) {
        return switch (cardType) {
            case CREDIT -> new CreditCardPayment();
            case DEBIT -> new DebitCardPayment();
            case REWARDS -> new RewardsCardPayment();
        };
    }
}
