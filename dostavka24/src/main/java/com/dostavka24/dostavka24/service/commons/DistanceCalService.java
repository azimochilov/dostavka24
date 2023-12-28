package com.dostavka24.dostavka24.service.commons;

import org.springframework.stereotype.Service;

@Service
public class DistanceCalService {
    private static final double EARTH_RADIUS = 6371;

    public double calculateDistance(double userLat, double userLng, double restaurantLat, double restaurantLng) {
        double dLat = Math.toRadians(restaurantLat - userLat);
        double dLng = Math.toRadians(restaurantLng - userLng);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(restaurantLat)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }
}
