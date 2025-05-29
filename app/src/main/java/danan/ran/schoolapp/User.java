package danan.ran.schoolapp;

    public class User {
        private String firstName;
        private String lastName;
        private String gmail;
        private String location;
        private String phones;

        public User() {

        }

        public User(String firstName, String lastName, String gmail, String location, String phones) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.gmail = gmail;
            this.location = location;
            this.phones = phones;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getGmail() {
            return gmail;
        }

        public void setGmail(String gmail) {
            this.gmail = gmail;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getPhones() {
            return phones;
        }

        public void setPhones(String phones) {
            this.phones = phones;
        }

    }

