package org.mailgrupo02.sistema.negocio.pagos;

import org.mailgrupo02.sistema.modelo.CreditoM;
import org.mailgrupo02.sistema.modelo.PagoCuotaM;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class PagoCuotaService {

    public String registrarPago(int creditoId, int numeroCuota, double montoCuota) throws SQLException {
        PagoCuotaM pago = new PagoCuotaM();
        pago.setCreditoId(creditoId);
        pago.setNumeroCuota(numeroCuota);
        pago.setMontoCuota(montoCuota);
        pago.setFechaPago(new Date(System.currentTimeMillis()));
        pago.setEstado("PAGADO");
        return pago.crear();
    }

    public String verCuotas(int creditoId) throws SQLException {
        List<PagoCuotaM> lista = new PagoCuotaM().obtenerPorCredito(creditoId);
        return mapear(lista);
    }

    public String listarCreditos() throws SQLException {
        List<CreditoM> lista = CreditoM.obtenerTodos();
        return mapearCreditos(lista);
    }

    private String mapear(List<PagoCuotaM> lista) {
        StringBuilder sb = new StringBuilder();
        String format = "%-5s %-10s %-12s %-12s %-15s %-15s %-10s %-10s%n";
        sb.append(String.format(format, "ID", "Credito", "Nro Cuota", "Monto", "Fecha Venc", "Fecha Pago", "Mora", "Estado"));
        sb.append("----------------------------------------------------------------------------------------------------\r\n");
        for (PagoCuotaM p : lista) {
            sb.append(String.format(format,
                    p.getId(),
                    p.getCreditoId(),
                    p.getNumeroCuota(),
                    String.format("%.2f", p.getMontoCuota()),
                    p.getFechaVencimiento() != null ? p.getFechaVencimiento().toString() : "N/A",
                    p.getFechaPago() != null ? p.getFechaPago().toString() : "N/A",
                    String.format("%.2f", p.getMora()),
                    p.getEstado() != null ? p.getEstado() : "N/A"));
        }
        return sb.toString();
    }

    private String mapearCreditos(List<CreditoM> lista) {
        StringBuilder sb = new StringBuilder();
        String format = "%-5s %-8s %-12s %-12s %-18s %-10s%n";
        sb.append(String.format(format, "ID", "Venta", "Nro Cuotas", "Interes", "Saldo Pendiente", "Estado"));
        sb.append("-----------------------------------------------------------------\r\n");
        for (CreditoM c : lista) {
            sb.append(String.format(format,
                    c.getId(),
                    c.getVentaId(),
                    c.getNumeroCuotas(),
                    String.format("%.2f", c.getTasaInteres()),
                    String.format("%.2f", c.getSaldoPendiente()),
                    c.getEstado() != null ? c.getEstado() : "N/A"));
        }
        return sb.toString();
    }
}
