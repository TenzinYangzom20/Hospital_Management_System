package gvs.com.hospital_management_system;

public class Feedback_Model {
    String id;
    String feedback;
    String from ;
    String to;

    public Feedback_Model() {
    }

    public Feedback_Model(String id, String feedback, String from, String to) {
        this.id = id;
        this.feedback = feedback;
        this.from = from;
        this.to = to;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
