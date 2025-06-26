import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VacataireService } from '../../../core/services/vacataire.service';
import { Vacataire } from '../vacataire.model';
import { Router, RouterModule, NavigationEnd } from '@angular/router';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog } from '@angular/material/dialog';
import { FeedbackService } from '../../../core/services/feedback.service';
import { LoadingService } from '../../../core/services/loading.service';
import { ConfirmDialogComponent } from '../../../shared/components/confirm-dialog/confirm-dialog.component';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { filter } from 'rxjs';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

@Component({
  selector: 'app-vacataire-list',
  standalone: true,
  imports: [CommonModule, RouterModule, MatTableModule, MatButtonModule, MatIconModule, MatPaginatorModule, MatSortModule, MatFormFieldModule, MatInputModule, FormsModule],
  templateUrl: './vacataire-list.component.html',
  styleUrls: ['./vacataire-list.component.scss']
})
export class VacataireListComponent implements OnInit, AfterViewInit {
  dataSource = new MatTableDataSource<Vacataire>([]);
  displayedColumns: string[] = ['nom', 'prenom', 'email', 'telephone', 'specialite', 'actif', 'actions'];
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  searchTerm: string = '';

  constructor(
    private vacataireService: VacataireService,
    private router: Router,
    private dialog: MatDialog,
    private feedback: FeedbackService,
    private loading: LoadingService
  ) {}

  ngOnInit(): void {
    setTimeout(() => this.loadVacataires(), 0);
    // Recharge la liste à chaque navigation sur /vacataires
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd && this.router.url.startsWith('/vacataires'))
    ).subscribe(() => this.loadVacataires());
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }

  loadVacataires(): void {
    this.loading.show();
    // Utilise la nouvelle méthode pour récupérer TOUS les vacataires (actifs et inactifs)
    this.vacataireService.getAllWithInactifs().subscribe({
      next: vacs => {
        this.dataSource.data = vacs.map(v => ({ ...v, actif: !!v.actif }));
        this.loading.hide();
      },
      error: err => {
        this.feedback.error('Erreur chargement vacataires');
        this.loading.hide();
      }
    });
  }

  ajouterVacataire() {
    this.router.navigate(['/vacataires/new']);
  }

  deleteVacataire(id: number) {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: { message: 'Voulez-vous vraiment supprimer ce vacataire ?' }
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result === true) {
        this.loading.show();
        this.vacataireService.delete(id).subscribe({
          next: () => {
            this.feedback.success('Vacataire supprimé avec succès');
            this.loadVacataires();
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
