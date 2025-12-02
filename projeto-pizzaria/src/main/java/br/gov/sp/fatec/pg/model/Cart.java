package br.gov.sp.fatec.pg.model;

public class Cart {
    private Integer cd;
    private Integer cdEmployee;
    private Integer cdProduct;
    private Float value;
    private Integer quantity;

    public Cart(){}

    public Cart(Integer cdEmployee, Integer cdProduct, Float value, Integer quantity) {
        this.cdEmployee = cdEmployee;
        this.cdProduct = cdProduct;
        this.value = value;
        this.quantity = quantity;
    }

    public Integer getCd() {
        return cd;
    }

    public void setCd(Integer cd) {
        this.cd = cd;
    }

    public Integer getCdProduct() {
        return cdProduct;
    }

    public void setCdProduct(Integer cdProduct) {
        this.cdProduct = cdProduct;
    }

    public Integer getCdEmployee() {
        return cdEmployee;
    }

    public void setCdEmployee(Integer cdEmployee) {
        this.cdEmployee = cdEmployee;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }
    
}
