/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formas;

import Animacion.Fade;
import clases_varias.conectar;
import clases_varias.user_logged;
import clases_varias.varias_funciones;
import java.awt.Color;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author wwwki
 */
public class jfCliente extends javax.swing.JFrame {
    
    public static Integer iTipoProc;
    public static Integer Id_clliente;
    conectar cc = new conectar();            

    /**
     * Creates new form jfCliente
     */
    public jfCliente() {
        initComponents();        
        this.setIconImage(new ImageIcon(getClass().getResource("/Iconos/CyCo64.png")).getImage());
        this.setLocationRelativeTo(null);
    }

    
    void validarCampos(){
    
    String sMensaje = "";
    
    if(jtNombre.getText().equals("")){
        sMensaje = "Favor completar el campo Nombre \n";        
    }
    
    if(jtNit.getText().equals("")){
        sMensaje = sMensaje + "Favor completar el campo Nit \n";
    }
    
    if(jtTelefono.getText().equals("")){
        sMensaje = sMensaje + "Favor completar el campo Telefono \n";
    }
    
    if(jtDireccion.getText().equals("")){
        sMensaje = sMensaje + "Favor completar el campo Direccion \n";
    }
          
    
    if(!sMensaje.equals("")){        
        JOptionPane.showMessageDialog(null, sMensaje, "CyCo", JOptionPane.INFORMATION_MESSAGE);
    }else{    
        
        if(iTipoProc ==1){
            validaRepetidos();        
        
        }else{
            grabarCliente();        
        }                                
    }
    
    }
    
    void validaRepetidos(){
        String ssql;
        Connection cn = cc.conexion();
        try {
            ssql = "";
            ssql = ssql + "select * from tcliente";
            ssql = ssql + " where txt_nombre = " + "'" + jtNombre.getText() + "'";
            ssql = ssql + " or txt_nit = " + "'" + jtNit.getText() + "'";
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
            Connection cn = cc.conexion();
            
            if(chkActivo.isSelected() == true){                
                iActivo = 1;                
            }else{
                iActivo = 0;
            }
            
            if(iTipoProc ==1){
                varias_funciones ultimo = new varias_funciones();
                int ulti = ultimo.recup_ult_numero("tcliente", "id_cliente");
                                                           
                CallableStatement cst = cn.prepareCall("{ call sp_grabar_cliente (?,?,?,?,?,?,?,?)}");
                cst.setInt(1, ulti);
                cst.setString(2, jtNombre.getText());
                cst.setString(3, jtNit.getText());
                cst.setString(4, jtTelefono.getText());
                cst.setString(5, jtDireccion.getText());
                cst.setInt(6, iActivo);
                cst.setInt(7, 1);
                cst.setInt(8, Integer.parseInt(jtxtCredito.getText()));
                cst.execute();
                JOptionPane.showMessageDialog(null, "Registro guardado con exito", "CyCo", JOptionPane.INFORMATION_MESSAGE);
                limpiar(); 
                cst.close();
            }else{
                //----------Modificar datos-----------------------------------------                        
                CallableStatement cstt = cn.prepareCall("{ call sp_grabar_cliente (?,?,?,?,?,?,?,?)}");
                cstt.setInt(1, Id_clliente);
                cstt.setString(2, jtNombre.getText());
                cstt.setString(3, jtNit.getText());
                cstt.setString(4, jtTelefono.getText());
                cstt.setString(5, jtDireccion.getText());
                cstt.setInt(6, iActivo);
                cstt.setInt(7, 2);
                cstt.setInt(8, Integer.parseInt(jtxtCredito.getText()));
                cstt.execute();
                JOptionPane.showMessageDialog(null, "Registro modificado con exito", "CyCo", JOptionPane.INFORMATION_MESSAGE);
                limpiar();   
                cstt.close();
            }  
            cn.close();
            
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
        }                
    }    
    
