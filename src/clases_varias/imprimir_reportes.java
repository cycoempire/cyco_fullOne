
package clases_varias;

import java.awt.Frame;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;



/**
 *
 * @author wwwki
 */
public class imprimir_reportes {
    
    
    public void imprimir_factura(Integer id_factura, String sNombreReporte){
         
        
        try {
            
            Map parametro = new HashMap();
                parametro.put("id_doc", id_factura);                                       
                                
                //Aramado del reporte
                JasperReport reporte = JasperCompileManager.compileReport("C:\\CyCo\\Reportes\\"+ sNombreReporte +".jrxml");
                //JasperReport reporte = JasperCompileManager.compileReport("C:\\CyCo\\Reportes\\Factura_servicio.jrxml");
                
                JasperPrint p =  JasperFillManager.fillReport(reporte, parametro, cn);
                JasperViewer ver = new JasperViewer(p,false);
                ver.setExtendedState(Frame.MAXIMIZED_BOTH);
                ver.setVisible(true);
                                                
                       
//            JasperReport reporte = (JasperReport) JRLoader.loadObject("C:\\Users\\wwwki\\OneDrive\\Documents\\NetBeansProjects\\CyCo-Empire\\src\\Reportes\\"+ sNombreReporte +".jasper");
//            Map parametro = new HashMap();            
//            parametro.put("id_fac", id_factura);                                       
//            
//            JasperPrint j = JasperFillManager.fillReport(reporte, parametro, cn);
//                    
//            JasperViewer jv = new JasperViewer(j,false);
//            //jv.setExtendedState(Frame.MAXIMIZED_BOTH);
//            //jv.setTitle("Factura");
//            jv.setVisible(true);
            
//         Map parametro = new HashMap();
//         parametro.put("id_fac", id_factura);
//         
//         //Aramado del reporte
//                JasperReport reporte = JasperCompileManager.compileReport("C:\\Users\\wwwki\\OneDrive\\Documents\\CyCo\\REPORTES\\Factura_Jose.jrxml");
//                JasperPrint p =  JasperFillManager.fillReport(reporte, parametro, cn);
//                JasperViewer ver = new JasperViewer(p,false);
//                ver.setExtendedState(Frame.MAXIMIZED_BOTH);
//                ver.setVisible(true);
                       
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
         
    }
    
    
    public void imprimir_cuentas(Integer id_impre, String sNombreReporte){
         
        
        try {                       
                Map parametro = new HashMap();
                parametro.put("id_impresion", id_impre);                                       
                                
                //Aramado del reporte
                JasperReport reporte = JasperCompileManager.compileReport("C:\\CyCo\\Reportes\\"+ sNombreReporte +".jrxml");
                JasperPrint p =  JasperFillManager.fillReport(reporte, parametro, cn);
                JasperViewer ver = new JasperViewer(p,false);
                ver.setExtendedState(Frame.MAXIMIZED_BOTH);
                ver.setVisible(true);
                                                                                  
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
         
    }

    
    
    
    
    
    conectar cc= new conectar();
    Connection cn= cc.conexion(); 
    
}
