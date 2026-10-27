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
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class MetodoPagoNegocioImpl implements MetodoPagoNegocio {

    private final FabricaDAO fabrica;
    private final MetodoPagoEntidadAssembler ensamblador;

    public MetodoPagoNegocioImpl(final FabricaDAO fabrica) {
        super();
        if (UtilObjeto.esNulo(fabrica)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_FACTORY_NO_INICIALIZADA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_FACTORY_NO_INICIALIZADA.getContenido());
        }
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
        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getNombre(), "nombre del método de pago");
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getNombre(), "nombre del método de pago", 3, 100);
        if (!UtilTexto.estaVacia(dominio.getDescripcion())) {
            ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getDescripcion(),
                    "descripción del método de pago", 0, 255);
        }
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
        fabrica.obtenerMetodoPagoDAO().acceder(ensamblador.ensamblarEntidad(dominio));
    }

    private void modificar(final MetodoPagoDominio dominio) {
        fabrica.obtenerMetodoPagoDAO().actualizar(ensamblador.ensamblarEntidad(dominio));
    }

    private void borrar(final UUID id) {
        fabrica.obtenerMetodoPagoDAO().eliminar(id);
    }
}
