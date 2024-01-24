package com.dostavka24.dostavka24.service.commons;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class DeliveryCalService {
    public static String calculateEstimatedDeliveryTime(int numberOfItems, int distanceInKilometers) {
        // Preparation time: 5 minutes for every 4 items (or part thereof)
        int totalPreparationTime = (numberOfItems + 3) / 4 * 5;

        // Delivery time: 3 minutes per kilometer
        int deliveryTimeForDistance = distanceInKilometers * 3;

        // Total estimated time is the sum of preparation time and delivery time
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime estimatedDeliveryTime = now.plusMinutes(totalPreparationTime + deliveryTimeForDistance);

        // Format the estimated delivery time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm'minutes'");
        return formatter.format(estimatedDeliveryTime);
    }

    public  Double calculationOfTotalPriceOfProduct(Integer getPriceOfProduct,Integer getCountOfProduct ){
        Double total = (double) (getPriceOfProduct * getCountOfProduct);
        return total;
    }
}
