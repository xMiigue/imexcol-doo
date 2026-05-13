package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.negocio.casouso.ItemCarritoNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarIdNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarNumeroEsMayorACero;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarNumeroEsPositivo;
import co.edu.uco.imexcol.negocio.dominio.ItemCarritoDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

public final class ItemCarritoNegocioImpl implements ItemCarritoNegocio {

    private static final String CASO_USO = "ItemCarritoNegocio";

    public ItemCarritoNegocioImpl() {
        super();
        // TODO: recibir DAOFactory cuando exista la capa de datos.
    }

    @Override
    public void registrar(final ItemCarritoDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new ItemCarritoDominio());
        validarDatosComunes(dominioSeguro);
        // TODO: integrar con DAO — daoFactory.obtenerItemCarritoDAO().crear(...)
        throw operacionPendiente("registrar");
    }

    @Override
    public void actualizar(final ItemCarritoDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new ItemCarritoDominio());
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominioSeguro.getId(), "ítem del carrito");
        validarDatosComunes(dominioSeguro);
        // TODO: integrar con DAO — daoFactory.obtenerItemCarritoDAO().modificar(...)
        throw operacionPendiente("actualizar");
    }

    @Override
    public void eliminar(final UUID id) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(id, "ítem del carrito");
        // TODO: integrar con DAO — daoFactory.obtenerItemCarritoDAO().eliminar(id);
        throw operacionPendiente("eliminar");
    }

    @Override
    public List<ItemCarritoDominio> consultar(final ItemCarritoDominio filtros) {
        UtilObjeto.obtenerValorDefecto(filtros, new ItemCarritoDominio());
        // TODO: integrar con DAO — daoFactory.obtenerItemCarritoDAO().consultarPorFiltro(...)
        return new ArrayList<>();
    }

    private void validarDatosComunes(final ItemCarritoDominio dominio) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominio.getCarrito().getId(),
                "carrito asociado al ítem");
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominio.getProducto().getId(),
                "producto asociado al ítem");

        // La cantidad de un ítem debe ser al menos 1 (estricto > 0).
        ValidarNumeroEsMayorACero.ejecutarValidacion(dominio.getCantidad(),
                "cantidad del ítem del carrito");
        // El precio unitario puede ser 0 (productos promocionales) pero no negativo.
        ValidarNumeroEsPositivo.ejecutarValidacion(dominio.getPrecioUnitario(),
                "precio unitario del ítem del carrito");
    }

    private static ImexcolException operacionPendiente(final String operacion) {
        return ImexcolException.crear(
                MensajesEnum.ERROR_USUARIO_CASO_USO_DAO_PENDIENTE.getContenido(),
                MensajesEnum.ERROR_TECNICO_CASO_USO_DAO_PENDIENTE.getContenido().formatted(operacion, CASO_USO),
                Lugar.NEGOCIO);
    }
}
