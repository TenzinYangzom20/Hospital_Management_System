package gvs.com.hospital_management_system;

public class Specialization_Model {
    String specialization;
    String id;

    public Specialization_Model() {
    }

    public Specialization_Model(String specialization, String id) {
        this.specialization = specialization;
        this.id = id;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
