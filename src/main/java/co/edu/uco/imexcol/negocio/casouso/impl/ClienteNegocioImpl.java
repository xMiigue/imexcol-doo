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
        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getNombre(), "nombre del cliente");
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getNombre(), "nombre del cliente", 2, 100);

        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getApellido(), "apellido del cliente");
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getApellido(), "apellido del cliente", 2, 100);

        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getCorreoElectronico(),
                "correo electrónico del cliente");
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getCorreoElectronico(),
                "correo electrónico del cliente", 6, 150);

        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getContrasena(), "contraseña del cliente");
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getContrasena(), "contraseña del cliente", 8, 255);

        if (!UtilTexto.estaVacia(dominio.getTelefono())) {
            ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getTelefono(),
                    "teléfono del cliente", 7, 20);
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
