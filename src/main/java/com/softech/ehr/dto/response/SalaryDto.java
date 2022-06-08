package com.softech.ehr.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.softech.ehr.enums.SalaryType;

import java.math.BigDecimal;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SalaryDto {

    private BigDecimal amount;
    private SalaryType type;
    private String remark;

}
