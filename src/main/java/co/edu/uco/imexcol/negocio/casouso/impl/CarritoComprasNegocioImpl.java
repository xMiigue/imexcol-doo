package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.negocio.casouso.CarritoComprasNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarFechaNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarIdNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.dominio.CarritoComprasDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

public final class CarritoComprasNegocioImpl implements CarritoComprasNegocio {

    private static final String CASO_USO = "CarritoComprasNegocio";

    public CarritoComprasNegocioImpl() {
        super();
        // TODO: recibir DAOFactory cuando exista la capa de datos.
    }

    @Override
    public void registrar(final CarritoComprasDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new CarritoComprasDominio());
        validarDatosComunes(dominioSeguro);
        // TODO: integrar con DAO — daoFactory.obtenerCarritoComprasDAO().crear(...)
        throw operacionPendiente("registrar");
    }

    @Override
    public void actualizar(final CarritoComprasDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new CarritoComprasDominio());
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominioSeguro.getId(), "carrito de compras");
        validarDatosComunes(dominioSeguro);
        // TODO: integrar con DAO — daoFactory.obtenerCarritoComprasDAO().modificar(...)
        throw operacionPendiente("actualizar");
    }

    @Override
    public void eliminar(final UUID id) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(id, "carrito de compras");
        // TODO: integrar con DAO — daoFactory.obtenerCarritoComprasDAO().eliminar(id);
        throw operacionPendiente("eliminar");
    }

    @Override
    public List<CarritoComprasDominio> consultar(final CarritoComprasDominio filtros) {
        UtilObjeto.obtenerValorDefecto(filtros, new CarritoComprasDominio());
        // TODO: integrar con DAO — daoFactory.obtenerCarritoComprasDAO().consultarPorFiltro(...)
        return new ArrayList<>();
    }

    private void validarDatosComunes(final CarritoComprasDominio dominio) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominio.getCliente().getId(),
                "cliente asociado al carrito de compras");
        ValidarFechaNoEsValorPorDefecto.ejecutarValidacion(dominio.getFechaCreacion(),
                "fecha de creación del carrito de compras");
    }

    private static ImexcolException operacionPendiente(final String operacion) {
        return ImexcolException.crear(
                MensajesEnum.ERROR_USUARIO_CASO_USO_DAO_PENDIENTE.getContenido(),
                MensajesEnum.ERROR_TECNICO_CASO_USO_DAO_PENDIENTE.getContenido().formatted(operacion, CASO_USO),
                Lugar.NEGOCIO);
    }
}
