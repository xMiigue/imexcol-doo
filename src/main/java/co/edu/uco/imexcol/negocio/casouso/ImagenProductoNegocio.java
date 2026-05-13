package co.edu.uco.imexcol.negocio.casouso;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.negocio.dominio.ImagenProductoDominio;

public interface ImagenProductoNegocio {

    void registrar(ImagenProductoDominio dominio);

    void actualizar(ImagenProductoDominio dominio);

    void eliminar(UUID id);

    List<ImagenProductoDominio> consultar(ImagenProductoDominio filtros);
}
