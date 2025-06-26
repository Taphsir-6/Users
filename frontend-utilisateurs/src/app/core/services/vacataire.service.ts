import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Vacataire } from '../../features/vacataires/vacataire.model';

@Injectable({ providedIn: 'root' })
export class VacataireService {
  private apiUrl = '/api/vacataires'; // Utilise le proxy Angular

  constructor(private http: HttpClient) {}

  getAll(): Observable<Vacataire[]> {
    return this.http.get<Vacataire[]>(this.apiUrl);
  }

  getById(id: number): Observable<Vacataire> {
    return this.http.get<Vacataire>(`${this.apiUrl}/${id}`);
  }

  create(vacataire: Vacataire): Observable<Vacataire> {
    return this.http.post<Vacataire>(this.apiUrl, vacataire);
  }

  update(id: number, vacataire: Vacataire): Observable<Vacataire> {
    return this.http.put<Vacataire>(`${this.apiUrl}/${id}`, vacataire);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  desactiver(id: number): Observable<Vacataire> {
    return this.http.post<Vacataire>(`${this.apiUrl}/${id}/desactiver`, {});
  }

  reactiver(id: number): Observable<Vacataire> {
    return this.http.post<Vacataire>(`${this.apiUrl}/${id}/reactiver`, {});
  }

  /**
   * Retourne TOUS les vacataires (actifs et inactifs)
   */
  getAllWithInactifs(): Observable<Vacataire[]> {
    return this.http.get<Vacataire[]>(`${this.apiUrl}/all`);
  }
}
