package br.gov.sp.fatec.pg;

import java.util.Map;
import java.util.UUID;

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

        // Login do funcionário
        app.post("/funcionario/login", ctx -> {
            Employee employee = ctx.bodyAsClass(Employee.class);

            if(EmployeeRepository.validateEmployee(employee.getCpf(), employee.getPassword())){
                // Gerando token
                String token = UUID.randomUUID().toString();

                // Atualizando token do funcionário no banco de dados
                EmployeeRepository.updateToken(employee.getCpf(), token);

                ctx.json(Map.of("token", token));
            }else{
                ctx.result("Credenciais inválidas");
            }
        });

        // Endpoint para selecionar um funcionário
        app.get("/funcionario/{cpf}", ctx -> {
            Integer cpf = Integer.parseInt(ctx.pathParam("cpf"));

            Employee employee = EmployeeRepository.getEmployee(cpf);

            ctx.json(Map.of("cpf", employee.getCpf(), "nome", employee.getName(), "senha", employee.getPassword()));
        });

        // Endpoint para adicionar funcionário
        app.post("/funcionario", ctx -> {
            Employee employee = ctx.bodyAsClass(Employee.class);

            try{
                EmployeeRepository.createEmployee(employee);
                ctx.json(Map.of("success", "true"));
            }catch(Exception e){
                System.out.println("Erro: " + e);
            }
        });

        // Endpoint para remover funcionário
        app.delete("/funcionario/{cpf}", ctx -> {
            Integer cpf = Integer.parseInt(ctx.pathParam("cpf"));

            if(EmployeeRepository.deleteEmployee(cpf)){
                ctx.json(Map.of("success", "true"));
            }else{
                ctx.result("Usuário não encontrado");
            }
        });
    }
}