/**@TRE-PB
 * @author Alan Giovanni
 * @version 1.0
 * @since 01 March 2018
 * @CommentLanguage Pt-Br
 * 
 * Esta coleção representa um ARRAY de Usuarios que irão ter acesso ao Ponto eletrônico
 */
package coleções;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.swing.JComboBox;

import classes.Usuario;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

public class ColUsuarios implements Serializable{

	private ArrayList<Usuario> colUsuarios = new ArrayList<Usuario>();
	private static final long serialVersionUID = 1L;
	
	public ColUsuarios(){
		
	}
	
	public ArrayList<Usuario> retornaLista(){
		return colUsuarios;
	}
	public void adicionaUsuarioNaLista(Usuario usuario){
		colUsuarios.add(usuario);
	}
	
	public void removeUsuarioDaLista(Usuario usuario){
		colUsuarios.remove(usuario);
	}
	
	public boolean autenticacao(String login, char[] senha){
		//Necess�rio a convers�o da senha para string antes de testar se a autentica��o do usuario
		String senhaConvertidaRecebida = new String (senha);
		String senhaConvertidaSalvaNaLista;
		for(Usuario user: colUsuarios) {
			senhaConvertidaSalvaNaLista = new String(user.getSenha());
			if(user.getLogin().equals(login) && senhaConvertidaSalvaNaLista.equals(senhaConvertidaRecebida)) {
				return true;
			}
		}
		return false;
	}
	
	public Usuario retornaUsuario(String login){
		for(Usuario user: colUsuarios) {
			if(user.getLogin().equals(login)) {
				return user;
			}
		}
		return null;
	}
	
	public void preencheComboDeUsuarios(JComboBox<String> comboEstagiario){
	    for (Usuario usuario: colUsuarios) {
	    	comboEstagiario.addItem(usuario.getLogin());
	    }
	}
	
	public void salvaEmXML(String localArmazenamento){
		XStream xStream = new XStream(new StaxDriver());
        File arquivo = new File(localArmazenamento);
        xStream.alias("Usuario", Usuario.class);
        FileOutputStream gravar;
        try {
            gravar = new FileOutputStream(arquivo);
            gravar.write(xStream.toXML(colUsuarios).getBytes(StandardCharsets.UTF_8));
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
			xStream.alias("Usuario", Usuario.class);
			xStream.processAnnotations(Usuario.class);
			BufferedInputStream input = new BufferedInputStream(new FileInputStream(localArmazenamento));
			@SuppressWarnings("unchecked")
			ArrayList<Usuario> lista = (ArrayList<Usuario>) xStream.fromXML(input);
			input.close();
			
			this.colUsuarios = lista;
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		return "ColUsuarios [colUsuarios=" + colUsuarios + "]";
	}
}