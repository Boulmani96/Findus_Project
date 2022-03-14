# Selbstreflektion Alexander Beckerbauer

## Wochen 27.10.2021 - 14.11.2021

- Präsentation von OpenAPI
- Einarbeitung in Android Studio, Kotlin, KMM
- Einschätzen & Verteilen der Aufgaben
- Arbeit an Issue #7 & #13
- Einarbeitung in Jetpack Compose, JUnit
- Recherche zu Mockito, MongoDB
- Zusammenarbeit (Progrmamieren) mit Partner an Issues

### Anmerkungen

Ich hatte anfänglich große Schwierigkeiten mit den Programmieraufgaben fertig zu werden. Ich musste viel recherchieren und nachlesen, um die mir zugeteilten
Aufgaben zu verstehen und Lösungsansätze zu schaffen. Es war ein großer Vorteil, mit einem Partner der schon einiges mehr an Programmiererfahrung & Fähigkeiten hat zusammen zu arbeiten, da dieser mir viel erklären und helfen konnte.

## Wochen 15.11.2021 - 28.11.2021

- Arbeiten an Issues #13 und #7 (Unit Tests, Funktionalität, API)
- Zusammenarbeit mit Partner
- Absprache mit Desktop Team
- Kommunikation mit KTor & Mongo Team
- Fertigstellung von #13
- Weitere Absprache über Vorgehen für Issue #7
- Retrospektive erfolgt

### Anmerkungen

Wir haben weiterhin an den uns zugeteilten Issues gearbeitet. Zusammen mit dem Desktop Compose Team haben wir uns oft gegenseitig gefragt, wie wir was implementieren. Leider konnten wir uns nicht bei allem abstimmen, da Desktop Compose viele Funktionen von Android nicht besitzt. Wir haben zudem bei einer Anforderung, die im Issue war nicht genauer nachgefragt, was dazu geführt hat, das wir viel Zeit dafür verschwendet haben, etwas zu implementieren und zu recherchieren, das im Endeffekt wieder entfernt werden musste. Wir haben uns ebenfalls mit dem KTor-Team getroffen und gefragt, wie und ob wir die API implementieren können. Allerdings ist diese noch nicht richtig funktional, weswegen wir nicht (richtig) testen können. Zudem wurde die Retrospektive durchgeführt, was mir gezeigt hat, dass ich und meine nähere Arbeitsumgebung nicht die Einzigen waren, die die Arbeitslast als falsch eingeschätzt und verteilt, sowie die Kommunikation als zu wenig, ansah. Wir haben unsere Schlüsse daraus gezogen und denken, dass es so im nächsten Sprint besser laufen wird.

## Wochen 29.11.2021 - 12.12.2021

- Arbeiten an Issues #13, #7 und neuem Issue #19 (Merge, Ktor-Endpunkte, Navigation)
- Zusammenarbeit an Issues mit Partner
- Zussamenarbeit mit KTor-Team, Mongo-Team, Desktop-Team
- Review und Merge von #13 Schmerzen ins Skizze markieren
- Fertigstellung, Review und Merge von #19 Navigationskomponente
- Fertigstellung Issue #7 Vertikaler Prototyp Android

### Anmerkungen

Da wir auf Grund verschiedener Probleme innerhalb des Organisation, Verzögerungen des Mergens und Blockierungen durch andere Issues bisher noch keine unserer Issues mit dem Master mergen konnten, sind viele dieser Probleme in der Zeit seit der letzten Retrospektive gelöst worden. Zum einen wurde die Githead-Rolle neu besetzt. Dadurch konnten wir unser Issue #13 zügig mergen, welches bereits reviewed worden war. Die Arbeit an Navigationskomponente #19 war anfänglich sehr schwierig und kompliziert, da zunächst gefordert war, dass eine Navigation für sowohl Android als auch Desktop mit möglichst viel geteiltem Code implementiert werden sollte. Wir haben hierbei mit der neuen und experimentellen Library Decompose gearbeitet, mit welcher wir die Navigationskomponente mit geteiltem Code verwirklichen wollten. Da wir allerdings mit anderen Issues noch beschäftigt waren, konnten wir uns nicht voll und ganz auf dieses konzentrieren. An dem Termin, an dem wir die Anweisung bekommen haben, wir könnten die Desktop-Variante vernachlässigen und nur falls es einfach wäre, diese ebenfalls mit implementieren, waren wir mit der Navigation noch nicht so weit, ein auf beiden Plattformen funktionsfähiges Frontend durch Decompose erzeugt zu haben. Da diese Library noch sehr neu ist und es wenig Referenzen gibt, haben ich und mein Teampartner uns entschieden, die Decompose Library für die Navigation zu verwerfen und eine gestandenere Technologie wie sie in "People In Space" verwendet wird, zu verwenden. Diese haben wir anschließend implementiert sowie reviewen und mergen lassen. Des Weiteren beschäftigte uns auch die Arbeit an #7 weiterhin. Wir waren lange Zeit geblockt, da ein interner Fehler des KTor/MongoDB Backends uns nicht weiter an den Endpunkten arbeiten ließ. Sind wir mit dem Frontend schon seit längerer Zeit fertig, so standen die Endpunkte sowie die Implementierung dieser ins Frontend noch nicht. Wir haben an diesem Wochende diese allerdings implementieren können und ins Frontend einbauen können. Dadurch, dass die Endpunkte kein eigenes Issue war, sondern auf uns (Desktop und Android Frontend Gruppe) zurückgefallen ist, mussten wir in unseren Frontend Issues die meiste Zeit verwertbare Endpunkte für das Backend programmieren und diese sinnvoll mit einbinden.

