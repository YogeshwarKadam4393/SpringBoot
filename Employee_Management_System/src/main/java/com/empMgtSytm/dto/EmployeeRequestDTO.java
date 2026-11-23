package com.empMgtSytm.dto;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.empMgtSytm.utils.EmployeeStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeRequestDTO {

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50)
    private String lastName;
    
//    @Min(value = 18 , message = "Age Must Be Minimum 18")
//    @Max(value = 60 , message = "Age Must Not exceeds 60")
//    private int age;
    
    @NotBlank(message = "UserName is required")
    @Column(name = "Employee_UserName" , nullable = false, unique = true, length = 50)
    private String userName;
    
    @NotBlank(message = "Password is required")
    @Column(name = "Employee_Password" , nullable = false, unique = true, length = 50)
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[6-9][0-9]{9}$", message = "Enter a valid 10 digit mobile number")
    private String phoneNumber;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Designation is required")
    private String designation;

    @NotNull(message = "Salary is required")
    @Positive(message = "Salary must be greater than 0")
    private BigDecimal salary;

    // used only when adding an employee alone.
    // Not needed when the employee is inside a department.
    private Long departmentId;
    
    //@NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;
}