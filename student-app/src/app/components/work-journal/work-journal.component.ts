import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { WorkJournalService } from '../../services/work-journal.service';
import { AuthService } from '../../services/auth.service';
import { InternshipLogResponse } from '../../models/internship-log.model';
import { AddLogDialogComponent } from './add-log-dialog.component';

export interface CompanyLogGroup {
  companyName: string;
  logs: InternshipLogResponse[];
}

@Component({
  selector: 'app-work-journal',
  templateUrl: './work-journal.component.html',
  styleUrls: ['./work-journal.component.css']
})
export class WorkJournalComponent implements OnInit {
  logs: InternshipLogResponse[] = [];
  groupedLogs: CompanyLogGroup[] = [];
  filteredGroups: CompanyLogGroup[] = [];
  
  loading = true;
  searchQuery = '';

  constructor(
    private journalService: WorkJournalService,
    private authService: AuthService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.loadLogs();
  }

  loadLogs(): void {
    const studentId = this.authService.getStudentId();
    if (!studentId) return;

    this.loading = true;
    this.journalService.getStudentLogs(studentId).subscribe({
      next: (data) => {
        this.logs = data;
        this.groupLogsByCompany();
        this.loading = false;
      },
      error: () => {
        this.loading = false;
        this.snackBar.open('Failed to load work journal logs.', 'Close', { duration: 3000 });
      }
    });
  }

  groupLogsByCompany(): void {
    const map = new Map<string, InternshipLogResponse[]>();

    this.logs.forEach(log => {
      const compName = (log.internship && log.internship.company && log.internship.company.name) 
        ? log.internship.company.name 
        : 'Other Companies';
      
      if (!map.has(compName)) {
        map.set(compName, []);
      }
      map.get(compName)!.push(log);
    });

    this.groupedLogs = Array.from(map.entries()).map(([companyName, logs]) => ({
      companyName,
      logs
    }));

    this.applySearchFilter();
  }

  applySearchFilter(): void {
    if (!this.searchQuery || !this.searchQuery.trim()) {
      this.filteredGroups = [...this.groupedLogs];
      return;
    }

    const query = this.searchQuery.toLowerCase().trim();
    this.filteredGroups = this.groupedLogs.map(group => {
      const matchingLogs = group.logs.filter(log => {
        const titleMatch = log.internship && log.internship.name && log.internship.name.toLowerCase().includes(query);
        const contentMatch = log.content && log.content.toLowerCase().includes(query);
        const compCommentMatch = log.companyComment && log.companyComment.toLowerCase().includes(query);
        const facCommentMatch = log.facultyComment && log.facultyComment.toLowerCase().includes(query);
        return titleMatch || contentMatch || compCommentMatch || facCommentMatch;
      });

      return {
        companyName: group.companyName,
        logs: matchingLogs
      };
    }).filter(group => group.logs.length > 0 || group.companyName.toLowerCase().includes(query));
  }

  openAddLogDialog(log: InternshipLogResponse): void {
    const dialogRef = this.dialog.open(AddLogDialogComponent, {
      width: '550px',
      data: {
        logId: log.logId,
        internshipName: log.internship ? log.internship.name : 'Internship',
        companyName: (log.internship && log.internship.company) ? log.internship.company.name : ''
      }
    });

    dialogRef.afterClosed().subscribe(newContent => {
      if (newContent) {
        this.journalService.appendLogContent(log.logId, newContent).subscribe({
          next: (updatedLog) => {
            this.snackBar.open('Work log entry added successfully!', 'Close', {
              duration: 3500,
              panelClass: ['bg-success', 'text-white']
            });
            this.loadLogs();
          },
          error: () => {
            this.snackBar.open('Failed to save log entry. Please try again.', 'Close', { duration: 3500 });
          }
        });
      }
    });
  }

  parseEntries(content: string): string[] {
    if (!content) return [];
    return content.split('\n').filter(line => line && line.trim().length > 0);
  }
}
