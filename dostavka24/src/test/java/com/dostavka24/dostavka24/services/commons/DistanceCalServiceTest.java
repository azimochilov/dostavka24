package com.dostavka24.dostavka24.services.commons;

import com.dostavka24.dostavka24.service.commons.DistanceCalService;
import org.junit.jupiter.api.Test;

import static java.lang.Math.abs;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DistanceCalServiceTest {

    private final DistanceCalService distanceCalService = new DistanceCalService();

    @Test
    public void calculateDistance_WithKnownCoordinates_ShouldReturnAccurateDistance() {

        double nycLat = 40.7128;
        double nycLng = -74.0060;
        double laLat = 34.0522;
        double laLng = -118.2437;

        double expectedDistance = 3940;

        double calculatedDistance = distanceCalService.calculateDistance(nycLat, nycLng, laLat, laLng);

        double marginOfError = 50;

        assertTrue(abs(calculatedDistance - expectedDistance) <= marginOfError,
                "Calculated distance should be within " + marginOfError + " km of the expected distance");
    }
}
