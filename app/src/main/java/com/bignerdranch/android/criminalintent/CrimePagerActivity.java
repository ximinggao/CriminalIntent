package com.bignerdranch.android.criminalintent;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;

import java.util.ArrayList;
import java.util.UUID;


public class CrimePagerActivity extends ActionBarActivity {
    private ViewPager viewPager;
    private ArrayList<Crime> crimes;

    @TargetApi(11)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (NavUtils.getParentActivityName(this) != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

        viewPager = new ViewPager(this);
        viewPager.setId(R.id.viewPager);
        setContentView(viewPager);

        crimes = CrimeLab.get(this).getCrimes();
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                Crime c = crimes.get(i);
                return CrimeFragment.newInstance(c.getId());
            }

            @Override
            public int getCount() {
                return crimes.size();
            }
        });

        UUID crimeId = (UUID)getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
        for (int i = 0; i < crimes.size(); i++) {
            if (crimes.get(i).getId().equals(crimeId)) {
                viewPager.setCurrentItem(i);
                String title = crimes.get(i).getTitle();
                if (title != null) {
                    setTitle(title);
                }
                break;
            }
        }

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                Crime c = crimes.get(i);
                if (c.getTitle() != null) {
                    setTitle(c.getTitle());
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
}
