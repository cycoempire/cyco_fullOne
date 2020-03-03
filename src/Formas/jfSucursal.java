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
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author wwwki
 */
public class jfSucursal extends javax.swing.JFrame {
    conectar cc= new conectar();  
    int iTipo_proceso = 1;
    int iId_suc = 0;    
    /**
     * Creates new form jfSucursal
     */
    public jfSucursal() {
        initComponents();
        this.setLocationRelativeTo(null);
        jtbSucursal.setAutoResizeMode(jtbSucursal.AUTO_RESIZE_OFF); 
        cargar_tabla();
    }
    
    
    void cargar_tabla(){
    String ssql;
        try {
            DefaultTableModel su = new DefaultTableModel();
            su.addColumn("id_suc");
            su.addColumn("COD. SUCURSAL");
            su.addColumn("NOMBRE");
            su.addColumn("DIRECCION");
            su.addColumn("TELEFONO");
            su.addColumn("ESTADO");
            su.addColumn("CREADA");
            jtbSucursal.setModel(su);
            
            //ID_compra
            jtbSucursal.getColumnModel().getColumn(0).setMaxWidth(0);
            jtbSucursal.getColumnModel().getColumn(0).setMinWidth(0);
            jtbSucursal.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
            jtbSucursal.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
            
            //cod suc
            jtbSucursal.getColumnModel().getColumn(1).setMaxWidth(100);
            jtbSucursal.getColumnModel().getColumn(1).setMinWidth(100);
            jtbSucursal.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(100);
            jtbSucursal.getTableHeader().getColumnModel().getColumn(1).setMinWidth(100);
            
            //nombre
            jtbSucursal.getColumnModel().getColumn(2).setMaxWidth(200);
            jtbSucursal.getColumnModel().getColumn(2).setMinWidth(200);
            jtbSucursal.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(200);
            jtbSucursal.getTableHeader().getColumnModel().getColumn(2).setMinWidth(200);
            
            //direccion
            jtbSucursal.getColumnModel().getColumn(3).setMaxWidth(250);
            jtbSucursal.getColumnModel().getColumn(3).setMinWidth(250);
            jtbSucursal.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(250);
            jtbSucursal.getTableHeader().getColumnModel().getColumn(3).setMinWidth(250);
            
            //telefono
            jtbSucursal.getColumnModel().getColumn(4).setMaxWidth(100);
            jtbSucursal.getColumnModel().getColumn(4).setMinWidth(100);
            jtbSucursal.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(100);
            jtbSucursal.getTableHeader().getColumnModel().getColumn(4).setMinWidth(100);
            
                                                                                    
            //estado
            jtbSucursal.getColumnModel().getColumn(5).setMaxWidth(100);
            jtbSucursal.getColumnModel().getColumn(5).setMinWidth(100);
            jtbSucursal.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(100);
            jtbSucursal.getTableHeader().getColumnModel().getColumn(5).setMinWidth(100);
            
            //estado
            jtbSucursal.getColumnModel().getColumn(6).setMaxWidth(100);
            jtbSucursal.getColumnModel().getColumn(6).setMinWidth(100);
            jtbSucursal.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(100);
            jtbSucursal.getTableHeader().getColumnModel().getColumn(6).setMinWidth(100);
            
            Connection cn= cc.conexion(); 
            ssql = "";
            ssql = ssql + " select id_sucursal, cod_suc, txt_descripcion, txt_direccion, txt_telefono, te.txt_desc, create_at";
            ssql = ssql + " from tsucursal";
            ssql = ssql + " inner join testado te on te.id_estado = tsucursal.sn_activo";
            ssql = ssql + " where id_sucursal <> 0";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            String datos[] = new String[7];
            while(rs.next()){
                datos [0] = rs.getString(1);
                datos [1] = rs.getString(2);
                datos [2] = rs.getString(3);
                datos [3] = rs.getString(4);
                datos [4] = rs.getString(5);
                datos [5] = rs.getString(6);                
                datos [6] = rs.getString(7);      
                su.addRow(datos);
            }            
            jtbSucursal.setModel(su);                                    
            rs.close();
            st.close();
            cn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar datos en: " +e, "CyCo",JOptionPane.INFORMATION_MESSAGE);
        }    
    }
    