## Wochen 13.12.2021 - 19.12.2021

- Feinschliff und Mergen des Issue #7
- Retro

### Anmerkungen

In der letzten Woche vor den Ferien haben wir nach dem Merge-Request von Issue #7 und zwei darauffolgenden Reviews noch Fehler, welche uns nicht aufgefallen sind gefixt und die Anforderungen der Git-Heads für das Mergen erfüllt. Daraufhin wurde unser Issue gemerged und wir konnten es dann am Mittwoch im Review zeigen. Des Weiteren fand die Retrospektive statt, bei der Dinge besprochen wurden, die unser Arbeiten noch weiter verbesseren. Ich denke es wurden gute und wichtige Dinge gesprochen und wir können diese umsetzen. Wir haben uns das neue Backlog angeschaut, um die Issues zu verstehen und dazu Fragen zu stellen, so dass für alle klar ist, wie und was zu tun ist.

## Wochen 12.01.2022 - 26.01.2022

- Neues Issue #37 Onboarding
- Arbeiten an Issue mit Partner

### Anmerkungen

Am ersten Termin nach den Ferien wurden die Issues für den letzten Sprint geschätzt und verteilt. Ich und mein Teampartner haben Issue #37 Onboarding zugeteilt bekommmen. Zunächst hatten wir Probleme mit Android Studio, die wir selbst nicht reparieren konnten, also haben uns die Git-Heads geholfen.
Als wir zum Programmieren übergangen sind, mussten wir feststellen, dass die meisten Onboarding Libraries nicht mit Jetpack-Compose kompatibel sind und wenn es Libraries gibt, die kompatibel sind, die Dokumentation und Beispiele meistens outdated und nicht mehr funkionsfähig sind, also mussten wir, wie zuvor bereits auch, einige verschiedene Libraries, Dokumentationen und Beispiele ausprobieren, um zu sehen, was funktioniert. Da wir dies in den vorherigen Issues aber bereits erlebt hatten, wussten wir bereits was auf uns zukommt. Als wir zum Programmieren kamen, hat das meiste zwar nicht auf Anhieb geklappt, aber wir konnten immer eine Lösung finden, so dass wir am Ende ein funktionierendes Produkt erstellt haben, was reviewed werden kann.

## Woche 27.01.2022 - 02.02.2022

- Fertigstellung von #37 Onboarding
- "Übernahme" von Issue #25 Anamnese-Screen und diesbezüglich Austausch mit Robin
- Fertigstellung von Projekt

### Anmerkungen

Kurz nach der letzten Reflektion hat unser Issue ein Review bekommen, was danach schnell gemerget werden konnte. Da wir nun noch eine Woche "Arbeitszeit" hatten und früher fertig wurden, haben wir uns danach bemüht noch dort zu Helfen, wo noch Hilfe gebraucht wird. Bei dem gemeinsamen Daily wurde klar, dass bei Issue #25 Anamnese Screen noch Hilfe gebraucht wird und so habe ich mich mit dem Verantwortlichen Robin besprochen, der mir erklärt hat, was vorher gemacht wurde, was noch zu machen ist und wie es umgesetzt werden sollte etc. Ich habe dieses Issue, später mit Furkan, weiter "ausprogrammiert" zu den Sachen die noch gefehlt haben, dies war Implementierung der Routen ins Frontend also Datenbankanbindung, Logik hinter dem Frontend und Bugfixing. Das Layout, Frontend und Design war in diesem Issue schon bearbeitet. Im letzten Schritt wurde es nochmal wie bei vielen Anderen sehr Zeitintensiv, da wir vor Ende des Projekts alles bearbeiten und mergen wollten, wodurch auch die im bis dahin sehr gut eingehaltenen Qualitätsstandarts des Projekts im letzten Schritt gelitten haben.

## Zusammenfassung und Einschätzung

### Was habe ich gelernt?

