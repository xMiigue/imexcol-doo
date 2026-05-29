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
    registroCliente: '/api/v1/auth/cliente/registro',
    pedidos: '/api/v1/pedidos',
    lineasPedido: '/api/v1/lineas-pedido',
    pagos: '/api/v1/pagos',
    metodosPago: '/api/v1/metodos-pago',
    direcciones: '/api/v1/direcciones',
    clientes: '/api/v1/clientes',
    envios: '/api/v1/envios'
};

const ESTADOS_PEDIDO = ['PENDIENTE', 'CONFIRMADO', 'PREPARANDO', 'ENVIADO', 'ENTREGADO', 'CANCELADO'];
const ESTADOS_PAGO = ['PENDIENTE', 'EN_PROCESO', 'APROBADO', 'RECHAZADO', 'REEMBOLSADO'];
const ESTADOS_ENVIO = ['PREPARANDO', 'EN_TRANSITO', 'ENTREGADO', 'DEVUELTO', 'EXTRAVIADO'];

// Estado global en memoria (no se persiste)
const estado = {
    usuario: null,        // { tipo: 'cliente' | 'administrador', datos: {...} }
    carrito: [],          // [ { producto, cantidad } ]
    catEditandoId: null,
    proEditandoId: null,
    dirEditandoId: null,
    adminInicializado: false,
    clienteInicializado: false,
    direccionesCliente: [],
    metodosPago: [],
    direccionSeleccionada: null,
    pedidosCliente: []
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

function fechaHoyIso() {
    const hoy = new Date();
    const yyyy = hoy.getFullYear();
    const mm = String(hoy.getMonth() + 1).padStart(2, '0');
    const dd = String(hoy.getDate()).padStart(2, '0');
    return `${yyyy}-${mm}-${dd}`;
}

function formatoFecha(valor) {
    if (!valor) return '—';
    const partes = String(valor).split('-');
    if (partes.length < 3) return valor;
    return `${partes[2]}/${partes[1]}/${partes[0]}`;
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

// ============ Carrusel hero ============

function iniciarCarruselHero() {
    const slides = document.querySelectorAll('.hero-slide');
    if (slides.length < 2) return;
    if (iniciarCarruselHero._timer) return; // ya está corriendo
    let indice = 0;
    iniciarCarruselHero._timer = setInterval(() => {
        slides[indice].classList.remove('activa');
        indice = (indice + 1) % slides.length;
        slides[indice].classList.add('activa');
    }, 8000);
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

    // Carga bajo demanda al entrar a ciertas vistas
    if (vista === 'mis-pedidos') {
        cargarMisPedidos();
    } else if (vista === 'mis-direcciones') {
        cargarMisDirecciones();
    } else if (vista === 'admin-pedidos') {
        cargarPedidosAdmin();
    } else if (vista === 'admin-clientes') {
        cargarClientesAdmin();
    } else if (vista === 'admin-pagos') {
        cargarPagosAdmin();
    } else if (vista === 'admin-envios') {
        cargarEnviosAdmin();
    }
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
    estado.direccionesCliente = [];
    estado.adminInicializado = false;
    estado.clienteInicializado = false;
    // Salir de modos edición que pudieran quedar abiertos
    if (estado.dirEditandoId && document.getElementById('form-mi-direccion')) {
        salirModoEdicionDireccion();
    }
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
        const mensajeBackend = (error.message || '').toLowerCase();
        if (mensajeBackend.includes('correo') || mensajeBackend.includes('existe')) {
            mostrarNotificacion(
                'El correo electrónico ya está registrado. Por favor use otro.',
                'error'
            );
        } else {
            mostrarNotificacion(error.message, 'error');
        }
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

function irACheckout() {
    if (estado.carrito.length === 0) {
        mostrarNotificacion('Tu carrito está vacío.', 'error');
        return;
    }
    cambiarVista('checkout');
    prepararCheckout();
}

function calcularTotalCarrito() {
    return estado.carrito.reduce((s, i) => s + (i.producto.precio * i.cantidad), 0);
}

function renderizarResumenCheckout() {
    const cont = document.getElementById('checkout-resumen');
    const totalNodo = document.getElementById('checkout-total-valor');
    if (!cont || !totalNodo) return;

    if (estado.carrito.length === 0) {
        cont.innerHTML = '<p class="celda-vacia">Sin productos.</p>';
        totalNodo.textContent = formatoMoneda(0);
        return;
    }

    cont.innerHTML = estado.carrito.map(item => {
        const subtotal = item.producto.precio * item.cantidad;
        return `
            <div class="checkout-resumen-linea">
                <div class="checkout-resumen-linea-nombre">
                    ${escapar(item.producto.nombre)}
                    <small>${formatoMoneda(item.producto.precio)} c/u</small>
                </div>
                <div class="checkout-resumen-linea-cantidad">x ${item.cantidad}</div>
                <div class="checkout-resumen-linea-subtotal">${formatoMoneda(subtotal)}</div>
            </div>
        `;
    }).join('');

    totalNodo.textContent = formatoMoneda(calcularTotalCarrito());
}

async function prepararCheckout() {
    renderizarResumenCheckout();
    estado.direccionSeleccionada = null;
    document.getElementById('form-nueva-direccion').classList.add('oculto');
    await Promise.all([cargarDireccionesCliente(), cargarMetodosPago()]);
}

async function cargarDireccionesCliente() {
    const cont = document.getElementById('checkout-direcciones-existentes');
    if (!cont || !estado.usuario) return;
    cont.innerHTML = '<p class="celda-vacia">Cargando…</p>';
    try {
        const datos = await peticionApi(`${API.direcciones}?idCliente=${estado.usuario.datos.id}`);
        estado.direccionesCliente = datos.datos || [];
        renderizarDireccionesCheckout();
    } catch (error) {
        cont.innerHTML = `<p class="celda-vacia">${escapar(error.message)}</p>`;
    }
}

function renderizarDireccionesCheckout() {
    const cont = document.getElementById('checkout-direcciones-existentes');
    if (!cont) return;

    if (estado.direccionesCliente.length === 0) {
        cont.innerHTML = '<p class="celda-vacia">No tienes direcciones registradas. Agrega una nueva abajo.</p>';
        document.getElementById('form-nueva-direccion').classList.remove('oculto');
        return;
    }

    const principal = estado.direccionesCliente.find(d => d.esPrincipal) || estado.direccionesCliente[0];
    estado.direccionSeleccionada = principal.id;

    cont.innerHTML = estado.direccionesCliente.map(d => `
        <label class="checkout-opcion">
            <input type="radio" name="direccion-envio" value="${escapar(d.id)}"
                   ${d.id === principal.id ? 'checked' : ''}>
            <span class="checkout-opcion-texto">
                <strong>${escapar(d.calle)}${d.esPrincipal ? ' · Principal' : ''}</strong>
                <span>${escapar(d.ciudad)}, ${escapar(d.departamento)} — ${escapar(d.pais)}</span>
            </span>
        </label>
    `).join('');

    cont.querySelectorAll('input[name="direccion-envio"]').forEach(radio => {
        radio.addEventListener('change', () => {
            estado.direccionSeleccionada = radio.value;
        });
    });
}

async function cargarMetodosPago() {
    const select = document.getElementById('checkout-metodo-pago');
    if (!select) return;
    select.innerHTML = '<option value="">Cargando…</option>';
    try {
        const datos = await peticionApi(API.metodosPago);
        estado.metodosPago = (datos.datos || []).filter(m => m.estado);
        if (estado.metodosPago.length === 0) {
            select.innerHTML = '<option value="">No hay métodos de pago disponibles</option>';
            return;
        }
        select.innerHTML = '<option value="">Seleccione un método…</option>' +
            estado.metodosPago.map(m =>
                `<option value="${escapar(m.id)}">${escapar(m.nombre)}</option>`
            ).join('');
    } catch (error) {
        select.innerHTML = `<option value="">${escapar(error.message)}</option>`;
    }
}

async function confirmarCompra() {
    if (estado.carrito.length === 0) {
        mostrarNotificacion('Tu carrito está vacío.', 'error');
        return;
    }
    if (!estado.usuario || estado.usuario.tipo !== 'cliente') {
        mostrarNotificacion('Debes iniciar sesión como cliente.', 'error');
        return;
    }

    const idMetodoPago = document.getElementById('checkout-metodo-pago').value;
    if (!idMetodoPago) {
        mostrarNotificacion('Selecciona un método de pago.', 'error');
        return;
    }

    const formNueva = document.getElementById('form-nueva-direccion');
    const nuevaCalle = document.getElementById('dir-calle').value.trim();
    const usaNueva = !formNueva.classList.contains('oculto') && nuevaCalle.length > 0;

    if (!usaNueva && !estado.direccionSeleccionada) {
        mostrarNotificacion('Selecciona o agrega una dirección de envío.', 'error');
        return;
    }

    const boton = document.getElementById('btn-confirmar-compra');
    boton.disabled = true;
    boton.textContent = 'Procesando…';

    try {
        if (usaNueva) {
            await crearDireccionNueva();
        }

        const total = calcularTotalCarrito();
        const clienteId = estado.usuario.datos.id;
        const fecha = fechaHoyIso();

        // Identificar pedido nuevo: capturar IDs existentes antes
        const antes = await peticionApi(`${API.pedidos}?idCliente=${clienteId}`);
        const idsAntes = new Set((antes.datos || []).map(p => p.id));

        await peticionApi(API.pedidos, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                cliente: { id: clienteId },
                fechaPedido: fecha,
                total: total,
                estado: 'PENDIENTE'
            })
        });

        const despues = await peticionApi(`${API.pedidos}?idCliente=${clienteId}`);
        const pedidoNuevo = (despues.datos || []).find(p => !idsAntes.has(p.id));
        if (!pedidoNuevo) {
            throw new Error('No fue posible identificar el pedido creado.');
        }

        // Crear líneas de pedido
        for (const item of estado.carrito) {
            const cantidad = item.cantidad;
            const precio = item.producto.precio;
            const subtotal = +(cantidad * precio).toFixed(2);
            await peticionApi(API.lineasPedido, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    pedido: { id: pedidoNuevo.id },
                    producto: { id: item.producto.id },
                    cantidad: cantidad,
                    precioUnitario: precio,
                    subtotal: subtotal
                })
            });
        }

        // Crear pago
        await peticionApi(API.pagos, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                pedido: { id: pedidoNuevo.id },
                metodoPago: { id: idMetodoPago },
                monto: total,
                fechaPago: fecha,
                estado: 'PENDIENTE'
            })
        });

        mostrarNotificacion(`Compra confirmada por ${formatoMoneda(total)}.`, 'exito');
        estado.carrito = [];
        actualizarBadgeCarrito();
        renderizarCarrito();
        formNueva.classList.add('oculto');
        document.getElementById('form-nueva-direccion').querySelectorAll('input').forEach(i => {
            if (i.type === 'checkbox') i.checked = false;
            else if (i.id === 'dir-pais') i.value = 'Colombia';
            else i.value = '';
        });
        cambiarVista('mis-pedidos');
        await cargarMisPedidos();
    } catch (error) {
        mostrarNotificacion(error.message || 'No fue posible procesar la compra.', 'error');
    } finally {
        boton.disabled = false;
        boton.textContent = 'Confirmar compra';
    }
}

