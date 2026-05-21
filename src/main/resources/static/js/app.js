/* ============================================================
   Imexcol Comercializadora — Frontend e-commerce
   API: /api/v1/{categorias,productos,auth}
   Respuesta backend: { mensajes: string[], datos: T[], exitosa: bool }
   ============================================================ */

const API = {
    categorias: '/api/v1/categorias',
    productos: '/api/v1/productos',
    authCliente: '/api/v1/auth/cliente',
    authAdmin: '/api/v1/auth/administrador',
    registroCliente: '/api/v1/auth/cliente/registro'
};

// Estado global en memoria (no se persiste)
const estado = {
    usuario: null,        // { tipo: 'cliente' | 'administrador', datos: {...} }
    carrito: [],          // [ { producto, cantidad } ]
    catEditandoId: null,
    proEditandoId: null,
    adminInicializado: false,
    clienteInicializado: false
};

// ============ Utilidades ============

function mostrarNotificacion(mensaje, tipo) {
    const nodo = document.getElementById('notificacion');
    nodo.textContent = mensaje;
    nodo.className = 'notificacion notificacion-' + tipo;
    clearTimeout(mostrarNotificacion._timer);
    mostrarNotificacion._timer = setTimeout(() => {
        nodo.classList.add('oculto');
    }, 4500);
}

function extraerMensaje(respuesta, predeterminado) {
    if (respuesta && Array.isArray(respuesta.mensajes) && respuesta.mensajes.length > 0) {
        return respuesta.mensajes.join(' ');
    }
    return predeterminado;
}

function formatoMoneda(valor) {
    if (valor === null || valor === undefined) return '—';
    return new Intl.NumberFormat('es-CO', {
        style: 'currency',
        currency: 'COP',
        minimumFractionDigits: 0,
        maximumFractionDigits: 0
    }).format(valor);
}

function etiquetaEstado(activo) {
    const clase = activo ? 'etiqueta-activa' : 'etiqueta-inactiva';
    const texto = activo ? 'Activo' : 'Inactivo';
    return `<span class="etiqueta ${clase}">${texto}</span>`;
}

function iniciales(nombre) {
    if (!nombre) return '—';
    return nombre.trim().split(/\s+/).slice(0, 2).map(p => p.charAt(0).toUpperCase()).join('');
}

