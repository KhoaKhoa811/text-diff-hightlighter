package com.textdiff.model;

import jakarta.validation.constraints.NotBlank;

/**
 * Request object for the diff endpoint
 */
public class DiffRequest {

    @NotBlank(message = "text1 cannot be blank")
    private String text1;

    @NotBlank(message = "text2 cannot be blank")
    private String text2;

    public DiffRequest() {
    }

    public DiffRequest(String text1, String text2) {
        this.text1 = text1;
        this.text2 = text2;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    @Override
    public String toString() {
        return "DiffRequest{" +
                "text1='" + text1 + '\'' +
                ", text2='" + text2 + '\'' +
                '}';
    }
}
