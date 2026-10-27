package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.assembler.entidad.impl.PedidoEntidadAssembler;
import co.edu.uco.imexcol.negocio.casouso.PedidoNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarFechaNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarIdNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarNumeroEsPositivo;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarTextoEsObligatorio;
import co.edu.uco.imexcol.negocio.casouso.validador.pedido.ValidarEstadoPedidoEsValido;
import co.edu.uco.imexcol.negocio.dominio.PedidoDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class PedidoNegocioImpl implements PedidoNegocio {

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
        dominioSeguro.setId(UUID.randomUUID());
        fabrica.obtenerPedidoDAO().acceder(ensamblador.ensamblarEntidad(dominioSeguro));
    }

    @Override
    public void actualizar(final PedidoDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new PedidoDominio());
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominioSeguro.getId(), "pedido");
        validarDatosComunes(dominioSeguro);
        fabrica.obtenerPedidoDAO().actualizar(ensamblador.ensamblarEntidad(dominioSeguro));
    }

    @Override
    public void eliminar(final UUID id) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(id, "pedido");
        fabrica.obtenerPedidoDAO().eliminar(id);
    }

    @Override
    public List<PedidoDominio> consultar(final PedidoDominio filtros) {
        final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new PedidoDominio());
        final var entidadFiltro = ensamblador.ensamblarEntidad(filtroSeguro);
        final var entidades = fabrica.obtenerPedidoDAO().consultarPorFiltro(entidadFiltro);
        return ensamblador.ensamblarDominio(entidades);
    }

    private void validarDatosComunes(final PedidoDominio dominio) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominio.getCliente().getId(),
                "cliente asociado al pedido");
        ValidarFechaNoEsValorPorDefecto.ejecutarValidacion(dominio.getFechaPedido(),
                "fecha del pedido");
        ValidarNumeroEsPositivo.ejecutarValidacion(dominio.getTotal(), "total del pedido");

        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getEstado(), "estado del pedido");
        ValidarEstadoPedidoEsValido.ejecutarValidacion(dominio.getEstado());
    }
}
