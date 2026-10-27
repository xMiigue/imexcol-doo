package co.edu.uco.imexcol.negocio.casouso.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.negocio.assembler.entidad.impl.CategoriaEntidadAssembler;
import co.edu.uco.imexcol.negocio.casouso.CategoriaNegocio;
import co.edu.uco.imexcol.negocio.casouso.validador.categoria.ValidarCategoriaExistePorId;
import co.edu.uco.imexcol.negocio.casouso.validador.categoria.ValidarCategoriaNoTieneProductos;
import co.edu.uco.imexcol.negocio.casouso.validador.categoria.ValidarNombreCategoriaNoExiste;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarLongitudTextoEsValida;
import co.edu.uco.imexcol.negocio.casouso.validador.genericas.ValidarTextoEsObligatorio;
import co.edu.uco.imexcol.negocio.dominio.CategoriaDominio;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolNegocioException;

public final class CategoriaNegocioImpl implements CategoriaNegocio {

    private final FabricaDAO fabrica;
    private final CategoriaEntidadAssembler ensamblador;

    public CategoriaNegocioImpl(final FabricaDAO fabrica) {
        super();
        if (UtilObjeto.esNulo(fabrica)) {
            throw ImexcolNegocioException.crear(
                    MensajesEnum.ERROR_USUARIO_FACTORY_NO_INICIALIZADA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_FACTORY_NO_INICIALIZADA.getContenido());
        }
        this.fabrica = fabrica;
        this.ensamblador = CategoriaEntidadAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final CategoriaDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new CategoriaDominio());
        validarDatosComunes(dominioSeguro);
        asignarNuevoIdentificador(dominioSeguro);
        asegurarNombreNoExista(dominioSeguro);
        guardar(dominioSeguro);
    }

    @Override
    public void actualizar(final CategoriaDominio dominio) {
        final var dominioSeguro = UtilObjeto.obtenerValorDefecto(dominio, new CategoriaDominio());
        validarDatosComunes(dominioSeguro);
        asegurarCategoriaExista(dominioSeguro.getId());
        asegurarNombreNoExista(dominioSeguro);
        modificar(dominioSeguro);
    }

    @Override
    public void eliminar(final UUID id) {
        asegurarCategoriaExista(id);
        asegurarCategoriaNoTengaProductos(id);
        borrar(id);
    }

    @Override
    public List<CategoriaDominio> consultar(final CategoriaDominio filtros) {
        final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new CategoriaDominio());
        final var entidadFiltro = ensamblador.ensamblarEntidad(filtroSeguro);
        final var entidades = fabrica.obtenerCategoriaDAO().consultarPorFiltro(entidadFiltro);
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

    private void asignarNuevoIdentificador(final CategoriaDominio dominio) {
        dominio.setId(UtilUUID.generarNuevoUUID());
    }

    private void asegurarNombreNoExista(final CategoriaDominio dominio) {
        ValidarNombreCategoriaNoExiste.ejecutarValidacion(dominio.getNombre(), dominio.getId(), fabrica);
    }

    private void asegurarCategoriaExista(final UUID id) {
        ValidarCategoriaExistePorId.ejecutarValidacion(id, fabrica);
    }

    private void asegurarCategoriaNoTengaProductos(final UUID id) {
        ValidarCategoriaNoTieneProductos.ejecutarValidacion(id, fabrica);
    }

    private void guardar(final CategoriaDominio dominio) {
        fabrica.obtenerCategoriaDAO().acceder(ensamblador.ensamblarEntidad(dominio));
    }

    private void modificar(final CategoriaDominio dominio) {
        fabrica.obtenerCategoriaDAO().actualizar(ensamblador.ensamblarEntidad(dominio));
    }

    private void borrar(final UUID id) {
        fabrica.obtenerCategoriaDAO().eliminar(id);
    }
}
