import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { LoadingService } from '../../services/loading.service';

@Component({
  selector: 'app-global-loader',
  standalone: true,
  imports: [CommonModule, MatProgressSpinnerModule],
  template: `
    <div class="global-loader-backdrop" *ngIf="loadingService.loading$ | async">
      <mat-progress-spinner mode="indeterminate" color="primary"></mat-progress-spinner>
    </div>
  `,
  styleUrls: ['./global-loader.component.scss']
})
export class GlobalLoaderComponent {
  constructor(public loadingService: LoadingService) {}
}
