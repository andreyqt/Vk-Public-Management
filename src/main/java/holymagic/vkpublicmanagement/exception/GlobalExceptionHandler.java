package holymagic.vkpublicmanagement.exception;

import jakarta.security.auth.message.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpClientErrorException(HttpClientErrorException e) {
        return new ErrorResponse(e.getStatusCode() + " | " + e.getStatusText());
    }

    @ExceptionHandler(HttpServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleHttpServerErrorException(HttpServerErrorException e) {
        return new ErrorResponse("Vk Api failed to respond:\n" + e.getStatusCode() + " | " + e.getStatusText());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleRuntimeException(RuntimeException e) {
        log.error("{}: {}",e.getClass().getName(), e.getMessage());
        String stackTrace = Arrays.stream(e.getStackTrace())
                .map(StackTraceElement::toString)
                .collect(Collectors.joining("\n"));
        log.error("StackTrace:\n {}", stackTrace);
        return new ErrorResponse("Something went wrong :(");
    }

    @ExceptionHandler(EmptyResponseException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleEmptyResponseException(EmptyResponseException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(ParamValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleParamValidationException(ParamValidationException e) {
        return new ErrorResponse("invalid param(s): " + e.getMessage());
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHandlerMethodValidationException(HandlerMethodValidationException e) {
        return new ErrorResponse("argument validation failed");
    }

    @ExceptionHandler(AuthUriSyntaxException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleAuthUriSyntaxException(AuthUriSyntaxException e) {
        return new ErrorResponse(e.getMessage());
    }

}
