package com.domain.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.models.entity.Product;
import com.domain.models.entity.Supplier;
import com.domain.models.repos.ProductRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private SupplierService supplierService;

    public Product save(Product product) {
        return productRepo.save(product);
    }

    public Product findOne(long id) {
        Optional<Product> product = productRepo.findById(id);
        if (!product.isPresent()) {
            return null;
        }
        return productRepo.findById(id).get();
    }

    public Iterable<Product> findAll() {
        return productRepo.findAll();
    }

    public void removeOne(long id) {
        productRepo.deleteById(id);
    }

    public List<Product> findByName(String name) {
        return productRepo.findByNameContains(name);
    }

    public void addSupplier(Supplier supplier, long productId){
        Product product = findOne(productId);
        if(product == null){
            throw new RuntimeException("Product with id " + productId + " does not exist");
        }
        product.getSuppliers().add(supplier);
        save(product);
    }

    public Product findByProductName(String name) {
        return productRepo.findByProductName(name);
    }

    public List<Product> findByProductNameLike(String name) {
        return productRepo.findByProductNameLike("%" + name + "%");
    }

    public List<Product> findByProductCategory(long categoryId) {
        return productRepo.findByProductCategory(categoryId);
    }

    public List<Product> findByProductSupplier(long supplierId) {
        Supplier supplier = supplierService.findOne(supplierId);
        if(supplier == null){
            return new ArrayList<Product>();
        }
        return productRepo.findByProductSupplier(supplier);
    }
}
