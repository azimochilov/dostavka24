package com.dostavka24.dostavka24.service.commons;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DeliveryCalService {
    public static String calculateEstimatedDeliveryTime(int numberOfItems, int distanceInKilometers) {
        int totalPreparationTime = (numberOfItems / 4) * 5 + (numberOfItems % 4 != 0 ? 5 : 0);

        int deliveryTimeForDistance = distanceInKilometers * 3;

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime estimatedDeliveryTime = now.plusMinutes(totalPreparationTime + deliveryTimeForDistance);
        String devTime = String.valueOf(estimatedDeliveryTime.getHour()+ estimatedDeliveryTime.getMinute()+"minutes");
        return devTime;
    }

    public  Double calculationOfTotalPriceOfProduct(Integer getPriceOfProduct,Integer getCountOfProduct ){
        Double total = (double) (getPriceOfProduct * getCountOfProduct);
        return total;
    }
}
