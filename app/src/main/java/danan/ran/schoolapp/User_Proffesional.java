package danan.ran.schoolapp;

public class User_Proffesional {
    private String fullName;
    private String email;
    private String phone;
    private String businessName;
    private String profession;
    private int yearsOfExperience;
    private boolean professional;

    public User_Proffesional() {}

    public User_Proffesional(String fullName, String email, String phone, String buisnessName, String profession, int yearsOfExperience, boolean professional) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.businessName = buisnessName;
        this.profession = profession;
        this.yearsOfExperience = yearsOfExperience;
        this.professional = professional;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBuisnessName() {
        return businessName;
    }

    public void setBuisnessName(String buisnessName) {
        this.businessName = buisnessName;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public boolean isProfessional() {
        return professional;
    }

    public void setProfessional(boolean professional) {
        this.professional = professional;
    }
}
