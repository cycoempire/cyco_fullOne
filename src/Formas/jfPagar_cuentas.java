/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formas;

import Animacion.Fade;
import clases_varias.conectar;
import clases_varias.idex_combo;
import clases_varias.user_logged;
import clases_varias.varias_funciones;
import java.awt.Color;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


/**
 *
 * @author wwwki
 */
public class jfPagar_cuentas extends javax.swing.JFrame {
    public static Integer id_factura;
    conectar cc = new conectar();            
    /**
     * Creates new form jfPagar_cuentas
     */
    public jfPagar_cuentas() {
        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("/Iconos/bender_cyco.png")).getImage());
        this.setLocationRelativeTo(null);      
        
        idex_combo combo = new idex_combo();
        combo.llenarCombo(jcboTipoPago, "id_tipo_pago", "ttipo_pago", "txt_tipo_pago");
        combo.llenarCombo(jcboSerieRecibo, "id_serie_recibo", "tserie_recibo", "txt_no_serie_recibo");
    }

    
    void calculo_pagar(){
    
        String ssql;
        Double dSaldo_pagado;
        Double dTotal;
        try {
            Connection cn = cc.conexion();
            //Primero recuperamos la sumatoria de los pagos de la factura pendiente
            ssql = "";
            ssql = ssql + " select sum(monto_abono) from tbitacora_pagos_cuentas";
            ssql = ssql + " where id_factura = " + id_factura;
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            if(rs.next()){
                dSaldo_pagado = rs.getDouble(1);            
            }else{
                dSaldo_pagado = Double.parseDouble(lblTotal.getText());                
            }                          
            
            //Luego calculamos el saldo pendiente
            
            if(lblTotal.getText().equals(dSaldo_pagado)){
                 lblPendiente.setText(lblTotal.getText());
            }else{
                dTotal = Double.parseDouble(lblTotal.getText());                 
                DecimalFormat df = new DecimalFormat("#.00");                
                lblPendiente.setText(df.format(Double.parseDouble(lblTotal.getText()) - dSaldo_pagado));                                                                                                                                                    
            }
            rs.close();
            st.close();
            cn.close();
                                                                                  
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en calcular pendiente en: " +e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
        }        
    }
    
    
    void validar_monto(){
        String sMensaje ="";
        String ssql;
        Double dTotal;
        Double dSuma;
        try {                                    
            Connection cn = cc.conexion();
            if(Double.parseDouble(jtxtAbono.getText()) > Double.parseDouble(lblTotal.getText())){
                sMensaje = "* El monto ingresado es mayor al saldo pendiente.\n";            
            }
            
            if(jtxtAbono.getText().equals("0.00") || jtxtAbono.getText().equals("0.0") || jtxtAbono.getText().equals("0") || lblPendiente.getText().equals(".00")){
               sMensaje = sMensaje + "* Favor ingresar un monto mayor a 0.00 \n";
            }
            
            if(jcboSerieRecibo.getItemAt(jcboSerieRecibo.getSelectedIndex()).getId() == 0){
                sMensaje = sMensaje + "* Favor seleccione un tipo de pago. \n";                
            }
            
            if(jtxtComprobante.getText().equals("")){
                sMensaje = sMensaje + "* Favor ingresar Numero de comprobante.";                
            }                        
            
            ssql = "";
            ssql = ssql + " select sum(monto_abono) from tbitacora_pagos_cuentas";
            ssql = ssql + " where id_factura = " + id_factura;
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            if(rs.next()){
                dTotal = rs.getDouble(1);     
                Double dPagado = rs.getDouble(1);
                dSuma = dTotal + (Double.parseDouble(jtxtAbono.getText()));
                if(dSuma > Double.parseDouble(lblTotal.getText())){
                    sMensaje = sMensaje + "* La sumatoria del monto ingresado mas el saldo pagado es mayor al saldo pendiente total\n"; 
                    sMensaje = sMensaje + "* Saldo pagado: "+dPagado +"\n";
                    sMensaje = sMensaje + "* Monto ingresado: "+jtxtAbono.getText() + "\n";
                    sMensaje = sMensaje + "* Monto ingresado + Saldo pagado: "+dSuma + "\n";
                }
            }
            rs.close();
            st.close();
            cn.close();
                                                
            if(!sMensaje.equals("")){
                JOptionPane.showMessageDialog(null, sMensaje, "CyCo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }else{
                grabar_bitacora();
            }                                                
        } catch (Exception e) {            
            JOptionPane.showMessageDialog(null, "Error al validar montos en: "+e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    void grabar_bitacora(){
        
        try {
            Iterator<user_logged> itrLogg = Menu_principal.user_memory.iterator();
            user_logged ul = itrLogg.next();       
            
            Connection cn = cc.conexion();
            varias_funciones vr = new varias_funciones();
            int ultimo_num = vr.recup_ult_numero("tbitacora_pagos_cuentas", "id_bitacora");
            CallableStatement cst = cn.prepareCall("{ call sp_grabar_bitacora_cuentas(?,?,?,?,?,?,?,?)}");
            cst.setInt(1, ultimo_num);
            cst.setInt(2, id_factura);
            cst.setDouble(3, Double.parseDouble(jtxtAbono.getText()));
            cst.setInt(4, 1);
            cst.setInt(5, jcboSerieRecibo.getItemAt(jcboSerieRecibo.getSelectedIndex()).getId());
            cst.setString(6, jtxtComprobante.getText());
            cst.setInt(7, jcboSerieRecibo.getItemAt(jcboSerieRecibo.getSelectedIndex()).getId());
            cst.setInt(8, ul.getidUsy());
            cst.execute();
            cst.close();
            JOptionPane.showMessageDialog(null, "¡Registro guardado con exito!", "CyCo", JOptionPane.INFORMATION_MESSAGE);
            calculo_pagar();
            jtxtAbono.setText("0.00");
            
            if(lblPendiente.getText().equals("0") || lblPendiente.getText().equals("0.0") || lblPendiente.getText().equals("0.00") || lblPendiente.getText().equals(".00")){
                JOptionPane.showMessageDialog(null, "¡La factura a sido pagada en su totalidad!", "CyCo", JOptionPane.INFORMATION_MESSAGE);                
                actualizar_estado_factura();
            }
            cn.close();
            jtxtComprobante.setText("");
            jcboSerieRecibo.setSelectedIndex(0);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al grabar bitacora en: "+e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
        }
    
    }
    
    void actualizar_estado_factura(){
    
        try {          
            Connection cn = cc.conexion();                                
            CallableStatement cst = cn.prepareCall("{call sp_actualiza_estado_venta(?,?)}");
            cst.setInt(1, id_factura); // factura o recibo
            cst.setInt(2, 2);           
            cst.execute();  
            cst.close();
            cn.close();                                                            
            JOptionPane.showMessageDialog(null, "Estado de la factura actualizado", "CyCo", JOptionPane.INFORMATION_MESSAGE);
            jbtnGrabar.setEnabled(false);
            jtxtAbono.setEditable(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar estado factura en: "+e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
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
        lblSalir = new javax.swing.JLabel();
        lblMinimizar = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jbtnGrabar = new javax.swing.JButton();
        jbtnBitacora = new javax.swing.JButton();
        jbtnDetalle = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        lblTotal = new javax.swing.JLabel();
        lblPendiente = new javax.swing.JLabel();
        jtxtAbono = new javax.swing.JTextField();
        lblNo = new javax.swing.JLabel();
        lblSerie = new javax.swing.JLabel();
        lblTIpoDoc = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jtxtComprobante = new javax.swing.JTextField();
        jcboSerieRecibo = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jcboTipoPago = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(41, 42, 44));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 500, 10));

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
        jPanel1.add(lblSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 10, 50, 40));

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
        jPanel1.add(lblMinimizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, 50, 40));

        jLabel1.setFont(new java.awt.Font("Corbel", 0, 36)); // NOI18N
        jLabel1.setForeground(java.awt.Color.gray);
        jLabel1.setText("Pagar Documento");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 40));

        jLabel2.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jLabel2.setForeground(java.awt.Color.cyan);
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("No. Comprobante :");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 340, 150, -1));

        jLabel3.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jLabel3.setForeground(java.awt.Color.cyan);
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Total a Pagar:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 110, 110, -1));

        jLabel4.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jLabel4.setForeground(java.awt.Color.cyan);
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Pendiente:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 160, 100, -1));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 220, 110, 10));

        jbtnGrabar.setBackground(java.awt.Color.black);
        jbtnGrabar.setFont(new java.awt.Font("Corbel", 1, 18)); // NOI18N
        jbtnGrabar.setForeground(new java.awt.Color(232, 251, 244));
        jbtnGrabar.setText("Grabar");
        jbtnGrabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnGrabarActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnGrabar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, 480, 30));

        jbtnBitacora.setBackground(java.awt.Color.black);
        jbtnBitacora.setFont(new java.awt.Font("Corbel", 0, 14)); // NOI18N
        jbtnBitacora.setForeground(new java.awt.Color(232, 251, 244));
        jbtnBitacora.setText("Bitacora");
        jbtnBitacora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnBitacoraActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnBitacora, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 120, 30));

        jbtnDetalle.setBackground(java.awt.Color.black);
        jbtnDetalle.setFont(new java.awt.Font("Corbel", 0, 14)); // NOI18N
        jbtnDetalle.setForeground(new java.awt.Color(232, 251, 244));
        jbtnDetalle.setText("Detalle Factura");
        jbtnDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnDetalleActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnDetalle, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 430, 130, 30));

        jLabel5.setFont(new java.awt.Font("Corbel", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(232, 251, 244));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Serie:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 40, -1));

        jLabel6.setFont(new java.awt.Font("Corbel", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(232, 251, 244));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Fecha:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 70, -1, -1));
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 500, 10));

        jLabel7.setFont(new java.awt.Font("Corbel", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(232, 251, 244));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Tipo Doc.:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 60, -1));
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 500, 10));
        jPanel1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 130, 110, 10));
        jPanel1.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 180, 110, 10));

        lblTotal.setFont(new java.awt.Font("Ebrima", 0, 24)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(232, 251, 244));
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotal.setText("0.00");
        jPanel1.add(lblTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 110, 90, 20));

        lblPendiente.setFont(new java.awt.Font("Ebrima", 0, 24)); // NOI18N
        lblPendiente.setForeground(java.awt.Color.orange);
        lblPendiente.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPendiente.setText("0.00");
        jPanel1.add(lblPendiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 160, 90, 20));

        jtxtAbono.setBackground(new java.awt.Color(41, 42, 44));
        jtxtAbono.setFont(new java.awt.Font("Ebrima", 0, 24)); // NOI18N
        jtxtAbono.setForeground(java.awt.Color.green);
        jtxtAbono.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtAbono.setText("0.00");
        jtxtAbono.setBorder(null);
        jPanel1.add(jtxtAbono, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 200, 90, 20));

        lblNo.setFont(new java.awt.Font("Ebrima", 1, 16)); // NOI18N
        lblNo.setForeground(new java.awt.Color(232, 251, 244));
        jPanel1.add(lblNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 68, 60, 20));

        lblSerie.setFont(new java.awt.Font("Ebrima", 1, 16)); // NOI18N
        lblSerie.setForeground(new java.awt.Color(232, 251, 244));
        jPanel1.add(lblSerie, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 68, 60, 20));

        lblTIpoDoc.setFont(new java.awt.Font("Ebrima", 1, 16)); // NOI18N
        lblTIpoDoc.setForeground(new java.awt.Color(232, 251, 244));
        jPanel1.add(lblTIpoDoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 68, 80, 20));

        jLabel8.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jLabel8.setForeground(java.awt.Color.cyan);
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Abonar:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 200, 110, -1));

        jLabel9.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jLabel9.setForeground(java.awt.Color.cyan);
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Tipo de Pago:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 240, 110, -1));
        jPanel1.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 360, 210, 10));

        jtxtComprobante.setBackground(new java.awt.Color(41, 42, 44));
        jtxtComprobante.setFont(new java.awt.Font("Ebrima", 0, 24)); // NOI18N
        jtxtComprobante.setForeground(java.awt.Color.green);
        jtxtComprobante.setBorder(null);
        jPanel1.add(jtxtComprobante, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 340, 210, 20));

        jcboSerieRecibo.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        jcboSerieRecibo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboSerieReciboActionPerformed(evt);
            }
        });
        jPanel1.add(jcboSerieRecibo, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 290, 210, -1));

        jLabel10.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jLabel10.setForeground(java.awt.Color.cyan);
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("No. Serie:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 290, 150, -1));

        jcboTipoPago.setFont(new java.awt.Font("Ebrima", 1, 12)); // NOI18N
        jPanel1.add(jcboTipoPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 240, 210, -1));

        jLabel11.setFont(new java.awt.Font("Corbel", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(232, 251, 244));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("No. Doc.");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, -1, -1));

        lblFecha.setFont(new java.awt.Font("Ebrima", 1, 16)); // NOI18N
        lblFecha.setForeground(new java.awt.Color(232, 251, 244));
        jPanel1.add(lblFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 68, 90, 20));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 520, 470));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblSalirMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseMoved
        lblSalir.setBorder(javax.swing.BorderFactory.createLineBorder(Color.RED));
    }//GEN-LAST:event_lblSalirMouseMoved

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        int dialog = JOptionPane.YES_NO_OPTION;
        int result = JOptionPane.showConfirmDialog(null, "¿Desea salir?","Salir",dialog);
        if(result == 0){
            //Fade.JFrameFadeIn(0f, 1f, 0.2f, 50, this);
            //Fade.JFrameFadeOut(1f, 0f, 0.1f, 50, this,Fade.DISPOSE);
            jfCuentas_cobrar mp = new jfCuentas_cobrar();               
            this.dispose();
            mp.setVisible(true);
            Fade.JFrameFadeIn(0f, 1f, 0.2f, 50, mp);

        }
    }//GEN-LAST:event_lblSalirMouseClicked

    private void lblSalirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSalirMouseEntered

    private void lblSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseExited
        lblSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(41,42,44)));
    }//GEN-LAST:event_lblSalirMouseExited

    private void lblMinimizarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinimizarMouseExited
        lblMinimizar.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(41,42,44)));
    }//GEN-LAST:event_lblMinimizarMouseExited

    private void lblMinimizarMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinimizarMouseMoved
        lblMinimizar.setBorder(javax.swing.BorderFactory.createLineBorder(Color.CYAN));
    }//GEN-LAST:event_lblMinimizarMouseMoved

    private void jbtnBitacoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnBitacoraActionPerformed
        
        jfBitacora_pagos bp = new jfBitacora_pagos();
        bp.idFactura = id_factura;
        bp.mostrar_bitacora();
        bp.lblSerie.setText(lblSerie.getText());
        bp.lblTipoDoc.setText(lblTIpoDoc.getText());
        bp.lblFecha.setText(lblFecha.getText());
        bp.lblTipoDoc.setText(lblNo.getText());                
        bp.setVisible(true);
    }//GEN-LAST:event_jbtnBitacoraActionPerformed

    private void jbtnGrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGrabarActionPerformed
        validar_monto();
    }//GEN-LAST:event_jbtnGrabarActionPerformed

    private void jbtnDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnDetalleActionPerformed
        jfDetalle_factura bp = new jfDetalle_factura();
        bp.idFactura = id_factura;
        bp.mostrar_detalle();
        bp.lblSerie.setText(lblSerie.getText());
        bp.lblTipoDoc.setText(lblTIpoDoc.getText());
        bp.lblFecha.setText(lblFecha.getText());
        bp.lblNo.setText(lblNo.getText());        
        bp.setVisible(true);
    }//GEN-LAST:event_jbtnDetalleActionPerformed

    private void jcboSerieReciboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcboSerieReciboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcboSerieReciboActionPerformed

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
            java.util.logging.Logger.getLogger(jfPagar_cuentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfPagar_cuentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfPagar_cuentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfPagar_cuentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jfPagar_cuentas().setVisible(true);
            }
        });
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JButton jbtnBitacora;
    private javax.swing.JButton jbtnDetalle;
    private javax.swing.JButton jbtnGrabar;
    private javax.swing.JComboBox<idex_combo> jcboSerieRecibo;
    private javax.swing.JComboBox<idex_combo> jcboTipoPago;
    private javax.swing.JTextField jtxtAbono;
    private javax.swing.JTextField jtxtComprobante;
    public static javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblMinimizar;
    public static javax.swing.JLabel lblNo;
    private javax.swing.JLabel lblPendiente;
    private javax.swing.JLabel lblSalir;
    public static javax.swing.JLabel lblSerie;
    public static javax.swing.JLabel lblTIpoDoc;
    public static javax.swing.JLabel lblTotal;
    // End of variables declaration//GEN-END:variables
}
