# TPO Programación II: Sistema de Guardia Médica

## 1. Requerimiento y Objetivo Functional
El sistema surge de la necesidad de gestionar la atención en una **guardia hospitalaria**, donde el orden de llegada no debe ser el único criterio de atención. El **objetivo** es implementar un simulador de triaje médico que permita registrar pacientes y priorizarlos según su nivel de urgencia (Emergencia, Urgente, Consulta), garantizando que los recursos médicos se asignen primero a quienes tienen riesgo de vida.

## 2. Temas de la Cursada Aplicados
Para este proyecto se seleccionaron los siguientes 4 temas exigidos por la cátedra:
1.  **Especificación de TDAs e Interfaces:** Separación total entre el "qué" y el "cómo".
2.  **Implementaciones Dinámicas:** Uso de nodos para gestionar la memoria de forma flexible.
3.  **TDA Cola con Prioridad:** Estructura central para el triaje de pacientes.
4.  **TDA Diccionario y Conjunto:** Para el registro único de identidades (DNI) y nombres.

## 3. Estructura de Carpetas
El proyecto respeta el **Paradigma TDA**, organizando el código en paquetes según su responsabilidad:

```text
src/
│
├── apis/                       # Especificaciones (El "QUÉ" - Interfaces)
│   ├── GuardiaTDA.java         # Interfaz de la lógica de negocio
│   ├── ColaPrioridadTDA.java   # Contrato para el triaje
│   ├── DiccionarioSimpleTDA.java # Contrato para registro de pacientes
│   └── ConjuntoTDA.java        # Validación de unicidad de DNI
│
├── impl/                       # Implementaciones (El "CÓMO" - Clases)
│   ├── ColaPrioridadLD.java    # Lista dinámica con prioridad O(n)
│   ├── DicSimpleL.java         # Diccionario dinámico O(n)
│   ├── ConjuntoLD.java         # Conjunto dinámico para evitar duplicados
│   ├── GuardiaDinamica.java    # Orquestador del sistema
│   └── nodos/                  # Clases base de memoria dinámica
│       ├── Nodo.java           
│       ├── NodoPrioridad.java  
│       └── NodoClave.java      
│
├── model/                      # Objetos de datos (Entidades)
│   └── Paciente.java           # Clase Paciente (DNI, Nombre, Urgencia)
│
└── app/                        # Punto de entrada
    └── MainGuardia.java        # Menú interactivo para el usuario
```

## 4. Justificación Técnica: Los 4 Pilares del Arquitecto
Basándonos en los criterios de la cátedra, justificamos nuestra arquitectura de la siguiente manera:

*   **Programar contra interfaces:** El `Main` interactúa únicamente con interfaces (`GuardiaTDA`, `DiccionarioSimpleTDA`). Esto oculta la implementación de bajo nivel y permite que el sistema sea flexible a cambios futuros (como pasar de memoria dinámica a una base de datos) sin alterar la lógica de la aplicación.
*   **Lo dinámico no siempre es superior:** Elegimos implementaciones dinámicas (nodos) porque el volumen de una guardia es **desconocido y variable**. Si el sistema fuera para un consultorio con 5 turnos fijos, una implementación estática con arreglos sería más eficiente al no sobrecargar el *Garbage Collector* de Java.
*   **Medir el corrimiento:** Al usar la implementación **`ColaPrioridadLD` (Lista Dinámica)**, evitamos el costo de "corrimiento físico" de datos en memoria que sufriría un arreglo (`ColaPU`) al insertar un paciente de máxima urgencia al principio de la fila.
*   **Priorizar según contexto:** El contexto médico exige **Cola con Prioridad**. Una cola común sería inaceptable, ya que respetaría el orden de llegada sobre la gravedad del paciente, poniendo en riesgo vidas humanas.

## 5. Análisis de Complejidad (Big O)
Se analizaron los costos temporales de las operaciones críticas en el peor de los casos:

| Operación | Estructura | Costo Big O | Justificación |
| :--- | :--- | :--- | :--- |
| **Ingresar Paciente** | `ColaPrioridadLD` | **$O(n)$** | Se debe recorrer la lista para encontrar el lugar según prioridad. |
| **Atender Siguiente** | `ColaPrioridadLD` | **$O(1)$** | Se extrae siempre el primer nodo (el de mayor prioridad). |
| **Registrar Nombre** | `DicSimpleL` | **$O(n)$** | Requiere búsqueda lineal de la clave antes de insertar. |
| **Validar Duplicado** | `ConjuntoLD` | **$O(n)$** | Debe verificar que el DNI no "pertenezca" recorriendo los nodos. |

