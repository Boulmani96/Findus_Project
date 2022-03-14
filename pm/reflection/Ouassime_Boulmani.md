# Selbstreflexion Ouassime Boulmani

## Woche vom 03.11.2021 bis 09.11.2021

### Issue #3: gitlab Struktur
- Boards wurden erstellt
- Labels hinzugefügt
- Merge request & gitlab Workflow definiert
- Readme dementsprechend erweitert
- Der erste Meilenstein wurde definiert

### Anmerkungen 
Bei der initialen Verzeichnisstruktur war noch unklar, und wird noch mal mit Frau Trapp besprochen und erklärt.
Ich habe mein Team den GitLab Workflow erklärt und darauf hingewisen, sich an den GitLab Workflow zu halten.

## Woche vom 10.11.2021 bis 24.11.2021

### Issue #2: CI-Pipeline
- Linting-Fehler etc. werden geprüft.
- Automatische Tests werden ausgeführt.

### Anmerkungen 
Die CI-Pipeline habe ich allein aufgesetzt, da der Git-Head vom Team 1 mit einem anderen Issue beschäftigt war, und wir haben beschlossen, dass ich erstmal aufsetze, und dann schaut er.
Ich hatte ein paar Schwierigkeiten bei dem Aufsetzen der CI-Pipeline, erstens da ich noch keine Idee oder Erfarung wie man es macht, zweitens es gab viele Probleme bei dem Initialen Projekt, die später abgehoben sind, was mir richtig lange zeit gekostet.

## Woche vom 25.11.2021 bis 01.12.2021

### Issue #16: Internationalisierung
- Überall utf-8
- Alle Strings inkl. Fehlermeldungen sind in Englisch und Deutsch angelegt. 
- Eine Readme zur Wartung beider Sprache und zum Anlegen einer weiteren Sprache ist erstellt.

### Anmerkungen       
 kann ich leider nicht soweit weiter machen, da es noch nicht alle Strings inkl. Fehlermeldungen dabei vorhanden sind.

## Woche vom 02.12.2021 bis 08.12.2021

### Issue #16: Internationalisierung
- Naming-Convention unter Readme aufsetzen.
- Zeit- und Datumsangaben werden entsprechend formatiert.
- Tests für Englisch und Deutsch sind für Zeit- und Datumsangaben implementiert.
- In der GUI werden alle Texte in Deutsch oder Englisch angezeigt -- aktuell auf Basis der Sprache des jeweiligen Betriebssystems. 
       => Problem: kann ich ja immer noch nicht implementieren, da keine GUI noch gibt.

### Anmerkungen
Ich habe noch mal mit Frau Trapp am 08.12. darüber gesprochen, wie man mit dem Issue weitermachen, obwohl es noch keine GUI gibt, und dann würde erklärt erstmal Tests zu schreiben,
und eine Readme für Naming-Convention aufsetzten, und Tests für Englisch und Deutsch für Zeit- und Datumsangaben und Währung implementiert.

## Woche vom 09.12.2021 bis 15.12.2021

### Issue #36: Charts in Android
- **Gemeinsam mit Taibi implementiert**
- Recherche einer geeigneten Chart-Bibliothek (line chart, bar chart, pie chart).
- möglichst viel gemeinsamer Code.
- Implementation als Komponente, soweit möglich, so dass eine einfach Integration in verschiedene Screens möglich ist.
- gute und intuitive Benutzbarkeit
- Datenreihen für dieses Issue als einfache Liste von zufälligen Werten

### Anmerkungen
Recherche einer geeigneten Chart-Bibliothek hat uns richtig viel Zeit gekostet, da keine Viele Beispiele im Internet vorhanden sind, und die meisten lassen sich nicht fehlgeschlagen erstellen, was uns nicht richtig gewusst haben, ob wir dann unsere Bibliothek erstellen sollen, und ob die ist die einzige Lösung für den Issue.
Nach vielen und vielen Beispiele und Versuchen hat am Ende geklappt. :D

## Woche 12.01.2022 bis 19.01.2022

### Issue #23: Refactoring Client - Architektur umsetzen
- Unterstützung des Navigationsgraphen: Taibi und ich haben Amir zu diesem Akzeptanzkriterien geholfen, indem wir Geänderte Bildschirmstruktur. Pakete im App-Modul hinzugefügt haben. Jeder Bildschirm hat sein eigenes Paket (Dashboard, Anamnese, Bodymapping), das für die zukünftige Verwendung vorgesehen ist, um Probleme beim Zusammenführen zu vermeiden.
			
### Issue #23: Implementation Dashboard
- **Gemeinsam mit Taibi implementiert**
- Navigation zu Settings und BodyMapping-Editor ist möglich: was Taibi, Amir und ich am Anfang implementiert haben, ist ein Buttom Navigation, was Taibi und ich nicht so richtig gut gefallen hat, deswegen haben wir die Zeit genutzt, bis die Leute für die Dummydaten und den Zugriff zum Datenbank fertig sind, eine Linke Seite Navigation was sie von Frau Trapp gewünscht ist.
- Es können Notizen editiert/hinzugefügt/gelöscht werden, aber noch nicht in MongoDB, da wir noch keinen Zugriff zum Datenbank haben.
- Das Dashboard ist in Android verfügbar: 

