package co.edu.uco.imexcol.negocio.fachada.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.imexcol.dto.ImagenProductoDTO;
import co.edu.uco.imexcol.negocio.assembler.dto.impl.ImagenProductoDTOAssembler;
import co.edu.uco.imexcol.negocio.casouso.impl.ImagenProductoNegocioImpl;
import co.edu.uco.imexcol.negocio.fachada.ImagenProductoFachada;

public final class ImagenProductoFachadaImpl implements ImagenProductoFachada {

    private final ImagenProductoDTOAssembler ensamblador;

    public ImagenProductoFachadaImpl() {
        super();
        this.ensamblador = ImagenProductoDTOAssembler.obtenerInstancia();
    }

    @Override
    public void registrar(final ImagenProductoDTO dto) {
        EjecutorTransaccionFachada.ejecutar(fabrica ->
                new ImagenProductoNegocioImpl(fabrica).registrar(ensamblador.ensamblarDominio(dto)));
    }

    @Override
    public void actualizar(final ImagenProductoDTO dto) {
        EjecutorTransaccionFachada.ejecutar(fabrica ->
                new ImagenProductoNegocioImpl(fabrica).actualizar(ensamblador.ensamblarDominio(dto)));
    }

    @Override
    public void eliminar(final UUID idProducto, final UUID idImagen) {
        EjecutorTransaccionFachada.ejecutar(fabrica ->
                new ImagenProductoNegocioImpl(fabrica).eliminar(idProducto, idImagen));
    }

    @Override
    public List<ImagenProductoDTO> consultarPorProducto(final UUID idProducto) {
        return EjecutorTransaccionFachada.ejecutarConResultado(fabrica -> ensamblador.ensamblarDTO(
                new ImagenProductoNegocioImpl(fabrica).consultarPorProducto(idProducto)));
    }
}
