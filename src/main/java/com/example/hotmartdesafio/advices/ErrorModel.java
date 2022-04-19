package com.example.hotmartdesafio.advices;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorModel {
    public String message;
    public String reason;
    public String code;
}
