# Selbstreflexion: Gabriel Lukas Schön
## Wochen vom 27.10 bis 10.11

### Arbeiten
- Einarbeitung in Kotlin, Kotlin Multiplatform
- Einarbeitung in MongoDB, KMongo, Ktor
- Bearbeitung Issue #5
  - Implementierung eines Controllers für eine MongoDB-Instanz, gemäß Akzeptanzkriterien (zusammen mit Alexander Czegley)
  - Implementation von Tests
- MongoDB Präsentationsfolien als Nachschlagewerk erweitert (zusammen mit Alexander Czegley)

### Anmerkungen
- Die Arbeit mit MongoDB in Kotlin funktioniert mit KMongo soweit gut, die Dokumentation
  ist jedoch teilweise ausbaufähig. Besonders die Verwendung von Gridfs ist nicht allzu einfach,
  da hierzu quasi keine Dokumentation für Kotlin vorhanden ist, auch Beispiele lassen sich nur
  schwer finden. Somit habe ich an einigen Problemen zu viel Zeit verloren, die eigentlich mit einer
  passenden Dokumentation in einer deutlich kürzen Zeit zu bearbeiten gewesen wären. Hier hätte ich mir schneller
  Hilfe suchen sollen, um die Zeit effektiver zu nutzen.

## Wochen vom 11.11 bis 24.11

### Arbeiten

- Bearbeitung Issue #5 (jeweils zusammen mit Alexander Czegley)
  - Refactorings und Vereinfachung des Datenbankcontrollers
  - Viele Code-Cleanup arbeiten, Verbesserung der Codequalität und Lesbarkeit
  - Einarbeitung des Feedbacks, welches im Code-Review aufgekommen ist
- Bearbeitung Issue #11 (jeweils zusammen mit Alexander Czegley)
  - Recherche einer geeigneten Technologie
  - Erweiterung und Anpassung des in Issue #5 verwendeten Datenbankcontrollers, sodass dieser auch für die Cloud-Datenbank (MongoDB Atlas)
    verwendet werden kann
  - Implementierung der API-Routen und Endpunkte für den Cloud-Service
  - Implementierung von Unit-Tests für den API-Service
  - Code-Cleanup und Qualitätsverbesserung des Codes
- Identifizierung und Behebung von Problemen im initialen Projekt (Zusammen mit Alexander Czegley)
- Code Reviews durchgeführt (jeweils zusammen mit Alexander Czegley)
  - für Issue #10
  - für Issue #14

### Anmerkungen
- Für Issue #5 mussten wir den Datenbankcontroller noch einmal ummodellieren, damit dieser eine Verbindung zu einer Datenbank
  in einem Docker-Container aufbauen kann. Bei dieser Ummodellierung zur Verbindung mit einem Docker-Container statt einer lokal laufenden MongoDB
  Instanz sind wir in einige, für uns unerklärliche Fehler beim eigentlich automatischen Mappen mit KMongo gelaufen, die die Arbeiten mal wieder
  unnötig in die Länge gezogen haben. Leider haben wir den Mongo-Dockercontainer auch erst sehr spät (am 19.11) bereitgestellt bekommen,
  sodass wir unter großem Zeitdruck standen, den Datenbankcontroller anzupassen und möglichst schnell für die anderen Gruppen bereitzustellen.
- Für Issue #11 mussten wir zunächst eine geeignete Technologie zum Speichern der anonymisierten Datensätze suchen. Hierzu haben wir uns zunächst
  auf DynamoDB von AWS festgelegt und uns in diese Technologie eingearbeitet. Leider mussten wir dann jedoch schnell feststellen, dass das
  AWS Preismodell sehr intransparent gestalt ist, sodass wir im Endeffekt von DynamoDB auf eine MongoDB Atlas Instanz gewechselt sind
  und die Einarbeitung in DynamoDB für das Projekt umsonst war. Durch den bereits existierenden Datenbankcontroller aus Issue #5 mussten nur noch
  wenige Anpasungen vorgenommen werden, um die MongoDB Atlas Instanz in unser Kotlin-Projekt einbinden zu können.
  Größere Probleme gab es beim Empfangen der Bilder, die als Multipart empfangen werden mussten. Während die Implementierung noch im angedachten
  Zeitrahmen lag, erwies sich das Schreiben von Tests als sehr schwierig, da die Testdokumentation für komplexere Strukturen
  wie Dateien nicht sehr hilfreich war.

