package com.dostavka24.dostavka24.rest.restaurants;

import com.dostavka24.dostavka24.domain.dtos.addresses.AddressCreationDto;
import com.dostavka24.dostavka24.domain.dtos.products.ProductCreationDto;
import com.dostavka24.dostavka24.domain.dtos.products.ProductUpdateDto;
import com.dostavka24.dostavka24.domain.dtos.restaurants.RestaurantCreationDto;
import com.dostavka24.dostavka24.domain.dtos.restaurants.RestaurantUpdateDto;
import com.dostavka24.dostavka24.domain.entities.addresses.Address;
import com.dostavka24.dostavka24.domain.entities.restaurants.Product;
import com.dostavka24.dostavka24.domain.entities.restaurants.Restaurant;
import com.dostavka24.dostavka24.service.restaurant.RestaurantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody RestaurantCreationDto restaurantCreationDto){
        restaurantService.create(restaurantCreationDto);
        return ResponseEntity.ok(restaurantCreationDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        restaurantService.delete(id);
        return ResponseEntity.ok("deleted");
    }

    @GetMapping
    public ResponseEntity getAll(){
        List<Restaurant> restaurants = restaurantService.getAll();
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id){

        Restaurant restaurant = restaurantService.getById(id);
        RestaurantCreationDto resRes = new RestaurantCreationDto();

        AddressCreationDto address = new AddressCreationDto();
        address.setLongitude(restaurant.getAddress().getLongitude());
        address.setLatitude(restaurant.getAddress().getLatitude());
        address.setStreet(restaurant.getAddress().getStreet());
        address.setCity(restaurant.getAddress().getCity());

        resRes.setName(restaurant.getName());
        resRes.setAddress(address);

        return ResponseEntity.ok(resRes);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id,@RequestBody RestaurantUpdateDto restaurantUpdateDto){
        Restaurant updatedRestaurant = restaurantService.update(id,restaurantUpdateDto);
        return ResponseEntity.ok(updatedRestaurant);
    }
}
