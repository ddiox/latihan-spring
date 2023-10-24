package com.domain.models.repos;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.domain.models.entity.Product;
import com.domain.models.entity.Supplier;

import jakarta.websocket.server.PathParam;

public interface ProductRepo extends CrudRepository<Product, Long> {
    List<Product> findByNameContains(String name);

    @Query("SELECT p FROM Product p WHERE p.name = :name")
    public Product findByProductName(@PathParam("name") String name);

    @Query("SELECT p FROM Product p WHERE p.name LIKE :name")
    public List<Product> findByProductNameLike(@PathParam("name") String name);

    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
    public List<Product> findByProductCategory(@PathParam("categoryId") long categoryId);  

    @Query("SELECT p FROM Product p WHERE :supplier MEMBER OF p.suppliers")
    public List<Product> findByProductSupplier(@PathParam("supplier") Supplier supplier);
}
