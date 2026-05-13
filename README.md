# Bank API 

API REST desarrollada con Java y Spring Boot para la administración de clientes, cuentas bancarias y transacciones financieras.

## Tecnologías utilizadas

* Java 21
* Spring Boot 3
* Spring Data JPA
* PostgreSQL
* Maven
* Lombok
* Swagger
* JUnit 5

---

# Descripción del proyecto

Esta aplicación permite gestionar clientes de una entidad financiera mediante operaciones CRUD y realizar movimientos financieros entre cuentas.

El sistema incluye:

* Gestión de clientes
* Gestión de cuentas bancarias
* Consignaciones
* Retiros
* Transferencias entre cuentas
* Validaciones de negocio
* Persistencia en PostgreSQL
* Arquitectura MVC
* API REST

---

# Arquitectura

El proyecto fue desarrollado utilizando el patrón MVC (Model - View - Controller) y organizado por capas:

```text
controller
service
repository
entity
dto
exception
config
```

---

# Estructura del proyecto

```text
src/main/java/com/felipe/bankapi

├── controller
├── service
├── repository
├── entity
├── dto
├── exception
├── config
└── util
```

---

# Funcionalidades principales

## Clientes

* Crear cliente
* Actualizar cliente
* Eliminar cliente
* Consultar clientes

## Cuentas

* Crear cuenta de ahorros
* Crear cuenta corriente
* Activar/Inactivar cuentas
* Cancelar cuentas

## Transacciones

* Consignaciones
* Retiros
* Transferencias

---

# Reglas de negocio implementadas

* No se permiten clientes menores de edad
* No se puede eliminar un cliente con cuentas asociadas
* Las cuentas de ahorro no pueden tener saldo negativo
* Las transferencias solo se realizan entre cuentas existentes
* Las cuentas se generan automáticamente
* El saldo se actualiza automáticamente en cada transacción

---

# Testing

El proyecto incluye pruebas unitarias para:

* Services
* Controllers

Utilizando:

* JUnit 5

---
