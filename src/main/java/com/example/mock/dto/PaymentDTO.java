package com.example.mock.dto;

import com.example.mock.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class PaymentDTO {
    private int paymentId;
    private int amount;
    private String paymentType;
    private int transactionId;
    private PaymentStatus paymentStatus;
    private Timestamp paymentTime;
    private ReservationDTO reservationDTO;
}
