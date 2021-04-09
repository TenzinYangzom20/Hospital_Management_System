package gvs.com.hospital_management_system;

public class BookModel {
    String id;
    String docname;
    String username;
    String phone;
    String ap_date;
    String fromtime;
    String tootime;
    String bookdate;
    String count;
    String userstatus;
    String treatmentdel;
    String docstatus;
    String username_docname_bookdate;

    public BookModel() {
    }

    public BookModel(String id, String docname, String username, String phone, String ap_date, String fromtime, String tootime, String bookdate, String count, String userstatus, String docstatus, String username_docname_bookdate, String treatmentdel) {
        this.id = id;
        this.docname = docname;
        this.username = username;
        this.phone = phone;
        this.ap_date = ap_date;
        this.fromtime = fromtime;
        this.tootime = tootime;
        this.bookdate = bookdate;
        this.treatmentdel=treatmentdel;
        this.count = count;
        this.userstatus = userstatus;
        this.docstatus = docstatus;
        this.username_docname_bookdate = username_docname_bookdate;
    }

    public String getTreatmentdel() {
        return treatmentdel;
    }

    public void setTreatmentdel(String treatmentdel) {
        this.treatmentdel = treatmentdel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAp_date() {
        return ap_date;
    }

    public void setAp_date(String ap_date) {
        this.ap_date = ap_date;
    }

    public String getFromtime() {
        return fromtime;
    }

    public void setFromtime(String fromtime) {
        this.fromtime = fromtime;
    }

    public String getTootime() {
        return tootime;
    }

    public void setTootime(String tootime) {
        this.tootime = tootime;
    }

    public String getBookdate() {
        return bookdate;
    }

    public void setBookdate(String bookdate) {
        this.bookdate = bookdate;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getUserstatus() {
        return userstatus;
    }

    public void setUserstatus(String userstatus) {
        this.userstatus = userstatus;
    }

    public String getDocstatus() {
        return docstatus;
    }

    public void setDocstatus(String docstatus) {
        this.docstatus = docstatus;
    }

    public String getUsername_docname_bookdate() {
        return username_docname_bookdate;
    }

    public void setUsername_docname_bookdate(String username_docname_bookdate) {
        this.username_docname_bookdate = username_docname_bookdate;
    }
}
