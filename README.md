# Catalog Recovery System

This project is a data processing tool designed to clean, validate, and organize library catalogs containing corrupted or incomplete information. The system transforms raw data from CSV files into a robust object-oriented structure, applying data quality and auditing criteria.

## Architectural and Design Decisions

### 1. Identity and Deduplication Criteria
Unlike conventional data loading systems, this software prioritizes information preservation. A book's identity is defined by the combination of **Title, Author, and Publication Year**.

* **Rationale:** In a context of corrupted data, deleting a record just because the title matches while the author is "Unknown" could lead to losing real physical copies. Only exact duplicates are removed.

### 2. Confidence Indicator
A scoring system (1 to 3) was implemented for each record to track data integrity:

* **Level 3:** Complete record (Valid Title, Author, and Year).
* **Level 2:** Partial record (Title present, but missing Author or Year).
* **Level 1:** Critical record (Only the Title was recovered).

This allows users to manage catalog updates based on priority levels.

### 3. Functional Processing
The **Java Streams API** was utilized to ensure efficient and immutable data processing. By using `.distinct()`, the uniqueness logic was delegated to the model's `equals()` and `hashCode()` methods, keeping the service layer clean and scalable.

### 4. Output Organization
The output is divided into two main blocks to improve the user experience:

* **Records without Author:** Grouped chronologically in descending order.
* **Records by Author:** Organized alphabetically using a `TreeMap` implementation.

## Requirements and Execution

* **JDK:** Ensure you have JDK 22 or higher installed.
* **Data Source:** Place the `books.csv` file in the project's root directory.
* **Configuration:** If using a different file, you can rename it to `books.csv` or edit the `fileName` variable in the `Main.java` file.

### Compilation

```bash
javac -d out src/com/matias/challenge/model/*.java src/com/matias/challenge/service/*.java src/com/matias/challenge/util/*.java src/com/matias/challenge/Main.java
```

### Execution

```bash
java -cp out com.matias.challenge.Main
```

<details>
<summary><d>Click aquí para ver la versión en Español</d></summary>

# Sistema de Recuperación de Catálogo

Este proyecto es una herramienta de procesamiento de datos diseñada para limpiar, validar y organizar catálogos de bibliotecas con información corrupta o incompleta. El sistema transforma datos crudos de archivos CSV en una estructura de objetos robusta, aplicando criterios de calidad de datos y auditoría.

## Decisiones de Arquitectura y Diseño

### 1. Criterio de Identidad y Deduplicación 

A diferencia de un sistema de carga convencional, este software prioriza la preservación de información. Se definió que la identidad de un libro está compuesta por **Título, Autor y Año de publicación**.

* **Razón:** En un contexto de datos corruptos, eliminar un registro porque el título coincide pero el autor es "Unknown" podría causar la pérdida de ejemplares físicos reales. Solo se eliminan duplicados exactos.


### 2. Indicador de Confianza

Se implementó un sistema de puntuación de 1 a 3 para cada registro:

* **Nivel 3:** Registro completo (Título, Autor y Año válidos).
* **Nivel 2:** Registro parcial (Título presente, pero falta Autor o Año).
* **Nivel 1:** Registro crítico (Solo se recuperó el Título).

Esto permite al usuario tener un seguimiento con prioridades.

### 3. Procesamiento Funcional

Se utilizó la **API de Streams de Java** para garantizar un procesamiento eficiente e inmutable.
El uso de `.distinct()` delegó la lógica de unicidad a los métodos `equals()` y `hashCode()` del modelo, manteniendo el servicio limpio y escalable.

### 4. Organización de Salida

La visualización se divide en dos grandes bloques para mejorar la experiencia de usuario:

* **Registros sin Autor:** Agrupados cronológicamente de manera descendiente.
* **Registros por Autor:** Organizados alfabéticamente mediante `TreeMap`.

## Requisitos y Ejecución

**JDK:** Asegúrese de tener instalado el JDK 22 o superior.
**Datos de Origen:** Coloque el archivo ``books.csv`` en la raíz del proyecto.
**Configuración:** En caso de utilizar otro archivo, lo puede renombrar a `books.csv` o editar la variable `fileName` del archivo `Main.java`.

### Compilación

```bash
javac -d out src/com/matias/challenge/model/*.java src/com/matias/challenge/service/*.java src/com/matias/challenge/util/*.java src/com/matias/challenge/Main.java
```

### Ejecución

```bash
java -cp out com.matias.challenge.Main
```
</details>