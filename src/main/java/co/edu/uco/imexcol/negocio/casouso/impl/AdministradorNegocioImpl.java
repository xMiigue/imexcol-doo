package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.assembler.entidad.impl.AdministradorEntidadAssembler;
import co.edu.uco.imexcol.negocio.casouso.AdministradorNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.administrador.ValidarAdministradorExistePorId;
import co.edu.uco.imexcol.negocio.casouso.validador.administrador.ValidarAdministradorExistePorNombreUsuario;
import co.edu.uco.imexcol.negocio.casouso.validador.administrador.ValidarAdministradorNoEsElUnico;
import co.edu.uco.imexcol.negocio.casouso.validador.administrador.ValidarContrasenaAdministradorCoincide;
import co.edu.uco.imexcol.negocio.casouso.validador.administrador.ValidarCredencialesAdministradorSonObligatorias;
import co.edu.uco.imexcol.negocio.casouso.validador.administrador.ValidarNombreUsuarioAdministradorNoExiste;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarLongitudTextoEsValida;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarTextoEsObligatorio;
import co.edu.uco.imexcol.negocio.dominio.AdministradorDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class AdministradorNegocioImpl implements AdministradorNegocio {

    private static final String DATO_NOMBRE_USUARIO = "nombre de usuario del administrador";
    private static final String DATO_CORREO = "correo electrónico del administrador";
    private static final String DATO_CONTRASENA = "contraseña del administrador";
    private static final int LONGITUD_MINIMA_NOMBRE_USUARIO = 4;
    private static final int LONGITUD_MAXIMA_NOMBRE_USUARIO = 50;
    private static final int LONGITUD_MINIMA_CORREO = 6;
    private static final int LONGITUD_MAXIMA_CORREO = 150;
    private static final int LONGITUD_MINIMA_CONTRASENA = 8;
    private static final int LONGITUD_MAXIMA_CONTRASENA = 255;

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

    @Override
    public AdministradorDominio autenticar(final AdministradorDominio credenciales) {
        final var credencialesSeguras = UtilObjeto.obtenerValorDefecto(credenciales, new AdministradorDominio());
        asegurarCredencialesCompletas(credencialesSeguras);
        asegurarAdministradorRegistrado(credencialesSeguras.getNombreUsuario());
        final var administrador = consultarPorNombreUsuario(credencialesSeguras.getNombreUsuario());
        asegurarContrasenaCoincida(credencialesSeguras.getContrasena(), administrador);
        ocultarContrasena(administrador);
        return administrador;
    }

    private void validarDatosComunes(final AdministradorDominio dominio) {
        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getNombreUsuario(), DATO_NOMBRE_USUARIO);
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getNombreUsuario(), DATO_NOMBRE_USUARIO,
                LONGITUD_MINIMA_NOMBRE_USUARIO, LONGITUD_MAXIMA_NOMBRE_USUARIO);
        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getCorreoElectronico(), DATO_CORREO);
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getCorreoElectronico(), DATO_CORREO,
                LONGITUD_MINIMA_CORREO, LONGITUD_MAXIMA_CORREO);
        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getContrasena(), DATO_CONTRASENA);
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getContrasena(), DATO_CONTRASENA,
                LONGITUD_MINIMA_CONTRASENA, LONGITUD_MAXIMA_CONTRASENA);
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

    private void asegurarCredencialesCompletas(final AdministradorDominio credenciales) {
        ValidarCredencialesAdministradorSonObligatorias.ejecutarValidacion(credenciales.getNombreUsuario(),
                credenciales.getContrasena());
    }

    private void asegurarAdministradorRegistrado(final String nombreUsuario) {
        ValidarAdministradorExistePorNombreUsuario.ejecutarValidacion(nombreUsuario, fabrica);
    }

    private AdministradorDominio consultarPorNombreUsuario(final String nombreUsuario) {
        final var filtro = new AdministradorDominio();
        filtro.setNombreUsuario(nombreUsuario);
        return consultar(filtro).stream().findFirst().orElse(new AdministradorDominio());
    }

    private void asegurarContrasenaCoincida(final String contrasenaIngresada,
            final AdministradorDominio administrador) {
        ValidarContrasenaAdministradorCoincide.ejecutarValidacion(contrasenaIngresada,
                administrador.getContrasena(), administrador.getNombreUsuario());
    }

    private void ocultarContrasena(final AdministradorDominio administrador) {
        administrador.setContrasena(UtilTexto.VACIO);
    }

    private void guardar(final AdministradorDominio dominio) {
        fabrica.obtenerAdministradorDAO().crear(ensamblador.ensamblarEntidad(dominio));
    }

    private void modificar(final AdministradorDominio dominio) {
        fabrica.obtenerAdministradorDAO().actualizar(ensamblador.ensamblarEntidad(dominio));
    }

    private void borrar(final UUID id) {
        fabrica.obtenerAdministradorDAO().eliminar(id);
    }
}
