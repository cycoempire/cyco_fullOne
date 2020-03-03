/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formas;

import clases_varias.SetearCombo;
import clases_varias.conectar;
import java.awt.Color;
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
public class jfProveedor extends javax.swing.JFrame {
    conectar cc= new conectar();
    int idProve = 0;
    int iEstado = 0;
    int iProceso = 1;
    /**
     * Creates new form jfProveedor
     */
    public jfProveedor() {
        initComponents();
        this.setLocationRelativeTo(null);
        jtbProve.setAutoResizeMode(jtbProve.AUTO_RESIZE_OFF);
        cargar_tabla();
        jtxtDirec.setEnabled(true);
        jtxtDirec.setText("");
        jtxtDirec.setText("");
        jtxtDirec.setText("");
        jchkEstado.setSelected(false);
    }
    
    
    
    void cargar_tabla(){
        try {
            
            String ssql;
            
            DefaultTableModel tprov = new DefaultTableModel();
            tprov.addColumn("idProve");
            tprov.addColumn("CODIGO");
            tprov.addColumn("N.I.T.");
            tprov.addColumn("PROVEEDOR");
            tprov.addColumn("TELEFONO");
            tprov.addColumn("DIRECCION");
            tprov.addColumn("ESTADO");
            jtbProve.setModel(tprov);
            
            //ID_prove
            jtbProve.getColumnModel().getColumn(0).setMaxWidth(0);
            jtbProve.getColumnModel().getColumn(0).setMinWidth(0);
            jtbProve.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
            jtbProve.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
            
            //codigo
            jtbProve.getColumnModel().getColumn(1).setMaxWidth(70);
            jtbProve.getColumnModel().getColumn(1).setMinWidth(70);
            jtbProve.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(70);
            jtbProve.getTableHeader().getColumnModel().getColumn(1).setMinWidth(70);
            
            //nit
            jtbProve.getColumnModel().getColumn(2).setMaxWidth(100);
            jtbProve.getColumnModel().getColumn(2).setMinWidth(100);
            jtbProve.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(100);
            jtbProve.getTableHeader().getColumnModel().getColumn(2).setMinWidth(100);
            
            //prove
            jtbProve.getColumnModel().getColumn(3).setMaxWidth(300);
            jtbProve.getColumnModel().getColumn(3).setMinWidth(300);
            jtbProve.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(300);
            jtbProve.getTableHeader().getColumnModel().getColumn(3).setMinWidth(300);
            
            //tele
            jtbProve.getColumnModel().getColumn(4).setMaxWidth(100);
            jtbProve.getColumnModel().getColumn(4).setMinWidth(100);
            jtbProve.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(100);
            jtbProve.getTableHeader().getColumnModel().getColumn(4).setMinWidth(100);
            
            //direc
            jtbProve.getColumnModel().getColumn(5).setMaxWidth(200);
            jtbProve.getColumnModel().getColumn(5).setMinWidth(200);
            jtbProve.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(200);
            jtbProve.getTableHeader().getColumnModel().getColumn(5).setMinWidth(200);
            
            //estado
            jtbProve.getColumnModel().getColumn(6).setMaxWidth(80);
            jtbProve.getColumnModel().getColumn(6).setMinWidth(80);
            jtbProve.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(80);
            jtbProve.getTableHeader().getColumnModel().getColumn(6).setMinWidth(80);
            
            Connection cn= cc.conexion();                                     
            ssql = "";
            ssql = ssql + " select tprov.id_prove, tprov.cod_prove, tprov.txt_descripcion, tprov.txt_telefono, tprov.txt_nit, tprov.txt_direccion, tes.txt_desc";
            ssql = ssql + " from tprovedor tprov";
            ssql = ssql + " inner join testado tes on tes.id_estado = tprov.sn_activo";                                                                                                
            ssql = ssql + " where tprov.id_prove <> 0";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            String datos[] = new String[7];
            while(rs.next()){
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(4);
                datos[3] = rs.getString(3);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                datos[6] = rs.getString(7);
                tprov.addRow(datos);
            }
            jtbProve.setModel(tprov);
            rs.close();
            st.close();
            cn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar datos en: "+e);
        }
    
    
    }
    
    
    void validaciones(){
        try {
            Connection cn= cc.conexion(); 
            String ssql;
            String sMensaje = "";
            
            //validamos nit y nombre
            ssql = "";
            ssql = ssql + " select txt_nit, txt_descripcion from tprovedor";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            while(rs.next()){
                if(rs.getString(1).equals(jtxtDirec.getText().trim())){
                    sMensaje = " * Existe un provedor con el NIT ingresado. \n";                
                }
                
                if(rs.getString(2).equals(jtxtDirec.getText().trim())){
                    sMensaje = sMensaje + " * Existe un provedor con el Nombre ingresado. \n";                
                }                            
            }
            rs.close();
            st.close();
            cn.close();
            
            if(jtxtDirec.getText().equals("")){
               sMensaje = sMensaje + " * Favor complete el campo NIT. \n";                
            }
            
            if(jtxtDirec.getText().equals("")){
                sMensaje = sMensaje + " * Favor complete el campo Proveedor. \n";                
            }
            
            if(jtxtDirec.getText().equals("")){
                sMensaje = sMensaje + " * Favor complete el campo Telefono. \n";                
            }
            
            if(jtxtDirec.getText().equals(ABORT)){
                sMensaje = sMensaje + " * Favor complete el campo Direccion. \n";                
            }
            
            
            if(sMensaje.equals("")){
                grabar_prove();            
            }else{
                JOptionPane.showMessageDialog(null, sMensaje, "CyCo", JOptionPane.INFORMATION_MESSAGE);
            }
                                                                                    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al validar en: " +e);
        }
    
    
    }
    
    
    void grabar_prove(){
        try {
            Connection cn= cc.conexion(); 
            
            if(jchkEstado.isSelected()){
                    iEstado = 1;
            }else{
                    iEstado = 0;
            }
            
            CallableStatement cst = cn.prepareCall("{ call sp_grabar_prove(?,?,?,?,?,?,?)}");
            cst.setInt(1, idProve);
            cst.setString(2, jtxtProve.getText());
            cst.setString(3, jtxtNit.getText());
            cst.setString(4, jtxtTele.getText());
            cst.setString(5, jtxtDirec.getText());
            cst.setInt(6, iEstado);
            cst.setInt(7, iProceso);
            cst.execute();
            cst.close();
            cn.close();
            JOptionPane.showMessageDialog(null, "Registro grabado con exito!", "CyCO", JOptionPane.INFORMATION_MESSAGE);
            cargar_tabla();
            limpiar();            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al grabar registro en: "+e, "CyCO", JOptionPane.INFORMATION_MESSAGE);
        }        
    }
    
    
    void limpiar(){
        idProve = 0;
        jtxtDirec.setText("");
        jtxtDirec.setText("");
        jtxtDirec.setText("");
        jtxtDirec.setText("");
        iEstado = 0;
        iProceso = 1;
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
        jSeparator7 = new javax.swing.JSeparator();
        lblSalir = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbProve = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        jSeparator10 = new javax.swing.JSeparator();
        jSeparator11 = new javax.swing.JSeparator();
        jtxtCodProv = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        jSeparator13 = new javax.swing.JSeparator();
        jchkEstado = new javax.swing.JCheckBox();
        jbtnGrabar = new javax.swing.JButton();
        jbtnNuevo = new javax.swing.JButton();
        jtxtDirec = new javax.swing.JTextField();
        jtxtNit = new javax.swing.JTextField();
        jtxtProve = new javax.swing.JTextField();
        jtxtTele = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(41, 42, 44));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Corbel", 1, 36)); // NOI18N
        jLabel1.setForeground(java.awt.Color.gray);
        jLabel1.setText("Proveedores");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 500, 40));
        jPanel1.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 860, 20));

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
        jPanel1.add(lblSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 10, 50, 40));

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel4.setForeground(java.awt.Color.cyan);
        jLabel4.setText("Cod. Proveedor:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel5.setForeground(java.awt.Color.cyan);
        jLabel5.setText("N.I.T.:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, -1, -1));

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel6.setForeground(java.awt.Color.cyan);
        jLabel6.setText("Proveedor:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 190, -1, -1));

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel7.setForeground(java.awt.Color.cyan);
        jLabel7.setText("Teléfono:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 70, -1, -1));

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel8.setForeground(java.awt.Color.cyan);
        jLabel8.setText("Dirección:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 130, -1, -1));

        jtbProve.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jtbProve.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtbProveMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtbProve);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 860, 220));
        jPanel1.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 860, 10));
        jPanel1.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, 270, 20));
        jPanel1.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 150, 270, 20));
        jPanel1.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 210, 270, 20));
        jPanel1.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 90, 340, 20));

        jtxtCodProv.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jtxtCodProv.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(jtxtCodProv, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, 250, 20));
        jPanel1.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, 250, 20));
        jPanel1.add(jSeparator13, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 150, 340, 20));

        jchkEstado.setBackground(new java.awt.Color(41, 42, 44));
        jchkEstado.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jchkEstado.setForeground(new java.awt.Color(255, 255, 255));
        jchkEstado.setText("Activo");
        jPanel1.add(jchkEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 190, -1, -1));

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
        jPanel1.add(jbtnGrabar, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 240, 650, 60));

        jbtnNuevo.setBackground(new java.awt.Color(0, 0, 0));
        jbtnNuevo.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jbtnNuevo.setForeground(new java.awt.Color(255, 255, 255));
        jbtnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/editar432.png"))); // NOI18N
        jbtnNuevo.setText("Nuevo");
        jbtnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNuevoActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 200, 60));

        jtxtDirec.setBackground(new java.awt.Color(41, 42, 44));
        jtxtDirec.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jtxtDirec.setForeground(new java.awt.Color(255, 255, 255));
        jtxtDirec.setBorder(null);
        jPanel1.add(jtxtDirec, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 130, 330, 20));

        jtxtNit.setBackground(new java.awt.Color(41, 42, 44));
        jtxtNit.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jtxtNit.setForeground(new java.awt.Color(255, 255, 255));
        jtxtNit.setBorder(null);
        jPanel1.add(jtxtNit, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, 270, 20));

        jtxtProve.setBackground(new java.awt.Color(41, 42, 44));
        jtxtProve.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jtxtProve.setForeground(new java.awt.Color(255, 255, 255));
        jtxtProve.setBorder(null);
        jPanel1.add(jtxtProve, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, 270, 20));

        jtxtTele.setBackground(new java.awt.Color(41, 42, 44));
        jtxtTele.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jtxtTele.setForeground(new java.awt.Color(255, 255, 255));
        jtxtTele.setBorder(null);
        jPanel1.add(jtxtTele, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 70, 330, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 881, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblSalirMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseMoved
        lblSalir.setBorder(javax.swing.BorderFactory.createLineBorder(Color.RED));
    }//GEN-LAST:event_lblSalirMouseMoved

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        jfConfigura para = new jfConfigura();
        para.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblSalirMouseClicked

    private void lblSalirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSalirMouseEntered

    private void lblSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseExited
        lblSalir.setBorder(null);
    }//GEN-LAST:event_lblSalirMouseExited

    private void jbtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNuevoActionPerformed
        limpiar();
    }//GEN-LAST:event_jbtnNuevoActionPerformed

    private void jbtnGrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGrabarActionPerformed
        validaciones();
    }//GEN-LAST:event_jbtnGrabarActionPerformed

    private void jtbProveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbProveMouseClicked
            if(evt.getClickCount()==2){ 
                
                if (this.jtbProve.getRowCount() == 0 ){ 
                    JOptionPane.showMessageDialog(null,"No hay registros para seleccionar","CyCo",JOptionPane.INFORMATION_MESSAGE); 
                    return;
                }
                       
                iProceso = 2;              
                idProve = Integer.parseInt(String.valueOf(jtbProve.getValueAt(jtbProve.getSelectedRow(),0)));
                jtxtCodProv.setText(String.valueOf(jtbProve.getValueAt(jtbProve.getSelectedRow(),1)));
                jtxtNit.setText(String.valueOf(jtbProve.getValueAt(jtbProve.getSelectedRow(),2)));
                jtxtProve.setText(String.valueOf(jtbProve.getValueAt(jtbProve.getSelectedRow(),3)));
                jtxtTele.setText(String.valueOf(jtbProve.getValueAt(jtbProve.getSelectedRow(),4)));
                jtxtDirec.setText(String.valueOf(jtbProve.getValueAt(jtbProve.getSelectedRow(),5)));                                                                                                                                                                                                                        
                if(String.valueOf(jtbProve.getValueAt(jtbProve.getSelectedRow(),6)).equals("ACTIVO")){
                    jchkEstado.setSelected(true);
                }else{
                    jchkEstado.setSelected(false);
                }                                                
            }                                                 
    }//GEN-LAST:event_jtbProveMouseClicked

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
            java.util.logging.Logger.getLogger(jfProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jfProveedor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JButton jbtnGrabar;
    private javax.swing.JButton jbtnNuevo;
    private javax.swing.JCheckBox jchkEstado;
    private javax.swing.JTable jtbProve;
    private javax.swing.JLabel jtxtCodProv;
    private javax.swing.JTextField jtxtDirec;
    private javax.swing.JTextField jtxtNit;
    private javax.swing.JTextField jtxtProve;
    private javax.swing.JTextField jtxtTele;
    private javax.swing.JLabel lblSalir;
    // End of variables declaration//GEN-END:variables
}
