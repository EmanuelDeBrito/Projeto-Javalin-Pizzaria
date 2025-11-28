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

        // Tabela do pedido
        String orderTable = 
        "CREATE TABLE IF NOT EXISTS pedido (" + 
            "cd_pedido INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "cd_funcionario INTEGER, " +
            "vl_total_pedido REAL NOT NULL DEFAULT 0, " + 
            "nm_cliente TEXT NOT NULL, " +
            "FOREIGN KEY (cd_funcionario) REFERENCES funcionario (cd_funcionario)" + 
        ")";

        // Tabela do Item no Pedido
        String orderItemTable = 
        "CREATE TABLE IF NOT EXISTS item_pedido (" + 
            "cd_funcionario INTEGER, " + 
            "cd_produto INTEGER, " + 
            "vl_item_pedido REAL NOT NULL, " + 
            "qt_item_pedido INTEGER NOT NULL DEFAULT 1, " +
            "PRIMARY KEY (cd_pedido, cd_produto), " +
            "FOREIGN KEY (cd_funcionario) REFERENCES funcionario (cd_funcionario), " +
            "FOREIGN KEY (cd_produto) REFERENCES produto (cd_produto)" +
        ")";

        // Criando as tabelas
        try(Connection conn = connect(); Statement stmt = conn.createStatement()){
            // Executando os scripts das tabelas
            stmt.execute(employeeTable);
            stmt.execute(productTable);
            stmt.execute(orderTable);
            stmt.execute(orderItemTable);

            // Exibindo mensagem de verificação
            System.out.println("Database criada/verificada com sucesso!");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}