package com.textdiff.service;

import com.textdiff.model.DiffRequest;
import com.textdiff.model.DiffResponse;
import com.textdiff.model.DiffSegment;
import com.textdiff.util.DiffAlgorithm;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for handling diff operations
 */
@Service
public class DiffService {

    /**
     * Computes the difference between two texts
     *
     * @param request DiffRequest containing text1 and text2
     * @return DiffResponse containing the list of diff segments
     */
    public DiffResponse computeDifference(DiffRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("DiffRequest cannot be null");
        }

        String text1 = request.getText1() != null ? request.getText1() : "";
        String text2 = request.getText2() != null ? request.getText2() : "";

        // Compute the diff using the algorithm
        List<DiffSegment> differences = DiffAlgorithm.computeDiff(text1, text2);

        return new DiffResponse(differences);
    }
}
