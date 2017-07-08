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
import javafxmvc.model.domain.Emprestimo;

/**
 *
 * @author Lara
 */
public class EmprestimoDAO {
     private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Emprestimo emprestimo) {
        String sql = "INSERT INTO emprestimo(cd_agencia, valor, qdeParcelas) VALUES(?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, emprestimo.getcdAgencia());
            stmt.setDouble(2, emprestimo.getValor());
            stmt.setInt(3, emprestimo.getQdeParcelas());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(EmprestimoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Emprestimo emprestimo) {
        String sql = "UPDATE emprestimo SET cd_agencia=?, valor=?,qdeParcelas=? WHERE cdEmprestimo=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, emprestimo.getcdAgencia());
            stmt.setDouble(2, emprestimo.getValor());
            stmt.setInt(3, emprestimo.getQdeParcelas());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(EmprestimoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Emprestimo emprestimo) {
        String sql = "DELETE FROM emprestimo WHERE cdEmprestimo=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, emprestimo.getCdEmprestimo());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(EmprestimoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Emprestimo> listar() {
        String sql = "SELECT * FROM emprestimo";
        List<Emprestimo> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Emprestimo emprestimo = new Emprestimo();
                Agencia f = new Agencia();
                emprestimo.setCdEmprestimo(resultado.getInt("cdEmprestimo"));
            
                emprestimo.setValor(resultado.getDouble("valor"));
                emprestimo.setQdeParcelas(resultado.getInt("qdeParcelas"));  
                f.setCdAgencia(resultado.getInt("cd_agencia"));
                
              
                        //Obtendo os dados completos da agencia associada ao emprestimo
                        //// ?????????????????????????????????????????????????????????????//
                AgenciaDAO ag = new AgenciaDAO();
                ag.setConnection(connection);
                f = ag.buscar(f);
                
                 emprestimo.setAgencia(f);
                
                retorno.add(emprestimo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmprestimoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Emprestimo buscar(Emprestimo emprestimo) {
        String sql = "SELECT * FROM emprestimo WHERE cdEmprestimo=?";
        Emprestimo retorno = new Emprestimo();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, emprestimo.getCdEmprestimo());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                Agencia f = new Agencia();
                emprestimo.setValor(resultado.getDouble("valor"));               
                emprestimo.setQdeParcelas(resultado.getInt("qdeParcelas"));
                 f.setCdAgencia(resultado.getInt("cd_agencia"));
                emprestimo.setAgencia(f);
                retorno = emprestimo;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmprestimoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
