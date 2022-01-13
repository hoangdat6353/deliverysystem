package hoangdat.tdtu.deliverysystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivityShipper extends AppCompatActivity {
    TextView totalShip;

    RecyclerView rcvShipper;
    List<ShipObject> lstShipping;
    List<String> mKeys = new ArrayList<>();
    ShipperAdapter adapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_shipper);
        totalShip = findViewById(R.id.tvTotalShip);
        rcvShipper = findViewById(R.id.rcvShipper);
        getData();
        rcvShipper.setLayoutManager(new LinearLayoutManager(MainActivityShipper.this));
        adapter = new ShipperAdapter(lstShipping,MainActivityShipper.this);
        rcvShipper.setAdapter(adapter);
    }

    private void getData()
    {
        lstShipping = new ArrayList<>();
        myRef = database.getReference("Quản Lý Giao Hàng").child("Shipper A");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ShipObject shipObject = snapshot.getValue(ShipObject.class);
                if (shipObject != null && shipObject.getShipStatus().equals("Đã chuyển tới nhân viên giao hàng")) {
                    lstShipping.add(shipObject);
                    String key = snapshot.getKey();
                    mKeys.add(key);
                    totalShip.setText(String.valueOf(lstShipping.size()));
                    adapter.notifyDataSetChanged();
                }
                if (lstShipping.isEmpty())
                {
                    totalShip.setText("0");
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ShipObject rmd = snapshot.getValue(ShipObject.class);
                if (rmd == null || lstShipping == null || lstShipping.isEmpty())
                {
                    totalShip.setText(String.valueOf(lstShipping.size()));
                    return;
                }
                int index = mKeys.indexOf(snapshot.getKey());
                lstShipping.set(index,rmd);
                adapter.notifyDataSetChanged();
                totalShip.setText(String.valueOf(lstShipping.size()));

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                ShipObject rmd = snapshot.getValue(ShipObject.class);
                if (rmd == null || lstShipping == null || lstShipping.isEmpty())
                {
                    totalShip.setText(String.valueOf(lstShipping.size()));
                    return;
                }
                int index = mKeys.indexOf(snapshot.getKey());
                if (index != -1)
                {
                    lstShipping.remove(index);
                    mKeys.remove(index);
                }
                adapter.notifyDataSetChanged();
                totalShip.setText(String.valueOf(lstShipping.size()));

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivityShipper.this);
        prefs.edit().clear().commit();
        Intent intent = new Intent(MainActivityShipper.this, LoginActivity.class);
        MainActivityShipper.this.startActivity(intent);

        return super.onOptionsItemSelected(item);
    }
}