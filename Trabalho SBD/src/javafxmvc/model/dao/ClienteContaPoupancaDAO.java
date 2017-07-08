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
import javafxmvc.model.domain.Cliente;
import javafxmvc.model.domain.ClienteContaPoupanca;
import javafxmvc.model.domain.ContaPoupanca;
/**
 *
 * @author Lara
 */
public class ClienteContaPoupancaDAO {
      private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(ClienteContaPoupanca clienteConta) {
     String sql = "INSERT INTO cliente_possui_contapoupanca(cliente_id, conta_id, agencia_id) VALUES(?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, clienteConta.getCdCliente());
            stmt.setInt(2, clienteConta.getCdConta());
             stmt.setInt(3, clienteConta.getCdAgencia());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteContaPoupancaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(ClienteContaPoupanca clienteConta) {
        String sql = "UPDATE cliente_possui_contapoupanca SET cliente_id=?,conta_id=?, agencia_id=? WHERE conta_id=?, cliente_id=?, agencia_id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, clienteConta.getCdCliente());
            stmt.setInt(2, (clienteConta.getCdConta()));
             stmt.setInt(3, clienteConta.getCdAgencia());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteContaPoupancaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(ClienteContaPoupanca clienteConta) {
        String sql = "DELETE FROM cliente_possui_contapoupanca WHERE cliente_id=?, conta_id=?, agencia_id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, clienteConta.getCdCliente());
            stmt.setInt(2, clienteConta.getCdConta());
             stmt.setInt(3, clienteConta.getCdAgencia());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteContaCorrenteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<ClienteContaPoupanca> listar() {
        String sql = "SELECT * FROM cliente_possui_contapoupanca";
        List<ClienteContaPoupanca> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                ClienteContaPoupanca clienteConta = new ClienteContaPoupanca();
                Cliente f = new Cliente();
                ContaPoupanca c = new ContaPoupanca();
                Agencia a = new Agencia();
                clienteConta.setContaCodigo(resultado.getInt("conta_id"));
                clienteConta.setClienteCodigo(resultado.getInt("cliente_id"));
                c.setCdConta(resultado.getInt("conta_id"));
                f.setCdCliente(resultado.getInt("cliente_id"));
                a.setCdAgencia(resultado.getInt("agencia_id"));

                        //Obtendo os dados completos da associado ao clienteConta
                ClienteDAO clienteDAO = new ClienteDAO();
                clienteDAO.setConnection(connection);
                f = clienteDAO.buscar(f);
                ContaPoupancaDAO contaDAO = new ContaPoupancaDAO();
                contaDAO.setConnection(connection);
                c = contaDAO.buscar(c);
                AgenciaDAO agenciaDAO = new AgenciaDAO();
                agenciaDAO.setConnection(connection);
                a = agenciaDAO.buscar(a);
                
                 clienteConta.setCliente(f);
                 clienteConta.setConta(c);
                 clienteConta.setAgencia(a);
                
                retorno.add(clienteConta);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteContaPoupancaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public ClienteContaPoupanca buscar(ClienteContaPoupanca clienteConta) {
        String sql = "SELECT * FROM cliente_possui_contapoupanca WHERE conta_id=?, cliente_id=?, agencia_id=?";
        ClienteContaPoupanca retorno = new ClienteContaPoupanca();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, clienteConta.getCdCliente());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                Cliente f = new Cliente();
                ContaPoupanca c = new ContaPoupanca();
                Agencia a = new Agencia();
                c.setCdConta(resultado.getInt("conta_id"));
                f.setCdCliente(resultado.getInt("cliente_id"));
                a.setCdAgencia(resultado.getInt("agencia_id"));
                //clienteConta.setContaCodigo(resultado.getInt("conta_id"));
                //clienteConta.setClienteCodigo(resultado.getInt("cliente_id"));
                clienteConta.setCliente(f);
                clienteConta.setConta(c);
                clienteConta.setAgencia(a);
                retorno = clienteConta;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteContaCorrenteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