function escapar(texto) {
    if (texto === null || texto === undefined) return '';
    return String(texto)
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;')
        .replace(/'/g, '&#39;');
}

async function peticionApi(url, opciones) {
    const respuesta = await fetch(url, opciones);
    let datos = null;
    try {
        datos = await respuesta.json();
    } catch (e) {
        datos = null;
    }
    if (!respuesta.ok || !datos || !datos.exitosa) {
        throw new Error(extraerMensaje(datos, 'Ocurrió un problema procesando la solicitud.'));
    }
    return datos;
}

// ============ Navegación entre vistas ============

function cambiarVista(vista) {
    document.querySelectorAll('.nav-enlace').forEach(b => {
        b.classList.toggle('activo', b.dataset.vista === vista);
    });
    document.querySelectorAll('.vista').forEach(v => {
        v.classList.toggle('oculto', v.id !== 'vista-' + vista);
    });
    window.scrollTo({ top: 0, behavior: 'smooth' });
}

function configurarNavegacion() {
    document.querySelectorAll('[data-vista]').forEach(btn => {
        btn.addEventListener('click', evento => {
            evento.preventDefault();
            cambiarVista(btn.dataset.vista);
        });
    });

    // Botones que navegan entre vistas auth (data-ir)
    document.querySelectorAll('[data-ir]').forEach(btn => {
        btn.addEventListener('click', evento => {
            evento.preventDefault();
            cambiarVista(btn.dataset.ir);
        });
    });

    // Botones que hacen scroll a anclas dentro de una misma vista
    document.querySelectorAll('[data-ir-ancla]').forEach(btn => {
        btn.addEventListener('click', () => {
            const ancla = document.getElementById(btn.dataset.irAncla);
            if (ancla) ancla.scrollIntoView({ behavior: 'smooth' });
        });
    });

    // Selección de rol en pantalla de bienvenida
    document.querySelectorAll('[data-rol]').forEach(btn => {
        btn.addEventListener('click', () => {
            const rol = btn.dataset.rol;
            if (rol === 'cliente') cambiarVista('login-cliente');
            else if (rol === 'administrador') cambiarVista('login-administrador');
        });
    });
}

// ============ Sesión / Header ============

function nombreUsuarioActivo() {
    if (!estado.usuario) return '';
    if (estado.usuario.tipo === 'cliente') {
        const c = estado.usuario.datos;
        return `${c.nombre || ''} ${c.apellido || ''}`.trim();
    }
    return estado.usuario.datos.nombreUsuario || '';
}

function actualizarHeader() {
    const header = document.getElementById('header');
    const navCliente = document.getElementById('nav-cliente');
    const navAdmin = document.getElementById('nav-administrador');
    const saludo = document.getElementById('header-saludo');

    if (!estado.usuario) {
        header.classList.add('oculto');
        navCliente.classList.add('oculto');
        navAdmin.classList.add('oculto');
        saludo.textContent = '';
        return;
    }

    header.classList.remove('oculto');
    saludo.textContent = `Hola, ${nombreUsuarioActivo()}`;

    if (estado.usuario.tipo === 'cliente') {
        navCliente.classList.remove('oculto');
        navAdmin.classList.add('oculto');
        actualizarBadgeCarrito();
    } else {
        navAdmin.classList.remove('oculto');
        navCliente.classList.add('oculto');
    }
}

function cerrarSesion() {
    estado.usuario = null;
    estado.carrito = [];
    estado.adminInicializado = false;
    estado.clienteInicializado = false;
    actualizarHeader();
    // Reset de formularios auth
    const formLoginCli = document.getElementById('form-login-cliente');
    const formLoginAdm = document.getElementById('form-login-administrador');
    const formRegistro = document.getElementById('form-registro-cliente');
    if (formLoginCli) formLoginCli.reset();
    if (formLoginAdm) formLoginAdm.reset();
    if (formRegistro) formRegistro.reset();
    cambiarVista('bienvenida');
}

// ============ Login Cliente ============

async function loginCliente(evento) {
    evento.preventDefault();
    const form = evento.target;
    const cuerpo = {
        correoElectronico: form.correoElectronico.value.trim(),
        contrasena: form.contrasena.value
    };
    try {
        const datos = await peticionApi(API.authCliente, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(cuerpo)
        });
        const cliente = (datos.datos && datos.datos[0]) || null;
        if (!cliente) {
            throw new Error('No se recibieron los datos del cliente.');
        }
        estado.usuario = { tipo: 'cliente', datos: cliente };
        estado.carrito = [];
        actualizarHeader();
        mostrarNotificacion(`Bienvenido, ${cliente.nombre}.`, 'exito');
        cambiarVista('tienda');
        if (!estado.clienteInicializado) {
            await cargarCatalogo();
            estado.clienteInicializado = true;
        }
    } catch (error) {
        mostrarNotificacion(error.message || 'Credenciales inválidas.', 'error');
    }
}

// ============ Login Administrador ============

async function loginAdministrador(evento) {
    evento.preventDefault();
    const form = evento.target;
    const cuerpo = {
        nombreUsuario: form.nombreUsuario.value.trim(),
        contrasena: form.contrasena.value
    };
    try {
        const datos = await peticionApi(API.authAdmin, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(cuerpo)
        });
        const admin = (datos.datos && datos.datos[0]) || null;
        if (!admin) {
            throw new Error('No se recibieron los datos del administrador.');
        }
        estado.usuario = { tipo: 'administrador', datos: admin };
        actualizarHeader();
        mostrarNotificacion(`Bienvenido, ${admin.nombreUsuario}.`, 'exito');
        cambiarVista('categorias');
        if (!estado.adminInicializado) {
            await cargarCategorias();
            await cargarProductos();
            await cargarOpcionesCategoria();
            estado.adminInicializado = true;
        }
    } catch (error) {
        mostrarNotificacion(error.message || 'Credenciales inválidas.', 'error');
    }
}

// ============ Registro Cliente ============

