package com.armoomragames.denketa;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.braintreepayments.api.BraintreeClient;
import com.braintreepayments.api.BrowserSwitchResult;
import com.braintreepayments.api.Card;
import com.braintreepayments.api.CardClient;
import com.braintreepayments.api.DropInResult;
import com.braintreepayments.api.PayPalCheckoutRequest;
import com.braintreepayments.api.PayPalClient;
import com.braintreepayments.api.PayPalPaymentIntent;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Get_Token;
import com.armoomragames.denketa.Utils.IWebCallback;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.HashMap;

public class BraintreeActivity extends AppCompatActivity {

    final String get_token = "YOUR-API-TO-GET-TOKEN";
    final String send_payment_details = "YOUR-API-FOR-PAYMENTS";
    String token, amount;
    HashMap<String, String> paramHash;

    Button btnPay;
    EditText etAmount;
    LinearLayout llHolder;

    private static final int REQUEST_CODE = 123;
    private Button paypal_button;


        CardClient cardClient;
    PayPalClient payPalClient;
    private BraintreeClient braintreeClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_braintree);


        braintreeClient = new BraintreeClient(BraintreeActivity.this, "sandbox_v2nf5t6c_mybf9tq8g5qv92zw");
        payPalClient = new PayPalClient(braintreeClient);
        cardClient = new CardClient(braintreeClient);

//        tokenizeCard();

//        myTokenizePayPalAccountWithVaultMethod();

        paypal_button=(Button)findViewById(R.id.start_payment);

        paypal_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTokenizePayPalAccountWithCheckoutMethod();
//                requestFeedback(); //  -->> add api to generate client token to call startPayment()
//                onBraintreeSubmit(getString(R.string.test_client_token)); // -->> added for testing with sample client token, call this method from onResponse() of startPayment() only
            }
        });
    }


    private void myTokenizePayPalAccountWithCheckoutMethod() {

        PayPalCheckoutRequest request = new PayPalCheckoutRequest("0.01");
        request.setCurrencyCode("USD");
        request.setIntent(PayPalPaymentIntent.AUTHORIZE);
        payPalClient.tokenizePayPalAccount(BraintreeActivity.this, request, (error) -> {
            if (error != null) {
                // Handle error
            }
            else{
                if (error != null)
                Log.d("DROP_IN_REQUEST_CODE","Error: "+ error.toString());
            }


        });


    }
    public void startPayment() {

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://18.119.55.236:2000/api/v1/denetkas/generate/payment/token")   // http://www.example.com
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        RequestInterface request = retrofit.create(RequestInterface.class);
//        retrofit2.Call<String> call1=request.getClientToken();
//        call1.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(retrofit2.Call<String> call, Response<String> response) {
//                Toast.makeText(BraintreeActivity.this,"Success",Toast.LENGTH_SHORT).show();
//                if (response.isSuccessful() && response!=null) {
//
//                }
//            }
//            @Override
//            public void onFailure(retrofit2.Call<String> call, Throwable t) {
//                Toast.makeText(BraintreeActivity.this,"Oops! Something went wrong!  " +call.request(),Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void requestFeedback() {

        Intro_WebHit_Get_Token intro_webHit_get_token = new Intro_WebHit_Get_Token();
        intro_webHit_get_token.getToken(new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                if (isSuccess) {
                    Toast.makeText(BraintreeActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    onBraintreeSubmit(Intro_WebHit_Get_Token.responseObject.getData().getClientToken());
                } else {
                    Toast.makeText(BraintreeActivity.this, strMsg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onWebException(Exception ex) {
                Toast.makeText(BraintreeActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE) {
//            if (resultCode == Activity.RESULT_OK) {
//                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
//                String payment_method_nonce = result.getPaymentMethodNonce().getNonce();
////                submitNonce(payment_method_nonce); -->> create server side api to accept payment amount and PaymentNonce
//                // use the result to update your UI and send the payment method nonce to your server
//            } else if (resultCode == Activity.RESULT_CANCELED) {
//                // the user canceled
//            } else {
//                // handle errors here, an exception may be available in
//                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
//            }
//        }
    }

    private void submitNonce(String payment_method_nonce) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("add_base_url")  // http://www.example.com
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        JsonObject jsonObject=new JsonObject();
//        jsonObject.addProperty("AmountDebit", "");
//        jsonObject.addProperty("PaymentNonce",payment_method_nonce);
//
//        RequestInterface request = retrofit.create(RequestInterface.class);
//        Call<ResponseBody> call1=request.getPayment(jsonObject);
//        call1.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {
//
//            }
//
//            @Override
//            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
//
//            }
//
//
//        });
    }

    public void onBraintreeSubmit(String token) {
//        DropInRequest dropInRequest = new DropInRequest()
//                .clientToken(token);
//        startActivityForResult(dropInRequest.getIntent(this), REQUEST_CODE);
    }




}