/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formas;

import Animacion.Fade;
import clases_varias.conectar;
import clases_varias.idex_combo;
import clases_varias.varias_funciones;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author wwwki
 */
public class jfAsignar_Equipo extends javax.swing.JFrame {
    conectar cc= new conectar();    
    int idAsig;
    /**
     * Creates new form jfAsignar_Equipo
     */
    public jfAsignar_Equipo() {
        initComponents();
        this.setLocationRelativeTo(null);
        recupIdPc();       
        cargarTabla();
        idex_combo combo = new idex_combo();
        combo.llenarCombo(jcboSuc, "id_sucursal", "tsucursal", "txt_descripcion");    
    }
    
    void cargarTabla(){
        try {
            String ssql;
            DefaultTableModel pc = new DefaultTableModel();
            pc.addColumn("id_asigna");
            pc.addColumn("SUCURSAL");
            pc.addColumn("BODEGA");
            pc.addColumn("No. SERIE PC");
            pc.addColumn("id_bodega");
            jtbPC.setModel(pc);
            
            //ID_asigna
            jtbPC.getColumnModel().getColumn(0).setMaxWidth(0);
            jtbPC.getColumnModel().getColumn(0).setMinWidth(0);
            jtbPC.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
            jtbPC.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
            
            //sucursal
            jtbPC.getColumnModel().getColumn(1).setMaxWidth(150);
            jtbPC.getColumnModel().getColumn(1).setMinWidth(150);
            jtbPC.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(150);
            jtbPC.getTableHeader().getColumnModel().getColumn(1).setMinWidth(150);
            
            //bodega
            jtbPC.getColumnModel().getColumn(2).setMaxWidth(150);
            jtbPC.getColumnModel().getColumn(2).setMinWidth(150);
            jtbPC.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(150);
            jtbPC.getTableHeader().getColumnModel().getColumn(2).setMinWidth(150);
            
            //serie
            jtbPC.getColumnModel().getColumn(3).setMaxWidth(150);
            jtbPC.getColumnModel().getColumn(3).setMinWidth(150);
            jtbPC.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(150);
            jtbPC.getTableHeader().getColumnModel().getColumn(3).setMinWidth(150);
                                   
            //ID_bodega
            jtbPC.getColumnModel().getColumn(4).setMaxWidth(0);
            jtbPC.getColumnModel().getColumn(4).setMinWidth(0);
            jtbPC.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(0);
            jtbPC.getTableHeader().getColumnModel().getColumn(4).setMinWidth(0);
            
            Connection cn= cc.conexion(); 
            ssql = "";
            ssql = ssql + " select pc.id_asigna, tsu.txt_descripcion, tb.txt_descripcion, pc.serie_pc, pc.id_bodega";
            ssql = ssql + " from tAsignaPC pc";
            ssql = ssql + " inner join tbodega tb on tb.id_bodega = pc.id_bodega";
            ssql = ssql + " inner join tsucursal tsu on tsu.id_sucursal = tb.id_suc";
            ssql = ssql + " where tb.iBodega_venta = 1";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            String datos[] = new String[5];
            while(rs.next()){
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                pc.addRow(datos);
            }
            jtbPC.setModel(pc);
            rs.close();
            st.close();
            cn.close();                                                                                                      
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar tabla en: "+ e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
        }            
    }
    
    
            
