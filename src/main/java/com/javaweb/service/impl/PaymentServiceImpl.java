package com.javaweb.service.impl;

import com.javaweb.model.PaymentDTO;
import com.javaweb.repository.entity.PaymentEntity;
import com.javaweb.repository.impl.PaymentRepositoryImpl;
import com.javaweb.service.PaymentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepositoryImpl paymentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<PaymentDTO> getTransactionHistory(Integer userId) {
        List<PaymentEntity> payments = paymentRepo.findByUserId(userId);
        return payments.stream()
                .map(payment -> modelMapper.map(payment, PaymentDTO.class))
                .collect(Collectors.toList());
    }
}