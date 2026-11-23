package com.empMgtSytm.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;

import com.empMgtSytm.utils.EmployeeStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Employee_INFO")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true)
    private Long id;

//  @NotBlank means: this text field must have real content. 
//  It can’t be null, empty, or only spaces.
//  @Size checks the minimum and maximum number of characters
    
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be 2 to 50 characters")
    @Column(name = "Employee_FirstName" , nullable = false , length = 50)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be 2 to 50 characters")
    @Column(name = "Employee_LastName" , nullable = false , length = 50)
    private String lastName;
    
//  @Min(value = 18 , message = "Age Must Be Minimum 18")
//  @Max(value = 60 , message = "Age Must Not exceeds 60")
//  private int age;
    
    @NotBlank(message = "UserName is required")
    @Column(name = "Employee_UserName" , nullable = false, unique = true, length = 50)
    private String userName;
    
    @NotBlank(message = "Password is required")
    @Column(name = "Employee_Password" , nullable = false, unique = true, length = 50)
    private String password;

//  @Email checks that a String looks like a valid email address (something like name@domain.com)
    
    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email")
    @Column(name = "Employee_Email" , nullable = false, unique = true, length = 100)
    private String email;

//  ^      = Start of the text
//  [0-9]  = Any one digit from 0 to 9
//  {10}   = The previous thing must repeat exactly 10 times
//  $	   = End of the text
//  [6-9]  = First digit must be 6, 7, 8 or 9 (valid Indian mobile number)
    
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[6-9][0-9]{9}$", message = "Enter a valid 10 digit mobile number")
    @Column(name = "Employee_PhoneNumber" , nullable = false, unique = true, length = 10)
    private String phoneNumber;
    
    @NotBlank(message = "City is required")
    @Column(name = "Employee_City" , nullable = false, length = 50)
    private String city;

    //@NotNull(message = "Department is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_ID")
//  @ToString.Exclude
//  @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Department department;

    @NotBlank(message = "Designation is required")
    @Column(name = "Employee_Designation" , nullable = false, length = 50)
    private String designation;

//  @Positive means: The number must be greater than 0. Zero and negative numbers are rejected
//  precision : Total number of digits (before + after the decimal point)
//  scale :	Number of digits after the decimal point  
//  Example --->
    
//  12345678 90 . 12
//  |-------|     |--|
//  10 digits     2 digits
//  (before .)    (scale)
//  (Precision)  

//  BigDecimal is a Java class for storing decimal numbers with exact precision. It’s the correct type for money.    
//  Why not double? ---> double stores numbers in binary, so many decimals can’t be represented exactly:
//  @NotNull is used because @Positive allows null on its own
//  @Digits is the Java-side check that matches precision and scale
//  integer = 10 : Maximum 10 digits before the decimal point
//  fraction = 2 : Maximum 2 digits after the decimal point    
    
    @NotNull(message = "Salary is required")
    @Positive(message = "Salary must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Salary format is invalid")
    @Column(name = "Employee_Salary", nullable = false, precision = 12, scale = 2)
    private BigDecimal salary;

//  @PastOrPresent means: the date must be today or earlier. Future dates are rejected
    
//  @NotNull(message = "Date of joining is required")
//  @PastOrPresent(message = "Date of joining cannot be in the future")
//  @Past
//  @Future    
    @CreationTimestamp
    @Column(name = "Employee_DateOfJoining", nullable = false, updatable = false)
    private LocalDate dateOfJoining;

//  @Enumerated tells JPA how to store a Java enum in the database
//  EnumType.STRING	: “Save it as its text name” (instead of a number) 
    
    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "Employee_Status", nullable = false, length = 20)
    private EmployeeStatus status;
    
}