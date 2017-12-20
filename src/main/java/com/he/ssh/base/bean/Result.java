package com.he.ssh.base.bean;

import com.he.ssh.base.core.Guava;
import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;

public class Result extends BaseBean {

    private static final long  serialVersionUID = 1L;

    public boolean             success;                                                                             // 成功标志
    public Integer             code;                                                                                      // 返回标示
    public String              msg;                                                                                         // 相关消息
    public Object              data;                                                                                      // 相关数据
    public Map<String, Object> errors;                                                                                // 错误详细

    public Result() {
        super();
    }

    public Result(boolean success) {
        this.success = success;
    }

    public Result(boolean success, Integer code, Object data, String msg) {
        this(success);
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public Result(Integer code, Map<String, Object> errors, String msg) {
        this.success = false;
        this.code = code;
        this.errors = errors;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public boolean hasErrors() {
        if (this.errors != null && this.errors.size() > 0) {
            return true;
        }
        return false;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Map<String, Object> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, Object> errors) {
        this.errors = errors;
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

    public static Result failure() {
        return failure(null, null);
    }
    public static Result failure(Integer code) {
        return failure(code, null);
    }
    public static Result failure(String msg) {
        return failure(null, msg);
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
}
