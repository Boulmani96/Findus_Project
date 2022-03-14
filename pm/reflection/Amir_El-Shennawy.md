# Selbstreflexion: Amir El-Shennawy

## Woche vom 1.11.2021 - 14.11.2021

- Initiales Projekt Präsentation

- Einarbeiten in PeopleInSpace Projekt und darin verwendete Technologien

- Verteilung der Issues und Einschätzungen

- Einführung in Kotlin, KMM, Jetpack Compose, Compose for Desktop etc.

  

## Woche vom 15.11.2021 - 21.11.2021
### Issue #6 Initiales Projekt:
- PeopleInSpace klonen. Minimieren & Anpassen, um initiales Projekt zu erhalten
- Minimieren: 
1. Unassozierte Dateien, Funktionalitäten löschen
2. Abhängigkeiten anpassen
3. Module entfernen
- Anpassen:
1. Umbenennen von PeopleInSpace Referenzen zu Findus; insbesondere Package-Namen
2. Namen der App/Anwendung umbennenen
3. Code Inspection und Warnings/Errors beseitigen/beheben

- ReadMe erstellen: Instruktionen zum Setup des Frameworks (Android Studio) & Start der Anwendung auf den zwei Plattformen. Auf Fehlerquellen und deren Lösung hinweisen.
- Merge-Request erstellen

## Woche vom 22.11.2021 - 5.12.2021

### Issue #6 - Initiales Projekt:
#### Überarbeitung der angemerkten Anforderungen im Merge-Request, Mittwoch-Besprechung und E-Mail von Product Owner von feat6/initial_Project-branch:
- Änderung FindusRepositoryInterface: Entfernen von SQLDelight und hinzufügen von "Fake Functions", um SQL Funktionen und Anbindung an Datenbank für ViewModel zu imitieren
- Datenbank und Abhängigkeiten entfernen/ersetzen
- Probleme mit Abhängigkeiten und Warnings beheben
- Review und Merge von Branch und Überarbeitung
- Umbenennen der Packages in das von Product Owner gewünschte Format
- Entfernen von Plattform Modul, da für Android/Desktop-Version nicht benötigt
- Ändern der ApplicationId, anpassen der Workspace.xml für die Verwendung von JDK11
- Entfernen der letzten Überbleibsel von com.surrus package
- ReadMe angepasst und verfeinert

### Issue #4 - Design Sprint:
- Auf Nachfrage von Alicia und Philip:
1. Einarbeitung in Figma
2. Gestalten des Dashboards in Figma entsprechend der Anforderung
3. Verbessern/Fertigstellung der Bodymapping und Anamnese Screens
4. Vereinigung der Screens und Erstellung eines klickbaren Prototypen

### Issue #34 - Projekt läuft:
-Projekt war durch einen Merge eines anderen Branchs zwischenzeitlich nicht kompilierbar, durch folgende Schritte konnte ich dies schnell beheben
1. Erkennen der Abhängigkeitsprobleme
2. Verschieben des common Ordners/Moduls von gradle Folder in src Verzeichnis
3. Mergen des quickFix und kommunizieren mit Gruppen, um weitere Probleme zu vermeiden
- In zwei aufeinanderfolgenden Sessions zusammen mit anderen des Teams wurde versucht, die Probleme mit Docker zu beheben
- Auch durch Hilfe von Colja Wesp konnten diese Probleme, während ich mit daran gearbeitet habe, nicht gelöst werden

### Issue #18 - Doku Architektur und Entwicklung:
#### ReadMe überarbeitet:
- "idiotensichere" Anweisungen mit Screenshots
- Setup des Frameworks mit Downloadlinks und Einstellungen zum erfolgreichen Starten der Anwendungen auf beiden Plattformen hinterlegt

#### Hilfe in anderen Teams:
- Während diesen 2 Wochen habe ich oft Anfragen von verschiedenen Teams bekommen, da ich für das initiale Projekt verantwortlich war. In drei Teams galt es hierbei bei Fragen Auskunft zu erteilen, leider konnte ich nicht allen bei der Lösung des Problems in kurzer Zeit behilflich sein. 
## Woche vom 6.12.2021 - 12.12.2021

