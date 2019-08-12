package telas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import classes.Usuario;
import coleções.ColUsuarios;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JdialogAlteraSenha extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JPasswordField textSenha;

	/**
	 * Create the dialog.
	 */
	public JdialogAlteraSenha(String login, ColUsuarios usuarios) {
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 376, 197);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblAlterarSenha = new JLabel("ALTERAR SENHA");
		lblAlterarSenha.setHorizontalAlignment(SwingConstants.CENTER);
		lblAlterarSenha.setFont(new Font("Algerian", Font.PLAIN, 16));
		lblAlterarSenha.setBounds(10, 11, 340, 28);
		contentPanel.add(lblAlterarSenha);
		
		JLabel lblNovaSenha = new JLabel("Digite a nova senha: ");
		lblNovaSenha.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNovaSenha.setBounds(10, 70, 130, 28);
		contentPanel.add(lblNovaSenha);
		
		textSenha = new JPasswordField();
		textSenha.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textSenha.setBounds(150, 74, 200, 21);
		contentPanel.add(textSenha);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelar = new JButton("Salvar");
				cancelar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						char [] novaSenha = textSenha.getPassword();
						//Necessário converter para comparar se é uma STRING vazia
						if(String.valueOf(novaSenha).equals(""))
							JOptionPane.showMessageDialog(null, "ERRO: Por favor Defina uma NOVA SENHA");
						else {
							Usuario usuarioLogado = usuarios.retornaUsuario(login);
							usuarios.removeUsuarioDaLista(usuarioLogado);
							usuarioLogado.setSenha(novaSenha);
							usuarios.adicionaUsuarioNaLista(usuarioLogado);
							usuarios.salvaEmXML("armazenamento_xml/ColUsuarios.xml");
							
							usuarioLogado = usuarios.retornaUsuario(login);
							
							if(usuarioLogado.getSenha().equals(novaSenha))
							JOptionPane.showMessageDialog(null, "Senha alterada com sucesso");
							else {
								JOptionPane.showMessageDialog(null, "ERRO.: Senha não alterada");
							}
								
							
						}
					}
				});
				cancelar.setActionCommand("OK");
				buttonPane.add(cancelar);
				getRootPane().setDefaultButton(cancelar);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
