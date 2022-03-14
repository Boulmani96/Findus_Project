# Docker

## Einleitung

### Was sind Container?

- stellen abgekapselte Einheiten dar, die unabhängig voneinander ausgeführt werden können

- Ein Docker-Container enthält eine Anwendung, aber auch alle Ressourcen, die diese zur Laufzeit benötigt

- Damit ist Docker eine Form der Anwendungsvirtualisierung

### Container Images

- Die Basis für Container bilden sogenannte Images

- Images beinhalten alle Komponenten, um eine Anwendung plattformunabhängig auszuführen

- Sind für die Installation und das Updaten der Container zuständig

- ein Image kann lediglich durch einen einfachen Kopiervorgang auf ein anderes System übertragen werden

### Wozu werden Container eingesetzt?

- Docker vereinfacht die Bereitstellung von Anwendungen, weil sich Container, die alle nötigen Pakete enthalten, leicht als Dateien transportieren und installieren lassen.

- Seine Vorteile kann Docker besonders gut in Cluster-Umgebungen und Rechenzentren entfalten

### Vorteile von Docker im Vergleich zu Virtuellen Maschinen

- Docker Container sind im Vergleich zu VMs deutlich effizienter und ressourcensparender: Sie benötigen weniger CPU und Arbeitsspeicher

- Als geschlossene Anwendungspakete sind sie auf unterschiedlichsten Systemen ausführbar und haben damit eine sehr gute Portabilität

- Container sind hochskalierbar

### Docker Hub

- Registery das Images speichert, verwaltet und zum Download bereitstellt

---

## Docker File

### Die grundlegenden Befehle

- FROM: Spezifiziert, welches Base Image und welche Version geladen werden soll

- RUN: Führt einen Befel aus

- COPY: lokale Dateien in Verzeichnis vom Container kopieren

- WORKSPACE: Gibt das Arbeitsverzeichnis im Container an

- ENV: Variablen die an das Programm im Container beim Start übergeben werden

- EXPOSE: Gibt an, welchen Port der Container öffnen soll

- \#: Zum Kommentieren

---

## Docker Compose

- Seperate docker-compose.yml Datei

- Zum starten und verwlten mehrerer Container gleichzeitig

- Definiert die Umgebung in der die einzelnen Container ausgeführt werden

- Wird mit 'docker-compose up' gestartet und mit 'docker-compose down' gestoppt

### grundlegende Befehle

- version: Gibt die Docker Compose Version an (aktuell 3.8)

- services: Spezifiziert die Konfiguration für jeden einzlnen Container

- build: Option für die Erstellung des Containers

- context: Entweder der Pfad zu einer Dockerfile, url oder einem Git Repository

- dockerfile: Falls die Dockerfile einen speizellen Namen hat muss dieser zusätzlich angegeben werden

- container_name: Name des Containers

- ports: Die Ports die dem Container zugewiesen werden z.B.: "8000:8000"

- environment: Variablen die beim Start an den Continer übergeben werden sollen

- networks: virtuelle Netzwerke, damit die Container miteinander kommunizieren können

---

## Container

### KTor

[Image](https://ktor.io/docs/docker.html)

### OpenAPI

[Image Generator](https://hub.docker.com/r/openapitools/openapi-generator#openapi-generator)

[Image Swagger](https://hub.docker.com/r/swaggerapi/swagger-ui)

### MongoDB

[Image](https://hub.docker.com/_/mongo)

---

## Nützliche Quellen

[Docs](https://docs.docker.com/)

[Install](https://docs.docker.com/desktop/)
