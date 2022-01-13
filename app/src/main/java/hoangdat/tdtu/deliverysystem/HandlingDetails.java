package hoangdat.tdtu.deliverysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HandlingDetails extends AppCompatActivity {
    TextView shipType, receiverName, receiverAddress, receiverPhone, shipMethod;
    Spinner spnWarehouse;
    ShipObject shipTemp;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handling_details);

        mapping();
        Bundle vehBundle = getIntent().getExtras();
        if (vehBundle == null)
            return;
        ShipObject shipObject = (ShipObject) vehBundle.get("shipping_object_Handling");
        shipTemp = shipObject;

        showData(shipObject);
        spnWarehouseShow();
    }
    private void mapping()
    {
        shipType = findViewById(R.id.tvShipType_HandlingDetails);
        receiverName = findViewById(R.id.tvReceiverName_HandlingDetails);
        receiverAddress = findViewById(R.id.tvReceiverAddress_HandlingDetails);
        receiverPhone = findViewById(R.id.tvReceiverPhone_HandlingDetails);
        shipMethod = findViewById(R.id.tvMethod_HandlingDetails);
        spnWarehouse = findViewById(R.id.spnWarehouse);
    }

    private void showData(ShipObject shipObject)
    {
        shipType.setText(shipObject.getShipType());
        receiverName.setText(shipObject.getReceiveName());
        receiverAddress.setText(shipObject.getReceiveAddress());
        receiverPhone.setText(shipObject.getReceivePhone());
        shipMethod.setText(shipObject.getShipMethod());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        DatabaseReference donhangRef = database.getReference("Quản Lý Đơn Hàng");
        DatabaseReference myRef = database.getReference("Quản Lý Kho");

        AlertDialog.Builder alert = new AlertDialog.Builder(HandlingDetails.this);
        alert.setTitle("Đưa hàng vào kho");
        alert.setIcon(R.drawable.ic_baseline_info_24);
        alert.setMessage("Thao tác này sẽ đưa hàng vào kho đã được chọn. Bạn chắc chứ ?");
        alert.setNegativeButton("Hủy", null);
        alert.setPositiveButton("Chuyển vào:" + spnWarehouse.getSelectedItem().toString(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                donhangRef.child("khachhang").child(shipTemp.getShipID()).child("userName").setValue(spnWarehouse.getSelectedItem().toString());
                donhangRef.child("khachhang").child(shipTemp.getShipID()).child("shipStatus").setValue("Đã đóng gói");

                shipTemp.setUserName(spnWarehouse.getSelectedItem().toString());
                shipTemp.setShipStatus("Đã đóng gói");
                myRef.child("khachhang").child(shipTemp.getShipID()).setValue(shipTemp).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(HandlingDetails.this,"Yêu cầu chuyển đơn hàng vào kho thành công !", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
            }
        });
        alert.show();
        return super.onOptionsItemSelected(item);
    }

    private void spnWarehouseShow()
    {
        ArrayAdapter<String> spnMethodAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.strWarehouseList));
        spnMethodAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnWarehouse.setAdapter(spnMethodAdapter);
    }

}