package apis;

// Interfaz para la atención (Cola con Prioridad)
public interface GuardiaTDA {

    void inicializar();
    void ingresarPaciente(int dni, int prioridad); // acolarPrioridad()
    void atenderSiguiente(); // desacolar()
    int proximoDNI(); // primero()
    int proximaPrioridad(); // prioridad()
    boolean hayPacientes(); // colaVacia()
}
