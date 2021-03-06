package hoangdat.tdtu.deliverysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.util.Calendar;

public class AddShipping extends AppCompatActivity {
    EditText customerName, customerAddress,customerPhone,receiveName,receivePhone,receiveAddress,shipNote;
    Spinner spnType, spnMethod;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    Button btnAddShipping;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shipping);

        mapping();
        spinnerTypeShowed();
        spinnerMethodShowed();

        btnAddShipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addShipping();
            }
        });
    }

    private void mapping()
    {
        customerName = findViewById(R.id.etCustomerName_AddShipping);
        customerAddress = findViewById(R.id.etCustomerAddress_AddShipping);
        customerPhone = findViewById(R.id.etCustomerNumber_AddShipping);
        receiveName = findViewById(R.id.etCustomerName2_AddShipping);
        receivePhone = findViewById(R.id.etCustomerNumber2_AddShipping);
        receiveAddress = findViewById(R.id.etCustomerAddress2_AddShipping);
        shipNote = findViewById(R.id.etNote_AddShipping);
        spnType = findViewById(R.id.spnTypeShip);
        spnMethod = findViewById(R.id.spnTypeShipMethod);
        btnAddShipping = findViewById(R.id.btnAddShipping);
    }

    private void spinnerTypeShowed()
    {
        ArrayAdapter<String> spnTypeAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.strSpinnerType));
        spnTypeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnType.setAdapter(spnTypeAdapter);
    }

    private void spinnerMethodShowed()
    {
        ArrayAdapter<String> spnMethodAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.strSpinnerMethod));
        spnMethodAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnMethod.setAdapter(spnMethodAdapter);
    }



    private void addShipping()
    {
        if (customerName.getText().toString().isEmpty() || customerPhone.getText().toString().isEmpty() || customerAddress.getText().toString().isEmpty()
        || receiveName.getText().toString().isEmpty() || receivePhone.getText().toString().isEmpty() || receiveAddress.getText().toString().isEmpty()
        || shipNote.getText().toString().isEmpty() )
        {
            Toast.makeText(AddShipping.this,"Vui l??ng nh???p ?????y ????? c??c th??ng tin c???n thi???t ????? t???o ????n h??ng !", Toast.LENGTH_LONG).show();
        } else {

            DatabaseReference myRef = database.getReference("Qu???n L?? ????n H??ng");
            String shipKey = myRef.push().getKey();
            ShipObject shipObject = new ShipObject(shipKey,"khachhang",customerName.getText().toString(),customerPhone.getText().toString(),customerAddress.getText().toString()
                    ,receiveName.getText().toString(),receivePhone.getText().toString(),receiveAddress.getText().toString(),spnType.getSelectedItem().toString(),spnMethod.getSelectedItem().toString(),"??ang ch??? x??? l??",shipNote.getText().toString());

            myRef.child("khachhang").child(shipKey).setValue(shipObject).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(AddShipping.this);
                    alert.setTitle("Y??u c???u t???o ????n h??ng m???i th??nh c??ng !");
                    alert.setIcon(R.drawable.ic_baseline_assignment_turned_in_24);
                    alert.setMessage("Y??u c???u t???o ????n h??ng c???a qu?? kh??ch ???? th??nh c??ng ! Qu?? kh??ch vui l??ng ch??? nh??n vi??n ch??m s??c KH c???p nh???t tr??nh tr???ng ????n h??ng !");
                    alert.setPositiveButton("???? hi???u", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    alert.show();
                }
            });
        }

    }
}