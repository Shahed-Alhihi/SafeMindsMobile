package com.example.safemindsmobile.ui.screens.onBoarding

import android.net.Uri
import androidx.annotation.OptIn
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.navigation.NavHostController
import com.example.safemindsmobile.R
import com.example.safemindsmobile.ui.theme.darkBackground
import com.example.safemindsmobile.ui.theme.primaryColor
import kotlinx.coroutines.delay
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.safemindsmobile.navigation.AppScreens
import com.example.safemindsmobile.ui.components.SafeMindsPrimaryButtons
import com.example.safemindsmobile.ui.theme.onPrimaryColor


@Composable
fun EntryScreen (
    navController: NavHostController, //we use navController to navigate between screens
    isFirstLaunch: Boolean=true //it is used to check if the user use the app for the first time, if yes after entryScreen it will show onboarding screens if not it will show login page directly
) {
    val context =
        LocalContext.current  //gives me the current context of the app to use it in ExoPlayer
    val lifeCycleOwner =
        androidx.lifecycle.compose.LocalLifecycleOwner.current //manages the whole lifecycle of the current page


    //the elements I have added to the screen(app name, slogan,...)
    //remember is used to enable compose to save the state(value) even when the app make recomposition
    // it means that the compose start use @Composable many times when the state changes
    //for mutableStateOf it creates a state that is changeable according to the changes/updates, and it's tracked by compose
    var shortLine by remember {
        mutableStateOf(false)
    }

    var appDesc by remember {
        mutableStateOf(false)
    }

    var appName by remember {
        mutableStateOf(false)
    }

    var appSlogan by remember {
        mutableStateOf(false)
    }

    var showButton by remember {
        mutableStateOf(false)
    }


    val videoPlayer =
        remember { //to create the player of the video, it takes the URI(video resource)
            ExoPlayer.Builder(context).build().apply {
                setMediaItem(
                    MediaItem.fromUri(
                        Uri.parse("android.resource://${context.packageName}/${R.raw.animated_logo}")
                    )
                )

                prepare()
                playWhenReady = true
                repeatMode = Player.REPEAT_MODE_ONE
                volume = 0f
            }
        }

    //this is used to link the player with the lifecycle of teh screen, if the user in the screen -> start video, moves from the screen-> stop the video temporarily, go out from the screen-> remove/clean everything
    DisposableEffect(lifeCycleOwner) {
        val lifecycleObserver = LifecycleEventObserver { _, lifeCycleEvent ->
            when (lifeCycleEvent) {
                Lifecycle.Event.ON_RESUME -> videoPlayer.play()
                Lifecycle.Event.ON_PAUSE -> videoPlayer.pause()
                else -> {}
            }
        }
        lifeCycleOwner.lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifeCycleOwner.lifecycle.removeObserver(lifecycleObserver)
            videoPlayer.release()
        }
    }


    //effects applied on the elements I added
    LaunchedEffect(Unit) {
        delay(200); shortLine = true
        delay(200); appDesc = true
        delay(150); appName = true
        delay(200); appSlogan = true
        delay(200); showButton = true
    }

    //the main container of the screen
    Box(
        modifier = Modifier.fillMaxSize().background(darkBackground)
    ) {
        VideoBackground(videoPlayer)
        Overlay()
        GlowedCircle()
        ScreenContent(
            shortLine = shortLine,
            appDesc = appDesc,
            appName = appName,
            appSlogan = appSlogan,
            showButton = showButton,
            onButtonClick = {
                val route = if (isFirstLaunch) {
                    AppScreens.AwarenessScreen.flow
                } else {
                    AppScreens.LoginScreen.flow
                }

                navController.navigate(route) {
                    popUpTo(AppScreens.EntryScreen.flow) {
                        inclusive = true
                    }
                }
            }
        )
    }
}

