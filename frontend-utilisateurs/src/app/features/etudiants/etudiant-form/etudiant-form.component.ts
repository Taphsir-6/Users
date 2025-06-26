import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { EtudiantService } from '../../../core/services/etudiant.service';
import { Router, ActivatedRoute } from '@angular/router';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';

@Component({
  selector: 'app-etudiant-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatCheckboxModule],
  templateUrl: './etudiant-form.component.html',
  styleUrls: ['./etudiant-form.component.scss']
})
export class EtudiantFormComponent {
  form: FormGroup;
  loading = false;
  errorMsg = '';
  etudiantId?: number;

  constructor(
    private fb: FormBuilder,
    private etudiantService: EtudiantService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.form = this.fb.group({
      nom: ['', Validators.required],
      prenom: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      matricule: [''],
      photo: ['']
    });

    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.etudiantId = +id;
        this.loading = true;
        this.etudiantService.getById(this.etudiantId).subscribe({
          next: etu => {
            this.form.patchValue(etu);
            this.loading = false;
          },
          error: () => {
            this.errorMsg = "Impossible de charger l'étudiant.";
            this.loading = false;
          }
        });
      }
    });
  }

  submit() {
    if (this.form.invalid) return;
    this.loading = true;
    if (this.etudiantId) {
      // Edition
      this.etudiantService.update(this.etudiantId, this.form.value).subscribe({
        next: () => this.router.navigate(['/etudiants']),
        error: err => {
          this.errorMsg = 'Erreur lors de la modification';
          this.loading = false;
        }
      });
    } else {
      // Création
      this.etudiantService.create(this.form.value).subscribe({
        next: () => this.router.navigate(['/etudiants']),
        error: err => {
          this.errorMsg = 'Erreur lors de la création';
          this.loading = false;
        }
      });
    }
  }
}
