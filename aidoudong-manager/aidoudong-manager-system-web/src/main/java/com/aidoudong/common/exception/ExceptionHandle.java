package com.aidoudong.common.exception;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import aidoudong.common.resultview.BaseResultView;
import com.aidoudong.common.result.ResultView;
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

import com.aidoudong.common.utils.PropertiesUtil;


@RestControllerAdvice
public class ExceptionHandle {
	public static final Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);
    private static final Map<String, String> EMPTY_DATA = new HashMap<>();
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
    public String handleParamException(HttpMessageNotReadableException httpMessageNotReadableException, HttpServletRequest request) {
        Properties errorCodeProps = PropertiesUtil.getErrorCodeEnProperties();
        String msg = errorCodeProps.getProperty(httpMessageNotReadableException.getMessage());
        return fastJsonResultView.fail(new ResultView(PARAM_CODE,PARAM_MESSAGE,msg));
    }
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
    public String handleParamException(MethodArgumentNotValidException methodArgumentNotValidException , HttpServletRequest request) {
		BindingResult results = methodArgumentNotValidException.getBindingResult();
		List<ObjectError> errorList = results.getAllErrors();
		List<String> resultList = new LinkedList<>();
        Properties errorCodeProps = PropertiesUtil.getErrorCodeEnProperties();
		for(ObjectError error : errorList) {
			resultList.add(errorCodeProps.getProperty(error.getDefaultMessage()));
		}
		return fastJsonResultView.fail(new ResultView(PARAM_CODE,PARAM_MESSAGE,resultList));
    }
	
    @ExceptionHandler(value = BussinessException.class) // 处理BussinessException异常
    public String handleBussinessException(BussinessException bussiness, HttpServletRequest request) {
        Properties errorCodeProps = PropertiesUtil.getErrorCodeEnProperties();
    	String msg = errorCodeProps.getProperty(bussiness.getMessage());
    	int exceptionCode = bussiness.getCode() > 0 ? bussiness.getCode() : BUSSINESS_CODE;
		return fastJsonResultView.fail(new ResultView(exceptionCode,PARAM_MESSAGE,msg));
    }
    
    @ExceptionHandler(value = Exception.class) // 处理Exception异常
    public String handleException(Exception e) {
        logger.error("Exception for handle ", e);
        // 自定义类，项目中用作API统一响应模板
        String resultStr = "";
        if (e instanceof ConstraintViolationException) {
            ConstraintViolationException applicationException = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> violations = applicationException.getConstraintViolations();
            StringBuilder stringBuilder = new StringBuilder();
            for (ConstraintViolation<?> item : violations) {
                stringBuilder.append("[" + item.getMessage() + "]");
            }
            String msg = stringBuilder.toString();
            logger.error("ConstraintViolation msg is : " + msg);
            resultStr = fastJsonResultView.fail(new ResultView(BUSSINESS_CODE,PARAM_MESSAGE,msg));
        } else if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException applicationException = (MethodArgumentNotValidException) e;
            List<ObjectError> allErrors = applicationException.getBindingResult().getAllErrors();
            StringBuilder stringBuilder = new StringBuilder();
            for (ObjectError error : allErrors) {
                stringBuilder.append("[" + error.getDefaultMessage() + "]");
            }
            String msg = stringBuilder.toString();
            logger.error("ArgumentNotValid  msg is : " + msg);
            resultStr = fastJsonResultView.fail(new ResultView().fail(ERROR_CODE, msg));
        } else if (e instanceof MissingServletRequestParameterException) {
            MissingServletRequestParameterException applicationException = (MissingServletRequestParameterException) e;
            String parameterName = applicationException.getParameterName();
            String parameterType = applicationException.getParameterType();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("parameter "
                    		+ parameterName + " is null "
                    		+ " , expect: " + parameterType);
            String msg = stringBuilder.toString();
            resultStr = fastJsonResultView.fail(new ResultView().fail(ERROR_CODE, msg));
        } else if (e instanceof HttpMediaTypeNotSupportedException) {
            HttpMediaTypeNotSupportedException applicationException = (HttpMediaTypeNotSupportedException) e;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(applicationException.getContentType().getSubtype());
            String msg = stringBuilder.toString();
            resultStr = fastJsonResultView.fail(new ResultView().fail(ERROR_CODE, msg));
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            HttpRequestMethodNotSupportedException applicationException = (HttpRequestMethodNotSupportedException) e;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(applicationException.getMethod());
            String msg = stringBuilder.toString();
            resultStr = fastJsonResultView.fail(new ResultView().fail(ERROR_CODE, msg));
        } else if (e instanceof NoHandlerFoundException) {
            NoHandlerFoundException applicationException = (NoHandlerFoundException) e;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(
                    applicationException.getHttpMethod() + " --> " + applicationException.getRequestURL());
            String msg = stringBuilder.toString();
            resultStr = fastJsonResultView.fail(new ResultView().fail(ERROR_CODE, msg));
        } else if (e instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException applicationException = (MethodArgumentTypeMismatchException) e;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(
                    "parameter " + applicationException.getName()
                    + " is not type of " + applicationException.getRequiredType().getSimpleName());
            String msg = stringBuilder.toString();
            resultStr = fastJsonResultView.fail(new ResultView().fail(ERROR_CODE, msg));
        } else if (e instanceof HttpMessageNotReadableException) {
            HttpMessageNotReadableException applicationException = (HttpMessageNotReadableException) e;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(applicationException.getMessage());
            String msg = stringBuilder.toString();
            resultStr = fastJsonResultView.fail(new ResultView().fail(ERROR_CODE, msg));
        } else {
            resultStr = fastJsonResultView.fail(new ResultView().fail(ERROR_CODE, e.getMessage()));
        }
        return resultStr;
    }
}
