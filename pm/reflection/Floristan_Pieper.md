# Selbstreflexion Floristan Pieper


## Woche vom 03.11. bis 09.11.

### Issue #1:
- Erstellung von einer Dockerfile für den KTOR-Server
- Compose File für Mongo-DB-Server

Da wir nicht wollten, dass man jedes mal vor ausführen des Projekts selber gradlen muss, haben wir uns dazu entschieden, dies im entsprechenden Container für den KTOR-Server zu tun, wobei wir auf Probleme gestoßen sind. Außerdem gab es unklarheiten bei den Docker-GUIS. In beiden Fällen wurde Hilfe angefordert.

<br>

### Issue #3:
- Boards wurden erstellt
- Labels hinzugefügt
- Merge request & gitlab Workflow definiert
- Readme dementsprechend erweitert
- Der erste Meilenstein wurde definiert

Es gab leider noch Unklarheiten bei der initialen Verzeichnisstruktur, welche wir am Mittwoch, den 10.11. nochmal mit der Gruppe / Frau Trapp besprechen.
Die weiteren Meilensteine werden im Verlauf des Projekts erstellt.
Im Meeting am Montag, den 08.11. habe ich während des Dailys meinem Team den leicht angepassten gitlab-Workflow erklärt.

<br><br>

## Woche vom 10.11. bis 16.11.

### Issue #1:
- Fix der KTOR-Server Instanz mit Hilfe durch Frau Trapp
- KTOR-Server & Mongo-DB-Seerver laufen in 2 Docker Containern und können mit docker compose-up gestartet werden ohne vorher gradlen zu müssen
- haben Rückmeldung zu den Docker-GUIS bekommen, haben viel Zeit daraufhin in die Recherche gesteckt und dann nochmal Hilfe angefordert, da es uns immer noch nicht richtig klar war

Wir haben Zeit investiert um zu recherchieren ob man nicht auch mit dem GitLab-CI Buildserver die Projekte testen kann, aber kamen zu dem Schluss, dass Docker Container die Wahl sind.

<br>

### Issue #2:
Habe mit dem anderen Git-Head beschlossen, dass er die Pipeline erstmal aufsetzt und ich danach nochmal darüber schaue. 

<br>

### Issue #3:
- Initiale Verzeichnisstruktur wurde angelegt und wird hauptsächlich durch das Initiale Projekt festgelegt
- Die commit messages wurden in ihrer Struktur nochmal leicht angepasst und dies dem Team kommuniziert
- Das Team wurde von mir erneut darauf hingewisen, sich an den GitLab Workflow zu halten und die commit message Guidelines zu beachten

Dieser Issue ist quasi abgeschlossen, kann aber erst finalisiert werden, sobald das initiale Projekt in den Master gemerged wurde.

<br>

### Tätigkeit als Git-Head
- Habe 2 Merge Requests bearbeitet, dabei musste ich (aus unterschiedlichen Gründen) beide ablehnen
- Dies hat leider mehrere Stunden in anspruch genommen, da ich mich auch erst in den Issue einarbeiten musste und nicht genau wusste, wie man eine merge Request richtig abarbeitet, außerdem musste ich Rücksprache mit dem Scrum-Master halten bei einigen unklarheiten
- Kommunikation im Team lässt bisher leider zu großen Teilen sehr zu wünschen übrig, Hilfe wird kaum Angeboten und auch nicht wirklich angefragt
- Falls Hilfe angefordert wurde, dann meist Mitten in der Nacht oder spät Abends, von mir privat zu Zeiten zu denen ich keine Zeit hatte
- Habe einem Teammitglied mit Problemen bei Git & Android Studio geholfen
- Bei den commit messages wurde sich zu großen Teilen an die Struktur gehalten

<br><br>

## Woche vom 17.11. bis 24.11.

### Issue #1:
- Wir haben die letzten vorhandenen Fehler behoben
- Das Review wurde mit Robin Holzwarth durchgeführt
- Die Readme wurde ergänzt mit der Beschreibung, wie man die Docker Container startet
- Der Windows & Android Container für die CI Pipeline wurde wegen zu vielen Schwierigkeiten nach hinten verschoben in Issue #28
- Eine Merge Reqeust wurde gestellt und von Ouassime Boulmani durchgeführt ohne Rückmeldung

<br>

### Issue #2:
- Ich habe mich doch nochmal mit der Pipeline beschäftigt, da sie nicht funktionsfähig war
- Habe ein paar Sachen Recherschiert wie man eine Pipeline aufsetzt, die Android und Desktop tests ausführt
- habe Rücksprache mit Ouassime Boulmani gehalten wie wir weiter verfahren

