package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Wangmin
 * @date 2022/11/15 15:10
 */

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login (HttpServletRequest request, @RequestBody Employee employee){
        /**
         * 1.将页面提交的密码password进行md5加密
         * 2.根据页面提交的用户名username查询数据库
         * 3。判断用户是否存在，不存在返回登录失败结果
         * 4。存在对比用户密码是否正确 不正确返回登录失败结果
         * 5。查看员工状态，禁用返回禁用结果
         * 6。登录成功 将员工信息存入session返回登录成功结果
         */
        //1.将页面提交的密码password进行md5加密
        String password = DigestUtils.md5DigestAsHex(employee.getPassword().getBytes());
        //2.根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee employee1 = employeeService.getOne(queryWrapper);
        //3。判断用户是否存在，不存在返回登录失败结果
        if(employee1==null){
            return R.error("登录失败，用户不存在。");
        }
        //4。存在对比用户密码是否正确 不正确返回登录失败结果
        if(!employee1.getPassword().equals(password)){
            return R.error("登录失败，密码错误。");
        }
        //5。查看员工状态，禁用返回禁用结果
        if(employee1.getStatus()==0){
            return R.error("登录失败，账号已禁用。");
        }
        //6。登录成功 将员工信息存入session返回登录成功结果
        request.getSession().setAttribute("employee",employee1);
        log.info("登录成功");

        return R.success(employee1);
    }

    /**
     * 退出登录
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> login (HttpServletRequest request){
        //清理session数据
        request.getSession().removeAttribute("employee");
        log.info("退出成功");

        return R.success("退出成功");
    }


    /**
     * 新增员工
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee){
        log.info("新增员工，员工信息:{}",employee.toString());
        // 设置初始值密码
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        // employee.setCreateTime(LocalDateTime.now());
        // employee.setUpdateTime(LocalDateTime.now());

        // 获得当前用户登录的id
        // Long id = (Long) request.getSession().getAttribute("employee");

        // employee.setUpdateUser(id);
        // employee.setCreateUser(id);

        employeeService.save(employee);
        return R.success("新增员工成功");
    }

    public R<Page> page (int page , int pageSize , String name ){
        log.info("page = {},pagesize = {}, name = {}",page, pageSize , name);

        //构造分页构造器
        Page pageInfo = new Page(page ,pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        //添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        //执行查询
        employeeService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }
}
