package Modelo;

public class Estadisticas {
	private final Usuario usuario;

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

	public double calcularPorcentajeVictorias() {
		// TODO - implement Estadisticas.calcularPorcentajeVictorias
		throw new UnsupportedOperationException();
	}

	public int calcularRachaMaxima() {
		// TODO - implement Estadisticas.calcularRachaMaxima
		throw new UnsupportedOperationException();
	}

	public TipoApuesta calcularTipoMasJugado() {
		// TODO - implement Estadisticas.calcularTipoMasJugado
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param usuario
	 */
	public Estadisticas(Usuario usuario) {
		// TODO - implement Estadisticas.Estadisticas
		this.usuario = usuario;
	}

}