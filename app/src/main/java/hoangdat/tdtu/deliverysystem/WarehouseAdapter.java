package hoangdat.tdtu.deliverysystem;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class WarehouseAdapter extends RecyclerView.Adapter<WarehouseAdapter.ViewHolder> {
    private List<ShipObject> lstShipping;
    private Context mContext;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    public WarehouseAdapter(List<ShipObject> lstShipping, Context mContext) {
        this.lstShipping = lstShipping;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public WarehouseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.handle_item,parent,false);
        WarehouseAdapter.ViewHolder holder = new WarehouseAdapter.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull WarehouseAdapter.ViewHolder holder, int position) {
        ShipObject shipObject = lstShipping.get(position);
        if (shipObject == null)
            return;

        holder.shipName.setText(shipObject.getShipType());
        holder.warehouseName.setText(shipObject.getUserName());
        holder.sendShipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendShipper(shipObject);
            }
        });
    }

    private void sendShipper(ShipObject shipObject)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
        alert.setTitle("Y??u c???u nh??n vi??n giao h??ng");
        alert.setIcon(R.drawable.ic_baseline_local_shipping_24);
        alert.setMessage("Thao t??c n??y s??? g???i ????n h??ng ?????n Shipper ????? th???c hi???n vi???c giao h??ng. B???n ch???c ch??? ?");
        alert.setNegativeButton("H???y", null);
        alert.setPositiveButton("Y??u c???u giao h??ng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myRef = database.getReference("Qu???n L?? ????n H??ng");
                DatabaseReference shipperRef = database.getReference("Qu???n L?? Giao H??ng");
                DatabaseReference warehouseRef = database.getReference("Qu???n L?? Kho");
                warehouseRef.child("khachhang").child(shipObject.getShipID()).removeValue();

                ShipObject shipTemp = shipObject;
                shipTemp.setShipStatus("???? chuy???n t???i nh??n vi??n giao h??ng");
                shipperRef.child("Shipper A").child(shipTemp.getShipID()).setValue(shipTemp);
                myRef.child("khachhang").child(shipObject.getShipID()).child("shipStatus").setValue("???? chuy???n t???i nh??n vi??n giao h??ng").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(mContext,"????n h??ng ???? ???????c chuy???n t???i Shipper th??nh c??ng !", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        alert.show();
    }
    @Override
    public int getItemCount() {
        return lstShipping.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView shipName, warehouseName;
        ImageView sendShipper;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shipName = itemView.findViewById(R.id.tvShipName_HandleItem);
            warehouseName = itemView.findViewById(R.id.tvWarehouseName_HandleItem);
            sendShipper = itemView.findViewById(R.id.imvSendShipper_HandleItem);
        }
    }
}
