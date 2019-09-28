package com.ariemay.entertainmentlistmade3.views;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ariemay.entertainmentlistmade3.R;
import com.ariemay.entertainmentlistmade3.adapters.ViewAdapter;
import com.ariemay.entertainmentlistmade3.fragments.MoviesFragment;
import com.ariemay.entertainmentlistmade3.fragments.TVListFragment;

import java.lang.ref.WeakReference;

import static com.ariemay.entertainmentlistmade3.databases.DBContract.CONTENT_URI_MOVIE;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewAdapter adapter;

    private static HandlerThread handlerThread;
    private MoviesFragment.DataObserver myObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);
        String type = String.format(getResources().getString(R.string.movies));
        String type1 = String.format(getResources().getString(R.string.tv_show));
        String type2 = String.format(getResources().getString(R.string.favorite));
        adapter = new ViewAdapter(getSupportFragmentManager());

        adapter.AddFragment(new MoviesFragment(), "Movies");
        adapter.AddFragment(new TVListFragment(), "TV Show");
        adapter.AddFragment(new FavoriteActivity(), "Favorites");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.movie_icon);
        tabLayout.getTabAt(1).setIcon(R.drawable.tv_icon);
        tabLayout.getTabAt(2).setIcon(R.drawable.favorite_image);
        tabLayout.getTabAt(0).setText(type);
        tabLayout.getTabAt(1).setText(type1);
        tabLayout.getTabAt(2).setText(type2);
        setTitle("Entertainment List");

        handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        myObserver = new MoviesFragment.DataObserver(handler, this);
        getContentResolver().registerContentObserver(CONTENT_URI_MOVIE, true, myObserver);


        getContentResolver().query(CONTENT_URI_MOVIE, null, null, null, null);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case  R.id.action_change_setting:
                intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
                break;
            case R.id.alarm_setting:
                intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
