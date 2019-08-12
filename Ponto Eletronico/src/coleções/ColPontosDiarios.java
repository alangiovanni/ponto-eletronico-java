package coleções;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

import classes.PontoDiario;

public class ColPontosDiarios {
	private ArrayList<PontoDiario> colPontosDiarios = new ArrayList<PontoDiario>();
	
	public ColPontosDiarios(){
		
	}
	public ArrayList<PontoDiario> retornaLista(){
		return colPontosDiarios;
	}
	public void adicionaPontoDiario(PontoDiario novoPonto){
		colPontosDiarios.add(novoPonto);
	}
	
	//TODOS OS METODOS SET ALL ... É PARA O JASPERVIEW NÃO BUGAR NO MOMENTO DE SETAR A VARIÁVEL COM AS INFORMAÇÕES SOLICITADAS
	public void setAllNomes(String nome, String assinatura){
		//Adiciona em todos os pontos batidos pelo usuario o nome dele na variável nome;
		for(PontoDiario ponto: colPontosDiarios){
			ponto.setNome(nome);
			if(ponto.getHoraEntrada().equals("AUSENTE") || ponto.getHoraEntrada().equals("SABADO") || ponto.getHoraEntrada().equals("DOMINGO") || ponto.getHoraEntrada().equals("")){
				ponto.setAssinatura("");
			}
			else{
				ponto.setAssinatura(assinatura);
			}
		}
	}
	
	public void setAllMesAno(int mes, int ano){
		//Adiciona em todos os pontos batidos pelo usuario o nome dele na variável nome;
		for(PontoDiario ponto: colPontosDiarios){
			ponto.setMes(mes);
			ponto.setAno(ano);
		}
	}
	
	public void setAllLotacao(String lotacao){
		for(PontoDiario ponto: colPontosDiarios)
			ponto.setLotacao(lotacao);
	}
	
	//EM TESTES
	public void ordenaLista(ArrayList<PontoDiario> listaAtualizada, String localArmazenamento, int mes, String ano){
		int QtdeDeDiasDaDataRecebida = retornaQtdeDeDiasDoMes(mes,Integer.valueOf(ano));
		ArrayList<PontoDiario> listaOrdenada = new ArrayList<PontoDiario>();
		
		for(int i = 1; i <= QtdeDeDiasDaDataRecebida; i++){
			for(PontoDiario ponto: listaAtualizada){
				if(ponto.getDia() == i){
					listaOrdenada.add(ponto);
				}
			}
		}
		colPontosDiarios = listaOrdenada;
	}
	
	public void removePontoDiario(PontoDiario ponto){
		colPontosDiarios.remove(ponto);
	}
	
	public PontoDiario retornaUsuarioPelaData(int dia){
		for(PontoDiario user: colPontosDiarios) {
			if(user.getDia() == dia) {
				return user;
			}
		}
		return null;
	}
	
	public int retornaTamanho(){
		return colPontosDiarios.size();
	}
	
	private void atualizaColecao(ArrayList<PontoDiario> listaAtualizada, String fimDeSemana, int dia){
		for(PontoDiario user: listaAtualizada) {
			if(user.getDia() == dia) {
				return;
			}
		}
		PontoDiario novoPonto = new PontoDiario(fimDeSemana, dia);
		listaAtualizada.add(novoPonto);
	}
	
