/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formas;

import clases_varias.conectar;
import clases_varias.user_logged;
import java.awt.Color;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author wwwki
 */
public class jfPermisos extends javax.swing.JFrame {
    int idUsuario = 0;
    conectar cc= new conectar();    
    
    int iFactura;
    int iReportes;
    int iConf;
    
    int rVentas;
    int iCuentas;
    
    int iClientes;
    int iProductos;
    int iInventario;
    int iCompras;
    int iProve;
    int iParam;
    int iTras;
    
    int iSerieRec;
    int iSerirFac;
    int iSuc;
    int iBod;
    int iMarca;
    int iModelo;
    int iUsuario;
    int iPermisos;                
    /**
     * Creates new form jfPermisos
     */
    public jfPermisos() {
        initComponents();                
        this.setLocationRelativeTo(null);
        validaIncio();
        cargaTabla();        
    }
   
    
    void cargaTabla(){
    
        try {
            String ssql;
            String espacio = " ";
            Connection cn = cc.conexion();
            DefaultTableModel tUsu = new DefaultTableModel();
            tUsu.addColumn("idUsu");
            tUsu.addColumn("USUARIO");
            tUsu.addColumn("NOMBRE COMPLETO");
            jtbUsu.setModel(tUsu);
                        
            //id
            jtbUsu.getColumnModel().getColumn(0).setMaxWidth(0);
            jtbUsu.getColumnModel().getColumn(0).setMinWidth(0);
            jtbUsu.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
            jtbUsu.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
            
            //USUARIO
            jtbUsu.getColumnModel().getColumn(1).setMaxWidth(100);
            jtbUsu.getColumnModel().getColumn(1).setMinWidth(100);
            jtbUsu.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(100);
            jtbUsu.getTableHeader().getColumnModel().getColumn(1).setMinWidth(100);
            
            //NOMBRE
            jtbUsu.getColumnModel().getColumn(2).setMaxWidth(250);
            jtbUsu.getColumnModel().getColumn(2).setMinWidth(250);
            jtbUsu.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(250);
            jtbUsu.getTableHeader().getColumnModel().getColumn(2).setMinWidth(250);
            
            
            ssql = "";
            ssql = ssql + " select tusu.id_usuario, tusu.cod_usuario, concat(tusu.txt_nombre_usu,' ',tusu.txt_apellidos) as Nombre";
            ssql = ssql + " from tusuario tusu";
            ssql = ssql + " where sn_activo = 1";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            String datos[] = new String[3];
            while(rs.next()){
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                tUsu.addRow(datos);            
            }
            jtbUsu.setModel(tUsu); 
            st.close();
            rs.close();
            cn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar usuarios en: " + e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
        }            
    }
            
    
    
    void verifica_chekcs(){
    
    //Menu principal------------------------------------------------------------
    if(jchkFactura.isSelected()){
        iFactura = 1;
    }else{
        iFactura = 0;
    }        

    if(jchkReporte.isSelected()){
        iReportes = 1;
    }else{
        iReportes = 0;
    }            

    if(jchkConf.isSelected()){
        iConf = 1;        
    }else{
        iConf = 0;
    }              
    
    //sub menus-----------------------------------------------------------------
    if(jchkVentas.isSelected()){
        rVentas = 1;        
    }else{
        rVentas = 0;        
    }
    
    if(jchkCuentas.isSelected()){
        iCuentas = 1;        
    }else{
        iCuentas = 0;        
    }
    
    //--------------------------------------------------------------------------
        if(jchkClientes.isSelected()){
            iClientes = 1;            
        }else{
            iClientes = 0;
        }       


        if(jchkProductos.isSelected()){
            iProductos = 1;        
        }else{
            iProductos = 0;        
        }      

        if(jchkInven.isSelected()){
            iInventario = 1;
        }else{
            iInventario = 0;        
        }       

        if(jchkCompras.isSelected()){
            iCompras = 1;
        }else{
            iCompras = 0;
        }       

        if(jchkProve.isSelected()){
            iProve = 1;   
        }else{
            iProve = 0;
        }

        if(jchkParam.isSelected()){
            iParam = 1;
        }else{
            iParam = 0;        
        }
        
        if(jchkTras.isSelected()){
            iTras = 1;
        }else{
            iTras = 0;        
        }
                                
    //Parametros----------------------------------------------------------------

        if(jchkSerieRe.isSelected()){
            iSerieRec = 1;
        }else{
            iSerieRec = 0;
        }      

        if(jchkSerieFac.isSelected()){                      
            iSerirFac = 1;
        }else{
            iSerirFac = 0;
        }      

        if(jchkSuc.isSelected()){
            iSuc = 1;
        }else{
            iSuc = 0;
        }             

        if(jchkBodega.isSelected()){
            iBod = 1;
        }else{
            iBod = 0;
        }           

        if(jchkMarca.isSelected()){
            iMarca = 1;
        }else{
            iMarca = 0;
        }      

        if(jchkModelo.isSelected()){
            iModelo = 1;
        }else{
            iModelo = 0;
        }      

        if(jchkUsu.isSelected()){
            iUsuario = 1;
        }else{
            iUsuario = 0;
        }   

        if(jchkPermisos.isSelected()){
            iPermisos = 1;
        }else{
            iPermisos = 0;
        }                                  
        
        //Despues de veiricar se graba
        grabarPermisos();
    }
    
