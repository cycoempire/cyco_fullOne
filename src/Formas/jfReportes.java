/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formas;

import Animacion.Fade;
import clases_varias.permisos;
import clases_varias.user_logged;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author wwwki
 */
public class jfReportes extends javax.swing.JFrame {
 
 
    /**
     * Creates new form jfReportes
     */
    public jfReportes() {
        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("/Iconos/bender_cyco.png")).getImage());
        this.setLocationRelativeTo(null);
        Iterator<user_logged> itrLogg = Menu_principal.user_memory.iterator();
        user_logged ul = itrLogg.next();     
        permisos pr = new permisos();      
        pr.idUsu = ul.getidUsy();
        pr.cargar_permisos(2);
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
        lblReportes = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblCuentas = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(41, 42, 44));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator1.setBackground(java.awt.Color.cyan);
        jSeparator1.setForeground(java.awt.Color.cyan);
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 780, 20));

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
        jPanel1.add(lblMinimizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 10, 50, 40));

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
        jPanel1.add(lblSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 10, 50, 40));

        jLabel1.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel1.setForeground(java.awt.Color.cyan);
        jLabel1.setText("Reportes");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, 20));

        lblReportes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblReportes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/reportes128.png"))); // NOI18N
        lblReportes.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lblReportesMouseMoved(evt);
            }
        });
        lblReportes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblReportesMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblReportesMouseExited(evt);
            }
        });
        jPanel1.add(lblReportes, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 180, 220));

        jLabel3.setBackground(new java.awt.Color(41, 42, 44));
        jLabel3.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(232, 251, 244));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Cuentas por Cobrar");
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 270, 160, 70));

        lblCuentas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCuentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cuentas.png"))); // NOI18N
        lblCuentas.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lblCuentasMouseMoved(evt);
            }
        });
        lblCuentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCuentasMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblCuentasMouseExited(evt);
            }
        });
        jPanel1.add(lblCuentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 80, 180, 220));

        jLabel5.setBackground(new java.awt.Color(41, 42, 44));
        jLabel5.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(232, 251, 244));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Reporte de Ventas");
        jLabel5.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 270, 160, 60));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 360));

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
                        
        Menu_principal mp = new Menu_principal();
        int dialog = JOptionPane.YES_NO_OPTION;
        int result = JOptionPane.showConfirmDialog(null, "¿Desea salir?","Salir",dialog);
        if(result == 0){
            //Fade.JFrameFadeOut(1f, 0f, 0.1f, 50, this,Fade.DISPOSE);                      
            this.dispose();
            mp.setVisible(true);
            Fade.JFrameFadeIn(0f, 1f, 0.2f, 50, mp);  
        }
    }//GEN-LAST:event_lblSalirMouseClicked

    private void lblSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseExited
        lblSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(41,42,44)));
    }//GEN-LAST:event_lblSalirMouseExited

    private void lblReportesMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblReportesMouseMoved
         lblReportes.setBorder(javax.swing.BorderFactory.createLineBorder(Color.CYAN));
    }//GEN-LAST:event_lblReportesMouseMoved

    private void lblCuentasMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCuentasMouseMoved
        lblCuentas.setBorder(javax.swing.BorderFactory.createLineBorder(Color.CYAN));
    }//GEN-LAST:event_lblCuentasMouseMoved

    private void lblCuentasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCuentasMouseExited
        lblCuentas.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(41,42,44)));
    }//GEN-LAST:event_lblCuentasMouseExited

    private void lblReportesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblReportesMouseExited
        lblReportes.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(41,42,44)));
    }//GEN-LAST:event_lblReportesMouseExited

    private void lblReportesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblReportesMouseClicked
        if(lblReportes.isEnabled()){
            jfRVentas fc = new jfRVentas();               
            Fade.JFrameFadeOut(1f, 0f, 0.1f, 50, this,Fade.DISPOSE);
            //this.dispose();       
            fc.setVisible(true);
        }        
    }//GEN-LAST:event_lblReportesMouseClicked

    private void lblCuentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCuentasMouseClicked
        if(lblCuentas.isEnabled()){
            jfCuentas_cobrar fc = new jfCuentas_cobrar();              
            Fade.JFrameFadeOut(1f, 0f, 0.1f, 50, this,Fade.DISPOSE);        
            //this.dispose();       
            fc.setVisible(true);
        }
        
    }//GEN-LAST:event_lblCuentasMouseClicked

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
            java.util.logging.Logger.getLogger(jfReportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfReportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfReportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfReportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jfReportes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    public static javax.swing.JLabel lblCuentas;
    private javax.swing.JLabel lblMinimizar;
    public static javax.swing.JLabel lblReportes;
    private javax.swing.JLabel lblSalir;
    // End of variables declaration//GEN-END:variables
}
