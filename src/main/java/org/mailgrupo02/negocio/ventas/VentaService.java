package org.mailgrupo02.negocio.ventas;

import org.mailgrupo02.datos.modelo.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class VentaService {

    public VentaService(VentaM ventaM, CreditoM creditoM) {
    }

    public String obtenerVentas() throws SQLException {
        List<VentaM> ventas = VentaM.obtenerTodos();
        return mapear(ventas);
    }

    public String crearVentaContado(int clienteId, Timestamp fecha, double montoTotal, String metodoPago)
            throws SQLException {
        VentaValidator.validarVentaContado(clienteId, fecha, montoTotal, metodoPago);

        VentaM venta = new VentaM();
        venta.setClienteId(clienteId);
        venta.setFecha(fecha);
        venta.setMontoTotal(montoTotal);
        venta.setTipoVenta("CONTADO");
        venta.setMetodoPago(metodoPago);
        venta.setEstado("COMPLETADA");

        int ventaId = VentaM.crear(venta);
        return "Venta al contado creada con éxito (ID: " + ventaId + ")";
    }

    public String crearVentaCredito(int clienteId, Timestamp fecha, double montoTotal,
            int numeroCuotas, double tasaInteres, String metodoPago) throws SQLException {
        VentaValidator.validarVentaCredito(clienteId, fecha, montoTotal, numeroCuotas, tasaInteres, metodoPago);

        VentaM venta = new VentaM();
        venta.setClienteId(clienteId);
        venta.setFecha(fecha);
        venta.setMontoTotal(montoTotal);
        venta.setTipoVenta("CREDITO");
        venta.setMetodoPago(metodoPago);
        venta.setEstado("PENDIENTE");

        int ventaId = VentaM.crear(venta);

        CreditoM credito = new CreditoM();
        credito.setVentaId(ventaId);
        credito.setNumeroCuotas(numeroCuotas);
        credito.setTasaInteres(tasaInteres);
        credito.setSaldoPendiente(montoTotal);
        credito.setEstado("VIGENTE");

        int creditoId = CreditoM.crear(credito);

        double montoCuota = (montoTotal * (1 + tasaInteres / 100)) / numeroCuotas;
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);

        for (int i = 1; i <= numeroCuotas; i++) {
            PagoCuotaM pago = new PagoCuotaM();
            pago.setCreditoId(creditoId);
            pago.setNumeroCuota(i);
            pago.setMontoCuota(montoCuota);
            cal.add(Calendar.MONTH, 1);
            pago.setFechaVencimiento(new Date(cal.getTimeInMillis()));
            pago.setFechaPago(null);
            pago.setMora(0);
            pago.setEstado("PENDIENTE");
            pago.crear();
        }

        return "Venta a crédito creada con éxito (ID: " + ventaId + "), Crédito ID: " + creditoId
                + " con " + numeroCuotas + " cuotas generadas.";
    }

    public String actualizarVenta(int id, int clienteId, Timestamp fecha, double montoTotal,
            String tipoVenta, String metodoPago, String estado) throws SQLException {
        VentaValidator.validarActualizarVenta(id, clienteId, fecha, montoTotal, tipoVenta, metodoPago, estado);

        VentaM venta = new VentaM();
        venta.setId(id);
        venta.setClienteId(clienteId);
        venta.setFecha(fecha);
        venta.setMontoTotal(montoTotal);
        venta.setTipoVenta(tipoVenta);
        venta.setMetodoPago(metodoPago);
        venta.setEstado(estado);

        return VentaM.actualizar(venta);
    }

    public VentaN leerVenta(int id) throws SQLException {
        VentaValidator.validarEliminarVenta(id);
        VentaM venta = VentaM.leer(id);

        VentaN n = new VentaN();
        n.setId(venta.getId());
        n.setClienteId(venta.getClienteId());
        n.setFecha(venta.getFecha() != null ? venta.getFecha().toString() : "N/A");
        n.setMontoTotal(venta.getMontoTotal());
        n.setTipoVenta(venta.getTipoVenta());
        n.setMetodoPago(venta.getMetodoPago());
        n.setEstado(venta.getEstado());

        DetalleVentaM detM = new DetalleVentaM();
        n.setDetalles(detM.obtenerPorVenta(id));

        if ("CREDITO".equals(venta.getTipoVenta())) {
            CreditoM credito = CreditoM.obtenerPorVenta(id);
            if (credito != null) {
                n.setCredito(credito);
                PagoCuotaM pagM = new PagoCuotaM();
                n.setCuotas(pagM.obtenerPorCredito(credito.getId()));
            }
        }

        return n;
    }

    public String eliminarVenta(int id) throws SQLException {
        VentaValidator.validarEliminarVenta(id);
        return VentaM.eliminar(id);
    }

    private String mapear(List<VentaM> ventas) throws SQLException {
        StringBuilder sb = new StringBuilder();
        String format = "%-5s %-10s %-22s %-15s %-12s %-15s %-15s%n";
        sb.append(String.format(format, "ID", "Cliente", "Fecha", "Monto Total", "Tipo", "Metodo Pago", "Estado"));
        sb.append(
                "----------------------------------------------------------------------------------------------------\r\n");

        for (VentaM venta : ventas) {
            sb.append(String.format(format,
                    venta.getId(),
                    venta.getClienteId(),
                    venta.getFecha() != null ? venta.getFecha().toString() : "N/A",
                    String.format("%.2f", venta.getMontoTotal()),
                    venta.getTipoVenta() != null ? venta.getTipoVenta() : "N/A",
                    venta.getMetodoPago() != null ? venta.getMetodoPago() : "N/A",
                    venta.getEstado() != null ? venta.getEstado() : "N/A"));
        }
        return sb.toString();
    }
}
