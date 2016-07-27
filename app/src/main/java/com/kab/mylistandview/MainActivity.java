package com.kab.mylistandview;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    private Fragment mFragList;
    private Fragment mFragFullView;
    private FragmentTransaction mFTrans;
    private FrameLayout frameLayout2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        frameLayout2 = (FrameLayout) findViewById(R.id.frameLayout2);
        landscapeMode();
        mFragList = new ListFragment();

        if (savedInstanceState == null) {
        createFragmentList();
        } else {
            if (Utils.isTablet(getApplicationContext())&&Utils.getLandscapeOrientation(getApplicationContext())) {
                createFragmentList();
            }
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reloadFragment();
            }
        });
    }

    public void createFragmentList() {
        mFTrans = getFragmentManager().beginTransaction();
        mFTrans.replace(R.id.frameLayout1, mFragList, "MY_FRAGMENT_LIST");
        mFTrans.addToBackStack(null);
        mFTrans.commit();
    }

    public void reloadFragment() {
        if (fragmentAlive() == 1) {
            GetJSON getJSON = new GetJSON(getApplicationContext());
            getJSON.getJSONList();
        } else {
            if (FullViewFragment.sBitmap == null) {
                FullViewFragment fragment = (FullViewFragment) getFragmentManager().findFragmentByTag("MY_FRAGMENT_FULL");
                fragment.getImagesFromUrl();
            }
        }
    }

    public int fragmentAlive() {
        Fragment myFragment1 = getFragmentManager().findFragmentByTag("MY_FRAGMENT_LIST");
        Fragment myFragment2 = getFragmentManager().findFragmentByTag("MY_FRAGMENT_FULL");

        if ((myFragment1 != null && myFragment1.isVisible()) && (myFragment2 != null && myFragment2.isVisible())) {
            return 3;
        }

        if (myFragment1 != null && myFragment1.isVisible()) {
            return 1;
        }
        if (myFragment1 != null && myFragment1.isVisible()) {
            return 2;
        }


        return 0;
    }

    @Override
    public void onBackPressed() {
        if (fragmentAlive() == 3 || fragmentAlive() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    public void landscapeMode() {
        if (!Utils.isTablet(getApplicationContext())||!Utils.getLandscapeOrientation(getApplicationContext())) {
            frameLayout2.setVisibility(View.GONE);
        }
    }





}
