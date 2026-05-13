package co.edu.uco.imexcol.negocio.casouso;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.negocio.dominio.ProductoDominio;

public interface ProductoNegocio {

    void registrar(ProductoDominio dominio);

    void actualizar(ProductoDominio dominio);

    void eliminar(UUID id);

    List<ProductoDominio> consultar(ProductoDominio filtros);
}