async function registrarCliente(evento) {
    evento.preventDefault();
    const form = evento.target;
    const cuerpo = {
        nombre: form.nombre.value.trim(),
        apellido: form.apellido.value.trim(),
        correoElectronico: form.correoElectronico.value.trim(),
        contrasena: form.contrasena.value,
        telefono: form.telefono.value.trim(),
        estado: true
    };
    try {
        const datos = await peticionApi(API.registroCliente, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(cuerpo)
        });
        mostrarNotificacion(
            extraerMensaje(datos, 'Cuenta creada satisfactoriamente. Inicia sesión.'),
            'exito'
        );
        form.reset();
        // Prerellenar login con el correo registrado
        const correoLogin = document.getElementById('login-cliente-correo');
        if (correoLogin) correoLogin.value = cuerpo.correoElectronico;
        cambiarVista('login-cliente');
    } catch (error) {
        mostrarNotificacion(error.message, 'error');
    }
}

// ============ Carrito ============

function actualizarBadgeCarrito() {
    const badge = document.getElementById('badge-carrito');
    if (!badge) return;
    const cantidad = estado.carrito.reduce((sum, item) => sum + item.cantidad, 0);
    badge.textContent = cantidad;
}

function agregarAlCarrito(productoId) {
    const producto = estado.catalogo && estado.catalogo.find(p => p.id === productoId);
    if (!producto) return;
    if (producto.stock <= 0) {
        mostrarNotificacion('Este producto no tiene stock disponible.', 'error');
        return;
    }
    const existente = estado.carrito.find(item => item.producto.id === productoId);
    if (existente) {
        if (existente.cantidad + 1 > producto.stock) {
            mostrarNotificacion('Ya tienes el stock máximo de este producto.', 'error');
            return;
        }
        existente.cantidad += 1;
    } else {
        estado.carrito.push({ producto, cantidad: 1 });
    }
    actualizarBadgeCarrito();
    renderizarCarrito();
    mostrarNotificacion(`Agregado: ${producto.nombre}`, 'exito');
}

function modificarCantidad(productoId, delta) {
    const item = estado.carrito.find(i => i.producto.id === productoId);
    if (!item) return;
    const nueva = item.cantidad + delta;
    if (nueva <= 0) {
        eliminarDelCarrito(productoId);
        return;
    }
    if (nueva > item.producto.stock) {
        mostrarNotificacion('Has alcanzado el stock máximo disponible.', 'error');
        return;
    }
    item.cantidad = nueva;
    actualizarBadgeCarrito();
    renderizarCarrito();
}

function eliminarDelCarrito(productoId) {
    estado.carrito = estado.carrito.filter(i => i.producto.id !== productoId);
    actualizarBadgeCarrito();
    renderizarCarrito();
}

function vaciarCarrito() {
    if (estado.carrito.length === 0) return;
    if (!window.confirm('¿Vaciar el carrito por completo?')) return;
    estado.carrito = [];
    actualizarBadgeCarrito();
    renderizarCarrito();
    mostrarNotificacion('Carrito vaciado.', 'exito');
}

function finalizarCompra() {
    if (estado.carrito.length === 0) {
        mostrarNotificacion('Tu carrito está vacío.', 'error');
        return;
    }
    const total = estado.carrito.reduce((s, i) => s + (i.producto.precio * i.cantidad), 0);
    if (!window.confirm(`Confirmar compra por ${formatoMoneda(total)}?`)) return;
    estado.carrito = [];
    actualizarBadgeCarrito();
    renderizarCarrito();
    mostrarNotificacion(`Compra finalizada. Total: ${formatoMoneda(total)}.`, 'exito');
    cambiarVista('tienda');
}

