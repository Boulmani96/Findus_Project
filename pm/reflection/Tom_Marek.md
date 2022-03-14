# Selbstreflexion: Tom Marek
## Wochen vom 27.10 bis 10.11 

### Arbeiten
- Einarbeitung in Kotlin, Kotlin Multiplatform
- Einarbeitung in Unit Tests, Mocking
- Postman zum Testen der API gelernt
- Einarbeitung in Ktor, Authentifizierung mit JWT
- Bearbeitung Issue #9 und #10
    - Implementierung der Routen um einen Patienten zu laden, bearbeiten, erstellen und löschen.
	- Implementierung der Authentifizierung mittels JWT so das alle Routen nur mit einem gültigen JWT-Token aufgerufen werden können.
- Präsentation von Gitlab CI/CD

### Issues
- #9
- #10

### Anmerkungen
- Die Umsetzung des Ktor Severs und der Authentifizierung mit JWT hat gut funktioniert.
Konnte allerdings nur die Funktionen nur Testen und nicht für das eigentliche Projekt implementieren, 
da immer noch die Routen fehlen für welche ich die Authentifizierung implementieren soll. 
Diese können aber nicht in das eigentliche Projekt implementiert werden da das Initiale Projekt und die Datenbank Anbindung fehlt.

## Wochen vom 10.11 bis 24.11

### Arbeiten
- Verbesserung der Ktor API
    - Aufteilung in verschiedenen Dateien
    - Verbesserung der Response Codes und deren Nachrichten
- Uploading und Downloading von Bildern
- Unit Tests für alle Routen
- Implementierung der Ktor API in das Initiale Projekt
- Implementierung das alle Daten aus der Datenbank geladen, bearbeitet, erstellt und gelöscht werden
- Code review

### Issues
- #9
- #10

### Merge
- !23

### Anmerkungen
- Die Verbesserung der Ktor API, Anbindung an die Datenbank, das Code Review und die Implementierung der Unit Tests hat gut funktioniert.
- Es war nicht einfach das das Uploading und Downloading von Bildern funktioniert, da dieser Bereich in Ktor nicht gut Dokumentiert ist.
- Die Zusammenarbeit im Team, und Team übergreifend war sehr gut.
- Es gab große Probleme alles mit Gradle und Docker laufen zu lassen, es wurde in diesem Punkt auch nicht viel geholfen, was auch vor allem damit zu tun hat das sich damit keiner Auskannte und zu diesem Problem auch Online nicht viel gefunden wurde.

## Wochen vom 24.11 bis 08.12

### Arbeiten
- Das Projekt wurde Umstrukturiert
    -  Der Teil für das Office Backend wurde in einen Extra Ordner verschoben
- Die Docker File und Docker Compose File wurde verbessert so dass die Services untereinander kommunizieren können und richtig gebaut werden
- Viele Probleme mit Gradel wurden behoben so dass das Projekt jetzt mit Gradel außerhalb und innerhalb des Docker Container bebaut werden kann
- Habe der Android Gruppe und der Desktop Gruppe geholfen die Endpunkte der API zu implementieren
- Habe angefangen die Automatische Erstellung der Dokumentation für die Ktor-API zu erstellen

### Issues
- #32
- #34

### Merge
- !28

### Anmerkungen
Das Issue "Initiales Projekt läuft" war nicht einfach und hat viel Zeit und Meetings erfordert. Aber jetzt geht alles was sehr schön ist ;).

## Wochen vom 08.12 bis 19.01 (Weihnachtsferien)

### Arbeiten
- Issue #32: Die noch Fehlenden Routen wurden mit dem Framework implementiert so das diese automatisch Dokumentiert werden.
- Bei dem Issue #32 "Automatische Erzeugung der KTOR-Doku" gab es ein Problem das Routen in denen eine Datei empfangen werden soll nicht Funktioniernen. Dieser Fehler tritt aber allerdings intern in dem verwendeten [Framework](https://github.com/papsign/Ktor-OpenAPI-Generator) auf. Der fehler wurde auch schon auf [Github](https://github.com/papsign/Ktor-OpenAPI-Generator/issues/122) gemeldet, allerdings leider noch nicht behoben. 
    - Nach Rücksprache mit Frau Trapp soll das Problem nun so gelöst das die Route in der die Datei hochgeladen werden soll nicht mit dem Framework erstellt werden soll, sondern mit der normalen Ktor-API.
    - Die Extra Route wurde implementiert und Funktioniert.
    - Die alte Route die für den Datei Upload zuständig ist welche mit dem Framework umgesetzt wurde, ist nicht gelöscht worden um später wenn der Fehler im Framework behoben wurde, diese leicht ausgetauscht werden kann.

### Issues
- #32

### Merge
- !41
- !40

