package gvs.com.hospital_management_system;

public class Prescrption_Model {
    String id;
    String prescrption;
    String fromm;
    String too;
    String trdel;

    public Prescrption_Model() {
    }

    public Prescrption_Model(String id, String prescrption, String fromm, String too,String trdel) {
        this.id = id;
        this.prescrption = prescrption;
        this.fromm = fromm;
        this.too = too;
        this.trdel=trdel;
    }

    public String getTrdel() {
        return trdel;
    }

    public void setTrdel(String trdel) {
        this.trdel = trdel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrescrption() {
        return prescrption;
    }

    public void setPrescrption(String prescrption) {
        this.prescrption = prescrption;
    }

    public String getFromm() {
        return fromm;
    }

    public void setFromm(String fromm) {
        this.fromm = fromm;
    }

    public String getToo() {
        return too;
    }

    public void setToo(String too) {
        this.too = too;
    }
}
