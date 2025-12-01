package br.gov.sp.fatec.pg;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import br.gov.sp.fatec.pg.database.SQLiteConnection;
import br.gov.sp.fatec.pg.model.Cart;
import br.gov.sp.fatec.pg.model.Employee;
import br.gov.sp.fatec.pg.model.Order;
import br.gov.sp.fatec.pg.model.Product;
import br.gov.sp.fatec.pg.repository.CartRepository;
import br.gov.sp.fatec.pg.repository.EmployeeRepository;
import br.gov.sp.fatec.pg.repository.OrderRepository;
import br.gov.sp.fatec.pg.repository.ProductRepository;
import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        
        // Criando/Verificando banco de dados
        SQLiteConnection.createDatabase();

        // Criando app
        Javalin app = Javalin.create().start(7070);
        System.out.println("Bem vindo ao Pizza Note!");

        // Rota base
        app.get("/", ctx -> {
            ctx.result("Projeto Javalin no Ar");
        });

        app.before("/api/*", ctx -> {
            // Pegando o cabeçalho
            String token = ctx.header("Authorization");

            // Verificando se o token é null
            if(token == null){
                ctx.status(401).result("Cabeçalho Inexistente");
                ctx.json("Erro");
                ctx.skipRemainingHandlers();
                return;
            }
            
            // Pegando o funcionário pelo token
            Employee employee = EmployeeRepository.getEmployeeByToken(token);

            // Verificando se o funcionário existe
            if(employee == null){
                ctx.status(403).result("Token Inválido");
                ctx.skipRemainingHandlers();
                return;
            }

            System.out.println("Autorizado: " + employee.getName());
        });

        // Endpoint para adicionar funcionário
        app.post("/signup", ctx -> {
            // Pegando o corpo da requisição
            Employee employee = ctx.bodyAsClass(Employee.class);

            try{
                EmployeeRepository.createEmployee(employee);
                ctx.json(Map.of("success", "true"));
            }catch(Exception e){
                ctx.json(Map.of("success", "false"));
                System.out.println("Erro: " + e);
            }
        });

        // Endpoint para login do funcionário
        app.post("/login", ctx -> {
            Employee employee = ctx.bodyAsClass(Employee.class);

            if(EmployeeRepository.validateEmployee(employee.getCpf(), employee.getPassword())){
                // Gerando token
                String token = UUID.randomUUID().toString();

                // Atualizando token do funcionário no banco de dados
                EmployeeRepository.updateToken(employee.getCpf(), token);

                // Enviando token para o usuário
                ctx.json(Map.of("token", token));
            }else{
                ctx.json(Map.of("success", "false"));
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
                ctx.json(Map.of("success", "true"));
                ctx.result("Funcionário não encontrado");
            }
        });

        // Endpoint para remover funcionário
        app.delete("/api/funcionario/{cpf}", ctx -> {
            Integer cpf = Integer.parseInt(ctx.pathParam("cpf"));

            if(EmployeeRepository.deleteEmployee(cpf)){
                ctx.json(Map.of("success", "true"));
            }else{
                ctx.json(Map.of("success", "false"));
                ctx.result("Usuário não encontrado");
            }
        });

        // Endpoint para adicionar um produto
        app.post("/api/produto", ctx -> {
            Product product = ctx.bodyAsClass(Product.class);

            try{
                ProductRepository.createProduct(product);
                ctx.json(Map.of("success", "true"));
            }catch(Exception e){
                ctx.json(Map.of("success", "false"));
                System.out.println("Erro: " + e);
            }
        });

        // Endpoint para pegar um produto
        app.get("/api/produto/{id}", ctx -> {
            Integer id = Integer.parseInt(ctx.pathParam("id"));

            Product product = ProductRepository.getProduct(id);

            System.out.println(product);

            if(product != null){
                ctx.json(Map.of("imagem", product.getImage(), "nome", product.getName(), "descricao", product.getDescription(), "preco", product.getPrice()));
            }else{
                ctx.json(Map.of("success", "false"));
                ctx.result("Produto não encontrado");
            }
        });

        // Endpoint para criar um item no carrinho
        app.post("/api/carrinho", ctx -> {
            Cart cart = ctx.bodyAsClass(Cart.class);

            try{
                CartRepository.createCart(cart.getCdEmployee(), cart.getCdProduct(), cart.getValue());
                ctx.json(Map.of("success", "true"));
            }catch(Exception e){
                ctx.json(Map.of("success", "false"));
                System.out.println("Erro: " + e);
            }
        });

        // Endpoint para pegar todos os itens no carrinho do funcionário
        app.get("api/carrinho/{idFuncionario}", ctx -> {
            Integer employeeId = Integer.parseInt(ctx.pathParam("idFuncionario"));

            List<Cart> cart = CartRepository.getCartByEmployee(employeeId);

            if(cart.size() > 0){
                ctx.json(cart);
            }else{
                ctx.json(Map.of("success", "false"));
                ctx.result("Sem nenhum pedido no carrinho");
            }
        });

        // Endpoint para criar um pedido
        app.post("/api/pedido", ctx -> {
            Order order = ctx.bodyAsClass(Order.class);

            try{
                OrderRepository.createOrder(order);
                ctx.json(Map.of("success", "true"));
            }catch(Exception e){
                ctx.json(Map.of("success", "false"));
                System.out.println("Erro: " + e);
            }
        });

        // Endpoint para pegar todos os pedidos de um funcionário específico
        app.get("/api/pedido/{idFuncionario}", ctx -> {
            Integer employeeId = Integer.parseInt(ctx.pathParam("idFuncionario"));

            List<Order> orders = OrderRepository.getOrdersByEmployee(employeeId);

            if(orders.size() > 0){
                ctx.json(orders);
            }else{
                ctx.json(Map.of("success", "false"));
                ctx.result("Sem nenhum pedido anotado");
            }
        });

        // Endpoint para marcar um pedido como entregue
        app.patch("/api/pedido/entregue/{idPedido}", ctx -> {
            Integer orderId = Integer.parseInt(ctx.pathParam("idPedido"));

            try{
                OrderRepository.markAsDelivered(orderId);
                ctx.json(Map.of("success", "true"));
            }catch(Exception e){
                ctx.json(Map.of("success", "false"));
                System.out.println("Erro: " + e);
            }
        });

        // Endpoint para deletar um pedido
        app.delete("/api/pedido/{idPedido}", ctx -> {
            Integer orderId = Integer.parseInt(ctx.pathParam("idPedido"));

            try{
                OrderRepository.deleteOrder(orderId);
                ctx.json(Map.of("success", "true"));
            }catch(Exception e){
                ctx.json(Map.of("success", "false"));
                System.out.println("Erro: " + e);
            }
        });
    }
}