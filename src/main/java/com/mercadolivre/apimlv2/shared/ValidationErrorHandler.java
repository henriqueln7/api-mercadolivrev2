package com.mercadolivre.apimlv2.shared;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ValidationErrorHandler {
    private final MessageSource messageSource;

    public ValidationErrorHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidationErrorsOutputDTO handleValidationError(BindException exception) {
        List<ObjectError> globalErrors = exception.getBindingResult().getGlobalErrors();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        return buildValidationErrorsOutputDTO(globalErrors, fieldErrors);
    }

    private ValidationErrorsOutputDTO buildValidationErrorsOutputDTO(List<ObjectError> globalErrors, List<FieldError> fieldErrors) {
        ValidationErrorsOutputDTO validationErrorsOutputDTO = new ValidationErrorsOutputDTO();

        globalErrors.forEach(error -> validationErrorsOutputDTO.addError(getErrorMessage(error)));

        fieldErrors.forEach(error -> {
            String message = getErrorMessage(error);
            validationErrorsOutputDTO.addFieldError(error.getField(), message);
        });

        return validationErrorsOutputDTO;
    }

    private String getErrorMessage(ObjectError error) {
        return messageSource.getMessage(error, LocaleContextHolder.getLocale());
    }
}
