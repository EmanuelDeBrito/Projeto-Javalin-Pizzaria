package br.gov.sp.fatec.pg.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.gov.sp.fatec.pg.database.SQLiteConnection;
import br.gov.sp.fatec.pg.model.Cart;


public class CartRepository {

    public static List<Cart> getCartByEmployee(Integer employeeCd) throws Exception{
        List<Cart> cartList = new ArrayList<Cart>();
        String query = "SELECT * FROM carrinho WHERE cd_funcionario = ?";

        try(Connection conn = SQLiteConnection.connect(); PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setInt(1, employeeCd);
        
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                Integer cdEmployee = rs.getInt("cd_funcionario");
                Integer cdProduct = rs.getInt("cd_produto");
                Float price = rs.getFloat("vl_carrinho");
                Integer quantity = rs.getInt("qt_carrinho");

                Cart cartItem = new Cart(cdEmployee, cdProduct, price, quantity); 
                cartList.add(cartItem);
            }
        }

        return cartList;
    }

    public static void createCart(Integer cdProduct, Integer cdEmployee, Float value) throws Exception {
        String sql = "INSERT INTO carrinho (cd_produto, cd_funcionario, vl_carrinho, qt_carrinho) VALUES(?, ?, ?, 1)";
        
        try(Connection conn = SQLiteConnection.connect(); PreparedStatement Pstmt = conn.prepareStatement(sql)) {
            Pstmt.setInt(1, cdProduct);
            Pstmt.setInt(2, cdEmployee);
            Pstmt.setFloat(3, value);
            
            Pstmt.executeUpdate();
        }
    }

    public static void incrementCart(Integer cdEmployee, Integer cdProduct) throws Exception {
        // Função que ira incrementar a quantidade do produto no carrinho do funcionario.
        String sql = "SELECT qt_carrinho FROM carrinho WHERE cd_funcionario = ? AND cd_produto = ?";
        int qt;
        try (Connection conn = SQLiteConnection.connect(); PreparedStatement Pstmt = conn.prepareStatement(sql)) {
            Pstmt.setInt(1, cdEmployee);
            Pstmt.setInt(2, cdProduct);
            
            ResultSet rs = Pstmt.executeQuery();
            qt = rs.getInt(1);
        }
        
        sql = "UPDATE carrinho SET qt_carrinho = ? WHERE cd_funcionario = ? AND cd_produto = ?";
        qt++; // Nova quantidade incrementada
        
        try (Connection conn = SQLiteConnection.connect(); PreparedStatement Pstmt = conn.prepareStatement(sql)) {
            Pstmt.setInt(1, qt);
            Pstmt.setInt(2, cdEmployee);
            Pstmt.setInt(3, cdProduct);
            
            Pstmt.executeUpdate();
        }
    }

    public static void decrementCart(Integer cdEmployee, Integer cdProduct) throws Exception {
        // Função que ira incrementar a quantidade do produto no carrinho do funcionario.
        String sql = "SELECT qt_carrinho FROM carrinho WHERE cd_funcionario = ? AND cd_produto = ?";
        int qt;
        try (Connection conn = SQLiteConnection.connect(); PreparedStatement Pstmt = conn.prepareStatement(sql)) {
            Pstmt.setInt(1, cdEmployee);
            Pstmt.setInt(2, cdProduct);
            
            ResultSet rs = Pstmt.executeQuery();
            qt = rs.getInt(1);
        }
        
        qt--; // Nova quantidade decrementada

        // Caso chegue a 0, ele será deletado do carrinho
        if(qt == 0) {
            deleteCart(cdProduct, cdEmployee);
            return;
        }
        
        sql = "UPDATE carrinho SET qt_carrinho = ? WHERE cd_funcionario = ? AND cd_produto = ?";
        
        try (Connection conn = SQLiteConnection.connect(); PreparedStatement Pstmt = conn.prepareStatement(sql)) {
            Pstmt.setInt(1, qt);
            Pstmt.setInt(2, cdEmployee);
            Pstmt.setInt(3, cdProduct);
            
            Pstmt.executeUpdate();
        }
    }
    
    // Deletar item do Carrinho
    public static void deleteCart(Integer cdProduct, Integer cdEmployee) throws Exception {
        String sql = "DELETE FROM carrinho WHERE cd_produto = ? AND cd_funcionario = ?";

        try(Connection conn = SQLiteConnection.connect(); PreparedStatement Pstmt = conn.prepareStatement(sql)) {
            Pstmt.setInt(1, cdProduct);
            Pstmt.setInt(2, cdEmployee);

            Pstmt.executeUpdate();
        }
    }

    // Deletar itens por funcionário
    public static void deleteCartByEmployee(Integer cdEmployee) throws Exception {
        String sql = "DELETE FROM carrinho WHERE cd_funcionario = ?";

        try(Connection conn = SQLiteConnection.connect(); PreparedStatement Pstmt = conn.prepareStatement(sql)) {
            Pstmt.setInt(1, cdEmployee);

            Pstmt.executeUpdate();
        }
    }
}