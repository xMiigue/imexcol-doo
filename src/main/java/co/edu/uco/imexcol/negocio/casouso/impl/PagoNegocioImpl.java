package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.PagoDAO;
import co.edu.uco.imexcol.negocio.assembler.entidad.impl.PagoEntidadAssembler;
import co.edu.uco.imexcol.negocio.casouso.PagoNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarFechaNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarIdNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarNumeroEsMayorACero;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarTextoEsObligatorio;
import co.edu.uco.imexcol.negocio.casouso.validador.pago.ValidarEstadoPagoEsValido;
import co.edu.uco.imexcol.negocio.dominio.PagoDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

/**
 * Casos de uso del objeto de dominio Pago.
 *
 * Políticas de negocio aplicadas (referencia documentación de políticas):
 * <ul>
 *   <li>PAG-POL-01: Validación de datos requeridos.</li>
 *   <li>PAG-POL-02: El pedido asociado debe existir.</li>
 *   <li>PAG-POL-03: El método de pago debe estar activo.</li>
 * </ul>
 */
public final class PagoNegocioImpl implements PagoNegocio {

    private final PagoDAO dao;
    private final PagoEntidadAssembler ensamblador;

    public PagoNegocioImpl(final PagoDAO dao) {
        super();
        if (UtilObjeto.esNulo(dao)) {
            throw ImexcolException.crear(
                    MensajesEnum.ERROR_USUARIO_FACTORY_NO_INICIALIZADA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_FACTORY_NO_INICIALIZADA.getContenido(),
                    Lugar.NEGOCIO);
        }
        this.dao = dao;
        this.ensamblador = PagoEntidadAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final PagoDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new PagoDominio());
        validarDatosComunes(dominioSeguro);
        // TODO: validar que el monto del pago no exceda el total del pedido asociado.
        dominioSeguro.setId(UUID.randomUUID());
        dao.acceder(ensamblador.ensamblarEntidad(dominioSeguro));
    }

    @Override
    public void actualizar(final PagoDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new PagoDominio());
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominioSeguro.getId(), "pago");
        validarDatosComunes(dominioSeguro);
        dao.actualizar(ensamblador.ensamblarEntidad(dominioSeguro));
    }

    @Override
    public void eliminar(final UUID id) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(id, "pago");
        dao.eliminar(id);
    }

    @Override
    public List<PagoDominio> consultar(final PagoDominio filtros) {
        final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new PagoDominio());
        final var entidadFiltro = ensamblador.ensamblarEntidad(filtroSeguro);
        final var entidades = dao.consultarPorFiltro(entidadFiltro);
        return ensamblador.ensamblarDominio(entidades);
    }

    private void validarDatosComunes(final PagoDominio dominio) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominio.getPedido().getId(),
                "pedido asociado al pago");
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominio.getMetodoPago().getId(),
                "método de pago asociado al pago");

        ValidarNumeroEsMayorACero.ejecutarValidacion(dominio.getMonto(), "monto del pago");
        ValidarFechaNoEsValorPorDefecto.ejecutarValidacion(dominio.getFechaPago(), "fecha del pago");

        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getEstado(), "estado del pago");
        ValidarEstadoPagoEsValido.ejecutarValidacion(dominio.getEstado());
    }
}