### Issue #18 - Doku Architektur und Entwicklung:
- Einarbeitung in Architektur und Komponenten
- Erstellung eines ersten Architekturdiagramms mithilfe von Figma
- Erstellung eines ersten Einarbeitungs-ReadMe's
#### ReadMe überarbeitet:
- Verbesserungen von Product Owner übernommen (fehlerhafte AndroidStudio Version & SDK-Bild)

### Issue #26 - Farbschema:

- Versuch, mit der bearbeitenden Gruppe, single source of truth zu implementieren (Theme & Color.kt Datei in common module)
- Leider durch Probleme mit Abhängigkeiten nicht erfolgreich 
- Nach vergeblichen Versuch in zwei Sitzungen unterbrochen 

## Woche vom 13.12.2021 - 19.12.2021

### Issue #18 - Doku Architektur und Entwicklung:
- weitere Einarbeitung in Architektur und Komponenten
- Einarbeitung in Komponentendiagramm, Studieren/Verstehen der Architektur
- Erstellung einer Einarbeitungs-ReadMe mit Links zum Einarbeiten
- Verfeinerung des Architektur/Komponentendiagramms

## Anmerkungen

Die Einarbeitung in das Projekt und in KMM war sehr umfassend. KMM ist unglaublich komplex und trotz des Vorhandenseins eines Basis Projekts sind viele Abhängigkeiten und Tools zunächst schwer durchschaubar und deren Zusammenarbeit schwer zu verstehen.

Bei der Präsentation, und später beim Bearbeiten meines Issues, ist mir dies zunehmend klar geworden.

Die Bearbeitungszeit des Issues lag deutlich höher als geschätzt, dies lag meines Erachtens nach an der Komplexität und Vernetzheit der einzelnen Module und durch mein Mangel an Erfahrung mit KMM, geschweige denn Kotlin.

Das Löschen einzelner Module hat sich als schwer herausgestellt und immer wieder zu internen Compiler-Fehlern geführt, Workarounds, wie das Umstellen auf unterschiedliche JDKs etc., um diese zu beheben (Internetrecherche), haben oft nicht die gewünschten Ergebnisse erzielt und ein Rollback zu einer funktionierenden Version und das Löschen des Gradle-Cache waren die einzige Lösung, um neu zu beginnen. Dieser Prozess und die zunehmende Unklarheit darüber was in dem initialen Projekt vorhanden sein muss und was gelöscht werden kann, waren Schlüsselfaktoren, die zu der langen Bearbeitungszeit geführt haben.

Das Umbenennen von Packages/Directories die Libraries beinhaltet haben war ebenfalls ein aufwendigerer Teil als zunächst vermutet.

In vielen Momenten habe ich mir einen Partner beim Bearbeiten des Issues gewünscht, der genauso tief in dieser speziellen Thematik ist, mit dem ich diese Probleme zusammen bereden kann.

Durch die Neuheit der Technologie (z.B. Compose for Desktop) waren deutlich weniger Quellen im Internet zu Problemen und Fehlermeldungen zu finden, als ich es im Studium bei anderen Modulen gewohnt war.

## Woche vom 12.01 - 19.01

### Issue #Screen-Struktur:
- Was hab ich gemacht: 
- Nach Aufforderung von Ouassime, Taibi und Robin
- Erstellen einer Screen-Struktur mit Navigationsgraph
- Dashboard,  Anamnese & Bodymapping Screens wurden erstellt
- Komplette zuvor erstellte Navigation wurde gelöscht und entsprechen best-practice Anweisungen von Android.devs

### Issue #41 - Refactoring Client - Architektur umsetzen:
- Was hab ich gemacht: 
	 - Verwendung von Repository umsetzen, enge Absprache mit Teams und entsprechend ausgeholfen
	 - Hilfe bei API-Zugriffsproblemen und Einsatz von Coroutinen
	 - Anpassung von Koin

- Was lief gut:
	 - Schnelle Bearbeitung von der Navigation, sodass andere mit Implementation anfangen können
	 - Nach Fertigstellung der Aufgabe schnelles Review und Merge von Ouassime und Taibi in enger Absprache
     - Absprache mit Teams war besser als zuvor: Kommunikation auf Discord verlief schneller:
     - Fragen erreichten mich direkt und ich konnte die meisten ohne Hilfe beantworten und unterstützen
