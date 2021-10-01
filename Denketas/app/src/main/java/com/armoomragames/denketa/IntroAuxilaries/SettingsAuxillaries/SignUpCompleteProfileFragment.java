package com.armoomragames.denketa.IntroAuxilaries.SettingsAuxillaries;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.armoomragames.denketa.AppConfig;
import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.IntroAuxilaries.WebServices.Intro_WebHit_Post_AddUserProfile;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;
import com.armoomragames.denketa.Utils.IWebCallback;
import com.google.gson.JsonObject;

public class SignUpCompleteProfileFragment extends Fragment implements View.OnClickListener {


    TextView txvLogin, txvSignout;
    RelativeLayout rlGetStarted;

    EditText edtName, edtEmail, edtDOB;
    EditText edtNationality, edtGender;
    IBadgeUpdateListener mBadgeUpdateListener;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_signup_complete, container, false);


        init();
        bindViews(frg);
        edtEmail.setText(AppConfig.getInstance().mUser.getEmail());

        if (!AppConfig.getInstance().mUser.Name.isEmpty())
            edtName.setText(AppConfig.getInstance().mUser.getName());
        if (!AppConfig.getInstance().mUser.getNationality().isEmpty())
            edtNationality.setText(AppConfig.getInstance().mUser.getNationality());
        if (!AppConfig.getInstance().mUser.getGender().isEmpty())
            edtGender.setText(AppConfig.getInstance().mUser.getGender());
        if (!AppConfig.getInstance().mUser.getDOB().isEmpty())
            edtDOB.setText(AppConfig.getInstance().mUser.getDOB());


        return frg;
    }



    void setToolbar() {

        try {
            mBadgeUpdateListener = (IBadgeUpdateListener) getActivity();
        } catch (ClassCastException castException) {
            castException.printStackTrace(); // The activity does not implement the listener
        }
        if (getActivity() != null && isAdded()) {
            mBadgeUpdateListener.setToolbarState(AppConstt.INTRO_ToolbarStates.TOOLBAR_VISIBLE);
        }

    }

    void init() {
        setToolbar();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!isHidden()) {
            setToolbar();
        }
    }
    private void bindViews(View frg) {


//        txvLogin = frg.findViewById(R.id.fg_signup_complete_txvLogin);
        txvSignout = frg.findViewById(R.id.fg_signup_complete_txvSignOut);
        rlGetStarted = frg.findViewById(R.id.fg_signup_complete_rlRegister);

        edtName = frg.findViewById(R.id.frg_profile_edtname);
        edtEmail = frg.findViewById(R.id.frg_profile_edtEmail);
        edtDOB = frg.findViewById(R.id.frg_profile_edtdob);
        edtGender = frg.findViewById(R.id.frg_profile_edtgender);
        edtNationality = frg.findViewById(R.id.frg_profile_edtNationality);

//        txvLogin.setOnClickListener(this);
        rlGetStarted.setOnClickListener(this);
        txvSignout.setOnClickListener(this);

        editTextWatchers();
    }

    //region Validation
    private void editTextWatchers() {

        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().startsWith(" ")) {
                    edtEmail.setText("");
                }
            }
        });
        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().startsWith(" ")) {
                    edtName.setText("");
                }
            }
        });
        edtDOB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().startsWith(" ")) {
                    edtDOB.setText("");
                }
            }
        });
        edtNationality.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().startsWith(" ")) {
                    edtNationality.setText("");
                }
            }
        });
        edtGender.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().startsWith(" ")) {
                    edtGender.setText("");
                }
            }
        });

    }

    private void closeKeyboard() {
        AppConfig.getInstance().closeKeyboard(getActivity());

    }


    private boolean checkDobErrorCondition() {
//        if (edtDOB.getText().toString().equalsIgnoreCase("Date of Birth")) {
        if (edtDOB.getText().toString().isEmpty()) {
//            AppConfig.getInstance().showErrorMessage(getContext(), "Date of birth is not selected");
            Toast.makeText(getActivity(), "Date of birth is not selected", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean checkNameErrorCondition() {
        if (edtName.getText().toString().isEmpty()) {
//            AppConfig.getInstance().showErrorMessage(getContext(), "Empty name field");
            Toast.makeText(getActivity(), "Empty Name Feild", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean checkCityErrorCheck() {
        if (edtNationality.getText().toString().isEmpty()) {
//        if (edtNationality.getText().toString().equalsIgnoreCase("Select City")) {
//            AppConfig.getInstance().showErrorMessage(getContext(), "City is not selected");
            Toast.makeText(getActivity(), "Country is not selected", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean checkGenderCheck() {
//        if (edtGender.getText().toString().equalsIgnoreCase("Select Gender")) {
        if (edtGender.getText().toString().isEmpty()) {
//            AppConfig.getInstance().showErrorMessage(getContext(), "City is not selected");
            Toast.makeText(getActivity(), "Gender is not selected", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void checkErrorConditions() {
        if (checkCityErrorCheck() && checkNameErrorCondition() && checkDobErrorCondition() && checkGenderCheck()) {

            closeKeyboard();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", edtName.getText().toString());
            jsonObject.addProperty("userName", edtName.getText().toString());
            jsonObject.addProperty("dateOfBirth", "1999-02-02");
//            jsonObject.addProperty("dateOfBirth", edtDOB.getText().toString());
            jsonObject.addProperty("gender", edtGender.getText().toString().toLowerCase());
            jsonObject.addProperty("nationality", "German");
//            jsonObject.addProperty("nationality", edtNationality.getText().toString());

            requestUserProfile(jsonObject.toString());
        }
    }


//
//    private boolean termCndtionErrorCondition() {
//        if (chbTermCondition.isChecked()) {
//            return true;
//        } else {
//            AppConfig.getInstance().showErrorMessage(getContext(), "Terms and conditions are not checked");
//            return false;
//        }
//    }


//
//    private boolean checkNumberErrorCondition() {
//        if (edtPhoneNum.getText().toString().isEmpty()) {
//            AppConfig.getInstance().showErrorMessage(getContext(), "Empty phone number field");
//            return false;
//        } else {
//            return true;
//        }
//    }


//

//
//    private void showCalender() {
////        new SingleDateAndTimePickerDialog.Builder(getContext())
////                .bottomSheet()
////                .curved()
////                .displayMinutes(false)
////                .displayHours(false)
////                .displayDays(false)
////                .displayMonth(true)
////                .displayYears(true).mainColor(Color.BLACK)
////                .displayDaysOfMonth(true).listener(new SingleDateAndTimePickerDialog.Listener() {
////            @Override
////            public void onDateSelected(Date date) {
////                DateFormat format = new SimpleDateFormat("dd MMMM, yyyy");
////                String datee = format.parse(Date.parse( date.toString()));
////
////                txvDob.setText(date.toString());
//////                if (date.after(yesterday()))
//////                {
//////                    goalDate =datee;
//////                    txvCalender.setText(datee);
//////                    btnNext.setEnabled(true);
//////                }
////            }
////        }).defaultDate(calendar.getTime()).display();
//////
//////
//
//        new DatePickerDialog(getContext(), AlertDialog.THEME_HOLO_LIGHT, date, calendar
//                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
//                calendar.get(Calendar.DAY_OF_MONTH)).show();
//    }
    //endregion


    private void requestUserProfile(String _signUpEntity) {
        Intro_WebHit_Post_AddUserProfile intro_webHit_post_addUserProfile = new Intro_WebHit_Post_AddUserProfile();
        intro_webHit_post_addUserProfile.postAddProfile(getContext(), new IWebCallback() {
            @Override
            public void onWebResult(boolean isSuccess, String strMsg) {
                if (isSuccess) {

                    AppConfig.getInstance().mUser.Name = Intro_WebHit_Post_AddUserProfile.responseObject.getData().getName();
                    AppConfig.getInstance().mUser.DOB = Intro_WebHit_Post_AddUserProfile.responseObject.getData().getDateOfBirth();
                    AppConfig.getInstance().mUser.Gender = Intro_WebHit_Post_AddUserProfile.responseObject.getData().getGender();
                    AppConfig.getInstance().mUser.Nationality = Intro_WebHit_Post_AddUserProfile.responseObject.getData().getNationality();

                    AppConfig.getInstance().saveUserProfile();

                    ((IntroActivity) getActivity()).navToPreSignInVAFragment();
                } else {
                    Toast.makeText(getActivity(), strMsg, Toast.LENGTH_SHORT).show();
//                    AppConfig.getInstance().showErrorMessage(getContext(), strMsg);
                }
            }

            @Override
            public void onWebException(Exception ex) {
                Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
//                AppConfig.getInstance().showErrorMessage(getContext(), ex.toString());
            }
        }, _signUpEntity);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.fg_signup_complete_rlRegister:
                checkErrorConditions();
                break;
            case R.id.fg_signup_complete_txvSignOut:
                AppConfig.getInstance().navtoLogin();
                break;
        }
        }


    }
