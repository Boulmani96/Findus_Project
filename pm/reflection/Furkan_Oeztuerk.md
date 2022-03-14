# Selbstreflexion: Furkan Öztürk
## Woche vom 27.10.2021 - 14.11.2021
- OpenAPI Präsentation
- Verteilung der Issues und Einschätzungen
- Einführung in Kotlin, KMM, Jetpack Compose, JUnit, Mockito und MongoDB
- Zusammenarbeit mit Partner an Issues

### Issues:
- #7: https://code.fbi.h-da.de/pse-trapp/findus/-/commits/feat/7-vertical_prototype_android
- #13: https://code.fbi.h-da.de/pse-trapp/findus/-/commits/feat/13-android_schmerzen_in_skizze_markieren

### Anmerkungen
Keine.

---

## Woche vom 14.11.2021 - 26.11.2021
- Weitere Implementierung des Issues #7.
- Probleme in #13 lösen.
- Zusammenarbeit mit Partner an Issues
- Verteilung der weiteren Issues und Einschätzungen

### Issues:
- #7: https://code.fbi.h-da.de/pse-trapp/findus/-/commits/feat/7-vertical_prototype_android
- #19 https://code.fbi.h-da.de/pse-trapp/findus/-/issues/19

### Anmerkungen
Die Implementierung von # 7 hat viele Probleme verursacht. Ein Grund dafür war, dass wir einige Akzeptanzkriterien falsch verstanden haben und dies in der Folge viel Zeit gekostet hat. Außerdem gibt Android manchmal kryptische Fehlermeldungen aus, die (eventuell) leicht zu beheben, aber schwer zu finden sind, was als Effekt auch viel Zeit kostet.

### Eigene Ziele
- Bei Problemen so zeitnah wie möglich nachfragen

---

## Woche vom 27.11.2021 - 15.12.2021

In diesem Sprint haben wir uns mit den drei Issues #7, #13 und #19 beschäftigt.

Für das Issue #19, die Navigationskomponente, haben wir uns anfänglich verschiedene Bibliotheken angeschaut, da es in diesem Issue darum ging eine gemeinsame Navigation für Android und für Desktop zu teilen. Eines dieser Bibliotheken ist [Decompose](https://github.com/arkivanov/Decompose). Die Verwendung dieser Bibliotheken hat viele Fehler ausgegeben, deswegen haben wir uns mit anderen Issues so lange beschäftigt und dieses Issue nach hinten geschoben. Nach einiger Zeit, nachdem die Desktop Variante vernachlässigt werden kann, haben wir uns wieder mit diesem Issue beschäftigt und dafür entschlossen Decompose mit [Navigation mit Jepack Compose](https://developer.android.com/jetpack/compose/navigation) zu ersetzen. Dieses zu Implementieren war relativ einfach und wurde dann anschließend auch relativ schnell reviewed und in den Master Branch gemerged.

Issue #7, der vertikale Android-Prototyp, ist was uns am größten Probleme bereitet hat. Zu einem war dieses Issue durch andere Issues blockiert, zum anderem war das Frontend fertig, aber konnten die Endpunkte nicht ansprechen, weil diese noch nicht fertiggestellt worden waren. Seit Samstag, seitdem wir die Endpunkte in der Android App ansprechen können, haben wir täglich stundenlang damit beschäftigt. Teilweise saßen wir 10+ Stunden an einzelnen Tagen nur an diesem Issue. Dieses kann man in der Gitlab Timeline sehen. Außerdem wurde der Code unzählig oft refactored, reviewed und erneut geändert. Nachdem alles funktioniert hat, wurde dieses Issue gestern abend erneut reviewed und anschließend mit dem Desktop Prototypen zusammen in den Master Branch gemerged.

Fazit:
Issues #13 und #19 sind normal abgelaufen. Issue #7 hat uns viel Zeit, Nerven und Meetings gekostet. Aber nun ist alles vollendet und wir können endlich mal wieder durchatmen.

---

## Woche vom 16.12.2021 - 02.02.2022

