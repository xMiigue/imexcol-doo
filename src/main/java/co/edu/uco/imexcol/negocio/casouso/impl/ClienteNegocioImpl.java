package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.assembler.entidad.impl.ClienteEntidadAssembler;
import co.edu.uco.imexcol.negocio.casouso.ClienteNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.cliente.ValidarClienteExistePorId;
import co.edu.uco.imexcol.negocio.casouso.validador.cliente.ValidarCorreoClienteNoExiste;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarLongitudTextoEsValida;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarTextoEsObligatorio;
import co.edu.uco.imexcol.negocio.dominio.ClienteDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class ClienteNegocioImpl implements ClienteNegocio {

    private static final String DATO_NOMBRE = "nombre del cliente";
    private static final String DATO_APELLIDO = "apellido del cliente";
    private static final String DATO_CORREO = "correo electrónico del cliente";
    private static final String DATO_CONTRASENA = "contraseña del cliente";
    private static final String DATO_TELEFONO = "teléfono del cliente";
    private static final int LONGITUD_MINIMA_NOMBRE = 2;
    private static final int LONGITUD_MAXIMA_NOMBRE = 100;
    private static final int LONGITUD_MINIMA_APELLIDO = 2;
    private static final int LONGITUD_MAXIMA_APELLIDO = 100;
    private static final int LONGITUD_MINIMA_CORREO = 6;
    private static final int LONGITUD_MAXIMA_CORREO = 150;
    private static final int LONGITUD_MINIMA_CONTRASENA = 8;
    private static final int LONGITUD_MAXIMA_CONTRASENA = 255;
    private static final int LONGITUD_MINIMA_TELEFONO = 7;
    private static final int LONGITUD_MAXIMA_TELEFONO = 20;

    private final FabricaDAO fabrica;
    private final ClienteEntidadAssembler ensamblador;

    public ClienteNegocioImpl(final FabricaDAO fabrica) {
        super();
        if (UtilObjeto.esNulo(fabrica)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_FACTORY_NO_INICIALIZADA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_FACTORY_NO_INICIALIZADA.getContenido());
        }
        this.fabrica = fabrica;
        this.ensamblador = ClienteEntidadAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final ClienteDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new ClienteDominio());
        validarDatosComunes(dominioSeguro);
        asignarNuevoIdentificador(dominioSeguro);
        asegurarCorreoNoExista(dominioSeguro);
        guardar(dominioSeguro);
    }

    @Override
    public void actualizar(final ClienteDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new ClienteDominio());
        validarDatosComunes(dominioSeguro);
        asegurarClienteExista(dominioSeguro.getId());
        asegurarCorreoNoExista(dominioSeguro);
        modificar(dominioSeguro);
    }

    @Override
    public void eliminar(final UUID id) {
        asegurarClienteExista(id);
        borrar(id);
    }

    @Override
    public List<ClienteDominio> consultar(final ClienteDominio filtros) {
        final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new ClienteDominio());
        final var entidadFiltro = ensamblador.ensamblarEntidad(filtroSeguro);
        final var entidades = fabrica.obtenerClienteDAO().consultarPorFiltro(entidadFiltro);
        return ensamblador.ensamblarDominio(entidades);
    }

    private void validarDatosComunes(final ClienteDominio dominio) {
        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getNombre(), DATO_NOMBRE);
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getNombre(), DATO_NOMBRE,
                LONGITUD_MINIMA_NOMBRE, LONGITUD_MAXIMA_NOMBRE);
        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getApellido(), DATO_APELLIDO);
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getApellido(), DATO_APELLIDO,
                LONGITUD_MINIMA_APELLIDO, LONGITUD_MAXIMA_APELLIDO);
        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getCorreoElectronico(), DATO_CORREO);
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getCorreoElectronico(), DATO_CORREO,
                LONGITUD_MINIMA_CORREO, LONGITUD_MAXIMA_CORREO);
        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getContrasena(), DATO_CONTRASENA);
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getContrasena(), DATO_CONTRASENA,
                LONGITUD_MINIMA_CONTRASENA, LONGITUD_MAXIMA_CONTRASENA);
        validarTelefonoOpcional(dominio.getTelefono());
    }

    private void validarTelefonoOpcional(final String telefono) {
        if (!UtilTexto.estaVacia(telefono)) {
            ValidarLongitudTextoEsValida.ejecutarValidacion(telefono, DATO_TELEFONO,
                    LONGITUD_MINIMA_TELEFONO, LONGITUD_MAXIMA_TELEFONO);
        }
    }

    private void asignarNuevoIdentificador(final ClienteDominio dominio) {
        dominio.setId(UtilUUID.generarNuevoUUID());
    }

    private void asegurarCorreoNoExista(final ClienteDominio dominio) {
        ValidarCorreoClienteNoExiste.ejecutarValidacion(dominio.getCorreoElectronico(), dominio.getId(), fabrica);
    }

    private void asegurarClienteExista(final UUID id) {
        ValidarClienteExistePorId.ejecutarValidacion(id, fabrica);
    }

    private void guardar(final ClienteDominio dominio) {
        fabrica.obtenerClienteDAO().acceder(ensamblador.ensamblarEntidad(dominio));
    }

    private void modificar(final ClienteDominio dominio) {
        fabrica.obtenerClienteDAO().actualizar(ensamblador.ensamblarEntidad(dominio));
    }

    private void borrar(final UUID id) {
        fabrica.obtenerClienteDAO().eliminar(id);
    }
}