    void limpiar(){
        jtNombre.setText("");
        jtNit.setText("");
        jtTelefono.setText("");
        jtDireccion.setText("");
        chkActivo.setSelected(false);   
        iTipoProc =1;
        jtxtCredito.setText("");
    }    
    
    
    void recuperar_cliente(Integer id_cliente){
        Integer iActivo;
        
        try {
            Connection cn = cc.conexion();
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
            //Obentemos la salida de datos del sp            
            jtNombre.setText(cst.getString(2));
            jtNit.setText(cst.getString(3));
            jtTelefono.setText(cst.getString(4));
            jtDireccion.setText(cst.getString(5));
            if(cst.getInt(6) == 1){
                chkActivo.setSelected(true);
            }else{
                chkActivo.setSelected(false);
            }                                                            
            jtxtCredito.setText(cst.getString(7));
            
            cst.close();
            cn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en: " + e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
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
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lblMinimizar = new javax.swing.JLabel();
        lblSalir = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        chkActivo = new javax.swing.JCheckBox();
        jSeparator6 = new javax.swing.JSeparator();
        btnGuardar = new javax.swing.JButton();
        btnConsulta = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jtTelefono = new javax.swing.JTextField();
        jtDireccion = new javax.swing.JTextField();
        jtNit = new javax.swing.JTextField();
        jtNombre = new javax.swing.JTextField();
        jtxtCredito = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(41, 42, 44));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Corbel", 0, 36)); // NOI18N
        jLabel1.setForeground(java.awt.Color.gray);
        jLabel1.setText("Clientes");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 130, 40));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 710, 20));

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
        jPanel1.add(lblMinimizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 6, 50, 40));

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
        jPanel1.add(lblSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 6, 50, 40));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 300, 200, 20));

        jLabel3.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel3.setForeground(java.awt.Color.cyan);
        jLabel3.setText("Nombre:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, -1, -1));

        jLabel4.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel4.setForeground(java.awt.Color.cyan);
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Nit:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 170, 90, -1));

        jLabel5.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel5.setForeground(java.awt.Color.cyan);
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Dias de Credito:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 280, 170, -1));
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 250, 530, 20));
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 190, 200, 20));
        jPanel1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 130, 530, 20));

        jLabel7.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel7.setForeground(java.awt.Color.cyan);
        jLabel7.setText("Direccion:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, -1, -1));

        chkActivo.setBackground(new java.awt.Color(41, 42, 44));
        chkActivo.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        chkActivo.setForeground(java.awt.Color.cyan);
        chkActivo.setText("Activo");
        jPanel1.add(chkActivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 280, 110, 40));
        jPanel1.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 710, 30));

        btnGuardar.setBackground(java.awt.Color.black);
        btnGuardar.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        btnGuardar.setForeground(java.awt.Color.cyan);
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/guardar32_1.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 150, 50));

        btnConsulta.setBackground(java.awt.Color.black);
        btnConsulta.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        btnConsulta.setForeground(java.awt.Color.cyan);
        btnConsulta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/buscar32_2.png"))); // NOI18N
        btnConsulta.setText("Consultar");
        btnConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaActionPerformed(evt);
            }
        });
        jPanel1.add(btnConsulta, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 350, 150, 50));

        jLabel2.setForeground(java.awt.Color.cyan);
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/clientes132.png"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 40, 50));

        jtTelefono.setBackground(new java.awt.Color(41, 42, 44));
        jtTelefono.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jtTelefono.setForeground(new java.awt.Color(232, 251, 244));
        jtTelefono.setBorder(null);
        jtTelefono.setCaretColor(new java.awt.Color(232, 251, 244));
        jtTelefono.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtTelefonoFocusGained(evt);
            }
        });
        jtTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtTelefonoActionPerformed(evt);
            }
        });
        jPanel1.add(jtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 160, 200, 30));

        jtDireccion.setBackground(new java.awt.Color(41, 42, 44));
        jtDireccion.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jtDireccion.setForeground(new java.awt.Color(232, 251, 244));
        jtDireccion.setBorder(null);
        jtDireccion.setCaretColor(new java.awt.Color(232, 251, 244));
        jtDireccion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtDireccionFocusGained(evt);
            }
        });
        jPanel1.add(jtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 220, 530, 40));

        jtNit.setBackground(new java.awt.Color(41, 42, 44));
        jtNit.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jtNit.setForeground(new java.awt.Color(232, 251, 244));
        jtNit.setBorder(null);
        jtNit.setCaretColor(new java.awt.Color(232, 251, 244));
        jtNit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtNitFocusGained(evt);
            }
        });
        jPanel1.add(jtNit, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 160, 200, 40));

        jtNombre.setBackground(new java.awt.Color(41, 42, 44));
        jtNombre.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jtNombre.setForeground(new java.awt.Color(232, 251, 244));
        jtNombre.setBorder(null);
        jtNombre.setCaretColor(new java.awt.Color(232, 251, 244));
        jtNombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtNombreFocusGained(evt);
            }
        });
        jPanel1.add(jtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 100, 530, 40));

        jtxtCredito.setBackground(new java.awt.Color(41, 42, 44));
        jtxtCredito.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jtxtCredito.setForeground(new java.awt.Color(232, 251, 244));
        jtxtCredito.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxtCredito.setBorder(null);
        jPanel1.add(jtxtCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 280, 190, 20));
        jPanel1.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 190, 220, 20));

        jLabel6.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel6.setForeground(java.awt.Color.cyan);
        jLabel6.setText("Telefono:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 170, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 730, 410));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblSalirMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseMoved
        lblSalir.setBorder(javax.swing.BorderFactory.createLineBorder(Color.RED));
    }//GEN-LAST:event_lblSalirMouseMoved

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        jfConfigura cf = new jfConfigura();
//        int dialog = JOptionPane.YES_NO_OPTION;
//        int result = JOptionPane.showConfirmDialog(null, "¿Desea salir?","Salir",dialog);        
//        if(result == 0){            
            this.dispose();
            //Fade.JFrameFadeOut(1f, 0f, 0.1f, 50, this,Fade.DISPOSE);            
            cf.setVisible(true);
            //Fade.JFrameFadeIn(0f, 1f, 0.2f, 50, cf);  
            //Fade.JFrameFadeOut(1f, 0f, 0.1f, 50, this,Fade.EXIT);            
