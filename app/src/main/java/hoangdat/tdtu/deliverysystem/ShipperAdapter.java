package hoangdat.tdtu.deliverysystem;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShipperAdapter extends RecyclerView.Adapter<ShipperAdapter.ViewHolder> {
    private List<ShipObject> lstShipping;
    private Context mContext;

    public ShipperAdapter(List<ShipObject> lstShipping, Context mContext) {
        this.lstShipping = lstShipping;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ShipperAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shipper_item,parent,false);
        ShipperAdapter.ViewHolder holder = new ShipperAdapter.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShipperAdapter.ViewHolder holder, int position) {
        ShipObject shipObject = lstShipping.get(position);
        if (shipObject == null)
            return;

        holder.receiverAddress.setText(shipObject.getReceiveAddress());
        holder.receiverName.setText(shipObject.getReceiveName());
        holder.shipType.setText(shipObject.getShipType());

        holder.imvCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dial = "tel:" + shipObject.getReceivePhone();
                mContext.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDetails(shipObject);
            }
        });
    }

    private void onClickDetails(ShipObject shipObject)
    {
        Intent sendShippingInfo = new Intent(mContext, ShippingOngoing.class);
        Bundle shipInfo = new Bundle();
        shipInfo.putSerializable("shipping_object_Shipper", shipObject);
        sendShippingInfo.putExtras(shipInfo);
        mContext.startActivity(sendShippingInfo);
    }

    @Override
    public int getItemCount() {
        return lstShipping.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView shipType, receiverName, receiverAddress;
        ImageView imvCall;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            receiverName = itemView.findViewById(R.id.tvReceiverName_ShipperItem);
            shipType = itemView.findViewById(R.id.tvShipType_ShipperItem);
            receiverAddress = itemView.findViewById(R.id.tvReceiverAddress_ShipperItem);
            imvCall = itemView.findViewById(R.id.imvCall_ShipperItem);
        }
    }
}