    void grabar_sucursal(){
        try {
            Connection cn= cc.conexion();              
            int iEstado = 0;
                if(tchkEstado.isSelected() == true){
                    iEstado = 1;
                }else{
                    iEstado = 0;
                }
                CallableStatement cst = cn.prepareCall("{ call sp_grabar_sucursal(?,?,?,?,?,?)}");
                cst.setInt(1, iId_suc);
                cst.setString(2, jtxtSuc.getText());
                cst.setString(3, jtxtDirec.getText());
                cst.setString(4, jtxtTelefono.getText());
                cst.setInt(5, iEstado);
                cst.setInt(6, iTipo_proceso);
                cst.execute();
                JOptionPane.showMessageDialog(null, "Sucursal grabada con exito", "CyCo", JOptionPane.INFORMATION_MESSAGE);                                                                                    
                cst.close();
                cn.close();
                limpiar();
                cargar_tabla();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al grabar sucursal en: " +e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
        }                
    }
    
    
    void limpiar(){
        jtxtSuc.setText("");
        jtxtDirec.setText("");
        jtxtTelefono.setText("");
        tchkEstado.setSelected(false);
        iTipo_proceso = 1;
        iId_suc = 0;                
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
        jtxtSuc = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jtxtDirec = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jtxtTelefono = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        tchkEstado = new javax.swing.JCheckBox();
        jbtnGrabar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbSucursal = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(41, 42, 44));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Corbel", 1, 36)); // NOI18N
        jLabel1.setForeground(java.awt.Color.gray);
        jLabel1.setText("Sucursal");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 210, 40));
        jPanel1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 640, 20));

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
        jPanel1.add(lblSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 10, 40, 40));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 470, 20));

        jtxtSuc.setBackground(new java.awt.Color(41, 42, 44));
        jtxtSuc.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jtxtSuc.setForeground(new java.awt.Color(232, 251, 244));
        jtxtSuc.setBorder(null);
        jtxtSuc.setCaretColor(new java.awt.Color(232, 251, 244));
        jtxtSuc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtSucFocusGained(evt);
            }
        });
        jtxtSuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtSucActionPerformed(evt);
            }
        });
        jPanel1.add(jtxtSuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, 470, 40));

        jLabel6.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel6.setForeground(java.awt.Color.cyan);
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Sucursal:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 130, 40));
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 170, 470, 20));

        jtxtDirec.setBackground(new java.awt.Color(41, 42, 44));
        jtxtDirec.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jtxtDirec.setForeground(new java.awt.Color(232, 251, 244));
        jtxtDirec.setBorder(null);
        jtxtDirec.setCaretColor(new java.awt.Color(232, 251, 244));
        jtxtDirec.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtDirecFocusGained(evt);
            }
        });
        jtxtDirec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtDirecActionPerformed(evt);
            }
        });
        jPanel1.add(jtxtDirec, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 140, 470, 40));

        jLabel7.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel7.setForeground(java.awt.Color.cyan);
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Direccion:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 130, 40));
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 230, 190, 10));

        jtxtTelefono.setBackground(new java.awt.Color(41, 42, 44));
        jtxtTelefono.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jtxtTelefono.setForeground(new java.awt.Color(232, 251, 244));
        jtxtTelefono.setBorder(null);
        jtxtTelefono.setCaretColor(new java.awt.Color(232, 251, 244));
        jtxtTelefono.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtTelefonoFocusGained(evt);
            }
        });
        jtxtTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtTelefonoActionPerformed(evt);
            }
        });
        jPanel1.add(jtxtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, 190, 40));

        jLabel8.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel8.setForeground(java.awt.Color.cyan);
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Telefono:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 130, 40));

        tchkEstado.setBackground(new java.awt.Color(41, 42, 44));
        tchkEstado.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        tchkEstado.setForeground(new java.awt.Color(255, 255, 255));
        tchkEstado.setText("ACTIVO");
        tchkEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tchkEstadoActionPerformed(evt);
            }
        });
        jPanel1.add(tchkEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 200, -1, -1));

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

        jtbSucursal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jtbSucursal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtbSucursalMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtbSucursal);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 630, 220));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 660, 540));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblSalirMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseMoved
        lblSalir.setBorder(javax.swing.BorderFactory.createLineBorder(Color.RED));
    }//GEN-LAST:event_lblSalirMouseMoved

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        jfParametros pr = new jfParametros();        
        pr.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblSalirMouseClicked

    private void lblSalirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSalirMouseEntered

    private void lblSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseExited
        lblSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(41,42,44)));
    }//GEN-LAST:event_lblSalirMouseExited

    private void jtxtSucFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtSucFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtSucFocusGained

    private void jtxtSucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtSucActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtSucActionPerformed

    private void jtxtDirecFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtDirecFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtDirecFocusGained

    private void jtxtDirecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtDirecActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtDirecActionPerformed

    private void jtxtTelefonoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtTelefonoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtTelefonoFocusGained

    private void jtxtTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtTelefonoActionPerformed

    private void tchkEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tchkEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tchkEstadoActionPerformed

    private void jbtnGrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGrabarActionPerformed
        grabar_sucursal();
    }//GEN-LAST:event_jbtnGrabarActionPerformed

    private void jtbSucursalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbSucursalMouseClicked
        
        if(evt.getClickCount()==2){ 
                
                if (this.jtbSucursal.getRowCount() == 0 ){ 
                    JOptionPane.showMessageDialog(null,"No hay registros para seleccionar","CyCo",JOptionPane.INFORMATION_MESSAGE); 
                    return;
                }
                
                jtxtSuc.setText(String.valueOf(jtbSucursal.getValueAt(jtbSucursal.getSelectedRow(),2)));
                jtxtDirec.setText(String.valueOf(jtbSucursal.getValueAt(jtbSucursal.getSelectedRow(),3)));
                jtxtTelefono.setText(String.valueOf(jtbSucursal.getValueAt(jtbSucursal.getSelectedRow(),4)));
                if(String.valueOf(jtbSucursal.getValueAt(jtbSucursal.getSelectedRow(),5)).equals("ACTIVO")){
                    tchkEstado.setSelected(true);
                }else{
                    tchkEstado.setSelected(false);
                }                
                iTipo_proceso = 2;
                iId_suc = Integer.parseInt(String.valueOf(jtbSucursal.getValueAt(jtbSucursal.getSelectedRow(),0)));                     
            }                                                    
    }//GEN-LAST:event_jtbSucursalMouseClicked

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
            java.util.logging.Logger.getLogger(jfSucursal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfSucursal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfSucursal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfSucursal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jfSucursal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JButton jbtnGrabar;
    private javax.swing.JTable jtbSucursal;
    private javax.swing.JTextField jtxtDirec;
    private javax.swing.JTextField jtxtSuc;
    private javax.swing.JTextField jtxtTelefono;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JCheckBox tchkEstado;
    // End of variables declaration//GEN-END:variables
}
