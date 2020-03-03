/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formas;

import Animacion.Fade;
import clases_varias.Exportar_excel;
import clases_varias.conectar;
import clases_varias.idex_combo;
import clases_varias.user_logged;
import clases_varias.varias_funciones;
import java.awt.Color;
import java.io.IOException;
import java.sql.CallableStatement;
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
public class jfModelo extends javax.swing.JFrame {
    public Integer iProceso;
    public Integer id_modelo;
    conectar cc = new conectar();    
    
    
    /**
     * Creates new form jfSeries
     */
    public jfModelo() {
        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("/Iconos/bender_cyco.png")).getImage());
        this.setLocationRelativeTo(null);   
         //La tabla no se autoresizable
        jtbMarcas.setAutoResizeMode(jtbMarcas.AUTO_RESIZE_OFF);         
        idex_combo combo = new idex_combo();
        combo.llenarCombo(jcomboMarca, "id_marca", "tmarca", "txt_descripcion_marca");
        
        
        mostrar_modelos();
        
        
        
    }
    
    
    void valida_modelo(){
    String ssql;
    
        try {
            Connection cn = cc.conexion();
            ssql = "";
            ssql = ssql + " select * from tmodelo";
            ssql = ssql + " where txt_descripcion_modelo = '" + jtxt_modelo.getText() +"'";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            if(rs.next()){
                JOptionPane.showMessageDialog(null, "Modelo existente!", "CyCO", JOptionPane.INFORMATION_MESSAGE);            
                return;
            }else{
                grabar_modelo();
            }   
            rs.close();
            st.close();
            cn.close();
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, "Error al validar serie existente en: "+e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
        }    
    }
    

    void grabar_modelo(){
        Integer iEstado;
        try {
            Connection cn = cc.conexion();
            varias_funciones vr = new varias_funciones();
            int ultimo = vr.recup_ult_numero("tmodelo", "id_modelo");
            
            if(chkActivo.isSelected() == true){
                iEstado = 1;
            }else{
                iEstado = 0;
            }
            
            
            if(iProceso == 1){
                CallableStatement cst = cn.prepareCall("{ call sp_grabar_modelo (?,?,?,?,?)}");
                cst.setInt(1, ultimo);
                cst.setInt(2, jcomboMarca.getItemAt(jcomboMarca.getSelectedIndex()).getId());                
                cst.setString(3, jtxt_modelo.getText());
                cst.setInt(4, iEstado);
                cst.setInt(5, iProceso);
                cst.execute();
                cst.close();
                JOptionPane.showMessageDialog(null, "Registro guardado con exito", "CyCo", JOptionPane.INFORMATION_MESSAGE);
                iProceso = 1;
                jtxt_modelo.setText("");
                chkActivo.setSelected(false);   
                mostrar_modelos();
            }else{
                CallableStatement cst = cn.prepareCall("{ call sp_grabar_modelo (?, ?,?,?,?)}");
                cst.setInt(1, id_modelo);
                cst.setInt(2, jcomboMarca.getItemAt(jcomboMarca.getSelectedIndex()).getId());   
                cst.setString(3, jtxt_modelo.getText());
                cst.setInt(4, iEstado);
                cst.setInt(5, iProceso);
                cst.execute();
                cst.close();
                JOptionPane.showMessageDialog(null, "Registro modificado con exito", "CyCo", JOptionPane.INFORMATION_MESSAGE);
                iProceso = 1;
                jtxt_modelo.setText("");
                chkActivo.setSelected(false);            
                mostrar_modelos();
            }               
            cn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en guardar registro en: "+e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
        }            
    }
    
    void mostrar_modelos(){
    String ssql;
    
        try {
            Connection cn = cc.conexion();
            DefaultTableModel sr = new DefaultTableModel();
            sr.addColumn("id_modelo");
            sr.addColumn("MODELO");
            sr.addColumn("MARCA");
            sr.addColumn("ESTADO");
            jtbMarcas.setModel(sr);
            
                                     
            //id
            jtbMarcas.getColumnModel().getColumn(0).setMaxWidth(0);
            jtbMarcas.getColumnModel().getColumn(0).setMinWidth(0);
            jtbMarcas.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
            jtbMarcas.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
        
            //MODELO
            jtbMarcas.getColumnModel().getColumn(1).setMaxWidth(229);
            jtbMarcas.getColumnModel().getColumn(1).setMinWidth(229);
            jtbMarcas.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(229);
            jtbMarcas.getTableHeader().getColumnModel().getColumn(1).setMinWidth(229);
            
             //MARCA
            jtbMarcas.getColumnModel().getColumn(2).setMaxWidth(229);
            jtbMarcas.getColumnModel().getColumn(2).setMinWidth(229);
            jtbMarcas.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(229);
            jtbMarcas.getTableHeader().getColumnModel().getColumn(2).setMinWidth(229);
            
            //estado
            jtbMarcas.getColumnModel().getColumn(3).setMaxWidth(225);
            jtbMarcas.getColumnModel().getColumn(3).setMinWidth(225);
            jtbMarcas.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(225);
            jtbMarcas.getTableHeader().getColumnModel().getColumn(3).setMinWidth(225);
                        
            
            ssql = "";
            ssql = ssql + " select ts.id_modelo ,ts.txt_descripcion_modelo, tmar.txt_descripcion_marca,";
            ssql = ssql + " te.txt_desc";
            ssql = ssql + " from tmodelo ts";
            ssql = ssql + " inner join testado te on te.id_estado = ts.sn_activo";
            ssql = ssql + " inner join tmarca tmar on tmar.id_marca = ts.id_marca";
            ssql = ssql + " where ts.id_modelo <> 0";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            String datos[] = new String[4];
            while(rs.next()){
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                sr.addRow(datos);
            }
            jtbMarcas.setModel(sr);    
            rs.close();
            st.close();
            cn.close();
            iProceso = 1;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar modelos en: "+e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
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
        jtbMarcas = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jSeparator8 = new javax.swing.JSeparator();
        jtxt_modelo = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jcomboMarca = new javax.swing.JComboBox<>();

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
        jPanel1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 460, 10));

        jLabel1.setFont(new java.awt.Font("Corbel", 0, 36)); // NOI18N
        jLabel1.setForeground(java.awt.Color.gray);
        jLabel1.setText("Modelo");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 40));

        jLabel3.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel3.setForeground(java.awt.Color.cyan);
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Marca:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 90, -1));
        jPanel1.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 170, 360, 10));

        chkActivo.setBackground(new java.awt.Color(41, 42, 44));
        chkActivo.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        chkActivo.setForeground(java.awt.Color.cyan);
        chkActivo.setText("Activo");
        jPanel1.add(chkActivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 90, 100, 30));
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
        jPanel1.add(jbtnGrabar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 460, -1));

        jtbMarcas.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jtbMarcas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jtbMarcas.setRowHeight(18);
        jtbMarcas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtbMarcasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtbMarcas);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 460, 140));
        jPanel1.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 460, 10));

        jtxt_modelo.setBackground(new java.awt.Color(41, 42, 44));
        jtxt_modelo.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jtxt_modelo.setForeground(new java.awt.Color(255, 255, 255));
        jtxt_modelo.setBorder(null);
        jPanel1.add(jtxt_modelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, 360, 20));

        jButton1.setBackground(java.awt.Color.black);
        jButton1.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(232, 251, 244));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/excel132.png"))); // NOI18N
        jButton1.setText("Exportar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 420, 150, 50));

        jLabel4.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel4.setForeground(java.awt.Color.cyan);
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Modelo:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 90, -1));

        jcomboMarca.setFont(new java.awt.Font("Ebrima", 1, 16)); // NOI18N
        jPanel1.add(jcomboMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, 260, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 490, 480));

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
        
            jfParametros mp = new jfParametros();            
            mp.setVisible(true);            
            Fade.JFrameFadeOut(1f, 0f, 0.1f, 50, this,Fade.DISPOSE);               
    }//GEN-LAST:event_lblSalirMouseClicked

    private void lblSalirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSalirMouseEntered

    private void lblSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseExited
        lblSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(41,42,44)));
    }//GEN-LAST:event_lblSalirMouseExited

    private void jtbMarcasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbMarcasMouseClicked
        if(evt.getClickCount()==2){
            
                   if (this.jtbMarcas.getRowCount() == 0 )
                        { 
                            JOptionPane.showMessageDialog(null,"No hay registros para leccionar","CyCo",JOptionPane.INFORMATION_MESSAGE); 
                            return;
                        }
                   
                   id_modelo = Integer.parseInt(String.valueOf(jtbMarcas.getValueAt(jtbMarcas.getSelectedRow(), 0)));
                   jtxt_modelo.setText(String.valueOf(jtbMarcas.getValueAt(jtbMarcas.getSelectedRow(), 1)));
                   iProceso =2;
                   if(String.valueOf(jtbMarcas.getValueAt(jtbMarcas.getSelectedRow(), 3)).equals("ACTIVO")){
                       chkActivo.setSelected(true);
                   }else{
                       chkActivo.setSelected(false);
                   }                                                                                               
        }
    }//GEN-LAST:event_jtbMarcasMouseClicked

    private void jbtnGrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGrabarActionPerformed
        if(jtxt_modelo.getText().equals("")){
            JOptionPane.showMessageDialog(null, "* Favor completar el campo modelo", "CyCo", JOptionPane.INFORMATION_MESSAGE);
        }else{
            if(iProceso == 1){
                valida_modelo();
            }else{
                grabar_modelo();
            }
            
            
        }
    }//GEN-LAST:event_jbtnGrabarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            Exportar_excel excel = new Exportar_excel();
            excel.exportarExcel(jtbMarcas);
        } catch (IOException ex) {
            Logger.getLogger(jfConsultaProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            java.util.logging.Logger.getLogger(jfModelo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfModelo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfModelo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfModelo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jfModelo().setVisible(true);
            }
        });
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chkActivo;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JButton jbtnGrabar;
    private javax.swing.JComboBox<idex_combo> jcomboMarca;
    private javax.swing.JTable jtbMarcas;
    private javax.swing.JTextField jtxt_modelo;
    private javax.swing.JLabel lblMinimizar;
    private javax.swing.JLabel lblSalir;
    // End of variables declaration//GEN-END:variables
}
