package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.entidad.ClienteDAO;
import co.edu.uco.imexcol.negocio.assembler.entidad.impl.ClienteEntidadAssembler;
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

    private final ClienteDAO dao;
    private final ClienteEntidadAssembler ensamblador;

    public ClienteNegocioImpl(final ClienteDAO dao) {
        super();
        if (UtilObjeto.esNulo(dao)) {
            throw ImexcolException.crear(
                    MensajesEnum.ERROR_USUARIO_FACTORY_NO_INICIALIZADA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_FACTORY_NO_INICIALIZADA.getContenido(),
                    Lugar.NEGOCIO);
        }
        this.dao = dao;
        this.ensamblador = ClienteEntidadAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final ClienteDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new ClienteDominio());
        validarDatosComunes(dominioSeguro);
        dao.acceder(ensamblador.ensamblarEntidad(dominioSeguro));
    }

    @Override
    public void actualizar(final ClienteDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new ClienteDominio());
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominioSeguro.getId(), "cliente");
        validarDatosComunes(dominioSeguro);
        dao.actualizar(ensamblador.ensamblarEntidad(dominioSeguro));
    }

    @Override
    public void eliminar(final UUID id) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(id, "cliente");
        dao.eliminar(id);
    }

    @Override
    public List<ClienteDominio> consultar(final ClienteDominio filtros) {
        final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new ClienteDominio());
        final var entidadFiltro = ensamblador.ensamblarEntidad(filtroSeguro);
        final var entidades = dao.consultarPorFiltro(entidadFiltro);
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
}
