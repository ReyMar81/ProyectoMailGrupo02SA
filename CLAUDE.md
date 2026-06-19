# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Email-driven business system for "RAO MOTOS" (motorbike shop). Users interact entirely by sending emails to `grupo02sa@tecnoweb.org.bo` with commands in the **Subject** line. The server polls POP3 every 10 seconds, processes commands, queries PostgreSQL, and replies via SMTP.

Requires access to `mail.tecnoweb.org.bo` (university network or VPN) for both email and database.

## Commands

```bash
# Compile
mvn clean compile

# Run interactive menu (BD test / CRUD test / start email service)
mvn exec:java

# Start email polling service directly (skip menu)
mvn exec:java -Dexec.args="email"

# Create all DB tables from database_schema.sql
mvn clean compile exec:java -Dexec.mainClass="org.mailgrupo02.CrearTablas"

# Build fat JAR with all dependencies
mvn package
```

## Setup

Copy `.env.example` to `.env` in the project root. The actual credentials (including passwords) are in `Credentials.txt` (not committed). `EnvLoader` reads `.env` from the current working directory; falls back to `System.getenv()`.

Required `.env` keys: `SMTP_HOST`, `SMTP_PORT`, `EMAIL_USER`, `EMAIL_PASSWORD`, `EMAIL_ADDRESS`, `DB_URL`, `DB_USER`, `DB_PASSWORD`.

## Architecture

### Layer naming conventions
- `*M.java` — Model/DAO: raw JDBC `PreparedStatement` queries, one class per DB table
- `*N.java` — DTO (data transfer object) for a business domain
- `*Service.java` — Business logic; all methods return a formatted `String` (the email reply body)
- `*Validator.java` — Input validation helpers used by services

### Request flow
```
ClientePOP (reads emails) 
  → Main.ServicioEmail.procesarCorreo()
    → ComandoEmailNuevo.evaluarYEjecutar(subject)  ← regex dispatch on all commands
      → *Service methods (validate + call *M DAOs)
        → Conexion.conectar() (PostgreSQL JDBC singleton)
  → ClienteSMTP.enviarCorreo(from, subject, response)
```

### Key files
| File | Role |
|---|---|
| `Main.java` | Entry point; interactive menu + `"email"` arg to skip to service |
| `servicioemail/ComandoEmailNuevo.java` | Central command router — all Subject patterns matched with regex here |
| `servicioemail/ClientePOP.java` / `ClienteSMTP.java` | POP3 reader / SMTP sender |
| `sistema/conexion/Conexion.java` | JDBC singleton for PostgreSQL |
| `Database/EnvLoader.java` | Reads `.env` file; used by `ConfigEmailServer` and `Conexion` |
| `database_schema.sql` | Full DDL for all 15 tables |
| `seed_data.sql` | Sample data for development |

### Domain organization under `sistema/negocio/`
Each subdomain (`usuarios`, `productos`, `ventas`, `compras`, `pedidos`, `inventario`, `pagos`) follows the same structure: DTO (`*N`), service (`*Service`), optional validator (`*Validator`).

### Database schema summary
- **Inheritance pattern**: `usuario` (base) → `cliente` / `proveedor` / `propietario` (shared PK, ON DELETE CASCADE)
- **Sales flow**: `venta` → `detalle_venta` (line items); if `tipo_venta = 'CREDITO'`, creates `credito` (1:1) → `pago_cuota` (N installments auto-generated)
- **Inventory**: `inventario` (one row per product, tracks stock) + `movimiento_inventario` (audit log of every change)

## Command format

```
COMANDO[param1,param2,...]
```

Parameters are comma-separated; `*` means "all". Date format for ventas: `2026-06-05T10:00:00`. Send `HELP` as Subject to receive the full command list by email.

## Known gaps / pending work

Role-based access control is **not yet implemented** — any sender can run any command. The README contains a detailed plan for adding it (modify `procesarCorreo` → `ComandoEmailNuevo.evaluarYEjecutar` to accept sender email + rol, add `UsuarioService.buscarRolPorEmail()`, define permission matrix).
