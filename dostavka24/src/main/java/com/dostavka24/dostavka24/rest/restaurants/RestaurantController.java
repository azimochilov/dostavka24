package com.dostavka24.dostavka24.rest.restaurants;

import com.dostavka24.dostavka24.domain.dtos.products.ProductCreationDto;
import com.dostavka24.dostavka24.domain.dtos.products.ProductUpdateDto;
import com.dostavka24.dostavka24.domain.dtos.restaurants.RestaurantCreationDto;
import com.dostavka24.dostavka24.domain.dtos.restaurants.RestaurantUpdateDto;
import com.dostavka24.dostavka24.domain.entities.restaurants.Product;
import com.dostavka24.dostavka24.domain.entities.restaurants.Restaurant;
import com.dostavka24.dostavka24.service.restaurant.RestaurantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestaurantController {
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/restaurant")
    public ResponseEntity create(@RequestBody RestaurantCreationDto restaurantCreationDto){
        restaurantService.create(restaurantCreationDto);
        return ResponseEntity.ok(restaurantCreationDto);
    }

    @DeleteMapping("/restaurant/{id}")
    public ResponseEntity delete(@RequestBody Long id){
        restaurantService.delete(id);
        return ResponseEntity.ok("deleted");
    }

    @GetMapping("/restaurants")
    public ResponseEntity getAll(){
        List<Restaurant> restaurants = restaurantService.getAll();
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/restaurants/{id}")
    public ResponseEntity getById(@RequestBody Long id){
        return ResponseEntity.ok(restaurantService.getById(id));
    }

    @PutMapping("/restaurants/{id}")
    public ResponseEntity update(@PathVariable Long id,@RequestBody RestaurantUpdateDto restaurantUpdateDto){
        Restaurant updatedRestaurant = restaurantService.update(id,restaurantUpdateDto);
        return ResponseEntity.ok(updatedRestaurant);
    }
}
