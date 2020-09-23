package com.coronacircle.adapter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.coronacircle.R;

import java.util.Calendar;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

    /*
     *    일별 동선보기 Dialog 커스텀
     * */
public class YearMonthDayPickerDialog extends DialogFragment {

    private static final int MAX_YEAR = 2021;
    private static final int MIN_YEAR = 2020;

    private DatePickerDialog.OnDateSetListener listener;
    public Calendar cal = Calendar.getInstance();

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    Button btnConfirm;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialog = inflater.inflate(R.layout.dialog_datefilter, null);

        final NumberPicker dayPicker = (NumberPicker) dialog.findViewById(R.id.day_datapicker);
        final NumberPicker monthPicker = (NumberPicker) dialog.findViewById(R.id.month_datapicker);
        final NumberPicker yearPicker = (NumberPicker) dialog.findViewById(R.id.year_datapicker);

        btnConfirm = dialog.findViewById(R.id.confirm_button);
        btnConfirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                listener.onDateSet(null, yearPicker.getValue(), monthPicker.getValue(), 0);
                YearMonthDayPickerDialog.this.getDialog().cancel();
            }
        });

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(cal.get(Calendar.MONTH) + 1);

        int year = cal.get(Calendar.YEAR);
        yearPicker.setMinValue(MIN_YEAR);
        yearPicker.setMaxValue(MAX_YEAR);
        yearPicker.setValue(year);

        dayPicker.setMinValue(1);
        dayPicker.setMaxValue(31);
        dayPicker.setValue(cal.get(Calendar.DAY_OF_MONTH));

        builder.setView(dialog);

        Dialog b = builder.create();
        Window window = b.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();

        //화면 꽉차게
//        DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
//        int width = (int) (dm.widthPixels*0.99);
//        params.width         = width;
//        params.height        = WindowManager.LayoutParams.MATCH_PARENT;

        //모서리 둥글게 (레이아웃.xml에 background 설정해주면 그대로 반영)
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.requestFeature(Window.FEATURE_NO_TITLE);

        // 열기&닫기 시 애니메이션 설정
        params.windowAnimations = R.style.AnimationPopupStyle;

        // UI 하단 정렬
        window.setGravity(Gravity.BOTTOM);
        window.setAttributes( params );

        return b;
    }
}

