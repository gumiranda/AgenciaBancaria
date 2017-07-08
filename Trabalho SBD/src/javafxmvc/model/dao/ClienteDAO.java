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
import javafxmvc.model.domain.Cliente;
import javafxmvc.model.domain.Funcionario;


public class ClienteDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Cliente cliente) {
        String sql = "INSERT INTO clientes(nome, cpf, telefone,endereco,cidade,estado,data_de_nascimento,nro_funcional_funcionario) VALUES(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEndereco());
            stmt.setString(5, cliente.getCidade());
            stmt.setString(6, cliente.getEstado());
            stmt.setDate(7, Date.valueOf(cliente.getData_de_nascimento()));
            stmt.setInt(8, cliente.getFuncionario().getCdFuncionario());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Cliente cliente) {
        String sql = "UPDATE clientes SET nome=?, cpf=?, telefone=?,endereco=?,cidade=?,estado=?,data_de_nascimento=?,nro_funcional_funcionario =? WHERE cdCliente=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEndereco());
            stmt.setString(5, cliente.getCidade());
            stmt.setString(6, cliente.getEstado());
            stmt.setDate(7, Date.valueOf(cliente.getData_de_nascimento()));
            stmt.setInt(8, cliente.getFuncionario().getCdFuncionario());
            stmt.setInt(9, cliente.getCdCliente());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Cliente cliente) {
        String sql = "DELETE FROM clientes WHERE cdCliente=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, cliente.getCdCliente());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Cliente> listar() {
        String sql = "SELECT * FROM clientes";
        List<Cliente> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Cliente cliente = new Cliente();
                Funcionario f = new Funcionario();
                cliente.setCdCliente(resultado.getInt("cdCliente"));
                cliente.setNome(resultado.getString("nome"));
                cliente.setCpf(resultado.getString("cpf"));
                cliente.setTelefone(resultado.getString("telefone"));
                cliente.setEndereco(resultado.getString("endereco"));
                cliente.setCidade(resultado.getString("estado"));
                cliente.setEstado(resultado.getString("cidade"));                
                cliente.setData_de_nascimento(resultado.getDate("data").toLocalDate());
                f.setCdFuncionario(resultado.getInt("nro_funcional_funcionario"));

                        //Obtendo os dados completos do funcionario associado ao cliente
                FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
                funcionarioDAO.setConnection(connection);
                f = funcionarioDAO.buscar(f);
                
                 cliente.setFuncionario(f);
                
                retorno.add(cliente);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Cliente buscar(Cliente cliente) {
        String sql = "SELECT * FROM clientes WHERE cdCliente=?";
        Cliente retorno = new Cliente();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, cliente.getCdCliente());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                Funcionario f = new Funcionario();
                cliente.setNome(resultado.getString("nome"));
                cliente.setCpf(resultado.getString("cpf"));
                cliente.setTelefone(resultado.getString("telefone"));
                cliente.setEndereco(resultado.getString("endereco"));
                cliente.setCidade(resultado.getString("estado"));
                cliente.setEstado(resultado.getString("cidade"));                
                cliente.setData_de_nascimento(resultado.getDate("data").toLocalDate());
                 f.setCdFuncionario(resultado.getInt("nro_funcional_funcionario"));
                cliente.setFuncionario(f);
                retorno = cliente;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
     public Cliente buscarPorCpf(Cliente cliente) {
        String sql = "SELECT * FROM clientes WHERE cpf=?";
        Cliente retorno = new Cliente();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cliente.getCpf());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                Funcionario f = new Funcionario();
                cliente.setNome(resultado.getString("nome"));
                cliente.setCpf(resultado.getString("cpf"));
                cliente.setTelefone(resultado.getString("telefone"));
                cliente.setEndereco(resultado.getString("endereco"));
                cliente.setCidade(resultado.getString("estado"));
                cliente.setEstado(resultado.getString("cidade"));                
                cliente.setData_de_nascimento(resultado.getDate("data").toLocalDate());
                 f.setCdFuncionario(resultado.getInt("nro_funcional_funcionario"));
                cliente.setFuncionario(f);
                retorno = cliente;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
