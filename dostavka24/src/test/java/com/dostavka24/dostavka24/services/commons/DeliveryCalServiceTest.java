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
        // Optionally set up a fixed clock for the calculateEstimatedDeliveryTime method
        // Clock fixedClock = Clock.fixed(LocalDateTime.of(2023, 1, 1, 12, 0).toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
        // DeliveryCalService.setClock(fixedClock); // If you have such a mechanism in your method
    }

    @Test
    public void calculateEstimatedDeliveryTime_WithVariousInputs_ShouldCalculateCorrectly() {
        int numberOfItems = 8; // 2 sets of 4, so 10 minutes
        int distanceInKilometers = 10; // 10 km * 3 min/km = 30 minutes

        LocalDateTime now = LocalDateTime.now();
        String calculatedTime = DeliveryCalService.calculateEstimatedDeliveryTime(numberOfItems, distanceInKilometers);

        LocalDateTime expectedDeliveryTime = now.plusMinutes(40); // 10 minutes prep + 30 minutes delivery

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm'minutes'");
        String expectedTimeString = formatter.format(expectedDeliveryTime);

        assertEquals(expectedTimeString, calculatedTime);
    }
    @Test
    public void calculationOfTotalPriceOfProduct_WithVariousInputs_ShouldCalculateCorrectly() {
        // Example test case
        // Price of product = 50, count of product = 2
        // Total = 50 * 2 = 100

        double totalPrice = deliveryCalService.calculationOfTotalPriceOfProduct(50, 2);

        assertEquals(100.0, totalPrice);
    }

    // Additional test cases can be added for different numbers of items, distances, and product prices/count
}
