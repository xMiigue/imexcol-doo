package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.assembler.entidad.impl.MetodoPagoEntidadAssembler;
import co.edu.uco.imexcol.negocio.casouso.MetodoPagoNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarLongitudTextoEsValida;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarTextoEsObligatorio;
import co.edu.uco.imexcol.negocio.casouso.validador.metodopago.ValidarMetodoPagoExistePorId;
import co.edu.uco.imexcol.negocio.casouso.validador.metodopago.ValidarNombreMetodoPagoNoExiste;
import co.edu.uco.imexcol.negocio.dominio.MetodoPagoDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class MetodoPagoNegocioImpl implements MetodoPagoNegocio {

    private static final String DATO_NOMBRE = "nombre del método de pago";
    private static final String DATO_DESCRIPCION = "descripción del método de pago";
    private static final int LONGITUD_MINIMA_NOMBRE = 3;
    private static final int LONGITUD_MAXIMA_NOMBRE = 100;
    private static final int LONGITUD_MINIMA_DESCRIPCION = 0;
    private static final int LONGITUD_MAXIMA_DESCRIPCION = 255;

    private final FabricaDAO fabrica;
    private final MetodoPagoEntidadAssembler ensamblador;

    public MetodoPagoNegocioImpl(final FabricaDAO fabrica) {
        if (UtilObjeto.esNulo(fabrica)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_FACTORY_NO_INICIALIZADA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_FACTORY_NO_INICIALIZADA.getContenido());
        }
        super();
        this.fabrica = fabrica;
        this.ensamblador = MetodoPagoEntidadAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final MetodoPagoDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new MetodoPagoDominio());
        validarDatosComunes(dominioSeguro);
        asignarNuevoIdentificador(dominioSeguro);
        asegurarNombreNoExista(dominioSeguro);
        guardar(dominioSeguro);
    }

    @Override
    public void actualizar(final MetodoPagoDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new MetodoPagoDominio());
        validarDatosComunes(dominioSeguro);
        asegurarMetodoPagoExista(dominioSeguro.getId());
        asegurarNombreNoExista(dominioSeguro);
        modificar(dominioSeguro);
    }

    @Override
    public void eliminar(final UUID id) {
        asegurarMetodoPagoExista(id);
        borrar(id);
    }

    @Override
    public List<MetodoPagoDominio> consultar(final MetodoPagoDominio filtros) {
        final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new MetodoPagoDominio());
        final var entidadFiltro = ensamblador.ensamblarEntidad(filtroSeguro);
        final var entidades = fabrica.obtenerMetodoPagoDAO().consultarPorFiltro(entidadFiltro);
        return ensamblador.ensamblarDominio(entidades);
    }

    private void validarDatosComunes(final MetodoPagoDominio dominio) {
        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getNombre(), DATO_NOMBRE);
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getNombre(), DATO_NOMBRE,
                LONGITUD_MINIMA_NOMBRE, LONGITUD_MAXIMA_NOMBRE);
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getDescripcion(), DATO_DESCRIPCION,
                LONGITUD_MINIMA_DESCRIPCION, LONGITUD_MAXIMA_DESCRIPCION);
    }

    private void asegurarNombreNoExista(final MetodoPagoDominio dominio) {
        ValidarNombreMetodoPagoNoExiste.ejecutarValidacion(dominio.getNombre(), dominio.getId(), fabrica);
    }

    private void asegurarMetodoPagoExista(final UUID id) {
        ValidarMetodoPagoExistePorId.ejecutarValidacion(id, fabrica);
    }

    private void asignarNuevoIdentificador(final MetodoPagoDominio dominio) {
        dominio.setId(UtilUUID.generarNuevoUUID());
    }

    private void guardar(final MetodoPagoDominio dominio) {
        fabrica.obtenerMetodoPagoDAO().crear(ensamblador.ensamblarEntidad(dominio));
    }

    private void modificar(final MetodoPagoDominio dominio) {
        fabrica.obtenerMetodoPagoDAO().actualizar(ensamblador.ensamblarEntidad(dominio));
    }

    private void borrar(final UUID id) {
        fabrica.obtenerMetodoPagoDAO().eliminar(id);
    }
}
