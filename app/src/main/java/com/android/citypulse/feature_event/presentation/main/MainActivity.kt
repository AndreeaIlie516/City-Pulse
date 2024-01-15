package com.android.citypulse.feature_event.presentation.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.rememberNavController
import com.android.citypulse.R
import com.android.citypulse.feature_event.data.network.NetworkStatusObserver
import com.android.citypulse.feature_event.presentation.events.EventsViewModel
import com.android.citypulse.feature_event.presentation.util.bottomnavigation.BottomNavigation
import com.android.citypulse.feature_event.presentation.util.navigation.NavigationGraph
import com.android.citypulse.ui.theme.CityPulseTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: EventsViewModel by viewModels()
    private val snackbarHostState = SnackbarHostState()
    private var isConnected by mutableStateOf(true)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val networkStatusObserver = NetworkStatusObserver(this)
        networkStatusObserver.startNetworkObserver { connectionStatus ->
            isConnected = connectionStatus
            viewModel.viewModelScope.launch {
                snackbarHostState.showSnackbar(
                    if (connectionStatus) "Connected to network" else "No connection"
                )
            }
            if (connectionStatus) {
                viewModel.viewModelScope.launch {
                    viewModel.syncPendingChanges()
                }
            }
        }


        setContent {
            CityPulseTheme {
                MainScreenView(isConnected, snackbarHostState)
            }
        }
    }
}

@Composable
fun SetTitle(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(start = 25.dp, top = 10.dp)
    ) {
        Column(
            modifier = modifier
        )
        {
            Text(
                text = stringResource(R.string.app_name),
                textAlign = TextAlign.Left,
                color = colorResource(R.color.purple),
                style = MaterialTheme.typography.displaySmall,
                fontFamily = FontFamily(Font(R.font.sf_pro_display_bold))
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreenView(isConnected: Boolean, snackbarHostState: SnackbarHostState) {
    val navController = rememberNavController()

    LaunchedEffect(isConnected) {
        snackbarHostState.currentSnackbarData?.dismiss()
    }

    Scaffold(
        bottomBar = { BottomNavigation(navController = navController) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        SetTitle()
        NavigationGraph(navController = navController)
    }
}

