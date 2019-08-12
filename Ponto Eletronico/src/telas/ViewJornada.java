package telas;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import JasperReport.Relatorio;
import coleções.ColPontosDiarios;
import net.sf.jasperreports.engine.JRException;

import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class ViewJornada extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ViewJornada(ColPontosDiarios pontosDiarios, String nome, String login, String diretorioDoUsuario, String armazenamentoPontos) {
		//USADO ONDE REALIZA O PREENCHIMENTO DA TABELA
		
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 605, 603);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelTitulo = new JPanel();
		panelTitulo.setBounds(10, 11, 566, 66);
		contentPane.add(panelTitulo);
		
		JLabel lblFolhaDePonto = new JLabel("REGISTRO DA FOLHA DE PONTO");
		lblFolhaDePonto.setHorizontalAlignment(SwingConstants.CENTER);
		lblFolhaDePonto.setFont(new Font("Tahoma", Font.PLAIN, 32));
		panelTitulo.add(lblFolhaDePonto);
		
		JPanel panelInformacao = new JPanel();
		panelInformacao.setBounds(10, 88, 566, 51);
		contentPane.add(panelInformacao);
		panelInformacao.setLayout(null);
		
		JLabel lblEscolhaQualFolha = new JLabel("Escolha M\u00CAS e ANO que deseja visualizar e aperte o bot\u00E3o \"Carregar\": ");
		lblEscolhaQualFolha.setBounds(33, 20, 510, 20);
		lblEscolhaQualFolha.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelInformacao.add(lblEscolhaQualFolha);
		
		JPanel panelDados = new JPanel();
		panelDados.setBounds(10, 150, 566, 51);
		contentPane.add(panelDados);
		panelDados.setLayout(null);
		
		JComboBox comboMes = new JComboBox();
		comboMes.setBounds(71, 12, 85, 22);
		panelDados.add(comboMes);
		comboMes.setModel(new DefaultComboBoxModel(new String[] {"Janeiro", "Fevereiro", "Mar�o", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"}));
		comboMes.setFont(new Font("Tahoma", Font.PLAIN, 13));
		//Setando o mes atual no combo BOx
		comboMes.setSelectedIndex(retornaMes());
		
		JComboBox comboAno = new JComboBox();
		comboAno.setBounds(208, 12, 76, 22);
		panelDados.add(comboAno);
		comboAno.setModel(new DefaultComboBoxModel(new String[] {"2018", "2019", "2020"}));
		comboAno.setFont(new Font("Tahoma", Font.PLAIN, 13));
		comboAno.setSelectedItem(Integer.toString(retornaAno()));
		
		JLabel lblMes = new JLabel("M\u00EAs: ");
		lblMes.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblMes.setBounds(29, 15, 41, 17);
		panelDados.add(lblMes);
		
		JLabel lblAno = new JLabel("Ano: ");
		lblAno.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblAno.setBounds(166, 15, 41, 17);
		panelDados.add(lblAno);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 212, 566, 341);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setFont(new Font("Tahoma", Font.PLAIN, 13));
		table.setModel(new DefaultTableModel(
			new String[][] {
			},
			new String[] {
				"Dia", "Hora de Entrada", "Hora de Saida", "Assinatura"
			}
		));
		
		//Padronizando o JTABLE
		DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
		DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
		DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
		esquerda.setHorizontalAlignment(SwingConstants.LEFT);
		centralizado.setHorizontalAlignment(SwingConstants.CENTER);
		direita.setHorizontalAlignment(SwingConstants.RIGHT);
		
		table.getColumnModel().getColumn(0).setCellRenderer(centralizado);
		table.getColumnModel().getColumn(1).setCellRenderer(centralizado);
		table.getColumnModel().getColumn(2).setCellRenderer(centralizado);
		table.getColumnModel().getColumn(3).setCellRenderer(centralizado);
		
		JButton btnImprimir = new JButton("");
		btnImprimir.setEnabled(false);
		btnImprimir.setIcon(new ImageIcon("Images/icon_impressao.png"));
		btnImprimir.setBounds(493, 0, 63, 40);
		panelDados.add(btnImprimir);
		
		JButton btnCarregar = new JButton("Carregar");
		btnCarregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int mes = (int) comboMes.getSelectedIndex()+1;
				String ano = (String) comboAno.getSelectedItem();
				
				//Limpar a tabelza
				apagaRowJtable(table);
				//PROCURA AS INFORMACOES QUE O USUARIO PEDIU
				if(existeXML(diretorioDoUsuario+mes+"_"+ano+".xml")){
					pontosDiarios.lerDoXML(diretorioDoUsuario+mes+"_"+ano+".xml");
					
					//Atualizando e Ordenando minha cole��o
					pontosDiarios.ordenaLista(pontosDiarios.popularColecao(mes-1, ano), armazenamentoPontos, mes-1, ano);
					pontosDiarios.popularDiasAusente();
					pontosDiarios.preencherJtable(table, mes-1, ano);
					pontosDiarios.salvaEmXML(diretorioDoUsuario+mes+"_"+ano+".xml");
					btnImprimir.setEnabled(true);
				} else {
					JOptionPane.showMessageDialog(null, "Nao foi encontrado nenhum registro na DATA informada");
					btnImprimir.setEnabled(false);
				}
				//RECUPERANDO AS INFORMA�OES SALVAS EM XML E CARREGANDO NA VARI�VEL
				pontosDiarios.lerDoXML(armazenamentoPontos);
			}
		});
		
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//preencherPlanilha(table);
	            //JASPER REPORT
	            Relatorio novoRelatorio = new Relatorio();
	            try {
	            	//Pegando os pontos do XML
	            	int mes = (int) comboMes.getSelectedIndex()+1;
					String ano = (String) comboAno.getSelectedItem();
	            	pontosDiarios.lerDoXML(diretorioDoUsuario+mes+"_"+ano+".xml");
					novoRelatorio.load(pontosDiarios);
					
					//Retomando a lista do m�s atual
					pontosDiarios.lerDoXML(armazenamentoPontos);
					
				} catch (JRException e1) {
					JOptionPane.showMessageDialog(null, "Erro: " + e1);
				}
			}
		});
		
		btnCarregar.setBounds(313, 12, 134, 22);
		panelDados.add(btnCarregar);
		btnCarregar.setFont(new Font("Tahoma", Font.PLAIN, 13));
	}
	
	private void apagaRowJtable(JTable table){
		 DefaultTableModel modeloTable;
		 modeloTable = (DefaultTableModel) table.getModel();
		 
		 //Aqui verifico se a jTable tem algum registo se tiver eu deleto
		 while (modeloTable.getRowCount() > 0) {
			 modeloTable.removeRow(0);
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
	
	private boolean existeXML(String caminhoDoArquivo){
		File arquivo = new File(caminhoDoArquivo);
		if(arquivo.exists()){
		return true;
		} else{
			return false;
		}
	}
	
	protected void preencherPlanilha(JTable table){
		try {
            File arquivo = new File("dados.xls");
            TableModel modelo = table.getModel();
            FileWriter excel = new FileWriter(arquivo);
             for(int i = 0; i < modelo.getColumnCount(); i++){
                excel.write(modelo.getColumnName(i) + "\t");
             }
             excel.write("\n");
             for(int i=0; i< modelo.getRowCount(); i++) {
                for(int j=0; j < modelo.getColumnCount(); j++) {
                    String data = (String)modelo.getValueAt(i, j);
                    if(data == "null"){
                        data="";
                    }
                    excel.write(data+"\t");
                }
                excel.write("\n");
            }
        excel.close();
            JOptionPane.showMessageDialog(null, "Arquivo Criado");
        } catch (IOException ex) 
        {
        	JOptionPane.showMessageDialog(null, "ERRO: "+ex);
        }
	}
}