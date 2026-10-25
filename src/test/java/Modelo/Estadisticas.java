
package Modelo;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Calcula las estadisticas individuales del usuario
 * utilizando su historial de resultados.
 */
public class Estadisticas {

	// Usuario al que pertenecen las estadisticas
	private final Usuario usuario;

	/**
	 * Constructor que vincula las estadisticas
	 * con un usuario.
	 */
	public Estadisticas(Usuario usuario) {
		this.usuario = Objects.requireNonNull(
				usuario,
				"El usuario es obligatorio"
		);
	}

	/**
	 * Calcula la cantidad total de jugadas.
	 */
	public int calcularTotalJugadas() {
		return usuario.getHistorial().size();
	}

	/**
	 * Calcula la cantidad de victorias.
	 */
	public int calcularVictorias() {

		int victorias = 0;

		for (Resultado resultado : usuario.getHistorial()) {

			if (resultado.isGano()) {
				victorias++;
			}
		}

		return victorias;
	}

	/**
	 * Calcula el porcentaje de victorias.
	 * Evita dividir entre cero.
	 */
	public double calcularPorcentajeVictorias() {

		int totalJugadas = calcularTotalJugadas();

		if (totalJugadas == 0) {
			return 0.0;
		}

		return calcularVictorias() * 100.0
				/ totalJugadas;
	}

	/**
	 * Calcula la mayor cantidad de
	 * victorias consecutivas.
	 */
	public int calcularRachaMaxima() {

		int rachaActual = 0;
		int rachaMaxima = 0;

		for (Resultado resultado : usuario.getHistorial()) {

			if (resultado.isGano()) {

				rachaActual++;

				if (rachaActual > rachaMaxima) {
					rachaMaxima = rachaActual;
				}

			} else {
				rachaActual = 0;
			}
		}

		return rachaMaxima;
	}

	/**
	 * Determina el tipo de apuesta mas utilizado.
	 *
	 * Utiliza la etiqueta de cada apuesta
	 * para no depender de un enum.
	 *
	 * Si existe empate, devuelve el primero
	 * registrado en el historial.
	 *
	 * Si no existen jugadas, devuelve null.
	 */
	public String calcularTipoMasJugado() {

		Map<String, Integer> conteo =
				new LinkedHashMap<>();

		// Cuenta las veces que aparece cada apuesta
		for (Resultado resultado : usuario.getHistorial()) {

			String etiqueta =
					resultado.getApuesta().getEtiqueta();

			conteo.put(
					etiqueta,
					conteo.getOrDefault(etiqueta, 0) + 1
			);
		}

		String masJugado = null;
		int mayorCantidad = 0;

		// Busca la apuesta con mayor frecuencia
		for (Map.Entry<String, Integer> entrada
				: conteo.entrySet()) {

			if (entrada.getValue() > mayorCantidad) {

				mayorCantidad = entrada.getValue();
				masJugado = entrada.getKey();
			}
		}

		return masJugado;
	}
}
