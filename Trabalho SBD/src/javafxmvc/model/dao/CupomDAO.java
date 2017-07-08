/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafxmvc.model.domain.ContaCorrente;
import javafxmvc.model.domain.Cupom;
import javafxmvc.model.domain.OperacaoBancaria;
/**
 *
 * @author Lara
 */
public class CupomDAO {
     private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Cupom cupom) {
        String sql = "INSERT INTO cupom(validade, operacao_cd, conta_id, agencia_id) VALUES(?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(cupom.getValidade()));
            stmt.setInt(2, cupom.getCdOperacao());
            stmt.setInt(3, cupom.getCdConta());
            stmt.setInt(4, cupom.getCdAgencia());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CupomDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Cupom cupom) {
        String sql = "UPDATE cupom SET validade=?, operacao_cd=?, conta_id=?, agencia_id=? WHERE cdcupom=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
           stmt.setDate(1, Date.valueOf(cupom.getValidade()));
            stmt.setInt(2, cupom.getCdOperacao());
            stmt.setInt(3, cupom.getCdConta());
            stmt.setInt(4, cupom.getCdAgencia());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CupomDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Cupom cupom) {
        String sql = "DELETE FROM cupom WHERE cdcupom=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, cupom.getNumCupom());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CupomDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Cupom> listar() {
        String sql = "SELECT * FROM cupom";
        List<Cupom> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                OperacaoBancaria b = new OperacaoBancaria();
                ContaCorrente c = new ContaCorrente();
                Agencia a = new Agencia();
                Cupom cupom = new Cupom();
            
                b.setCdOperacaoBancaria(resultado.getInt("operacao_cd"));
                c.setCdConta(resultado.getInt("conta_id"));
                a.setCdAgencia(resultado.getInt("agencia_id"));
                cupom.setValidade(resultado.getDate("validade").toLocalDate());
                cupom.setNumCupom(resultado.getInt("cdcupom"));  
               
                
              
                        //Obtendo os dados completos da agencia associada ao emprestimo
                        //// ?????????????????????????????????????????????????????????????//
                OperacaoBancariaDAO operacaoDAO = new OperacaoBancariaDAO();
                operacaoDAO.setConnection(connection);
                b = operacaoDAO.buscar(b);
                ContaCorrenteDAO contaDAO = new ContaCorrenteDAO();
                contaDAO.setConnection(connection);
                c = contaDAO.buscar(c);
                 AgenciaDAO agenciaDAO = new AgenciaDAO();
                agenciaDAO.setConnection(connection);
                a = agenciaDAO.buscar(a);
                
                 cupom.setOperacao(b);
                 cupom.setConta(c);
                 cupom.setAgencia(a);
                
                retorno.add(cupom);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CupomDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Cupom buscar(Cupom cupom) {
        String sql = "SELECT * FROM cupom WHERE cdcupom=?";
        Cupom retorno = new Cupom();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, cupom.getNumCupom());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                OperacaoBancaria op = new OperacaoBancaria();
                ContaCorrente conta = new ContaCorrente();
                Agencia a = new Agencia();
                cupom.setValidade(resultado.getDate("validade").toLocalDate());               
                //cupom.setCdOperacao(resultado.getInt("operacao_cd"));
                //cupom.setCdConta(resultado.getInt("conta_id"));
                 op.setCdOperacaoBancaria(resultado.getInt("operacao_cd"));
                 conta.setCdConta(resultado.getInt("conta_id"));
                 a.setCdAgencia(resultado.getInt("agencia_id"));
                cupom.setOperacao(op);
                cupom.setConta(conta);
                cupom.setAgencia(a);
                retorno = cupom;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CupomDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
