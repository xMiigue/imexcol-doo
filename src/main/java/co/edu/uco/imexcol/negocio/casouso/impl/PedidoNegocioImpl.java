package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.assembler.entidad.impl.PedidoEntidadAssembler;
import co.edu.uco.imexcol.negocio.casouso.PedidoNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.cliente.ValidarClienteExistePorId;
import co.edu.uco.imexcol.negocio.casouso.validador.direccion.ValidarDireccionExistePorId;
import co.edu.uco.imexcol.negocio.casouso.validador.direccion.ValidarDireccionPerteneceAlCliente;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarFechaNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarIdNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarNumeroEsPositivo;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarTextoEsObligatorio;
import co.edu.uco.imexcol.negocio.casouso.validador.pedido.ValidarEstadoPedidoEsValido;
import co.edu.uco.imexcol.negocio.casouso.validador.pedido.ValidarPedidoExistePorId;
import co.edu.uco.imexcol.negocio.casouso.validador.pedido.ValidarPedidoNoTieneEnvios;
import co.edu.uco.imexcol.negocio.casouso.validador.pedido.ValidarPedidoNoTieneFactura;
import co.edu.uco.imexcol.negocio.casouso.validador.pedido.ValidarPedidoNoTieneLineas;
import co.edu.uco.imexcol.negocio.casouso.validador.pedido.ValidarPedidoNoTienePagos;
import co.edu.uco.imexcol.negocio.dominio.DireccionDominio;
import co.edu.uco.imexcol.negocio.dominio.PedidoDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class PedidoNegocioImpl implements PedidoNegocio {

    private static final String DATO_CLIENTE = "cliente asociado al pedido";
    private static final String DATO_FECHA_PEDIDO = "fecha del pedido";
    private static final String DATO_TOTAL = "total del pedido";
    private static final String DATO_ESTADO = "estado del pedido";

    private final FabricaDAO fabrica;
    private final PedidoEntidadAssembler ensamblador;

    public PedidoNegocioImpl(final FabricaDAO fabrica) {
        super();
        if (UtilObjeto.esNulo(fabrica)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_FACTORY_NO_INICIALIZADA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_FACTORY_NO_INICIALIZADA.getContenido());
        }
        this.fabrica = fabrica;
        this.ensamblador = PedidoEntidadAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final PedidoDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new PedidoDominio());
        validarDatosComunes(dominioSeguro);
        asegurarClienteExista(dominioSeguro);
        asegurarDireccionDelCliente(dominioSeguro);
        asegurarTotalNoNegativo(dominioSeguro);
        asegurarEstadoValido(dominioSeguro);
        asignarNuevoIdentificador(dominioSeguro);
        guardar(dominioSeguro);
    }

    @Override
    public void actualizar(final PedidoDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new PedidoDominio());
        validarDatosComunes(dominioSeguro);
        asegurarPedidoExista(dominioSeguro.getId());
        asegurarClienteExista(dominioSeguro);
        conservarDireccionRegistrada(dominioSeguro);
        asegurarDireccionDelCliente(dominioSeguro);
        asegurarTotalNoNegativo(dominioSeguro);
        asegurarEstadoValido(dominioSeguro);
        modificar(dominioSeguro);
    }

    @Override
    public void eliminar(final UUID id) {
        asegurarPedidoExista(id);
        asegurarPedidoNoTengaFactura(id);
        asegurarPedidoNoTengaPagos(id);
        asegurarPedidoNoTengaEnvios(id);
        asegurarPedidoNoTengaLineas(id);
        borrar(id);
    }

    @Override
    public List<PedidoDominio> consultar(final PedidoDominio filtros) {
        final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new PedidoDominio());
        final var entidadFiltro = ensamblador.ensamblarEntidad(filtroSeguro);
        final var entidades = fabrica.obtenerPedidoDAO().consultarPorFiltro(entidadFiltro);
        return ensamblador.ensamblarDominio(entidades);
    }

    private void validarDatosComunes(final PedidoDominio dominio) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominio.getCliente().getId(), DATO_CLIENTE);
        ValidarFechaNoEsValorPorDefecto.ejecutarValidacion(dominio.getFechaPedido(), DATO_FECHA_PEDIDO);
    }

    private void asegurarClienteExista(final PedidoDominio dominio) {
        ValidarClienteExistePorId.ejecutarValidacion(dominio.getCliente().getId(), fabrica);
    }

    private void conservarDireccionRegistrada(final PedidoDominio dominio) {
        if (!tieneDireccion(dominio)) {
            final var pedidoRegistrado = fabrica.obtenerPedidoDAO().consultarPorId(dominio.getId());
            dominio.setDireccion(new DireccionDominio(pedidoRegistrado.getDireccion().getId()));
        }
    }

    private void asegurarDireccionDelCliente(final PedidoDominio dominio) {
        if (tieneDireccion(dominio)) {
            ValidarDireccionExistePorId.ejecutarValidacion(dominio.getDireccion().getId(), fabrica);
            ValidarDireccionPerteneceAlCliente.ejecutarValidacion(dominio.getDireccion().getId(),
                    dominio.getCliente().getId(), fabrica);
        }
    }

    private static boolean tieneDireccion(final PedidoDominio dominio) {
        return !UtilUUID.esUUIDPorDefecto(dominio.getDireccion().getId());
    }

    private void asegurarTotalNoNegativo(final PedidoDominio dominio) {
        ValidarNumeroEsPositivo.ejecutarValidacion(dominio.getTotal(), DATO_TOTAL);
    }

    private void asegurarEstadoValido(final PedidoDominio dominio) {
        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getEstado(), DATO_ESTADO);
        ValidarEstadoPedidoEsValido.ejecutarValidacion(dominio.getEstado());
    }

    private void asegurarPedidoExista(final UUID id) {
        ValidarPedidoExistePorId.ejecutarValidacion(id, fabrica);
    }

    private void asegurarPedidoNoTengaFactura(final UUID id) {
        ValidarPedidoNoTieneFactura.ejecutarValidacion(id, fabrica);
    }

    private void asegurarPedidoNoTengaPagos(final UUID id) {
        ValidarPedidoNoTienePagos.ejecutarValidacion(id, fabrica);
    }

    private void asegurarPedidoNoTengaEnvios(final UUID id) {
        ValidarPedidoNoTieneEnvios.ejecutarValidacion(id, fabrica);
    }

    private void asegurarPedidoNoTengaLineas(final UUID id) {
        ValidarPedidoNoTieneLineas.ejecutarValidacion(id, fabrica);
    }

    private void asignarNuevoIdentificador(final PedidoDominio dominio) {
        dominio.setId(UtilUUID.generarNuevoUUID());
    }

    private void guardar(final PedidoDominio dominio) {
        fabrica.obtenerPedidoDAO().crear(ensamblador.ensamblarEntidad(dominio));
    }

    private void modificar(final PedidoDominio dominio) {
        fabrica.obtenerPedidoDAO().actualizar(ensamblador.ensamblarEntidad(dominio));
    }

    private void borrar(final UUID id) {
        fabrica.obtenerPedidoDAO().eliminar(id);
    }
}