    void validaIncio(){
        if(jchkMenuM.isSelected() == false){
            AcitvarSubMenus(1);
        }
        
        if(jchkReportes.isSelected() == false){
            AcitvarSubMenus(3);
        }
        
        if(jchkConfi.isSelected() == false){
            AcitvarSubMenus(5);          
        }        
        
        if(jchkParametro.isSelected() == false){
            AcitvarSubMenus(7);
        }                                
    }
    
    
    void AcitvarSubMenus(int Opc){
       
        switch (Opc){
            case 1: 
                //desactivar los submenus del Menu principal
                jchkFactura.setSelected(false);                                                    
                jchkReporte.setSelected(false);                                                
                jchkConf.setSelected(false);                
                                                                             
                jchkFactura.setEnabled(false);
                jchkReporte.setEnabled(false);
                jchkConf.setEnabled(false);   
                break;
            case 2:
                //activar los submenus del Menu principal                
                jchkFactura.setEnabled(true);                                
                jchkReporte.setEnabled(true);                                                             
                jchkConf.setEnabled(true);         
                break;
            case 3:
                //desactivar los submenus de Reportes
                jchkVentas.setSelected(false);
                jchkVentas.setEnabled(false);                
                break;           
            case 4:
                //activar los submenus de Reportes                
                jchkVentas.setEnabled(true);                
                break;           
            case 5:
                //desactivar los submenus de Configuraciones
                jchkClientes.setSelected(false);
                jchkClientes.setEnabled(false);
                
                jchkProductos.setSelected(false);
                jchkProductos.setEnabled(false);
                
                jchkTras.setSelected(false);
                jchkTras.setEnabled(false);
                
                jchkCompras.setSelected(false);
                jchkCompras.setEnabled(false);
                
                jchkParam.setSelected(false);
                jchkParam.setEnabled(false);
                
                jchkProve.setSelected(false);
                jchkProve.setEnabled(false);
                break;
            case 6:
                //activar los submenus de Configuraciones                
                jchkClientes.setEnabled(true);                                
                jchkProductos.setEnabled(true);                                
                jchkTras.setEnabled(true);                                
                jchkCompras.setEnabled(true);                                
                jchkParam.setEnabled(true);                               
                jchkProve.setEnabled(true);
                break;
            case 7: 
                //desactivar los submenus de parametros
                jchkSerieRe.setSelected(false);
                jchkSerieRe.setEnabled(false);
                        
                jchkSerieFac.setSelected(false);
                jchkSerieFac.setEnabled(false);
                        
                jchkSuc.setSelected(false);
                jchkSuc.setEnabled(false);
                        
                jchkBodega.setSelected(false);
                jchkBodega.setEnabled(false);
                        
                jchkMarca.setSelected(false);
                jchkMarca.setEnabled(false);
                        
                jchkModelo.setSelected(false);
                jchkModelo.setEnabled(false);
                        
                jchkUsu.setSelected(false);
                jchkUsu.setEnabled(false);
                        
                jchkPermisos.setSelected(false);
                jchkPermisos.setEnabled(false);
                break;
            case 8:
                //desactivar los submenus de parametros                
                jchkSerieRe.setEnabled(true);                                        
                jchkSerieFac.setEnabled(true);                                        
                jchkSuc.setEnabled(true);                                        
                jchkBodega.setEnabled(true);                                        
                jchkMarca.setEnabled(true);                                        
                jchkModelo.setEnabled(true);                                        
                jchkUsu.setEnabled(true);                                       
                jchkPermisos.setEnabled(true);
                break;                
        }        
    }
                
