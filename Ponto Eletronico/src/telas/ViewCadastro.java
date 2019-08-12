package telas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import classes.Usuario;
import coleções.ColUsuarios;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class ViewCadastro extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textNome;
	private JTextField textLotacao;
	private JLabel lblLogin;
	private JTextField textLogin;
	private JLabel lblSenha;
	private JButton btnCadastrar;
	private JPasswordField textPassword;

	public ViewCadastro(ColUsuarios usuarios) {
		setResizable(false);
		setTitle("Cadastro de Estagi\u00E1rios");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 493, 421);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCadastroDeUsurio = new JLabel("Cadastro de Usu\u00E1rio");
		lblCadastroDeUsurio.setFont(new Font("Tahoma", Font.BOLD, 21));
		lblCadastroDeUsurio.setHorizontalAlignment(SwingConstants.CENTER);
		lblCadastroDeUsurio.setBounds(10, 11, 460, 53);
		contentPane.add(lblCadastroDeUsurio);
		
		JLabel lblNomeDoEstagiario = new JLabel("Nome do Estagiario: ");
		lblNomeDoEstagiario.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNomeDoEstagiario.setBounds(10, 75, 460, 28);
		contentPane.add(lblNomeDoEstagiario);
		
		textNome = new JTextField();
		textNome.setFont(new Font("Tahoma", Font.BOLD, 13));
		textNome.setBounds(10, 114, 460, 28);
		contentPane.add(textNome);
		textNome.setColumns(10);
		
		JLabel lblLotacao = new JLabel("Lota\u00E7\u00E3o (Ex.: Se\u00E7\u00E3o de Infraestrutura de Rede - SEINF ): ");
		lblLotacao.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblLotacao.setBounds(10, 153, 460, 28);
		contentPane.add(lblLotacao);
		
		textLotacao = new JTextField();
		textLotacao.setFont(new Font("Tahoma", Font.BOLD, 13));
		textLotacao.setColumns(10);
		textLotacao.setBounds(10, 192, 460, 28);
		contentPane.add(textLotacao);
		
		lblLogin = new JLabel("Crie um Login");
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblLogin.setBounds(10, 231, 215, 28);
		contentPane.add(lblLogin);
		
		textLogin = new JTextField();
		textLogin.setFont(new Font("Tahoma", Font.BOLD, 13));
		textLogin.setColumns(10);
		textLogin.setBounds(10, 270, 215, 28);
		contentPane.add(textLogin);
		
		lblSenha = new JLabel("Crie uma Senha");
		lblSenha.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSenha.setBounds(255, 231, 215, 28);
		contentPane.add(lblSenha);
		
		btnCadastrar = new JButton("CADASTRAR");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nome = textNome.getText();
				String lotacao = textLotacao.getText();
				String login = textLogin.getText();
				char [] senha = textPassword.getPassword();
				
				if(usuarios.retornaUsuario(login) != null){
					JOptionPane.showMessageDialog(null, "USUARIO "+login+" JÁ ESTÁ CADASTRADO", "ERRO DE CADASTRO", JOptionPane.ERROR_MESSAGE);
				} else{
					try {
						//CRIO O DIRETORIO COM O LOGIN DO USUARIO DENTRO DA PASTA ARMAZENAMENTO
			            File diretorio = new File("armazenamento_xml/"+login);
			            diretorio.mkdir();
			            try{
			            	//TENTO CADASTRAR O USUARIO
							Usuario novoUsuario = new Usuario (login, senha, nome, lotacao);
							usuarios.adicionaUsuarioNaLista(novoUsuario);
							usuarios.salvaEmXML("armazenamento_xml/ColUsuarios.xml");
							JOptionPane.showMessageDialog (null, "Sucesso! Usuario Cadastrado");
							//Limpa todos os campos
							limparCampos();
						} catch (Exception erro){
							JOptionPane.showMessageDialog (null, "ERRO AO CADASTRAR USUARIO", "ERRO DE ENTRADA" + erro, JOptionPane.ERROR_MESSAGE);
						}
			        } catch (Exception ex) {
			            JOptionPane.showMessageDialog(null, "ERRO DE PERMISSAO AO CRIAR DIRETORIO", "ERRO NA CRIACAO DO DIRETORIO", JOptionPane.ERROR_MESSAGE);
			            System.out.println(ex);
			        }
				}
			}
		});
		
		textPassword = new JPasswordField();
		textPassword.setFont(new Font("Tahoma", Font.BOLD, 13));
		textPassword.setBounds(255, 270, 215, 28);
		contentPane.add(textPassword);
		btnCadastrar.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnCadastrar.setBounds(10, 309, 460, 39);
		contentPane.add(btnCadastrar);
	}
	
	protected void limparCampos(){
		for (int i = 0; i < contentPane.getComponentCount(); i++){
			Component c = contentPane.getComponent(i);
			if(c instanceof JTextField){
				JTextField campo = (JTextField) c;
				campo.setText(null);
			}
		}
	}

}
