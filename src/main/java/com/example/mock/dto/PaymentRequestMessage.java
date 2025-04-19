package com.example.mock.dto;

import com.example.mock.enums.PaymentType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PaymentRequestMessage implements Serializable {
    private Integer reservationId;
    private Integer userId;
    private int amount;
    private PaymentType paymentType;
    private long timestamp;
    private String transactionId;
}