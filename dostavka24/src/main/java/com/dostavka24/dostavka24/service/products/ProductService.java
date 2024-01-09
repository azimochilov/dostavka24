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


    public Product create(ProductCreationDto product) {

        Product exsistsProduct = productRepository.findByName(product.getName());
        if (exsistsProduct != null) {
            exsistsProduct.setProductStatus(product.getProductStatus());
            exsistsProduct.setPrice(product.getPrice());
        }

        exsistsProduct = new Product();
        exsistsProduct.setCreateAt(Instant.now());
        exsistsProduct.setProductStatus(product.getProductStatus());
        exsistsProduct.setName(product.getName());
        exsistsProduct.setPrice(product.getPrice());

        return productRepository.save(exsistsProduct);
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
