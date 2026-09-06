export interface CompanyResponse {
  companyId: number;
  name: string;
  email: string;
  username: string;
  address?: string;
  isActive?: boolean;
}

export interface TechnologyResponse {
  technologyId: number;
  name: string;
}

export interface InternshipResponse {
  internshipId: number;
  name: string;
  description: string;
  startDate: string;
  endDate: string;
  company: CompanyResponse;
  technologies: TechnologyResponse[];
  aiRecommended?: boolean;
  matchScore?: number;
}

export interface AiRecommendationResponse {
  internshipId: number;
  internshipName: string;
  description: string;
  companyName: string;
  start: string;
  technologies: TechnologyResponse[];
  matchPercentage: number;
  reason: string;
}
