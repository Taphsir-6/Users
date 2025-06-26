import { Routes } from '@angular/router';
import { VacataireListComponent } from './features/vacataires/vacataire-list/vacataire-list.component';
import { VacataireFormComponent } from './features/vacataires/vacataire-form/vacataire-form.component';
import { EnseignantListComponent } from './features/enseignants/enseignant-list/enseignant-list.component';
import { EnseignantFormComponent } from './features/enseignants/enseignant-form/enseignant-form.component';
import { EtudiantListComponent } from './features/etudiants/etudiant-list/etudiant-list.component';
import { EtudiantFormComponent } from './features/etudiants/etudiant-form/etudiant-form.component';
import { HomeComponent } from './features/home/home.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'vacataires', component: VacataireListComponent },
  { path: 'vacataires/new', component: VacataireFormComponent },
  { path: 'vacataires/:id/edit', component: VacataireFormComponent },
  { path: 'enseignants', component: EnseignantListComponent },
  { path: 'enseignants/new', component: EnseignantFormComponent },
  { path: 'enseignants/:id/edit', component: EnseignantFormComponent },
  { path: 'etudiants', component: EtudiantListComponent },
  { path: 'etudiants/new', component: EtudiantFormComponent },
  { path: 'etudiants/:id/edit', component: EtudiantFormComponent },
  { path: '**', redirectTo: '' }
];
