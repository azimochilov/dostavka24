package com.dostavka24.dostavka24.rest.restaurants;


import com.dostavka24.dostavka24.domain.dtos.products.ProductCreationDto;
import com.dostavka24.dostavka24.domain.dtos.products.ProductUpdateDto;
import com.dostavka24.dostavka24.domain.entities.restaurants.Product;
import com.dostavka24.dostavka24.service.products.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping("/product")
    public ResponseEntity create(@RequestBody ProductCreationDto product){
        productService.create(product);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity delete(@RequestBody Long id){
        productService.delete(id);
        return ResponseEntity.ok("deleted");
    }

    @GetMapping("/products")
    public ResponseEntity getAll(){
        List<Product> products = productService.getAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity getById(@RequestBody Long id){
        return ResponseEntity.ok(productService.getById(id));
    }

    @PutMapping("/product/{id}")
    public ResponseEntity update(@PathVariable Long id,@RequestBody ProductUpdateDto product){
        Product updatedProduct = productService.update(id,product);
        return ResponseEntity.ok(updatedProduct);
    }
}

