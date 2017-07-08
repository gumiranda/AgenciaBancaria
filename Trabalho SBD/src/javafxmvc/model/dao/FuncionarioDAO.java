package javafxmvc.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafxmvc.model.domain.Agencia;
import javafxmvc.model.domain.Funcionario;
import javafxmvc.model.domain.Funcionario;


public class FuncionarioDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Funcionario funcionario) {
        String sql = "INSERT INTO funcionario(nome,telefone,tempo_de_servico,data_de_admissao,agencia_codigo) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getTelefone());
            stmt.setInt(3, funcionario.getTempoServico());
            stmt.setDate(4, Date.valueOf(funcionario.getData()));
            stmt.setInt(5, funcionario.getAgencia().getCdAgencia());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Funcionario funcionario) {
        String sql = "UPDATE funcionario SET nome=?, telefone=?,agencia_codigo=?,tempo_de_servico=?,data_de_admissao=? WHERE nro_funcional=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, funcionario.getNome());
            stmt.setInt(4, funcionario.getTempoServico());
            stmt.setString(2, funcionario.getTelefone());
            stmt.setInt(3, funcionario.getAgencia().getCdAgencia());
            stmt.setDate(5, Date.valueOf(funcionario.getData()));
            stmt.setInt(6, funcionario.getCdFuncionario());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Funcionario funcionario) {
        String sql = "DELETE FROM funcionario WHERE nro_funcional=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, funcionario.getCdFuncionario());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
   public List<Funcionario> listar(Agencia f) {
        String sql = "SELECT * FROM funcionario WHERE agencia_codigo =?";
        List<Funcionario> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, f.getCdAgencia());
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Funcionario funcionario = new Funcionario();
                Agencia a = new Agencia();
                funcionario.setCdFuncionario(resultado.getInt("nro_funcional"));
                funcionario.setNome(resultado.getString("nome"));
                funcionario.setTelefone(resultado.getString("telefone"));
                funcionario.setTempoServico(resultado.getInt("tempo_de_servico"));
                funcionario.setData(resultado.getDate("data").toLocalDate());
                a.setCdAgencia(resultado.getInt("agencia_codigo"));

                        //Obtendo os dados completos da agencia associada ao funcionario
                AgenciaDAO agenciaDAO = new AgenciaDAO();
                agenciaDAO.setConnection(connection);
                a = agenciaDAO.buscar(a);
                
                 funcionario.setAgencia(a);
                
                retorno.add(funcionario);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    public List<Funcionario> listar() {
        String sql = "SELECT * FROM funcionario";
        List<Funcionario> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Funcionario funcionario = new Funcionario();
                Agencia a = new Agencia();
                funcionario.setCdFuncionario(resultado.getInt("nro_funcional"));
                funcionario.setNome(resultado.getString("nome"));
                funcionario.setTelefone(resultado.getString("telefone"));
                funcionario.setTempoServico(resultado.getInt("tempo_de_servico"));
                funcionario.setData(resultado.getDate("data").toLocalDate());
                a.setCdAgencia(resultado.getInt("agencia_codigo"));

                        //Obtendo os dados completos da agencia associada ao funcionario
                AgenciaDAO agenciaDAO = new AgenciaDAO();
                agenciaDAO.setConnection(connection);
                a = agenciaDAO.buscar(a);
                
                 funcionario.setAgencia(a);
                
                retorno.add(funcionario);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Funcionario buscar(Funcionario funcionario) {
        String sql = "SELECT * FROM funcionario WHERE nro_funcional=?";
        Funcionario retorno = new Funcionario();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, funcionario.getCdFuncionario());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                Agencia a = new Agencia();
                Funcionario f = new Funcionario();
                funcionario.setNome(resultado.getString("nome"));
                funcionario.setTelefone(resultado.getString("telefone"));
                funcionario.setTempoServico(resultado.getInt("tempo_de_servico"));
                funcionario.setData(resultado.getDate("data_de_admissao").toLocalDate());
                 a.setCdAgencia(resultado.getInt("agencia_codigo"));
                funcionario.setAgencia(a);
                retorno = funcionario;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
