package com.kab.mylistandview;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private Fragment mFragList;
    private Fragment mFragFullView;
    private FragmentTransaction mFTrans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFragList = new ListFragment();

        if (savedInstanceState == null) {
            createFragmentList();
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               GetJSON getJSON = new GetJSON(getApplicationContext());
                getJSON.getJSONList();
            }
        });
    }

    public void createFragmentList() {
        mFTrans = getFragmentManager().beginTransaction();
        mFTrans.replace(R.id.frameLayout1, mFragList, "MY_FRAGMENT_LIST");
        mFTrans.addToBackStack(null);
        mFTrans.commit();
    }

}