async function crearDireccionNueva() {
    const calle = document.getElementById('dir-calle').value.trim();
    const ciudad = document.getElementById('dir-ciudad').value.trim();
    const departamento = document.getElementById('dir-departamento').value.trim();
    const pais = document.getElementById('dir-pais').value.trim();
    const codigoPostal = document.getElementById('dir-codigo-postal').value.trim();
    const esPrincipal = document.getElementById('dir-es-principal').checked;

    if (!calle || !ciudad || !departamento || !pais) {
        throw new Error('Completa calle, ciudad, departamento y país de la dirección.');
    }

    await peticionApi(API.direcciones, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            cliente: { id: estado.usuario.datos.id },
            calle, ciudad, departamento, pais, codigoPostal,
            esPrincipal
        })
    });
}

// ============ MIS PEDIDOS (cliente) ============

async function cargarMisPedidos() {
    const tbody = document.getElementById('tabla-mis-pedidos-body');
    if (!tbody || !estado.usuario) return;
    tbody.innerHTML = '<tr><td colspan="5" class="celda-vacia">Cargando…</td></tr>';
    try {
        const datos = await peticionApi(`${API.pedidos}?idCliente=${estado.usuario.datos.id}`);
        estado.pedidosCliente = datos.datos || [];
        renderizarMisPedidos();
    } catch (error) {
        tbody.innerHTML = `<tr><td colspan="5" class="celda-vacia">${escapar(error.message)}</td></tr>`;
    }
    // ocultar detalle previo
    document.getElementById('panel-detalle-pedido').classList.add('oculto');
}

