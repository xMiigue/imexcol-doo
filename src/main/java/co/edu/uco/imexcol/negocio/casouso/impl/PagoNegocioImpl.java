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
import co.edu.uco.imexcol.negocio.casouso.validador.metodopago.ValidarMetodoPagoExistePorId;
import co.edu.uco.imexcol.negocio.casouso.validador.pago.ValidarEstadoPagoEsValido;
import co.edu.uco.imexcol.negocio.casouso.validador.pago.ValidarPagoExistePorId;
import co.edu.uco.imexcol.negocio.casouso.validador.pago.ValidarPagoNoTieneFactura;
import co.edu.uco.imexcol.negocio.casouso.validador.pedido.ValidarPedidoExistePorId;
import co.edu.uco.imexcol.negocio.dominio.PagoDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;
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
        asegurarPedidoExista(dominioSeguro);
        asegurarMetodoPagoExista(dominioSeguro);
        asegurarMontoMayorACero(dominioSeguro);
        asegurarEstadoValido(dominioSeguro);
        asignarNuevoIdentificador(dominioSeguro);
        guardar(dominioSeguro);
    }

    @Override
    public void actualizar(final PagoDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new PagoDominio());
        validarDatosComunes(dominioSeguro);
        asegurarPagoExista(dominioSeguro.getId());
        asegurarPedidoExista(dominioSeguro);
        asegurarMetodoPagoExista(dominioSeguro);
        asegurarMontoMayorACero(dominioSeguro);
        asegurarEstadoValido(dominioSeguro);
        modificar(dominioSeguro);
    }

    @Override
    public void eliminar(final UUID id) {
        asegurarPagoExista(id);
        asegurarPagoNoTengaFactura(id);
        borrar(id);
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

        ValidarFechaNoEsValorPorDefecto.ejecutarValidacion(dominio.getFechaPago(), "fecha del pago");
    }

    private void asegurarPedidoExista(final PagoDominio dominio) {
        ValidarPedidoExistePorId.ejecutarValidacion(dominio.getPedido().getId(), fabrica);
    }

    private void asegurarMetodoPagoExista(final PagoDominio dominio) {
        ValidarMetodoPagoExistePorId.ejecutarValidacion(dominio.getMetodoPago().getId(), fabrica);
    }

    private void asegurarMontoMayorACero(final PagoDominio dominio) {
        ValidarNumeroEsMayorACero.ejecutarValidacion(dominio.getMonto(), "monto del pago");
    }

    private void asegurarEstadoValido(final PagoDominio dominio) {
        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getEstado(), "estado del pago");
        ValidarEstadoPagoEsValido.ejecutarValidacion(dominio.getEstado());
    }

    private void asegurarPagoExista(final UUID id) {
        ValidarPagoExistePorId.ejecutarValidacion(id, fabrica);
    }

    private void asegurarPagoNoTengaFactura(final UUID id) {
        ValidarPagoNoTieneFactura.ejecutarValidacion(id, fabrica);
    }

    private void asignarNuevoIdentificador(final PagoDominio dominio) {
        dominio.setId(UtilUUID.generarNuevoUUID());
    }

    private void guardar(final PagoDominio dominio) {
        fabrica.obtenerPagoDAO().acceder(ensamblador.ensamblarEntidad(dominio));
    }

    private void modificar(final PagoDominio dominio) {
        fabrica.obtenerPagoDAO().actualizar(ensamblador.ensamblarEntidad(dominio));
    }

    private void borrar(final UUID id) {
        fabrica.obtenerPagoDAO().eliminar(id);
    }
}
