package com.edenspring.framework.mvc.ex;

import brave.Tracer;
import com.edenspring.framework.common.error.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/**
 * @author eden
 * @since 2020/8/15
 */
@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
@ResponseBody
public class CommonExceptionHandler {

    private final String appName;

    private final Tracer tracer;

    private final boolean includeErrorStacks;

    private ErrorMessage buildErrorMessage(HttpServletRequest request, Throwable error, ErrorCode errorCode, String appName) {
        return ErrorMessage.builder()
                .errorParam(Optional.of(error)
                        .filter(BusinessException.class::isInstance)
                        .map(BusinessException.class::cast)
                        .map(BusinessException::getErrorParam)
                        .orElse(null))
                .note(errorCode.getNote())
                .detail(errorCode.getDetail())
                .code(errorCode.getCode())
                .from(appName)
                .path(request.getRequestURI())
                .trace(tracer.currentSpan().context().toString().replace("/", ","))
                .timestamp(System.currentTimeMillis())
                .errorStacks(includeErrorStacks ? Arrays.stream(ExceptionUtils.getRootCauseStackTrace(error))
                        .map(stack -> stack.replaceAll("\t", "  "))
                        .collect(Collectors.toList()) : null)
                .build();
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleBusinessException(BusinessException ex, HttpServletRequest request) {
        log.warn(ex.getMessage(), ex);
        return buildErrorMessage(request, ex, ex, appName);
    }

    /**
     * 参数异常
     */
    @ExceptionHandler({
            ServletRequestBindingException.class,
            HttpMessageNotReadableException.class,
            TypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ErrorMessage handleParamException(Exception ex, HttpServletRequest request) {
        log.warn(ex.getMessage(), ex);
        ErrorCode errorCode = ParamErrorCode.of(getTrace(), ex.getMessage());
        return buildErrorMessage(request, ex, errorCode, appName);
    }

    /**
     * 参数异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ErrorCode handleNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        log.warn(ex.getMessage(), ex);
        String note = Optional.of(ex)
                .map(MethodArgumentNotValidException::getBindingResult)
                .map(BindingResult::getFieldError)
                .map(error -> String.format("'%s' %s", error.getField(), error.getDefaultMessage()))
                .orElse("");
        ErrorCode errorCode = ParamNotValidErrorCode.of(note, ex.getMessage());
        return buildErrorMessage(request, ex, errorCode, appName);
    }

    /**
     * 其他系统非预期异常
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private ErrorMessage handleException(Throwable ex, HttpServletRequest request) {
        log.error(ex.getMessage(), ex);
        ErrorCode errorCode = SystemErrorCode.of(getTrace(), ex.getMessage());
        return buildErrorMessage(request, ex, errorCode, appName);
    }

    /**
     * 网络异常
     */
    @ExceptionHandler({
            ConnectException.class,
            TimeoutException.class,
            SocketTimeoutException.class
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private ErrorMessage handleNetworkException(Exception ex, HttpServletRequest request) {
        log.warn(ex.getMessage(), ex);
        ErrorCode errorCode = NetworkErrorCode.of(getTrace(), ex.getMessage());
        return buildErrorMessage(request, ex, errorCode, appName);
    }

    /**
     * 需要版本升级异常
     */
    @ExceptionHandler(NeedUpgradeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ErrorMessage handleNeedUpgradeException(NeedUpgradeException ex, HttpServletRequest request) {
        log.warn(ex.getMessage(), ex);
        return buildErrorMessage(request, ex, ex, appName);
    }

    private String getTrace() {
        return tracer.currentSpan()
                .context()
                .traceIdString();
    }


}
