package de.h_da.fbi.findus.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import de.h_da.fbi.common.di.DEFAULT_URL
import de.h_da.fbi.common.di.commonModule
import de.h_da.fbi.common.entity.ApiPatient
import de.h_da.fbi.common.repository.FindusRepository
import de.h_da.fbi.findus.R
import de.h_da.fbi.findus.di.AppModule
import de.h_da.fbi.findus.ui.anamnese.AnamneseScreen
import de.h_da.fbi.findus.ui.bodymapping.BodyMapping_View
import de.h_da.fbi.findus.ui.bodymapping.BodymappingScreen
import de.h_da.fbi.findus.ui.dashboard.DashBoardScreen
import de.h_da.fbi.findus.view.OnboardingUi
import io.ktor.client.call.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin
import java.util.*
import kotlin.collections.ArrayList
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import de.h_da.fbi.common.entity.ApiAnamnese
import de.h_da.fbi.common.entity.ApiMuscle
import de.h_da.fbi.common.entity.ApiOrgan

var mainActivity: MainActivity? = null
const val IMAGE_ID = "0"
var selectedPatient: ApiPatient? by mutableStateOf(null)
const val ROUTE_ANAMNESE = "Anamnese"
const val ROUTE_BODYMAPPING = "Bodymapping"
const val ROUTE_DASHBOARD = "Dashboard"

var bodyMapCaptureView: MutableState<BodyMapping_View>? = null
var patientsList: MutableList<ApiPatient> = ArrayList()
var myBiteArray: ByteArray? = null
var patientsPhotos: MutableMap<String, Bitmap?> = hashMapOf()
lateinit var logger: AndroidLogger

lateinit var currentPatient: ApiPatient
var currentAnamnese = ApiAnamnese("", "", mutableListOf<ApiOrgan>(), mutableListOf<ApiMuscle>())
var repo = FindusRepository()
var FONT_SIZE_TEXT = 25.sp
var patientList: List<ApiPatient> = ArrayList()

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainActivity = this

        val koin = startKoin {
            logger = AndroidLogger()
            androidContext(this@MainActivity)
            modules(AppModule)
            modules(commonModule(enableNetworkLogs = true, DEFAULT_URL))
        }.koin

        setContent {
            LaunchedEffect(true) {
                patientsList = FindusRepository().fetchPatients() as MutableList<ApiPatient>
                patientsList.forEach {
                    myBiteArray = FindusRepository().fetchImage(it.id,IMAGE_ID)
                    patientsPhotos[it.id] = BitmapFactory.decodeByteArray(myBiteArray, 0 , myBiteArray!!.size)
                }
            }
            if(selectedPatient != null){
                currentPatient = selectedPatient!!
                currentAnamnese = currentPatient.anamnese[0]
            }

            MainLayout()
        }
    }
}

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Anamnese : Screen("Anamnese", "Anamnese", Icons.Default.List)
    object Bodymapping : Screen("Bodymapping", "Bodymapping", Icons.Default.Edit)
    object Dashboard : Screen("Dashboard", "Dashboard", Icons.Default.Home)
}

class RailNavigationItem(
    val route: String,
    val icon: ImageVector,
    val contentDescription: String
)

val railNavigationItems = listOf(
    RailNavigationItem(
        Screen.Dashboard.route,
        Screen.Dashboard.icon,
        Screen.Dashboard.title
    ),
    RailNavigationItem(
        Screen.Anamnese.route,
        Screen.Anamnese.icon,
        Screen.Anamnese.title
    ),
    RailNavigationItem(
        Screen.Bodymapping.route,
        Screen.Bodymapping.icon,
        Screen.Bodymapping.title
    )
)

@Composable
fun NavRail(navController: NavController) {
    NavigationRail {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        Column(modifier = Modifier.width(dimensionResource(id = R.dimen.width_mainactivity_navrail))) {
            val textState = remember { mutableStateOf(TextFieldValue("")) }
            Column {
                SearchView(textState)
                if (textState.value != TextFieldValue("")) {
                    PatientList(state = textState)
                }
            }
            railNavigationItems.forEach { railNavigationItem ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    NavigationRailItem(
                        icon = {
                            Icon(
                                railNavigationItem.icon,
                                contentDescription = railNavigationItem.contentDescription,
                                modifier = Modifier.size(dimensionResource(id = R.dimen.size_mainactivity_navrail_icon))
                            )
                        },
                        //The selected state of each BottomNavigationItem can then be determined by comparing the item's route with the route of
                        // the current destination  and its parent destinations
                        // (to handle cases when you are using nested navigation e.g. for login later) via the hierarchy helper method
                        //label = { Text(text = railNavigationItem.contentDescription, textAlign = TextAlign.Right)},
                        selected = currentDestination?.hierarchy?.any { it.route == railNavigationItem.route } == true,
                        onClick = {
                            navController.navigate(railNavigationItem.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                    Text(text = railNavigationItem.contentDescription,
                        fontSize = dimensionResource(id = R.dimen.all_font_size_txt).value.sp,
                        modifier = Modifier.clickable {
                            navController.navigate(railNavigationItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        })
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.all_padding)),
                verticalAlignment = Alignment.Bottom
            ) {
                Button(
                    onClick = {},
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.mainactivity_btn_logout),
                        contentDescription = stringResource(id = R.string.mainactivitiy_btn_logout),
                        Modifier.size(dimensionResource(id = R.dimen.size_mainactivity_btn_logout))
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(text = stringResource(id = R.string.mainactivitiy_btn_logout),
                        fontSize = dimensionResource(id = R.dimen.all_font_size_txt).value.sp
                    )
                }
            }
        }
    }
}

@OptIn(
    ExperimentalPermissionsApi::class,
    ExperimentalAnimationApi::class,
    ExperimentalPagerApi::class
)

@Composable
fun MainLayout() {
    val navController = rememberNavController()
    Theme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            stringResource(id = R.string.app_name),
                            fontSize = dimensionResource(
                                id = R.dimen.font_size_txt_app_name).value.sp)},

                    navigationIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.app_icon_round),
                            contentDescription = stringResource(id = R.string.mainactivitiy_image_topbar)
                        )
                    }
                )
            }) {
            Row() {
                NavRail(navController)
                NavHost(
                    navController = navController,
                    startDestination = railNavigationItems.first().route
                ) {
                    composable(Screen.Anamnese.route) { Anamnese(navController) }
                    composable(Screen.Dashboard.route) { Dashboard(navController) }
                    composable(Screen.Bodymapping.route) { Bodymapping(navController) }
                }
            }
        }
        Surface(color = MaterialTheme.colors.background) {
            mainActivity?.let { OnboardingUi(it) }
        }
    }
}

