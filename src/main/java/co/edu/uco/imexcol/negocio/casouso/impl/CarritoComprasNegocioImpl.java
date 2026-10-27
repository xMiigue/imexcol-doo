package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.assembler.entidad.impl.CarritoComprasEntidadAssembler;
import co.edu.uco.imexcol.negocio.casouso.CarritoComprasNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.carritocompras.ValidarEstadoCarritoComprasEsValido;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarFechaNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarIdNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarTextoEsObligatorio;
import co.edu.uco.imexcol.negocio.dominio.CarritoComprasDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class CarritoComprasNegocioImpl implements CarritoComprasNegocio {

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
        dominioSeguro.setId(UUID.randomUUID());
        fabrica.obtenerCarritoComprasDAO().acceder(ensamblador.ensamblarEntidad(dominioSeguro));
    }

    @Override
    public void actualizar(final CarritoComprasDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new CarritoComprasDominio());
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominioSeguro.getId(), "carrito de compras");
        validarDatosComunes(dominioSeguro);
        fabrica.obtenerCarritoComprasDAO().actualizar(ensamblador.ensamblarEntidad(dominioSeguro));
    }

    @Override
    public void eliminar(final UUID id) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(id, "carrito de compras");
        fabrica.obtenerCarritoComprasDAO().eliminar(id);
    }

    @Override
    public List<CarritoComprasDominio> consultar(final CarritoComprasDominio filtros) {
        final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new CarritoComprasDominio());
        final var entidadFiltro = ensamblador.ensamblarEntidad(filtroSeguro);
        final var entidades = fabrica.obtenerCarritoComprasDAO().consultarPorFiltro(entidadFiltro);
        return ensamblador.ensamblarDominio(entidades);
    }

    private void validarDatosComunes(final CarritoComprasDominio dominio) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominio.getCliente().getId(),
                "cliente asociado al carrito de compras");
        ValidarFechaNoEsValorPorDefecto.ejecutarValidacion(dominio.getFechaCreacion(),
                "fecha de creación del carrito de compras");

        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getEstado(),
                "estado del carrito de compras");
        ValidarEstadoCarritoComprasEsValido.ejecutarValidacion(dominio.getEstado());
    }
}
