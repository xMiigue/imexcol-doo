package co.edu.uco.imexcol.negocio.fachada.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.EventoRastreoDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.impl.EventoRastreoDTOAssembler;
import co.edu.uco.imexcol.negocio.casouso.impl.EventoRastreoNegocioImpl;
import co.edu.uco.imexcol.negocio.fachada.EventoRastreoFachada;

public final class EventoRastreoFachadaImpl implements EventoRastreoFachada {

    private final EventoRastreoDTOAssembler ensamblador;

    public EventoRastreoFachadaImpl() {
        super();
        this.ensamblador = EventoRastreoDTOAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final EventoRastreoDTO dto) {
        EjecutorTransaccionFachada.ejecutar(fabrica ->
                new EventoRastreoNegocioImpl(fabrica).registrar(ensamblador.ensamblarDominio(dto)));
    }

    @Override
    public List<EventoRastreoDTO> consultarHistorialPorEnvio(final UUID idEnvio) {
        return EjecutorTransaccionFachada.ejecutarConResultado(fabrica -> ensamblador.ensamblarDTO(
                new EventoRastreoNegocioImpl(fabrica).consultarHistorialPorEnvio(idEnvio)));
    }

    @Override
    public List<EventoRastreoDTO> consultarHistorialDelCliente(final UUID idEnvio, final UUID idCliente) {
        return EjecutorTransaccionFachada.ejecutarConResultado(fabrica -> ensamblador.ensamblarDTO(
                new EventoRastreoNegocioImpl(fabrica).consultarHistorialDelCliente(idEnvio, idCliente)));
    }
}
