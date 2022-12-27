package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Wangmin
 * @date 2022/11/15 15:02
 */

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
