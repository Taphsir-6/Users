# UtilisateursAPI & Frontend (Angular + Spring Boot)

Ce projet est une application complète de gestion des utilisateurs (vacataires, enseignants, étudiants) avec un backend Spring Boot (API REST) et un frontend Angular (interface web moderne).

---

## Fonctionnalités principales

### Backend (Spring Boot)
- **API REST** pour la gestion des vacataires, enseignants et étudiants
- **CRUD** complet (Créer, Lire, Modifier, Supprimer)
- **Gestion des exceptions** centralisée
- **Tests unitaires** et couverture de code
- **CI/CD** avec GitHub Actions et Docker
- **Sécurité et validation** côté serveur

### Frontend (Angular)
- **Interface web moderne** avec Angular & Angular Material
- **Gestion des utilisateurs** (vacataires, enseignants, étudiants)
- **Formulaires dynamiques** (création/édition avec validation)
- **Navigation fluide** (listes, formulaires, retours automatiques)
- **Feedback utilisateur** (messages d’erreur, confirmation)
- **Responsive design** (adapté mobile & desktop)

---

## Lancement du projet

### 1. Backend (API Spring Boot)
```sh
cd Users
mvn clean package
java -jar target/UtilisateursAPI-0.0.1-SNAPSHOT.jar
```
API disponible sur http://localhost:8080

#### Avec Docker
```sh
docker build -t utilisateurs-api .
docker run -d -p 8080:8080 --name test-api utilisateurs-api
```

### 2. Frontend (Angular)
```sh
cd frontend-utilisateurs
npm install
ng serve
```
Interface accessible sur http://localhost:4200

---

## Structure du projet

```
Users/
│
├── src/                 # Backend Spring Boot (API)
├── frontend-utilisateurs/ # Frontend Angular
└── README.md
```

---

## CI/CD & Docker
- **GitHub Actions** : build, tests, image Docker, push sur Docker Hub à chaque push sur `main`
- **Secrets requis** : `DOCKER_USERNAME`, `DOCKER_PASSWORD` (token Docker Hub)

---

## Auteur
- Omar DIOP
- Koumba Thiogane
- Taphsir Abdoulaye Ciss
- Ousmane mané

**Mise à jour :** Projet fullstack Angular + Spring Boot, prêt pour production et déploiement Docker.
