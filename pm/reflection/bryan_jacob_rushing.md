# Selbstreflektion Bryan J. Rushing
## Wochen 27.10.2021 bis 10.11.2021
### Was ist alles passiert?
- In diesen 2 Wochen habe ich mir Grundkenntnisse für das Projekt angeeignet.Es gab viel zu recherchieren, vorallem weil ich  
weder mit der Umgebungsentwicklung Android Studio/ IntelliJ, noch mit Kotlin als Programmiersprache irgendwas anfangen konnte.  
Da ich im 3. Semester bin und zuvor nur grundlegende Kenntnisse über C++ habe, war die Einarbeitung etwas gewöhnungsbedürftig.  
Jedoch hat sich herausgestellt, dass die Arbeit mit Kotlin wesentlich angenehmer ist, da die Sprache einiges für den  
Entwickler übernimmt und man generell viel einfacher und mit weniger Programmieraufwand bei vielen Dingen ans Ziel kommt.  
Für die Einarbeitung habe ich mir viele Tutorials angeschaut und mich an kleinen Demos selbst getestet.  
- Ben Joel und ich waren für das Vorstellen des Themas "Mocking mit Mockito" zuständig, was in der Theorie zwar gut verständlich ist,
sich bei der Implementierung in der Praxis aber als nicht so durchschaubar rausgestellt hat. Man hat beim Import/ Einfügen der
notwendigen Dependencies schon einen Kampf, es überhaupt zum laufen zu bringen. Dennoch haben wir uns gut in das Thema eingearbeitet
und eine gute Grundkenntnis dafür entwickelt.
- Die nächste Hürde war Desktop Compose, wofür es leider nur wenig brauchbares Material gibt. Auch da war Tutorials und Guides 
anschauen angesagt. Auch habe ich immer mal wieder in das Beispielprojekt "PeopleInSpace" reingeschaut, was einem, wenn auch nicht immer, nochmal ein bisschen für das Verständnis der allgemeinen Struktur geholfen hat. Durch die Mischung aus beiden war mein 
Verständnis schon so weit, dass ich sehr simple Anwendungen für Desktop erstellen konnte. 