@OptIn(UnstableApi::class) // we add to approve that we know (package is not stable, and it may be updated later)
@Composable
private fun VideoBackground(
    videoPlayer: ExoPlayer
)
{
    AndroidView( //used to make view for the video
        factory = { vidContext -> //to create a view to update the settings of the vid
            PlayerView(vidContext).apply {
                player = videoPlayer
                useController = false
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                setShutterBackgroundColor(darkBackground.toArgb())

            }
        },

        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun Overlay(){
    Box(
        modifier = Modifier.fillMaxSize().background(
            Brush.verticalGradient( //color change from top to bottom
                colorStops = arrayOf(
                    0.00f to Color.Transparent,
                    0.30f to Color.Transparent,
                    0.52f to darkBackground.copy(alpha = 0.55f),
                    0.68f to darkBackground.copy(alpha = 0.92f),
                    0.78f to darkBackground.copy(alpha = 0.98f),
                    1.00f to darkBackground

                )
            )
        )
    )
}


@Composable
private fun BoxScope.GlowedCircle(){//bottom glow
    Box(
        modifier = Modifier.fillMaxWidth().height(260.dp).align(Alignment.BottomCenter)
            .drawBehind{
                drawCircle(
                    brush = Brush.radialGradient(
                        colors =listOf(
                            primaryColor.copy(alpha = 0.07f),
                            Color.Transparent
                        ),
                        center=Offset(size.width/ 2f, size.height*0.3f),
                        radius =size.width*0.9f
                    )
                )
            }
    )
}






 @Composable
 private fun ScreenContent(
      shortLine: Boolean,

      appDesc : Boolean,

      appName: Boolean,

      appSlogan: Boolean,

      showButton : Boolean,
      onButtonClick:()->Unit
 ){
     Column(
         modifier = Modifier.fillMaxWidth()
             .padding(horizontal = 32.dp, vertical = 36.dp), // gives inner spaces, so the elements don't stick in the edges

         horizontalAlignment = Alignment.CenterHorizontally,
         verticalArrangement = Arrangement.Bottom
     ) {
         Spacer(modifier = Modifier.weight(1f))
         ShortLine(isVisible=shortLine)

         Spacer(modifier = Modifier.height(16.dp))

         AppDescription(isVisible=appDesc)
         Spacer(modifier = Modifier.height(8.dp))

         AppName(isVisible=appName)
         Spacer(modifier = Modifier.height(10.dp))

         AppSlogan(isVisible=appSlogan)
         Spacer(modifier = Modifier.height(32.dp))

         Button(
             isVisible=showButton,
             onClick=onButtonClick
         )



     }

     }






@Composable
private fun ShortLine(
    isVisible: Boolean
){
    AnimatedVisibility(
        visible = isVisible,
        enter = expandHorizontally (
            animationSpec = tween(600, easing = FastOutSlowInEasing),
            expandFrom = Alignment.CenterHorizontally
        ) + fadeIn(tween(600))
    ) {
        Box(
            modifier = Modifier
                .width(32.dp)
                .height(1.dp)
                .background(Brush.horizontalGradient(
                    listOf(Color.Transparent,primaryColor, Color.Transparent)
                ))
        )
    }
}

@Composable
private fun AppDescription(
    isVisible:Boolean
){
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn (tween(600)) + slideInVertically (
            initialOffsetY = {it/3},
            animationSpec = tween(600, easing = FastOutSlowInEasing)
        )
    )
    {
      Text(
            text = "Cognitive Awareness Platform",
            style = MaterialTheme.typography.labelLarge.copy(
                color= primaryColor.copy(alpha = 0.75f),
                letterSpacing = 4.sp,
                fontWeight = FontWeight.Normal,
                fontSize = 11.sp
            )
        )
    }
}


@Composable
private fun AppName(
    isVisible: Boolean
)
{
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn (tween(700)) + slideInVertically (
            initialOffsetY = {it/3},
            animationSpec = tween(700, easing = FastOutSlowInEasing)
        )
    )
    {
        Text(
            text = "SAFEMINDS",
            style = MaterialTheme.typography.headlineLarge.copy(
                color= onPrimaryColor,
                letterSpacing = 14.sp,
                fontWeight = FontWeight.ExtraLight,
                fontSize = 38.sp
            )
        )
    }
}

@Composable
private fun AppSlogan(
    isVisible: Boolean
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(tween(700)) + slideInVertically(
            initialOffsetY = { it / 3 },
            animationSpec = tween(700, easing = FastOutSlowInEasing)
        )
    )
    {
        Text(
            text = "Noticing the Unseen, Protecting the Mind",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.75f),
                letterSpacing = 1.sp,
                fontWeight = FontWeight.Light,
                fontSize = 15.sp
            ), textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun Button(
    isVisible: Boolean,
    onClick:()-> Unit
){
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn (tween(800)) + slideInVertically (
            initialOffsetY = {it/2},
            animationSpec = tween(800, easing = FastOutSlowInEasing)
        )
    )
    {
        SafeMindsPrimaryButtons(
            label = "Begin the Journey",
            onClick = onClick
        )
    }

}