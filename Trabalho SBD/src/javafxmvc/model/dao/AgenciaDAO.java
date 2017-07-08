package javafxmvc.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafxmvc.model.domain.Agencia;

public class AgenciaDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Agencia agencia) {
        String sql = "INSERT INTO agencia(nome,estado,municipio) VALUES(?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, agencia.getNome());
            stmt.setString(2, agencia.getEstado());
            stmt.setString(3, agencia.getMunicipio());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AgenciaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Agencia agencia) {
        String sql = "UPDATE agencia SET nome=?,estado=?,municipio=? WHERE cdagencia=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, agencia.getNome());
            stmt.setString(2, agencia.getEstado());
            stmt.setString(3, agencia.getMunicipio());
            stmt.setInt(4, agencia.getCdAgencia());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AgenciaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Agencia agencia) {
        String sql = "DELETE FROM agencia WHERE cdAgencia=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, agencia.getCdAgencia());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AgenciaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Agencia> listar() {
        String sql = "SELECT * FROM agencia";
        List<Agencia> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Agencia agencia = new Agencia();
                agencia.setCdAgencia(resultado.getInt("cdAgencia"));
                agencia.setNome(resultado.getString("nome"));
                agencia.setEstado(resultado.getString("estado"));
                agencia.setMunicipio(resultado.getString("municipio"));
                retorno.add(agencia);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AgenciaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Agencia buscar(Agencia agencia) {
        String sql = "SELECT * FROM agencia WHERE cdAgencia=?";
        Agencia retorno = new Agencia();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, agencia.getCdAgencia());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                agencia.setNome(resultado.getString("nome"));
                agencia.setEstado(resultado.getString("estado"));
                agencia.setMunicipio(resultado.getString("municipio"));
                retorno = agencia;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AgenciaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
