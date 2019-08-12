package telas;


import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Component;
import java.awt.Font;
import javax.swing.SwingConstants;
import classes.UsuarioAdmin;
import coleções.ColAdministradores;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewAdminCadastro extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textNome;
	private JTextField textLogin;
	private JPasswordField textSenha;

	/**
	 * Create the dialog.
	 */
	public ViewAdminCadastro(ColAdministradores administradores) {
		setBounds(100, 100, 366, 349);
		getContentPane().setLayout(null);
		
		JLabel lblCadastroDeAdministrador = new JLabel("Cadastro de Administrador");
		lblCadastroDeAdministrador.setHorizontalAlignment(SwingConstants.CENTER);
		lblCadastroDeAdministrador.setFont(new Font("Tahoma", Font.BOLD, 21));
		lblCadastroDeAdministrador.setBounds(10, 11, 330, 53);
		getContentPane().add(lblCadastroDeAdministrador);
		
		JLabel lblNomeDoAdministrador = new JLabel("Nome do Administrador");
		lblNomeDoAdministrador.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNomeDoAdministrador.setBounds(10, 75, 330, 28);
		getContentPane().add(lblNomeDoAdministrador);
		
		textNome = new JTextField();
		textNome.setFont(new Font("Tahoma", Font.BOLD, 13));
		textNome.setColumns(10);
		textNome.setBounds(10, 114, 330, 28);
		getContentPane().add(textNome);
		
		JLabel label_2 = new JLabel("Crie um Login");
		label_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_2.setBounds(10, 153, 154, 28);
		getContentPane().add(label_2);
		
		textLogin = new JTextField();
		textLogin.setFont(new Font("Tahoma", Font.BOLD, 13));
		textLogin.setColumns(10);
		textLogin.setBounds(10, 192, 154, 28);
		getContentPane().add(textLogin);
		
		JButton btnCadastrar = new JButton("CADASTRAR");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nome = textNome.getText();
				String login = textLogin.getText();
				char [] senha = textSenha.getPassword();
				
				if(administradores.retornaUsuarioAdmin(login) != null){
					JOptionPane.showMessageDialog(null, "USUARIO "+login+" JÁ ESTÁ CADASTRADO", "ERRO DE CADASTRO", JOptionPane.ERROR_MESSAGE);
				} else{
		            try{
		            	//TENTO CADASTRAR O USUARIO
						UsuarioAdmin novoUsuario = new UsuarioAdmin (login, senha, nome);
						administradores.adicionaUsuarioNaLista(novoUsuario);
						administradores.salvaEmXML("armazenamento_xml/admin/ColAdministradores.xml");
						JOptionPane.showMessageDialog (null, "Sucesso! Administrador Criado com sucesso");
						limparCampos();
					} catch (Exception erro){
						JOptionPane.showMessageDialog (null, "ERRO AO CADASTRAR USUARIO", "ERRO DE ENTRADA" + erro, JOptionPane.ERROR_MESSAGE);
					}
		        }
			}
		});
		btnCadastrar.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnCadastrar.setBounds(10, 260, 330, 39);
		getContentPane().add(btnCadastrar);
		
		JLabel label_3 = new JLabel("Crie uma Senha");
		label_3.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_3.setBounds(186, 153, 154, 28);
		getContentPane().add(label_3);
		
		textSenha = new JPasswordField();
		textSenha.setFont(new Font("Tahoma", Font.BOLD, 13));
		textSenha.setBounds(186, 192, 154, 28);
		getContentPane().add(textSenha);

	}
	protected void limparCampos(){
		for (int i = 0; i < getContentPane().getComponentCount(); i++){
			Component c = getContentPane().getComponent(i);
			if(c instanceof JTextField){
				JTextField campo = (JTextField) c;
				campo.setText(null);
			}
		}
	}
}