## Wochen 10.11.2021 bis 24.11.2021
### Was ist alles passiert?
- In diesen 2 Wochen habe ich so viel Zeit investiert, dass es sich angefühlt hat, als würde das Studium nur aus dem PSE Projekt bestehen. Den Richtwert von 10 Stunden pro Woche habe ich deutlich überschritten (teilweise in drei aufeinanderfolgenden Tagen
schon fast 20 Stunden investiert). Das lag erstens daran, dass man 80 bis 90 Prozent der Zeit überhaupt erstmal damit verbracht hat, für das Thema/ für das Problem passende Guides und Tutorials zu suchen, und wenn man dann mal zufälligerweise etwas gutes gefunden hatte (nicht gerade viel brauchbare Dokumentation über Desktop Compose), dann musste man sich da erstmal einarbeiten, verstehen,
dies reproduzieren und dann gab es schon die nächsten Probleme. Auch, wenn man 1 zu 1 das gemacht hat, was bei den Guides/ Tutorials 
ohne Probleme funktioniert hat, dann lief es bei einem selbst sehr häufig nicht. Das lag sehr oft an den Imports, Plugins und Dependencies, für die man wieder viel Zeit investieren musste, zu finden, wie man die gewünschten Funktionen in das eigene Projekt
einpflegen kann. Hat man nichts dazu gefunden, musste man sich über Umwege oder andere Ansätze Gedanken machen und die Suche ging
wieder von neuem los.
- Als issues hatten Ben Joel und ich die Features #8 "Vertikaler Prototyp: Desktop Anwendung" und #14 "Desktop: Skizze markieren", wovon 
der Drag and Drop Canvas höchstens 5% und der vertikale Prototyp den Rest der Zeit beansprucht hat. Beim Canvas haben wir uns an der
Android Gruppe orientiert und deren Code so weit wie möglich übernommen. Nachdem dies von Ben Joel durchgeführt wurde, habe ich mich ans Aufräumen und an die Code Inspection gesetzt. Die Code Insepction warnings waren schnell beseitigt und auch das Auftrennen der
teilweise zu Blob-Funktionen neigenden Funktionen lief reibungslos und ich habe noch Kommentare (Kotlin Kommentierstandard) hinzugefügt. Funktional gab es noch zwei Probleme: Das erste Problem war das Anzeigen eines Bildes auf dem Screen, wofür ich schon
für den vertikalen Prototyp mehrere Stunden lang an der Recherche und am Rumprobieren verzweifelt bin und es letztendlich (da es eh nur fürs Testen genutzt werden würde) verworfen habe. Ein Stichwort bei der Suche nach einer Lösung war "BitmapFactory", worüber ich oft im Internet gestolpert bin. Jedoch ließ sich dies aufgrund von Imports/ Dependencies nicht in unser Feature einpflegen.
Für den Canvas war dies aber unumgänglich. Am Ende hat es Ben Joel aber dann doch hinbekommen, dass ein Bild angezeigt wird.
Das zweite Problem hatte auch schon die Android Gruppe: Das Erscheinen einer neuen dragbaren Box, wenn schon eine in der Skizze angeheftet ist. Da Rekursion in Desktop Compose aber viel schwerer durchzusetzen ist als gedacht, haben wir es erstmal mit einer dragbaren Box sein lassen (dies war auch nur angefordert erstmal).
Der vertikale Prototyp Desktop war ein ganz schöner Brocken an Arbeit. Ich hatte es mir anfangs viel einfacher vorgestellt, als es im Endeffekt dann war. Für dieses Issue ging am meisten Zeit drauf. Am Anfang musste ich mich erstmal tiefgründiger mit Compose beschäftigen, da das Issue doch mehr abverlangt hat als man sich in den ersten zwei Wochen angeeignet hatte. Wir hatten ständig mit Imports, Plugins und Dependencies zu kämpfen. Zum eigentlichen Coden kam es anfangs nicht wirklich oft. Ich habe mich dann an das Einfügen von Bildern für einen Patienten gewagt, was nur ganz knapp nicht gescheitert ist, wäre ich nicht auf das passende Guide gestoßen. Die Herausforderung bestand darin, den File Explorer zu triggern, wo dann die gewünschten Bilder ausgewählt werden können.
Von "Solche Funktionen werden noch nicht von Desktop Compose unterstützt" bis hin zu Lösungen, die nur für Android anwendbar sind (und das waren ganz schön viele; Blöd gelaufen für Desktop...) gab es glücklicherweise einen einzigen Reddit Post, der mich zum Ziel geführt hat. Mit der von dem Ersteller entwickelten Funktion funktionierte das Öffnen des File Explorers auf Windows bei Knopfdruck reibungslos. Das nächste Problem war, dass die Liste, welche die Files aus dem File Explorer übernimmt, immer leer war. Dies war aber auf eine falsche Konvertierung zurückzuführen, was auch schon wieder einiges an Recherchezeit gekostet hat.
Als dann alles vom Frontend gepasst hat, gab es schon wieder die nächsten Probleme:
In den Akzeptanzkriterien war angegeben, dass alle Daten in der MongoDB gespeichert werden sollen. Deswegen haben Ben Joel und ich uns in die Implementation der MongoDB in unseren Prototypen eingearbeitet, uns sogar mit den Leuten vom Issue mit der MongoDB besprochen, deren Funktionen übernommen und in unser Issue eingepflegt, MongoDB installiert und dann alles mit Testdaten getestet.
Dies hat wieder viel Zeit beansprucht (die, wie sich später herausgestellt hat, verschwendet war), hat aber im großen Ganzen funktioniert. Durch Zufall haben wir dann bei der Android Gruppe in deren Issue-Kommentaren und von ihnen selbst erfahren, dass das Anbinden der MongoDB nicht gefragt war. Auf Nachfrage haben wir dann erfahren, dass wir zum Speichern der Daten REST benutzen sollen. Wieder einmal Recherche, Testen und dann eine eigene REST API geschrieben (wenn auch nur minimal). Doch das war auch nicht das, was verlangt wurde. Nach diesen zwei Fehlschlägen wissen wir jetzt, wie man Daten in eine REST API und in die MongoDB speichert, jedoch war alles ein unnötiger Mehraufwand, den wir komplett über den Haufen schmeißen mussten. Am Ende haben wir dann mit dem eigentlich Verlangten, und zwar die Nutzung der bereitgestellten Funktionen mit Verknüpfung der Endpunkte, begonnen.
Ohne die zwei unnötigen Zwischenschritte hätte es zwar auch viel Zeit beansprucht, aber wäre wesentlich angenehmer gewesen.

