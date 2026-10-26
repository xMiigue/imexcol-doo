package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.negocio.casouso.FacturaNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarFechaNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarIdNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarLongitudTextoEsValida;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarNumeroEsMayorACero;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarTextoEsObligatorio;
import co.edu.uco.imexcol.negocio.dominio.FacturaDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

public final class FacturaNegocioImpl implements FacturaNegocio {

    private static final String CASO_USO = "FacturaNegocio";

    public FacturaNegocioImpl() {
        super();
    }

    @Override
    public void registrar(final FacturaDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new FacturaDominio());
        validarDatosComunes(dominioSeguro);
        throw operacionPendiente("registrar");
    }

    @Override
    public void actualizar(final FacturaDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new FacturaDominio());
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominioSeguro.getId(), "factura");
        validarDatosComunes(dominioSeguro);
        throw operacionPendiente("actualizar");
    }

    @Override
    public void eliminar(final UUID id) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(id, "factura");
        throw operacionPendiente("eliminar");
    }

    @Override
    public List<FacturaDominio> consultar(final FacturaDominio filtros) {
        UtilObjeto.obtenerValorDefecto(filtros, new FacturaDominio());
        return new ArrayList<>();
    }

    private void validarDatosComunes(final FacturaDominio dominio) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominio.getPedido().getId(),
                "pedido asociado a la factura");

        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getNumeroFactura(),
                "número de factura");
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getNumeroFactura(),
                "número de factura", 1, 50);

        ValidarFechaNoEsValorPorDefecto.ejecutarValidacion(dominio.getFechaEmision(),
                "fecha de emisión de la factura");
        ValidarNumeroEsMayorACero.ejecutarValidacion(dominio.getTotal(), "total de la factura");
    }

    private static ImexcolException operacionPendiente(final String operacion) {
        return ImexcolException.crear(
                MensajesEnum.ERROR_USUARIO_CASO_USO_DAO_PENDIENTE.getContenido(),
                MensajesEnum.ERROR_TECNICO_CASO_USO_DAO_PENDIENTE.getContenido().formatted(operacion, CASO_USO),
                Lugar.NEGOCIO);
    }
}