Grundsätzlich habe ich in diesem Projekt sehr viel lernen können. Angefangen hat dies technisch in die Einarbeitung und späterem Beherrschen von Kotlin inkl. dem Compose-Framework was wir verwendet haben, sowie kennenlernen von Libraries, Datenbanken, APIs uvm. "Hands-on" mit diesen Technologien zu arbeiten war mir vor Projektbeginn neu. Am Anfang des Projekts konnte ich nur dem nur schwer folgen, doch man wächst bekanntlich mit den Anforderungen und dank meines Teampartners Furkan, der sich bereits im 5. Semester befindet und technisch versiert ist in der Programmierung, konnte ich sehr schnell viel dazu lernen, nicht nur was Programmieren betrifft, sondern auch den Ansatz um an Probleme ranzugehen und wie man sich besser helfen kann, wenn etwas nicht klappt. Abgesehen vom technischen war dies auch eine Erfahrung wie man sie ähnlich in einem realen Job machen würde, mit Issues, einem großen Team, Scrum etc. Deswegen fand ich dieses Projekt ebenfalls aus Erfahrungs-technischer Sicht wertvoll. In der Kommunikation mit dem Team konnte ich ebenfalls einiges lernen. Wenn ich von meinem Team, sei es direkt mit Furkan oder durch Hilfe von Alex C. und Gabriel auf Defizite im Code, hingewiesen wurde, habe ich mir dies zu Herzen genommen und als Möglichkeit gesehen, mich zu verbessern. Dies war besonders profitabel für mich, da ich vor diesem Projekt über PAD hinaus wenig Programmiererfahrung hatte.

### Was habe ich beigetragen?