## Wochen vom 25.11 bis 08.12

### Arbeiten
- Bearbeitung Issue #30 (alles Zusammen mit Alexander Czegley)
  - Vollständiger Umbau des "Datenbankcontrollers" aus Issue #5. Hierbei haben wir quasi den gesamten Code aus Issue #5
  - Ergänzen von verschiedenen Argumentprüfungen für die bereitgestellten Methoden
    abgeändert und die Struktur auf einen Service und ein Repository angepasst
  - Integration von Koin und bereitstellen der Funktionalität der Datenbankverwaltung in einem Modul
  - Verschieben der verwendeten Dateien in einen Unterordner der Ktor-API innerhalb des office_backend Teils
  - Anpassen der KTOR-API an die neue Struktur der Datenbankverwaltung, sodass die API weiterhin auf die Datenbank zugreifen kann
  - Auslagern der Verbindungsinformationen zur Datenbank in ein .env File
  - Umfangreiche Ergänzung der Unit-Tests mit vielen (fail)-Tests
  - Einarbeitung des Feedbacks, welches im Code-Review für dieses Issue aufgekommen ist (Zusammen mit Alexander Czegley)
- Bearbeitung Issue #27 (alles Zusammen mit Alexander Czegley)
  - Treffen mit der Mathematiker:innen Gruppe, um die Anforderungen für den Cloud-Service zu besprechen
  - Erstellen der Issuebeschreibung und der Akzeptanzkriterien für Issue #27 nach Analyse der Anforderungen der Mathematiker:innen
  - Implementierung der API-Routen und Endpunkte für den Cloud-Service-Server
  - Erstellen eines einer weiteren Datenbankverwaltung mit einem weiteren Repository und Service für die Cloud
  - Implementierung von Unit-Tests für den API-Service
  - Implementierung von Unit-Tests für die Cloud-Datenbankverwaltung
  - Code-Cleanup und Qualitätsverbesserung des Codes
  - Erstellen eines Dockerfile und Ergänzen des Docker-Compose files für einen Cloud-Docker container
