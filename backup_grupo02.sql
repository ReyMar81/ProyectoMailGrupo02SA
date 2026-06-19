-- RAO MOTOS Backup | 2026-06-19T17:09:36.034759600

-- proveedor
INSERT INTO proveedor (id, razon_social, contacto_principal, telefono, activo) VALUES (1, 'Distribuidora Japonesa Ltda.', 'Tanaka Suzuki', '44123401', true) ON CONFLICT (id) DO NOTHING;
INSERT INTO proveedor (id, razon_social, contacto_principal, telefono, activo) VALUES (2, 'Importadora China del Sur', 'Li Wei', '44123402', true) ON CONFLICT (id) DO NOTHING;

-- usuario
INSERT INTO usuario (id, nombre, email, telefono, direccion, foto_url, password, rol, activo, fecha_reg) VALUES (1, 'Carlos Diego Marca Peñaranda', 'marcacarlosestudio@gmail.com', '77123401', 'Av. America 123, Cochabamba', NULL, 'admin123', 'PROPIETARIO', true, '2026-06-19 15:20:06.200509') ON CONFLICT (id) DO NOTHING;
INSERT INTO usuario (id, nombre, email, telefono, direccion, foto_url, password, rol, activo, fecha_reg) VALUES (2, 'Arnez Fernández Fabio Alejandro', 'fabioarnez200@gmail.com', '77123402', 'Calle Punata 456, Cochabamba', NULL, 'admin123', 'PROPIETARIO', true, '2026-06-19 15:20:06.200509') ON CONFLICT (id) DO NOTHING;
INSERT INTO usuario (id, nombre, email, telefono, direccion, foto_url, password, rol, activo, fecha_reg) VALUES (3, 'Reymar Loaiza Labarden', 'loaizalabardenreymar@gmail.com', '77123403', 'Av. Ayacucho 789, Cochabamba', NULL, 'admin123', 'PROPIETARIO', true, '2026-06-19 15:20:06.200509') ON CONFLICT (id) DO NOTHING;
INSERT INTO usuario (id, nombre, email, telefono, direccion, foto_url, password, rol, activo, fecha_reg) VALUES (6, 'Juan Perez Mamani', 'juan.perez@email.com', '72123401', 'Calle Lanza 100, Cochabamba', NULL, 'cli1234', 'CLIENTE', true, '2026-06-19 15:20:06.200509') ON CONFLICT (id) DO NOTHING;
INSERT INTO usuario (id, nombre, email, telefono, direccion, foto_url, password, rol, activo, fecha_reg) VALUES (7, 'Maria Flores Quispe', 'maria.flores@email.com', '72123402', 'Av. Heroinas 200, Cochabamba', NULL, 'cli1234', 'CLIENTE', true, '2026-06-19 15:20:06.200509') ON CONFLICT (id) DO NOTHING;
INSERT INTO usuario (id, nombre, email, telefono, direccion, foto_url, password, rol, activo, fecha_reg) VALUES (8, 'Pedro Gutierrez Soliz', 'pedro.gutierrez@email.com', '72123403', 'Calle Bolivar 300, Cochabamba', NULL, 'cli1234', 'CLIENTE', true, '2026-06-19 15:20:06.200509') ON CONFLICT (id) DO NOTHING;
INSERT INTO usuario (id, nombre, email, telefono, direccion, foto_url, password, rol, activo, fecha_reg) VALUES (9, 'Ana Rodriguez Lopez', 'ana.rodriguez@email.com', '72123404', 'Av. Oquendo 400, Cochabamba', NULL, 'cli1234', 'CLIENTE', true, '2026-06-19 15:20:06.200509') ON CONFLICT (id) DO NOTHING;
INSERT INTO usuario (id, nombre, email, telefono, direccion, foto_url, password, rol, activo, fecha_reg) VALUES (10, 'Luis Vargas Rojas', 'luis.vargas@email.com', '72123405', 'Calle Espana 500, Cochabamba', NULL, 'cli1234', 'CLIENTE', true, '2026-06-19 15:20:06.200509') ON CONFLICT (id) DO NOTHING;
INSERT INTO usuario (id, nombre, email, telefono, direccion, foto_url, password, rol, activo, fecha_reg) VALUES (11, 'Reymar prueba', 'reymarloaizalabarden@gmail.com', '75521217', 'uagrm', NULL, 'reymar123456', 'CLIENTE', true, '2026-06-19 16:11:07.27511') ON CONFLICT (id) DO NOTHING;

-- cliente
INSERT INTO cliente (id, nit_ci, tipo_cliente) VALUES (6, '12345601', 'REGULAR') ON CONFLICT (id) DO NOTHING;
INSERT INTO cliente (id, nit_ci, tipo_cliente) VALUES (7, '12345602', 'FRECUENTE') ON CONFLICT (id) DO NOTHING;
INSERT INTO cliente (id, nit_ci, tipo_cliente) VALUES (8, '12345603', 'MAYORISTA') ON CONFLICT (id) DO NOTHING;
INSERT INTO cliente (id, nit_ci, tipo_cliente) VALUES (9, '12345604', 'REGULAR') ON CONFLICT (id) DO NOTHING;
INSERT INTO cliente (id, nit_ci, tipo_cliente) VALUES (10, '12345605', 'FRECUENTE') ON CONFLICT (id) DO NOTHING;
INSERT INTO cliente (id, nit_ci, tipo_cliente) VALUES (11, '12345678', 'FRECUENTE') ON CONFLICT (id) DO NOTHING;

