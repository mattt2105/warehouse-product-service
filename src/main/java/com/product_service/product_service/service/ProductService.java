package com.product_service.product_service.service;

import com.product_service.product_service.model.Product;
import com.product_service.product_service.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private RestTemplate restTemplate;


    @Value("${inventory.rest.path}")
    private String inventory_url;

    public Product addProduct(Product product) {
        validateProduct(product);
        addToInventory(product);
        return productRepository.save(product);
    }

    public Product updateQuantityProduct(Long id, int quantity) {
        Product product = getProductById(id);
        product.setQuantity(product.getQuantity() + quantity);
        updateQtyInventory(id,quantity);
        return productRepository.save(product);

    }

    private void validateProduct(Product product) {
        if (product.getName() == null || product.getName().isEmpty()) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (product.getPrice() == null ) {
            throw new IllegalArgumentException("Price is required");
        }
        if (product.getQuantity() == null) {
            throw new IllegalArgumentException("Quantity is required");
        }
    }

    private Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
    }

    private void addToInventory(Product product){
        String url = inventory_url+"/api/inventorys/create";
        ResponseEntity<Product> reponse = restTemplate.exchange(
                url,
                HttpMethod.POST,
                null,
                Product.class);
    }

    private void updateQtyInventory(Long id, int quantity){
        String url = inventory_url+"/update/"+id;
        ResponseEntity<Product> reponse = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                null,
                Product.class,
                id,quantity);
    }
}
