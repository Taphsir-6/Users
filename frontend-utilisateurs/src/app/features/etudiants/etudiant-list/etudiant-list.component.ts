import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EtudiantService } from '../../../core/services/etudiant.service';
import { Etudiant } from '../etudiant.model';
import { Router, RouterModule } from '@angular/router';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { FeedbackService } from '../../../core/services/feedback.service';
import { LoadingService } from '../../../core/services/loading.service';
import { ConfirmDialogComponent } from '../../../shared/components/confirm-dialog/confirm-dialog.component';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

@Component({
  selector: 'app-etudiant-list',
  standalone: true,
  imports: [CommonModule, RouterModule, MatTableModule, MatButtonModule, MatIconModule, MatPaginatorModule, MatSortModule, MatFormFieldModule, MatInputModule, FormsModule],
  templateUrl: './etudiant-list.component.html',
  styleUrls: ['./etudiant-list.component.scss']
})
export class EtudiantListComponent implements OnInit, AfterViewInit {
  dataSource = new MatTableDataSource<Etudiant>([]);
  displayedColumns: string[] = ['nom', 'prenom', 'email', 'matricule', 'photo', 'actions'];
  searchTerm: string = '';

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private etudiantService: EtudiantService,
    private router: Router,
    private dialog: MatDialog,
    private feedback: FeedbackService,
    private loading: LoadingService
  ) {}

  ngOnInit(): void {
    setTimeout(() => this.loadEtudiants(), 0);
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }

  loadEtudiants(): void {
    this.loading.show();
    this.etudiantService.getAll().subscribe({
      next: ets => {
        this.dataSource.data = ets;
        this.loading.hide();
      },
      error: err => {
        this.feedback.error('Erreur chargement étudiants');
        this.loading.hide();
      }
    });
  }

  ajouterEtudiant() {
    this.router.navigate(['/etudiants/new']);
  }

  deleteEtudiant(id: number) {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: { message: 'Voulez-vous vraiment supprimer cet étudiant ?' }
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result === true) {
        this.loading.show();
        this.etudiantService.delete(id).subscribe({
          next: () => {
            this.feedback.success('Étudiant supprimé avec succès');
            this.loadEtudiants();
          },
          error: () => {
            this.feedback.error('Erreur lors de la suppression');
            this.loading.hide();
          }
        });
      }
    });
  }

  applyFilter() {
    const filterValue = this.searchTerm.trim().toLowerCase();
    this.dataSource.filterPredicate = (data: any, filter: string) =>
      data.nom.toLowerCase().includes(filter) || data.prenom.toLowerCase().includes(filter);
    this.dataSource.filter = filterValue;
  }
}
