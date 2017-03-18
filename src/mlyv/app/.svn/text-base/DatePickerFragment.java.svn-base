package mlyv.app;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

public TextView activity_edittext;
public TextView cadena_regreso;

public DatePickerFragment(TextView edit_text, TextView cadena) {
    activity_edittext = edit_text;
    cadena_regreso = cadena;
}

@Override
public Dialog onCreateDialog(Bundle savedInstanceState) {
    // Use the current date as the default date in the picker
    final Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);

    // Create a new instance of DatePickerDialog and return it
    return new DatePickerDialog(getActivity(), this, year, month, day);
}

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        activity_edittext.setText(String.valueOf(day) + "/" + String.valueOf(month + 1 ) + "/" +   String.valueOf(year));
        cadena_regreso.setText(String.valueOf(year)+"/"+(month+1)+"/"+day);
    }

}