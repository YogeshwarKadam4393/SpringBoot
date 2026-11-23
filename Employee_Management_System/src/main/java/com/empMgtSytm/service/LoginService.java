package com.empMgtSytm.service;

import com.empMgtSytm.dto.LoginRequestDTO;
import com.empMgtSytm.dto.LoginResponseDTO;

public interface LoginService {

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO);

}