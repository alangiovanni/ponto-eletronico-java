package telas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import classes.Usuario;
import classes.UsuarioAdmin;
import coleções.ColAdministradores;
import coleções.ColUsuarios;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.Color;

public class ViewLogin extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textLogin;
	private JPasswordField textSenha;
	private char invisivelPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewLogin frame = new ViewLogin();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ViewLogin() {
		ColUsuarios usuarios = new ColUsuarios();
		ColAdministradores administradores = new ColAdministradores();
		setResizable(false);
		
		//CRIO OS DIRETORIO NECESSÁRIOS
        File pastaInicial = new File("armazenamento_xml/admin");
        pastaInicial.mkdirs();
		
		//BUSCAR OS LOGINS SALVOS ARMAZENADOS
		if(existeXML("armazenamento_xml/ColUsuarios.xml")){
			usuarios.lerDoXML("armazenamento_xml/ColUsuarios.xml");
		}
		
		if(existeXML("armazenamento_xml/admin/ColAdministradores.xml")){
			administradores.lerDoXML("armazenamento_xml/admin/ColAdministradores.xml");
		} else {
			//Cria Admin Padrão
			char [] senha = "admin".toCharArray();
			UsuarioAdmin adminPadrao = new UsuarioAdmin("admin", senha, "Administrador Padrao");
			administradores.adicionaUsuarioNaLista(adminPadrao);
		}
		
		setTitle("Tela de Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//LABEL DO ICONE DE LOGIN ANTIGO
		JLabel lblIconLogin = new JLabel("");
		lblIconLogin.setIcon(new ImageIcon("Images/entrar.png"));
		lblIconLogin.setBounds(225, 27, 212, 200);
		contentPane.add(lblIconLogin);
		
		JLabel lblLogin = new JLabel("Usu\u00E1rio:");
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblLogin.setBounds(141, 241, 62, 27);
		contentPane.add(lblLogin);
		
		textLogin = new JTextField();
		lblLogin.setLabelFor(textLogin);
		textLogin.setHorizontalAlignment(SwingConstants.CENTER);
		textLogin.setBounds(213, 238, 232, 30);
		contentPane.add(textLogin);
		textLogin.setColumns(10);
		
		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setHorizontalAlignment(SwingConstants.CENTER);
		lblSenha.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSenha.setBounds(141, 279, 62, 27);
		contentPane.add(lblSenha);
		
		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.setForeground(new Color(0, 128, 0));
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String login = textLogin.getText();
				char[] senha = textSenha.getPassword();
				
				if(administradores.autenticacao(login, senha)){
						ViewAdminPrincipal obj = new ViewAdminPrincipal(usuarios, administradores, login);
						obj.setLocationRelativeTo(null);
						obj.setVisible(true);
						dispose();
					} else {
						if(usuarios.autenticacao(login, senha)){
						Usuario usuarioLogado = usuarios.retornaUsuario(login);
						String nome = usuarioLogado.getNome();
						String lotacao = usuarioLogado.getLotacao();
						
						ViewPrincipal obj = new ViewPrincipal(nome, lotacao, login, usuarios);
						obj.setLocationRelativeTo(null);
						obj.setVisible(true);
						dispose();
					} else {
						JOptionPane.showMessageDialog (null, "NÃO ENCONTRAMOS ESTE USUARIO E SENHA EM NOSSO SISTEMA","ERRO DE AUTENTICAÇÃO", JOptionPane.ERROR_MESSAGE);
						textLogin.setText("");
						textSenha.setText("");
					}
				}
			}
		});
		
		textSenha = new JPasswordField();
		lblSenha.setLabelFor(textSenha);
		textSenha.setHorizontalAlignment(SwingConstants.CENTER);
		textSenha.setBounds(213, 276, 232, 30);
		//Pegando os caracteres que escondem a senha.
		invisivelPassword = textSenha.getEchoChar();
		contentPane.add(textSenha);
		
		JButton btnMostraEscondeSenha = new JButton("Mostrar");
		btnMostraEscondeSenha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mostraEscondeSenha(btnMostraEscondeSenha);
			}
		});
		btnMostraEscondeSenha.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnMostraEscondeSenha.setBounds(455, 279, 95, 26);
		contentPane.add(btnMostraEscondeSenha);
		btnEntrar.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnEntrar.setBounds(263, 317, 118, 38);
		contentPane.add(btnEntrar);
		
		JLabel lblLogoTRE = new JLabel("");
		lblLogoTRE.setIcon(new ImageIcon("Images/tre-pb_90x90.png"));
		lblLogoTRE.setBounds(5, 6, 95, 90);
		contentPane.add(lblLogoTRE);
		
		JLabel lblDesenvolvedor = new JLabel("Em desenvolvimento por ALAN GIOVANNI (agmtargino@gmail.com.br) - Estagi\u00E1rio da SEINF");
		lblDesenvolvedor.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
		lblDesenvolvedor.setHorizontalAlignment(SwingConstants.LEFT);
		lblDesenvolvedor.setForeground(new Color(255, 255, 0));
		lblDesenvolvedor.setBounds(10, 365, 679, 21);
		contentPane.add(lblDesenvolvedor);
		
		JLabel lblFundo = new JLabel("");
		lblFundo.setHorizontalAlignment(SwingConstants.CENTER);
		lblFundo.setIcon(new ImageIcon("Images/FundoLogin.jpg"));
		lblFundo.setBounds(5, 5, 633, 381);
		contentPane.add(lblFundo);
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
	
	private boolean existeXML(String caminhoDoArquivo){
		File arquivo = new File(caminhoDoArquivo);
		if(arquivo.exists()){
		return true;
		} else{
			return false;
		}
	}
}
