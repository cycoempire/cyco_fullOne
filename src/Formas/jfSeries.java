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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author wwwki
 */
public class jfSeries extends javax.swing.JFrame {
    public Integer iProceso;
    public Integer id_serie;
    conectar cc = new conectar();    
    
    
    /**
     * Creates new form jfSeries
     */
    public jfSeries() {
        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("/Iconos/bender_cyco.png")).getImage());
        this.setLocationRelativeTo(null);   
         //La tabla no se autoresizable
        jtbSerie.setAutoResizeMode(jtbSerie.AUTO_RESIZE_OFF);         
        mostrar_series();
        
    }
    
    
    void valida_serie(){
    String ssql;
    
        try {
            Connection cn = cc.conexion();
            ssql = "";
            ssql = ssql + " select * from tserie_fac";
            ssql = ssql + " where txt_serie = '" + jtxtSerie.getText() +"'";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            if(rs.next()){
                JOptionPane.showMessageDialog(null, "Numero de serie existente", "CyCO", JOptionPane.INFORMATION_MESSAGE);            
                return;
            }else{
                grabar_serie();
            }   
            rs.close();
            st.close();
            cn.close();
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, "Error al validar serie existente en: "+e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
        }    
    }
    

    void grabar_serie(){
        Integer iEstado;
        try {
            Connection cn = cc.conexion();
            varias_funciones vr = new varias_funciones();
            int ultimo = vr.recup_ult_numero("tserie_fac", "id_serie");
            
            if(chkActivo.isSelected() == true){
                iEstado = 1;
            }else{
                iEstado = 0;
            }
            
            
            if(iProceso == 1){
                CallableStatement cst = cn.prepareCall("{ call sp_grabar_serie(?,?,?,?)}");
                cst.setInt(1, ultimo);
                cst.setString(2, jtxtSerie.getText());
                cst.setInt(3, iEstado);
                cst.setInt(4, iProceso);
                cst.execute();
                cst.close();
                JOptionPane.showMessageDialog(null, "Registro guardado con exito", "CyCo", JOptionPane.INFORMATION_MESSAGE);
                iProceso = 1;
                jtxtSerie.setText("");
                chkActivo.setSelected(false);   
                mostrar_series();
            }else{
                CallableStatement cst = cn.prepareCall("{ call sp_grabar_serie(?,?,?,?)}");
                cst.setInt(1, id_serie);
                cst.setString(2, jtxtSerie.getText());
                cst.setInt(3, iEstado);
                cst.setInt(4, iProceso);
                cst.execute();
                cst.close();
                JOptionPane.showMessageDialog(null, "Registro modificado con exito", "CyCo", JOptionPane.INFORMATION_MESSAGE);
                iProceso = 1;
                jtxtSerie.setText("");
                chkActivo.setSelected(false);            
                mostrar_series();
            }               
            cn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en guardar registro en: "+e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
        }            
    }
    
    void mostrar_series(){
    String ssql;
    
        try {
            Connection cn = cc.conexion();
            DefaultTableModel sr = new DefaultTableModel();
            sr.addColumn("id_serie");
            sr.addColumn("SERIE");
            sr.addColumn("ESTADO");
            jtbSerie.setModel(sr);
            
            
            
             
            //id
            jtbSerie.getColumnModel().getColumn(0).setMaxWidth(0);
            jtbSerie.getColumnModel().getColumn(0).setMinWidth(0);
            jtbSerie.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
            jtbSerie.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
        
            //serie
            jtbSerie.getColumnModel().getColumn(1).setMaxWidth(229);
            jtbSerie.getColumnModel().getColumn(1).setMinWidth(229);
            jtbSerie.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(229);
            jtbSerie.getTableHeader().getColumnModel().getColumn(1).setMinWidth(229);
            
            //estado
            jtbSerie.getColumnModel().getColumn(2).setMaxWidth(225);
            jtbSerie.getColumnModel().getColumn(2).setMinWidth(225);
            jtbSerie.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(225);
            jtbSerie.getTableHeader().getColumnModel().getColumn(2).setMinWidth(225);
            
            
            
            ssql = "";
            ssql = ssql + " select ts.id_serie,ts.txt_serie, ";
            ssql = ssql + " te.txt_desc";
            ssql = ssql + " from tserie_fac ts";
            ssql = ssql + " inner join testado te on te.id_estado = ts.sn_activo";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            String datos[] = new String[3];
            while(rs.next()){
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                sr.addRow(datos);
            }
            jtbSerie.setModel(sr);    
            rs.close();
            st.close();
            cn.close();
            iProceso = 1;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar series en: "+e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
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
        lblMinimizar = new javax.swing.JLabel();
        lblSalir = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        chkActivo = new javax.swing.JCheckBox();
        jSeparator7 = new javax.swing.JSeparator();
        jbtnGrabar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbSerie = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jSeparator8 = new javax.swing.JSeparator();
        jtxtSerie = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(41, 42, 44));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jPanel1.add(lblMinimizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 10, 50, 40));

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
        jPanel1.add(lblSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, 50, 40));
        jPanel1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 460, 10));

        jLabel1.setFont(new java.awt.Font("Corbel", 0, 36)); // NOI18N
        jLabel1.setForeground(java.awt.Color.gray);
        jLabel1.setText("Series de Facturas");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 40));

        jLabel3.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel3.setForeground(java.awt.Color.cyan);
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Serie:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 70, -1));
        jPanel1.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, 210, 10));

        chkActivo.setBackground(new java.awt.Color(41, 42, 44));
        chkActivo.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        chkActivo.setForeground(java.awt.Color.cyan);
        chkActivo.setText("Activo");
        jPanel1.add(chkActivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 80, 100, 30));
        jPanel1.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 460, 10));

        jbtnGrabar.setBackground(java.awt.Color.black);
        jbtnGrabar.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jbtnGrabar.setForeground(new java.awt.Color(232, 251, 244));
        jbtnGrabar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/check32.png"))); // NOI18N
        jbtnGrabar.setText("Grabar");
        jbtnGrabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnGrabarActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnGrabar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 460, -1));

        jtbSerie.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jtbSerie.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jtbSerie.setRowHeight(18);
        jtbSerie.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtbSerieMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtbSerie);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 460, 120));
        jPanel1.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 460, 10));

        jtxtSerie.setBackground(new java.awt.Color(41, 42, 44));
        jtxtSerie.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jtxtSerie.setForeground(new java.awt.Color(232, 251, 244));
        jtxtSerie.setBorder(null);
        jPanel1.add(jtxtSerie, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 210, 20));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 490, 340));

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
        int dialog = JOptionPane.YES_NO_OPTION;
        int result = JOptionPane.showConfirmDialog(null, "¿Desea salir?","Salir",dialog);
        if(result == 0){
            //Fade.JFrameFadeIn(0f, 1f, 0.2f, 50, this);
            //Fade.JFrameFadeOut(1f, 0f, 0.1f, 50, this,Fade.DISPOSE);
            jfParametros mp = new jfParametros();            
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

    private void jtbSerieMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbSerieMouseClicked
        if(evt.getClickCount()==2){
            
                   if (this.jtbSerie.getRowCount() == 0 )
                        { 
                            JOptionPane.showMessageDialog(null,"No hay registros para leccionar","CyCo",JOptionPane.INFORMATION_MESSAGE); 
                            return;
                        }
                   
                   id_serie = Integer.parseInt(String.valueOf(jtbSerie.getValueAt(jtbSerie.getSelectedRow(), 0)));
                   jtxtSerie.setText(String.valueOf(jtbSerie.getValueAt(jtbSerie.getSelectedRow(), 1)));
                   iProceso =2;
                   if(String.valueOf(jtbSerie.getValueAt(jtbSerie.getSelectedRow(), 2)).equals("ACTIVO")){
                       chkActivo.setSelected(true);
                   }else{
                       chkActivo.setSelected(false);
                   }                                                                                               
        }
    }//GEN-LAST:event_jtbSerieMouseClicked

    private void jbtnGrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGrabarActionPerformed
        if(jtxtSerie.getText().equals("")){
            JOptionPane.showMessageDialog(null, "* Favor completar el campo serie", "CyCo", JOptionPane.INFORMATION_MESSAGE);
        }else{
            if(iProceso == 1){
                valida_serie();
            }else{
                grabar_serie();
            }
            
            
        }
    }//GEN-LAST:event_jbtnGrabarActionPerformed

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
            java.util.logging.Logger.getLogger(jfSeries.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfSeries.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfSeries.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfSeries.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jfSeries().setVisible(true);
            }
        });
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chkActivo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JButton jbtnGrabar;
    private javax.swing.JTable jtbSerie;
    private javax.swing.JTextField jtxtSerie;
    private javax.swing.JLabel lblMinimizar;
    private javax.swing.JLabel lblSalir;
    // End of variables declaration//GEN-END:variables
}
