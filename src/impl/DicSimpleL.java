package impl;

import apis.ConjuntoTDA;
import apis.DiccionarioSimpleTDA;
import impl.nodos.NodoClave;

public class DicSimpleL implements DiccionarioSimpleTDA {
    
    NodoClave origen;

    public void inicializarDiccionario() {
        origen = null;
    }

    @Override
    public void agregar(int clave, String valor) {
        
        NodoClave nc = buscarNodoClave(clave);

        if (nc == null) {
            // Si no existe, creamos un nodo nuevo al principio (Costo o(1) la inserción)
            nc = new NodoClave();
            nc.clave = clave;
            nc.sig = origen;
            origen = nc;
        }
        // Si ya existe o es nuevo, actualizamos el valor (sobrescribe)
        nc.valor = valor;
    }

    public void eliminar(int clave) {

        if (origen != null) {
            if (origen.clave == clave) {
                origen = origen.sig;
            } else {
                NodoClave aux = origen;
                while (aux.sig != null && aux.sig.clave != clave) {
                    aux = aux.sig;
                }
                if (aux.sig != null) {
                    aux.sig = aux.sig.sig;
                }
            }
        }
    }


    public String recuperar(int clave) {
        NodoClave nc = buscarNodoClave(clave);
        return nc.valor; // Precondición: la clave debe existir
    }

    public ConjuntoTDA claves() {
        ConjuntoTDA c = new ConjuntoLD(); 
        
        c.inicializarConjunto();
        NodoClave aux = origen;
        while (aux != null) {
            c.agregar(aux.clave);
            aux = aux.sig;
        }
        return c;
    }

    // Método auxiliar privado para no repetir código de búsqueda
    private NodoClave buscarNodoClave(int clave) {

        NodoClave aux = origen;
        while (aux != null && aux.clave != clave) {
            aux = aux.sig;
        }
        return aux;
    }
}
