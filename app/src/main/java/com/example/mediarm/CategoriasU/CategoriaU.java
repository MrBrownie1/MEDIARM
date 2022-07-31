package com.example.mediarm.CategoriasU;

public class CategoriaU {

    String img;
    String categoria;


    public CategoriaU(){
    }

    public CategoriaU(String img, String categoria) {
        this.img = img;
        this.categoria = categoria;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
