package com.sunl19ht.mapper;

import com.sunl19ht.pojo.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

@Mapper
public interface EmployeeMapper {
    @Select("select * from employees where openid = #{openid}")
    Employee getByOpenid(String openid);

    void insert(Employee employee);
}
