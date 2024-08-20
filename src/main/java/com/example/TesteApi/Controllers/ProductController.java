package com.example.TesteApi.Controllers;

import com.example.TesteApi.DTO.ProductRecordDto;
import com.example.TesteApi.Models.ProductModel;
import com.example.TesteApi.Repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @PostMapping("/Products")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
        var productModal = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModal);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModal));
    }
}
