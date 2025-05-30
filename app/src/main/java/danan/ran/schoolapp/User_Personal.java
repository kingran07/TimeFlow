package danan.ran.schoolapp;

public class User_Personal {
    private String fullName;
    private String email;
    private String phone;
    private boolean professional;

    public User_Personal() {}


    public User_Personal(String fullName, String email, String phone, boolean professional) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.professional = professional;
    }



    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isProfessional() {
        return professional;
    }


    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setProfessional(boolean professional) {
        this.professional = professional;
    }
}
