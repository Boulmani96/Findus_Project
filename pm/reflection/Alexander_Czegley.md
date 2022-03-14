# Selbstreflexion: Alexander Czegley
## Wochen vom 27.10 bis 24.11 
### Arbeiten

- Einarbeitung in Kotlin, Kotlin Multiplatform

- Einarbeitung in MongoDB, KMongo, Ktor

- Ausführliche MongoDB Präsentation, die als Nachschlagwerk genutzt werden kann (Zusammen mit Gabriel Lukas Schön)

- Bearbeitung von Issue #5 

  - Implementierung eines MongoDB Controllers der die geforderten Akzeptanzkriterien erfüllt (Zusammen mit Gabriel Lukas Schön)
  - Viel code cleanup und refactoring (Zusammen mit Gabriel Lukas Schön)

- Bearbeitung von Issue #11

  - Implementierung eines MongoDB Atlas Controllers (Zusammen mit Gabriel Lukas Schön)
  - Mithilfe bei der Implementierung von Endpunkten für den Cloud-Dienst (Zusammen mit Gabriel Lukas Schön)
  - Mithilfe bei der Implementierung von Unit-Tests (Zusammen mit Gabriel Lukas Schön)
  - Dokumentation der Endpunkte mit OpenAPI
  - Viel code cleanup und refactoring (Zusammen mit Gabriel Lukas Schön)

- Behebung von Problemen mit dem initialen Projekt (Zusammen mit Gabriel Lukas Schön)

- Code Reviews durchgeführt

  - für Issue #10 (Zusammen mit Gabriel Lukas Schön)
  - für Issue #14 (Zusammen mit Gabriel Lukas Schön)
  

### Anmerkungen

In Issue #5 hat die Arbeit mit MongoDB in Kotlin eigentlich ganz gut funktioniert. Die Dokumentation von KMongo ist nicht gerade sehr ausführlich, was zu etwas Unsicherheit bei der Implementierung geführt hat.  
Die Arbeit mit KMongo kann bei komplexen Queries  problematisch werden, da es in der Dokumentation nicht genügend Beispiele gibt, die einem dort weiterhelfen. Die Arbeit mit Gridfs hat uns einige Probleme bereitet und uns mehr Zeit gekostet als ich ursprünglich geschätzt habe.  
Was uns ebenfalls viel Zeit gekostet hat, ist Android Studio. Wir haben viel zu lange versucht, das Issue in Android Studio zum Laufen zu kriegen. Hier hätten wir früher den Schlussstrich ziehen oder um Hilfe bitten müssen.

In Issue #11 haben wir zuerst geschaut, welche Technologie wir für den Cloud-Dienst verwenden. Hier haben wir unseren Fokus recht schnell auf DynamoDB von AWS gelegt.  
Hier haben wir uns letztendlich jedoch gegen DynamoDB entschieden, da die Preismodelle bei AWS extrem intransparent sind. So ist einem nicht wirklich ersichtlich, ob oder wie viel uns DynamoDB kosten würde.  
Des Weiteren gibt es bei AWS lauter versteckter Features, die standardmäßig aktiviert sind, aber den Preis in die Höhe treiben. Aufgrund der Intransparenz von AWS haben wir uns dann für MongoDB Atlas entschieden, was ich schon für das Live-Beispiel von unserer Präsentation getestet hatte.  
Die Arbeit mit MongoDB Atlas lief aufgrund unserer Vorarbeit mit MongoDB in Issue #5 sehr gut. Ktor hat uns hingegen einige Probleme bereitet.  
Die Dokumentation zum Testen von Ktor Endpunkten war nicht gerade sehr aufschlussreich und somit hatten wir einige Schwierigkeiten bei dem Schreiben von Tests.  


## Wochen vom 25.11 bis 15.12

### Arbeiten

- Bearbeitung von Issue #5
  - Versuch von Behebung des Docker-Problems (Zusammen mit Gabriel Lukas Schön)