<br>

### Issue #3:
- Die erste Version des initialen Projektes wurde gemerged
- Das initiale Projekt wurde angepasst und nochmal repariert auf den Master gemerged

Damit konnte dieser Issue abgeschlossen werden und in Sprint review verschoben werden.

<br>

### Git-Head
- 3 Merges wurden von mir bearbeitet, die alle durchgingen ohne weitere Probleme / Auffälligkeiten
- Das initiale Projekt war dabei beim ersten Merge nicht im endgültigen Stadium, weshalb dieses geupdated noch einmal gemerged wurde
- Habe den Teammitgliedern bei Problemen / Rückfagen zu Git geholfen
- Commit Messages haben sich verbessert, weshalb ich dies nicht nochmal extra erwähnt habe

<br><br>

## Woche vom 25.11. bis 01.12.

### Issue #2:
- Meine Änderungen wurden verworfen von Ouassime Boulmani
- Die CI/CD Pipeline wurde finalisiert. Sie überprüft nur die Tests und Lintet die :App: Component der Anwendung
- Die CI/CD Pipeline wurde gemerged auf den Master

 Damit kam dieser Issue in den Sprint Review.

<br>

### Issue #21:
Es gab eine Einführung in den ersten Wireframe für das Bodymapping. Diesen haben wir uns erklären lassen um den Wireframe und die Programmierung des Prototypen zu übernehmen.

<br>

### Issue #28:
- Wir haben nochmal Rechersche betrieben, dabei ist aufgefallen, dass ein Windows Container für uns nicht in absehbarer Zeit möglich ist aufzusetzen
- Die Android Komponente der Applikation wird bereits von der Pipeline überprüft, weshalb ein extra Container hierfür nicht nötig ist
- In Rücksprache mit dem PO haben wir uns darauf geeinigt, den Issue vorerst zu schließen.

<br>

## Git-Head
- 1 Merge Reqeust wurde von mir bearbeitet, welche ich nach der Prüfung ohne weitere Probleme annehmen konnte
- Das initiale Projekt wurde noch ein letztes mal aufgeräumt und gemerged
- Commit Messages sehen soweit gut aus, die Merge Reqeusts und Branches verursachen auch keine Probleme mehr, weshalb ich dort nicht so viel machen musste
- Der Git-Head in Team 2 wurde getauscht von Ouassime Boulmani zu Alexander Czegley und Gabriel Lukas Schön

<br><br>

## Woche vom 02.12. bis 08.12.

### Issue #21:
- Wir haben uns erste Gedanken zu dem Wireframe Prototypen gemacht und gebrainstormt, wie man die beste User-Experience erhält
- Wir haben Rücksprache mit dem PO gehalten, wie der Prototyp am besten geeignet ist
- Danach wurde die Gruppe in 2 2er-Teams aufgeteilt, wobei mein Team den Prototypen finalisiert und das andere Team anfängt mit der Programmierung
- Der Prototyp wurde weiter verfeinert und ausgearbeitet, sodass er teilweise klickbar ist und (soweit möglich) animationen hat

<br>

### Git-Head
- 1 Merge wurde in dieser Woche von mir abgearbeitet
- Das Board wurde von mir nochmal leicht angepasst, ebenso die Labels und ein neuer (Blocked) hinzugefügt auf Anraten von Frau Trapp
- Die Versionierung wurde von mir aus dem ersten Meilenstein entfernt, da diese nicht so viel Sinn ergeben hat und wir nicht genügend Zeit / Kapazitäten haben, uns ausführlich um die Versionierung zu kümmern
- ansonsten war die Woche Relativ ruhig

<br><br>

## Woche vom 09.12. bis 15.12.

### Issue #21:
- Das Wireframe wurde von meinem Team finalisiert
- Das Wireframe wurde von Robin Holzwarth überprüft und abgesegnet
- ich habe es meinem Team und im Retro der Gruppe und dem PO präsentiert
- damit ist der Wireframe Teil abgeschlossen und ich konnte erste Eindrücke von dem Programmierungsteil gewinnen

<br>

### Git-Head
- 3 Merges wurden von mir durchgeführt
- Die Merges haben relativ lange gedauert, da sie die ersten mit Tests waren und ich vieles überprüfen muss
- 2 der Merge Requestst waren relativ groß, was zu zusätzlicher Bearbeitungszeit führte
- Das Stellen von den Reqeusts läuft immer besser
- Die Reviews werden sehr ordentlich durchgeführt, was mich sehr freut, weshalb bei meinen Überprüfungen nichts neues aufgefallen ist

<br><br>

