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
| **Registrar Nombre** | `DicSimpleL` | **$O(n)$** | Requiere búsqueda lineal de la clave antes de insertar. |
| **Atender Siguiente** | `ColaPrioridadLD` | **$O(1)$** | Se extrae siempre el primer nodo (el de mayor prioridad). |
| **Validar Duplicado** | `ConjuntoLD` | **$O(n)$** | Debe verificar que el DNI no "pertenezca" recorriendo los nodos. |

## 6. Escalabilidad y Conclusiones
Gracias al uso de **estructuras dinámicas**, el sistema escala eficientemente en términos de memoria, creciendo solo cuando es necesario. Si bien el costo de búsqueda es lineal (**$O(n)$**), para un sistema de guardia hospitalaria este rendimiento es aceptable frente a la flexibilidad y ahorro de memoria que ofrecen los nodos en comparación con arreglos estáticos de tamaño fijo.