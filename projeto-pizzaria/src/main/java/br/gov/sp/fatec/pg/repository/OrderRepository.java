package br.gov.sp.fatec.pg.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.gov.sp.fatec.pg.database.SQLiteConnection;
import br.gov.sp.fatec.pg.model.Order;

public class OrderRepository {
    public static void createOrder(Order order) throws Exception{
        String query = "INSERT INTO pedido (cd_funcionario, vl_total_pedido, ic_entregue_pedido, nm_cliente_pedido) VALUES (?, ?, ?, ?)";

        try(Connection conn = SQLiteConnection.connect(); PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setInt(1, order.getEmployeeCd());
            pstmt.setFloat(2, order.getTotalPrice());
            pstmt.setInt(3, order.getDelivered());
            pstmt.setString(4, order.getClientName());
            
            pstmt.executeUpdate();
        }
    }

    public static List<Order> getOrdersByEmployee(Integer employeeCd) throws Exception{
        List<Order> orders = new ArrayList<Order>();
        String query = "SELECT * FROM pedido WHERE cd_funcionario = ?";

        try(Connection conn = SQLiteConnection.connect(); PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setInt(1, employeeCd);
        
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                Integer cd = rs.getInt("cd_pedido");
                Integer cdEmployee = rs.getInt("cd_funcionario");
                Float totalPrice = rs.getFloat("vl_total_pedido");
                Integer delivered = rs.getInt("ic_entregue_pedido");
                String clientName = rs.getString("nm_cliente_pedido");

                Order order = new Order(cd, cdEmployee, totalPrice, delivered, clientName);
                orders.add(order);
            }
        }

        return orders;
    }

    public static void markAsDelivered(Integer orderCd) throws Exception{
        String query = "UPDATE pedido SET ic_entregue_pedido = 1 WHERE cd_pedido = ?";

        try(Connection conn = SQLiteConnection.connect(); PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setInt(1, orderCd);
            
            pstmt.executeUpdate();
        }
    }

    public static void deleteOrder(Integer orderCd) throws Exception{
        String query = "DELETE FROM pedido WHERE cd_pedido = ?";

        try(Connection conn = SQLiteConnection.connect(); PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setInt(1, orderCd);
            
            pstmt.executeUpdate();
        }
    }
}