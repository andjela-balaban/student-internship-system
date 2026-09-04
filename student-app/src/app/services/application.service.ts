import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { ApplicationResponse } from '../models/application.model';

@Injectable({
  providedIn: 'root'
})
export class ApplicationService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getStudentApplications(studentId: number): Observable<ApplicationResponse[]> {
    return this.http.get<ApplicationResponse[]>(`${this.apiUrl}/students/${studentId}/applications`);
  }

  applyForInternship(internshipId: number, studentId: number): Observable<ApplicationResponse> {
    return this.http.post<ApplicationResponse>(`${this.apiUrl}/internships/${internshipId}/applications`, {
      studentId: studentId
    });
  }
}