- Was lief nicht gut:
	 - Umsetzung der Navigation als Bottom Navigation
		- Fehlkommunikation: es war eine SideBar gewünscht, dies wurde mir allerdings nicht gesagt
	 - Fragen waren teilweise zu spät oder gar nicht gestellt worden von einzelnen Teams
     - Durch die alleinige Einteilung war die Arbeit sehr umfassend und ich konnte nicht soviel helfen wie ich gerne geholfen hätte,
     weil dies mehr und engere Zusammenarbeit mit den Teams erfordert hätte, so war die Zusammenarbeit oft oberflächlicher als nötig gewesen wäre
     - Trotz Vorstellung der Architektur  wurde diese oft ignoriert und im Nachhinein hat man Arbeit doppelt gemacht
- Anmerkungen: 
	 - Trotz Bitte/Nachfrage nach einem Partner, war niemand bereit mir bei diesem Issue zu helfen
## Woche 19.01 - 26.01
### Issue #25 - Implementation Anamnese:
- Was hab ich gemacht: 
	 - Implementation des UI für Anamnese
	 - Versuch die Architektur umzusetzen zusammen mit Robin
     - Erstellen von Funktion für DefaultCheckboxen (Zustände, Sonographie Ergebnisse)
     - Benutzung von Enums als Zustände
	 - Anfang des Erstellens von Room Database (Dao,TypeConverter,Repository) nachdem ungefähre Datentypen in Anamnese festgelegt wurden
- Was lief gut:
	 - schnelle Fortschritte in der UI
	 - Datentypen hinzufügen um auf dem Wissen aufzubauen
     - Room-Database Anfang der Implementation lief isoliert (in eigenem Projekt) sehr gut
- Was lief nicht gut:
	 - Robin und ich hatten Probleme Room-Database hinzuzufügen:
     Die Probleme die es gab konnte ich zunächst beheben (TypeConverter, Anpassen von Datentypen) doch bei dem Einsatz von Coroutinen, ist es nötig
     eine Dependency hinzuzufügen (androidx.room:room-ktx) die für Probleme gesorgt hat (commonModule wurde nicht mehr erkannt, ebenso kermit Referenzen)
	 - Frustrationsgrenze wurde ausgereizt, da viele Bemühungen und Arbeit in eigenem Projekt zum Testen funktionierten bei dem Einfügen in Findus
     allerdings Probleme hervorgerufen haben die ich nicht beheben konnte trotz dem Einsatz von **viel** Zeit
	 - Bei Daily und vorallem in Discord habe ich mehrmals nach Hilfe bezüglich der Dependency Probleme gefragt, leider konnte bzw. wurde mir nicht geholfen/geantwortet
- Anmerkungen: 
	 - Nachdem ich in Issue 42 nicht weiterarbeiten konnte, aufgrund von Blockaden wie Bibliotheken updaten die noch nicht vorhanden waren, Bilder wurden noch nicht eingesetzt (zumindest welche die abgerufen werden und dementsprechend mit Glide aufgerufen werden können) etc. konnte ich mich
     vollends der Anamnese widmen
     - Viele Bemühungen sind zunächst ins Leere gelaufen und würde ich nochmal in dieser Woche anfangen würde ich Dinge pragmatischer lösen anstatt
     direkt alles perfektionistisch anzugehen, um so schneller Ergebnisse zu erzielen 

## Woche 26.01 - 02.02

- Was hab ich gemacht: 
	 - Erstellen des ViewModels, Funktion für fetchPatientasFlow erstellt
	 - Für die Implementation des ViewModel war die Zeit zu gering, da bereits an zuvielen Stellen (Dashboard, Bodymapping) die Umsetzung mithilfe der Daten direkt anstatt über ein ViewModel benutzt wurde
     - Unterstützung von Ben Joel bei Bodymapping Abschluss (Laden von Bildern, Speichern von Bildern, Bugfixes) über 4 Stunden
     - Unterstützung bei Anamnese Abschluss (Kleinigkeit 30 min) Alex B.
     - CodeAusbesserungen (Magic Numbers, Naming Convention) in **allen** Screens (über 6 Stunden von Dienstag auf Mittwoch)
     - Bugfixes in Anamnese: Abspeichern und neu laden eines in der gleichen Session erstellten/veränderten Anamnese Bogens ohne Neustarten der App (vorher musste die App neu gestartet werden, ansonsten waren die Ergebnisse die gleichen wie zu Anfang/Starten des Programms)
     - Reviewen und Mergen von Branches in enger Absprache(Discord) 

