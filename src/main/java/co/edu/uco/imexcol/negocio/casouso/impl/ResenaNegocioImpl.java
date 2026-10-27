package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.negocio.casouso.ResenaNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarFechaNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarIdNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarLongitudTextoEsValida;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarNumeroEnRango;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarTextoEsObligatorio;
import co.edu.uco.imexcol.negocio.dominio.ResenaDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class ResenaNegocioImpl implements ResenaNegocio {

    private static final String CASO_USO = "ResenaNegocio";

    public ResenaNegocioImpl() {
        super();
    }

    @Override
    public void registrar(final ResenaDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new ResenaDominio());
        validarDatosComunes(dominioSeguro);
        throw operacionPendiente("registrar");
    }

    @Override
    public void actualizar(final ResenaDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new ResenaDominio());
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominioSeguro.getId(), "reseña");
        validarDatosComunes(dominioSeguro);
        throw operacionPendiente("actualizar");
    }

    @Override
    public void eliminar(final UUID id) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(id, "reseña");
        throw operacionPendiente("eliminar");
    }

    @Override
    public List<ResenaDominio> consultar(final ResenaDominio filtros) {
        UtilObjeto.obtenerValorDefecto(filtros, new ResenaDominio());
        return new ArrayList<>();
    }

    private void validarDatosComunes(final ResenaDominio dominio) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominio.getProducto().getId(),
                "producto asociado a la reseña");
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominio.getCliente().getId(),
                "cliente asociado a la reseña");

        ValidarNumeroEnRango.ejecutarValidacion(dominio.getCalificacion(),
                "calificación de la reseña", 1, 5);

        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getComentario(), "comentario de la reseña");
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getComentario(),
                "comentario de la reseña", 5, 1000);

        ValidarFechaNoEsValorPorDefecto.ejecutarValidacion(dominio.getFecha(), "fecha de la reseña");
    }

    private static ImexcolNegocioException operacionPendiente(final String operacion) {
        return ImexcolNegocioException.crear(
                MensajesEnum.ERROR_USUARIO_CASO_USO_DAO_PENDIENTE.getContenido(),
                MensajesEnum.ERROR_TECNICO_CASO_USO_DAO_PENDIENTE.getContenido().formatted(operacion, CASO_USO));
    }
}
