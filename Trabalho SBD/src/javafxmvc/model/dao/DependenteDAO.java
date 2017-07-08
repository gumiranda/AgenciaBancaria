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
import javafxmvc.model.domain.Dependente;
import javafxmvc.model.domain.Funcionario;


public class DependenteDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Dependente dependente) {
        String sql = "INSERT INTO dependente(nome,nro_funcional_funcionario) VALUES(?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, dependente.getNome());
            stmt.setInt(2, dependente.getFuncionario().getCdFuncionario());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DependenteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Dependente dependente) {
        String sql = "UPDATE dependente SET nome=? WHERE nro_funcional_funcionario=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, dependente.getNome());
            stmt.setInt(2, dependente.getFuncionario().getCdFuncionario());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DependenteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Dependente dependente) {
        String sql = "DELETE FROM dependente WHERE nro_funcional_funcionario=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, dependente.getFuncionario().getCdFuncionario());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DependenteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Dependente> listar() {
        String sql = "SELECT * FROM dependente";
        List<Dependente> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Dependente dependente = new Dependente();
                Funcionario f = new Funcionario();
                dependente.setNome(resultado.getString("nome_do_dependente"));              
                f.setCdFuncionario(resultado.getInt("nro_funcional_funcionario"));

                        //Obtendo os dados completos do funcionario associado ao dependente
                FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
                funcionarioDAO.setConnection(connection);
                f = funcionarioDAO.buscar(f);
                
                 dependente.setFuncionario(f);
                
                retorno.add(dependente);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DependenteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Dependente buscar(Dependente dependente) {
        String sql = "SELECT * FROM dependente WHERE nro_funcional_funcionario=?";
        Dependente retorno = new Dependente();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, dependente.getFuncionario().getCdFuncionario());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                Funcionario f = new Funcionario();
                dependente.setNome(resultado.getString("nome_do_dependente"));
                 f.setCdFuncionario(resultado.getInt("nro_funcional_funcionario"));
                dependente.setFuncionario(f);
                retorno = dependente;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DependenteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
