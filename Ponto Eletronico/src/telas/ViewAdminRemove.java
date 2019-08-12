package telas;


import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;

import classes.UsuarioAdmin;
import coleções.ColAdministradores;

import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewAdminRemove extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ViewAdminRemove(ColAdministradores administradores, String AdminLogado) {
		setTitle("Remover ADM");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 403, 272);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(10, 11, 363, 45);
		getContentPane().add(panel);
		
		JLabel lblRemoveAdministradorDo = new JLabel("REMOVE ADMINISTRADOR DO SISTEMA");
		lblRemoveAdministradorDo.setHorizontalAlignment(SwingConstants.CENTER);
		lblRemoveAdministradorDo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblRemoveAdministradorDo.setBounds(10, 11, 343, 23);
		panel.add(lblRemoveAdministradorDo);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBounds(10, 67, 363, 130);
		getContentPane().add(panel_1);
		
		JLabel lblSelecioneOAdministrador = new JLabel("Selecione o Administrador e aperte o bot\u00E3o \"EXCLUIR\"");
		lblSelecioneOAdministrador.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSelecioneOAdministrador.setBounds(10, 11, 350, 39);
		panel_1.add(lblSelecioneOAdministrador);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(52, 61, 139, 28);
		
		//Preencher o combo
		administradores.preencheComboDeUsuarios(comboBox, AdminLogado);
		panel_1.add(comboBox);
		
		JButton button = new JButton("EXCLUIR");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String loginAdmin = String.valueOf(comboBox.getSelectedItem());
				if(administradores.retornaQuantosAdminTem()>1) {
					UsuarioAdmin usuarioARemover = administradores.retornaUsuarioAdmin(loginAdmin);
					administradores.removeUsuarioDaLista(usuarioARemover);
					administradores.salvaEmXML("armazenamento_xml/admin/ColAdministradores.xml");
					JOptionPane.showMessageDialog(null, "Administrador Excluido do Sistema!");
					comboBox.removeItem(loginAdmin);
				} else
					JOptionPane.showMessageDialog(null, "S� h� um �nico administrador no sistema voc� n�o pode exclu�-lo");
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 13));
		button.setBounds(201, 61, 89, 28);
		panel_1.add(button);

	}

}
