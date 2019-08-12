package telas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Component;

import javax.swing.border.TitledBorder;

import classes.Usuario;
import coleções.ColPontosDiarios;
import coleções.ColUsuarios;

import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;

public class ViewAlteraUsuario extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private char invisivelPassword;
	private String loginEstagiario;
	private Usuario inforEstagiario;
	private JTextField textNome;
	private JTextField textLotacao;
	private JTextField textLogin;
	private JPasswordField textSenha;
	
	public ViewAlteraUsuario(ColUsuarios usuarios) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 548, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAlterarInformaesDo = new JLabel("ALTERAR INFORMA\u00C7\u00D5ES DO ESTAGI\u00C1RIO");
		lblAlterarInformaesDo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblAlterarInformaesDo.setHorizontalAlignment(SwingConstants.CENTER);
		lblAlterarInformaesDo.setBounds(10, 11, 515, 38);
		contentPane.add(lblAlterarInformaesDo);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 60, 515, 50);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblSelecioneOEstagirio = new JLabel("Selecione o estagi\u00E1rio:");
		lblSelecioneOEstagirio.setBounds(10, 11, 149, 27);
		panel.add(lblSelecioneOEstagirio);
		lblSelecioneOEstagirio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JComboBox<String> comboEstagiario = new JComboBox<String>();
		comboEstagiario.setBounds(169, 11, 134, 27);
		panel.add(comboEstagiario);
		//Preenchendo o combo de Usuarios
		usuarios.preencheComboDeUsuarios(comboEstagiario);
		
		//BTN SALVAR
		JButton btnSalvar = new JButton("SALVAR INFORMA\u00C7\u00D5ES");
		JRadioButton rdbSim = new JRadioButton("Sim");
		JRadioButton rdbNao = new JRadioButton("N\u00E3o");
		
		JButton btnCarregar = new JButton("Carregar");
		btnCarregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginEstagiario = (String) comboEstagiario.getSelectedItem();
				inforEstagiario = usuarios.retornaUsuario(loginEstagiario);
				
				textNome.setText(inforEstagiario.getNome());
				textLotacao.setText(inforEstagiario.getLotacao());
				textLogin.setText(inforEstagiario.getLogin());
				
				textNome.setEnabled(true);
				textLotacao.setEnabled(true);
				textLogin.setEnabled(true);
				
				//PERMITO REALIZAR A ALTERAÇÃO
				btnSalvar.setEnabled(true);
			}
		});
		btnCarregar.setBounds(352, 13, 118, 27);
		panel.add(btnCarregar);
		
		JButton btnMostrar = new JButton("Mostrar");
		btnMostrar.setBounds(302, 354, 105, 33);
		contentPane.add(btnMostrar);
		btnMostrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mostraEscondeSenha(btnMostrar);
			}
		});
		btnMostrar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnMostrar.setEnabled(false);
		btnMostrar.setBackground(Color.WHITE);
		
		JLabel lblNovaSenha = new JLabel("Nova Senha:");
		lblNovaSenha.setBounds(10, 357, 78, 27);
		contentPane.add(lblNovaSenha);
		lblNovaSenha.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNovaSenha.setEnabled(false);
		
		rdbSim.setBounds(10, 325, 48, 23);
		contentPane.add(rdbSim);
		
		rdbSim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbNao.setSelected(false);
				textSenha.setEnabled(true);
				btnMostrar.setEnabled(true);
				lblNovaSenha.setEnabled(true);
				
			}
		});
		
		rdbSim.setFont(new Font("Tahoma", Font.PLAIN, 13));
		rdbNao.setBounds(61, 324, 48, 23);
		contentPane.add(rdbNao);
		
		rdbNao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbSim.setSelected(false);
				textSenha.setEnabled(false);
				btnMostrar.setEnabled(false);
				lblNovaSenha.setEnabled(false);
			}
		});
		
		rdbNao.setSelected(true);
		rdbNao.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		JLabel lblAlterarSenha = new JLabel("Alterar Senha?");
		lblAlterarSenha.setBounds(10, 297, 99, 27);
		contentPane.add(lblAlterarSenha);
		lblAlterarSenha.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		textSenha = new JPasswordField();
		textSenha.setBounds(98, 357, 185, 27);
		contentPane.add(textSenha);
		textSenha.setEnabled(false);
		invisivelPassword = textSenha.getEchoChar();
		btnSalvar.setBackground(Color.WHITE);
		btnSalvar.setEnabled(false);
		btnSalvar.setBounds(175, 430, 180, 30);
		contentPane.add(btnSalvar);
		
		JButton btnCancelar = new JButton("CANCELAR");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCancelar.setBounds(435, 430, 97, 30);
		contentPane.add(btnCancelar);
		
		textNome = new JTextField();
		textNome.setFont(new Font("Tahoma", Font.PLAIN, 13));
		textNome.setEnabled(false);
		textNome.setColumns(10);
		textNome.setBounds(98, 162, 427, 27);
		contentPane.add(textNome);
		
		JLabel label = new JLabel("NOME:");
		label.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label.setBounds(10, 162, 48, 27);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("Lota\u00E7\u00E3o: ");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_1.setBounds(10, 200, 78, 27);
		contentPane.add(label_1);
		
		textLotacao = new JTextField();
		textLotacao.setFont(new Font("Tahoma", Font.PLAIN, 13));
		textLotacao.setEnabled(false);
		textLotacao.setColumns(10);
		textLotacao.setBounds(98, 200, 427, 27);
		contentPane.add(textLotacao);
		
		textLogin = new JTextField();
		textLogin.setFont(new Font("Tahoma", Font.PLAIN, 13));
		textLogin.setEnabled(false);
		textLogin.setColumns(10);
		textLogin.setBounds(98, 238, 185, 27);
		contentPane.add(textLogin);
		
		JLabel label_2 = new JLabel("Login:");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_2.setBounds(10, 238, 78, 27);
		contentPane.add(label_2);
		
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				char[] novaSenha = null;
				String nome = textNome.getText();
				String lotacao = textLotacao.getText();
				String login = textLogin.getText();
				String localArmazenamento = "armazenamento_xml/"+login+"/"+retornaMes()+"_"+retornaAno()+".xml";
				String localArmazenamentoUsuario = "armazenamento_xml/ColUsuarios.xml";
				if(nome.equals("") || lotacao.equals("") || login.equals("") || (!rdbNao.isSelected() && !rdbSim.isSelected()))
					JOptionPane.showMessageDialog(null, "ERRO: Por favor preencha todos os campos adequadamente sem deixar nenhum em BRANCO!!");
				else{
					if(rdbSim.isSelected()){
						novaSenha = textSenha.getPassword();
						//Necessário converter para comparar se é uma STRING vazia
						if(String.valueOf(novaSenha).equals(""))
							JOptionPane.showMessageDialog(null, "ERRO: Por favor Defina uma NOVA SENHA para o ESTAGIÁRIO.");
						else {
							inforEstagiario.setLogin(login);
							inforEstagiario.setLotacao(lotacao);
							inforEstagiario.setNome(nome);
							inforEstagiario.setSenha(novaSenha);
							usuarios.salvaEmXML(localArmazenamentoUsuario);
							
							//Trecho necessário para poder setar as novas informações dentro da coleção de pontos
							ColPontosDiarios listaTemporaria = new ColPontosDiarios();
							listaTemporaria.lerDoXML(localArmazenamento);
							listaTemporaria.setAllNomes(nome, login);
							listaTemporaria.setAllLotacao(lotacao);
							listaTemporaria.salvaEmXML(localArmazenamento);
							
							JOptionPane.showMessageDialog(null, "NOVA SENHA definida e Informações de " + nome + " atualizadas com SUCESSO");
							//Limpar todos os campos e setar algumas funcionalidades
							limparCampos();
							btnSalvar.setEnabled(false);
							lblNovaSenha.setEnabled(false);
							textSenha.setText(null);
							textSenha.setEnabled(false);
							rdbNao.setSelected(true);
							rdbSim.setSelected(false);
							btnMostrar.setEnabled(false);
						}
					}
					else {
						inforEstagiario.setLogin(login);
						inforEstagiario.setLotacao(lotacao);
						inforEstagiario.setNome(nome);
						usuarios.salvaEmXML(localArmazenamentoUsuario);
						
						//Trecho necessário para poder setar as novas informações dentro da coleção de pontos
						ColPontosDiarios listaTemporaria = new ColPontosDiarios();
						listaTemporaria.lerDoXML(localArmazenamento);
						listaTemporaria.setAllNomes(nome, login);
						listaTemporaria.setAllLotacao(lotacao);
						listaTemporaria.salvaEmXML(localArmazenamento);
						
						JOptionPane.showMessageDialog(null, "Informações de " + nome + " atualizadas com SUCESSO");
						//Limpar todos os campos e setar algumas funcionalidades
						limparCampos();
						btnSalvar.setEnabled(false);
						lblNovaSenha.setEnabled(false);
						textSenha.setText(null);
						textSenha.setEnabled(false);
						rdbNao.setSelected(true);
						rdbSim.setSelected(false);
						btnMostrar.setEnabled(false);
					}
				}
			}
		});
		
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
	private void limparCampos(){
		for (int i = 0; i < contentPane.getComponentCount(); i++){
			Component c = contentPane.getComponent(i);
			if(c instanceof JTextField){
				JTextField campo = (JTextField) c;
				campo.setText(null);
			}
		}
	}
	private void mostraEscondeSenha(JButton btn){
		if(textSenha.getEchoChar() == '\u0000'){
			btn.setText("Mostrar");
			btn.setFont(new Font("Tahoma", Font.PLAIN, 13));
			textSenha.setEchoChar(invisivelPassword);
		} else {
			textSenha.setEchoChar('\u0000');
			btn.setText("Esconder");
			btn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		}
	}
}
