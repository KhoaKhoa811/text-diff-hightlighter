import { Component, OnInit } from '@angular/core';
import { DiffService, DiffSegment } from '../../services/diff.service';

@Component({
  selector: 'app-diff',
  templateUrl: './diff.component.html',
  styleUrls: ['./diff.component.css']
})
export class DiffComponent implements OnInit {
  text1: string = '';
  text2: string = '';
  diffs: DiffSegment[] = [];
  loading: boolean = false;
  error: string | null = null;
  hasCompared: boolean = false;

  constructor(private diffService: DiffService) { }

  ngOnInit(): void {
    // Initialize with empty inputs
  }

  /**
   * Triggers the comparison of two texts
   */
  onCompare(): void {
    this.error = null;
    
    // Validate inputs
    if (!this.text1.trim() && !this.text2.trim()) {
      this.error = 'Please enter at least one text to compare.';
      return;
    }

    this.loading = true;
    this.diffs = [];
    this.hasCompared = false;

    this.diffService.compareDiffs(this.text1, this.text2).subscribe({
      next: (response) => {
        this.diffs = response.differences || [];
        this.hasCompared = true;
        this.loading = false;
      },
      error: (err) => {
        this.error = err.message || 'An error occurred while comparing texts.';
        this.loading = false;
        this.hasCompared = false;
      }
    });
  }

  /**
   * Clears all inputs and results
   */
  onClear(): void {
    this.text1 = '';
    this.text2 = '';
    this.diffs = [];
    this.error = null;
    this.hasCompared = false;
  }

  /**
   * Gets the CSS class for a diff segment based on its type
   */
  getDiffClass(segment: DiffSegment): string {
    switch (segment.type) {
      case 'added':
        return 'diff-insert';
      case 'removed':
        return 'diff-delete';
      case 'unchanged':
      default:
        return 'diff-equal';
    }
  }
}
