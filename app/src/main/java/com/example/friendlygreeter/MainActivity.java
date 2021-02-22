package com.example.friendlygreeter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_help, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.helper:
                loadHelp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void bmiRechnen(View view) {
        EditText gewicht = findViewById(R.id.gewicht);
        EditText groesse = findViewById(R.id.groesse);
        groesse.clearFocus();
        gewicht.clearFocus();
        TextView output = findViewById(R.id.resultView);
        try {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            System.out.println("didnt work");
        }

        try {
            double gewichtDOUB = Double.parseDouble(gewicht.getText().toString());
            double groesseDOUB = Double.parseDouble(groesse.getText().toString());
            if(gewichtDOUB == 0||groesseDOUB == 0) showError(view);
            else {
                double result = (gewichtDOUB) / (Math.pow((groesseDOUB) / 100, 2));
                DecimalFormat f = new DecimalFormat("##.00");
                String additionalInfo = "";
                if (result > 30) additionalInfo = getString(R.string.message_fett);
                if (result <= 30 && result > 25)
                    additionalInfo = getString(R.string.message_leichtFett);
                if (result <= 25 && result > 19)
                    additionalInfo = getString(R.string.message_normal);
                if (result <= 19) additionalInfo = getString(R.string.message_untergewicht);
                String resultString = f.format(result) + "\n" + additionalInfo;
                output.setText(resultString);
            }
        }catch (Exception e){
            showError(view);
        }
    }

    private void showError(View view) {
        Snackbar mySnackbar = Snackbar.make(view, "fucking dunkey", 5000);
        mySnackbar.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.design_default_color_error));
        mySnackbar.show();
    }

    private void loadHelp(){
        //Intent intent = new Intent(this, OurGoodHelp.class);
        //intent.addCategory(Intent.CATEGORY_HOME);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //startActivity(intent);
        setContentView(R.layout.activity_our_good_help);
    }



}