- Meeting mit Colja wegen des Docker-Problems
- Meeting mit Frau Trapp und Colja wegen des Docker-Problems
- Bearbeitung von Issue #34
  - Umstrukturierung des Projekts (Verschiebung des KTOR-Servers mit Datenbankverwaltung in einen eigenen Ordner office_backend) (Zusammen mit Gabriel Lukas Schön und Tom Marek)
  - Lösung des Docker-Problems (Zusammen mit Gabriel Lukas Schön und Tom Marek)
- Bearbeitung von Issue #30
  - Integration von Koin (Zusammen mit Gabriel Lukas Schön)
  - Auslagerung des Connectionstrings der MongoDB in eine .env Datei (Zusammen mit Gabriel Lukas Schön)
  - ReadME um Anleitung für das lokale und globale Bauen ergänzt (Zusammen mit Gabriel Lukas Schön)
  - Implementation von Fail-Tests (Zusammen mit Gabriel Lukas Schön)
  - Erweiterte Argumentenprüfung (Zusammen mit Gabriel Lukas Schön)
  - Behebung eines Problems in Bezug mit dem Connectionstring (Zusammen mit Gabriel Lukas Schön)
- Code Reviews durchgeführt
  - erneut für Issue #14 (Zusammen mit Gabriel Lukas Schön)
- Bearbeitung von Issue #27
  - Gespräch mit Frau Piat bezüglich der Daten die unsere Cloud bereitstellen soll (Zusammen mit Gabriel Lukas Schön)
  - Erstellen der Issuebeschreibung und der Akzeptanzkriterien (Zusammen mit Gabriel Lukas Schön)
  - Erstellen einer Datenklasse gemäß der Beschreibung (Zusammen mit Gabriel Lukas Schön)
  - Implementierung der API-Routen und Endpunkte (Zusammen mit Gabriel Lukas Schön)
  - Implementierung einer weiteren Datenbankverwaltung für die Cloud (Zusammen mit Gabriel Lukas Schön)
  - Implementierung von Unit-Tests für den API-Service (Zusammen mit Gabriel Lukas Schön)
  - Implementierung von Unit-Tests für die Cloud-Datenbankverwaltung (Zusammen mit Gabriel Lukas Schön)
  - Docker-Funktionalität für die Cloud-Datenbankverwaltung (Zusammen mit Gabriel Lukas Schön)
  - Code cleanup und refactoring (Zusammen mit Gabriel Lukas Schön)
  - Implementierung von Diagnose und Upload Funktionalität aus Issue #11 (Zusammen mit Gabriel Lukas Schön)
  - Erstellung der OpenAPI Dokumentation (Zusammen mit Gabriel Lukas Schön)
- Code Reviews durchgeführt
  - für Issue #19 (Zusammen mit Gabriel Lukas Schön)


### Anmerkungen

