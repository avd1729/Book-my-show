package com.example.mock.dto;

import com.example.mock.enums.PaymentStatus;
import com.example.mock.enums.ReservationStatus;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ReservationDTO {

    private int reservationId;
    private ReservationStatus reservationStatus;
    private int totalAmount;
    private PaymentStatus paymentStatus;
    private Integer userId;
    private Integer showTimeId;
    private PaymentDTO paymentDTO;
}
