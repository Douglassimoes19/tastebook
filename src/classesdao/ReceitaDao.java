package classesdao;

import classesmodel.Ingrediente;
import classesmodel.Receita;
import classesmodel.Usuario;
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
            String sql = "INSERT INTO receita (titulo, categoria, modo_preparo, tempo_de_preparo, id_usuario) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, receita.getTitulo());
                stmt.setString(2, receita.getCategoria());
                stmt.setString(3, receita.getModoPreparo());
                stmt.setString(4, receita.getTempoPreparo());
                stmt.setInt(5, receita.getUsuario().getId());

                int result = stmt.executeUpdate();
                if (result > 0) {
                    ResultSet rs = stmt.getGeneratedKeys();
                    if (rs.next()) {
                        int receitaId = rs.getInt(1);
                        receita.setId(receitaId);
                        IngredienteDao ing_Dao = new IngredienteDao();
                        for (Ingrediente ingrediente : receita.getIngredientes()) {
                            int idIngrediente = ing_Dao.buscarIngrediente2(ingrediente.getNome());
                            String sqlRelacao = "insert into receita_ingrediente (id_receita, id_ingrediente) VALUES (?, ?)";
                            try (PreparedStatement stmt2 = conn.prepareStatement(sqlRelacao)) {
                                stmt2.setInt(1, receitaId);
                                stmt2.setInt(2, idIngrediente);
                                stmt2.executeUpdate();
                            }
                        }
                        System.out.println("Receita cadastrada. ID: " + receitaId);
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
            String sql = "UPDATE receita SET titulo = ?, categoria = ?, modoPreparo = ?,tempoPreparo, categoria = ?, id_usuario = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, receita.getTitulo());
                stmt.setString(3, receita.getModoPreparo());
                stmt.setString(4, receita.getCategoria());
                stmt.setInt(5, receita.getUsuario().getId());
                stmt.setInt(6, receita.getId());

                int result = stmt.executeUpdate();
                if (result > 0) {
                    System.out.println("Receita alterada. ID: " + receita.getId());
                    return true;
                }

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
        String sql = " SELECT r.id, r.titulo, r.modo_preparo, r.categoria, r.tempo_preparo," +
                "u.id AS usuario_id, u.nome AS usuario_nome FROM receita r " +
                " JOIN usuario u ON r.id_usuario = u.id WHERE r.id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Receita receita = new Receita();
                    receita.setId(rs.getInt("id"));
                    receita.setTitulo(rs.getString("titulo"));
                    receita.setModoPreparo(rs.getString("modo_preparo"));
                    receita.setCategoria(rs.getString("categoria"));
                    receita.setTempoPreparo(rs.getString("tempo_preparo"));

                    // Instancia o usuário com nome e id
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("usuario_id"));
                    usuario.setNome(rs.getString("usuario_nome"));
                    receita.setUsuario(usuario);

                    // Busca os ingredientes
                    List<Ingrediente> ingredientes = buscarIngredientesDaReceita(id);
                    receita.setIngredientes(ingredientes.toArray(new Ingrediente[0]));

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
        String sql = " SELECT r.id, r.titulo, r.modo_preparo, r.categoria, r.tempo_preparo," +
                "u.id AS usuario_id, u.nome AS usuario_nome FROM receita r " +
                " JOIN usuario u ON r.id_usuario = u.id WHERE r.id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Receita receita = new Receita();
                receita.setId(rs.getInt("id"));
                receita.setTitulo(rs.getString("titulo"));
                receita.setModoPreparo(rs.getString("modo_preparo"));
                receita.setCategoria(rs.getString("categoria"));
                receita.setTempoPreparo(rs.getString("tempo_preparo"));

                // Instancia o usuário com nome e id
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("usuario_id"));
                usuario.setNome(rs.getString("usuario_nome"));
                receita.setUsuario(usuario);

                // Busca os ingredientes
                List<Ingrediente> ingredientes = buscarIngredientesDaReceita(receita.getId());
                receita.setIngredientes(ingredientes.toArray(new Ingrediente[0]));


            }
            return receitas;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Ingrediente> buscarIngredientesDaReceita(int idReceita) {
        List<Ingrediente> ingredientes = new ArrayList<>();
        String sql = """
        SELECT i.id, i.nome
        FROM ingrediente i
        INNER JOIN receita_ingrediente ri ON i.id = ri.id_ingrediente
        WHERE ri.id_receita = ?
    """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idReceita);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Ingrediente ingrediente = new Ingrediente();
                    ingrediente.setId(rs.getInt("id"));
                    ingrediente.setNome(rs.getString("nome"));
                    ingredientes.add(ingrediente);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar ingredientes da receita: " + e.getMessage());
            e.printStackTrace();
        }

        return ingredientes;
    }
}
