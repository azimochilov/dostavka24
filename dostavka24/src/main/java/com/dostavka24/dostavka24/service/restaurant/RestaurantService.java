package com.dostavka24.dostavka24.service.restaurant;

import com.dostavka24.dostavka24.domain.dtos.products.ProductCreationDto;
import com.dostavka24.dostavka24.domain.dtos.products.ProductUpdateDto;
import com.dostavka24.dostavka24.domain.dtos.restaurants.RestaurantCreationDto;
import com.dostavka24.dostavka24.domain.dtos.restaurants.RestaurantUpdateDto;
import com.dostavka24.dostavka24.domain.entities.restaurants.Product;
import com.dostavka24.dostavka24.domain.entities.restaurants.Restaurant;
import com.dostavka24.dostavka24.domain.enums.ProductStatus;
import com.dostavka24.dostavka24.exception.NotFoundException;
import com.dostavka24.dostavka24.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant create(RestaurantCreationDto restaurantCreationDto) {

        Restaurant exsistsRestaurant = restaurantRepository.findByName(restaurantCreationDto.getName());
        if (exsistsRestaurant != null) {
            throw new NotFoundException("Restaurant already exsists! ");
        }

        exsistsRestaurant.setAddress(restaurantCreationDto.getAddress());
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
        existingRestaurant.setAddress(updtRestaurant.getAddress());

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

}
