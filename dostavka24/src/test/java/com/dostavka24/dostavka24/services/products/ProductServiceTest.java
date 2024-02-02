package com.dostavka24.dostavka24.services.products;


import com.dostavka24.dostavka24.domain.dtos.products.ProductCreationDto;
import com.dostavka24.dostavka24.domain.dtos.products.ProductUpdateDto;
import com.dostavka24.dostavka24.domain.entities.restaurants.Product;
import com.dostavka24.dostavka24.domain.enums.ProductStatus;
import com.dostavka24.dostavka24.repository.ProductRepository;
import com.dostavka24.dostavka24.service.products.ProductService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;

    private Product product = new Product();
    private ProductCreationDto productCreationDto = new ProductCreationDto();;
    private ProductUpdateDto productUpdateDto = new ProductUpdateDto();
    private List<Product> products = new ArrayList<>();

    @BeforeEach
    public void setUp(){

        productCreationDto.setName("Test");
        productCreationDto.setProductStatus(ProductStatus.BURGER);
        productCreationDto.setPrice(654);

        productUpdateDto.setName("TestUpdt");
        productUpdateDto.setProductStatus(ProductStatus.APPETIZER);
        productUpdateDto.setPrice(777);

        product.setId(1L);
        product.setName("TestTest");
        product.setProductStatus(ProductStatus.CHICKEN);
        product.setPrice(2602);
        product.setCreateAt(Instant.now());

        products.add(product);

    }

    @Test
    public void ProductService_Create(){
        when(productRepository.save(any())).thenAnswer(invocation -> {
            Product savedProduct = invocation.getArgument(0);
            savedProduct.setId(1L);
            return savedProduct;
        });

        Product productT = productService.create(productCreationDto);

        assertAll(
                () ->  Assertions.assertThat(productT).isNotNull(),
                () -> Assertions.assertThat(productT.getName()).isEqualTo(productCreationDto.getName()),
                () -> Assertions.assertThat(productT.getProductStatus()).isEqualTo(productCreationDto.getProductStatus()),
                () -> Assertions.assertThat(productT.getPrice()).isEqualTo(productCreationDto.getPrice()),
                () -> Assertions.assertThat(productT.getCreateAt()).isNotNull()
        );
    }

    @Test
    public void ProductService_delete(){

        when(productRepository.getById(1L)).thenReturn(product);

        assertAll(
                () -> productService.delete(1L)
        );
    }

    @Test
    public void ProductService_update(){
        when(productRepository.getById(any())).thenReturn(product);
        when(productRepository.save(any())).thenReturn(product);

        Product productT = productService.update(1L, productUpdateDto);

        assertAll(
                () -> Assertions.assertThat(productT).isNotNull(),
                () -> Assertions.assertThat(productT.getName()).isEqualTo(productUpdateDto.getName()),
                () -> Assertions.assertThat(productT.getProductStatus()).isEqualTo(productUpdateDto.getProductStatus()),
                () -> Assertions.assertThat(productT.getPrice()).isEqualTo(productUpdateDto.getPrice()),
                () -> Assertions.assertThat(productT.getCreateAt()).isNotNull()
        );
    }

    @Test
    public void ProductService_getById(){
        when(productRepository.getById(1L)).thenReturn(product);
        Product productT = productService.getById(1L);

        assertAll(
                () -> Assertions.assertThat(productT).isNotNull(),
                () -> Assertions.assertThat(productT.getName()).isEqualTo(product.getName()),
                () -> Assertions.assertThat(productT.getProductStatus()).isEqualTo(product.getProductStatus()),
                () -> Assertions.assertThat(productT.getPrice()).isEqualTo(product.getPrice()),
                () -> Assertions.assertThat(productT.getCreateAt()).isEqualTo(product.getCreateAt())
        );
    }

    @Test
    public void ProductService_getAll(){
        when(productRepository.findAll()).thenReturn(products);
        List<Product> productList = productRepository.findAll();

        assertAll(
                () -> Assertions.assertThat(productList).isNotNull()
        );
    }

}
