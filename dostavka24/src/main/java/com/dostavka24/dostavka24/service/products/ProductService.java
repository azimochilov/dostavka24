package com.dostavka24.dostavka24.service.products;


import com.dostavka24.dostavka24.domain.dtos.products.ProductCreationDto;
import com.dostavka24.dostavka24.domain.dtos.products.ProductUpdateDto;
import com.dostavka24.dostavka24.domain.entities.restaurants.Product;
import com.dostavka24.dostavka24.domain.enums.ProductStatus;
import com.dostavka24.dostavka24.exception.NotFoundException;
import com.dostavka24.dostavka24.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public Product create(ProductCreationDto productDto) {

        Product existingProduct = new Product();

            existingProduct.setName(productDto.getName());
            existingProduct.setPrice(productDto.getPrice());
            existingProduct.setProductStatus(productDto.getProductStatus());
            existingProduct.setCreateAt(Instant.now());

        return productRepository.save(existingProduct);
    }


    public void delete(Long id) {

        Product productForDeletion = productRepository.getById(id);

        productRepository.delete(productForDeletion);
    }

    public Product update(Long id, ProductUpdateDto updtProduct) {

        Product existingProduct = productRepository.getById(id);

        if (existingProduct == null) {
            throw new NotFoundException("Product not found");
        }

        existingProduct.setName(updtProduct.getName());
        existingProduct.setPrice(updtProduct.getPrice());
        existingProduct.setProductStatus(updtProduct.getProductStatus());

        return productRepository.save(existingProduct);
    }

    public List<Product> getAll() {

        return productRepository.findAll();
    }

    public List<Product> getAllByStatus(ProductStatus status){

        return productRepository.getAllByProductStatus(status);
    }

    public Product getById(Long id) {

        Product existingProduct = productRepository.getById(id);
        if (existingProduct == null) {
            throw new NotFoundException("Product not found with this id");
        }
        return existingProduct;
    }

}
