# Selbstreflexion: Taibi El-houssine

## **Woche vom 27.10.2021 - 03.11.2021**

- Präsentation zu Projektstruktur von multiplatform und nativen Kotlin-Anwendungen.
- Verteilung der Issues und Einschätzungen
- Einführung in Kotlin, KMM, Jetpack Compose, JUnit, Mockito und MongoDB
- Zusammenarbeit mit Partner an Issues


## **Woche vom 03.11.2021 - 02.12.2021**

Issue #15: Recherche Open Source Systeme 

- Klare Anleitung für Hashing o.ä., für eine injektive Abbildung Tier -> Cloud
- Klare Checkliste, welche Daten unverändert hochgeladen werden können und welche wie zu anonymisieren sind.


## **Woche vom 02.12.2021 bis 08.12.2021**

Issue #18: Doku Architektur und Entwicklung:

- Einarbeitung in Architektur und Komponenten
- Links mit guten Tutorials zur Einarbeitung





## **Woche vom 09.12.2021 bis 15.12.2021**

Issue #36:

- Recherche einer geeigneten Chart-Bibliothek (line chart, bar chart, pie chart).
- möglichst viel gemeinsamer Code.
- Implementation als Komponente, soweit möglich, so dass eine einfach Integration in verschiedene Screens möglich ist.
- gute und intuitive Benutzbarkeit
- Datenreihen für dieses Issue als einfache Liste von zufälligen Werten

### **Anmerkungen**
Recherche einer geeigneten Chart-Bibliothek hat uns richtig viel Zeit gekostet, da keine Viele Beispiele im Internet vorhanden sind, und die meisten lassen sich nicht fehlgeschlagen erstellen, was uns nicht richtig gewusst haben, ob wir dann unsere Bibliothek erstellen sollen, und ob die ist die einzige Lösung für den Issue.
Nach vielen und vielen Beispiele und Versuchen hat am Ende geklappt. :D

## **Woche vom 12.01.2022 bis 26.01.2022**

### Issue #41: Refactoring Client - Architektur umsetzen
- **Gemeinsam mit Boulmani und Amir implementiert**
- Unterstützung des Navigationsgraphen: Boulmani und ich haben Amir zu diesem Akzeptanzkriterien geholfen, indem wir Geänderte Bildschirmstruktur. Pakete im App-Modul hinzugefügt haben. Jeder Bildschirm hat sein eigenes Paket (Dashboard, Anamnese, Bodymapping), das für die zukünftige Verwendung vorgesehen ist, um Probleme beim Zusammenführen zu vermeiden.

### Issue #23: Implementation Dashboard
- **Gemeinsam mit Boulmani implementiert**
- Navigation zu Settings und BodyMapping-Editor ist möglich: was Boulmani, Amir und ich am Anfang implementiert haben, ist ein Buttom Navigation, was Boulmani und ich nicht so richtig gut gefallen hat, deswegen haben wir die Zeit genutzt, bis die Leute für die Dummydaten und den Zugriff zum Datenbank fertig sind, eine Linke Seite Navigation was sie von Frau Trapp gewünscht ist.
- Es können Notizen editiert/hinzugefügt/gelöscht werden.
- Das Dashboard ist in Android verfügbar: 

### Anmerkungen
Dashboard zu implementieren hat mir richtig Spaß gemacht, wo meine Stärke liegen, ist Code zu schreiben, wo ich richtig gut meine Bestens geben könnte.
Die Zusammenarbeit mit Boulmani war richtig gut, wir haben uns gut verstanden, indem wir den Code zwischen uns geteilt haben, und jeder hat den anderen unterstützt.

### Issue #43: Patientensuche
- **Gemeinsam mit Boulmani implementiert**
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
Wir haben richtig in den letzten Tagen Gaz gegeben, und sogar den ganzen Tag (mehr als 10 Stunden pro Tag) nur mit dem Projekt beschäftigt waren, um den Dashboard wie es von Frau Trapp gewünscht ist zu implementieren.

### Selbsteinschätzung

Insgesamt bin ich sehr zufrieden mit meiner Leistung und der des Teams, obwohl ich zu Beginn des Projekts Probleme hatte, mich in das Projekt einzuarbeiten, da ich vielleicht die falschen Issues genommen habe, das hat mich etwas unsichtbar gemacht, und nach Zwischenfeedback von Frau Trapp  habe ich Ihr gesagt, was ich gut kann, und zwar Code zu schreiben und programmieren, das war der gleiche Fall mit Boulmani, dann hat Sie für uns beide extra einen programmieren Issue Charts in Android erstellte, was mir Spaß gemacht hat, und wo ich meines Bestes gegeben habe.
In der Zeit von Weihnachtsferien habe ich die Zeit genutzt und habe noch viel mehr über Kotlin und Jetpack Compose gelernt und nach der Weihnachtsferien also ab dem zweiten Sprint war ich sehr zufrieden und motiviert, was ich in der Ferien gelernt habe, in diesem Projekt zu verwenden und reinzubrigen, dann haben Boulmani und ich überlegt, dass wir die naschte Issues zusammen Bearbeiten und die Issues auswählen, die für uns geeignet sind, dann haben wir die Entscheidung getroffen das wir zusammen an Dashboard, Patientensuche und noch Unterstützung des Navigationsgraphen arbeiten, mit der Übernahme dieser Issues wollte ich zeigen, dass ich mich noch mehr in das Projekt einbringen wollte und auch bereit war, dafür deutlich mehr zu tun. Da habe ich richtig Gas gegeben und sehr viel Zeit in die Projektarbeit investiert, teilweise auch ganze Nächte verbraucht um die Probleme zubeheben, da Jetpack Compose relativ neu ist und im Internet keine hilfreichen Quellen gibt, und wir sollten ja das meiste selbst eine Lösung finden. Um ein läufiges und gutes Produkt am Ende des Sprints zu geben.

Insgesamt würde ich meiner Leistung eine 2,0 geben.

 


