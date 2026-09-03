import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CvService } from '../../services/cv.service';
import { AuthService } from '../../services/auth.service';
import { CvResponse } from '../../models/cv.model';
import { TechnologyResponse } from '../../models/internship.model';

@Component({
  selector: 'app-cv',
  templateUrl: './cv.component.html',
  styleUrls: ['./cv.component.css']
})
export class CvComponent implements OnInit {
  cv: CvResponse | null = null;
  allTechnologies: TechnologyResponse[] = [];

  editMode = false;
  loading = true;
  saving = false;
  cvForm!: FormGroup;

  selectedFile: File | null = null;
  imagePreviewUrl: string | null = null;
  hasImageError = false;

  constructor(
    private cvService: CvService,
    private authService: AuthService,
    private fb: FormBuilder,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.initForm();
    this.loadData();
  }

  initForm(): void {
    this.cvForm = this.fb.group({
      interests: ['', [Validators.required]],
      address: ['', [Validators.required]],
      technologyIds: [[]]
    });
  }

  loadData(): void {
    const studentId = this.authService.getStudentId();
    if (!studentId) return;

    this.loading = true;
    this.hasImageError = false;

    // Set avatar image URL
    this.imagePreviewUrl = this.cvService.getCvImageUrl(studentId);

    // Load available technologies
    this.cvService.getAllTechnologies().subscribe({
      next: (techs) => {
        this.allTechnologies = techs;
      }
    });

    // Load Student CV
    this.cvService.getCv(studentId).subscribe({
      next: (cvData) => {
        this.cv = cvData;
        this.populateForm(cvData);
        this.loading = false;
      },
      error: () => {
        this.cv = null;
        this.loading = false;
      }
    });
  }

  populateForm(cvData: CvResponse): void {
    const selectedTechIds = cvData.technologies ? cvData.technologies.map(t => t.technologyId) : [];
    this.cvForm.patchValue({
      interests: cvData.interests || '',
      address: cvData.address || '',
      technologyIds: selectedTechIds
    });
  }

  onImageSelected(event: Event): void {
    const fileInput = event.target as HTMLInputElement;
    if (fileInput.files && fileInput.files[0]) {
      this.selectedFile = fileInput.files[0];

      // Preview selected image
      const reader = new FileReader();
      reader.onload = () => {
        this.imagePreviewUrl = reader.result as string;
        this.hasImageError = false;
      };
      reader.readAsDataURL(this.selectedFile);
    }
  }

  onImageError(): void {
    this.hasImageError = true;
  }

  toggleEditMode(): void {
    this.editMode = !this.editMode;
    if (!this.editMode && this.cv) {
      this.populateForm(this.cv);
      this.selectedFile = null;
      const studentId = this.authService.getStudentId();
      if (studentId) {
        this.imagePreviewUrl = this.cvService.getCvImageUrl(studentId);
      }
    }
  }

  onSave(): void {
    if (this.cvForm.invalid) return;

    const studentId = this.authService.getStudentId();
    if (!studentId) return;

    this.saving = true;

    this.cvService.updateCvMultipart(studentId, this.cvForm.value, this.selectedFile || undefined).subscribe({
      next: (updatedCv) => {
        this.cv = updatedCv;
        this.saving = false;
        this.editMode = false;
        this.selectedFile = null;
        this.imagePreviewUrl = this.cvService.getCvImageUrl(studentId);
        this.snackBar.open('CV and profile picture updated successfully!', 'Close', {
          duration: 3500,
          panelClass: ['bg-success', 'text-white']
        });
      },
      error: () => {
        this.saving = false;
        this.snackBar.open('Failed to update CV. Please try again.', 'Close', { duration: 3500 });
      }
    });
  }

  downloadPdf(): void {
    const studentId = this.authService.getStudentId();
    if (!studentId) return;

    this.cvService.downloadPdf(studentId).subscribe({
      next: (blob) => {
        const fileUrl = URL.createObjectURL(blob);
        window.open(fileUrl, '_blank');
      },
      error: () => {
        this.snackBar.open('Failed to generate PDF CV.', 'Close', { duration: 3000 });
      }
    });
  }
}
