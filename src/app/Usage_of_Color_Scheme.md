# Anwendung des Farbschemas
## Implementation
In
```
findus/src/app/src/main/java/de/h_da/fbi/findus/ui
``` 
Befinden sich die Dateien Theme.kt und Color.kt , wo sich die Farben des Farbschemas befinden. In Color.kt kann man diese Modifizieren und in
Theme.kt werden diese als MaterialTheme angewendet.
### Color.kt:

```kotlin
package de.h_da.fbi.findus.ui

import androidx.compose.ui.graphics.Color

val PrimaryTuerkies = Color(0xFF12bbad)
val PrimaryTuerkiesLight = Color(0xFF61eedf)
val PrimaryTuerkiesDark = Color(0xFF008a7e)

val SecondaryGray = Color(0xFFbee1dd)
val SecondaryGrayLight = Color(0xFFf1ffff)
val SecondaryGrayDark = Color(0xFF8dafab)

val PrimaryTextColor = Color(0xFF000000)
val SecondaryTextColor = Color(0xFF333333)
```

### Theme.kt:

```kotlin
package de.h_da.fbi.findus.ui


import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val MainThemeColors = lightColors(

    primary = PrimaryTuerkies,
    primaryVariant = PrimaryTuerkiesLight,
    secondary = SecondaryGray,
    secondaryVariant = SecondaryGrayDark,

    onPrimary = PrimaryTextColor,
    onSecondary = SecondaryTextColor
)

@Composable
fun Theme(
    content: @Composable () -> Unit
) {
    val colors = MainThemeColors
    MaterialTheme(
        colors = colors,
        content = content
    )
}
```

## Benutzung
- Man kann das Theme auf seine Elemente anwenden, indem man seine Composable Funktionen in die Theme() Methode einfügt.
### MainActivity.kt:

```kotlin
 Theme {
        Surface(color = Color.White) {
            LazyColumn {
                item {
                    Text(
                        text = PATIENT_STRING,
                        style = typography.h6,
                        modifier = Modifier.padding(PADDING.dp)
                    )

                    InputName()
                    InputWeight()
                    InputContext()
                }

                items(mutableListImages.size) { index ->
                    ImageView(index, mutableListImages)
                }

                item {
                    AddImageButton(mutableListImages)
                    SaveButton()

                    InputPatient()
                    LoadPatientButton(mutableListImages)
                    DeletePatientButton(mutableListImages)

                    NewPatientButton(mutableListImages)
                }
            }
        }
    }
```

## Farbänderungen einzelner Elemente
- Elemente wie Buttons können mit folgenden Modifier die zu wünschenden Farben des Themes annehmen:

```kotlin

    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(PADDING.dp),
        colors = ButtonDefaults.textButtonColors( //<----
            backgroundColor = MaterialTheme.colors.primaryVariant, //<----
            contentColor = MaterialTheme.colors.onPrimary //<----
        ),
        onClick = {
            //OnClick Funktion
        })

        //Oder auch

        Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(PADDING.dp),
        colors = ButtonDefaults.textButtonColors( //<----
            backgroundColor = MaterialTheme.colors.secondary, //<----
            contentColor = MaterialTheme.colors.onSecondary //<----
        ),
        onClick = {
            //OnClick Funktion
        })
```

