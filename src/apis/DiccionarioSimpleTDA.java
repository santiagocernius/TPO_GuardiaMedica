package apis;

public interface DiccionarioSimpleTDA {

    void inicializarDiccionario();
    void agregar(int clave, String valor); // DNI como clave
    void eliminar(int clave);
    String recuperar(int clave);
    ConjuntoTDA claves(); // Necesitaremos también la interfaz ConjuntoTDA
}