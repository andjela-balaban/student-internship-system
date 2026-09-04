import { TechnologyResponse } from './internship.model';

export interface EducationItem {
  institution: string;
  degree: string;
  startDate?: string;
  endDate?: string;
}

export interface ExperienceItem {
  company: string;
  position: string;
  startDate?: string;
  endDate?: string;
}

export interface CvResponse {
  cvId?: number;
  interests?: string;
  address?: string;
  education?: EducationItem[];
  experience?: ExperienceItem[];
  technologies?: TechnologyResponse[];
}

export interface CvRequest {
  interests: string;
  address: string;
  education?: EducationItem[];
  experience?: ExperienceItem[];
  technologyIds: number[];
}
