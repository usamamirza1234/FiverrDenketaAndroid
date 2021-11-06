package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.CustomToast;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import static com.armoomragames.denketa.Utils.IAdapterCallback.EVENT_A;

public class MakeDenketaFragment extends Fragment implements View.OnClickListener {
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final String KEY_POSITION = "position";
    RelativeLayout rlToolbar, rlBack, rlCross;
    String encodedImage;
    Bitmap selectedImageBitmap = null;
    LinearLayout insert1, insert2;
    ImageView imvInsert1, imvInsert2;
    ImageView imvAddMainRegitlto, imvAddSingle;
    RecyclerView rcvRegilto;
    boolean isFirst = false;
    boolean isSecond = false;
    EditText edtRegilto;
    RelativeLayout rlRegiltoitem;
    LinearLayout llSubmit;
    TextView txvSubmit;
    ArrayList<String> lstRegilto;
    RegiltoRCVAdapter regiltoRCVAdapter;
    EditText edtTitle, edtRidle, edtAns;
    private Dialog progressDialog;

    public static Fragment newInstance(int position) {
        Fragment frag = new MakeDenketaFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        frag.setArguments(args);
        return (frag);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_make_denketa, container, false);

        init();
        bindviews(frg);

        lstRegilto.add("Press Add.. Example");
        populatePopulationList();
        return frg;
    }

    private void init() {
        lstRegilto = new ArrayList<>();
    }

    private void bindviews(View frg) {
        rcvRegilto = frg.findViewById(R.id.frg_make_rcv_regilto);
        imvAddMainRegitlto = frg.findViewById(R.id.frg_make_imv_addRegiltoMain);
        edtRegilto = frg.findViewById(R.id.frg_make_edtRegilto);


        edtTitle = frg.findViewById(R.id.frg_make_edtTitle);
        edtRidle = frg.findViewById(R.id.frg_make_edtRidle);
        edtAns = frg.findViewById(R.id.frg_make_edtAnswer);


        rlRegiltoitem = frg.findViewById(R.id.frg_make_rlRegiltoItem);
        imvAddSingle = frg.findViewById(R.id.frg_make_imvRegilto);

        llSubmit = frg.findViewById(R.id.frg_make_llSubmit);
        txvSubmit = frg.findViewById(R.id.frg_make_txvSubmit);


        insert1 = frg.findViewById(R.id.frg_make_llInsert1);
        insert2 = frg.findViewById(R.id.frg_make_llInsert2);
        imvInsert1 = frg.findViewById(R.id.frg_make_inset1Imv);
        imvInsert2 = frg.findViewById(R.id.frg_make_inset2Imv);


        insert1.setOnClickListener(this);
        insert2.setOnClickListener(this);
        imvAddSingle.setOnClickListener(this);
        imvAddMainRegitlto.setOnClickListener(this);
        llSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.frg_make_llInsert1:
                isFirst = true;
                isSecond = false;
                takePhoto();
                break;
            case R.id.frg_make_llInsert2:
                isFirst = false;
                isSecond = true;
                takePhoto();
                break;
            case R.id.frg_make_llSubmit:


                if (!edtTitle.getText().toString().equals("") && !edtRidle.getText().toString().equals("") && !edtAns.getText().toString().equals("")) {
                    showProgDialog();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            //do something
                            dismissProgDialog();
                            llSubmit.setBackground(getActivity().getResources().getDrawable(R.drawable.shp_rect_rounded_app_green));
                            txvSubmit.setText("Submitted");
                        }
                    }, 5000);//time in milisecond
                } else
                    CustomToast.showToastMessage(getActivity(), "Please fill all fields", Toast.LENGTH_LONG);

                break;

            case R.id.frg_make_imv_addRegiltoMain:

                if (lstRegilto.size() <= 4) {
                    rlRegiltoitem.setVisibility(View.VISIBLE);
                    imvAddMainRegitlto.setVisibility(View.GONE);
                } else
                    CustomToast.showToastMessage(getActivity(), "Maximum Added", Toast.LENGTH_LONG);
                break;
            case R.id.frg_make_imvRegilto:
                if (lstRegilto.size() <= 4) {
                    if (!edtRegilto.getText().toString().equals("")) {
                        lstRegilto.add(edtRegilto.getText().toString());
                        populatePopulationList();
                        rlRegiltoitem.setVisibility(View.GONE);
                        edtRegilto.setText("");
                        imvAddMainRegitlto.setVisibility(View.VISIBLE);
                        AppConfig.getInstance().closeKeyboard(getActivity());
                    } else
                        CustomToast.showToastMessage(getActivity(), "Enter some text", Toast.LENGTH_LONG);

                } else
                    CustomToast.showToastMessage(getActivity(), "Maximum Added", Toast.LENGTH_LONG);
                break;
        }
    }


    private void populatePopulationList() {


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        if (regiltoRCVAdapter == null) {

            regiltoRCVAdapter = new RegiltoRCVAdapter(getActivity(), lstRegilto, (eventId, position) -> {

                switch (eventId) {
                    case EVENT_A:


                        break;
                }

            });


            rcvRegilto.setLayoutManager(linearLayoutManager);
            rcvRegilto.setAdapter(regiltoRCVAdapter);

        } else {
            regiltoRCVAdapter.notifyDataSetChanged();
        }
    }

    private void dismissProgDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void showProgDialog() {

        progressDialog = new Dialog(getActivity(), R.style.AppTheme);
//        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress_loading);
        WindowManager.LayoutParams wmlp = progressDialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER | Gravity.CENTER;
        wmlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wmlp.height = ViewGroup.LayoutParams.MATCH_PARENT;

        ImageView imageView = progressDialog.findViewById(R.id.img_anim);
        Glide.with(getContext()).asGif().load(R.raw.loading).into(imageView);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();


    }

    //region PermissionsAccess
    //region Permissions


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Uri selectedImageUri = data.getData();

            try {
                selectedImageBitmap = (Bitmap) data.getExtras().get("data");
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (isFirst && !isSecond) {
                imvInsert1.setImageBitmap(selectedImageBitmap);
                imvInsert1.setVisibility(View.VISIBLE);

            }

            if (!isFirst && isSecond) {
                imvInsert2.setImageBitmap(selectedImageBitmap);
                imvInsert2.setVisibility(View.VISIBLE);

            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            selectedImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArrayImage = byteArrayOutputStream.toByteArray();
            encodedImage = Base64.encodeToString(byteArrayImage, Base64.NO_WRAP);
            Log.d("Vicky", "I'm in.");

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        Log.d("GPS_TRACKER", " : requestCode " + requestCode);
        switch (requestCode) {

            case MY_CAMERA_PERMISSION_CODE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CustomToast.showToastMessage(getActivity(), "Camera permission granted", Toast.LENGTH_LONG);
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                } else {
                    CustomToast.showToastMessage(getActivity(), "Camera permission denied", Toast.LENGTH_LONG);

                }
            }
            break;


        }
    }


    //endregion
    //region  Camera
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void takePhoto() {
        if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
        } else {

            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }


    }
    //endregion
    //endregion
}
