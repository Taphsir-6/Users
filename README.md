# UtilisateursAPI (Spring Boot)

Ce projet est une API REST de gestion des vacataires développée avec Spring Boot.

## Fonctionnalités principales
- CRUD Vacataire
- Gestion des exceptions
- Couverture de tests unitaires

## Lancement local
```sh
mvn clean package
java -jar target/UtilisateursAPI-0.0.1-SNAPSHOT.jar
```

## Lancement avec Docker
```sh
docker build -t utilisateurs-api .
docker run -d -p 8080:8080 --name test-api utilisateurs-api
```

## CI/CD avec GitHub Actions & Docker
- Build, test, création d'image Docker et push automatique sur Docker Hub à chaque push sur `main`.
- Secrets requis : `DOCKER_USERNAME`, `DOCKER_PASSWORD` (access token Docker Hub avec droits "write")

## Auteur
- Papa Omar Diop

**Mise à jour :** test de déclenchement du pipeline CI/CD.
