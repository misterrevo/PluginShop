package com.revo.PluginShop.infrastructure.application.rest;

import com.revo.PluginShop.domain.port.PaymentServicePort;
import com.revo.PluginShop.domain.port.PluginServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("payments")
@RequiredArgsConstructor
class PaymentController {

    private final PaymentServicePort paymentServicePort;

    @PostMapping()
    void processPayment(
            @RequestParam String id,
            @RequestParam String operation_number,
            @RequestParam String operation_type,
            @RequestParam String operation_status,
            @RequestParam String operation_amount,
            @RequestParam String operation_currency,
            @RequestParam String description,
            @RequestParam String email
            ) {
        paymentServicePort.processPayment(id, operation_number, operation_type, operation_status, operation_amount, operation_currency, description, email);
    }
}
