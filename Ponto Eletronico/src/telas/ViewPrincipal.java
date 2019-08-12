package telas;

import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import classes.PontoDiario;
import coleções.ColPontosDiarios;
import coleções.ColUsuarios;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.text.DateFormat;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class ViewPrincipal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public ViewPrincipal(String nome, String lotacao, String login, ColUsuarios usuarios) {
		setResizable(false);
		JButton btnAbrir = new JButton("MARCAR ENTRADA");
		JButton btnFechar = new JButton("MARCAR SA\u00CDDA");
		//ARMAZENAMENTO DOS PONTOS DENTRO DE UMA PASTA COM O NOME DO LOGIN DO USUARIO SALVO EM XML
		String armazenamentoPontos = "armazenamento_xml/"+login+"/"+retornaMes()+"_"+retornaAno()+".xml";
		String diretorioDoUsuario = "armazenamento_xml/"+login+"/";
		
		//INSTANCIANDO A LISTA DE PONTOS
		ColPontosDiarios pontosDiarios = new ColPontosDiarios();

		//BUSCAR OS PONTOS SALVOS ARMAZENADOS NO MES E ANO ATUAL
		if(existeXML(armazenamentoPontos)){
			pontosDiarios.lerDoXML(armazenamentoPontos);
		} else {
			//Atualizando e Ordenando minha coleção INICIALMENTE
			pontosDiarios.ordenaLista(pontosDiarios.popularColecao(retornaMes()-1, String.valueOf(retornaAno())), armazenamentoPontos, retornaMes()-1, String.valueOf(retornaAno()));
			//Seto todos os nomes e logins na coleção
			pontosDiarios.setAllNomes(nome, login);
			//Seto mes e ano na coleção
			pontosDiarios.setAllMesAno(retornaMes(), retornaAno());
			//Seto a lotacao na coleção
			pontosDiarios.setAllLotacao(lotacao);
			//Salvo em XML
			pontosDiarios.salvaEmXML(armazenamentoPontos);;
		}
		
		//BUSCA A DATA DE HOJE
		int dia = retornaDia();
        
		//Procurando por algum registro de Ponto do Usuário Autenticado na DATA DE HOJE
		PontoDiario RecebePontoDoUsuario = pontosDiarios.retornaUsuarioPelaData(dia);
		
		//DESABILITANDO OS BOTÕES CASO O USUÁRIO JÁ TENHA BATIDO O PONTO
		if(RecebePontoDoUsuario.getHoraEntrada().equals("")){
		} else {
			btnAbrir.setEnabled(false);
			btnAbrir.setText("ENTRADA MARCADA");
			if(RecebePontoDoUsuario.getHoraSaida().equals("")){
			} else {
				btnFechar.setEnabled(false);
				btnFechar.setText("SAÍDA MARCADA");
			}
		}
		
		setTitle("Estagi\u00E1rios Atualizados");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 989, 537);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel lblHora = new JLabel("HORA");
		lblHora.setHorizontalAlignment(SwingConstants.CENTER);
		lblHora.setFont(new Font("Tahoma", Font.PLAIN, 60));
		lblHora.setBounds(10, 147, 963, 143);
		contentPane.add(lblHora);
		
		//MOSTRAR HORA E DATA POR THREAD
	    Thread th = new Thread(new Runnable() { //cria uma thread
	        public void run() {
	            while(true) { //roda indefinidamente
	            	
	                String horaAtual = retornaHoraAtual();
	                lblHora.setText(horaAtual);    
	                try {
	                    Thread.sleep(1000); //espera 1 segundo para fazer a nova evolução
	                } catch(InterruptedException ex){
	                }
	            }
	        }
	    }); th.start();
		
		btnAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Instanciando o novo ponto
				String horaAtual = retornaHoraAtual();
				int dia = retornaDia();
				
				//Marcando o PONTO
				PontoDiario novoPonto = pontosDiarios.retornaUsuarioPelaData(dia);
				novoPonto.setHoraEntrada(horaAtual);
				pontosDiarios.salvaEmXML(armazenamentoPontos);
				
				btnAbrir.setEnabled(false);
				btnAbrir.setText("ENTRADA MARCADA");
			}
		});
		
		JPanel panelSlogan = new JPanel();
		panelSlogan.setBackground(Color.WHITE);
		panelSlogan.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelSlogan.setBounds(10, 11, 479, 101);
		contentPane.add(panelSlogan);
		panelSlogan.setLayout(null);
		
		JLabel lblPonto = new JLabel("   Precise Time");
		lblPonto.setIcon(new ImageIcon("Images/PreciseTimeSlogan1.1.png"));
		lblPonto.setBounds(10, 11, 459, 79);
		panelSlogan.add(lblPonto);
		lblPonto.setHorizontalAlignment(SwingConstants.LEFT);
		lblPonto.setFont(new Font("Algerian", Font.PLAIN, 32));
		btnAbrir.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnAbrir.setBounds(10, 395, 249, 92);
		contentPane.add(btnAbrir);
		
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//DATA DE HOJE
				int dia = retornaDia();
				
				//Pegando a frequência do usuário pela data de hoje
				PontoDiario novoPonto = pontosDiarios.retornaUsuarioPelaData(dia);
				
				if(!novoPonto.getHoraEntrada().equals("")){
					//Aqui eu Pego a hora atual, Seto a hora da saída, coloco a assinatura do usuario e salvo as informações em XML
					String horaSaida = retornaHoraAtual();
					novoPonto.setHoraSaida(horaSaida);
					novoPonto.setAssinatura(login);
					pontosDiarios.salvaEmXML(armazenamentoPontos);
					btnFechar.setEnabled(false);
					btnFechar.setText("SAÍDA MARCADA");
				} else {
					JOptionPane.showMessageDialog(null, "VOCÊ PRECISA ABRIR O PONTO PRIMEIRO!");
				}

			}
		});

		btnFechar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnFechar.setBounds(359, 395, 249, 92);
		contentPane.add(btnFechar);
		
		JButton btnVerPontos = new JButton("ACOMPANHAR JORNADA");
		btnVerPontos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ViewJornada obj = new ViewJornada(pontosDiarios, nome, login, diretorioDoUsuario, armazenamentoPontos);
				obj.setVisible(true);
				obj.setLocationRelativeTo(null);
			}
		});
		btnVerPontos.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnVerPontos.setBounds(714, 395, 249, 92);
		contentPane.add(btnVerPontos);
		
		JPanel panelInformativo = new JPanel();
		panelInformativo.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelInformativo.setBackground(new Color(255, 255, 255));
		panelInformativo.setBounds(510, 11, 463, 101);
		contentPane.add(panelInformativo);
		panelInformativo.setLayout(null);
		
		JButton btnSair = new JButton("SAIR");
		btnSair.setBounds(339, 11, 114, 32);
		panelInformativo.add(btnSair);
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewLogin obj = new ViewLogin();
				obj.setLocationRelativeTo(null);
				obj.setVisible(true);
				dispose();
			}
		});
		
		JEditorPane textInforUsuario = new JEditorPane();
		textInforUsuario.setBounds(10, 11, 326, 75);
		panelInformativo.add(textInforUsuario);
		textInforUsuario.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textInforUsuario.setEditable(false);
		textInforUsuario.setText(retornaCumprimento()+"\nNome do Estagiario: " + nome + "\rLotação: " + lotacao);
		
		JButton btnAlterarSenha = new JButton("Alterar Senha");
		btnAlterarSenha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JdialogAlteraSenha obj = new JdialogAlteraSenha(login, usuarios);
				obj.setLocationRelativeTo(null);
				obj.setVisible(true);
			}
		});
		btnAlterarSenha.setBounds(339, 54, 114, 32);
		panelInformativo.add(btnAlterarSenha);
	}
	
	private boolean existeXML(String caminhoDoArquivo){
		File arquivo = new File(caminhoDoArquivo);
		if(arquivo.exists()){
		return true;
		} else{
			return false;
		}
	}
	
	protected int retornaDia(){
		Calendar calendario = Calendar.getInstance();
		return calendario.get(Calendar.DAY_OF_MONTH);
	}
	
	private String retornaHoraAtual (){
        Date data = Calendar.getInstance().getTime();
        DateFormat horaAgora = DateFormat.getTimeInstance();
        String horaAtual = horaAgora.format(data);
        
        return horaAtual;
	}
	
	private int retornaMes(){
		Calendar calendario = Calendar.getInstance();
		//É NECESSÁRIO PEGAR O RESULTADO +1 POIS A CONTAGEM DOS MESES COMEÇAM DO 0, SENDO JANEIRO = 0;
		return (calendario.get(Calendar.MONTH)+1);
	}
	
	private int retornaAno(){
		Calendar calendario = Calendar.getInstance();
		return calendario.get(Calendar.YEAR);
	}
	
	private String retornaCumprimento(){
		Calendar cal = Calendar.getInstance();
		System.out.println(cal.getTime());
		int hora = cal.get(Calendar.HOUR_OF_DAY);
		if(hora < 12 ) {
			return "Bom dia,";
		}else if(hora >= 12 && hora < 18) {
			return "Boa tarde,";
		}else {
			return "Boa noite,";
		}
	}
}
