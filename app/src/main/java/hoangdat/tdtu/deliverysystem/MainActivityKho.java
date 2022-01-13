package hoangdat.tdtu.deliverysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivityKho extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_kho);

        bottomNavigationView = findViewById(R.id.bottom_nav_view_kho);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container_kho,new Handling()).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.nav_view_handle:
                        fragment = new Handling();
                        break;
                    case R.id.nav_view_warehouse:
                        fragment = new Warehouse();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container_kho,fragment).commit();
                return true;
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivityKho.this);
        prefs.edit().clear().commit();
        Intent intent = new Intent(MainActivityKho.this, LoginActivity.class);
        MainActivityKho.this.startActivity(intent);

        return super.onOptionsItemSelected(item);
    }
}