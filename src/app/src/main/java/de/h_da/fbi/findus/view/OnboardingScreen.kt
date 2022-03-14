package de.h_da.fbi.findus.view

import android.Manifest
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.airbnb.lottie.compose.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import de.h_da.fbi.common.repository.FindusRepository
import de.h_da.fbi.findus.model.Page
import de.h_da.fbi.findus.model.onboardPages
import de.h_da.fbi.findus.ui.MainActivity
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


const val PAGE_BOX_HEIGHT = 400
const val PAGE_SPACER_HEIGHT = 8
const val PAGE_TEXT_HEIGHT = 10
const val PAGE_COUNT = 4
const val START_BUTTON_TEXT = "Findus starten"
const val LOTTIE_PADDING = 10
const val FONT_SIZE_TITLE = 32
const val FONT_SIZE_DESCRIPTION = 16
const val BUTTON_PADDING = 8
const val PAGER_PADDING = 16
const val PERMISSION_REQUEST_PAGE = 3
const val ENDING_PAGE = 3
const val SHARED_PREFS = "sharedPrefs"
const val SHARED_PREF_ONBOARDING_DONE = "onboardingDone"
const val ANIMATION_SPEED = 1.0f

/**
 * PageUI takes and assembles a single Page Object and throws the permission requests
 * @param page the Page object that will be displayed
 */
@ExperimentalPermissionsApi
@Composable
fun PageUi(page: Page) {
    val permissionsStateCamera = rememberPermissionState(permission = Manifest.permission.CAMERA)
    val permissionsStateMedia =
        rememberPermissionState(permission = Manifest.permission.READ_EXTERNAL_STORAGE)
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) {
                    if (page.index == PERMISSION_REQUEST_PAGE) {
                        permissionsStateCamera.launchPermissionRequest()
                        permissionsStateMedia.launchPermissionRequest()
                    }
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    )


    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.height(PAGE_BOX_HEIGHT.dp)) {
            Loader(page.image)
            Spacer(modifier = Modifier.height(PAGE_SPACER_HEIGHT.dp))
        }
        Text(
            text = page.title,
            fontSize = FONT_SIZE_TITLE.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(0.dp, PAGE_TEXT_HEIGHT.dp)
        )
        Text(
            text = page.description,
            fontSize = FONT_SIZE_DESCRIPTION.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = PAGE_TEXT_HEIGHT.dp, end = PAGE_TEXT_HEIGHT.dp)
        )
    }
}

/**
 * OnboardingUi displays the Onboarding Pages together and saves/checks whether Onboarding was done before already
 * @param mainActivity takes MainAcvtivity as a parameter in order to provide a context object for getsharedPreferences
 */
@ExperimentalPermissionsApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun OnboardingUi(mainActivity: MainActivity) {
    val pagerState = rememberPagerState(pageCount = PAGE_COUNT)
    var visible by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    try {
        visible = !mainActivity.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
            .getBoolean(SHARED_PREF_ONBOARDING_DONE, false)
    } catch (exception: Exception) {
        exception.printStackTrace()
    }

    if (visible) {
        LaunchedEffect(true) {
            // has to run blocking, otherwise the first fetch of the patients will return an empty list
            runBlocking {
                FindusRepository().createDummyData()
            }
        }
        Column {
            HorizontalPager(
                state = pagerState, modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) { page ->
                PageUi(page = onboardPages[page])
            }

            HorizontalPagerIndicator(
                pagerState = pagerState, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(PAGER_PADDING.dp),
                activeColor = Color.Blue
            )

            AnimatedVisibility(visible = pagerState.currentPage == ENDING_PAGE) {
                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = BUTTON_PADDING.dp),
                    onClick = {
                        visible = false
                        val sharedPrefs =
                            mainActivity.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
                        sharedPrefs.edit().apply {
                            putBoolean(SHARED_PREF_ONBOARDING_DONE, true)
                        }.apply()
                    },
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = Color.Blue
                    )
                ) {
                    Text(text = START_BUTTON_TEXT)
                }
            }
        }
    }
}

/**
 * Built in function of the Lottie Library, builds the animation with given json file
 * parameters can be edited here
 * @param json_id takes id of the json Lottie file
 */
@Composable
private fun Loader(json_id: Int) {
    val compositionResult: LottieCompositionResult =
        rememberLottieComposition(spec = LottieCompositionSpec.RawRes(json_id))

    val progress by animateLottieCompositionAsState(
        compositionResult.value,
        isPlaying = true,
        iterations = LottieConstants.IterateForever,
        speed = ANIMATION_SPEED
    )

    val color by derivedStateOf { Color.Red }

    val dynamicProperties = rememberLottieDynamicProperties(
    )

    LottieAnimation(
        compositionResult.value,
        progress,
        dynamicProperties = dynamicProperties,
        modifier = Modifier.padding(all = LOTTIE_PADDING.dp)
    )
}


