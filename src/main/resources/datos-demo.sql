SET NOCOUNT ON;

DECLARE @categoria_hogar UNIQUEIDENTIFIER = 'CA111111-1111-4111-8111-111111111101';
DECLARE @categoria_moda UNIQUEIDENTIFIER = 'CA111111-1111-4111-8111-111111111102';
DECLARE @categoria_accesorios UNIQUEIDENTIFIER = 'CA111111-1111-4111-8111-111111111103';
DECLARE @categoria_tecnologia UNIQUEIDENTIFIER = 'CA111111-1111-4111-8111-111111111104';
DECLARE @categoria_cocina UNIQUEIDENTIFIER = 'CA111111-1111-4111-8111-111111111105';
DECLARE @cliente_demo UNIQUEIDENTIFIER = '3F2504E0-4F89-11D3-9A0C-0305E82C3301';

INSERT INTO dbo.imex_administrador (id, nombre_usuario, correo_electronico, contrasena, estado)
SELECT v.id, v.nombre_usuario, v.correo_electronico, v.contrasena, v.estado
FROM (VALUES
    (CAST('A1B2C3D4-5566-4F77-8899-AABBCCDDEE01' AS UNIQUEIDENTIFIER), N'admin', N'admin@imexcol.com',
     N'DemoAdmin123', 1)
) AS v (id, nombre_usuario, correo_electronico, contrasena, estado)
WHERE NOT EXISTS (SELECT 1 FROM dbo.imex_administrador a WHERE a.nombre_usuario = v.nombre_usuario);

INSERT INTO dbo.imex_metodo_pago (id, nombre, descripcion, estado)
SELECT v.id, v.nombre, v.descripcion, v.estado
FROM (VALUES
    (CAST('AB000000-1111-4222-8333-AAAAAA000001' AS UNIQUEIDENTIFIER), N'Tarjeta de crédito',
     N'Visa, Mastercard, American Express.', 1),
    (CAST('AB000000-1111-4222-8333-AAAAAA000003' AS UNIQUEIDENTIFIER), N'Nequi', N'Billetera digital Bancolombia.', 1),
    (CAST('AB000000-1111-4222-8333-AAAAAA000005' AS UNIQUEIDENTIFIER), N'Daviplata', N'Billetera digital Davivienda.', 0)
) AS v (id, nombre, descripcion, estado)
WHERE NOT EXISTS (SELECT 1 FROM dbo.imex_metodo_pago m WHERE m.id = v.id);

INSERT INTO dbo.imex_categoria (id, nombre, descripcion, estado)
SELECT v.id, v.nombre, v.descripcion, v.estado
FROM (VALUES
    (@categoria_hogar, N'Hogar', N'Productos decorativos y utilitarios para el hogar.', 1),
    (@categoria_moda, N'Moda', N'Prendas de vestir y accesorios de moda.', 1),
    (@categoria_accesorios, N'Accesorios', N'Accesorios personales y complementos.', 1),
    (@categoria_tecnologia, N'Tecnología', N'Dispositivos y accesorios tecnológicos.', 1),
    (@categoria_cocina, N'Cocina', N'Utensilios y menaje de cocina.', 1)
) AS v (id, nombre, descripcion, estado)
WHERE NOT EXISTS (SELECT 1 FROM dbo.imex_categoria c WHERE c.id = v.id);

INSERT INTO dbo.imex_producto (id, categoria_id, nombre, descripcion, precio, stock, imagen_url, estado)
SELECT v.id, v.categoria_id, v.nombre, v.descripcion, v.precio, v.stock, v.imagen_url, v.estado
FROM (VALUES
    (CAST('4F8A0001-2222-4333-8444-555566667701' AS UNIQUEIDENTIFIER), @categoria_hogar,
     N'Lámpara decorativa NP5115', N'Lámpara de mesa decorativa con base metálica y pantalla en tela.',
     89900, 25, N'img/Lampara_NP5115.png', 1),
    (CAST('4F8A0001-2222-4333-8444-555566667702' AS UNIQUEIDENTIFIER), @categoria_accesorios,
     N'Sombrilla antiviento NP4292', N'Sombrilla plegable antiviento con forro reforzado y mango ergonómico.',
     145000, 40, N'img/Sombrilla_antiviento_NP4292.png', 1),
    (CAST('4F8A0001-2222-4333-8444-555566667703' AS UNIQUEIDENTIFIER), @categoria_moda,
     N'Gorra para hombre NP5275', N'Gorra deportiva para hombre con visera curva y ajuste trasero.',
     35500, 60, N'img/Gorra_hombre_NP5275.png', 1),
    (CAST('4F8A0001-2222-4333-8444-555566667704' AS UNIQUEIDENTIFIER), @categoria_cocina,
     N'Set de cucharas inoxidables x 12', N'Set de 12 cucharas en acero inoxidable con diseño labrado.',
     32000, 0, N'img/Set_Cucharas_NP712.png', 1),
    (CAST('4F8A0001-2222-4333-8444-555566667705' AS UNIQUEIDENTIFIER), @categoria_tecnologia,
     N'Audífonos inalámbricos', N'Audífonos bluetooth con cancelación de ruido y estuche de carga.',
     189900, 15, N'img/Audifonos_inalam_NP0314.png', 1),
    (CAST('4F8A0001-2222-4333-8444-555566667706' AS UNIQUEIDENTIFIER), @categoria_tecnologia,
     N'Parlante profesional REF 113', N'Parlante de alta potencia para eventos. Su precio supera el tope de la pasarela.',
     1250000, 3, N'img/Parlante_REF_113.jpeg', 1)
) AS v (id, categoria_id, nombre, descripcion, precio, stock, imagen_url, estado)
WHERE NOT EXISTS (SELECT 1 FROM dbo.imex_producto p WHERE p.id = v.id);

INSERT INTO dbo.imex_cliente (id, nombre, apellido, correo_electronico, contrasena, telefono, estado)
SELECT v.id, v.nombre, v.apellido, v.correo_electronico, v.contrasena, v.telefono, v.estado
FROM (VALUES
    (@cliente_demo, N'Carlos', N'Restrepo', N'carlos.restrepo@imexcol.com', N'DemoCliente123', N'3001112233', 1)
) AS v (id, nombre, apellido, correo_electronico, contrasena, telefono, estado)
WHERE NOT EXISTS (SELECT 1 FROM dbo.imex_cliente c WHERE c.correo_electronico = v.correo_electronico);

INSERT INTO dbo.imex_direccion (id, cliente_id, calle, ciudad, departamento, codigo_postal, pais, es_principal)
SELECT v.id, v.cliente_id, v.calle, v.ciudad, v.departamento, v.codigo_postal, v.pais, v.es_principal
FROM (VALUES
    (CAST('D1234567-8901-4234-5678-90123456D001' AS UNIQUEIDENTIFIER), @cliente_demo,
     N'Calle 10 # 43-20', N'Medellín', N'Antioquia', N'050021', N'Colombia', 1)
) AS v (id, cliente_id, calle, ciudad, departamento, codigo_postal, pais, es_principal)
WHERE NOT EXISTS (SELECT 1 FROM dbo.imex_direccion d WHERE d.id = v.id)
    AND EXISTS (SELECT 1 FROM dbo.imex_cliente c WHERE c.id = v.cliente_id);
