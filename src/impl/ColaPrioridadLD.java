package impl;

import apis.ColaPrioridadTDA;
import impl.nodos.NodoPrioridad;

public class ColaPrioridadLD implements ColaPrioridadTDA {


    NodoPrioridad mayorPrioridad;

    public void inicializarCola() {
        mayorPrioridad = null;
    }

    public void acolarPrioridad(int x, int prioridad) {
        NodoPrioridad nuevo = new NodoPrioridad();
        nuevo.data = x;
        nuevo.prioridad = prioridad;

        // Caso 1: La cola está vacía o el nuevo tiene más prioridad que el primero
        if (mayorPrioridad == null || prioridad < mayorPrioridad.prioridad) {

            nuevo.sig = mayorPrioridad;
            mayorPrioridad = nuevo;

        } else {
            // Caso 2: Buscar el lugar correcto por prioridad (Costo O(n))
            NodoPrioridad aux = mayorPrioridad;
            //System.out.println(aux.prioridad);
            while (aux.sig != null && aux.sig.prioridad <= prioridad) {
                aux = aux.sig;
            }
            nuevo.sig = aux.sig;
            aux.sig = nuevo;
        }
    }

    public void desacolar() {
        mayorPrioridad = mayorPrioridad.sig;
    }

    public int primero() {

        return mayorPrioridad.data;
    }

    public int prioridad() {

        return mayorPrioridad.prioridad;
    }

    public boolean colaVacia() {
        return (mayorPrioridad == null);
    }

}
