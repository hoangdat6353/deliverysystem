package hoangdat.tdtu.deliverysystem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.util.List;

public class ShippingAdapter extends RecyclerView.Adapter<ShippingAdapter.ViewHolder> {
    private List<ShipObject> lstShipping;
    private Context mContext;

    public ShippingAdapter(List<ShipObject> lstShipping, Context mContext) {
        this.lstShipping = lstShipping;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ShippingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shipping_item,parent,false);
        ShippingAdapter.ViewHolder holder = new ShippingAdapter.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShippingAdapter.ViewHolder holder, int position) {
        ShipObject shipObject = lstShipping.get(position);
        if (shipObject == null)
            return;

        holder.receiverName.setText(shipObject.getReceiveName());
        holder.shipType.setText(shipObject.getShipType());
        holder.shipMethod.setText(shipObject.getShipMethod());
        holder.imvCancelShipItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteShipping(shipObject);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDetails(shipObject);
            }
        });

        if (shipObject.getShipStatus().equals("??ang ch??? x??? l??"))
        {
            holder.shipItem.setBackgroundColor(Color.parseColor("#eded0e"));
        } else if (shipObject.getShipStatus().equals("???? giao h??ng"))
        {
            holder.shipItem.setBackgroundColor(Color.parseColor("#7bf55f"));
        } else if (shipObject.getShipStatus().equals("???? b??? t??? ch???i")) {
            holder.shipItem.setBackgroundColor(Color.parseColor("#f24933"));
        } else if (shipObject.getShipStatus().equals("???? chuy???n t???i nh??n vi??n kho") || (shipObject.getShipStatus().equals("???? ????ng g??i")))
        {
            holder.shipItem.setBackgroundColor(Color.parseColor("#f27500"));
        }  else if (shipObject.getShipStatus().equals("???? chuy???n t???i nh??n vi??n giao h??ng"))
        {
            holder.shipItem.setBackgroundColor(Color.parseColor("#f27500"));
        }  else
        {
            holder.shipItem.setBackgroundColor(Color.parseColor("#f24933"));
        }
    }
    private void onClickDetails(ShipObject shipObject)
    {
        Intent sendShippingInfo = new Intent(mContext, ShippingDetails.class);
        Bundle shipInfo = new Bundle();
        shipInfo.putSerializable("shipping_object", shipObject);
        sendShippingInfo.putExtras(shipInfo);
        mContext.startActivity(sendShippingInfo);
    }
    private void deleteShipping(ShipObject shipObject)
    {
        if (shipObject.getShipStatus().equals("???? ???????c giao"))
        {
            Toast.makeText(mContext,"Kh??ng th??? x??a ????n h??ng ???? ???????c giao !",Toast.LENGTH_LONG).show();
        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
            alert.setTitle("X??a ????n h??ng");
            alert.setIcon(R.drawable.ic_baseline_delete_forever_24);
            alert.setMessage("Thao t??c n??y s??? x??a ????n giao h??ng ???????c ch???n? B???n ch???c ch???n ch????");
            alert.setPositiveButton("C??", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Qu???n L?? ????n H??ng");
                    myRef.child("khachhang").child(shipObject.getShipID()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(mContext,"????n h??ng ???? ???????c x??a th??nh c??ng !", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(mContext,"C?? l???i x???y ra ! X??a ????n h??ng th???t b???i", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
            alert.setNegativeButton("Kh??ng", null);
            alert.show();
        }

    }
    @Override
    public int getItemCount() {
        return lstShipping.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView receiverName,shipType,shipMethod;
        ImageView imvCancelShipItem;
        ConstraintLayout shipItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            receiverName = itemView.findViewById(R.id.tvReceiverName_ShippingItem);
            shipType = itemView.findViewById(R.id.tvReceiverType_ShippingItem);
            shipMethod = itemView.findViewById(R.id.tvReceiverMethod_ShippingItem);
            imvCancelShipItem = itemView.findViewById(R.id.imvDeleteShip_ShippingItem);
            shipItem = itemView.findViewById(R.id.ship_item_layout);
        }
    }
}