-- propietario
INSERT INTO propietario (id, nivel_acceso) VALUES (1, 'TOTAL') ON CONFLICT (id) DO NOTHING;
INSERT INTO propietario (id, nivel_acceso) VALUES (2, 'TOTAL') ON CONFLICT (id) DO NOTHING;
INSERT INTO propietario (id, nivel_acceso) VALUES (3, 'TOTAL') ON CONFLICT (id) DO NOTHING;

-- producto
INSERT INTO producto (id, codigo, nombre, marca, modelo, descripcion, precio_venta_base, foto_url, activo, fecha_reg) VALUES (1, 'CAD-001', 'Kit Transmision Completo', 'DID', '520VX3', 'Kit cadena 520 + pinon + corona 150cc', 350.00, NULL, true, '2026-06-19 15:20:06.200509') ON CONFLICT (id) DO NOTHING;
INSERT INTO producto (id, codigo, nombre, marca, modelo, descripcion, precio_venta_base, foto_url, activo, fecha_reg) VALUES (2, 'CAD-002', 'Cadena de Distribucion', 'Tsubaki', 'DID830', 'Cadena distribucion para motos 200cc', 180.00, NULL, true, '2026-06-19 15:20:06.200509') ON CONFLICT (id) DO NOTHING;
INSERT INTO producto (id, codigo, nombre, marca, modelo, descripcion, precio_venta_base, foto_url, activo, fecha_reg) VALUES (3, 'CAD-003', 'Pinon de Acero 15T', 'Sunstar', '15T-520', 'Pinon delantero 15 dientes cadena 520', 85.00, NULL, true, '2026-06-19 15:20:06.200509') ON CONFLICT (id) DO NOTHING;
INSERT INTO producto (id, codigo, nombre, marca, modelo, descripcion, precio_venta_base, foto_url, activo, fecha_reg) VALUES (4, 'FRE-001', 'Pastillas Freno Delantero', 'EBC', 'FA209', 'Pastillas sinterizadas para freno disco', 120.00, NULL, true, '2026-06-19 15:20:06.200509') ON CONFLICT (id) DO NOTHING;
INSERT INTO producto (id, codigo, nombre, marca, modelo, descripcion, precio_venta_base, foto_url, activo, fecha_reg) VALUES (5, 'FRE-002', 'Disco Freno Trasero', 'Brembo', 'DB203', 'Disco freno trasero 220mm', 250.00, NULL, true, '2026-06-19 15:20:06.200509') ON CONFLICT (id) DO NOTHING;
INSERT INTO producto (id, codigo, nombre, marca, modelo, descripcion, precio_venta_base, foto_url, activo, fecha_reg) VALUES (6, 'FRE-003', 'Cable de Freno Acero', 'Venhill', 'CB-15', 'Cable freno delantero con funda acero', 65.00, NULL, true, '2026-06-19 15:20:06.200509') ON CONFLICT (id) DO NOTHING;
INSERT INTO producto (id, codigo, nombre, marca, modelo, descripcion, precio_venta_base, foto_url, activo, fecha_reg) VALUES (7, 'BUJ-001', 'Bujia Iridium', 'NGK', 'CR8EIX', 'Bujia iridio para motos 125-250cc', 55.00, NULL, true, '2026-06-19 15:20:06.200509') ON CONFLICT (id) DO NOTHING;
INSERT INTO producto (id, codigo, nombre, marca, modelo, descripcion, precio_venta_base, foto_url, activo, fecha_reg) VALUES (8, 'BUJ-002', 'Bobina de Encendido', 'Denso', '129700', 'Bobina encendido universal 12V', 180.00, NULL, true, '2026-06-19 15:20:06.200509') ON CONFLICT (id) DO NOTHING;
INSERT INTO producto (id, codigo, nombre, marca, modelo, descripcion, precio_venta_base, foto_url, activo, fecha_reg) VALUES (9, 'BUJ-003', 'CDI Electronico', 'Mitsubishi', 'CDI-150', 'Modulo encendido CDI para motos 150cc', 220.00, NULL, true, '2026-06-19 15:20:06.200509') ON CONFLICT (id) DO NOTHING;
INSERT INTO producto (id, codigo, nombre, marca, modelo, descripcion, precio_venta_base, foto_url, activo, fecha_reg) VALUES (10, 'FIL-001', 'Filtro de Aceite', 'Hiflofiltro', 'HF-204', 'Filtro aceite para motos 125-250cc', 35.00, NULL, true, '2026-06-19 15:20:06.200509') ON CONFLICT (id) DO NOTHING;
INSERT INTO producto (id, codigo, nombre, marca, modelo, descripcion, precio_venta_base, foto_url, activo, fecha_reg) VALUES (11, 'FIL-002', 'Filtro Aire Deportivo', 'K&N', 'KA-1508', 'Filtro aire alto flujo', 160.00, NULL, true, '2026-06-19 15:20:06.200509') ON CONFLICT (id) DO NOTHING;
INSERT INTO producto (id, codigo, nombre, marca, modelo, descripcion, precio_venta_base, foto_url, activo, fecha_reg) VALUES (12, 'FIL-003', 'Filtro de Gasolina', 'Bosch', '045-123', 'Filtro combustible universal', 25.00, NULL, true, '2026-06-19 15:20:06.200509') ON CONFLICT (id) DO NOTHING;
INSERT INTO producto (id, codigo, nombre, marca, modelo, descripcion, precio_venta_base, foto_url, activo, fecha_reg) VALUES (13, 'ACC-001', 'Espejo Retrovisor Universal', 'TST', 'MR-01', 'Espejo retrovisor negro universal', 70.00, NULL, true, '2026-06-19 15:20:06.200509') ON CONFLICT (id) DO NOTHING;
INSERT INTO producto (id, codigo, nombre, marca, modelo, descripcion, precio_venta_base, foto_url, activo, fecha_reg) VALUES (14, 'ACC-002', 'Manillar Deportivo', 'Renthal', 'RC-971', 'Manillar aluminio 28mm', 200.00, NULL, true, '2026-06-19 15:20:06.200509') ON CONFLICT (id) DO NOTHING;
INSERT INTO producto (id, codigo, nombre, marca, modelo, descripcion, precio_venta_base, foto_url, activo, fecha_reg) VALUES (15, 'ACC-003', 'Cubre Carter Aluminio', 'Givi', 'GC-150', 'Cubre carter aluminio pulido', 310.00, NULL, true, '2026-06-19 15:20:06.200509') ON CONFLICT (id) DO NOTHING;

