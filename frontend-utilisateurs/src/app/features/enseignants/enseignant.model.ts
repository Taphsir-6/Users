// Auteur : Omar DIOP
// Modèle TypeScript pour un Enseignant
export interface Enseignant {
  id?: number;
  nom: string;
  prenom: string;
  email: string;
  telephone: string;
  matricule?: string;
  grade?: string;
  actif: boolean;
}