function renderizarCarrito() {
    const tbody = document.getElementById('tabla-carrito-body');
    const pie = document.getElementById('carrito-pie');
    if (!tbody) return;

    if (estado.carrito.length === 0) {
        tbody.innerHTML = '<tr><td colspan="6" class="celda-vacia">Tu carrito está vacío.</td></tr>';
        if (pie) pie.classList.add('oculto');
        return;
    }

    tbody.innerHTML = estado.carrito.map(item => {
        const p = item.producto;
        const subtotal = p.precio * item.cantidad;
        const cat = (p.categoria && p.categoria.nombre) || '—';
        return `
            <tr>
                <td>${escapar(p.nombre)}</td>
                <td>${escapar(cat)}</td>
                <td class="celda-num">${formatoMoneda(p.precio)}</td>
                <td>
                    <div class="carrito-cantidad">
                        <button class="carrito-cantidad-btn" data-accion-carrito="restar"
                                data-id="${escapar(p.id)}" type="button">−</button>
                        <span class="carrito-cantidad-valor">${item.cantidad}</span>
                        <button class="carrito-cantidad-btn" data-accion-carrito="sumar"
                                data-id="${escapar(p.id)}" type="button">+</button>
                    </div>
                </td>
                <td class="celda-num">${formatoMoneda(subtotal)}</td>
                <td class="celda-acciones">
                    <button class="boton-fila boton-fila-peligro"
                            data-accion-carrito="eliminar"
                            data-id="${escapar(p.id)}" type="button">Quitar</button>
                </td>
            </tr>
        `;
    }).join('');

    const total = estado.carrito.reduce((s, i) => s + (i.producto.precio * i.cantidad), 0);
    const valor = document.getElementById('carrito-total-valor');
    if (valor) valor.textContent = formatoMoneda(total);
    if (pie) pie.classList.remove('oculto');
}

function manejarAccionCarrito(evento) {
    const boton = evento.target.closest('[data-accion-carrito]');
    if (!boton) return;
    const id = boton.dataset.id;
    const accion = boton.dataset.accionCarrito;
    if (accion === 'sumar') modificarCantidad(id, 1);
    else if (accion === 'restar') modificarCantidad(id, -1);
    else if (accion === 'eliminar') eliminarDelCarrito(id);
}

// ============ Catálogo (vista tienda) ============

async function cargarCatalogo() {
    const contenedor = document.getElementById('grilla-catalogo');
    if (!contenedor) return;
    contenedor.innerHTML = '<div class="catalogo-vacio">Cargando catálogo…</div>';
    try {
        const datos = await peticionApi(API.productos);
        estado.catalogo = datos.datos || [];
        renderizarCatalogo(estado.catalogo);
    } catch (error) {
        contenedor.innerHTML = `<div class="catalogo-vacio">${escapar(error.message)}</div>`;
    }
}

function renderizarCatalogo(productos) {
    const contenedor = document.getElementById('grilla-catalogo');
    if (!contenedor) return;
    const activos = productos.filter(p => p.estado);
    if (activos.length === 0) {
        contenedor.innerHTML = '<div class="catalogo-vacio">Aún no hay productos disponibles.</div>';
        return;
    }
    contenedor.innerHTML = activos.map(p => `
        <article class="tarjeta-producto">
            <div class="tarjeta-producto-imagen">
                <span class="iniciales">${escapar(iniciales(p.nombre))}</span>
            </div>
            <div class="tarjeta-producto-info">
                <span class="tarjeta-producto-categoria">${escapar((p.categoria && p.categoria.nombre) || 'Sin categoría')}</span>
                <h3 class="tarjeta-producto-nombre">${escapar(p.nombre)}</h3>
                <div class="tarjeta-producto-pie">
                    <p class="tarjeta-producto-precio">${formatoMoneda(p.precio)}</p>
                    <span class="tarjeta-producto-stock">Stock: ${escapar(p.stock)}</span>
                </div>
                <button class="boton-agregar-carrito" data-accion-catalogo="agregar"
                        data-id="${escapar(p.id)}" type="button"
                        ${p.stock <= 0 ? 'disabled' : ''}>
                    ${p.stock <= 0 ? 'Sin stock' : 'Agregar al carrito'}
                </button>
            </div>
        </article>
    `).join('');
}

function manejarAccionCatalogo(evento) {
    const boton = evento.target.closest('[data-accion-catalogo]');
    if (!boton) return;
    if (boton.dataset.accionCatalogo === 'agregar') {
        agregarAlCarrito(boton.dataset.id);
    }
}

// ============ CATEGORÍAS ============

async function cargarCategorias() {
    const tbody = document.getElementById('tabla-categorias-body');
    if (!tbody) return;
    tbody.innerHTML = '<tr><td colspan="4" class="celda-vacia">Cargando…</td></tr>';
    try {
        const datos = await peticionApi(API.categorias);
        renderizarCategorias(datos.datos || []);
    } catch (error) {
        tbody.innerHTML = `<tr><td colspan="4" class="celda-vacia">${escapar(error.message)}</td></tr>`;
    }
}

