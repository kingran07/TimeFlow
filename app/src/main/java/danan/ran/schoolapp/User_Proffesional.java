package danan.ran.schoolapp;

public class User_Proffesional {
    private String fullName;
    private String email;
    private String password;
    private String phone;
    private String buisnessName;
    private String buisnessType;
    private String yearStr;
    private boolean professional;



    public User_Proffesional(String fullName, String email, String phone, String businessName, String businessType, int yearsOfExperience, boolean professional) {
    }



    public User_Proffesional(String fullName, String email, String password, String phone, String buisnessName, String buisnessType, String yearStr, boolean professional)
    {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.buisnessName = buisnessName;
        this.buisnessType = buisnessType;
        this.yearStr = yearStr;
        this.professional = professional;
    }




    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getBuisnessName() {
        return buisnessName;
    }

    public String getBuisnessType() {
        return buisnessType;
    }

    public String getYearStr() {
        return yearStr;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setBuisnessName(String buisnessName) {
        this.buisnessName = buisnessName;
    }

    public void setBuisnessType(String buisnessType) {
        this.buisnessType = buisnessType;
    }

    public void setYearStr(String yearStr) {
        this.yearStr = yearStr;
    }

    public void setProfessional(boolean professional) {
        this.professional = professional;
    }
}
