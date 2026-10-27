package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.assembler.entidad.impl.ItemCarritoEntidadAssembler;
import co.edu.uco.imexcol.negocio.casouso.ItemCarritoNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.carritocompras.ValidarCarritoComprasExistePorId;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarIdNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarNumeroEsMayorACero;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarNumeroEsPositivo;
import co.edu.uco.imexcol.negocio.casouso.validador.itemcarrito.ValidarItemCarritoExistePorId;
import co.edu.uco.imexcol.negocio.casouso.validador.producto.ValidarProductoExistePorId;
import co.edu.uco.imexcol.negocio.dominio.ItemCarritoDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class ItemCarritoNegocioImpl implements ItemCarritoNegocio {

    private final FabricaDAO fabrica;
    private final ItemCarritoEntidadAssembler ensamblador;

    public ItemCarritoNegocioImpl(final FabricaDAO fabrica) {
        super();
        if (UtilObjeto.esNulo(fabrica)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_FACTORY_NO_INICIALIZADA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_FACTORY_NO_INICIALIZADA.getContenido());
        }
        this.fabrica = fabrica;
        this.ensamblador = ItemCarritoEntidadAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final ItemCarritoDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new ItemCarritoDominio());
        validarDatosComunes(dominioSeguro);
        asegurarCarritoExista(dominioSeguro);
        asegurarProductoExista(dominioSeguro);
        asegurarCantidadMayorACero(dominioSeguro);
        asignarNuevoIdentificador(dominioSeguro);
        guardar(dominioSeguro);
    }

    @Override
    public void actualizar(final ItemCarritoDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new ItemCarritoDominio());
        validarDatosComunes(dominioSeguro);
        asegurarItemExista(dominioSeguro.getId());
        asegurarCarritoExista(dominioSeguro);
        asegurarProductoExista(dominioSeguro);
        asegurarCantidadMayorACero(dominioSeguro);
        modificar(dominioSeguro);
    }

    @Override
    public void eliminar(final UUID id) {
        asegurarItemExista(id);
        borrar(id);
    }

    @Override
    public List<ItemCarritoDominio> consultar(final ItemCarritoDominio filtros) {
        final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new ItemCarritoDominio());
        final var entidadFiltro = ensamblador.ensamblarEntidad(filtroSeguro);
        final var entidades = fabrica.obtenerItemCarritoDAO().consultarPorFiltro(entidadFiltro);
        return ensamblador.ensamblarDominio(entidades);
    }

    private void validarDatosComunes(final ItemCarritoDominio dominio) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominio.getCarrito().getId(),
                "carrito asociado al ítem");
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominio.getProducto().getId(),
                "producto asociado al ítem");

        ValidarNumeroEsPositivo.ejecutarValidacion(dominio.getPrecioUnitario(),
                "precio unitario del ítem del carrito");
    }

    private void asegurarCarritoExista(final ItemCarritoDominio dominio) {
        ValidarCarritoComprasExistePorId.ejecutarValidacion(dominio.getCarrito().getId(), fabrica);
    }

    private void asegurarProductoExista(final ItemCarritoDominio dominio) {
        ValidarProductoExistePorId.ejecutarValidacion(dominio.getProducto().getId(), fabrica);
    }

    private void asegurarCantidadMayorACero(final ItemCarritoDominio dominio) {
        ValidarNumeroEsMayorACero.ejecutarValidacion(dominio.getCantidad(), "cantidad del ítem del carrito");
    }

    private void asegurarItemExista(final UUID id) {
        ValidarItemCarritoExistePorId.ejecutarValidacion(id, fabrica);
    }

    private void asignarNuevoIdentificador(final ItemCarritoDominio dominio) {
        dominio.setId(UtilUUID.generarNuevoUUID());
    }

    private void guardar(final ItemCarritoDominio dominio) {
        fabrica.obtenerItemCarritoDAO().acceder(ensamblador.ensamblarEntidad(dominio));
    }

    private void modificar(final ItemCarritoDominio dominio) {
        fabrica.obtenerItemCarritoDAO().actualizar(ensamblador.ensamblarEntidad(dominio));
    }

    private void borrar(final UUID id) {
        fabrica.obtenerItemCarritoDAO().eliminar(id);
    }
}
