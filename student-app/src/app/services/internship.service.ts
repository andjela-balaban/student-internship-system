import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { InternshipResponse, AiRecommendationResponse } from '../models/internship.model';

@Injectable({
  providedIn: 'root'
})
export class InternshipService {
  private apiUrl = environment.apiUrl + '/internships';

  constructor(private http: HttpClient) {}

  getAll(): Observable<InternshipResponse[]> {
    return this.http.get<InternshipResponse[]>(this.apiUrl);
  }

  getById(id: number): Observable<InternshipResponse> {
    return this.http.get<InternshipResponse>(`${this.apiUrl}/${id}`);
  }

  getAiRecommended(studentId: number): Observable<AiRecommendationResponse[]> {
    return this.http.get<AiRecommendationResponse[]>(`${this.apiUrl}/ai-recommendations/${studentId}`);
  }
}
