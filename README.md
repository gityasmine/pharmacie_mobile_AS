## Pharmacie Mobile - Application Android

Cette application Android permet de gérer un stock de médicaments dans une pharmacie. Elle a été développée en Kotlin avec Android Studio en utilisant Jetpack Compose pour l'interface utilisateur. 
L'application offre une expérience complète de gestion avec la possibilité de consulter, ajouter et supprimer des médicaments. Les données sont synchronisées avec une API REST distante tout en étant sauvegardées localement (Room).

L'application se compose de trois écrans principaux. 

Le premier écran affiche la liste de tous les médicaments disponibles dans la pharmacie sous forme de cartes. Chaque carte montre le nom du médicament, sa forme pharmaceutique et sa quantité en stock. 
La quantité est affichée en rouge si elle est inférieure à 5 unités pour alerter d'un stock faible, et en vert sinon. Une barre de recherche en haut de l'écran permet de filtrer les médicaments par leur nom en temps réel.

Le deuxième écran permet de consulter les détails complets d'un médicament. 
On y retrouve toutes les informations du médicament incluant sa dénomination, sa forme pharmaceutique, sa quantité, et une photo si elle existe. Cet écran est en mode consultation uniquement.

Le troisième écran sert à ajouter un médicament. 
Un formulaire simple demande trois informations obligatoires : 
la dénomination du médicament, sa forme pharmaceutique et la quantité en stock. 
Une validation est effectuée avant la sauvegarde pour s'assurer que tous les champs sont remplis correctement