function renderizarMisPedidos() {
    const tbody = document.getElementById('tabla-mis-pedidos-body');
    if (estado.pedidosCliente.length === 0) {
        tbody.innerHTML = '<tr><td colspan="5" class="celda-vacia">Aún no has realizado pedidos.</td></tr>';
        return;
    }
    const ordenados = estado.pedidosCliente.slice().sort((a, b) =>
        String(b.fechaPedido).localeCompare(String(a.fechaPedido))
    );
    tbody.innerHTML = ordenados.map(p => `
        <tr>
            <td>${escapar(idCortoPedido(p.id))}</td>
            <td>${escapar(formatoFecha(p.fechaPedido))}</td>
            <td class="celda-num">${formatoMoneda(p.total)}</td>
            <td>${etiquetaPedidoEstado(p.estado)}</td>
            <td class="celda-acciones">
                <button class="boton-fila" data-accion-pedido="ver-detalle"
                        data-id="${escapar(p.id)}"
                        data-fecha="${escapar(p.fechaPedido)}">Ver detalle</button>
            </td>
        </tr>
    `).join('');
}

function etiquetaPedidoEstado(estadoTexto) {
    const t = String(estadoTexto || '').toUpperCase();
    const clasesPorEstado = {
        'PENDIENTE': 'etiqueta-pendiente',
        'CONFIRMADO': 'etiqueta-confirmado',
        'PREPARANDO': 'etiqueta-preparando',
        'ENVIADO': 'etiqueta-enviado',
        'ENTREGADO': 'etiqueta-entregado',
        'CANCELADO': 'etiqueta-cancelado'
    };
    const clase = clasesPorEstado[t] || 'etiqueta-inactiva';
    return `<span class="etiqueta ${clase}">${escapar(t || '—')}</span>`;
}

function idCortoPedido(id) {
    if (!id) return '—';
    return '#' + String(id).substring(0, 8).toUpperCase();
}

async function cargarMapaProductos() {
    const datos = await peticionApi(API.productos);
    const mapa = new Map();
    for (const p of (datos.datos || [])) {
        if (p && p.id) mapa.set(String(p.id), p.nombre || '—');
    }
    return mapa;
}

function resolverNombreProducto(linea, mapaProductos) {
    const directo = linea.producto && linea.producto.nombre;
    if (directo) return directo;
    const id = linea.producto && linea.producto.id;
    if (id && mapaProductos && mapaProductos.has(String(id))) {
        return mapaProductos.get(String(id));
    }
    return '—';
}

async function verDetallePedido(idPedido, fecha) {
    const panel = document.getElementById('panel-detalle-pedido');
    const tbody = document.getElementById('tabla-detalle-pedido-body');
    const titulo = document.getElementById('detalle-pedido-titulo');
    panel.classList.remove('oculto');
    titulo.textContent = `Detalle del pedido — ${formatoFecha(fecha)}`;
    tbody.innerHTML = '<tr><td colspan="4" class="celda-vacia">Cargando…</td></tr>';
    try {
        const [lineasResp, mapaProductos] = await Promise.all([
            peticionApi(`${API.lineasPedido}?idPedido=${idPedido}`),
            cargarMapaProductos()
        ]);
        const lineas = lineasResp.datos || [];
        if (lineas.length === 0) {
            tbody.innerHTML = '<tr><td colspan="4" class="celda-vacia">Sin líneas registradas.</td></tr>';
            return;
        }
        tbody.innerHTML = lineas.map(l => `
            <tr>
                <td>${escapar(resolverNombreProducto(l, mapaProductos))}</td>
                <td class="celda-num">${escapar(l.cantidad)}</td>
                <td class="celda-num">${formatoMoneda(l.precioUnitario)}</td>
                <td class="celda-num">${formatoMoneda(l.subtotal)}</td>
            </tr>
        `).join('');
        panel.scrollIntoView({ behavior: 'smooth', block: 'start' });
    } catch (error) {
        tbody.innerHTML = `<tr><td colspan="4" class="celda-vacia">${escapar(error.message)}</td></tr>`;
    }
}

function manejarAccionPedido(evento) {
    const boton = evento.target.closest('[data-accion-pedido]');
    if (!boton) return;
    if (boton.dataset.accionPedido === 'ver-detalle') {
        verDetallePedido(boton.dataset.id, boton.dataset.fecha);
    }
}

// ============ MIS DIRECCIONES (cliente) ============

async function cargarMisDirecciones() {
    const tbody = document.getElementById('tabla-mis-direcciones-body');
    if (!tbody || !estado.usuario || estado.usuario.tipo !== 'cliente') return;
    tbody.innerHTML = '<tr><td colspan="6" class="celda-vacia">Cargando…</td></tr>';
    try {
        const datos = await peticionApi(`${API.direcciones}?idCliente=${estado.usuario.datos.id}`);
        estado.direccionesCliente = datos.datos || [];
        renderizarMisDirecciones();
    } catch (error) {
        tbody.innerHTML = `<tr><td colspan="6" class="celda-vacia">${escapar(error.message)}</td></tr>`;
    }
}

function renderizarMisDirecciones() {
    const tbody = document.getElementById('tabla-mis-direcciones-body');
    if (!tbody) return;
    if (estado.direccionesCliente.length === 0) {
        tbody.innerHTML = '<tr><td colspan="6" class="celda-vacia">No tienes direcciones registradas.</td></tr>';
        return;
    }
    tbody.innerHTML = estado.direccionesCliente.map(d => `
        <tr>
            <td>${escapar(d.calle)}</td>
            <td>${escapar(d.ciudad)}</td>
            <td>${escapar(d.departamento)}</td>
            <td>${escapar(d.pais)}</td>
            <td>${d.esPrincipal
                ? '<span class="etiqueta etiqueta-activa">Principal</span>'
                : '<span class="etiqueta etiqueta-inactiva">—</span>'}</td>
            <td class="celda-acciones">
                <button class="boton-fila" data-accion="editar-direccion"
                        data-id="${escapar(d.id)}"
                        data-calle="${escapar(d.calle)}"
                        data-ciudad="${escapar(d.ciudad)}"
                        data-departamento="${escapar(d.departamento)}"
                        data-pais="${escapar(d.pais)}"
                        data-codigo-postal="${escapar(d.codigoPostal || '')}"
                        data-es-principal="${d.esPrincipal}">Editar</button>
                <button class="boton-fila boton-fila-peligro" data-accion="eliminar-direccion"
                        data-id="${escapar(d.id)}"
                        data-etiqueta="${escapar(d.calle)}">Eliminar</button>
            </td>
        </tr>
    `).join('');
}

