import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { InternshipsComponent } from './components/internships/internships.component';
import { WorkJournalComponent } from './components/work-journal/work-journal.component';
import { CvComponent } from './components/cv/cv.component';
import { AuthGuard } from './guards/auth.guard';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'internships', component: InternshipsComponent, canActivate: [AuthGuard] },
  { path: 'work-journal', component: WorkJournalComponent, canActivate: [AuthGuard] },
  { path: 'cv', component: CvComponent, canActivate: [AuthGuard] },
  { path: '', redirectTo: '/internships', pathMatch: 'full' },
  { path: '**', redirectTo: '/internships' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
