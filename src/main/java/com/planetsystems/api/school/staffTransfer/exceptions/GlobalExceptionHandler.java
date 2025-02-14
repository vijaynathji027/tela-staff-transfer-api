package com.planetsystems.api.school.staffTransfer.exceptions;


import com.planetsystems.api.school.staffTransfer.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;


import java.rmi.ServerException;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler  {

    @ExceptionHandler({AlreadyExistsException.class})
    public ErrorResponseDTO handleAlreadyExistsException(AlreadyExistsException exception) {
//        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT , exception.getMessage());
        return new ErrorResponseDTO(exception.getMessage());
    }

    @ExceptionHandler({InvalidException.class})
    public ErrorResponseDTO handleInvalidException(InvalidException exception) {
        //ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN , exception.getMessage());

        return new ErrorResponseDTO(exception.getMessage());
    }

    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    public ErrorResponseDTO handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException exception) {
//        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE , exception.getMessage());
        return new ErrorResponseDTO(exception.getMessage());
    }

    @ExceptionHandler({DateTimeParseException.class})
    public ErrorResponseDTO handleDateTimeParseException(DateTimeParseException exception) {
//        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST , exception.getMessage());
        return new ErrorResponseDTO(exception.getMessage());
    }



    @ExceptionHandler({NotFoundException.class})
    public ErrorResponseDTO handleNotFoundException(NotFoundException exception) {
//        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND , exception.getMessage());
        return new ErrorResponseDTO(exception.getMessage());
    }

    @ExceptionHandler({MissingDataException.class})
    public ErrorResponseDTO handleMissingDataException(MissingDataException exception) {
//        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST , exception.getMessage());
        return new ErrorResponseDTO(exception.getMessage());
    }




    @ExceptionHandler({HttpClientErrorException.Forbidden.class})
    public ErrorResponseDTO handleForbiddenException(HttpClientErrorException.Forbidden exception) {
//        return ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN , exception.getMessage());
        return new ErrorResponseDTO(exception.getMessage());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ErrorResponseDTO handleForbiddenException(HttpMessageNotReadableException exception) {
        exception.printStackTrace();
//        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST , "Required request body is missing");
        return new ErrorResponseDTO(exception.getMessage());
    }





    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponseDTO handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

//        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST , "Wrong values");
//
//        problemDetail.setProperty("Errors" , errors);

//        return problemDetail;
        return new ErrorResponseDTO(errors.toString());
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ErrorResponseDTO handleHandlerMethodValidationException(HandlerMethodValidationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

//        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST , "validation error");
//
//        problemDetail.setProperty("Errors" , errors);
//
//        return problemDetail;

        return new ErrorResponseDTO(errors.toString());
    }

    @ExceptionHandler({RuntimeException.class , ServerException.class})
    public ErrorResponseDTO handleRuntimeException(RuntimeException exception) {
        exception.printStackTrace();

//        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR , exception.getMessage());
        return new ErrorResponseDTO(exception.getMessage());
    }




}