	//ATUALIZA A COLEÇÃO PASSANDO OS SABADOS, DOMINGOS E FALTAS DO USUARIO
	public ArrayList<PontoDiario> popularColecao(int mes, String ano){
		 int i;
		 int QtdeDeDiasDaDataRecebida = retornaQtdeDeDiasDoMes(mes,Integer.valueOf(ano));
		 Calendar calendario = Calendar.getInstance();
		 String fimDeSemana = null;
		 ArrayList<PontoDiario> listaAtualizada = new ArrayList<PontoDiario>();
		 listaAtualizada = colPontosDiarios;
		 
		 for (i=1; i<=QtdeDeDiasDaDataRecebida; i++) {
			//ATRIBUINDO O DIA AO CALENDARIO SALVO PARA VERFICAR SE É DIA DE SEMANA SE NÃO FOR EU PREENCHO COM O DIA CORRESPONDENTE
			 calendario.set(Calendar.DAY_OF_MONTH, i);
			 //Campos em branco
			 fimDeSemana = "";
			 if(calendario.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
				 fimDeSemana="DOMINGO";
			 else{
				 if(calendario.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
					 fimDeSemana = "SABADO";
			 }
				 atualizaColecao(listaAtualizada, fimDeSemana, i);
		}
		 return listaAtualizada;
	}
	
	public void popularDiasAusente(){
		 //Aqui eu verifico se preciso atualizar algum dia que o usuario faltou
		 Calendar calendarioTemporario = Calendar.getInstance();
		 int hoje = calendarioTemporario.get(Calendar.DAY_OF_MONTH);
		 String fimDeSemana = null;
		 ArrayList<PontoDiario> listaAtualizada = new ArrayList<PontoDiario>();
		 listaAtualizada = colPontosDiarios;
		 
		 for(PontoDiario ponto: colPontosDiarios){
			if((ponto.getHoraEntrada().equals("") || ponto.getHoraEntrada() == null) && ponto.getDia() < hoje) {
					fimDeSemana = "AUSENTE";
					ponto.setHoraEntrada(fimDeSemana);
					ponto.setHoraSaida(fimDeSemana);
			}
		 }
		 colPontosDiarios = listaAtualizada;
	}
	
	public void preencherJtable(JTable table, int mes, String ano) {
		 //Aqui pego o modelo da Tabela
		 DefaultTableModel modeloTable;
		 modeloTable = (DefaultTableModel) table.getModel();
		 int dia;
		 int QtdeDeDiasDaDataRecebida = retornaQtdeDeDiasDoMes(mes,Integer.valueOf(ano));
		 
		 //PEGO OS PONTOS DO USUARIO NO DIA DO LOOP
		for (dia=1; dia<=QtdeDeDiasDaDataRecebida; dia++) {
			 for (PontoDiario ponto : colPontosDiarios) {
				 int diaNoLoop = ponto.getDia();
				 if(Integer.valueOf(diaNoLoop) == dia)
					 modeloTable.addRow(new String[] { String.valueOf(dia),
							 ponto.getHoraEntrada(), ponto.getHoraSaida(), ponto.getAssinatura()});
			}
		}
	 }
	//RETIRAR ESSA FUNÇÃO DAQUI -- USADA EM VIEW ALTERA FOLHA
	public void preencherJtableComInicioEFim(JTable table, int diaInicio, int diaFim) {
		 //Aqui pego o modelo da Tabela
		 DefaultTableModel modeloTable;
		 modeloTable = (DefaultTableModel) table.getModel();
		 
		 //Percorro os pontos, pegando apenas os com a data inicial e final
		for (int i=diaInicio; i<=diaFim; i++) {
			 for (PontoDiario ponto : colPontosDiarios) {
				 int diaNoLoop = ponto.getDia();
				 
				 if(diaNoLoop == i)
						 modeloTable.addRow(new String[] { String.valueOf(i),
								 ponto.getHoraEntrada(), ponto.getHoraSaida(), ponto.getAssinatura()});
			}
		}
	 }
	
	//CASO A VARIAVEL BOOLEANA SEJA TRUE SERÁ IMPRESSO O RESULTADO NO JTABLE
	//localJustificativa é uma variavel que receberá inteiros para poder atribuir corretamente onde será aplicada a justificativa.
	//Se 1, justificativa na entrada, se 2, justificativa na saída, se 3 justificativa na entrada e na saída
	public void justificaFolhaDePonto(JTable table, int diaInicial, int diaFinal, String justificativa, int localJustificativa, String assinatura, boolean imprimeNaTabela){
		//Aqui pego o modelo da Tabela
		DefaultTableModel modeloTable;
		modeloTable = (DefaultTableModel) table.getModel();
		//Percorro os pontos, pegando apenas os com a data inicial e final
		for (int i=diaInicial; i<=diaFinal; i++) {
			 for (PontoDiario ponto : colPontosDiarios) {
				 int diaNoLoop = ponto.getDia();
				 if(diaNoLoop == i){
					 if((ponto.getHoraEntrada().equals("SABADO") || ponto.getHoraEntrada().equals("DOMINGO")) && imprimeNaTabela)
						 //IMPRIMINDO O FIM DE SEMANA
						 modeloTable.addRow(new String[] { String.valueOf(i),
								 ponto.getHoraEntrada(), ponto.getHoraSaida(), ponto.getAssinatura()});
					 else {
						 if(localJustificativa == 1)
						 ponto.setHoraEntrada(justificativa);
						 else if(localJustificativa == 2)
						 ponto.setHoraSaida(justificativa);
							 else{
								 ponto.setHoraEntrada(justificativa);
								 ponto.setHoraSaida(justificativa);
							 }
						 
						 ponto.setAssinatura(assinatura);
						 //IMPRIMINDO O RESULTADO
						 if(imprimeNaTabela)
						 modeloTable.addRow(new String[] { String.valueOf(i),
								 ponto.getHoraEntrada(), ponto.getHoraSaida(), ponto.getAssinatura()});
					 }
				 }					 
			}
		}
		
	}
	
	private int retornaQtdeDeDiasDoMes(int mes, int ano){
		Calendar calendario = Calendar.getInstance();
		calendario.set(Calendar.YEAR, ano); 
		calendario.set(Calendar.MONTH, mes);
		return calendario.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	public void salvaEmXML(String localArmazenamento){
		XStream xStream = new XStream(new StaxDriver());
        File arquivo = new File(localArmazenamento);
        xStream.alias("Ponto_Diario", PontoDiario.class);
        FileOutputStream gravar;
        try {
            gravar = new FileOutputStream(arquivo);
            gravar.write(xStream.toXML(colPontosDiarios).getBytes());
            gravar.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
		
	}
	
	public void lerDoXML(String localArmazenamento){
		try {
			XStream xStream = new XStream(new StaxDriver());
			//Questões de segurança
			XStream.setupDefaultSecurity(xStream);
			xStream.addPermission(AnyTypePermission.ANY); 
			xStream.alias("Ponto_Diario", PontoDiario.class);
			xStream.processAnnotations(PontoDiario.class);
			BufferedInputStream input = new BufferedInputStream(new FileInputStream(localArmazenamento));
			@SuppressWarnings("unchecked")
			ArrayList<PontoDiario> lista = (ArrayList<PontoDiario>) xStream.fromXML(input);
			input.close();
			
			colPontosDiarios = lista;
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "ColPontosDiarios [colPontosDiarios=" + colPontosDiarios + "]";
	}
	
}
