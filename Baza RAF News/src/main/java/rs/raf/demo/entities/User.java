package rs.raf.demo.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class User {

    private Integer id;

    @NotNull(message = "ime field is required")
    @NotEmpty(message = "ime field is required")
    private String ime;

    @NotNull(message = "prezime field is required")
    @NotEmpty(message = "prezime field is required")
    private String prezime;

    @NotNull(message = "username field is required")
    @NotEmpty(message = "username field is required")
    private String username;

    @NotNull(message = "tip field is required")
    @NotEmpty(message = "tip field is required")
    private String tip;

    @NotNull(message = "password field is required")
    @NotEmpty(message = "password field is required")
    private String password;

    private Boolean isActive;

    public User() {
    }

    public User(Integer id, String ime, String prezime, String username, String tip, String password, Boolean isActive) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.username = username;
        this.tip = tip;
        this.password = password;
        this.isActive = isActive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

}
