package com.example.harsh.mortgagecalculator;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ResultFragment extends Fragment {

    public ResultFragment() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_result, container, false);

        //View v = getActivity().findViewById(R.id.);

        Bundle bundle= getArguments();

        String homeValue = bundle.getString("homeValue");
        String loanAmount =bundle.getString("loanAmoumt");
        String interestRate=bundle.getString("interestRate");
        String propertyTaxRate=bundle.getString("propertyTaxRate");
        String term = bundle.getString("loanTerm");
        int month = bundle.getInt("month");
        int year = bundle.getInt("year");

        double principleAmount = Double.parseDouble(homeValue);
        double iRate = Double.parseDouble(interestRate);
        double downPayment= Double.parseDouble(loanAmount);
        double ptaxRate= Double.parseDouble(propertyTaxRate);
        double terms = Double.parseDouble(term);
        double rate=0;

        double totalMonths=0;
        double monthlyPayment=0;
        double totalInterest=0;

        double count=0;
        double MMP=0;
        double monthlyPropertyTax=0;
        double totalPropertyTax=0;
        double actualLoan=0;


        totalMonths=terms*12;

        totalPropertyTax=(principleAmount*ptaxRate*terms)/100;

        monthlyPropertyTax=totalPropertyTax/12;

        actualLoan=principleAmount-downPayment;

        rate= iRate/(1200);

        double ne = iRate+1;
        count= Math.pow(ne,totalMonths);

        MMP= (actualLoan*rate*count)/(count-1);

        monthlyPayment=MMP+monthlyPropertyTax;

        totalInterest= (monthlyPayment*totalMonths)-principleAmount-totalPropertyTax;



        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(Double.toString(monthlyPayment));

        TextView textView2 = (TextView) view.findViewById(R.id.textView2);
        textView2.setText(Double.toString(totalInterest));
        TextView textView3 = (TextView) view.findViewById(R.id.textView3);
        textView3.setText(Double.toString(totalPropertyTax));
        TextView textView4 = (TextView) view.findViewById(R.id.textView4);
        textView4.setText(Integer.toString(year+ ((int) terms)));

        return view;




    }


}
