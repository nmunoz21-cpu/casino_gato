package org.example;

import java.lang.classfile.instruction.SwitchCase;
import java.util.Random;
import java.util.Scanner;

public class Ruleta {

    public static final int MAX_HISTORIAL = 100;
    public static int[] historialNumeros = new int[MAX_HISTORIAL];
    public static int[] historialApuestas = new int[MAX_HISTORIAL];
    public static boolean[] historialAciertos = new boolean[MAX_HISTORIAL];
    public static int historialSize = 0;
    public static Random rng = new Random();
    public  static  final int CANTIDAD_NUMEROS= 37;

    public static int[] numerosRojos = {
            1, 3, 5, 7, 9, 12, 14, 16, 18,
            19, 21, 23, 25, 27, 30, 32, 34, 36
    };

    /**
     * Método principal: inicia el programa llamando al menú.
     */
    public static void main(String[] args) {

        menu();
    }

    /**
     * Controla el flujo principal del programa mostrando
     * un menú en consola.
     */
    public static void menu() {
        Scanner sc=new Scanner(System.in);
       int opcion= 0;
        do{
            mostrarMenu();
            opcion=leerOpcion(sc);
            ejecutarOpcion(opcion,sc );
        }while (opcion!=3);


    }

    /**
     * Muestra en consola las opciones disponibles del menú.
     */
    public static void mostrarMenu() {
        System.out.println("MENU");
        System.out.println("1) INICIO DE RONDA ");
        System.out.println("2) VER ESTADISTICAS ");
        System.out.println("3) SALIR ");
    }

    /**
     * Lee la opción elegida por el usuario desde teclado.
     *
     * @param in Scanner para entrada por consola.
     * @return número de opción ingresado.
     */
    public static int leerOpcion(Scanner in) {
        // TODO: Leer y retornar la opción ingresada.
        return in.nextInt();
    }

    /**
     * Ejecuta la acción correspondiente a la opción del menú.
     *
     * @param opcion opción elegida por el usuario.
     * @param in Scanner para entrada por consola.
     */
    public static void ejecutarOpcion(int opcion, Scanner in) {
        switch (opcion) {
            case 1:
                iniciarRonda(in);
                break;
            case 2:
                mostrarEstadisticas();
                break;
            case 3:
                System.out.printf("ADIOS, VUELVE PRONTO");
                break;
            default:
                System.out.println("Opcion invalidad, intenta de nuevo");
        }

        // TODO: Ejecutar la acción asociada a la opción.
    }

    /**
     * Inicia una ronda de la ruleta: leer apuesta, girar,
     * evaluar y mostrar resultado.
     *
     * @param in Scanner para entrada por consola.
     */
    public static void iniciarRonda(Scanner in) {
        System.out.println("INGRESE SU APUESTA:");
        int apuesta=in.nextInt();
        // TODO: Implementar el flujo completo de una ronda.
    }

    /**
     * Permite al usuario seleccionar el tipo de apuesta
     * (R/N/P/I).
     *
     * @param in Scanner para entrada por consola.
     * @return el tipo de apuesta elegido.
     */
    public static char leerTipoApuesta(Scanner in) {
        System.out.println("SELECCIONE APUESTA");    // TODO: Leer y validar el tipo de apuesta
        System.out.println("(ROJO(R)/NEGRO(N)/PAR(P)/IMPAR(I))");
        return in.next().charAt(0);
    }

    /**
     * Simula el giro de la ruleta generando un número
     * aleatorio de 0 a 36.
     *
     * @return número de la ruleta.
     */
    public static int girarRuleta() {
        return rng.nextInt(CANTIDAD_NUMEROS);
    }// TODO: Generar y retornar un número entre 0 y 36.

    /**
     * Evalúa si la apuesta realizada por el jugador
     * fue acertada.
     *
     * @param numero número obtenido en la ruleta.
     * @param tipo tipo de apuesta elegida.
     * @return true si acertó, false si perdió.
     */
    public static boolean evaluarResultado(int numero, char tipo) {
        // TODO: Evaluar el resultado según el tipo de apuesta.
        return false;
    }

    /**
     * Determina si un número corresponde a color rojo.
     *
     * @param n número de la ruleta.
     * @return true si es rojo, false en caso contrario.
     */
    public static boolean esRojo(int n) {
        for (int i =0; i< numerosRojos.length; i++){                      /*el .lenght cuenta la cantidad de elementos de un arreglo*/
            if (n==numerosRojos[i]){
                return true;  /*verifica si es rojo con booleana*/
            }
        }
        return false;
    }

    /**
     * Registra los resultados de la ronda en los arreglos
     * de historial.
     *
     * @param numero número obtenido en la ruleta.
     * @param apuesta monto apostado.
     * @param acierto si el jugador acertó o no.
     */
    public static void registrarResultado(int numero, int apuesta, boolean acierto) {
        // TODO: Guardar los datos sin superar MAX_HISTORIAL.
    }

    /**
     * Muestra en consola el resultado de la ronda.
     *
     * @param numero número obtenido en la ruleta.
     * @param tipo tipo de apuesta realizada.
     * @param monto monto apostado.
     * @param acierto si el jugador ganó o perdió.
     */
    public static void mostrarResultado(int numero, char tipo, int monto, boolean acierto) {
        // TODO: Mostrar los datos y el resultado de la ronda.
    }

    /**
     * Muestra estadísticas generales de todas las
     * rondas jugadas.
     */
    public static void mostrarEstadisticas() {
        // TODO: Calcular y mostrar las estadísticas acumuladas.
    }
}