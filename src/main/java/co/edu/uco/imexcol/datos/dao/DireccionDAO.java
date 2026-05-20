package co.edu.uco.imexcol.datos.dao;

import java.util.UUID;

import co.edu.uco.imexcol.entidad.DireccionEntidad;

public interface DireccionDAO
        extends AccederDAO<DireccionEntidad>,
                RecuperarDAO<DireccionEntidad, UUID>,
                ActualizarDAO<DireccionEntidad>,
                EliminarDAO<UUID> {
}
