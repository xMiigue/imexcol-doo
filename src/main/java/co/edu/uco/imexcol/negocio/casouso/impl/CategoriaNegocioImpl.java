package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.entidad.CategoriaDAO;
import co.edu.uco.imexcol.negocio.assembler.entidad.impl.CategoriaEntidadAssembler;
import co.edu.uco.imexcol.negocio.casouso.CategoriaNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarIdNoEsValorPorDefecto;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarLongitudTextoEsValida;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarTextoEsObligatorio;
import co.edu.uco.imexcol.negocio.dominio.CategoriaDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

public final class CategoriaNegocioImpl implements CategoriaNegocio {

    private final CategoriaDAO dao;
    private final CategoriaEntidadAssembler ensamblador;

    public CategoriaNegocioImpl(final CategoriaDAO dao) {
        super();
        if (UtilObjeto.esNulo(dao)) {
            throw ImexcolException.crear(
                    MensajesEnum.ERROR_USUARIO_FACTORY_NO_INICIALIZADA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_FACTORY_NO_INICIALIZADA.getContenido(),
                    Lugar.NEGOCIO);
        }
        this.dao = dao;
        this.ensamblador = CategoriaEntidadAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final CategoriaDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new CategoriaDominio());
        validarDatosComunes(dominioSeguro);
        dao.acceder(ensamblador.ensamblarEntidad(dominioSeguro));
    }

    @Override
    public void actualizar(final CategoriaDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new CategoriaDominio());
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(dominioSeguro.getId(), "categoría");
        validarDatosComunes(dominioSeguro);
        dao.actualizar(ensamblador.ensamblarEntidad(dominioSeguro));
    }

    @Override
    public void eliminar(final UUID id) {
        ValidarIdNoEsValorPorDefecto.ejecutarValidacion(id, "categoría");
        dao.eliminar(id);
    }

    @Override
    public List<CategoriaDominio> consultar(final CategoriaDominio filtros) {
        final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new CategoriaDominio());
        final var entidadFiltro = ensamblador.ensamblarEntidad(filtroSeguro);
        final var entidades = dao.consultarPorFiltro(entidadFiltro);
        return ensamblador.ensamblarDominio(entidades);
    }

    private void validarDatosComunes(final CategoriaDominio dominio) {
        ValidarTextoEsObligatorio.ejecutarValidacion(dominio.getNombre(), "nombre de la categoría");
        ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getNombre(), "nombre de la categoría", 3, 100);
        if (!UtilTexto.estaVacia(dominio.getDescripcion())) {
            ValidarLongitudTextoEsValida.ejecutarValidacion(dominio.getDescripcion(),
                    "descripción de la categoría", 0, 255);
        }
    }
}
