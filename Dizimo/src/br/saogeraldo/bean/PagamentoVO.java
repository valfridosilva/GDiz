package br.saogeraldo.bean;

public class PagamentoVO {
private int idPagamento;
private int cdDizimista;
private int cdAno;
private int cdMes;
private String dataPagamento;
private double valor;

public int getIdPagamento() {
	return idPagamento;
}
public void setIdPagamento(int idPagamento) {
	this.idPagamento = idPagamento;
}
public int getCdDizimista() {
	return cdDizimista;
}
public void setCdDizimista(int cdDizimista) {
	this.cdDizimista = cdDizimista;
}
public int getCdAno() {
	return cdAno;
}
public void setCdAno(int cdAno) {
	this.cdAno = cdAno;
}
public int getCdMes() {
	return cdMes;
}
public void setCdMes(int cdMes) {
	this.cdMes = cdMes;
}
public String getDataPagamento() {
	return dataPagamento;
}
public void setDataPagamento(String dataPagamento) {
	this.dataPagamento = dataPagamento;
}
public double getValor() {
	return valor;
}
public void setValor(double valor) {
	this.valor = valor;
}

}
