package br.gov.sp.fatec.pg.model;

public class Order {
    private Integer cd;
    private Integer employeeCd;
    private Float totalPrice;
    private Integer delivered;
    private String clientName;

    public Order(){ }

    public Order(Integer cd, Integer employeeCd, Float totalPrice, Integer delivered, String clientName){
        this.cd = cd;
        this.employeeCd = employeeCd;
        this.totalPrice = totalPrice;
        this.delivered = delivered;
        this.clientName = clientName;
    }

    public Order(Integer employeeCd, Float totalPrice, Integer delivered, String clientName){
        this.employeeCd = employeeCd;
        this.totalPrice = totalPrice;
        this.delivered = delivered;
        this.clientName = clientName;
    }

    public Integer getCd() {
        return this.cd;
    }

    public void setCd(Integer newCd) {
        this.cd = newCd;
    }

    public Integer getEmployeeCd() {
        return this.employeeCd;
    }

    public void setEmployeeCd(Integer newEmployeeCd) {
        this.employeeCd = newEmployeeCd;
    }

    public Float getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(Float newTotalPrice) {
        this.totalPrice = newTotalPrice;
    }
    
    public Integer getDelivered() {
        return this.delivered;
    }

    public void setDelivered(Integer newDelivered) {
        this.delivered = newDelivered;
    }

    public String getClientName(){
        return this.clientName;
    }

    public void setClientName(String newClientName){
        this.clientName = newClientName;
    }
}