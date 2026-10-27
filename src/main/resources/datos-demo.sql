SET NOCOUNT ON;

IF NOT EXISTS (SELECT 1 FROM dbo.imex_administrador WHERE nombre_usuario = N'admin')
BEGIN
    INSERT INTO dbo.imex_administrador (id, nombre_usuario, correo_electronico, contrasena, estado)
    VALUES ('A1B2C3D4-5566-4F77-8899-AABBCCDDEE01', N'admin', N'admin@imexcol.com', N'DemoAdmin123', 1);
END;

IF NOT EXISTS (SELECT 1 FROM dbo.imex_metodo_pago WHERE id = 'AB000000-1111-4222-8333-AAAAAA000001')
BEGIN
    INSERT INTO dbo.imex_metodo_pago (id, nombre, descripcion, estado)
    VALUES ('AB000000-1111-4222-8333-AAAAAA000001', N'Tarjeta de crédito', N'Visa, Mastercard, American Express.', 1);
END;

IF NOT EXISTS (SELECT 1 FROM dbo.imex_metodo_pago WHERE id = 'AB000000-1111-4222-8333-AAAAAA000003')
BEGIN
    INSERT INTO dbo.imex_metodo_pago (id, nombre, descripcion, estado)
    VALUES ('AB000000-1111-4222-8333-AAAAAA000003', N'Nequi', N'Billetera digital Bancolombia.', 1);
END;

IF NOT EXISTS (SELECT 1 FROM dbo.imex_metodo_pago WHERE id = 'AB000000-1111-4222-8333-AAAAAA000005')
BEGIN
    INSERT INTO dbo.imex_metodo_pago (id, nombre, descripcion, estado)
    VALUES ('AB000000-1111-4222-8333-AAAAAA000005', N'Daviplata', N'Billetera digital Davivienda.', 0);
END;

IF NOT EXISTS (SELECT 1 FROM dbo.imex_categoria WHERE id = 'CA111111-1111-4111-8111-111111111101')
BEGIN
    INSERT INTO dbo.imex_categoria (id, nombre, descripcion, estado)
    VALUES ('CA111111-1111-4111-8111-111111111101', N'Hogar', N'Productos decorativos y utilitarios para el hogar.', 1);
END;

IF NOT EXISTS (SELECT 1 FROM dbo.imex_categoria WHERE id = 'CA111111-1111-4111-8111-111111111102')
BEGIN
    INSERT INTO dbo.imex_categoria (id, nombre, descripcion, estado)
    VALUES ('CA111111-1111-4111-8111-111111111102', N'Moda', N'Prendas de vestir y accesorios de moda.', 1);
END;

IF NOT EXISTS (SELECT 1 FROM dbo.imex_categoria WHERE id = 'CA111111-1111-4111-8111-111111111103')
BEGIN
    INSERT INTO dbo.imex_categoria (id, nombre, descripcion, estado)
    VALUES ('CA111111-1111-4111-8111-111111111103', N'Accesorios', N'Accesorios personales y complementos.', 1);
END;

IF NOT EXISTS (SELECT 1 FROM dbo.imex_categoria WHERE id = 'CA111111-1111-4111-8111-111111111104')
BEGIN
    INSERT INTO dbo.imex_categoria (id, nombre, descripcion, estado)
    VALUES ('CA111111-1111-4111-8111-111111111104', N'Tecnología', N'Dispositivos y accesorios tecnológicos.', 1);
END;

IF NOT EXISTS (SELECT 1 FROM dbo.imex_categoria WHERE id = 'CA111111-1111-4111-8111-111111111105')
BEGIN
    INSERT INTO dbo.imex_categoria (id, nombre, descripcion, estado)
    VALUES ('CA111111-1111-4111-8111-111111111105', N'Cocina', N'Utensilios y menaje de cocina.', 1);
END;

IF NOT EXISTS (SELECT 1 FROM dbo.imex_producto WHERE id = '4F8A0001-2222-4333-8444-555566667701')
BEGIN
    INSERT INTO dbo.imex_producto (id, categoria_id, nombre, descripcion, precio, stock, imagen_url, estado)
    VALUES ('4F8A0001-2222-4333-8444-555566667701', 'CA111111-1111-4111-8111-111111111101',
            N'Lámpara decorativa NP5115', N'Lámpara de mesa decorativa con base metálica y pantalla en tela.',
            89900, 25, N'img/Lampara_NP5115.png', 1);
