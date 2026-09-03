import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { environment } from '../../environments/environment';
import { LoginRequest, LoginResponse, UserSession } from '../models/auth.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = environment.apiUrl + '/auth';
  private currentUserSubject = new BehaviorSubject<UserSession | null>(this.getUserFromStorage());
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient) {}

  public get currentUserValue(): UserSession | null {
    return this.currentUserSubject.value;
  }

  login(request: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, request).pipe(
      tap(res => {
        if (res.role !== 'ROLE_STUDENT') {
          throw new Error('Access denied: Account is not a Student account.');
        }

        const session: UserSession = {
          userId: res.userId,
          username: res.username,
          role: res.role,
          token: res.token
        };

        localStorage.setItem('student_session', JSON.stringify(session));
        this.currentUserSubject.next(session);
      })
    );
  }

  logout(): void {
    localStorage.removeItem('student_session');
    this.currentUserSubject.next(null);
  }

  isLoggedIn(): boolean {
    const session = this.currentUserValue;
    return !!session && !!session.token;
  }

  getToken(): string | null {
    return this.currentUserValue?.token || null;
  }

  getStudentId(): number | null {
    return this.currentUserValue?.userId || null;
  }

  private getUserFromStorage(): UserSession | null {
    const raw = localStorage.getItem('student_session');
    if (!raw) return null;
    try {
      return JSON.parse(raw);
    } catch {
      return null;
    }
  }
}