### Anmerkungen
Die Umsetzung der Automatischen Erzeugung der KTOR-Doku war nicht einfach da das [Framework](https://github.com/papsign/Ktor-OpenAPI-Generator) nicht gut Dokumentiert ist. 
Dann ist noch der Fehler mit dem Datei Uploading aufgereten, welcher viel Zeit nach der Suche des Fehlers gekostet hat, um dann festzustellen das es in Fehler im Framework ist.

## Wochen vom 19.01 bis 26.01
- Issue #25: Das Model für die Anamnese wurde erstellt. Die Routen für das Backend wurden implementiert. Das heißt CRUD Operation für die Anamnese (Create, Read, Update, Delete).
- Das Patient Model wurde erweitert, so dass es dem Format der Dummydaten enspricht. Das überarbeitete Patient Model kann auch komplett mittels CRUD operation bearbeitet werden.
- Das Bodymap Model und die CRUD Routen für das Bodymap wurden in das Office Backend gemerged.
- Alle erstellen Routen wurden mit dem Framework erstellt welche die Swagger Dokumentation automatisch erstellt.
- Außerdem wurden für alle erstellten Routen Tests geschrieben.
- Issue #44: Geholfen die Dummydaten zu implementieren.

### Issues
- #25
- #44

### Merge
- !43
- !45

### Anmerkungen
Es musste viel Kommuniziert werden um zu wissen welche Daten und in welchem Fomrmat diese für die Models (Anamnese und Patient) benötigt werden. Bei dem erstellen der Routen, dem bearbeiten und hinzufügen der Models, der Automatischen Erzeugung der Dokumentation für die Routen und bei dem schreiben der Tests sind keine weiteren Probleme aufgereten.

## Wochen vom 26.01 bis 02.02
- Issue #44: Fehler behoben, das die Bilder der Dummydaten auch im Docker Container gefunden und in die Datenbank geladen werden.
- Issue #50: Einen Teil der Routen im Frontend implementiert. Das heißt die Findus API und Repository im Frontend angepasst.
- Issue #50, #25, #44: Habe dabei geholfen, dass die Datenklassen für den Patienten und Anamnese angepasst werden, da diese nochmal geändert werden sollten.
- Issue #50: Habe dabei geholfen, dass das Repository im Frontend richtig verwendet wird.

### Issues
- #25
- #44
- #50

### Merge
- !46
- !53
- !50
- !51
- !52

### Anmerkungen
Dass die Bilder der Dummydaten auch im Docker Container gefunden werden, hat viel Zeit in Anspruch genommen.
Die Routen im Frontend zum Uploaden und Downloaden der Bilder hat viele Probleme bereitet.


## Selbsteinschätzung

Ich bin insgesamt mit der Arbeit und des Endprodukts des Teams sehr zufrieden. Die Arbeit mit dem Team hat viel Spaß gemacht, das wurde vor allem gegen Ende des Projekts immer besser.

Insgesamt habe ich eine große Rolle gespielt, um das Projekt voranzubringen. Alexander Czegley, Gabriel Lukas Schön und ich haben das Backend alleine umgesetzt.
Vor allem für den Office Backend teil habe ich eine große Rolle gespielt, denn ich habe mich in Ktor eingearbeitet und die erarbeiteten ersten funktionierenden Routen wurden dann als Beispiel 
für das Cloud Backend und weitere Erweiterungen am Office Backend verwendet. Die Beispiele konnten so verwendet werden und es musste daran auch nichts mehr verändert werden.

Außerdem habe ich die entscheidende Idee habt das Gradle Docker Problem (Es war vor allem das Problem, das Office Backend und Cloud Backend erfolgreich in einem Docker Container zu bauen und laufen zu lassen) zu lösen, welches das Projekt schon seit Wochen beschäftigt hatte und bis dahin keiner eine Lösung hatte.

Später im Projekt sollten dann alle Routen automatisch mit dem Open Api Framework dokumentiert werden. Dafür mussten sehr große Teile im Backend neu geschrieben werden. Ich habe mich wieder als Erstes damit eingearbeitet. Die entstandenen ersten Routen,
welche mit dem Framework umgesetzt wurden, wurden dann wieder als Beispiel verwendet, um die restlichen Routen und das Cloud Backend automatisch zu dokumentieren.
Desweitern sind sehr umfangreiche Tests für das Office Backend geschrieben wurden, welche alle Funktionen abdecken.

Alle von mir bearbeiten Issues wurden erfolgreich abgeschlossen. Außerdem war ich immer daran interessiert, das Projekt voranzubringen, weshalb ich auch die Feuerlöscherrolle angenommen habe. Ich habe außerdem einige Merge Request bearbeitet und war auch immer daran interessiert, das der Code in einem Guten zustand auf dem Master gemerged wurde. Ich habe außerdem auch den Code von Merge request angeschaut, die nicht von mir bearbeitet wurden, weil ich immer daran interessiert bin, wie andere die Issues gelöst haben, um dadurch lernen zu können.

In dem Projekt habe ich vor allem gelernt, das Kommunikation ein sehr wichtiger Aspekt in der Arbeit in ein Team ist und der Zeitaufwand definitiv nicht unterschätzt werden darf. 
Die Zusammenarbeit mit Alexander Czegley und Gabriel Lukas Schön hat immer Spaß gemacht und sehr gut funktioniert. Es wurde schnell und unkompliziert kommuniziert und immer eine Lösung für ein Problem gefunden.
Anhand der oben genannten Punkte würde ich meine Leistung mit einer 1,0 bewerten.

