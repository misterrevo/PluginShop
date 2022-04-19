package com.revo.PluginShop.domain;

import com.revo.PluginShop.domain.dto.PluginDto;
import com.revo.PluginShop.domain.exception.PaymentException;
import com.revo.PluginShop.domain.port.PaymentServicePort;
import com.revo.PluginShop.domain.port.PluginServicePort;

import java.util.Objects;

public class PaymentService implements PaymentServicePort {

    private static final String CURRENCY = "PLN";
    private static final String TYPE = "payment";
    private static final String STATUS = "completed";
    private static final String ID = "775355";

    private final PluginServicePort pluginServicePort;

    public PaymentService(PluginServicePort pluginServicePort) {
        this.pluginServicePort = pluginServicePort;
    }

    @Override
    public void processPayment(String id, String operation_number, String operation_type, String operation_status, String operation_amount, String operation_currency, String description, String email) {
        var plugin = pluginServicePort.getPluginById(Long.valueOf(description));
        checkPayment(plugin, id, operation_amount, operation_currency, operation_status, operation_type);
        pluginServicePort.buyPlugin(Long.valueOf(description), email);
    }

    private void checkPayment(PluginDto plugin, String id, String operation_amount, String operation_currency, String operation_status, String operation_type) {
        if(!Objects.equals(id, ID) || Double.valueOf(operation_amount) != plugin.getPrice() || !Objects.equals(operation_currency, CURRENCY) || !Objects.equals(operation_status, STATUS) || !Objects.equals(operation_type, TYPE)){
            throw new PaymentException(id);
        }
    }
}
