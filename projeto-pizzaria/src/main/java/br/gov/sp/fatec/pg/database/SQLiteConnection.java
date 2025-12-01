package br.gov.sp.fatec.pg.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class SQLiteConnection {
    // String da conexao com o banco
    private static final String URL = "jdbc:sqlite:pizzaria.db";

    public static Connection connect() throws Exception{
        return DriverManager.getConnection(URL);
    }

    // Método que cria o banco
    public static void createDatabase(){
        
        // Tabela do funcionário
        String employeeTable = 
        "CREATE TABLE IF NOT EXISTS funcionario( " + 
            "cd_funcionario INTEGER PRIMARY KEY AUTOINCREMENT, " + 
            "cd_cpf_funcionario INTEGER UNIQUE NOT NULL, " +
            "nm_funcionario TEXT NOT NULL, " +
            "nm_senha_funcionario TEXT NOT NULL, " + 
            "token TEXT" +
        ")";
        
        // Tabela do produto
        String productTable = 
        "CREATE TABLE IF NOT EXISTS produto (" +
            "cd_produto INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "im_produto TEXT NOT NULL, " +
            "nm_produto TEXT NOT NULL, " +
            "ds_produto TEXT, " + 
            "vl_produto REAL NOT NULL" +
        ")";

        // Tabela do Carrinho
        String cartTable = 
        "CREATE TABLE IF NOT EXISTS carrinho (" + 
            "cd_funcionario INTEGER, " + 
            "cd_produto INTEGER, " + 
            "vl_carrinho REAL NOT NULL, " + 
            "qt_carrinho INTEGER NOT NULL DEFAULT 1, " +
            "PRIMARY KEY (cd_funcionario, cd_produto), " +
            "FOREIGN KEY (cd_funcionario) REFERENCES funcionario (cd_funcionario), " +
            "FOREIGN KEY (cd_produto) REFERENCES produto (cd_produto)" +
        ")";

        // Tabela do pedido
        String orderTable = 
        "CREATE TABLE IF NOT EXISTS pedido (" + 
            "cd_pedido INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "cd_funcionario INTEGER, " +
            "vl_total_pedido REAL NOT NULL DEFAULT 0, " +
            "ic_entregue_pedido INTEGER NOT NULL DEFAULT 0, " + 
            "nm_cliente_pedido TEXT NOT NULL, " +
            "FOREIGN KEY (cd_funcionario) REFERENCES funcionario (cd_funcionario)" + 
        ")";

        // Criando as tabelas
        try(Connection conn = connect(); Statement stmt = conn.createStatement()){
            // Executando os scripts das tabelas
            stmt.execute(employeeTable);
            stmt.execute(productTable);
            stmt.execute(cartTable);
            stmt.execute(orderTable);
            stmt.execute("PRAGMA foreign_keys = ON;");

            // Exibindo mensagem de verificação
            System.out.println("Database criada/verificada com sucesso!");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}