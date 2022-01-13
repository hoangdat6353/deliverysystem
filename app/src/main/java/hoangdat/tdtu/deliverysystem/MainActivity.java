package hoangdat.tdtu.deliverysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_nav_view);

        //bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(1).setEnabled(false);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new Home()).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.nav_view_shipping:
                        fragment = new Shipping();
                        break;
                    case R.id.nav_view_more:
                        fragment = new More();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container,fragment).commit();
                return true;
            }
        });

        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveToAdd = new Intent(MainActivity.this, AddShipping.class);
                startActivity(moveToAdd);
            }
        });

    }
}