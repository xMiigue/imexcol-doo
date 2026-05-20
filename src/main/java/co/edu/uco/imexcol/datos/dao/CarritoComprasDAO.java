package co.edu.uco.imexcol.datos.dao;

import java.util.UUID;

import co.edu.uco.imexcol.entidad.CarritoComprasEntidad;

public interface CarritoComprasDAO
        extends AccederDAO<CarritoComprasEntidad>,
                RecuperarDAO<CarritoComprasEntidad, UUID>,
                ActualizarDAO<CarritoComprasEntidad>,
                EliminarDAO<UUID> {
}
