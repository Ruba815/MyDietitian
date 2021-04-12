package com.appterm.mydietician;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Locale;

public class HomeClientActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_client);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        editor.putString("type","مستخدم");
        editor.commit();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.drawer_opened, R.string.drawer_closed);
        drawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();

        setTitle("الرئيسية");
        getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout,new HomeFragment()).commit();




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_client, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Configuration configuration = new Configuration();
        configuration.locale = new Locale("ar");
        getResources().updateConfiguration(configuration,getResources().getDisplayMetrics());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                setTitle("الرئيسية");
                getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout,new HomeFragment()).commit();
                break;
            case R.id.nav_Profile:
                setTitle("الملف الشخصي");
                getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout,new ClientProfileFragment()).commit();
                break;
            case R.id.nav_ComDietPlan:
                setTitle("الخطة الكاملة");
                getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout,new ClientPlanFragment()).commit();
                break;
            case R.id.nav_AltDietPlan:

                new AlertDialog.Builder(this)
                        .setTitle("تنبيه")
                        .setMessage("هل تريد عمل خطة بديلة؟")
                        .setCancelable(false)
                        .setNegativeButton("لا",null)
                        .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               Intent intent = new Intent(HomeClientActivity.this,ClientQuizOneActivity.class);
                               startActivity(intent);
                            }
                        }).show();
                break;
            case R.id.nav_Recipes:
                setTitle("وصفات صحية");
                getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout,new RecipesFragment()).commit();
                break;
            case R.id.nav_ScanBarCode:
                setTitle("مسح الباركود");
                getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout,new ScanBarcodeFragment()).commit();
                break;
            case R.id.nav_logOut:
                Toast.makeText(this, "nav_logOut", Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(this)
                        .setTitle("تنبيه")
                        .setMessage("هل تريد تسجيل الخروج")
                        .setCancelable(false)
                        .setNegativeButton("لا",null)
                        .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                editor.clear().commit();
                                Intent intent1 = new Intent(HomeClientActivity.this,RegisterAndLoginActivity.class);
                                startActivity(intent1);
                            }
                        }).show();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}