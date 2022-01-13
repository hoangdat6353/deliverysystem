package hoangdat.tdtu.deliverysystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivityCSKH extends AppCompatActivity {
    RecyclerView rcvCSKH;
    List<ShipObject> lstShipping;
    List<ShipObject> lstTemp;
    List<String> mKeys = new ArrayList<>();
    CSKHAdapter adapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cskh);

        rcvCSKH = findViewById(R.id.rcvCSKH);
        getData();
        rcvCSKH.setLayoutManager(new LinearLayoutManager(MainActivityCSKH.this));
        adapter = new CSKHAdapter(lstShipping,MainActivityCSKH.this);
        rcvCSKH.setAdapter(adapter);
    }

    private void getData()
    {
        lstShipping = new ArrayList<>();
        myRef = database.getReference("Quản Lý Đơn Hàng").child("khachhang");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ShipObject shipObject = snapshot.getValue(ShipObject.class);
                if (shipObject != null && shipObject.getShipStatus().equals("Đang chờ xử lý"))
                {
                    String key = snapshot.getKey();
                    mKeys.add(key);
                    lstShipping.add(shipObject);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivityCSKH.this);
        prefs.edit().clear().commit();
        Intent intent = new Intent(MainActivityCSKH.this, LoginActivity.class);
        MainActivityCSKH.this.startActivity(intent);

        return super.onOptionsItemSelected(item);
    }
}