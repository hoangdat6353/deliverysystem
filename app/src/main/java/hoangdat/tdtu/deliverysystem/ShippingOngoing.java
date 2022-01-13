package hoangdat.tdtu.deliverysystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShippingOngoing extends AppCompatActivity {
    TextView shipType, receiverName;
    ProgressBar progressBar;
    ObjectAnimator objectAnimator;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_ongoing);

        mapping();
        Bundle vehBundle = getIntent().getExtras();
        if (vehBundle == null)
            return;
        ShipObject shipObject = (ShipObject) vehBundle.get("shipping_object_Shipper");
        showData(shipObject);

        handleProgressbar();
        objectAnimator.setDuration(5000);
        objectAnimator.start();
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                myRef = database.getReference("Quản Lý Đơn Hàng");
                DatabaseReference shipperRef = database.getReference("Quản Lý Giao Hàng");

                AlertDialog.Builder alert = new AlertDialog.Builder(ShippingOngoing.this);
                alert.setTitle("Cập nhật tình trạng giao hàng");
                alert.setIcon(R.drawable.ic_baseline_local_shipping_24);
                alert.setMessage("Vui lòng cập nhật tình trạng việc giao hàng !");
                alert.setNegativeButton("Giao hàng thất bại", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        shipperRef.child("Shipper A").child(shipObject.getShipID()).removeValue();
                        myRef.child("khachhang").child(shipObject.getShipID()).child("shipStatus").setValue("Giao hàng không thành công").addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(ShippingOngoing.this,"Cập nhật trạng thái giao hàng thành công!", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        });
                    }
                });
                alert.setPositiveButton("Giao hàng thành công", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        shipperRef.child("Shipper A").child(shipObject.getShipID()).removeValue();
                        myRef.child("khachhang").child(shipObject.getShipID()).child("shipStatus").setValue("Đã giao hàng").addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(ShippingOngoing.this,"Cập nhật trạng thái giao hàng thành công!", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        });
                    }
                });
                alert.show();
            }
        });

    }

    private void mapping()
    {
        shipType = findViewById(R.id.tvShipType_ShipOngoing);
        receiverName = findViewById(R.id.tvReceiverName_ShipOngoing);
        progressBar = findViewById(R.id.progressBar);
    }

    private void showData(ShipObject shipObject)
    {
        shipType.setText(shipObject.getShipType());
        receiverName.setText(shipObject.getReceiveName());
    }

    private void handleProgressbar()
    {
        objectAnimator = ObjectAnimator.ofInt(progressBar,"progress",0,100);
    }


}