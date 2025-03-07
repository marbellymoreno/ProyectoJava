/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocios;

import datosDAO.ArticuloDAO;
import datosDAO.CategoriaDAO;
import entidades.Articulo;
import entidades.Categoria;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author marbellymoreno
 */
public class ArticuloControl {
    
    private final ArticuloDAO DATOS;
    private Articulo obj;
    private DefaultTableModel tModel;
    private final CategoriaDAO DATOSCAT;
    public int registrosMostrados;

    public ArticuloControl() {
        this.DATOS = new ArticuloDAO();
        this.obj = new Articulo();
        this.DATOSCAT = new CategoriaDAO();
    }
    
    public DefaultComboBoxModel selectCategoria(){
        DefaultComboBoxModel items = new DefaultComboBoxModel();
        List<Categoria> lista = new ArrayList();
        lista = DATOSCAT.SelectCategoria();
        for ( Categoria item : lista ){
            items.addElement(new Categoria(item.getID(), item.getNombre()));
        }
        return items;
    }
    

    public DefaultTableModel listar(
            String texto,
            int totalPorpagina,
            int numpagina
    ) {
        List<Articulo> lista = new ArrayList();
        lista.addAll(DATOS.getAll(texto, totalPorpagina, numpagina));
        String[] titulos
                = {"id", 
                    "categoria_id",
                    "categoria",
                    "codigo", 
                    "nombre",
                    "precio_venta", 
                    "stock",
                    "descripcion", 
                    "imagen",
                    "estado"
                };
        this.tModel = new DefaultTableModel(null, titulos);

        String estado;
        String[] registro = new String[9];
        this.registrosMostrados = 0;
        for (Articulo item : lista) {
            if (item.isEstado()) {
                estado = "activo";
            } else {
                estado = "Inactivo";
            }

            registro[0] = Integer.toString(item.getIdArticulo());
            registro[1] = Integer.toString(item.getCategoria_id());
            registro[2] = item.getCategoriaNombre();
            registro[3] = item.getCodigo();
            registro[4] = item.getNombre();
            registro[5] = Double.toString(item.getPrecio_venta());
            registro[6] = Integer.toString(item.getStock());
            registro[7] = item.getDesscriocion();
            registro[8] = item.getImagen();
            registro[9] = Boolean.toString(item.isEstado());
            this.registrosMostrados = this.registrosMostrados + 1;
            this.tModel.addRow(registro);
        }
        return this.tModel;
    }

    public String Insertar(
            int categoriaId,
            String codigo,
            String nombre,
            double precio,
            int stock,
            String descripcion,
            String imagen
    ) {
        if (DATOS.exist(nombre)) {
            return "Registro ya existe";
        } else {
            obj.setNombre(nombre);
            obj.setCategoria_id(categoriaId);
            obj.setCodigo(codigo);
            obj.setNombre(nombre);
            obj.setPrecio_venta(precio);
            obj.setDesscriocion(descripcion);
            obj.setImagen(imagen);
            if (DATOS.insert(obj)) {
                return "OK";
            } else {
                return "Error en el registro ";
            }
        }
    }

    public String actualizar(
        int idArticulo,
        int categoriaId,
        String codigo,
        String nombre,
        String nombreAnterior,
        double precio,
        int stock,
        String descripcion,
        String imagen
    ) {
        if (!nombre.equals(nombreAnterior)) {

            obj.setIdArticulo(idArticulo);
            obj.setNombre(nombre);
            obj.setCategoria_id(categoriaId);
            obj.setCodigo(codigo);
            obj.setNombre(nombre);
            obj.setPrecio_venta(precio);
            obj.setDesscriocion(descripcion);
            obj.setImagen(imagen);

            // Primero verificamos si el nombre ya existe en la base de datos
            if (DATOS.exist(nombre)) {
                return "El objeto ya existe";
            }
            if (DATOS.update(obj)) {
                return "OK";
            } else {
                return "Error en la actualización";
            }

        } else {
            return "Error en la actualización";
        }
    }

    public String desactivar(int id) {
        if (DATOS.offVariable(id)) {
            return "OK";
        } else {
            return "No se puede desactivar el registro";
        }
    }

    public String activar(int id) {
        if (DATOS.onVariable(id)) {
            return "OK";
        } else {
            return "No se puede activar el registro";
        }
    }

    public int total() {
        return DATOS.total();
    }

    public int totalMostrados() {
        return this.registrosMostrados;
    }

    public int obtenerIDDesdeBD() {
        return DATOS.getID();
    }
}
