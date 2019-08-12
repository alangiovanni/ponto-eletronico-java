package telas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import JasperReport.Relatorio;
import coleções.ColAdministradores;
import coleções.ColPontosDiarios;
import coleções.ColUsuarios;
import net.sf.jasperreports.engine.JRException;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.DefaultComboBoxModel;

public class ViewAdminPrincipal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	//INSTANCIANDO A LISTA DE PONTOS
	private ColPontosDiarios pontosDiarios = new ColPontosDiarios();

	public ViewAdminPrincipal(ColUsuarios usuarios, ColAdministradores administradores, String adminLogado) {
		setResizable(false);
		setTitle("Administra\u00E7\u00E3o");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 775, 538);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelMenu = new JPanel();
		panelMenu.setBounds(0, 0, 769, 30);
		contentPane.add(panelMenu);
		panelMenu.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 625, 25);
		panelMenu.add(menuBar);
		menuBar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		
		JMenu mnCadastroEstagiarios = new JMenu("   Estagiarios   ");
		mnCadastroEstagiarios.setHorizontalAlignment(SwingConstants.CENTER);
		mnCadastroEstagiarios.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		menuBar.add(mnCadastroEstagiarios);
		
		JMenuItem mntmCriarConta = new JMenuItem("Criar Conta");
		mntmCriarConta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewCadastro obj = new ViewCadastro(usuarios);
				obj.setVisible(true);
				obj.setLocationRelativeTo(null);
			}
		});
		mnCadastroEstagiarios.add(mntmCriarConta);
		
		JMenuItem mntmRemoverConta = new JMenuItem("Remover Conta");
		mntmRemoverConta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewRemoveUsuario obj = new ViewRemoveUsuario(usuarios);
				obj.setVisible(true);
				obj.setLocationRelativeTo(null);
			}
		});
		mnCadastroEstagiarios.add(mntmRemoverConta);
		
		JMenuItem mntmAlterarInformaes = new JMenuItem("Alterar Informa\u00E7\u00F5es da Conta");
		mntmAlterarInformaes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewAlteraUsuario obj = new ViewAlteraUsuario(usuarios);
				obj.setVisible(true);
				obj.setLocationRelativeTo(null);
			}
		});
		mnCadastroEstagiarios.add(mntmAlterarInformaes);
		
		JMenu mnCadastroAdmin = new JMenu("   Administrador   ");
		mnCadastroAdmin.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		menuBar.add(mnCadastroAdmin);
		
		JMenuItem mntmCriarConta_1 = new JMenuItem("Criar Conta");
		mntmCriarConta_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ViewAdminCadastro obj = new ViewAdminCadastro(administradores);
				obj.setVisible(true);
				obj.setLocationRelativeTo(null);
			}
		});
		mnCadastroAdmin.add(mntmCriarConta_1);
		
		JMenuItem mntmRemoverConta_1 = new JMenuItem("Remover Conta");
		mntmRemoverConta_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewAdminRemove obj = new ViewAdminRemove(administradores, adminLogado);
				obj.setVisible(true);
				obj.setLocationRelativeTo(null);
			}
		});
		mnCadastroAdmin.add(mntmRemoverConta_1);
		
		JMenu mnFolhaDePonto = new JMenu("   Folha de Ponto   ");
		mnFolhaDePonto.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		menuBar.add(mnFolhaDePonto);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Alterar Informa\u00E7\u00F5es");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewAlteraFolha dialog = new ViewAlteraFolha(usuarios, pontosDiarios);
				dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
				dialog.setVisible(true);
				dialog.setLocationRelativeTo(null);
			}
		});
		mnFolhaDePonto.add(mntmNewMenuItem);
		
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewLogin obj = new ViewLogin();
				obj.setVisible(true);
				obj.setLocationRelativeTo(null);
				dispose();
			}
		});
		btnSair.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSair.setBounds(626, 0, 133, 25);
		panelMenu.add(btnSair);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 166, 748, 332);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Dia", "Hora de Entrada", "Hora de Saída", "Assinatura"
			}
		));
		scrollPane.setViewportView(table);
		
		JPanel panelDados = new JPanel();
		panelDados.setBounds(10, 41, 605, 114);
		contentPane.add(panelDados);
		panelDados.setLayout(null);
		
		JComboBox<String> comboEstagiario = new JComboBox<String>();
		comboEstagiario.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		//PREENCHENDO COMBO COM OS ESTAGIARIOS CADASTRADOS
		usuarios.preencheComboDeUsuarios(comboEstagiario);
		
		comboEstagiario.setBounds(94, 53, 108, 39);
		panelDados.add(comboEstagiario);
		
		JComboBox<Object> comboMes = new JComboBox<Object>();
		comboMes.setFont(new Font("Tahoma", Font.PLAIN, 13));
		comboMes.setModel(new DefaultComboBoxModel<Object>(new String[] {"Janeiro", "Fevereiro", "Marco", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"}));
		comboMes.setBounds(270, 53, 76, 39);
		panelDados.add(comboMes);
		//Setando o mes atual no combo BOx
		comboMes.setSelectedIndex(retornaMes());
		
		JComboBox<Object> comboAno = new JComboBox<Object>();
		comboAno.setModel(new DefaultComboBoxModel<Object>(new String[] {"2018", "2019", "2020"}));
		comboAno.setFont(new Font("Tahoma", Font.PLAIN, 13));
		comboAno.setBounds(414, 53, 76, 39);
		panelDados.add(comboAno);
		//Setando o Ano atual no combo Box se houver
		comboAno.setSelectedItem(Integer.toString(retornaAno()));
		
		JLabel lblEstagiario = new JLabel("Estagi\u00E1rio: ");
		lblEstagiario.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEstagiario.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEstagiario.setBounds(10, 51, 74, 41);
		panelDados.add(lblEstagiario);
		
		JLabel lblMes = new JLabel("M\u00EAs: ");
		lblMes.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMes.setBounds(212, 53, 48, 39);
		panelDados.add(lblMes);
		
		JLabel lblAno = new JLabel("Ano: ");
		lblAno.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAno.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAno.setBounds(356, 53, 48, 39);
		panelDados.add(lblAno);
		
		JButton btnImprimir = new JButton("");
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String loginEstagiario = String.valueOf(comboEstagiario.getSelectedItem());
				int mes = (int) comboMes.getSelectedIndex()+1;
				String ano = (String) comboAno.getSelectedItem();
				String localArmazenamento = "armazenamento_xml/"+loginEstagiario+"/"+mes+"_"+ano+".xml";

				pontosDiarios.lerDoXML(localArmazenamento);
				
				Relatorio gerarRelatorio = new Relatorio();
				try {
					gerarRelatorio.load(pontosDiarios);
				} catch (JRException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Erro: "+e);
				}
			}
		});
		JButton btnVariosRelatorios = new JButton("");
		btnVariosRelatorios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int mes = (int) comboMes.getSelectedIndex()+1;
				String ano = (String) comboAno.getSelectedItem();
				
				Relatorio gerarRelatorio = new Relatorio();
				
				//PEGO O DIRETORIO DO USUARIO
	            JFileChooser fc = new JFileChooser();
	            // MOSTRA APENAS DIRETORIOS
	            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	            int resultado = fc.showOpenDialog(null);
	            if(resultado == JFileChooser.APPROVE_OPTION){
	                File diretorio = fc.getSelectedFile();
					try {
						for (int i = 0; i < comboEstagiario.getItemCount(); i++){
							
							String loginEstagiario = String.valueOf(comboEstagiario.getItemAt(i));
							String nomeDoArquivo = diretorio.getAbsolutePath()+"/"+loginEstagiario+"_"+mes+"_"+ano+".pdf";
							File localArmazenamento = new File("armazenamento_xml/"+loginEstagiario+"/"+mes+"_"+ano+".xml");
							
							if(localArmazenamento.exists()){
								//Se os pontos do usuario existirem, carrega na variável
								pontosDiarios.lerDoXML(localArmazenamento.getAbsolutePath());
								//Gerando Relatorio Individual
								gerarRelatorio.pdf(pontosDiarios, nomeDoArquivo);
							}
						}
						JOptionPane.showMessageDialog(null, "Relatórios Criados com Sucesso!");
					} catch (JRException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "Erro: "+e1);
					}
	            }
	            else
	                JOptionPane.showMessageDialog(null, "Você não selecionou nenhum diretorio. Repita a operação!", "Nenhum diretório Selecionado", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		JButton btnConsultar = new JButton("");
		btnConsultar.setIcon(new ImageIcon("Images/oie_1cWAbh9p98nu.png"));
		btnConsultar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String loginEstagiario = String.valueOf(comboEstagiario.getSelectedItem());
				String diretorioDoUsuario = "armazenamento_xml/"+loginEstagiario+"/";
				int mes = (int) comboMes.getSelectedIndex()+1;
				String ano = (String) comboAno.getSelectedItem();
				
				//Limpar a tabela
				apagaRowJtable();
				//PROCURA AS INFORMACOES QUE O USUARIO PEDIU
				if(existeXML(diretorioDoUsuario+mes+"_"+ano+".xml")){
					pontosDiarios.lerDoXML(diretorioDoUsuario+mes+"_"+ano+".xml");
					//Preenchendo minha tabela
					pontosDiarios.preencherJtable(table, mes-1, ano);
					btnImprimir.setEnabled(true);
				} else {
					JOptionPane.showMessageDialog(null, "Nao foi encontrado nenhum registro na DATA informada");
					btnImprimir.setEnabled(false);
				}
			}
		});
		btnConsultar.setBounds(500, 53, 89, 39);
		panelDados.add(btnConsultar);
		
		JLabel lblSelecioneCorretamenteOs = new JLabel("Selecione corretamente os seguintes campos abaixo");
		lblSelecioneCorretamenteOs.setHorizontalAlignment(SwingConstants.LEFT);
		lblSelecioneCorretamenteOs.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSelecioneCorretamenteOs.setBounds(10, 11, 585, 31);
		panelDados.add(lblSelecioneCorretamenteOs);
		
		JPanel panelAcoes = new JPanel();
		panelAcoes.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelAcoes.setBounds(624, 41, 134, 114);
		contentPane.add(panelAcoes);
		panelAcoes.setLayout(null);
		
		btnImprimir.setBounds(10, 54, 52, 49);
		panelAcoes.add(btnImprimir);
		btnImprimir.setIcon(new ImageIcon("Images/icon_impressao.png"));
		btnImprimir.setEnabled(false);
		
		JLabel lblBarraDeAes = new JLabel("Barra de A\u00E7\u00F5es");
		lblBarraDeAes.setHorizontalAlignment(SwingConstants.CENTER);
		lblBarraDeAes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBarraDeAes.setBounds(10, 11, 115, 22);
		panelAcoes.add(lblBarraDeAes);
		
		btnVariosRelatorios.setIcon(new ImageIcon("Images/relatorios.png"));
		btnVariosRelatorios.setBounds(72, 54, 53, 49);
		panelAcoes.add(btnVariosRelatorios);
	}
	
	private void apagaRowJtable(){
		 DefaultTableModel modeloTable;
		 modeloTable = (DefaultTableModel) table.getModel();
		 
		 //Aqui verifico se a jTable tem algum registo se tiver eu deleto
		 while (modeloTable.getRowCount() > 0) {
			 modeloTable.removeRow(0);
	     }
	}
	
	private boolean existeXML(String caminhoDoArquivo){
		File arquivo = new File(caminhoDoArquivo);
		if(arquivo.exists()){
		return true;
		} else{
			return false;
		}
	}
	private int retornaMes(){
		Calendar calendario = Calendar.getInstance();
		return (calendario.get(Calendar.MONTH));
	}
	
	private int retornaAno(){
		Calendar calendario = Calendar.getInstance();
		return calendario.get(Calendar.YEAR);
	}
}
