package com.trimble.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetatils {
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private String details;

}
