package co.edu.uco.imexcol.datos.dao;

import java.util.UUID;

import co.edu.uco.imexcol.entidad.ItemCarritoEntidad;

public interface ItemCarritoDAO
        extends CrearDAO<ItemCarritoEntidad>,
                RecuperarDAO<ItemCarritoEntidad, UUID>,
                ActualizarDAO<ItemCarritoEntidad>,
                EliminarDAO<UUID> {
}
