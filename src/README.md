# Initiales Projekt

- Um das initiale Projekt auf den zwei Plattformen Windows (compose-desktop) und Android (app) erfolgreich zu
  starten müssen folgende Schritte erfolgen.
1. Klonen von Findus Repository

![image](/uploads/042f022642e73deaf70b074abef613b0/image.png)

2. Importieren/Öffnen von src mittels Android Studio 2020.3.1 [Download-Link](https://developer.android.com/studio/):
   File --> Open --> Findus Src auswählen oder direkt mittels VCS (wichtig lediglich Findus Src auswählen nicht gesamtes Repository)

![image](/uploads/af658a12347f6d8f74d67d4e7b6c7c1f/image.png)

![image](/uploads/dc992dda6ea20e91e75c3b4e6302660a/image.png)

3. Einstellen/Hinzufügen von AndroidSdk (wenn nicht bereits erledigt); (z.B. C:\Microsoft\AndroidSDK\25)
   File --> Project Structure -->  SDK Location

![image](/uploads/f17d90557170464c3003720f416962c9/image.png)

![image](/uploads/c0ba62f43decf0270d9d3fe4021e7764/image.png)

4. Einstellen der richtigen JDK-Version

File --> Settings

![image](/uploads/87e2211123382dbb939be3efb85d7fbd/image.png)

--> Build, Execution, Deployment --> Build Tools --> Gradle --> Gradle JDK --> Download JDK

![image](/uploads/668f7b1df8459b7cfd2a4e5a7010c658/image.png)

--> Version: 11, Vendor: Amazon Coretto 11.0.13 --> Download

![image](/uploads/4238cd07b4a63a4760d4aabd5de214a9/image.png)

Apply, OK Knopf betätigen

![image](/uploads/5ac96641f28c9ef7f05d70483d995f61/image.png)

5. Falls zuvor JDK17 oder eine andere Version eingestellt war, kann dies zu Problemen beim Gradle Build führen.
   Lösung: **File --> Invalidate Caches / Restart **

   ![image](/uploads/fadb1b66041ca6de808f37635909ec27/image.png)
   Android Studio startet nun erneut und Gradle synchronisiert

   (Falls dies nicht hilft, Gradle Cache manuell löschen (Android Studio und alle JDK Prozesse müssen hierfür beendet sein)
   Gradle-Cache befindet sich normal in C:\Users\(Benutzer)\.gradle, hier einfach caches löschen und danach AndroidStudio erneut starten)

6. Wenn der Gradle Build erfolgreich war, befinden sich auf der Rechten Seite Gradle-Tasks (Mit einem Klick auf das rechts stehende Gradle kann der Reiter geöffnet werden).
   Um die Gradle Leiste zu öffnen muss man auf der rechten Seite von Android Studio auf Gradle klicken, es öffnet sich dann der "Gradle-Reiter".

![image](/uploads/fc8dedff6d6096bf2f7655048a23340b/image.png)



**Um die Desktop-Version zu starten im "Gradle-Reiter":**
compose-desktop --> Tasks --> application --> run (ausführen mit Doppelklick)

![image](/uploads/7b16abc8bb83a317053ea73b219888e1/image.png)

Die Desktop-Version wird gebuildet und startet in einem neuen Fenster.

![image](/uploads/3dfee80c42fe01d52fa1c73199cf51fc/image.png)


**Um die App zu starten:**

**Erstellen eines Android Virtual Device:**
Tools --> AVD Manager

![image](/uploads/423a9e25e8d990ea02375a940fab853a/image.png)

AVD Manager --> + Create Virtual Device

![image](/uploads/fb3773de127fda8fb95531b10c089e53/image.png)

gewünschtes Handy auswählen --> Next

![image](/uploads/e974f19882648f803b564bd2d16fcf52/image.png)

Download - Release Name: R API Level: 30 --> Next

![image](/uploads/9439d4bd5dec445fbe78d47c53112043/image.png)

Finish

![image](/uploads/bb1608de8e6c1ffd46b96b7f2cb885f7/image.png)

Das Gerät wurde nun erstellt und kann ausgewählt werden.

![image](/uploads/e38d6826e0814ecd4f2f9c1403dfc8aa/image.png)



**App starten:**

Edit configurations(neben dem "Build-Hammer")

![image](/uploads/c11332b6a36cb6c44f46c2c357fd927c/image.png)

--> + --> Android App

![image](/uploads/38cb4f1fa40a5cae3af0fd7be9435b8b/image.png)

--> Module: Findus.app --> Apply & Ok

![image](/uploads/14a5e0ee91e8e4b3d455c24e2b9f4346/image.png)

![image](/uploads/b0d7f3a9a56f4cd6a1a43b7b004f0cf9/image.png)

--> app auswählen

![image](/uploads/1b40e52e571904823ea6faf055438d96/image.png)

--> run (grüner Pfeil oder Umschalt+F10)

![image](/uploads/63865b275f5e1af5ea397c1b11ea8e98/image.png)

Die App startet und der Hauptscreen wird angezeigt.

![image](/uploads/d6c9b4f93d90886641e6c918787b2f25/image.png)

Falls keine Tasks im Gradle Fenster angezeigt werden, kann der Link [hier](https://stackoverflow.com/questions/67405791/gradle-tasks-are-not-showing-in-the-gradle-tool-window-in-android-studio-4-2#:~:text=You%20can%20re-enable%20it,note%20of%20android%20studio%204.2%20) dabei helfen sie anzuzeigen.

# Docker Container

1. Die Container können mit dem Befehl `docker-compose up` im src Ordner gestartet werden.
2. Die Container können mit dem Befehl `docker-compose down` im src Ordner geschlossen werden.
3. Zugriff auf [Ktor-Server](http://localhost:8082/)
4. Zugriff auf [MongoDB](http://localhost:8081/)
5. Die Zugangsdaten zur Datenbank können in der docker-compose.yml gefunden werden.


# Internationalisierung

Das Projekt wurde in Deutsch und Englisch erstellt, weiterhin ist es möglich, weitere Sprachen anzulegen. Hierzu werden alle Strings in diese Sprache übersetzt.
Basierend auf den System/Spracheinstellungen des Endgerätes werden dann in der App die passenden Texte in der jeweiligen Sprache angezeigt.

**Zum Anlegen einer weiteren Sprache:**
- Unter `src/app/src/main/res/values/strings.xml` finden Sie die strings.xml Datei.
- Mit einem Rechtsklick auf die Datei erscheint ganz unten im Auswahlmenü der Translations Editor.
- In der oberen Leiste gibt es ein Symbol Add Locale (dargestellt als Globus). Mit einem Klick auf das Symbol erscheinen alle von Android Studio unterstützten Sprachen.
- Wählen Sie aus dieser Liste die gewünschte Sprache und ergänzen Sie die Strings in der neuen strings.xml Datei.

**Hinzufügen eines Strings mit der dazugehörigen Übersetzung**
Unter `src/app/src/main/res/` gibt es momentan zwei strings.xml Dateien für die deutsche und englische Sprache.
Um einen neuen String multilingual anlegen zu können, muss der String mit seinem eindeutigen Namen in allen strings.xml Dateien hinzugefügt- und die passende Übersetzung eingetragen werden.

Beispiel:
<string name="txt_greeting">Hello</string> in der englischen strings.xml Datei (`src/app/src/main/res/values/strings.xml`)
<string name="txt_greeting">Guten Tag</string> in der deutschen strings.xml Datei (`src/app/src/main/res/values-de/strings.xml`)

Bei der Benennung der neu angelegten Strings sollte auf folgende Namenskonvention geachtet werden:
**Naming Convention**:
Links:
- https://jeroenmols.com/blog/2016/03/07/resourcenaming/
- https://medium.com/mindorks/android-resource-naming-convention-42e4e8026614
- https://hungsonandroid.wordpress.com/2016/10/17/androidnaming-convention/
- https://github.com/androidx/androidx/blob/androidx-main/compose/docs/compose-api-guidelines.md

Grundprinzip: Alle Ressourcennamen folgen einer einfachen Konvention.
        **<WHAT>_<WHERE>/<HOW>_<DESCRIPTION>_<SIZE>**

Strings: <WHERE>/<HOW>_<DESCRIPTION>
 <WHERE>_<DESCRIPTION>: <WHERE>, um anzugeben, wo die String verwendet werden soll.
 <HOW>_<DESCRIPTION>: <HOW>, um anzugeben, warum die String verwendet wird, und eine Beschreibung mit zusätzlichen Informationen.
 all_<DESCRIPTION>: all, wenn die String in der gesamten Anwendung wiederverwendet wird.
  
Bsp.:
    - <string name="dashboard_label_home">Home</string> : where = dashboard & how = label & home is description of the string.
    - <string name="dashboard_hint_user_name">Enter Your Name</string> : where = dashboard & how = hint & user_name is the description of string.
    - <string name="dashboard_label_name_animal"> </string> : where = dashboard & how = label & dashboard_name_animal = für Name des Tieres im Screen Dashboard
    - <string name="bodymap_hint_pain"> </string> : where = bodymaping & how = hint & description = pain
    - <string name="bodymap_label_date"> </string> : where = bodymaping & how = label & description = date
    - ok --> all_ok -- Beschriftung für alle Ok-Buttons
    - cancel --> all_cancel -- Beschriftung für alle Cancel-Buttons

Drawables: <WHERE>_<WHAT>_<DESCRIPTION>
 <WHERE> wo soll verwendet werden & <WHAT>: ist Button,Dialog,Divider,Icon ... & <DESCRIPTION> enthält zusätzliche Informationen.
  +-------------------------------------------------------------------------+
  | Element            | Prefix         |          Example
  |--------------------+----------------------------------------------------+
  | Action bar         | ab_            | bodymap_ab_stacked.png            |
  | Image              | image_         | dashboard_image_logout.png        |
  | Button             | btn_           | anamnese_btn_pressed.png          |
  | Menu               | menu_          | bodymap_menu_bg.png               |
  | Notification       | notification_  | anamnese_notification_bg.png      |
  | Icon               | ic_            | dashboard_ic_star.png             |
  | Dialog             | dialog_        | dashboard_dialog_star.png         |
  | Divider            | divider_       | dashboard_divider_horizontal.png  |
  +--------------------+----------------------------------------------------+
  
Dimensions: <WHAT>_<WHERE>:
 <WHAT>: Es kann width(in dp), height (in dp), size (wenn width == height), margin(in dp), padding(in dp), elevation(in dp), text_size(in sp) sein.
 <WHERE>: wo soll verwendet werden.

Bsp.:
    - <dimen name="size_dashboard">10dp</dimen> // size (wenn width == height)
    - <dimen name="margin_activity">15dp</dimen>
    - <dimen name="padding_anamnese">5dp</dimen>
    - <dimen name="width_dashboard">30dp</dimen>
    - <dimen name="height_bodymap">40dp</dimen>
    - <dimen name="text_size_anamnese">25sp</dimen>

**Static text**
Um sicherzustellen, dass Ihre Übersetzer die String während der Übersetzung nicht ändern, verwenden Sie einen <xliff:g> -Platzhalter-Tag.
Bsp.: Eine Url-Webseite darf nicht übersetzt werden.
     //For default strings.xml file
       <string name="landing_url"> Visit us at <xliff:g id="application_homepage">https://xyz.com/</xliff:g> to know more.</string>
     //For the German strings.xml file, similarly add this string     
       <string name="landing_url">Erfahren Sie mehr unter <xliff:g id="application_homepage">https://xyz.com/</xliff:g></string>

**Dynamic Text**
Eine Begrüßungsnachricht ("Hallo John") anzeigen, wobei "John" ein beliebiger Name ist, den der Benutzer auf dem Bildschirm eingegeben hat. Hier ist "John" dynamisch und hängt von den Eingaben des Benutzers ab. Sie müssen den Namen als Parameter übergeben, während Sie die Zeichenkette aus der Ressource mit der gleichen stringResource()-Funktion abrufen.
In strings.xml Datei: <string name="greeting">Hello,
                            <xliff:g name="name" example="Joe">%s</xliff:g>
                      </string>
Verwenden Sie einen <xliff:g>-Platzhalter-Tag mit den Attributen id und example. Das Beispiel-Attribut hilft Ihrem Übersetzer zu verstehen, welche Art von Werten als Parameter übergeben werden. Schliessen Sie die %s in <xliff:g>-Tags ein.
Hier steht %s für einen String-Parameter, dezimale Ganzzahl (%d) und eine Gleitkommazahl (%f).
Um den String Resource zu verwenden: text = stringResource(R.string.greeting, displayName). Der displayName steht für die Eingabe des Benutzers.

**Plurals**
Um zwischen Plural- und Singular-Strings zu unterscheiden, können wir in der Datei strings.xml Plurale definieren und verschiedene Mengen auflisten.
In strings.xml Datei: <plurals name="heading_dashboard">
                            <item quantity="one">Patient</item>
                            <item quantity="other">Patienten</item>
                      </plurals>
Hinweis: Die Version 1.0.0-beta05 von Jetpack Compose bietet noch keine eingebaute Funktion zum Abrufen mehrerer Ressourcen.
=> Unsere eigene benutzerdefinierte Funktion erstellen, die LocalContext verwendet und die entsprechende Zeichenfolge zurückgibt.
//Custom function to get plural resources
@Composable
fun quantityStringResource(@PluralsRes id: Int, quantity: Int): String {
    return LocalContext.current.resources.getQuantityString(id, quantity)
}
//Verwenden Sie hier die benutzerdefinierte Funktion, wobei Sie die Größe der Liste übergeben.
Text(text = quantityStringResource(R.plurals.heading_dashboard, Patienten.size)

Tests:
Links: 
    - https://developer.android.com/jetpack/compose/testing
    - https://proandroiddev.com/how-to-test-jetpack-compose-f62ce23694b2

**Internationalisierung von Zahlen**
Zahlen werden weltweit in unterschiedlichen Formaten verwendet.
In den USA werden zum Beispiel Tausender durch ein Komma (,) getrennt, in Deutschland hingegen wird ein Punkt (.) als Trennzeichen verwendet.
Verwenden Sie die Klasse NumberFormat, um die Zahlen je nach dem Gebietsschema des Endgeräts zu formatieren.
Bsp:`NumberFormat.getInstance().format(Zahl)`

**Internationalisierung von Währungen**
Währungssymbole unterscheiden sich von Land zu Land (Bsp: USA: $, Europa €)
Verwenden Sie auch hier die Klasse NumberFormat.
Bsp.: `NumberFormat.getCurrencyInstance().format(Balance)`

**Internationalisierung von Datumsangaben**
Links:
- https://developer.android.com/reference/kotlin/android/icu/text/DateFormat
  Datumsangaben können sich teils stark unterscheiden. Zum Beispiel wird der 27. April 2021 im Amerikanischen als "4/27/21″ ausgegeben
  während im Deutschen der 27. April 2021 als "27.04.21" ausgegeben wird.
  Um ein Datum basierend auf den lokalen Formatierungseinstellungen des Endgeräts anzuzeigen, kann ein Datum im folgenden Format gesetzt werden
- `DateFormat.getDateInstance(DateFormat.SHORT).format(Date) ` für ein Datum
- `DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(Date)` Für Datum und Zeit
- Bsp.:
  In English:
    - SHORT is completely numeric, such as 12.13.52 or 3:30 PM
    - MEDIUM is longer, such as Jan 12, 1952 or 3:30:32 PM
    - LONG is longer, such as January 12, 1952 or 3:30:32 PM GMT
    - FULL is pretty completely specified, such as Tuesday, April 12, 1952 AD or 3:30:42 PM Greenwich Mean Time.
      In Deutsch:
    - SHORT is completely numeric, such as 15.10.18 or 20:10
    - MEDIUM is longer, such as 15.10.2018 20:10:00
    - LONG is longer, such as 15. Oktober 2018 20:10:00 GMT+00:00
    - FULL is pretty completely specified, such as Tuesday, April 12, 1952 AD or 3:30:42 PM Greenwich Mean Time.

- Hinweise:
    - Die Monate sind von 0 (Januar) bis 11 (Dezember) durchnummeriert.
    - Das frühste darstellbare Jahr ist das Jahr 1900