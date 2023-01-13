package com.shahin.drivesafe.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.shahin.drivesafe.Adapters.ScreenSlidePageFragment;
import com.shahin.drivesafe.Admin.Fragments.AHomeFragment;
import com.shahin.drivesafe.Admin.Fragments.ANotificationFragment;
import com.shahin.drivesafe.Admin.Fragments.AProfileFragment;
import com.shahin.drivesafe.Admin.Fragments.ASearchFragment;
import com.shahin.drivesafe.R;

import java.util.ArrayList;

public class AdminMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);


        ArrayList<Fragment> fragList = new ArrayList<>();
        fragList.add(new AHomeFragment());
        fragList.add(new ASearchFragment());
        fragList.add(new ANotificationFragment());
        fragList.add(new AProfileFragment());

        ScreenSlidePageFragment pagerAdapter = new ScreenSlidePageFragment(fragList, getSupportFragmentManager());

        final BubbleNavigationLinearView bubbleNavigationLinearView = findViewById(R.id.bottom_navigation_view1);
        bubbleNavigationLinearView.setTypeface(null);

        //   bubbleNavigationLinearView.setBackgroundColor(R.color.colorPrimary);

        bubbleNavigationLinearView.setBadgeValue(0, null);
        bubbleNavigationLinearView.setBadgeValue(1, null); //invisible badge
        bubbleNavigationLinearView.setBadgeValue(2, null);


        final ViewPager viewPager = findViewById(R.id.view_pager1);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                bubbleNavigationLinearView.setCurrentActiveItem(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        bubbleNavigationLinearView.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                viewPager.setCurrentItem(position, true);
            }
        });

    }
}
