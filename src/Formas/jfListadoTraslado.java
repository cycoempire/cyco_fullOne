/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formas;

import clases_varias.conectar;
import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author wwwki
 */
public class jfListadoTraslado extends javax.swing.JFrame {
    conectar cc= new conectar();
    /**
     * Creates new form jfListadoTraslado
     */
    public jfListadoTraslado() {
        initComponents();
        this.setLocationRelativeTo(null);
        jtbTralado.setAutoResizeMode(jtbTralado.AUTO_RESIZE_OFF); 
        cargarTabla();
    }

    
    
    
    void cargarTabla(){
        try {
            String ssql;
            Connection cn= cc.conexion(); 
            
            DefaultTableModel tras = new DefaultTableModel();
            tras.addColumn("id_tras");
            tras.addColumn("NO. TRASLADO");
            tras.addColumn("FECHA");
            tras.addColumn("SUCURSAL SALIDA");
            tras.addColumn("BODEGA SALIDA");
            tras.addColumn("SUCURSAL INGRESO");
            tras.addColumn("BODEGA INGRESO");
            tras.addColumn("OBSERVACIONES");
            tras.addColumn("USUARIO");
            jtbTralado.setModel(tras);
            
            //ID_tras
            jtbTralado.getColumnModel().getColumn(0).setMaxWidth(0);
            jtbTralado.getColumnModel().getColumn(0).setMinWidth(0);
            jtbTralado.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
            jtbTralado.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
            
             //no tras
            jtbTralado.getColumnModel().getColumn(1).setMaxWidth(100);
            jtbTralado.getColumnModel().getColumn(1).setMinWidth(100);
            jtbTralado.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(100);
            jtbTralado.getTableHeader().getColumnModel().getColumn(1).setMinWidth(100);
            
             //fecha
            jtbTralado.getColumnModel().getColumn(2).setMaxWidth(100);
            jtbTralado.getColumnModel().getColumn(2).setMinWidth(100);
            jtbTralado.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(100);
            jtbTralado.getTableHeader().getColumnModel().getColumn(2).setMinWidth(100);
            
             //suc sale
            jtbTralado.getColumnModel().getColumn(3).setMaxWidth(150);
            jtbTralado.getColumnModel().getColumn(3).setMinWidth(150);
            jtbTralado.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(150);
            jtbTralado.getTableHeader().getColumnModel().getColumn(3).setMinWidth(150);
            
            //bodega sale
            jtbTralado.getColumnModel().getColumn(4).setMaxWidth(150);
            jtbTralado.getColumnModel().getColumn(4).setMinWidth(150);
            jtbTralado.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(150);
            jtbTralado.getTableHeader().getColumnModel().getColumn(4).setMinWidth(150);
            
            //suc entra
            jtbTralado.getColumnModel().getColumn(5).setMaxWidth(150);
            jtbTralado.getColumnModel().getColumn(5).setMinWidth(150);
            jtbTralado.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(150);
            jtbTralado.getTableHeader().getColumnModel().getColumn(5).setMinWidth(150);
            
            //bodega entra
            jtbTralado.getColumnModel().getColumn(6).setMaxWidth(150);
            jtbTralado.getColumnModel().getColumn(6).setMinWidth(150);
            jtbTralado.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(150);
            jtbTralado.getTableHeader().getColumnModel().getColumn(6).setMinWidth(150);
            
             //obse
            jtbTralado.getColumnModel().getColumn(7).setMaxWidth(300);
            jtbTralado.getColumnModel().getColumn(7).setMinWidth(300);
            jtbTralado.getTableHeader().getColumnModel().getColumn(7).setMaxWidth(300);
            jtbTralado.getTableHeader().getColumnModel().getColumn(7).setMinWidth(300);
            
            //usuario
            jtbTralado.getColumnModel().getColumn(8).setMaxWidth(80);
            jtbTralado.getColumnModel().getColumn(8).setMinWidth(80);
            jtbTralado.getTableHeader().getColumnModel().getColumn(8).setMaxWidth(80);
            jtbTralado.getTableHeader().getColumnModel().getColumn(8).setMinWidth(80);
                        
            
            ssql = "";
            ssql = ssql + " select tras.id_traslado, tras.no_traslado, tras.fecha_traslado,";
            ssql = ssql + " tsuc_sale.txt_descripcion, tbod_sale.txt_descripcion,tsuc_entra.txt_descripcion, tbod_entra.txt_descripcion,";
            ssql = ssql + " tras.txt_observaciones, tusu.cod_usuario";
            ssql = ssql + " from ttraslado tras";
            ssql = ssql + " inner join tbodega tbod_sale on tbod_sale.id_bodega = tras.id_bodega_sale";
            ssql = ssql + " inner join tsucursal tsuc_sale on tsuc_sale.id_sucursal = tbod_sale.id_suc";
            ssql = ssql + " inner join tbodega tbod_entra on tbod_entra.id_bodega = tras.id_bodega_entra";
            ssql = ssql + " inner join tsucursal tsuc_entra on tsuc_entra.id_sucursal = tbod_entra.id_suc";
            ssql = ssql + " inner join tusuario tusu on tusu.id_usuario = tras.id_usu";                        
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            String datos[] = new String[9];
            while(rs.next()){
              datos[0] = rs.getString(1);
              datos[1] = rs.getString(2);
              datos[2] = rs.getString(3);
              datos[3] = rs.getString(4);
              datos[4] = rs.getString(5);
              datos[5] = rs.getString(6);
              datos[6] = rs.getString(7);
              datos[7] = rs.getString(8);
              datos[8] = rs.getString(9);
              tras.addRow(datos);                            
            }
             jtbTralado.setModel(tras);                                               
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar listado en: "+e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
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
        jSeparator6 = new javax.swing.JSeparator();
        lblSalir = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbTralado = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jSeparator7 = new javax.swing.JSeparator();
        jbtnImp = new javax.swing.JButton();
        jbtnNuevo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(41, 42, 44));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Corbel", 0, 36)); // NOI18N
        jLabel1.setForeground(java.awt.Color.gray);
        jLabel1.setText("Traslado entre Bodegas");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 380, 40));
        jPanel1.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 840, 10));

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
        jPanel1.add(lblSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 10, 50, 40));

        jtbTralado.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jtbTralado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jtbTralado.setRowHeight(25);
        jtbTralado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtbTraladoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtbTralado);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 820, 360));
        jPanel1.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 840, 20));

        jbtnImp.setBackground(new java.awt.Color(0, 0, 0));
        jbtnImp.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jbtnImp.setForeground(new java.awt.Color(255, 255, 255));
        jbtnImp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/imprimir32.png"))); // NOI18N
        jbtnImp.setText("Imprimir");
        jPanel1.add(jbtnImp, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, 150, 60));

        jbtnNuevo.setBackground(new java.awt.Color(0, 0, 0));
        jbtnNuevo.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jbtnNuevo.setForeground(new java.awt.Color(255, 255, 255));
        jbtnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/editar432.png"))); // NOI18N
        jbtnNuevo.setText("Nuevo Traslado");
        jbtnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNuevoActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 450, 210, 60));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 860, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblSalirMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseMoved
        lblSalir.setBorder(javax.swing.BorderFactory.createLineBorder(Color.RED));
    }//GEN-LAST:event_lblSalirMouseMoved

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        jfConfigura conf = new jfConfigura();
        conf.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblSalirMouseClicked

    private void lblSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseExited
        lblSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(41,42,44)));
    }//GEN-LAST:event_lblSalirMouseExited

    private void jbtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNuevoActionPerformed
        jfTraslado tras = new jfTraslado();
        tras.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jbtnNuevoActionPerformed

    private void jtbTraladoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbTraladoMouseClicked
         if(evt.getClickCount()==2){            
                   if (this.jtbTralado.getRowCount() == 0 )
                        { 
                            JOptionPane.showMessageDialog(null,"No hay registros para leccionar","CyCo",JOptionPane.INFORMATION_MESSAGE); 
                            return;
                        }
                   
                   jfTraslado tra = new jfTraslado();
                   tra.recuperar_datos(Integer.parseInt(String.valueOf(jtbTralado.getValueAt(jtbTralado.getSelectedRow(), 0))));                                      
                   tra.setVisible(true);
                   this.dispose();
        }
    }//GEN-LAST:event_jtbTraladoMouseClicked

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
            java.util.logging.Logger.getLogger(jfListadoTraslado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfListadoTraslado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfListadoTraslado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfListadoTraslado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jfListadoTraslado().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JButton jbtnImp;
    private javax.swing.JButton jbtnNuevo;
    private javax.swing.JTable jtbTralado;
    private javax.swing.JLabel lblSalir;
    // End of variables declaration//GEN-END:variables
}
