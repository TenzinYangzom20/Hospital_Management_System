package gvs.com.hospital_management_system;

public class Doctor_Model {
    String id;
    String name;
    String username;
    String phone;
    String password;
    String speci;
    String expi;
    String hosname;
    String about;
    String username_hosname;
    String username_password;

    public Doctor_Model() {
    }

    public Doctor_Model(String id, String name, String username, String phone, String password, String speci, String expi, String hosname, String about, String username_hosname,String username_password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.phone = phone;
        this.password = password;
        this.speci = speci;
        this.expi = expi;
        this.hosname = hosname;
        this.about = about;
        this.username_hosname = username_hosname;
        this.username_password = username_password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSpeci() {
        return speci;
    }

    public void setSpeci(String speci) {
        this.speci = speci;
    }

    public String getExpi() {
        return expi;
    }

    public void setExpi(String expi) {
        this.expi = expi;
    }

    public String getHosname() {
        return hosname;
    }

    public void setHosname(String hosname) {
        this.hosname = hosname;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getUsername_hosname() {
        return username_hosname;
    }

    public void setUsername_hosname(String username_hosname) {
        this.username_hosname = username_hosname;
    }

    public String getUsername_password() {
        return username_password;
    }

    public void setUsername_password(String username_password) {
        this.username_password = username_password;
    }
}
