package org.mailgrupo02.negocio.pedidos;

import org.mailgrupo02.datos.conexion.Conexion;
import org.mailgrupo02.datos.modelo.*;
import org.mailgrupo02.negocio.pagos.PagoFacilService;
import org.mailgrupo02.negocio.ventas.VentaService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class PedidoService {

    public String obtenerPedidos() throws SQLException {
        List<PedidoM> lista = PedidoM.obtenerTodos();
        return mapear(lista);
    }

    /** Admin: crea pedido vacío para un cliente (por clienteId). */
    public String crearPedido(int clienteId) throws SQLException {
        PedidoM pedido = new PedidoM();
        pedido.setClienteId(clienteId);
        pedido.setFecha(new Timestamp(System.currentTimeMillis()));
        pedido.setEstado("SOLICITADO");
        int id = PedidoM.crear(pedido);
        return "Pedido creado con éxito (ID: " + id + ")";
    }

    /**
     * Cliente: crea un pedido con sus productos en una sola operación.
     * items = array de [productoId, cantidad].
     * Valida stock antes de crear el pedido.
     */
    public String crearConProductos(int clienteId, int[][] items) throws SQLException {
        if (items == null || items.length == 0) {
            return "Error: debe especificar al menos un producto.";
        }
        StringBuilder erroresStock = new StringBuilder();
        for (int[] item : items) {
            int productoId = item[0];
            int cantidad   = item[1];
            ProductoM p = ProductoM.leer(productoId);
            if (p == null || !p.isActivo()) {
                return "Error: producto ID " + productoId + " no existe o está inactivo.";
            }
            int stock = consultarStock(productoId);
            if (cantidad > stock) {
                erroresStock.append("\n  • ").append(p.getNombre())
                    .append(" — solicitado: ").append(cantidad)
                    .append(", disponible: ").append(stock);
            }
        }
        if (erroresStock.length() > 0) {
            return "Error: stock insuficiente para los siguientes productos:" + erroresStock.toString();
        }

        PedidoM pedido = new PedidoM();
        pedido.setClienteId(clienteId);
        pedido.setFecha(new Timestamp(System.currentTimeMillis()));
        pedido.setEstado("SOLICITADO");
        int pedidoId = PedidoM.crear(pedido);

        StringBuilder resumen = new StringBuilder();
        for (int[] item : items) {
            int productoId = item[0];
            int cantidad   = item[1];
            ProductoM p = ProductoM.leer(productoId);
            PedidoDetalleM det = new PedidoDetalleM();
            det.setPedidoId(pedidoId);
            det.setProductoId(productoId);
            det.setCantidad(cantidad);
            PedidoDetalleM.crear(det);
            resumen.append("\n  • ").append(p.getNombre())
                   .append(" x").append(cantidad)
                   .append(" — Bs. ").append(String.format("%.2f", p.getPrecioVentaBase() * cantidad));
        }
        return "Pedido registrado exitosamente (ID: " + pedidoId + ")" + resumen.toString();
    }

    /** Cliente: lista sus propios pedidos. */
    public String obtenerPorCliente(int clienteId) throws SQLException {
        List<PedidoM> lista = PedidoM.obtenerPorCliente(clienteId);
        if (lista.isEmpty()) return "No tienes pedidos registrados.";
        return mapear(lista);
    }

    public PedidoN leerPedido(int id) throws SQLException {
        PedidoM pedido = PedidoM.leer(id);
        PedidoN n = new PedidoN();
        n.setId(pedido.getId());
        n.setClienteId(pedido.getClienteId());
        n.setFecha(pedido.getFecha() != null ? pedido.getFecha().toString() : "N/A");
        n.setEstado(pedido.getEstado());
        n.setDetalles(PedidoDetalleM.obtenerPorPedido(id));
        return n;
    }

    /** Cliente: obtiene su pedido verificando que le pertenezca. */
    public String leerPedidoCliente(int pedidoId, int clienteId) throws SQLException {
        PedidoM p = PedidoM.leer(pedidoId);
        if (p.getClienteId() != clienteId) return "Error: el pedido no pertenece a tu cuenta.";
        PedidoN n = leerPedido(pedidoId);
        return formatearDetalle(n);
    }

    /** Cliente: cancela su pedido solo si está en estado SOLICITADO. */
    public String cancelarPorCliente(int pedidoId, int clienteId) throws SQLException {
        PedidoM p = PedidoM.leer(pedidoId);
        if (p.getClienteId() != clienteId) return "Error: el pedido no pertenece a tu cuenta.";
        if (!"SOLICITADO".equals(p.getEstado())) {
            return "Error: solo puedes cancelar pedidos en estado SOLICITADO. Estado actual: " + p.getEstado();
        }
        p.setEstado("ANULADO");
        return PedidoM.actualizar(p);
    }

    public String despacharPedido(int id) throws SQLException {
        PedidoM pedido = PedidoM.leer(id);
        pedido.setEstado("DESPACHADO");
        return PedidoM.actualizar(pedido);
    }

    public String anularPedido(int id) throws SQLException {
        PedidoM pedido = PedidoM.leer(id);
        pedido.setEstado("ANULADO");
        return PedidoM.actualizar(pedido);
    }

    private int consultarStock(int productoId) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexion.conectar();
            pstmt = conn.prepareStatement("SELECT stock_actual FROM inventario WHERE producto_id = ?");
            pstmt.setInt(1, productoId);
            rs = pstmt.executeQuery();
            return rs.next() ? rs.getInt("stock_actual") : 0;
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) {}
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) {}
            if (conn != null) try { conn.close(); } catch (SQLException e) {}
        }
    }

    /**
     * Admin: acepta un pedido (cambia estado a EN_PROCESO).
     * Valida stock suficiente y descuenta del inventario con movimiento EGRESO.
     */
    public String aceptarPedido(int pedidoId) throws SQLException {
        PedidoM pedido = PedidoM.leer(pedidoId);
        if (!"SOLICITADO".equals(pedido.getEstado())) {
            return "Error: solo se pueden aceptar pedidos en estado SOLICITADO. Estado actual: " + pedido.getEstado();
        }
        List<PedidoDetalleM> detalles = PedidoDetalleM.obtenerPorPedido(pedidoId);
        if (detalles == null || detalles.isEmpty()) {
            return "Error: el pedido no tiene productos.";
        }
        StringBuilder erroresStock = new StringBuilder();
        for (PedidoDetalleM d : detalles) {
            int stock = consultarStock(d.getProductoId());
            if (d.getCantidad() > stock) {
                String nombre = "ID:" + d.getProductoId();
                try { ProductoM p = ProductoM.leer(d.getProductoId()); nombre = p != null ? p.getNombre() : nombre; } catch (Exception e) {}
                erroresStock.append("\n  • ").append(nombre)
                    .append(" — solicitado: ").append(d.getCantidad())
                    .append(", disponible: ").append(stock);
            }
        }
        if (erroresStock.length() > 0) {
            return "Error: stock insuficiente para aceptar el pedido:" + erroresStock.toString();
        }
        for (PedidoDetalleM d : detalles) {
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try {
                conn = Conexion.conectar();
                pstmt = conn.prepareStatement("SELECT id, stock_actual FROM inventario WHERE producto_id = ?");
                pstmt.setInt(1, d.getProductoId());
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    int invId = rs.getInt("id");
                    int nuevoStock = rs.getInt("stock_actual") - d.getCantidad();
                    rs.close();
                    pstmt = conn.prepareStatement("UPDATE inventario SET stock_actual = ?, fecha_actualizacion = ? WHERE id = ?");
                    pstmt.setInt(1, nuevoStock);
                    pstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                    pstmt.setInt(3, invId);
                    pstmt.executeUpdate();
                    MovimientoInventarioM mov = new MovimientoInventarioM();
                    mov.setInventarioId(invId);
                    mov.setTipoMovimiento("EGRESO");
                    mov.setCantidad(d.getCantidad());
                    mov.setMotivo("Pedido #" + pedidoId + " aceptado");
                    mov.setFecha(new Timestamp(System.currentTimeMillis()));
                    mov.crear();
                }
            } finally {
                if (rs != null) try { rs.close(); } catch (SQLException e) {}
                if (pstmt != null) try { pstmt.close(); } catch (SQLException e) {}
                if (conn != null) try { conn.close(); } catch (SQLException e) {}
            }
        }
        pedido.setEstado("EN_PROCESO");
        PedidoM.actualizar(pedido);
        return "Pedido #" + pedidoId + " aceptado exitosamente. Stock descontado del inventario.";
    }

    // ── Métodos de pago del pedido (cliente) ──────────────────────────────

    double calcularTotalPedido(int pedidoId) throws SQLException {
        List<PedidoDetalleM> detalles = PedidoDetalleM.obtenerPorPedido(pedidoId);
        double total = 0;
        for (PedidoDetalleM d : detalles) {
            ProductoM p = ProductoM.leer(d.getProductoId());
            if (p != null) total += p.getPrecioVentaBase() * d.getCantidad();
        }
        return total;
    }

    private String obtenerDatosCliente(int clienteId, StringBuilder nombre, StringBuilder email, StringBuilder tel) {
        try {
            UsuarioM u = UsuarioM.leer(clienteId);
            if (u != null) {
                nombre.append(u.getNombre() != null ? u.getNombre() : "");
                email.append(u.getEmail() != null ? u.getEmail() : "");
                tel.append(u.getTelefono() != null ? u.getTelefono() : "");
            }
        } catch (Exception e) {
            System.err.println("[PedidoService] Error al obtener datos del cliente: " + e.getMessage());
        }
        return nombre.toString();
    }

    private String generarQRHtml(String txId, double monto, int clienteId, String descripcion) {
        StringBuilder nombre = new StringBuilder();
        StringBuilder email = new StringBuilder();
        StringBuilder tel = new StringBuilder();
        obtenerDatosCliente(clienteId, nombre, email, tel);
        String[] qr = PagoFacilService.generarQR(nombre.toString(), tel.toString(), email.toString(), txId, monto, descripcion);
        if (qr == null) return null;
        PagoFacilService.registrarTransaccion(txId, email.toString(), monto, "pedido;" + qr[0]);
        String b64 = qr[1].replace("\r", "").replace("\n", "").trim();
        return "<div style=\"text-align:center;margin:15px 0;\">" +
               "<img src=\"data:image/png;base64," + b64 +
               "\" style=\"max-width:100%;width:250px;border:4px solid #1d4ed8;border-radius:12px;\"><br><br>" +
               "<strong style=\"color:#1d4ed8;font-size:15px;\">Monto: " +
               String.format("%.2f", monto) + " Bs.</strong><br>" +
               "<span style=\"color:#6b7280;font-size:12px;\">Ref: " + txId + "</span>" +
               "</div>";
    }

    /**
     * Cliente: paga pedido al contado en EFECTIVO.
     * Crea la venta inmediatamente (COMPLETADA).
     */
    public String pagarContadoEfectivo(int pedidoId, int clienteId) throws SQLException {
        PedidoM p = PedidoM.leer(pedidoId);
        if (p.getClienteId() != clienteId) return "Error: el pedido no te pertenece.";
        if (!"SOLICITADO".equals(p.getEstado()) && !"EN_PROCESO".equals(p.getEstado())) {
            return "Error: el pedido debe estar en SOLICITADO o EN_PROCESO. Estado actual: " + p.getEstado();
        }
        VentaService vs = new VentaService(new VentaM(), new CreditoM());
        return vs.procesarDesdePedido(pedidoId, "CONTADO", "EFECTIVO", 0, 0);
    }

    /**
     * Cliente: paga pedido al contado con QR.
     * Genera QR con el total. Cuando PagoFacil confirme, se crea la venta.
     */
    public String pagarContadoQR(int pedidoId, int clienteId) throws SQLException {
        PedidoM p = PedidoM.leer(pedidoId);
        if (p.getClienteId() != clienteId) return "Error: el pedido no te pertenece.";
        if (!"SOLICITADO".equals(p.getEstado()) && !"EN_PROCESO".equals(p.getEstado())) {
            return "Error: el pedido debe estar en SOLICITADO o EN_PROCESO. Estado actual: " + p.getEstado();
        }
        double total = calcularTotalPedido(pedidoId);
        String txId = "PED-CONTADO-" + pedidoId;
        String qrHtml = generarQRHtml(txId, total, clienteId, "Pago Pedido #" + pedidoId + " (Contado)");
        if (qrHtml == null) return "Error: No se pudo generar el código QR. Intente de nuevo.";
        return "Escanea el QR para pagar tu pedido #" + pedidoId + " (Contado). Total: Bs. "
            + String.format("%.2f", total) + "\n" + qrHtml;
    }

    /**
     * Cliente: paga pedido a crédito en EFECTIVO.
     * Crea la venta (PENDIENTE), crédito y cuotas inmediatamente.
     */
    public String pagarCreditoEfectivo(int pedidoId, int clienteId, int cuotas, double tasa) throws SQLException {
        PedidoM p = PedidoM.leer(pedidoId);
        if (p.getClienteId() != clienteId) return "Error: el pedido no te pertenece.";
        if (!"SOLICITADO".equals(p.getEstado()) && !"EN_PROCESO".equals(p.getEstado())) {
            return "Error: el pedido debe estar en SOLICITADO o EN_PROCESO. Estado actual: " + p.getEstado();
        }
        VentaService vs = new VentaService(new VentaM(), new CreditoM());
        return vs.procesarDesdePedido(pedidoId, "CREDITO", "EFECTIVO", cuotas, tasa);
    }

    /**
     * Cliente: paga pedido a crédito con QR para la primera cuota.
     * Crea la venta + crédito + cuotas inmediatamente y genera QR para la primera cuota.
     */
    public String pagarCreditoQR(int pedidoId, int clienteId, int cuotas, double tasa) throws SQLException {
        PedidoM p = PedidoM.leer(pedidoId);
        if (p.getClienteId() != clienteId) return "Error: el pedido no te pertenece.";
        if (!"SOLICITADO".equals(p.getEstado()) && !"EN_PROCESO".equals(p.getEstado())) {
            return "Error: el pedido debe estar en SOLICITADO o EN_PROCESO. Estado actual: " + p.getEstado();
        }
        VentaService vs = new VentaService(new VentaM(), new CreditoM());
        String ventaResult = vs.procesarDesdePedido(pedidoId, "CREDITO", "QR", cuotas, tasa);
        if (ventaResult.toLowerCase().startsWith("error")) return ventaResult;
        int ventaId = Integer.parseInt(ventaResult.replaceAll(".*\\(ID:\\s*(\\d+)\\).*", "$1").trim());
        CreditoM credito = CreditoM.obtenerPorVenta(ventaId);
        if (credito == null) return ventaResult + "\nError: no se encontró el crédito asociado.";
        double total = calcularTotalPedido(pedidoId);
        double montoPrimeraCuota = (total * (1 + tasa / 100)) / cuotas;
        String txId = "CUO-" + credito.getId() + "-1";
        String qrHtml = generarQRHtml(txId, montoPrimeraCuota, clienteId,
            "Pago 1ra cuota Pedido #" + pedidoId + " (" + cuotas + " cuotas, " + tasa + "% interés)");
        if (qrHtml == null) return ventaResult + "\nError: No se pudo generar el QR. Usa PAGARCUOTA[" + credito.getId() + ",1] para pagar.";
        return ventaResult + "\n\nEscanea el QR para pagar la primera cuota de tu Pedido #" + pedidoId
            + "\nCrédito ID: " + credito.getId()
            + "\nPrimera cuota: Bs. " + String.format("%.2f", montoPrimeraCuota)
            + "\n" + qrHtml;
    }

    public String agregarDetalle(int pedidoId, int productoId, int cantidad) throws SQLException {
        PedidoDetalleM det = new PedidoDetalleM();
        det.setPedidoId(pedidoId);
        det.setProductoId(productoId);
        det.setCantidad(cantidad);
        return PedidoDetalleM.crear(det);
    }

    private String mapear(List<PedidoM> lista) {
        StringBuilder sb = new StringBuilder();
        String fmt = "%-5s %-10s %-22s %-12s%n";
        sb.append(String.format(fmt, "ID", "Cliente", "Fecha", "Estado"));
        sb.append("---------------------------------------------------------\r\n");
        for (PedidoM p : lista) {
            sb.append(String.format(fmt,
                p.getId(), p.getClienteId(),
                p.getFecha() != null ? p.getFecha().toString() : "N/A",
                p.getEstado() != null ? p.getEstado() : "N/A"));
        }
        return sb.toString();
    }

    private String formatearDetalle(PedidoN n) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Pedido #%d | Estado: %s | Fecha: %s%n",
            n.getId(), n.getEstado(), n.getFecha()));
        sb.append("---------------------------------------------------------\r\n");
        List<PedidoDetalleM> detalles = n.getDetalles();
        if (detalles == null || detalles.isEmpty()) {
            sb.append("Sin productos registrados.\r\n");
        } else {
            String fmt = "%-5s %-30s %-8s%n";
            sb.append(String.format(fmt, "ProdID", "Producto", "Cantidad"));
            sb.append("-----------------------------------------\r\n");
            for (PedidoDetalleM d : detalles) {
                try {
                    ProductoM p = ProductoM.leer(d.getProductoId());
                    String nombre = p != null ? p.getNombre() : "ID:" + d.getProductoId();
                    sb.append(String.format(fmt, d.getProductoId(), nombre, d.getCantidad()));
                } catch (Exception e) {
                    sb.append(String.format(fmt, d.getProductoId(), "N/A", d.getCantidad()));
                }
            }
        }
        return sb.toString();
    }
}
