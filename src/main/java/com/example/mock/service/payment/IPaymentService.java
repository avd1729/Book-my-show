package com.example.mock.service.payment;

import com.example.mock.dto.PaymentDTO;
import com.example.mock.entity.Payment;

public interface IPaymentService {
    Payment addPayment(PaymentDTO paymentDTO);
}