Im ersten Sprint haben ich und mein Teampartner zwei große Frontend Issues gepickt. Zum einen Issue #7, den vertikalen Prototyp Android und Issue #13, den vertikalen Prototyp Schmerze in Skizze markieren.
Issue #13 Schmerzen in Skizze markieren war zunächst nur schwierig, da wir unvertraut mit Kotlin sowie Jetpack Compose waren, deshalb mussten wir viel probieren und testen, um herauszufinden, was funktioniert. Dieses konnten wir jedoch im ersten Sprint fertig machen, für das Review und den Merge hatte es noch nicht gelangt.
Issue #7 hingegen war ein größeres Issue, nicht wegen dem Frontend, sondern den "versteckten" Aufgaben was Datenbank-Anbindung betrifft. Diese haben wir versehentlich zunächst so wie die Desktop-Gruppe selbst zu implementieren versucht, nach einem Kommentar hat sich dies jedoch geklärt.
Anschließend haben wir auf Grund von einem Fehler bei Docker der Ktor/Mongo Gruppe warten müssen, da wir den Container lauffähig brauchten, um weiter zu programmieren.
Nachdem dies gelöst war, haben wir zusammen mit Benjo & Bryan und unter Hilfe von Tom sowie Gabriel und Alex C. die Routen in FindusApi.kt programmiert, die schlussendlich vom Frontend im Prototyp Android/Desktop dann verwendet wurden, wofür kein extra Issue erstellt worden war, im Rückblick dies jedoch sinnvoll gewesen wäre.
Zudem haben wir im 2. Sprint noch ein neues Issue mit aufgenommen, und zwar Issue #19, die Navigation. Für diese war zunächst geplant, die Navigation mit möglichst viel geteiltem Code zwischen Android und Desktop zu implementieren, dies wurde später aber nur noch eine Empfehlung. Wie oft an anderer Stelle in unserer Arbeit, haben wir hier viel Zeit in einen Versuch reingesteckt eine (in diesem Fall sogar einige) Libraries zu implementieren, die im Endeffekt wegen fehlender Unterstützung, veralteter- oder garnicht vorhandener Dokumnentation oder ähnlichem nicht implementierbar waren. Diese Versuche haben leider kein Einzug ins Projekt gefunden, finde ich aber wichtig mit zu erwähnen.
Diese drei Issues zusammen (#7, #13 & #19), die ich und Furkan bearbeitet hatten, bildeten in der Retro des 2. Sprints das gesamte (Prototyp) Frontend von Android.
Nach den Ferien und im 3. Sprint übernahmen ich und Furkan das Issue #37, Onboarding. Hier stießen wir auf eine ähnliche Situation wie bei Issue #19 Navigation und man musste sich durch einige verschiedene Libraries coden, um eine zu finden, die funktioniert. Speziell für das Permission-Handling von [Accompanist](https://google.github.io/accompanist/) gab es eine "dünne" Dokumentation oder Beispiele und so mussten wir selbst ausprobieren, was funktioniert und was nicht.
Das tatsächliche implementieren, wenn mal was geklappt hat ging dafür umso schneller und da wir hierbei eine Woche vor Sprint fertig wurden, haben wir unsere Hilfe noch anderen angeboten und schlussendlich noch einiges an Issue #25, dem Anamnese Screen helfen können. Das generieren von Daten hat hier noch gefehlt, so wie Bugs im Code zu reparieren und letztlich noch das mergen mit dem Dashboard.
Der Anamnese Screen #25 und das Onboarding #37 ist im jetzigen Projekt noch vollständig vorhanden. Die Navigationskomponente #19 ist, was das Aussehen betrifft stark überarbeitet worden, die Logik ist jedoch meines Wissens noch die Gleiche. Die Issues #13 sowie Issue #7 waren beides Prototypen und es wurde sich dagegen enschieden, diese "auszuprogrammieren", sondern stattdessen die Anamnese/Dashboard/Patientsuche/Bodymapping unabhängig von ihren Prototypen zu programmieren. Diese sind Deshalb im Projekt nicht mehr vorhanden. Die Routen wurden nochmal überarbeitet sowie Ergänzt und die neuen Routen in FindusRepository.kt greifen auf diese zu.

### Was hätte ich besser machen können?

Ich hätte von Anfang an des Projekts das Time-Tracking, so wie das Pushen von Code nach jedem Arbeitsschritt sowie die genauen Probleme als Kommentar im Git verfassen besser pflegen sollen. Auf dies wurde mich nach dem ersten Feedback hingewiesen, anschließend habe ich dies beachtet. Außerdem hätte ich vielleicht mehr Kommentare zum genauen Stand (zusätzlich zu Commit-Messages) von Issues im Gitlab verfassen können, was andere aus dem Team besser gemacht haben, mir allerdings erst zu Ende, als ich Teamübergreifend gearbeitet habe, aufgefallen ist.
Zudem hätte ich von Anfang an Issues hinterfragen sollen, so dass das die Extra-Arbeit mit dem Programmieren der Datenbankschnittstelle nicht passiert wäre. Diese Lerneffekte sind zum Glück bis auf Eines früh im Projekt passiert, so konnte ich an den erwähnten Dingen nach dem ersten Sprint direkt arbeiten.

### Selbsteinschätzung

Ich habe mich nicht nur jede Woche für mindestens die Zeit eingebracht, die von mir gefordert war, sondern in den meisten Wochen darüber hinaus so eingebracht, ein qualitatives, fertiges Produkt zu erstellen oder wie zuletzt eigenständig nach Issues gesucht, wo noch Hilfe benötigt wird und hierbei in Kauf genommen, dass es hierbei zu einem großen Zeiteinsatz von meiner Seite kam. Wenn es zu Problemen kam, habe ich mir sofort Hilfe gesucht, statt mich an Problemen aufzuhalten und konnte so (meiner Meinung nach) viel zum Projekt beitragen. Die Opferbereitschaft von meiner Seite war hoch und ich habe mich stets bemüht, gute Leistungen zu zeigen und selbständig sowie im Team gute Arbeit zu leisten. Ich würde mich selbst daher als sehr gut einschätzen, jedoch nicht so "sehr gut" wie andere, die noch mehr im Projekt geleistet haben als ich und sehe mich deshalb bei der Note 1,7.

### Feedback zum Projekt

Wie zuvor erwähnt konnte ich viel durch das Team wachsen, da diese schon einiges mehr an Erfahrung und Wissen besitzen, deswegen habe ich von der Situation, mit 5. Semestern zu arbeiten, sehr profitiert.
Anfänglich war mir die Natur des Projekts nicht so bewusst, wie die Projektleitung es geplant hatte. Zunächst schätzte ich die Rolle der Tutoren falsch ein und die Verantwortung die dem gesamten Team über die Organisation, Planung und Issues etc. über das Projekt übergegeben wurde. Nach den ersten paar Terminen und einem klärenden Gespräch war diese Sache jedoch geklärt.
Zudem gab es im ersten Sprint sehr große Defizite was die Kommunikation zwischen den Teams und manchmal intern in den Teams anging. Im Verlauf des zweiten Sprints ist dies besser geworden und wurde im letzten Sprint (speziell am Ende) sehr gut und alle haben am gleichen Strang gezogen. Die gemeinsamen Dailys gegen Ende hin haben dies nochmal bestärkt.
Immer wieder musste ich mich selbst daran erinnern dass es ein Projekt an der Hochschule ist und nicht der Release eines Produkts und hier der Lerneffekt im Vordergrund steht und nicht die Effizienz dieser Unternehmung. So hat es sich dennoch manchmal angefühlt. Man hat sich selbst Druck gemacht, bestimmte Dinge in bestimmter Zeit fertig zu kriegen.
Die Rolle von Frau Trapp als Product-Owner hat uns zusammen als Team fast die gesamte Verantwortung für die unmittelbare Ausführung des Projekts gegeben und hat rückblickend zu viel Lernerfahrung für mich und meine Komilitonen gesorgt.
