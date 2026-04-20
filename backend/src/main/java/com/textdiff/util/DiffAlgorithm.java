package com.textdiff.util;

import com.textdiff.model.DiffSegment;
import java.util.*;

/**
 * Utility class implementing character-level diff algorithm using Myers algorithm approach
 * with a simplified implementation for practical use
 */
public class DiffAlgorithm {

    /**
     * Performs a character-level diff between two texts
     * Returns a list of DiffSegment objects with type and text content
     *
     * @param text1 Original text
     * @param text2 Modified text
     * @return List of DiffSegment objects
     */
    public static List<DiffSegment> computeDiff(String text1, String text2) {
        if (text1 == null) {
            text1 = "";
        }
        if (text2 == null) {
            text2 = "";
        }

        List<DiffSegment> result = new ArrayList<>();

        // Handle edge cases
        if (text1.isEmpty() && text2.isEmpty()) {
            return result;
        }

        if (text1.isEmpty()) {
            result.add(new DiffSegment("added", text2));
            return result;
        }

        if (text2.isEmpty()) {
            result.add(new DiffSegment("removed", text1));
            return result;
        }

        // Use Longest Common Subsequence (LCS) approach for diff
        int[][] dp = computeLCS(text1, text2);
        buildDiffSegments(text1, text2, dp, result);

        return result;
    }

    /**
     * Computes the Longest Common Subsequence matrix
     */
    private static int[][] computeLCS(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp;
    }

    /**
     * Builds diff segments from LCS matrix by backtracking
     */
    private static void buildDiffSegments(String text1, String text2, int[][] dp, List<DiffSegment> result) {
        int i = text1.length();
        int j = text2.length();

        // Use a stack to maintain order when backtracking
        Stack<DiffSegment> stack = new Stack<>();

        while (i > 0 || j > 0) {
            if (i > 0 && j > 0 && text1.charAt(i - 1) == text2.charAt(j - 1)) {
                // Characters match - unchanged
                stack.push(new DiffSegment("unchanged", String.valueOf(text1.charAt(i - 1))));
                i--;
                j--;
            } else if (j > 0 && (i == 0 || dp[i][j - 1] >= dp[i - 1][j])) {
                // Character in text2 but not in text1 - added
                stack.push(new DiffSegment("added", String.valueOf(text2.charAt(j - 1))));
                j--;
            } else {
                // Character in text1 but not in text2 - removed
                stack.push(new DiffSegment("removed", String.valueOf(text1.charAt(i - 1))));
                i--;
            }
        }

        // Pop from stack and merge consecutive segments of same type
        List<DiffSegment> tempSegments = new ArrayList<>();
        while (!stack.isEmpty()) {
            tempSegments.add(stack.pop());
        }

        // Merge consecutive segments with same type
        mergeConsecutiveSegments(tempSegments, result);
    }

    /**
     * Merges consecutive segments of the same type to reduce fragmentation
     */
    private static void mergeConsecutiveSegments(List<DiffSegment> tempSegments, List<DiffSegment> result) {
        if (tempSegments.isEmpty()) {
            return;
        }

        DiffSegment current = new DiffSegment(tempSegments.get(0).getType(), tempSegments.get(0).getText());

        for (int i = 1; i < tempSegments.size(); i++) {
            DiffSegment segment = tempSegments.get(i);
            if (segment.getType().equals(current.getType())) {
                // Merge with current segment
                current.setText(current.getText() + segment.getText());
            } else {
                // Different type, add current and start new
                result.add(current);
                current = new DiffSegment(segment.getType(), segment.getText());
            }
        }

        // Don't forget to add the last segment
        result.add(current);
    }
}
