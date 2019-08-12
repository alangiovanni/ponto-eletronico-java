package classes;

public class PontoDiario {
	
	String nome;
	String horaEntrada;
	String horaSaida;
	String assinatura;
	String lotacao;
	int dia;
	int mes;
	int ano;
	
	public PontoDiario(String fimDeSemana, int dia){
		this.nome = "";
		this.horaEntrada = fimDeSemana;
		this.dia = dia;
		this.horaSaida = fimDeSemana;
	}
	
	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getHoraEntrada() {
		return horaEntrada;
	}

	public void setHoraEntrada(String horaEntrada) {
		this.horaEntrada = horaEntrada;
	}

	public String getHoraSaida() {
		return horaSaida;
	}

	public void setHoraSaida(String horaSaida) {
		this.horaSaida = horaSaida;
	}
	
	public String getAssinatura() {
		return assinatura;
	}

	public void setAssinatura(String assinatura) {
		this.assinatura = assinatura;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}
	public void setLotacao(String lotacao){
		this.lotacao = lotacao;
	}
	public String getLotacao() {
		return lotacao;
	}

	@Override
	public String toString() {
		return "PontoDiario [nome=" + nome + ", horaEntrada=" + horaEntrada + ", horaSaida=" + horaSaida
				+ ", assinatura=" + assinatura + ", lotacao=" + lotacao + ", dia=" + dia + ", mes=" + mes + ", ano="
				+ ano + "]";
	}
}
