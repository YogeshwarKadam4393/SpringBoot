package com.empMgtSytm.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepartmentRequestDTO {
	
//	private Long departmentId;

    @NotBlank(message = "Department name is required")
    private String departmentName;

//    @Valid
//    private List<EmployeeRequestDTO> employees;
}
