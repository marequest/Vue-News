package rs.raf.demo.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;

public class News {

    private Integer id;

    @NotNull(message = "naslov field is required")
    @NotEmpty(message = "naslov field is required")
    private String naslov;

    @NotNull(message = "tekst field is required")
    @NotEmpty(message = "tekst field is required")
    private String tekst;

    private Date vremeKreiranja;

    private Integer brojPoseta;

    private Integer autorVesti;

    private Integer kategorijaVesti;

    private String ime_autora_vesti;

    public News() {
    }

    public News(Integer id, String naslov, String tekst, Date vremeKreiranja, Integer brojPoseta, Integer autorVesti, Integer kategorijaVesti, String ime_autora_vesti) {
        this.id = id;
        this.naslov = naslov;
        this.tekst = tekst;
        this.vremeKreiranja = vremeKreiranja;
        this.brojPoseta = brojPoseta;
        this.autorVesti = autorVesti;
        this.kategorijaVesti = kategorijaVesti;
        this.ime_autora_vesti = ime_autora_vesti;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
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

    public Integer getBrojPoseta() {
        return brojPoseta;
    }

    public void setBrojPoseta(Integer brojPoseta) {
        this.brojPoseta = brojPoseta;
    }

    public Integer getAutorVesti() {
        return autorVesti;
    }

    public void setAutorVesti(Integer autorVesti) {
        this.autorVesti = autorVesti;
    }

    public Integer getKategorijaVesti() {
        return kategorijaVesti;
    }

    public void setKategorijaVesti(Integer kategorijaVesti) {
        this.kategorijaVesti = kategorijaVesti;
    }

    public String getIme_autora_vesti() {
        return ime_autora_vesti;
    }

    public void setIme_autora_vesti(String ime_autora_vesti) {
        this.ime_autora_vesti = ime_autora_vesti;
    }
}
