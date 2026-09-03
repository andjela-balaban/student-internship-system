import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { CvResponse, CvRequest } from '../models/cv.model';
import { TechnologyResponse } from '../models/internship.model';

@Injectable({
  providedIn: 'root'
})
export class CvService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getCv(studentId: number): Observable<CvResponse> {
    return this.http.get<CvResponse>(`${this.apiUrl}/cvs/student/${studentId}`);
  }

  updateCvMultipart(studentId: number, cvRequest: CvRequest, imageFile?: File): Observable<CvResponse> {
    const formData = new FormData();
    const cvBlob = new Blob([JSON.stringify(cvRequest)], { type: 'application/json' });
    formData.append('cv', cvBlob);
    
    if (imageFile) {
      formData.append('image', imageFile, imageFile.name);
    }

    return this.http.put<CvResponse>(`${this.apiUrl}/cvs/student/${studentId}`, formData);
  }

  getCvImageUrl(studentId: number): string {
    return `${this.apiUrl}/cvs/student/${studentId}/image?t=${new Date().getTime()}`;
  }

  downloadPdf(studentId: number): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/students/${studentId}/cv/pdf`, {
      responseType: 'blob'
    });
  }

  getAllTechnologies(): Observable<TechnologyResponse[]> {
    return this.http.get<TechnologyResponse[]>(`${this.apiUrl}/technologies`);
  }
}
