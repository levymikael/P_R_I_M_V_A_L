Programme d'installation Primval sous Windows
=============================================


Mise en place et installation
-----------------------------
Le programme d'installation de Primval requiert l'utilisation des outils suivants :
 * Launch4j : création d'un lanceur .exe vers .jar (http://launch4j.sourceforge.net/)
 * Inno Setup : création des installateurs Setup et Update (https://jrsoftware.org/isinfo.php)

L'outil make est également recommandé pour l'utisation des commandes de build.


Création d'un installateur
--------------------------
Les étapes pour créer un installateur sont les suivantes :
 * Placer le fichier Primval.jar dans le dossier setup/Prog
 * Editer le numéro de version dans le fichier setup/define.h
 * Lancer la commande suivante :
 ```
 make setup
 ```

Création d'une mise à jour
--------------------------
Les étapes pour créer une mise à jour sont les suivantes :
 * Placer le fichier Primval.jar dans le dossier setup/Prog
 * Editer le numéro de version dans le fichier setup/define.h
 * Lancer la commande suivante :
 ```
 make update
 ```




