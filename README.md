# Logfile-Parser

Es ist eine fertig JAR mit dem Programm im Projekt vorhanden

Installation des Programms(Nur wer selbst kompilieren möchte):
Maven zum Kompilieren vorausgesetzt (Unter Ubuntu standardmäßig drauf).
Navigiere in das Projektverzeichnis, in dem sich der src Ordner befindet und gib folgendes in die Konsole:

mvn clean compile assembly:single

Es wird ein target Ordner erstellt in dem sich die ausführbare JAR mit dem Programm befindet




Kurze Erklärung zur Funktionalität:

Load file Button: Dieser Button öffnet ein Explorerfenster um eine .log Datei auszuwählen.

FilterBy-SelectionBox: Man kann in dieser SelectionBox auswählen nach welchen Kriterien gefiltert werden soll.

Textfeld: In dieses Textfeld wird der gesuchte Eintrag eingegeben.

Save file Button: Dieser Button öffnet ein Explorerfenster um die Inhalte, so wie sie in der Tabelle angezeigt werden, zu exportieren.

Sortiert werden kann nach einem beliebigen Kriterium bei Klick auf eine der Kopfzeilen der Tabelle

