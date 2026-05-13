package co.edu.uco.imexcol.negocio.fachada.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.CategoriaDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.impl.CategoriaDTOAssembler;
import co.edu.uco.imexcol.negocio.casouso.CategoriaNegocio;
import co.edu.uco.imexcol.negocio.casouso.impl.CategoriaNegocioImpl;
import co.edu.uco.imexcol.negocio.fachada.CategoriaFachada;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.enums.Lugar;

public final class CategoriaFachadaImpl implements CategoriaFachada {

    private final CategoriaNegocio negocio;
    private final CategoriaDTOAssembler ensamblador;

    public CategoriaFachadaImpl() {
        super();
        // TODO: integrar con DAOFactory — instanciar CategoriaNegocioImpl(daoFactory) cuando exista la capa de datos.
        this.negocio = new CategoriaNegocioImpl();
        this.ensamblador = CategoriaDTOAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final CategoriaDTO dto) {
        // TODO: integrar con DAOFactory — daoFactory.iniciarTransaccion();
        try {
            final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new CategoriaDTO());
            final var dominio = ensamblador.ensamblarDominio(dtoSeguro);
            negocio.registrar(dominio);
            // TODO: integrar con DAOFactory — daoFactory.confirmarTransaccion();
        } catch (final ImexcolException excepcion) {
            // TODO: integrar con DAOFactory — daoFactory.revertirTransaccion();
            throw excepcion;
        } catch (final Exception excepcion) {
            // TODO: integrar con DAOFactory — daoFactory.revertirTransaccion();
            throw ImexcolException.crear(excepcion,
                    MensajesEnum.ERROR_USUARIO_FACHADA_OPERACION_INESPERADA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_FACHADA_OPERACION_INESPERADA.getContenido(),
                    Lugar.FACHADA);
        }
        // TODO: integrar con DAOFactory — daoFactory.cerrarConexion();
    }

    @Override
    public void actualizar(final CategoriaDTO dto) {
        // TODO: integrar con DAOFactory — daoFactory.iniciarTransaccion();
        try {
            final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new CategoriaDTO());
            final var dominio = ensamblador.ensamblarDominio(dtoSeguro);
            negocio.actualizar(dominio);
            // TODO: integrar con DAOFactory — daoFactory.confirmarTransaccion();
        } catch (final ImexcolException excepcion) {
            // TODO: integrar con DAOFactory — daoFactory.revertirTransaccion();
            throw excepcion;
        } catch (final Exception excepcion) {
            // TODO: integrar con DAOFactory — daoFactory.revertirTransaccion();
            throw ImexcolException.crear(excepcion,
                    MensajesEnum.ERROR_USUARIO_FACHADA_OPERACION_INESPERADA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_FACHADA_OPERACION_INESPERADA.getContenido(),
                    Lugar.FACHADA);
        }
        // TODO: integrar con DAOFactory — daoFactory.cerrarConexion();
    }

    @Override
    public void eliminar(final UUID id) {
        // TODO: integrar con DAOFactory — daoFactory.iniciarTransaccion();
        try {
            negocio.eliminar(id);
            // TODO: integrar con DAOFactory — daoFactory.confirmarTransaccion();
        } catch (final ImexcolException excepcion) {
            // TODO: integrar con DAOFactory — daoFactory.revertirTransaccion();
            throw excepcion;
        } catch (final Exception excepcion) {
            // TODO: integrar con DAOFactory — daoFactory.revertirTransaccion();
            throw ImexcolException.crear(excepcion,
                    MensajesEnum.ERROR_USUARIO_FACHADA_OPERACION_INESPERADA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_FACHADA_OPERACION_INESPERADA.getContenido(),
                    Lugar.FACHADA);
        }
        // TODO: integrar con DAOFactory — daoFactory.cerrarConexion();
    }

    @Override
    public List<CategoriaDTO> consultar(final CategoriaDTO filtros) {
        // TODO: integrar con DAOFactory — daoFactory.iniciarTransaccion();
        try {
            final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new CategoriaDTO());
            final var dominioFiltro = ensamblador.ensamblarDominio(filtroSeguro);
            final var dominios = negocio.consultar(dominioFiltro);
            // TODO: integrar con DAOFactory — daoFactory.confirmarTransaccion();
            return ensamblador.ensamblarDTO(dominios);
        } catch (final ImexcolException excepcion) {
            // TODO: integrar con DAOFactory — daoFactory.revertirTransaccion();
            throw excepcion;
        } catch (final Exception excepcion) {
            // TODO: integrar con DAOFactory — daoFactory.revertirTransaccion();
            throw ImexcolException.crear(excepcion,
                    MensajesEnum.ERROR_USUARIO_FACHADA_OPERACION_INESPERADA.getContenido(),
                    MensajesEnum.ERROR_TECNICO_FACHADA_OPERACION_INESPERADA.getContenido(),
                    Lugar.FACHADA);
        }
        // TODO: integrar con DAOFactory — daoFactory.cerrarConexion();
    }
}
