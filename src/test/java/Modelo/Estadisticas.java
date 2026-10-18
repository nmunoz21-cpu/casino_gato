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
     // calcula el porcentaje de victoria del usuario
	public double calcularPorcentajeVictorias() {
		int Totaljugadas =calcularTotalJugadas();
		if (Totaljugadas==0){
			return 0.0;
		}
		return (calcularVictorias())* 100.0/ Totaljugadas;
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