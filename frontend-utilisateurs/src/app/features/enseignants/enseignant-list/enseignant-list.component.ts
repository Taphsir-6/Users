import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EnseignantService } from '../../../core/services/enseignant.service';
import { Enseignant } from '../enseignant.model';
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
  selector: 'app-enseignant-list',
  standalone: true,
  imports: [CommonModule, RouterModule, MatTableModule, MatButtonModule, MatIconModule, MatPaginatorModule, MatSortModule, MatFormFieldModule, MatInputModule, FormsModule],
  templateUrl: './enseignant-list.component.html',
  styleUrls: ['./enseignant-list.component.scss']
})
export class EnseignantListComponent implements OnInit, AfterViewInit {
  dataSource = new MatTableDataSource<Enseignant>([]);
  displayedColumns: string[] = ['nom', 'prenom', 'email', 'telephone', 'matricule', 'grade', 'actif', 'actions'];
  searchTerm: string = '';

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private enseignantService: EnseignantService,
    private router: Router,
    private dialog: MatDialog,
    private feedback: FeedbackService,
    private loading: LoadingService
  ) {}

  ngOnInit(): void {
    setTimeout(() => this.loadEnseignants(), 0);
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }

  loadEnseignants(): void {
    this.loading.show();
    this.enseignantService.getAll().subscribe({
      next: ens => {
        this.dataSource.data = ens;
        this.loading.hide();
      },
      error: err => {
        this.feedback.error('Erreur chargement enseignants');
        this.loading.hide();
      }
    });
  }

  ajouterEnseignant() {
    this.router.navigate(['/enseignants/new']);
  }

  deleteEnseignant(id: number) {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: { message: 'Voulez-vous vraiment supprimer cet enseignant ?' }
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result === true) {
        this.loading.show();
        this.enseignantService.delete(id).subscribe({
          next: () => {
            this.feedback.success('Enseignant supprimé avec succès');
            this.loadEnseignants();
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
