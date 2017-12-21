package com.he.ssh.base.bean;

import com.he.ssh.base.core.Guava;
import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;

public class Results {

    protected Results() {
    }

    public static Result newResult() {
        return new Result();
    }

    public static Result newResult(Map<String, Object> properties) throws Exception {
        Result result = newResult();
        BeanUtils.populate(result, properties);
        return result;
    }

    //
    // 业务调用成功
    // --------------------------------------------------------------------------------------------------------------------------------
    public static Result success() {
        return success(null, null);
    }

    public static Result success(Integer code) {
        return success(code, null);
    }

    public static Result success(String msg) {
        return success(null, msg);
    }

    public static Result success(Integer code, String msg) {
        return successWithData(code, null, msg);
    }

    public static Result successWithData(Object data) {
        return successWithData(null, data, null);
    }

    public static Result successWithData(Object data, String msg) {
        return successWithData(null, data, msg);
    }

    public static Result successWithData(Integer code, Object data) {
        return successWithData(code, data, null);
    }

    public static Result successWithData(Integer code, Object data, String msg) {
        return new Result(true, code, data, msg);
    }

    //
    // 业务调用失败
    // --------------------------------------------------------------------------------------------------------------------------------

    public static Result failure(Integer code) {
        return failure(code, null);
    }

    public static Result failure(Integer code, String msg) {
        return failureWithData(code, null, msg);
    }

    public static Result failureWithData(Object data) {
        return failureWithData(null, data, null);
    }

    public static Result failureWithData(Object data, String msg) {
        return failureWithData(null, data, msg);
    }

    public static Result failureWithData(Integer code, Object data) {
        return failureWithData(code, data, null);
    }

    public static Result failureWithData(Integer code, Object data, String msg) {
        return new Result(false, code, data, msg);
    }

    //
    // 代码执行失败:返回包含错误提示
    // --------------------------------------------------------------------------------------------------------------------------------
    public static Result failureWithError(Throwable ex) {
        return failureWithError(null, null, ex.getMessage());
    }

    public static Result failureWithError(String field, String msg) {
        Map<String, Object> errors = Guava.newHashMap();
        errors.put(field, msg);
        return failureWithError(null, errors, msg);
    }

    public static Result failureWithError(Map<String, Object> errors, String msg) {
        return failureWithError(null, errors, msg);
    }

    public static Result failureWithError(Integer code, Map<String, Object> errors, String msg) {
        return new Result(code, errors, msg);
    }

    //
    // 实体验证信息
    // --------------------------------------------------------------------------------------------------------------------------------

 /*   public Result failure(BindingResult result) {
        return failure(result.getAllErrors());
    }

    public Result failure(BindingResult result, String msg) {
        return failure(result.getAllErrors(), msg);
    }

    public Result failure(List<ObjectError> objectErrors) {
        return failure(objectErrors, null);
    }

    public Result failure(List<ObjectError> objectErrors, String msg) {
        Map<String, Object> errors = Maps.newHashMap();
        if (objectErrors != null && objectErrors.size() > 0) {
            errors = Maps.newHashMap();
            for (ObjectError error : objectErrors) {
                String key = (error instanceof FieldError ? ((FieldError) error).getField() : error.getObjectName());
                errors.put(key, error.getDefaultMessage());
            }
        }
        return Results.failureWithError(errors, msg);
    }*/
}
