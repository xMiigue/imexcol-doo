package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.assembler.entidad.impl.AdministradorEntidadAssembler;
import co.edu.uco.imexcol.negocio.casouso.AdministradorNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.administrador.ValidarAdministradorExistePorId;
import co.edu.uco.imexcol.negocio.casouso.validador.administrador.ValidarAdministradorNoEsElUnico;
import co.edu.uco.imexcol.negocio.casouso.validador.administrador.ValidarNombreUsuarioAdministradorNoExiste;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarLongitudTextoEsValida;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarTextoEsObligatorio;
import co.edu.uco.imexcol.negocio.dominio.AdministradorDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class AdministradorNegocioImpl implements AdministradorNegocio {

    private final FabricaDAO fabrica;
    private final AdministradorEntidadAssembler ensamblador;

    public AdministradorNegocioImpl(final FabricaDAO fabrica) {
        super();
        if (UtilObjeto.esNulo(fabrica)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_FACTORY_NO_INICIALIZADA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_FACTORY_NO_INICIALIZADA.getContenido());
        }
        this.fabrica = fabrica;
        this.ensamblador = AdministradorEntidadAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final AdministradorDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new AdministradorDominio());
        validarDatosComunes(dominioSeguro);
        asignarNuevoIdentificador(dominioSeguro);
        asegurarNombreUsuarioNoExista(dominioSeguro);
        guardar(dominioSeguro);
    }

    @Override
    public void actualizar(final AdministradorDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new AdministradorDominio());
        validarDatosComunes(dominioSeguro);
        asegurarAdministradorExista(dominioSeguro.getId());
        asegurarNombreUsuarioNoExista(dominioSeguro);
        modificar(dominioSeguro);
    }

    @Override
    public void eliminar(final UUID id) {
        asegurarAdministradorExista(id);
        asegurarAdministradorNoSeaElUnico(id);
        borrar(id);
    }

    @Override
    public List<AdministradorDominio> consultar(final AdministradorDominio filtros) {
        final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new AdministradorDominio());
        final var entidadFiltro = ensamblador.ensamblarEntidad(filtroSeguro);
        final var entidades = fabrica.obtenerAdministradorDAO().consultarPorFiltro(entidadFiltro);
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

    private void asegurarNombreUsuarioNoExista(final AdministradorDominio dominio) {
        ValidarNombreUsuarioAdministradorNoExiste.ejecutarValidacion(dominio.getNombreUsuario(), dominio.getId(),
                fabrica);
    }

    private void asegurarAdministradorExista(final UUID id) {
        ValidarAdministradorExistePorId.ejecutarValidacion(id, fabrica);
    }

    private void asegurarAdministradorNoSeaElUnico(final UUID id) {
        ValidarAdministradorNoEsElUnico.ejecutarValidacion(id, fabrica);
    }

    private void asignarNuevoIdentificador(final AdministradorDominio dominio) {
        dominio.setId(UtilUUID.generarNuevoUUID());
    }

    private void guardar(final AdministradorDominio dominio) {
        fabrica.obtenerAdministradorDAO().acceder(ensamblador.ensamblarEntidad(dominio));
    }

    private void modificar(final AdministradorDominio dominio) {
        fabrica.obtenerAdministradorDAO().actualizar(ensamblador.ensamblarEntidad(dominio));
    }

    private void borrar(final UUID id) {
        fabrica.obtenerAdministradorDAO().eliminar(id);
    }
}