- Bearbeitung Issue #11: (alles Zusammen mit Alexander Czegley)
  - Die Funktionalität für die Diagnose und den Upload von Bildern wurde in den neuen Cloud-Server(#27) überführt
- Bearbeitung des Issues #34 (Zusammen mit Alexander Czegley, Tom Marek)
  - Das Projekt wurde Umstrukturiert: Verschieben des Ktor-Server mit Datenbankverwaltung in einen Unterordner office_backend
  - Docker File und Docker Compose File wurde verbessert so, dass die Services untereinander kommunizieren können und richtig gebaut werden
  - Viele Probleme mit Gradel wurden behoben so, dass das Projekt jetzt mit Gradel außerhalb und innerhalb des Docker Container bebaut werden kann
- Code Review durchgeführt (Zusammen mit Alexander Czegley)
  - Erneutes Review von Issue #14
## Rolle Githead
- Übernahme der Rolle des Githeads für Team2 zusammen mit Alexander Czegley
- Feedback zum Merge-Request Nr.24 gegeben, nach Nacharbeit der Assignees merge dieses Requests in den Master (Zusammen mit Alexander Czegley)

## Rolle Feuerlöscher
- Übernahme der Rolle "Feuerlöscher" zusammen mit Alexander Czegley, Tom Marek
- Arbeiten als Feuerlöscher: (jeweils zusammen mit Alexander Czegley)
  - Unterstützung der Android, Desktop Gruppe bei der Anbindung an die API und Datenbank (Zusammen mit Alexander Czegley, Tom Marek)
  - Durchführen eines komplizierteren Merges vom Masterbranch in den feat-14 Branch für Issue #14, nachdem die Gruppe für lange Zeit keinen Merge des Masters in
    ihren Featurebranch durchgeführt hatte
  - Den Assignees von Issue #7 dabei geholfen, Koin richtig einzubinden und zu starten, um die Findus-API aus dem Common-Ordner verwenden zu können
  - Den Assignees von Issue #7 dabei geholfen, ein Netzwerkproblem in ihrem App-Prototypen zu lösen
- Insgesamter Zeitaufwand für die Rolle im angegebenen Zeitraum: 8 Stunden

### Anmerkungen
- Für das Issue "Initiales Projekt läuft" haben wir viel Zeit verbraucht und viele Meetings benötigt. Von daher sind die Teammitglieder,
  die an diesem Issue gearbeitet haben, umso glücklicher darüber, dass die in diesem Issue aufgekommenen Probleme und auch bereits vorher selbst identifizierten Probleme
  (Docker läuft nicht) endlich behoben werden konnten.
- Für das Issue #30 haben wir unseren bisherigen Aufbau des Datenbankcontrollers vollständig überarbeitet. Auch dies hat viel Zeit in Anspruch genommen,
  da wir uns zusätzlich in ein für uns neue Thema Koin und dependency-injection einarbeiten mussten. Dank eines guten Beispielprojekts, welches auch im readme
  des office_backend Teilprojekts verlinkt ist, konnten wir aber auch dieser Herausforderung begegnen.
- Der Cloud Service wurde doch umfangreicher als gedacht, insbesondere war es für uns eine Herausforderung, die genauen Anforderungen der Mathematiker:innen
  zu verstehen und diese passend in einem Issue zu beschreiben. Weiterhin mussten wir versuchen, die Anforderungen aus Issue #11 in diesen neuen Cloud-Service einzuarbeiten.
- Die Zusammenarbeit unter den "Feuerlöschern" hat meiner Meinung nach gut funktioniert und wir konnten Wanderern Gruppen dabei helfen,
  einige Probleme schnell zu beheben. Diese neu geschaffenen Rollen wurden auch im Team, soweit ich das beurteilen kann, gut angenommen.
- Insgesamt denke ich auch, dass sich die Kommunikation im gesamten Team verbessert hat

## Woche vom 09.11 bis 14.12

### Arbeiten
- Issue #30
  - Kleinere Fixes und Optimierungen für Issue #30
-	 Zusammen mit Alexander Czegley: Lösen eines Docker-Problems im Zusammenhang mit der Datenbankverbindung,
      bei der der Connectionstring nicht mehr richtig verwendet werden konnte.
- Issue #27, #11 (Zusammen mit Alexander Czegley)
  - Fixes und Codeverbesserungen
  - Umsetzen der Anmerkungen aus dem Code-Review
  - Umbauten am Routing
  - OpenAPI Documentation erstellt
  - Unit-Tests ergänzt
- Code Review durchgeführt (Zusammen mit Alexander Czegley)
  - für Issue #19
## Rolle Githead

- Bearbeitung von Merge-Requests (Zusammen mit Alexander Czegley)
  - !33
  - !34
  - !35
- Zeitaufwand für diese Woche als Githead: 8h (Siehe genauere Erläuterung der Arbeiten als Githead für diese Woche in den Anmerkungen)

## Rolle Feuerlöscher
- Unterstützen der Teams von #7 und #8 bei einigen verbleibenden Problemen bei der Anbindung der Desktop-App und der Android App an die KTOR-API. Hierzu musste ich insbesondere ein
  nicht ganz einfach zu durchschauendes Serialisierungsproblem bei den Aufrufen der API und der Verarbeitung der Antworten der API im Programmcode lösen.
- Zeitaufwand für die Rolle Feuerlöscher in dieser Woche: 3,5h

### Anmerkungen
- Für die Merge-Requests !33 und !34 haben wir sehr ausführliches Feedback gegeben, was aus unserer Sicht noch verbessert werden sollte, bevor wir den Code
  auf den Master mergen können. Hierzu haben wir die Android-App sowie die Desktop-App sehr genau geprüft und viele verschiedene Aktionen ausgeführt um zu prüfen,
  ob sich die Apps richtig verhalten. Aus dieser Prüfung haben wir für beide Requests  jeweils eine ausführliche TODO Liste erstellt, die umgesetzt werden sollte, bevor der Code erneut geprüft wird.
  Weiterhin mussten wir zusätzlich zu der TODO-Liste noch viele Anmerkungen und Kommentare am Code selbst vermerken.
- Im Merge-Request #35 haben wir zunächst die verbesserten Versionen der issues #7, #8 (d.h. die Issues zu den Merge-Requests !33, !34) auf ihre Korrektheit geprüft.
  In diesen Code-Version ist bereits gut nachgearbeitet worden, sodass wir in Zusammenarbeit mit den Teammitgliedern, von Issue #7 und #8 abgesprochen haben, wie der Code noch weiter verbessert
  werden kann und wie das Mergen funktionieren soll. Hierzu habe ich zunächst einen neuen Branch feat/7-8-verticalPrototypesMerge erstellt und dann die Branches, auf denen die Issues #7 und #8 bearbeitet wurden
  für die Teammitglieder gemerged und merge-Konflikte gelöst. Anschließend habe ich mit Alexander Czegley noch einmal in einem gemeinsamen Treffen Feedback gegeben, diese Anmerkungen wurden dann von
  den beteiligten Teammitgliedern im Code umgesetzt. Zuletzt konnte ich mit Alexander Czegley den Merge-Request annehmen, nachdem wir noch einmal alles genau geprüft hatten.

## Weihnachtspause (15.12-11.01)
- Mongo Atlas Tutorial/Installationsanleitung in cloud backend readme ergänzt (Zusammen mit Alexander Czegley) (1h 30min)
- Arbeit an Issue #39 Ktor-Dokumentation für den Cloud-Dienst begonnen (6h)

## Woche vom 12.01 bis 18.01

### Arbeiten
- Issue #39 bearbeitet (Zusammen mit Alexander Czegley)
  - OpenAPI Routen implementiert
  - Routen getestet
- Issue #49 bearbeitet (Zusammen mit Alexander Czegley)
- Durchgeführte Code Reviews: (Zeitaufwand: 4h)
  - !37 (Draft: Feat/36 charts in android) (2x durchgeführt)
  - !41 (Draft: Feat/32 ktor docs) (Zusammen mit Alexander Czegley)

## Rolle Githead
- Bearbeitung von Merge-Requests
  - !39
  - !36
- Zeitaufwand für diese Woche als Githead: 2h

## Rolle Feuerlöscher
- Problem in #37 gelöst (App konnte nicht mehr gestarter werden)(45 Minuten)
- Teilnahme an "Krisentreffen" für Issue #24 (1h 30 Minuten), Angeboten ab dem Wochenende (21/22.01) bei der Bearbeitung zu helfen

### Anmerkungen
Die OpenAPI Dokumentation für #39 mithilfe des [Frameworks](https://github.com/papsign/Ktor-OpenAPI-Generator) hat bei uns einige Probleme verursacht.
Zum einen ist dieses nur sehr dürftig dokumentiert, zum anderen ist die Syntax und der Aufbau der Routen gewöhnungsbedürtig. Deshalb sind wir
bei der Implementierung auf einige Fehler gestoßen, die wir trotz der Vorlage aus #32 nur mit einem hohen Such- und Zeitaufwand beheben konnten.
Im Späteren Verlauf sind wir dann ebenso wie bei #32 auf den Fehler im Datei Upload gestoßen. Für diesen Fehler haben wir gemeinsam mit Tom von #32 versucht eine Lösung
zu finden, alle unsere Versuche haben aber letztendlich zu keinem Erfolg geführt, weshalb am 19.01 in Absprache beschlossen wurde, diese Route aus der automatischen Generierung
auszulagern und als "normale" Ktor-Route zu implementieren.

## Woche vom 19.01 bis 02.02

### Arbeiten
- Issue #39 bearbeitet
  - Image upload Route wegen Framework-Problemen gemäß Absprache als normale KTOR-Route ausgelagert und neu getestet
  - Anmerkungen aus Code Review umgesetzt
- Für Issue #32 und #39 versucht, ein Problem mit der temporären Erzeugung von Dateien bei der Verwendung von GridFS zu lösen
- Issue #48 bearbeitet
  - Routen angelegt
  - Tests ergänzt
  - Absprache, wie Patient-Datenklasse mit Anamnesen und BodyMaps im Backend zusammengeführt werden (zwischen #48 und #25)
- Issue #44
  - DummyRouten angelegt
  - Datenbankfunktionalität zum Prüfen von Datensätzen ergänzt
  - Patientservice um Methode erweitert, welche die Dummydaten in die Datenbank speichert
  - Test ergänzt
  - Beim erstellen der konkreten Dummydaten geholfen
  - Frontend Aufruf zum Erzeugen der Dummydaten beim ersten Starten der App im Onboarding ergänzt
- Issue #24 #25:
  - Datenklassen und Routen im Backend erweitert und angepasst
  - Datenbank-und Routentests an die neuen Datenklassen angepasst
- Issue #50 bearbeitet
  - im Frontend FindusAPI mit allen benötigten Routenaufrufen des Backends angelegt
  - Findusrepository erstellt, welches die Zugriffe auf die FindusAPI verwaltet
  - Funktionalität geprüft
- Issue #24 in Feuerwehrrolle ausgeholfen und Funktionalität zum Speichern der Bodymap als Bild ergänzt

## Rolle Githead
- Bearbeitung von Merge-Requests
  - !50
  - !42
  - !43
  - !16
  - !44
  - !38
- Zeitaufwand für diese Wochen als Githead: 5h

## Rolle Feuerlöscher
- Für #23: Ein Serialisierungsproblem im Frontend behoben, welches dazu geführt hatte, dass keine Patienten geladen werden können und stattdessen
  eine Serialisierungs-Exception geworfen wurde (1h 30min)
- Mehrere Treffen mit den Scrum-Mastern gehabt, in denen abgesprochen wurde welche Arbeiten im Projekt noch von den Feuerlöschern übernommen werden müssen,
  um das Projekt zu einem guten Ende zu führen (2h)
- Auf dieser Basis haben wir herausgearbeitet, dass insbesondere die Kommunikation zwischen Frontend- und Backend Probleme bereiten wird, da noch
  keine Personen dafür eingeteilt waren, diese Verbindung herzustellen. Deshalb habe ich zusätzlich zu meinen zugeteilten Issues noch #50 erstellt und zusammen mit Tom Marek
  bearbeitet, um diese Funktionalität bereitzustellen. (24h)
- Diverese Hilfestellung zur Verwendung des Findus-Repository im Frontend sowie bei der Arbeit mit den Containern und dem Backend. (1h)

### Anmerkungen
In den letzten beiden Wochen habe ich noch einmal sehr viel Zeit (siehe Zeittracking der übernommenen Issues und die zusätzlich hier dokumentierte Zeit in der Feuerlöscherrolle) in das Projekt
investiert, um meinem Beitrag dazu zu leisten, das Projekt zu einem guten Ende zu führen. Insbesondere die Implementation der Klasse FindusAPI sowie des Repositories im Frontend
war sehr aufwändig, da erst bei der Erstellung der Frontend Findus-API einige Probleme bei der Kommunikation mit den überarbeiteten Backendrouten aufgetreten sind, die beim
Testen des Backends so nicht vorhersehbar waren und dort auch nicht aufgetreten sind. Weiterhin gab es von Robin mehrmals anfragen, die neu angelegten Backend-Datenklassen (Organ, Muskel, Anamnese etc.) noch einmal anzupassen,
da bei der Analyse der Anforderungen Attribute dieser Klasse übersehen wurden, die dann im Nachhinein zu diesen Datenklassen hinzugefügt wurden. Auch dies war sehr zeitaufwändig, da nach jeder dieser Änderungen
die Funktionalität der angelegten Routen an diese geänderten Datenklassen angepasst werden mussten, ebenso die Datenbank-und Routentests im Backend. Da wir das Backend sehr ausführlich getestet haben (die beiden Testdateien
haben zusammen ca. 3500 Codezeilen), war diese Arbeit sehr mühsam. Des Weiteren habe ich in der Feuerlöscherrolle noch diverse Probleme behoben(Siehe Rolle Feuerlöscher), die meisten davon ließen sich relativ schnell lösen,
nur ein Problem mit der Deserialisierung der empfangenen Datenklassen im Frontend hat einige Zeit gekostet, da lange Zeit nicht genau klar war, woher diese Exception stammt und wie sie gelöst werden kann. Letztendlich konnte auch dieses Problem
in der Zusammenarbeit mit meinem Feuerlöscherkollegen Alexander Czegley gelöst werden.


# Feedback zum Projekt
- Frau Trapp hat ihre PO-Rolle sehr glaubhaft dargestellt, wodurch sich viele Lernmöglichkeiten ergeben haben. Ich habe auf jeden Fall einen sehr guten Eindruck davon bekommen, wie ein solcher Projekt im späteren Arbeitsleben ablaufen kann
- Colja und Clarissa haben bei der Schätzung der Issues und auch generell beim SCRUM-Prozess viele hilfreiche Tipps gegeben
- Aus meiner Sicht hätte die Rolle der Betreuer klarer definiert werden sollen, diese war, soweit ich es im Team mitbekommen habe, insbesondere im ersten Sprint sehr unklar

# Zusammenfassung und Selbsteinschätzung

## Was habe ich gelernt
Im Laufe des Projekts konnte ich viele neue Fähigkeiten erwerben, die mir auch in Zukunft von Nutzen sein werden.
Hierzu zählt das Arbeiten in einem SCRUM-Team, bei dem ich die Arbeitsweise bisher nur aus der Theorie kannte.
Zu sehen wie die einzelnen Rollen aus SCRUM-Master Githead etc. ineinandergreifen war sehr lehrreich. Da ich nach dem ersten Sprint die Githead Rolle für Team2 zusammen mit Alexander Czegley übernommen habe,
konnte ich auch hier viele Fähigkeiten erlernen sowie kennenlernen auf was in dieser Rolle geachtet werden muss. Dies trifft einerseits auf die fachliche Sicht zu, in der ich eine bessere Einschätzung entwickelt habe,
welchen Qualität im Code vorliegen muss damit dieser auf den Master-Branch gemerged werden kann. Anderseits konnte ich in dieser Rolle auch auf persönlicher Ebene lernen, wie ich Verbesserungsvorschläge im Code, oder auch Probleme, die mir im Code
von anderen Teammitgliedern aufgefallen sind, am besten an die jeweiligen Teammitglieder kommunizieren kann. Ähnliche Erfahrungen habe ich auch bei den von mir zahlreich übernommenen Code-Reviews gemacht.
Des Weiteren konnte ich lernen, wie wichtig eine gute Kommunikation in größeren Projekten innerhalb des Teams ist, um gemeinsam zu einem Ziel zu kommen.

Auch aus fachlicher Sicht konnte ich einige neuen Dinge lernen. Hierzu zählt als Erstes das Aufsetzen und Verwenden von NoSQL Datenbanken. Im Laufe des Projekts konnte ich dabei viel Erfahrung mit der Dokumentendatenbank
MongoDB und dem zugehörigen toolkit KMongo sammeln. Auch wenn bei der Bearbeitung des Issues (#5, #30) immer wieder kleinere Probleme, mit der Datenbank aufgetreten sind, habe ich im Laufe des Projekts
ein immer tieferes Verständnis diese Art von Datenbanken entwickelt. Auch wahr es sehr lehrreich die GridFS Technologie im Zusammenhang mit MongoDB zu verwenden, hierbei hatten Alexander Czegley und ich zunächst große
Probleme, den Bildupload und Download zu realisieren. Es gibt zwar viele Java Beispiele zu GridFS, im Zusammenhang mit Kotlin gibt es jedoch quasi überhaupt keine Beispiele, an denen eine Orientierung möglich gewesen wäre.
Auch wenn es an dieser Stelle natürlich komfortabler gewesen wäre, einige GridFS Beispiele in Kotlin zu haben, konnte ich somit lernen, wie ich mit nicht-trivialen Bibliotheken umgehen kann, zu denen es keine ausführliche Dokumentation oder Beispiele gibt.
Für #11 habe ich mich noch einmal mit No-SQL Datenbanken beschäftigt, hierbei habe ich mich zusammen mit Alexander Czegley in die Grundlagen von AWS eingearbeitet, auch wenn wir dann letztendlich aufgrund einer intransparenten Preisgestaltung
doch auch auf eine selbst konfigurierte MongoAtlas Instanz umgestiegen sind.

Weiterhin konnte ich mit Kotlin eine weitere objektorientierte Programmiersprache kennenlernen. 
Mit dem Aufsetzen des Cloud-Backends (#11, #27) sowie der Arbeit am Office-Backend (#44, #50, #48) konnte ich weiterhin
den Umgang mit Ktor lernen, um Client-Server Applikationen aufzusetzen.
Später kam dann noch mit #39 das Framework OpenAPI hinzu, bei dem die Funktions- und Arbeitsweise auch erst erlernt werden musste und uns einiges abverlangt hat(Siehe Fileupload-Problem, welches letztendlich nur
damit gelöst werden konnte, diese Route als reguläre Ktor-Route ohne OpenAPI-Autogenerierung anzulegen).
Im letzten Sprint habe ich dann noch für #24 die Speicherung der Bodymap als JPG-Bild übernommen. Bei der Bearbeitung dieses Issues und in meiner Rolle als Feuerlöscher konnte ich somit
auch noch einige Erfahrungen mit Jetpack Compose sammeln.
Im Zuge von #30 habe ich mich weiterhin mit dem Prinzip von Dependency Injection auseinandergesetzt und mich darin in Form von Koin eingearbeitet.


## Was hätte ich besser machen können?
Zumindest am Anfang hätte ich in meiner Githeadrolle noch klarer machen sollen, dass es nicht um eine persönliche Kritik geht, wenn ich Verbesserungsvorschläge am Code vorgenommen habe, sondern darum,
möglichst qualitativ hochwertigen Code auf dem Master-Branch zu haben. Dies ist mir wie bereits oben beschrieben im Laufe des Projekts immer besser gelungen.

## Was ist mir im Projekt gut gelungen?
- Alle angenommenen Issues konnten zu einem Abschluss geführt werden
- Sehr gute Zusammenarbeit an den zugeteilten Issues, meistens zusammen mit Alexander Czegley, einige auch mit Tom Marek
- Eine aus meiner Sicht unkomplizierte Kommunikation zwischen den Feuerlöschern (Tom Marek, Alexander Czegley und mir), wie aufgetretene Probleme am besten gelöst werden können und gemeinsames Lösen dieser Probleme
- Ich habe in meiner Githeadrolle versucht die gestellten Merge-Requests zügig zu bearbeiten und den Assignees schnelles Feedback zu geben, wenn mir noch Probleme aufgefallen sind, sodass die jeweiligen Assignees nacharbeiten konnten
- Kommunikation zwischen uns Feuerlöschern und dem Team ist ebenfalls gut gelaufen, zumindest wurde dieses Hilfeangebot häufig angenommen und viele Probleme konnten schnell und unkompliziert gelöst werden, sodass die jeweiligen Assignees an ihren Issues weiterarbeiten konnten.
  Auch die Weitergabe des jeweiligen Status zu Problemen zwischen uns Feuerlöschern und Githeads ist sehr unkompliziert gelaufen.


## Selbsteinschätzung
Zuletzt möchte ich unter Beachtung der angegebenen Kriterien zur Bewertung meine Leistung im Projekt zusammenfassen. Unter [Was habe ich gelernt](#was-habe-ich-gelernt)
habe ich bereits beschrieben, mit welchen Technologien und Frameworks ich mich im Laufe des Projekts auseinandergesetzt habe.
Die mir zugeteilten Issues konnten alle erfolgreich abgeschlossen werden. Hierbei habe ich bei der Verwendung von Gitlab stets darauf geachtet, unseren Gitlab-Workflow
einzuhalten. Dieses Einhalten umfasst neben den Commitkommentaren sowie dem Taggen des Issues, welches von diesem Commit betroffen ist und der passenden Benennung der Branches, auch das Refactoring und Aufräumen des selbstgeschriebenen
Codes, sodass dieser der Definition of Done entspricht, bevor ein Merge-Request für den geschriebenen Code erstellt wurde. Im Rahmen meiner Githeadrolle habe ich stets
darauf geachtet, nur Code mit einem Stand, der der Definition of Done entspricht, auf den Master zu mergen.
Bei Problemen, die bei der Bearbeitung der Issues aufgetreten sind, habe ich stets versucht, nach Möglichkeit selbstständig mit meinen Bearbeitungspartnern eine Lösung zu finden, wenn sich jedoch
abgezeichnet hat, dass das jeweilige Problem ohne Hilfe nicht zu lösen ist oder eine Absprache bezüglich eines Issues nötig ist, habe ich dies stets unter dem Issue vermerkt und an den Besprechungterminen
Vorschläge gemacht, wie ein Problem angegangen werden kann (Siehe FileUpload-Problem mit OpenAPI sowie der Frage, wie Dummydaten am besten für die MongoDB erstellt werden können).

Insgesamt denke ich, mit meiner Leistung einen erheblichen Beitrag zum Erfolg des Projekts beigetragen zu haben.
Wie kommt diese Einschätzung zustande?
Zum einen möchte ich auf die reine Anzahl der Issues hinweisen, die im Laufe des Projekts von mir bearbeitet wurden (12 Stück), was deutlich mehr als das ist, was viele
anderen Projektteilnehmer übernommen haben. Dies spiegelt sich auch in meinem Zeitaufwand für das Projekt (sowie der reinen Commitanzahl) wider. Es war während des Projekts keine Seltenheit, dass ich zusammen mit Alexander Czegley
auch jeweils 30 Stunden und mehr in der Woche in das Projekt investiert haben, um unsere Issues abzuarbeiten und unseren weiteren Rollen(Githead, Feuerlöscher) gerecht zu werden.
Dieser Einsatz lässt sich auch im aktuellen Projektstand gut dadurch nachvollziehen, dass derzeit der gesamte Praxis-Backendteil mit Ktor-Server, Datenbankbankverwaltung von nur 3 Teammitgliedern (Tom Marek, Alexander Czegley und Mir)
erstellt wurde. Zusätzlich haben Alexander Czegley und ich auch noch den gesamten Backendteil (Ktor-Server und Datenbank) des Cloud-Services zu zweit erstellt. 
Tom, Alexander C. und ich sind auch die einzigen, die ausführliche Tests für ihre umgesetzte Funktionalität geschriebenen haben. Alleine im Office-Backend haben wir für die Datenbank und Routen über 3500 Codezeilen an Tests geschrieben, durch das Cloud-Backend kommen noch einmal 1100 Codezeilen an Tests hinzu.



Zum anderen war ich stets daran interessiert, das Projekt sinnvoll voranzubringen. Deshalb habe ich auch während den laufenden
Sprints neue Issues, die sich in Absprache mit den Betreuern ergeben haben, z.B. der Frage, wie Dummydaten bei MongoDB eingefügt werden können (#49) selbstständig erstellt
und zusätzlich zu meinen zugeteilten Issues bearbeitet. Des Weiteren überlegte ich, welche Features für die App noch sinnvoll umzusetzten wären und habe diese
aktiv vorgeschlagen sowie in Absprache mit Fr. Trapp auch als neue Issues formuliert (Siehe #39, #40).

Zusätzlich habe ich versucht, mich auch über die reine Bearbeitung der Issues hinaus sinnvoll in das Projekt einzubringen.
Dies umfasst neben dem Anbieten und Durchführen zahlreicher Code-Reviews(siehe Dokumentation weiter oben) noch viele weitere Aspekte:
Wenn mir Probleme im Projekt aufgefallen sind, wie etwa zum Ende des ersten Sprints der nicht-lauffähige src-Ordner im Projekt aufgrund der sich zahlreichen Gradle-Dependencies in den Subprojekten, sowie ein Docker-Problem, was zu einem
nicht lauffähigem Backend-Ktor-Server geführt hat, habe ich diese Probleme nicht von mir geschoben. Stattdessen kommunizierte ich diese Probleme sowohl im Team als auch an die Betreuer (Resultat war dann z.B #34) und versucht die Probleme
aktiv zu lösen. Auch diese Arbeiten gehen natürlich über das "stumpfe"
Abarbeiten von zugeteilten Issues hinaus und zeigen die Bereitschaft, auch bei nicht zugeteilten Issues zu helfen.

Weiterhin habe ich nach dem ersten Sprint die Githead Rolle für Team 2 zusammen mit Alexander Czegley übernommen und viele Merge-Requests erfolgreich alleine und
zu zweit bearbeitet. Hierbei waren wir stets darauf bedacht, einen möglichst hohe Codequalität zu erreichen, sodass es keine Seltenheit war, dass wir ausführliches
Feedback zum Code gegeben haben (z.B. !33,!34) und uns dann zusätzlich mit den Assignees zusammengesetzt haben um über die Anmerkungen und Vorschläge zu sprechen.
Hierbei ist auch anzumerken, dass viele unserer typischen Kritikpunkte, wie nicht kommentierte Funktionen oder nicht-ausgelagerte Strings und Zahlen, im Laufe des Projekts von den Teammitgliedern immer selbstständiger
bereits vor dem Merge-Request bereinigt wurden, sodass ich denke, zu einem generell besseren Verständnis von "gutem" Code bei den Teammitgliedern beigetragen zu haben.
Außerdem wurde nach dem ersten Sprint die Feuerlöscherrolle von den SCRUM-Mastern vorgeschlagen und eingeführt, um innerhalb der beiden Teams Probleme schneller lösen zu können.
Diese Rolle habe ich zusammen mit Alexander Czegley und Tom Marek zusätzlich angenommen. Durch diese Rolle hat sich der wöchentliche Arbeitsaufwand für
mich noch einmal erhöht, gerade da ich auch versucht habe, möglichst einfach und unkompliziert ansprechbar zu sein und somit auch manchmal noch am späten Abend bei dringenden Problemen geholfen habe. 
Umso mehr habe ich mich darüber gefreut, dass diese Rolle im Team (2) sehr gut angenommen wurde und ich bei vielen Problemen (siehe Dokumentation der Feurlöscherrolle in den jeweiligen Wochen weiter oben)
helfen konnte.

Anhand dieser gesammten Ausführungen würde ich meine Leistung mit einer 1.0 bewerten.
