package singareddy.productionapps.dbd_contacts.models;

public class Phone {
    private String phoneType;
    private Integer areaCode, number;
    private int contactId;
    private int _id;

    public Phone(String phoneType, Integer areaCode, Integer number) {
        this.phoneType = phoneType;
        this.areaCode = areaCode;
        this.number = number;
    }

    public Phone(String phoneType, Integer areaCode, Integer number, int contactId, int _id) {
        this.phoneType = phoneType;
        this.areaCode = areaCode;
        this.number = number;
        this.contactId = contactId;
        this._id = _id;
    }

    public Phone() {

    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public Integer getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(Integer areaCode) {
        this.areaCode = areaCode;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
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
