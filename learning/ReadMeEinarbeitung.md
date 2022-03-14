# Einarbeitung & Architektur-/Komponentendiagramm

# Einarbeitung
## Kotlin Multiplatform
Um eine Übersicht zu bekommen wie KMM funktioniert bietet der [Link](https://kotlinlang.org/docs/multiplatform.html) eine erste Möglichkeit. Das umfassende Themengebiet wird hier ausreichend erklärt und je nach Bedarf kann in der gegebenen Struktur über einzelne Themenbereiche wie beispielsweise KMM-Projektstruktur, Tests, Tools etc. näheres in Erfahrung gebracht werden.

Weitere nützliche Links:
https://danielebaroncelli.medium.com/the-future-of-apps-declarative-uis-with-kotlin-multiplatform-d-kmp-part-2-3-1bbadaf19aef



### Jetpack Compose
Unter diesem [Link](https://developer.android.com/jetpack/compose) finden sich Tutorials, Beispiel Apps und eine Anleitung um die Entwicklungsumgebung einzurichten und mit der Entwicklung mit Compose zu starten.

### Compose for Desktop
Was man unter Compose for Desktop versteht und eine erste Einleitung in das Thema kann man sich mithilfe der [Seite](https://simply-how.com/getting-started-with-compose-for-desktop) verschaffen. Anschaulich an einer Beispielapplikation wird hier ein leichter Einstieg ermöglicht.
### Kotlin
Da Compose auf Kotlin basiert ist es wichtig die Programmiersprache zu verstehen und die fundamentalen Strukturen zu kennen und anwenden zu können. Mithilfe des [Artikels](https://kotlinlang.org/docs/getting-started.html) ist dies möglich. 
Ausgehend von dem Artikel kann zu spezifischeren Themen navigiert werden, wie bspw. die [Syntax](https://kotlinlang.org/docs/basic-syntax.html), die Kotlin verwendet.

### Gradle Dependency Manager
Um das Dependency Managment mit Gradle vollends zu verstehen und sich einen Überblick zu verschaffen, wie dieses funktioniert, sollte dieser [Artikel](https://docs.gradle.org/current/userguide/dependency_management.html) gelesen werden. Auch Antworten auf spezifische Fragen können dort gefunden werden.

## Docker
Wofür Docker benutzt wird und wie man es verwendet kann [hier](https://docs.docker.com/get-started/overview/) nachgelesen werden.

## REST-APIs
Eine generelle Übersicht was eine REST-API ausmacht und wie diese (wie in unserem Fall) mithilfe von KTor aufgebaut werden kann, kann [hier](https://medium.com/@billwixted/building-a-rest-api-with-ktor-4c322d31eb31) nachgelesen werden.

## KTor
Die von uns entwickelte HTTP API bildet unser Backend und wurde mit Kotlin und Ktor gebaut. Um sich in diese Thematik einzuarbeiten, kann man den [Artikel](https://ktor.io/docs/welcome.html) und die darauf verlinkten Seiten für nähere Informationen verwenden. 

### Ktor Client
Ktor enthält von Natur aus ein HTTP-Client, [KTor-Client](https://ktor.io/docs/getting-started-ktor-client.html). Diesen benutzen wir um Requests zu senden und Responses zu verwalten/bearbeiten. Die Funktionalität kann mit Plugins erweitert werden, bspw. im Bezug auf Authentifizierung, Json-Serialisierung und so weiter.

Die Dokumentation unserer KTor APIs erfolgt im Projekt mit OpenAPI wie das ganze funktioniert kann [hier](https://oai.github.io/Documentation/start-here.html) nachgeschaut werden.

### Swagger
Zur Dokumentation der KTor API wurde unter anderem Swagger verwendet. Was Swagger ist und wie es verwendet wird kann [hier](https://swagger.io/docs/specification/2-0/what-is-swagger/) nachgelesen werden.

## MongoDB
Zur Speicherung unserer Daten verwenden wir im Projekt eine bzw. mehrere MongoDB Instanzen. Was MongoDB ist, wird [hier](https://www.opc-router.de/was-ist-mongodb/) erklärt. Nachfolgend sind einige der Tools, die wir zusammen mit einer MongoDB benutzen um diese zu verwalten und Funktionalitäten (bspw. Bilder hochladen mit Gridfs) zu ermöglichen.

### Gridfs
Um die Bilder zu speichern benutzen wir [Gridfs](https://docs.mongodb.com/manual/core/gridfs/), so werden die Bilder in kleinere Teile aufgespalten. Die Aufteilung in Junks dient in erster Linie dazu die Dokumentengröße nicht zu überschreiten.

### KMongo
Da wir MongoDB mit Kotlin zusammen verwenden benutzen wir ein Kotlin Toolkit für MongoDB und zwar KMongo.
Für eine generelle Übersicht lohnt sich diese [Seite](https://litote.org/kmongo/) zu besuchen. 

In unserem Anwendungsfall und bei Fragen von der Verwendung von KMongo zusammen mit Koin wird [hier](https://michaelstromer.nyc/books/kotlin-multiplatform-mobile/koin-and-kmongo) der Zusammenhang erklärt und anhand von Codebeispielen anschaulich erklärt.

### MongoExpress & MongoAtlas
Für eine grafische Benutzeroberfläche der MongoDB kann [MongoExpress](https://github.com/mongo-express/mongo-express) oder [MongoAtlas](https://www.mongodb.com/basics/mongodb-atlas-tutorial) verwendet werden, wie mit diesen Tools umzugehen ist, kann in den hinterlegten Artikeln nachgelesen ist.

### Koin 
Zur Einarbeitung in Koin lohnt es sich den [Artikel](https://medium.com/@sreeharikv112/dependency-injection-using-koin-4de4a3494cbe) zu lesen, um zu verstehen wozu Koin dient.

### Postman
[Postman](https://www.encora.com/insights/what-is-postman-api-test#:~:text=Postman%20is%20an%20application%20used,need%20to%20be%20subsequently%20validated.) dient dem API-Testing mit einem grafischen UI, es ist ein HTTP-Client, welches HTTP-Requests testet.

### Backend-Architektur mit MongoDB und KTor
Einen ersten Einblick in die Zusammenarbeit unserer Backend-Architektur mit MongoDB, KMongo und KTor ist mithilfe [hiervon](https://github.com/hi-manshu/ktor-mongodb-backend) leichter zu verstehen.


# Architekturdiagramm
![image](/uploads/a9816174ca397c31989f457b6028168d/image.png)

Um die Bestandteile des Architekturdiagramms besser zu verstehen, sind einzelne Komponenten in Schichten zusammengefasst (z.B. Declarative UI layer, View Model etc.) und mit einer Zahl versehen worden. Diese werden nachfolgend im Ansatz erklärt.
## 1: Declarative UI Layer
Deklarative UIs ermögliche uns eine saubere Trennung zwischen dem UI-Layout und dem ViewModel. Da keine weitere Codeschicht erforderlich ist, um die beiden zu verbinden (vgl. findViewById, genauere Informationen dazu [hier](https://medium.com/androiddevelopers/understanding-jetpack-compose-part-1-of-2-ca316fe39050)). Die eingespeisten Daten sind für die UI-Frameworks gleich.

Für die Android-Entwicklung wird Jetpack Compose, für die Desktop-Entwicklung Compose for Desktop verwendet.

## 2: View Model
Das ViewModel ist für die Bereitstellung von Daten für die Declarative UI Komponenten über LiveData verantwortlich. Die Daten werden dabei über das Repository bezogen.

### StateFlow & UI recomposition:
Triggert eine UI Rekomposition - und zwar immer dann, wenn ein Wert sich verändert (mithilfe von Kotlin's mutableStateFlow, mutableStateOf), dies ist nur von Bedeutung, wenn es sich um sich ändernde (mutable) Zustände handelt. Wenn der Zustand eines Wertes nicht verändert wird, beispielsweise beim einmaligen Laden von feststehenden Daten aus der Datenbank, so findet für diesen Wert keine Rekomposition statt. 

### Navigation:
(Im Bestfall ist) Die Navigation für die Android-Version und die Desktop-Version (ist) die Gleiche und kann somit geteilt werden. 

### Events:
Events sind Funktionen die im geteilten Code (common) definiert werden und im UI jeder Plattform aufgerufen werden können. Events dienen einer Funktionalität und lösen Aktionen aus die den Zustand der Anwendung verändert. Events können somit UI-Rekompositionen anregen die der State Manager im Falle eines entsprechendes Events dann auslöst.

### State Provider:
Der State Provider stellt/verwaltet den Zustand eines Screens, bei jeder UI-Rekomposition dient dieser dazu, die Daten mithilfe des State Managers aufzurufen.

### State Manager:
Bildet den Kern des ViewModels, der State Manager ist eine Klasse der den UI-Zustand verwaltet bzw. für diesen zuständig ist. Im Falle das sich Werte die in der UI angezeigt werden verändern wird er informiert und löst eine UI-Rekomposition aus.

## 3: Data Layer

### Repository
Das ViewModel (State Manager) erhält Daten aus dem Repository. Das Repository ist mit einem KTor Server, mit Endpunkten, die Funktionalitäten implementieren und einer Datenbank verbunden. So kann auf Daten zugegriffen und diese verändern werden.

### KTor Server Webservices
KTor-API, die es Benutzern ermöglicht verschiedene Funktionalitäten auszuführen.

- Funktionalitäten:
1. Authentifizierung: 
- Einloggen um Sitzungstoken zu generieren 
- Angemeldeten Benutzer zurückgeben
2. Patient:
- Patienten Daten zurückgeben
- Patientenakte erstellen
- Patientenakte zurückgeben
- Patientenakte löschen
- Patientenakte aktualisieren
- Bild zu Patientenakte hinzufügen
Die Entities und Befehle können nachgelesen werden. Die API wurde [hier](https://code.fbi.h-da.de/pse-trapp/findus/-/blob/master/src/office_backend/ktor-server/swagger/swagger.yaml) im Detail dokumentiert. 

### MongoDB Database
Die MongoDB speichert sowohl die Patientendaten als auch die zugehörigen Bilder in dem folgenden Format.
 ![image](/uploads/46c27b93b4a1136c5df7a8497c4ff441/image.png)
 
## 4: Webservices KTor Server - Cloud Dienst:
Alle Clients (Praxis-Instanzen) können sich mit dem KTor Server verbinden und somit den Datenbankcontroller und die Dienste nutzen. Dafür existieren Endpunkte, die unterschiedliche Funktionalitäten implementieren und mittels OpenAPI dokumentiert sind.
- Endpunkte für: 
  1. **Hochladen von anonymisierten Daten** (Untersuchung) **in die Datenbank** 
  2. **Hochladen/Hinzufügen eines Bildes** (einer Untersuchung)
  3. **Rückgabe/Anfrage von individuellen Therapievorschlägen** (mocking) entsprechend der gegebenen JSON-Struktur und der Liste von Bildern.
  4. **Exportieren der Untersuchung als CSV-Datei**
  5. Abrufen einer Willkommensnachricht

Zum genaueren Nachlesen lohnt sich ein Anschauen der [ReadMe](https://code.fbi.h-da.de/pse-trapp/findus/-/blob/23f122086c1fb973c52a6278d08fc202fb4d6b78/src/cloud_backend/README.md).





