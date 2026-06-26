package impl;

import apis.ConjuntoTDA;
import impl.nodos.Nodo;

public class ConjuntoLD implements ConjuntoTDA {
    Nodo c;

    public void inicializarConjunto() {
        c = null;
    }

    public boolean conjuntoVacio() {
        return (c == null);
    }

    public void agregar(int x) {
        if (!this.pertenece(x)) {
            Nodo nuevo = new Nodo();
            nuevo.data = x;
            nuevo.sig = c;
            c = nuevo;
        }
    }

    public int elegir() {
        return c.data; // Retorna el primero por simplicidad
    }

    public void sacar(int x) {
        if (c != null) {
            if (c.data == x) {
                c = c.sig;
            } else {
                Nodo aux = c;
                while (aux.sig != null && aux.sig.data != x) {
                    aux = aux.sig;
                }
                if (aux.sig != null) {
                    aux.sig = aux.sig.sig;
                }
            }
        }
    }

    public boolean pertenece(int x) {
        Nodo aux = c;
        while (aux != null && aux.data != x) {
            aux = aux.sig;
        }
        return (aux != null);
    }
}