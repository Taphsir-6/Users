import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { VacataireService } from '../../../core/services/vacataire.service';
import { Router, ActivatedRoute } from '@angular/router';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';

@Component({
  selector: 'app-vacataire-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCheckboxModule
  ],
  templateUrl: './vacataire-form.component.html',
  styleUrls: ['./vacataire-form.component.scss']
})
export class VacataireFormComponent implements OnInit {
  form: FormGroup;
  loading = false;
  errorMsg = '';
  vacataireId?: number;

  constructor(
    private fb: FormBuilder,
    private vacataireService: VacataireService,
    private router: Router,
    public route: ActivatedRoute
  ) {
    this.form = this.fb.group({
      nom: ['', Validators.required],
      prenom: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      telephone: ['', [
        Validators.required,
        Validators.pattern(/^((\+221|\+33|0)[1-9](\d{7,8}|( \d{2}){4})|([7-9]\d{8})|(\+2217\d{7})|(\+33\d{9})|(\+33 ?[1-9]( ?\d{2}){4})|(\+221 ?7\d{7})|(0[1-9](\d{8}|( \d{2}){4}))|(0 ?[1-9]( ?\d{2}){4}))$/)
      ]],
      matricule: [''],
      specialite: [''],
      actif: [true]
    });
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.vacataireId = +id;
      this.loading = true;
      this.vacataireService.getById(+id).subscribe({
        next: vac => {
          this.form.patchValue({ ...vac, actif: !!vac.actif });
          this.loading = false;
        },
        error: err => {
          this.errorMsg = "Vacataire introuvable ou supprimé.";
          this.loading = false;
          setTimeout(() => this.router.navigate(['/vacataires']), 2500);
        }
      });
    }
  }

  submit() {
    if (this.form.invalid) return;
    this.loading = true;
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      // Edition : update
      this.vacataireService.update(+id, this.form.value).subscribe({
        next: () => window.location.href = '/vacataires',
        error: err => {
          this.errorMsg = 'Erreur lors de la modification';
          this.loading = false;
        }
      });
    } else {
      // Création : create
      this.vacataireService.create(this.form.value).subscribe({
        next: () => window.location.href = '/vacataires',
        error: err => {
          this.errorMsg = 'Erreur lors de la création';
          this.loading = false;
        }
      });
    }
  }
}
