# Atelier 1
## Approche Web Statique + WebService
Cette approche consiste en des sources CSS et HTML, ainsi que des scripts JS, qui indiquent aux clients les opérations à effectuer. Parmi ces opérations se trouvent des appels à une API\
Ces sources peuvent être servies sur le cloud (en utilisant un CDN)\
![Statique + Webservice](img%2Fatelier1-webservice.jpg)
### Avantages
* Moins coûteux en ressources serveur car on n'a pas de templating HTML côté serveur
* Scalabilité horizontale facilitée car on peut facilement séparer l'API entre plusieurs machines
* Facilite l'interopérabilité avec d'autres applicatifs si besoin (on peut réutiliser l'API sur un autre produit)
* Facilite le développement si les ressources front-end et le code du serveur sont développés par des personnes différentes (à condition de bien définir les contrats d'API)
### Inconvénients
* Rend plus difficile le développement si la vue et le contrôleur sont développés par la même personne: 2 bases de code pour la même fonctionnalité
* Code HTML moins compréhensibles pour les moteurs de recherches
* Déploiement plus complexe
## Approche Web dynamique
Cette approche délègue une grande partie de la logique au serveur pour limiter le nombre d'appels AJAX.\
On a donc un serveur qui centralise tout le fonctionnel
![Web Dynamique](img%2Fatelier1-mvc.jpg)
### Avantages
* Même base de code pour la majorité de l'applicatif
* Plus de contrôle sur la logique opérée, moins de besoin de sécurité entre la vue et le contrôleur puisque c'est sur la même machine
* Demande moins de ressources au client
* Rendu côté serveur: on peut facilité l'indexation du site par les moteurs de recherches
### Inconvénients
* Plus de ressources serveur: déploiement plus coûteux
* La maintenabilité peut être plus difficile si la séparation des fonctionnalités n'est pas nette (si on migre la technologie d'une fonctionnalité, on peut être amené à migrer tout le serveur, alors que des Webservice séparés ne poseraient pas ce problème)
## MVC
Les 2 approches respectent le pattern MVC:
* Pour l'approche Web dynamique, le MVC est explicite dans le serveur, les contrôleurs reçoivent la requête et utilisent les modèles pour interagir avec la base de données et la Vue.
* Pour l'approche Web statique + Web service, les scripts Javascripts tiennent le rôle de contrôleur pusiqu'ils contiennent la logique. La structure de JSON représente les modèles et la vue est dans les sources HTML et CSS