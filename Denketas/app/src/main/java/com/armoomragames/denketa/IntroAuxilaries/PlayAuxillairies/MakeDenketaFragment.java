package com.armoomragames.denketa.IntroAuxilaries.PlayAuxillairies;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.IntroAuxilaries.DModelCustomDanetka;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Post_AddUserCustomsDanetkas;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.CustomToast;
import com.armoomragames.denketa.Utils.ExifUtil;
import com.armoomragames.denketa.Utils.IWebCallback;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static androidx.core.content.PermissionChecker.checkSelfPermission;
import static com.armoomragames.denketa.Utils.IAdapterCallback.EVENT_A;

public class MakeDenketaFragment extends Fragment implements View.OnClickListener {

    private static final String KEY_POSITION = "position";
    LinearLayout insert1, insert2;
    ImageView imvInsert1, imvInsert2;
    ImageView imvAddMainRegitlto, imvAddSingle;
    RecyclerView rcvRegilto;
    EditText edtRegilto;
    RelativeLayout rlRegiltoitem;
    LinearLayout llSubmit;
    TextView txvSubmit;
    ArrayList<String> lstRegilto;
    RegiltoRCVAdapter regiltoRCVAdapter;
    EditText edtTitle, edtQuestion, edtAns;
    boolean isQuestion;
    private Uri picUri, imageUri;
    private File filePhotoForQuestion;
    private File filePhotoForAnswer;
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
        edtQuestion = frg.findViewById(R.id.frg_make_edtRidle);
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
                if (isGrantedPermCamera())
                {
                    if (isGrantedPermWriteExternalStorage()) {
                        if (getActivity() != null) {
                            showProgDialog();
                            getGalleryPic(true);
                        }
                    }
                }


                break;
            case R.id.frg_make_llInsert2:
                if (isGrantedPermCamera()) {
                    if (isGrantedPermWriteExternalStorage()) {
                        if (getActivity() != null) {
                            showProgDialog();
                            getGalleryPic(false);
                        }
                    }
                }

                break;
            case R.id.frg_make_llSubmit:

