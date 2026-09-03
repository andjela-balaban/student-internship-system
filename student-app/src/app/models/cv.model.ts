import { TechnologyResponse } from './internship.model';

export interface CvResponse {
  cvId?: number;
  interests?: string;
  address?: string;
  technologies?: TechnologyResponse[];
}

export interface CvRequest {
  interests: string;
  address: string;
  technologyIds: number[];
}
