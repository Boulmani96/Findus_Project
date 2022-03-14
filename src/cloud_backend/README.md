# Cloud Backend
## Lokales Arbeiten
- Nur den cloud_backend Ordner in IntelliJ IDEA öffnen
- - Gradle Sync durchführen

Um den cloud Server mit der MongoDB zu starten, muss im Ordner src Ordner des Findus Projekts der Befehl `docker-compose up` verwendet werden.

# Cloud-Server
## Routen
![Bodymapping](resources/cloudRoutes.png)

## Anonymisierung (Prototyp)
- Derzeit wird der Patientenname bzw. Patientenidentifier provisorisch anonymisiert.
- Hierzu wird beim Empfangen einer Untersuchung über die API der Patientidentifier (string)
  1. Zunächst in eine Bytesequenz aufgeteilt und als Bytearray abgespeichert 
  2. Die Bytes im Bytearray werden nun mithilfe eines BigIntegers in eine nummerische Repräsentation konvertiert 
  3. Der BigInteger wird in einen String zurückgewandelt
- Somit wird für jede selbe Eingabe (Id des Patienten) dieselbe Ausgabe erzeugt, d.h. es können
  später pseudonymisiert alle Untersuchungen zu einem Patienten über dessen Identifier abgefragt werden.
  Gleichzeitig ist es nicht möglich, für unterschiedliche eingegebene Identifier dieselbe Id-Repräsentation
  zu erhalten.
  (siehe hierzu https://stackoverflow.com/questions/16673683/get-same-unique-id-for-same-string)
  
# MongoDB

## Aufsetzen einer MongoDB Atlas Instanz
- Besuchen Sie https://www.mongodb.com/atlas/database
- Klicken Sie auf "Try Free"
- Füllen Sie die geforderten Daten aus  
  ![Bodymapping](resources/atlasLogin.png)
- E-Mail verifizieren
- Umfrage ausfüllen
- Klicken Sie auf "Create Starting at FREE"  
  ![Bodymapping](resources/atlasCosts.png)
- Wählen Sie ihre gewünschte Konfiguration aus und klicken Sie danach auf "Create Cluster"  
  ![Bodymapping](resources/atlasClusterSelection.png)
- Nutzer erstellen  
  ![Bodymapping](resources/atlasConfigure.png)
- Network Access konfigurieren  
  ![Bodymapping](resources/atlasNetworkConfigure.png)
- Klicken Sie auf "Connect"  
  ![Bodymapping](resources/atlasDeployments.png)
- Klicken Sie auf "Connect your application"  
  ![Bodymapping](resources/atlasConnectSelection.png)  
- Lassen Sie sich einen ConnectionString erzeugen  
  ![Bodymapping](resources/atlasConnectionString.png)
- Kopieren Sie den ConnectionString in die [.env Datei](resources/.env)
  - Geben Sie statt \<username> den Namen ihres Users ein
  - Geben Sie statt \<password> das Passwort ihres Users ein
  - Löschen Sie "myFirstDatabase" nach dem letzten / `mongodb+srv://beispielNutzer:beispielPasswort@cluster0.udql1.mongodb.net/myFirstDatabase?retryWrites=true&w=majority`
  - Geben Sie nach dem letzten / dafür ein einfaches "?" an `mongodb+srv://beispielNutzer:beispielPasswort@cluster0.udql1.mongodb.net/?retryWrites=true&w=majority`

## Beschreibung
Innerhalb des Cloud-Server-Projekts befindet sich ein Datenbankcontroller, der die Zugriffe auf die Clouddatenbank verwaltet.
Dieser wird über ein Modul (CloudDatabaseModule) bereitgestellt.

## Externe Quellen
Beim Aufbau der Datenbankverwaltung haben wir uns an folgenden Quellen orientiert:

- Erstellen eines KMongo Projekts mit Koin und Trennung der Dateien in model, repository, service
  - https://michaelstromer.nyc/books/kotlin-multiplatform-mobile/koin-and-kmongo Das zugehörige Gitlab-Projekt findet sich hier: https://github.com/Maelstroms38/ktor-graphql
  - Ein Projekt mit einer ähnlichen Dateiaufteilung findet sich hier: https://github.com/hi-manshu/ktor-mongodb-backend
- Arbeiten mit KMongo und verwalten von _id's
  - https://litote.org/kmongo/quick-start/
  - https://litote.org/kmongo/object-mapping/

## Aufbau einer Verbindung
- Damit der Controller eine Verbindung zu einer Mongo-Datenbank aufbauen kann, wird ein Connectionstring
  benötigt. Dieser wird aus einer .env Datei gelesen. Hierzu muss in der .env Datei der Connectionstring
  im Format angegeben werden:
  ```
  MONGO_URI="mongodb://connectionString"
  ```
## Verhalten im Fehlerfall

Sollte die Datenbank nicht erreichbar sein, werden die Datenbankoperationen, die über das Repository
verwaltet werden, über einen Timeout von einer Sekunde abgebrochen. In diesem Fall wird eine
MongoException geworfen, die im CloudService gefangen und neu geworfen wird. Erfolgt ein Aufruf
über die API an den CloudService, so fängt diese die CloudService Exception ab und liefert als
Statuscode 500 (internal Server Error)
an den aufrufenden Client zurück.

## Funktionen
Alle Datenbankoperationen werden über den CloudService ausgeführt. Dieser umfasst folgende Operationen:
- createMedicalExamination übernimmt als Parameter eine Untersuchung eines Tieres und speichert diese in die Datenbank.
- getAllExaminations Sucht alle gespeicherten Untersuchungen in der Cloud-Datenbank und gibt diese zurück.
- getExaminationById Sucht nach einer Untersuchung über deren Id und gibt diese, falls sie gefunden wurde zurück
- uploadExaminationImages Speichert die Bilder zu einer Untersuchung in der Clouddatenbank.

## Datenmodell
![Bodymapping](resources/datamodelCloud.png)
- [Hier findet sich das verwendete MongoDB-Schema](resources/datamodelCloud.json)
- [Hier finden sich 5 gespeicherte Beispieldatensätze, die diesem Schema folgen](resources/examinations.csv)

