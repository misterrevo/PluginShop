package com.revo.PluginShop.domain.port;

public interface PaymentServicePort {

    void processPayment(String id, String operation_number, String operation_type, String operation_status, String operation_amount, String operation_currency, String description, String email);
}
