package singareddy.productionapps.dbd_contacts.models;

public class Contact {
    private String fname, mname, lname;
    private int _id;

    public Contact(String fname, String mname, String lname) {
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
    }

    public Contact(String fname, String mname, String lname, int _id) {
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
        this._id = _id;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }
}
