package hoangdat.tdtu.deliverysystem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class CSKHAdapter extends RecyclerView.Adapter<CSKHAdapter.ViewHolder> {
    private List<ShipObject> lstShipping;
    private Context mContext;

    public CSKHAdapter(List<ShipObject> lstShipping, Context mContext) {
        this.lstShipping = lstShipping;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CSKHAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cskh_item_row,parent,false);
        CSKHAdapter.ViewHolder holder = new CSKHAdapter.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CSKHAdapter.ViewHolder holder, int position) {
        ShipObject shipObject = lstShipping.get(position);
        if (shipObject == null)
            return;

        holder.userName.setText(shipObject.getCustomerName());
        holder.shipType.setText(shipObject.getShipType());
        holder.shipMethod.setText(shipObject.getShipMethod());
        holder.printShipOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printPdf(shipObject);
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
        Intent sendShippingInfo = new Intent(mContext, CSKHDetails.class);
        Bundle shipInfo = new Bundle();
        shipInfo.putSerializable("shipping_object_CSKH", shipObject);
        sendShippingInfo.putExtras(shipInfo);
        mContext.startActivity(sendShippingInfo);
    }
    private void printPdf(ShipObject shipObject)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
        alert.setTitle("Xu???t ????n h??ng ra file PDF");
        alert.setIcon(R.drawable.ic_baseline_print_24);
        alert.setMessage("Thao t??c n??y s??? xu???t th??ng tin c???a ????n h??ng n??y ra file PDF. B???n ch???c ch??? ?");
        alert.setNegativeButton("H???y", null);
        alert.setPositiveButton("In th??ng tin ????n h??ng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                exportToPdf(shipObject);
            }
        });
        alert.show();
    }
    private void exportToPdf(ShipObject shipObject)
    {
        PdfDocument myPdfDocument = new PdfDocument();
        Paint myPaint = new Paint();
        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(250,400,1).create();
        PdfDocument.Page myPage = myPdfDocument.startPage(myPageInfo);
        Canvas canvas = myPage.getCanvas();

        myPaint.setTextAlign(Paint.Align.CENTER);
        myPaint.setTextSize(12.0f);
        canvas.drawText("TH??NG TIN ????N H??NG",myPageInfo.getPageWidth()/2, 30,myPaint);
        myPaint.setTextSize(6.0f);
        myPaint.setColor(Color.rgb(122,119,119));
        canvas.drawText("Delivery System Demo App",myPageInfo.getPageWidth()/2,40,myPaint);

        myPaint.setTextAlign(Paint.Align.LEFT);
        myPaint.setTextSize(9.0f);
        canvas.drawText("Th??ng tin c??? th???: ",10,70, myPaint);

        myPaint.setTextAlign(Paint.Align.LEFT);
        myPaint.setTextSize(8.0f);
        myPaint.setColor(Color.BLACK);

        String[] listInformation = new String[]{"T??n t??i kho???n: " + shipObject.getUserName() ,"T??n ng?????i g???i: " + shipObject.getCustomerName(),
                "S??? ??i???n tho???i ng?????i g???i " + shipObject.getCustomerPhone(), "?????a ch??? ng?????i g???i: " + shipObject.getCustomerAddress(),
                "T??n ng?????i nh???n: " + shipObject.getReceiveName(),
                "S??? ??i???n tho???i ng?????i nh???n: " + shipObject.getReceivePhone(),
                "?????a ch??? ng?????i nh???n: " + shipObject.getReceiveAddress(),
                "Lo???i h??ng:" + shipObject.getShipType(),
                "Ph????ng th???c giao h??ng: " + shipObject.getShipMethod(),
                "Ghi ch??: " + shipObject.getShipNote()};
        int startXPosition = 10;
        int endXPosition = myPageInfo.getPageWidth() - 10;
        int startYPosition = 100;

        for (int i = 0; i <= 9; i++)
        {
            canvas.drawText(listInformation[i], startXPosition, startYPosition,myPaint);
            canvas.drawLine(startXPosition,startYPosition +3 ,endXPosition,startYPosition + 3,myPaint);
            startYPosition += 20;
        }

        myPdfDocument.finishPage(myPage);

        String fileName = "HoaDon_" + shipObject.getUserName() + ".pdf";
        File file = new File(Environment.getExternalStorageDirectory(), fileName);
        try {
            myPdfDocument.writeTo(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        myPdfDocument.close();

        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
        alert.setTitle("In th??ng tin ????n h??ng th??nh c??ng !");
        alert.setIcon(R.drawable.ic_baseline_print_24);
        alert.setMessage("Th??ng tin ????n h??ng ???? ???????c in th??nh c??ng ra t???p PDF. Vui l??ng ki???m tra l???i trong b??? nh??? c???a thi???t b??? !");
        alert.setPositiveButton("???? hi???u", null);
        alert.show();

    }
    @Override
    public int getItemCount() {
        return lstShipping.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView userName, shipType, shipMethod;
        ImageView printShipOrder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.tvReceiverName_CSKHItem);
            shipType = itemView.findViewById(R.id.tvReceiverType_CSKHItem);
            shipMethod = itemView.findViewById(R.id.tvReceiverMethod_CSKHItem);
            printShipOrder = itemView.findViewById(R.id.imvPrintShip_CSKHItem);
        }
    }
}
