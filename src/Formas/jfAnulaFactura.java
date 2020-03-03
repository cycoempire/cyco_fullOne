/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formas;

import clases_varias.conectar;
import clases_varias.user_logged;
import clases_varias.varias_funciones;
import java.awt.Color;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;


public class jfAnulaFactura extends javax.swing.JFrame {
   conectar cc = new conectar();       
    public jfAnulaFactura() {
        initComponents();
         jAreaMotivo.setLineWrap(true);
         jAreaMotivo.setWrapStyleWord(true);
         this.setLocationRelativeTo(null); 
         lblIdFac.setVisible(false);
    }
       
    
    void recuperaFac(Integer id_factu){    
        try {          
            Connection cn = cc.conexion();
            String ssql = "";
            ssql = ssql + " select no_serie_doc, no_doc, fecha_doc from tventa";
            ssql = ssql + " where id_doc = " + id_factu;                                    
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            if(rs.next()){
                lblSerie.setText(rs.getString(1));
                lblNo.setText(rs.getString(2));
                lblFecha.setText(rs.getString(3));
            }else{
                JOptionPane.showMessageDialog(null, "No se cargaron los datos, salga y vuela a intentarlo", "CyCo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }                    
            rs.close();
            st.close();
            cn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro en recuperar datos en: "+e, "CyCo", JOptionPane.INFORMATION_MESSAGE);            
        }        
    }
    void validaCampo(Integer id_factu){    
        try {           
            if(jAreaMotivo.getText().trim().equals("") || jAreaMotivo.getText().trim().equals(null)){
                JOptionPane.showMessageDialog(null, "Favor ingresar un motivo de anulacion de la factura pra continuar", "CyCo", JOptionPane.INFORMATION_MESSAGE);                                    
            }else{
                anularFac(id_factu);
            }                        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al validar campos en: "+e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
        }    
    }
    
    void anularFac(Integer id_factu){
        try {
            Connection cn = cc.conexion();
            varias_funciones vr = new varias_funciones();
            Integer ult_numero = vr.recup_ult_numero("tanula_fac", "id_anula");    
                        
            Iterator<user_logged> itrLogg = Menu_principal.user_memory.iterator();
            user_logged ul = itrLogg.next();        
                                    
            CallableStatement cst = cn.prepareCall("{ call sp_anula_fac(?,?,?,?)}");
            cst.setInt(1, ult_numero);
            cst.setInt(2, id_factu);
            cst.setString(3, jAreaMotivo.getText());
            cst.setInt(4, ul.getidUsy());
            cst.execute();            
            JOptionPane.showMessageDialog(null,"Factura anulada con exito", "CyCo", JOptionPane.INFORMATION_MESSAGE);            
            jfRVentas rv = new jfRVentas();
            rv.buscar(1);
            
            //rebajar inventario
            
            this.dispose();
            cst.close();
            cn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error en anular factura en: "+ e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
        }   
    }
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        lblSalir = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        lblFecha = new javax.swing.JLabel();
        lblSerie = new javax.swing.JLabel();
        lblNo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jAreaMotivo = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        lblIdFac = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(41, 42, 44));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 380, 10));

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
        jPanel1.add(lblSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 10, 50, 40));

        jLabel1.setFont(new java.awt.Font("Corbel", 0, 36)); // NOI18N
        jLabel1.setForeground(java.awt.Color.gray);
        jLabel1.setText("Anular Factura");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 220, 40));

        jLabel5.setFont(new java.awt.Font("Corbel", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(232, 251, 244));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Serie:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 40, -1));

        jLabel6.setFont(new java.awt.Font("Corbel", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(232, 251, 244));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Fecha:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 70, -1, -1));
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 380, 10));

        jLabel7.setFont(new java.awt.Font("Corbel", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(232, 251, 244));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("No. Factura:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, -1, -1));
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 380, 10));

        lblFecha.setFont(new java.awt.Font("Corbel", 1, 16)); // NOI18N
        lblFecha.setForeground(new java.awt.Color(232, 251, 244));
        jPanel1.add(lblFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 68, 80, 20));

        lblSerie.setFont(new java.awt.Font("Corbel", 1, 16)); // NOI18N
        lblSerie.setForeground(new java.awt.Color(232, 251, 244));
        jPanel1.add(lblSerie, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 68, 70, 20));

        lblNo.setFont(new java.awt.Font("Corbel", 1, 16)); // NOI18N
        lblNo.setForeground(new java.awt.Color(232, 251, 244));
        jPanel1.add(lblNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 68, 60, 20));

        jAreaMotivo.setBackground(java.awt.Color.darkGray);
        jAreaMotivo.setColumns(20);
        jAreaMotivo.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jAreaMotivo.setForeground(new java.awt.Color(232, 251, 244));
        jAreaMotivo.setRows(5);
        jScrollPane1.setViewportView(jAreaMotivo);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, 240, 100));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setForeground(java.awt.Color.cyan);
        jLabel2.setText("Motivo Anulación:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, -1, -1));

        jButton1.setBackground(new java.awt.Color(41, 42, 44));
        jButton1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(232, 251, 244));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/borrar32.png"))); // NOI18N
        jButton1.setText("Anular");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 240, 290, 60));

        lblIdFac.setText("jLabel3");
        jPanel1.add(lblIdFac, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 320));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblSalirMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseMoved
        lblSalir.setBorder(javax.swing.BorderFactory.createLineBorder(Color.RED));
    }//GEN-LAST:event_lblSalirMouseMoved

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        jfRVentas rv = new jfRVentas();
        rv.buscar(1);  
        this.dispose();   
    }//GEN-LAST:event_lblSalirMouseClicked

    private void lblSalirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSalirMouseEntered

    private void lblSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseExited
        lblSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(41,42,44)));
    }//GEN-LAST:event_lblSalirMouseExited

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        validaCampo(Integer.parseInt(lblIdFac.getText()));
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(jfAnulaFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfAnulaFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfAnulaFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfAnulaFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jfAnulaFactura().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea jAreaMotivo;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    public static javax.swing.JLabel lblFecha;
    public static javax.swing.JLabel lblIdFac;
    public static javax.swing.JLabel lblNo;
    private javax.swing.JLabel lblSalir;
    public static javax.swing.JLabel lblSerie;
    // End of variables declaration//GEN-END:variables
}
