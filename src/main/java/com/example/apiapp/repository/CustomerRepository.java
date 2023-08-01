package com.example.apiapp.repository;

import com.example.apiapp.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    boolean existsByPhoneNumber(String phoneNumber);

    // Id si id ga teng bo`lmagan shu telefo`n raqamli mijoz bormi degani
    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Integer id);
}
