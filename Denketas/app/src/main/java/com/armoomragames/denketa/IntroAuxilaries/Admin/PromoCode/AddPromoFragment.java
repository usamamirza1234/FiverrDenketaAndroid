package com.armoomragames.denketa.IntroAuxilaries.Admin.PromoCode;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.armoomragames.denketa.IntroActivity;
import com.armoomragames.denketa.R;
import com.armoomragames.denketa.Utils.AppConstt;
import com.armoomragames.denketa.Utils.IBadgeUpdateListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddPromoFragment extends Fragment implements View.OnClickListener {


    Calendar calendar;
    DatePickerDialog.OnDateSetListener date;
    Calendar todayCalender;

    RelativeLayout rlToolbar, rlBack, rlCross;
    RelativeLayout rlDenketaDetails, rlPromo, rlsave;
    EditText edtPromo;
    TextView edtValidFrom, edtValidTill, edtStarttime, edtEndtime;
    Dialog dialog;
    IBadgeUpdateListener mBadgeUpdateListener;
    private boolean isValidFrom = false, isValidTill = false;
    private boolean isStarttime = false, isEndtime = false;
    private long min_date;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frg = inflater.inflate(R.layout.fragment_add_promo, container, false);

        init();
        bindViews(frg);

        return frg;
    }

    void setToolbar() {

        try {
            mBadgeUpdateListener = (IBadgeUpdateListener) getActivity();
        } catch (ClassCastException castException) {
            castException.printStackTrace(); // The activity does not implement the listener
        }
        if (getActivity() != null && isAdded()) {
            mBadgeUpdateListener.setToolbarState(AppConstt.INTRO_ToolbarStates.TOOLBAR_HIDDEN);

        }

    }

    void init() {
        setToolbar();
        calendar = Calendar.getInstance();
        initializeDate();
        min_date = calendar.getTimeInMillis();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!isHidden()) {
            setToolbar();
        }
    }


    private void bindViews(View frg) {
//        rlToolbar = frg.findViewById(R.id.act_intro_rl_toolbar);
//        rlBack = frg.findViewById(R.id.act_intro_lay_toolbar_rlBack);
//        rlCross = frg.findViewById(R.id.act_intro_lay_toolbar_rlCross);

        edtPromo = frg.findViewById(R.id.frg_add_promo_edt_promo);
        edtValidFrom = frg.findViewById(R.id.frg_add_promo_edt_valid_from);
        edtValidTill = frg.findViewById(R.id.frg_add_promo_edt_valid_till);
        edtStarttime = frg.findViewById(R.id.frg_add_promo_edt_start_time);
        edtEndtime = frg.findViewById(R.id.frg_add_promo_edt_end_t);
        rlsave = frg.findViewById(R.id.frg_add_promo_edt_save);

        edtValidFrom.setOnClickListener(this);
        edtValidTill.setOnClickListener(this);
        edtStarttime.setOnClickListener(this);
        edtEndtime.setOnClickListener(this);
        rlsave.setOnClickListener(this);
//        rlBack.setOnClickListener(this);
//        rlCross.setOnClickListener(this);
//        rlToolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.frg_add_promo_edt_valid_from:
                isValidFrom = true;
                isValidTill = false;
                showCalender().show();
                break;
            case R.id.frg_add_promo_edt_valid_till:
                isValidTill = true;
                isValidFrom = false;
                showCalender().show();
                break;
            case R.id.frg_add_promo_edt_start_time:
                isStarttime = true;
                isEndtime = false;
                setTime();

                break;
            case R.id.frg_add_promo_edt_end_t:
                isStarttime = false;
                isEndtime = true;
                setTime();


                break;


            case R.id.act_intro_lay_toolbar_rlBack:
                getActivity().onBackPressed();
                break;
            case R.id.act_intro_lay_toolbar_rlCross:
                ((IntroActivity) getActivity()).navToPreSignInVAFragment();
                break;
        }
    }

    private void setTime() {


        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, (timePicker, selectedHour, selectedMinute) -> {

            String am_pm = "";

            Calendar datetime = Calendar.getInstance();
            datetime.set(Calendar.HOUR_OF_DAY, selectedHour);
            datetime.set(Calendar.MINUTE, minute);

            if (datetime.get(Calendar.AM_PM) == Calendar.AM)
                am_pm = "AM";
            else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
                am_pm = "PM";

            String strHrsToShow = (datetime.get(Calendar.HOUR) == 0) ? "12" : datetime.get(Calendar.HOUR) + "";


            if (isStarttime) {
                edtStarttime.setText(strHrsToShow + ":" + selectedMinute + " " + am_pm);
                isStarttime = false;
            } else if (isEndtime) {
                edtEndtime.setText(strHrsToShow + ":" + selectedMinute + " " + am_pm);
                isEndtime = false;
            }

        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Promo validation time");
        mTimePicker.show();

    }


    private DatePickerDialog showCalender() {
        DatePickerDialog dpd = new DatePickerDialog(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, date, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dpd.getDatePicker().setMinDate(min_date);
        return dpd;

    }

    private void initializeDate() {
        todayCalender = Calendar.getInstance(Locale.ENGLISH);

        date = (view, year, monthOfYear, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            if (isValidFrom) setValidFrom();
            else if (isValidTill) setValidTill();


        };
    }


    public void setValidFrom() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edtValidFrom.setText(sdf.format(calendar.getTime()));
        isValidFrom = false;
    }


    public void setValidTill() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edtValidTill.setText(sdf.format(calendar.getTime()));
        isValidTill = false;
    }
}