function renderizarCategorias(categorias) {
    const tbody = document.getElementById('tabla-categorias-body');
    if (categorias.length === 0) {
        tbody.innerHTML = '<tr><td colspan="4" class="celda-vacia">No hay categorías registradas.</td></tr>';
        return;
    }
    tbody.innerHTML = categorias.map(c => `
        <tr>
            <td>${escapar(c.nombre)}</td>
            <td>${escapar(c.descripcion) || '—'}</td>
            <td>${etiquetaEstado(c.estado)}</td>
            <td class="celda-acciones">
                <button class="boton-fila" data-accion="editar-categoria"
                        data-id="${escapar(c.id)}"
                        data-nombre="${escapar(c.nombre)}"
                        data-descripcion="${escapar(c.descripcion || '')}"
                        data-estado="${c.estado}">Editar</button>
                <button class="boton-fila boton-fila-peligro" data-accion="eliminar-categoria"
                        data-id="${escapar(c.id)}"
                        data-nombre="${escapar(c.nombre)}">Eliminar</button>
            </td>
        </tr>
    `).join('');
}

function entrarModoEdicionCategoria(datos) {
    estado.catEditandoId = datos.id;
    document.getElementById('cat-id').value = datos.id;
    document.getElementById('cat-nombre').value = datos.nombre;
    document.getElementById('cat-descripcion').value = datos.descripcion;
    document.getElementById('cat-estado').checked = datos.estado === 'true' || datos.estado === true;
    document.getElementById('cat-titulo-form').textContent = 'Editar categoría';
    document.getElementById('cat-boton-submit').textContent = 'Guardar cambios';
    document.getElementById('cat-boton-cancelar').classList.remove('oculto');
    document.getElementById('form-categoria').scrollIntoView({ behavior: 'smooth', block: 'center' });
}

function salirModoEdicionCategoria() {
    estado.catEditandoId = null;
    const form = document.getElementById('form-categoria');
    form.reset();
    document.getElementById('cat-id').value = '';
    document.getElementById('cat-estado').checked = true;
    document.getElementById('cat-titulo-form').textContent = 'Nueva categoría';
    document.getElementById('cat-boton-submit').textContent = 'Registrar categoría';
    document.getElementById('cat-boton-cancelar').classList.add('oculto');
}

async function guardarCategoria(evento) {
    evento.preventDefault();
    const form = evento.target;
    const boton = document.getElementById('cat-boton-submit');
    boton.disabled = true;

    const cuerpo = {
        nombre: form.nombre.value.trim(),
        descripcion: form.descripcion.value.trim(),
        estado: form.estado.checked
    };

    const enEdicion = !!estado.catEditandoId;
    const url = enEdicion ? `${API.categorias}/${estado.catEditandoId}` : API.categorias;
    const metodo = enEdicion ? 'PUT' : 'POST';

    try {
        const datos = await peticionApi(url, {
            method: metodo,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(cuerpo)
        });
        mostrarNotificacion(
            extraerMensaje(datos, enEdicion
                ? 'Categoría actualizada satisfactoriamente.'
                : 'Categoría registrada satisfactoriamente.'),
            'exito'
        );
        salirModoEdicionCategoria();
        await cargarCategorias();
        await cargarOpcionesCategoria();
    } catch (error) {
        mostrarNotificacion(error.message, 'error');
    } finally {
        boton.disabled = false;
    }
}

async function eliminarCategoria(id, nombre) {
    if (!window.confirm(`¿Eliminar la categoría "${nombre}"? Esta acción no se puede deshacer.`)) return;
    try {
        const datos = await peticionApi(`${API.categorias}/${id}`, { method: 'DELETE' });
        mostrarNotificacion(extraerMensaje(datos, 'Categoría eliminada satisfactoriamente.'), 'exito');
        if (estado.catEditandoId === id) salirModoEdicionCategoria();
        await cargarCategorias();
        await cargarOpcionesCategoria();
    } catch (error) {
        mostrarNotificacion(error.message, 'error');
    }
}

// ============ PRODUCTOS ============

