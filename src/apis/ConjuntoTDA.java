package apis;

public interface ConjuntoTDA {
    void inicializarConjunto(); // Prepara la estructura.
    boolean conjuntoVacio(); // Indica si el conjunto no tiene elementos.
    void agregar(int x); // Incorpora el elemento si todavía no pertenece.
    int elegir(); // Devuelve algún elemento del conjunto.
    void sacar(int x); // Elimina un elemento.
    boolean pertenece(int x); // Indica si el valor está presente o no.
}