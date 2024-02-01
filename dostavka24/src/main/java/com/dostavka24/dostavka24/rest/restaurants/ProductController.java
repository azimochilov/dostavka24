package com.dostavka24.dostavka24.rest.restaurants;


import com.dostavka24.dostavka24.domain.dtos.products.ProductCreationDto;
import com.dostavka24.dostavka24.domain.dtos.products.ProductUpdateDto;
import com.dostavka24.dostavka24.domain.entities.addresses.Address;
import com.dostavka24.dostavka24.domain.entities.restaurants.Product;
import com.dostavka24.dostavka24.service.products.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PreAuthorize("hasAuthority('CREATE')")
    @PostMapping
    public ResponseEntity create(@RequestBody ProductCreationDto product){
        productService.create(product);
        return ResponseEntity.ok(product);
    }

    @PreAuthorize("hasAuthority('DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        productService.delete(id);
        return ResponseEntity.ok("deleted");
    }

    @GetMapping
    public ResponseEntity getAll(){
        List<Product> products = productService.getAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id){
        Product product = productService.getById(id);

        ProductCreationDto productCreationDto = new ProductCreationDto();
        productCreationDto.setName(product.getName());
        productCreationDto.setPrice(product.getPrice());
        productCreationDto.setProductStatus(product.getProductStatus());

        return ResponseEntity.ok(productCreationDto);
    }
    @PreAuthorize("hasAuthority('UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id,@RequestBody ProductUpdateDto product){
        Product updatedProduct = productService.update(id,product);
        return ResponseEntity.ok(product);
    }
}

