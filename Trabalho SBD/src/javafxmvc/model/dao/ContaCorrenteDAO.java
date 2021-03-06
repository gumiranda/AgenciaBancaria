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
import javafxmvc.model.domain.ContaCorrente;
import javafxmvc.model.domain.Agencia;


public class ContaCorrenteDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(ContaCorrente conta) {
        String sql = "INSERT INTO contacorrente(saldo,data_de_criacao,agencia_codigo,ultimo_acesso) VALUES(?,?,?,NOW())";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDouble(1, conta.getSaldo());
            stmt.setDate(2, Date.valueOf(conta.getDataCriacao()));
            stmt.setInt(3, conta.getAgencia().getCdAgencia());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ContaCorrenteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(ContaCorrente conta) {
        String sql = "UPDATE contacorrente SET saldo=?,ultimo_acesso=?,data_de_criacao=?,agencia_codigo =? WHERE cdconta=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDouble(1, conta.getSaldo());
            stmt.setDate(2, Date.valueOf(conta.getUltimoAcesso()));
            stmt.setDate(3, Date.valueOf(conta.getDataCriacao()));
            stmt.setInt(4, conta.getAgencia().getCdAgencia());
            stmt.setInt(5, conta.getCdConta());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ContaCorrenteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(ContaCorrente conta) {
        String sql = "DELETE FROM contacorrente WHERE cdconta=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, conta.getCdConta());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ContaCorrenteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<ContaCorrente> listar() {
        String sql = "SELECT * FROM contacorrente";
        List<ContaCorrente> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                ContaCorrente conta = new ContaCorrente();
                Agencia f = new Agencia();
                conta.setCdConta(resultado.getInt("cdconta"));
                conta.setSaldo(resultado.getDouble("saldo"));
                conta.setDataCriacao(resultado.getDate("data_de_criacao").toLocalDate());
                                conta.setUltimoAcesso(resultado.getDate("ultimo_acesso").toLocalDate());
                f.setCdAgencia(resultado.getInt("agencia_codigo"));

                        //Obtendo os dados completos da associado ao conta
                AgenciaDAO agenciaDAO = new AgenciaDAO();
                agenciaDAO.setConnection(connection);
                f = agenciaDAO.buscar(f);
                
                 conta.setAgencia(f);
                
                retorno.add(conta);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ContaCorrenteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public ContaCorrente buscar(ContaCorrente conta) {
        String sql = "SELECT * FROM contacorrente WHERE cdconta=?";
        ContaCorrente retorno = new ContaCorrente();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, conta.getCdConta());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                Agencia f = new Agencia();
                conta.setSaldo(resultado.getDouble("saldo"));
                conta.setDataCriacao(resultado.getDate("data_de_criacao").toLocalDate());
                conta.setUltimoAcesso(resultado.getDate("ultimo_acesso").toLocalDate());
                 f.setCdAgencia(resultado.getInt("agencia_codigo"));
                conta.setAgencia(f);
                retorno = conta;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ContaCorrenteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
