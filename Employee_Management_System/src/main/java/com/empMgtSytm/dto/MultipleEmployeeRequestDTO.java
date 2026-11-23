package com.empMgtSytm.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MultipleEmployeeRequestDTO {

    @NotNull(message = "Department ID is required")
    private Long departmentId;

    @Valid
    @NotEmpty(message = "Employee list cannot be empty")
    private List<EmployeeRequestDTO> employees;
}