                if (!edtTitle.getText().toString().equals("") && !edtQuestion.getText().toString().equals("") && !edtAns.getText().toString().equals("")
                        && filePhotoForQuestion.exists() && filePhotoForQuestion.exists()) {
                    showProgDialog();

                    String hints = android.text.TextUtils.join(",", lstRegilto);
                    DModelCustomDanetka dModelCustomDanetka = new DModelCustomDanetka(
                            edtTitle.getText().toString(),
                            edtAns.getText().toString(),
                            filePhotoForQuestion, filePhotoForAnswer,
                            hints.toString(),
                            edtQuestion.getText().toString(),
                            edtQuestion.getText().toString(),
                            AppConfig.getInstance().mUser.getUser_Id() + ""
                    );
                    requestAddCustomDanteka(dModelCustomDanetka);
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


    private void requestAddCustomDanteka(DModelCustomDanetka dModelCustomDanetka) {


        Intro_WebHit_Post_AddUserCustomsDanetkas intro_webHit_post_addUserCustomsDanetkas = new Intro_WebHit_Post_AddUserCustomsDanetkas();
        intro_webHit_post_addUserCustomsDanetkas.postCustomDanetka(getContext(), new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                if (isSuccess) {
                    dismissProgDialog();
                    CustomToast.showToastMessage(getActivity(), "Added Soon!", Toast.LENGTH_SHORT);
                    llSubmit.setBackground(getActivity().getResources().getDrawable(R.drawable.shp_rect_rounded_app_green));
                    txvSubmit.setText("Submitted");
                } else {
                    dismissProgDialog();
                    CustomToast.showToastMessage(getActivity(), strMsg, Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onWebException(Exception ex) {
                dismissProgDialog();
                CustomToast.showToastMessage(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT);
            }
        }, dModelCustomDanetka);
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


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case AppConstt.REQ_CODE_PERM_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Strorage Permission Granted
                    getGalleryPic(true);
                } else {
                    // Permission Denied
                    CustomToast.showToastMessage(getActivity(),
                            "msg_permission_denied", Toast.LENGTH_SHORT);
                }
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    private String setPicPathForQuestion(boolean isQuestion) {
        //Create Dir if D.N.E
        File fileDir = new File(AppConstt.IMAGE_DIR_PATH);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        String tempImagePath = AppConstt.IMAGE_DIR_PATH;
        tempImagePath += "image_default" + ".png";

        if (isQuestion) {
            this.isQuestion = true;
            filePhotoForQuestion = new File(tempImagePath);
        } else {
            this.isQuestion = false;
            filePhotoForAnswer = new File(tempImagePath);
        }

        Log.d("hdlksaSADFDSAFSD", "setPicPath:tempImagePath " + tempImagePath);
        try {
            Log.d("hdlksaSADFDSAFSD", "setPicPath:filePhotoForQuestion " + filePhotoForQuestion.getName());
            Log.d("hdlksaSADFDSAFSD", "setPicPath:filePhotoForAnswer " + filePhotoForAnswer.getName());
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("hdlksaSADFDSAFSD", "setPicPath: " + e.getMessage());
        }

        return tempImagePath;
    }


    public void getGalleryPic(boolean isQuestion) {
        setPicPathForQuestion(isQuestion);
        if (Build.VERSION.SDK_INT < 19) {
            Intent intent = new Intent();
            intent.setType("image/*");
//            intent.setType("image/");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), AppConstt.GALLERY_INTENT_CALLED);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
//            intent.setType("image/jpeg");
            intent.setType("image/*");
            startActivityForResult(intent, AppConstt.GALLERY_KITKAT_INTENT_CALLED);
        }
    }

    //region Permissions
    @SuppressWarnings("ResourceType")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri originalUri = null;
        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == AppConstt.GALLERY_INTENT_CALLED || requestCode == AppConstt.GALLERY_KITKAT_INTENT_CALLED) {
                originalUri = data.getData();

                try {
                    dismissProgDialog();
                    if (isQuestion) {
                        Bitmap bitmap;
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), originalUri);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 50, bos);
                        byte[] bitmapdata = bos.toByteArray();
                        FileOutputStream fos = new FileOutputStream(filePhotoForQuestion);
                        String imagePath = filePhotoForQuestion.getAbsolutePath();
                        Bitmap orientedBitmap = ExifUtil.rotateBitmap(imagePath, bitmap);
                        imvInsert1.setImageBitmap(orientedBitmap);
                        fos.write(bitmapdata);
                        fos.flush();
                        fos.close();

                    }
                    else {
                        Bitmap bitmap;
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), originalUri);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 50, bos);
                        byte[] bitmapdata = bos.toByteArray();
                        FileOutputStream fos = new FileOutputStream(filePhotoForAnswer);
                        String imagePath = filePhotoForAnswer.getAbsolutePath();             // photoFile is a File class.
                        Bitmap orientedBitmap = ExifUtil.rotateBitmap(imagePath, bitmap);
                        imvInsert2.setImageBitmap(orientedBitmap);
                        fos.write(bitmapdata);
                        fos.flush();
                        fos.close();

                    }
                }
                catch (IOException e)
                {

                    dismissProgDialog();
                    e.printStackTrace();
                    CustomToast.showToastMessage(getActivity(), e.toString(), Toast.LENGTH_LONG);
                }
                if (requestCode == AppConstt.GALLERY_KITKAT_INTENT_CALLED) {
                    // Check for the freshest data.
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        final int takeFlags = data.getFlags()
                                & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                                | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        getActivity().getApplicationContext().getContentResolver().takePersistableUriPermission(originalUri, takeFlags);
                    }
                }
            }

        }

        super.onActivityResult(requestCode, resultCode, data);

    }



    private boolean isGrantedPermCamera() {
        if (Build.VERSION.SDK_INT >= 23) {
            int hasCameraPermission = checkSelfPermission(getContext(), Manifest.permission.CAMERA);
            if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        AppConstt.REQ_CODE_PERM_CAMERA);
                return false;
            }
            return true;
        } else
            return true;
    }

    private boolean isGrantedPermWriteExternalStorage() {
        if (Build.VERSION.SDK_INT >= 23) {
            int hasWritePermission = checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (hasWritePermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        AppConstt.REQ_CODE_WRITE_EXTERNAL_STORAGE);
                return false;
            }
            return true;
        } else
            return true;
    }
    //endregion

}