### Anmerkungen
Dashboard zu implementieren hat mir richtig Spaß gemacht, wo meine Stärke liegen, ist Code zu schreiben, wo ich richtig gut meine Bestens geben könnte.
Die Zusammenarbeit mit Taibi war richtig gut, wir haben uns gut verstanden, indem wir den Code zwischen uns geteilt haben, und jeder hat den anderen unterstützt.

## Woche 20.01.2022 bis 26.01.2022

### Issue #43: Patientensuche
- **Gemeinsam mit Taibi implementiert**
- Dieser Issue gehört zum Dashboard, und in Figma steht nicht, wo man in Dashboard implementieren soll, war noch unklar, und wird noch mal mit Frau Trapp besprochen und zusammen erklärt.
- Wir haben ein paar Demo Beispiele für Patienten, um unseres Code zu testen, und wenn die Dummydaten und den Zugriff zum Datenbank implementiert sind. dann werden wir direkt die Daten von Datenbank holen, und mit denen arbeiten.
- Suche nach Haltername/Tiername möglich
- Anzeige Trefferliste mit Bild etc.
- Auswahl eines Tieres zeigt Details dazu im Dashboard.

## Woche 27.01.2022 bis 02.02.2022
- Die Demo Beispiele für Patienten wurden gelöscht und es wurde den richtigen Patienten bearbeitet, die von Datenbank geholt sind.
- Die Daten des BodyMappings stammen aus der MongoDB und werden über KTOR bereit gestellt.
- Ein Chart wird auf der Basis von gemockten Daten angezeigt.
- Es können Notizen editiert/hinzugefügt/gelöscht werden -- diese werden in einer MongoDB gespeichert.

### Anmerkungen
Die Dummydaten und den Zugriff zum Datenbank wurde eigentlich ein bisschen zu spät implementiert, was für uns wenig Zeit gab, die Daten von Datenbank zu holen und zu bearbeiten, da wir zum ersten in diesem Projekt mit Datenbank arbeiten wurden.
Wir haben richtig in den letzten Tagen Gaz gegeben, und sogar den ganzen Tag (mehr als 10 Stunden pro Tag) nur mit dem Projekt beschäftigt waren, Taibi hat bei mir die letzten Tagen vor dem Sprint Review übernachtet, um den Dashboard wie es von Frau Trapp gewünscht ist zu implementieren.


##  Feedback zum Projekt	## Selbsteinschätzung
 
Das Projekt war sehr gut organisiert, obwohl ich am Anfang etwas unsichtbar war, da ich vielleicht die falschen Issues genommen habe und als Git-Head war ich leider nicht zufrieden, da ich in falschen Position war, und nach einem Kurzen Gespräch mit Frau Trapp habe ich Ihnen erzählt, was mir gut gefallen und zwar Code zu schreiben und programmieren, dann hat für mich extra einen Issue Charts in Android gegeben, was mir Spaß gemacht hat, und konnte mein bestes geben.
Ab diesem habe ich richtig überlegt, welche Issues sind geeignet für mich, und wo ich genau richtig mein bestes geben konnte, deshalb habe ich Dashboard, Patientensuche und noch Unterstützung des Navigationsgraphen für den zweiten Sprint genommen, und Taibi und ich haben richtig Gaz gegeben, und wenn etwas unklar ist, hat mir Frau Trapp richtig geholfen und erklärt, und ich danke Frau Trapp dafür, sowohl als Product Owner als auch unsere Lehrerin und uns die Lernmöglichkeitein zu schaffen.
In der Zeit von Weihnachtsferien habe ich die Zeit genutzt, und habe noch viel mehr über die D-KMP architecture und Declarative UIs, Kotlin MultiPlatform und die MVI pattern, und D-KMP layers gelernt, und nach der Weihnachtsferien also ab dem zweiten Sprint war ich sehr zufrieden und motiviert, was ich in der Ferien gelernt habe, in diesem Projekt zu verwenden und reinzubrigen, ich hatte wie bei den anderen auch ein paar Schwierigkeiten mit Jetpack Compose, da es relativ neu ist und im Internet keine hilfreichen Quellen gibt, und ich sollte ja das meiste selbt eine Lösung finden.
Zusammenfassend hat mir das Projekt ab dem zweiten Sprint Spaß als den ersten gemacht, und würde mal noch mehr lernen und in diesem Projekt weitermachen, und bin zufrieden mit der Zusammenarbeit und Leistung des Teams, und habe viel Erfahrungen in diesem Projekt gesammelt, wie man in einem Team zusammenarbeitet, und als ersten Schritt für Berufsleben mitnehme.
Ich würde meine Leistung mit 1,7 bewerten, da ich richtig mein bestes gegeben habe, und noch viel mehr Zeit für dieses Projekt als gefordert, um ein laufiges und gutes Produkt am Ende des Sprints zu geben, und meine Arbeit als mehr gewünscht ist zu implementieren und zu vorstellen.
