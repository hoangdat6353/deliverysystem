package hoangdat.tdtu.deliverysystem;

import java.io.Serializable;

public class ShipObject implements Serializable {
    private String shipID;
    private String userName;
    private String customerName;
    private String customerPhone;
    private String customerAddress;
    private String receiveName;
    private String receivePhone;
    private String receiveAddress;
    private String shipType;
    private String shipMethod;
    private String shipStatus;
    private String shipNote;

    public ShipObject()
    {
    }

    public String getShipStatus() {
        return shipStatus;
    }

    public void setShipStatus(String shipStatus) {
        this.shipStatus = shipStatus;
    }

    public String getShipNote() {
        return shipNote;
    }

    public void setShipNote(String shipNote) {
        this.shipNote = shipNote;
    }

    public ShipObject(String shipID, String userName, String customerName, String customerPhone, String customerAddress, String receiveName, String receivePhone, String receiveAddress, String shipType, String shipMethod, String shipStatus, String shipNote) {
        this.shipID = shipID;
        this.userName = userName;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerAddress = customerAddress;
        this.receiveName = receiveName;
        this.receivePhone = receivePhone;
        this.receiveAddress = receiveAddress;
        this.shipType = shipType;
        this.shipMethod = shipMethod;
        this.shipStatus = shipStatus;
        this.shipNote = shipNote;
    }

    public String getShipID() {
        return shipID;
    }

    public void setShipID(String shipID) {
        this.shipID = shipID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getShipType() {
        return shipType;
    }

    public void setShipType(String shipType) {
        this.shipType = shipType;
    }

    public String getShipMethod() {
        return shipMethod;
    }

    public void setShipMethod(String shipMethod) {
        this.shipMethod = shipMethod;
    }
}
