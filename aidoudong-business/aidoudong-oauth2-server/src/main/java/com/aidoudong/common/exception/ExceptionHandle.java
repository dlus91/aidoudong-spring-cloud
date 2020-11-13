package com.aidoudong.common.exception;

import aidoudong.common.resultview.BaseResultView;
import com.aidoudong.common.result.ResultViewBuilder;
import com.aidoudong.common.utils.PropertiesEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;


@RestControllerAdvice
public class ExceptionHandle {
	public static final Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);
    //系统错误
    public static final int ERROR_CODE = 900;
    //系统参数错误
    public static final int PARAM_CODE = 1000;
    //业务错误
	public static final int BUSSINESS_CODE = 1100;
	public static final String PARAM_MESSAGE = "参数异常";

    @Autowired
    private BaseResultView fastJsonResultView;
    
	@ExceptionHandler(value = HttpMessageNotReadableException.class)
    public String handleParamException(HttpMessageNotReadableException httpMessageNotReadableException) {
        String msg = PropertiesEnum.ERROR_CODE_EN.getProperty(httpMessageNotReadableException.getMessage());
        return fastJsonResultView.fail(ResultViewBuilder.of(PARAM_CODE,PARAM_MESSAGE,msg));
    }
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
    public String handleParamException(MethodArgumentNotValidException methodArgumentNotValidException) {
		BindingResult results = methodArgumentNotValidException.getBindingResult();
		List<ObjectError> errorList = results.getAllErrors();
		List<String> resultList = new ArrayList<>();
        Properties errorCodeProps =  PropertiesEnum.ERROR_CODE_EN.getProperties();
		for(ObjectError error : errorList) {
			resultList.add(errorCodeProps.getProperty(error.getDefaultMessage()));
		}
		return fastJsonResultView.fail(ResultViewBuilder.of(PARAM_CODE,PARAM_MESSAGE,resultList));
    }
	
    @ExceptionHandler(value = BussinessException.class) // 处理BussinessException异常
    public String handleBussinessException(BussinessException bussiness) {
        Properties errorCodeProps =  PropertiesEnum.ERROR_CODE_EN.getProperties();
    	String msg = errorCodeProps.getProperty(bussiness.getMessage());
    	int exceptionCode = bussiness.getCode() > 0 ? bussiness.getCode() : BUSSINESS_CODE;
		return fastJsonResultView.fail(ResultViewBuilder.of(exceptionCode,PARAM_MESSAGE,msg));
    }
    
    @ExceptionHandler(value = Exception.class) // 处理Exception异常
    public String handleException(Exception e) {
        logger.error("Exception for handle ", e);
        // 自定义类，项目中用作API统一响应模板
        String resultStr;
        if (e instanceof ConstraintViolationException) {
            ConstraintViolationException applicationException = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> violations = applicationException.getConstraintViolations();
            StringBuilder stringBuilder = new StringBuilder();
            for (ConstraintViolation<?> item : violations) {
                stringBuilder.append("[").append(item.getMessage()).append("]");
            }
            String msg = stringBuilder.toString();
            logger.error("ConstraintViolation msg is : " + msg);
            resultStr = fastJsonResultView.fail(ResultViewBuilder.of(BUSSINESS_CODE,PARAM_MESSAGE,msg));
        } else if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException applicationException = (MethodArgumentNotValidException) e;
            List<ObjectError> allErrors = applicationException.getBindingResult().getAllErrors();
            StringBuilder stringBuilder = new StringBuilder();
            for (ObjectError error : allErrors) {
                stringBuilder.append("[").append(error.getDefaultMessage()).append("]");
            }
            String msg = stringBuilder.toString();
            logger.error("ArgumentNotValid  msg is : " + msg);
            resultStr = fastJsonResultView.fail(ResultViewBuilder.fail(ERROR_CODE, msg));
        } else if (e instanceof MissingServletRequestParameterException) {
            MissingServletRequestParameterException applicationException = (MissingServletRequestParameterException) e;
            String parameterName = applicationException.getParameterName();
            String parameterType = applicationException.getParameterType();
            String msg = "parameter "
                    + parameterName + " is null "
                    + " , expect: " + parameterType;
            resultStr = fastJsonResultView.fail(ResultViewBuilder.fail(ERROR_CODE, msg));
        } else if (e instanceof HttpMediaTypeNotSupportedException) {
            HttpMediaTypeNotSupportedException applicationException = (HttpMediaTypeNotSupportedException) e;
            String msg = Objects.requireNonNull(applicationException.getContentType()).getSubtype();
            resultStr = fastJsonResultView.fail(ResultViewBuilder.fail(ERROR_CODE, msg));
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            HttpRequestMethodNotSupportedException applicationException = (HttpRequestMethodNotSupportedException) e;
            String msg = applicationException.getMethod();
            resultStr = fastJsonResultView.fail(ResultViewBuilder.fail(ERROR_CODE, msg));
        } else if (e instanceof NoHandlerFoundException) {
            NoHandlerFoundException applicationException = (NoHandlerFoundException) e;
            String msg = applicationException.getHttpMethod() + " --> " + applicationException.getRequestURL();
            resultStr = fastJsonResultView.fail(ResultViewBuilder.fail(ERROR_CODE, msg));
        } else if (e instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException applicationException = (MethodArgumentTypeMismatchException) e;
            String msg = "parameter " + applicationException.getName()
                    + " is not type of " + Objects.requireNonNull(applicationException.getRequiredType()).getSimpleName();
            resultStr = fastJsonResultView.fail(ResultViewBuilder.fail(ERROR_CODE, msg));
        } else if (e instanceof HttpMessageNotReadableException) {
            HttpMessageNotReadableException applicationException = (HttpMessageNotReadableException) e;
            String msg = applicationException.getMessage();
            resultStr = fastJsonResultView.fail(ResultViewBuilder.fail(ERROR_CODE, msg));
        } else {
            resultStr = fastJsonResultView.fail(ResultViewBuilder.fail(ERROR_CODE, e.getMessage()));
        }
        return resultStr;
    }
}
