package com.shahin.drivesafe.Driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.onesignal.OneSignal;
import com.shahin.drivesafe.Adapters.ScreenSlidePageFragment;
import com.shahin.drivesafe.Driver.Fragment.DHomeFragment;
import com.shahin.drivesafe.Driver.Fragment.DNotificationFragment;
import com.shahin.drivesafe.Driver.Fragment.DProfileFragment;
import com.shahin.drivesafe.Driver.Fragment.DSearchFragment;
import com.shahin.drivesafe.R;

import java.util.ArrayList;

public class MainDriverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
        SharedPreferences sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);

        boolean swmute = sharedPreferences.getBoolean("mute", false);

        if (swmute) {
            OneSignal.setSubscription(false);
        } else {
            OneSignal.setSubscription(true);
        }

        ///////////////////////////////////////////////////////


        ArrayList<Fragment> fragList = new ArrayList<>();
        fragList.add(new DHomeFragment());
        fragList.add(new DSearchFragment());
        fragList.add(new DNotificationFragment());
        fragList.add(new DProfileFragment());

        ScreenSlidePageFragment pagerAdapter = new ScreenSlidePageFragment(fragList, getSupportFragmentManager());

        final BubbleNavigationLinearView bubbleNavigationLinearView = findViewById(R.id.bottom_navigation_view_linear2);
        bubbleNavigationLinearView.setTypeface(null);

        //   bubbleNavigationLinearView.setBackgroundColor(R.color.colorPrimary);

        bubbleNavigationLinearView.setBadgeValue(0, null);
        bubbleNavigationLinearView.setBadgeValue(1, null); //invisible badge
        bubbleNavigationLinearView.setBadgeValue(2, null);


        final ViewPager viewPager = findViewById(R.id.view_pager2);
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
