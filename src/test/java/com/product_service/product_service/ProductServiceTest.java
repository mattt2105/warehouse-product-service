package com.product_service.product_service;

import com.product_service.product_service.model.Product;
import com.product_service.product_service.repository.ProductRepository;
import com.product_service.product_service.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;

@SpringBootTest
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addProduct_Success(){
//        Product product = createProduct();
//        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
//        Product savedProduct = productService.addProduct(product);
//        assertProduct(savedProduct);

        Product product = createProduct();
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

        ResponseEntity<Product> responseEntity = Mockito.mock(ResponseEntity.class);
        Mockito.when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(), eq(Product.class)))
                .thenReturn(responseEntity);

        Product savedProduct = productService.addProduct(product);

        assertProduct(savedProduct);
        Mockito.verify(restTemplate, Mockito.times(1))
                .exchange(anyString(), eq(HttpMethod.POST), any(), eq(Product.class));

    }

    @Test
    public void updateProduct_Success(){
//        Product product = createProduct();
//        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
//        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
//        Product updatedProduct = productService.updateQuantityProduct(1L, 5);
//        Assertions.assertNotNull(updatedProduct);
//        Assertions.assertEquals(15, updatedProduct.getQuantity());

        Product product = createProduct();
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

        ResponseEntity<Product> responseEntity = Mockito.mock(ResponseEntity.class);
        Mockito.when(restTemplate.exchange(anyString(), eq(HttpMethod.PUT), any(), eq(Product.class), eq(1L), eq(5)))
                .thenReturn(responseEntity);

        Product updatedProduct = productService.updateQuantityProduct(1L, 5);

        Assertions.assertNotNull(updatedProduct);
        Assertions.assertEquals(15, updatedProduct.getQuantity());
        Mockito.verify(restTemplate, Mockito.times(1))
                .exchange(anyString(), eq(HttpMethod.PUT), any(), eq(Product.class), eq(1L), eq(5));
    }

    private Product createProduct(){
        Product product = new Product();
        product.setName("testName");
        product.setPrice(new BigDecimal(70000));
        product.setQuantity(10);
        return product;
    }

    private void assertProduct(Product product){
        Assertions.assertNotNull(product);
        Assertions.assertEquals("testName", product.getName());
    }
}
