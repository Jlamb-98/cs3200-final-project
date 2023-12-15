package com.example.trainingplanner.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.PurchasesUpdatedListener
import com.example.trainingplanner.ui.navigation.Routes
import com.example.trainingplanner.ui.viewmodels.PurchaseScreenViewModel
import kotlinx.coroutines.launch

@Composable
fun PurchaseScreen(navHostController: NavHostController) {
    val viewModel: PurchaseScreenViewModel = viewModel()
    val scope = rememberCoroutineScope()
    val state = viewModel.uiState
    val context = LocalContext.current

    val billingClient = remember {
        BillingClient.newBuilder(context)
            .setListener(PurchasesUpdatedListener { billingResult, purchases ->
                println("PurchasesUpdatedListener")
                // purchase results would be assessed here
            })
            .enablePendingPurchases()
            .build()
    }

    DisposableEffect(context) {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(p0: BillingResult) {
                println("Billing setup finished, connected to Google Play")
                TODO("Not yet implemented")
            }

            override fun onBillingServiceDisconnected() {
                println("onBillingServiceDisconnected")
                TODO("Not yet implemented")
            }
        })

        onDispose {
            println("billingClient disposed")
            billingClient.endConnection()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Make a payment to start creating Training Plans!")
        Button(onClick = {
            println("Pay button clicked")
            viewModel.initiatePurchase(context, billingClient)
            navHostController.navigate(Routes.trainingPlanEditor.route)
        }) {
            Text(text = "Pay Now")
        }
    }
}