### Was nehme ich für die nächsten Wochen mit?
- Nicht mehr so lang an etwas verzweifeln, eher andere aus dem Projekt fragen
- Das Git ist nicht unveränderbar und nicht alles im Git ist perfekt beschrieben. Ich habe gelernt, das man nicht wie gewohnt durch ein Hochschulprojekt/ Praktikum getragen wird in PSE, sondern, dass dieses Projekt ein Projekt aus der realen Arbeitswelt widerspiegeln soll, wo nicht alle Issues bis ins kleinste Detail ausgeführt wird und man den PO durchlöchern muss mit Fragen :). Auch habe ich gelernt, dass das Git nichts Starres und Unveränderbares ist, was irgendein Professor vorgibt, sondern wir selbst aktiv werden können im Umgang damit (Issues anlegen auf Anfrage etc.)
- Mehr an den Richtwert von 10 Stunden pro Woche halten. Klar, die Issues sollten schon gemacht werden, aber man hat halt nicht nur das Projekt, sondern auch andere Veranstaltungen und Abgaben
- Kommunikation ist, vorallem bei vertikalen Prototypen, das A und O


## Woche 24.11.2021 bis 01.12.2021
### Was ist alles passiert?
- In dieser Woche haben wir noch die Anbindung der KTOR API weitestgehen fertiggestellt #8 . Das einzige, was noch nicht funktioniert, ist den Token vom Login für den Zugriff so zu extrahieren, dass dieser brauchbar ist für weitere Operationen
- Auch haben wir ein weiteres Issue aufgenommen: #26 Das Farbschema der Anwendung. Ben Joel hat sich um die Farbauswahl und um das Erstellen eines Themes gekümmert. Nach gescheiterten Versuchen, Color und Theme in den common Pfad einzupflegen, gab es schon wieder Probleme mit irgendwelchen Dependencies bezüglich Compose. Dies haben wir auch schon der jeweiligen Person, die sich um das initiale Projekt gekümmert hat, mitgeteilt. Bis sich das geklärt hat, werden wir durch das initiale Projekt, aber auch von den Frontend Issues blockiert, denn auf diese müssen wir die Themes am Ende anwenden
- Die Woche war wesentlich angenehmer als die letzten Wochen, aber trotzdem noch genug Arbeit  


## Woche 01.12.2021 bis 08.12.2021
### Was ist alles passiert?
- Leider hat das Einbinden des Farbschemas #26 und des Layouts in den common path des Projekts zur gemeinsamen Nutzung der Resourcen nicht funktioniert, weshalb wir deswegen die beiden Files lokal in den jeweiligen paths ausprobiert haben. Dies hat funktioniert
- Da Frau Trapp schon öfters mitbekommen hat, dass es Probleme mit Desktop Compose gibt und wir uns bei jedem Issue unnötig an der noch nicht ganz ausgereiften Technik schwergetan haben. Deshalb war Frau Trapps vorschlag, die Entwicklung für Desktop für das Projekt auf Eis zu legen und uns ersmtal auf Android zu konzentrieren, was auf jeden Fall eine gute Entscheidung für den weiteren Verlauf des Projekts war
- Aufgrund des letzten Punkts haben wir die Files für das Farbschema und das Layout in den android path für die App eingefügt
- In dieser Woche habe ich mich neuen Issues zugeteilt: #21 "Wireframe BodyMapping" und #24 "Android: Implementation BodyMapping".
Am Anfang haben Ben Joel, Dominik, Floristan und ich uns zusammen an eine große Idee in Form eines ersten Entwurfs, wie der Screen am Ende aussehen könnte, gesetzt und schon eine grobe Idee auf Mural zusammengebastelt, wie es am Ende aussehen könnte. Wir haben uns im Anschluss nochmal die Meinung von Frau Trapp eingeholt. Auf das Okay von Frau Trapp konnten Dominik und ich dann in der nächsten Woche schon mit der Entwicklung anfangen

