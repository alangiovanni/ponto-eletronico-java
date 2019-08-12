package coleções;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JComboBox;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;


import classes.UsuarioAdmin;

public class ColAdministradores implements Serializable{
	private ArrayList<UsuarioAdmin> colAdministradores = new ArrayList<UsuarioAdmin>();
	private static final long serialVersionUID = 1L;
	
	public ColAdministradores(){
		
	}
	
	public void adicionaUsuarioNaLista(UsuarioAdmin usuarioAdmin){
		colAdministradores.add(usuarioAdmin);
	}
	
	public void removeUsuarioDaLista(UsuarioAdmin usuarioAdmin){
		colAdministradores.remove(usuarioAdmin);
	}
	
	public boolean autenticacao(String login, char[] senha){
		//Necess�rio a convers�o da senha para string antes de testar se a autentica��o do usuario
		String senhaConvertidaRecebida = new String (senha);
		String senhaConvertidaSalvaNaLista;
		for(UsuarioAdmin user: colAdministradores) {
			senhaConvertidaSalvaNaLista = new String(user.getSenha());
			if(user.getLogin().equals(login) && senhaConvertidaSalvaNaLista.equals(senhaConvertidaRecebida)) {
				return true;
			}
		}
		return false;
	}
	
	public UsuarioAdmin retornaUsuarioAdmin(String login){
		for(UsuarioAdmin user: colAdministradores) {
			if(user.getLogin().equals(login)) {
				return user;
			}
		}
		return null;
	}
	
	public void preencheComboDeUsuarios(JComboBox<String> comboBox, String adminLogado){
	    for (UsuarioAdmin usuarioAdmin: colAdministradores) {
	    	if(!(usuarioAdmin.getLogin().equals(adminLogado)))
	    		comboBox.addItem(usuarioAdmin.getLogin());
	    }
	}
	
	public int retornaQuantosAdminTem(){
		int count = 0;
		for (int i = 0; i < colAdministradores.size(); i++) {
			count ++;
		}
		return count;
	}
	
	public void salvaEmXML(String localArmazenamento){
		XStream xStream = new XStream(new StaxDriver());
        File arquivo = new File(localArmazenamento);
        xStream.alias("Usuario_Administrador", UsuarioAdmin.class);
        FileOutputStream gravar;
        try {
            gravar = new FileOutputStream(arquivo);
            gravar.write(xStream.toXML(colAdministradores).getBytes());
            gravar.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
		
	}
	
	public void lerDoXML(String localArmazenamento){
		try {
			XStream xStream = new XStream(new StaxDriver());
			//Quest�es de seguran�a
			XStream.setupDefaultSecurity(xStream);
			xStream.addPermission(AnyTypePermission.ANY); 
			xStream.alias("Usuario_Administrador", UsuarioAdmin.class);
			xStream.processAnnotations(UsuarioAdmin.class);
			BufferedInputStream input = new BufferedInputStream(new FileInputStream(localArmazenamento));
			@SuppressWarnings("unchecked")
			ArrayList<UsuarioAdmin> lista = (ArrayList<UsuarioAdmin>) xStream.fromXML(input);
			input.close();
			
			this.colAdministradores = lista;
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
