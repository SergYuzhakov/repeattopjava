package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.ErrorInfo;
import ru.javawebinar.topjava.util.exception.ErrorType;
import ru.javawebinar.topjava.util.exception.IllegalRequestDataException;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.exception.ErrorType.*;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class ExceptionInfoHandler {
    private static Logger log = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    private static MessageSource messageSource;

    private static Map<String, String> CONSTRAINS_I18N_MAP = Map.of(
            "users_unique_email_idx", "user.duplicateEmail",
            "meals_unique_user_datetime_idx", "meal.duplicateDateTime");

    public ExceptionInfoHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    //  http://stackoverflow.com/a/22358422/548473
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(NotFoundException.class)
    public ErrorInfo handleError(HttpServletRequest req, NotFoundException e) {
        return logAndGetErrorInfo(req, e, false, DATA_NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorInfo conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        return logAndGetErrorInfo(req, e, false, DATA_ERROR);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler({IllegalRequestDataException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public ErrorInfo illegalRequestDataError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorInfo handleError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, true, APP_ERROR);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(BindException.class)
    public ErrorInfo bindingError(HttpServletRequest req, BindException e) {
        return logAndGetErrorInfo(req, e, e.getBindingResult());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorInfo MethodArgumentNotValidException(HttpServletRequest req, BindException e) {
        return logAndGetErrorInfo(req, e, e.getBindingResult());
    }

    //    https://stackoverflow.com/questions/538870/should-private-helper-methods-be-static-if-they-can-be-static
    private static ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, BindingResult result) {
        List<String> errorMessages = getErrorResponse(result);
        printLogErrorCause(req, e, true, VALIDATION_ERROR);
        return new ErrorInfo(req.getRequestURL(), VALIDATION_ERROR, errorMessages);
    }

    private static ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException, ErrorType errorType) {
        String rootCauseMessage = getErrorMessage(e);
        printLogErrorCause(req, e, logException, errorType);
        return new ErrorInfo(req.getRequestURL(), errorType, rootCauseMessage);
    }

    private static void printLogErrorCause(HttpServletRequest req, Exception e, boolean logException, ErrorType errorType) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        if (logException) {
            log.error(errorType + " at request " + req.getRequestURL(), rootCause.toString());
        } else {
            log.warn("{} at request  {}: {}", errorType, req.getRequestURL(), rootCause.toString());
        }

    }

    private static List<String> getErrorResponse(BindingResult result) {
        return result.getFieldErrors().stream()
                .map(fe -> String.format("[%s] %s",
                        fe.getField().substring(0, 1).toUpperCase(Locale.ROOT) + fe.getField().substring(1),
                        fe.getDefaultMessage()))
                .collect(Collectors.toList());
    }

    public static String getErrorMessage(Exception e) {
        String rootCauseMessage = ValidationUtil.getRootCause(e).getMessage();
        for (Map.Entry<String, String> entry : CONSTRAINS_I18N_MAP.entrySet()) {
            if (rootCauseMessage.contains(entry.getKey())) {
                return messageSource.getMessage(entry.getValue(), null, Locale.ROOT);
            }
        }
        return rootCauseMessage;
    }

}