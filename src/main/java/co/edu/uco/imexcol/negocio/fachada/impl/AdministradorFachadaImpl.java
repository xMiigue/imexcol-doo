package co.edu.uco.imexcol.negocio.fachada.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.dto.AdministradorDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.impl.AdministradorDTOAssembler;
import co.edu.uco.imexcol.negocio.casouso.impl.AdministradorNegocioImpl;
import co.edu.uco.imexcol.negocio.fachada.AdministradorFachada;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolFachadaException;

public final class AdministradorFachadaImpl implements AdministradorFachada {

    private final AdministradorDTOAssembler ensamblador;

    public AdministradorFachadaImpl() {
        super();
        this.ensamblador = AdministradorDTOAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final AdministradorDTO dto) {
        final var fabrica = FabricaDAO.obtenerFabrica();
        try {
            fabrica.iniciarTransaccion();
            final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new AdministradorDTO());
            final var dominio = ensamblador.ensamblarDominio(dtoSeguro);
            final var negocio = new AdministradorNegocioImpl(fabrica);
            negocio.registrar(dominio);
            fabrica.confirmarTransaccion();
        } catch (final ImexcolException excepcion) {
            fabrica.revertirTransaccion();
            throw excepcion;
        } catch (final Exception excepcion) {
            fabrica.revertirTransaccion();
            throw envolverErrorInesperado(excepcion);
        } finally {
            fabrica.cerrarConexion();
        }
    }

    @Override
    public void actualizar(final AdministradorDTO dto) {
        final var fabrica = FabricaDAO.obtenerFabrica();
        try {
            fabrica.iniciarTransaccion();
            final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new AdministradorDTO());
            final var dominio = ensamblador.ensamblarDominio(dtoSeguro);
            final var negocio = new AdministradorNegocioImpl(fabrica);
            negocio.actualizar(dominio);
            fabrica.confirmarTransaccion();
        } catch (final ImexcolException excepcion) {
            fabrica.revertirTransaccion();
            throw excepcion;
        } catch (final Exception excepcion) {
            fabrica.revertirTransaccion();
            throw envolverErrorInesperado(excepcion);
        } finally {
            fabrica.cerrarConexion();
        }
    }

    @Override
    public void eliminar(final UUID id) {
        final var fabrica = FabricaDAO.obtenerFabrica();
        try {
            fabrica.iniciarTransaccion();
            final var negocio = new AdministradorNegocioImpl(fabrica);
            negocio.eliminar(id);
            fabrica.confirmarTransaccion();
        } catch (final ImexcolException excepcion) {
            fabrica.revertirTransaccion();
            throw excepcion;
        } catch (final Exception excepcion) {
            fabrica.revertirTransaccion();
            throw envolverErrorInesperado(excepcion);
        } finally {
            fabrica.cerrarConexion();
        }
    }

    @Override
    public List<AdministradorDTO> consultar(final AdministradorDTO filtros) {
        final var fabrica = FabricaDAO.obtenerFabrica();
        try {
            fabrica.iniciarTransaccion();
            final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new AdministradorDTO());
            final var dominioFiltro = ensamblador.ensamblarDominio(filtroSeguro);
            final var negocio = new AdministradorNegocioImpl(fabrica);
            final var dominios = negocio.consultar(dominioFiltro);
            final var resultado = ensamblador.ensamblarDTO(dominios);
            fabrica.confirmarTransaccion();
            return resultado;
        } catch (final ImexcolException excepcion) {
            fabrica.revertirTransaccion();
            throw excepcion;
        } catch (final Exception excepcion) {
            fabrica.revertirTransaccion();
            throw envolverErrorInesperado(excepcion);
        } finally {
            fabrica.cerrarConexion();
        }
    }

    @Override
    public AdministradorDTO autenticar(final AdministradorDTO credenciales) {
        final var fabrica = FabricaDAO.obtenerFabrica();
        try {
            fabrica.iniciarTransaccion();
            final var credencialesSeguras = UtilObjeto.obtenerValorDefecto(credenciales, new AdministradorDTO());
            final var dominioCredenciales = ensamblador.ensamblarDominio(credencialesSeguras);
            final var negocio = new AdministradorNegocioImpl(fabrica);
            final var autenticado = ensamblador.ensamblarDTO(negocio.autenticar(dominioCredenciales));
            fabrica.confirmarTransaccion();
            return autenticado;
        } catch (final ImexcolException excepcion) {
            fabrica.revertirTransaccion();
            throw excepcion;
        } catch (final Exception excepcion) {
            fabrica.revertirTransaccion();
            throw envolverErrorInesperado(excepcion);
        } finally {
            fabrica.cerrarConexion();
        }
    }

    private static ImexcolFachadaException envolverErrorInesperado(final Exception excepcion) {
        return ImexcolFachadaException.crear(excepcion,
                MensajesEnum.ERROR_USUARIO_FACHADA_OPERACION_INESPERADA.getContenido(),
                MensajesEnum.ERROR_TECNICO_FACHADA_OPERACION_INESPERADA.getContenido());
    }
}
