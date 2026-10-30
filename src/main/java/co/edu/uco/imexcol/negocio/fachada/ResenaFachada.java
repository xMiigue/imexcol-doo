package co.edu.uco.imexcol.negocio.fachada;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.ResenaDTO;

public interface ResenaFachada {

    void registrar(ResenaDTO dto);

    void eliminarPorCliente(UUID idResena, UUID idCliente);

    void eliminarPorAdministrador(UUID idResena, UUID idAdministrador);

    List<ResenaDTO> consultarPorProducto(UUID idProducto);

    List<ResenaDTO> consultar(ResenaDTO filtros);
}
