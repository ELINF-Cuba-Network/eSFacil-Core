package cu.vlired.esFacilCore.controller;

import cu.vlired.esFacilCore.components.ResponsesHelper;
import cu.vlired.esFacilCore.constants.Codes;
import cu.vlired.esFacilCore.exception.ApiError;
import cu.vlired.esFacilCore.exception.BaseException;
import cu.vlired.esFacilCore.exception.ResourceAlreadyTakenException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Date;

@Log4j2
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ErrorApiHandler extends ResponseEntityExceptionHandler {

    final
    ResponsesHelper rh;

    public ErrorApiHandler(ResponsesHelper rh) {
        this.rh = rh;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        log.error(ExceptionUtils.getStackTrace(ex));

        var error = buildError(ex.getMessage());
        return rh.buildResponse(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(ExceptionUtils.getStackTrace(ex));

        var error = buildError(ex.getMessage());
        return rh.buildResponse(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationServiceException.class)
    public ResponseEntity<Object> handleUsernameNotFoundException(AuthenticationServiceException ex) {
        log.error(ExceptionUtils.getStackTrace(ex));

        var error = buildError(ex);
        return rh.buildResponse(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        log.error(ExceptionUtils.getStackTrace(ex));

        var error = buildError(ex.getMessage());
        return rh.buildResponse(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceAlreadyTakenException.class)
    public ResponseEntity<Object> handleResourceAlreadyTakenException(ResourceAlreadyTakenException ex) {
        log.error(ExceptionUtils.getStackTrace(ex));

        var error = buildError(ex);
        return rh.buildResponse(error, HttpStatus.BAD_REQUEST);
    }

    public static ApiError buildError(AuthenticationServiceException ex) {
        var base = new BaseException(
                ex.getMessage(),
                "404"
        );

        return buildError(base);
    }

    public static ApiError buildError(BaseException ex) {
        var apiError = new ApiError();
        apiError.setCode(ex.getCode());
        apiError.setMessage(ex.getMessage());
        apiError.setTimestamp(new Date());

        return apiError;
    }

    public static ApiError buildError(String message) {
        var apiError = new ApiError();
        apiError.setCode(Codes.UNKNOWN);
        apiError.setMessage(message);
        apiError.setTimestamp(new Date());

        return apiError;
    }

}
