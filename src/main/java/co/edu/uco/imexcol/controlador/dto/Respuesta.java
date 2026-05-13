package co.edu.uco.imexcol.controlador.dto;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;

public final class Respuesta<T> {

    private List<String> mensajes;
    private List<T> datos;
    private boolean exitosa;

    public Respuesta(final List<String> mensajes, final List<T> datos, final boolean exitosa) {
        setMensajes(mensajes);
        setDatos(datos);
        setExitosa(exitosa);
    }

    public static <T> Respuesta<T> crearExitosa() {
        return new Respuesta<>(new ArrayList<String>(), new ArrayList<T>(), true);
    }

    public static <T> Respuesta<T> crearFallida() {
        return new Respuesta<>(new ArrayList<String>(), new ArrayList<T>(), false);
    }

    public static <T> Respuesta<T> crearExitosa(final List<T> datos) {
        return new Respuesta<>(new ArrayList<String>(), datos, true);
    }

    public static <T> Respuesta<T> crearFallida(final List<T> datos) {
        return new Respuesta<>(new ArrayList<String>(), datos, false);
    }

    public List<String> getMensajes() {
        return mensajes;
    }

    public void setMensajes(final List<String> mensajes) {
        this.mensajes = UtilObjeto.obtenerValorDefecto(mensajes, new ArrayList<String>());
    }

    public void agregarMensaje(final String mensaje) {
        if (!UtilTexto.estaVacia(mensaje)) {
            getMensajes().add(mensaje);
        }
    }

    public List<T> getDatos() {
        return datos;
    }

    public void setDatos(final List<T> datos) {
        this.datos = UtilObjeto.obtenerValorDefecto(datos, new ArrayList<T>());
    }

    public boolean isExitosa() {
        return exitosa;
    }

    public void setExitosa(final boolean exitosa) {
        this.exitosa = exitosa;
    }
}