function entrarModoEdicionDireccion(datos) {
    estado.dirEditandoId = datos.id;
    document.getElementById('midir-id').value = datos.id;
    document.getElementById('midir-calle').value = datos.calle;
    document.getElementById('midir-ciudad').value = datos.ciudad;
    document.getElementById('midir-departamento').value = datos.departamento;
    document.getElementById('midir-pais').value = datos.pais;
    document.getElementById('midir-codigo-postal').value = datos.codigoPostal || '';
    document.getElementById('midir-es-principal').checked =
        datos.esPrincipal === 'true' || datos.esPrincipal === true;
    document.getElementById('midir-titulo-form').textContent = 'Editar dirección';
    document.getElementById('midir-boton-submit').textContent = 'Guardar cambios';
    document.getElementById('midir-boton-cancelar').classList.remove('oculto');
    document.getElementById('form-mi-direccion').scrollIntoView({ behavior: 'smooth', block: 'center' });
}

function salirModoEdicionDireccion() {
    estado.dirEditandoId = null;
    const form = document.getElementById('form-mi-direccion');
    form.reset();
    document.getElementById('midir-id').value = '';
    document.getElementById('midir-pais').value = 'Colombia';
    document.getElementById('midir-es-principal').checked = false;
    document.getElementById('midir-titulo-form').textContent = 'Nueva dirección';
    document.getElementById('midir-boton-submit').textContent = 'Registrar dirección';
    document.getElementById('midir-boton-cancelar').classList.add('oculto');
}

async function guardarDireccion(evento) {
    evento.preventDefault();
    if (!estado.usuario || estado.usuario.tipo !== 'cliente') return;
    const form = evento.target;
    const boton = document.getElementById('midir-boton-submit');
    boton.disabled = true;

    const cuerpo = {
        cliente: { id: estado.usuario.datos.id },
        calle: form.calle.value.trim(),
        ciudad: form.ciudad.value.trim(),
        departamento: form.departamento.value.trim(),
        pais: form.pais.value.trim(),
        codigoPostal: form.codigoPostal.value.trim(),
        esPrincipal: form.esPrincipal.checked
    };

    const enEdicion = !!estado.dirEditandoId;
    const url = enEdicion ? `${API.direcciones}/${estado.dirEditandoId}` : API.direcciones;
    const metodo = enEdicion ? 'PUT' : 'POST';

    try {
        const datos = await peticionApi(url, {
            method: metodo,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(cuerpo)
        });
        mostrarNotificacion(
            extraerMensaje(datos, enEdicion
                ? 'Dirección actualizada satisfactoriamente.'
                : 'Dirección registrada satisfactoriamente.'),
            'exito'
        );
        salirModoEdicionDireccion();
        await cargarMisDirecciones();
    } catch (error) {
        mostrarNotificacion(error.message, 'error');
    } finally {
        boton.disabled = false;
    }
}

async function eliminarDireccionPropia(id, etiqueta) {
    if (!window.confirm(`¿Eliminar la dirección "${etiqueta}"? Esta acción no se puede deshacer.`)) return;
    try {
        const datos = await peticionApi(`${API.direcciones}/${id}`, { method: 'DELETE' });
        mostrarNotificacion(extraerMensaje(datos, 'Dirección eliminada satisfactoriamente.'), 'exito');
        if (estado.dirEditandoId === id) salirModoEdicionDireccion();
        await cargarMisDirecciones();
    } catch (error) {
        mostrarNotificacion(error.message, 'error');
    }
}

// ============ ADMIN: PEDIDOS ============

async function cargarMapaClientes() {
    const datos = await peticionApi(API.clientes);
    const mapa = new Map();
    for (const c of (datos.datos || [])) {
        if (c && c.id) {
            const nombre = `${c.nombre || ''} ${c.apellido || ''}`.trim();
            mapa.set(String(c.id), nombre || '—');
        }
    }
    return mapa;
}

async function cargarPedidosAdmin() {
    const tbody = document.getElementById('tabla-admin-pedidos-body');
    if (!tbody) return;
    tbody.innerHTML = '<tr><td colspan="6" class="celda-vacia">Cargando…</td></tr>';
    try {
        const [pedidosResp, mapaClientes] = await Promise.all([
            peticionApi(API.pedidos),
            cargarMapaClientes().catch(() => new Map())
        ]);
        renderizarPedidosAdmin(pedidosResp.datos || [], mapaClientes);
    } catch (error) {
        tbody.innerHTML = `<tr><td colspan="6" class="celda-vacia">${escapar(error.message)}</td></tr>`;
    }
}

function renderizarPedidosAdmin(pedidos, mapaClientes) {
    const tbody = document.getElementById('tabla-admin-pedidos-body');
    if (pedidos.length === 0) {
        tbody.innerHTML = '<tr><td colspan="6" class="celda-vacia">No hay pedidos registrados.</td></tr>';
        return;
    }
    const ordenados = pedidos.slice().sort((a, b) =>
        String(b.fechaPedido).localeCompare(String(a.fechaPedido))
    );
    tbody.innerHTML = ordenados.map(p => {
        const cliente = p.cliente || {};
        let nombre = `${cliente.nombre || ''} ${cliente.apellido || ''}`.trim();
        if (!nombre && cliente.id && mapaClientes && mapaClientes.has(String(cliente.id))) {
            nombre = mapaClientes.get(String(cliente.id));
        }
        if (!nombre) nombre = '—';
        const opciones = ESTADOS_PEDIDO.map(e =>
            `<option value="${e}" ${e === (p.estado || '').toUpperCase() ? 'selected' : ''}>${e}</option>`
        ).join('');
        return `
            <tr>
                <td>${escapar(idCortoPedido(p.id))}</td>
                <td>${escapar(formatoFecha(p.fechaPedido))}</td>
                <td>${escapar(nombre)}</td>
                <td class="celda-num">${formatoMoneda(p.total)}</td>
                <td>
                    <select class="estado-selector" data-accion-admin-pedido="cambiar-estado"
                            data-id="${escapar(p.id)}"
                            data-cliente-id="${escapar(cliente.id || '')}"
                            data-fecha="${escapar(p.fechaPedido)}"
                            data-total="${escapar(p.total)}">
                        ${opciones}
                    </select>
                </td>
                <td class="celda-acciones">
                    <button class="boton-fila" data-accion-admin-pedido="ver-lineas"
                            data-id="${escapar(p.id)}"
                            data-fecha="${escapar(p.fechaPedido)}">Ver líneas</button>
                </td>
            </tr>
        `;
    }).join('');
}

