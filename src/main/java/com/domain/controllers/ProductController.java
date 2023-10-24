package com.domain.controllers;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.domain.models.entity.Product;
import com.domain.models.entity.Supplier;
import com.domain.services.ProductService;

import dto.ResponseData;
import dto.SearchData;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ResponseData<Product>> create(@Valid @RequestBody Product product, Errors errors) {
        
        ResponseData<Product> responseData = new ResponseData<>();
        
        if(errors.hasErrors()) {
            for(ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.setStatus(true);
        responseData.setPayload(productService.save(product));
        return ResponseEntity.ok(responseData);
    }

    @GetMapping
    public Iterable<Product> findAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Product findOne(@PathVariable("id") long id) {
        return productService.findOne(id);
    }

    @PutMapping
    public ResponseEntity<ResponseData<Product>> update(@Valid @RequestBody Product product, Errors errors) {
        ResponseData<Product> responseData = new ResponseData<>();
        
        if(errors.hasErrors()) {
            for(ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.setStatus(true);
        responseData.setPayload(productService.save(product));
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("/{id}")
    public void removeOne(@PathVariable("id") long id) {
        productService.removeOne(id);
    }

    @PostMapping("/{id}")
    public void addSupplier(@RequestBody Supplier supplier, @PathVariable("id") long productId) {
        productService.addSupplier(supplier, productId);
    }

    @PostMapping("/search/name")
    public Product getProductByName(@RequestBody SearchData searchData){
        return productService.findByProductName(searchData.getSearchKey());
    }

    @PostMapping("/search/namelike")
    public List<Product> getProductByNameLike(@RequestBody SearchData searchData){
        return productService.findByProductNameLike(searchData.getSearchKey());
    }

    @GetMapping("/search/category/{categoryId}")
    public List<Product> getProductByCategory(@PathVariable("categoryId") long categoryId){
        return productService.findByProductCategory(categoryId);
    }

    @GetMapping("/search/supplier/{supplierId}")
    public List<Product> getProductBySupplier(@PathVariable("supplierId") long supplierId){
        return productService.findByProductSupplier(supplierId);
    }
}
