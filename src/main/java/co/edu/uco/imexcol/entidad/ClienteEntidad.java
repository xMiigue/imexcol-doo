package co.edu.uco.imexcol.entidad;

import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilBooleano;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class ClienteEntidad {

    private UUID id;
    private String nombre;
    private String apellido;
    private String correoElectronico;
    private String contrasena;
    private String telefono;
    private boolean estado;

    public ClienteEntidad() {
        setId(UtilUUID.obtenerValorPorDefecto());
        setNombre(UtilTexto.VACIO);
        setApellido(UtilTexto.VACIO);
        setCorreoElectronico(UtilTexto.VACIO);
        setContrasena(UtilTexto.VACIO);
        setTelefono(UtilTexto.VACIO);
        setEstado(UtilBooleano.obtenerValorPorDefecto());
    }

    public ClienteEntidad(final UUID id) {
        setId(id);
        setNombre(UtilTexto.VACIO);
        setApellido(UtilTexto.VACIO);
        setCorreoElectronico(UtilTexto.VACIO);
        setContrasena(UtilTexto.VACIO);
        setTelefono(UtilTexto.VACIO);
        setEstado(UtilBooleano.obtenerValorPorDefecto());
    }

    public ClienteEntidad(final UUID id, final String nombre, final String apellido, final String correoElectronico,
                          final String contrasena, final String telefono, final boolean estado) {
        setId(id);
        setNombre(nombre);
        setApellido(apellido);
        setCorreoElectronico(correoElectronico);
        setContrasena(contrasena);
        setTelefono(telefono);
        setEstado(estado);
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = UtilUUID.obtenerValorDefecto(id);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = UtilTexto.aplicarTrim(nombre);
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(final String apellido) {
        this.apellido = UtilTexto.aplicarTrim(apellido);
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(final String correoElectronico) {
        this.correoElectronico = UtilTexto.aplicarTrim(correoElectronico);
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(final String contrasena) {
        this.contrasena = UtilTexto.aplicarTrim(contrasena);
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(final String telefono) {
        this.telefono = UtilTexto.aplicarTrim(telefono);
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(final boolean estado) {
        this.estado = estado;
    }
}
