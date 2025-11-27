package br.gov.sp.fatec.pg.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.gov.sp.fatec.pg.database.SQLiteConnection;
import br.gov.sp.fatec.pg.model.Employee;

public class EmployeeRepository {
    public static boolean validateEmployee(Integer cpf, String password) throws Exception{
        String query = "SELECT COUNT(*) FROM funcionario WHERE cd_cpf_funcionario = ? AND nm_senha_funcionario = ?";

        try(Connection conn = SQLiteConnection.connect(); PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setInt(1, cpf);
            pstmt.setString(2, password);
            
            ResultSet rs =  pstmt.executeQuery();
            if(rs.next()){
                return rs.getInt(1) > 0;
            }
        }

        return false;
    }

    public static void updateToken(Integer cpf, String token) throws Exception{
        String query = "UPDATE FROM funcionario SET token = ? WHERE cd_cpf_funcionario = ?";

        try(Connection conn = SQLiteConnection.connect(); PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setString(1, token);
            pstmt.setInt(2, cpf);
            
            pstmt.executeUpdate();
        }
    }    

    /**
     * Cria um funcionário no banco de dados
     * @param employee Objeto de um funcionário
     * @throws Exception para exceção de duplicação de CPF
     */
    public static void createEmployee(Employee employee) throws Exception{
        // Query de inserção
        String query = "INSERT INTO funcionario(cd_cpf_funcionario, nm_funcionario, nm_senha_funcionario) VALUES (?, ?, ?)";

        try(Connection conn = SQLiteConnection.connect(); PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setInt(1, employee.getCpf());
            pstmt.setString(2, employee.getName());
            pstmt.setString(3, employee.getPassword());

            pstmt.executeUpdate();
        }
    }

    /**
     * Procura um funcionário no banco de dados
     * @param cpf CPF do funcionário procurado
     * @return Retorna o funcionário procurado
     */
    public static Employee getEmployee(Integer cpf) throws Exception{
        String query = "SELECT * FROM funcionario WHERE cd_cpf_funcionario = ?";

        try(Connection conn = SQLiteConnection.connect(); PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setInt(1, cpf);
        
            ResultSet result = pstmt.executeQuery();

            Integer cpfBusca = result.getInt("cd_cpf_funcionario");
            String nome = result.getString("nm_funcionario");
            String senha = result.getString("nm_senha_funcionario");

            Employee employeeSearched = new Employee(cpfBusca, nome, senha);

            return employeeSearched;
        }
    }

    /**
     * Remove um funcionário pelo seu CPF
     * @param cpf cpf do funcionário a ser removido
     * @return true se apagou, false se não encontrou
     * @throws Exception Erros de conexão
     */
    public static boolean deleteEmployee(Integer cpf) throws Exception{
        // Query de remoção
        String query = "DELETE FROM funcionario WHERE cd_cpf_funcionario = ?";

        try(Connection conn = SQLiteConnection.connect(); PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setInt(1, cpf);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }
}