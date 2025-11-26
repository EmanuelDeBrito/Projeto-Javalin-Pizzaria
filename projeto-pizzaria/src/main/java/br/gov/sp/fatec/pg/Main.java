package br.gov.sp.fatec.pg;

import java.util.Map;

import br.gov.sp.fatec.pg.database.SQLiteConnection;
import br.gov.sp.fatec.pg.model.Employee;
import br.gov.sp.fatec.pg.repository.EmployeeRepository;
import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {

        // Criando/Verificando banco de dados
        SQLiteConnection.createDatabase();

        // Criando app
        Javalin app = Javalin.create().start(7070);
        System.out.println("Javalin rodando com sucesso!");

        // Rota base
        app.get("/", ctx -> {
            ctx.result("Projeto Javalin no Ar");
        });

        // Endpoint para selecionar um funcionário
        app.get("/funcionario/{cpf}", ctx -> {
            Integer cpf = Integer.parseInt(ctx.pathParam("cpf"));

            try{
                Employee employee = EmployeeRepository.getEmployee(cpf);

                ctx.json(Map.of("cpf", employee.getCpf(), "nome", employee.getName(), "senha", employee.getPassword()));
            }catch(Exception e){
                System.out.println("Erro: " + e);
            }
        });

        // Endpoint para adicionar funcionário
        app.post("/funcionario", ctx -> {
            Employee employee = ctx.bodyAsClass(Employee.class);

            try{
                EmployeeRepository.createEmployee(employee);
                ctx.result("Usuário criado com sucesso!");
            }catch(Exception e){
                System.out.println("Erro: " + e);
            }
        });

        // Endpoint para remover funcionário
        app.delete("/funcionario/{cpf}", ctx -> {
            Integer cpf = Integer.parseInt(ctx.pathParam("cpf"));

            try{
                if(EmployeeRepository.deleteEmployee(cpf)){
                    ctx.result("Usuário deletado com sucesso!");
                }else{
                    ctx.result("Usuário não encontrado!");
                }
            }catch(Exception e){
                System.out.println("Erro: " + e);
                ctx.result("Não foi possível deletar o usuário!");
            }
        });
    }
}