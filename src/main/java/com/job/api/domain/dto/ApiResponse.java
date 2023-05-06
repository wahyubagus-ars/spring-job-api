package com.job.api.domain.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ApiResponse<T> {
    private String responseKey;
    private String responseMessage;
    private T data;
}
