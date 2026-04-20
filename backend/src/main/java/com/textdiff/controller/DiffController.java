package com.textdiff.controller;

import com.textdiff.model.DiffRequest;
import com.textdiff.model.DiffResponse;
import com.textdiff.service.DiffService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for text difference API endpoints
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DiffController {

    @Autowired
    private DiffService diffService;

    /**
     * POST endpoint to compute differences between two texts
     *
     * @param request DiffRequest containing text1 and text2
     * @return DiffResponse containing the diff segments
     */
    @PostMapping("/diff")
    public ResponseEntity<DiffResponse> computeDiff(@Valid @RequestBody DiffRequest request) {
        try {
            DiffResponse response = diffService.computeDifference(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Health check endpoint
     *
     * @return Simple confirmation message
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Text Diff Highlighter API is running");
    }
}
