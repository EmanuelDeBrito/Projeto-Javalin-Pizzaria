package br.gov.sp.fatec.pg.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.gov.sp.fatec.pg.database.SQLiteConnection;
import br.gov.sp.fatec.pg.model.Product;

public class ProductRepository {
    public static void createProduct(Product product) throws Exception{
        String query = "INSERT INTO produto (im_produto, nm_produto, ds_produto, vl_produto) VALUES (?, ?, ?, ?)";

        try(Connection conn = SQLiteConnection.connect(); PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setString(1, product.getImage());
            pstmt.setString(2, product.getName());
            pstmt.setString(3, product.getDescription());
            pstmt.setFloat(4, product.getPrice());
            
            pstmt.executeUpdate();
        }
    }

    public static Product getProduct(Integer codigo) throws Exception{
        String query = "SELECT * FROM produto WHERE cd_produto = ?";

        try(Connection conn = SQLiteConnection.connect(); PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setInt(1, codigo);
        
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                String imagemProduto = rs.getString("im_produto");
                String nomeProduto = rs.getString("nm_produto");
                String descricaoProduto = rs.getString("ds_produto");
                float precoProduto = rs.getFloat("vl_produto");

                Product productSearched = new Product(imagemProduto, nomeProduto, descricaoProduto, precoProduto);

                return productSearched;
            }
        }

        return null;
    }
}