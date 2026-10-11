package Modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Usuario {
    private final List<Resultado> historial;

    // Constructor: crea al usuario con su historial vacío.
    // Sin inicializar la lista, el primer add daría NullPointerException.
    public Usuario(String nombre, String clave) {
        this.historial = new ArrayList<>();
    }

    // Agrega un resultado al final del historial del usuario.
    // Es la única forma de modificar el historial desde fuera.
    public void agregarResultado(Resultado r) {
        historial.add(r);
    }

    // Devuelve el historial en modo solo lectura.
    // Quien lo consulte puede recorrerlo, pero no agregar ni borrar.
    public List<Resultado> getHistorial() {
        return Collections.unmodifiableList(historial);
    }
}