package com.dostavka24.dostavka24.service.restaurant;

import com.dostavka24.dostavka24.domain.dtos.restaurants.RestaurantCreationDto;
import com.dostavka24.dostavka24.domain.dtos.restaurants.RestaurantUpdateDto;
import com.dostavka24.dostavka24.domain.entities.addresses.Address;
import com.dostavka24.dostavka24.domain.entities.orders.Order;
import com.dostavka24.dostavka24.domain.entities.restaurants.Restaurant;
import com.dostavka24.dostavka24.exception.NotFoundException;
import com.dostavka24.dostavka24.repository.OrderRepository;
import com.dostavka24.dostavka24.repository.RestaurantRepository;
import com.dostavka24.dostavka24.service.addresses.AddressService;
import com.dostavka24.dostavka24.service.commons.DistanceCalService;
import com.dostavka24.dostavka24.service.users.UserService;
import com.dostavka24.dostavka24.service.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final DistanceCalService distanceCalService;
    private final AddressService addressService;
    private final OrderRepository orderRepository;
    public RestaurantService(RestaurantRepository restaurantRepository,
                             DistanceCalService distanceCalService,
                             AddressService addressService,
                             OrderRepository orderRepository
    ) {
        this.restaurantRepository = restaurantRepository;
        this.distanceCalService = distanceCalService;
        this.addressService = addressService;
        this.orderRepository = orderRepository;
    }

    public Restaurant create(RestaurantCreationDto restaurantCreationDto) {

        Restaurant exsistsRestaurant = restaurantRepository.findByName(restaurantCreationDto.getName());
        if (exsistsRestaurant != null) {
            throw new NotFoundException("Restaurant already exsists! ");
        }

        Address address = addressService.create(restaurantCreationDto.getAddress());

        exsistsRestaurant = new Restaurant();
        exsistsRestaurant.setName(restaurantCreationDto.getName());
        exsistsRestaurant.setAddress(address);
        exsistsRestaurant.setName(exsistsRestaurant.getName());

        return restaurantRepository.save(exsistsRestaurant);
    }

    public void delete(Long id) {
        Restaurant restaurantForDeletion = restaurantRepository.findById(id).get();
        restaurantRepository.delete(restaurantForDeletion);
    }

    public Restaurant update(Long id, RestaurantUpdateDto updtRestaurant) {
        Restaurant existingRestaurant = restaurantRepository.findById(id).get();

        if (existingRestaurant == null) {
            throw new NotFoundException("Restaurant not found");
        }

        existingRestaurant.setName(updtRestaurant.getName());
        existingRestaurant.setAddress(addressService.update(id,updtRestaurant.getAddress()));

        return restaurantRepository.save(existingRestaurant);
    }

    public List<Restaurant> getAll() {

        return restaurantRepository.findAll();
    }

    public Restaurant getById(Long id) {
        Restaurant existingRestaurant = restaurantRepository.findById(id).get();
        if (existingRestaurant == null) {
            throw new NotFoundException("Restaurant not found with this id");
        }
        return existingRestaurant;
    }

    public Restaurant findNearestRestaurant() {

        Long currentUserId = SecurityUtils.getCurrentUserId();
        Order order = orderRepository.getOrderByUserId(currentUserId);

        double userLat = order.getAddress().getLatitude();
        double userLng = order.getAddress().getLongitude();

        Restaurant nearestRestaurant = null;
        double minDistance = Double.MAX_VALUE;

        List<Restaurant> restaurants = restaurantRepository.findAll();
        for (Restaurant restaurant : restaurants) {

            double distance = distanceCalService.calculateDistance(userLat,
                    userLng,
                    restaurant.getAddress().getLatitude(),
                    restaurant.getAddress().getLongitude());

            if (distance < minDistance) {
                minDistance = distance;
                nearestRestaurant = restaurant;
            }
        }

        nearestRestaurant.getOrders().add(order);

        restaurantRepository.save(nearestRestaurant);

        return nearestRestaurant;
    }
}
