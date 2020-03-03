/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formas;

import clases_varias.SetearCombo;
import clases_varias.conectar;
import clases_varias.idex_combo;
import clases_varias.user_logged;
import clases_varias.varias_funciones;
import java.awt.Color;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author wwwki
 */
public class jfCompra extends javax.swing.JFrame {
    conectar cc = new conectar();
    public int iProceso_compra;
    public int idCompra;    
    

    public jfCompra() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon(getClass().getResource("/Iconos/CyCo64.png")).getImage());
        jtxtIdCompra.setEditable(false);
        idex_combo combo = new idex_combo();
        combo.llenarCombo(jcboTipoDoc, "id_tipo_doc", "ttipo_documento", "txt_descripcion");
        combo.llenarCombo(jcboSucu, "id_sucursal", "tsucursal", "txt_descripcion");        
        combo.llenarCombo(jcboProve, "id_prove", "tprovedor", "txt_descripcion");
        combo.llenarCombo(jcboEstado, "id_estado", "testado", "txt_desc");        
        //jbtnNext.setEnabled(false);
        
        varias_funciones vr = new varias_funciones();
        Integer iUltimo = vr.recup_ult_numero("tcompra", "id_compra");
        jtxtIdCompra.setText(iUltimo.toString());                  
    }

    
                
    void Grabar_compra(){
        String ssql;  
                        
        try {            
            Iterator<user_logged> itrLogg = Menu_principal.user_memory.iterator();
            user_logged ul = itrLogg.next();                                      
            Connection cn = cc.conexion();
            varias_funciones vr = new varias_funciones(); 
            String fecha_ingresa = vr.formatoFecha(jtxtFecha_formated.getText());                                                                                                                                     
            CallableStatement cst = cn.prepareCall("{ call sp_grabar_compra_cabecera(?,?,?,?,?,?,?,?,?,?)}");
            jfCompra_detalle cd = new jfCompra_detalle();
            
            if(jcboEstado.getItemAt(jcboEstado.getSelectedIndex()).getId() == 3){
                iProceso_compra = 3;            
            }                                    
//            switch (iProceso_compra){            
                switch (iProceso_compra){
                
                case 1 :              
//                        JOptionPane.showMessageDialog(null, "Entro a compra nueva");
                        cst.setInt(1, Integer.parseInt(jtxtIdCompra.getText()));                      //id compra
                        cst.setInt(2, Integer.parseInt(jtxtNoDoc.getText()));                         //no compra
                        cst.setInt(3, jcboProve.getItemAt(jcboProve.getSelectedIndex()).getId());     //id_provedor
                        cst.setInt(4, jcboTipoDoc.getItemAt(jcboTipoDoc.getSelectedIndex()).getId()); //id_tipo_doc
                        cst.setInt(5, jcboSucu.getItemAt(jcboSucu.getSelectedIndex()).getId());       //id_suc
                        cst.setInt(6, jcboBodega.getItemAt(jcboBodega.getSelectedIndex()).getId());   //id_bodega
                        cst.setString(7, fecha_ingresa);                                              //fecha_compra
                        cst.setInt(8, jcboEstado.getItemAt(jcboEstado.getSelectedIndex()).getId());   //estado
                        cst.setInt(9, ul.getidUsy());                                                 //id_usuario
                        cst.setInt(10, iProceso_compra);                                              //iProceso
                        cst.execute();
                        cst.close();
                        JOptionPane.showMessageDialog(null, "¡Inicio de compra grabada con exito!", "CyCo", JOptionPane.INFORMATION_MESSAGE);                                                                                                    
                        cd.idCompra = Integer.parseInt(jtxtIdCompra.getText());                                                                             
                        cd.iEstado_compra = jcboEstado.getItemAt(jcboEstado.getSelectedIndex()).getId();
                        cd.iTipo_doc = jcboTipoDoc.getItemAt(jcboTipoDoc.getSelectedIndex()).getId();
                        cd.idSucu = jcboSucu.getItemAt(jcboSucu.getSelectedIndex()).getId();
                        cd.idBodega = jcboBodega.getItemAt(jcboBodega.getSelectedIndex()).getId();                             
                        cd.setVisible(true);
                        this.dispose();
                        limpiar();
                    break;
                
                case 2 :     
                        //Modificar compra
                        cst.setInt(1, Integer.parseInt(jtxtIdCompra.getText()));                      //id compra
                        cst.setString(2, jtxtNoDoc.getText());                                        //no compra
                        cst.setInt(3, jcboProve.getItemAt(jcboProve.getSelectedIndex()).getId());     //id_provedor
                        cst.setInt(4, jcboTipoDoc.getItemAt(jcboTipoDoc.getSelectedIndex()).getId()); //id_tipo_doc
                        cst.setInt(5, jcboSucu.getItemAt(jcboSucu.getSelectedIndex()).getId());       //id_suc
                        cst.setInt(6, jcboBodega.getItemAt(jcboBodega.getSelectedIndex()).getId());   //id_bodega
                        cst.setString(7, fecha_ingresa);                                              //fecha_compra
                        cst.setInt(8, jcboEstado.getItemAt(jcboEstado.getSelectedIndex()).getId());   //estado
                        cst.setInt(9, ul.getidUsy());                                                 //id_uduario
                        cst.setInt(10, iProceso_compra);                                              //iProceso
                        cst.execute();
                        cst.close();
                        JOptionPane.showMessageDialog(null, "¡Inicio de compra grabada con exito!", "CyCo", JOptionPane.INFORMATION_MESSAGE);                                                                        
                        cd.idCompra = Integer.parseInt(jtxtIdCompra.getText());                                                                             
                        cd.iEstado_compra = jcboEstado.getItemAt(jcboEstado.getSelectedIndex()).getId();
                        cd.iTipo_doc = jcboTipoDoc.getItemAt(jcboTipoDoc.getSelectedIndex()).getId();
                        cd.idSucu = jcboSucu.getItemAt(jcboSucu.getSelectedIndex()).getId();
                        cd.idBodega = jcboBodega.getItemAt(jcboBodega.getSelectedIndex()).getId();                             
                        cd.recup_detalle_compra();
                        cd.setVisible(true);
                        this.dispose();
                        limpiar();
                    break;
                    
                case 3 :                     
//                        JOptionPane.showMessageDialog(null, "1: " +idCompra + "\n"+ "2:" + jcboTipoDoc.getItemAt(jcboTipoDoc.getSelectedIndex()).getId() +"\n"+ "3: "+ ul.getidUsy());
                        
                        CallableStatement ccst = cn.prepareCall("{ call sp_anula_compra(?,?,?)}");
                        ccst.setInt(1, Integer.parseInt(jtxtIdCompra.getText()));
                        ccst.setInt(2, jcboTipoDoc.getItemAt(jcboTipoDoc.getSelectedIndex()).getId());
                        ccst.setInt(3, ul.getidUsy());
                        ccst.execute();
                        ccst.close();
                        JOptionPane.showMessageDialog(null, "¡La compra ha sido anulada, solo podra consultarla", "CyCo", JOptionPane.INFORMATION_MESSAGE);                                                
                        cd.idCompra = Integer.parseInt(jtxtIdCompra.getText());                                                                             
                        cd.iEstado_compra = jcboEstado.getItemAt(jcboEstado.getSelectedIndex()).getId();
                        cd.iTipo_doc = jcboTipoDoc.getItemAt(jcboTipoDoc.getSelectedIndex()).getId();
                        cd.idSucu = jcboSucu.getItemAt(jcboSucu.getSelectedIndex()).getId();
                        cd.idBodega = jcboBodega.getItemAt(jcboBodega.getSelectedIndex()).getId();                             
                        cd.recup_detalle_compra();
                        limpiar();                              
                        cd.setVisible(true);
                        this.dispose();
                    break;            
                case 4:                     
                        cd.idCompra = Integer.parseInt(jtxtIdCompra.getText());                                                                             
                        cd.iEstado_compra = jcboEstado.getItemAt(jcboEstado.getSelectedIndex()).getId();
                        cd.iTipo_doc = jcboTipoDoc.getItemAt(jcboTipoDoc.getSelectedIndex()).getId();
                        cd.idSucu = jcboSucu.getItemAt(jcboSucu.getSelectedIndex()).getId();
                        cd.idBodega = jcboBodega.getItemAt(jcboBodega.getSelectedIndex()).getId();                             
                        cd.recup_detalle_compra();
                        cd.jbtnAdd.setEnabled(false);
                        cd.jbtGrabar.setEnabled(false);
                        cd.setVisible(true);
                        this.dispose();
                        limpiar();
                    break;
            }               
            cn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al grabar cabecera de compra en: " + e, "CyCo", JOptionPane.INFORMATION_MESSAGE);                                                
        }                        
    }
        
    
    public void recuperar_compra(){
    
        try {            
            Connection cn = cc.conexion();            
            CallableStatement cst = cn.prepareCall("{ call sp_recuperar_compra(?,?,?,?,?,?,?,?)}");
            cst.setInt(1, Integer.parseInt(jtxtIdCompra.getText()));
            cst.registerOutParameter(2, java.sql.Types.INTEGER);
            cst.registerOutParameter(3, java.sql.Types.VARCHAR);
            cst.registerOutParameter(4, java.sql.Types.INTEGER);
            cst.registerOutParameter(5, java.sql.Types.INTEGER);
            cst.registerOutParameter(6, java.sql.Types.INTEGER);
            cst.registerOutParameter(7, java.sql.Types.DATE);
            cst.registerOutParameter(8, java.sql.Types.INTEGER);
            cst.execute();
            
            SetearCombo sc = new SetearCombo();
            varias_funciones vr = new varias_funciones();
            jcboTipoDoc.setSelectedIndex(sc.set_tipo_doc(cst.getInt(2)));
            jtxtNoDoc.setText(cst.getString(3));
            jcboSucu.setSelectedIndex(sc.set_sucursal(cst.getInt(4)));
            jcboBodega.setSelectedIndex(sc.set_bodega(cst.getInt(4), cst.getInt(5)));
            jcboProve.setSelectedIndex(sc.set_prove(cst.getInt(6)));                       
            jtxtFecha_formated.setText(vr.MostrarformatoFecha2(cst.getDate(7).toString()));
            jcboEstado.setSelectedIndex(cst.getInt(8));
            
            cst.close();
            cn.close();                                    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperar datos en: "+e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
        }                   
    }
                
    
    
    void limpiar(){
    
    jtxtIdCompra.setText("0");
    jcboTipoDoc.setSelectedIndex(0);
    jtxtNoDoc.setText("0");
    jcboSucu.setSelectedIndex(0);
    jcboBodega.setSelectedIndex(0);
    jcboProve.setSelectedIndex(0);
    jtxtFecha_formated.setText("");
    jcboEstado.setSelectedIndex(0);                            
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
        jSeparator4 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        lblSalir = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jcboEstado = new javax.swing.JComboBox<>();
        jtxtFecha_formated = new javax.swing.JFormattedTextField();
        jLabel14 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jtxtIdCompra = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jcboTipoDoc = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jtxtNoDoc = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jcboProve = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jbtnNext = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jcboBodega = new javax.swing.JComboBox<>();
        jcboSucu = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(41, 42, 44));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 580, 570, 10));

        jLabel1.setFont(new java.awt.Font("Corbel", 1, 36)); // NOI18N
        jLabel1.setForeground(java.awt.Color.gray);
        jLabel1.setText("Compras");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 210, 40));

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
        jPanel1.add(lblSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 10, 40, 40));

        jPanel7.setBackground(new java.awt.Color(41, 42, 44));
        jPanel7.setBorder(new javax.swing.border.LineBorder(java.awt.Color.cyan, 1, true));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel9.setForeground(java.awt.Color.cyan);
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Estado:");
        jPanel7.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 20, 220, 20));

        jcboEstado.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jPanel7.add(jcboEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 40, 220, -1));

        jtxtFecha_formated.setBackground(new java.awt.Color(41, 42, 44));
        jtxtFecha_formated.setForeground(new java.awt.Color(255, 255, 255));
        jtxtFecha_formated.setFormatterFactory(new javax.swing.JFormattedTextField.AbstractFormatterFactory() {
            public javax.swing.JFormattedTextField.AbstractFormatter
            getFormatter(javax.swing.JFormattedTextField tf){

                try {
                    return new javax.swing.text.MaskFormatter("##/##/####");
                }
                catch (java.text.ParseException pe){
                    pe.printStackTrace();
                }
                return null;
            }
        });
        jtxtFecha_formated.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jPanel7.add(jtxtFecha_formated, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 220, 30));

        jLabel14.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel14.setForeground(java.awt.Color.cyan);
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("Fecha Compra:");
        jPanel7.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 180, 20));

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 480, 490, 90));

        jPanel8.setBackground(new java.awt.Color(41, 42, 44));
        jPanel8.setBorder(new javax.swing.border.LineBorder(java.awt.Color.cyan, 1, true));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtxtIdCompra.setBackground(new java.awt.Color(41, 42, 44));
        jtxtIdCompra.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        jtxtIdCompra.setForeground(new java.awt.Color(255, 255, 255));
        jtxtIdCompra.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxtIdCompra.setText("0");
        jtxtIdCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtIdCompraActionPerformed(evt);
            }
        });
        jPanel8.add(jtxtIdCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 40, 140, 50));

        jLabel10.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel10.setForeground(java.awt.Color.cyan);
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("No. de Compra");
        jPanel8.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 180, 20));

        jPanel1.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, 490, 100));

        jPanel9.setBackground(new java.awt.Color(41, 42, 44));
        jPanel9.setBorder(new javax.swing.border.LineBorder(java.awt.Color.cyan, 1, true));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel12.setForeground(java.awt.Color.cyan);
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("No. Documento:");
        jPanel9.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 20, 180, 20));

        jcboTipoDoc.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jcboTipoDoc.setForeground(new java.awt.Color(255, 255, 255));
        jPanel9.add(jcboTipoDoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 230, -1));

        jLabel13.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel13.setForeground(java.awt.Color.cyan);
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Tipo Documento:");
        jPanel9.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 210, 20));

        jtxtNoDoc.setBackground(new java.awt.Color(41, 42, 44));
        jtxtNoDoc.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jtxtNoDoc.setForeground(new java.awt.Color(255, 255, 255));
        jPanel9.add(jtxtNoDoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 40, 220, 30));

        jPanel1.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 490, 90));

        jPanel10.setBackground(new java.awt.Color(41, 42, 44));
        jPanel10.setBorder(new javax.swing.border.LineBorder(java.awt.Color.cyan, 1, true));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jcboProve.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jcboProve.setForeground(new java.awt.Color(255, 255, 255));
        jPanel10.add(jcboProve, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 460, -1));

        jLabel15.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel15.setForeground(java.awt.Color.cyan);
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("Proovedor:");
        jPanel10.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 180, 20));

        jPanel1.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 380, 490, 90));
        jPanel1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 570, 20));

        jbtnNext.setBackground(new java.awt.Color(0, 0, 0));
        jbtnNext.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jbtnNext.setForeground(new java.awt.Color(255, 255, 255));
        jbtnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/derecho.png"))); // NOI18N
        jbtnNext.setText("Continuar");
        jbtnNext.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jbtnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNextActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnNext, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 590, 160, 60));

        jPanel11.setBackground(new java.awt.Color(41, 42, 44));
        jPanel11.setBorder(new javax.swing.border.LineBorder(java.awt.Color.cyan, 1, true));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel16.setForeground(java.awt.Color.cyan);
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("Bodega:");
        jPanel11.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 20, 180, 20));

        jcboBodega.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jcboBodega.setForeground(new java.awt.Color(255, 255, 255));
        jPanel11.add(jcboBodega, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 40, 220, -1));

        jcboSucu.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jcboSucu.setForeground(new java.awt.Color(255, 255, 255));
        jcboSucu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboSucuActionPerformed(evt);
            }
        });
        jPanel11.add(jcboSucu, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 230, -1));

        jLabel17.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel17.setForeground(java.awt.Color.cyan);
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("Sucursal:");
        jPanel11.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 180, 20));

        jPanel1.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 280, 490, 90));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 590, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblSalirMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseMoved
        lblSalir.setBorder(javax.swing.BorderFactory.createLineBorder(Color.RED));
    }//GEN-LAST:event_lblSalirMouseMoved

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        jfListadoCompras lc = new jfListadoCompras();       
        lc.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblSalirMouseClicked

    private void lblSalirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSalirMouseEntered

    private void lblSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseExited
        lblSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(41,42,44)));
    }//GEN-LAST:event_lblSalirMouseExited

    private void jtxtIdCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtIdCompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtIdCompraActionPerformed

    private void jbtnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNextActionPerformed
        Grabar_compra();                        
    }//GEN-LAST:event_jbtnNextActionPerformed

    private void jcboSucuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcboSucuActionPerformed
        idex_combo combo = new idex_combo();        
                
        int idSuc = 0;
        if(jcboSucu.getItemAt(jcboSucu.getSelectedIndex()).getId() == null){
            idSuc = 0;
        }else{
            idSuc = jcboSucu.getItemAt(jcboSucu.getSelectedIndex()).getId();        
        }
        
        jcboBodega.removeAllItems();
        combo.llenarCombo2(jcboBodega, "id_bodega", "tBodega", "txt_descripcion"," and id_suc = " + "'" + idSuc + "'");
    }//GEN-LAST:event_jcboSucuActionPerformed

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
            java.util.logging.Logger.getLogger(jfCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jfCompra().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JButton jbtnNext;
    private javax.swing.JComboBox<idex_combo> jcboBodega;
    private javax.swing.JComboBox<idex_combo> jcboEstado;
    private javax.swing.JComboBox<idex_combo> jcboProve;
    private javax.swing.JComboBox<idex_combo> jcboSucu;
    private javax.swing.JComboBox<idex_combo> jcboTipoDoc;
    private javax.swing.JFormattedTextField jtxtFecha_formated;
    public static javax.swing.JTextField jtxtIdCompra;
    private javax.swing.JTextField jtxtNoDoc;
    private javax.swing.JLabel lblSalir;
    // End of variables declaration//GEN-END:variables
}