### Was hat alles gut funktioniert?
Die Zusammenarbeit mit Dominik und Floristan, die eigentlich Team 1 zugewiesen sind, lief unfassbar gut. Wir konnten immer gut Termine vereinbaren und jeder hat seine Ideen zum Entwurf beigetragen, sodass wir am Ende schon eine zufriedenstellende Idee hatten.


## Woche 08.12.2021 bis 17.12.2021
### Was ist alles passiert?
- Vor der Winterpause haben Dominik und ich schon angefangen, die im Entwurf sichtbaren Elemente als Composables in der App zu übernehmen, damit wir schonmal Frontend-technisch etwas entwickeln konnten. Wir haben die wesentlichen Frontend-Komponente des Screens als Composables implementiert: Die Anzeige für das Profilbild des ausgewählten Hundes, der Name, Datum, sogar schon eine Liste für weitere Bilder wie Ultraschallbilder, ein Canvas, auf dem das Outline des Hundes der Tabletgröße entsprechend skaliert und eine Leiste für die Draggables mit einem halb funktionerenden Draggable.  
Das Draggable, welches wir schon in #14 entwickelt haben, mussten wir so anpassen, dass ein neues Draggable an der anfänglichen Position erscheint, wenn dieses auf eine OnDragFläche gezogen wurde. Dies hat einige Probleme bereitet.
Jedes mal, wenn ein Draggable von der Anfangsposition weggezogen wurde, ist zwar ein neues Draggable erschienen, aber um eine Draggable-Hitbox nach unten versetzt. Da wir auf die Schnelle keinen Fix für das Problem gefunden hatten, haben wir es bei den wesentlichen Frontend-Items belassen.

### Was hat alles gut funktioniert?
Die Teamarbeit hat wieder super funktioniert. Wir konnten uns immer einen Termin ausmachen, an dem wir am Issue weitergearbeitet haben.
Wir haben uns auf die Issues #21 und #24 verteilt: Ben Joel und Floristan haben den Wireframe übernommen und Dominik und ich haben uns an die Umsetzung gesetzt. 


## Winterpause bis zum Mittwochstermin am 12.01.2022
Die Winterpause habe ich für andere Module genutzt und hauptsächlich zum Entspannen, da vorallem die ersten Wochen sehr zeitintensiv und anstrengend waren.


## Woche 12.01.2022 bis 26.01.2022
### Was ist alles passiert?
In diesem Sprint habe ich den Fokus auf die Implementation der Bodymap gerichtet, da dieser Screen zusammen mit der Anamnese und dem Dashboard einer der wichtigsten Screens darstellt. Nachdem Ben Joel ordentlich viel Arbeit geleistet hatte (Fix des Bugs mit den Draggables, erstellen der ersten OnDragFlächen, Unterteilung des Canvas in mehrere Sektoren und vieles mehr), habe ich mich dann an das Mapping der OnDragFlächen auf dem Canvas gesetzt #24 . Zuvor haben wir nochmal in der Gruppe über die weitere Vorangehensweise an das Issue gesprochen und letztendlich das Issue intern in mehrere kleine Teilissues unterteilt, damit sich jeder, der daran gearbeitet hat (dem Issue wurden noch nachträglich weitere Mitglieder zugewiesen), einfach ein Teilissue nehmen und dies in Ruhe bearbeiten konnte.
  
