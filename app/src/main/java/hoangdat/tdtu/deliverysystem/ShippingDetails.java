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
        if (shipObject.getShipStatus().equals("??ang ch??? x??? l??"))
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(ShippingDetails.this);
            alert.setTitle("S???a th??ng tin ng?????i nh???n");
            alert.setIcon(R.drawable.ic_baseline_local_shipping_24);
            alert.setMessage("Thao t??c n??y s??? s???a th??ng tin c???a ng?????i nh???n. B???n ch???c ch??? ?");
            alert.setNegativeButton("H???y", null);
            alert.setPositiveButton("C???p nh???t", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    myRef = database.getReference("Qu???n L?? ????n H??ng");
                    shipObject.setReceiveName(edtName.getText().toString());
                    shipObject.setReceiveAddress(edtAddress.getText().toString());
                    shipObject.setReceivePhone(edtPhone.getText().toString());
                    shipObject.setShipNote(edtNote.getText().toString());
                    myRef.child("khachhang").child(shipObject.getShipID()).setValue(shipObject).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(ShippingDetails.this,"Th??ng tin ng?????i nh???n ???? ???????c c???p nh???t th??nh c??ng!", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });
                }
            });
            alert.show();
        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(ShippingDetails.this);
            alert.setTitle("S???a th??ng tin ng?????i nh???n");
            alert.setIcon(R.drawable.ic_baseline_local_shipping_24);
            alert.setMessage("B???n ch??? c?? th??? s???a th??ng tin ng?????i nh???n khi ????n h??ng ??ang ch??? x??? l??. Vui l??ng li??n h??? NV CSKH ????? ???????c h??? tr??? !");
            alert.setPositiveButton("???? hi???u",null);
            alert.show();
        }
    }
}