-- inventario
INSERT INTO inventario (id, producto_id, stock_actual, tecnica_inventario, tecnica_costo, fecha_actualizacion) VALUES (1, 1, 14, 'PERMANENTE', 'PROMEDIO', '2026-06-19 15:20:06.200509') ON CONFLICT (id) DO NOTHING;
INSERT INTO inventario (id, producto_id, stock_actual, tecnica_inventario, tecnica_costo, fecha_actualizacion) VALUES (2, 2, 11, 'PERMANENTE', 'PROMEDIO', '2026-06-19 15:20:06.200509') ON CONFLICT (id) DO NOTHING;
INSERT INTO inventario (id, producto_id, stock_actual, tecnica_inventario, tecnica_costo, fecha_actualizacion) VALUES (3, 3, 21, 'PERMANENTE', 'PROMEDIO', '2026-06-19 15:20:06.200509') ON CONFLICT (id) DO NOTHING;
INSERT INTO inventario (id, producto_id, stock_actual, tecnica_inventario, tecnica_costo, fecha_actualizacion) VALUES (4, 4, 8, 'PERMANENTE', 'PROMEDIO', '2026-06-19 15:20:06.200509') ON CONFLICT (id) DO NOTHING;
INSERT INTO inventario (id, producto_id, stock_actual, tecnica_inventario, tecnica_costo, fecha_actualizacion) VALUES (5, 5, 4, 'PERMANENTE', 'PROMEDIO', '2026-06-19 15:20:06.200509') ON CONFLICT (id) DO NOTHING;
INSERT INTO inventario (id, producto_id, stock_actual, tecnica_inventario, tecnica_costo, fecha_actualizacion) VALUES (6, 6, 24, 'PERMANENTE', 'PROMEDIO', '2026-06-19 15:20:06.200509') ON CONFLICT (id) DO NOTHING;
INSERT INTO inventario (id, producto_id, stock_actual, tecnica_inventario, tecnica_costo, fecha_actualizacion) VALUES (7, 7, 8, 'PERMANENTE', 'PROMEDIO', '2026-06-19 15:20:06.200509') ON CONFLICT (id) DO NOTHING;
INSERT INTO inventario (id, producto_id, stock_actual, tecnica_inventario, tecnica_costo, fecha_actualizacion) VALUES (8, 8, 24, 'PERMANENTE', 'PROMEDIO', '2026-06-19 15:20:06.200509') ON CONFLICT (id) DO NOTHING;
INSERT INTO inventario (id, producto_id, stock_actual, tecnica_inventario, tecnica_costo, fecha_actualizacion) VALUES (9, 9, 4, 'PERMANENTE', 'PROMEDIO', '2026-06-19 15:20:06.200509') ON CONFLICT (id) DO NOTHING;
INSERT INTO inventario (id, producto_id, stock_actual, tecnica_inventario, tecnica_costo, fecha_actualizacion) VALUES (10, 10, 94, 'PERMANENTE', 'PROMEDIO', '2026-06-19 15:20:06.200509') ON CONFLICT (id) DO NOTHING;
INSERT INTO inventario (id, producto_id, stock_actual, tecnica_inventario, tecnica_costo, fecha_actualizacion) VALUES (11, 11, 31, 'PERMANENTE', 'PROMEDIO', '2026-06-19 15:20:06.200509') ON CONFLICT (id) DO NOTHING;
INSERT INTO inventario (id, producto_id, stock_actual, tecnica_inventario, tecnica_costo, fecha_actualizacion) VALUES (12, 12, 208, 'PERMANENTE', 'PROMEDIO', '2026-06-19 15:20:06.200509') ON CONFLICT (id) DO NOTHING;
INSERT INTO inventario (id, producto_id, stock_actual, tecnica_inventario, tecnica_costo, fecha_actualizacion) VALUES (13, 13, 55, 'PERMANENTE', 'PROMEDIO', '2026-06-19 15:20:06.200509') ON CONFLICT (id) DO NOTHING;
INSERT INTO inventario (id, producto_id, stock_actual, tecnica_inventario, tecnica_costo, fecha_actualizacion) VALUES (14, 14, 3, 'PERMANENTE', 'PROMEDIO', '2026-06-19 15:20:06.200509') ON CONFLICT (id) DO NOTHING;
INSERT INTO inventario (id, producto_id, stock_actual, tecnica_inventario, tecnica_costo, fecha_actualizacion) VALUES (15, 15, 19, 'PERMANENTE', 'PROMEDIO', '2026-06-19 15:20:06.200509') ON CONFLICT (id) DO NOTHING;

