package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.CustomToast;

import java.io.ByteArrayOutputStream;

public class MakeDenketaFragment extends Fragment implements View.OnClickListener {
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final String KEY_POSITION = "position";
    RelativeLayout rlToolbar, rlBack, rlCross;
    String encodedImage;
    Bitmap selectedImageBitmap = null;
    LinearLayout insert1, insert2;
    ImageView imvInsert1,imvInsert2;
    boolean isFirst=false;
    boolean isSecond=false;

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
        bindviews(frg);
        return frg;
    }

    private void bindviews(View frg)
    {
        insert1 = frg.findViewById(R.id.frg_make_llInsert1);
        insert2 = frg.findViewById(R.id.frg_make_llInsert2);
        imvInsert1 = frg.findViewById(R.id.frg_make_inset1Imv);
        imvInsert2 = frg.findViewById(R.id.frg_make_inset2Imv);


        insert1.setOnClickListener(this);
        insert2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.frg_make_llInsert1:
                isFirst=true; isSecond=false;
                takePhoto();
                break;
            case R.id.frg_make_llInsert2:
                isFirst=false; isSecond=true;
                takePhoto();
                break;
        }
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
            if (isFirst&& !isSecond){
                imvInsert1.setImageBitmap(selectedImageBitmap);
                imvInsert1.setVisibility(View.VISIBLE);

            }

            if (!isFirst&& isSecond){
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