END;

IF NOT EXISTS (SELECT 1 FROM dbo.imex_producto WHERE id = '4F8A0001-2222-4333-8444-555566667702')
BEGIN
    INSERT INTO dbo.imex_producto (id, categoria_id, nombre, descripcion, precio, stock, imagen_url, estado)
    VALUES ('4F8A0001-2222-4333-8444-555566667702', 'CA111111-1111-4111-8111-111111111103',
            N'Sombrilla antiviento NP4292', N'Sombrilla plegable antiviento con forro reforzado y mango ergonómico.',
            145000, 40, N'img/Sombrilla_antiviento_NP4292.png', 1);
END;

IF NOT EXISTS (SELECT 1 FROM dbo.imex_producto WHERE id = '4F8A0001-2222-4333-8444-555566667703')
BEGIN
    INSERT INTO dbo.imex_producto (id, categoria_id, nombre, descripcion, precio, stock, imagen_url, estado)
    VALUES ('4F8A0001-2222-4333-8444-555566667703', 'CA111111-1111-4111-8111-111111111102',
            N'Gorra para hombre NP5275', N'Gorra deportiva para hombre con visera curva y ajuste trasero.',
            35500, 60, N'img/Gorra_hombre_NP5275.png', 1);
END;

IF NOT EXISTS (SELECT 1 FROM dbo.imex_producto WHERE id = '4F8A0001-2222-4333-8444-555566667704')
BEGIN
    INSERT INTO dbo.imex_producto (id, categoria_id, nombre, descripcion, precio, stock, imagen_url, estado)
    VALUES ('4F8A0001-2222-4333-8444-555566667704', 'CA111111-1111-4111-8111-111111111105',
            N'Set de cucharas inoxidables x 12', N'Set de 12 cucharas en acero inoxidable con diseño labrado.',
            32000, 0, N'img/Set_Cucharas_NP712.png', 1);
END;

IF NOT EXISTS (SELECT 1 FROM dbo.imex_producto WHERE id = '4F8A0001-2222-4333-8444-555566667705')
BEGIN
    INSERT INTO dbo.imex_producto (id, categoria_id, nombre, descripcion, precio, stock, imagen_url, estado)
    VALUES ('4F8A0001-2222-4333-8444-555566667705', 'CA111111-1111-4111-8111-111111111104',
            N'Audífonos inalámbricos', N'Audífonos bluetooth con cancelación de ruido y estuche de carga.',
            189900, 15, N'img/Audifonos_inalam_NP0314.png', 1);
END;

IF NOT EXISTS (SELECT 1 FROM dbo.imex_producto WHERE id = '4F8A0001-2222-4333-8444-555566667706')
BEGIN
    INSERT INTO dbo.imex_producto (id, categoria_id, nombre, descripcion, precio, stock, imagen_url, estado)
    VALUES ('4F8A0001-2222-4333-8444-555566667706', 'CA111111-1111-4111-8111-111111111104',
            N'Parlante profesional REF 113', N'Parlante de alta potencia para eventos. Su precio supera el tope de la pasarela.',
            1250000, 3, N'img/Parlante_REF_113.jpeg', 1);
END;

IF NOT EXISTS (SELECT 1 FROM dbo.imex_cliente WHERE correo_electronico = N'carlos.restrepo@imexcol.com')
BEGIN
    INSERT INTO dbo.imex_cliente (id, nombre, apellido, correo_electronico, contrasena, telefono, estado)
    VALUES ('3F2504E0-4F89-11D3-9A0C-0305E82C3301', N'Carlos', N'Restrepo', N'carlos.restrepo@imexcol.com',
            N'DemoCliente123', N'3001112233', 1);
END;

IF NOT EXISTS (SELECT 1 FROM dbo.imex_direccion WHERE id = 'D1234567-8901-4234-5678-90123456D001')
    AND EXISTS (SELECT 1 FROM dbo.imex_cliente WHERE id = '3F2504E0-4F89-11D3-9A0C-0305E82C3301')
BEGIN
    INSERT INTO dbo.imex_direccion (id, cliente_id, calle, ciudad, departamento, codigo_postal, pais, es_principal)
    VALUES ('D1234567-8901-4234-5678-90123456D001', '3F2504E0-4F89-11D3-9A0C-0305E82C3301',
            N'Calle 10 # 43-20', N'Medellín', N'Antioquia', N'050021', N'Colombia', 1);
END;
