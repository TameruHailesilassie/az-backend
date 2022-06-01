package com.softech.ehr.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Data
@Getter
@Setter
@Accessors(fluent = true)
@AllArgsConstructor
public class MetaData {
    private int page;
    private int per_page;
    private long page_count;
    private int total_count;
}
