package gvs.com.hospital_management_system;


import java.util.List;


public class AppTocken {
    private String id;
    private String pname;
    private List<String> tokens;

    public AppTocken() {
    }

    public AppTocken(String id, String pname, List<String> tokens) {
        this.id = id;
        this.pname = pname;
        this.tokens = tokens;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public List<String> getTokens() {
        return tokens;
    }

    public void setTokens(List<String> tokens) {
        this.tokens = tokens;
    }
}
