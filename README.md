# A propos du projet

Ce projet fait parti du projet de mise en situation professionnelle de la formation Lead Development Java. Le but du projet est de développer une application New QCM Plus qui permet de gérer des comptes utilisateurs et des questionnaires en ligne.

# Installation de l'environnement de travail

## Prérequis

Vous devez avoir Java 11 installé sur votre PC ainsi que Maven.

Vous devez aussi disposer d'un SGBD (MySQL, PostgreSQL, MariaDB, etc).

## Installation

1. Cloner le projet
```
git clone https://github.com/BaCrs/NewQCMPlus.git
```

2. Renseigner les informations de la base de données locale dans le fichier `application.properties`.
```
spring.datasource.driverClassName=[Nom du driver utilisé]
spring.datasource.url=[URL d’accès à la base de données]
spring.datasource.username=[Identifiant]
spring.datasource.password=[Mot de passe]
``` 
3. Jouer le script `Script_BDD.sql` pour peupler votre base de données locale. Il faudra peut-être adapter le script au SGBD choisi. Le script disponible est formatté pour PostgreSQL.

4. Exécuter la méthode `main` du projet.
