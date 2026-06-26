package impl;

import apis.GuardiaTDA;
import apis.ColaPrioridadTDA;

public class GuardiaDinamica implements GuardiaTDA {
    ColaPrioridadTDA cola;

    public void inicializar() {
        cola = new ColaPrioridadLD();
        cola.inicializarCola();
    }

    public void ingresarPaciente(int dni, int prioridad) {
        cola.acolarPrioridad(dni, prioridad);
    }

    public void atenderSiguiente() {
        cola.desacolar();
    }

    public int proximoDNI() {
        return cola.primero();
    }

    public int proximaPrioridad() {
        return cola.prioridad();
    }

    public boolean hayPacientes() {
        return !cola.colaVacia();
    }
}
