package com.example.TesteApi.Controllers;

import com.example.TesteApi.DTO.ProductRecordDto;
import com.example.TesteApi.Models.ProductModel;
import com.example.TesteApi.Repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @GetMapping("/Products")
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.findAll());
    }

    @GetMapping("/Products/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id")UUID id){
        Optional<ProductModel> productModelO = productRepository.findById(id);
        if (productModelO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(productModelO.get());
    }

    @PutMapping("/Products/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id,
                                                @RequestBody @Valid ProductRecordDto productRecordDto) {
        Optional<ProductModel> productModelO = productRepository.findById(id);
        if(productModelO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found!");
        }

        //Não podemos começão um modelo do zero, pois o intuito é atualizar algo que já existe
        var productModel = productModelO.get();

        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
    }

    @DeleteMapping("/Products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable(value = "id") UUID id) {
        Optional<ProductModel> productmodelO = productRepository.findById(id);
        if(productmodelO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found!");
        }
        productRepository.delete(productmodelO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully!");
    }

}
