## Was ist eine API?
Application Programming Interface
Ist eine Schnittstele welche dazu
Dient dazu Informationen zwischen Anwendungen und einzelnen Programmteilen standartisiert auszutauschen. 
Dadurch werden die Programmteile klar von der Anwendung getrennt und ermöglichen Modulares Arbeiten.
In welcher Form die Daten übertragen werden wird von der API angegeben.

## Was ist Rest? (SHEMA)
Representational State Transfer
API welche durch die REST Architektur beschränkt ist.
Wenn diese Beschränkung eingehalten werden ist die API Restful.

# Design Prinziples
1. Uniform interface. 
Die Daten sollten nur aus einem benötigten Stück Information bestehen. 
(URI ist ein Indikator bestehend aus einer Zeichenfolge zur Identifkiation von Ressourcen)

# 2. Client-server decoupling. 
/Client und Server müssen komplett unabhängig voneinander sein, der Client kennt nur die URI der gefragten Daten, und der Server übermittelt diese nur ohne den Client
zu beeinflussen.

# 3. Statelessness. 
/Die Anfrage des Clients darf nur die Informationen die für die verarbeitung benötigt werden enthalten und der Server darf keine Informationen zur Anfrage behalten.

# 4. Cacheability. 
/Wenn möglich sollten ressourcen auf Client oder Serverseite Temporär gespeichert sein. Die Antwort des Servers übermittelt auch ob das "Caching" erlaubt ist. 
Das ermgölicht eine höhere Performance auf seite des Clients und eine bessere Skalierbarkeit auf der Server Seite.

# 5. Layered system architecture. 
/Client und Server sollten nicht direkt verbunden sein, sie sollten durch mehrere Schichten gehen. Dabei könnten verschiedene Vermittler in der 
Kommunikationkette bestehen.

# 6. Code on demand (optional). 
/Auf Anfrage ausführbaren Code vom Server an den Client zu senden und so die Funktionalität zu erweitern, dieser Code sollte nur auf Nachfrage ausführbar sein.

Code on Demand ist Optional und steht dafür das der Server dem Client ausführbaren Code schicken kann um so die Funktionialität zu erweitern. Dieser Code sollte dann nur auf
nachfrage ausführbar sein

## Wie Funtkioniert Rest
CRUD = Primitive Operationen um mit Daten zu arbeiten. 
REST = Arbeitet mit einem Komplexem systemen mit abhängigkeiten und Zusammenhängen

## Praxis Beispiel von Robin als Git Repo
https://code.fbi.h-da.de/stroholz/rest_api_test

# Schaubild
https://www.seobility.net/de/wiki/REST-API

## Quellen:
https://www.ibm.com/cloud/learn/rest-apis
https://github.com/microsoft/api-guidelines/blob/vNext/Guidelines.md
https://www.redhat.com/de/topics/api/what-is-a-rest-api
https://developer.spotify.com/documentation/web-api/
https://play.kotlinlang.org/hands-on/Creating
https://spring.io/guides/tutorials/rest/
https://github.com/s1monw1/ktor-REST-sample <-  Rest Beispiel
https://www.redhat.com/de/topics/api/what-is-a-rest-api
https://www.seobility.net/de/wiki/REST-API

by Philipp Pulver und Robin Holzwarth