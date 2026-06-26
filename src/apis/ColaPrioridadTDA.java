package apis;

public interface ColaPrioridadTDA {
    void inicializarCola();
    void acolarPrioridad(int x, int prioridad); // El "x" será el DNI del paciente
    void desacolar(); // Elimina al de mayor prioridad
    int primero();    // Devuelve el DNI del más prioritario
    int prioridad();  // Devuelve la prioridad del más prioritario
    boolean colaVacia();
}