package com.waseefakhtar.doseapp.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.waseefakhtar.doseapp.feature.addmedication.navigation.addMedicationGraph
import com.waseefakhtar.doseapp.feature.calendar.navigation.calendarGraph
import com.waseefakhtar.doseapp.feature.home.navigation.ASK_NOTIFICATION_PERMISSION
import com.waseefakhtar.doseapp.feature.home.navigation.HomeDestination
import com.waseefakhtar.doseapp.feature.home.navigation.homeGraph
import com.waseefakhtar.doseapp.feature.medicationconfirm.navigation.MEDICATION
import com.waseefakhtar.doseapp.feature.medicationconfirm.navigation.MedicationConfirmDestination
import com.waseefakhtar.doseapp.feature.medicationconfirm.navigation.medicationConfirmGraph

@Composable
fun DoseNavHost(
    bottomBarVisibility: MutableState<Boolean>,
    fabVisibility: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = HomeDestination.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeGraph(navController, bottomBarVisibility, fabVisibility)
        calendarGraph(bottomBarVisibility, fabVisibility)
        addMedicationGraph(
            bottomBarVisibility = bottomBarVisibility,
            fabVisibility = fabVisibility,
            onBackClicked = { navController.navigateUp() },
            navigateToMedicationConfirm = {
                // TODO: Replace with medication id
                val bundle = Bundle()
                bundle.putParcelableArrayList(MEDICATION, ArrayList(it))
                navController.currentBackStackEntry?.savedStateHandle.apply {
                    this?.set(MEDICATION, bundle)
                }
                navController.navigate(MedicationConfirmDestination.route)
            }
        )
        medicationConfirmGraph(
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            fabVisibility = fabVisibility,
            onBackClicked = { navController.navigateUp() },
            navigateToHome = {
                // TODO: Navigate to Home with no backstack.
                navController.currentBackStackEntry?.savedStateHandle.apply {
                    this?.set(ASK_NOTIFICATION_PERMISSION, true)
                }
                navController.navigate(HomeDestination.route)
            }
        )
    }
}
