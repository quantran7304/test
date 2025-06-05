package com.javaweb.service;

import com.javaweb.model.PaymentDTO;
import java.util.List;

public interface PaymentService {
    List<PaymentDTO> getTransactionHistory(Integer userId);
}