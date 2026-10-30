package co.edu.uco.imexcol.datos.dao;

import java.util.UUID;

import co.edu.uco.imexcol.datos.dao.CrearDAO;
import co.edu.uco.imexcol.datos.dao.ActualizarDAO;
import co.edu.uco.imexcol.datos.dao.EliminarDAO;
import co.edu.uco.imexcol.datos.dao.RecuperarDAO;
import co.edu.uco.imexcol.entidad.ClienteEntidad;

public interface ClienteDAO
        extends CrearDAO<ClienteEntidad>,
                RecuperarDAO<ClienteEntidad, UUID>,
                ActualizarDAO<ClienteEntidad>,
                EliminarDAO<UUID> {
}
