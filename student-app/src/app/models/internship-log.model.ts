import { InternshipResponse } from './internship.model';

export interface InternshipLogResponse {
  logId: number;
  content: string;
  grade?: number;
  companyComment?: string;
  facultyComment?: string;
  internship: InternshipResponse;
}

export interface StudentLogUpdate {
  content: string;
}
