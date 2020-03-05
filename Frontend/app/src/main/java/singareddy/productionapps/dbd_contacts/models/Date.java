package singareddy.productionapps.dbd_contacts.models;

public class Date {
    private String dateType;
    private java.util.Date date;
    private int contactId;
    private int _id;

    public Date(String dateType, java.util.Date date) {
        this.dateType = dateType;
        this.date = date;
    }

    public Date(String dateType, java.util.Date date, int contactId, int _id) {
        this.dateType = dateType;
        this.date = date;
        this.contactId = contactId;
        this._id = _id;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public java.util.Date getDate() {
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
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
