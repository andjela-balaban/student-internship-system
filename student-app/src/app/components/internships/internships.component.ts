import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { InternshipService } from '../../services/internship.service';
import { ApplicationService } from '../../services/application.service';
import { AuthService } from '../../services/auth.service';
import { InternshipResponse } from '../../models/internship.model';
import { ApplicationResponse } from '../../models/application.model';

@Component({
  selector: 'app-internships',
  templateUrl: './internships.component.html',
  styleUrls: ['./internships.component.css']
})
export class InternshipsComponent implements OnInit {
  displayedColumns: string[] = ['name', 'company', 'period', 'technologies', 'status', 'action'];
  dataSource = new MatTableDataSource<InternshipResponse>([]);
  
  aiRecommendations: InternshipResponse[] = [];
  studentApplications: Map<number, ApplicationResponse> = new Map();
  
  loading = true;
  applyingMap: Map<number, boolean> = new Map();
  searchQuery = '';

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    private internshipService: InternshipService,
    private applicationService: ApplicationService,
    private authService: AuthService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    const studentId = this.authService.getStudentId();
    if (!studentId) return;

    this.loading = true;

    // Load Applications first
    this.applicationService.getStudentApplications(studentId).subscribe({
      next: (apps) => {
        this.studentApplications.clear();
        apps.forEach(app => {
          if (app.internship && app.internship.internshipId) {
            this.studentApplications.set(app.internship.internshipId, app);
          }
        });

        // Load All Internships
        this.internshipService.getAll().subscribe({
          next: (internships) => {
            this.dataSource.data = internships;
            setTimeout(() => {
              this.dataSource.paginator = this.paginator;
            });
            this.setupCustomFilter();
            this.loading = false;
          },
          error: () => {
            this.loading = false;
            this.snackBar.open('Failed to load internships.', 'Close', { duration: 3000 });
          }
        });

        // Load AI Recommendations
        this.internshipService.getAiRecommended(studentId).subscribe({
          next: (recs) => {
            this.aiRecommendations = recs;
          },
          error: () => {
            // Fallback if AI recommendations endpoint is empty
            this.aiRecommendations = [];
          }
        });
      },
      error: () => {
        this.loading = false;
        this.snackBar.open('Failed to load application data.', 'Close', { duration: 3000 });
      }
    });
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  setupCustomFilter(): void {
    this.dataSource.filterPredicate = (data: InternshipResponse, filter: string) => {
      const searchStr = filter.toLowerCase();
      const nameMatch = data.name ? data.name.toLowerCase().includes(searchStr) : false;
      const companyMatch = data.company && data.company.name ? data.company.name.toLowerCase().includes(searchStr) : false;
      const techMatch = data.technologies ? data.technologies.some(t => t.name.toLowerCase().includes(searchStr)) : false;
      return nameMatch || companyMatch || techMatch;
    };
  }

  getApplication(internshipId: number): ApplicationResponse | undefined {
    return this.studentApplications.get(internshipId);
  }

  hasApplied(internshipId: number): boolean {
    return this.studentApplications.has(internshipId);
  }

  applyNow(internship: InternshipResponse): void {
    const studentId = this.authService.getStudentId();
    if (!studentId) return;

    this.applyingMap.set(internship.internshipId, true);

    this.applicationService.applyForInternship(internship.internshipId, studentId).subscribe({
      next: (app) => {
        this.applyingMap.set(internship.internshipId, false);
        this.studentApplications.set(internship.internshipId, app);
        this.snackBar.open(`Successfully applied to "${internship.name}"!`, 'Close', {
          duration: 4000,
          panelClass: ['bg-success', 'text-white']
        });
      },
      error: (err) => {
        this.applyingMap.set(internship.internshipId, false);
        const msg = err.error?.message || 'Failed to submit application. Please try again.';
        this.snackBar.open(msg, 'Close', { duration: 4000 });
      }
    });
  }
}
