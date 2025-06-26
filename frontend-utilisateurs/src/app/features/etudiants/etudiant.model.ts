// Auteur : Omar DIOP
// Modèle TypeScript pour un Étudiant
export interface Etudiant {
  id?: number;
  nom: string;
  prenom: string;
  email: string;
  telephone: string;
  matricule?: string;
  photo?: string;
  actif?: boolean; // facultatif pour supporter les données sans ce champ
}
