package org.mailgrupo02.presentacion.email;

public class PlantillaBase {

    static String css() {
        return
            "body{font-family:-apple-system,BlinkMacSystemFont,'Segoe UI',Roboto,sans-serif;background:#f0f2f5;color:#1e293b;margin:0;padding:0;}\n" +
            ".container{max-width:700px;margin:20px auto;background:#fff;border-radius:12px;overflow:hidden;box-shadow:0 4px 20px rgba(0,0,0,0.12);border:1px solid #e2e8f0;}\n" +
            ".header{background:linear-gradient(135deg,#c0392b,#7b241c);padding:32px 24px;text-align:center;color:#fff;}\n" +
            ".header h1{margin:0;font-size:32px;font-weight:900;letter-spacing:4px;text-transform:uppercase;}\n" +
            ".header p{margin:0;font-size:14px;letter-spacing:0.5px;opacity:0.82;}\n" +
            ".content{padding:32px 28px;}\n" +
            ".card-title{font-size:26px;font-weight:800;margin-top:0;margin-bottom:20px;color:#c0392b;border-bottom:3px solid #fca5a5;padding-bottom:10px;}\n" +
            // OK card
            ".ok-card{background:#fff;border:2px solid #fca5a5;border-top:6px solid #c0392b;border-radius:12px;padding:28px 30px;text-align:center;margin-bottom:20px;}\n" +
            ".ok-icon{display:block;font-size:58px;color:#c0392b;line-height:1;margin-bottom:12px;}\n" +
            ".ok-tit{display:block;font-size:24px;font-weight:900;color:#c0392b;letter-spacing:2px;text-transform:uppercase;margin-bottom:14px;}\n" +
            ".ok-msg{display:block;font-size:19px;color:#374151;line-height:1.70;margin-bottom:6px;}\n" +
            ".id-badge{display:inline-block;background:#c0392b;color:#fff;padding:10px 28px;border-radius:28px;font-size:24px;font-weight:800;margin-top:16px;letter-spacing:1px;}\n" +
            // Error card
            ".err-card{background:#fff5f5;border:2px solid #fca5a5;border-top:6px solid #991b1b;border-radius:12px;padding:28px 30px;text-align:center;margin-bottom:20px;}\n" +
            ".err-icon{display:block;font-size:58px;color:#991b1b;line-height:1;margin-bottom:12px;}\n" +
            ".err-tit{display:block;font-size:24px;font-weight:900;color:#991b1b;letter-spacing:2px;text-transform:uppercase;margin-bottom:14px;}\n" +
            ".err-msg{display:block;font-size:18px;color:#7f1d1d;line-height:1.65;}\n" +
            // List / table results
            ".lista-hdr{background:#4a5568;color:#fff;padding:13px 20px;border-radius:10px 10px 0 0;font-size:16px;font-weight:700;text-transform:uppercase;letter-spacing:0.5px;margin-top:4px;}\n" +
            ".lista-wrap{border:2px solid #e2e8f0;border-top:none;border-radius:0 0 10px 10px;overflow-x:auto;margin-bottom:22px;}\n" +
            ".table-pre{font-family:'Courier New',Courier,monospace;font-size:17px;background:#f8fafc;padding:20px;white-space:pre;color:#1e293b;line-height:1.65;margin:0;}\n" +
            // Footer
            ".footer{background:#4a5568;padding:18px 24px;text-align:center;font-size:15px;color:#fff;}\n" +
            // Badges for detail cards (create/update/delete)
            ".badge-ok{background:#f0fdf4;border:2px solid #86efac;color:#166534;padding:14px 18px;border-radius:10px;font-size:19px;font-weight:700;margin-bottom:18px;display:block;}\n" +
            ".badge-edit{background:#fffbeb;border:2px solid #fcd34d;color:#92400e;padding:14px 18px;border-radius:10px;font-size:19px;font-weight:700;margin-bottom:18px;display:block;}\n" +
            ".badge-del{background:#fef2f2;border:2px solid #fca5a5;color:#991b1b;padding:14px 18px;border-radius:10px;font-size:19px;font-weight:700;margin-bottom:18px;display:block;}\n" +
            // Detail table
            ".dt{width:100%;border-collapse:collapse;margin-top:8px;font-size:18px;}\n" +
            ".dt td{padding:12px 16px;border-bottom:1px solid #f1f5f9;vertical-align:top;}\n" +
            ".dt .fl{font-weight:700;color:#4a5568;width:36%;background:#f8fafc;}\n" +
            ".dt .fv{color:#1e293b;}\n" +
            // Diff table
            ".dif{width:100%;border-collapse:collapse;margin-top:10px;font-size:18px;}\n" +
            ".dif th{background:#4a5568;color:#fff;padding:12px 16px;font-size:16px;font-weight:700;text-align:left;}\n" +
            ".dif td{padding:12px 16px;border-bottom:1px solid #f1f5f9;vertical-align:top;}\n" +
            ".dif .fn{font-weight:700;color:#4a5568;background:#f8fafc;width:28%;}\n" +
            ".dif .bef{color:#991b1b;background:#fef2f2;}\n" +
            ".dif .aft{color:#166534;background:#f0fdf4;font-weight:700;}\n" +
            // QR card (pagos)
            ".qr-card{background:linear-gradient(145deg,#dbeafe,#bfdbfe);border:2px solid #93c5fd;border-top:6px solid #1d4ed8;border-radius:12px;padding:22px 30px 18px;text-align:center;margin-bottom:18px;}\n" +
            ".qr-icon{display:block;font-size:48px;line-height:1;margin-bottom:10px;}\n" +
            ".qr-tit{display:block;font-size:22px;font-weight:800;color:#1d4ed8;letter-spacing:2px;text-transform:uppercase;margin-bottom:8px;}\n" +
            ".qr-msg{display:block;font-size:17px;color:#1e40af;}\n" +
            // HELP page
            ".tip{background:#eff6ff;border:2px solid #bfdbfe;border-radius:10px;padding:16px 20px;font-size:18px;color:#1d4ed8;margin-bottom:22px;line-height:1.65;}\n" +
            ".section{margin-bottom:24px;}\n" +
            ".section-title{font-size:17px;font-weight:800;color:#fff;background:#c0392b;padding:10px 16px;border-radius:8px;margin:0 0 8px 0;display:block;}\n" +
            ".alert{padding:16px;border-radius:10px;margin-bottom:16px;font-size:18px;line-height:1.6;}\n" +
            ".alert-error{background:#fef2f2;border:2px solid #fca5a5;color:#991b1b;}\n" +
            "table{width:100%;border-collapse:collapse;font-size:16px;}\n" +
            "th{background:#4a5568;color:#fff;font-weight:700;text-align:left;padding:12px 14px;font-size:14px;text-transform:uppercase;letter-spacing:0.4px;}\n" +
            "td{padding:12px 14px;border-bottom:1px solid #f1f5f9;vertical-align:top;}\n" +
            "tr:last-child td{border-bottom:none;}\n" +
            "tr:nth-child(even) td{background:#f9fafb;}\n" +
            ".cmd{font-family:'Courier New',monospace;font-size:14px;background:#eff6ff;color:#1d4ed8;padding:4px 8px;border-radius:4px;word-break:break-all;display:inline-block;}\n" +
            ".ejemplo{font-family:'Courier New',monospace;font-size:14px;background:#f0fdf4;color:#166534;padding:4px 8px;border-radius:4px;word-break:break-all;display:inline-block;}\n" +
            "code{font-family:'Courier New',monospace;background:#f1f5f9;color:#1d4ed8;padding:2px 6px;border-radius:3px;font-size:15px;}\n";
    }

