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
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.interfaces.HttpResponseCallback;
import com.braintreepayments.api.models.PaymentMethodNonce;

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
                String stringNonce = nonce.getNonce();
                Log.d("mylog", "Result: " + stringNonce);
                // Send payment price with the nonce
                // use the result to update your UI and send the payment method nonce to your server
                if (!etAmount.getText().toString().isEmpty()) {
                    amount = etAmount.getText().toString();
                    paramHash = new HashMap<>();
                    paramHash.put("amount", amount);
                    paramHash.put("nonce", stringNonce);
//                    sendPaymentDetails();
                } else
                    Toast.makeText(BraintreeActivity.this, "Please enter a valid amount.", Toast.LENGTH_SHORT).show();

            } else if (resultCode == Activity.RESULT_CANCELED) {
                // the user canceled
                Log.d("mylog", "user canceled");
            } else {
                // handle errors here, an exception may be available in
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Log.d("mylog", "Error : " + error.toString());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onBraintreeSubmit(String token) {
//        DropInRequest dropInRequest = new DropInRequest().clientToken(token);
//        startActivityForResult(dropInRequest.getIntent(this), REQUEST_CODE);

        DropInRequest dropInRequest = new DropInRequest().tokenizationKey("sandbox_f252zhq7_hh4cpc39zq4rgjcg");
        startActivityForResult(dropInRequest.getIntent(this), REQUEST_CODE);


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


//    private class HttpRequest extends AsyncTask {
//        ProgressDialog progress;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progress = new ProgressDialog(BraintreeActivity.this, android.R.style.Theme_DeviceDefault_Dialog);
//            progress.setCancelable(false);
//            progress.setMessage("We are contacting our servers for token, Please wait");
//            progress.setTitle("Getting token");
//            progress.show();
//        }
//
//        @Override
//        protected Object doInBackground(Object[] objects) {
//            HttpClient client = new HttpClient();
//            client.get(get_token, new HttpResponseCallback() {
//                @Override
//                public void success(String responseBody) {
//                    Log.d("mylog", responseBody);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(BraintreeActivity.this, "Successfully got token", Toast.LENGTH_SHORT).show();
//                            llHolder.setVisibility(View.VISIBLE);
//                        }
//                    });
//                    token = responseBody;
//                }
//
//                @Override
//                public void failure(Exception exception) {
//                    final Exception ex = exception;
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(BraintreeActivity.this, "Failed to get token: " + ex.toString(), Toast.LENGTH_LONG).show();
//                        }
//                    });
//                }
//            });
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Object o) {
//            super.onPostExecute(o);
//            progress.dismiss();
//        }
//    }


}