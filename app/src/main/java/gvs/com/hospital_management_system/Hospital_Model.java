package gvs.com.hospital_management_system;

public class Hospital_Model {
    String id;
    String hname;
    String htype;
    String haddress;
    String lati;
    String lang;
    String hname_address;

    public Hospital_Model() {
    }

    public Hospital_Model(String id, String hname, String htype, String haddress, String lati, String lang, String hname_address) {
        this.id = id;
        this.hname = hname;
        this.htype = htype;
        this.haddress = haddress;
        this.lati = lati;
        this.lang = lang;
        this.hname_address = hname_address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHname() {
        return hname;
    }

    public void setHname(String hname) {
        this.hname = hname;
    }

    public String getHtype() {
        return htype;
    }

    public void setHtype(String htype) {
        this.htype = htype;
    }

    public String getHaddress() {
        return haddress;
    }

    public void setHaddress(String haddress) {
        this.haddress = haddress;
    }

    public String getLati() {
        return lati;
    }

    public void setLati(String lati) {
        this.lati = lati;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getHname_address() {
        return hname_address;
    }

    public void setHname_address(String hname_address) {
        this.hname_address = hname_address;
    }
}
