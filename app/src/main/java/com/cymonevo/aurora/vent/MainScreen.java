package com.cymonevo.aurora.vent;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cymonevo.aurora.vent.adapter.CategoryAdapter;
import com.cymonevo.aurora.vent.common.Setting;
import com.cymonevo.aurora.vent.constant.ClickHandler;
import com.cymonevo.aurora.vent.handler.ClickEventHandler;

public class MainScreen extends AppCompatActivity implements ClickEventHandler {
    private Snackbar snackbar;
    private View shadow;
    private String[] topic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.topic = getResources().getStringArray(R.array.category_topic);
        int theme = Setting.getCurrentTheme(this);
        setFlavour(theme);
        setContentView(R.layout.screen_main);
        setDrawer();
        personalize(theme);
    }

    private void setDrawer() {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView nav = findViewById(R.id.drawer);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                drawer.closeDrawers();
                return false;
            }
        });
    }

    private void setFlavour(int theme) {
        switch (theme) {
            case ClickHandler.CATEGORY_DEFAULT:
                setTheme(R.style.ThemeDefault);
                break;
            case ClickHandler.CATEGORY_LOVE:
                setTheme(R.style.ThemeLove);
                break;
        }
    }

    private void personalize(int theme) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        TextView text = new TextView(this);
        text.setText(topic[theme]);
        text.setTextSize(getResources().getDimension(R.dimen.xs_font));
        text.setTextColor(Color.WHITE);
        text.setTypeface(ResourcesCompat.getFont(this, R.font.handycheera));

        actionBar.setTitle("");
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(text);
    }

    private void showClickHandler() {
        CoordinatorLayout root = findViewById(R.id.fragment_container);
        View layout = LayoutInflater.from(this).inflate(R.layout.snackbar_category, null);
        snackbar = Snackbar.make(root, "", Snackbar.LENGTH_INDEFINITE);
        shadow = findViewById(R.id.shadow);
        shadow.setVisibility(View.VISIBLE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = layout.findViewById(R.id.category_recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new CategoryAdapter(this));

        snackbar.getView().setPadding(0,0,0,0);
        ((ViewGroup) snackbar.getView()).removeAllViews();
        ((ViewGroup) snackbar.getView()).addView(layout);
        snackbar.show();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (snackbar != null && snackbar.isShown()) {
                Rect rect = new Rect();
                snackbar.getView().getHitRect(rect);
                if (!rect.contains((int) ev.getX(), (int) ev.getY())) {
                    shadow.setVisibility(View.GONE);
                    snackbar.dismiss();
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_category:
                showClickHandler();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onClickEvent(int requestCode, int resultCode) {
        switch (requestCode) {
            case ClickHandler.CATEGORY_REQUEST:
                Setting.setCurrentTheme(getApplicationContext(), resultCode);
                snackbar.dismiss();
                recreate();
                break;
        }
    }
}
