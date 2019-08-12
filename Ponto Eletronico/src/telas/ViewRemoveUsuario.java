package telas;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import classes.Usuario;
import coleções.ColUsuarios;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class ViewRemoveUsuario extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	public ViewRemoveUsuario(ColUsuarios usuarios) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 396, 287);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 363, 45);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblRemoveUsuario = new JLabel("REMOVE USU\u00C1RIO DO SISTEMA");
		lblRemoveUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblRemoveUsuario.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblRemoveUsuario.setBounds(10, 11, 343, 23);
		panel.add(lblRemoveUsuario);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 67, 360, 130);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblSelecioneOUsurio = new JLabel("Selecione o usu\u00E1rio a remover e aperte o bot\u00E3o \"EXCLUIR\"");
		lblSelecioneOUsurio.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSelecioneOUsurio.setBounds(10, 11, 350, 39);
		panel_1.add(lblSelecioneOUsurio);
		
		JComboBox<String> comboEstagiario = new JComboBox<String>();
		comboEstagiario.setBounds(10, 61, 139, 28);
		usuarios.preencheComboDeUsuarios(comboEstagiario);
		panel_1.add(comboEstagiario);
		
		JButton btnExcluir = new JButton("EXCLUIR");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String loginEstagiario = String.valueOf(comboEstagiario.getSelectedItem());
				Usuario usuarioARemover = usuarios.retornaUsuario(loginEstagiario);
				usuarios.removeUsuarioDaLista(usuarioARemover);
				usuarios.salvaEmXML("armazenamento_xml/ColUsuarios.xml");
				try{
					removeDiretorio(loginEstagiario);
					JOptionPane.showMessageDialog(null, "Usuário Removido com Sucesso!");
					//Removendo o usuario
					comboEstagiario.removeItem(loginEstagiario);
				} catch (Exception erro){
					JOptionPane.showMessageDialog(null, "Erro ao EXCLUIR diretório, tente excluir manualmente.\nInformações do Erro: " +erro, "ERRO AO EXCLUIR DIRETORIO", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		btnExcluir.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnExcluir.setBounds(159, 61, 89, 28);
		panel_1.add(btnExcluir);
	}
	
	private void removeDiretorio(String login){
		File diretorio = new File("armazenamento_xml/"+login);
		File [] arquivosDoDiretorio = diretorio.listFiles();
		for(File arquivo : arquivosDoDiretorio){
			arquivo.delete();
		}
		     diretorio.delete();
	}
}
