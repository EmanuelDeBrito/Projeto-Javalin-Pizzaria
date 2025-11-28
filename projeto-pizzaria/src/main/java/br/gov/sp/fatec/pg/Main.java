package br.gov.sp.fatec.pg;

import java.util.Map;
import java.util.UUID;

import br.gov.sp.fatec.pg.database.SQLiteConnection;
import br.gov.sp.fatec.pg.model.Employee;
import br.gov.sp.fatec.pg.model.Product;
import br.gov.sp.fatec.pg.repository.EmployeeRepository;
import br.gov.sp.fatec.pg.repository.ProductRepository;
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

        app.before("/api/*", ctx -> {
            // Pegando o cabeçalho
            String token = ctx.header("Authorization");

            if(token == null){
                ctx.status(401).result("Cabeçalho Inexistente");
                ctx.json("Erro");
                ctx.skipRemainingHandlers();
                return;
            }

            Employee employee = EmployeeRepository.getEmployeeByToken(token);

            if(employee == null){
                ctx.status(403).result("Token Inválido");
                ctx.skipRemainingHandlers();
                return;
            }

            System.out.println("Autorizado: " + employee.getName());
        });

        // Endpoint para adicionar funcionário
        app.post("/signup", ctx -> {
            Employee employee = ctx.bodyAsClass(Employee.class);

            try{
                EmployeeRepository.createEmployee(employee);
                ctx.json(Map.of("success", "true"));
            }catch(Exception e){
                System.out.println("Erro: " + e);
            }
        });

        // Login do funcionário
        app.post("/login", ctx -> {
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
        app.get("/api/funcionario/{cpf}", ctx -> {
            Integer cpf = Integer.parseInt(ctx.pathParam("cpf"));

            Employee employee = EmployeeRepository.getEmployee(cpf);

            if(employee != null){
                ctx.json(Map.of("cpf", employee.getCpf(), "nome", employee.getName(), "senha", employee.getPassword()));
            }else{
                ctx.result("Funcionário não encontrado");
            }
        });

        // Endpoint para remover funcionário
        app.delete("/api/funcionario/{cpf}", ctx -> {
            Integer cpf = Integer.parseInt(ctx.pathParam("cpf"));

            if(EmployeeRepository.deleteEmployee(cpf)){
                ctx.json(Map.of("success", "true"));
            }else{
                ctx.result("Usuário não encontrado");
            }
        });

        app.post("/api/produto", ctx -> {
            Product product = ctx.bodyAsClass(Product.class);

            try{
                ProductRepository.createProduct(product);
                ctx.json(Map.of("success", "true"));
            }catch(Exception e){
                System.out.println("Erro: " + e);
            }
        });

        app.get("/api/produto/{id}", ctx -> {
            Integer id = Integer.parseInt(ctx.pathParam("id"));

            Product product = ProductRepository.getProduct(id);

            System.out.println(product);

            if(product != null){
                ctx.json(Map.of("imagem", product.getImage(), "nome", product.getName(), "descricao", product.getDescription(), "preco", product.getPrice()));
            }else{
                ctx.result("Produto não encontrado");
            }
        });
    }
}