package rs.raf.demo.entities.helper;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserHelper {

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

    private Boolean isActive;

    public UserHelper() {
    }

    public UserHelper(Integer id, String ime, String prezime, String username, String tip, String password, Boolean isActive) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.username = username;
        this.tip = tip;
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

}
