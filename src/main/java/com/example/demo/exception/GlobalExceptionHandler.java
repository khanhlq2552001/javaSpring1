package com.example.demo.exception;


import com.example.demo.dto.request.ApiResponse;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintViolation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private  static  final String MIN_ATTRIBUTE = "min";

    @ExceptionHandler(value =  Exception.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException e){

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(ErrorCode.UNCATEGOIED_EXEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGOIED_EXEPTION.getMessage());
        return  ResponseEntity.badRequest().body(apiResponse);
    }
    @ExceptionHandler(value =  AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException e){
        ApiResponse apiResponse = new ApiResponse();
        ErrorCode errorCode = e.getErrorCode();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return  ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlingAccessDeniedExeption(AccessDeniedException e){
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        return  ResponseEntity.status(errorCode.getStatusCode()).body(
                ApiResponse.builder().
                        code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }


    @ExceptionHandler(value =  MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException e){

        String enumKey = e.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        Map<String, Object> attibutes = null;
        try{
        errorCode = ErrorCode.valueOf(enumKey);
        var contrainViolation = e.getBindingResult()
                .getAllErrors().getFirst().unwrap(ConstraintViolation.class);
        attibutes = contrainViolation.getConstraintDescriptor().getAttributes();

        }catch (IllegalArgumentException exception){

        }
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(Objects.nonNull(attibutes)?
                mapAtribute(errorCode.getMessage(), attibutes)
                : errorCode.getMessage());

        return  ResponseEntity.badRequest().body(apiResponse);
    }

    private String mapAtribute(String message, Map<String,Object> attributes){
        String min = String.valueOf(attributes.get(MIN_ATTRIBUTE));
        return  message.replace("{"+ MIN_ATTRIBUTE + "}", min);

    }
}