In Issue #30 mussten wir einige Refactorings an der MongoDB vornehmen. Ein großer Punkt war hierbei die Integration von Koin. Hierfür mussten wir quasi noch einmal unsere komplette Struktur verändern und auf Koin anpassen.  
Dies hat, entgegen unserer Erwartung, erstaunlich gut funktioniert. Hier wäre es jedoch schön gewesen im Voraus bescheid zu Wissen, dass wir Koin verwenden sollen (hätte man in Issue #5 anmerken können).  
In Issue #34 haben wir einige Zeit mit Docker gekämpft. Nach einigen Gesprächen und mit etwas Zeit und Geduld haben wir auch dieses Problem lösen können. So haben die KTOR-API und die Cloud nun jeweils einen eigenen Docker-Container.
In Issue #27 haben wir zuerst alles so Implementiert, dass die Datenbankverwaltung die Anforderungen der Mathematiker:innen gerecht wird. Später hat sich herausgestellt, dass die Funktionen aus Issue #11 ebenfalls benötigt werden und wir haben die Datenbankverwaltung entsprechend angepasst.  
Bei unseren neuen Rollen (Githead und Feuerlöscher) haben wir uns sehr viel Mühe gegeben und schon einiges an Zeit investiert. Gerade die Rolle des Githeads nehmen wir sehr ernst und schauen sehr genau über die Merge-Requests. So haben wir zum Beispiel für Merge-Requests Nr. 33 und 34 jeweils neben dem Kommentieren des Codes noch eine ausführliche TODO-Liste mit bestehenden Problemen angefertigt.


### Rolle Githead

Übernahme der Rolle des Githeads in Team 2 zusammen mit Gabriel Lukas Schön  

- Bearbeitung von Merge-Request !24 (Zusammen mit Gabriel Lukas Schön)
- Bearbeitung von Merge-Request !33 (Zusammen mit Gabriel Lukas Schön)
- Bearbeitung von Merge-Request !34 (Zusammen mit Gabriel Lukas Schön)
- Bearbeitung von Merge-Request !35 (Zusammen mit Gabriel Lukas Schön)

### Rolle Feuerlöscher

Übernahme der Rolle des Feuerlöschers zusammen mit Gabriel Lukas Schön und Tom Marek  

- Unterstützung der Desktop und Android Gruppe (Implementierung von KTOR-API bei der Desktop Gruppe und update des Branches mit dem Master Stand bei der Android Gruppe) (Zusammen mit Gabriel Lukas Schön)
- Durchführen eines komplizierten Merges vom Masterbranch in den feat-14 Branch für Issue #14, nachdem die Gruppe für lange Zeit keinen Merge des Masters in ihren Featurebranch durchgeführt hatte (Zusammen mit Gabriel Lukas Schön)
- Der Android Gruppe bei einem kleinen Problem bei der Abfrage der KTOR-API geholfen  

Insgesamter Zeitaufwand für das Feuerlöschen: 7h


## Weihnachtspause vom 15.12 bis 11.01

- Installationsanleitung für MongoDB Atlas (Zusammen mit Gabriel Lukas Schön) Zeitaufwand: 1:20h

## Wochen vom 12.01 bis 2.02

### Arbeiten

- Bearbeitung von Issue #39
  - OpenAPI Routen implementiert (Zusammen mit Gabriel Lukas Schön)
  - API Routen getestet (Zusammen mit Gabriel Lukas Schön)
- Bearbeitung von Issue #49
  - Recherche nach Möglichkeiten zum Speichern von Dummydaten in MongoDB (Zusammen mit Gabriel Lukas Schön)
- Bearbeitung von Issue #48
  - Erstellung der Datenklassen zum Speichern der Bodymap (Zusammen mit Gabriel Lukas Schön)
  - CRUD Routen für die Bodymap implementiert (Zusammen mit Gabriel Lukas Schön)
  - Tests für die Routen geschrieben (Zusammen mit Gabriel Lukas Schön)
- Bearbeitung von Issue #44
  - Implementierung einer Route für die Dummydaten (Zusammen mit Gabriel Lukas Schön)
  - Anlegen mehrerer Datenklassen zum Speichern der Dummydaten (Zusammen mit Gabriel Lukas Schön)
  - Aufruf der Route im Onboarding (Zusammen mit Gabriel Lukas Schön)
- Code Reviews durchgeführt
  - für Merge-Request !39
  - für Issue #41 (Zusammen mit Gabriel Lukas Schön)
  - für Issue #32


### Anmerkungen

In Issue #39 sind wir bei der automatischen Erzeugung der OpenAPI Doku in einige Probleme gerannt. Letzten Endes sind wir auf das gleiche Problem wie in Issue #32 gestoßen.  
Hier haben wir zusammen mit Tom Marek vergeblich nach einer Lösung gesucht.  
Nach Absprache mit Frau Trapp und Colja haben wir uns auf eine "Hackerroute" einigen können, bei der die Route nicht mit dem Framework der automatischen OpenAPI Doku geschrieben wurde.  


### Rolle Githead

- Bearbeitung von Merge-Request !36 (Zusammen mit Gabriel Lukas Schön)
- Bearbeitung von Merge-Request !37
- Bearbeitung von Merge-Request !41 (Zusammen mit Gabriel Lukas Schön)
- Bearbeitung von Merge-Request !42 (Zusammen mit Gabriel Lukas Schön)
- Bearbeitung von Merge-Request !44 (Zusammen mit Gabriel Lukas Schön)
- Bearbeitung von Merge-Request !45
- Bearbeitung von Merge-Request !46 (Zusammen mit Gabriel Lukas Schön)

### Rolle Feuerlöscher

- Der Dashboard Gruppe die Verwendung der autogenerierten OpenAPI erklärt
- Der Dashboard Gruppe bei einem Serialisierungsproblem im Frontend geholfen (Zusammen mit Gabriel Lukas Schön)
- Bei der Implementierung der Anamnese geholfen verschiedene Probleme zu lösen (Zusammen mit Gabriel Lukas Schön)


## Selbsteinschätzung

Zum Start musste ich mich erst einmal in das Projekt einarbeiten. Hierfür hatte ich, zusammen mit Gabriel Lukas Schön, eine Präsentation über MongoDB erstellt und gehalten.
Da wir uns durch die Präsentation schon etwas in das Thema eingearbeitet hatten, übernahmen wir auch die Implementierung der MongoDB. Ab diesem Zeitpunkt hatten Gabriel Lukas Schön und ich gemeinsam sehr viel an diesem Projekt gearbeitet.
Die darauffolgenden Issues hatten wir größtenteils zusammen bearbeitet. Nach kurzer Zeit hatten wir mit dem Tool IntelliJ für uns einen guten Workflow für die Zusammenarbeit gefunden.
So hatten wir die Funktion "Code with me" von IntelliJ für all unsere Backend Issues verwendet, um gleichzeitig zusammenarbeiten zu können. Zusammen mit Gabriel Lukas Schön hatte ich seit Anfang an sehr viel Zeit in das Projekt investiert.
Wir hatten bei der Issue-Wahl immer darauf geachtet, dass wir uns genug in das Projekt einbringen und nicht nur kleinere Issues bearbeiten. Nach dem ersten Sprint hatte ich zusammen mit Gabriel Lukas Schön die Rolle des Githeads übernommen.
Ich hatte ebenfalls mit Tom Marek und Gabriel Lukas schön die neue Rolle des Feuerlöschers übernommen. Mit der Übernahme dieser Positionen wollte ich zeigen, dass ich mich noch mehr in das Projekt einbringen wollte und auch bereit war, dafür deutlich mehr zu tun.
Die wöchentlichen 7-10 Stunden hatte ich eigentlich immer weit überschritten, so gab es durchaus auch mal Wochen mit 30+ Stunden. Hier hatte ich, gerade auch in Zusammenarbeit mit Gabriel Lukas Schön, sehr viel Zeit in die Projektarbeit investiert.
Unsere Arbeit kann man auch sehr gut in dem Backend Teil des Projektes sehen. Hier hatten Gabriel und ich das Cloud Backend, sowie das Office Backend mit Tom Marek alleine geschrieben. Des Weiteren hatten wir schon von Projektbeginn an viele Tests geschrieben, um die gewünschten Funktionalitäten und Qualität sicher zu gewährleisten.
Gerade auch in der Rolle des Feuerlöschers hatte ich ebenfalls immer versucht, den anderen Projektmitgliedern bei Problemen zu helfen. Die Rolle des Githeads haben Gabriel Lukas Schön und ich sehr ernst genommen. Hier hatten wir sehr darauf geachtet, dass der Code ordentlich ist und eine entsprechende Qualität hat.
Hier denke ich, hatten wir auch einen guten Einfluss auf die anderen Gruppenmitglieder. So finde ich, dass man deutlich gemerkt hat, wie sich die anderen Projektmitglieder mit der Zeit verbessert haben und auch selbstständig angefangen haben, zum Beispiel magic strings, magic numbers usw. auszulagern.
Die zusätzliche Übernahme der Funktionen Githead und Feuerlöscher im Projekt erklärt auch den größeren Stundenumfang.  
Aufgrund der genannten Punkte und Aufgaben würde ich mir selbst eine 1,0 geben.
