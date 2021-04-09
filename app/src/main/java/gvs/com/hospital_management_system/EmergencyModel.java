package gvs.com.hospital_management_system;

public class EmergencyModel {
    String id;
    String latitude;
    String longtitude;
    String specilization;
    String description;
    String phone;
    String status;
    String date;
    String date_phone;
    String count;

    public EmergencyModel() {
    }

    public EmergencyModel(String id, String latitude, String longtitude, String specilization, String description, String phone, String status, String date, String date_phone, String count) {
        this.id = id;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.specilization = specilization;
        this.description = description;
        this.phone = phone;
        this.status = status;
        this.date = date;
        this.date_phone = date_phone;
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public String getSpecilization() {
        return specilization;
    }

    public void setSpecilization(String specilization) {
        this.specilization = specilization;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate_phone() {
        return date_phone;
    }

    public void setDate_phone(String date_phone) {
        this.date_phone = date_phone;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
