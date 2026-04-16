## Sistema de Recuperación de Catálogo
Este proyecto es una herramienta de procesamiento de datos diseñada para limpiar, validar y organizar catálogos de bibliotecas con información corrupta o incompleta. El sistema transforma datos crudos de archivos CSV en una estructura de objetos robusta, aplicando criterios de calidad de datos y auditoría.

### Decisiones de Arquitectura y Diseño
#### 1. Criterio de Identidad y Deduplicación 

A diferencia de un sistema de carga convencional, este software prioriza la preservación de información. Se definió que la identidad de un libro está compuesta por Título, Autor y Año de publicación.

* Razón: En un contexto de datos corruptos, eliminar un registro porque el título coincide pero el autor es "Unknown" podría causar la pérdida de ejemplares físicos reales. Solo se eliminan duplicados exactos.


#### 2. Indicador de Confianza

Se implementó un sistema de puntuación de 1 a 3 para cada registro:

* Nivel 3: Registro completo (Título, Autor y Año válidos).
* Nivel 2: Registro parcial (Título presente, pero falta Autor o Año).
* Nivel 1: Registro crítico (Solo se recuperó el Título).

Esto permite al usuario tener un seguimiento con prioridades.

#### 3. Procesamiento Funcional
Se utilizó la API de Streams de Java para garantizar un procesamiento eficiente e inmutable.
El uso de .distinct() delegó la lógica de unicidad a los métodos equals() y hashCode() del modelo, manteniendo el servicio limpio y escalable.

#### 4. Organización de Salida
La visualización se divide en dos grandes bloques para mejorar la experiencia de usuario:

* Registros sin Autor: Agrupados cronológicamente de manera descendiente.
* Registros por Autor: Organizados alfabéticamente mediante TreeMap.

#### Requisitos y Ejecución
Asegúrese de tener instalado el JDK 22 o superior.

Coloque el archivo books.csv en la raíz del proyecto.
En caso de utilizar otro archivo, lo puede renombrar a "books.csv" o editar la variable fileName del archivo Main.java.

Compile el proyecto:
`javac -d out src/com/matias/challenge/model/*.java src/com/matias/challenge/service/*.java src/com/matias/challenge/util/*.java src/com/matias/challenge/Main.java`

Ejecute la aplicación:
`java -cp out com.matias.challenge.Main`