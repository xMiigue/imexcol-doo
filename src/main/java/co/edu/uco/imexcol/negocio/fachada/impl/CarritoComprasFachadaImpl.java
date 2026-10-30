package co.edu.uco.imexcol.negocio.fachada.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.CarritoComprasDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.impl.CarritoComprasDTOAssembler;
import co.edu.uco.imexcol.negocio.casouso.impl.CarritoComprasNegocioImpl;
import co.edu.uco.imexcol.negocio.fachada.CarritoComprasFachada;

public final class CarritoComprasFachadaImpl implements CarritoComprasFachada {

    private final CarritoComprasDTOAssembler ensamblador;

    public CarritoComprasFachadaImpl() {
        super();
        this.ensamblador = CarritoComprasDTOAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final CarritoComprasDTO dto) {
        EjecutorTransaccionFachada.ejecutar(fabrica ->
                new CarritoComprasNegocioImpl(fabrica).registrar(ensamblador.ensamblarDominio(dto)));
    }

    @Override
    public void actualizar(final CarritoComprasDTO dto) {
        EjecutorTransaccionFachada.ejecutar(fabrica ->
                new CarritoComprasNegocioImpl(fabrica).actualizar(ensamblador.ensamblarDominio(dto)));
    }

    @Override
    public void eliminar(final UUID id) {
        EjecutorTransaccionFachada.ejecutar(fabrica -> new CarritoComprasNegocioImpl(fabrica).eliminar(id));
    }

    @Override
    public List<CarritoComprasDTO> consultar(final CarritoComprasDTO filtros) {
        return EjecutorTransaccionFachada.ejecutarConResultado(fabrica -> ensamblador.ensamblarDTO(
                new CarritoComprasNegocioImpl(fabrica).consultar(ensamblador.ensamblarDominio(filtros))));
    }
}
