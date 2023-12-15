package com.example.trainingplanner.ui.viewmodels

import android.app.Application
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.lifecycle.AndroidViewModel
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClient.ProductType
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.QueryProductDetailsParams
import com.google.common.collect.ImmutableList

class PurchaseScreenState {

}

class PurchaseScreenViewModel(application: Application): AndroidViewModel(application) {
    val uiState = PurchaseScreenState()
    fun initiatePurchase(context: Context, billingClient: BillingClient) {
        // define list of products to query Google Play store
        val queryProductDetailsParams =
            QueryProductDetailsParams.newBuilder()
                .setProductList(
                    ImmutableList.of(
                        QueryProductDetailsParams.Product.newBuilder()
                            .setProductId("001")    // Real or Test Purchase ID as Google developer would go here
                            .setProductType(ProductType.INAPP)  // can be an in-app purchase or subscription (INAPP or SUBS)
                            .build()))
                .build()

        // get list of products from Google Play Store
        billingClient.queryProductDetailsAsync(queryProductDetailsParams) { billingResult, productDetailsList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {   // check for result from Google Play
                println("query successful")
                if (productDetailsList.isNotEmpty()) {  // check that we got the products
                    val productDetailsParamsList = listOf(
                        BillingFlowParams.ProductDetailsParams.newBuilder()
                            .setProductDetails(productDetailsList[0])
                            .build()
                    )
                    val billingFlowParams = BillingFlowParams.newBuilder()  // compile list of products
                        .setProductDetailsParamsList(productDetailsParamsList)
                        .build()

                    // launch purchase flow window
                    println("launching billing flow")
                    billingClient.launchBillingFlow(context as ComponentActivity, billingFlowParams)
                }
            }
        }
    }
}