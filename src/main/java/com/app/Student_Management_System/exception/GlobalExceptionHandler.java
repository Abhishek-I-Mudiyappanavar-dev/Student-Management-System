package com.app.Student_Management_System.exception;

import com.app.Student_Management_System.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            StudentNotFoundException.class,
            InstructorNotFoundException.class,
            DepartmentNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFoundException(
            RuntimeException ex,
            HttpServletRequest request
    ) {


        return buildStandardErrorResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request
        );
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEmailException(
            DuplicateEmailException ex,
            HttpServletRequest request
    ) {
        return buildStandardErrorResponse(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                request
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        Map<String, String> errors = new LinkedHashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.putIfAbsent(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        return buildStandardErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Request validation failed",
                request,
                errors
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleUnreadableMessage(HttpMessageNotReadableException ex, HttpServletRequest request){

        return buildStandardErrorResponse(HttpStatus.BAD_REQUEST,
                "Malformed or unreadable request body",request);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleGlobalException(
//            Exception ex,
//            HttpServletRequest request
//    ) {
//
//        return buildStandardErrorResponse(
//                HttpStatus.INTERNAL_SERVER_ERROR,
//                "An unexpected error occurred",
//                request
//        );
//    }

    private ResponseEntity<ErrorResponse> buildStandardErrorResponse(
            HttpStatus status,
            String message,
            HttpServletRequest request
    ) {
        return buildStandardErrorResponse(
                status,
                message,
                request,
                null
        );
    }

    private ResponseEntity<ErrorResponse> buildStandardErrorResponse(
            HttpStatus status,
            String message,
            HttpServletRequest request,
            Map<String, String> errors
    ) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timeStamp(OffsetDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(request.getRequestURI())
                .errors(errors)
                .build();

        return ResponseEntity
                .status(status)
                .body(errorResponse);
    }
}