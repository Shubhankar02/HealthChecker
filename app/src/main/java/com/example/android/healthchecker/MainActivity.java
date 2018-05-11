package com.example.android.healthchecker;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when calculate button is clicked
     * This will update the BMI value in the result
     *
     * @param view
     */
    public void calculateBMI(View view) {
        displayResult(calcuateResult());
        status();
        dietSuggestion();
        exerciseSuggetion();
    }

    /**
     * This method is called when 'Create an event in calender' button is clicked
     * This will open a calender app to create an event
     * @param view
     */
    public void createEvent(View view){
        calender();
    }

    /**
     * This method is called when 'Email me the all tips' button is clicked
     * This will open an email app for user to send an email
     *
     * @param view
     */
    public void emailTips(View view) {
        email();
    }

    /**
     * This method update the text with BMI value
     *
     * @param val
     */
    private void displayResult(String val) {
        TextView result = (TextView) findViewById(R.id.result);
        result.setText(val);
    }

    /**
     * This method convert BMI value int to String
     *
     * @return String calcuateResult
     */
    private String calcuateResult() {
        String val = String.valueOf(BMI());
        return val;
    }

    /**
     * This method calculate the BMI and hold in Float
     *
     * @return fval
     */
    private float BMI() {
        float fval = getWeight() / (getHeight() * getHeight());
        return fval;
    }

    /**
     * This method get the weight value in int
     *
     * @return getWeight
     */
    private float getWeight() {
        EditText getWeight = (EditText) findViewById(R.id.weight);
        String value = getWeight.getText().toString();
        float finalValue = Float.parseFloat(value);
        return finalValue;
    }

    /**
     * This method get the height value in int
     *
     * @return getHeight
     */
    private float getHeight() {
        EditText getHeight = (EditText) findViewById(R.id.height);
        String value = getHeight.getText().toString();
        float finalValue = Float.parseFloat(value);
        return finalValue;
    }

    /**
     * This method check the BMI status from the result (BMI())
     */
    private void status() {
        float fval = getWeight() / (getHeight() * getHeight());
        TextView status = (TextView) findViewById(R.id.status);
        if (fval <= 18.5) {
            status.setText(R.string.underWeight);
        } else if (fval <= 24.9) {
            status.setText(R.string.normal);
        } else if (fval <= 29.9) {
            status.setText(R.string.overWeight);
        } else if (fval >= 30) {
            status.setText(R.string.obesity);
        }
    }

    /**
     * This method update the diet sugesstion according to result
     */
    private String dietSuggestion() {
        float fval = getWeight() / (getHeight() * getHeight());
        TextView dietPlan = (TextView) findViewById(R.id.diet_info);
        String s = dietPlan.getText().toString();
        if (Float.isNaN(fval)) {
            dietPlan.setText(" ");
        } else if (fval <= 18.5) {
            dietPlan.setText(R.string.text1);
        } else if (fval <= 24.9) {
            dietPlan.setText(R.string.text2);
        } else if (fval <= 29.9) {
            dietPlan.setText(R.string.text3);
        } else if (fval >= 30) {
            dietPlan.setText(R.string.text4);
        }
        return s;
    }

    /**
     * This method update the exercise according to result
     */
    private String exerciseSuggetion() {
        float fval = getWeight() / (getHeight() * getHeight());
        TextView dietPlan = (TextView) findViewById(R.id.exersie_tips);
        String v = dietPlan.getText().toString();
        if (Float.isNaN(fval)) {
            dietPlan.setText(" ");
        } else if (fval <= 18.5) {
            dietPlan.setText(R.string.exe_under);
        } else if (fval <= 24.9) {
            dietPlan.setText(R.string.exe_norm);
        } else if (fval <= 29.9) {
            dietPlan.setText(R.string.exe_overwt);
        } else if (fval >= 30) {
            dietPlan.setText(R.string.exe_obs);
        }
        return v;
    }

    /**
     * This method combine the dietSuggestion and exerciseSuggetion for email()
     *
     * @return v
     */
    private String suggestions() {
        String v = "Diet tips:\n" + dietSuggestion();
        v += "\n\nExercise tips\n" + exerciseSuggetion();
        return v;
    }

    /**
     * Method for email activity
     */
    private void email() {
        String addresses = "";
        String subject = "Exercise tips";
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, suggestions());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Method for creating calender event
     * The event description will be exerciseSuggestion()
     * This event shows the default availability is busy
     */
    private void calender(){
        String title = "Start Exercise";
        String description = exerciseSuggetion();
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, title)
                .putExtra(CalendarContract.Events.DESCRIPTION, description)
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
