package com.empMgtSytm.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.empMgtSytm.utils.EmployeeStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeResponseDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String city;
    private String designation;
    private BigDecimal salary;
    private LocalDate dateOfJoining;
    private EmployeeStatus status;

//  instead of the whole Department object, send only what you need
    private Long departmentId;
    private String departmentName;
}