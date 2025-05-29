package classesdao;

import classesmodel.Ingrediente;
import classesutil.Dbutil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
                    ResultSet rs = stmt.getGeneratedKeys();
                    if(rs.next()){
                        ingrediente.setId(rs.getInt(1));
                        System.out.println("Ingrediente criado com sucesso! " + ingrediente.toString());
                        return true;
                    }

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
        if (ingrediente != null) {
            String sql = "DELETE FROM ingrediente WHERE id = ?";
            try(PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                stmt.setInt(1, ingrediente.getId());
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

    public Ingrediente buscarIngrediente(String nome) {
        if (nome != null) {
            String sql = "SELECT id, nome FROM ingrediente WHERE nome = ?";
            try(PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                stmt.setString(1, nome);
                ResultSet res  = stmt.executeQuery();
                if(res != null ){

                    if(res.next()){
                        Ingrediente ingrediente = new Ingrediente();
                        ingrediente.setId(res.getInt(1));
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
                ResultSet res  = stmt.executeQuery();
                if(res != null ){

                    if(res.next()){
                        Ingrediente ingrediente = new Ingrediente();
                        ingrediente.setId(res.getInt(1));
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

    public List<Ingrediente> listarIngredientes() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        String sql = "SELECT id, nome FROM ingrediente";
        try(PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Ingrediente ingrediente = new Ingrediente();
                ingrediente.setId(rs.getInt(1));
                ingrediente.setNome(rs.getString(2));
                ingredientes.add(ingrediente);
            }
            return ingredientes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
