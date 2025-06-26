import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Enseignant } from '../../features/enseignants/enseignant.model';

@Injectable({ providedIn: 'root' })
export class EnseignantService {
  private apiUrl = '/api/enseignants'; // Utilise le proxy Angular

  constructor(private http: HttpClient) {}

  getAll(): Observable<Enseignant[]> {
    return this.http.get<Enseignant[]>(this.apiUrl);
  }

  getById(id: number): Observable<Enseignant> {
    return this.http.get<Enseignant>(`${this.apiUrl}/${id}`);
  }

  create(enseignant: Enseignant): Observable<Enseignant> {
    return this.http.post<Enseignant>(this.apiUrl, enseignant);
  }

  update(id: number, enseignant: Enseignant): Observable<Enseignant> {
    return this.http.put<Enseignant>(`${this.apiUrl}/${id}`, enseignant);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
