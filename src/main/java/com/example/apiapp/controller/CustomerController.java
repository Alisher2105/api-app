package com.example.apiapp.controller;

import com.example.apiapp.entity.Customer;
import com.example.apiapp.payload.ApiResponse;
import com.example.apiapp.payload.CustomerDto;
import com.example.apiapp.service.CustomerService;
import jakarta.validation.Valid;
import org.hibernate.dialect.unique.CreateTableUniqueDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customer")
//@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping
    public ApiResponse addCustomer(@Valid @RequestBody CustomerDto customerDto){
        return customerService.addCustomer(customerDto);
    }// @Valid annotatsiyasi CustomerDtoning constrainlarining xossalarini tekshiradi.

    @GetMapping
    public List<Customer> getCustomers(){
        return customerService.getCustomers();
    }

    @GetMapping("{id}")
    public Customer getCustomerById(@PathVariable Integer id){
        return customerService.getCustomerById(id);
    }

    @DeleteMapping("{id}")
    public ApiResponse deleteCustomerById(@PathVariable Integer id){
        return customerService.deleteCustomerById(id);
    }

    @PutMapping("{id}")
    public ApiResponse editCustomerById(@PathVariable Integer id, @Valid @RequestBody CustomerDto customerDto){
        return customerService.editCustomerById(id,customerDto);
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
