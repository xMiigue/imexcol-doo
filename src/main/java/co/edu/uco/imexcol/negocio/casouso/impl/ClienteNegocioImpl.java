package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.negocio.casouso.ClienteNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarIdNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarLongitudTextoEsValida;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarTextoEsObligatorio;
import co.edu.uco.imexcol.negocio.dominio.ClienteDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

public final class ClienteNegocioImpl implements ClienteNegocio {

    private static final String CASO_USO = "ClienteNegocio";

    public ClienteNegocioImpl() {
        super();
        // TODO: recibir DAOFactory cuando exista la capa de datos.
    }

    @Override
    public void registrar(final ClienteDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new ClienteDominio());
        validarDatosComunes(dominioSeguro);
        // TODO: integrar con DAO — daoFactory.obtenerClienteDAO().crear(...)
        throw operacionPendiente("registrar");
    }

    @Override
    public void actualizar(final ClienteDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new ClienteDominio());
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominioSeguro.getId(), "cliente");
        validarDatosComunes(dominioSeguro);
        // TODO: integrar con DAO — daoFactory.obtenerClienteDAO().modificar(...)
        throw operacionPendiente("actualizar");
    }

    @Override
    public void eliminar(final UUID id) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(id, "cliente");
        // TODO: integrar con DAO — daoFactory.obtenerClienteDAO().eliminar(id);
        throw operacionPendiente("eliminar");
    }

    @Override
    public List<ClienteDominio> consultar(final ClienteDominio filtros) {
        UtilObjeto.obtenerValorDefecto(filtros, new ClienteDominio());
        // TODO: integrar con DAO — daoFactory.obtenerClienteDAO().consultarPorFiltro(...)
        return new ArrayList<>();
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

    private static ImexcolException operacionPendiente(final String operacion) {
        return ImexcolException.crear(
                MensajesEnum.ERROR_USUARIO_CASO_USO_DAO_PENDIENTE.getContenido(),
                MensajesEnum.ERROR_TECNICO_CASO_USO_DAO_PENDIENTE.getContenido().formatted(operacion, CASO_USO),
                Lugar.NEGOCIO);
    }
}
