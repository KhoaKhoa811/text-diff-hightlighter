package com.textdiff.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a single segment in the diff output
 */
public class DiffSegment {

    @JsonProperty("type")
    private String type;

    @JsonProperty("text")
    private String text;

    public DiffSegment() {
    }

    public DiffSegment(String type, String text) {
        this.type = type;
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "DiffSegment{" +
                "type='" + type + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
