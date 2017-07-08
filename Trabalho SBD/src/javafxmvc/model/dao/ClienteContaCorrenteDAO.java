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
import javafxmvc.model.domain.ClienteContaCorrente;
import javafxmvc.model.domain.Cliente;
import javafxmvc.model.domain.ContaCorrente;


public class ClienteContaCorrenteDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(ClienteContaCorrente clienteConta) {
        String sql = "INSERT INTO cliente_possui_contacorrente(cliente_id, conta_id, agencia_id) VALUES(?,?, ?)";
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

    public boolean alterar(ClienteContaCorrente clienteConta) {
        String sql = "UPDATE cliente_possui_contacorrente SET conta_id=?,cliente_id=?, agencia_id=? WHERE conta_id=?, cliente_id=?, agencia_id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, clienteConta.getContaCodigo());
            stmt.setInt(2, (clienteConta.getClienteCodigo()));
            stmt.setInt(3, clienteConta.getCdAgencia());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteContaCorrenteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(ClienteContaCorrente clienteConta) {
        String sql = "DELETE FROM cliente_possui_contacorrente WHERE cliente_id=?, conta_id=?, agencia_id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, clienteConta.getCdCliente());
            stmt.setInt(1, clienteConta.getCdConta());
            stmt.setInt(3, clienteConta.getCdAgencia());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteContaCorrenteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    } 
     public List<ContaCorrente> listarConta(Cliente cliente) {
         
              String sql = "SELECT * FROM cliente_possui_contacorrente WHERE  cliente_id=?";
                List<ContaCorrente> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
                        stmt.setInt(1, cliente.getCdCliente());
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                ContaCorrente c = new ContaCorrente();
                c.setCdConta(resultado.getInt("conta_id"));

                ContaCorrenteDAO contaDAO = new ContaCorrenteDAO();
                contaDAO.setConnection(connection);
                c = contaDAO.buscar(c);
                
                retorno.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteContaCorrenteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
   
    }
 public List<ContaCorrente> listarConta() {
        String sql = "SELECT * FROM cliente_possui_contacorrente";
                List<ContaCorrente> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                ContaCorrente c = new ContaCorrente();
                c.setCdConta(resultado.getInt("conta_id"));

                ContaCorrenteDAO contaDAO = new ContaCorrenteDAO();
                contaDAO.setConnection(connection);
                c = contaDAO.buscar(c);
                
                retorno.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteContaCorrenteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
 public List<Cliente> listarCliente() {
        String sql = "SELECT * FROM cliente_possui_contacorrente";
                List<Cliente> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Cliente f = new Cliente();
                f.setCdCliente(resultado.getInt("cliente_id"));

                        //Obtendo os dados completos da associado ao clienteConta
                ClienteDAO clienteDAO = new ClienteDAO();
                clienteDAO.setConnection(connection);
                f = clienteDAO.buscar(f);                
                retorno.add(f);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteContaCorrenteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    public List<ClienteContaCorrente> listar() {
        String sql = "SELECT * FROM cliente_possui_contacorrente";
        List<ClienteContaCorrente> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                ClienteContaCorrente clienteConta = new ClienteContaCorrente();
                Cliente f = new Cliente();
                ContaCorrente c = new ContaCorrente();
                Agencia a = new Agencia();
                clienteConta.setContaCodigo(resultado.getInt("conta_id"));
                clienteConta.setClienteCodigo(resultado.getInt("cliente_id"));
                clienteConta.setCdAgencia(resultado.getInt("agencia_id"));
                c.setCdConta(resultado.getInt("conta_id"));
                f.setCdCliente(resultado.getInt("cliente_id"));
                a.setCdAgencia(resultado.getInt("agencia_id"));

                        //Obtendo os dados completos da associado ao clienteConta
                ClienteDAO clienteDAO = new ClienteDAO();
                clienteDAO.setConnection(connection);
                f = clienteDAO.buscar(f);
                ContaCorrenteDAO contaDAO = new ContaCorrenteDAO();
                contaDAO.setConnection(connection);
                c = contaDAO.buscar(c);
                AgenciaDAO agenciaDAO = new AgenciaDAO();
                agenciaDAO.setConnection(connection);
                a = agenciaDAO.buscar(a);
                
                 clienteConta.setCliente(f);
                 clienteConta.setContacorrente(c);
                 clienteConta.setAgencia(a);
                
                retorno.add(clienteConta);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteContaCorrenteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public ClienteContaCorrente buscar(ClienteContaCorrente clienteConta) {
        String sql = "SELECT * FROM cliente_possui_contacorrente WHERE conta_id=?, cliente_id=?, agencia_id=?";
        ClienteContaCorrente retorno = new ClienteContaCorrente();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, clienteConta.getClienteCodigo());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                Cliente f = new Cliente();
                ContaCorrente c = new ContaCorrente();
                Agencia a = new Agencia();
                a.setCdAgencia(resultado.getInt("agencia_id"));
                c.setCdConta(resultado.getInt("conta_id"));
                f.setCdCliente(resultado.getInt("cliente_id"));
               // clienteConta.setContaCodigo(resultado.getInt("conta_id"));
               // clienteConta.setClienteCodigo(resultado.getInt("cliente_id"));
                //clienteConta.setCdAgencia(resultado.getInt("agencia_id"));
                clienteConta.setCliente(f);
                clienteConta.setContacorrente(c);
                clienteConta.setAgencia(a);
                retorno = clienteConta;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteContaCorrenteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
