# Sprint 1 Review / Retro

## Actions to take

1. Die Issues müssen vor der Bearbeitung klarer definiert werden und gegenseitige Abhängikeiten von Issues müssen **vor** dem Planing klar sein.
	- mehr Fragen an den PO und die Scrum Master
2. Probleme bei der Bearbeitung von Issues sollten schneller (und an die richtigen Personen adressiert) kommuniziert werden.
	- @Person im Discord und in den Issue comments
	- Problem detailliert beschreiben - wie bei stackoverflow

## Review

### Was wurde geschafft

- #6 Initiales Projekt: 
	- initiale Projekt-Struktur wurde für zwei Plattformen (Android u. Desktop) generiert
	- Assignees: Amir
- #3 gitlab Struktur: 
	- für das Git wurde eine initiale Verzeichnisstruktur angelegt
	- Maßnahmen zur Projekt-Orga und zur Messung des Fortschritts definiert
	- Assignees: Floristan und Quassime
- #1 Docker-Container: 
	- die Docker-Container für KTOR-Server, Mongo-DB-Server und Android wurden aufgesetzt
	- Probleme beim mergen mit #9 , #10 und dem Container für Windows
	- Assignees: Dominik und Floristan
- #5 Vertikaler Prototyp: Mongo-DB:
	- Mongo-DB angelegt in der sich ein Patient anlegen / editieren / löschen lässt
	- Assignees: Alexander C. und Gabriel
- #9 u. #10 Vertikaler Prototyp: KTOR-API:
	- KTOR-API mit Routen zur Authentifizierung und zum abfragen / anlegen / editieren und löschen von Patienten
	- Probleme beim merge mit #1 ==> KTOR-Server läuft nicht im Docker-Container
	- Assignees: Tom und Robin
- #4 Design Sprint: 
	- Design-Sprint vorbereitet, Experten gesucht und interviewt und durchgeführt
	- Klick-Prototyp wurde auf den nächsten Sprint verschoben
	- Link zum Mural: https://app.mural.co/t/fbihda7663/m/fbihda7663/1637047833566/aea44e996e380e5ef78353d404958539f6b57d73?sender=u085a0187c404bfe18b741883
	- Assignees: Alica, Philipp und Dennis

## Retro

### Was lief gut:

- Gegenseitige Hilfe innerhalb des Teams
- gut organisierter Design-Sprint
- Commitment, Teamarbeit, gute Atmosphäre
- hilfsbereite und gut organisierte Scrum Master
- Durchführung des Design-Sprints lief gut - gute Orga / Unterstützung der Betreuer u. Professorin
- Vor-Ort treffen mittwochs haben die Kommunikation verbessert
	
### Was lief nicht so gut:

#### Orga / Workload

- viele Issues noch nicht fertig
- sehr viel Arbeit - zu wenig Zeit
- Planung der Issues war nicht detailliert genug
- Zeit für Orga, Kommunikation, Planung etc. wurde nich berücksichtigt
- Zeitspanne zwischen fertig "gereviewtem-Code" und Merge ist zu groß

#### Kommunikation

- Fragen werden nicht bzw. zu spät beantwortet
	- Fragen werden nicht schnell genug gestellt
- Unterstützung durch Betreuer / Professorin zu spät
- Betreuer scheinen aus der Sicht der Studis unsichtbar (Rolle der Betreuer unklar)
- schlechte Kommunikation
- verwirrende Zeitorga / unklare Fertigstellungstermine

#### Issues

- viele gegenseitige Blockaden der Issues ==> nicht gut geplant was zu erst gemacht werden muss
	- initiales Projekt war zu spät vefügbar
- Issues zu vage formuliert / unklar
- Workload nicht gleichmäßig verteilt
- Akzeptanzkriterien falsch verstanden ==> flasche Implementiertung

#### schlechte Review / DoD

- mangelhaftes Qualitätsmanagement
- nicht-Einhaltung der DoD ==> unfertige Issue wurde auf Master gemerged
- nicht lauffähiges Projekt zwischenzeitlich auf Master

#### neue Technologie

- neue Technologie für Anfänger ungeeignet aufgrund fehlender / knapper Guides
- Compose ist noch "ungereift" ==> vor allem Desktop
- Framework nicht gut gewählt