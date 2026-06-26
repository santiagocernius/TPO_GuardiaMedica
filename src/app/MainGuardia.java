package app;

import java.util.Scanner;
import apis.*;
import impl.*;

public class MainGuardia {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        GuardiaTDA hospital = new GuardiaDinamica();
        DiccionarioSimpleTDA nombres = new DicSimpleL();
        ConjuntoTDA dniEnEspera = new ConjuntoLD();

        
        hospital.inicializar();
        nombres.inicializarDiccionario();
        dniEnEspera.inicializarConjunto(); 

        int opcion = 0;
        do {
            System.out.println("\n--- SIMULADOR DE GUARDIA HOSPITALARIA ---");
            System.out.println("1. Ingresar Paciente (Triaje)");
            System.out.println("2. Atender Próximo Paciente (Por Urgencia)");
            System.out.println("3. Ver Paciente en Espera");
            System.out.println("4. Salir");
            System.out.print("Selección: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1: // Ingreso
                    System.out.print("DNI del paciente: ");
                    int dni = sc.nextInt();
                    if (dniEnEspera.pertenece(dni)) {
                        System.out.println("ERROR: El paciente con DNI " + dni + " ya se encuentra en espera.");
                    } else {
                        sc.nextLine(); // Limpieza de buffer

                        System.out.print("Nombre completo: ");
                        String nombre = sc.nextLine();

                        System.out.println("Prioridad (1: Emergencia, 2: Urgente, 3: Consulta): ");
                        int prioridad = sc.nextInt();

                        hospital.ingresarPaciente(dni, prioridad);
                        nombres.agregar(dni, nombre);
                        dniEnEspera.agregar(dni); // Lo marcamos como "en espera" 
                        System.out.println("Paciente " + nombre + " ingresado al sistema.");
                        
                    }
                    break;

                case 2: // Atender
                    if (hospital.hayPacientes()) {
                        int dniAtendido = hospital.proximoDNI();
                        String nombreAtendido = nombres.recuperar(dniAtendido); 

                        System.out.println("ATENDIENDO AHORA PACIENTE: " + nombreAtendido);
                        System.out.println("DNI: " + dniAtendido);

                        hospital.atenderSiguiente();
                        dniEnEspera.sacar(dniAtendido);

                    } else {
                        System.out.println("No hay pacientes esperando.");
                    }
                    break;

                case 3:
                    if (hospital.hayPacientes()) {
                        int dniAtendido = hospital.proximoDNI();
                        String nombreAtendido = nombres.recuperar(dniAtendido);
                        System.out.println("En espera: "+ nombreAtendido + 
                                            " | DNI " + dniAtendido + 
                                            " | Urgencia nivel: " + hospital.proximaPrioridad());
                    } else {
                        System.out.println("Guardia vacía.");
                    }
                    break;
            }
        } while (opcion != 4);
        
        System.out.println("Cerrando sistema de guardia...");
        sc.close();
    }
}