package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.assembler.entidad.impl.AdministradorEntidadAssembler;
import co.edu.uco.imexcol.negocio.casouso.AdministradorNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarIdNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarLongitudTextoEsValida;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarTextoEsObligatorio;
import co.edu.uco.imexcol.negocio.dominio.AdministradorDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
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
        dominioSeguro.setId(UUID.randomUUID());
        fabrica.obtenerAdministradorDAO().acceder(ensamblador.ensamblarEntidad(dominioSeguro));
    }

    @Override
    public void actualizar(final AdministradorDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new AdministradorDominio());
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominioSeguro.getId(), "administrador");
        validarDatosComunes(dominioSeguro);
        fabrica.obtenerAdministradorDAO().actualizar(ensamblador.ensamblarEntidad(dominioSeguro));
    }

    @Override
    public void eliminar(final UUID id) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(id, "administrador");
        fabrica.obtenerAdministradorDAO().eliminar(id);
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
}
