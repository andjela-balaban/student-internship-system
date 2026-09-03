import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

export interface AddLogDialogData {
  logId: number;
  internshipName: string;
  companyName: string;
}

@Component({
  selector: 'app-add-log-dialog',
  template: `
    <div class="p-3">
      <h5 class="fw-bold text-dark mb-2" style="font-size: 1.2rem;">
        <i class="bi bi-journal-plus me-2 text-teal"></i>
        Add Work Log Entry
      </h5>

      <p class="text-muted small mb-3">
        Internship: <strong>{{ data.internshipName }}</strong> ({{ data.companyName }})
      </p>

      <div class="mb-3">
        <label class="form-label text-secondary fw-bold small">New Work Log Description / Entry</label>
        <textarea [(ngModel)]="newEntryContent" 
                  class="form-control custom-textarea" 
                  rows="4" 
                  placeholder="Describe your tasks, completed activities, and key learnings for this period..."></textarea>
      </div>

      <div class="d-flex justify-content-end gap-2 mt-4">
        <button class="btn btn-outline-secondary btn-sm rounded-3 px-3" (click)="onCancel()">Cancel</button>
        <button class="btn btn-teal btn-sm rounded-3 px-3 fw-semibold" 
                [disabled]="!newEntryContent || !newEntryContent.trim()" 
                (click)="onSave()">
          Submit Entry
        </button>
      </div>
    </div>
  `,
  styles: [`
    .text-teal { color: #37A5B0; }
    .btn-teal { background-color: #37A5B0; color: #ffffff; border: none; }
    .btn-teal:hover { background-color: #2b8892; }

    .custom-textarea {
      background-color: #ffffff !important;
      border: 1px solid #e2e8f0 !important;
      border-radius: 8px !important;
      transition: border-color 0.2s ease, box-shadow 0.2s ease;
    }

    .custom-textarea:focus {
      border-color: #37A5B0 !important;
      box-shadow: 0 0 0 0.2rem rgba(55, 165, 176, 0.2) !important;
      outline: none !important;
    }
  `]
})
export class AddLogDialogComponent {
  newEntryContent = '';

  constructor(
    public dialogRef: MatDialogRef<AddLogDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: AddLogDialogData
  ) { }

  onCancel(): void {
    this.dialogRef.close();
  }

  onSave(): void {
    if (this.newEntryContent && this.newEntryContent.trim()) {
      this.dialogRef.close(this.newEntryContent.trim());
    }
  }
}