package co.edu.uco.imexcol.datos.dao;

import java.util.UUID;

import co.edu.uco.imexcol.entidad.EventoRastreoEntidad;

public interface EventoRastreoDAO
        extends CrearDAO<EventoRastreoEntidad>,
                RecuperarDAO<EventoRastreoEntidad, UUID> {

    void eliminarPorEnvio(UUID idEnvio);
}
