package telas;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import coleções.ColPontosDiarios;
import coleções.ColUsuarios;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JToggleButton;

public class ViewAlteraFolha extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textJustificativa;
	private JTable table;
	private String localArmazenamento;
	private JTextField textAssinatura;
	
	public ViewAlteraFolha(ColUsuarios usuarios, ColPontosDiarios pontosDiarios) {
		setTitle("ALTERA\u00C7\u00C3O NA FOLHA DE FREQU\u00CANCIA");
		setResizable(false);
		setBounds(100, 100, 784, 693);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JPanel panelInformações = new JPanel();
		panelInformações.setBounds(10, 11, 758, 355);
		contentPanel.add(panelInformações);
		panelInformações.setLayout(null);
		
		JComboBox<String> comboEstagiario = new JComboBox<String>();
		comboEstagiario.setBounds(85, 52, 105, 27);
		panelInformações.add(comboEstagiario);
		//PREENCHENDO O JCOMBO com os ESTAGIÁRIOS
		comboEstagiario.addItem("TODOS");
		usuarios.preencheComboDeUsuarios(comboEstagiario);
		
		JComboBox<Integer> comboDiaInicio = new JComboBox<Integer>();
		comboDiaInicio.setBounds(321, 53, 105, 26);
		panelInformações.add(comboDiaInicio);
		
		JComboBox<Integer> comboDiaFim = new JComboBox<Integer>();
		comboDiaFim.setBounds(511, 53, 105, 26);
		panelInformações.add(comboDiaFim);
		
		//PREENCHE OS COMBOS DE DIAS COM OS DIAS DO MES ATUAL
		preencheJcomboComOsDiasDoMesAtual(comboDiaInicio, comboDiaFim);
		
		//SETA O JCOMBO com o Dia Atual e seu Sucessor
		comboDiaInicio.setSelectedItem(retornaDia());
		comboDiaFim.setSelectedItem(retornaDia()+1);
		
		JLabel lblEstagiario = new JLabel("Estagi\u00E1rio:");
		lblEstagiario.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEstagiario.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEstagiario.setBounds(10, 52, 65, 27);
		panelInformações.add(lblEstagiario);
		
		JLabel lblDiainicio = new JLabel("Dia [In\u00EDcio]:");
		lblDiainicio.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDiainicio.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDiainicio.setBounds(236, 52, 75, 27);
		panelInformações.add(lblDiainicio);
		
		JLabel lblDiafim = new JLabel("Dia [Fim]:");
		lblDiafim.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDiafim.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDiafim.setBounds(436, 52, 65, 27);
		panelInformações.add(lblDiafim);
		
		JButton btnExibirAgora = new JButton("Exibir Agora");
		JButton btnSalvar = new JButton("SALVAR ALTERA\u00C7\u00D5ES");
		JButton btnJustificar = new JButton("Inserir Justificativa");
		
		btnExibirAgora.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int diaInicial = (int) comboDiaInicio.getSelectedItem();
				int diaFinal = (int) comboDiaFim.getSelectedItem();
				
		 		//Limpando a tabela
		 		apagaRowJtable();
				 
				 if(diaInicial > diaFinal)
					 JOptionPane.showMessageDialog(null, "O dia de Inicio é maior que o dia Final. Por favor corrija o erro invertendo os campos.");
				 	else {
				 		if(comboEstagiario.getSelectedItem().equals("TODOS"))
				 			//A opção TODOS é apenas uma forma de aplicar a todos a alteração que vinher a ser feita
				 			JOptionPane.showMessageDialog(null, "Não há como exibir as informações de TODOS na Planilha.\nPreencha corretamente os campos e em seguida clique no botão\n"+btnJustificar.getText()+" para que as alterações sejam aplicadas a TODOS.");
				 		else {
					 		String loginUsuario = (String) comboEstagiario.getSelectedItem();
					 		localArmazenamento = "armazenamento_xml/"+loginUsuario+"/"+retornaMes()+"_"+retornaAno()+".xml";
					 		pontosDiarios.lerDoXML(localArmazenamento);
					 		
					 		//CASO A LISTA ESTEJA VAZIA SIGNIFICA DIZER QUE O USUARIO NÃO LOGOU NO SISTEMA ESTE MES, 
					 		//POIS A FOLHA DO MES É GERADA QUANDO O USUARIO LOGA NO SISTEMA
					 		if(pontosDiarios.retornaTamanho() == 0)
					 			JOptionPane.showMessageDialog(null, "O Usuário: [" + loginUsuario + "] não logou no sistema este mês ainda.", "Erro na captura da folha de ponto", JOptionPane.ERROR_MESSAGE);
					 		else
					 		//PREENCHENDO A TABELA COM OS DIAS SELECIONADOS
					 		pontosDiarios.preencherJtableComInicioEFim(table, diaInicial, diaFinal);
				 	}
				 }
			}
		});
		btnExibirAgora.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnExibirAgora.setBounds(626, 47, 122, 34);
		panelInformações.add(btnExibirAgora);
		
		JLabel lblSelecioneCorretamenteO = new JLabel("Selecione um estagi\u00E1rio ou TODOS e o dia ou a sequ\u00EAncia de dias que deseja realizar a altera\u00E7\u00E3o: ");
		lblSelecioneCorretamenteO.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSelecioneCorretamenteO.setBounds(10, 11, 748, 30);
		panelInformações.add(lblSelecioneCorretamenteO);
		
		JRadioButton rdbComum = new JRadioButton("Comum:");
		textJustificativa = new JTextField();
		textJustificativa.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textJustificativa.setEnabled(false);
		JRadioButton rdbPersonalizada = new JRadioButton("Personalizada:");
		JComboBox<String> comboJustificativa = new JComboBox<String>();
		comboJustificativa.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbComum.setSelected(true);
		
		rdbPersonalizada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textJustificativa.setEnabled(true);
				textJustificativa.requestFocus(); //Setar o foco para logo quando o usuario clicar no rdb
				comboJustificativa.setEnabled(false);
				rdbComum.setSelected(false);
				
			}
		});
		
		rdbComum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbPersonalizada.setSelected(false);
				textJustificativa.setEnabled(false);
				rdbComum.setEnabled(true);
				comboJustificativa.setEnabled(true);
			}
		});
		rdbComum.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbComum.setBounds(10, 201, 83, 23);
		panelInformações.add(rdbComum);
		
		rdbPersonalizada.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbPersonalizada.setBounds(10, 240, 116, 23);
		panelInformações.add(rdbPersonalizada);
		
		JLabel lblSelecioneOAjuste = new JLabel("Selecione a Justificativa, a localiza\u00E7\u00E3o da Justificativa na frequ\u00EAncia e assinatura:");
		lblSelecioneOAjuste.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSelecioneOAjuste.setBounds(10, 119, 738, 30);
		panelInformações.add(lblSelecioneOAjuste);
		
		JLabel lblAjuste = new JLabel("JUSTIFICATIVA: ");
		lblAjuste.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAjuste.setBounds(10, 160, 301, 30);
		panelInformações.add(lblAjuste);
		
		comboJustificativa.setModel(new DefaultComboBoxModel<String>(new String[] {" Recesso do TRIBUNAL", " Ferias", " Falta Justificada", " Ausencia por Infermidade", " Problema Familiar", " Feriado", " Expediente Antecipado", " AUSENTE"}));
		comboJustificativa.setBounds(137, 200, 172, 27);
		panelInformações.add(comboJustificativa);
		
		JRadioButton rdbHoraDeEntrada = new JRadioButton("Hora de Entrada");
		JRadioButton rdbHoraDeSaida = new JRadioButton("Hora de Sa\u00EDda");
		
		JLabel lblAssinatura = new JLabel("ASSINATURA: ");
		lblAssinatura.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAssinatura.setBounds(602, 160, 146, 30);
		panelInformações.add(lblAssinatura);
		
		textAssinatura = new JTextField();
		textAssinatura.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textAssinatura.setEditable(false);
		textAssinatura.setEnabled(false);
		textAssinatura.setColumns(10);
		textAssinatura.setBounds(598, 200, 150, 27);
		panelInformações.add(textAssinatura);
		
		JToggleButton tglAssinar = new JToggleButton("Assinar");
		tglAssinar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(tglAssinar.isSelected()){
					textAssinatura.setEnabled(true);
					textAssinatura.setEditable(true);
					if(comboEstagiario.getSelectedItem().equals("TODOS"))
						textAssinatura.setText("");
					else
						textAssinatura.setText((String) comboEstagiario.getSelectedItem());
				}
				else{
					textAssinatura.setEnabled(false);
					textAssinatura.setEditable(false);
					textAssinatura.setText("");
				}
			}
		});
		tglAssinar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tglAssinar.setBounds(598, 235, 122, 34);
		panelInformações.add(tglAssinar);
		
		btnJustificar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnJustificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int diaInicial = (int) comboDiaInicio.getSelectedItem();
				int diaFinal = (int) comboDiaFim.getSelectedItem();
				int localJustificativa = 3; //Se 1, somente entrada. Se 2, somente saída. Se 3, entrada e saída;
		 		String justificativa;
		 		String assinatura="";
		 		
		 		//PEGO O USUARIO SELECIONADO, PEGO O XML E LEIO. SALVO EM pontosDiarios
		 		String loginUsuario = (String) comboEstagiario.getSelectedItem();
		 		localArmazenamento = "armazenamento_xml/"+loginUsuario+"/"+retornaMes()+"_"+retornaAno()+".xml";
		 		pontosDiarios.lerDoXML(localArmazenamento);
				
				//Apaga a tabela
				apagaRowJtable();
				
				//TRATAMENTOS DE ERROS
				 if(diaInicial > diaFinal){
					 JOptionPane.showMessageDialog(null, "O dia de Inicio é maior que o dia Final. Por favor corrija o erro invertendo os campos.");
				 }
				 else if (!rdbHoraDeEntrada.isSelected() && !rdbHoraDeSaida.isSelected()){
					 JOptionPane.showMessageDialog(null, "Selecione pelo menos UM campo onde inserir a JUSTIFICATIVA");
				 }
				 else {
					 if (rdbPersonalizada.isSelected())
						 justificativa = textJustificativa.getText();
					 else
						 justificativa = (String) comboJustificativa.getSelectedItem();
					 
					 if (rdbPersonalizada.isSelected() && justificativa.equals(""))
						 JOptionPane.showMessageDialog(null, "Não foi passada nenhuma justificativa para ser adicionada a folha de Ponto\nOs campos na data selecionada ficarão em BRANCO, EXCETO Domingos e Sábados.");
						 
					 //AQUI EU PEGO O LOCAL ONDE SERÁ INSERIDA A JUSTIFICATIVA. 1 PARA APENAS ENTRADA, 2 PARA APENAS SAIDA, 3 PARA AMBOS
					 if(rdbHoraDeEntrada.isSelected() && !rdbHoraDeSaida.isSelected())
						 localJustificativa = 1;
					 else
						 if (!rdbHoraDeEntrada.isSelected() && rdbHoraDeSaida.isSelected())
							 localJustificativa = 2;
					 
					 //Pegando a assinatura
					 if(tglAssinar.isSelected())
						 assinatura = textAssinatura.getText();
					 
					 if(!comboEstagiario.getSelectedItem().equals("TODOS")){
							 pontosDiarios.justificaFolhaDePonto(table, diaInicial, diaFinal, justificativa, localJustificativa, assinatura, true);
							 btnSalvar.setEnabled(true);
				 	}
					 else {
						 int confirmacao = JOptionPane.showConfirmDialog(null, "Isto afetará a TODAS as fichas de frequências. Deseja prosseguir com a INSERÇÃO desta justificativa? Dia Inicial: ["+diaInicial+"] | "+"Dia Final: ["+diaFinal+"]");
						 if(confirmacao == 0){
							 if(rdbPersonalizada.isSelected()){ 					 
									 //O ITEM 0 DO COMBO É TODOS, dessa forma preciso começar do 1
									 for(int i = 1; i < comboEstagiario.getItemCount(); i++){
										 loginUsuario = (String) comboEstagiario.getItemAt(i);
										 localArmazenamento = "armazenamento_xml/"+loginUsuario+"/"+retornaMes()+"_"+retornaAno()+".xml";
										 pontosDiarios.lerDoXML(localArmazenamento);
										 pontosDiarios.justificaFolhaDePonto(table, diaInicial, diaFinal, justificativa, localJustificativa, assinatura, false);
										 //Salvando automaticamente
										 pontosDiarios.salvaEmXML(localArmazenamento);
									 }
									 setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
									 JOptionPane.showMessageDialog(null, "As alterações foram salvas automaticamente. Pode fechar a Janela ou apertar o botão de 'VOLTAR'");
							 }
							 else
								 JOptionPane.showMessageDialog(null, "Nenhuma Operação foi realizada");
						 }
					 }
				 }
			}
		});
		btnJustificar.setBounds(290, 308, 172, 34);
		panelInformações.add(btnJustificar);
		
		textJustificativa.setBounds(137, 236, 172, 27);
		panelInformações.add(textJustificativa);
		textJustificativa.setColumns(10);
		
		JLabel lblFrequencia = new JLabel("LOCAL NA FREQU\u00CANCIA:");
		lblFrequencia.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFrequencia.setBounds(358, 160, 234, 30);
		panelInformações.add(lblFrequencia);
		
		rdbHoraDeEntrada.setSelected(true);
		rdbHoraDeEntrada.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbHoraDeEntrada.setBounds(358, 201, 234, 23);
		panelInformações.add(rdbHoraDeEntrada);
		
		rdbHoraDeSaida.setSelected(true);
		rdbHoraDeSaida.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbHoraDeSaida.setBounds(358, 240, 234, 23);
		panelInformações.add(rdbHoraDeSaida);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 377, 758, 223);
		contentPanel.add(scrollPane);
		
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
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 611, 748, 43);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnVoltar.setBounds(643, 11, 95, 29);
		panel.add(btnVoltar);
		
		btnSalvar.setEnabled(false);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pontosDiarios.salvaEmXML(localArmazenamento);
				JOptionPane.showMessageDialog(null, "Sucesso! Justificativa Inserida em: " + comboEstagiario.getSelectedItem());
				setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			}
		});
		btnSalvar.setBounds(471, 11, 162, 29);
		panel.add(btnSalvar);
	}
	protected void preencheJcomboComOsDiasDoMesAtual(JComboBox<Integer> comboDiaInicio, JComboBox<Integer> comboDiaFim){
		Calendar calendario = Calendar.getInstance();
		int diasDoMes = calendario.getActualMaximum(Calendar.DAY_OF_MONTH);
		for (int i = 1; i <= diasDoMes; i++){
			comboDiaInicio.addItem(i);
			comboDiaFim.addItem(i);
		}
	}
	protected int retornaDia(){
		Calendar calendario = Calendar.getInstance();
		return (calendario.get(Calendar.DAY_OF_MONTH));
	}
	protected int retornaMes(){
		Calendar calendario = Calendar.getInstance();
		//É NECESSÁRIO PEGAR O RESULTADO +1 POIS A CONTAGEM DOS MESES COMEÇAM DO 0, SENDO JANEIRO = 0;
		return (calendario.get(Calendar.MONTH)+1);
	}
	
	protected int retornaAno(){
		Calendar calendario = Calendar.getInstance();
		return calendario.get(Calendar.YEAR);
	}
	protected void apagaRowJtable(){
		 DefaultTableModel modeloTable;
		 modeloTable = (DefaultTableModel) table.getModel();
		 
		 //Aqui verifico se a jTable tem algum registo se tiver eu deleto
		 while (modeloTable.getRowCount() > 0) {
			 modeloTable.removeRow(0);
	     }
	}
}
