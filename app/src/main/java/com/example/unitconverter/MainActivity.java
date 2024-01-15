package com.example.unitconverter;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Spinner fromSpinner, toSpinner;
    Button convertButton;
    String check[] = {"Metre", "Kilometre", "Centimetre", "Micrometre", "Millimetre"};
    EditText editTextFrom, editTextTo;
    String[] units = {"Metre", "Kilometre", "Centimetre", "Millimetre", "Micrometre", "Kilogram", "Gram"};
    private static final double METER_TO_KILOMETER = 0.001;
    private static final double KILOMETER_TO_METER = 1000.0;
    private static final double METER_TO_CENTIMETER = 100.0;
    private static final double CENTIMETER_TO_METER = 0.01;
    private static final double METER_TO_MILLIMETER = 1000.0;
    private static final double MILLIMETER_TO_METER = 0.001;
    private static final double METER_TO_MICROMETER = 1_000_000.0;
    private static final double MICROMETER_TO_METER = 1e-6;
    private static final double KILOGRAM_TO_GRAM = 1000;
    private static final double GRAM_TO_KILOGRAM = 0.001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, units);

        fromSpinner.setAdapter(spinnerAdapter);
        toSpinner.setAdapter(spinnerAdapter);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndConvert();
            }
        });
    }

    private void initializeViews() {
        fromSpinner = findViewById(R.id.item_list1);
        toSpinner = findViewById(R.id.item_list2);
        convertButton = findViewById(R.id.convert_btn);
        editTextFrom = findViewById(R.id.edt1);
        editTextTo = findViewById(R.id.edt2);
    }

    private void checkAndConvert() {
        String fromUnit = fromSpinner.getSelectedItem().toString();
        String toUnit = toSpinner.getSelectedItem().toString();

        if (isInvalidChoice(fromUnit, toUnit)==2) {
            Toast.makeText(this, "Invalid choice", Toast.LENGTH_SHORT).show();
            return;
        }

        String userInput = editTextFrom.getText().toString();
        if (!userInput.isEmpty()) {
            try {
                double num = Double.parseDouble(userInput);
                double result = convertUnit(num, fromUnit, toUnit);
                editTextTo.setText(String.valueOf(result));
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show();
        }
    }

    private int isInvalidChoice(String fromUnit, String toUnit) {
        int choice = 1;

        for (int i = 0; i < check.length; i++) {

            if ((fromUnit.equals(check[i]) && (toUnit.equals("Kilogram") || toUnit.equals("Gram"))) ||
                    (toUnit.equals(check[i]) && (fromUnit.equals("Kilogram") || fromUnit.equals("Gram")))) {
                choice = 2;
            }

        }
        return choice;
    }

    private double convertUnit ( double value, String fromUnit, String toUnit){
        double result = value;

        switch (fromUnit) {
            case "kilometre":
                result *= KILOMETER_TO_METER;
                break;
            case "Centimetre":
                result *= CENTIMETER_TO_METER;
                break;
            case "Millimetre":
                result *= MILLIMETER_TO_METER;
                break;
            case "Micrometre":
                result *= MICROMETER_TO_METER;
                break;
            case "Kilogram":
                result *= KILOGRAM_TO_GRAM;
                break;
        }

        switch (toUnit) {
            case "kilometre":
                result *= METER_TO_KILOMETER;
                break;
            case "Centimetre":
                result *= METER_TO_CENTIMETER;
                break;
            case "Millimetre":
                result *= METER_TO_MILLIMETER;
                break;
            case "Micrometre":
                result *= METER_TO_MICROMETER;
                break;
            case "Kilogram":
                result *= GRAM_TO_KILOGRAM;
                break;
        }

        return result;
    }
}

