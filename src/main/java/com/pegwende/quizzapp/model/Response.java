package com.pegwende.quizzapp.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Response {
    private int questionId;
    private String answer;
}
