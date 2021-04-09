package gvs.com.hospital_management_system;

public class Available_Model {
    String id;
    String datee;
    String fromtime;
    String tootime;
    String docname;

    public Available_Model() {
    }

    public Available_Model(String id, String datee, String fromtime, String tootime, String docname) {
        this.id = id;
        this.datee = datee;
        this.fromtime = fromtime;
        this.tootime = tootime;
        this.docname = docname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDatee() {
        return datee;
    }

    public void setDatee(String datee) {
        this.datee = datee;
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

    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname;
    }
}
