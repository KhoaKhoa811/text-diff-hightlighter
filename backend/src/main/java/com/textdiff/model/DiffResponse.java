package com.textdiff.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Response object containing the diff segments
 */
public class DiffResponse {

    @JsonProperty("differences")
    private List<DiffSegment> differences;

    public DiffResponse() {
    }

    public DiffResponse(List<DiffSegment> differences) {
        this.differences = differences;
    }

    public List<DiffSegment> getDifferences() {
        return differences;
    }

    public void setDifferences(List<DiffSegment> differences) {
        this.differences = differences;
    }

    @Override
    public String toString() {
        return "DiffResponse{" +
                "differences=" + differences +
                '}';
    }
}
