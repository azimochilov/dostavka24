package com.dostavka24.dostavka24.services.commons;

import static org.junit.jupiter.api.Assertions.*;

import com.dostavka24.dostavka24.service.commons.DeliveryCalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DeliveryCalServiceTest {
    private DeliveryCalService deliveryCalService;
    @BeforeEach
    public void setUp() {
        deliveryCalService = new DeliveryCalService();
    }

    @Test
    public void calculateEstimatedDeliveryTime_WithVariousInputs_ShouldCalculateCorrectly() {
        int numberOfItems = 8;
        int distanceInKilometers = 10;

        LocalDateTime now = LocalDateTime.now();
        String calculatedTime = DeliveryCalService.calculateEstimatedDeliveryTime(numberOfItems, distanceInKilometers);

        LocalDateTime expectedDeliveryTime = now.plusMinutes(40);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm'minutes'");
        String expectedTimeString = formatter.format(expectedDeliveryTime);

        assertEquals(expectedTimeString, calculatedTime);
    }
    @Test
    public void calculationOfTotalPriceOfProduct_WithVariousInputs_ShouldCalculateCorrectly() {

        double totalPrice = deliveryCalService.calculationOfTotalPriceOfProduct(50, 2);

        assertEquals(100.0, totalPrice);
    }

}
