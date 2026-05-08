package co.edu.uco.imexcol.negocio.dominio;

import java.util.UUID;

import co.edu.uco.imexcol.transversal.UtilBooleano;
import co.edu.uco.imexcol.transversal.UtilTexto;
import co.edu.uco.imexcol.transversal.UtilUUID;

public final class MetodoPagoDominio {

    private UUID id;
    private String nombre;
    private String descripcion;
    private boolean estado;

    private MetodoPagoDominio(final Builder builder) {
        super();
        setId(builder.id);
        setNombre(builder.nombre);
        setDescripcion(builder.descripcion);
        setEstado(builder.estado);
    }

    public UUID getId() {
        return id;
    }

    private void setId(final UUID id) {
        this.id = UtilUUID.obtenerValorDefecto(id);
    }

    public String getNombre() {
        return nombre;
    }

    private void setNombre(final String nombre) {
        this.nombre = UtilTexto.aplicarTrim(nombre);
    }

    public String getDescripcion() {
        return descripcion;
    }

    private void setDescripcion(final String descripcion) {
        this.descripcion = UtilTexto.aplicarTrim(descripcion);
    }

    public boolean isEstado() {
        return estado;
    }

    private void setEstado(final Boolean estado) {
        this.estado = UtilBooleano.obtenerValorDefecto(estado);
    }

    public static final class Builder {

        private UUID id;
        private String nombre;
        private String descripcion;
        private Boolean estado;

        public Builder id(final UUID id) {
            this.id = id;
            return this;
        }

        public Builder nombre(final String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder descripcion(final String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public Builder estado(final Boolean estado) {
            this.estado = estado;
            return this;
        }

        public MetodoPagoDominio build() {
            return new MetodoPagoDominio(this);
        }
    }
}
