package JasperReport;

import java.io.InputStream;
import java.util.ArrayList;

import classes.PontoDiario;
import coleções.ColPontosDiarios;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class Relatorio {
	public void load(ColPontosDiarios pontosDiarios) throws JRException{
		InputStream arquivoJasper = Relatorio.class.getClassLoader().getResourceAsStream("JasperReport/reports/FolhaDePontoOficial.jasper");
		ArrayList<PontoDiario> listaPonto = pontosDiarios.retornaLista();
		JasperPrint jasperPrint = JasperFillManager.fillReport(arquivoJasper, null, new JRBeanCollectionDataSource(listaPonto));
		JasperViewer.viewReport(jasperPrint, false);
	}
	
	public void pdf(ColPontosDiarios pontosDiarios, String nomeDoArquivo) throws JRException{
		InputStream arquivoJasper = Relatorio.class.getClassLoader().getResourceAsStream("JasperReport/reports/FolhaDePontoOficial.jasper");
		ArrayList<PontoDiario> listaPonto = pontosDiarios.retornaLista();
		JasperPrint jasperPrint = JasperFillManager.fillReport(arquivoJasper, null, new JRBeanCollectionDataSource(listaPonto));
		//Nome do arquivo com extens�o PDF
		JasperExportManager.exportReportToPdfFile(jasperPrint, nomeDoArquivo);
	}
}

