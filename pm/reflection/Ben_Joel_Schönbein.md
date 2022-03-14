# Selbstreflektion Ben Joel Schönbein
## Wochen 27.10.2021 bis 10.11.2021
### Erreichtes:
- In diesen Wochen habe ich mich mit dem Projekt zurecht gefunden und habe eine Idee entwickelt, dieses anzugehen.
- Ich habe mir einen Überblick über Kotlin verschafft und etwas Verständnis dafür entwickelt.
- Mir ist JetPack Compose klarer geworden und ich kann damit eine einfache Desktop Anwendung erstellen.

### Was zu verbessern ist:
- Ich muss mir mehr Zeit für das Projekt nehmen.
- Ich sollte mir einen besseren Überblick über die Möglichkeiten von Compose verschaffen.

## Wochen 10.11.2021 bis 24.11.2021
### Erreichtes:
- Ich habe in IntelliJ Idea einen Vertikalen Prototypen erstellt, der die Basisfunktion eines Drag and Drops enthält. Ich habe mir dazu den Code des vertikalen Prototypen der Android Gruppe genommen. Dies war soweit ohne viel Hindernisse zu bewältigen. Nur das einfügen in Android Studio, genauer in das initiale Projekt war komplizierter, trotzdem funktioniert der Code. Er muss nur noch in das initiale Projekt von den Githeads gemerged werden.
- Der vertikale Desktop Prototyp hingegen war und ist noch eine große Herausforderung. Desktop Compose richtig zu implementieren war sehr kompliziert, vorallem weil dieses Framework noch in der Beta ist. Das Frontend schreiben war an sich kein Problem, es ist etwas anders, als man es normalerweise gewohnt ist, aber sonst war es realisierbar und die Funktionen im Frontend zum anlegen und bearbeiten von Patienten ist ebenfalls weitestgehend vorhanden. Was am meisten Zeit gekostet hat und noch kostet, ist die Implementierung von Endpunkten der Api. Ich hatte zuvor seinesgleichen noch nie gemacht und dementsprechend war es sehr schwer dort überhaupt einen Ansatz zu finden, was sehr viel Nerven gekostet hat. Nach einem Gespräch mit der Ktor Gruppe ist mir jetzt das zugreifen auf die Api etwas klarer geworden, nur kann man das nicht wirklich testen, zumal ich bisher auch nicht weiß wie man richtige Unit tests schreibt, da dies erst noch in diesem Semester behandelt wird. Theoretisch bestehen die Funktionen zum anlegen, abrufen und ändern von Patienten bereits, nur weiß ich nicht, ob diese überhaupt funktionieren, da es noch keine Anbindung zur Api dieses Projektes gibt. Das realisieren eines Bilder uploads ist ebenfalls noch problematisch, da es dazu bisher auch sehr wenig Literatur gibt. 

### Eindrücke aus diesem Sprint:
- Zusammengefasst sind wir relativ weit gekommen, vorallem weil Bryan und ich noch im 3. Semester sind und eigentlich noch überhaupt keine Ahnung von dem haben, womit wir arbeiten. Nur war der ganze Aufwand war mental sehr belastend und ich hab es teilweise körperlich gemerkt, da ich oftmals mehr als 10 Stunden am Stück am Rechner saß und an dem Projekt gearbeitet habe.

### Was ich mir in Zukunft vornehmen werde:
- Ich werde mir in Zukunft Issues zuteilen, mit denen ich besser klar komme und die eher für mich geeignet sind. Ich werde zwar noch das Pensum von mindestens 10 Stunden pro Woche investieren, nur kann ich mir nicht teilweise 20 Stunden pro Woche leisten, zumal ich noch andere Sachen für das Studium zu erledigen habe.

## Wochen 24.11.2021 bis 08.12.2021
### Erreichtes:
- Wir haben weiteren Fortschritt mit den Endpunkten gemacht, mit denen kann man jetzt auf die Datenbank zugreifen die Authentifizierung funtkioniert auch, nur scheint es noch mit der Datenbank probleme zu geben, die noch gelöst werden müssen. Des weiteren muss man nochmal über die GET funktion schauen, da ich mir noch nicht sicher bin, ob diese richtig funktioniert.
- Das Farbschema haben wir ebenfalls erstellt und es müsste noch in das Android Frontend eingebunden werden, wenn dieses fertig ist. Dieses Issue wird from Frontend generell blockiert.
- Der vertikale Canvas Prototyp wurde diese Woche auch auf den Master gepushed.
- Der Wireframe Bodymap Prototyp wurde diese Woche ebenfalls fertig gestellt und ist für ein Sprint Review bereit.
- Es wurde auch parallel mit der Bodymap in Compose angefangen, wo wir schon auf einige Grenzen von Compose gestoßen sind, die wir noch umgehen müssen. Dieses Issue wird wahrscheinlich noch viel Zeit und Arbeit in Anspruch nehmen.