-- movimiento_inventario
INSERT INTO movimiento_inventario (id, inventario_id, tipo_movimiento, cantidad, motivo, fecha) VALUES (1, 1, 'INGRESO', 10, 'Compra #1', '2026-01-10 09:00:00') ON CONFLICT (id) DO NOTHING;
INSERT INTO movimiento_inventario (id, inventario_id, tipo_movimiento, cantidad, motivo, fecha) VALUES (2, 2, 'INGRESO', 15, 'Compra #1', '2026-01-10 09:00:00') ON CONFLICT (id) DO NOTHING;
INSERT INTO movimiento_inventario (id, inventario_id, tipo_movimiento, cantidad, motivo, fecha) VALUES (3, 3, 'INGRESO', 25, 'Compra #1', '2026-01-10 09:00:00') ON CONFLICT (id) DO NOTHING;
INSERT INTO movimiento_inventario (id, inventario_id, tipo_movimiento, cantidad, motivo, fecha) VALUES (4, 4, 'INGRESO', 20, 'Compra #1', '2026-01-10 09:00:00') ON CONFLICT (id) DO NOTHING;
INSERT INTO movimiento_inventario (id, inventario_id, tipo_movimiento, cantidad, motivo, fecha) VALUES (5, 5, 'INGRESO', 12, 'Compra #1', '2026-01-10 09:00:00') ON CONFLICT (id) DO NOTHING;
INSERT INTO movimiento_inventario (id, inventario_id, tipo_movimiento, cantidad, motivo, fecha) VALUES (6, 6, 'INGRESO', 30, 'Compra #1', '2026-01-10 09:00:00') ON CONFLICT (id) DO NOTHING;
INSERT INTO movimiento_inventario (id, inventario_id, tipo_movimiento, cantidad, motivo, fecha) VALUES (7, 7, 'INGRESO', 35, 'Compra #1', '2026-01-10 09:00:00') ON CONFLICT (id) DO NOTHING;
INSERT INTO movimiento_inventario (id, inventario_id, tipo_movimiento, cantidad, motivo, fecha) VALUES (8, 8, 'INGRESO', 8, 'Compra #2', '2026-03-15 09:00:00') ON CONFLICT (id) DO NOTHING;
INSERT INTO movimiento_inventario (id, inventario_id, tipo_movimiento, cantidad, motivo, fecha) VALUES (9, 9, 'INGRESO', 10, 'Compra #2', '2026-03-15 09:00:00') ON CONFLICT (id) DO NOTHING;
INSERT INTO movimiento_inventario (id, inventario_id, tipo_movimiento, cantidad, motivo, fecha) VALUES (10, 10, 'INGRESO', 50, 'Compra #2', '2026-03-15 09:00:00') ON CONFLICT (id) DO NOTHING;
INSERT INTO movimiento_inventario (id, inventario_id, tipo_movimiento, cantidad, motivo, fecha) VALUES (11, 11, 'INGRESO', 12, 'Compra #2', '2026-03-15 09:00:00') ON CONFLICT (id) DO NOTHING;
INSERT INTO movimiento_inventario (id, inventario_id, tipo_movimiento, cantidad, motivo, fecha) VALUES (12, 12, 'INGRESO', 60, 'Compra #2', '2026-03-15 09:00:00') ON CONFLICT (id) DO NOTHING;
INSERT INTO movimiento_inventario (id, inventario_id, tipo_movimiento, cantidad, motivo, fecha) VALUES (13, 13, 'INGRESO', 20, 'Compra #2', '2026-03-15 09:00:00') ON CONFLICT (id) DO NOTHING;
INSERT INTO movimiento_inventario (id, inventario_id, tipo_movimiento, cantidad, motivo, fecha) VALUES (14, 14, 'INGRESO', 10, 'Compra #2', '2026-03-15 09:00:00') ON CONFLICT (id) DO NOTHING;
INSERT INTO movimiento_inventario (id, inventario_id, tipo_movimiento, cantidad, motivo, fecha) VALUES (15, 15, 'INGRESO', 8, 'Compra #2', '2026-03-15 09:00:00') ON CONFLICT (id) DO NOTHING;
INSERT INTO movimiento_inventario (id, inventario_id, tipo_movimiento, cantidad, motivo, fecha) VALUES (16, 8, 'INGRESO', 20, 'Compra #3', '2026-03-25 09:00:00') ON CONFLICT (id) DO NOTHING;
INSERT INTO movimiento_inventario (id, inventario_id, tipo_movimiento, cantidad, motivo, fecha) VALUES (17, 9, 'INGRESO', 15, 'Compra #3', '2026-03-25 09:00:00') ON CONFLICT (id) DO NOTHING;
INSERT INTO movimiento_inventario (id, inventario_id, tipo_movimiento, cantidad, motivo, fecha) VALUES (18, 11, 'INGRESO', 20, 'Compra #3', '2026-03-25 09:00:00') ON CONFLICT (id) DO NOTHING;
INSERT INTO movimiento_inventario (id, inventario_id, tipo_movimiento, cantidad, motivo, fecha) VALUES (19, 10, 'INGRESO', 100, 'Compra #4', '2026-05-10 09:00:00') ON CONFLICT (id) DO NOTHING;
INSERT INTO movimiento_inventario (id, inventario_id, tipo_movimiento, cantidad, motivo, fecha) VALUES (20, 12, 'INGRESO', 150, 'Compra #4', '2026-05-10 09:00:00') ON CONFLICT (id) DO NOTHING;
INSERT INTO movimiento_inventario (id, inventario_id, tipo_movimiento, cantidad, motivo, fecha) VALUES (21, 13, 'INGRESO', 40, 'Compra #4', '2026-05-10 09:00:00') ON CONFLICT (id) DO NOTHING;
INSERT INTO movimiento_inventario (id, inventario_id, tipo_movimiento, cantidad, motivo, fecha) VALUES (22, 15, 'INGRESO', 15, 'Compra #4', '2026-05-10 09:00:00') ON CONFLICT (id) DO NOTHING;

