/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formas;

import Animacion.Fade;
import clases_varias.Exportar_excel;
import clases_varias.conectar;
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
public class jfBitacoraPrecios extends javax.swing.JFrame {
    public Integer iProceso;
    public Integer id_producto;
    public Integer id_precio;
    conectar cc = new conectar();    
    public int idUsu;
    
    
    /**
     * Creates new form jfSeries
     */
    public jfBitacoraPrecios() {
        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("/Iconos/CyCo64.png")).getImage());
        this.setLocationRelativeTo(null);   
         //La tabla no se autoresizable
        jtbPrecios.setAutoResizeMode(jtbPrecios.AUTO_RESIZE_OFF);    
        recuperar_datos();
        mostrar_precios();
    }
            
    
    
    void recuperar_datos(){
    String ssql;
    
        try {
            Connection cn = cc.conexion();
            ssql = "";
            ssql = ssql + " select tpro.txt_descripcion, tp.dCosto, tp.dPventa, tp.txt_notas";
            ssql = ssql + " from tprecio tp";
            ssql = ssql + " inner join tproducto tpro on tpro.id_producto = tp.id_producto";
            ssql = ssql + " where tp.id_producto = " + id_producto;
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            if(rs.next()){
                jtxtDesc.setText(rs.getString(1));
                jtxtCosto.setText(rs.getString(2));
                jtxtVenta.setText(rs.getString(3));
                jtxtNotas.setText(rs.getString(4));
            }
            rs.close();
            st.close();
            cn.close();                                                            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar datos en: "+e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
       }                    
    }
    
    
    
    
    
    
    void mostrar_precios(){
    String ssql;
    
        try {
            Connection cn = cc.conexion();
            DefaultTableModel prec = new DefaultTableModel();
            prec.addColumn("id_precio");
            prec.addColumn("P. COSTO");
            prec.addColumn("P. VENTA");
            prec.addColumn("FECHA INGRESO");
            prec.addColumn("NOTAS");
            jtbPrecios.setModel(prec);
            
            //id_precio
            jtbPrecios.getColumnModel().getColumn(0).setMaxWidth(0);
            jtbPrecios.getColumnModel().getColumn(0).setMinWidth(0);
            jtbPrecios.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
            jtbPrecios.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
            
            //costo
            jtbPrecios.getColumnModel().getColumn(1).setMaxWidth(100);
            jtbPrecios.getColumnModel().getColumn(1).setMinWidth(100);
            jtbPrecios.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(100);
            jtbPrecios.getTableHeader().getColumnModel().getColumn(1).setMinWidth(100);
            
            //venta
            jtbPrecios.getColumnModel().getColumn(2).setMaxWidth(100);
            jtbPrecios.getColumnModel().getColumn(2).setMinWidth(100);
            jtbPrecios.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(100);
            jtbPrecios.getTableHeader().getColumnModel().getColumn(2).setMinWidth(100);
            
            //fecha
            jtbPrecios.getColumnModel().getColumn(3).setMaxWidth(150);
            jtbPrecios.getColumnModel().getColumn(3).setMinWidth(150);
            jtbPrecios.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(150);
            jtbPrecios.getTableHeader().getColumnModel().getColumn(3).setMinWidth(150);
            
            //notas
            jtbPrecios.getColumnModel().getColumn(4).setMaxWidth(350);
            jtbPrecios.getColumnModel().getColumn(4).setMinWidth(350);
            jtbPrecios.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(350);
            jtbPrecios.getTableHeader().getColumnModel().getColumn(4).setMinWidth(350);
            
            ssql = "";
            ssql = ssql + " select id_precio, dCosto, dPventa, fecha_ingreso, txt_notas";
            ssql = ssql + " from tprecio";
            ssql = ssql + " where id_producto = "+  id_producto;
            ssql = ssql + " order by fecha_ingreso ASC";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            String datos[] = new String[5];
            while(rs.next()){
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                prec.addRow(datos);                
            }
            jtbPrecios.setModel(prec);
            rs.close();
            st.close();
            cn.close();                                                
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar precios en: "+e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
        }                                                    
    }
            
    void grabar_precio(){
    
        try {      
            
            Connection cn = cc.conexion();
            varias_funciones vr = new varias_funciones();
            int iUlt = vr.recup_ult_numero("tprecio", "id_precio");            
            CallableStatement cst = cn.prepareCall("{call sp_grabar_precio(?,?,?,?,?)}");
            cst.setInt(1, iUlt);
            cst.setInt(2, id_producto);
            cst.setDouble(3, Double.parseDouble(jtxtCosto.getText()));
            cst.setDouble(4, Double.parseDouble(jtxtVenta.getText()));
            cst.setString(5, jtxtNotas.getText());
            cst.execute();
            cst.close();
            JOptionPane.showMessageDialog(null, "Registro guardado con exito", "CyCo", JOptionPane.INFORMATION_MESSAGE);
            cn.close();  
            mostrar_precios();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al grabar precio nuevo en: " +e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
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
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jbtnGrabar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbPrecios = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jSeparator8 = new javax.swing.JSeparator();
        jtxtDesc = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        jtxtCosto = new javax.swing.JTextField();
        jSeparator10 = new javax.swing.JSeparator();
        jtxtVenta = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();
        jtxtNotas = new javax.swing.JTextField();

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
        jPanel1.add(lblMinimizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 10, 50, 40));

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
        jPanel1.add(lblSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 10, 50, 40));
        jPanel1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, 580, 10));

        jLabel1.setFont(new java.awt.Font("Corbel", 0, 36)); // NOI18N
        jLabel1.setForeground(java.awt.Color.gray);
        jLabel1.setText("Bitacora Precios");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 290, 40));
        jPanel1.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, 480, 10));
        jPanel1.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 580, 10));

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
        jPanel1.add(jbtnGrabar, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 200, 420, -1));

        jtbPrecios.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jtbPrecios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jtbPrecios.setRowHeight(18);
        jtbPrecios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtbPreciosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtbPrecios);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 580, 180));
        jPanel1.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 580, 10));

        jtxtDesc.setBackground(new java.awt.Color(41, 42, 44));
        jtxtDesc.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jtxtDesc.setForeground(new java.awt.Color(255, 255, 255));
        jtxtDesc.setBorder(null);
        jPanel1.add(jtxtDesc, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, 480, 20));

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
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 150, 50));

        jLabel4.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel4.setForeground(java.awt.Color.cyan);
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Nombre:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 90, -1));

        jLabel5.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel5.setForeground(java.awt.Color.cyan);
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Costo:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 90, -1));
        jPanel1.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 140, 180, 10));

        jtxtCosto.setBackground(new java.awt.Color(41, 42, 44));
        jtxtCosto.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jtxtCosto.setForeground(new java.awt.Color(255, 255, 255));
        jtxtCosto.setBorder(null);
        jPanel1.add(jtxtCosto, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 180, 20));
        jPanel1.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 140, 210, 10));

        jtxtVenta.setBackground(new java.awt.Color(41, 42, 44));
        jtxtVenta.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jtxtVenta.setForeground(new java.awt.Color(255, 255, 255));
        jtxtVenta.setBorder(null);
        jPanel1.add(jtxtVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 120, 210, 20));

        jLabel6.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel6.setForeground(java.awt.Color.cyan);
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText(" Venta:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 120, 80, -1));

        jLabel7.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel7.setForeground(java.awt.Color.cyan);
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Notas:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 90, -1));
        jPanel1.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 180, 480, 10));

        jtxtNotas.setBackground(new java.awt.Color(41, 42, 44));
        jtxtNotas.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jtxtNotas.setForeground(new java.awt.Color(255, 255, 255));
        jtxtNotas.setBorder(null);
        jPanel1.add(jtxtNotas, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 160, 480, 20));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 600, -1));

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
//        int dialog = JOptionPane.YES_NO_OPTION;
//        int result = JOptionPane.showConfirmDialog(null, "¿Desea salir?","Salir",dialog);
//        if(result == 0){
            //Fade.JFrameFadeIn(0f, 1f, 0.2f, 50, this);
            //Fade.JFrameFadeOut(1f, 0f, 0.1f, 50, this,Fade.DISPOSE);            
            jfConsultaProducto mp = new jfConsultaProducto();                       
            this.dispose();
            mp.setVisible(true);
            Fade.JFrameFadeIn(0f, 1f, 0.2f, 50, mp);

