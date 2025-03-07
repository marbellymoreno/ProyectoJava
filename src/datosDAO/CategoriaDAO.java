package datosDAO;

import datos.interfaces.CRUDGeneralInterface;
import entidades.Categoria;
import database.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class CategoriaDAO implements CRUDGeneralInterface<Categoria> {

    private final Conexion conectar;
    private PreparedStatement ps;
    private ResultSet rs;
    private boolean resp;

    public CategoriaDAO() {
        conectar = Conexion.getInstance();
    }

    @Override
    public List<Categoria> getAll(String list) {
        List<Categoria> registros = new ArrayList<>();
        try {
            ps = conectar.getConnection().prepareStatement("SELECT * FROM categoria WHERE nombre LIKE ?");
            ps.setString(1, "%" + list + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                registros.add(new Categoria(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4)));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            closeResources();
        }
        return registros;
    }

    @Override
    public boolean insert(Categoria object) {
        resp = false;
        try {
            ps = conectar.getConnection().prepareStatement("INSERT INTO categoria (nombre, descripcion, estado) VALUES (?, ?, 1)");
            ps.setString(1, object.getNombre());
            ps.setString(2, object.getDescripcion());
            resp = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            closeResources();
        }
        return resp;
    }

    @Override
    public boolean update(Categoria object) {
        resp = false;
        try {
            ps = conectar.getConnection().prepareStatement("UPDATE categoria SET nombre=?, descripcion=?, estado=1 WHERE id=?");
            ps.setString(1, object.getNombre());
            ps.setString(2, object.getDescripcion());
            ps.setInt(3, object.getID());
            resp = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            closeResources();
        }
        return resp;
    }

@Override
    public boolean onVariable(int id) {
        try {
            ps = conectar.getConnection().prepareStatement(
                    "UPDATE categoria SET estado=1 WHERE id= ?"
            );
            ps.setInt(1, id);  // Establece el ID en el parámetro de la consulta

            // Ejecuta la actualización y verifica si se modificaron filas
            if (ps.executeUpdate() > 0) {
                resp = true;  // Si la actualización fue exitosa, cambia el estado de la variable
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());  // Muestra el error en caso de fallo
        } finally {
            try {
                if (ps != null) {
                    ps.close();  // Asegúrate de cerrar el PreparedStatement después de su uso
                }
            } catch (SQLException e) {
                // Manejo de errores al cerrar el PreparedStatement (opcional)
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            conectar.desconectar();  // Desconecta la base de datos
        }
        return resp;  // Retorna el resultado de la operación
    }

    @Override
    public boolean offVariable(int id) {
        resp = false;
        try {
            ps = conectar.getConnection().prepareStatement("UPDATE categoria SET estado=0 WHERE id=?");
            ps.setInt(1, id);
            resp = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            closeResources();
        }
        return resp;
    }

    @Override
    public boolean exist(String text) {
        resp = false;
        try {
            ps = conectar.getConnection().prepareStatement("SELECT nombre FROM categoria WHERE id = ?");
            ps.setInt(1, Integer.parseInt(text));
            rs = ps.executeQuery();
            resp = rs.next();
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            closeResources();
        }
        return resp;
    }

    @Override
    public int total() {
        int totalRegistro = 0;
        try {
            ps = conectar.getConnection().prepareStatement("SELECT COUNT(id) AS total FROM categoria");
            rs = ps.executeQuery();
            if (rs.next()) {
                totalRegistro = rs.getInt("total");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            closeResources();
        }
        return totalRegistro;
    }

    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cerrar recursos: " + e.getMessage());
        }
    }
}
