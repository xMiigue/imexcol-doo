package co.edu.uco.imexcol.negocio.fachada.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.fabrica.FabricaDAO;
import co.edu.uco.imexcol.dto.ClienteDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.impl.ClienteDTOAssembler;
import co.edu.uco.imexcol.negocio.casouso.impl.ClienteNegocioImpl;
import co.edu.uco.imexcol.negocio.fachada.ClienteFachada;
import co.edu.uco.imexcol.transversal.MensajesEnum;
import co.edu.uco.imexcol.transversal.UtilObjeto;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolException;
import co.edu.uco.imexcol.transversal.excepcion.ImexcolFachadaException;

public final class ClienteFachadaImpl implements ClienteFachada {

    private final ClienteDTOAssembler ensamblador;

    public ClienteFachadaImpl() {
        super();
        this.ensamblador = ClienteDTOAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final ClienteDTO dto) {
        final var fabrica = FabricaDAO.obtenerFabrica();
        try {
            fabrica.iniciarTransaccion();
            final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new ClienteDTO());
            final var dominio = ensamblador.ensamblarDominio(dtoSeguro);
            final var negocio = new ClienteNegocioImpl(fabrica);
            negocio.registrar(dominio);
            fabrica.confirmarTransaccion();
        } catch (final ImexcolException excepcion) {
            fabrica.revertirTransaccionSinOcultar(excepcion);
            throw excepcion;
        } catch (final Exception excepcion) {
            fabrica.revertirTransaccionSinOcultar(excepcion);
            throw envolverErrorInesperado(excepcion);
        } finally {
            fabrica.cerrarConexionSinOcultar();
        }
    }

    @Override
    public void actualizar(final ClienteDTO dto) {
        final var fabrica = FabricaDAO.obtenerFabrica();
        try {
            fabrica.iniciarTransaccion();
            final var dtoSeguro = UtilObjeto.obtenerValorDefecto(dto, new ClienteDTO());
            final var dominio = ensamblador.ensamblarDominio(dtoSeguro);
            final var negocio = new ClienteNegocioImpl(fabrica);
            negocio.actualizar(dominio);
            fabrica.confirmarTransaccion();
        } catch (final ImexcolException excepcion) {
            fabrica.revertirTransaccionSinOcultar(excepcion);
            throw excepcion;
        } catch (final Exception excepcion) {
            fabrica.revertirTransaccionSinOcultar(excepcion);
            throw envolverErrorInesperado(excepcion);
        } finally {
            fabrica.cerrarConexionSinOcultar();
        }
    }

    @Override
    public void eliminar(final UUID id) {
        final var fabrica = FabricaDAO.obtenerFabrica();
        try {
            fabrica.iniciarTransaccion();
            final var negocio = new ClienteNegocioImpl(fabrica);
            negocio.eliminar(id);
            fabrica.confirmarTransaccion();
        } catch (final ImexcolException excepcion) {
            fabrica.revertirTransaccionSinOcultar(excepcion);
            throw excepcion;
        } catch (final Exception excepcion) {
            fabrica.revertirTransaccionSinOcultar(excepcion);
            throw envolverErrorInesperado(excepcion);
        } finally {
            fabrica.cerrarConexionSinOcultar();
        }
    }

    @Override
    public List<ClienteDTO> consultar(final ClienteDTO filtros) {
        final var fabrica = FabricaDAO.obtenerFabrica();
        try {
            fabrica.iniciarTransaccion();
            final var filtroSeguro = UtilObjeto.obtenerValorDefecto(filtros, new ClienteDTO());
            final var dominioFiltro = ensamblador.ensamblarDominio(filtroSeguro);
            final var negocio = new ClienteNegocioImpl(fabrica);
            final var dominios = negocio.consultar(dominioFiltro);
            final var resultado = ensamblador.ensamblarDTO(dominios);
            fabrica.confirmarTransaccion();
            return resultado;
        } catch (final ImexcolException excepcion) {
            fabrica.revertirTransaccionSinOcultar(excepcion);
            throw excepcion;
        } catch (final Exception excepcion) {
            fabrica.revertirTransaccionSinOcultar(excepcion);
            throw envolverErrorInesperado(excepcion);
        } finally {
            fabrica.cerrarConexionSinOcultar();
        }
    }

    @Override
    public ClienteDTO autenticar(final ClienteDTO credenciales) {
        final var fabrica = FabricaDAO.obtenerFabrica();
        try {
            fabrica.iniciarTransaccion();
            final var credencialesSeguras = UtilObjeto.obtenerValorDefecto(credenciales, new ClienteDTO());
            final var dominioCredenciales = ensamblador.ensamblarDominio(credencialesSeguras);
            final var negocio = new ClienteNegocioImpl(fabrica);
            final var autenticado = ensamblador.ensamblarDTO(negocio.autenticar(dominioCredenciales));
            fabrica.confirmarTransaccion();
            return autenticado;
        } catch (final ImexcolException excepcion) {
            fabrica.revertirTransaccionSinOcultar(excepcion);
            throw excepcion;
        } catch (final Exception excepcion) {
            fabrica.revertirTransaccionSinOcultar(excepcion);
            throw envolverErrorInesperado(excepcion);
        } finally {
            fabrica.cerrarConexionSinOcultar();
        }
    }

    private static ImexcolFachadaException envolverErrorInesperado(final Exception excepcion) {
        return ImexcolFachadaException.crear(excepcion,
                MensajesEnum.ERROR_USUARIO_FACHADA_OPERACION_INESPERADA.getContenido(),
                MensajesEnum.ERROR_TECNICO_FACHADA_OPERACION_INESPERADA.getContenido());
    }
}
