tataviiz
========
data visualisation dans l'espace physique
---------------------------------------

**Objectifs**

Visualiser des données dans l'espace physique.

Ce prototype a pour objectif de faire varier la hauteur d'une sphère légère dans un tube transparent.

**Matériel**

pour l'installation : 
* tube transparent en verre acrylique de 2m (main courante achetée chez Leroy Merlin)
* turbine Foxconn de récupération 12v trouvée dans les cartons de récupération rue Montgallet
* sphères en polystirène 

pour l'électronique : 
* arduino

**Montage**

![Diagramme de circuit](http://en.wikipedia.org/wiki/File:Circuit_diagram_%E2%80%93_pictorial_and_schematic.png)

**Source de données**

* valeur de transactions bitcoin

*bootstrap from previous hackday*

Copy Arduino RXTXcomm.jar into Play lib directory

[ARDUINO_HOME]\lib\RXTXcomm.jar => [PLAY_HOME]/lib (create if it does not exist)

Run Play with additional enironment variable
play> run -Djava.library.path=[ARDUINO_HOME]

play> run -Djava.library.path=/Applications/Arduino.app/Contents/Resources/Java

On MacOs
========

    $ sudo mkdir /var/lock
    $ sudo chmod 777 /var/lock
    $
    $ play
    [server] run -Djava.library.path=path/macos/

Go to http://localhost:9000/ first to init all correctly.
