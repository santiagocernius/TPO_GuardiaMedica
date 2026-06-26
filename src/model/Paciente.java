package model;

public class Paciente {
    public int dni;
    public String nombre;
    public int nivelUrgencia; // 1: Emergencia, 2: Urgente, 3: Consulta

    public Paciente(int dni, String nombre, int nivelUrgencia) {
        this.dni = dni;
        this.nombre = nombre;
        this.nivelUrgencia = nivelUrgencia;
    }
}