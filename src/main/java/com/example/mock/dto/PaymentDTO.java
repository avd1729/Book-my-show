package com.example.mock.dto;

import com.example.mock.enums.PaymentStatus;
import com.example.mock.enums.PaymentType;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class PaymentDTO {
    private int paymentId;
    private int amount;
    private PaymentType paymentType;
    private String transactionId;
    private PaymentStatus paymentStatus;
    private Timestamp paymentTime;
    private ReservationDTO reservationDTO;
}
