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
}
