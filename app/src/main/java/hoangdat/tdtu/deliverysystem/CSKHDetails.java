package hoangdat.tdtu.deliverysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CSKHDetails extends AppCompatActivity {
    TextView customerName, customerAddress,customerPhone,receiveName,receivePhone,receiveAddress,shipNote,shipType;
    ImageView imvDecline, imvApprove;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cskhdetails);

        mapping();
        Bundle vehBundle = getIntent().getExtras();
        if (vehBundle == null)
            return;
        ShipObject shipObject = (ShipObject) vehBundle.get("shipping_object_CSKH");
        showData(shipObject);

        imvDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                declineRequest(shipObject);
            }
        });

        imvApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptRequest(shipObject);
            }
        });
    }

    private void mapping()
    {
        customerName = findViewById(R.id.tvCustomerName_CSKHDetails);
        customerAddress = findViewById(R.id.tvCustomerAddress_CSKHDetails);
        customerPhone = findViewById(R.id.tvCustomerPhone_CSKHDetails);
        receiveName = findViewById(R.id.tvReceiverName_CSKHDetails);
        receivePhone = findViewById(R.id.tvReceiverPhone_CSKHDetails);
        receiveAddress = findViewById(R.id.tvReceiverAddress_CSKHDetails);
        shipNote = findViewById(R.id.tvNote_CSKHDetails);
        shipType = findViewById(R.id.tvShipType_CSKHDetails);
        imvDecline = findViewById(R.id.btnDecline);
        imvApprove = findViewById(R.id.btnApprove);
    }

    private void showData(ShipObject shipObject)
    {
        customerName.setText(shipObject.getCustomerName());
        customerAddress.setText(shipObject.getCustomerAddress());
        customerPhone.setText(shipObject.getCustomerPhone());
        receiveName.setText(shipObject.getReceiveName());
        receiveAddress.setText(shipObject.getReceiveAddress());
        receivePhone.setText(shipObject.getReceivePhone());

        shipNote.setText(shipObject.getShipNote());
        shipType.setText(shipObject.getShipType());
    }

    private void declineRequest(ShipObject shipObject)
    {
        myRef = database.getReference("Qu???n L?? ????n H??ng");

        AlertDialog.Builder alert = new AlertDialog.Builder(CSKHDetails.this);
        alert.setTitle("C???p nh???t t??nh tr???ng ????n h??ng");
        alert.setIcon(R.drawable.ic_baseline_info_24);
        alert.setMessage("Thao t??c n??y s??? t??? ch???i y??u c???u c???a ????n h??ng n??y. B???n ch???c ch??? ?");
        alert.setNegativeButton("H???y", null);
        alert.setPositiveButton("T??? ch???i y??u c???u", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myRef.child("khachhang").child(shipObject.getShipID()).child("shipStatus").setValue("???? b??? t??? ch???i").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(CSKHDetails.this,"????n h??ng ???? b??? t??? ch???i th??nh c??ng !", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
            }
        });
        alert.show();
    }

    private void acceptRequest(ShipObject shipObject)
    {
        myRef = database.getReference("Qu???n L?? ????n H??ng");

        AlertDialog.Builder alert = new AlertDialog.Builder(CSKHDetails.this);
        alert.setTitle("C???p nh???t t??nh tr???ng ????n h??ng");
        alert.setIcon(R.drawable.ic_baseline_info_24);
        alert.setMessage("Thao t??c n??y s??? ch???p thu???n y??u c???u c???a ????n h??ng n??y v?? g???i ?????n nh??n vi??n kho x??? l??. B???n ch???c ch??? ?");
        alert.setNegativeButton("H???y", null);
        alert.setPositiveButton("Ch???p thu???n y??u c???u", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myRef.child("khachhang").child(shipObject.getShipID()).child("shipStatus").setValue("???? chuy???n t???i nh??n vi??n kho").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(CSKHDetails.this,"????n h??ng ???? ???????c ch???p thu???n th??nh c??ng !", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
            }
        });
        alert.show();
    }
}