package com.mercadolivre.apimlv2.shared.errors;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorsOutputDTO {
    private final List<FieldErrorOutputDTO> fieldErrors = new ArrayList<>();
    private final List<String> globalErrorMessages = new ArrayList<>();

    public void addError(String message) {
        globalErrorMessages.add(message);
    }

    public void addFieldError(String field, String message) {
        FieldErrorOutputDTO fieldErrorOutputDTO = new FieldErrorOutputDTO(field, message);
        fieldErrors.add(fieldErrorOutputDTO);
    }

    public List<String> getGlobalErrorMessages() {
        return globalErrorMessages;
    }

    public List<FieldErrorOutputDTO> getFieldErrors() {
        return fieldErrors;
    }

    public int getNumberOfErrors() {
        return fieldErrors.size() + globalErrorMessages.size();
    }
}