### Eindrücke aus diesem Sprint:
- Die Zusammenarbeit in diesem Sprint war um einiges besser als zuvor, zudem waren wir auch viel produktiver. Ein Problem, was ich sehe ist, das vorallem im vertikalen Prototyp noch einiges geändert werden muss, dass alles so funktioniert, wie es soll.
- Ich fande in diesen Wochen meinen Arbeitsaufwand sehr inordnung, es war gut ausbalanciert und es war entspannter als im Sprint zuvor.

### Was ich mir in Zukunft vornehmen werde:
- Ich werde meinen Arbeitsaufwand wie aus den letzten Wochen beibehalten, da es für mich gut funktioniert hat.
- Ich will in naher Zukunft den vertikalen Prototyp fertig bekommen, dass ich mich mehr um die anderen Issues kümmern kann.

## Woche 08.12.2021 bis 17.12.2021

### Erreichtes:
- In dieser Woche haben wir es geschafft den vertikalen Prototypen für eine Desktop Anwendung fertig zu bekommen und haben diesen im Sprint Review präsentiert. Zudem haben wir noch das Farbschema fertig bekommen, welches wir jetzt durch die Fertigstellung der vertikalen Prototypen integrieren konnten.

### Eindrücke aus diesem Sprint:
- Das ist die letzte Reflection im Jahr 2021, weshalb ich mich dazu nicht ausführlich äußern kann, da diese nur eine Woche behandelt. Dennoch kann ich sagen, dass der zweite Sprint um einiges besser war als der erste, es gibt zwar noch ein Paar Sachen, die man verbessern kann, diese wurden auch in der Retro angesprochen. 

### Was ich mir in Zukunft vornehmen werde:
- Da jetzt Winterferien sind, werde ich mich erstmal erholen. Ich werde mein Fokus auf andere Module legen und die Abgaben für das nächste Jahr bearbeiten, wodurch ich mit weniger Stress in den letzten Sprint starten kann.

## Wochen 12.01.2022 bis 26.01.2022

### Erreichtes:
- In dem Sprint habe ich an der Implementation  der Bodymap gearbeitet. Anfangs überlegte ich mir, wie man die Farbflächen effizient einbinden kann, dass diese leicht erweiterbar und veränderbar sind. Ich kam auf die Idee mit Klassen zu arbeiten, obwohl dieses Verfahren in Jetpack Compose bisher kaum verbreitet ist. Das einbinden von Composables als Klassen Methoden funktioniert gut, was eine Redundanz im Code verhindert. 
In der ersten Woche habe ich ein Grundgerüst für die Bodymap mit den ziehbaren Markern fertig bekommen. Man kann diese Draggables auf bestimmte Felder ziehen, die darauf ihre Farbe ändern und zudem die jeweiligen Attribute für Schmerz und Spannung der Feldklasse geändert werden, wodurch das Initialisieren und Speichern der Bodymap in eine andere Datenklasse und dementsprechend auf eine Datenbank einfach ist. 
In der zweiten Woche habe ich die Slider zum Abändern der Feldfarben implementiert (ebenfalls als Klassen Methode). Die Farben werden als Kombination aus Schmerz und Spannung dargestellt. Dazu werden ebenfalls die Integer für Schmerz und Spannung angepasst. 
Die Farbflächen sind anklickbar, wenn diese gefüllt sind, wodurch sich dann die jeweiligen Boxen für Spannung und/oder Schmerz öffnen, worüber man die jeweiligen Werte ändern oder entfernen kann, die Farbe des Feldes passt sich dementsprechend wieder den neuen Werten an. 
Ein Problem, das aufkam war die Performanz der Bodymap, wenn viele Felder vorhanden sind. Das kam dadurch zustande, das bei jedem Ziehen eines Draggables durch eine Liste, welche mit den Farbflächen gefüllt ist iteriert wird. 
Dieses Problem habe ich behoben, indem ich diese Liste in Sektoren aufgeteilt habe. Es gibt auf dem Canvas 9 Sektoren, die jeweils die gleiche Größe haben und ebenfalls jeweils eine Liste von Farbflächen sind. Die Farbflächen werden abhängig von deren Positionen in die jeweilige Sektor Liste geladen. Jetzt wird nur noch durch die Liste von Farbflächen iteriert, in deren Sektor sich das jeweilige Draggable befindet. Es hat zwar noch ein paar performante Probleme, aber es ist wesentlich besser als das System davor. 
Ein Problem der Sektoren war ebenfalls, das wenn sich eine Farbfläche an einer Sektorgrenze befindet, man über dieser Farbfäche war und dann den Sektor verlies, sich die Farbfläche nicht wieder deaktiviert hat. 
Dieses Problem habe ich gelöst, indem sich alle Farbflächen in den anderen Sektoren sich automatisch deaktivieren, falls sich das Draggable nicht in dem jeweiligen Sektor befindet. 
Alles in Allem habe ich die ganze Struktur der Bodymap erstellt, auf die man die dazugehörigen Funktionalitäten aufbauen kann. 
- Ich habe der Anamnese Gruppe ebenfalls geholfen deren UI zu implementieren, da ich mich jetzt relativ gut mit Compose auskenne. Ich habe mit Robin Holzwarth ebenfalls eine Klassenstruktur implementiert, wodurch das Erweitern und Modifizieren von Organen und Muskeln einfacher ist. Ich habe die zu der Anamnese gehörenden Checkboxen Mittels Enum Klassen implementiert. 
Der Slider wirkt sich ebenfalls auf eine Enumklasse aus, wodurch die Lesbarkeit und Integrität der jeweiligen Klasse vereinfacht wurde. Das Textfeld für Kommentare wurde ebenfalls angepasst. Und man kann mittels Buttons zwischen der Organ und Muskelanamnese wechseln.

