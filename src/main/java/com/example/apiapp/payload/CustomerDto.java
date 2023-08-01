package com.example.apiapp.payload;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {


    @NotNull(message = "FullName kiritilmagan")
    private String fullName;

    @NotNull(message = "PhoneNumber kiritilmagan")
    private String phoneNumber;

    @NotNull(message = "Address kirirtilmagan")
    private String address;

}