async function cargarOpcionesCategoria() {
    const select = document.getElementById('pro-categoria');
    if (!select) return;
    const valorActual = select.value;
    try {
        const datos = await peticionApi(API.categorias);
        const lista = (datos.datos || []).filter(c => c.estado);
        select.innerHTML = '<option value="">Seleccione una categoría…</option>' +
            lista.map(c => `<option value="${escapar(c.id)}">${escapar(c.nombre)}</option>`).join('');
        if (valorActual) select.value = valorActual;
    } catch (error) {
        select.innerHTML = '<option value="">No se pudieron cargar las categorías</option>';
    }
}

async function cargarProductos() {
    const tbody = document.getElementById('tabla-productos-body');
    if (!tbody) return;
    tbody.innerHTML = '<tr><td colspan="6" class="celda-vacia">Cargando…</td></tr>';
    try {
        const datos = await peticionApi(API.productos);
        renderizarProductos(datos.datos || []);
    } catch (error) {
        tbody.innerHTML = `<tr><td colspan="6" class="celda-vacia">${escapar(error.message)}</td></tr>`;
    }
}

function renderizarProductos(productos) {
    const tbody = document.getElementById('tabla-productos-body');
    if (productos.length === 0) {
        tbody.innerHTML = '<tr><td colspan="6" class="celda-vacia">No hay productos registrados.</td></tr>';
        return;
    }
    tbody.innerHTML = productos.map(p => {
        const categoriaId = (p.categoria && p.categoria.id) || '';
        const categoriaNombre = (p.categoria && p.categoria.nombre) || '';
        return `
            <tr>
                <td>${escapar(p.nombre)}</td>
                <td>${escapar(categoriaNombre) || '—'}</td>
                <td class="celda-num">${formatoMoneda(p.precio)}</td>
                <td class="celda-num">${escapar(p.stock)}</td>
                <td>${etiquetaEstado(p.estado)}</td>
                <td class="celda-acciones">
                    <button class="boton-fila" data-accion="editar-producto"
                            data-id="${escapar(p.id)}"
                            data-categoria-id="${escapar(categoriaId)}"
                            data-nombre="${escapar(p.nombre)}"
                            data-descripcion="${escapar(p.descripcion || '')}"
                            data-precio="${escapar(p.precio)}"
                            data-stock="${escapar(p.stock)}"
                            data-estado="${p.estado}">Editar</button>
                    <button class="boton-fila boton-fila-peligro" data-accion="eliminar-producto"
                            data-id="${escapar(p.id)}"
                            data-nombre="${escapar(p.nombre)}">Eliminar</button>
                </td>
            </tr>
        `;
    }).join('');
}

function entrarModoEdicionProducto(datos) {
    estado.proEditandoId = datos.id;
    document.getElementById('pro-id').value = datos.id;
    document.getElementById('pro-categoria').value = datos.categoriaId;
    document.getElementById('pro-nombre').value = datos.nombre;
    document.getElementById('pro-descripcion').value = datos.descripcion;
    document.getElementById('pro-precio').value = datos.precio;
    document.getElementById('pro-stock').value = datos.stock;
    document.getElementById('pro-estado').checked = datos.estado === 'true' || datos.estado === true;
    document.getElementById('pro-titulo-form').textContent = 'Editar producto';
    document.getElementById('pro-boton-submit').textContent = 'Guardar cambios';
    document.getElementById('pro-boton-cancelar').classList.remove('oculto');
    document.getElementById('form-producto').scrollIntoView({ behavior: 'smooth', block: 'center' });
}

function salirModoEdicionProducto() {
    estado.proEditandoId = null;
    const form = document.getElementById('form-producto');
    form.reset();
    document.getElementById('pro-id').value = '';
    document.getElementById('pro-estado').checked = true;
    document.getElementById('pro-titulo-form').textContent = 'Nuevo producto';
    document.getElementById('pro-boton-submit').textContent = 'Registrar producto';
    document.getElementById('pro-boton-cancelar').classList.add('oculto');
}

