package com.pcoundia.products.infrastructure.repository;

import com.pcoundia.products.infrastructure.entity.Product;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository extends R2dbcRepository<Product, String> {
    Flux<Product> findAllByNameContainingIgnoreCase(String name);

    @Query("INSERT INTO products (id, name, price) VALUES (:id, :name, :price) RETURNING *")
    Mono<Product> insert(String id, String name, double price);

    @Query("SELECT * FROM products ORDER BY name LIMIT :limit OFFSET :offset")
    Flux<Product> findAllByPage(int limit, int offset);

    @Query("UPDATE products SET name = :name WHERE id = :id")
    Mono<Integer> updateNameById(String id, String name);


}