    public static String envolver(String subtitulo, String contenido) {
        return "<!DOCTYPE html>\n<html>\n<head>\n<meta charset=\"utf-8\">\n<style>\n" +
               css() +
               "</style>\n</head>\n<body>\n" +
               "<div class=\"container\" style=\"max-width:700px;margin:20px auto;background:#fff;border-radius:12px;overflow:hidden;\">\n" +
               "<div class=\"header\" style=\"background:linear-gradient(135deg,#c0392b,#7b241c);padding:32px 24px;text-align:center;color:#fff;\">" +
               "<span style=\"display:block;font-size:48px;line-height:1;margin-bottom:8px;\">&#x1F3CD;&#xFE0F;</span>" +
               "<h1 style=\"margin:0;font-size:32px;font-weight:900;letter-spacing:4px;text-transform:uppercase;color:#fff;\">RAO MOTOS</h1>" +
               "<div style=\"width:48px;height:3px;background:rgba(255,255,255,0.35);margin:12px auto 10px;border-radius:2px;\"></div>" +
               "<p style=\"margin:0;font-size:14px;letter-spacing:0.5px;opacity:0.82;\">" + subtitulo + "</p>" +
               "</div>\n" +
               "<div class=\"content\" style=\"padding:32px 28px;\">" + contenido + "</div>\n" +
               "<div class=\"footer\" style=\"background:#4a5568;padding:18px 24px;text-align:center;font-size:15px;color:#fff;\">" +
               "<strong>Grupo 02 SA &mdash; Tecnolog&iacute;a Web (UAGRM)</strong><br>" +
               "Correo autom&aacute;tico &mdash; no responder directamente." +
               "</div>\n" +
               "</div>\n</body>\n</html>";
    }
}
