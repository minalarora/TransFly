package com.truck.transfly.Activty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.leavjenn.smoothdaterangepicker.date.SmoothDateRangePickerFragment;
import com.truck.transfly.Frament.ShowInvoiceFragment;
import com.truck.transfly.Model.ResponseFieldStaff;
import com.truck.transfly.Model.ResponseVehicleOwner;
import com.truck.transfly.R;
import com.truck.transfly.utils.TransflyApplication;

import org.joda.time.DateTime;

import java.util.Calendar;

public class FieldStaffInvoicesActivity extends AppCompatActivity implements SmoothDateRangePickerFragment.OnDateRangeSetListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_staff_invoices);

        findViewById(R.id.export_report).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Calendar now = Calendar.getInstance();

                SmoothDateRangePickerFragment smoothDateRangePickerFragment = SmoothDateRangePickerFragment.newInstance(FieldStaffInvoicesActivity.this::onDateRangeSet, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));

                smoothDateRangePickerFragment.setAccentColor(R.color.project_color);

                smoothDateRangePickerFragment.setThemeDark(false);

                smoothDateRangePickerFragment.show(getFragmentManager(), "smoothDateRangePicker");

            }
        });

        ShowInvoiceFragment showInvoiceFragment = new ShowInvoiceFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, showInvoiceFragment, "showInvoices").commit();

    }

    @Override
    public void onDateRangeSet(SmoothDateRangePickerFragment view, int yearStart, int monthStart, int dayStart, int yearEnd, int monthEnd, int dayEnd) {

        ResponseFieldStaff responseVehicleOwner = ((TransflyApplication) getApplication()).getResponseFieldStaff();

        DateTime dateStart = new DateTime(yearStart, monthStart + 1, dayStart, new DateTime().getHourOfDay(), new DateTime().getMinuteOfHour());

        DateTime dateEnd = new DateTime(yearEnd, monthEnd + 1, dayEnd, new DateTime().getHourOfDay(), new DateTime().getMinuteOfHour()).plusDays(1);

        Intent intent = new Intent(FieldStaffInvoicesActivity.this, WebViewActivity.class);
        intent.putExtra("from_time", dateStart.getMillis());
        intent.putExtra("to_time", dateEnd.getMillis());
        intent.putExtra("mobile", responseVehicleOwner.getMobile());

        intent.putExtra("keywords", "mobinvoicefieldstaff");

        startActivity(intent);

    }
}