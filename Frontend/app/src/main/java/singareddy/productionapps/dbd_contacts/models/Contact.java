package singareddy.productionapps.dbd_contacts.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Contact {

    private Name nameData;
    private List<Address> addressData;
    private List<Phone> phoneData;
    private List<Date> dateData;

    public Contact(Name nameData, List<Address> addressData, List<Phone> phoneData, List<Date> dateData) {
        this.nameData = nameData;
        this.addressData = addressData;
        this.phoneData = phoneData;
        this.dateData = dateData;
    }

    public Name getNameData() {
        return nameData;
    }

    public void setNameData(Name nameData) {
        this.nameData = nameData;
    }

    public List<Address> getAddressData() {
        return addressData;
    }

    public void setAddressData(List<Address> addressData) {
        this.addressData = addressData;
    }

    public List<Phone> getPhoneData() {
        return phoneData;
    }

    public void setPhoneData(List<Phone> phoneData) {
        this.phoneData = phoneData;
    }

    public List<Date> getDateData() {
        return dateData;
    }

    public void setDateData(List<Date> dateData) {
        this.dateData = dateData;
    }
}
