import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NavbarComponent } from './shared/components/navbar/navbar.component';
import { GlobalLoaderComponent } from './core/components/global-loader/global-loader.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NavbarComponent, GlobalLoaderComponent],
  template: `
    <app-global-loader></app-global-loader>
    <app-navbar></app-navbar>
    <div class="container" style="margin-top: 32px">
      <router-outlet></router-outlet>
    </div>
  `,
  styleUrls: ['./app.component.scss']
})
export class AppComponent {}