async function guardarProducto(evento) {
    evento.preventDefault();
    const form = evento.target;
    const boton = document.getElementById('pro-boton-submit');
    boton.disabled = true;

    const cuerpo = {
        categoria: { id: form.categoriaId.value },
        nombre: form.nombre.value.trim(),
        descripcion: form.descripcion.value.trim(),
        precio: parseFloat(form.precio.value),
        stock: parseInt(form.stock.value, 10),
        estado: form.estado.checked
    };

    const enEdicion = !!estado.proEditandoId;
    const url = enEdicion ? `${API.productos}/${estado.proEditandoId}` : API.productos;
    const metodo = enEdicion ? 'PUT' : 'POST';

    try {
        const datos = await peticionApi(url, {
            method: metodo,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(cuerpo)
        });
        mostrarNotificacion(
            extraerMensaje(datos, enEdicion
                ? 'Producto actualizado satisfactoriamente.'
                : 'Producto registrado satisfactoriamente.'),
            'exito'
        );
        salirModoEdicionProducto();
        await cargarProductos();
    } catch (error) {
        mostrarNotificacion(error.message, 'error');
    } finally {
        boton.disabled = false;
    }
}

async function eliminarProducto(id, nombre) {
    if (!window.confirm(`¿Eliminar el producto "${nombre}"? Esta acción no se puede deshacer.`)) return;
    try {
        const datos = await peticionApi(`${API.productos}/${id}`, { method: 'DELETE' });
        mostrarNotificacion(extraerMensaje(datos, 'Producto eliminado satisfactoriamente.'), 'exito');
        if (estado.proEditandoId === id) salirModoEdicionProducto();
        await cargarProductos();
    } catch (error) {
        mostrarNotificacion(error.message, 'error');
    }
}

// ============ Manejador delegado de acciones en filas admin ============

function manejarAccionFila(evento) {
    const boton = evento.target.closest('[data-accion]');
    if (!boton) return;
    const accion = boton.dataset.accion;

    if (accion === 'editar-categoria') {
        entrarModoEdicionCategoria({
            id: boton.dataset.id,
            nombre: boton.dataset.nombre,
            descripcion: boton.dataset.descripcion,
            estado: boton.dataset.estado
        });
    } else if (accion === 'eliminar-categoria') {
        eliminarCategoria(boton.dataset.id, boton.dataset.nombre);
    } else if (accion === 'editar-producto') {
        entrarModoEdicionProducto({
            id: boton.dataset.id,
            categoriaId: boton.dataset.categoriaId,
            nombre: boton.dataset.nombre,
            descripcion: boton.dataset.descripcion,
            precio: boton.dataset.precio,
            stock: boton.dataset.stock,
            estado: boton.dataset.estado
        });
    } else if (accion === 'eliminar-producto') {
        eliminarProducto(boton.dataset.id, boton.dataset.nombre);
    }
}

// ============ Inicialización ============

document.addEventListener('DOMContentLoaded', () => {
    configurarNavegacion();

    // Autenticación
    document.getElementById('form-login-cliente').addEventListener('submit', loginCliente);
    document.getElementById('form-login-administrador').addEventListener('submit', loginAdministrador);
    document.getElementById('form-registro-cliente').addEventListener('submit', registrarCliente);
    document.getElementById('btn-cerrar-sesion').addEventListener('click', cerrarSesion);

    // Categorías
    document.getElementById('form-categoria').addEventListener('submit', guardarCategoria);
    document.getElementById('cat-boton-cancelar').addEventListener('click', salirModoEdicionCategoria);
    document.getElementById('btn-recargar-categorias').addEventListener('click', cargarCategorias);
    document.getElementById('tabla-categorias-body').addEventListener('click', manejarAccionFila);

    // Productos
    document.getElementById('form-producto').addEventListener('submit', guardarProducto);
    document.getElementById('pro-boton-cancelar').addEventListener('click', salirModoEdicionProducto);
    document.getElementById('btn-recargar-productos').addEventListener('click', () => {
        cargarProductos();
        cargarOpcionesCategoria();
    });
    document.getElementById('tabla-productos-body').addEventListener('click', manejarAccionFila);

    // Catálogo y carrito
    document.getElementById('grilla-catalogo').addEventListener('click', manejarAccionCatalogo);
    document.getElementById('tabla-carrito-body').addEventListener('click', manejarAccionCarrito);
    document.getElementById('btn-vaciar-carrito').addEventListener('click', vaciarCarrito);
    document.getElementById('btn-finalizar-compra').addEventListener('click', finalizarCompra);

    // Estado inicial: vista bienvenida, sin sesión
    actualizarHeader();
    cambiarVista('bienvenida');
});