async function cambiarEstadoPedido(selector) {
    const id = selector.dataset.id;
    const nuevoEstado = selector.value;
    const cuerpo = {
        cliente: { id: selector.dataset.clienteId },
        fechaPedido: selector.dataset.fecha,
        total: parseFloat(selector.dataset.total),
        estado: nuevoEstado
    };
    selector.disabled = true;
    try {
        const datos = await peticionApi(`${API.pedidos}/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(cuerpo)
        });
        mostrarNotificacion(
            extraerMensaje(datos, `Pedido actualizado a ${nuevoEstado}.`),
            'exito'
        );
    } catch (error) {
        mostrarNotificacion(error.message, 'error');
        await cargarPedidosAdmin();
    } finally {
        selector.disabled = false;
    }
}

function manejarAccionAdminPedido(evento) {
    const selector = evento.target.closest('select[data-accion-admin-pedido="cambiar-estado"]');
    if (selector && evento.type === 'change') {
        cambiarEstadoPedido(selector);
        return;
    }
    const boton = evento.target.closest('button[data-accion-admin-pedido="ver-lineas"]');
    if (boton && evento.type === 'click') {
        verLineasPedidoAdmin(boton.dataset.id, boton.dataset.fecha);
    }
}

async function verLineasPedidoAdmin(idPedido, fecha) {
    try {
        const [lineasResp, mapaProductos] = await Promise.all([
            peticionApi(`${API.lineasPedido}?idPedido=${idPedido}`),
            cargarMapaProductos()
        ]);
        const lineas = lineasResp.datos || [];
        if (lineas.length === 0) {
            mostrarNotificacion('Este pedido no tiene líneas registradas.', 'error');
            return;
        }
        const resumen = lineas.map(l =>
            `• ${resolverNombreProducto(l, mapaProductos)} — ${l.cantidad} × ${formatoMoneda(l.precioUnitario)} = ${formatoMoneda(l.subtotal)}`
        ).join('\n');
        window.alert(`Pedido ${formatoFecha(fecha)}\n\n${resumen}`);
    } catch (error) {
        mostrarNotificacion(error.message, 'error');
    }
}

// ============ ADMIN: CLIENTES ============

async function cargarClientesAdmin() {
    const tbody = document.getElementById('tabla-admin-clientes-body');
    if (!tbody) return;
    tbody.innerHTML = '<tr><td colspan="5" class="celda-vacia">Cargando…</td></tr>';
    try {
        const datos = await peticionApi(API.clientes);
        renderizarClientesAdmin(datos.datos || []);
    } catch (error) {
        tbody.innerHTML = `<tr><td colspan="5" class="celda-vacia">${escapar(error.message)}</td></tr>`;
    }
}

function renderizarClientesAdmin(clientes) {
    const tbody = document.getElementById('tabla-admin-clientes-body');
    if (clientes.length === 0) {
        tbody.innerHTML = '<tr><td colspan="5" class="celda-vacia">No hay clientes registrados.</td></tr>';
        return;
    }
    tbody.innerHTML = clientes.map(c => `
        <tr>
            <td>${escapar(c.nombre)}</td>
            <td>${escapar(c.apellido)}</td>
            <td>${escapar(c.correoElectronico)}</td>
            <td>${escapar(c.telefono) || '—'}</td>
            <td>${etiquetaEstado(c.estado)}</td>
        </tr>
    `).join('');
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

async function cargarMapaCategorias() {
    const datos = await peticionApi(API.categorias);
    const mapa = new Map();
    for (const c of (datos.datos || [])) {
        if (c && c.id) mapa.set(String(c.id), c.nombre || '—');
    }
    return mapa;
}

function resolverNombreCategoria(producto, mapaCategorias) {
    const directo = producto.categoria && producto.categoria.nombre;
    if (directo) return directo;
    const id = producto.categoria && producto.categoria.id;
    if (id && mapaCategorias && mapaCategorias.has(String(id))) {
        return mapaCategorias.get(String(id));
    }
    return '';
}

async function cargarCatalogo() {
    const contenedor = document.getElementById('grilla-catalogo');
    if (!contenedor) return;
    contenedor.innerHTML = '<div class="catalogo-vacio">Cargando catálogo…</div>';
    try {
        const [productosResp, mapaCategorias] = await Promise.all([
            peticionApi(API.productos),
            cargarMapaCategorias().catch(() => new Map())
        ]);
        estado.catalogo = productosResp.datos || [];
        estado.mapaCategorias = mapaCategorias;
        renderizarFiltroCategoria(mapaCategorias);
        renderizarCarruselDestacados(estado.catalogo, mapaCategorias);
        renderizarCatalogo(estado.catalogo, mapaCategorias);
    } catch (error) {
        contenedor.innerHTML = `<div class="catalogo-vacio">${escapar(error.message)}</div>`;
    }
}

function renderizarFiltroCategoria(mapa) {
    const select = document.getElementById('catalogo-filtro-categoria');
    if (!select) return;
    const valorActual = select.value;
    const opciones = Array.from((mapa || new Map()).entries())
        .map(([id, nombre]) => `<option value="${escapar(id)}">${escapar(nombre)}</option>`)
        .join('');
    select.innerHTML = '<option value="">Todas las categorías</option>' + opciones;
    if (valorActual) select.value = valorActual;
}

function aplicarFiltrosCatalogo() {
    const inputBuscar = document.getElementById('catalogo-buscar');
    const selectCat = document.getElementById('catalogo-filtro-categoria');
    const buscar = (inputBuscar ? inputBuscar.value : '').trim().toLowerCase();
    const catId = selectCat ? selectCat.value : '';

    let lista = estado.catalogo || [];
    if (catId) {
        lista = lista.filter(p => p.categoria && String(p.categoria.id) === String(catId));
    }
    if (buscar) {
        lista = lista.filter(p => (p.nombre || '').toLowerCase().includes(buscar));
    }
    renderizarCatalogo(lista, estado.mapaCategorias);
}

// ============ Carrusel "slot machine" de productos destacados ============

function renderizarCarruselDestacados(productos, mapaCategorias) {
    const pista = document.getElementById('carrusel-pista');
    if (!pista) return;
    const activos = (productos || []).filter(p => p.estado);
    if (activos.length === 0) {
        pista.innerHTML = '';
        return;
    }

    const tarjeta = (p) => {
        const categoria = resolverNombreCategoria(p, mapaCategorias) || 'Sin categoría';
        const imagenUrl = p.imagenUrl && String(p.imagenUrl).trim();
        const contenidoImagen = imagenUrl
            ? `<img src="${escapar(imagenUrl)}" alt="${escapar(p.nombre)}"
                    onerror="this.outerHTML='&lt;span class=&quot;iniciales&quot;&gt;${escapar(iniciales(p.nombre))}&lt;/span&gt;'">`
            : `<span class="iniciales">${escapar(iniciales(p.nombre))}</span>`;
        return `
            <article class="carrusel-tarjeta" data-id="${escapar(p.id)}">
                <div class="carrusel-tarjeta-imagen">${contenidoImagen}</div>
                <div class="carrusel-tarjeta-info">
                    <span class="carrusel-tarjeta-categoria">${escapar(categoria)}</span>
                    <h3 class="carrusel-tarjeta-nombre">${escapar(p.nombre)}</h3>
                    <p class="carrusel-tarjeta-precio">${formatoMoneda(p.precio)}</p>
                </div>
            </article>
        `;
    };

    // Duplicamos 4× para que la animación de 0% → -50% sea continua e indistinguible.
    const segmento = activos.map(tarjeta).join('');
    pista.innerHTML = segmento + segmento + segmento + segmento;

    // Re-disparar la animación
    pista.classList.remove('animando');
    void pista.offsetWidth; // force reflow
    pista.classList.add('animando');
}

function manejarClickCarrusel(evento) {
    const tarjeta = evento.target.closest('.carrusel-tarjeta');
    if (!tarjeta) return;
    const id = tarjeta.dataset.id;
    if (!id) return;
    const objetivo = document.querySelector(`#grilla-catalogo .tarjeta-producto[data-id="${id}"]`);
    if (objetivo) {
        objetivo.scrollIntoView({ behavior: 'smooth', block: 'center' });
        objetivo.classList.remove('resaltado');
        void objetivo.offsetWidth;
        objetivo.classList.add('resaltado');
        setTimeout(() => objetivo.classList.remove('resaltado'), 1600);
    }
}

function renderizarCatalogo(productos, mapaCategorias) {
    const contenedor = document.getElementById('grilla-catalogo');
    if (!contenedor) return;
    const activos = productos.filter(p => p.estado);
    if (activos.length === 0) {
        contenedor.innerHTML = '<div class="catalogo-vacio">Aún no hay productos disponibles.</div>';
        return;
    }
    contenedor.innerHTML = activos.map(p => {
        const categoria = resolverNombreCategoria(p, mapaCategorias) || 'Sin categoría';
        const imagenUrl = p.imagenUrl && String(p.imagenUrl).trim();
        const contenidoImagen = imagenUrl
            ? `<img src="${escapar(imagenUrl)}" alt="${escapar(p.nombre)}" class="producto-foto"
                    onerror="this.outerHTML='&lt;span class=&quot;iniciales&quot;&gt;${escapar(iniciales(p.nombre))}&lt;/span&gt;'">`
            : `<span class="iniciales">${escapar(iniciales(p.nombre))}</span>`;
        const claseFoto = imagenUrl ? ' con-foto' : '';
        return `
        <article class="tarjeta-producto" data-id="${escapar(p.id)}">
            <div class="tarjeta-producto-imagen${claseFoto}">
                ${contenidoImagen}
            </div>
            <div class="tarjeta-producto-info">
                <span class="tarjeta-producto-categoria">${escapar(categoria)}</span>
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
        `;
    }).join('');
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
        const [productosResp, mapaCategorias] = await Promise.all([
            peticionApi(API.productos),
            cargarMapaCategorias().catch(() => new Map())
        ]);
        renderizarProductos(productosResp.datos || [], mapaCategorias);
    } catch (error) {
        tbody.innerHTML = `<tr><td colspan="6" class="celda-vacia">${escapar(error.message)}</td></tr>`;
    }
}

function renderizarProductos(productos, mapaCategorias) {
    const tbody = document.getElementById('tabla-productos-body');
    if (productos.length === 0) {
        tbody.innerHTML = '<tr><td colspan="6" class="celda-vacia">No hay productos registrados.</td></tr>';
        return;
    }
    tbody.innerHTML = productos.map(p => {
        const categoriaId = (p.categoria && p.categoria.id) || '';
        const categoriaNombre = resolverNombreCategoria(p, mapaCategorias);
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
                            data-imagen-url="${escapar(p.imagenUrl || '')}"
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
    document.getElementById('pro-imagen-url').value = datos.imagenUrl || '';
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
    document.getElementById('pro-imagen-url').value = '';
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
        imagenUrl: form.imagenUrl.value.trim(),
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

// ============ ADMIN: PAGOS ============

function esFechaPorDefecto(valor) {
    if (!valor) return true;
    const v = String(valor);
    return v === '0001-01-01' || v === 'null' || v === 'undefined';
}

async function cargarPagosAdmin() {
    const tbody = document.getElementById('tabla-admin-pagos-body');
    if (!tbody) return;
    tbody.innerHTML = '<tr><td colspan="5" class="celda-vacia">Cargando…</td></tr>';
    try {
        const [pagosResp, metodosResp, pedidosResp] = await Promise.all([
            peticionApi(API.pagos),
            peticionApi(API.metodosPago),
            peticionApi(API.pedidos).catch(() => ({ datos: [] }))
        ]);
        estado.metodosPago = metodosResp.datos || [];
        const mapaMetodos = new Map();
        for (const m of estado.metodosPago) {
            if (m && m.id) mapaMetodos.set(String(m.id), m.nombre || '—');
        }
        const mapaPedidos = new Map();
        for (const p of (pedidosResp.datos || [])) {
            if (p && p.id) mapaPedidos.set(String(p.id), p.fechaPedido);
        }
        renderizarPagosAdmin(pagosResp.datos || [], mapaMetodos, mapaPedidos);
    } catch (error) {
        tbody.innerHTML = `<tr><td colspan="5" class="celda-vacia">${escapar(error.message)}</td></tr>`;
    }
}

function renderizarPagosAdmin(pagos, mapaMetodos, mapaPedidos) {
    const tbody = document.getElementById('tabla-admin-pagos-body');
    if (pagos.length === 0) {
        tbody.innerHTML = '<tr><td colspan="5" class="celda-vacia">No hay pagos registrados.</td></tr>';
        return;
    }
    const ordenados = pagos.slice().sort((a, b) =>
        String(b.fechaPago).localeCompare(String(a.fechaPago))
    );
    tbody.innerHTML = ordenados.map(p => {
        // Método de pago: SIEMPRE buscar primero en el mapa por id
        let metodo = '';
        const metodoId = p.metodoPago && p.metodoPago.id;
        if (metodoId && mapaMetodos && mapaMetodos.has(String(metodoId))) {
            metodo = mapaMetodos.get(String(metodoId));
        }
        if (!metodo) metodo = '—';

        // Pedido: mostrar siempre el ID corto del pedido (primeros 8 chars del UUID)
        const pedidoId = p.pedido && p.pedido.id;
        const pedidoFecha = pedidoId
            ? '#' + String(pedidoId).substring(0, 8).toUpperCase()
            : '—';
        const opciones = ESTADOS_PAGO.map(e =>
            `<option value="${e}" ${e === (p.estado || '').toUpperCase() ? 'selected' : ''}>${e}</option>`
        ).join('');
        return `
            <tr>
                <td>${escapar(formatoFecha(p.fechaPago))}</td>
                <td class="celda-num">${formatoMoneda(p.monto)}</td>
                <td>${escapar(metodo)}</td>
                <td>${escapar(pedidoFecha)}</td>
                <td>
                    <select class="estado-selector" data-accion-admin-pago="cambiar-estado"
                            data-id="${escapar(p.id)}"
                            data-pedido-id="${escapar((p.pedido && p.pedido.id) || '')}"
                            data-metodo-id="${escapar((p.metodoPago && p.metodoPago.id) || '')}"
                            data-monto="${escapar(p.monto)}"
                            data-fecha="${escapar(p.fechaPago)}">
                        ${opciones}
                    </select>
                </td>
            </tr>
        `;
    }).join('');
}

async function cambiarEstadoPago(selector) {
    const id = selector.dataset.id;
    const nuevoEstado = selector.value;
    const cuerpo = {
        pedido: { id: selector.dataset.pedidoId },
        metodoPago: { id: selector.dataset.metodoId },
        monto: parseFloat(selector.dataset.monto),
        fechaPago: selector.dataset.fecha,
        estado: nuevoEstado
    };
    selector.disabled = true;
    try {
        const datos = await peticionApi(`${API.pagos}/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(cuerpo)
        });
        mostrarNotificacion(
            extraerMensaje(datos, `Pago actualizado a ${nuevoEstado}.`),
            'exito'
        );
    } catch (error) {
        mostrarNotificacion(error.message, 'error');
        await cargarPagosAdmin();
    } finally {
        selector.disabled = false;
    }
}

function manejarAccionAdminPago(evento) {
    const selector = evento.target.closest('select[data-accion-admin-pago="cambiar-estado"]');
    if (selector && evento.type === 'change') {
        cambiarEstadoPago(selector);
    }
}

// ============ ADMIN: ENVÍOS ============

async function cargarEnviosAdmin() {
    const tbody = document.getElementById('tabla-admin-envios-body');
    if (!tbody) return;
    tbody.innerHTML = '<tr><td colspan="6" class="celda-vacia">Cargando…</td></tr>';
    try {
        const datos = await peticionApi(API.envios);
        renderizarEnviosAdmin(datos.datos || []);
    } catch (error) {
        tbody.innerHTML = `<tr><td colspan="6" class="celda-vacia">${escapar(error.message)}</td></tr>`;
    }
}

function renderizarEnviosAdmin(envios) {
    const tbody = document.getElementById('tabla-admin-envios-body');
    if (envios.length === 0) {
        tbody.innerHTML = '<tr><td colspan="6" class="celda-vacia">No hay envíos registrados.</td></tr>';
        return;
    }
    const ordenados = envios.slice().sort((a, b) =>
        String(b.fechaEnvio).localeCompare(String(a.fechaEnvio))
    );
    tbody.innerHTML = ordenados.map(e => {
        const pedidoId = e.pedido && e.pedido.id;
        const pedidoEtiqueta = pedidoId
            ? '#' + String(pedidoId).substring(0, 8).toUpperCase()
            : '—';
        const opciones = ESTADOS_ENVIO.map(s =>
            `<option value="${s}" ${s === (e.estado || '').toUpperCase() ? 'selected' : ''}>${s}</option>`
        ).join('');
        return `
            <tr>
                <td>${escapar(pedidoEtiqueta)}</td>
                <td>${escapar(e.transportadora)}</td>
                <td>${escapar(e.numeroGuia)}</td>
                <td>${escapar(formatoFecha(e.fechaEnvio))}</td>
                <td>${escapar(formatoFecha(e.fechaEntrega))}</td>
                <td>
                    <select class="estado-selector" data-accion-admin-envio="cambiar-estado"
                            data-id="${escapar(e.id)}"
                            data-pedido-id="${escapar((e.pedido && e.pedido.id) || '')}"
                            data-fecha-envio="${escapar(e.fechaEnvio)}"
                            data-fecha-entrega="${escapar(e.fechaEntrega)}"
                            data-transportadora="${escapar(e.transportadora)}"
                            data-numero-guia="${escapar(e.numeroGuia)}">
                        ${opciones}
                    </select>
                </td>
            </tr>
        `;
    }).join('');
}

async function cambiarEstadoEnvio(selector) {
    const id = selector.dataset.id;
    const nuevoEstado = selector.value;
    const cuerpo = {
        pedido: { id: selector.dataset.pedidoId },
        fechaEnvio: selector.dataset.fechaEnvio,
        fechaEntrega: selector.dataset.fechaEntrega,
        transportadora: selector.dataset.transportadora,
        numeroGuia: selector.dataset.numeroGuia,
        estado: nuevoEstado
    };
    // Si el nuevo estado es ENTREGADO y no hay fecha entrega válida, fijarla a hoy
    if (nuevoEstado === 'ENTREGADO' &&
        (!cuerpo.fechaEntrega || cuerpo.fechaEntrega === '0001-01-01' || cuerpo.fechaEntrega === 'null')) {
        cuerpo.fechaEntrega = fechaHoyIso();
    }
    selector.disabled = true;
    try {
        const datos = await peticionApi(`${API.envios}/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(cuerpo)
        });
        mostrarNotificacion(
            extraerMensaje(datos, `Envío actualizado a ${nuevoEstado}.`),
            'exito'
        );
        await cargarEnviosAdmin();
    } catch (error) {
        mostrarNotificacion(error.message, 'error');
        await cargarEnviosAdmin();
    } finally {
        selector.disabled = false;
    }
}

function manejarAccionAdminEnvio(evento) {
    const selector = evento.target.closest('select[data-accion-admin-envio="cambiar-estado"]');
    if (selector && evento.type === 'change') {
        cambiarEstadoEnvio(selector);
    }
}

async function mostrarFormCrearEnvio() {
    const panel = document.getElementById('panel-form-envio');
    panel.classList.remove('oculto');

    // Set fecha por defecto = hoy
    document.getElementById('env-fecha-envio').value = fechaHoyIso();
    document.getElementById('env-transportadora').value = '';
    document.getElementById('env-numero-guia').value = '';

    // Cargar pedidos y clientes en paralelo para el selector
    const select = document.getElementById('env-pedido');
    select.innerHTML = '<option value="">Cargando pedidos…</option>';
    try {
        const [pedidosResp, mapaClientes] = await Promise.all([
            peticionApi(API.pedidos),
            cargarMapaClientes().catch(() => new Map())
        ]);
        const pedidos = pedidosResp.datos || [];
        if (pedidos.length === 0) {
            select.innerHTML = '<option value="">No hay pedidos disponibles</option>';
            return;
        }
        const ordenados = pedidos.slice().sort((a, b) =>
            String(b.fechaPedido).localeCompare(String(a.fechaPedido))
        );
        select.innerHTML = '<option value="">Seleccione un pedido…</option>' +
            ordenados.map(p => {
                const c = p.cliente || {};
                let nombreCliente = `${c.nombre || ''} ${c.apellido || ''}`.trim();
                if (!nombreCliente && c.id && mapaClientes.has(String(c.id))) {
                    nombreCliente = mapaClientes.get(String(c.id));
                }
                if (!nombreCliente) nombreCliente = '—';
                const idCorto = '#' + String(p.id).substring(0, 6).toUpperCase();
                const etiqueta = `${idCorto} — ${nombreCliente} — ${formatoMoneda(p.total)}`;
                return `<option value="${escapar(p.id)}">${escapar(etiqueta)}</option>`;
            }).join('');
    } catch (error) {
        select.innerHTML = `<option value="">${escapar(error.message)}</option>`;
    }

    panel.scrollIntoView({ behavior: 'smooth', block: 'start' });
}

function ocultarFormCrearEnvio() {
    document.getElementById('panel-form-envio').classList.add('oculto');
    document.getElementById('form-envio').reset();
}

async function crearEnvio(evento) {
    evento.preventDefault();
    const form = evento.target;
    const idPedido = form.pedidoId.value;
    if (!idPedido) {
        mostrarNotificacion('Selecciona un pedido.', 'error');
        return;
    }
    const cuerpo = {
        pedido: { id: idPedido },
        fechaEnvio: form.fechaEnvio.value,
        transportadora: form.transportadora.value.trim(),
        numeroGuia: form.numeroGuia.value.trim(),
        estado: 'PREPARANDO'
    };
    const boton = form.querySelector('button[type="submit"]');
    boton.disabled = true;
    try {
        const datos = await peticionApi(API.envios, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(cuerpo)
        });
        mostrarNotificacion(
            extraerMensaje(datos, 'Envío registrado satisfactoriamente.'),
            'exito'
        );
        ocultarFormCrearEnvio();
        await cargarEnviosAdmin();
    } catch (error) {
        mostrarNotificacion(error.message, 'error');
    } finally {
        boton.disabled = false;
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
            imagenUrl: boton.dataset.imagenUrl,
            estado: boton.dataset.estado
        });
    } else if (accion === 'eliminar-producto') {
        eliminarProducto(boton.dataset.id, boton.dataset.nombre);
    } else if (accion === 'editar-direccion') {
        entrarModoEdicionDireccion({
            id: boton.dataset.id,
            calle: boton.dataset.calle,
            ciudad: boton.dataset.ciudad,
            departamento: boton.dataset.departamento,
            pais: boton.dataset.pais,
            codigoPostal: boton.dataset.codigoPostal,
            esPrincipal: boton.dataset.esPrincipal
        });
    } else if (accion === 'eliminar-direccion') {
        eliminarDireccionPropia(boton.dataset.id, boton.dataset.etiqueta);
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

    // Filtros del catálogo
    const inputBuscar = document.getElementById('catalogo-buscar');
    if (inputBuscar) inputBuscar.addEventListener('input', aplicarFiltrosCatalogo);
    const selectFiltroCat = document.getElementById('catalogo-filtro-categoria');
    if (selectFiltroCat) selectFiltroCat.addEventListener('change', aplicarFiltrosCatalogo);

    // Carrusel destacados → scroll a producto del catálogo
    const carrusel = document.getElementById('carrusel-destacados');
    if (carrusel) carrusel.addEventListener('click', manejarClickCarrusel);

    // Catálogo y carrito
    document.getElementById('grilla-catalogo').addEventListener('click', manejarAccionCatalogo);
    document.getElementById('tabla-carrito-body').addEventListener('click', manejarAccionCarrito);
    document.getElementById('btn-vaciar-carrito').addEventListener('click', vaciarCarrito);
    document.getElementById('btn-ir-checkout').addEventListener('click', irACheckout);

    // Checkout
    document.getElementById('btn-nueva-direccion').addEventListener('click', () => {
        document.getElementById('form-nueva-direccion').classList.toggle('oculto');
    });
    document.getElementById('btn-cancelar-checkout').addEventListener('click', () => cambiarVista('carrito'));
    document.getElementById('btn-confirmar-compra').addEventListener('click', confirmarCompra);

    // Mis pedidos
    document.getElementById('tabla-mis-pedidos-body').addEventListener('click', manejarAccionPedido);
    document.getElementById('btn-recargar-mis-pedidos').addEventListener('click', cargarMisPedidos);
    document.getElementById('btn-cerrar-detalle').addEventListener('click', () => {
        document.getElementById('panel-detalle-pedido').classList.add('oculto');
    });

    // Mis direcciones
    document.getElementById('form-mi-direccion').addEventListener('submit', guardarDireccion);
    document.getElementById('midir-boton-cancelar').addEventListener('click', salirModoEdicionDireccion);
    document.getElementById('btn-recargar-mis-direcciones').addEventListener('click', cargarMisDirecciones);
    document.getElementById('tabla-mis-direcciones-body').addEventListener('click', manejarAccionFila);

    // Admin: pedidos
    document.getElementById('tabla-admin-pedidos-body').addEventListener('change', manejarAccionAdminPedido);
    document.getElementById('tabla-admin-pedidos-body').addEventListener('click', manejarAccionAdminPedido);
    document.getElementById('btn-recargar-admin-pedidos').addEventListener('click', cargarPedidosAdmin);

    // Admin: clientes
    document.getElementById('btn-recargar-admin-clientes').addEventListener('click', cargarClientesAdmin);

    // Admin: pagos
    document.getElementById('tabla-admin-pagos-body').addEventListener('change', manejarAccionAdminPago);
    document.getElementById('btn-recargar-admin-pagos').addEventListener('click', cargarPagosAdmin);

    // Admin: envíos
    document.getElementById('tabla-admin-envios-body').addEventListener('change', manejarAccionAdminEnvio);
    document.getElementById('btn-recargar-admin-envios').addEventListener('click', cargarEnviosAdmin);
    document.getElementById('btn-nuevo-envio').addEventListener('click', mostrarFormCrearEnvio);
    document.getElementById('btn-cancelar-envio').addEventListener('click', ocultarFormCrearEnvio);
    document.getElementById('form-envio').addEventListener('submit', crearEnvio);

    // Carrusel del hero (cliente)
    iniciarCarruselHero();

    // Estado inicial: vista bienvenida, sin sesión
    actualizarHeader();
    cambiarVista('bienvenida');
});