Das Mappen der OnDragFlächen habe ich mir ehrlich gesagt einfacher vorgestellt, als es am Ende war. Es gab mehrere Aspekte, die diese Teilaufgabe erschwert haben. Jedes OnDragField konnte ich anhand der diesen zu übergebenden Parametern xKoordinate, yKoordinate und Rotationsgrad mit ein bisschen rumprobieren auf die richtige Position auf dem Canvas mappen. Mir war anfangs schon bewusst, dass sich mit einer anderen Größe des Tablets, sich auch die Positionen der Flächen verschiebt. Deswegen habe ich mit einem anderen Tablet geschaut, ob sich die Flächen noch auf der gleichen Position befinden. Da ich aber erst 3 Koordinaten gemappt hatte, sah es leider nur so aus, als würden die Positionen mit der Skalierung des Canvas mitskalieren.
Ich habe mich also dann am Samurai-Hund orientierend an das Mapping aller Flächen auf die Outline des Hundes im Canvas gesetzt.
Am Ende sind es, grob wie beim Samurai-Hund, insgesamt 92 Flächen geworden, die ich alle händisch mit merhmaligem Checken durch Starten des Emulators gesetzt und überprüft habe, ob die Position und die Rotation stimmen. Dies war eine sehr zeitintensive und feine Arbeit, da ich bei der kleinsten Abweichung wieder die Koordinaten minimal ändern und dies wieder überprüfen musste. Dummerweise habe ich erst, nachdem ich alle 92 Flächen gesetzt habe, wieder überprüft, ob die Flächen auch auf anderen Tablets richtig gesetzt wurden. Das war jedoch nicht der Fall. Alle Flächen waren komplett in die obere Ecke gestaucht, da das Tablet größer war.
Daraufhin habe ich viele Dinge ausprobiert, das Problem irgendwie zu fixen, aber vergeblich. Ich habe mich dann an Ben Joel gewendet und ihn gefragt, ob er die Implementation der Liste für die Flächen dort einfügen kann, wo auch die Skalierung des Canvas geschieht, damit ich damit an irgendeinen Faktor gelangen könnte, den ich mit den Koordinaten für eine Mitskalierung multiplizieren kann.
Ben Joel hat dies dann umgesetzt und ich habe wieder lange daran gesessen, irgendwie einen Faktor aus der Skalierung zu extrahieren.
Da die Skalierung des Outlines vom Hund im Canvas aber automatisch mit Compose funktionierte, gab es keinen Wert, den ich irgendwo extrahieren konnte. 
Ich hatte dann aber die Idee, die Gesamtgröße des Canvas, die verfügbar war, und das Größenverhältnis des auch von mir neu erstellten Bildes für die Outline des Hundes (die alte Version hatte ein Wasserzeichen und war ziemlich unscharf) zu verwenden, um daraus die Skalierung der Positionen zu berechnen.
Das Bild für die Outline des Hundes befindet sich in der Mitte des Canvas, sodass ich dafür einen Offset für die Flächen berechnen musste. Das Bild habe ich ursprünglich mit 1000x600 Pixeln erstellt, was ein X zu Y Größenverhältnis von *0.6 entspricht.
Diese Daten habe ich genutzt, um diese mit in die Koordinaten einrechnen zu können. Glücklicherweise waren diese dann auch auf anderen Tablet auf den vorgesehenen Positionen.
Mit ist aber aufgefallen, dass das Draggen eines Draggables sich als etwas schwierig herausgestellt hat, da die Flächen an feinen Stellen sehr dicht nebeneinander waren und auch viel zu klein, um mit einem Finger im Weg ein Draggable darauf zu ziehen.
Auf Absprache mit Frau Trapp habe ich dann die feinen Stellen, die meist aus 3 Flächen bestanden, zu einer OnDragFläche zusammengefasst und vergrößert. Auch habe ich nachträglich die Toleranz-Werte des von Ben Joel entwickelten Toleranz-Systems für das Detekten eines Draggables in der Nähe der Hitbos eines Feldes geändert, da sich die Detektion oft überlappt hatte und man fälschlicherweise zwei Flächen markiert hat.
Mit dem Endergebnis bin ich aber sehr zufrieden, da die Skalierung endlich funktioniert hatte und die Usability noch hoch genug war, ein Draggable auf die Felder ziehen zu können.

### Was hat alles gut funktioniert?
Dieses mal habe ich mich an meine aus früheren Sprints gewonnenen Erkenntnisse gehalten und bin direkt auf die Anderen eingegangen, wenn ich mal nicht weitergewusst habe. Dies hat mir viel Zeit erspart, da die anderen am Issue beteiligten Personen schnell auf mich eingegangen sind und geholfen haben.
Außerdem bin ich froh, es selbstständig am Ende mit der Skalierungsberechnung hinbekommen zu haben. Dazu gab es nämlich nichts Brauchbares im Internet und ich musste selbst einen Lösungsvorschlag dafür finden.
Man hat auf jeden Fall am Ende gespürt, dass jeder das Projekt mit einem guten Ergebnis fertigstellen möchte, was einem selbst auch nochmal einen Motivationsschub gegeben hat.  


