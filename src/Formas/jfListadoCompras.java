/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formas;

import clases_varias.conectar;
import clases_varias.idex_combo;
import clases_varias.user_logged;
import clases_varias.varias_funciones;
import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class jfListadoCompras extends javax.swing.JFrame {
    conectar cc= new conectar();      
    /**
     * Creates new form jfListadoCompras
     */
    public jfListadoCompras() {
        initComponents();
        jtbListadoC.setAutoResizeMode(jtbListadoC.AUTO_RESIZE_OFF);
        this.setLocationRelativeTo(null);
        cargar_tabla();
        idex_combo combo = new idex_combo();        
        combo.llenarCombo(jcboTipoDoc, "id_tipo_doc", "ttipo_documento", "txt_descripcion");   
        combo.llenarCombo(jcboEstado, "id_estado", "testado", "txt_desc");   
    }
    
    
    void cargar_tabla(){        
        try {
            String ssql;
            String sDesde;
            String sHasta;
            
            varias_funciones vr = new varias_funciones();                        
            DefaultTableModel lista = new DefaultTableModel();
            lista.addColumn("NO. COMPRA");
            lista.addColumn("FECHA");
            lista.addColumn("TIPO DOC");
            lista.addColumn("NO. DOC");
            lista.addColumn("PROOVEDOR");
            lista.addColumn("SUCURSAL");
            lista.addColumn("BODEGA");
            lista.addColumn("ESTADO");            
            lista.addColumn("USUARIO");  
            jtbListadoC.setModel(lista);
            
            
            //ID_compra
            jtbListadoC.getColumnModel().getColumn(0).setMaxWidth(95);
            jtbListadoC.getColumnModel().getColumn(0).setMinWidth(95);
            jtbListadoC.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(95);
            jtbListadoC.getTableHeader().getColumnModel().getColumn(0).setMinWidth(95);
            
            //fecha
            jtbListadoC.getColumnModel().getColumn(1).setMaxWidth(97);
            jtbListadoC.getColumnModel().getColumn(1).setMinWidth(97);
            jtbListadoC.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(97);
            jtbListadoC.getTableHeader().getColumnModel().getColumn(1).setMinWidth(97);
            
            //TIPO DOC
            jtbListadoC.getColumnModel().getColumn(2).setMaxWidth(100);
            jtbListadoC.getColumnModel().getColumn(2).setMinWidth(100);
            jtbListadoC.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(100);
            jtbListadoC.getTableHeader().getColumnModel().getColumn(2).setMinWidth(100);
            
            //NO DOC
            jtbListadoC.getColumnModel().getColumn(3).setMaxWidth(100);
            jtbListadoC.getColumnModel().getColumn(3).setMinWidth(100);
            jtbListadoC.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(100);
            jtbListadoC.getTableHeader().getColumnModel().getColumn(3).setMinWidth(100);
            
            //PROOVEDOR
            jtbListadoC.getColumnModel().getColumn(4).setMaxWidth(200);
            jtbListadoC.getColumnModel().getColumn(4).setMinWidth(200);
            jtbListadoC.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(200);
            jtbListadoC.getTableHeader().getColumnModel().getColumn(4).setMinWidth(200);
            
            //sucursal
            jtbListadoC.getColumnModel().getColumn(5).setMaxWidth(150);
            jtbListadoC.getColumnModel().getColumn(5).setMinWidth(150);
            jtbListadoC.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(150);
            jtbListadoC.getTableHeader().getColumnModel().getColumn(5).setMinWidth(150);
            
            //Ibodega
            jtbListadoC.getColumnModel().getColumn(6).setMaxWidth(150);
            jtbListadoC.getColumnModel().getColumn(6).setMinWidth(150);
            jtbListadoC.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(150);
            jtbListadoC.getTableHeader().getColumnModel().getColumn(6).setMinWidth(150);
            
            //estado
            jtbListadoC.getColumnModel().getColumn(7).setMaxWidth(95);
            jtbListadoC.getColumnModel().getColumn(7).setMinWidth(95);
            jtbListadoC.getTableHeader().getColumnModel().getColumn(7).setMaxWidth(95);
            jtbListadoC.getTableHeader().getColumnModel().getColumn(7).setMinWidth(95);
            
                
            //usaurio
            jtbListadoC.getColumnModel().getColumn(8).setMaxWidth(100);
            jtbListadoC.getColumnModel().getColumn(8).setMinWidth(100);
            jtbListadoC.getTableHeader().getColumnModel().getColumn(8).setMaxWidth(100);
            jtbListadoC.getTableHeader().getColumnModel().getColumn(8).setMinWidth(100);
            
            Connection cn= cc.conexion(); 
            ssql = "";
            ssql = ssql + " select tc.id_compra, tc.fecha_compra, ttd.txt_descripcion, tc.nro_compra, tprove.txt_descripcion, tsuc.txt_descripcion, tbod.txt_descripcion, te.txt_desc, tusu.cod_usuario";
            ssql = ssql + " from tcompra tc";
            ssql = ssql + " inner join ttipo_documento ttd on ttd.id_tipo_doc = tc.id_tipo_doc";
            ssql = ssql + " inner join tprovedor tprove on tprove.id_prove = tc.id_proovedor";
            ssql = ssql + " inner join tsucursal tsuc on tsuc.id_sucursal = tc.id_sucursal";
            ssql = ssql + " inner join tbodega tbod on tbod.id_bodega = tc.id_bodega";
            ssql = ssql + " inner join testado te on te.id_estado = tc.sn_estado";
            ssql = ssql + " inner join tusuario tusu on tusu.id_usuario = tc.id_usuario";
            ssql = ssql + " where tc.id_compra <> 0";
            if(jchkFechas.isSelected() == true){                                               
                sDesde = vr.formatoFecha(jtxtDesde.getText());
                sHasta = vr.formatoFecha(jtxtHasta.getText());                                
                ssql = ssql + " and tc.fecha_compra between '" + sDesde +  "' and '" + sHasta +"'";            
            }
            
            if(jchkNoCom.isSelected() == true){
                ssql = ssql + " and tc.id_compra = " + jtxtNoCom.getText();            
            }
            
            if(jchkTipoD.isSelected() == true){
                ssql = ssql + " and tc.id_tipo_doc = "+ jcboTipoDoc.getItemAt(jcboTipoDoc.getSelectedIndex()).getId();            
            }
            
            if(jchkDoc.isSelected() == true){
                ssql = ssql + " and tc.nro_compra = '" + jtxtNoDoc.getText() + "'";
            }
            
            if(jchkEstado.isSelected() == true){
                ssql = ssql + " and sn_estado = " + jcboEstado.getItemAt(jcboEstado.getSelectedIndex()).getId();
            }                                                
            ssql = ssql + " order by tc.fecha_compra desc";                                    
            String datos[] = new String[9];
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
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
                lista.addRow(datos);                                            
            }
            jtbListadoC.setModel(lista);
            rs.close();
            st.close();
            cn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error al mostrar listado en: "+ e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbListadoC = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jPanel9 = new javax.swing.JPanel();
        jtxtNoDoc = new javax.swing.JTextField();
        jchkDoc = new javax.swing.JCheckBox();
        jPanel10 = new javax.swing.JPanel();
        jchkFechas = new javax.swing.JCheckBox();
        jtxtHasta = new javax.swing.JFormattedTextField();
        jtxtDesde = new javax.swing.JFormattedTextField();
        jPanel11 = new javax.swing.JPanel();
        jchkEstado = new javax.swing.JCheckBox();
        jcboEstado = new javax.swing.JComboBox<>();
        jPanel12 = new javax.swing.JPanel();
        jchkTipoD = new javax.swing.JCheckBox();
        jcboTipoDoc = new javax.swing.JComboBox<>();
        jPanel13 = new javax.swing.JPanel();
        jtxtNoCom = new javax.swing.JTextField();
        jchkNoCom = new javax.swing.JCheckBox();
        jbtnBuscar = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JSeparator();
        jbtnCompra = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(41, 42, 44));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Corbel", 1, 36)); // NOI18N
        jLabel1.setForeground(java.awt.Color.gray);
        jLabel1.setText("Listado de Compras");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 330, 40));
        jPanel1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 560, 920, 10));

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
        jPanel1.add(lblSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 10, 50, 40));

        jtbListadoC.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jtbListadoC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jtbListadoC.setRowHeight(25);
        jtbListadoC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtbListadoCMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtbListadoC);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 920, 300));

        jPanel9.setBackground(new java.awt.Color(41, 42, 44));
        jPanel9.setBorder(new javax.swing.border.LineBorder(java.awt.Color.cyan, 1, true));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtxtNoDoc.setBackground(new java.awt.Color(41, 42, 44));
        jtxtNoDoc.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jtxtNoDoc.setForeground(new java.awt.Color(255, 255, 255));
        jtxtNoDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtNoDocFocusLost(evt);
            }
        });
        jtxtNoDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtNoDocActionPerformed(evt);
            }
        });
        jPanel9.add(jtxtNoDoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 170, -1));

        jchkDoc.setBackground(new java.awt.Color(41, 42, 44));
        jchkDoc.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jchkDoc.setForeground(java.awt.Color.cyan);
        jchkDoc.setText("No. Documento");
        jchkDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkDocActionPerformed(evt);
            }
        });
        jPanel9.add(jchkDoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 190, 30));

        jPanel1.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 70, 210, 80));

        jPanel10.setBackground(new java.awt.Color(41, 42, 44));
        jPanel10.setBorder(new javax.swing.border.LineBorder(java.awt.Color.cyan, 1, true));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jchkFechas.setBackground(new java.awt.Color(41, 42, 44));
        jchkFechas.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jchkFechas.setForeground(java.awt.Color.cyan);
        jchkFechas.setText("Fecha Desde    Fecha Hasta");
        jchkFechas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkFechasActionPerformed(evt);
            }
        });
        jPanel10.add(jchkFechas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 310, 30));

        jtxtHasta.setBackground(new java.awt.Color(41, 42, 44));
        jtxtHasta.setForeground(new java.awt.Color(255, 255, 255));
        jtxtHasta.setFormatterFactory(new javax.swing.JFormattedTextField.AbstractFormatterFactory() {
            public javax.swing.JFormattedTextField.AbstractFormatter
            getFormatter(javax.swing.JFormattedTextField tf){

                try {
                    return new javax.swing.text.MaskFormatter("##/##/####");
                }
                catch (java.text.ParseException pe){
                    pe.printStackTrace();
                }
                return null;
            }
        });
        jtxtHasta.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jPanel10.add(jtxtHasta, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 40, 130, 30));

        jtxtDesde.setBackground(new java.awt.Color(41, 42, 44));
        jtxtDesde.setForeground(new java.awt.Color(255, 255, 255));
        jtxtDesde.setFormatterFactory(new javax.swing.JFormattedTextField.AbstractFormatterFactory() {
            public javax.swing.JFormattedTextField.AbstractFormatter
            getFormatter(javax.swing.JFormattedTextField tf){

                try {
                    return new javax.swing.text.MaskFormatter("##/##/####");
                }
                catch (java.text.ParseException pe){
                    pe.printStackTrace();
                }
                return null;
            }
        });
        jtxtDesde.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jPanel10.add(jtxtDesde, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 130, 30));

        jPanel1.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 330, 80));

        jPanel11.setBackground(new java.awt.Color(41, 42, 44));
        jPanel11.setBorder(new javax.swing.border.LineBorder(java.awt.Color.cyan, 1, true));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jchkEstado.setBackground(new java.awt.Color(41, 42, 44));
        jchkEstado.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jchkEstado.setForeground(java.awt.Color.cyan);
        jchkEstado.setText("Estado");
        jchkEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkEstadoActionPerformed(evt);
            }
        });
        jPanel11.add(jchkEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 150, 30));

        jcboEstado.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jPanel11.add(jcboEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 300, -1));

        jPanel1.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 330, 80));

        jPanel12.setBackground(new java.awt.Color(41, 42, 44));
        jPanel12.setBorder(new javax.swing.border.LineBorder(java.awt.Color.cyan, 1, true));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jchkTipoD.setBackground(new java.awt.Color(41, 42, 44));
        jchkTipoD.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jchkTipoD.setForeground(java.awt.Color.cyan);
        jchkTipoD.setText("Tipo Doc.");
        jchkTipoD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkTipoDActionPerformed(evt);
            }
        });
        jPanel12.add(jchkTipoD, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 150, 30));

        jcboTipoDoc.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jPanel12.add(jcboTipoDoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 150, -1));

        jPanel1.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 70, 170, 80));

        jPanel13.setBackground(new java.awt.Color(41, 42, 44));
        jPanel13.setBorder(new javax.swing.border.LineBorder(java.awt.Color.cyan, 1, true));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtxtNoCom.setBackground(new java.awt.Color(41, 42, 44));
        jtxtNoCom.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jtxtNoCom.setForeground(new java.awt.Color(255, 255, 255));
        jtxtNoCom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtNoComFocusLost(evt);
            }
        });
        jtxtNoCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtNoComActionPerformed(evt);
            }
        });
        jPanel13.add(jtxtNoCom, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 150, -1));

        jchkNoCom.setBackground(new java.awt.Color(41, 42, 44));
        jchkNoCom.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jchkNoCom.setForeground(java.awt.Color.cyan);
        jchkNoCom.setText("No. Compra");
        jchkNoCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkNoComActionPerformed(evt);
            }
        });
        jPanel13.add(jchkNoCom, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 150, 30));

        jPanel1.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 70, 170, 80));

        jbtnBuscar.setBackground(new java.awt.Color(0, 0, 0));
        jbtnBuscar.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jbtnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        jbtnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/buscar132.png"))); // NOI18N
        jbtnBuscar.setText("Buscar");
        jbtnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnBuscarActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 160, 350, 80));
        jPanel1.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 920, 20));

        jbtnCompra.setBackground(new java.awt.Color(0, 0, 0));
        jbtnCompra.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jbtnCompra.setForeground(new java.awt.Color(255, 255, 255));
        jbtnCompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/editar232.png"))); // NOI18N
        jbtnCompra.setText("Nueva Compra");
        jbtnCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCompraActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 160, 210, 80));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 580));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblSalirMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseMoved
        lblSalir.setBorder(javax.swing.BorderFactory.createLineBorder(Color.RED));
    }//GEN-LAST:event_lblSalirMouseMoved

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        
        jfConfigura confi = new jfConfigura();        
        confi.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblSalirMouseClicked

    private void lblSalirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSalirMouseEntered

    private void lblSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseExited
        lblSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(41,42,44)));
    }//GEN-LAST:event_lblSalirMouseExited

    private void jtxtNoDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtNoDocFocusLost
        
    }//GEN-LAST:event_jtxtNoDocFocusLost

    private void jtxtNoDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtNoDocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtNoDocActionPerformed

    private void jchkFechasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkFechasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jchkFechasActionPerformed

    private void jchkEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jchkEstadoActionPerformed

    private void jchkDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkDocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jchkDocActionPerformed

    private void jchkTipoDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkTipoDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jchkTipoDActionPerformed

    private void jtxtNoComFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtNoComFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtNoComFocusLost

    private void jtxtNoComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtNoComActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtNoComActionPerformed

    private void jchkNoComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkNoComActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jchkNoComActionPerformed

    private void jbtnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnBuscarActionPerformed
        cargar_tabla();
    }//GEN-LAST:event_jbtnBuscarActionPerformed

    private void jtbListadoCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbListadoCMouseClicked
        try {                      
            if(evt.getClickCount()==2){                 
                if (this.jtbListadoC.getRowCount() == 0 ){ 
                    JOptionPane.showMessageDialog(null,"No hay registor para seleccionar","CyCo",JOptionPane.INFORMATION_MESSAGE); 
                    return;
                }            
                
                if(String.valueOf(jtbListadoC.getValueAt(jtbListadoC.getSelectedRow(),7)).equals("PENDIENTE")){
                    jfCompra com = new jfCompra();                                
                    com.jtxtIdCompra.setText(String.valueOf(jtbListadoC.getValueAt(jtbListadoC.getSelectedRow(),0)));                                      
                    com.recuperar_compra();
                    com.iProceso_compra = 2;                
                    com.setVisible(true);
                    this.dispose();                                        
                }else if(String.valueOf(jtbListadoC.getValueAt(jtbListadoC.getSelectedRow(),7)).equals("ACTIVO")){
                    jfCompra com = new jfCompra();                                
                    com.jtxtIdCompra.setText(String.valueOf(jtbListadoC.getValueAt(jtbListadoC.getSelectedRow(),0)));                                      
                    com.recuperar_compra();
                    com.iProceso_compra = 4; //CONSULTAR                     
                    com.setVisible(true);
                    this.dispose();        
                }                                
            }
            
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jtbListadoCMouseClicked

    private void jbtnCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCompraActionPerformed
        try {
            
            jfCompra com = new jfCompra();
            com.iProceso_compra = 1;            
            com.setVisible(true);
            this.dispose();                     
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jbtnCompraActionPerformed

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
            java.util.logging.Logger.getLogger(jfListadoCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfListadoCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfListadoCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfListadoCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jfListadoCompras().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JButton jbtnBuscar;
    private javax.swing.JButton jbtnCompra;
    private javax.swing.JComboBox<idex_combo> jcboEstado;
    private javax.swing.JComboBox<idex_combo> jcboTipoDoc;
    private javax.swing.JCheckBox jchkDoc;
    private javax.swing.JCheckBox jchkEstado;
    private javax.swing.JCheckBox jchkFechas;
    private javax.swing.JCheckBox jchkNoCom;
    private javax.swing.JCheckBox jchkTipoD;
    private javax.swing.JTable jtbListadoC;
    private javax.swing.JFormattedTextField jtxtDesde;
    private javax.swing.JFormattedTextField jtxtHasta;
    private javax.swing.JTextField jtxtNoCom;
    private javax.swing.JTextField jtxtNoDoc;
    private javax.swing.JLabel lblSalir;
    // End of variables declaration//GEN-END:variables
}
