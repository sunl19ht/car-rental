package com.sunl19ht.service;

import com.sunl19ht.client.pojo.dto.UserLoginDTO;
import com.sunl19ht.pojo.dto.EmployeeLoginDTO;
import com.sunl19ht.pojo.entity.Employee;

public interface EmployeeService {

    Employee wxLogin(EmployeeLoginDTO employeeLoginDTO);
}
