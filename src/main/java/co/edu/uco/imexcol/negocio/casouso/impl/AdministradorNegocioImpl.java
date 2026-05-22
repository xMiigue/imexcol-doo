package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.AdministradorDAO;
import co.edu.uco.imexcol.negocio.assembler.entidad.impl.AdministradorEntidadAssembler;
import co.edu.uco.imexcol.negocio.casouso.AdministradorNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarIdNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarLongitudTextoEsValida;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarTextoEsObligatorio;
import co.edu.uco.imexcol.negocio.dominio.AdministradorDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

/**
 * Casos de uso del objeto de dominio Administrador.
 *
 * Políticas de negocio aplicadas (referencia documentación de políticas):
 * <ul>
 *   <li>ADM-POL-01: Validación de datos requeridos.</li>
 *   <li>ADM-POL-02: El nombre de usuario no debe existir previamente.</li>
 * </ul>
 */
public final class AdministradorNegocioImpl implements AdministradorNegocio {

    private final AdministradorDAO dao;
    private final AdministradorEntidadAssembler ensamblador;

    public AdministradorNegocioImpl(final AdministradorDAO dao) {
        super();
        if (UtilObjeto.esNulo(dao)) {
            throw ImexcolException.crear(
                    MensajesEnum.ERROR_USUARIO_FACTORY_NO_INICIALIZADA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_FACTORY_NO_INICIALIZADA.getContenido(),
                    Lugar.NEGOCIO);
        }
        this.dao = dao;
        this.ensamblador = AdministradorEntidadAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final AdministradorDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new AdministradorDominio());
        validarDatosComunes(dominioSeguro);
        dominioSeguro.setId(UUID.randomUUID());
        dao.acceder(ensamblador.ensamblarEntidad(dominioSeguro));
    }

    @Override
    public void actualizar(final AdministradorDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new AdministradorDominio());
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominioSeguro.getId(), "administrador");
        validarDatosComunes(dominioSeguro);
        dao.actualizar(ensamblador.ensamblarEntidad(dominioSeguro));
    }

    @Override
    public void eliminar(final UUID id) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(id, "administrador");
        dao.eliminar(id);
    }

    @Override
    public List<AdministradorDominio> consultar(final AdministradorDominio filtros) {
        final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new AdministradorDominio());
        final var entidadFiltro = ensamblador.ensamblarEntidad(filtroSeguro);
        final var entidades = dao.consultarPorFiltro(entidadFiltro);
        return ensamblador.ensamblarDominio(entidades);
    }

    private void validarDatosComunes(final AdministradorDominio dominio) {
        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getNombreUsuario(),
                "nombre de usuario del administrador");
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getNombreUsuario(),
                "nombre de usuario del administrador", 4, 50);

        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getCorreoElectronico(),
                "correo electrónico del administrador");
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getCorreoElectronico(),
                "correo electrónico del administrador", 6, 150);

        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getContrasena(),
                "contraseña del administrador");
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getContrasena(),
                "contraseña del administrador", 8, 255);
    }
}