## Woche 26.01.2022 bis 02.02.2022
Wir haben alle vor Sprintende nochmal dafür gesorgt, dass alles fertig wird und alles gemergt werden kann. Wir haben uns alle zusammengesetzt, Mergerequests erstellt, diese reviewt und das gesamte Projekt zusammengepuzzelt, sodass alles am Ende richtig funktioniert hat.


## Selbsteinschätzung
Am Anfang war ich ein wenig überfordert mit den Themen, in die wir uns einarbeiten sollten und mit den Issues, die dann verteilt wurden.
Ich habe mich am Anfang dann erstmal mit dem Thema "Mockito" und Mocking im Allgemeinen auseinandergesetzt, worüber Ben Joel und ich auch eine Präsentation gehalten hatten. Im ersten Sprint habe ich mir dann mit Ben Joel zugetraut, ein relativ großes Issue zu übernehmen, was nicht nur wegen der neuen Technologie "Compose für Desktop" und der neuen Sprache Kotlin einarbeitungstechnisch besonders viel war, sondern auch entwicklungstechnisch viel abverlangt hat. Wir mussten einen kompletten Screen mit Composables, von denen ich zuvor noch nie was mitbekommen habe, in einer mir damals unbekannten Sprache erstellen. Das war sehr viel Einarbeitung und danach noch viel Arbeit (inklusive Recherche). Dann musste ich mich auch noch ein wenig mit dem Backend auseinandersetzen, da der Screen mit der Datenbank (Stichwort vertikaler Prototyp) verknüpft werden sollte. Die Bearbeitung der Issues in den ersten Wochen war sehr zeitintensiv und ich habe deutlich mehr Zeit in das Projekt investiert, als gefordert war.
Die gewonnene Erfahrung konnte ich dann noch ein bisschen in das Issue "Skizze markieren" einfließen lassen.
Anfangs hatte ich mich auch noch öfters darum gekümmert, dass der Code sauber, also frei von Code Inspection Warnings und Errors und auch von mir noch erkannten Makel, und kommentiert war, um für einen schnelleren Mergevorgang zu sorgen.
Zur Abwechslung zum reinen Coden habe ich mich mit Ben Joel um das Farbschema gekümmert, wo ich dann die Files an die richtige Stelle eingebunden habe und mich auch über Farbschemen und Layouts und über deren Implementation und Einbindung in das Projekt informiert habe.
Auch in der Mitte der Projektzeit habe ich mich wieder an ein weiteres großes Issue einteilen lassen, und zwar für das Bodymapping.
Für das Bodymapping habe ich anfangs in der Entwurfsphase meine Ideen mit eingebracht und später bei der Implementation zusammen mit Dominik einen Grundgerüst für das weitere Entwickeln geschaffen. Im weiteren Verlauf habe ich noch an Teilfunktionen gearbeitet und am Ende den Code wieder kommentiert und ein wenig gesäubert. Auch dieses Issue war vom Aufwand sehr groß und hat einiges an Arbeit erfordert.

An diesen Issues habe ich aktiv gearbeitet:
- #8
- #14
- #21
- #24
- #26

Auch, wenn die meiste Zeit in die aktiv von mir bearbeiteten Issues geflossen ist, habe ich mich auch öfters mit an nah verwandte Issues dazugesetzt (z.B. für die Android Version unserer Issues), wenn es dort ein Problem gab und ich meine Ideen mit einfließen lassen konnte.
Ich finde, ich habe einen guten Beitrag zum Projekt geleistet, da ich 
- viel Zeit in dieses investiert habe
- mich bestmöglich in die Themengebiete eingearbeitet habe und so auch ein Ansprechpartner für bestimmte Themen (z.B. Compose) war
- mit an großen und wichtigen Issues für das Projekt mitgearbeitet habe, die fast alle den Schwerpunkt auf Coding hatten
- immer versucht habe, mich an eng verwandte Issues zu setzen, auch, wenn es nur das "bloße Dabeisein" auf dem Discord war, da ich auch dort meine Ideen vorschlagen konnte  
  
Zurückblickend auf das Projekt und orientiert an der Arbeit der anderen Teammitgliedern würde ich mir selbst die Note 1,5 geben.
Gegen Ende hin hätte meine Motivation noch ein wenig besser sein können, ansonsten denke ich, dass ich sonst viel Gas in dem Projekt gegeben habe und ich viel aus dem Projekt gewinnen konnte.


