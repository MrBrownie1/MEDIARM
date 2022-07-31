package com.example.mediarm.ImgStorage;

public class Conceptos {

    private String img;
    private String nombre;
    private String concepto;

    public Conceptos(){

    }

    public Conceptos(String img, String nombre, String concepto) {
        this.img = img;
        this.nombre = nombre;
        this.concepto = concepto;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }
}