- Was lief gut:
	 - Kommunikation, Zusammenarbeit in Discord
	 - durch die Arbeit mit Partnern bzw. Kommilitonen hat das Bearbeiten funktioniert. Fortschritte waren größer und es hat Spaß gemacht
     - Review und Merge hat besser funktioniert, da die Leute direkt abrufbereit waren, Probleme konnte man direkt ansprechen und beheben 
- Was lief nicht gut:
	 - Wir mussten zum Ende des Projekts viele Vorgaben in Bezug auf Merge und DoD brechen
     - Bugfixes und CodeAusbesserungen die ich über mehrere Stunden nachdem Mergen und Helfen (18Uhr-24Uhr Dienstag) von Dienstag auf Mittwoch
     (1Uhr-7Uhr siehe Git) gemacht habe wurden nicht vor Review gemerged, obwohl dies zuvor besprochen wurde
- Anmerkungen: 
	 - In der letzten Woche haben sich einige nochmal ordentlich ins Zeug gelegt! 
	 - Kommunikation und Zusammenarbeit auf Allzeithoch
     - Letzter Abend (Dienstag) spaßigste Abend im ganzen Projekt mit vielen Leuten, Erleichterung und Zusammenhalt war deutlich spürbar
	 
## Issues:

- **#4: Design Sprint** - ausgeholfen

- **#6: Initiales Projekt** - alleine bearbeitet

- **#18: Doku Architektur und Entwicklung** - alleine bearbeitet

- **#26: Farbschema** - ausgeholfen

- **#34: Projekt läuft** - ausgeholfen

- **#42: Refactoring Client** - alleine bearbeitet

- **#25: Implementation  Anamnese** - ausgeholfen

- **#Screen Struktur** - BottomNavigation alleine; Refactoring zu SideBar nicht von mir

- **#24_bodymap_updated** - ausgeholfen beim Abschluss 

##Review/Mergen:
- feat/6
- quickFix
- feat/23_implementation_dashboard Review
- Feat/25_implementation_anamnese_v2 Review
- fix_some_Bugs Mergen & 2.tes Review 
- feat/24_bodymap_updated Review 



## Projektabschluss

- Fazit zum Projekt
	- Trotz des verspäteten Hinzukommens ins Projekt (am Anfang sehr überfordert) hat mich die Idee des Projekts und der Fortschritt doch noch gepackt
    - Zusammenarbeit ist das A und O, wäre diese am Anfang so stark wie am Ende, wäre das Ergebnis noch besser
    - Großer Teil des Lernmöglichkeitein waren Praxisbezug, Projektarbeit und vorallem Teamarbeit, Organisation und Einstellen auf Andere/Anpassungsvermögen erweitern
	- teilweise mit den Modulen die ich dieses Semester belegt habe, sehr zeitintensiv
    - Erleichterung bei Projektabschluss

