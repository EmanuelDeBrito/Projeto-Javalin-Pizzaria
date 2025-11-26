package br.gov.sp.fatec.pg;

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
            ctx.result("Rodando na porta 7070");
        });
    }
}