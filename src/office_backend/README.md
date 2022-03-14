# Office Backend

## Lokales Arbeiten

- Nur den office_backend Ordner in IntelliJ IDEA öffnen
- Gradle Sync durchführen

Um den Ktor Server mit der MongoDB zu starten, muss im Ordner findus/src des Findus Projekts der
Befehl `docker-compose up` verwendet werden.
Wenn der backend-container bereits einmal gebaut wurde, muss gegebenenfalls `docker-compose up --build`
aufgerufen werden, um gemachte Änderungen im Projekt zu übernehmen.

# Ktor-Server

# MongoDB

## Beschreibung

Innerhalb des ktor-server Projekts befindet sich ein Datenbankcontroller, der alle Operationen zur
Verwaltung eines Patienten bereitstellt (CRUD-Operationen). Dieser wird über ein Modul (databaseModule) bereitgestellt.

## Externe Quellen

Beim Aufbau der Datenbankverwaltung haben wir uns an folgenden Quellen orientiert:

- Erstellen eines KMongo Projekts mit Koin und Trennung der Dateien in model, repository, service
    - https://michaelstromer.nyc/books/kotlin-multiplatform-mobile/koin-and-kmongo das zugehörige
      Gitlab-Projekt findet sich hier: https://github.com/Maelstroms38/ktor-graphql
    - Ein Projekt mit einer ähnlichen Dateiaufteilung findet sich
      hier: https://github.com/hi-manshu/ktor-mongodb-backend
- Arbeiten mit KMongo und verwalten von _id's
    - https://litote.org/kmongo/quick-start/
    - https://litote.org/kmongo/object-mapping/
- Naming convention
  - Restriktionen: https://docs.mongodb.com/manual/reference/limits/#naming-restrictions 
  - naming der collection (lowerCase, plural): https://docs.mongodb.com/manual/crud/

## Aufbau einer Verbindung

- Damit der Controller eine Verbindung zu einer Mongo-Datenbank aufbauen kann, wird ein
  Connectionstring benötigt. Dieser wird aus einer .env Datei gelesen. Hierzu muss in der .env Datei
  der Connectionstring im Format angegeben werden:
  ```
  MONGO_URI="mongodb://connectionString"
  ```

## Verhalten im Fehlerfall

Sollte die Datenbank nicht erreichbar sein, werden die Datenbankoperationen, die über das Repository
verwaltet werden, über einen Timeout von einer Sekunde abgebrochen. In diesem Fall wird eine
MongoException geworfen, die im PatientService gefangen und neu geworfen wird. Erfolgt ein Aufruf
über die API an den PatientService, so fängt diese die PatientService Exception ab und liefert als
Statuscode 500 (internal Server Error)
an den aufrufenden Client zurück.

## Funktionen

Alle Datenbankoperationen werden über den PatientService ausgeführt. Dieser umfasst folgende
Operationen:

- createPatient: Persistiert einen Patienten in der Datenbank
- createMultiplePatients: Persistiert mehrere neue Patienten in der Datenbank
- getPatientById: Sucht nach der übergebenen Id in der Datenbank. Liefert, falls ein Patient
  gefunden wurde, alle Informationen als Patient-Objekt zurück.
- getPatientByName: Übernimmt einen Namen Parameter. Sucht alle gespeicherten Patienten mit diesem
  Namen und liefert diese Patienten als Ergebnis zurück.
- getAllPatients: Sucht alle aktuell gespeicherten Patienten und gibt diese zurück.
- updatePatient: Übernimmt einen Patienten als Parameter und aktualisiert dessen Repräsentation in
  der Datenbank, inklusive neu hinzugefügter Bilder.
- updateMultiplePatients: Aktualisiert die Datenbankrepräsentation aller übergebener Patienten,
  inklusive neu hinzugefügter Bilder.
- deletePatientById: Löscht einen Patienten über dessen id aus der Datenbank.
- deleteMultiplePatientsByIds: Übernimmt eine Liste an PatientenId's und löscht die Patienten mit
  dieser Id aus der Datenbank.
- deleteAllPatients: Löscht alle aktuell gespeicherten Patientendaten.

## Datenmodell

- [Hier findet sich das verwendete MongoDB-Schema](resources/datamodelMongoDB.json)
- [Hier finden sich 10 gespeicherte Beispieldatensätze, die diesem Schema folgen](resources/patients_example.csv)

# Tests
Es existieren Tests zum Prüfen des PatientService (PatientServiceTest), sowie zur Prüfung
des Ktor-Servers (ApplicationTest) im Testordner. Damit die Tests erfolgreich durchlaufen, müssen vorher die Docker-Container
mit ``docker-compose up`` gestartet werden.
