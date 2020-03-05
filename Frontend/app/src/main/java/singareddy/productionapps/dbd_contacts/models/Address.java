package singareddy.productionapps.dbd_contacts.models;

public class Address {
    private String addressType, address, city, state;
    private Integer zipcode;
    private int contactId;
    private int _id;

    public Address(String addressType, String address, String city, String state, Integer zipcode) {
        this.addressType = addressType;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
    }

    public Address(String addressType, String address, String city, String state, Integer zipcode, int contactId, int _id) {
        this.addressType = addressType;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.contactId = contactId;
        this._id = _id;
    }

    public Address() {

    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getZipcode() {
        return zipcode;
    }

    public void setZipcode(Integer zipcode) {
        this.zipcode = zipcode;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
