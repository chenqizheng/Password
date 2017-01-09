package com.yonyou.password.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.yonyou.password.R;
import com.yonyou.password.ui.lock.LockActivity;
import com.yonyou.password.ui.password.AddPassword;
import com.yonyou.password.ui.password.PasswordFragment;
import com.yonyou.password.ui.password.PasswordList;

import static com.yonyou.password.ui.password.PasswordFragment.REQUEST_CODE_ADD;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AboutFragment.OnFragmentInteractionListener, PasswordFragment.OnListFragmentInteractionListener {

    private Fragment mAboutFragment;
    private Fragment mPasswordFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LockActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD);
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mPasswordFragment = new PasswordFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_main, mPasswordFragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_ADD:
                mPasswordFragment.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (id == R.id.password) {
            if (mPasswordFragment != null) {
                fragmentTransaction.show(mPasswordFragment);
                if (mAboutFragment != null) {
                    fragmentTransaction.hide(mAboutFragment);
                }
            } else {
                mPasswordFragment = new PasswordFragment();
                fragmentTransaction.add(R.id.content_main, mPasswordFragment);
                fragmentTransaction.show(mPasswordFragment);
                fragmentTransaction.hide(mAboutFragment);
            }
        } else if (id == R.id.about) {
            if (mAboutFragment != null) {
                fragmentTransaction.show(mAboutFragment);
                fragmentTransaction.hide(mPasswordFragment);
            } else {
                mAboutFragment = new AboutFragment();
                fragmentTransaction.add(R.id.content_main, mAboutFragment);
                fragmentTransaction.show(mAboutFragment);
                fragmentTransaction.hide(mPasswordFragment);
            }
        }
        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(PasswordList.Password item) {

    }
}
