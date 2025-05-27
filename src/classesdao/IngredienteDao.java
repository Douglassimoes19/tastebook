package classesdao;

import classesmodel.Ingrediente;
import classesutil.Dbutil;

import java.sql.*;
import java.util.ArrayList;

public class IngredienteDao {
    Connection conn;
    public IngredienteDao() {
        this.conn = Dbutil.getConnection();
    }

    public boolean criarIngrediente(Ingrediente ingrediente) {
       if (ingrediente != null) {
           String sql = "INSERT INTO ingrediente(nome) VALUES (?)";
           try(PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                stmt.setString(1, ingrediente.getNome());
                int result = stmt.executeUpdate();
                if(result > 0){
                    return true;
                }
           } catch (SQLException e) {
               throw new RuntimeException(e);
           }
       }
        return false;
    }

    public boolean alterarIngrediente(Ingrediente ingrediente) {
        return false;
    }

    public boolean excluirIngrediente(Ingrediente ingrediente) {
        return false;
    }

    public Ingrediente buscarIngrediente(String nome) {
        if (nome != null) {
            String sql = "SELECT * FROM ingrediente WHERE nome = ?";
            try(PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                stmt.setString(1, nome);
                int result = stmt.executeUpdate();
                if(result > 0){
                    ResultSet rs = stmt.getGeneratedKeys();
                    if(rs.next()){
                        Ingrediente ingrediente = new Ingrediente();
                        ingrediente.setId(rs.getInt(1));
                        ingrediente.setNome(nome);
                        return ingrediente;
                    }
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public int buscarIngrediente2(String nome) {
        if (nome != null) {
            String sql = "SELECT * FROM ingrediente WHERE nome = ?";
            try(PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                stmt.setString(1, nome);
                int result = stmt.executeUpdate();
                if(result > 0){
                    ResultSet rs = stmt.getGeneratedKeys();
                    if(rs.next()){
                        Ingrediente ingrediente = new Ingrediente();
                        ingrediente.setId(rs.getInt(1));
                        ingrediente.setNome(nome);
                        return ingrediente.getId();
                    }
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return 0 ;
    }

    public ArrayList<Ingrediente> listarIngredientes() {
        return null;
    }
}
