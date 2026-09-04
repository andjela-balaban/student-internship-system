import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { InternshipLogResponse } from '../models/internship-log.model';

@Injectable({
  providedIn: 'root'
})
export class WorkJournalService {
  private apiUrl = environment.apiUrl + '/internship-logs';

  constructor(private http: HttpClient) {}

  getStudentLogs(studentId: number): Observable<InternshipLogResponse[]> {
    return this.http.get<InternshipLogResponse[]>(`${this.apiUrl}/student/${studentId}`);
  }

  appendLogContent(logId: number, newEntryContent: string): Observable<InternshipLogResponse> {
    return this.http.put<InternshipLogResponse>(`${this.apiUrl}/${logId}/student-content`, {
      content: newEntryContent
    });
  }
}
