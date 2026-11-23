package com.empMgtSytm.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepartmentResponseDTO {

    private Long departmentId;
    private String departmentName;
    private List<EmployeeResponseDTO> employees;
}
