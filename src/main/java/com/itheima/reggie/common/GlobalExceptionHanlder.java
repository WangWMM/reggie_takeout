package com.itheima.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author Wangmin
 * @date 2022/12/9 17:43
 */

@ControllerAdvice(annotations = {RestController.class , Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHanlder {

    /**
     * 异常处理方法
     * @param ex SQL异常
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.error(ex.getMessage());
        if(ex.getMessage().contains("Duplicate entry"))
        return R.error("失败了");
    }
}
