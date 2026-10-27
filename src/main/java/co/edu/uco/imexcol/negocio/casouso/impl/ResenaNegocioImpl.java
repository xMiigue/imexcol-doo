package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.assembler.entidad.impl.ResenaEntidadAssembler;
import co.edu.uco.imexcol.negocio.casouso.ResenaNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.administrador.ValidarAdministradorExistePorId;
import co.edu.uco.imexcol.negocio.casouso.validador.cliente.ValidarClienteComproProducto;
import co.edu.uco.imexcol.negocio.casouso.validador.cliente.ValidarClienteExistePorId;
import co.edu.uco.imexcol.negocio.casouso.validador.cliente.ValidarClienteNoHaResenadoProducto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarIdNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarLongitudTextoEsValida;
import co.edu.uco.imexcol.negocio.casouso.validador.producto.ValidarProductoExistePorId;
import co.edu.uco.imexcol.negocio.casouso.validador.resena.ValidarCalificacionResenaEnRango;
import co.edu.uco.imexcol.negocio.casouso.validador.resena.ValidarResenaExistePorId;
import co.edu.uco.imexcol.negocio.casouso.validador.resena.ValidarResenaPerteneceAlCliente;
import co.edu.uco.imexcol.negocio.dominio.ProductoDominio;
import co.edu.uco.imexcol.negocio.dominio.ResenaDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilFecha;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class ResenaNegocioImpl implements ResenaNegocio {

    private static final String DATO_CLIENTE = "cliente que escribe la reseña";
    private static final String DATO_PRODUCTO = "producto calificado";
    private static final String DATO_ADMINISTRADOR = "administrador que elimina la reseña";
    private static final String DATO_COMENTARIO = "comentario de la reseña";
    private static final int LONGITUD_MINIMA_COMENTARIO = 0;
    private static final int LONGITUD_MAXIMA_COMENTARIO = 255;

    private final FabricaDAO fabrica;
    private final ResenaEntidadAssembler ensamblador;

    public ResenaNegocioImpl(final FabricaDAO fabrica) {
        super();
        if (UtilObjeto.esNulo(fabrica)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_FACTORY_NO_INICIALIZADA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_FACTORY_NO_INICIALIZADA.getContenido());
        }
        this.fabrica = fabrica;
        this.ensamblador = ResenaEntidadAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final ResenaDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new ResenaDominio());
        validarDatosComunes(dominioSeguro);
        asegurarCalificacionEnRango(dominioSeguro);
        asegurarClienteExista(dominioSeguro);
        asegurarProductoExista(dominioSeguro.getProducto().getId());
        asegurarClienteHayaCompradoProducto(dominioSeguro);
        asegurarClienteNoHayaResenadoProducto(dominioSeguro);
        asignarNuevoIdentificador(dominioSeguro);
        asignarFechaPublicacion(dominioSeguro);
        guardar(dominioSeguro);
    }

    @Override
    public void eliminarPorCliente(final UUID idResena, final UUID idCliente) {
        validarIdentificador(idCliente, DATO_CLIENTE);
        asegurarResenaExista(idResena);
        asegurarResenaPertenezcaAlCliente(idResena, idCliente);
        borrar(idResena);
    }

    @Override
    public void eliminarPorAdministrador(final UUID idResena, final UUID idAdministrador) {
        validarIdentificador(idAdministrador, DATO_ADMINISTRADOR);
        asegurarAdministradorExista(idAdministrador);
        asegurarResenaExista(idResena);
        borrar(idResena);
    }

    @Override
    public List<ResenaDominio> consultarPorProducto(final UUID idProducto) {
        validarIdentificador(idProducto, DATO_PRODUCTO);
        asegurarProductoExista(idProducto);
        final var filtro = new ResenaDominio();
        filtro.setProducto(new ProductoDominio(idProducto));
        return consultar(filtro);
    }

    @Override
    public List<ResenaDominio> consultar(final ResenaDominio filtros) {
        final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new ResenaDominio());
        final var entidadFiltro = ensamblador.ensamblarEntidad(filtroSeguro);
        final var entidades = fabrica.obtenerResenaDAO().consultarPorFiltro(entidadFiltro);
        return ensamblador.ensamblarDominio(entidades);
    }

    private void validarDatosComunes(final ResenaDominio dominio) {
        validarIdentificador(dominio.getCliente().getId(), DATO_CLIENTE);
        validarIdentificador(dominio.getProducto().getId(), DATO_PRODUCTO);
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getComentario(), DATO_COMENTARIO,
                LONGITUD_MINIMA_COMENTARIO, LONGITUD_MAXIMA_COMENTARIO);
    }

    private void validarIdentificador(final UUID id, final String nombreDato) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(UtilUUID.obtenerValorDefecto(id), nombreDato);
    }

    private void asegurarCalificacionEnRango(final ResenaDominio dominio) {
        ValidarCalificacionResenaEnRango.ejecutarValidacion(dominio.getCalificacion());
    }

    private void asegurarClienteExista(final ResenaDominio dominio) {
        ValidarClienteExistePorId.ejecutarValidacion(dominio.getCliente().getId(), fabrica);
    }

    private void asegurarProductoExista(final UUID idProducto) {
        ValidarProductoExistePorId.ejecutarValidacion(idProducto, fabrica);
    }

    private void asegurarClienteHayaCompradoProducto(final ResenaDominio dominio) {
        ValidarClienteComproProducto.ejecutarValidacion(dominio.getCliente().getId(),
                dominio.getProducto().getId(), fabrica);
    }

    private void asegurarClienteNoHayaResenadoProducto(final ResenaDominio dominio) {
        ValidarClienteNoHaResenadoProducto.ejecutarValidacion(dominio.getCliente().getId(),
                dominio.getProducto().getId(), fabrica);
    }

    private void asegurarResenaExista(final UUID idResena) {
        ValidarResenaExistePorId.ejecutarValidacion(idResena, fabrica);
    }

    private void asegurarResenaPertenezcaAlCliente(final UUID idResena, final UUID idCliente) {
        ValidarResenaPerteneceAlCliente.ejecutarValidacion(idResena, idCliente, fabrica);
    }

    private void asegurarAdministradorExista(final UUID idAdministrador) {
        ValidarAdministradorExistePorId.ejecutarValidacion(idAdministrador, fabrica);
    }

    private void asignarNuevoIdentificador(final ResenaDominio dominio) {
        dominio.setId(UtilUUID.generarNuevoUUID());
    }

    private void asignarFechaPublicacion(final ResenaDominio dominio) {
        dominio.setFecha(UtilFecha.obtenerFechaHoraActual());
    }

    private void guardar(final ResenaDominio dominio) {
        fabrica.obtenerResenaDAO().crear(ensamblador.ensamblarEntidad(dominio));
    }

    private void borrar(final UUID idResena) {
        fabrica.obtenerResenaDAO().eliminar(idResena);
    }
}
