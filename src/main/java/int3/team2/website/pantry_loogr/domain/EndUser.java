package int3.team2.website.pantry_loogr.domain;

/**
 * EndUser contains all data relating to a specific user.
 * When created the first constructor is used with only the
 * necessary parameters.
 */
public class EndUser {
    private int id;

    private String password;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String city;
    private String stateRegion;
    private String zip;
    private String country;

    /**
     * Constructor that initially creates users on user registration
     *
     * @param username username of the user
     * @param email email of the user
     * @param password password of the user
     */
    public EndUser(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     *
     * Constructor that is used when getting users from the database.
     * All values except username, email and password may be null.
     *
     * @param id id from the database
     * @param password password of the user
     * @param username username of the user
     * @param firstName firstname of the user
     * @param lastName lastname of the user
     * @param email email of the user
     * @param city city of residence
     * @param stateRegion stateRegion of residence
     * @param zip zip code of residence
     * @param country country of residence
     */
    public EndUser(int id, String password, String username, String firstName, String lastName, String email, String city, String stateRegion, String zip, String country) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.city = city;
        this.stateRegion = stateRegion;
        this.zip = zip;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {return password;}

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public String getStateRegion() {
        return stateRegion;
    }

    public String getZip() {
        return zip;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return "end_user{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", stateRegion='" + stateRegion + '\'' +
                ", zip='" + zip + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
