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
public class jfBodega extends javax.swing.JFrame {
    conectar cc= new conectar();  
    int iBodega;
    int iPunto;
    int iEstado;
    int iProceso;    
    /**
     * Creates new form jfBodega
     */
    public jfBodega() {
        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("/Iconos/CyCo64.png")).getImage());
        this.setLocationRelativeTo(null);
        jtbBodega.setAutoResizeMode(jtbBodega.AUTO_RESIZE_OFF);
        cargar_tabla();        
        idex_combo combo = new idex_combo();        
        combo.llenarCombo(jcboSuc, "id_sucursal", "tsucursal", "txt_descripcion");   
        iProceso = 1;
    }                
    
    void cargar_tabla(){
        String ssql;
        try {
            
            DefaultTableModel bod = new DefaultTableModel();
            bod.addColumn("id_bodega");
            bod.addColumn("COD. BODEGA");
            bod.addColumn("BODEGA");
            bod.addColumn("SUCURSAL");
            bod.addColumn("PUNTO DE VENTA");
            bod.addColumn("ESTADO");
            bod.addColumn("id_suc");
            jtbBodega.setModel(bod);
                                                                                                                
            
            //ID_compra
            jtbBodega.getColumnModel().getColumn(0).setMaxWidth(0);
            jtbBodega.getColumnModel().getColumn(0).setMinWidth(0);
            jtbBodega.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
            jtbBodega.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
            
            //cod bodega
            jtbBodega.getColumnModel().getColumn(1).setMaxWidth(100);
            jtbBodega.getColumnModel().getColumn(1).setMinWidth(100);
            jtbBodega.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(100);
            jtbBodega.getTableHeader().getColumnModel().getColumn(1).setMinWidth(100);
            
            //bodega
            jtbBodega.getColumnModel().getColumn(2).setMaxWidth(165);
            jtbBodega.getColumnModel().getColumn(2).setMinWidth(165);
            jtbBodega.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(165);
            jtbBodega.getTableHeader().getColumnModel().getColumn(2).setMinWidth(165);
            
            //SUCURSAL
            jtbBodega.getColumnModel().getColumn(3).setMaxWidth(160);
            jtbBodega.getColumnModel().getColumn(3).setMinWidth(160);
            jtbBodega.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(160);
            jtbBodega.getTableHeader().getColumnModel().getColumn(3).setMinWidth(160);
            
            //PUNTO DE VENTA
            jtbBodega.getColumnModel().getColumn(4).setMaxWidth(120);
            jtbBodega.getColumnModel().getColumn(4).setMinWidth(120);
            jtbBodega.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(120);
            jtbBodega.getTableHeader().getColumnModel().getColumn(4).setMinWidth(120);
            
            //ESTADO
            jtbBodega.getColumnModel().getColumn(5).setMaxWidth(75);
            jtbBodega.getColumnModel().getColumn(5).setMinWidth(75);
            jtbBodega.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(75);
            jtbBodega.getTableHeader().getColumnModel().getColumn(5).setMinWidth(75);
            
            //id_suc
            jtbBodega.getColumnModel().getColumn(6).setMaxWidth(0);
            jtbBodega.getColumnModel().getColumn(6).setMinWidth(0);
            jtbBodega.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(0);
            jtbBodega.getTableHeader().getColumnModel().getColumn(6).setMinWidth(0);
                                                                                    
            Connection cn= cc.conexion(); 
            ssql = "";
            ssql = ssql + " select tb.id_bodega, tb.cod_bodega, tb.txt_descripcion, tsuc.txt_descripcion, tb.iBodega_venta, te.txt_desc,";
            ssql = ssql + " tb.id_suc";
            ssql = ssql + " from tbodega tb";
            ssql = ssql + " inner join tsucursal tsuc on tsuc.id_sucursal = tb.id_suc";
            ssql = ssql + " inner join testado te on te.id_estado = tb.sn_activo";
            ssql = ssql + " where tb.id_bodega <> 0";
            String datos[] = new String[7];
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            while(rs.next()){
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);                
                if(rs.getString(5).equals("1")){
                    datos[4] = "SI";
                }else{
                    datos[4] = "NO";
                }                                
                datos[5] = rs.getString(6);
                datos[6] = rs.getString(7);
                bod.addRow(datos);                
            }
                jtbBodega.setModel(bod);                                                                     
                rs.close();
                st.close();
                cn.close();                
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar Bodegas en: " + e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
        }            
    }
    
    
    void grabar_bodega(){
        String ssql;
        
        try {
            
            if(jchkPunto.isSelected() == true){
                iPunto = 1;
            }else{
                iPunto = 0;
            }
            
            if(jchkEstado.isSelected() == true){
                iEstado = 1;            
            }else{
                iEstado = 0;
            }

            JOptionPane.showMessageDialog(null, "id bodega: "+iBodega);
            JOptionPane.showMessageDialog(null, "proceso: "+iProceso);
            
            Connection cn= cc.conexion(); 
            CallableStatement cst = cn.prepareCall("{ call sp_grabar_bodega(?,?,?,?,?,?)}");
            cst.setInt(1, iBodega);
            cst.setInt(2, jcboSuc.getItemAt(jcboSuc.getSelectedIndex()).getId());
            cst.setString(3, jtxtNombre.getText());
            cst.setInt(4, iPunto);
            cst.setInt(5, iEstado);
            cst.setInt(6, iProceso);
            cst.execute();
            JOptionPane.showMessageDialog(null, "Bodega grabada con exito", "CyCo", JOptionPane.INFORMATION_MESSAGE);                                                            
            cst.close();
            cn.close();
            cargar_tabla();
            limpiar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al grabar bodega en: "+e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
        }        
    }
                
    void limpiar(){    
        iBodega = 0;
        iPunto = 0;
        iEstado = 0;
        iProceso = 1;        
        jtxtNombre.setText("");
        jcboSuc.setSelectedIndex(0);     
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
        jSeparator5 = new javax.swing.JSeparator();
        lblSalir = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jtxtNombre = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jchkEstado = new javax.swing.JCheckBox();
        jbtnGrabar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbBodega = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jcboSuc = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jchkPunto = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(41, 42, 44));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Corbel", 1, 36)); // NOI18N
        jLabel1.setForeground(java.awt.Color.gray);
        jLabel1.setText("Bodega");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 210, 40));
        jPanel1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 630, 20));

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
        jPanel1.add(lblSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 10, 50, 40));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 470, 20));

        jtxtNombre.setBackground(new java.awt.Color(41, 42, 44));
        jtxtNombre.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jtxtNombre.setForeground(new java.awt.Color(232, 251, 244));
        jtxtNombre.setBorder(null);
        jtxtNombre.setCaretColor(new java.awt.Color(232, 251, 244));
        jtxtNombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtNombreFocusGained(evt);
            }
        });
        jtxtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtNombreActionPerformed(evt);
            }
        });
        jPanel1.add(jtxtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, 470, 40));

        jLabel6.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel6.setForeground(java.awt.Color.cyan);
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Sucursal:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 130, 40));

        jchkEstado.setBackground(new java.awt.Color(41, 42, 44));
        jchkEstado.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jchkEstado.setForeground(java.awt.Color.cyan);
        jchkEstado.setText("ACTIVO");
        jchkEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkEstadoActionPerformed(evt);
            }
        });
        jPanel1.add(jchkEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 200, -1, -1));

        jbtnGrabar.setBackground(new java.awt.Color(0, 0, 0));
        jbtnGrabar.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jbtnGrabar.setForeground(new java.awt.Color(255, 255, 255));
        jbtnGrabar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/guardar32_1.png"))); // NOI18N
        jbtnGrabar.setText("Grabar");
        jbtnGrabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnGrabarActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnGrabar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 242, 630, -1));

        jtbBodega.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jtbBodega.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtbBodegaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jtbBodegaMouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(jtbBodega);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 630, 220));

        jcboSuc.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jPanel1.add(jcboSuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 140, 470, -1));

        jLabel7.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel7.setForeground(java.awt.Color.cyan);
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Nombre:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 130, 40));

        jchkPunto.setBackground(new java.awt.Color(41, 42, 44));
        jchkPunto.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jchkPunto.setForeground(java.awt.Color.cyan);
        jchkPunto.setText("ASIGNAR BODEGA PARA VENTAS");
        jchkPunto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkPuntoActionPerformed(evt);
            }
        });
        jPanel1.add(jchkPunto, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 200, -1, -1));

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/pc_asigna.png"))); // NOI18N
        jButton1.setText("Asignar Bodega a PC");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 530, 230, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 650, 590));

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

    private void jtxtNombreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtNombreFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtNombreFocusGained

    private void jtxtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtNombreActionPerformed

    private void jchkEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jchkEstadoActionPerformed

    private void jchkPuntoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkPuntoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jchkPuntoActionPerformed

    private void jbtnGrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGrabarActionPerformed
        grabar_bodega();
    }//GEN-LAST:event_jbtnGrabarActionPerformed

    private void jtbBodegaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbBodegaMouseClicked
        
        if(evt.getClickCount()==2){ 
                
                if (this.jtbBodega.getRowCount() == 0 ){ 
                    JOptionPane.showMessageDialog(null,"No hay registros para seleccionar","CyCo",JOptionPane.INFORMATION_MESSAGE); 
                    return;
                }
                                
                jtxtNombre.setText(String.valueOf(jtbBodega.getValueAt(jtbBodega.getSelectedRow(),2)));
                SetearCombo sc = new SetearCombo();
                int idSucur = sc.set_sucursal(Integer.parseInt(String.valueOf(jtbBodega.getValueAt(jtbBodega.getSelectedRow(),6))));                
                jcboSuc.setSelectedIndex(idSucur);
                                
                iBodega = Integer.parseInt(String.valueOf(jtbBodega.getValueAt(jtbBodega.getSelectedRow(),0)));
                if(String.valueOf(jtbBodega.getValueAt(jtbBodega.getSelectedRow(),4)).equals("SI")){
                    jchkPunto.setSelected(true);                    
                }else{
                    jchkPunto.setSelected(false);                    
                }                                                
                iProceso = 2;                                                                                                
                if(String.valueOf(jtbBodega.getValueAt(jtbBodega.getSelectedRow(),5)).equals("ACTIVO")){
                    jchkEstado.setSelected(true);
                }else{
                    jchkEstado.setSelected(false);
                }                                                
            }                   
    }//GEN-LAST:event_jtbBodegaMouseClicked

    private void jtbBodegaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbBodegaMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jtbBodegaMouseEntered

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jfAsignar_Equipo ae = new jfAsignar_Equipo();
        ae.setVisible(true);
        this.dispose();
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
            java.util.logging.Logger.getLogger(jfBodega.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfBodega.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfBodega.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfBodega.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jfBodega().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JButton jbtnGrabar;
    private javax.swing.JComboBox<idex_combo> jcboSuc;
    private javax.swing.JCheckBox jchkEstado;
    private javax.swing.JCheckBox jchkPunto;
    private javax.swing.JTable jtbBodega;
    private javax.swing.JTextField jtxtNombre;
    private javax.swing.JLabel lblSalir;
    // End of variables declaration//GEN-END:variables
}
