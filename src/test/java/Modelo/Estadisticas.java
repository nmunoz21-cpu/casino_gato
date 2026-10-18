package Modelo;
import java.util.Map;
import java.util.EnumMap;

public class Estadisticas {
	private final Usuario usuario;
	//constructor
	public Estadisticas(Usuario usuario) {
		this.usuario = usuario;
	}

	public int calcularTotalJugadas() {
		// TODO - implement Estadisticas.calcularTotalJugadas
		return usuario.getHistorial().size();
	}
	// Calcula la cantidad de apuestas ganadas por el usuario
	public int calcularVictorias() {
		int victorias=0;
		for (Resultado resultado: usuario.getHistorial()){
			if(resultado.isGano()){

				victorias++;
			}
		}
		return victorias;
	}
     // calcula el porcentaje de victoria del usuario
	public double calcularPorcentajeVictorias() {
		int Totaljugadas =calcularTotalJugadas();
		if (Totaljugadas==0){
			return 0.0;
		}
		return (calcularVictorias())* 100.0/ Totaljugadas;
	}

	// Calcula la mayor racha de victorias consecutivas
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

	// Calcula el tipo de apuesta más utilizado por el usuario
	public TipoApuesta calcularTipoMasJugado() {

		Map<TipoApuesta, Integer> conteo = new EnumMap<>(TipoApuesta.class);

		for (Resultado resultado : usuario.getHistorial()) {

			TipoApuesta tipo = resultado.getTipoApuesta();

			conteo.put(tipo, conteo.getOrDefault(tipo, 0) + 1);
		}

		TipoApuesta masJugado = null;
		int mayorCantidad = 0;

		for (TipoApuesta tipo : TipoApuesta.values()) {

			int cantidad = conteo.getOrDefault(tipo, 0);

			if (cantidad > mayorCantidad) {
				mayorCantidad = cantidad;
				masJugado = tipo;
			}
		}

		return masJugado;
	}

}