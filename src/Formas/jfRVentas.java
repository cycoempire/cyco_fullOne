/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formas;

import Animacion.Fade;
import clases_varias.Controlador_impresiones;
import clases_varias.Exportar_excel;
import clases_varias.conectar;
import clases_varias.idex_combo;
import clases_varias.imprimir_reportes;
import clases_varias.user_logged;
import clases_varias.varias_funciones;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author wwwki
 */
public class jfRVentas extends javax.swing.JFrame {
     Integer id_serie =0;
     Integer id_Cliente =0;
     Integer id_Producto=0;
     Integer iEstadoFac=0;
     Integer iTipoFactura=0;  
     
     
     conectar cc = new conectar();
    
    /**
     * Creates new form jfRVentas
     */
    public jfRVentas() {
        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("/Iconos/bender_cyco.png")).getImage());
        this.setLocationRelativeTo(null);
        
         //La tabla no se autoresizable
        jtableVentas.setAutoResizeMode(jtableVentas.AUTO_RESIZE_OFF); 
                
        idex_combo combo = new idex_combo();
        combo.llenarCombo(jcboTipoDoc, "id_tipo_doc", "ttipo_documento", "txt_descripcion");                          
        combo.llenarCombo(jcboTipoVenta, "id_tipoF", "ttipo_factura", "txt_desc");                      
        combo.llenarCombo(jcomboEstado, "id_estado", "testado", "txt_desc");      
        
        
        buscarClienteAutoC();
    }
    
    
    void buscar(Integer tipo_bus){
    
        String ssql;
        
        try {
            Connection cn = cc.conexion();
            if(tipo_bus == 1){
            
                DefaultTableModel tventas = new DefaultTableModel();
                tventas.addColumn("id_doc");
                tventas.addColumn("FECHA DOC.");
                tventas.addColumn("TIPO DOC.");
                tventas.addColumn("TIPO VENTA");
                tventas.addColumn("SERIE");
                tventas.addColumn("NO. DOC");                
                tventas.addColumn("TOTAL");
                tventas.addColumn("N.I.T.");
                tventas.addColumn("CLIENTE");
                tventas.addColumn("ESTADO");
                tventas.addColumn("USUARIO");
                tventas.addColumn("id_tipo_doc");
                tventas.addColumn("id_tipo_venta");
                tventas.addColumn("sn_estado");
                jtableVentas.setModel(tventas);
                                                                           
            //id_doc
            jtableVentas.getColumnModel().getColumn(0).setMaxWidth(0);
            jtableVentas.getColumnModel().getColumn(0).setMinWidth(0);
            jtableVentas.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
            jtableVentas.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
        
            //FECHA DOC.
            jtableVentas.getColumnModel().getColumn(1).setMaxWidth(100);
            jtableVentas.getColumnModel().getColumn(1).setMinWidth(100);
            jtableVentas.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(100);
            jtableVentas.getTableHeader().getColumnModel().getColumn(1).setMinWidth(100);
            
            //TIPO DOC.
            jtableVentas.getColumnModel().getColumn(2).setMaxWidth(100);
            jtableVentas.getColumnModel().getColumn(2).setMinWidth(100);
            jtableVentas.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(100);
            jtableVentas.getTableHeader().getColumnModel().getColumn(2).setMinWidth(100);
            
            //TIPO VENTA
            jtableVentas.getColumnModel().getColumn(3).setMaxWidth(100);
            jtableVentas.getColumnModel().getColumn(3).setMinWidth(100);
            jtableVentas.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(100);
            jtableVentas.getTableHeader().getColumnModel().getColumn(3).setMinWidth(100);
            
            
            //SERIE
            jtableVentas.getColumnModel().getColumn(4).setMaxWidth(100);
            jtableVentas.getColumnModel().getColumn(4).setMinWidth(100);
            jtableVentas.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(100);
            jtableVentas.getTableHeader().getColumnModel().getColumn(4).setMinWidth(100);
            
            //NO. DOC
            jtableVentas.getColumnModel().getColumn(5).setMaxWidth(100);
            jtableVentas.getColumnModel().getColumn(5).setMinWidth(100);
            jtableVentas.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(100);
            jtableVentas.getTableHeader().getColumnModel().getColumn(5).setMinWidth(100);
            
            //TOTAL
            jtableVentas.getColumnModel().getColumn(6).setMaxWidth(100);
            jtableVentas.getColumnModel().getColumn(6).setMinWidth(100);
            jtableVentas.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(100);
            jtableVentas.getTableHeader().getColumnModel().getColumn(6).setMinWidth(125);
                                   
            //N.I.T.
            jtableVentas.getColumnModel().getColumn(7).setMaxWidth(100);
            jtableVentas.getColumnModel().getColumn(7).setMinWidth(100);
            jtableVentas.getTableHeader().getColumnModel().getColumn(7).setMaxWidth(100);
            jtableVentas.getTableHeader().getColumnModel().getColumn(7).setMinWidth(100);
            
            //CLIENTE
            jtableVentas.getColumnModel().getColumn(8).setMaxWidth(250);
            jtableVentas.getColumnModel().getColumn(8).setMinWidth(250);
            jtableVentas.getTableHeader().getColumnModel().getColumn(8).setMaxWidth(250);
            jtableVentas.getTableHeader().getColumnModel().getColumn(8).setMinWidth(250);                                                            
            
             //ESTADO
            jtableVentas.getColumnModel().getColumn(9).setMaxWidth(100);
            jtableVentas.getColumnModel().getColumn(9).setMinWidth(100);
            jtableVentas.getTableHeader().getColumnModel().getColumn(9).setMaxWidth(100);
            jtableVentas.getTableHeader().getColumnModel().getColumn(9).setMinWidth(100);       
            
             //USUARIO
            jtableVentas.getColumnModel().getColumn(10).setMaxWidth(100);
            jtableVentas.getColumnModel().getColumn(10).setMinWidth(100);
            jtableVentas.getTableHeader().getColumnModel().getColumn(10).setMaxWidth(100);
            jtableVentas.getTableHeader().getColumnModel().getColumn(10).setMinWidth(100);       
            
             //id_tipo_doc
            jtableVentas.getColumnModel().getColumn(11).setMaxWidth(0);
            jtableVentas.getColumnModel().getColumn(11).setMinWidth(0);
            jtableVentas.getTableHeader().getColumnModel().getColumn(11).setMaxWidth(0);
            jtableVentas.getTableHeader().getColumnModel().getColumn(11).setMinWidth(0);       
            
             //id_tipo_venta
            jtableVentas.getColumnModel().getColumn(12).setMaxWidth(0);
            jtableVentas.getColumnModel().getColumn(12).setMinWidth(0);
            jtableVentas.getTableHeader().getColumnModel().getColumn(12).setMaxWidth(0);
            jtableVentas.getTableHeader().getColumnModel().getColumn(12).setMinWidth(0);  
            
            //sn_estado
            jtableVentas.getColumnModel().getColumn(13).setMaxWidth(0);
            jtableVentas.getColumnModel().getColumn(13).setMinWidth(0);
            jtableVentas.getTableHeader().getColumnModel().getColumn(13).setMaxWidth(0);
            jtableVentas.getTableHeader().getColumnModel().getColumn(13).setMinWidth(0); 
            
            
            ssql = "";
            ssql = ssql + " select tv.id_doc, tv.fecha_doc, tdoc.txt_descripcion, tipof.txt_desc, tv.no_serie_doc, ";
            ssql = ssql + " tv.no_doc, tv.total_factura,tcli.txt_nit, tcli.txt_nombre,  tes.txt_desc, tusu.txt_nombre_usu,";
            ssql = ssql + " tv.id_tipo_doc, tv.id_tipo_venta, tv.sn_estado";
            ssql = ssql + " from tventa tv";
            ssql = ssql + " inner join ttipo_documento tdoc on tdoc.id_tipo_doc = tv.id_tipo_doc";
            ssql = ssql + " inner join ttipo_factura tipof on tipof.id_tipof = tv.id_tipo_venta";
            ssql = ssql + " inner join tcliente tcli on tcli.id_cliente = tv.id_cliente";
            ssql = ssql + " inner join testado tes on tes.id_estado = tv.sn_estado";
            ssql = ssql + " inner join tusuario tusu on tusu.id_usuario = tv.id_usu_reg";
            ssql = ssql + " where tv.id_doc <> 0";
                                                
            if(jrFechas.isSelected()){
                varias_funciones vr = new varias_funciones();
                String fecha_desde = vr.formatoFechaTipo3(jtfDesde.getText());
                String fecha_hasta = vr.formatoFechaTipo3(jtfHasta.getText());
                ssql = ssql + " and tv.fecha_doc between '" + fecha_desde + "' and '" + fecha_hasta + "'" ;                  
            }                                                           
           
            if(jchkTipoDoc.isSelected()){
                ssql = ssql + " and tv.id_tipo_doc = "+ jcboTipoDoc.getItemAt(jcboTipoDoc.getSelectedIndex()).getId();
            }
            
            if(jchkTipoVenta.isSelected()){
                ssql = ssql + " and tv.id_tipo_venta = " + jcboTipoVenta.getItemAt(jcboTipoVenta.getSelectedIndex()).getId();
            }
            
            if(jchkSerie.isSelected()){
                ssql = ssql + " and tv.no_serie_doc = '" + jcomboSerie.getItemAt(jcomboSerie.getSelectedIndex()).getDesc() + "'";            
            }
            
            if(jrNoDoc.isSelected()){
                ssql = ssql + " and tv.no_doc = '" + jtxtNoDoc.getText() + "'";
            }
            
            if(jchkEstado.isSelected()){
                ssql = ssql + " and tv.sn_estado = " + jcomboEstado.getItemAt(jcomboEstado.getSelectedIndex()).getId();
            }
            
            if(jrNit.isSelected()){
                ssql = ssql + " and tcli.txt_nit = '" + jtxtNoFac.getText() + "'";
            }                                    
            ssql = ssql + " order by tv.fecha_doc ";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            String datos[] = new String [14];
            while(rs.next()){
                varias_funciones vr = new varias_funciones();
                datos[0] = rs.getString(1);
                datos[1] = vr.MostrarformatoFecha(rs.getString(2));
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                datos[6] = rs.getString(7);
                datos[7] = rs.getString(8);
                datos[8] = rs.getString(9);
                datos[9] = rs.getString(10);
                datos[10] = rs.getString(11);
                datos[11] = rs.getString(12);
                datos[12] = rs.getString(13);
                datos[13] = rs.getString(14);                
                tventas.addRow(datos);                
            }
            jtableVentas.setModel(tventas);  
            rs.close();
            st.close();
            }else{            
                DefaultTableModel tventas = new DefaultTableModel();
                tventas.addColumn("id_doc");
                tventas.addColumn("FECHA");
                tventas.addColumn("TIPO DOC");
                tventas.addColumn("TIPO VENTA");
                tventas.addColumn("SERIE");
                tventas.addColumn("NO DOC");
                tventas.addColumn("DESCRIPCION");
                tventas.addColumn("CANTIDAD");
                tventas.addColumn("PRECIO");
                tventas.addColumn("SUB TOTAL");
                tventas.addColumn("NIT");
                tventas.addColumn("CLIENTE");
                tventas.addColumn("ESTADO");
                tventas.addColumn("USUARIO");
                tventas.addColumn("id_tipo_doc");
                tventas.addColumn("id_tipo_venta");
                tventas.addColumn("sn_estado");                                
                jtableVentas.setModel(tventas);
                                	 	 	 	                
                
                //id_doc
                jtableVentas.getColumnModel().getColumn(0).setMaxWidth(0);
                jtableVentas.getColumnModel().getColumn(0).setMinWidth(0);
                jtableVentas.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
                jtableVentas.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
                
                
                //FECHA
                jtableVentas.getColumnModel().getColumn(1).setMaxWidth(100);
                jtableVentas.getColumnModel().getColumn(1).setMinWidth(100);
                jtableVentas.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(100);
                jtableVentas.getTableHeader().getColumnModel().getColumn(1).setMinWidth(100);
                
                
                //TIPO DOC
                jtableVentas.getColumnModel().getColumn(2).setMaxWidth(70);
                jtableVentas.getColumnModel().getColumn(2).setMinWidth(70);
                jtableVentas.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(70);
                jtableVentas.getTableHeader().getColumnModel().getColumn(2).setMinWidth(70);
                
                
                //TIPO VENTA
                jtableVentas.getColumnModel().getColumn(3).setMaxWidth(100);
                jtableVentas.getColumnModel().getColumn(3).setMinWidth(100);
                jtableVentas.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(100);
                jtableVentas.getTableHeader().getColumnModel().getColumn(3).setMinWidth(100);
                
                //SERIE
                jtableVentas.getColumnModel().getColumn(4).setMaxWidth(70);
                jtableVentas.getColumnModel().getColumn(4).setMinWidth(70);
                jtableVentas.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(70);
                jtableVentas.getTableHeader().getColumnModel().getColumn(4).setMinWidth(70);
                
                //NO DOC
                jtableVentas.getColumnModel().getColumn(5).setMaxWidth(70);
                jtableVentas.getColumnModel().getColumn(5).setMinWidth(70);
                jtableVentas.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(70);
                jtableVentas.getTableHeader().getColumnModel().getColumn(5).setMinWidth(70);
                
                //DESCRIPCION
                jtableVentas.getColumnModel().getColumn(6).setMaxWidth(300);
                jtableVentas.getColumnModel().getColumn(6).setMinWidth(300);
                jtableVentas.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(300);
                jtableVentas.getTableHeader().getColumnModel().getColumn(6).setMinWidth(300);
                
                //CANTIDAD
                jtableVentas.getColumnModel().getColumn(7).setMaxWidth(70);
                jtableVentas.getColumnModel().getColumn(7).setMinWidth(70);
                jtableVentas.getTableHeader().getColumnModel().getColumn(7).setMaxWidth(70);
                jtableVentas.getTableHeader().getColumnModel().getColumn(7).setMinWidth(70);
                
                //PRECIO
                jtableVentas.getColumnModel().getColumn(8).setMaxWidth(70);
                jtableVentas.getColumnModel().getColumn(8).setMinWidth(70);
                jtableVentas.getTableHeader().getColumnModel().getColumn(8).setMaxWidth(70);
                jtableVentas.getTableHeader().getColumnModel().getColumn(8).setMinWidth(70);
                
                //SUB TOTAL
                jtableVentas.getColumnModel().getColumn(9).setMaxWidth(80);
                jtableVentas.getColumnModel().getColumn(9).setMinWidth(80);
                jtableVentas.getTableHeader().getColumnModel().getColumn(9).setMaxWidth(80);
                jtableVentas.getTableHeader().getColumnModel().getColumn(9).setMinWidth(80);
                
                //NIT
                jtableVentas.getColumnModel().getColumn(10).setMaxWidth(100);
                jtableVentas.getColumnModel().getColumn(10).setMinWidth(100);
                jtableVentas.getTableHeader().getColumnModel().getColumn(10).setMaxWidth(100);
                jtableVentas.getTableHeader().getColumnModel().getColumn(10).setMinWidth(100);
                
                //CLIENTE
                jtableVentas.getColumnModel().getColumn(11).setMaxWidth(200);
                jtableVentas.getColumnModel().getColumn(11).setMinWidth(200);
                jtableVentas.getTableHeader().getColumnModel().getColumn(11).setMaxWidth(200);
                jtableVentas.getTableHeader().getColumnModel().getColumn(11).setMinWidth(200);
                
                //ESTADO
                jtableVentas.getColumnModel().getColumn(12).setMaxWidth(100);
                jtableVentas.getColumnModel().getColumn(12).setMinWidth(100);
                jtableVentas.getTableHeader().getColumnModel().getColumn(12).setMaxWidth(100);
                jtableVentas.getTableHeader().getColumnModel().getColumn(12).setMinWidth(100);
                
                //USUARIO
                jtableVentas.getColumnModel().getColumn(13).setMaxWidth(80);
                jtableVentas.getColumnModel().getColumn(13).setMinWidth(80);
                jtableVentas.getTableHeader().getColumnModel().getColumn(13).setMaxWidth(80);
                jtableVentas.getTableHeader().getColumnModel().getColumn(13).setMinWidth(80);
                
                //id_tipo_doc
                jtableVentas.getColumnModel().getColumn(14).setMaxWidth(0);
                jtableVentas.getColumnModel().getColumn(14).setMinWidth(0);
                jtableVentas.getTableHeader().getColumnModel().getColumn(14).setMaxWidth(0);
                jtableVentas.getTableHeader().getColumnModel().getColumn(14).setMinWidth(0);
                
                //id_tipo_venta
                jtableVentas.getColumnModel().getColumn(15).setMaxWidth(0);
                jtableVentas.getColumnModel().getColumn(15).setMinWidth(0);
                jtableVentas.getTableHeader().getColumnModel().getColumn(15).setMaxWidth(0);
                jtableVentas.getTableHeader().getColumnModel().getColumn(15).setMinWidth(0);
                
                //sn_estado
                jtableVentas.getColumnModel().getColumn(16).setMaxWidth(0);
                jtableVentas.getColumnModel().getColumn(16).setMinWidth(0);
                jtableVentas.getTableHeader().getColumnModel().getColumn(16).setMaxWidth(0);
                jtableVentas.getTableHeader().getColumnModel().getColumn(16).setMinWidth(0);
                
                ssql = "";
                ssql = ssql + " select  tved.id_doc, tv.fecha_doc, ttipo.txt_descripcion, tipoven.txt_desc,";
                ssql = ssql + " tv.no_serie_doc,tv.no_doc, tprod.txt_descripcion, tved.cnt_producto, tpre.dPventa,";
                ssql = ssql + " tved.sub_total_venta, tcli.txt_nit, tcli.txt_nombre, tes.txt_desc, tusu.txt_nombre_usu,";
                ssql = ssql + " tv.id_tipo_doc, tv.id_tipo_venta,tv.sn_estado";
                ssql = ssql + " from tventa_detalle tved";
                ssql = ssql + " inner join tventa tv on tv.id_doc = tved.id_doc";
                ssql = ssql + " inner join ttipo_documento ttipo on ttipo.id_tipo_doc = tv.id_tipo_doc";
                ssql = ssql + " inner join ttipo_factura tipoven on tipoven.id_tipof = tv.id_tipo_venta";
                ssql = ssql + " inner join tproducto tprod on tprod.id_producto = tved.id_producto";
                ssql = ssql + " inner join tprecio tpre on tpre.id_precio = tved.id_precio";
                ssql = ssql + " inner join tcliente tcli on tcli.id_cliente = tv.id_cliente";
                ssql = ssql + " inner join tusuario tusu on tusu.id_usuario = tv.id_usu_reg";
                ssql = ssql + " inner join testado tes on tes.id_estado = tv.sn_estado";                
                ssql = ssql + " where tved.id_doc <> 0";                                                                			                                                                                                                                                                
                                                                
            if(jrFechas.isSelected()){
                varias_funciones vr = new varias_funciones();
                String fecha_desde = vr.formatoFechaTipo3(jtfDesde.getText());
                String fecha_hasta = vr.formatoFechaTipo3(jtfHasta.getText());
                ssql = ssql + " and tv.fecha_doc between '" + fecha_desde + "' and '" + fecha_hasta + "'" ;                  
            }                                                           
           
            if(jchkTipoDoc.isSelected()){
                ssql = ssql + " and tv.id_tipo_doc = "+ jcboTipoDoc.getItemAt(jcboTipoDoc.getSelectedIndex()).getId();
            }
            
            if(jchkTipoVenta.isSelected()){
                ssql = ssql + " and tv.id_tipo_venta = " + jcboTipoVenta.getItemAt(jcboTipoVenta.getSelectedIndex()).getId();
            }
            
            if(jchkSerie.isSelected()){
                ssql = ssql + " and tv.no_serie_doc = '" + jcomboSerie.getItemAt(jcomboSerie.getSelectedIndex()).getDesc() + "'";            
            }
            
            if(jrNoDoc.isSelected()){
                ssql = ssql + " and tv.no_doc = '" + jtxtNoDoc.getText() + "'";
            }
            
            if(jchkEstado.isSelected()){
                ssql = ssql + " and tv.sn_estado = " + jcomboEstado.getItemAt(jcomboEstado.getSelectedIndex()).getId();
            }
            
            if(jrNit.isSelected()){
                ssql = ssql + " and tcli.txt_nit = '" + jtxtNoFac.getText() + "'";
            }
                                                                                                                
                ssql = ssql + " union all";
                ssql = ssql + " select  tved.id_fac, tvv.fecha_doc, ttipo.txt_descripcion, tipoven.txt_desc, tvv.no_serie_doc, tvv.no_doc, tved.txt_desc_serv, tved.cantidad,";
                ssql = ssql + " tved.precio, tved.sub_total, tcli.txt_nit, tcli.txt_nombre, tes.txt_desc, tusu.txt_nombre_usu, tvv.id_tipo_doc,tvv.id_tipo_venta, tvv.sn_estado";
                ssql = ssql + " from tservicio tved";
                ssql = ssql + " inner join tventa tvv on tvv.id_doc = tved.id_fac";
                ssql = ssql + " inner join ttipo_documento ttipo on ttipo.id_tipo_doc = tvv.id_tipo_doc";
                ssql = ssql + " inner join ttipo_factura tipoven on tipoven.id_tipof = tvv.id_tipo_venta";
                ssql = ssql + " inner join tcliente tcli on tcli.id_cliente = tvv.id_cliente";
                ssql = ssql + " inner join tusuario tusu on tusu.id_usuario = tvv.id_usu_reg";
                ssql = ssql + " inner join testado tes on tes.id_estado = tvv.sn_estado";
                ssql = ssql + " where tved.id_fac <> 0";
                
                                                                
            if(jrFechas.isSelected()){
                varias_funciones vr = new varias_funciones();
                String fecha_desde = vr.formatoFechaTipo3(jtfDesde.getText());
                String fecha_hasta = vr.formatoFechaTipo3(jtfHasta.getText());
                ssql = ssql + " and tvv.fecha_doc between '" + fecha_desde + "' and '" + fecha_hasta + "'" ;                  
            }                                                           
           
            if(jchkTipoDoc.isSelected()){
                ssql = ssql + " and tvv.id_tipo_doc = "+ jcboTipoDoc.getItemAt(jcboTipoDoc.getSelectedIndex()).getId();
            }
            
            if(jchkTipoVenta.isSelected()){
                ssql = ssql + " and tvv.id_tipo_venta = " + jcboTipoVenta.getItemAt(jcboTipoVenta.getSelectedIndex()).getId();
            }
            
            if(jchkSerie.isSelected()){
                ssql = ssql + " and tvv.no_serie_doc = '" + jcomboSerie.getItemAt(jcomboSerie.getSelectedIndex()).getDesc() + "'";            
            }
            
            if(jrNoDoc.isSelected()){
                ssql = ssql + " and tvv.no_doc = '" + jtxtNoDoc.getText() + "'";
            }
            
            if(jchkEstado.isSelected()){
                ssql = ssql + " and tvv.sn_estado = " + jcomboEstado.getItemAt(jcomboEstado.getSelectedIndex()).getId();
            }
            
            if(jrNit.isSelected()){
                ssql = ssql + " and tcli.txt_nit = '" + jtxtNoFac.getText() + "'";
            }
                                                                                                                                                                                
        
//                ssql = ssql + " order by tff.fecha_factura";
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(ssql);
                String datos[] = new String[17];
                 while(rs.next()){
                    varias_funciones vr = new varias_funciones();
                    datos[0] = rs.getString(1);
                    datos[1] = vr.MostrarformatoFecha(rs.getString(2));
                    datos[2] = rs.getString(3);
                    datos[3] = rs.getString(4);
                    datos[4] = rs.getString(5);
                    datos[5] = rs.getString(6);
                    datos[6] = rs.getString(7);
                    datos[7] = rs.getString(8);
                    datos[8] = rs.getString(9);
                    datos[9] = rs.getString(10);
                    datos[10] = rs.getString(11);
                    datos[11] = rs.getString(12);                    
                    datos[12] = rs.getString(13);                                                                           
                    datos[13] = rs.getString(14);
                    datos[14] = rs.getString(15);
                    datos[15] = rs.getString(16);
                    datos[16] = rs.getString(17);
                    tventas.addRow(datos);                
                }
                jtableVentas.setModel(tventas); 
                rs.close();
                st.close();
            }              
            cn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar tabla en: " +e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
        }   
    }
    
    
    void anulaFac(){
        
        try {            
            if(jtableVentas.getSelectedRow() >=0){
                 Integer iDf = Integer.parseInt(String.valueOf(jtableVentas.getValueAt(jtableVentas.getSelectedRow(),0)));                 
                jfAnulaFactura af = new jfAnulaFactura();                
                af.recuperaFac(iDf);
                af.lblIdFac.setText(iDf.toString());
                af.setVisible(true);                                                        
            }else{
                JOptionPane.showMessageDialog(null, "Favor seleccione una factura en la tabla para anular", "CyCo", JOptionPane.INFORMATION_MESSAGE);            
                
            }                                    
        } catch (Exception e) {
        }        
    }
    
    void buscarClienteAutoC(){
    String ssql;
    
        try {
            Connection cn = cc.conexion();
            ssql = "";
            ssql = ssql + " select * from ";
            ssql = ssql + " tcliente ";
            ssql = ssql + " where sn_activo = 1";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            TextAutoCompleter nombreCli = new TextAutoCompleter(jtxtCliente);
            while(rs.next()){
                 nombreCli.addItem(rs.getString("txt_nombre"));
            }
            nombreCli.setMode(0); // infijo
             rs.close();
             st.close();
             cn.close();
        } catch (Exception e) {
        
        }
    }
    
    void mostrarId(String sTabla, String sCampoMostrar, String sWhere, String sCampoCompara, Integer id_captura){    
                varias_funciones vr = new varias_funciones();
                //int id = vr.recupId("tserie_fac", "id_serie", "txt_serie", jtxtSerie.getText());
                int id = vr.recupId(sTabla,sCampoMostrar,sWhere,sCampoCompara);
                                
                switch (id_captura){
                    
                    case 1: id_serie = id;
                            break;
                    case 2: id_Cliente = id;
                            break;
                    case 3: id_Producto = id;
                            break;                                
                }                         
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        lblMinimizar = new javax.swing.JLabel();
        lblSalir = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtableVentas = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jPanel2 = new javax.swing.JPanel();
        jchkEstado = new javax.swing.JCheckBox();
        jcomboEstado = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jSeparator5 = new javax.swing.JSeparator();
        jrCliente = new javax.swing.JRadioButton();
        jtxtCliente = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jchkSerie = new javax.swing.JCheckBox();
        jcomboSerie = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jSeparator6 = new javax.swing.JSeparator();
        jtxtNoFac = new javax.swing.JTextField();
        jrNit = new javax.swing.JRadioButton();
        jPanel6 = new javax.swing.JPanel();
        jchkTipoVenta = new javax.swing.JCheckBox();
        jcboTipoVenta = new javax.swing.JComboBox<>();
        jbtnBuscar = new javax.swing.JButton();
        jbtnExportar = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jchkDetalle = new javax.swing.JCheckBox();
        jbtnAnularFac = new javax.swing.JButton();
        jbtnMostrarDesc = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jtfDesde = new javax.swing.JFormattedTextField();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jrFechas = new javax.swing.JRadioButton();
        jtfHasta = new javax.swing.JFormattedTextField();
        jbtnBitacora = new javax.swing.JButton();
        jbtnMosDesc = new javax.swing.JButton();
        jbtnImprimirFac = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jchkTipoDoc = new javax.swing.JCheckBox();
        jcboTipoDoc = new javax.swing.JComboBox<>();
        jPanel9 = new javax.swing.JPanel();
        jSeparator9 = new javax.swing.JSeparator();
        jtxtNoDoc = new javax.swing.JTextField();
        jrNoDoc = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(41, 42, 44));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator1.setBackground(java.awt.Color.cyan);
        jSeparator1.setForeground(java.awt.Color.cyan);
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 1180, 10));

        lblMinimizar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMinimizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/minimizar32.png"))); // NOI18N
        lblMinimizar.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lblMinimizarMouseMoved(evt);
            }
        });
        lblMinimizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblMinimizarMouseExited(evt);
            }
        });
        jPanel1.add(lblMinimizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 10, 50, 40));

        lblSalir.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cerrar32.png"))); // NOI18N
        lblSalir.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lblSalirMouseMoved(evt);
            }
        });
        lblSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSalirMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSalirMouseExited(evt);
            }
        });
        jPanel1.add(lblSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(1140, 10, 50, 40));

        jLabel1.setFont(new java.awt.Font("Corbel", 0, 28)); // NOI18N
        jLabel1.setForeground(java.awt.Color.gray);
        jLabel1.setText("Reporte de Ventas");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jtableVentas.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jtableVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jtableVentas.setRowHeight(25);
        jScrollPane1.setViewportView(jtableVentas);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 1170, 290));

        jPanel2.setBackground(new java.awt.Color(41, 42, 44));
        jPanel2.setBorder(new javax.swing.border.LineBorder(java.awt.Color.cyan, 1, true));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jchkEstado.setBackground(new java.awt.Color(41, 42, 44));
        jchkEstado.setFont(new java.awt.Font("Corbel", 0, 14)); // NOI18N
        jchkEstado.setForeground(java.awt.Color.cyan);
        jchkEstado.setText("Por Estado");
        jPanel2.add(jchkEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        jcomboEstado.setBackground(new java.awt.Color(41, 42, 44));
        jcomboEstado.setFont(new java.awt.Font("Corbel", 0, 14)); // NOI18N
        jPanel2.add(jcomboEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 140, 30));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 80, 160, 70));

        jPanel3.setBackground(new java.awt.Color(41, 42, 44));
        jPanel3.setBorder(new javax.swing.border.LineBorder(java.awt.Color.cyan, 1, true));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel3.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 870, 10));

        jrCliente.setBackground(new java.awt.Color(41, 42, 44));
        jrCliente.setFont(new java.awt.Font("Corbel", 0, 14)); // NOI18N
        jrCliente.setForeground(java.awt.Color.cyan);
        jrCliente.setText("Por Cliente:");
        jrCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrClienteActionPerformed(evt);
            }
        });
        jPanel3.add(jrCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 8, -1, 20));

        jtxtCliente.setBackground(new java.awt.Color(41, 42, 44));
        jtxtCliente.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jtxtCliente.setForeground(new java.awt.Color(232, 251, 244));
        jtxtCliente.setBorder(null);
        jtxtCliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtClienteFocusLost(evt);
            }
        });
        jPanel3.add(jtxtCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 29, 870, 30));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 160, 890, 70));

        jPanel4.setBackground(new java.awt.Color(41, 42, 44));
        jPanel4.setBorder(new javax.swing.border.LineBorder(java.awt.Color.cyan, 1, true));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jchkSerie.setBackground(new java.awt.Color(41, 42, 44));
        jchkSerie.setFont(new java.awt.Font("Corbel", 0, 14)); // NOI18N
        jchkSerie.setForeground(java.awt.Color.cyan);
        jchkSerie.setText("Serie:");
        jchkSerie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkSerieActionPerformed(evt);
            }
        });
        jPanel4.add(jchkSerie, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        jcomboSerie.setBackground(new java.awt.Color(41, 42, 44));
        jcomboSerie.setFont(new java.awt.Font("Corbel", 0, 14)); // NOI18N
        jPanel4.add(jcomboSerie, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 160, 30));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 80, 180, 70));

        jPanel5.setBackground(new java.awt.Color(41, 42, 44));
        jPanel5.setBorder(new javax.swing.border.LineBorder(java.awt.Color.cyan, 1, true));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel5.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 250, 10));

        jtxtNoFac.setBackground(new java.awt.Color(41, 42, 44));
        jtxtNoFac.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jtxtNoFac.setForeground(new java.awt.Color(232, 251, 244));
        jtxtNoFac.setBorder(null);
        jPanel5.add(jtxtNoFac, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 37, 250, -1));

        jrNit.setBackground(new java.awt.Color(41, 42, 44));
        jrNit.setFont(new java.awt.Font("Corbel", 0, 14)); // NOI18N
        jrNit.setForeground(java.awt.Color.cyan);
        jrNit.setText("NIT Cliente:");
        jrNit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrNitActionPerformed(evt);
            }
        });
        jPanel5.add(jrNit, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 270, 70));

        jPanel6.setBackground(new java.awt.Color(41, 42, 44));
        jPanel6.setBorder(new javax.swing.border.LineBorder(java.awt.Color.cyan, 1, true));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jchkTipoVenta.setBackground(new java.awt.Color(41, 42, 44));
        jchkTipoVenta.setFont(new java.awt.Font("Corbel", 0, 14)); // NOI18N
        jchkTipoVenta.setForeground(java.awt.Color.cyan);
        jchkTipoVenta.setText("Tipo Venta");
        jPanel6.add(jchkTipoVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 7, -1, -1));

        jcboTipoVenta.setBackground(new java.awt.Color(41, 42, 44));
        jcboTipoVenta.setFont(new java.awt.Font("Corbel", 0, 14)); // NOI18N
        jPanel6.add(jcboTipoVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 160, 30));

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 80, 180, 70));

        jbtnBuscar.setBackground(new java.awt.Color(41, 42, 44));
        jbtnBuscar.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jbtnBuscar.setForeground(new java.awt.Color(232, 251, 244));
        jbtnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/buscar32_2.png"))); // NOI18N
        jbtnBuscar.setText("Buscar");
        jbtnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnBuscarActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 240, 890, 50));

        jbtnExportar.setBackground(new java.awt.Color(41, 42, 44));
        jbtnExportar.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jbtnExportar.setForeground(new java.awt.Color(232, 251, 244));
        jbtnExportar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/excel132.png"))); // NOI18N
        jbtnExportar.setText("Exportar");
        jbtnExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnExportarActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnExportar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 240, 140, 50));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 600, 1190, 10));

        jchkDetalle.setBackground(new java.awt.Color(41, 42, 44));
        jchkDetalle.setFont(new java.awt.Font("Corbel", 1, 24)); // NOI18N
        jchkDetalle.setForeground(new java.awt.Color(232, 251, 244));
        jchkDetalle.setText("Detalle");
        jPanel1.add(jchkDetalle, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, -1, -1));

        jbtnAnularFac.setBackground(new java.awt.Color(41, 42, 44));
        jbtnAnularFac.setFont(new java.awt.Font("Corbel", 1, 18)); // NOI18N
        jbtnAnularFac.setForeground(new java.awt.Color(232, 251, 244));
        jbtnAnularFac.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/borrar32.png"))); // NOI18N
        jbtnAnularFac.setText("Anular Factura");
        jbtnAnularFac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAnularFacActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnAnularFac, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 610, 200, 50));

        jbtnMostrarDesc.setBackground(java.awt.Color.black);
        jbtnMostrarDesc.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jbtnMostrarDesc.setForeground(new java.awt.Color(232, 251, 244));
        jbtnMostrarDesc.setText("Descripcion Completa");
        jbtnMostrarDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnMostrarDescActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnMostrarDesc, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 605, 180, 30));

        jPanel7.setBackground(new java.awt.Color(41, 42, 44));
        jPanel7.setBorder(new javax.swing.border.LineBorder(java.awt.Color.cyan, 1, true));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtfDesde.setBackground(new java.awt.Color(41, 42, 44));
        jtfDesde.setBorder(null);
        jtfDesde.setForeground(new java.awt.Color(232, 251, 244));
        jtfDesde.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("d-MM-yyyy"))));
        jtfDesde.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jtfDesde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfDesdeActionPerformed(evt);
            }
        });
        jPanel7.add(jtfDesde, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 37, 110, -1));
        jPanel7.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, 110, 10));
        jPanel7.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 110, 10));

        jrFechas.setBackground(new java.awt.Color(41, 42, 44));
        jrFechas.setFont(new java.awt.Font("Corbel", 0, 14)); // NOI18N
        jrFechas.setForeground(java.awt.Color.cyan);
        jrFechas.setText("Fecha Desde:         Fecha Hasta:");
        jPanel7.add(jrFechas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 8, -1, 20));

        jtfHasta.setBackground(new java.awt.Color(41, 42, 44));
        jtfHasta.setBorder(null);
        jtfHasta.setForeground(new java.awt.Color(232, 251, 244));
        jtfHasta.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("d-MM-yyyy"))));
        jtfHasta.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jtfHasta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfHastaActionPerformed(evt);
            }
        });
        jPanel7.add(jtfHasta, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 37, 110, -1));

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 270, 70));

        jbtnBitacora.setBackground(java.awt.Color.black);
        jbtnBitacora.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jbtnBitacora.setForeground(new java.awt.Color(232, 251, 244));
        jbtnBitacora.setText("Bitacora de Pagos");
        jbtnBitacora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnBitacoraActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnBitacora, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 640, 180, 30));

        jbtnMosDesc.setBackground(java.awt.Color.black);
        jbtnMosDesc.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jbtnMosDesc.setForeground(new java.awt.Color(232, 251, 244));
        jbtnMosDesc.setText("Motivo Anulación");
        jbtnMosDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnMosDescActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnMosDesc, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 640, 150, 30));

        jbtnImprimirFac.setBackground(java.awt.Color.black);
        jbtnImprimirFac.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        jbtnImprimirFac.setForeground(new java.awt.Color(232, 251, 244));
        jbtnImprimirFac.setText("Imprimir Factura");
        jbtnImprimirFac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnImprimirFacActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnImprimirFac, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 605, 150, 30));

        jPanel8.setBackground(new java.awt.Color(41, 42, 44));
        jPanel8.setBorder(new javax.swing.border.LineBorder(java.awt.Color.cyan, 1, true));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jchkTipoDoc.setBackground(new java.awt.Color(41, 42, 44));
        jchkTipoDoc.setFont(new java.awt.Font("Corbel", 0, 14)); // NOI18N
        jchkTipoDoc.setForeground(java.awt.Color.cyan);
        jchkTipoDoc.setText("Tipo Documento");
        jPanel8.add(jchkTipoDoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 7, -1, -1));

        jcboTipoDoc.setBackground(new java.awt.Color(41, 42, 44));
        jcboTipoDoc.setFont(new java.awt.Font("Corbel", 0, 14)); // NOI18N
        jcboTipoDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboTipoDocActionPerformed(evt);
            }
        });
        jPanel8.add(jcboTipoDoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 150, 30));

        jPanel1.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 80, 170, 70));

        jPanel9.setBackground(new java.awt.Color(41, 42, 44));
        jPanel9.setBorder(new javax.swing.border.LineBorder(java.awt.Color.cyan, 1, true));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel9.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 140, 10));

        jtxtNoDoc.setBackground(new java.awt.Color(41, 42, 44));
        jtxtNoDoc.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jtxtNoDoc.setForeground(new java.awt.Color(232, 251, 244));
        jtxtNoDoc.setBorder(null);
        jPanel9.add(jtxtNoDoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 37, 140, -1));

        jrNoDoc.setBackground(new java.awt.Color(41, 42, 44));
        jrNoDoc.setFont(new java.awt.Font("Corbel", 0, 14)); // NOI18N
        jrNoDoc.setForeground(java.awt.Color.cyan);
        jrNoDoc.setText("No. Documento");
        jrNoDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrNoDocActionPerformed(evt);
            }
        });
        jPanel9.add(jrNoDoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 130, 20));

        jPanel1.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 80, 160, 70));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 680));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblMinimizarMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinimizarMouseMoved
        lblMinimizar.setBorder(javax.swing.BorderFactory.createLineBorder(Color.CYAN));
    }//GEN-LAST:event_lblMinimizarMouseMoved

    private void lblMinimizarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinimizarMouseExited
        lblMinimizar.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(41,42,44)));
    }//GEN-LAST:event_lblMinimizarMouseExited

    private void lblSalirMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseMoved
        lblSalir.setBorder(javax.swing.BorderFactory.createLineBorder(Color.RED));
    }//GEN-LAST:event_lblSalirMouseMoved

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        jfReportes mp = new jfReportes();
        int dialog = JOptionPane.YES_NO_OPTION;
        int result = JOptionPane.showConfirmDialog(null, "¿Desea salir?","Salir",dialog);
        if(result == 0){                       
            //Fade.JFrameFadeOut(1f, 0f, 0.1f, 50, this,Fade.DISPOSE);
            this.dispose();            
            Fade.JFrameFadeIn(0f, 1f, 0.2f, 50, mp);
            mp.setVisible(true);
        }
    }//GEN-LAST:event_lblSalirMouseClicked

    private void lblSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseExited
        lblSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(41,42,44)));
    }//GEN-LAST:event_lblSalirMouseExited

    private void jrNitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrNitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jrNitActionPerformed

    private void jchkSerieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkSerieActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jchkSerieActionPerformed

    private void jbtnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnBuscarActionPerformed
        
        if(jchkDetalle.isSelected() == false){
            buscar(1);
        }else{
            buscar(2);
        }
        
    }//GEN-LAST:event_jbtnBuscarActionPerformed

    private void jbtnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnExportarActionPerformed
        try {
            Exportar_excel excel = new Exportar_excel();
            excel.exportarExcel(jtableVentas);
        } catch (IOException ex) {
            Logger.getLogger(jfConsultaProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jbtnExportarActionPerformed

    private void jbtnAnularFacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAnularFacActionPerformed
        anulaFac();
    }//GEN-LAST:event_jbtnAnularFacActionPerformed

    private void jbtnMostrarDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnMostrarDescActionPerformed
        try {
            
            if(jchkDetalle.isSelected() == false){
                JOptionPane.showMessageDialog(null, "Para ver la descripcion completa, favor seleccione datalle de la factura", "CyCo", JOptionPane.INFORMATION_MESSAGE);            
            }else{
                    
                 if(jtableVentas.getSelectedRow() >=0){
                    String iDf = String.valueOf(jtableVentas.getValueAt(jtableVentas.getSelectedRow(),6));                 
                    jfMostrarDescrip af = new jfMostrarDescrip();
                    af.jAreaDes.setText(iDf);
                    af.setVisible(true);                                                        
                }else{
                    JOptionPane.showMessageDialog(null, "Favor seleccione un registro para mostrar descripcion completa", "CyCo", JOptionPane.INFORMATION_MESSAGE);                            
                }                                                    
            }                        
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jbtnMostrarDescActionPerformed

    private void jtfDesdeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfDesdeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfDesdeActionPerformed

    private void jtfHastaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfHastaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfHastaActionPerformed

    private void jrClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jrClienteActionPerformed

    private void jtxtClienteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtClienteFocusLost
         try {
            if(jrCliente.isSelected() == true){                
                mostrarId("tcliente","id_cliente","txt_nombre",jtxtCliente.getText(),2);  
                
            }else{
                return;
            }
        } catch (Exception e) {            
        } 
    }//GEN-LAST:event_jtxtClienteFocusLost

    private void jbtnBitacoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnBitacoraActionPerformed
        
//        if(jchkDetalle.isSelected() == false){
//                JOptionPane.showMessageDialog(null, "Para ver la bitacora de pagos", "CyCo", JOptionPane.INFORMATION_MESSAGE);            
//            }

//tventas.addColumn("id_factura");
//                tventas.addColumn("FECHA");
//                tventas.addColumn("SERIE");
//                tventas.addColumn("NO. FACTURA");
//                tventas.addColumn("CLIENTE");
//                tventas.addColumn("TOTAL Q.");
//                tventas.addColumn("ESTADO");
//                tventas.addColumn("TIPO");            
//                tventas.addColumn("id_estado");  
//                tventas.addColumn("id_tipo");                 
//                tventas.addColumn("PRODUCTO"); 
//                tventas.addColumn("TIPO"); 
//                tventas.addColumn("CANTIDAD"); 
//                tventas.addColumn("PRECIO"); 
//                tventas.addColumn("SUB-TOTAL"); 
        if(jtableVentas.getSelectedRow() >=0){
                varias_funciones vr = new varias_funciones();
                jfBitacora_pagos bp = new jfBitacora_pagos();
                bp.idFactura = Integer.parseInt(String.valueOf(jtableVentas.getValueAt(jtableVentas.getSelectedRow(), 0)));
                bp.mostrar_bitacora();
                bp.lblSerie.setText(String.valueOf(jtableVentas.getValueAt(jtableVentas.getSelectedRow(), 2)));
                bp.lblTipoDoc.setText(String.valueOf(jtableVentas.getValueAt(jtableVentas.getSelectedRow(), 3)));
                bp.lblFecha.setText(vr.MostrarformatoFecha(String.valueOf(jtableVentas.getValueAt(jtableVentas.getSelectedRow(), 1))));
                bp.setVisible(true);
        }else{
              JOptionPane.showMessageDialog(null, "Favor seleccione un registro para mostrar descripcion completa", "CyCo", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jbtnBitacoraActionPerformed

    private void jbtnMosDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnMosDescActionPerformed
        try {
            Connection cn = cc.conexion();
            varias_funciones vr = new varias_funciones();
            String ssql;
            if( Integer.parseInt(String.valueOf(jtableVentas.getValueAt(jtableVentas.getSelectedRow(), 13))) == 3){
                ssql = "";
                ssql = ssql + " select ta.txt_motivo_anula,fecha_anula from tanula_fac ta";
                ssql = ssql + " where ta.id_factura = " + String.valueOf(jtableVentas.getValueAt(jtableVentas.getSelectedRow(),0));
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(ssql);
                if(rs.next()){                                        
                    String Desc = rs.getString(1);
                    String fechaAnula = vr.MostrarformatoFecha(rs.getString(2));
                    jfMotivoAnula af = new jfMotivoAnula();
                    af.jAreaDes.setText(Desc);
                    af.lblFecha.setText(fechaAnula);
                    af.setVisible(true);                    
                }
                rs.close();
                st.close();
                cn.close();                
            }else{
                JOptionPane.showMessageDialog(null, "Favor seleccione un registro para mostrar descripcion completa", "CyCo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jbtnMosDescActionPerformed

    private void jbtnImprimirFacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnImprimirFacActionPerformed
       
       JOptionPane.showMessageDialog(null, String.valueOf(jtableVentas.getValueAt(jtableVentas.getSelectedRow(),0)));
       JOptionPane.showMessageDialog(null, String.valueOf(jtableVentas.getValueAt(jtableVentas.getSelectedRow(),12)));
       Integer ifi = Integer.parseInt(String.valueOf(jtableVentas.getValueAt(jtableVentas.getSelectedRow(),0)));           
       Controlador_impresiones CI = new Controlador_impresiones();
       if(Integer.parseInt(String.valueOf(jtableVentas.getValueAt(jtableVentas.getSelectedRow(),12))) == 1){       
           JOptionPane.showMessageDialog(null, "Entro a la impresion tipo 3"); 
           CI.imprime_fac(3, ifi);  
       }else{
            CI.imprime_fac(4, ifi);       
       
       }
       
                 
    }//GEN-LAST:event_jbtnImprimirFacActionPerformed

    private void jrNoDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrNoDocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jrNoDocActionPerformed

    private void jcboTipoDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcboTipoDocActionPerformed
        try {
           jcomboSerie.removeAllItems();
           idex_combo combo = new idex_combo();        
           if(jcboTipoDoc.getItemAt(jcboTipoDoc.getSelectedIndex()).getId() == 1){               
               combo.llenarCombo(jcomboSerie, "id_serie", "tserie_fac", "txt_serie");     
           }else if(jcboTipoDoc.getItemAt(jcboTipoDoc.getSelectedIndex()).getId() == 2){
               combo.llenarCombo(jcomboSerie, "id_serie_recibo", "tserie_recibo", "txt_no_serie_recibo");     
           }                                 
       } catch (Exception e) {
       }
    }//GEN-LAST:event_jcboTipoDocActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(jfRVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfRVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfRVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfRVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jfRVentas().setVisible(true);
            }
        });
    }

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JButton jbtnAnularFac;
    private javax.swing.JButton jbtnBitacora;
    private javax.swing.JButton jbtnBuscar;
    private javax.swing.JButton jbtnExportar;
    private javax.swing.JButton jbtnImprimirFac;
    private javax.swing.JButton jbtnMosDesc;
    private javax.swing.JButton jbtnMostrarDesc;
    private javax.swing.JComboBox<idex_combo> jcboTipoDoc;
    private javax.swing.JComboBox<idex_combo> jcboTipoVenta;
    private javax.swing.JCheckBox jchkDetalle;
    private javax.swing.JCheckBox jchkEstado;
    private javax.swing.JCheckBox jchkSerie;
    private javax.swing.JCheckBox jchkTipoDoc;
    private javax.swing.JCheckBox jchkTipoVenta;
    private javax.swing.JComboBox<idex_combo> jcomboEstado;
    private javax.swing.JComboBox<idex_combo> jcomboSerie;
    private javax.swing.JRadioButton jrCliente;
    private javax.swing.JRadioButton jrFechas;
    private javax.swing.JRadioButton jrNit;
    private javax.swing.JRadioButton jrNoDoc;
    private javax.swing.JTable jtableVentas;
    private javax.swing.JFormattedTextField jtfDesde;
    private javax.swing.JFormattedTextField jtfHasta;
    private javax.swing.JTextField jtxtCliente;
    private javax.swing.JTextField jtxtNoDoc;
    private javax.swing.JTextField jtxtNoFac;
    private javax.swing.JLabel lblMinimizar;
    private javax.swing.JLabel lblSalir;
    // End of variables declaration//GEN-END:variables
}
