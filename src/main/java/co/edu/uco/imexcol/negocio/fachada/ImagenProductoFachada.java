package co.edu.uco.imexcol.negocio.fachada;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.ImagenProductoDTO;

public interface ImagenProductoFachada {

    void registrar(ImagenProductoDTO dto);

    void actualizar(ImagenProductoDTO dto);

    void eliminar(UUID id);

    List<ImagenProductoDTO> consultar(ImagenProductoDTO filtros);
}
