package co.edu.uco.imexcol.negocio.fachada.impl;

import java.util.function.Consumer;
import java.util.function.Function;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolFachadaException;

final class EjecutorTransaccionFachada {

    private EjecutorTransaccionFachada() {
        super();
    }

    static void ejecutar(final Consumer<FabricaDAO> operacion) {
        ejecutarConResultado(fabrica -> {
            operacion.accept(fabrica);
            return Boolean.TRUE;
        });
    }

    static <T> T ejecutarConResultado(final Function<FabricaDAO, T> operacion) {
        final var fabrica = FabricaDAO.obtenerFabrica();
        try {
            fabrica.iniciarTransaccion();
            final var resultado = operacion.apply(fabrica);
            fabrica.confirmarTransaccion();
            return resultado;
        } catch (final ImexcolException excepcion) {
            fabrica.revertirTransaccionSinOcultar(excepcion);
            throw excepcion;
        } catch (final Exception excepcion) {
            fabrica.revertirTransaccionSinOcultar(excepcion);
            throw envolverErrorInesperado(excepcion);
        } finally {
            fabrica.cerrarConexionSinOcultar();
        }
    }

    private static ImexcolFachadaException envolverErrorInesperado(final Exception excepcion) {
        return ImexcolFachadaException.crear(excepcion,
                MensajesEnum.ERROR_USUARIO_FACHADA_OPERACION_INESPERADA.getContenido(),
                MensajesEnum.ERROR_TECNICO_FACHADA_OPERACION_INESPERADA.getContenido());
    }
}
