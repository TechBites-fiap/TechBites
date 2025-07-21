package br.com.techchallenge.techbites.controllers.handlers;


import br.com.techchallenge.techbites.dtos.*;
import br.com.techchallenge.techbites.services.exceptions.*;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import br.com.techchallenge.techbites.domain.enums.Role;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;



import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ControllerExeceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ControllerExeceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(
            Exception ex,
            HttpServletRequest request
    ) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var response = new ErrorResponseDTO(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                request.getMethod()
        );

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResourceNotFoundDTO> handlerResourceNotFoundExeception (
            ResourceNotFoundException e,
            HttpServletRequest request) {

        var status = HttpStatus.NOT_FOUND.value();
        var method = request.getMethod();
        var path = request.getRequestURI();

        log.warn("Resource Not Found : [{} {}] - {} (status: {})", method, path, e.getMessage(), status);

        return ResponseEntity.status(status).body(new ResourceNotFoundDTO(e.getMessage() , status , method , path));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorDTO> handlerMethodArgumentNotValidException (
            MethodArgumentNotValidException e,
            HttpServletRequest request) {

        var status = HttpStatus.BAD_REQUEST.value();
        var method = request.getMethod();
        var path = request.getRequestURI();

        List<String> errors = new ArrayList<String>();
        for (var error : e.getBindingResult().getFieldErrors()) errors.add(error.getField() + " : " + error.getDefaultMessage());

        log.warn("Validation error: [{} {}] - Invalid fields: {}", method, path, errors);

        return ResponseEntity.status(status).body(new ValidationErrorDTO(errors , status , method , path));

    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<DuplicateKeyDTO> handlerDuplicateEmailException (
            DuplicateKeyException e,
           HttpServletRequest request
    ) {

        var status = HttpStatus.CONFLICT.value();
        var method = request.getMethod();
        var path = request.getRequestURI();

        log.warn("Duplicate key violation at [{} {}] - Reason: {}", method, path, e.getMessage());

        return ResponseEntity.status(status).body(new DuplicateKeyDTO(e.getMessage() , status , method , path));

    }

    // MÉTODO APRIMORADO
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ValidationErrorDTO> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex,
            HttpServletRequest request
    ) {
        var status = HttpStatus.BAD_REQUEST.value();
        var method = request.getMethod();
        var path = request.getRequestURI();
        List<String> errors = new ArrayList<>();

        Throwable cause = ex.getCause();

        if (cause instanceof InvalidFormatException invalidFormatException &&
                invalidFormatException.getTargetType().equals(Role.class)) {

            errors.add("role : Invalid role. Allowed values: ADMIN, USER, USER_RESTAURANT.");

        } else if (cause instanceof UnrecognizedPropertyException exCause &&
                exCause.getPropertyName().equals("password")) {

            errors.add("O campo 'password' não pode ser enviado para este endpoint. Utilize a rota específica (PATCH) de alteração de senha.");

        } else {
            // Mensagem genérica para todos os outros erros de formatação de JSON
            errors.add("Malformed JSON request.");
        }

        log.warn("Deserialization error: [{} {}] - {}", method, path, errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ValidationErrorDTO(errors, status, method, path));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ValidationErrorDTO> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request
    ) {
        var status = HttpStatus.BAD_REQUEST.value();
        var method = request.getMethod();
        var path = request.getRequestURI();

        String paramName = ex.getName(); // geralmente "id"
        String expectedType = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "expected type";

        List<String> errors;
        if (expectedType.equals(Long.class.getSimpleName())) {
            errors = List.of(paramName + " : Invalid value. Must be a valid Number.");
        } else {
            errors = List.of(paramName + " : Invalid value. Must be a valid " + expectedType + ".");
        }

        log.warn("Type mismatch error: [{} {}] - {}", method, path, errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ValidationErrorDTO(errors, status, method, path));
    }

    @ExceptionHandler(InvalidCurrentPasswordException.class)
    public ResponseEntity<PasswordExceptionDTO> handleInvalidCurrentPasswordException(
            InvalidCurrentPasswordException ex,
            HttpServletRequest request
    ) {
        var error = "Authentication Failed";
        var status = HttpStatus.UNAUTHORIZED.value();
        var method = request.getMethod();
        var path = request.getRequestURI();

        log.warn("The current password provided is incorrect.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new PasswordExceptionDTO(ex.getMessage() , error , status , method , path ));
    }

    @ExceptionHandler(HandleNewPasswordSameAsCurrent.class)
    public ResponseEntity<PasswordExceptionDTO> handleHandleNewPasswordSameAsCurrent(
            HandleNewPasswordSameAsCurrent ex,
            HttpServletRequest request
    ) {
        var error = "Validation Error";
        var status = HttpStatus.BAD_REQUEST.value();
        var method = request.getMethod();
        var path = request.getRequestURI();

        log.warn("New password cannot be the same as the current password.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new PasswordExceptionDTO(ex.getMessage() , error , status , method , path ));
    }

    @ExceptionHandler(HandleNewPasswordNotSameAsConfirmPassword.class)
    public ResponseEntity<PasswordExceptionDTO> handleHandleNewPasswordSameAsCurrent(
            HandleNewPasswordNotSameAsConfirmPassword ex,
            HttpServletRequest request
    ) {
        var error = "Validation Error";
        var status = HttpStatus.BAD_REQUEST.value();
        var method = request.getMethod();
        var path = request.getRequestURI();

        log.warn("New password and confirmation do not match.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new PasswordExceptionDTO(ex.getMessage() , error , status , method , path ));
    }


}
