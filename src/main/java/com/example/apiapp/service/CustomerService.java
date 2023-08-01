package com.example.apiapp.service;

import com.example.apiapp.entity.Customer;
import com.example.apiapp.payload.ApiResponse;
import com.example.apiapp.payload.CustomerDto;
import com.example.apiapp.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public ApiResponse addCustomer(CustomerDto customerDto){
        boolean existsByPhoneNumber = customerRepository.existsByPhoneNumber(customerDto.getPhoneNumber());
        if (existsByPhoneNumber)
            return new ApiResponse("Bunday telefo`n raqam mavjud",false);
        Customer customer = new Customer();
        customer.setFullName(customerDto.getFullName());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setAddress(customerDto.getAddress());customerRepository.save(customer);
        return new ApiResponse("Customer saqlandi",true,customer.getId());

    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Integer id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        return optionalCustomer.orElse(null);
    }

    public ApiResponse deleteCustomerById(Integer id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isEmpty()) {
            return new ApiResponse("Bunday Id li Customer yo`q", false);
        }
        customerRepository.deleteById(id);
        return new ApiResponse("Customer o`chirildi",true);
    }

    public ApiResponse editCustomerById(Integer id, CustomerDto customerDto) {
        boolean phoneNumberAndIdNot = customerRepository.existsByPhoneNumberAndIdNot(customerDto.getPhoneNumber(), id);
        if (phoneNumberAndIdNot) {
            return new ApiResponse("Bunday telefo`n raqamli  mijoz mavjud", false);
        }
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isEmpty()) {
            return new ApiResponse("Bunday Id li mijoz mavjud emas", false);
        }
        Customer customer = optionalCustomer.get();
        customer.setFullName(customerDto.getFullName());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setAddress(customerDto.getAddress());
        customerRepository.save(customer);
        return new ApiResponse("Customer o`zgartirildi",true,customer.getId());


    }
}
