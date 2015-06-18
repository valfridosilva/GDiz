package br.saogeraldo.bean;

import java.util.Date;

import br.saogeraldo.util.AnoMes;

public class FinanceiroVO implements Comparable<FinanceiroVO>{
	
	private int idFinanceiro;
	private int idDizimista;
	private Double valor;
	private Date dataLancamento;
	private AnoMes anoMesReferencia;
	
	public int getIdFinanceiro() {
		return idFinanceiro;
	}
	public void setIdFinanceiro(int idFinanceiro) {
		this.idFinanceiro = idFinanceiro;
	}
	public int getIdDizimista() {
		return idDizimista;
	}
	public void setIdDizimista(int idDizimista) {
		this.idDizimista = idDizimista;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Date getDataLancamento() {
		return dataLancamento;
	}
	public void setDataLancamento(Date dataLancamento) {
		this.dataLancamento = dataLancamento;
	}
	public void setAnoMesReferencia(AnoMes anoMesReferencia) {
		this.anoMesReferencia = anoMesReferencia;
	}
	public AnoMes getAnoMesReferencia() {
		return anoMesReferencia;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((anoMesReferencia == null) ? 0 : anoMesReferencia.hashCode());
		result = prime * result + idDizimista;
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
		FinanceiroVO other = (FinanceiroVO) obj;
		if (anoMesReferencia == null) {
			if (other.anoMesReferencia != null)
				return false;
		} else if (!anoMesReferencia.equals(other.anoMesReferencia))
			return false;
		if (idDizimista != other.idDizimista)
			return false;
		return true;
	}
	@Override
	public int compareTo(FinanceiroVO o) {
		return new Integer(o.getAnoMesReferencia().getAnoMes()).
				compareTo(new Integer(this.getAnoMesReferencia().getAnoMes()));
	}
	
}
