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
import javafxmvc.model.domain.ClienteEmprestimo;
import javafxmvc.model.domain.Emprestimo;
/**
 *
 * @author Lara
 */
public class ClienteEmprestimoDAO {
        private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(ClienteEmprestimo clienteemp) {
        String sql = "INSERT INTO cliente_possui_emprestimo(cd_emprestimo, cd_cliente) VALUES(?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, clienteemp.getCdEmprestimo());
            stmt.setInt(2, clienteemp.getCdCliente());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteEmprestimoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /*
    public boolean alterar(ClienteEmprestimo clienteemp) {
        String sql = "UPDATE cliente_possui_emprestimo SET cd_emprestimo=?, cd_cliente=? WHERE cdEmprestimo=?, cd_cliente=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, clienteemp.getCdEmprestimo());
            stmt.setInt(2, clienteemp.getCdCliente());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteEmprestimoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }*/

    public boolean remover(ClienteEmprestimo clienteemp) {
        String sql = "DELETE FROM cliente_possui_emprestimo WHERE cd_emprestimo=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, clienteemp.getCdEmprestimo());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteEmprestimoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<ClienteEmprestimo> listar() {
        String sql = "SELECT * FROM cliente_possui_emprestimo";
        List<ClienteEmprestimo> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                ClienteEmprestimo clienteemp = new ClienteEmprestimo();
               // Agencia f = new Agencia();
                Emprestimo e = new Emprestimo();
                Cliente c = new Cliente();
                e.setCdEmprestimo(resultado.getInt("cd_emprestimo"));
                c.setCdCliente(resultado.getInt("cd_cliente"));
                //f.setCdAgencia(resultado.getInt("cd_agencia"));
                
              
                        //Obtendo os dados completos da agencia associada ao emprestimo
                        //// ?????????????????????????????????????????????????????????????//
             
                ClienteDAO clienteDAO = new ClienteDAO();
                clienteDAO.setConnection(connection);
                c = clienteDAO.buscar(c);
                EmprestimoDAO empDAO = new EmprestimoDAO();
                empDAO.setConnection(connection);
                e = empDAO.buscar(e);
                
                clienteemp.setEmprestimo(e);
                clienteemp.setCliente(c);
       
                
                retorno.add(clienteemp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteEmprestimoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

   /* public ClienteEmprestimo buscar(ClienteEmprestimo clienteemp) {
        String sql = "SELECT * FROM emprestimo WHERE cd_emprestimo=?, cd_cliente=?";
        ClienteEmprestimo retorno = new ClienteEmprestimo();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, clienteemp.getCdEmprestimo());
             stmt.setInt(2, clienteemp.getCdCliente());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                ClienteDAO clienteDAO = new ClienteDAO();
                clienteDAO.setConnection(connection);
               EmprestimoDAO empDAO = new EmprestimoDAO();
                clienteDAO.setConnection(connection);
                Emprestimo e = new Emprestimo();
                Cliente c = new Cliente();
                 e.setCdEmprestimo(resultado.getInt("cd_emprestimo"));
                 c.setCdCliente(resultado.getInt("cd_cliente"));
                 c = clienteDAO.buscar(c);
                 e = empDAO.buscar(e);
                clienteemp.setEmprestimo(e);
                clienteemp.setCliente(c);
                retorno = clienteemp;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteEmprestimoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }*/
    
      public ClienteEmprestimo buscarPorCodigo(ClienteEmprestimo clienteemp) {
        String sql = "SELECT * FROM emprestimo WHERE cd_emprestimo=?";
        ClienteEmprestimo retorno = new ClienteEmprestimo();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, clienteemp.getCdEmprestimo());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                ClienteDAO clienteDAO = new ClienteDAO();
                clienteDAO.setConnection(connection);
               
                Cliente c = new Cliente();
            
                 c.setCdCliente(resultado.getInt("cd_cliente"));
                 c = clienteDAO.buscar(c);
                
               
                clienteemp.setCliente(c);
                retorno = clienteemp;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteEmprestimoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
