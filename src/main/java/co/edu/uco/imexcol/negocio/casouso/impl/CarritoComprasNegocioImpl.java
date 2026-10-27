package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.assembler.entidad.impl.CarritoComprasEntidadAssembler;
import co.edu.uco.imexcol.negocio.casouso.CarritoComprasNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.carritocompras.ValidarCarritoComprasExistePorId;
import co.edu.uco.imexcol.negocio.casouso.validador.carritocompras.ValidarCarritoComprasNoEstaConvertido;
import co.edu.uco.imexcol.negocio.casouso.validador.carritocompras.ValidarCarritoComprasPerteneceAlCliente;
import co.edu.uco.imexcol.negocio.casouso.validador.carritocompras.ValidarCarritoComprasTieneItemsParaConvertirse;
import co.edu.uco.imexcol.negocio.casouso.validador.carritocompras.ValidarClienteNoTieneOtroCarritoActivo;
import co.edu.uco.imexcol.negocio.casouso.validador.carritocompras.ValidarEstadoCarritoComprasEsValido;
import co.edu.uco.imexcol.negocio.casouso.validador.cliente.ValidarClienteExistePorId;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarFechaNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarIdNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarTextoEsObligatorio;
import co.edu.uco.imexcol.negocio.dominio.CarritoComprasDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class CarritoComprasNegocioImpl implements CarritoComprasNegocio {

    private static final String DATO_CLIENTE = "cliente asociado al carrito de compras";
    private static final String DATO_FECHA_CREACION = "fecha de creación del carrito de compras";
    private static final String DATO_ESTADO = "estado del carrito de compras";

    private final FabricaDAO fabrica;
    private final CarritoComprasEntidadAssembler ensamblador;

    public CarritoComprasNegocioImpl(final FabricaDAO fabrica) {
        super();
        if (UtilObjeto.esNulo(fabrica)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_FACTORY_NO_INICIALIZADA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_FACTORY_NO_INICIALIZADA.getContenido());
        }
        this.fabrica = fabrica;
        this.ensamblador = CarritoComprasEntidadAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final CarritoComprasDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new CarritoComprasDominio());
        validarDatosComunes(dominioSeguro);
        asegurarClienteExista(dominioSeguro);
        asignarNuevoIdentificador(dominioSeguro);
        asegurarClienteNoTengaOtroCarritoActivo(dominioSeguro);
        asegurarCarritoTengaItemsParaConvertirse(dominioSeguro);
        guardar(dominioSeguro);
    }

    @Override
    public void actualizar(final CarritoComprasDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new CarritoComprasDominio());
        validarDatosComunes(dominioSeguro);
        asegurarCarritoExista(dominioSeguro.getId());
        asegurarCarritoNoEsteConvertido(dominioSeguro.getId());
        asegurarCarritoPertenezcaAlCliente(dominioSeguro);
        asegurarClienteNoTengaOtroCarritoActivo(dominioSeguro);
        asegurarCarritoTengaItemsParaConvertirse(dominioSeguro);
        modificar(dominioSeguro);
    }

    @Override
    public void eliminar(final UUID id) {
        asegurarCarritoExista(id);
        asegurarCarritoNoEsteConvertido(id);
        borrar(id);
    }

    @Override
    public List<CarritoComprasDominio> consultar(final CarritoComprasDominio filtros) {
        final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new CarritoComprasDominio());
        final var entidadFiltro = ensamblador.ensamblarEntidad(filtroSeguro);
        final var entidades = fabrica.obtenerCarritoComprasDAO().consultarPorFiltro(entidadFiltro);
        return ensamblador.ensamblarDominio(entidades);
    }

    private void validarDatosComunes(final CarritoComprasDominio dominio) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominio.getCliente().getId(), DATO_CLIENTE);
        ValidarFechaNoEsValorPorDefecto.ejecutarValidacion(dominio.getFechaCreacion(), DATO_FECHA_CREACION);
        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getEstado(), DATO_ESTADO);
        ValidarEstadoCarritoComprasEsValido.ejecutarValidacion(dominio.getEstado());
    }

    private void asegurarClienteExista(final CarritoComprasDominio dominio) {
        ValidarClienteExistePorId.ejecutarValidacion(dominio.getCliente().getId(), fabrica);
    }

    private void asegurarClienteNoTengaOtroCarritoActivo(final CarritoComprasDominio dominio) {
        ValidarClienteNoTieneOtroCarritoActivo.ejecutarValidacion(dominio, fabrica);
    }

    private void asegurarCarritoExista(final UUID id) {
        ValidarCarritoComprasExistePorId.ejecutarValidacion(id, fabrica);
    }

    private void asegurarCarritoPertenezcaAlCliente(final CarritoComprasDominio dominio) {
        ValidarCarritoComprasPerteneceAlCliente.ejecutarValidacion(dominio.getId(), dominio.getCliente().getId(),
                fabrica);
    }

    private void asegurarCarritoNoEsteConvertido(final UUID id) {
        ValidarCarritoComprasNoEstaConvertido.ejecutarValidacion(id, fabrica);
    }

    private void asegurarCarritoTengaItemsParaConvertirse(final CarritoComprasDominio dominio) {
        ValidarCarritoComprasTieneItemsParaConvertirse.ejecutarValidacion(dominio, fabrica);
    }

    private void asignarNuevoIdentificador(final CarritoComprasDominio dominio) {
        dominio.setId(UtilUUID.generarNuevoUUID());
    }

    private void guardar(final CarritoComprasDominio dominio) {
        fabrica.obtenerCarritoComprasDAO().crear(ensamblador.ensamblarEntidad(dominio));
    }

    private void modificar(final CarritoComprasDominio dominio) {
        fabrica.obtenerCarritoComprasDAO().actualizar(ensamblador.ensamblarEntidad(dominio));
    }

    private void borrar(final UUID id) {
        fabrica.obtenerCarritoComprasDAO().eliminar(id);
    }
}
