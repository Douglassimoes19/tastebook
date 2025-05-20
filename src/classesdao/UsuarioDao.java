package classesdao;

import classesmodel.Usuario;
import classesutil.Dbutil;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UsuarioDao {

   Connection conn;

    public UsuarioDao() {
        this.conn = Dbutil.getConnection();
    }

    public boolean cadastrarUsuario(Usuario usuario) {
        if (usuario != null) {
            String sql = "insert into usuario (nome, nomeusuario, email, alergias, senha) values(?,?,?,?,?)";

            try(PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                stmt.setString(1, usuario.getNome());
                stmt.setString(2, usuario.getNomeUsuario());
                stmt.setString(3, usuario.getEmail());
                stmt.setString(4, usuario.getAlergias());
                stmt.setString(5, usuario.getSenha());
                int result = stmt.executeUpdate();
                if(result > 0){
                    ResultSet rs = stmt.getGeneratedKeys();
                    if(rs.next()){
                        usuario.setId(rs.getInt(1));
                        System.out.println("O usuario foi criado: " + usuario.getId() );
                        return true;
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else{
            return false;
        }
        return false;
    }

    public boolean alterarUsuario( Usuario usuario ) {

        if (usuario != null) {
            String sql = "UPDATE usuario SET nome = ?, nomeusuario = ?, email = ?, alergias = ?,senha = ? WHERE id = ?";

            try(PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                stmt.setString(1, usuario.getNome());;
                stmt.setString(2, usuario.getNomeUsuario());
                stmt.setString(3, usuario.getEmail());
                stmt.setString(4, usuario.getAlergias());
                stmt.setString(5,usuario.getSenha());
                stmt.setInt(6, usuario.getId());
                int result = stmt.executeUpdate();
                if(result > 0){
                    ResultSet rs = stmt.getGeneratedKeys();
                    if(rs.next()){
                        usuario.setId(rs.getInt(1));
                        System.out.println("O usuario foi Alterado: " + usuario.getId() );
                        return true;
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else{
            return false;
        }


        return false;
    }

    public boolean excluirUsuario(Usuario usuario) {
        if (usuario != null) {
            String sql = "DELETE FROM usuario WHERE id = ?";
            try(PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                stmt.setInt(1, usuario.getId());
                int result = stmt.executeUpdate();
                if(result > 0){
                    return true;
                }
            }catch (SQLException e) {
                throw new RuntimeException(e);

            }
        }
        return false;
    }

    public Usuario buscarUsuario(int id) {
        String sql = "select * from usuario where id = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setInt(1, id);
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setNomeUsuario(rs.getString("nomeUsuario"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setAlergias(Collections.singletonList(rs.getString("alergias")));
                    usuario.setSenha(rs.getString("senha"));
                    return usuario;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "select * from usuario";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
             ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setNomeUsuario(rs.getString("nomeUsuario"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setAlergias(Collections.singletonList(rs.getString("alergias")));
                    usuarios.add(usuario);
                }
                return usuarios;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
