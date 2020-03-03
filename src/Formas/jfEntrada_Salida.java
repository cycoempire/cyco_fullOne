/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formas;

import clases_varias.conectar;
import clases_varias.idex_combo;
import clases_varias.user_logged;
import java.awt.Color;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author wwwki
 */
public class jfEntrada_Salida extends javax.swing.JFrame {
    conectar cc = new conectar();    
    Integer idProd;
    Integer idPrecio;
    /**
     * Creates new form jfEntrada_Salida
     */
    public jfEntrada_Salida() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon(getClass().getResource("/Iconos/CyCo64.png")).getImage());
        idex_combo combo = new idex_combo();        
        combo.llenarCombo(jcboSucu, "id_sucursal", "tsucursal", "txt_descripcion");     
        
        cargarTabla();
    }

    
    
    void cargarTabla(){
    
        try {
            
            DefaultTableModel es = new DefaultTableModel();
            es.addColumn("id_prod");
            es.addColumn("CODIGO");
            es.addColumn("PRODUCTO");
            es.addColumn("CANTIDAD");
            es.addColumn("id_precio");
            jtbEnSal.setModel(es);
            
            //ID_compra
            jtbEnSal.getColumnModel().getColumn(0).setMaxWidth(0);
            jtbEnSal.getColumnModel().getColumn(0).setMinWidth(0);
            jtbEnSal.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
            jtbEnSal.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
            
             //codigo
            jtbEnSal.getColumnModel().getColumn(1).setMaxWidth(150);
            jtbEnSal.getColumnModel().getColumn(1).setMinWidth(150);
            jtbEnSal.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(150);
            jtbEnSal.getTableHeader().getColumnModel().getColumn(1).setMinWidth(150);
            
             //producto
            jtbEnSal.getColumnModel().getColumn(2).setMaxWidth(500);
            jtbEnSal.getColumnModel().getColumn(2).setMinWidth(500);
            jtbEnSal.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(500);
            jtbEnSal.getTableHeader().getColumnModel().getColumn(2).setMinWidth(500);
            
             //cantidad
            jtbEnSal.getColumnModel().getColumn(3).setMaxWidth(150);
            jtbEnSal.getColumnModel().getColumn(3).setMinWidth(150);
            jtbEnSal.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(150);
            jtbEnSal.getTableHeader().getColumnModel().getColumn(3).setMinWidth(150);
            
            //id_precio
            jtbEnSal.getColumnModel().getColumn(4).setMaxWidth(0);
            jtbEnSal.getColumnModel().getColumn(4).setMinWidth(0);
            jtbEnSal.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(0);
            jtbEnSal.getTableHeader().getColumnModel().getColumn(4).setMinWidth(0);
            
            
            
        } catch (Exception e) {
        }
    
    
    }
    
    
     void grabar_inventario(){    
     try {            
                                             
             String obs = "";
             int idMov;
             
             if(jrbEntrada.isSelected()){
                 obs = "ENTRADA MANUAL";  
                 idMov = 3;                 
             }else {
                 obs = "SALIDA MANUAL";
                 idMov = 4;                 
             } 
             
                                                    
             Iterator<user_logged> itrLogg = Menu_principal.user_memory.iterator();
             user_logged ul = itrLogg.next();      
             Connection cn= cc.conexion();             
             for(int i = 0; i < jtbEnSal.getRowCount(); i++){
                 
                 Double cantidad = Double.parseDouble(String.valueOf(jtbEnSal.getValueAt(i,3)));                 
                 if(jrbSalida.isSelected()){  
                    cantidad = (cantidad * -1);                                         
                 } 
                 
                 CallableStatement cst = cn.prepareCall("{ call sp_grabar_inventario(?,?,?,?,?,?,?,?,?)}");
                 cst.setInt(1, Integer.parseInt(String.valueOf(jtbEnSal.getValueAt(i,1))));             //id prod
                 cst.setInt(2, idMov);                                                                  //id tipo mov
                 cst.setDouble(3, cantidad);                                                            //cantidad
                 cst.setString(4, obs);                                                                 //observacione                 
                 cst.setInt(5, 4);                                                                      //id tipo doc
                 cst.setInt(6, 4);                                                                      //id documento
                 cst.setInt(7, jcboSucu.getItemAt(jcboSucu.getSelectedIndex()).getId());                //id sucu
                 cst.setInt(8, jcboBodega.getItemAt(jcboBodega.getSelectedIndex()).getId());            //id bodega
                 cst.setInt(9, ul.getidUsy());                                                          //id usu
                 cst.execute();       
                 cst.close();
             }
             JOptionPane.showMessageDialog(null, "¡Proceso realizado con exito!", "CyCo", JOptionPane.INFORMATION_MESSAGE);            
//             Cargar_tabla(); //limpiamos la tabla
             cn.close();
                                                                 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "¡Error al grabar detalle de la compra en: "+e, "CyCo", JOptionPane.INFORMATION_MESSAGE);            
        }                    
    }
    
    
     void agregarProd(){
        try {                                          
            DefaultTableModel prd = (DefaultTableModel) jtbEnSal.getModel();                
            String datos[] = new String[5];            
            datos[0] = idProd.toString();
            datos[1] = jtxtCodigo.getText();
            datos[2] = jtxtNombre.getText();                                                
            datos[3] = jtxtCanti.getText();                                                
            datos[4] = idPrecio.toString();                                                
            prd.addRow(datos);
            jtbEnSal.setModel(prd);                                                                       
      
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en: " + e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
     void recupProd_cod(int iBus){
        Integer id_tabla=0;
        String ssql;
        try {
            Connection cn= cc.conexion(); 
            
            switch (iBus){
            
                case 1:                                        
                    ssql = "";         
                    ssql = ssql + " select tprod.id_producto, tprod.cod_prod, tprod.txt_descripcion,";                                        
                    ssql = ssql + " tp.id_precio";                                                            
                    ssql = ssql + " from tproducto tprod";
                    ssql = ssql + " inner join tprecio tp on tp.id_producto = tprod.id_producto";                                    		                                                                                        
                    ssql = ssql + " where tprod.id_producto <> 0";
                    ssql = ssql + " and tp.id_precio = (select max(tprecio.id_precio) from tprecio where tprecio.id_producto = tp.id_producto)";
                    ssql = ssql + " and tprod.txt_codigo = '" + jtxtCodigo.getText() + "'";
                    Statement st = cn.createStatement();
                    ResultSet rs = st.executeQuery(ssql);
                    if(rs.next()){
                        idProd = rs.getInt(1);                        
                        jtxtNombre.setText(rs.getString(3));                                                                                                                                               
                        idPrecio = rs.getInt(4);
                    }                                        
                    rs.close();
                    st.close();                    
                    break;
                                                                                                                                            
                case 2:
                    ssql = "";         
                    ssql = ssql + " select tprod.id_producto, tprod.txt_codigo, tprod.txt_descripcion,";                                        
                    ssql = ssql + " tp.id_precio";                                                            
                    ssql = ssql + " from tproducto tprod";
                    ssql = ssql + " inner join tprecio tp on tp.id_producto = tprod.id_producto";                                    		                                                                                        
                    ssql = ssql + " where tprod.id_producto <> 0";
                    ssql = ssql + " and tp.id_precio = (select max(tprecio.id_precio) from tprecio where tprecio.id_producto = tp.id_producto)";                    
                    ssql = ssql + " and tprod.txt_descripcion = '" + jtxtNombre.getText() + "'";                                                                        
                    Statement stt = cn.createStatement();
                    ResultSet rss = stt.executeQuery(ssql);
                    if(rss.next()){
                        idProd = rss.getInt(1);                        
                        jtxtCodigo.setText(rss.getString(2));                                                                                                                                               
                        idPrecio = rss.getInt(4);
                    }                                        
                    rss.close();
                    stt.close();                                                                                    
                    break;
            }                   
            cn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar datos en: " + e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
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
        lblSalir = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbEnSal = new javax.swing.JTable();
        jSeparator4 = new javax.swing.JSeparator();
        jPanel4 = new javax.swing.JPanel();
        jtxtCanti = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jrbEntrada = new javax.swing.JRadioButton();
        jrbSalida = new javax.swing.JRadioButton();
        jPanel6 = new javax.swing.JPanel();
        jtxtNombre = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jbtnAgregar = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jtxtCodigo = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jbtnAgregar1 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jcboBodega = new javax.swing.JComboBox<>();
        jcboSucu = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(41, 42, 44));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Corbel", 0, 26)); // NOI18N
        jLabel1.setForeground(java.awt.Color.gray);
        jLabel1.setText("Entrada y salida manual de productos");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

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
        jPanel1.add(lblSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 10, 50, 40));

        jtbEnSal.setFont(new java.awt.Font("Corbel", 0, 14)); // NOI18N
        jtbEnSal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jtbEnSal);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 880, 180));
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 890, 10));

        jPanel4.setBackground(new java.awt.Color(41, 42, 44));
        jPanel4.setBorder(new javax.swing.border.LineBorder(java.awt.Color.cyan, 1, true));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtxtCanti.setBackground(new java.awt.Color(41, 42, 44));
        jtxtCanti.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jtxtCanti.setForeground(new java.awt.Color(255, 255, 255));
        jtxtCanti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtCantiActionPerformed(evt);
            }
        });
        jPanel4.add(jtxtCanti, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 32, 120, 40));

        jLabel7.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel7.setForeground(java.awt.Color.cyan);
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Cantidad:");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 110, 20));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 70, 140, 80));

        jPanel5.setBackground(new java.awt.Color(41, 42, 44));
        jPanel5.setBorder(new javax.swing.border.LineBorder(java.awt.Color.cyan, 1, true));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jrbEntrada.setBackground(new java.awt.Color(41, 42, 44));
        jrbEntrada.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jrbEntrada.setForeground(new java.awt.Color(255, 255, 255));
        jrbEntrada.setText("Entrada");
        jPanel5.add(jrbEntrada, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 25, -1, 30));

        jrbSalida.setBackground(new java.awt.Color(41, 42, 44));
        jrbSalida.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jrbSalida.setForeground(new java.awt.Color(255, 255, 255));
        jrbSalida.setText("Salida");
        jPanel5.add(jrbSalida, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 25, -1, 30));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 230, 80));

        jPanel6.setBackground(new java.awt.Color(41, 42, 44));
        jPanel6.setBorder(new javax.swing.border.LineBorder(java.awt.Color.cyan, 1, true));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtxtNombre.setBackground(new java.awt.Color(41, 42, 44));
        jtxtNombre.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jtxtNombre.setForeground(new java.awt.Color(255, 255, 255));
        jtxtNombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtNombreFocusLost(evt);
            }
        });
        jtxtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtNombreActionPerformed(evt);
            }
        });
        jPanel6.add(jtxtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 35, 460, -1));

        jLabel8.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel8.setForeground(java.awt.Color.cyan);
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Nombre Producto:");
        jPanel6.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 200, 20));

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 160, 490, 80));

        jbtnAgregar.setBackground(new java.awt.Color(41, 42, 44));
        jbtnAgregar.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jbtnAgregar.setForeground(new java.awt.Color(232, 251, 244));
        jbtnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/guardar32_1.png"))); // NOI18N
        jbtnAgregar.setText("Guardar");
        jbtnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAgregarActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 450, 300, 60));

        jPanel7.setBackground(new java.awt.Color(41, 42, 44));
        jPanel7.setBorder(new javax.swing.border.LineBorder(java.awt.Color.cyan, 1, true));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtxtCodigo.setBackground(new java.awt.Color(41, 42, 44));
        jtxtCodigo.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jtxtCodigo.setForeground(new java.awt.Color(255, 255, 255));
        jtxtCodigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtCodigoFocusLost(evt);
            }
        });
        jtxtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtCodigoActionPerformed(evt);
            }
        });
        jPanel7.add(jtxtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 35, 210, -1));

        jLabel9.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel9.setForeground(java.awt.Color.cyan);
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Codigo Producto:");
        jPanel7.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 200, 20));

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 230, 80));

        jbtnAgregar1.setBackground(new java.awt.Color(41, 42, 44));
        jbtnAgregar1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jbtnAgregar1.setForeground(new java.awt.Color(232, 251, 244));
        jbtnAgregar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/compras32.png"))); // NOI18N
        jbtnAgregar1.setText("Agregar");
        jbtnAgregar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAgregar1ActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnAgregar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 160, 140, 80));

        jPanel11.setBackground(new java.awt.Color(41, 42, 44));
        jPanel11.setBorder(new javax.swing.border.LineBorder(java.awt.Color.cyan, 1, true));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel16.setForeground(java.awt.Color.cyan);
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("Bodega:");
        jPanel11.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 20, 180, 20));

        jcboBodega.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jcboBodega.setForeground(new java.awt.Color(255, 255, 255));
        jPanel11.add(jcboBodega, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 40, 220, -1));

        jcboSucu.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jcboSucu.setForeground(new java.awt.Color(255, 255, 255));
        jcboSucu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboSucuActionPerformed(evt);
            }
        });
        jPanel11.add(jcboSucu, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 230, -1));

        jLabel17.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel17.setForeground(java.awt.Color.cyan);
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("Sucursal:");
        jPanel11.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 180, 20));

        jPanel1.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 490, 80));
        jPanel1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 890, 20));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 910, 520));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblSalirMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseMoved
        lblSalir.setBorder(javax.swing.BorderFactory.createLineBorder(Color.RED));
    }//GEN-LAST:event_lblSalirMouseMoved

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
       jfInventario inve = new jfInventario();           
       inve.setVisible(true);
       this.dispose();
    }//GEN-LAST:event_lblSalirMouseClicked

    private void lblSalirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSalirMouseEntered

    private void lblSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseExited
        lblSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(41,42,44)));
    }//GEN-LAST:event_lblSalirMouseExited

    private void jtxtCantiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtCantiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtCantiActionPerformed

    private void jtxtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtNombreActionPerformed

    private void jbtnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAgregarActionPerformed
        grabar_inventario();
    }//GEN-LAST:event_jbtnAgregarActionPerformed

    private void jtxtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtCodigoActionPerformed

    private void jbtnAgregar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAgregar1ActionPerformed
        agregarProd();
    }//GEN-LAST:event_jbtnAgregar1ActionPerformed

    private void jcboSucuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcboSucuActionPerformed
        idex_combo combo = new idex_combo();

        int idSuc = 0;
        if(jcboSucu.getItemAt(jcboSucu.getSelectedIndex()).getId() == null){
            idSuc = 0;
        }else{
            idSuc = jcboSucu.getItemAt(jcboSucu.getSelectedIndex()).getId();
        }

        jcboBodega.removeAllItems();
        combo.llenarCombo2(jcboBodega, "id_bodega", "tBodega", "txt_descripcion"," and id_suc = " + "'" + idSuc + "'");
    }//GEN-LAST:event_jcboSucuActionPerformed

    private void jtxtCodigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtCodigoFocusLost
        recupProd_cod(1);
    }//GEN-LAST:event_jtxtCodigoFocusLost

    private void jtxtNombreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtNombreFocusLost
        recupProd_cod(2);
    }//GEN-LAST:event_jtxtNombreFocusLost

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
            java.util.logging.Logger.getLogger(jfEntrada_Salida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfEntrada_Salida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfEntrada_Salida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfEntrada_Salida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jfEntrada_Salida().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JButton jbtnAgregar;
    private javax.swing.JButton jbtnAgregar1;
    private javax.swing.JComboBox<idex_combo> jcboBodega;
    private javax.swing.JComboBox<idex_combo> jcboSucu;
    private javax.swing.JRadioButton jrbEntrada;
    private javax.swing.JRadioButton jrbSalida;
    private javax.swing.JTable jtbEnSal;
    private javax.swing.JTextField jtxtCanti;
    private javax.swing.JTextField jtxtCodigo;
    private javax.swing.JTextField jtxtNombre;
    private javax.swing.JLabel lblSalir;
    // End of variables declaration//GEN-END:variables
}
