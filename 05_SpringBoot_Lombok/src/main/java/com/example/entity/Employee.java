package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//@Setter
//@Getter
@AllArgsConstructor
//@ToString
//@EqualsAndHashCode
@Data
@NoArgsConstructor
@Builder
public class Employee {

    private int id;
    private String name;
    private double salary;
}