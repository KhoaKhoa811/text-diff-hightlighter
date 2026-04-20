import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../../environments/environment';

export interface DiffRequest {
  text1: string;
  text2: string;
}

export interface DiffResponse {
  differences: DiffSegment[];
  message?: string;
}

export interface DiffSegment {
  type: 'unchanged' | 'added' | 'removed';
  text: string;
}

@Injectable({
  providedIn: 'root'
})
export class DiffService {
  private apiUrl = `${environment.apiUrl}/api/diff`;

  constructor(private http: HttpClient) { }

  /**
   * Sends two texts to the backend for comparison
   * @param text1 - First text to compare
   * @param text2 - Second text to compare
   * @returns Observable of DiffResponse with diff segments
   */
  compareDiffs(text1: string, text2: string): Observable<DiffResponse> {
    const payload: DiffRequest = {
      text1: text1 || '',
      text2: text2 || ''
    };

    return this.http.post<DiffResponse>(this.apiUrl, payload).pipe(
      catchError(error => {
        console.error('Error calling diff API:', error);
        return throwError(() => new Error('Failed to compare texts. Please ensure the backend is running.'));
      })
    );
  }
}
