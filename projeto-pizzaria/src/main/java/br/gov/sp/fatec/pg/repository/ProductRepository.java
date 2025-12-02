package br.gov.sp.fatec.pg.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.gov.sp.fatec.pg.database.SQLiteConnection;
import br.gov.sp.fatec.pg.model.Product;

public class ProductRepository {
    // Cadastrar um novo produto
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

    // Coletar um produto pelo código
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

    // Coletar todos os produtos
    public static List<Product> getAllProducts() throws Exception {
        String query = "SELECT * FROM produto";
        List<Product> products = new ArrayList<>();

        try (Connection conn = SQLiteConnection.connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
             ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                String imagemProduto = rs.getString("im_produto");
                String nomeProduto = rs.getString("nm_produto");
                String descricaoProduto = rs.getString("ds_produto");
                float precoProduto = rs.getFloat("vl_produto");

                Product product = new Product(imagemProduto, nomeProduto, descricaoProduto, precoProduto);
                products.add(product);
            }
        }
        return products;
    }
}