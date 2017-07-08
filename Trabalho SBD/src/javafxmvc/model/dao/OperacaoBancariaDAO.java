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
import javafxmvc.model.domain.OperacaoBancaria;
import javafxmvc.model.domain.ContaCorrente;


public class OperacaoBancariaDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(OperacaoBancaria operacaobancaria) {
        String sql = "INSERT INTO operacaobancaria(tipo, descricao,data_operacao,conta_id,agencia_id) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, operacaobancaria.getTipo());
            stmt.setString(2, operacaobancaria.getDescricao());
            stmt.setDate(3, Date.valueOf(operacaobancaria.getDataOperacao()));
            stmt.setInt(4, operacaobancaria.getConta().getCdConta());
             stmt.setInt(5, operacaobancaria.getCdAgencia());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(OperacaoBancariaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(OperacaoBancaria operacaobancaria) {
        String sql = "UPDATE operacaobancaria SET cdoperacao=?, tipo=?, descricao=?, agencia_id=?, data_operacao=?,conta_id =? WHERE cdoperacao=?, conta_id=?, agencia_id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, operacaobancaria.getTipo());
            stmt.setString(2, operacaobancaria.getDescricao());
            stmt.setInt(3, operacaobancaria.getCdAgencia());
            stmt.setDate(4, Date.valueOf(operacaobancaria.getDataOperacao()));
            stmt.setInt(5, operacaobancaria.getConta().getCdConta());
            stmt.setInt(6, operacaobancaria.getCdOperacaoBancaria());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(OperacaoBancariaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(OperacaoBancaria operacaobancaria) {
        String sql = "DELETE FROM operacaobancaria WHERE cdoperacao=?, conta_id=?, agencia_id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, operacaobancaria.getCdOperacaoBancaria());
             stmt.setInt(2, operacaobancaria.getConta_codigo());
              stmt.setInt(1, operacaobancaria.getCdAgencia());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(OperacaoBancariaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<OperacaoBancaria> listar() {
        String sql = "SELECT * FROM operacaobancaria";
        List<OperacaoBancaria> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                OperacaoBancaria operacaobancaria = new OperacaoBancaria();
                ContaCorrente f = new ContaCorrente();
                Agencia a = new Agencia();
                operacaobancaria.setCdOperacaoBancaria(resultado.getInt("cdoperacao"));
                operacaobancaria.setTipo(resultado.getString("tipo"));
                operacaobancaria.setDescricao(resultado.getString("descricao"));            
                operacaobancaria.setDataOperacao(resultado.getDate("data_operacao").toLocalDate());
                                operacaobancaria.setHora(resultado.getDate("hora").toLocalDate());
                f.setCdConta(resultado.getInt("conta_id"));
                a.setCdAgencia(resultado.getInt("agencia_id"));

                        //Obtendo os dados completos do funcionario associado ao operacaobancaria
                ContaCorrenteDAO funcionarioDAO = new ContaCorrenteDAO();
                funcionarioDAO.setConnection(connection);
                f = funcionarioDAO.buscar(f);
                 AgenciaDAO agenciaDAO = new AgenciaDAO();
                agenciaDAO.setConnection(connection);
                a = agenciaDAO.buscar(a);
                
                 operacaobancaria.setConta(f);
                 operacaobancaria.setAgencia(a);
                
                retorno.add(operacaobancaria);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OperacaoBancariaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public OperacaoBancaria buscar(OperacaoBancaria operacaobancaria) {
        String sql = "SELECT * FROM operacaobancaria WHERE cdoperacao=?, conta_id=?, agencia_id=?";
        OperacaoBancaria retorno = new OperacaoBancaria();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, operacaobancaria.getCdOperacaoBancaria());
            stmt.setInt(2, operacaobancaria.getConta_codigo());
            stmt.setInt(1, operacaobancaria.getCdAgencia());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                ContaCorrente f = new ContaCorrente();
                Agencia a = new Agencia();
                operacaobancaria.setTipo(resultado.getString("tipo"));
                operacaobancaria.setDescricao(resultado.getString("descricao"));               
                operacaobancaria.setDataOperacao(resultado.getDate("data_operacao").toLocalDate());
                operacaobancaria.setHora(resultado.getDate("hora").toLocalDate());
                 f.setCdConta(resultado.getInt("conta_id"));
                 a.setCdAgencia(resultado.getInt("agencia_id"));
                      
                operacaobancaria.setConta(f);
                operacaobancaria.setAgencia(a);
                retorno = operacaobancaria;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OperacaoBancariaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
