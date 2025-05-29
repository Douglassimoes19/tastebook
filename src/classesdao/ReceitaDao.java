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
            String sql = "INSERT INTO receita (titulo, categoria, modoPreparo, tempoPreparo, usuario_id) VALUES (?, ?, ?, ?, ?)";
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
                            String sqlRelacao = "insert into receita_ingrediente (receita_id, ingrediente_id) VALUES (?, ?)";
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
            String sql = "UPDATE receita SET titulo = ?, categoria = ?, modoPreparo = ?,tempoPreparo = ?, usuario_id = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, receita.getTitulo());
                stmt.setString(2, receita.getCategoria());
                stmt.setString(3, receita.getModoPreparo());
                stmt.setString(4, receita.getTempoPreparo());
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
            String sql = "DELETE FROM receita_ingrediente WHERE receita_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, receita.getId());
                int result = stmt.executeUpdate();
                if (result > 0) {
                    String sql2 = "DELETE FROM receita WHERE id = ?";
                    try (PreparedStatement stmt2 = conn.prepareStatement(sql2)) {
                        stmt2.setInt(1, receita.getId());
                        result = stmt2.executeUpdate();
                        if (result > 0) {
                            System.out.println("Receita excluída. ");
                            return true;
                        }
                    }

                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public Receita buscarReceita(int id) {
        String sql = " SELECT r.id, r.titulo, r.modoPreparo, r.categoria, r.tempoPreparo," +
                "u.id AS usuario_id, u.nomeUsuario AS usuario_Nome FROM receita r " +
                " JOIN usuario u ON r.usuario_id = u.id WHERE r.id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Receita receita = new Receita();
                    receita.setId(rs.getInt("id"));
                    receita.setTitulo(rs.getString("titulo"));
                    receita.setModoPreparo(rs.getString("modoPreparo"));
                    receita.setCategoria(rs.getString("categoria"));
                    receita.setTempoPreparo(rs.getString("tempoPreparo"));

                    // Instancia o usuário com nome e id
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("usuario_id"));
                    usuario.setNome(rs.getString("usuario_Nome"));
                    receita.setUsuario(usuario);

                    // Busca os ingredientes
                    List<Ingrediente> ingredientes = buscarIngredientesDaReceita(id);
                    receita.setIngredientes(ingredientes);

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
        String sql = " SELECT r.id, r.titulo, r.modoPreparo, r.categoria, r.tempoPreparo," +
                "u.id AS usuario_id, u.nomeUsuario AS usuario_nome FROM receita r " +
                " JOIN usuario u ON r.usuario_id = u.id ";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Receita receita = new Receita();
                receita.setId(rs.getInt("id"));
                receita.setTitulo(rs.getString("titulo"));
                receita.setModoPreparo(rs.getString("modoPreparo"));
                receita.setCategoria(rs.getString("categoria"));
                receita.setTempoPreparo(rs.getString("tempoPreparo"));


                // Instancia o usuário com nome e id

                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("usuario_id"));
                UsuarioDao usuarioDao = new UsuarioDao();
                String nomeUser = usuarioDao.buscarNomeUsuario(usuario.getId());
                usuario.setNome(nomeUser);
                receita.setUsuario(usuario);

                // Busca os ingredientes
                List<Ingrediente> ingredientes = buscarIngredientesDaReceita(receita.getId());
                receita.setIngredientes(ingredientes);
                receitas.add(receita);
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
        INNER JOIN receita_ingrediente ri ON i.id = ri.ingrediente_id
        WHERE ri.receita_id = ?
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
