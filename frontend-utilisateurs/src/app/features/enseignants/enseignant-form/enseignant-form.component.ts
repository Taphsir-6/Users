import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { EnseignantService } from '../../../core/services/enseignant.service';
import { Router, ActivatedRoute } from '@angular/router';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';

@Component({
  selector: 'app-enseignant-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatCheckboxModule],
  templateUrl: './enseignant-form.component.html',
  styleUrls: ['./enseignant-form.component.scss']
})
export class EnseignantFormComponent {
  form: FormGroup;
  loading = false;
  errorMsg = '';
  enseignantId?: number;

  constructor(
    private fb: FormBuilder,
    private enseignantService: EnseignantService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.form = this.fb.group({
      nom: ['', Validators.required],
      prenom: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      telephone: ['', Validators.required],
      matricule: [''],
      grade: [''],
      actif: [true]
    });

    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.enseignantId = +id;
        this.loading = true;
        this.enseignantService.getById(this.enseignantId).subscribe({
          next: ens => {
            this.form.patchValue(ens);
            this.loading = false;
          },
          error: () => {
            this.errorMsg = "Impossible de charger l'enseignant.";
            this.loading = false;
          }
        });
      }
    });
  }

  submit() {
    if (this.form.invalid) return;
    this.loading = true;
    if (this.enseignantId) {
      // Edition
      this.enseignantService.update(this.enseignantId, this.form.value).subscribe({
        next: () => this.router.navigate(['/enseignants']),
        error: err => {
          this.errorMsg = 'Erreur lors de la modification';
          this.loading = false;
        }
      });
    } else {
      // Création
      this.enseignantService.create(this.form.value).subscribe({
        next: () => this.router.navigate(['/enseignants']),
        error: err => {
          this.errorMsg = 'Erreur lors de la création';
          this.loading = false;
        }
      });
    }
  }
}
