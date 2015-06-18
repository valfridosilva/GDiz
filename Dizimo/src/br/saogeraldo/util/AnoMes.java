package br.saogeraldo.util;

public class AnoMes {
	
	private int anoMes;
	private int ano;
	private int mes;
	
	public AnoMes(Integer anoMes) {
		if(anoMes != null && anoMes.toString().length() == 6){
			this.anoMes = anoMes;
			this.ano = Integer.parseInt(anoMes.toString().substring(0,4));
			this.mes = Integer.parseInt(anoMes.toString().substring(4));
		}
	}
	
	public int getAnoMes() {
		return anoMes;
	}
	public int getAno() {
		return ano;
	}
	public int getMes() {
		return mes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + anoMes;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AnoMes other = (AnoMes) obj;
		if (anoMes != other.anoMes)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		if(this.getMes() < 10){
			return "0"+this.getMes()+"/"+this.getAno();
		}
		return this.getMes()+"/"+this.getAno();
	}
	
}
