/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formas;

import Animacion.Fade;
import clases_varias.Controlador_impresiones;
import clases_varias.Numero_Letras;
import clases_varias.conectar;
import clases_varias.idex_combo;
import clases_varias.imprimir_reportes;
import clases_varias.user_logged;
import clases_varias.varias_funciones;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author wwwki
 */
public class jfFacturar extends javax.swing.JFrame {

     Integer id_serie =0;
     Integer id_Cliente =0;
     Integer id_Producto=0;
     Integer iEstadoFac=0;
     Integer iTipoFactura=0;
     Integer idPrecio = 0;
     Integer idTipoVenta;
     
     Integer idSuc;
     Integer idBod;
     String seriePC;
     
     conectar cc= new conectar();          
       
    /**
     * Creates new form jfFacturar
     */
    public jfFacturar() {
        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("/Iconos/CyCo64.png")).getImage());
        this.setLocationRelativeTo(null);     
        jtxtFecha.requestFocus();
        btnAgregar.setBackground(new Color(0,0,0,64));
        jtxtPrecio.setEditable(false);
        
        //ASIGNACION DE RADIO BUTTONS
        jtxtFecha.requestFocus();
        jrbProducto.setSelected(true);
        jrbAutomatico.setSelected(true);
        jchkEstadoFac.setSelected(true);
        jrbPendiente.setSelected(false);
        jrbFactura.setSelected(true);
        
        jtxtCantidad.setEditable(true);
        jtxtPrecio.setEditable(false);
        jtxtSubTotal.setEditable(false);
        
        varias_funciones fecha = new varias_funciones();
        String fechaActual = fecha.recupFechaActual();
        String fechaFormato = fecha.MostrarformatoFecha(fechaActual);
        jtxtFecha.setText(fechaFormato);
        
        cargarTabla();
        validaforma_Fac();
        
        buscarProductoAutoC();        
        buscarClienteAutoC();
        
        
        //La tabla no se autoresizable
        jtbFactura.setAutoResizeMode(jtbFactura.AUTO_RESIZE_OFF);
        
        //grupo1
        jgrupoTipoFac.add(jrbProducto);
        jgrupoTipoFac.add(jrbServicio);
        
        //grupo2
        jgrupoFormaFac.add(jrbAutomatico);
        jgrupoFormaFac.add(jrbRecibo);
                        
        //grupo3                                                                       
        jgrupoEstadoFac.add(jchkEstadoFac);
        jgrupoEstadoFac.add(jrbPendiente);
        
        //grupo4
        jgrupoTipoDoc.add(jrbFactura);
        jgrupoTipoDoc.add(jrbRecibo);
        
        mostrar_serie(1);
        idTipoVenta = 1;
        recupIdPc();
        recup_datos_asignacion();
                                
    }                    
    
    void recupIdPc(){              
        try
        {
            String result = "";
            String serial;
            Process p = Runtime.getRuntime().exec("wmic bios get serialnumber");
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null)
            {                              
                result += line;                
            }
            if (result.equalsIgnoreCase(" ")) {
                System.out.println("Result is empty");
            } else
            {                
                serial = result.replace("SerialNumber","");
                seriePC = serial.trim();                
            }
            input.close();
        } catch (Exception ex){                
        }        
    }
                
   
    
    void recup_datos_asignacion(){
    
        try {
            Connection cn= cc.conexion(); 
                        String ssql;                                                                                                                                              
                        ssql = "";
                        ssql = ssql + " select tsuc.id_sucursal, tbod.id_bodega";
                        ssql = ssql + " from tasignapc tpc";
                        ssql = ssql + " inner join tbodega tbod on tbod.id_bodega = tpc.id_bodega";
                        ssql = ssql + " inner join tsucursal tsuc on tsuc.id_sucursal = tbod.id_suc";                        
                        ssql = ssql + " where tpc.serie_pc = '" + seriePC + "'";
                        Statement st = cn.createStatement();
                        ResultSet rs = st.executeQuery(ssql);
                        if(rs.next()){                    
                            idSuc = rs.getInt(1);
                            idBod = rs.getInt(2);                    
                        }  
                        rs.close();
                        st.close();
                        cn.close();                             
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperar datos de asignacion en: "+e);
        }                    
    }
    
    
    
    
    
    
    
   void mostrar_serie(int iTipo){
   
       try {
           jcboSerie.removeAllItems();
           idex_combo combo = new idex_combo();        
           if(iTipo == 1){               
               combo.llenarCombo(jcboSerie, "id_serie", "tserie_fac", "txt_serie");     
           }else{
               combo.llenarCombo(jcboSerie, "id_serie_recibo", "tserie_recibo", "txt_no_serie_recibo");     
           }                                 
       } catch (Exception e) {
       }         
   }
    
    
    
   void buscarClienteAutoC(){
    String ssql;
    
        try {
            Connection cn= cc.conexion();
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
    
   void buscarProductoAutoC(){
    String ssql;
    
        try {
            Connection cn= cc.conexion();
            ssql = "";
            ssql = ssql + " select * from ";
            ssql = ssql + " tproducto ";
            ssql = ssql + " where sn_activo = 1";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            TextAutoCompleter nombreCli = new TextAutoCompleter(jtxtProd);
            while(rs.next()){
                 nombreCli.addItem(rs.getString("txt_descripcion"));
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
    
    void cargarTabla(){
    
            DefaultTableModel prd = new DefaultTableModel();
            prd.addColumn("id_producto");
            prd.addColumn("PRODUCTO");
            prd.addColumn("CANTIDAD");
            prd.addColumn("PRECIO");
            prd.addColumn("SUB-TOTAL");
            prd.addColumn("id_precio");
            jtbFactura.setModel(prd);
    
    
            //ID_producto
            jtbFactura.getColumnModel().getColumn(0).setMaxWidth(0);
            jtbFactura.getColumnModel().getColumn(0).setMinWidth(0);
            jtbFactura.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
            jtbFactura.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
            
            //Producto
            jtbFactura.getColumnModel().getColumn(1).setMaxWidth(400);
            jtbFactura.getColumnModel().getColumn(1).setMinWidth(400);
            jtbFactura.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(400);
            jtbFactura.getTableHeader().getColumnModel().getColumn(1).setMinWidth(400);
            
            //Cantidad
            jtbFactura.getColumnModel().getColumn(2).setMaxWidth(85);
            jtbFactura.getColumnModel().getColumn(2).setMinWidth(85);
            jtbFactura.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(85);
            jtbFactura.getTableHeader().getColumnModel().getColumn(2).setMinWidth(85);
            
            //Precio
            jtbFactura.getColumnModel().getColumn(3).setMaxWidth(85);
            jtbFactura.getColumnModel().getColumn(3).setMinWidth(85);
            jtbFactura.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(85);
            jtbFactura.getTableHeader().getColumnModel().getColumn(3).setMinWidth(85);
            
            //subtotal
            jtbFactura.getColumnModel().getColumn(4).setMaxWidth(100);
            jtbFactura.getColumnModel().getColumn(4).setMinWidth(100);
            jtbFactura.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(100);
            jtbFactura.getTableHeader().getColumnModel().getColumn(4).setMinWidth(100);   
            
            //ID_precio
            jtbFactura.getColumnModel().getColumn(5).setMaxWidth(0);
            jtbFactura.getColumnModel().getColumn(5).setMinWidth(0);
            jtbFactura.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(0);
            jtbFactura.getTableHeader().getColumnModel().getColumn(5).setMinWidth(0);
    }
    
    
    void validaAgregar(){
        String sMensaje = "";
        varias_funciones vr = new varias_funciones();
        Double cantidad;
        try {
            
            if(jrbProducto.isSelected() == true){
                if(id_Producto == 0){
                    sMensaje = "* El producto no exite en el sistema, por favor ingresar nuevamente \n";
                }
            }
                        
            if(jtxtProd.getText().equals("")){
                sMensaje = sMensaje + "* Favor completar campo producto \n";                   
            }
            
            
            if(jrbProducto.isSelected() == true){
                if(jtxtCantidad.getText().equals("")){
                    sMensaje = sMensaje + "* Favor completar campo Cantidad \n ";                            
                }                            
            }
                                                            
            if(jtxtPrecio.getText().equals("")){
                sMensaje = sMensaje + "* Favor completar campo Precio \n ";                            
            }
            
            if(jtxtSubTotal.getText().equals("")){
                sMensaje = sMensaje + "* Favor completar campo Sub-Total \n ";                            
            }
            
                                    
            if(!sMensaje.equals("")){
                JOptionPane.showMessageDialog(null, sMensaje, "CyCo", JOptionPane.INFORMATION_MESSAGE);
            }else{
                
                if(jrbProducto.isSelected()){
                    
                        cantidad = Double.parseDouble(jtxtCantidad.getText());

                        if(jtbFactura.getRowCount() != 0){
                            for(int i = 0; i < jtbFactura.getRowCount(); i++){                    
                                if(id_Producto == Integer.parseInt(String.valueOf(jtbFactura.getValueAt(i, 0)))){
                                    cantidad = (cantidad + Double.parseDouble(String.valueOf(jtbFactura.getValueAt(i, 2))));                            
                                }                                    
                            }                                                                                                     
                        }

                        if(vr.valida_exitenciaProd(idSuc, idBod, id_Producto, cantidad)){
                            agregarProd();
                        }                                                                    
                                
                }else if(jrbServicio.isSelected()){
                    agregarProd();
                }
                        
            }              
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en primera validacion: " + e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
        }    
    }
    
        
    
            
    void agregarProd(){
        try {          
            
            if(jrbProducto.isSelected()){
            
                boolean bExiste = false;
                Double cantidad = Double.parseDouble(jtxtCantidad.getText());
                Double dSubTotal = Double.parseDouble(jtxtSubTotal.getText());
                for(int i = 0; i < jtbFactura.getRowCount(); i++){                         
                    if(id_Producto == Integer.parseInt(String.valueOf(jtbFactura.getValueAt(i, 0)))){
                        cantidad = (cantidad + Double.parseDouble(String.valueOf(jtbFactura.getValueAt(i, 2))));     
                        dSubTotal = (dSubTotal + Double.parseDouble(String.valueOf(jtbFactura.getValueAt(i, 4))));
    //                    JTable jtbFactura = new JTable();
                        jtbFactura.setValueAt(cantidad, i, 2); 
                        jtbFactura.setValueAt(dSubTotal, i, 4); 
                        bExiste = true;
                    }                       
                }          

                if(!bExiste){
                    DefaultTableModel prd = (DefaultTableModel) jtbFactura.getModel();                
                    String datos[] = new String[6];
                    datos[0] = id_Producto.toString();
                    datos[1] = jtxtProd.getText();
                    datos[2] = cantidad.toString();
                    datos[3] = jtxtPrecio.getText();
                    datos[4] = dSubTotal.toString();
                    datos[5] = idPrecio.toString();      
                    prd.addRow(datos);
                    jtbFactura.setModel(prd);            
                }            
            }else if(jrbServicio.isSelected()){
                DefaultTableModel prd = (DefaultTableModel) jtbFactura.getModel();                
                    String datos[] = new String[6];
                    datos[0] = id_Producto.toString();
                    datos[1] = jtxtProd.getText();
                    datos[2] = "0";
                    datos[3] = jtxtPrecio.getText();
                    datos[4] = jtxtSubTotal.getText();
                    datos[5] = idPrecio.toString();      
                    prd.addRow(datos);
                    jtbFactura.setModel(prd);                                        
            }
                                               
            calculaTotal();            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en agregar producto: " + e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    void calculaSubTotal(){
        DecimalFormat df = new DecimalFormat("#.00");                
        jtxtSubTotal.setText(df.format(Double.parseDouble(jtxtCantidad.getText()) * Double.parseDouble(jtxtPrecio.getText())));
    }    
    void calculaTotal(){
    
        try {
            Double dSub = 0.0;
            
            for(int i = 0; i < jtbFactura.getRowCount(); i++ ){
                dSub = (dSub + Double.parseDouble(String.valueOf(jtbFactura.getValueAt(i, 4))));
            }
                                    
            DecimalFormat df = new DecimalFormat("#.00");                
            lblTotal.setText(df.format(dSub));        
            id_Producto = 0;
            jtxtProd.setText("");
            jtxtCantidad.setText("");
            jtxtPrecio.setText("");
            jtxtSubTotal.setText("");            
            jtxtCodigo.setText("");
            jtxtCodigo.requestFocus();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en: " + e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
        }        
    }
    
    void validaCamposPrincipales(){
    String sMensaje = "";
    
        try {
            if(jcboSerie.getItemAt(jcboSerie.getSelectedIndex()).getId() == 0){
                sMensaje = "* Favor ingresar un numero de serie exitente \n";                
            }
            
            if(jtxtNoFac.getText().trim().equals("")){
               sMensaje = "* Favor ingresar un numero de documento \n";                            
            }
            
            
            if(jrbAutomatico.isSelected() == true){
                if(id_Cliente == 0){
                    sMensaje = sMensaje + "* Favor ingresar un cliente existente \n";
                }
            }else if(jrbManual.isSelected() == true){
                    grabarCliente();            
            }
            
            
            if(!sMensaje.equals("")){
                JOptionPane.showMessageDialog(null, sMensaje, "CyCo", JOptionPane.INFORMATION_MESSAGE);
            }else{
                validaNoFactura();                
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en valida campos principales: " +e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
        }
        
    
    }
    
    void validaNoFactura(){
        String ssql;
        String sSerie;
        String sNofac;
        int iTipoDoc = 0;
        
        try {
            if(jrbFactura.isSelected()){
                iTipoDoc = 1;            
            }else if (jrbRecibo.isSelected()){
                iTipoDoc = 2;            
            }
            
            Connection cn= cc.conexion();
            ssql = "";
            ssql = ssql + " select * from tventa";
            ssql = ssql + " where id_tipo_doc = " + iTipoDoc;
            ssql = ssql + " and no_serie_doc =  " + "'" + jcboSerie.getItemAt(jcboSerie.getSelectedIndex()).getDesc() + "'";
            ssql = ssql + " and no_doc = " + "'" + jtxtNoFac.getText() + "'";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            if(rs.next()){
                sSerie = rs.getString("no_serie_doc");
                sNofac = rs.getString("no_doc");
                
                JOptionPane.showMessageDialog(null, "Existe el numero de doc: "+sNofac + "\n" 
                                                    + "Bajo la serie: " + sSerie, ssql, 0);                                                                    
            }else{                
                //grabar
                grabarFactura();
            }      
            rs.close();
            st.close();
            cn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en valida factura: " +e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }
    
    void grabarFactura(){
        //Integer iEstadoFac;
        int idDoc = 0;
        try {
            Connection cn= cc.conexion();
            if(jchkEstadoFac.isSelected() == true){
                iEstadoFac = 2;
            }else if (jrbPendiente.isSelected() == true){
                iEstadoFac = 4;
                
            }                        
            
            
            if(jrbFactura.isSelected() == true){
                iTipoFactura = 1;
            }else if(jrbRecibo.isSelected() == true){
                iTipoFactura = 2;
            }
            
            
            if(jrbProducto.isSelected()){
                idTipoVenta = 1;            
            } else if(jrbServicio.isSelected()){                
                idTipoVenta = 2;  
            }
            
            
            varias_funciones vr = new varias_funciones();            
            String fecha = vr.formatoFechaTipo3(jtxtFecha.getText());
                        
                        
            double total_para_letras = Double.parseDouble(lblTotal.getText());
            Numero_Letras letritas = new Numero_Letras();
            String res = letritas.Convertir(total_para_letras + "",true);
            //lbl_numeros.setText(letritas.Convertir(total + "", true));
            
            Iterator<user_logged> itrLogg = Menu_principal.user_memory.iterator();
            user_logged ul = itrLogg.next();                                                                                                                                     	                           
            
            CallableStatement cst = cn.prepareCall("{call sp_grabar_factura(?,?,?,?,?,?,?,?,?,?,?,?)}");
            cst.setInt(1, iTipoFactura); // factura o recibo
            cst.setString(2, jcboSerie.getItemAt(jcboSerie.getSelectedIndex()).getDesc());
            cst.setString(3, jtxtNoFac.getText());
            cst.setString(4, fecha);
            cst.setInt(5, id_Cliente);
            cst.setDouble(6, Double.parseDouble(lblTotal.getText()));            
            cst.setInt(7, iEstadoFac);
            cst.setInt(8, idTipoVenta); //Producto o servicio
            cst.setInt(9, 1);
            cst.setString(10, res);
            cst.setInt(11, ul.getidUsy());
            cst.registerOutParameter(12, java.sql.Types.INTEGER);
            cst.execute();     
            
            
//            in id_tipo_doc int,
//                                   in serie varchar(100),
//                                   in nof varchar(100),
//                                   in fechaf date,
//                                   in idCLi int,
//                                   in totalF numeric(8,2),
//                                   in estado int,
//                                   in tipoFR int,
//								   in tipoProceso int,
//                                   in total_letras varchar(350),
//                                   in idUsuReg int,
//                                   out idDoc int)
            
            idDoc = cst.getInt(12);
            cst.close();                                                
            //grabar la bitacora de facturas que han sido pendintes
            if(jrbPendiente.isSelected() == true){                
                int ultimo_bit_fac_pend = vr.recup_ult_numero("tbitacora_fac_pendientes", "id_bit_fac");                
                CallableStatement cct = cn.prepareCall("{ call sp_grabar_bit_fac_pen(?,?,?)}");
                cct.setInt(1, ultimo_bit_fac_pend);
                cct.setInt(2, idDoc);
                cct.setInt(3, id_Cliente);
                cct.execute();    
                cct.close();
            }
            
            if(jrbProducto.isSelected() == true){
                grabarDetalleFactura(idDoc);
            }else if(jrbServicio.isSelected() == true){
                grabarServicio(idDoc,idDoc);
            }
             cn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al grabar  cabecera factura en: " +e, "CyCo", JOptionPane.INFORMATION_MESSAGE);            
        }            
    }
    
    void grabarDetalleFactura(Integer id_doc){
        
        try {
            Connection cn= cc.conexion();
            Integer i;
                                   
            for(i = 0; i<jtbFactura.getRowCount(); i++ ){                            
                CallableStatement cst = cn.prepareCall("{call sp_grabar_detalle_venta(?,?,?,?,?,?,?)}");
                cst.setInt(1, id_doc); //id_documento
                cst.setInt(2, Integer.parseInt(String.valueOf(jtbFactura.getValueAt(i, 0)))); //id_producto
                cst.setDouble(3, Double.parseDouble(String.valueOf(jtbFactura.getValueAt(i, 2)))); // cantidad 
                cst.setDouble(4, Integer.parseInt(String.valueOf(jtbFactura.getValueAt(i, 5)))); //id_precio                
                cst.setDouble(5, Double.parseDouble(String.valueOf(jtbFactura.getValueAt(i, 4)))); // sub total venta                
                cst.setInt(6, idSuc); //sucursal
                cst.setInt(7, idBod); //bodega
                cst.execute();
                cst.close();
                
            }
            JOptionPane.showMessageDialog(null, "Fatura generada con exito", "CyCo", JOptionPane.INFORMATION_MESSAGE);            
            iroshima();
            /*Facutura tipo Productos*/  
            Controlador_impresiones CI = new Controlador_impresiones();
            if(jrbProducto.isSelected()){
                CI.imprime_fac(3, id_doc); 
            }else if(jrbServicio.isSelected()){
                CI.imprime_fac(4, id_doc);             
            }
            
            
             
            cn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en grabar detalle factura: " +e);
        }        
    }
    
    void recuperar_cliente(Integer id_cliente){
        Integer iActivo;
        
        try {
            Connection cn= cc.conexion();
            //Se llama primero al procedimiento
            CallableStatement cst = cn.prepareCall("{call sp_recuperar_cliente(?,?,?,?,?,?,?)}");
            
            //Enviamos parametros de entrada
            cst.setInt(1, id_cliente);            
            
            //Definimos los tipos de datos de los parametros de salida del sp
            cst.registerOutParameter(2, java.sql.Types.VARCHAR);
            cst.registerOutParameter(3, java.sql.Types.VARCHAR);
            cst.registerOutParameter(4, java.sql.Types.VARCHAR);
            cst.registerOutParameter(5, java.sql.Types.VARCHAR);
            cst.registerOutParameter(6, java.sql.Types.INTEGER);
            cst.registerOutParameter(7, java.sql.Types.INTEGER);
            
            //Ejecutamos el procedimiento
            cst.execute();
//            cst.close();
//            cn.close();                        
            //Obentemos la salida de datos del sp            
            //jtNombre.setText(cst.getString(2));
            jtxtNit.setText(cst.getString(3));
            jtxtTele.setText(cst.getString(4));
            jtxtDirec.setText(cst.getString(5));
                                                                                       
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en: " + e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
        }                
    }
    
    void validaforma_Fac(){    
        try {            
            if(jrbAutomatico.isSelected() == true){
                jtxtCliente.setText("");
                jtxtNit.setText("");
                jtxtTele.setText("");
                jtxtDirec.setText("");
                id_Cliente=0;                                              
            }else{
                jtxtCliente.setText("");
                jtxtNit.setText("");
                jtxtTele.setText("");
                jtxtDirec.setText("");
                id_Cliente=0;                        
            }                                                                                
        } catch (Exception e) {            
        }            
    }
        
    void grabarServicio(Integer id_fac, Integer ifi){    
        try {    
            Connection cn= cc.conexion();
            for(int i = 0; i < jtbFactura.getRowCount(); i++){
                varias_funciones vr = new varias_funciones();
                int ult = vr.recup_ult_numero("tservicio", "id_serv");
            
                CallableStatement cst = cn.prepareCall("{call sp_grabar_servicio(?,?,?,?,?,?)}");
                cst.setInt(1, ult);
                cst.setInt(2, id_fac);
                cst.setString(3, String.valueOf(jtbFactura.getValueAt(i, 1)));
                if(jrbProducto.isSelected() == true){
                    cst.setDouble(4, Double.parseDouble(String.valueOf(jtbFactura.getValueAt(i, 2))));
                }
                if(jrbServicio.isSelected() == true){
                    cst.setDouble(4,0);
                }                                                
                cst.setDouble(5, Double.parseDouble(String.valueOf(jtbFactura.getValueAt(i, 3))));
                cst.setDouble(6, Double.parseDouble(String.valueOf(jtbFactura.getValueAt(i, 4))));
                cst.execute();
                cst.close();
                cn.close();
            }                        
            JOptionPane.showMessageDialog(null, "Factura grabada con exito", "CyCo", JOptionPane.INFORMATION_MESSAGE);            
            iroshima();
            /*Factura tipo servicio*/
            Controlador_impresiones CI = new Controlador_impresiones();
            CI.imprime_fac(2, ifi);                                                
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Error al grabar servicio en: "+e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
        }        
    }
    
    void validaClienteRepetidos(){
        String ssql;
        
                                        
                                                                                                                                                                              
        try {
            Connection cn= cc.conexion();
            ssql = "";
            ssql = ssql + "select * from tcliente";
            ssql = ssql + " where txt_nombre = " + "'" + jtxtCliente.getText() + "'";
            ssql = ssql + " or txt_nit = " + "'" + jtxtNit.getText() + "'";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            if(rs.next()){
                JOptionPane.showMessageDialog(null, "Exite un registro con el nombre: " + rs.getString("txt_nombre")+"\n"
                                                + "Nit: " + rs.getString("txt_nit"), "CyCo", JOptionPane.INFORMATION_MESSAGE);
            }else{
                    grabarCliente();                    
            }
            rs.close();
            st.close();
            cn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
        }    
    }
    
    
    void grabarCliente(){
        Integer iActivo;
        
        try {         
                Connection cn= cc.conexion();
                varias_funciones ultimo = new varias_funciones();
                int ulti = ultimo.recup_ult_numero("tcliente", "id_cliente");
                                                           
                CallableStatement cst = cn.prepareCall("{ call sp_grabar_cliente (?,?,?,?,?,?,?,?)}");
                cst.setInt(1, ulti);
                cst.setString(2, jtxtCliente.getText());
                cst.setString(3, jtxtNit.getText());
                cst.setString(4, jtxtTele.getText());
                cst.setString(5, jtxtDirec.getText());
                cst.setInt(6, 1);
                cst.setInt(7, 1);
                cst.setInt(8, 0);
                cst.execute();
                id_Cliente =ulti;
                cst.close();
                cn.close();
                JOptionPane.showMessageDialog(null, "Cliente nuevo guardado con exito", "CyCo", JOptionPane.INFORMATION_MESSAGE);
                limpiarClienteNuevo();                                                                                                                                                                                
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, "Error en guardar nuevo cliente en: " +e);
        }                
    }    
    
     void limpiarClienteNuevo(){
        jtxtCliente.setText("");
        jtxtNit.setText("");
        jtxtTele.setText("");
        jtxtDirec.setText("");      
    }    
    
     void iroshima(){
         jrbProducto.setSelected(true);
         jrbAutomatico.setSelected(true);
         jchkEstadoFac.setSelected(false);         
         jrbPendiente.setSelected(true);
         
         jtxtNoFac.setText("");
         jtxtCliente.setText("");
         jtxtNit.setText("");
         jtxtTele.setText("");
         jtxtDirec.setText("");     
         lblTotal.setText("0.00");
         cargarTabla();
         id_serie =0;
         id_Cliente =0;
         id_Producto=0;
         iEstadoFac=0;
         iTipoFactura=0;
     }
     
     
     
     
     void recupProd_cod(int iBus){
        Integer id_tabla=0;
        String ssql;
        try {
            Connection cn= cc.conexion(); 
            
            switch (iBus){
            
                case 1:                                        
                    ssql = "";         
                    ssql = ssql + " select tprod.id_producto, tprod.cod_prod, tprod.txt_descripcion,";                                        
                    ssql = ssql + " tp.id_precio, tp.dPventa";                                                            
                    ssql = ssql + " from tproducto tprod";
                    ssql = ssql + " inner join tprecio tp on tp.id_producto = tprod.id_producto";                                    		                                                                                        
                    ssql = ssql + " where tprod.id_producto <> 0";
                    ssql = ssql + " and tp.id_precio = (select max(tprecio.id_precio) from tprecio where tprecio.id_producto = tp.id_producto)";
                    ssql = ssql + " and tprod.txt_codigo = '" + jtxtCodigo.getText() + "'";
                    Statement st = cn.createStatement();
                    ResultSet rs = st.executeQuery(ssql);
                    if(rs.next()){
//                        idProd = rs.getInt(1);                        
                        jtxtProd.setText(rs.getString(3));                                                                                                                                               
                        idPrecio = rs.getInt(4);
                        jtxtPrecio.setText(rs.getString(5));                        
                    }                                        
                    rs.close();
                    st.close();                    
                    jtxtCantidad.requestFocus();
                    break;
                                                                                                                                            
                case 2:
                    ssql = "";         
                    ssql = ssql + " select tprod.id_producto, tprod.txt_codigo, tprod.txt_descripcion,";                                        
                    ssql = ssql + " tp.id_precio";                                                            
                    ssql = ssql + " from tproducto tprod";
                    ssql = ssql + " inner join tprecio tp on tp.id_producto = tprod.id_producto";                                    		                                                                                        
                    ssql = ssql + " where tprod.id_producto <> 0";
                    ssql = ssql + " and tp.id_precio = (select max(tprecio.id_precio) from tprecio where tprecio.id_producto = tp.id_producto)";                    
                    ssql = ssql + " and tprod.txt_descripcion = '" + jtxtProd.getText() + "'";                                                                        
                    Statement stt = cn.createStatement();
                    ResultSet rss = stt.executeQuery(ssql);
                    if(rss.next()){
//                        idProd = rss.getInt(1);                        
                        jtxtCodigo.setText(rss.getString(2));                                                                                                                                               
                        idPrecio = rss.getInt(4);
                    }                                        
                    rss.close();
                    stt.close();                                                                                    
                    break;
            }                   
            cn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar datos en: " + e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
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

        jgrupoTipoFac = new javax.swing.ButtonGroup();
        jgrupoFormaFac = new javax.swing.ButtonGroup();
        jgrupoEstadoFac = new javax.swing.ButtonGroup();
        jgrupoTipoDoc = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbFactura = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jSeparator7 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        jSeparator10 = new javax.swing.JSeparator();
        lblMinimizar = new javax.swing.JLabel();
        lblSalir = new javax.swing.JLabel();
        jtxtSubTotal = new javax.swing.JTextField();
        jtxtFecha = new javax.swing.JTextField();
        jtxtNoFac = new javax.swing.JTextField();
        jtxtCliente = new javax.swing.JTextField();
        jtxtCantidad = new javax.swing.JTextField();
        lblTotal = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        jbtnGuardar = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();
        jSeparator12 = new javax.swing.JSeparator();
        jtxtPrecio = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jtxtNit = new javax.swing.JTextField();
        jSeparator13 = new javax.swing.JSeparator();
        jLabel14 = new javax.swing.JLabel();
        jSeparator14 = new javax.swing.JSeparator();
        jtxtTele = new javax.swing.JTextField();
        jtxtDirec = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jSeparator15 = new javax.swing.JSeparator();
        jSeparator16 = new javax.swing.JSeparator();
        jrbProducto = new javax.swing.JRadioButton();
        jrbServicio = new javax.swing.JRadioButton();
        jSeparator17 = new javax.swing.JSeparator();
        jchkEstadoFac = new javax.swing.JRadioButton();
        jrbPendiente = new javax.swing.JRadioButton();
        jSeparator18 = new javax.swing.JSeparator();
        jrbAutomatico = new javax.swing.JRadioButton();
        jrbRecibo = new javax.swing.JRadioButton();
        jtxtProd = new javax.swing.JTextField();
        jbtnMosDesc = new javax.swing.JButton();
        jrbManual = new javax.swing.JRadioButton();
        jSeparator19 = new javax.swing.JSeparator();
        jrbFactura = new javax.swing.JRadioButton();
        jcboSerie = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jSeparator20 = new javax.swing.JSeparator();
        jtxtCodigo = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(41, 42, 44));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Corbel", 1, 36)); // NOI18N
        jLabel1.setForeground(java.awt.Color.gray);
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Facturación");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 200, 40));

        jSeparator1.setBackground(new java.awt.Color(0, 255, 204));
        jSeparator1.setForeground(new java.awt.Color(0, 255, 204));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 1220, 10));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setForeground(java.awt.Color.cyan);
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Fecha:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 130, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setForeground(java.awt.Color.cyan);
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Serie:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 120, 20));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setForeground(java.awt.Color.cyan);
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Sub Total");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 115, 100, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setForeground(java.awt.Color.cyan);
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("No. Doc:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 130, -1));

        jSeparator2.setBackground(java.awt.Color.cyan);
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 95, 370, 10));

        jSeparator3.setBackground(java.awt.Color.cyan);
        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 180, 20, 90));

        jSeparator4.setBackground(java.awt.Color.cyan);
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 310, 370, 20));

        jSeparator6.setBackground(java.awt.Color.cyan);
        jPanel1.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 430, 370, 20));

        jtbFactura.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jtbFactura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jtbFactura.setRowHeight(20);
        jtbFactura.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtbFacturaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jtbFacturaMouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(jtbFactura);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 200, 680, 330));

        jSeparator7.setBackground(java.awt.Color.cyan);
        jPanel1.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 490, 380, 20));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setForeground(java.awt.Color.cyan);
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Cliente:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 470, 110, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setForeground(java.awt.Color.cyan);
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Producto:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 75, 90, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setForeground(java.awt.Color.cyan);
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Total: Q.");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 530, 80, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setForeground(java.awt.Color.cyan);
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Precio:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 115, 60, -1));

        jSeparator8.setBackground(java.awt.Color.cyan);
        jPanel1.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 135, 130, 10));

        jSeparator9.setBackground(java.awt.Color.cyan);
        jPanel1.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 550, 140, 20));

        jSeparator10.setBackground(java.awt.Color.cyan);
        jPanel1.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 135, 130, 10));

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
        jPanel1.add(lblMinimizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 10, 50, 40));

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
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSalirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSalirMouseExited(evt);
            }
        });
        jPanel1.add(lblSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(1180, 10, 50, 40));

        jtxtSubTotal.setBackground(new java.awt.Color(41, 42, 44));
        jtxtSubTotal.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jtxtSubTotal.setForeground(java.awt.Color.green);
        jtxtSubTotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxtSubTotal.setBorder(null);
        jtxtSubTotal.setNextFocusableComponent(btnAgregar);
        jtxtSubTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtSubTotalActionPerformed(evt);
            }
        });
        jPanel1.add(jtxtSubTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 115, 130, -1));

        jtxtFecha.setBackground(new java.awt.Color(41, 42, 44));
        jtxtFecha.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jtxtFecha.setForeground(new java.awt.Color(232, 251, 244));
        jtxtFecha.setBorder(null);
        jtxtFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtFechaActionPerformed(evt);
            }
        });
        jPanel1.add(jtxtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 290, 370, -1));

        jtxtNoFac.setBackground(new java.awt.Color(41, 42, 44));
        jtxtNoFac.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jtxtNoFac.setForeground(new java.awt.Color(232, 251, 244));
        jtxtNoFac.setBorder(null);
        jtxtNoFac.setNextFocusableComponent(jtxtCliente);
        jtxtNoFac.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtNoFacFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtNoFacFocusLost(evt);
            }
        });
        jtxtNoFac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtNoFacActionPerformed(evt);
            }
        });
        jPanel1.add(jtxtNoFac, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 410, 370, -1));

        jtxtCliente.setBackground(new java.awt.Color(41, 42, 44));
        jtxtCliente.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jtxtCliente.setForeground(new java.awt.Color(232, 251, 244));
        jtxtCliente.setBorder(null);
        jtxtCliente.setNextFocusableComponent(jtxtProd);
        jtxtCliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtClienteFocusLost(evt);
            }
        });
        jtxtCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtClienteActionPerformed(evt);
            }
        });
        jPanel1.add(jtxtCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 470, 380, -1));

        jtxtCantidad.setBackground(new java.awt.Color(41, 42, 44));
        jtxtCantidad.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jtxtCantidad.setForeground(java.awt.Color.orange);
        jtxtCantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxtCantidad.setBorder(null);
        jtxtCantidad.setNextFocusableComponent(jtxtSubTotal);
        jtxtCantidad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtCantidadFocusLost(evt);
            }
        });
        jtxtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtCantidadActionPerformed(evt);
            }
        });
        jPanel1.add(jtxtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 115, 110, -1));

        lblTotal.setBackground(new java.awt.Color(41, 42, 44));
        lblTotal.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTotal.setForeground(java.awt.Color.green);
        lblTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        lblTotal.setText("0.00");
        lblTotal.setBorder(null);
        lblTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblTotalActionPerformed(evt);
            }
        });
        jPanel1.add(lblTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 530, 140, -1));

        btnAgregar.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        btnAgregar.setForeground(new java.awt.Color(232, 251, 244));
        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/compras32.png"))); // NOI18N
        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        jPanel1.add(btnAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 150, 680, 40));

        jbtnGuardar.setBackground(java.awt.Color.black);
        jbtnGuardar.setFont(new java.awt.Font("Corbel", 1, 18)); // NOI18N
        jbtnGuardar.setForeground(new java.awt.Color(232, 251, 244));
        jbtnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/check32.png"))); // NOI18N
        jbtnGuardar.setText("Guardar");
        jbtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 570, 680, 50));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setForeground(java.awt.Color.cyan);
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Cantidad:");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 115, 80, -1));

        jSeparator11.setBackground(new java.awt.Color(0, 255, 204));
        jPanel1.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 510, 20));

        jSeparator12.setBackground(java.awt.Color.cyan);
        jPanel1.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 135, 110, 10));

        jtxtPrecio.setBackground(new java.awt.Color(41, 42, 44));
        jtxtPrecio.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jtxtPrecio.setForeground(java.awt.Color.yellow);
        jtxtPrecio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxtPrecio.setBorder(null);
        jtxtPrecio.setNextFocusableComponent(jtxtSubTotal);
        jtxtPrecio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtPrecioFocusLost(evt);
            }
        });
        jtxtPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtPrecioActionPerformed(evt);
            }
        });
        jPanel1.add(jtxtPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 115, 130, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/factura32_1.png"))); // NOI18N
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 50));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setForeground(java.awt.Color.cyan);
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Nit:");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 530, 110, -1));

        jtxtNit.setBackground(new java.awt.Color(41, 42, 44));
        jtxtNit.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jtxtNit.setForeground(new java.awt.Color(232, 251, 244));
        jtxtNit.setBorder(null);
        jtxtNit.setNextFocusableComponent(jtxtTele);
        jtxtNit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtNitFocusLost(evt);
            }
        });
        jtxtNit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtNitActionPerformed(evt);
            }
        });
        jPanel1.add(jtxtNit, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 530, 140, -1));

        jSeparator13.setBackground(java.awt.Color.cyan);
        jPanel1.add(jSeparator13, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 550, 130, 20));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setForeground(java.awt.Color.cyan);
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Telefono:");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 530, 90, -1));

        jSeparator14.setBackground(java.awt.Color.cyan);
        jPanel1.add(jSeparator14, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 550, 130, 20));

        jtxtTele.setBackground(new java.awt.Color(41, 42, 44));
        jtxtTele.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jtxtTele.setForeground(new java.awt.Color(232, 251, 244));
        jtxtTele.setBorder(null);
        jtxtTele.setNextFocusableComponent(jtxtDirec);
        jtxtTele.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtTeleFocusLost(evt);
            }
        });
        jtxtTele.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtTeleActionPerformed(evt);
            }
        });
        jPanel1.add(jtxtTele, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 530, 130, -1));

        jtxtDirec.setBackground(new java.awt.Color(41, 42, 44));
        jtxtDirec.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jtxtDirec.setForeground(new java.awt.Color(232, 251, 244));
        jtxtDirec.setBorder(null);
        jtxtDirec.setNextFocusableComponent(jtxtProd);
        jtxtDirec.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtDirecFocusLost(evt);
            }
        });
        jtxtDirec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtDirecActionPerformed(evt);
            }
        });
        jPanel1.add(jtxtDirec, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 580, 380, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setForeground(java.awt.Color.cyan);
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Direccion:");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 580, 110, -1));

        jSeparator15.setBackground(java.awt.Color.cyan);
        jPanel1.add(jSeparator15, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 600, 380, 20));

        jSeparator16.setBackground(new java.awt.Color(0, 255, 204));
        jPanel1.add(jSeparator16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, 520, 20));

        jrbProducto.setBackground(new java.awt.Color(41, 42, 44));
        jrbProducto.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jrbProducto.setForeground(new java.awt.Color(232, 251, 244));
        jrbProducto.setText("Producto");
        jrbProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jrbProductoMouseClicked(evt);
            }
        });
        jPanel1.add(jrbProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 70, -1, -1));

        jrbServicio.setBackground(new java.awt.Color(41, 42, 44));
        jrbServicio.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jrbServicio.setForeground(new java.awt.Color(232, 251, 244));
        jrbServicio.setText("Servicio");
        jrbServicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jrbServicioMouseClicked(evt);
            }
        });
        jPanel1.add(jrbServicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 120, 140, -1));

        jSeparator17.setBackground(java.awt.Color.cyan);
        jSeparator17.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel1.add(jSeparator17, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 70, 20, 540));

        jchkEstadoFac.setBackground(new java.awt.Color(41, 42, 44));
        jchkEstadoFac.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jchkEstadoFac.setForeground(new java.awt.Color(232, 251, 244));
        jchkEstadoFac.setText("Pagada");
        jPanel1.add(jchkEstadoFac, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 180, 140, -1));

        jrbPendiente.setBackground(new java.awt.Color(41, 42, 44));
        jrbPendiente.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jrbPendiente.setForeground(new java.awt.Color(232, 251, 244));
        jrbPendiente.setText("Pendiente");
        jPanel1.add(jrbPendiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 230, -1, -1));

        jSeparator18.setBackground(java.awt.Color.cyan);
        jSeparator18.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel1.add(jSeparator18, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 70, 20, 90));

        jrbAutomatico.setBackground(new java.awt.Color(41, 42, 44));
        jrbAutomatico.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jrbAutomatico.setForeground(new java.awt.Color(232, 251, 244));
        jrbAutomatico.setText("Cliente Existente");
        jrbAutomatico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jrbAutomaticoMouseClicked(evt);
            }
        });
        jrbAutomatico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbAutomaticoActionPerformed(evt);
            }
        });
        jPanel1.add(jrbAutomatico, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 180, -1, -1));

        jrbRecibo.setBackground(new java.awt.Color(41, 42, 44));
        jrbRecibo.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jrbRecibo.setForeground(new java.awt.Color(232, 251, 244));
        jrbRecibo.setText("Recibo");
        jrbRecibo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jrbReciboMouseClicked(evt);
            }
        });
        jPanel1.add(jrbRecibo, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 120, 110, -1));

        jtxtProd.setBackground(new java.awt.Color(41, 42, 44));
        jtxtProd.setFont(new java.awt.Font("Corbel", 0, 16)); // NOI18N
        jtxtProd.setForeground(new java.awt.Color(232, 251, 244));
        jtxtProd.setBorder(null);
        jtxtProd.addHierarchyListener(new java.awt.event.HierarchyListener() {
            public void hierarchyChanged(java.awt.event.HierarchyEvent evt) {
                jtxtProdHierarchyChanged(evt);
            }
        });
        jtxtProd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtProdFocusLost(evt);
            }
        });
        jtxtProd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtxtProdMouseClicked(evt);
            }
        });
        jPanel1.add(jtxtProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 75, 370, -1));

        jbtnMosDesc.setBackground(java.awt.Color.black);
        jbtnMosDesc.setForeground(new java.awt.Color(232, 251, 244));
        jbtnMosDesc.setText("Mostrar Descripcion");
        jbtnMosDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnMosDescActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnMosDesc, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 530, 160, 30));

        jrbManual.setBackground(new java.awt.Color(41, 42, 44));
        jrbManual.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jrbManual.setForeground(new java.awt.Color(232, 251, 244));
        jrbManual.setText("Cliente Nuevo");
        jrbManual.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jrbManualMouseClicked(evt);
            }
        });
        jrbManual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbManualActionPerformed(evt);
            }
        });
        jPanel1.add(jrbManual, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 230, 190, -1));

        jSeparator19.setBackground(new java.awt.Color(0, 255, 204));
        jPanel1.add(jSeparator19, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 530, 20));

        jrbFactura.setBackground(new java.awt.Color(41, 42, 44));
        jrbFactura.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jrbFactura.setForeground(new java.awt.Color(232, 251, 244));
        jrbFactura.setText("Factura");
        jrbFactura.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jrbFacturaMouseClicked(evt);
            }
        });
        jPanel1.add(jrbFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, 120, -1));

        jcboSerie.setFont(new java.awt.Font("Corbel", 1, 18)); // NOI18N
        jPanel1.add(jcboSerie, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 345, 370, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel16.setForeground(java.awt.Color.cyan);
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("Codigo:");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 75, 80, -1));

        jSeparator20.setBackground(java.awt.Color.cyan);
        jPanel1.add(jSeparator20, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 95, 110, 10));

        jtxtCodigo.setBackground(new java.awt.Color(41, 42, 44));
        jtxtCodigo.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jtxtCodigo.setForeground(java.awt.Color.green);
        jtxtCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxtCodigo.setBorder(null);
        jtxtCodigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtCodigoFocusLost(evt);
            }
        });
        jtxtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtCodigoActionPerformed(evt);
            }
        });
        jPanel1.add(jtxtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 75, 110, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1240, 630));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblSalirMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseMoved
        lblSalir.setBorder(javax.swing.BorderFactory.createLineBorder(Color.RED));
    }//GEN-LAST:event_lblSalirMouseMoved

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        int dialog = JOptionPane.YES_NO_OPTION;
        int result = JOptionPane.showConfirmDialog(null, "¿Desea salir?","Salir",dialog);
            if(result == 0){
                jtbFactura.removeAll();
                //Fade.JFrameFadeIn(0f, 1f, 0.2f, 50, this);
                //Fade.JFrameFadeOut(1f, 0f, 0.1f, 50, this,Fade.DISPOSE);
                Menu_principal mp = new Menu_principal();                
                this.dispose();           
                mp.setVisible(true);
                Fade.JFrameFadeIn(0f, 1f, 0.2f, 50, mp);           
         }
    }//GEN-LAST:event_lblSalirMouseClicked

    private void lblSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseExited
        lblSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(41,42,44)));
    }//GEN-LAST:event_lblSalirMouseExited

    private void jtxtSubTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtSubTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtSubTotalActionPerformed

    private void jtxtFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtFechaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtFechaActionPerformed

    private void jtxtNoFacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtNoFacActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtNoFacActionPerformed

    private void jtxtClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtClienteActionPerformed

    private void jtxtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtCantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtCantidadActionPerformed

    private void lblTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblTotalActionPerformed

    private void jtxtPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtPrecioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtPrecioActionPerformed

    private void lblSalirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSalirMouseEntered

    private void lblMinimizarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinimizarMouseExited
        lblMinimizar.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(41,42,44)));
    }//GEN-LAST:event_lblMinimizarMouseExited

    private void lblMinimizarMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinimizarMouseMoved
        lblMinimizar.setBorder(javax.swing.BorderFactory.createLineBorder(Color.CYAN));
    }//GEN-LAST:event_lblMinimizarMouseMoved

    private void jtxtNoFacFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtNoFacFocusGained
               
    }//GEN-LAST:event_jtxtNoFacFocusGained

    private void jtxtNoFacFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtNoFacFocusLost
                             
    }//GEN-LAST:event_jtxtNoFacFocusLost

    private void jtxtClienteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtClienteFocusLost
        //mostrarId("tcliente","id_cliente","txt_nombre",jtxtCliente.getText(),2); 
        try {
            if(jrbAutomatico.isSelected() == true){                
                mostrarId("tcliente","id_cliente","txt_nombre",jtxtCliente.getText(),2);  
                recuperar_cliente(id_Cliente);            
            }else{
                return;
            }                                                                                
        } catch (Exception e) {            
        }                        
    }//GEN-LAST:event_jtxtClienteFocusLost

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        validaAgregar();
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void jtxtPrecioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtPrecioFocusLost
//        if(jrbServicio.isSelected() == true){
//            jtxtSubTotal.setText(jtxtPrecio.getText());
//        }else if(jrbProducto.isSelected() == true){
//           calculaSubTotal();
//        }        
    }//GEN-LAST:event_jtxtPrecioFocusLost

    private void jbtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGuardarActionPerformed
        if (this.jtbFactura.getRowCount() == 0 ){ 
            JOptionPane.showMessageDialog(null,"No hay productos para guardar la factura","CyCo",JOptionPane.INFORMATION_MESSAGE); 
            return;
        }

        int dialog = JOptionPane.YES_NO_OPTION;
        int result = JOptionPane.showConfirmDialog(null, "¿Desea Continuar?","CyCo",dialog);        
        if(result == 0){
            validaCamposPrincipales();
        }      
        
    }//GEN-LAST:event_jbtnGuardarActionPerformed

    private void jtbFacturaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbFacturaMouseClicked
        Double subTotal;
        Double dTotal;
        try {                                    
            if(evt.getClickCount()==2){ 
                
                if (this.jtbFactura.getRowCount() == 0 ){ 
                    JOptionPane.showMessageDialog(null,"No hay productos eliminar","CyCo",JOptionPane.INFORMATION_MESSAGE); 
                    return;
                }
                
                int dialog = JOptionPane.YES_NO_OPTION;
                int result = JOptionPane.showConfirmDialog(null, "¿Desea eliminar este registro?","CyCo",dialog);
                if(result == 0){

                //Calculo de total monto
                DecimalFormat df = new DecimalFormat("#.00");
                subTotal = Double.parseDouble(String.valueOf(jtbFactura.getValueAt(jtbFactura.getSelectedRow(), 4)));
                dTotal = Double.parseDouble(lblTotal.getText());
                lblTotal.setText(df.format(dTotal - subTotal));

                DefaultTableModel modelo = (DefaultTableModel) jtbFactura.getModel();
                int filase = jtbFactura.getSelectedRow();
                modelo.removeRow(filase);   
                }
            }                                    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en: "+e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
        }                
    }//GEN-LAST:event_jtbFacturaMouseClicked

    private void jtbFacturaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbFacturaMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jtbFacturaMouseEntered

    private void jtxtNitFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtNitFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtNitFocusLost

    private void jtxtNitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtNitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtNitActionPerformed

    private void jtxtTeleFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtTeleFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtTeleFocusLost

    private void jtxtTeleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtTeleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtTeleActionPerformed

    private void jtxtDirecFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtDirecFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtDirecFocusLost

    private void jtxtDirecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtDirecActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtDirecActionPerformed

    private void jrbReciboMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jrbReciboMouseClicked
        mostrar_serie(2);


        if(jrbRecibo.isSelected() == true){
            jtxtCliente.setText(null);
            jtxtNit.setText("");
            jtxtTele.setText("");
            jtxtDirec.setText("");
            id_Cliente=0;        
        }
    }//GEN-LAST:event_jrbReciboMouseClicked

    private void jrbAutomaticoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jrbAutomaticoMouseClicked

        validaforma_Fac();
        
        //if(jrbAutomatico.isSelected() == true){
          //  jtxtCliente.setText("");
