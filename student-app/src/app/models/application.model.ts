import { InternshipResponse } from './internship.model';

export interface StudentResponse {
  studentId: number;
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  studentIndex: string;
  dateOfBirth?: string;
  phoneNumber?: string;
}

export interface ApplicationResponse {
  applicationId: number;
  status: 'PENDING' | 'ACCEPTED' | 'REJECTED';
  student: StudentResponse;
  internship: InternshipResponse;
}
