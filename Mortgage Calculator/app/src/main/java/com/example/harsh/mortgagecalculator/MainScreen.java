package com.example.harsh.mortgagecalculator;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
//import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class MainScreen extends Fragment {
    sendMessage sm;


    public MainScreen() {
        // Required empty public constructor
    }

    public interface sendMessage{

       public void onclickSendData(String hvalue, String loanAmount, String interestRate, String propertyTaxRate, String loanTerm, int month, int year);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        sm = (sendMessage) getActivity();

        View view = inflater.inflate(R.layout.home_screen, container, false);
        Button btnCalculate = (Button) view.findViewById(R.id.btnCalculate);
        Button btnReset = (Button) view.findViewById(R.id.btnReset);
        DatePicker datePicker= (DatePicker) view.findViewById(R.id.datepicker);
        final int month =  datePicker.getMonth();
        final int year = datePicker.getYear();

        final EditText homeValue = (EditText) view.findViewById(R.id.homeValue);
        final EditText loanAmount = (EditText) view.findViewById(R.id.loanAmount);
        final EditText interestRate = (EditText) view.findViewById(R.id.interestRate);
        final EditText propertyTaxRate = (EditText) view.findViewById(R.id.propertyTaxRate);
        final Spinner loanTerm = (Spinner) view.findViewById(R.id.loanTerm);

        Integer [] terms = new Integer[]{15,20,25,30,40};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getActivity(),android.R.layout.simple_spinner_item,terms);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        loanTerm.setAdapter(adapter);

        btnCalculate.setOnClickListener(new View.OnClickListener() {

            String loanterm = loanTerm.getSelectedItem().toString();
            Fragment fragment = new ResultFragment();

            @Override
            public void onClick(View v) {

                if (homeValue.getText().toString().trim().equals("")) {
                    homeValue.setError("HomeValue Required");
                } else if (interestRate.getText().toString().trim().equals("")) {
                    interestRate.setError("Interest Rate Required");

                }
                else if (propertyTaxRate.getText().toString().trim().equals("")) {
                    propertyTaxRate.setError("Interest Rate Required");

                }
                else {

                    sm.onclickSendData(homeValue.getText().toString(), loanAmount.getText().toString(), interestRate.getText().toString(), propertyTaxRate.getText().toString(), loanterm, month, year);
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                homeValue.setText("");
                propertyTaxRate.setText("");
                interestRate.setText("");
                loanAmount.setText("");
                Integer [] terms = new Integer[]{15,20,25,30,40};
                ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getActivity(),android.R.layout.simple_spinner_item,terms);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                loanTerm.setAdapter(adapter);




            }
        });

        return view;

    }





}
