package rs.raf.demo.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Category {

    private Integer id;

    @NotNull(message = "Naziv field is required")
    @NotEmpty(message = "Naziv field is required")
    private String naziv;

    @NotNull(message = "Opis field is required")
    @NotEmpty(message = "Opis field is required")
    private String opis;

    public Category() {
    }

    public Category(Integer id, String naziv, String opis) {
        this.id = id;
        this.naziv = naziv;
        this.opis = opis;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
}