//            jtxtNit.setText("");
//            jtxtTele.setText("");
//            jtxtDirec.setText("");
//            id_Cliente=0;        
//        }
    }//GEN-LAST:event_jrbAutomaticoMouseClicked

    private void jrbServicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jrbServicioMouseClicked
        jtxtProd.setText("");
        id_Producto = 0;                
        idTipoVenta = 2;   
        jtxtCantidad.setEditable(false);
        jtxtPrecio.setEditable(true);
        jtxtSubTotal.setEditable(true);
            
    }//GEN-LAST:event_jrbServicioMouseClicked

    private void jrbProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jrbProductoMouseClicked
        jtxtProd.setText("");
            id_Producto = 0;                        
            idTipoVenta = 1;      
        jtxtCantidad.setEditable(true);
        jtxtPrecio.setEditable(false);
        jtxtSubTotal.setEditable(false);
    }//GEN-LAST:event_jrbProductoMouseClicked

    private void jtxtProdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtProdFocusLost
    
        try {
            if(jrbProducto.isSelected() == true){                
                mostrarId("tproducto","id_producto","txt_descripcion",jtxtProd.getText(),3);                 
            }else{
                return;
            }
                                                                                
        } catch (Exception e) {            
        }  
    }//GEN-LAST:event_jtxtProdFocusLost

    private void jbtnMosDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnMosDescActionPerformed
         try {            
            if(jtbFactura.getSelectedRow() >=0){
                String iDf = String.valueOf(jtbFactura.getValueAt(jtbFactura.getSelectedRow(),1));                 
                jfMostrarDescrip af = new jfMostrarDescrip();
                af.jAreaDes.setText(iDf);
                af.iTipo_proc_descrip = 1;
                af.setVisible(true);                                                        
            }else{
                JOptionPane.showMessageDialog(null, "Favor seleccione un registro para mostrar descripcion completa", "CyCo", JOptionPane.INFORMATION_MESSAGE);                            
            }                                    
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jbtnMosDescActionPerformed

    private void jtxtProdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtxtProdMouseClicked
      
        if(jrbServicio.isSelected() == true){
            jfMostrarDescrip af = new jfMostrarDescrip();            
            af.jAreaDes.setText(jtxtProd.getText());
            af.jAreaDes.setEditable(true);
            af.iTipo_proc_descrip = 2;            
            af.setVisible(true);         
        }                
    }//GEN-LAST:event_jtxtProdMouseClicked

    private void jrbManualMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jrbManualMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jrbManualMouseClicked

    private void jrbManualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbManualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jrbManualActionPerformed

    private void jrbFacturaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jrbFacturaMouseClicked
        mostrar_serie(1);
    }//GEN-LAST:event_jrbFacturaMouseClicked

    private void jrbAutomaticoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbAutomaticoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jrbAutomaticoActionPerformed

    private void jtxtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtCodigoActionPerformed

    private void jtxtCodigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtCodigoFocusLost
        try {
            if(jrbProducto.isSelected() == true){            
                recupProd_cod(1);
                mostrarId("tproducto","id_producto","txt_codigo",jtxtCodigo.getText(),3);                 
            }else{
                return;
            }
                                                                                
        } catch (Exception e) { 
            System.out.println(e);
        }  
    }//GEN-LAST:event_jtxtCodigoFocusLost

    private void jtxtCantidadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtCantidadFocusLost
        try {
            
            if(jrbServicio.isSelected() == true){
                jtxtSubTotal.setText(jtxtPrecio.getText());
            }else if(jrbProducto.isSelected() == true){
                calculaSubTotal();
            }        
//            
            
        } catch (Exception e) {
            System.out.println("Error en lost cantidad en: " +e);
        }
        
        
    }//GEN-LAST:event_jtxtCantidadFocusLost

    private void jtxtProdHierarchyChanged(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_jtxtProdHierarchyChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtProdHierarchyChanged

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
            java.util.logging.Logger.getLogger(jfFacturar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfFacturar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfFacturar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfFacturar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jfFacturar().setVisible(true);
            }
        });
    }

      
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator19;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator20;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JButton jbtnGuardar;
    private javax.swing.JButton jbtnMosDesc;
    private javax.swing.JComboBox<idex_combo> jcboSerie;
    private javax.swing.JRadioButton jchkEstadoFac;
    private javax.swing.ButtonGroup jgrupoEstadoFac;
    private javax.swing.ButtonGroup jgrupoFormaFac;
    private javax.swing.ButtonGroup jgrupoTipoDoc;
    private javax.swing.ButtonGroup jgrupoTipoFac;
    private javax.swing.JRadioButton jrbAutomatico;
    private javax.swing.JRadioButton jrbFactura;
    private javax.swing.JRadioButton jrbManual;
    private javax.swing.JRadioButton jrbPendiente;
    private javax.swing.JRadioButton jrbProducto;
    private javax.swing.JRadioButton jrbRecibo;
    private javax.swing.JRadioButton jrbServicio;
    private javax.swing.JTable jtbFactura;
    private javax.swing.JTextField jtxtCantidad;
    private javax.swing.JTextField jtxtCliente;
    private javax.swing.JTextField jtxtCodigo;
    private javax.swing.JTextField jtxtDirec;
    private javax.swing.JTextField jtxtFecha;
    private javax.swing.JTextField jtxtNit;
    private javax.swing.JTextField jtxtNoFac;
    private javax.swing.JTextField jtxtPrecio;
    public static javax.swing.JTextField jtxtProd;
    private javax.swing.JTextField jtxtSubTotal;
    private javax.swing.JTextField jtxtTele;
    private javax.swing.JLabel lblMinimizar;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JTextField lblTotal;
    // End of variables declaration//GEN-END:variables
}
