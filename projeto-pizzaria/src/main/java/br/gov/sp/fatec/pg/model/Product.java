package br.gov.sp.fatec.pg.model;

public class Product {
    private Integer codigo;
    private String image;
    private String name;
    private String description;
    private float price;

    public Product(){}

    public Product(String image, String name, String description, float price){
        this.image = image;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Integer getCodigo() {
        return this.codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }
    
    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return this.price;
    }

    public void setValue(float price) {
        this.price = price;
    }
}