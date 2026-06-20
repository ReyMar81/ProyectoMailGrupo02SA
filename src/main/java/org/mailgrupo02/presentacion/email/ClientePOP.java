package org.mailgrupo02.presentacion.email;

import org.mailgrupo02.datos.config.ConfigEmailServer;
import org.mailgrupo02.datos.conexion.Conexion;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientePOP {
    static final String HOST = ConfigEmailServer.HOST;
    static final int PORT = 110;
    static final String USER = ConfigEmailServer.USER;
    static final String PASS = ConfigEmailServer.PASSWORD;

    private Socket socket;
    private BufferedReader entrada;
    private DataOutputStream salida;

    public void conectar() throws IOException {
        socket = new Socket(HOST, PORT);
        entrada = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        salida = new DataOutputStream(socket.getOutputStream());
        System.out.println("S : " + entrada.readLine());

        enviarComando(salida, entrada, "USER " + USER + "\r\n");
        enviarComando(salida, entrada, "PASS " + PASS + "\r\n");
    }

    private static String enviarComando(DataOutputStream salida, BufferedReader entrada, String comando)
            throws IOException {
        System.out.print("C : " + comando);
        salida.write(comando.getBytes(StandardCharsets.UTF_8));
        String response;
        if (comando.startsWith("RETR") || comando.startsWith("LIST")) {
            response = leerRespuestaMultilinea(entrada);
            return response;
        }
        return entrada.readLine();
    }

    static protected String leerRespuestaMultilinea(BufferedReader in) throws IOException {
        StringBuilder lines = new StringBuilder();
        while (true) {
            String line = in.readLine();
            if (line == null)
                throw new IOException("S : Server unawares closed the connection.");
            if (line.equals("."))
                break;
            if (line.startsWith("."))
                line = line.substring(1);
            lines.append("\n").append(line);
        }
        return lines.toString();
    }

    private static String extraerSubject(String correo) {
        for (String line : correo.split("\n")) {
            if (line.startsWith("Subject:")) {
                return decodificarRFC2047(line.substring(8).trim());
            }
        }
        return null;
    }

    public static String decodificarRFC2047(String texto) {
        Pattern p = Pattern.compile("=\\?([^?]+)\\?([BbQq])\\?([^?]*)\\?=");
        Matcher m = p.matcher(texto);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String charset = m.group(1);
            String encoding = m.group(2).toUpperCase();
            String encoded = m.group(3);
            try {
                byte[] bytes;
                if ("B".equals(encoding)) {
                    bytes = Base64.getDecoder().decode(encoded);
                } else {
                    String qp = encoded.replace('_', ' ');
                    bytes = decodificarQuotedPrintable(qp);
                }
                m.appendReplacement(sb, Matcher.quoteReplacement(new String(bytes, charset)));
            } catch (Exception e) {
                m.appendReplacement(sb, Matcher.quoteReplacement(m.group(0)));
            }
        }
        m.appendTail(sb);
        return sb.toString().trim();
    }

    private static byte[] decodificarQuotedPrintable(String s) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '=' && i + 2 < s.length()) {
                try {
                    baos.write(Integer.parseInt(s.substring(i + 1, i + 3), 16));
                    i += 2;
                } catch (NumberFormatException e) {
                    baos.write(c);
                }
            } else {
                baos.write(c);
            }
        }
        return baos.toByteArray();
    }

    private static void ejecutarConsulta(String subject) {
        String query = parsearQuery(subject);
        if (query != null && subject.contains("PATTERN")) {
            try (Connection conn = Conexion.conectar();
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query)) {

                StringBuilder queryResult = new StringBuilder();
                int rowCount = 1;

                while (rs.next()) {
                    queryResult.append("Row ").append(rowCount).append(": ");
                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                        queryResult.append(rs.getString(i).trim()).append(" ");
                    }
                    queryResult.append("\r\n");
                    rowCount++;
                }
                System.out.print(queryResult.toString());
            } catch (Exception e) {
                System.out.println("Error ejecutando la consulta: " + e.getMessage());
            }
        }
    }

    private static String parsearQuery(String subject) {
        if (subject.startsWith("PATTERN:")) {
            return subject.substring(8).trim();
        }
        return null;
    }

    public int obtenerTotalDeCorreos() throws IOException {
        String response = enviarComando(salida, entrada, "STAT\r\n");
        response = response.substring(4, response.length());
        int i = 1;
        while (response.charAt(i) != ' ') {
            i++;
        }
        response = response.substring(0, i);
        return Integer.parseInt(response);
    }

    public void desconectar() {
        try {
            enviarComando(salida, entrada, "QUIT\r\n");
            entrada.close();
            salida.close();
            socket.close();
            System.out.println("S : Conexión finalizada.");
        } catch (IOException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    public void revisarCorreos() {
        try {
            conectar();
            String x;
            x = enviarComando(salida, entrada, "LIST\r\n");
            System.out.println(x);
            desconectar();
        } catch (Exception e) {
            System.out.println("Error al revisar correos: " + e.getMessage());
        }
    }

    public String obtenerCorreo(int posicion) {
        try {
            return enviarComando(salida, entrada, "RETR " + posicion + "\r\n");
        } catch (IOException e) {
            System.out.println("Error al obtener el correo: " + e.getMessage());
            return null;
        }
    }

    public String obtenerCorreoYEliminar(int posicion) {
        try {
            String correo = enviarComando(salida, entrada, "RETR " + posicion + "\r\n");
            enviarComando(salida, entrada, "DELE " + posicion + "\r\n");
            return correo;
        } catch (IOException e) {
            System.out.println("Error al obtener el correo: " + e.getMessage());
            return null;
        }
    }
}
