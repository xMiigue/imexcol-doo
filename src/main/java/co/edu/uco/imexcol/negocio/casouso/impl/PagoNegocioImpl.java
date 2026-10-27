package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
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
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class PagoNegocioImpl implements PagoNegocio {

    private final FabricaDAO fabrica;
    private final PagoEntidadAssembler ensamblador;

    public PagoNegocioImpl(final FabricaDAO fabrica) {
        super();
        if (UtilObjeto.esNulo(fabrica)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_FACTORY_NO_INICIALIZADA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_FACTORY_NO_INICIALIZADA.getContenido());
        }
        this.fabrica = fabrica;
        this.ensamblador = PagoEntidadAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final PagoDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new PagoDominio());
        validarDatosComunes(dominioSeguro);
        dominioSeguro.setId(UUID.randomUUID());
        fabrica.obtenerPagoDAO().acceder(ensamblador.ensamblarEntidad(dominioSeguro));
    }

    @Override
    public void actualizar(final PagoDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new PagoDominio());
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominioSeguro.getId(), "pago");
        validarDatosComunes(dominioSeguro);
        fabrica.obtenerPagoDAO().actualizar(ensamblador.ensamblarEntidad(dominioSeguro));
    }

    @Override
    public void eliminar(final UUID id) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(id, "pago");
        fabrica.obtenerPagoDAO().eliminar(id);
    }

    @Override
    public List<PagoDominio> consultar(final PagoDominio filtros) {
        final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new PagoDominio());
        final var entidadFiltro = ensamblador.ensamblarEntidad(filtroSeguro);
        final var entidades = fabrica.obtenerPagoDAO().consultarPorFiltro(entidadFiltro);
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
