package com.armoomragames.denketa;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Get_Token;
import com.armoomragames.denketa.Utils.IWebCallback;
import com.braintreepayments.api.BraintreeClient;
import com.braintreepayments.api.BrowserSwitchResult;
import com.braintreepayments.api.DataCollector;
import com.braintreepayments.api.DropInActivity;
import com.braintreepayments.api.DropInClient;
import com.braintreepayments.api.DropInRequest;
import com.braintreepayments.api.DropInResult;
import com.braintreepayments.api.PayPalCheckoutRequest;
import com.braintreepayments.api.PayPalClient;
import com.braintreepayments.api.PayPalPaymentIntent;
import com.braintreepayments.api.PaymentMethodNonce;
import com.braintreepayments.api.PostalAddress;

import java.util.HashMap;

import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.client.HttpClient;


public class BraintreeActivity extends AppCompatActivity {

    String btToken = "sandbox_v2nf5t6c_mybf9tq8g5qv92zw";
    final int REQUEST_CODE = 1;
    final String get_token = "YOUR-API-TO-GET-TOKEN";
    final String send_payment_details = "YOUR-API-FOR-PAYMENTS";
    String token, amount;
    HashMap<String, String> paramHash;

    String btToken1   = "sandbox_f252zhq7_hh4cpc39zq4rgjcg";
    BraintreeClient braintreeClient;
    PayPalClient payPalClient ;
    DataCollector dataCollector;

    Button btnPay;
    EditText etAmount;
    LinearLayout llHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_braintree);
        llHolder = (LinearLayout) findViewById(R.id.llHolder);
        etAmount = (EditText) findViewById(R.id.etPrice);
        btnPay = (Button) findViewById(R.id.btnPay);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestFeedback();
            }
        });
     requestFeedback();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                PaymentMethodNonce nonce = result.getPaymentMethodNonce();
//                String stringNonce = nonce.getNonce();
                Log.d("mylog", "Result: " + nonce);
                // Send payment price with the nonce
                // use the result to update your UI and send the payment method nonce to your server
//                if (!etAmount.getText().toString().isEmpty()) {
//                    amount = etAmount.getText().toString();
////                    paramHash = new HashMap<>();
////                    paramHash.put("amount", amount);
////                    paramHash.put("nonce", stringNonce);
////                    sendPaymentDetails();
//                } else
//                    Toast.makeText(BraintreeActivity.this, "Please enter a valid amount.", Toast.LENGTH_SHORT).show();

            } else if (resultCode == Activity.RESULT_CANCELED) {
                // the user canceled
                Log.d("mylog", "user canceled");
            } else {
                // handle errors here, an exception may be available in
                Exception error = (Exception) data.getSerializableExtra(DropInResult.EXTRA_ERROR);
                Log.d("mylog", "Error : " + error.toString());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onBraintreeSubmit(String token) {
        braintreeClient = new BraintreeClient(this, token);
        payPalClient = new PayPalClient(braintreeClient);
        dataCollector = new DataCollector(braintreeClient);
        onPayPalButtonClick();


//        DropInRequest dropInRequest = new DropInRequest();
//        dropInRequest.setGooglePayDisabled(true);
//        DropInClient dropInClient = new DropInClient(this, btToken, dropInRequest);
//        dropInClient.launchDropInForResult(this, REQUEST_CODE);
    }


    private void requestFeedback() {
        ProgressDialog progress;
        progress = new ProgressDialog(BraintreeActivity.this, android.R.style.Theme_DeviceDefault_Dialog);
        progress.setCancelable(false);
        progress.setMessage("We are contacting our servers for token, Please wait");
        progress.setTitle("Getting token");
        progress.show();
        Intro_WebHit_Get_Token intro_webHit_get_token = new Intro_WebHit_Get_Token();
        intro_webHit_get_token.getToken(new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                if (isSuccess) {
                    Toast.makeText(BraintreeActivity.this, "Success", Toast.LENGTH_SHORT).show();  progress.dismiss();
                    onBraintreeSubmit(Intro_WebHit_Get_Token.responseObject.getData().getClientToken());
                } else {
                    Toast.makeText(BraintreeActivity.this, strMsg, Toast.LENGTH_SHORT).show();   progress.dismiss();
                }
            }

            @Override
            public void onWebException(Exception ex) {
                Toast.makeText(BraintreeActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();   progress.dismiss();
            }
        });
    }

    private void onPayPalButtonClick() {
        PayPalCheckoutRequest request = new PayPalCheckoutRequest("0.01");
        request.setCurrencyCode("USD");
        request.setIntent(PayPalPaymentIntent.AUTHORIZE);
        // The PayPalRequest type will be based on integration type (Checkout vs. Vault)
        payPalClient.tokenizePayPalAccount(this, request, (error) -> {
            if (error != null) {
                // handle error
                Log.d("mylog","Err: "+ error.toString());
            }
            else {
                Log.d("mylog","Req: "+ request.toString());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (braintreeClient !=null)
        {
            BrowserSwitchResult browserSwitchResult = braintreeClient.deliverBrowserSwitchResult(this);
            if (browserSwitchResult != null) {
                payPalClient.onBrowserSwitchResult(browserSwitchResult, (payPalAccountNonce, browserSwitchError) -> {
                    dataCollector.collectDeviceData(this, (deviceData, dataCollectorError) -> {
                        // send paypalAccountNonce.getString() and deviceData to server
                        PostalAddress billingAddress = payPalAccountNonce.getBillingAddress();
                        String streetAddress = billingAddress.getStreetAddress();
                        String extendedAddress = billingAddress.getExtendedAddress();
                        String locality = billingAddress.getLocality();
                        String countryCodeAlpha2 = billingAddress.getCountryCodeAlpha2();
                        String postalCode = billingAddress.getPostalCode();
                        String region = billingAddress.getRegion();
                        Log.d("mylog","streetAddress: "+ streetAddress);
                        Log.d("mylog","streetAddress: "+ extendedAddress);
                        Log.d("mylog","streetAddress: "+ locality);
                        Log.d("mylog","streetAddress: "+ postalCode);
                        Log.d("mylog","streetAddress: "+ countryCodeAlpha2);
                        Log.d("mylog","streetAddress: "+ streetAddress);
                        Log.d("mylog","streetAddress: "+ region);
                        Log.d("mylog","streetAddress: "+ streetAddress);
                    });
                });
            }
            else {
                Log.d("mylog","braintreeClient==nill ");
            }
        }
        else {
            Log.d("mylog","braintreeClient==nill ");
        }

    }
    @Override
    protected void onNewIntent(Intent newIntent) {
        super.onNewIntent(newIntent);

        setIntent(newIntent);
    }
}