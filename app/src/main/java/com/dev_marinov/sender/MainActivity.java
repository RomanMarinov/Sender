package com.dev_marinov.sender;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ViewPagerAdapter viewPagerAdapter;
    List<ObjectTab> list = new ArrayList<>(); // массив для хранения объектов fragment
    LinearLayout ll_tab_1, ll_tab_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabLayout tabLayout = findViewById(R.id.tablayout);

        tabLayout.removeAllTabs();// удалить все закладки

        ViewPager2 viewPager2 = findViewById(R.id.vw_pager_2);

        // Таб отправить смс
        TabLayout.Tab new_Tab_1 = tabLayout.newTab();
        new_Tab_1.setText("sms");
        new_Tab_1.setTag("0");
        tabLayout.addTab(new_Tab_1);
        // Таб отправить email
        TabLayout.Tab new_Tab_2 = tabLayout.newTab();
        new_Tab_2.setText("email");
        new_Tab_2.setTag("1");
        tabLayout.addTab(new_Tab_2);

        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);

        // устанавливаем слушателя для tabLayout
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { //
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // если я кликаю на закладку я получаю из тега ноль или еденицу
                viewPager2.setCurrentItem(Integer.parseInt(tab.getTag().toString()), true);
                String num_tab = tab.getTag().toString();
                Log.e("MAIN_ACT","-num_tab-" + num_tab);

            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                Log.e("frag_fin","-position-" + position);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Log.e("frag_fin","-position-" + position);
                tabLayout.getTabAt(position).select(); //
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

    }
        @Override
        public void onBackPressed() {
            //super.onBackPressed();
            myAlertDialog();
        }


    public void myAlertDialog()
    {
        AlertDialog.Builder alertbox = new AlertDialog.Builder(MainActivity.this);
        alertbox.setTitle("Do you wish to exit ?");
        alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                // finish used for destroyed activity
                finish();
            }
        });

        alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                // Nothing will be happened when clicked on no button
                // of Dialog
            }
        });
        alertbox.show();
    }

}