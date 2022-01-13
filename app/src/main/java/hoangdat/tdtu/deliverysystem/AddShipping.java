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
            Toast.makeText(AddShipping.this,"Vui lòng nhập đầy đủ các thông tin cần thiết để tạo đơn hàng !", Toast.LENGTH_LONG).show();
        } else {

            DatabaseReference myRef = database.getReference("Quản Lý Đơn Hàng");
            String shipKey = myRef.push().getKey();
            ShipObject shipObject = new ShipObject(shipKey,"khachhang",customerName.getText().toString(),customerPhone.getText().toString(),customerAddress.getText().toString()
                    ,receiveName.getText().toString(),receivePhone.getText().toString(),receiveAddress.getText().toString(),spnType.getSelectedItem().toString(),spnMethod.getSelectedItem().toString(),"Đang chờ xử lý",shipNote.getText().toString());

            myRef.child("khachhang").child(shipKey).setValue(shipObject).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(AddShipping.this);
                    alert.setTitle("Yêu cầu tạo đơn hàng mới thành công !");
                    alert.setIcon(R.drawable.ic_baseline_assignment_turned_in_24);
                    alert.setMessage("Yêu cầu tạo đơn hàng của quý khách đã thành công ! Quý khách vui lòng chờ nhân viên chăm sóc KH cập nhật trình trạng đơn hàng !");
                    alert.setPositiveButton("Đã hiểu", new DialogInterface.OnClickListener() {
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