-- compra
INSERT INTO compra (id, proveedor_id, fecha, total, estado) VALUES (1, 1, '2026-01-10 09:00:00', 12500.00, 'RECIBIDA') ON CONFLICT (id) DO NOTHING;
INSERT INTO compra (id, proveedor_id, fecha, total, estado) VALUES (2, 2, '2026-03-15 14:00:00', 8400.00, 'RECIBIDA') ON CONFLICT (id) DO NOTHING;
INSERT INTO compra (id, proveedor_id, fecha, total, estado) VALUES (3, 1, '2026-03-25 10:00:00', 7300.00, 'RECIBIDA') ON CONFLICT (id) DO NOTHING;
INSERT INTO compra (id, proveedor_id, fecha, total, estado) VALUES (4, 2, '2026-05-10 11:00:00', 9275.00, 'RECIBIDA') ON CONFLICT (id) DO NOTHING;
INSERT INTO compra (id, proveedor_id, fecha, total, estado) VALUES (6, 1, '2026-06-19 17:09:17.648', 0.00, 'PENDIENTE') ON CONFLICT (id) DO NOTHING;

-- detalle_compra
INSERT INTO detalle_compra (id, compra_id, producto_id, cantidad, precio_unitario) VALUES (1, 1, 1, 10, 250.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_compra (id, compra_id, producto_id, cantidad, precio_unitario) VALUES (2, 1, 2, 15, 130.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_compra (id, compra_id, producto_id, cantidad, precio_unitario) VALUES (3, 1, 3, 25, 60.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_compra (id, compra_id, producto_id, cantidad, precio_unitario) VALUES (4, 1, 4, 20, 85.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_compra (id, compra_id, producto_id, cantidad, precio_unitario) VALUES (5, 1, 5, 12, 175.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_compra (id, compra_id, producto_id, cantidad, precio_unitario) VALUES (6, 1, 6, 30, 45.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_compra (id, compra_id, producto_id, cantidad, precio_unitario) VALUES (7, 1, 7, 35, 38.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_compra (id, compra_id, producto_id, cantidad, precio_unitario) VALUES (8, 2, 8, 8, 130.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_compra (id, compra_id, producto_id, cantidad, precio_unitario) VALUES (9, 2, 9, 10, 160.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_compra (id, compra_id, producto_id, cantidad, precio_unitario) VALUES (10, 2, 10, 50, 22.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_compra (id, compra_id, producto_id, cantidad, precio_unitario) VALUES (11, 2, 11, 12, 115.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_compra (id, compra_id, producto_id, cantidad, precio_unitario) VALUES (12, 2, 12, 60, 15.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_compra (id, compra_id, producto_id, cantidad, precio_unitario) VALUES (13, 2, 13, 20, 48.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_compra (id, compra_id, producto_id, cantidad, precio_unitario) VALUES (14, 2, 14, 10, 145.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_compra (id, compra_id, producto_id, cantidad, precio_unitario) VALUES (15, 2, 15, 8, 225.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_compra (id, compra_id, producto_id, cantidad, precio_unitario) VALUES (16, 3, 8, 20, 130.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_compra (id, compra_id, producto_id, cantidad, precio_unitario) VALUES (17, 3, 9, 15, 160.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_compra (id, compra_id, producto_id, cantidad, precio_unitario) VALUES (18, 3, 11, 20, 115.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_compra (id, compra_id, producto_id, cantidad, precio_unitario) VALUES (19, 4, 10, 100, 22.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_compra (id, compra_id, producto_id, cantidad, precio_unitario) VALUES (20, 4, 12, 150, 15.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_compra (id, compra_id, producto_id, cantidad, precio_unitario) VALUES (21, 4, 13, 40, 48.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_compra (id, compra_id, producto_id, cantidad, precio_unitario) VALUES (22, 4, 15, 15, 225.00) ON CONFLICT (id) DO NOTHING;

-- pedido
INSERT INTO pedido (id, cliente_id, fecha, estado) VALUES (1, 6, '2026-02-10 08:00:00', 'DESPACHADO') ON CONFLICT (id) DO NOTHING;
INSERT INTO pedido (id, cliente_id, fecha, estado) VALUES (2, 8, '2026-03-18 10:00:00', 'DESPACHADO') ON CONFLICT (id) DO NOTHING;
INSERT INTO pedido (id, cliente_id, fecha, estado) VALUES (3, 7, '2026-04-20 09:00:00', 'DESPACHADO') ON CONFLICT (id) DO NOTHING;
INSERT INTO pedido (id, cliente_id, fecha, estado) VALUES (4, 9, '2026-05-05 14:00:00', 'DESPACHADO') ON CONFLICT (id) DO NOTHING;
INSERT INTO pedido (id, cliente_id, fecha, estado) VALUES (5, 10, '2026-06-01 11:00:00', 'DESPACHADO') ON CONFLICT (id) DO NOTHING;
INSERT INTO pedido (id, cliente_id, fecha, estado) VALUES (6, 6, '2026-06-18 14:00:00', 'SOLICITADO') ON CONFLICT (id) DO NOTHING;

-- detalle_pedido
INSERT INTO detalle_pedido (id, pedido_id, producto_id, cantidad) VALUES (1, 1, 1, 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_pedido (id, pedido_id, producto_id, cantidad) VALUES (2, 1, 4, 3) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_pedido (id, pedido_id, producto_id, cantidad) VALUES (3, 1, 7, 4) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_pedido (id, pedido_id, producto_id, cantidad) VALUES (4, 2, 10, 8) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_pedido (id, pedido_id, producto_id, cantidad) VALUES (5, 2, 12, 10) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_pedido (id, pedido_id, producto_id, cantidad) VALUES (6, 3, 3, 3) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_pedido (id, pedido_id, producto_id, cantidad) VALUES (7, 3, 2, 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_pedido (id, pedido_id, producto_id, cantidad) VALUES (8, 3, 8, 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_pedido (id, pedido_id, producto_id, cantidad) VALUES (9, 4, 4, 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_pedido (id, pedido_id, producto_id, cantidad) VALUES (10, 4, 7, 5) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_pedido (id, pedido_id, producto_id, cantidad) VALUES (11, 5, 1, 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_pedido (id, pedido_id, producto_id, cantidad) VALUES (12, 5, 5, 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_pedido (id, pedido_id, producto_id, cantidad) VALUES (13, 6, 9, 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_pedido (id, pedido_id, producto_id, cantidad) VALUES (14, 6, 12, 5) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_pedido (id, pedido_id, producto_id, cantidad) VALUES (15, 6, 13, 3) ON CONFLICT (id) DO NOTHING;

-- venta
INSERT INTO venta (id, cliente_id, fecha, monto_total, tipo_venta, metodo_pago, estado) VALUES (1, 7, '2026-01-15 10:00:00', 700.00, 'CONTADO', 'EFECTIVO', 'COMPLETADA') ON CONFLICT (id) DO NOTHING;
INSERT INTO venta (id, cliente_id, fecha, monto_total, tipo_venta, metodo_pago, estado) VALUES (2, 9, '2026-01-28 15:30:00', 410.00, 'CONTADO', 'QR', 'COMPLETADA') ON CONFLICT (id) DO NOTHING;
INSERT INTO venta (id, cliente_id, fecha, monto_total, tipo_venta, metodo_pago, estado) VALUES (3, 6, '2026-02-15 10:30:00', 1490.00, 'CONTADO', 'EFECTIVO', 'COMPLETADA') ON CONFLICT (id) DO NOTHING;
INSERT INTO venta (id, cliente_id, fecha, monto_total, tipo_venta, metodo_pago, estado) VALUES (4, 8, '2026-02-20 14:00:00', 2120.00, 'CREDITO', 'EFECTIVO', 'PENDIENTE') ON CONFLICT (id) DO NOTHING;
INSERT INTO venta (id, cliente_id, fecha, monto_total, tipo_venta, metodo_pago, estado) VALUES (5, 6, '2026-03-05 09:00:00', 660.00, 'CONTADO', 'TARJETA', 'COMPLETADA') ON CONFLICT (id) DO NOTHING;
INSERT INTO venta (id, cliente_id, fecha, monto_total, tipo_venta, metodo_pago, estado) VALUES (6, 7, '2026-03-10 15:00:00', 220.00, 'CONTADO', 'QR', 'COMPLETADA') ON CONFLICT (id) DO NOTHING;
INSERT INTO venta (id, cliente_id, fecha, monto_total, tipo_venta, metodo_pago, estado) VALUES (7, 9, '2026-03-20 11:00:00', 280.00, 'CONTADO', 'EFECTIVO', 'COMPLETADA') ON CONFLICT (id) DO NOTHING;
INSERT INTO venta (id, cliente_id, fecha, monto_total, tipo_venta, metodo_pago, estado) VALUES (8, 10, '2026-04-05 09:45:00', 250.00, 'CREDITO', 'TARJETA', 'PENDIENTE') ON CONFLICT (id) DO NOTHING;
INSERT INTO venta (id, cliente_id, fecha, monto_total, tipo_venta, metodo_pago, estado) VALUES (9, 10, '2026-04-12 16:00:00', 830.00, 'CONTADO', 'EFECTIVO', 'COMPLETADA') ON CONFLICT (id) DO NOTHING;
INSERT INTO venta (id, cliente_id, fecha, monto_total, tipo_venta, metodo_pago, estado) VALUES (10, 7, '2026-04-25 13:00:00', 1550.00, 'CREDITO', 'QR', 'PENDIENTE') ON CONFLICT (id) DO NOTHING;
INSERT INTO venta (id, cliente_id, fecha, monto_total, tipo_venta, metodo_pago, estado) VALUES (11, 6, '2026-05-08 10:00:00', 590.00, 'CONTADO', 'EFECTIVO', 'COMPLETADA') ON CONFLICT (id) DO NOTHING;
INSERT INTO venta (id, cliente_id, fecha, monto_total, tipo_venta, metodo_pago, estado) VALUES (12, 8, '2026-05-12 14:00:00', 4700.00, 'CREDITO', 'EFECTIVO', 'PENDIENTE') ON CONFLICT (id) DO NOTHING;
INSERT INTO venta (id, cliente_id, fecha, monto_total, tipo_venta, metodo_pago, estado) VALUES (13, 9, '2026-05-20 11:30:00', 490.00, 'CONTADO', 'EFECTIVO', 'COMPLETADA') ON CONFLICT (id) DO NOTHING;
INSERT INTO venta (id, cliente_id, fecha, monto_total, tipo_venta, metodo_pago, estado) VALUES (14, 8, '2026-06-02 09:00:00', 1420.00, 'CONTADO', 'TARJETA', 'COMPLETADA') ON CONFLICT (id) DO NOTHING;
INSERT INTO venta (id, cliente_id, fecha, monto_total, tipo_venta, metodo_pago, estado) VALUES (15, 10, '2026-06-10 15:00:00', 1020.00, 'CREDITO', 'EFECTIVO', 'PENDIENTE') ON CONFLICT (id) DO NOTHING;

-- detalle_venta
INSERT INTO detalle_venta (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (1, 1, 1, 2, 350.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_venta (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (2, 2, 10, 4, 35.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_venta (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (3, 2, 7, 4, 55.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_venta (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (4, 2, 12, 2, 25.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_venta (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (5, 3, 1, 2, 350.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_venta (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (6, 3, 4, 3, 120.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_venta (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (7, 3, 7, 4, 55.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_venta (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (8, 3, 13, 3, 70.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_venta (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (9, 4, 2, 3, 180.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_venta (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (10, 4, 4, 4, 120.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_venta (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (11, 4, 5, 2, 250.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_venta (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (12, 4, 14, 3, 200.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_venta (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (13, 5, 3, 4, 85.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_venta (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (14, 5, 8, 1, 180.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_venta (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (15, 5, 13, 2, 70.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_venta (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (16, 6, 7, 4, 55.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_venta (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (17, 7, 10, 8, 35.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_venta (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (18, 8, 11, 1, 160.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_venta (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (19, 8, 6, 1, 65.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_venta (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (20, 8, 12, 1, 25.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_venta (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (21, 9, 7, 10, 55.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_venta (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (22, 9, 10, 8, 35.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_venta (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (23, 10, 15, 3, 310.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_venta (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (24, 10, 14, 2, 200.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_venta (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (25, 10, 9, 1, 220.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_venta (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (26, 11, 1, 1, 350.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_venta (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (27, 11, 4, 2, 120.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_venta (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (28, 12, 1, 5, 350.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_venta (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (29, 12, 5, 4, 250.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_venta (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (30, 12, 14, 6, 200.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_venta (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (31, 12, 9, 2, 220.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_venta (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (32, 12, 15, 1, 310.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_venta (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (33, 13, 6, 5, 65.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_venta (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (34, 13, 7, 3, 55.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_venta (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (35, 14, 5, 2, 250.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_venta (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (36, 14, 14, 1, 200.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_venta (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (37, 14, 2, 4, 180.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_venta (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (38, 15, 9, 3, 220.00) ON CONFLICT (id) DO NOTHING;
INSERT INTO detalle_venta (id, venta_id, producto_id, cantidad, precio_unitario) VALUES (39, 15, 8, 2, 180.00) ON CONFLICT (id) DO NOTHING;

-- credito
INSERT INTO credito (id, venta_id, numero_cuotas, tasa_interes, saldo_pendiente, estado) VALUES (1, 8, 3, 3.50, 172.50, 'VIGENTE') ON CONFLICT (id) DO NOTHING;
INSERT INTO credito (id, venta_id, numero_cuotas, tasa_interes, saldo_pendiente, estado) VALUES (2, 4, 4, 5.00, 1113.00, 'MOROSO') ON CONFLICT (id) DO NOTHING;
INSERT INTO credito (id, venta_id, numero_cuotas, tasa_interes, saldo_pendiente, estado) VALUES (3, 10, 3, 7.00, 1105.66, 'VIGENTE') ON CONFLICT (id) DO NOTHING;
INSERT INTO credito (id, venta_id, numero_cuotas, tasa_interes, saldo_pendiente, estado) VALUES (4, 12, 6, 10.00, 4308.35, 'VIGENTE') ON CONFLICT (id) DO NOTHING;
INSERT INTO credito (id, venta_id, numero_cuotas, tasa_interes, saldo_pendiente, estado) VALUES (5, 15, 6, 8.00, 1101.60, 'VIGENTE') ON CONFLICT (id) DO NOTHING;

-- pago_cuota
INSERT INTO pago_cuota (id, credito_id, numero_cuota, monto_cuota, fecha_vencimiento, fecha_pago, mora, estado) VALUES (1, 1, 1, 86.25, '2026-05-05', '2026-06-19', 0.00, 'PAGADO') ON CONFLICT (id) DO NOTHING;
INSERT INTO pago_cuota (id, credito_id, numero_cuota, monto_cuota, fecha_vencimiento, fecha_pago, mora, estado) VALUES (2, 1, 2, 86.25, '2026-06-05', NULL, 0.00, 'PENDIENTE') ON CONFLICT (id) DO NOTHING;
INSERT INTO pago_cuota (id, credito_id, numero_cuota, monto_cuota, fecha_vencimiento, fecha_pago, mora, estado) VALUES (3, 1, 3, 86.25, '2026-07-05', NULL, 0.00, 'PENDIENTE') ON CONFLICT (id) DO NOTHING;
INSERT INTO pago_cuota (id, credito_id, numero_cuota, monto_cuota, fecha_vencimiento, fecha_pago, mora, estado) VALUES (4, 2, 1, 556.50, '2026-03-20', '2026-03-18', 0.00, 'PAGADO') ON CONFLICT (id) DO NOTHING;
INSERT INTO pago_cuota (id, credito_id, numero_cuota, monto_cuota, fecha_vencimiento, fecha_pago, mora, estado) VALUES (5, 2, 2, 556.50, '2026-04-20', '2026-04-19', 0.00, 'PAGADO') ON CONFLICT (id) DO NOTHING;
INSERT INTO pago_cuota (id, credito_id, numero_cuota, monto_cuota, fecha_vencimiento, fecha_pago, mora, estado) VALUES (6, 2, 3, 556.50, '2026-05-20', NULL, 27.83, 'VENCIDO') ON CONFLICT (id) DO NOTHING;
INSERT INTO pago_cuota (id, credito_id, numero_cuota, monto_cuota, fecha_vencimiento, fecha_pago, mora, estado) VALUES (7, 2, 4, 556.50, '2026-06-15', NULL, 27.83, 'VENCIDO') ON CONFLICT (id) DO NOTHING;
INSERT INTO pago_cuota (id, credito_id, numero_cuota, monto_cuota, fecha_vencimiento, fecha_pago, mora, estado) VALUES (8, 3, 1, 552.83, '2026-05-25', '2026-05-22', 0.00, 'PAGADO') ON CONFLICT (id) DO NOTHING;
INSERT INTO pago_cuota (id, credito_id, numero_cuota, monto_cuota, fecha_vencimiento, fecha_pago, mora, estado) VALUES (9, 3, 2, 552.83, '2026-06-25', NULL, 0.00, 'PENDIENTE') ON CONFLICT (id) DO NOTHING;
INSERT INTO pago_cuota (id, credito_id, numero_cuota, monto_cuota, fecha_vencimiento, fecha_pago, mora, estado) VALUES (10, 3, 3, 552.83, '2026-07-25', NULL, 0.00, 'PENDIENTE') ON CONFLICT (id) DO NOTHING;
INSERT INTO pago_cuota (id, credito_id, numero_cuota, monto_cuota, fecha_vencimiento, fecha_pago, mora, estado) VALUES (11, 4, 1, 861.67, '2026-06-12', '2026-06-10', 0.00, 'PAGADO') ON CONFLICT (id) DO NOTHING;
INSERT INTO pago_cuota (id, credito_id, numero_cuota, monto_cuota, fecha_vencimiento, fecha_pago, mora, estado) VALUES (12, 4, 2, 861.67, '2026-07-12', NULL, 0.00, 'PENDIENTE') ON CONFLICT (id) DO NOTHING;
INSERT INTO pago_cuota (id, credito_id, numero_cuota, monto_cuota, fecha_vencimiento, fecha_pago, mora, estado) VALUES (13, 4, 3, 861.67, '2026-08-12', NULL, 0.00, 'PENDIENTE') ON CONFLICT (id) DO NOTHING;
INSERT INTO pago_cuota (id, credito_id, numero_cuota, monto_cuota, fecha_vencimiento, fecha_pago, mora, estado) VALUES (14, 4, 4, 861.67, '2026-09-12', NULL, 0.00, 'PENDIENTE') ON CONFLICT (id) DO NOTHING;
INSERT INTO pago_cuota (id, credito_id, numero_cuota, monto_cuota, fecha_vencimiento, fecha_pago, mora, estado) VALUES (15, 4, 5, 861.67, '2026-10-12', NULL, 0.00, 'PENDIENTE') ON CONFLICT (id) DO NOTHING;
INSERT INTO pago_cuota (id, credito_id, numero_cuota, monto_cuota, fecha_vencimiento, fecha_pago, mora, estado) VALUES (16, 4, 6, 861.67, '2026-11-12', NULL, 0.00, 'PENDIENTE') ON CONFLICT (id) DO NOTHING;
INSERT INTO pago_cuota (id, credito_id, numero_cuota, monto_cuota, fecha_vencimiento, fecha_pago, mora, estado) VALUES (17, 5, 1, 183.60, '2026-07-10', NULL, 0.00, 'PENDIENTE') ON CONFLICT (id) DO NOTHING;
INSERT INTO pago_cuota (id, credito_id, numero_cuota, monto_cuota, fecha_vencimiento, fecha_pago, mora, estado) VALUES (18, 5, 2, 183.60, '2026-08-10', NULL, 0.00, 'PENDIENTE') ON CONFLICT (id) DO NOTHING;
INSERT INTO pago_cuota (id, credito_id, numero_cuota, monto_cuota, fecha_vencimiento, fecha_pago, mora, estado) VALUES (19, 5, 3, 183.60, '2026-09-10', NULL, 0.00, 'PENDIENTE') ON CONFLICT (id) DO NOTHING;
INSERT INTO pago_cuota (id, credito_id, numero_cuota, monto_cuota, fecha_vencimiento, fecha_pago, mora, estado) VALUES (20, 5, 4, 183.60, '2026-10-10', NULL, 0.00, 'PENDIENTE') ON CONFLICT (id) DO NOTHING;
INSERT INTO pago_cuota (id, credito_id, numero_cuota, monto_cuota, fecha_vencimiento, fecha_pago, mora, estado) VALUES (21, 5, 5, 183.60, '2026-11-10', NULL, 0.00, 'PENDIENTE') ON CONFLICT (id) DO NOTHING;
INSERT INTO pago_cuota (id, credito_id, numero_cuota, monto_cuota, fecha_vencimiento, fecha_pago, mora, estado) VALUES (22, 5, 6, 183.60, '2026-12-10', NULL, 0.00, 'PENDIENTE') ON CONFLICT (id) DO NOTHING;

-- Resetear secuencias
SELECT setval('proveedor_id_seq', COALESCE((SELECT MAX(id) FROM proveedor), 1));
SELECT setval('usuario_id_seq', COALESCE((SELECT MAX(id) FROM usuario), 1));
SELECT setval('producto_id_seq', COALESCE((SELECT MAX(id) FROM producto), 1));
SELECT setval('inventario_id_seq', COALESCE((SELECT MAX(id) FROM inventario), 1));
SELECT setval('movimiento_inventario_id_seq', COALESCE((SELECT MAX(id) FROM movimiento_inventario), 1));
SELECT setval('compra_id_seq', COALESCE((SELECT MAX(id) FROM compra), 1));
SELECT setval('detalle_compra_id_seq', COALESCE((SELECT MAX(id) FROM detalle_compra), 1));
SELECT setval('pedido_id_seq', COALESCE((SELECT MAX(id) FROM pedido), 1));
SELECT setval('detalle_pedido_id_seq', COALESCE((SELECT MAX(id) FROM detalle_pedido), 1));
SELECT setval('venta_id_seq', COALESCE((SELECT MAX(id) FROM venta), 1));
SELECT setval('detalle_venta_id_seq', COALESCE((SELECT MAX(id) FROM detalle_venta), 1));
SELECT setval('credito_id_seq', COALESCE((SELECT MAX(id) FROM credito), 1));
SELECT setval('pago_cuota_id_seq', COALESCE((SELECT MAX(id) FROM pago_cuota), 1));
