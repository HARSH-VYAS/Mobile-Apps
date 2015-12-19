package com.example.harsh.mortgagecalculator;

import android.app.Fragment;
//import android.support.v4.app.Fragment;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.FrameLayout;

public class MainActivity extends FragmentActivity implements MainScreen.sendMessage {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    //    FrameLayout frame = new FrameLayout(this);
        //frame.setId(CONTENT_VIEW_ID);
      //  setContentView(frame, new FrameLayout.LayoutParams(
        //        FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        if (savedInstanceState == null) {
            Fragment newFragment = new MainScreen();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.homecontainer, newFragment).commit();
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public void onclickSendData(String hvalue, String loanAmount, String interestRate, String propertyTaxRate, String loanTerm, int month, int year) {

        Fragment Rfragment = new ResultFragment();
        Bundle bundle = new Bundle();

        bundle.putString("homeValue", hvalue);
        bundle.putString("loanAmoumt", loanAmount);
        bundle.putString("interestRate", interestRate);
        bundle.putString("loanTerm",loanTerm);
        bundle.putString("propertyTaxRate", propertyTaxRate);
        bundle.putInt("month", month);
        bundle.putInt("year",year);

        Rfragment.setArguments(bundle);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.resultcontainer, Rfragment);

        transaction.addToBackStack(null);
        transaction.commit();




    }
}