## Weihnachtszeit vom 16.12. bis 11.01.
Ich werde in der Weihnachtszeit mich etwas zurücknehmen.
Wir haben uns mit den Scrum Mastern darauf geeinigt, dass wir keinen festen Termin zum Mergen anbieten sondern wenn wir mal Zeit haben rein schauen und dann entsprechend etwas Mergen, falls was neues dazu gekommen ist.
Es wurde in den Ferien nur sehr wenig weiter gearbeitet, weshalb keine Merge Requests abgearbeitet wurden.

## Wochen vom 12.01. bis 02.02.
### Issue #21:
- Die Aufgaben wurden mehrfach umverteilt auf verschiedene Teammitglieder
- Durch den Ausfall von Dominik wurden weitere Leute zur Bearbeitung hinzugezogen
- Ich habe bei unterschiedlichen Teilen ausgeholfen, diesen Issue zu finalisieren
- Außerdem habe ich mich mit der Konversation von den Daten zu CSV beschäftigt, welche es nicht in den Finalen Prototypen geschafft haben
- Durch den Aufwand und ein paar Probleme wurde dieser Teil raus geschmissen, vor allem da die Routen und die Datenbank nochmal umgeschrieben werden müssten, wobei der Aufwand als zu groß eingestuft wurde (diesen Teil des Codes habe ich leider nicht gepusht & verworfen)
- Die Priorität lag vorerst auf der Funktionalität des Prototypen, weshalb dieser Priorität hatte
- Die Bodymap konnte von uns Finalisiert werden

<br>

### Git-Head
- Ich habe meine Hilfe anderen Gruppen angeboten und sie bei der Bearbeitung soweit unterstützt wie ich konnte
- Im neuen Jahr habe ich nur noch eine Einzige Merge Request abgearbeitet, da die anderen direkt die anderen beiden Git-Heads angesprochen haben um zu mergen, weshalb ich meiner Rolle nicht mehr ganz so effektiv nachkommen konnte

<br><br>

## Fazit
Insgesamt bin ich sehr zufrieden mit der Leistung des Teams, auch wenn ich mich gegen Ende nicht mehr ganz so gut einbringen konnte wie zu Anfang. Durch die direkte Kommunikation mit den anderen Git Heads wusste ich teilweise gar nicht über die Merges bescheid,
weshalb sie vermutlich mehr zu tun hatten als ich gegen Ende. Trotzdem habe ich das Team unterstützt so viel ich konnte. Vor allem in der Anfangszeit habe ich sehr viel Zeit in das Projekt investiert, um den Start und die Zusammenarbeit, sowie den Umgang mit Git
in der Gesamten Gruppe zu verbessern und weiter auszubauen. Jedoch habe ich über das Projekt, vor allem nach den Ferien, sehr stark in dieser Rolle nachgelassen, was zur Folge hatte, dass ich nicht mehr so sehr auf der Bildfläche war. Dies ist eine wichtige Lektion für
mich. Des weiteren war es mal interessant, eine Große Gruppe an einem Projekt arbeiten zu sehen. Dabei gab es mehrere Schwierigkeiten: Zum einen war die Kommunikation sehr wichtig, aber zu Anfang leider noch nicht wirklich vorhanden, jedoch wurde sie im Laufe des Projekts immer
besser. Des Weiteren war die Aufteilung der Aufgaben sehr schwierig, da die meisten keine Kenntnisse in den meisten genutzten Technologien hatten. Erschwerend kam auch noch hinzu, dass Jetpack Compose noch relativ neu ist und noch nicht so viele Basisprojekte hierzu vorhanden sind,
die man zum Vergleich nutzen kann. All diese Dinge und noch weiteres erschwerte vor allem den Anfang. Diese ganzen schwierigkeiten haben dazu geführt, dass die Motivation häufig sehr schnell gesunken ist, jedoch hat sich hier das Gesamte Team durch gekämpft und weiter gearbeitet.
Hierbei möchte ich auch Ben Joes Schönbein erwähnen, der durch seine im Verlaufe des Projekts erlernten Kenntnisse ungemein geholfen hat, vor allem im Bezug auf Jetpack Compose und im Zusammenhang mit der Bodymap. Ohne seine guten Kenntnisse und seinen Willen, sich durchzukämpfen, 
wäre die Bodymap nicht zu dem Ergebnis gekommen, den sie am Ende erlangt hat.
Insgesamt würde ich meiner Leistung eine 1.3 geben, da ich im Verlaufe des Projekts viel Organisatorisches vor allem am Anfang auch mit übernommen habe. Außerdem habe ich meine Kommolitonen so weit ich konnte unterstützt.