Wir haben uns mit dem Onboarding Issue #37 beschäftigt. In diesem Issue handelt es sich um eine einfache Einführung in die App die beim ersten Start des Apps angezeigt wird. Wir haben Bilder und Animationen mit Hilfe der [Lottie Bibliothek](https://lottiefiles.com/featured) eingerichtet. Außerdem mussten noch die passenden Rechte in der Android App dem Nutzer nachgefragt werden, diese sind zum Beispiel Zugriff auf Kamera- und Medien-Rechte um auf Bildern in der Gallerie zuzugreifen, die später für das Hochladen für Bildern nötig ist. Dieses Issue muss noch reviewt werden.
Nachdem dieses Issue vollständig fertig und reviewt wurde, wurde dieses in den Master gemergt. Anschließend haben wir (Alex B. und ich) noch die restlich verbleibende Zeit uns Issue #25 gewidment. Dort haben wir einige Problemen die noch nicht fertiggestellt wurden, wie z.B. den Save oder Delete Button vervollständigt, bzw. gefixt.

---

# Projektabschluss

## Was habe ich mitgenommen

Ich hatte viel Erfahrung mit Android, allerdings habe ich einiges wichtiges mitnehmen können bzw. neu gelernt:

- Kotlin. Kotlin ist eine sehr schöne Sprache für Androidentwicklung, meiner Meinung nach auch besser als Java. Das liegt daran das Java sehr verbose ("ausführlich") ist und alles explizit angegeben werden muss, wobei das in Kotlin mit einfachen Keywords wie var oder val angegeben kann (etwas Python-mäßig). Kotlin habe ich zum ersten mal dieses Semester programmiert und die Erfahrung war sehr gut, allerdings hatten wir am Anfang des Projektes Probleme, da uns die Kotlin Syntax zum ersten mal begegnete.
- Jetpack Compose. Hier kann ich leider sagen, das ich mit diesem Framework keine gute Erfahrung gemacht habe. Der Grund ist, das Jetpack Compose sehr neu ist (in 2020 veröffentlicht (?)) und es nicht viele Libraries gibt, bzw. die Libraries die es dafür gibt, sehr schnell veraltet oder durchaus einfach nicht funktionieren. Das Framework an sich selbst ist sehr gut und einfach, aber nur die Kommunikation mit Libraries bzw. anderen Frameworks ist sehr anstrengend, oder durchaus nicht möglich. Ich hätte mir anstelle dieses Frameworks Flutter gewünscht, da Flutter ausgereift (2017 veröffentlicht) ist und sehr viele Libraries hat mit denen alles reibungslos funktioniert und es sehr angenehm damit zu programmieren ist.
- Branches, Mergen und Merge Reviews auf Git. Auch wenn ich Git schon länger benutze, hatte ich noch nie so richtig mit vielen verschiedenen Branches gearbeitet. Außerdem ist das das erste Mal wo ich Merge Reviews durchgeführt habe. MR sind mir neu und ich dachte das Merge Reviews schwer sind, aber ich wurde hier positiv überrascht. Die Erfahrung mit MR war relativ einfach und gut, leichter als ich erwartet habe. In diesem Kurs kann ich also definitiv sehr viel für das Software-Leben mit Git mitnehmen.
- Andere Libraries, Seiten oder interessantes die ich in PSE gelernt und mitgenommen habe:
    - KMM (Kotlin Multiplatform)
    - JUnit
    - Mockito
    - MongoDB
    - Decompose
    - Navigation mit Jepack Compose
    - Lottie Website (schöne Animationen)
    - REST-APIs

---

## Selbsteinschätzung

Ich würde mich mit einer 1,7 schätzen. Mein Teampartner und ich hatten einige sehr schwierige Issues ausgewählt (insbesondere der vertikale Prototyp für Android #7 !) und haben uns teilweise an einigen Tagen 10+ Stunden an diesem Projekt hingesetzt, was viel Nerven und Zeit gekostet hat. 

Ebenfalls haben wir uns anderen Issues gewidmet, nachdem wir mit unseren Issues fertig waren, um anderen Teammitgliedern zu helfen.

Ein kurzer Rückblick auf das Projekt zeigt, wie viel wir wirklich in diesem Semester geschafft haben. Ich denke all diese Bemühungen haben sich gelohnt, es geht schließlich nicht nur um die Note, sondern auch um die gesammelten Erfahrungen in diesem Semester. Ich habe definitiv einige wichtigen Sachen für mich und mein Berufsleben mitnehmen können.

---

## Sonstiges

- Die Teamarbeit mit Alex B. war außergewöhnlich gut. Die Kommunikation war reibungslos, da er immer sehr schnell geantwortet hat.

- Issues an denen ich (und mein Teampartner) gearbeitet haben:
    - #7: Vertikaler Prototyp: Android
    - #13: Android: Schmerzen in Skizze markieren (erster Schritt)
    - #19: Navigationskomponente
    - #37: Onboarding
    - #25: Implementation Anamnese

