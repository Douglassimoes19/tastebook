package classesdao;

import classesmodel.Receita;
import classesutil.Dbutil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReceitaDao {

    Connection conn;

    public ReceitaDao() {
        this.conn = Dbutil.getConnection();
    }

    public boolean cadastrarReceita(Receita receita) {
        if (receita != null) {
            String sql = "INSERT INTO receita (nome, ingredientes, modo_preparo, categoria, id_usuario) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, receita.getNome());
                stmt.setArray(2, receita.getIngredientes());
                stmt.setString(3, receita.getModoPreparo());
                stmt.setString(4, receita.getCategoria());
                stmt.setInt(5, receita.getIdUsuario());

                int result = stmt.executeUpdate();
                if (result > 0) {
                    ResultSet rs = stmt.getGeneratedKeys();
                    if (rs.next()) {
                        receita.setId(rs.getInt(1));
                        System.out.println("Receita cadastrada: " + receita.getId());
                        return true;
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public boolean alterarReceita(Receita receita) {
        if (receita != null) {
            String sql = "UPDATE receita SET nome = ?, ingredientes = ?, modo_preparo = ?, categoria = ?, id_usuario = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, receita.getNome());
                stmt.setString(2, receita.getIngredientes());
                stmt.setString(3, receita.getModoPreparo());
                stmt.setString(4, receita.getCategoria());
                stmt.setInt(5, receita.getIdUsuario());
                stmt.setInt(6, receita.getId());

                int result = stmt.executeUpdate();
                return result > 0;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public boolean excluirReceita(Receita receita) {
        if (receita != null) {
            String sql = "DELETE FROM receita WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, receita.getId());
                int result = stmt.executeUpdate();
                return result > 0;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public Receita buscarReceita(int id) {
        String sql = "SELECT * FROM receita WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Receita receita = new Receita();
                    receita.setId(rs.getInt("id"));
                    receita.setNome(rs.getString("nome"));
                    receita.setIngredientes(rs.getString("ingredientes"));
                    receita.setModoPreparo(rs.getString("modo_preparo"));
                    receita.setCategoria(rs.getString("categoria"));
                    receita.setIdUsuario(rs.getInt("id_usuario"));
                    return receita;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar receita: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<Receita> listarReceitas() {
        List<Receita> receitas = new ArrayList<>();
        String sql = "SELECT * FROM receita";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Receita receita = new Receita();
                receita.setId(rs.getInt("id"));
                receita.setNome(rs.getString("nome"));
                receita.setIngredientes(rs.getString("ingredientes"));
                receita.setModoPreparo(rs.getString("modo_preparo"));
                receita.setCategoria(rs.getString("categoria"));
                receita.setIdUsuario(rs.getInt("id_usuario"));
                receitas.add(receita);
            }
            return receitas;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
