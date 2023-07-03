package rs.raf.demo.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;

public class Comment {

    private Integer id;

    @NotNull(message = "autor field is required")
    @NotEmpty(message = "autor field is required")
    private String autor;

    @NotNull(message = "tekst field is required")
    @NotEmpty(message = "tekst field is required")
    private String tekst;

    private Date vremeKreiranja;

    private Integer vestKomentara;

    public Comment() {
    }

    public Comment(Integer id, String autor, String tekst, Date vremeKreiranja, Integer vestKomentara) {
        this.id = id;
        this.autor = autor;
        this.tekst = tekst;
        this.vremeKreiranja = vremeKreiranja;
        this.vestKomentara = vestKomentara;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public Date getVremeKreiranja() {
        return vremeKreiranja;
    }

    public void setVremeKreiranja(Date vremeKreiranja) {
        this.vremeKreiranja = vremeKreiranja;
    }

    public Integer getVestKomentara() {
        return vestKomentara;
    }

    public void setVestKomentara(Integer vestKomentara) {
        this.vestKomentara = vestKomentara;
    }
}
