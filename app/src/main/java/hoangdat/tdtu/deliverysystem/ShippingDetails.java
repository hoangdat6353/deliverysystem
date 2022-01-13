package hoangdat.tdtu.deliverysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShippingDetails extends AppCompatActivity {
    TextView tvShipType, tvShipStatus;
    EditText edtName, edtAddress,edtPhone,edtNote;
    Button btnChange;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_details);

        mapping();

        Bundle vehBundle = getIntent().getExtras();
        if (vehBundle == null)
            return;
        ShipObject shipObject = (ShipObject) vehBundle.get("shipping_object");
        tvShipType.setText(shipObject.getShipType().toString());
        tvShipStatus.setText(shipObject.getShipStatus());
        edtName.setText(shipObject.getReceiveName());
        edtAddress.setText(shipObject.getReceiveAddress());
        edtPhone.setText(shipObject.getReceivePhone());
        edtNote.setText(shipObject.getShipNote());

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeInformation(shipObject);
            }
        });
    }

    private void mapping()
    {
        tvShipType = findViewById(R.id.tvShipType_ShippingDetails);
        tvShipStatus = findViewById(R.id.tvStatus_ShippingDetails);
        edtName = findViewById(R.id.etReceiverName_ShippingDetails);
        edtAddress = findViewById(R.id.etReceiverAddress_ShippingDetails);
        edtPhone = findViewById(R.id.etReceiverPhone_ShippingDetails);
        edtNote = findViewById(R.id.etNote_ShippingDetails);
        btnChange = findViewById(R.id.btnChangeInformation);
    }

    private void changeInformation(ShipObject shipObject)
    {
        if (shipObject.getShipStatus().equals("Đang chờ xử lý"))
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(ShippingDetails.this);
            alert.setTitle("Sửa thông tin người nhận");
            alert.setIcon(R.drawable.ic_baseline_local_shipping_24);
            alert.setMessage("Thao tác này sẽ sửa thông tin của người nhận. Bạn chắc chứ ?");
            alert.setNegativeButton("Hủy", null);
            alert.setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    myRef = database.getReference("Quản Lý Đơn Hàng");
                    shipObject.setReceiveName(edtName.getText().toString());
                    shipObject.setReceiveAddress(edtAddress.getText().toString());
                    shipObject.setReceivePhone(edtPhone.getText().toString());
                    shipObject.setShipNote(edtNote.getText().toString());
                    myRef.child("khachhang").child(shipObject.getShipID()).setValue(shipObject).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(ShippingDetails.this,"Thông tin người nhận đã được cập nhật thành công!", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });
                }
            });
            alert.show();
        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(ShippingDetails.this);
            alert.setTitle("Sửa thông tin người nhận");
            alert.setIcon(R.drawable.ic_baseline_local_shipping_24);
            alert.setMessage("Bạn chỉ có thể sửa thông tin người nhận khi đơn hàng đang chờ xử lý. Vui lòng liên hệ NV CSKH để được hỗ trợ !");
            alert.setPositiveButton("Đã hiểu",null);
            alert.show();
        }
    }
}