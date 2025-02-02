/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author marbellymoreno
 */
public class Categoria 
{
    private int ID;
    private String Nombre;
    private String Descripcion;
    private boolean Activo;

    public Categoria() 
    {
    }

    public Categoria(int ID, String Nombre, String Descripcion, boolean Activo) 
    {
        this.ID = ID;
        this.Nombre = Nombre;
        this.Descripcion = Descripcion;
        this.Activo = Activo;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public boolean isActivo() {
        return Activo;
    }

    public void setActivo(boolean Activo) {
        this.Activo = Activo;
    }

    @Override
    public String toString() {
        return "Categoria{" + "ID=" + ID + ", Nombre=" + Nombre + ", Descripcion=" + Descripcion + ", Activo=" + Activo + '}';
    }
    
}