@Composable
fun Anamnese(navController: NavController) {
    Button(onClick = { if(currentAnamnese != null) {
        navController.navigate(ROUTE_ANAMNESE)
    } }) {}
    AnamneseScreen(selectedPatient)
}

@Composable
fun Bodymapping(navController: NavController) {
    Button(onClick = { navController.navigate(ROUTE_BODYMAPPING) }) {
    }
     BodymappingScreen()
}


@Composable
fun Dashboard(navController: NavController) {
    Button(onClick = { navController.navigate(ROUTE_DASHBOARD) }) {}
    DashBoardScreen(selectedPatient)
}

@Composable
fun PatientListItem(patient: ApiPatient, onItemClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_mainactivity_patientlistitem))
            .clickable(onClick = { onItemClick(patient.name) })
            .fillMaxWidth()
            .border(
                dimensionResource(id = R.dimen.all_border),
                MaterialTheme.colors.secondary
            )
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.all_clip_roundedcornershape)))
            .background(MaterialTheme.colors.primary)
            .padding(dimensionResource(id = R.dimen.padding_background_mainactivity_patientlistitem))
    ) {
        Surface(
            modifier = Modifier.size(dimensionResource(id = R.dimen.size_surface_mainactivity_patientlistitem)),
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_mainactivity_patientlistitem)),
            color = MaterialTheme.colors.primary
        ) {
            patientsPhotos[patient.id]?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = " ",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Spacer(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_start_mainactivity_spacer_patientlistitem)))
        Column {
            Text(text = patient.name, fontSize = dimensionResource(id = R.dimen.font_size_txt_name).value.sp
                , fontWeight = FontWeight.Bold)
            Text(text = patient.ownerName, fontSize = dimensionResource(id = R.dimen.font_size_txt_name).value.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun PatientList(state: MutableState<TextFieldValue>) {
    val context = LocalContext.current
    val toastMsg = stringResource(id = R.string.mainactivitiy_txt_toast_patientnofound)
    var filteredPatients: MutableList<ApiPatient>
    LazyColumn(modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.all_clip_roundedcornershape)))
        .border(dimensionResource(id = R.dimen.all_border), MaterialTheme.colors.secondary)
        .background(MaterialTheme.colors.primary)) {
        val searchedText = state.value.text
        filteredPatients = if (searchedText.isEmpty()) {
            patientsList
        } else {
            val resultList: MutableList<ApiPatient> = ArrayList()
            for (patient in patientsList) {
                if (patient.name.lowercase(Locale.getDefault()).contains(searchedText.lowercase(Locale.getDefault()))
                    || patient.ownerName.lowercase(Locale.getDefault()).contains(searchedText.lowercase(Locale.getDefault()))) {
                    resultList.add(patient)
                }
            }
            resultList
        }
        if(filteredPatients.isEmpty()){
            Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT).show()
        }
        items(filteredPatients) { filteredPatient ->
            PatientListItem(
                patient = filteredPatient,
                onItemClick = {
                    selectedPatient = filteredPatient
                    state.value = TextFieldValue("")
                }
            )
        }
    }
}

@Composable
fun SearchView(state: MutableState<TextFieldValue>) {
    TextField(
        value = state.value,
        onValueChange = { value ->
            state.value = value
        },
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_mainactivity_searchview))
            .fillMaxWidth()
            .wrapContentHeight(Alignment.CenterVertically),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = stringResource(id = R.string.dashboard_description_search),
                modifier = Modifier.size(dimensionResource(id = R.dimen.size_mainactivity_icon_searchview))
            )
        },
        trailingIcon = {
            if (state.value != TextFieldValue("")) {
                IconButton(
                    onClick = {
                        state.value = TextFieldValue("")
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = stringResource(id = R.string.dashboard_description_close),
                        modifier = Modifier
                            .padding(dimensionResource(id = R.dimen.padding_mainactivity_icon_searchview))
                            .size(dimensionResource(id = R.dimen.size_mainactivity_icon_searchview))
                    )
                }
            }
        },
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = dimensionResource(id = R.dimen.all_font_size_txt).value.sp
        ),
        singleLine = true,
        placeholder = { Text( text = stringResource(id = R.string.mainactivitiy_placeholder_searchtxt),
            fontSize = dimensionResource(id = R.dimen.font_size_placeholder_search).value.sp) }
    )
}

@Preview
@Composable
fun DashBoardScreenPreview() {
    MainLayout()
}
