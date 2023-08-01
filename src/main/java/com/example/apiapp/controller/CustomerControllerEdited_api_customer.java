package com.example.apiapp.controller;

import com.example.apiapp.entity.Customer;
import com.example.apiapp.payload.ApiResponse;
import com.example.apiapp.payload.CustomerDto;
import com.example.apiapp.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
//@RequestMapping("/api/customers")
public class CustomerControllerEdited_api_customer {
    @Autowired
    CustomerService customerService;

    @PostMapping
    public HttpEntity<ApiResponse> addCustomer(@Valid @RequestBody CustomerDto customerDto){
        ApiResponse apiResponse = customerService.addCustomer(customerDto);
        if (apiResponse.isSuccess()){
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }// @Valid annotatsiyasi CustomerDtoning constrainlarining xossalarini tekshiradi.

    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers(){
        return  ResponseEntity.ok(customerService.getCustomers());
    }

    @GetMapping("{id}")
    public Customer getCustomerById(@PathVariable Integer id){
        return customerService.getCustomerById(id);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteCustomerById(@PathVariable Integer id){
        ApiResponse apiResponse = customerService.deleteCustomerById(id);
        if (apiResponse.isSuccess()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
        }return ResponseEntity.status(HttpStatus.GONE).body(apiResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> editCustomerById(@PathVariable Integer id, @Valid @RequestBody CustomerDto customerDto){
        ApiResponse apiResponse = customerService.editCustomerById(id, customerDto);
        if (apiResponse.isSuccess()){
            return ResponseEntity.status(HttpStatus.GONE).body(apiResponse);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }

    // https://www.baeldung.com/spring-boot-bean-validation shu sytdan olingan
    // CustomerDto dagi xatolik message larni ko`rsatish uchun

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }


}