- Feedback zum Projekt
	- Umsetzung der Rollen Product Owner, Betreuer gut; dadurch konnte man Praxisbezug herstellen und wichtige Dinge, wie die Definition von Issues oder das Stellen der richtigen Fragen lernen
	- Danke auch an Colja & Clarissa, Colja hat mich persönlich unterstützt und der Kontakt mit ihm war erfrischend; Clarissa war sowohl in der Retro als auch bei PlanningPoker etc. sehr hilfreich und offen
    - hoher Praxisbezug und viele unterschiedliche Lernmöglichkeitein, inklusive Rollen und Projektablauf war sehr gut und anders als in anderen Modulen im Studium
    - Arbeit mit aktuellem, zukunftssicheren Framework/Sprache anstatt (wie oft) "veraltetes" Wissen zu lernen was nicht in der Realität eingesetzt wird, zumindest nicht mehr die aktuelle Relevanz wie vor 10 Jahren 
	- Was aus meiner Perspektive nächstes Mal besser laufen könnte:
		- Issues die nicht direkt zu einer ersten lauffähigen Funktion führen später behandeln
        - Framework testen war teilweise aufgrund von wenig Quellen zur Recherche nicht einfach; autodidaktischen Weg bringt aber auch Vorteile und ist wichtig für später (deswegen nur ein kleiner Punkt)
		- Personen/Betreuer, die das Framework kennen und bei schweren Probleme helfen können
		- unterstützender bei schweren Issues oder bessere/fairere Aufteilung:
        Ich habe mich etwas alleingelassen gefühlt, obwohl ich mir dies explizit gewünscht habe, wurde ich über das ganze Projekt hinweg in große Issues alleine eingeteilt(Initiale Projektstruktur, ReadMe, Refactoring Architektur) oder komplett alleine gelassen (Doku Architektur und ReadMe für Einarbeitung) --> Hier hätte ich mir, nachdem ich es angesprochen hatte, von anderen Beteiligten mehr Unterstützung gewünscht oder aber in der Rolle als Dozent oder Scrum Master eine weitere Person die mir dabei hilft
- Persönlich:
   - Ich hätte sehr gerne andere Issues bearbeitet und hatte mich immer auf die Plannings entsprechend vorbereitet, mir wurden allerdings immerwieder Issues zugeteilt - ich bin dankbar weil ich viel lernen konnte, aber bin mir auch sicher durch den größeren Spaß an dem zu arbeiten das ich mir ausgesucht habe wäre mehr Begeisterung aufgekommen
		
**- Selbsteinschätzung und Notenvorschlag**
    - Die Punkte die am Anfang bemängelt wurden, wie das "schleifen lassen" von Issues, habe ich nicht mehr vorkommen lassen. Ich habe Issues wie Screen Struktur trotz Abgaben und anderen Verpflichtungen direkt im Anschluss gemacht und so schnell es geht fertiggestellt und Projektbeteiligte direkt gebeten diese zu reviewen und zu mergen.
    - Pflichtbewusstsein habe ich gesteigert und immer direkt nach Mittwoch angefangen mit den neuen Issues
    - Probleme direkt angesprochen/ Hilfe gesucht, anstatt diese selbst zu lösen und Prozess aufzuhalten
    - Aktiv kommuniziert und bei vielen Issues bzw. in anderen Teams ausgeholfen
    - Bei Daily kurzfristig Rolle des Scrum Masters übernommen, als dieser ausgefallen ist, um Kommunikation aufrechtzuerhalten alle Informationen direkt weitergeleitet und Leute vermittelt die Zeit hatten an Teams die hilfe gebraucht haben 

Da ich mich seit Beginn des Projektes mit großen und vielen Issues beschäftigt habe und nach deren Fertigstellung oder währenddessen (trotz Erfüllen der 10 Stunden Marke) aktiv darum gekümmert habe, anderen bei ihren Issues auszuhelfen (Bsp. DesignSprint: Erstellen von Dashboard Wireframe, Fertigstellen von Anamnese und Dashboard und erstellen des ersten klickbaren Prototypen, Farbschema, Docker etc.), sehe ich meine Mitarbeit als überdurchschnittlich. Ausserdem habe ich mich trotz verspätetem Dazukommen direkt eingebracht und von mir wurde (mein Gefühl) direkt zu beginn sehr viel (mehr) erwartet. Wie die Präsentation alleine und die Einarbeitung in halber Zeit. Das Bearbeiten von großen Issues alleine, die zweite Präsentation über die gesamte Architektur und Teilkomponenten. Das initiale, alleinige Erstellen der zwei hauptsächlichen ReadMes, neben der Bearbeitung der anderen Issues. Insbesondere auch die Überarbeitung dieser oder die zahlreichen Bugfixes am initialen Projekt und zum Schluss, zusammen mit den Bemühungen in Bezug auf Kommunikation, Motivation und Mergen/Reviewen, zeigen mit den Punkten zuvor meinen Anteil am Projekt. Diese Mitarbeit und "Opferbereitschaft", um zu einem Projekterfolg zu verhelfen auf Kosten von Zeit, Mühen, Frustration und schlaf bringen mich zu der Noteneinschätzung von 1.0. 




