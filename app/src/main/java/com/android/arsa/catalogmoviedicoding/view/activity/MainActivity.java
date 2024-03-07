package com.android.arsa.catalogmoviedicoding.view.activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import com.android.arsa.catalogmoviedicoding.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.android.arsa.catalogmoviedicoding.R;
import com.android.arsa.catalogmoviedicoding.view.adapter.PagerAdapter;
import com.android.arsa.catalogmoviedicoding.view.adapter.SearchAdapter;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding viewBinding;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());
        assert viewBinding.viewPager != null;
        viewPager = viewBinding.viewPager.pager;

        initComponent();
    }

    private void initComponent() {
        SearchAdapter adapter = new SearchAdapter(MainActivity.this);
        adapter.notifyDataSetChanged();
        setupToolbar();
        setupTabLayout();
        setupViewPager();
    }

    private void setupViewPager() {
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), viewBinding.tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(viewBinding.tabLayout));
    }

    private void setupTabLayout() {
        viewBinding.tabLayout.addTab(viewBinding.tabLayout.newTab().setText(getResources().getString(R.string.now_playing)));
        viewBinding.tabLayout.addTab(viewBinding.tabLayout.newTab().setText(getResources().getString(R.string.upcoming)));
//        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.favorite)));

        viewBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setupToolbar() {
        setSupportActionBar(viewBinding.toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(getResources().getString(R.string.toolbar_title));
        }
        viewBinding.toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        viewBinding.toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_sort));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Intent intentSearch = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intentSearch);
                break;

            case R.id.action_favorite:
                Intent favoriteMovie = new Intent(MainActivity.this, FavoriteActivity.class);
                startActivity(favoriteMovie);
                break;

            case R.id.action_change_lang:
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
