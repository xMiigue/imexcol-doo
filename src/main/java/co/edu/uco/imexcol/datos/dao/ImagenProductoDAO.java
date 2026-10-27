package co.edu.uco.imexcol.datos.dao;

import java.util.UUID;

import co.edu.uco.imexcol.entidad.ImagenProductoEntidad;

public interface ImagenProductoDAO
        extends AccederDAO<ImagenProductoEntidad>,
                RecuperarDAO<ImagenProductoEntidad, UUID>,
                ActualizarDAO<ImagenProductoEntidad>,
                EliminarDAO<UUID> {

    void eliminarPorProducto(UUID idProducto);
}
