package hoangdat.tdtu.deliverysystem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HandlingAdapter extends RecyclerView.Adapter<HandlingAdapter.ViewHolder> {
    private List<ShipObject> lstShipping;
    private Context mContext;

    public HandlingAdapter(List<ShipObject> lstShipping, Context mContext) {
        this.lstShipping = lstShipping;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public HandlingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.warehouse_item,parent,false);
        HandlingAdapter.ViewHolder holder = new HandlingAdapter.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HandlingAdapter.ViewHolder holder, int position) {
        ShipObject shipObject = lstShipping.get(position);
        if (shipObject == null)
            return;

        holder.receiverName.setText(shipObject.getReceiveName());
        holder.receiverAddress.setText(shipObject.getReceiveAddress());
        holder.shipType.setText(shipObject.getShipType());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDetails(shipObject);
            }
        });
    }
    private void onClickDetails(ShipObject shipObject)
    {
        Intent sendShippingInfo = new Intent(mContext, HandlingDetails.class);
        Bundle shipInfo = new Bundle();
        shipInfo.putSerializable("shipping_object_Handling", shipObject);
        sendShippingInfo.putExtras(shipInfo);
        mContext.startActivity(sendShippingInfo);
    }

    @Override
    public int getItemCount() {
        return lstShipping.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView receiverName, receiverAddress,shipType;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            receiverName = itemView.findViewById(R.id.tvReceiverName_WarehouseItem);
            receiverAddress = itemView.findViewById(R.id.tvReceiverAddress_WarehouseItem);
            shipType = itemView.findViewById(R.id.tvReceiverMethod_WarehouseItem);
        }
    }
}
