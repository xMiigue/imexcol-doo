package co.edu.uco.imexcol.negocio.fachada;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.CarritoComprasDTO;

public interface CarritoComprasFachada {

    void registrar(CarritoComprasDTO dto);

    void actualizar(CarritoComprasDTO dto);

    void eliminar(UUID id);

    List<CarritoComprasDTO> consultar(CarritoComprasDTO filtros);
}
