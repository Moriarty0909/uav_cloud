package com.ccssoft.cloudadmin.handler;

import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author moriarty
 * @date 2020/6/3 15:49
 */
@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    public Map<String, Object> methodArgNotValidException(ConstraintViolationException cve, HttpServletRequest httpServletRequest) {
        Set<ConstraintViolation<?>> cves = cve.getConstraintViolations();
        StringBuffer errorMsg = new StringBuffer();
        cves.forEach(ex -> errorMsg.append(ex.getMessage()));
        Map<String, Object> respMap = new HashMap<>(4);
        respMap.put("code", -1);
        respMap.put("msg", errorMsg);
        return respMap;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({MethodArgumentNotValidException.class})
    public Map<String, Object> methodDtoNotValidException(Exception ex, HttpServletRequest request) {
        MethodArgumentNotValidException c = (MethodArgumentNotValidException) ex;
        List<ObjectError> errors = c.getBindingResult().getAllErrors();
        StringBuffer errorMsg = new StringBuffer();
        errors.stream().forEach(x -> {

            errorMsg.append(x.getDefaultMessage()).append(";");
        });
        Map<String, Object> respMap = new HashMap<>(4);
        respMap.put("code", -1);
        respMap.put("msg", errorMsg);
        return respMap;
    }
}
