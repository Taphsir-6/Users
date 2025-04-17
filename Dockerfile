# Étape 1 : Utiliser une image Java officielle
FROM openjdk:17-jdk-slim

# Étape 2 : Définir le dossier de travail
WORKDIR /app

# Étape 3 : Copier le jar généré dans le conteneur
COPY target/*.jar app.jar

# Étape 4 : Exposer le port de l’application
EXPOSE 8080

# Étape 5 : Commande de lancement
ENTRYPOINT ["java", "-jar", "app.jar"]