### Eindrücke aus diesem Sprint:
- Da ich mich schon länger in diesem Projekt mit Jetpack Compose befasst habe, fiel es mir wesentlich einfacher als zum Start des Projekts die Bodymap zu implementieren. Ein Problem war, dass es zu Draggables etc. eigentlich keine hilfreichen Quellen im Internet gibt, wodurch ich mir das meiste selbst ausdenken musste. Das Drag and Drop habe ich strukturell dem Prototypen entnommen. Zudem kam noch dazu, dass Klassen im Zusammenhang mit Composables auch noch meist ungenutzt ist, wobei das meiner Meinung nach sehr mächtig ist. Ich kenne mich mittlerweiler recht gut mit Jetpack Compose aus, was es einfacher macht anderen Gruppen zu helfen, die sich weniger damit auskennen. Leider muss ich sagen, dass ich vieles alleine gemacht habe, das ist wahrscheinlich der Tatsache geschuldet, dass sich die anderen Gruppenmitglieder kaum mit Jetpack Compose befasst haben und Dominik dann auch noch an Corona erkrankte. Das hat mir viel Zeit gekostet, vorallem das Lösen von aufgekommenen Problemen.

## Woche 26.01.2022 bis 02.02.2022

### Erreichtes:
- Wir haben die Bodymap finalisiert, die Funktionalität zum Abspeichern der Bodymap als jpg wurde richtig eingebunden und das Laden von Patientendaten und das Speichern der Bodymap auf einen Patienten wurde ebenfalls implementiert. 
Das Abrufen der Endpunkte war wieder etwas kompliziert und hat viel Zeit gekostet. 
Dennoch funktioniert das Abrufen der Variable selectedPatient aus dem Dashboard.
Zudem waren der Großteil der am Projekt beteiligten Studenten am Ende beim mergen und haben dabei geholfen, dass alles richtig funktioniert.

# Selbsteinschätzung:
Ich hatte beim Start des Projektes Probleme mich in das Projekt hinein zu arbeiten. Ich war überfordert, da alles relativ neu war und ich keinen Überblick hatte. Ab der zweiten Woche habe ich angefangen mich richtig mit Jetpack Compose zu befassen. Wir hatten Probleme uns das Framework anzueignen, da es viele Probleme mit der IDE gab und Compose Desktop noch kaum anwendbar ist. 
Nach und nach habe ich mich jedoch immer besser in das Projekt eingefunden und konnte nach viel recherchieren, mehr mit Jetpack Compose anfangen. 
Zum Ende des ersten Sprints hin gab es Probleme mit der Einbindung von Endpunkten. Dort haben wir sehr viel Zeit investiert, teilweise auch ganze Nächte um diese in unser Frontend einzubinden. Dafür haben wir bis zum zweiten Sprint gebraucht, um die Endpunkte zum laufen zu bekommen. Spätestens zu diesem Zeitpunkt haben wir erkannt, wie wichtig die Kommunikation im ganzen Projektteam ist. 
Ab dem zweiten Sprint lief das meiste jedoch viel geschmeidiger und alle waren motiviert. 
Nach den Winterferien war diese Motivation meiner Auffassung nach jedoch wieder weg. 
Vorallem das Bodymap Issue war schwer einzuleiten, da meine Gruppe keine Idee hatte, wie man diese implementieren kann. 
Ich habe dann einfach die Initiative ergriffen und angefangen zu Coden. Mit dem Endergebnis bin ich recht zufrieden, vorallem mit den Mitteln die ich hatte. Ich helfe auch anderen Gruppen, wo ich mit meinem Wissen helfen kann (vorallem über Jetpack Compose). 
Die Entwicklung die ich während des Projektes nahm und noch nehme sind einer Benotung von 1.0 wert, da ich vorallem sehr einsatzbereit war (hervorheben möchte ich die Kenntnisse, die ich über Jetpack Compose erlangt habe) und viel mehr Zeit als gefordert in das Projekt gesteckt habe um ein gutes Produkt zu erstellen. 