    void grabarPermisos(){
        try {
            String ssql;
            int iProceso_menu;
            int iProceso_sub;
            int iProceso_sublv2;
            Connection cn = cc.conexion();
            
            
            ssql = "";
            ssql = ssql + " select * from tpermisos_menu";
            ssql = ssql + " where id_usu = " + Integer.parseInt(String.valueOf(jtbUsu.getValueAt(jtbUsu.getSelectedRow(), 0)));
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            if(rs.next()){
                iProceso_menu = 2;
            }else{
                iProceso_menu = 1;
            }
            rs.close();
            st.close();
                        
            ssql = "";
            ssql = ssql + " select * from tpermisos_submenu";
            ssql = ssql + " where id_usu = " + Integer.parseInt(String.valueOf(jtbUsu.getValueAt(jtbUsu.getSelectedRow(), 0)));
            Statement st2 = cn.createStatement();
            ResultSet rs2 = st2.executeQuery(ssql);
            if(rs2.next()){
                iProceso_sub = 2;
            }else{
                iProceso_sub = 1;
            }
            rs2.close();
            st2.close();
            
            
            ssql = "";
            ssql = ssql + " select * from tpermisos_sublv2";
            ssql = ssql + " where id_usu = " + Integer.parseInt(String.valueOf(jtbUsu.getValueAt(jtbUsu.getSelectedRow(), 0)));
            Statement st3 = cn.createStatement();
            ResultSet rs3 = st3.executeQuery(ssql);
            if(rs3.next()){
                iProceso_sublv2 = 2;
            }else{
                iProceso_sublv2 = 1;
            }
            rs3.close();
            st3.close();
            
            
            CallableStatement cst1 = cn.prepareCall("{ call sp_permisos_menus(?,?,?,?,?)}");
            cst1.setInt(1, Integer.parseInt(String.valueOf(jtbUsu.getValueAt(jtbUsu.getSelectedRow(), 0))));
            cst1.setInt(2, iFactura);
            cst1.setInt(3, iReportes);
            cst1.setInt(4, iConf);
            cst1.setInt(5, iProceso_sub);
            cst1.execute();
            cst1.close();                                                                                                                                
                                                                        
            CallableStatement cst2 = cn.prepareCall("{ call sp_permisos_submenu(?,?,?,?,?,?,?,?,?,?,?)}");
            cst2.setInt(1, Integer.parseInt(String.valueOf(jtbUsu.getValueAt(jtbUsu.getSelectedRow(), 0))));
            cst2.setInt(2, rVentas);
            cst2.setInt(3, iCuentas);
            cst2.setInt(4, iClientes);
            cst2.setInt(5, iProductos);
            cst2.setInt(6, iInventario);
            cst2.setInt(7, iCompras);                        
            cst2.setInt(8, iProve);    
            cst2.setInt(9, iParam);    
            cst2.setInt(10, iTras);    
            cst2.setInt(11, iProceso_sub);
            cst2.execute();
            cst2.close();
            
            CallableStatement cst3 = cn.prepareCall("{ call sp_permisos_submenu_lv2(?,?,?,?,?,?,?,?,?,?)}");
            cst3.setInt(1, Integer.parseInt(String.valueOf(jtbUsu.getValueAt(jtbUsu.getSelectedRow(), 0))));
            cst3.setInt(2, iSerieRec);
            cst3.setInt(3, iSerirFac);
            cst3.setInt(4, iSuc);
            cst3.setInt(5, iBod);
            cst3.setInt(6, iMarca);
            cst3.setInt(7, iModelo);
            cst3.setInt(8, iUsuario);
            cst3.setInt(9, iPermisos);
            cst3.setInt(10, iProceso_sublv2);
            cst3.execute();
            cst3.close();      
            cn.close();                        
            JOptionPane.showMessageDialog(null, "Permisos grabados con exito","CyCO",JOptionPane.INFORMATION_MESSAGE);                        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al grabar permisos en:" + e, "CyCo",JOptionPane.INFORMATION_MESSAGE);
        }            
    }
    
    
    void mostrar_actuales(){
    
        try {
            String ssql;
            Connection cn = cc.conexion();  
            
            //menus-------------------------------------------------------------
            ssql = "";
            ssql = ssql + " select * from tpermisos_menu";
            ssql = ssql + " where id_usu = " + String.valueOf(jtbUsu.getValueAt(jtbUsu.getSelectedRow(), 0));
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            if(rs.next()){
                if(rs.getInt(2) == 1){
                    jchkFactura.setSelected(true);
                }else{
                    jchkFactura.setSelected(false);
                }
                
                if(rs.getInt(3) == 1){
                    jchkReporte.setSelected(true);
                }else{
                    jchkReporte.setSelected(false);
                }
                
                if(rs.getInt(4) == 1){
                    jchkConf.setSelected(true);
                }else{
                    jchkConf.setSelected(false);
                }                                
            }else{
                jchkFactura.setSelected(false);
                jchkReporte.setSelected(false);
                jchkConf.setSelected(false);           
            }
            rs.close();
            st.close();;
                                    
            //sub-menus---------------------------------------------------------
            ssql = "";
            ssql = ssql + " select * from tpermisos_submenu";
            ssql = ssql + " where id_usu = " + String.valueOf(jtbUsu.getValueAt(jtbUsu.getSelectedRow(), 0));
            Statement st2 = cn.createStatement();
            ResultSet rs2 = st2.executeQuery(ssql);
            if(rs2.next()){
                
                if(rs2.getInt(2) == 1){
                    jchkVentas.setSelected(true);
                }else{
                    jchkVentas.setSelected(false);
                }
                                            
                if(rs2.getInt(3) == 1){
                    jchkCuentas.setSelected(true);
                }else{
                    jchkCuentas.setSelected(false);
                }
                
                if(rs2.getInt(4) == 1){
                    jchkClientes.setSelected(true);
                }else{
                    jchkClientes.setSelected(false);
                }
                
                if(rs2.getInt(5) == 1){
                    jchkProductos.setSelected(true);
                }else{
                    jchkProductos.setSelected(false);
                }
                
                if(rs2.getInt(6) == 1){
                    jchkInven.setSelected(true);
                }else{
                    jchkInven.setSelected(false);
                }
                
                if(rs2.getInt(7) == 1){
                    jchkCompras.setSelected(true);
                }else{
                    jchkCompras.setSelected(false);
                }
                
                if(rs2.getInt(8) == 1){
                    jchkProve.setSelected(true);
                }else{
                    jchkProve.setSelected(false);
                }               
                
                if(rs2.getInt(9) == 1){
                    jchkParam.setSelected(true);
                }else{
                    jchkParam.setSelected(false);
                }                                                                
                
                if(rs2.getInt(10) == 1){
                    jchkTras.setSelected(true);
                }else{
                    jchkTras.setSelected(false);
                }   
            }else{
                jchkVentas.setSelected(false);
                jchkCuentas.setSelected(false);
                jchkClientes.setSelected(false);
                jchkProductos.setSelected(false);
                jchkInven.setSelected(false);
                jchkCompras.setSelected(false);
                jchkProve.setSelected(false);
                jchkParam.setSelected(false);
                jchkTras.setSelected(false);
            }                                                     
            rs2.close();
            st2.close();
            
//            //sub_menus lv2-----------------------------------------------------
            ssql = "";
            ssql = ssql + " select * from tpermisos_sublv2";
            ssql = ssql + " where id_usu = " + String.valueOf(jtbUsu.getValueAt(jtbUsu.getSelectedRow(), 0));
            Statement st3 = cn.createStatement();
            ResultSet rs3 = st3.executeQuery(ssql);
            if(rs3.next()){
                if(rs3.getInt(2) == 1){
                    jchkSerieRe.setSelected(true);
                }else{
                    jchkSerieRe.setSelected(false);
                }
                
                if(rs3.getInt(3) == 1){
                    jchkSerieFac.setSelected(true);
                }else{
                    jchkSerieFac.setSelected(false);                
                }
                                                
                if(rs3.getInt(4) == 1){
                    jchkSuc.setSelected(true);
                }else{
                    jchkSuc.setSelected(false);
                }
                
                if(rs3.getInt(5) == 1){
                    jchkBodega.setSelected(true);
                }else{
                    jchkBodega.setSelected(false);
                }
                
                if(rs3.getInt(6) == 1){
                    jchkMarca.setSelected(true);
                }else{
                    jchkMarca.setSelected(false);
                }
                
                if(rs3.getInt(7) == 1){
                    jchkModelo.setSelected(true);
                }else{
                    jchkModelo.setSelected(false);
                }
                
                if(rs3.getInt(8) == 1){
                    jchkUsu.setSelected(true);
                }else{
                    jchkUsu.setSelected(false);
                }
                
                if(rs3.getInt(9) == 1){
                   jchkPermisos.setSelected(true);
                }else{
                   jchkPermisos.setSelected(false);
                }                                
            }else{
                jchkSerieRe.setSelected(false);
                jchkSerieFac.setSelected(false);
                jchkSuc.setSelected(false);
                jchkBodega.setSelected(false);
                jchkMarca.setSelected(false);
                jchkModelo.setSelected(false);
                jchkUsu.setSelected(false);
                jchkPermisos.setSelected(false);                
            }             
            rs3.close();
            st3.close();
            cn.close();                                    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar permisos en: " + e,"CyCo", JOptionPane.INFORMATION_MESSAGE);
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
        jLabel2 = new javax.swing.JLabel();
        lblSalir = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbUsu = new javax.swing.JTable();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        jbtnGrabar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jchkParametro = new javax.swing.JCheckBox();
        jSeparator14 = new javax.swing.JSeparator();
        jchkSerieFac = new javax.swing.JCheckBox();
        jchkSerieRe = new javax.swing.JCheckBox();
        jchkSuc = new javax.swing.JCheckBox();
        jchkModelo = new javax.swing.JCheckBox();
        jchkUsu = new javax.swing.JCheckBox();
        jchkMarca = new javax.swing.JCheckBox();
        jchkBodega = new javax.swing.JCheckBox();
        jchkPermisos = new javax.swing.JCheckBox();
        jPanel9 = new javax.swing.JPanel();
        jchkFactura = new javax.swing.JCheckBox();
        jchkReporte = new javax.swing.JCheckBox();
        jchkConf = new javax.swing.JCheckBox();
        jSeparator10 = new javax.swing.JSeparator();
        jchkMenuM = new javax.swing.JCheckBox();
        jPanel10 = new javax.swing.JPanel();
        jchkVentas = new javax.swing.JCheckBox();
        jchkCuentas = new javax.swing.JCheckBox();
        jchkReportes = new javax.swing.JCheckBox();
        jSeparator13 = new javax.swing.JSeparator();
        jPanel11 = new javax.swing.JPanel();
        jchkClientes = new javax.swing.JCheckBox();
        jchkCompras = new javax.swing.JCheckBox();
        jchkProductos = new javax.swing.JCheckBox();
        jchkProve = new javax.swing.JCheckBox();
        jchkTras = new javax.swing.JCheckBox();
        jchkParam = new javax.swing.JCheckBox();
        jchkConfi = new javax.swing.JCheckBox();
        jSeparator12 = new javax.swing.JSeparator();
        jchkInven = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(41, 42, 44));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Corbel", 1, 36)); // NOI18N
        jLabel2.setForeground(java.awt.Color.gray);
        jLabel2.setText("Permisos de Usuario");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 360, 40));

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
        jPanel1.add(lblSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 10, 50, 40));

        jtbUsu.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jtbUsu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jtbUsu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtbUsuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtbUsu);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 360, 460));
        jPanel1.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 960, 20));

        jSeparator9.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel1.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 60, 10, 460));

        jbtnGrabar.setBackground(new java.awt.Color(0, 0, 0));
        jbtnGrabar.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jbtnGrabar.setForeground(new java.awt.Color(255, 255, 255));
        jbtnGrabar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/guardar32_1.png"))); // NOI18N
        jbtnGrabar.setText("Grabar");
        jbtnGrabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnGrabarActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnGrabar, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 450, 550, 70));

        jPanel3.setBackground(new java.awt.Color(41, 42, 44));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.cyan));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jchkParametro.setBackground(new java.awt.Color(41, 42, 44));
        jchkParametro.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jchkParametro.setForeground(java.awt.Color.cyan);
        jchkParametro.setText("Parametros");
        jchkParametro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jchkParametroMouseClicked(evt);
            }
        });
        jPanel3.add(jchkParametro, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 20));
        jPanel3.add(jSeparator14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 32, 220, 10));

        jchkSerieFac.setBackground(new java.awt.Color(41, 42, 44));
        jchkSerieFac.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jchkSerieFac.setForeground(new java.awt.Color(204, 204, 204));
        jchkSerieFac.setText("Serie Factura");
        jchkSerieFac.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jchkSerieFacMouseClicked(evt);
            }
        });
        jPanel3.add(jchkSerieFac, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        jchkSerieRe.setBackground(new java.awt.Color(41, 42, 44));
        jchkSerieRe.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jchkSerieRe.setForeground(new java.awt.Color(204, 204, 204));
        jchkSerieRe.setText("Serie Recibo");
        jchkSerieRe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jchkSerieReMouseClicked(evt);
            }
        });
        jPanel3.add(jchkSerieRe, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 120, -1));

        jchkSuc.setBackground(new java.awt.Color(41, 42, 44));
        jchkSuc.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jchkSuc.setForeground(new java.awt.Color(204, 204, 204));
        jchkSuc.setText("Sucursal");
        jchkSuc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jchkSucMouseClicked(evt);
            }
        });
        jPanel3.add(jchkSuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 90, -1));

        jchkModelo.setBackground(new java.awt.Color(41, 42, 44));
        jchkModelo.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jchkModelo.setForeground(new java.awt.Color(204, 204, 204));
        jchkModelo.setText("Modelo");
        jchkModelo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jchkModeloMouseClicked(evt);
            }
        });
        jPanel3.add(jchkModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, -1, -1));

        jchkUsu.setBackground(new java.awt.Color(41, 42, 44));
        jchkUsu.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jchkUsu.setForeground(new java.awt.Color(204, 204, 204));
        jchkUsu.setText("Usuario");
        jchkUsu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jchkUsuMouseClicked(evt);
            }
        });
        jPanel3.add(jchkUsu, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 100, -1, -1));

        jchkMarca.setBackground(new java.awt.Color(41, 42, 44));
        jchkMarca.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jchkMarca.setForeground(new java.awt.Color(204, 204, 204));
        jchkMarca.setText("Marca");
        jchkMarca.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jchkMarcaMouseClicked(evt);
            }
        });
        jPanel3.add(jchkMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 40, -1, -1));

        jchkBodega.setBackground(new java.awt.Color(41, 42, 44));
        jchkBodega.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jchkBodega.setForeground(new java.awt.Color(204, 204, 204));
        jchkBodega.setText("Bodega");
        jchkBodega.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jchkBodegaMouseClicked(evt);
            }
        });
        jPanel3.add(jchkBodega, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 90, -1));

        jchkPermisos.setBackground(new java.awt.Color(41, 42, 44));
        jchkPermisos.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jchkPermisos.setForeground(new java.awt.Color(204, 204, 204));
        jchkPermisos.setText("Permisos");
        jchkPermisos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jchkPermisosMouseClicked(evt);
            }
        });
        jPanel3.add(jchkPermisos, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, -1, -1));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 270, 260, 170));

        jPanel9.setBackground(new java.awt.Color(41, 42, 44));
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.cyan));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jchkFactura.setBackground(new java.awt.Color(41, 42, 44));
        jchkFactura.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jchkFactura.setForeground(new java.awt.Color(204, 204, 204));
        jchkFactura.setText("Facturacion");
        jchkFactura.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jchkFacturaMouseClicked(evt);
            }
        });
        jPanel9.add(jchkFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 120, -1));

        jchkReporte.setBackground(new java.awt.Color(41, 42, 44));
        jchkReporte.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jchkReporte.setForeground(new java.awt.Color(204, 204, 204));
        jchkReporte.setText("Reportes");
        jchkReporte.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jchkReporteMouseClicked(evt);
            }
        });
        jPanel9.add(jchkReporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 100, -1));

        jchkConf.setBackground(new java.awt.Color(41, 42, 44));
        jchkConf.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jchkConf.setForeground(new java.awt.Color(204, 204, 204));
        jchkConf.setText("Configuraciones");
        jchkConf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jchkConfMouseClicked(evt);
            }
        });
        jPanel9.add(jchkConf, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 150, -1));
        jPanel9.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 32, 220, 10));

        jchkMenuM.setBackground(new java.awt.Color(41, 42, 44));
        jchkMenuM.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jchkMenuM.setForeground(java.awt.Color.cyan);
        jchkMenuM.setText("Menu Principal");
        jchkMenuM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jchkMenuMMouseClicked(evt);
            }
        });
        jPanel9.add(jchkMenuM, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 20));

        jPanel1.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 110, 260, 140));

        jPanel10.setBackground(new java.awt.Color(41, 42, 44));
        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.cyan));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jchkVentas.setBackground(new java.awt.Color(41, 42, 44));
        jchkVentas.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jchkVentas.setForeground(new java.awt.Color(204, 204, 204));
        jchkVentas.setText("Reporte de Ventas");
        jchkVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jchkVentasMouseClicked(evt);
            }
        });
        jPanel10.add(jchkVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 170, -1));

        jchkCuentas.setBackground(new java.awt.Color(41, 42, 44));
        jchkCuentas.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jchkCuentas.setForeground(new java.awt.Color(204, 204, 204));
        jchkCuentas.setText("Cuentas por Cobrar");
        jchkCuentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jchkCuentasMouseClicked(evt);
            }
        });
        jPanel10.add(jchkCuentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 170, -1));

        jchkReportes.setBackground(new java.awt.Color(41, 42, 44));
        jchkReportes.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jchkReportes.setForeground(java.awt.Color.cyan);
        jchkReportes.setText("Reportes");
        jchkReportes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jchkReportesMouseClicked(evt);
            }
        });
        jPanel10.add(jchkReportes, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 20));
        jPanel10.add(jSeparator13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 32, 220, 10));

        jPanel1.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 110, 260, 140));

        jPanel11.setBackground(new java.awt.Color(41, 42, 44));
        jPanel11.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.cyan));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jchkClientes.setBackground(new java.awt.Color(41, 42, 44));
        jchkClientes.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jchkClientes.setForeground(new java.awt.Color(204, 204, 204));
        jchkClientes.setText("Clientes");
        jchkClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jchkClientesMouseClicked(evt);
            }
        });
        jPanel11.add(jchkClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 90, -1));

        jchkCompras.setBackground(new java.awt.Color(41, 42, 44));
        jchkCompras.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jchkCompras.setForeground(new java.awt.Color(204, 204, 204));
        jchkCompras.setText("Compras");
        jchkCompras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jchkComprasMouseClicked(evt);
            }
        });
        jPanel11.add(jchkCompras, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, -1, -1));

        jchkProductos.setBackground(new java.awt.Color(41, 42, 44));
        jchkProductos.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jchkProductos.setForeground(new java.awt.Color(204, 204, 204));
        jchkProductos.setText("Productos");
        jchkProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jchkProductosMouseClicked(evt);
            }
        });
        jchkProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkProductosActionPerformed(evt);
            }
        });
        jPanel11.add(jchkProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 100, -1));

        jchkProve.setBackground(new java.awt.Color(41, 42, 44));
        jchkProve.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jchkProve.setForeground(new java.awt.Color(204, 204, 204));
        jchkProve.setText("Proveedores");
        jchkProve.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jchkProveMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jchkProveMouseEntered(evt);
            }
        });
        jPanel11.add(jchkProve, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, -1, -1));

        jchkTras.setBackground(new java.awt.Color(41, 42, 44));
        jchkTras.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jchkTras.setForeground(new java.awt.Color(204, 204, 204));
        jchkTras.setText("Traslado");
        jchkTras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jchkTrasMouseClicked(evt);
            }
        });
        jPanel11.add(jchkTras, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 100, -1));

        jchkParam.setBackground(new java.awt.Color(41, 42, 44));
        jchkParam.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jchkParam.setForeground(new java.awt.Color(204, 204, 204));
        jchkParam.setText("Parametros");
        jchkParam.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jchkParamMouseClicked(evt);
            }
        });
        jPanel11.add(jchkParam, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, -1, -1));

        jchkConfi.setBackground(new java.awt.Color(41, 42, 44));
        jchkConfi.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jchkConfi.setForeground(java.awt.Color.cyan);
        jchkConfi.setText("Configuraciones");
        jchkConfi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jchkConfiMouseClicked(evt);
            }
        });
        jPanel11.add(jchkConfi, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 20));
        jPanel11.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 32, 220, 10));

        jchkInven.setBackground(new java.awt.Color(41, 42, 44));
        jchkInven.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jchkInven.setForeground(new java.awt.Color(204, 204, 204));
        jchkInven.setText("Iventario");
        jchkInven.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jchkInvenMouseClicked(evt);
            }
        });
        jPanel11.add(jchkInven, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 100, -1));

        jPanel1.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 270, 260, 170));

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel4.setForeground(java.awt.Color.gray);
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Menus y Sub-menus");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 60, 240, 40));
        jPanel1.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 93, 550, 10));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 980, 540));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblSalirMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseMoved
        lblSalir.setBorder(javax.swing.BorderFactory.createLineBorder(Color.RED));
    }//GEN-LAST:event_lblSalirMouseMoved

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked

        jfParametros para = new jfParametros();              
        para.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblSalirMouseClicked

    private void lblSalirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSalirMouseEntered

    private void lblSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseExited
        lblSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(41,42,44)));
    }//GEN-LAST:event_lblSalirMouseExited

    private void jchkProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkProductosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jchkProductosActionPerformed

    private void jchkReportesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jchkReportesMouseClicked
        if(jchkReportes.isSelected()){
            AcitvarSubMenus(4);        
        }else if(jchkReportes.isSelected() == false){
            AcitvarSubMenus(3);
        }
    }//GEN-LAST:event_jchkReportesMouseClicked

    private void jchkConfiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jchkConfiMouseClicked
         if(jchkConfi.isSelected()){
            AcitvarSubMenus(6);          
         }else if(jchkConfi.isSelected() == false){
            AcitvarSubMenus(5);          
         }        
    }//GEN-LAST:event_jchkConfiMouseClicked

    private void jchkParametroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jchkParametroMouseClicked
        if(jchkParametro.isSelected()){
            AcitvarSubMenus(8);
        }else if(jchkParametro.isSelected() == false){
            AcitvarSubMenus(7);
        }
    }//GEN-LAST:event_jchkParametroMouseClicked

    private void jchkFacturaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jchkFacturaMouseClicked
                
    }//GEN-LAST:event_jchkFacturaMouseClicked

    private void jchkReporteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jchkReporteMouseClicked
               
    }//GEN-LAST:event_jchkReporteMouseClicked

    private void jchkConfMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jchkConfMouseClicked
    }//GEN-LAST:event_jchkConfMouseClicked

    private void jchkVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jchkVentasMouseClicked
               
    }//GEN-LAST:event_jchkVentasMouseClicked

    private void jchkCuentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jchkCuentasMouseClicked
        if(jchkCuentas.isSelected()){
            iCuentas = 1;        
        }else{
            iCuentas = 0;        
        }
    }//GEN-LAST:event_jchkCuentasMouseClicked

    private void jchkClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jchkClientesMouseClicked
                                    
    }//GEN-LAST:event_jchkClientesMouseClicked

    private void jchkProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jchkProductosMouseClicked
    
                  
    }//GEN-LAST:event_jchkProductosMouseClicked

    private void jchkTrasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jchkTrasMouseClicked
                        
    }//GEN-LAST:event_jchkTrasMouseClicked

    private void jchkComprasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jchkComprasMouseClicked
                     
    }//GEN-LAST:event_jchkComprasMouseClicked

    private void jchkProveMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jchkProveMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jchkProveMouseEntered

    private void jchkProveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jchkProveMouseClicked
        
    }//GEN-LAST:event_jchkProveMouseClicked

    private void jchkParamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jchkParamMouseClicked
       
    }//GEN-LAST:event_jchkParamMouseClicked

    private void jchkSerieReMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jchkSerieReMouseClicked
                                         
    }//GEN-LAST:event_jchkSerieReMouseClicked

    private void jchkSerieFacMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jchkSerieFacMouseClicked
                      
    }//GEN-LAST:event_jchkSerieFacMouseClicked

    private void jchkSucMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jchkSucMouseClicked
                          
    
    }//GEN-LAST:event_jchkSucMouseClicked

    private void jchkBodegaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jchkBodegaMouseClicked
                        
    }//GEN-LAST:event_jchkBodegaMouseClicked

    private void jchkMarcaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jchkMarcaMouseClicked
                      
    }//GEN-LAST:event_jchkMarcaMouseClicked

    private void jchkModeloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jchkModeloMouseClicked
           
    }//GEN-LAST:event_jchkModeloMouseClicked

    private void jchkUsuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jchkUsuMouseClicked
                                  
    }//GEN-LAST:event_jchkUsuMouseClicked

    private void jchkPermisosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jchkPermisosMouseClicked
       
    }//GEN-LAST:event_jchkPermisosMouseClicked

    private void jchkMenuMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jchkMenuMMouseClicked
        if(jchkMenuM.isSelected()){
            AcitvarSubMenus(2);
        }else if(jchkMenuM.isSelected() == false){
            AcitvarSubMenus(1);
        }
    }//GEN-LAST:event_jchkMenuMMouseClicked

    private void jtbUsuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbUsuMouseClicked
        if (this.jtbUsu.getRowCount() == 0 ){                         
            JOptionPane.showMessageDialog(null,"No hay registros para seleccionar","CyCo",JOptionPane.INFORMATION_MESSAGE); 
            return;
         }
        idUsuario = Integer.parseInt(String.valueOf(jtbUsu.getValueAt(jtbUsu.getSelectedRow(), 0)));
        mostrar_actuales();                        
    }//GEN-LAST:event_jtbUsuMouseClicked

    private void jbtnGrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGrabarActionPerformed
        try {
            if(idUsuario == 0){
                JOptionPane.showMessageDialog(null,"No se ha seleccionaro un usuario para grabar permisos","CyCo",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            verifica_chekcs();
                                                            
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jbtnGrabarActionPerformed

    private void jchkInvenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jchkInvenMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jchkInvenMouseClicked

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
            java.util.logging.Logger.getLogger(jfPermisos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfPermisos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfPermisos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfPermisos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jfPermisos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JButton jbtnGrabar;
    private javax.swing.JCheckBox jchkBodega;
    private javax.swing.JCheckBox jchkClientes;
    private javax.swing.JCheckBox jchkCompras;
    private javax.swing.JCheckBox jchkConf;
    private javax.swing.JCheckBox jchkConfi;
    private javax.swing.JCheckBox jchkCuentas;
    private javax.swing.JCheckBox jchkFactura;
    private javax.swing.JCheckBox jchkInven;
    private javax.swing.JCheckBox jchkMarca;
    private javax.swing.JCheckBox jchkMenuM;
    private javax.swing.JCheckBox jchkModelo;
    private javax.swing.JCheckBox jchkParam;
    private javax.swing.JCheckBox jchkParametro;
    private javax.swing.JCheckBox jchkPermisos;
    private javax.swing.JCheckBox jchkProductos;
    private javax.swing.JCheckBox jchkProve;
    private javax.swing.JCheckBox jchkReporte;
    private javax.swing.JCheckBox jchkReportes;
    private javax.swing.JCheckBox jchkSerieFac;
    private javax.swing.JCheckBox jchkSerieRe;
    private javax.swing.JCheckBox jchkSuc;
    private javax.swing.JCheckBox jchkTras;
    private javax.swing.JCheckBox jchkUsu;
    private javax.swing.JCheckBox jchkVentas;
    private javax.swing.JTable jtbUsu;
    private javax.swing.JLabel lblSalir;
    // End of variables declaration//GEN-END:variables
}