    void recupIdPc(){              
        try
        {
            String result = "";
            String serial;
            Process p = Runtime.getRuntime().exec("wmic bios get serialnumber");
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null)
            {                              
                result += line;                
            }
            if (result.equalsIgnoreCase(" ")) {
                System.out.println("Result is empty");
            } else
            {                
                serial = result.replace("SerialNumber","");
                jlblPC.setText(serial.trim());                
            }
            input.close();
        } catch (Exception ex){                
        }        
    }
    
    void grabarPC(int iProceso){
        try {
            Connection cn= cc.conexion();                                                             
            CallableStatement cst = cn.prepareCall("{ call sp_grabar_PC(?,?,?,?)}");
            cst.setInt(1, idAsig);
            cst.setInt(2, jcboBodega.getItemAt(jcboBodega.getSelectedIndex()).getId());
            cst.setString(3, jlblPC.getText());
            cst.setInt(4, iProceso);
            cst.execute();
            cst.close();
            cn.close();
            JOptionPane.showMessageDialog(null, "Registro grabado con exito!", "CyCo", JOptionPane.INFORMATION_MESSAGE);                        
            cargarTabla();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al grabar en: " +e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
        }        
    }
    
    void validacion(int iProc){
        try {
            Connection cn= cc.conexion(); 
            String sMensaje = "";            
            String ssql;
            if(jcboSuc.getItemAt(jcboSuc.getSelectedIndex()).getId() == 0){
                sMensaje = " *Favor seleccionar una sucursal \n";                            
            }
            
            if(jcboBodega.getItemAt(jcboBodega.getSelectedIndex()).getId() == 0) {
                sMensaje = sMensaje + " *Favor seleccionar una bodega \n";                            
            }
            
            ssql = "";
            ssql = ssql + " select * from tAsignaPC ";
            ssql = ssql + " where id_bodega =  " + jcboBodega.getItemAt(jcboBodega.getSelectedIndex()).getId();
            ssql = ssql + " and serie_pc = '" + jlblPC.getText() + "'";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            if(rs.next()){
                sMensaje = sMensaje + " *Esta PC ya cuenta con una bodega de venta asignada \n";                            
            }
            rs.close();
            st.close();
            cn.close();                       
            
            if(sMensaje.equals("")){
               grabarPC(iProc);
            }else{
                JOptionPane.showMessageDialog(null, sMensaje, "CyCO", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al validar datos en: " + e, "CyCO", JOptionPane.INFORMATION_MESSAGE);
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
        jSeparator5 = new javax.swing.JSeparator();
        lblSalir = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbPC = new javax.swing.JTable();
        jSeparator6 = new javax.swing.JSeparator();
        jlblPC = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jcboSuc = new javax.swing.JComboBox<>();
        jcboBodega = new javax.swing.JComboBox<>();
        jbtnGrabar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(41, 42, 44));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Corbel", 1, 36)); // NOI18N
        jLabel1.setForeground(java.awt.Color.gray);
        jLabel1.setText("Asignar Bodega de Venta a PC");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 500, 40));
        jPanel1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 640, 10));

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
        jPanel1.add(lblSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, 50, 40));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Bodega:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 100, 30));

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("ID de mi PC:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 120, 30));

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Sucursal:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 100, 30));

        jtbPC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jtbPC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtbPCMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtbPC);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 630, 210));
        jPanel1.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, 310, 20));

        jlblPC.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jlblPC.setForeground(java.awt.Color.cyan);
        jlblPC.setText("jLabel5");
        jPanel1.add(jlblPC, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 70, 310, -1));
        jPanel1.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 640, 20));

        jcboSuc.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jcboSuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboSucActionPerformed(evt);
            }
        });
        jPanel1.add(jcboSuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, 310, -1));

        jcboBodega.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jPanel1.add(jcboBodega, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 170, 310, -1));

        jbtnGrabar.setBackground(new java.awt.Color(0, 0, 0));
        jbtnGrabar.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jbtnGrabar.setForeground(new java.awt.Color(255, 255, 255));
        jbtnGrabar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/guardar32_1.png"))); // NOI18N
        jbtnGrabar.setText(" Grabar");
        jbtnGrabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnGrabarActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnGrabar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 80, 150, 110));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
        );

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
        lblSalir.setBorder(null);
    }//GEN-LAST:event_lblSalirMouseExited

    private void jcboSucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcboSucActionPerformed
        idex_combo combo = new idex_combo();        
        String sWhere = "";                
        Integer idSuc = 0;
        if(jcboSuc.getItemAt(jcboSuc.getSelectedIndex()).getId() == null){
            idSuc = 0;
            sWhere = idSuc.toString();
        }else{
            idSuc = jcboSuc.getItemAt(jcboSuc.getSelectedIndex()).getId();                 
            sWhere = idSuc.toString() + "' and iBodega_venta = 1";
        }
        
        jcboBodega.removeAllItems();
        combo.llenarCombo2(jcboBodega, "id_bodega", "tBodega", "txt_descripcion"," and id_suc = '" + sWhere );
    }//GEN-LAST:event_jcboSucActionPerformed

    private void jbtnGrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGrabarActionPerformed
        varias_funciones vr = new varias_funciones();
        int iUltimo = vr.recup_ult_numero("tAsignaPC", "id_asigna");
        idAsig = iUltimo;
        validacion(1);
    }//GEN-LAST:event_jbtnGrabarActionPerformed

    private void jtbPCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbPCMouseClicked
        if(evt.getClickCount()==2){ 
                
                if (this.jtbPC.getRowCount() == 0 ){ 
                    JOptionPane.showMessageDialog(null,"No hay registros para seleccionar","CyCo",JOptionPane.INFORMATION_MESSAGE); 
                    return;
                }
                
                int dialog = JOptionPane.YES_NO_OPTION;
                int result = JOptionPane.showConfirmDialog(null, "¿Desea borrar este registro?","CyCo",dialog);
                if(result == 0){
                    idAsig = Integer.parseInt(String.valueOf(jtbPC.getValueAt(jtbPC.getSelectedRow(), 0)));
                    validacion(2);
                }                                                                        
        }
    }//GEN-LAST:event_jtbPCMouseClicked

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
            java.util.logging.Logger.getLogger(jfAsignar_Equipo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfAsignar_Equipo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfAsignar_Equipo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfAsignar_Equipo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jfAsignar_Equipo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JButton jbtnGrabar;
    private javax.swing.JComboBox<idex_combo> jcboBodega;
    private javax.swing.JComboBox<idex_combo> jcboSuc;
    private javax.swing.JLabel jlblPC;
    private javax.swing.JTable jtbPC;
    private javax.swing.JLabel lblSalir;
    // End of variables declaration//GEN-END:variables
}
