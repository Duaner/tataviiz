tataviiz
========

Datavisualisation Physique

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
