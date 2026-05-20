package co.edu.uco.imexcol.datos.dao;

import java.util.UUID;

import co.edu.uco.imexcol.entidad.LineaPedidoEntidad;

public interface LineaPedidoDAO
        extends AccederDAO<LineaPedidoEntidad>,
                RecuperarDAO<LineaPedidoEntidad, UUID>,
                ActualizarDAO<LineaPedidoEntidad>,
                EliminarDAO<UUID> {
}