//        }
    }//GEN-LAST:event_lblSalirMouseClicked

    private void lblSalirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSalirMouseEntered

    private void lblSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseExited
        lblSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(41,42,44)));
    }//GEN-LAST:event_lblSalirMouseExited

    private void jtbPreciosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbPreciosMouseClicked
        
    }//GEN-LAST:event_jtbPreciosMouseClicked

    private void jbtnGrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGrabarActionPerformed
        grabar_precio();
    }//GEN-LAST:event_jbtnGrabarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            Exportar_excel excel = new Exportar_excel();
            excel.exportarExcel(jtbPrecios);
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
            java.util.logging.Logger.getLogger(jfBitacoraPrecios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfBitacoraPrecios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfBitacoraPrecios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfBitacoraPrecios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                new jfBitacoraPrecios().setVisible(true);
            }
        });
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JButton jbtnGrabar;
    private javax.swing.JTable jtbPrecios;
    private javax.swing.JTextField jtxtCosto;
    private javax.swing.JTextField jtxtDesc;
    private javax.swing.JTextField jtxtNotas;
    private javax.swing.JTextField jtxtVenta;
    private javax.swing.JLabel lblMinimizar;
    private javax.swing.JLabel lblSalir;
    // End of variables declaration//GEN-END:variables
}
