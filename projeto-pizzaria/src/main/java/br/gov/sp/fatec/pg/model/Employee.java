package br.gov.sp.fatec.pg.model;

public class Employee {
    private Integer cd;
    private Integer cpf;
    private String name;
    private String password;

    public Employee(){}

    public Employee(Integer cpf, String name, String password){
        this.cpf = cpf;
        this.name = name;
        this.password = password;
    }

    public Integer getCd(){
        return this.cd;
    }

    public void setCd(Integer newCd){
        this.cd = newCd;
    }

    public Integer getCpf(){
        return this.cpf;
    }

    public void setCpf(Integer newCpf){
        this.cpf = newCpf;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String newPassword){
        this.password = newPassword;
    }
}