package com.example.jonathan.personalcontrol;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    private static final long DRAWER_CLOSE_DELAY_MS = 250;
    private static final String NAV_ITEM_ID = "navItemId";

    private final Handler mDrawerActionHandler = new Handler();

    private DrawerLayout mDrawerLayout;
    private FrameLayout mContent;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private int mNavItemId;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mContent = (FrameLayout) findViewById(R.id.content);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(Color.argb(255, 57, 57, 57));
        setSupportActionBar(mToolbar);

        // load saved navigation state if present
        if (null != savedInstanceState) {
            mNavItemId = savedInstanceState.getInt(NAV_ITEM_ID);
        }

        if(mNavItemId == 0)
            mNavItemId = R.id.item_perfil;

        // listen for navigation events
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);

        // select the correct nav menu item
        navigationView.getMenu().findItem(mNavItemId).setChecked(true);

        // set up the hamburger icon to open and close the drawer
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open,
                R.string.close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        navigate(mNavItemId);
    }

    public void onClickSalvar(View view) {
        Toast.makeText(this, "Salvar...", Toast.LENGTH_SHORT).show();
    }

    private void navigate(final int itemId) {
        switch (itemId) {
            case R.id.item_perfil:
                mToolbar.setTitle("Perfil");
                mContent.removeAllViews();
                mContent.addView(new PerfilView(this));
                break;
            case R.id.item_exercicios:
                mToolbar.setTitle("Exercícios");
                mContent.removeAllViews();
                mContent.addView(new ExerciciosView(this));
                break;
            case R.id.item_relatorios:
                mToolbar.setTitle("Relatórios");
                mContent.removeAllViews();
                mContent.addView(new RelatoriosView(this));
                break;
            case R.id.item_atividades:
                mToolbar.setTitle("Atividades");
                mContent.removeAllViews();
                mContent.addView(new AtividadesView(this));
                break;
            case R.id.item_configuracoes:
                mToolbar.setTitle("Configurações");
                mContent.removeAllViews();
                mContent.addView(new ConfiguracoesView(this));
                break;
        }
        // perform the actual navigation logic, updating the main content fragment etc
    }

    @Override
    public boolean onNavigationItemSelected(final MenuItem menuItem) {
        // update highlighted item in the navigation menu
        menuItem.setChecked(true);
        mNavItemId = menuItem.getItemId();

        // allow some time after closing the drawer before performing real navigation
        // so the user can see what is happening
        mDrawerLayout.closeDrawer(GravityCompat.START);
        mDrawerActionHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                navigate(menuItem.getItemId());
            }
        }, DRAWER_CLOSE_DELAY_MS);
        return true;
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == android.support.v7.appcompat.R.id.home) {
            return mDrawerToggle.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(NAV_ITEM_ID, mNavItemId);
    }
}