## 6. Escalabilidad y Conclusiones
Gracias al uso de **estructuras dinámicas**, el sistema escala eficientemente en términos de memoria, creciendo solo cuando es necesario. Si bien el costo de búsqueda es lineal (**$O(n)$**), para un sistema de guardia hospitalaria este rendimiento es aceptable frente a la flexibilidad y ahorro de memoria que ofrecen los nodos en comparación con arreglos estáticos de tamaño fijo.
***
<br>

### 1. Justificación de las Estructuras Utilizadas

*   *Cola con Prioridad (Implementación Dinámica):*
    *   *Por qué se usó:* Es la estructura ideal para modelar el *triaje médico*. Permite que los pacientes sean atendidos según la gravedad de su síntoma (Prioridad 1: Emergencia) y no simplemente por su orden de llegada.
    *   *Por qué no otra:* Una *Cola común* sería insuficiente e incluso peligrosa en este contexto, ya que obligaría a un paciente con un infarto a esperar detrás de alguien con un resfrío solo por haber llegado después. Una *Pila* sería totalmente inadecuada por su comportamiento LIFO (último en llegar, primero en salir).

*   *Diccionario Simple (Implementación Dinámica):*
    *   *Por qué se usó:* Para establecer un *índice de búsqueda rápida* que asocie de forma unívoca el DNI (clave) con el Nombre (valor). Esto otorga una identidad semántica a los datos, permitiendo recuperar la información del paciente sin depender de su posición en una lista.
    *   *Por qué no otra:* Un *Arreglo simple* obligaría a buscar al paciente por un índice numérico (posicional) que no tiene relación con su identidad, complicando la lógica del sistema.

*   *Conjunto (Implementación Dinámica):*
    *   *Por qué se usó:* Su propiedad fundamental es la *unicidad de elementos*. Lo utilizamos como un control de integridad para asegurar que un mismo DNI no ingrese dos veces a la fila de espera.
    *   *Por qué no otra:* Cualquier otra estructura permitiría duplicados por defecto, lo que requeriría validaciones manuales menos eficientes y más propensas a errores.

*   *Implementación Dinámica (Nodos) en lugar de Estática (Arreglos):*
    *   *Por qué se usó:* El volumen de pacientes en una guardia es *desconocido y variable. Los nodos permiten que la estructura crezca bajo demanda sin desperdiciar RAM. Además, se cumple el pilar de *"Medir el corrimiento"**: en una lista dinámica, acolar un paciente urgente al principio solo requiere reenganchar punteros ($O(1)$ tras la búsqueda), mientras que en un arreglo obligaría a desplazar físicamente miles de elementos en memoria ($O(n)$).

---

### 2. ¿Por qué no usamos Árboles AVL?

Aunque mencionamos que los Árboles AVL son el siguiente paso para la *escalabilidad*, no los incluimos en esta versión por las siguientes razones técnicas:

1.  *Complejidad vs. Necesidad:* Los AVL garantizan un costo de búsqueda de **$O(\log n)$** gracias a su auto-balanceo. Sin embargo, para el volumen de datos de una guardia hospitalaria típica, el costo lineal **$O(n)$** de nuestras listas dinámicas es aceptable y mucho más simple de implementar y mantener.
2.  *Overhead de Implementación:* Un árbol AVL requiere una lógica mucho más pesada, incluyendo el cálculo de *factores de balance* y la ejecución de *rotaciones (simples y dobles)* cada vez que se inserta o elimina un elemento. Esto añade una carga de procesamiento extra que solo se justifica si tenemos millones de registros.
3.  *Memoria:* Cada nodo de un AVL debe guardar información adicional (como su altura o factor de balance), lo que aumenta el consumo de memoria por cada paciente registrado.
4.  *Criterio del Arquitecto:* Según la *"Regla de Oro"* de la materia, no existe la estructura perfecta, sino la óptima para el requerimiento del negocio. Para este TPO, priorizamos la claridad en el uso de los *4 Pilares* y los TDAs básicos antes que una optimización prematura con estructuras de búsqueda avanzada.

*En resumen:* Usamos estructuras dinámicas lineales porque resuelven el problema del hospital de forma eficiente y flexible, dejando los *AVL* como una evolución futura necesaria solo si el sistema tuviera que gestionar bases de datos masivas a nivel nacional.
