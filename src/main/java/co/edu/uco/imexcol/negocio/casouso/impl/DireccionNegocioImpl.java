package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.DireccionDAO;
import co.edu.uco.imexcol.negocio.assembler.entidad.impl.DireccionEntidadAssembler;
import co.edu.uco.imexcol.negocio.casouso.DireccionNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarIdNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarLongitudTextoEsValida;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarTextoEsObligatorio;
import co.edu.uco.imexcol.negocio.dominio.DireccionDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

/**
 * Casos de uso del objeto de dominio Dirección.
 *
 * Políticas de negocio aplicadas (referencia documentación de políticas):
 * <ul>
 *   <li>DIR-POL-01: Validación de datos requeridos (calle, ciudad, departamento, país).</li>
 *   <li>DIR-POL-02: El cliente asociado debe existir.</li>
 *   <li>DIR-POL-03: Un cliente no puede tener dos direcciones con la misma combinación
 *       de calle + ciudad + departamento + país.</li>
 * </ul>
 */
public final class DireccionNegocioImpl implements DireccionNegocio {

    private final DireccionDAO dao;
    private final DireccionEntidadAssembler ensamblador;

    public DireccionNegocioImpl(final DireccionDAO dao) {
        super();
        if (UtilObjeto.esNulo(dao)) {
            throw ImexcolException.crear(
                    MensajesEnum.ERROR_USUARIO_FACTORY_NO_INICIALIZADA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_FACTORY_NO_INICIALIZADA.getContenido(),
                    Lugar.NEGOCIO);
        }
        this.dao = dao;
        this.ensamblador = DireccionEntidadAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final DireccionDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new DireccionDominio());
        validarDatosComunes(dominioSeguro);
        validarNoExisteDireccionDuplicada(dominioSeguro);
        dominioSeguro.setId(UUID.randomUUID());
        dao.acceder(ensamblador.ensamblarEntidad(dominioSeguro));
    }

    @Override
    public void actualizar(final DireccionDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new DireccionDominio());
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominioSeguro.getId(), "dirección");
        validarDatosComunes(dominioSeguro);
        dao.actualizar(ensamblador.ensamblarEntidad(dominioSeguro));
    }

    @Override
    public void eliminar(final UUID id) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(id, "dirección");
        dao.eliminar(id);
    }

    @Override
    public List<DireccionDominio> consultar(final DireccionDominio filtros) {
        final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new DireccionDominio());
        final var entidadFiltro = ensamblador.ensamblarEntidad(filtroSeguro);
        final var entidades = dao.consultarPorFiltro(entidadFiltro);
        return ensamblador.ensamblarDominio(entidades);
    }

    private void validarDatosComunes(final DireccionDominio dominio) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominio.getCliente().getId(),
                "cliente asociado a la dirección");

        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getCalle(), "calle de la dirección");
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getCalle(), "calle de la dirección", 5, 200);

        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getCiudad(), "ciudad de la dirección");
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getCiudad(), "ciudad de la dirección", 2, 100);

        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getDepartamento(),
                "departamento de la dirección");
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getDepartamento(),
                "departamento de la dirección", 2, 100);

        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getPais(), "país de la dirección");
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getPais(), "país de la dirección", 2, 100);

        if (!UtilTexto.estaVacia(dominio.getCodigoPostal())) {
            ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getCodigoPostal(),
                    "código postal de la dirección", 3, 10);
        }
    }

    /**
     * Verifica que no exista ya una dirección registrada con la misma combinación
     * de cliente + calle + ciudad + departamento + país. Aplica la política DIR-POL-03.
     */
    private void validarNoExisteDireccionDuplicada(final DireccionDominio dominio) {
        final var filtro = ensamblador.ensamblarEntidad(new DireccionDominio());
        filtro.getCliente().setId(dominio.getCliente().getId());
        filtro.setCalle(dominio.getCalle());
        filtro.setCiudad(dominio.getCiudad());
        filtro.setDepartamento(dominio.getDepartamento());
        filtro.setPais(dominio.getPais());

        final var existentes = dao.consultarPorFiltro(filtro);
        if (!UtilObjeto.esNulo(existentes) && !existentes.isEmpty()) {
            throw ImexcolException.crear(
                    "Ya existe una dirección registrada con la misma calle, ciudad, departamento y país para este cliente.",
                    "Intento de inserción de dirección duplicada para cliente=%s (calle=%s, ciudad=%s, departamento=%s, pais=%s)."
                            .formatted(dominio.getCliente().getId(), dominio.getCalle(),
                                    dominio.getCiudad(), dominio.getDepartamento(), dominio.getPais()),
                    Lugar.NEGOCIO);
        }
    }
}
