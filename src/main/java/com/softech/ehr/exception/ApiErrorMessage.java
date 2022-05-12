package com.softech.ehr.exception;

import com.softech.ehr.exception.validators.Violation;

import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiErrorMessage {
    private int statusCode;
    private Date timestamp;
    private String message;
    private String description;
    private List<Violation> causes;

}