//        }
    }//GEN-LAST:event_lblSalirMouseClicked

    private void lblSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseExited
        lblSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(41,42,44)));
    }//GEN-LAST:event_lblSalirMouseExited

    private void btnConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaActionPerformed
        jfConsulta_Cliente ccli = new jfConsulta_Cliente();        
        this.dispose();
        ccli.setVisible(true);                
        Fade.JFrameFadeIn(0f, 1f, 0.2f, 50, ccli); 
        
    }//GEN-LAST:event_btnConsultaActionPerformed

    private void lblMinimizarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinimizarMouseExited
        lblMinimizar.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(41,42,44)));
    }//GEN-LAST:event_lblMinimizarMouseExited

    private void lblMinimizarMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinimizarMouseMoved
        lblMinimizar.setBorder(javax.swing.BorderFactory.createLineBorder(Color.CYAN));
    }//GEN-LAST:event_lblMinimizarMouseMoved

    private void jtTelefonoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtTelefonoFocusGained
       
        jtTelefono.selectAll();        
    }//GEN-LAST:event_jtTelefonoFocusGained

    private void jtDireccionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtDireccionFocusGained
        jtDireccion.selectAll();  
    }//GEN-LAST:event_jtDireccionFocusGained

    private void jtNitFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtNitFocusGained
        jtNit.selectAll();
    }//GEN-LAST:event_jtNitFocusGained

    private void jtTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtTelefonoActionPerformed

    private void jtNombreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtNombreFocusGained
            jtNombre.selectAll();
    }//GEN-LAST:event_jtNombreFocusGained

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
       
        int dialog = JOptionPane.YES_NO_OPTION;
        int result = JOptionPane.showConfirmDialog(null, "¿Desea Continuar?","CyCo",dialog);        
        if(result == 0){
            validarCampos();
        }                
    }//GEN-LAST:event_btnGuardarActionPerformed

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
            java.util.logging.Logger.getLogger(jfCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jfCliente().setVisible(true);
            }
        });
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConsulta;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JCheckBox chkActivo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JTextField jtDireccion;
    private javax.swing.JTextField jtNit;
    private javax.swing.JTextField jtNombre;
    private javax.swing.JTextField jtTelefono;
    private javax.swing.JTextField jtxtCredito;
    private javax.swing.JLabel lblMinimizar;
    private javax.swing.JLabel lblSalir;
    // End of variables declaration//GEN-END:variables
}
