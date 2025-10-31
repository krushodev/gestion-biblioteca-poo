# 📖 Proyecto: Gestión de Biblioteca

Este repositorio documenta el proyecto **Gestión de Biblioteca** , un sistema de software desarrollado como Proyecto Integrador para la materia Programación Orientada a Objetos. Como actividad integradora, su objetivo principal es consolidar y aplicar todos los Resultados de Aprendizaje (RA) adquiridos durante el cuatrimestre en un producto de software funcional. El sistema implementa una solución completa para la administración de préstamos de libros en una biblioteca , gestionando el catálogo, los socios (con reglas de negocio específicas para Estudiantes y Docentes) y el historial de préstamos para generar los informes requeridos.

---

## 🎯 Descripción General

Este software provee una solución para la administración de una biblioteca, permitiendo gestionar el catálogo de libros, los socios (divididos en Estudiantes y Docentes) y el historial de préstamos.

Implementa reglas de negocio específicas para controlar la disponibilidad de los libros y las condiciones de préstamo para cada tipo de socio, asegurando la integridad de los datos y la correcta gestión de vencimientos.

---

## ✨ Funcionalidades Clave

### Gestión de Préstamos y Devoluciones

- **Prestar Libro**: Asigna un libro a un socio, registrando la fecha de retiro.
  - **Reglas para Estudiantes:** Solo pueden solicitar préstamos si tienen menos de 3 libros en su poder y ningún préstamo vencido (límite de 20 días).
  - **Reglas para Docentes:** No tienen límite de cantidad, pero no deben tener préstamos vencidos (límite inicial de 5 días, que puede aumentar).
- **Devolver Libro**: Registra la fecha de devolución en el préstamo, liberando el libro.
- **Manejo de Excepciones**: El sistema es robusto y lanza una `LibroNoPrestadoException` en casos de inconsistencia (ej. devolver un libro que no figura como prestado).

### Consultas e Informes

El sistema puede generar los siguientes reportes por consola:

- Conteo de socios por tipo (Estudiante o Docente).
- Listado de todos los préstamos vencidos a la fecha actual.
- Consulta para saber qué socio tiene un libro específico en su poder.
- Listado de títulos únicos (sin duplicados por ejemplares).
- Listado de "docentes responsables" (aquellos sin historial de devoluciones tardías).

---

## 🏗️ Arquitectura y Diseño

Todo el código fuente se encuentra encapsulado dentro del paquete `biblioteca`. El diseño sigue un modelo de clases que representa las entidades del dominio:

- `Biblioteca`: Clase _Facade_ que centraliza toda la operativa (préstamos, devoluciones, consultas).
- `Socio` (Abstracta): Define el comportamiento y atributos comunes (DNI, nombre, historial de préstamos).
- `Docente`: Subclase de `Socio` con lógica de negocio propia (días de préstamo variables, concepto de "responsable").
- `Estudiante`: Subclase de `Socio` con sus reglas (límite de 3 libros).
- `Libro`: Representa un ejemplar físico con su historial.
- `Prestamo`: Objeto que vincula a un `Socio` con un `Libro` y maneja las fechas.
- `GestionBiblioteca`: Clase ejecutable (con `main`) para demostración y testing.
  ``

---

## 🛠️ Stack Tecnológico

- **Lenguaje:** Java
- **IDE:** BlueJ
- **Documentación:** Javadoc
- **Gestión de Proyecto:** Trello

---

## 🚀 Cómo Ejecutar

El punto de entrada de la aplicación es el método `main` de la clase `GestionBiblioteca`.

Esta clase contiene un conjunto de datos de prueba (mocks) para instanciar la `Biblioteca`, crear Socios y Libros. Al ejecutarse, simula varias operaciones (préstamos, devoluciones) e imprime en consola los resultados de los diferentes informes.

---

## 🌟 Mejoras a Futuro (Roadmap)

El proyecto base puede extenderse con las siguientes funcionalidades:

- **Persistencia de Datos**: Conectar el sistema a una base de datos o a archivos (JSON/XML) para guardar el estado.
- **Interfaz Gráfica (GUI)**: Desarrollar un frontend de escritorio (usando JavaFX o Swing) para una operación más amigable.
