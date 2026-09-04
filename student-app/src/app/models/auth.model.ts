export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  userId: number;
  username: string;
  role: string;
}

export interface UserSession {
  userId: number;
  username: string;
  role: string;
  token: string;
}
