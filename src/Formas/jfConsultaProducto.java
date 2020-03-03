/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formas;

import Animacion.Fade;
import static Formas.jfProductos.iProceso_prod;
import clases_varias.ColorearFilas;
import clases_varias.Exportar_excel;
import clases_varias.TableCellRendererColor;
import clases_varias.conectar;
import clases_varias.idex_combo;
import clases_varias.user_logged;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import java.io.IOException;
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
public class jfConsultaProducto extends javax.swing.JFrame {
     conectar cc = new conectar();     
     
     
    /**
     * Creates new form jfConsultaProducto
     */
    public jfConsultaProducto() {
        initComponents();        
        this.setIconImage(new ImageIcon(getClass().getResource("/Iconos/CyCo64.png")).getImage());
        this.setLocationRelativeTo(null);
        
        
        buscarProdCodAutoC();
        
        idex_combo combo = new idex_combo();
        combo.llenarCombo(jcomboTipo, "id_tipo", "ttipo", "txt_descripcion");
        combo.llenarCombo(jcomboEstado, "id_estado", "testado", "txt_desc");            
        combo.llenarCombo(jcomboMarca, "id_marca", "tmarca", "txt_descripcion_marca");
        
        jtProd.setEditable(false);
        jcomboModelo.setEditable(false);
        jcomboEstado.setEditable(false);
        
        
        //La tabla no se autoresizable
        jtbProductos.setAutoResizeMode(jtbProductos.AUTO_RESIZE_OFF); 
                        
    }    
    
    void buscarProdCodAutoC(){
    String ssql;
    
        try {
            Connection cn = cc.conexion();
            ssql = "";
            ssql = ssql + " select * from ";
            ssql = ssql + " tproducto ";
            ssql = ssql + " where sn_activo = 1";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            TextAutoCompleter nombreCli = new TextAutoCompleter(jtProd);
            while(rs.next()){
                 nombreCli.addItem(rs.getString("txt_descripcion"));
            }
            nombreCli.setMode(0); // infijo
             rs.close();
             st.close();
             cn.close();
        } catch (Exception e) {
        
        }
    }
    void buscar_producto(){
    String ssql;
        
        try {
            
            Connection cn = cc.conexion();
            DefaultTableModel tbProd = new DefaultTableModel();
            tbProd.addColumn("id_producto");
            tbProd.addColumn("DESCRIPCION");
            tbProd.addColumn("TIPO");
            tbProd.addColumn("MARCA");
            tbProd.addColumn("MODELO");
            tbProd.addColumn("P. COSTO");
            tbProd.addColumn("P. VENTA");
            tbProd.addColumn("ESTADO");
            jtbProductos.setModel(tbProd);
            
            
            //ID_producto
            jtbProductos.getColumnModel().getColumn(0).setMaxWidth(0);
            jtbProductos.getColumnModel().getColumn(0).setMinWidth(0);
            jtbProductos.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
            jtbProductos.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
            
            //ID_descripcion
            jtbProductos.getColumnModel().getColumn(1).setMaxWidth(375);
            jtbProductos.getColumnModel().getColumn(1).setMinWidth(375);
            jtbProductos.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(375);
            jtbProductos.getTableHeader().getColumnModel().getColumn(1).setMinWidth(375);
            
            
            //tipo
            jtbProductos.getColumnModel().getColumn(2).setMaxWidth(150);
            jtbProductos.getColumnModel().getColumn(2).setMinWidth(150);
            jtbProductos.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(150);
            jtbProductos.getTableHeader().getColumnModel().getColumn(2).setMinWidth(150);
            
            
            //MARCA
            jtbProductos.getColumnModel().getColumn(3).setMaxWidth(150);
            jtbProductos.getColumnModel().getColumn(3).setMinWidth(150);
            jtbProductos.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(150);
            jtbProductos.getTableHeader().getColumnModel().getColumn(3).setMinWidth(150);
            
            //MODELO
            jtbProductos.getColumnModel().getColumn(4).setMaxWidth(250);
            jtbProductos.getColumnModel().getColumn(4).setMinWidth(250);
            jtbProductos.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(250);
            jtbProductos.getTableHeader().getColumnModel().getColumn(4).setMinWidth(250);
            
            //COSTO
            jtbProductos.getColumnModel().getColumn(5).setMaxWidth(150);
            jtbProductos.getColumnModel().getColumn(5).setMinWidth(150);
            jtbProductos.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(150);
            jtbProductos.getTableHeader().getColumnModel().getColumn(5).setMinWidth(150);
            
            //PRECIO VENTA
            jtbProductos.getColumnModel().getColumn(6).setMaxWidth(150);
            jtbProductos.getColumnModel().getColumn(6).setMinWidth(150);
            jtbProductos.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(150);
            jtbProductos.getTableHeader().getColumnModel().getColumn(6).setMinWidth(150);
            
            //estado
            jtbProductos.getColumnModel().getColumn(7).setMaxWidth(150);
            jtbProductos.getColumnModel().getColumn(7).setMinWidth(150);
            jtbProductos.getTableHeader().getColumnModel().getColumn(7).setMaxWidth(150);
            jtbProductos.getTableHeader().getColumnModel().getColumn(7).setMinWidth(150);
                                                                                                
            ssql = "";         
            ssql = ssql + " select tprod.id_producto, tprod.txt_descripcion, tipo.txt_descripcion as tipo,";
            ssql = ssql + " tmar.txt_descripcion_marca,";
            ssql = ssql + " tmod.txt_descripcion_modelo,";
            ssql = ssql + " tp.dCosto,";
            ssql = ssql + " tp.dPventa,";
            ssql = ssql + " tes.txt_desc";
            ssql = ssql + " from tproducto tprod";
            ssql = ssql + " inner join tprecio tp on tp.id_producto = tprod.id_producto";                                    		        
            ssql = ssql + " inner join ttipo tipo on tipo.id_tipo = tprod.id_tipo";
            ssql = ssql + " inner join tmarca tmar on tmar.id_marca = tprod.id_marca";
            ssql = ssql + " inner join tmodelo tmod on tmod.id_modelo = tprod.id_modelo";
            ssql = ssql + " inner join testado tes on tes.id_estado = tprod.sn_activo";
            ssql = ssql + " where tprod.id_producto <> 0";
            ssql = ssql + " and tp.id_precio = (select max(tprecio.id_precio) from tprecio where tprecio.id_producto = tp.id_producto)";		                            
                                                                                                  
            if(jchkTipo.isSelected() == true){
                ssql = ssql + " and tprod.id_tipo = " + jcomboTipo.getItemAt(jcomboTipo.getSelectedIndex()).getId();
            }
            
            if(jchkMarca.isSelected() == true){
                ssql = ssql + " and tprod.id_marca = " + jcomboMarca.getItemAt(jcomboMarca.getSelectedIndex()).getId();
            }
            
            if(jchkModelo.isSelected() == true){
                ssql = ssql + " and tprod.id_modelo = " + jcomboModelo.getItemAt(jcomboModelo.getSelectedIndex()).getId();
            }
            
            if(jchkEstado.isSelected() == true){
                ssql = ssql + " and tprod.sn_activo = " + jcomboEstado.getItemAt(jcomboEstado.getSelectedIndex()).getId();
            }
            
            if(jchkNombre.isSelected() == true){
                ssql = ssql + " and tprod.txt_descripcion = '" + jtProd.getText() + "'";
                
            }                       
            ssql = ssql + " order by tprod.txt_descripcion asc";
            String datos[] = new String[8];
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
                tbProd.addRow(datos);                                                
                
//               ColorearFilas color = new ColorearFilas(5);
//               jtbProductos.getColumnModel().getColumn(5).setCellRenderer(color);
                
            }
               jtbProductos.setModel(tbProd);
             rs.close();
             st.close();
             cn.close();
             
             jtbProductos.setDefaultRenderer(Object.class, new TableCellRendererColor());
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Error al buscar productos: " +e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
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
        jSeparator1 = new javax.swing.JSeparator();
        lblMinimizar = new javax.swing.JLabel();
        lblSalir = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbProductos = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }

        };
        jPanel3 = new javax.swing.JPanel();
        jSeparator3 = new javax.swing.JSeparator();
        jchkNombre = new javax.swing.JCheckBox();
        jtProd = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jcomboModelo = new javax.swing.JComboBox<>();
        jchkModelo = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jbtnBuscar = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jcomboEstado = new javax.swing.JComboBox<>();
        jchkEstado = new javax.swing.JCheckBox();
        jPanel6 = new javax.swing.JPanel();
        jcomboTipo = new javax.swing.JComboBox<>();
        jchkTipo = new javax.swing.JCheckBox();
        jPanel7 = new javax.swing.JPanel();
        jcomboMarca = new javax.swing.JComboBox<>();
        jchkMarca = new javax.swing.JCheckBox();
        jSeparator4 = new javax.swing.JSeparator();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(41, 42, 44));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Corbel", 0, 36)); // NOI18N
        jLabel1.setForeground(java.awt.Color.cyan);
        jLabel1.setText("Consultar Productos");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, -1, -1));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 910, 20));

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
        jPanel1.add(lblMinimizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 10, 50, 40));

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
        jPanel1.add(lblSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 10, 50, 40));

        jtbProductos.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jtbProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jtbProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtbProductosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtbProductos);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 910, 380));

        jPanel3.setBackground(new java.awt.Color(41, 42, 44));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.cyan));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel3.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 430, 10));

        jchkNombre.setBackground(new java.awt.Color(41, 42, 44));
        jchkNombre.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jchkNombre.setForeground(java.awt.Color.cyan);
        jchkNombre.setText("Por Nombre:");
        jchkNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkNombreActionPerformed(evt);
            }
        });
        jPanel3.add(jchkNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jtProd.setBackground(new java.awt.Color(41, 42, 44));
        jtProd.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jtProd.setForeground(new java.awt.Color(232, 251, 244));
        jtProd.setBorder(null);
        jPanel3.add(jtProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 430, -1));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 450, 80));

        jPanel4.setBackground(new java.awt.Color(41, 42, 44));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.cyan));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jcomboModelo.setBackground(java.awt.Color.black);
        jcomboModelo.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jcomboModelo.setForeground(java.awt.Color.cyan);
        jPanel4.add(jcomboModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 29, 200, 40));

        jchkModelo.setBackground(new java.awt.Color(41, 42, 44));
        jchkModelo.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jchkModelo.setForeground(java.awt.Color.cyan);
        jchkModelo.setText("Modelo");
        jchkModelo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jchkModeloMouseClicked(evt);
            }
        });
        jchkModelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkModeloActionPerformed(evt);
            }
        });
        jPanel4.add(jchkModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 200, -1));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 70, 220, 80));

        jButton1.setBackground(java.awt.Color.black);
        jButton1.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(232, 251, 244));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/quetzal.png"))); // NOI18N
        jButton1.setText("Bitacora Precios");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 650, 190, 50));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/productos32.png"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jbtnBuscar.setBackground(java.awt.Color.black);
        jbtnBuscar.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jbtnBuscar.setForeground(new java.awt.Color(232, 251, 244));
        jbtnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/buscar32_2.png"))); // NOI18N
        jbtnBuscar.setText("Buscar");
        jbtnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnBuscarActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 160, 450, 80));

        jPanel5.setBackground(new java.awt.Color(41, 42, 44));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.cyan));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jcomboEstado.setBackground(java.awt.Color.black);
        jcomboEstado.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jcomboEstado.setForeground(java.awt.Color.cyan);
        jPanel5.add(jcomboEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 200, 40));

        jchkEstado.setBackground(new java.awt.Color(41, 42, 44));
        jchkEstado.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jchkEstado.setForeground(java.awt.Color.cyan);
        jchkEstado.setText("Estado:");
        jchkEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkEstadoActionPerformed(evt);
            }
        });
        jPanel5.add(jchkEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 200, -1));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 70, 220, 80));

        jPanel6.setBackground(new java.awt.Color(41, 42, 44));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.cyan));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jcomboTipo.setBackground(java.awt.Color.black);
        jcomboTipo.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jcomboTipo.setForeground(java.awt.Color.cyan);
        jPanel6.add(jcomboTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 29, 200, 40));

        jchkTipo.setBackground(new java.awt.Color(41, 42, 44));
        jchkTipo.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jchkTipo.setForeground(java.awt.Color.cyan);
        jchkTipo.setText("Tipo:");
        jchkTipo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jchkTipoMouseClicked(evt);
            }
        });
        jchkTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkTipoActionPerformed(evt);
            }
        });
        jPanel6.add(jchkTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 200, -1));

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 220, 80));

        jPanel7.setBackground(new java.awt.Color(41, 42, 44));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.cyan));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jcomboMarca.setBackground(java.awt.Color.black);
        jcomboMarca.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jcomboMarca.setForeground(java.awt.Color.cyan);
        jcomboMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcomboMarcaActionPerformed(evt);
            }
        });
        jPanel7.add(jcomboMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 29, 200, 40));

        jchkMarca.setBackground(new java.awt.Color(41, 42, 44));
        jchkMarca.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jchkMarca.setForeground(java.awt.Color.cyan);
        jchkMarca.setText("Marca:");
        jchkMarca.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jchkMarcaMouseClicked(evt);
            }
        });
        jchkMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkMarcaActionPerformed(evt);
            }
        });
        jPanel7.add(jchkMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 200, -1));

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 70, 220, 80));
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 640, 910, 10));

        jButton3.setBackground(java.awt.Color.black);
        jButton3.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(232, 251, 244));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/excel132.png"))); // NOI18N
        jButton3.setText("Exportar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 650, 150, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 930, 710));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblSalirMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseMoved
        lblSalir.setBorder(javax.swing.BorderFactory.createLineBorder(Color.RED));
    }//GEN-LAST:event_lblSalirMouseMoved

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
//        int dialog = JOptionPane.YES_NO_OPTION;
//        int result = JOptionPane.showConfirmDialog(null, "¿Desea salir?","Salir",dialog);
//        if(result == 0){            
            jfProductos cli = new jfProductos();
            //cli.iTipoProc = 1;    
            this.dispose();            
            cli.setVisible(true);                        
            Fade.JFrameFadeIn(0f, 1f, 0.2f, 50, cli);
            //Fade.JFrameFadeOut(1f, 0f, 0.1f, 50, this,Fade.EXIT);
//        }
        
        
    }//GEN-LAST:event_lblSalirMouseClicked

    private void lblSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseExited
        lblSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(41,42,44)));
    }//GEN-LAST:event_lblSalirMouseExited

    private void lblMinimizarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinimizarMouseExited
        lblMinimizar.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(41,42,44)));
    }//GEN-LAST:event_lblMinimizarMouseExited

    private void lblMinimizarMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinimizarMouseMoved
        lblMinimizar.setBorder(javax.swing.BorderFactory.createLineBorder(Color.CYAN));
    }//GEN-LAST:event_lblMinimizarMouseMoved

    private void jbtnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnBuscarActionPerformed
        buscar_producto();
    }//GEN-LAST:event_jbtnBuscarActionPerformed

    private void jchkNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkNombreActionPerformed
        if(jchkNombre.isSelected() == true){
            jtProd.setEditable(true);           
        }else{
            jtProd.setEditable(false);           
        }
    }//GEN-LAST:event_jchkNombreActionPerformed

    private void jchkModeloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jchkModeloMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jchkModeloMouseClicked

    private void jchkModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkModeloActionPerformed
            if(jchkModelo.isSelected() == true){
                jchkEstado.setEnabled(false);
            }else{
                jchkEstado.setEnabled(true);
            }
    }//GEN-LAST:event_jchkModeloActionPerformed

    private void jchkEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkEstadoActionPerformed
                          
        if(jchkEstado.isSelected() == true){
            jchkModelo.setEnabled(false);
        }else{
            jchkModelo.setEnabled(true);
        }
                
    }//GEN-LAST:event_jchkEstadoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        try {                        
            if(jtbProductos.getSelectedRow() >=0){
                String iDf = String.valueOf(jtbProductos.getValueAt(jtbProductos.getSelectedRow(),0));                 
                jfBitacoraPrecios bp = new jfBitacoraPrecios();
                bp.id_producto =  Integer.parseInt(iDf);    
                bp.recuperar_datos();
                bp.mostrar_precios();
                bp.setVisible(true);
                this.dispose();
            }else{
                JOptionPane.showMessageDialog(null, "Favor seleccione un registro para mostrar la bitacora de precios", "CyCo", JOptionPane.INFORMATION_MESSAGE);                            
                return;
            }                 
        } catch (Exception e) {
        }                                     
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jtbProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbProductosMouseClicked
        
        try {
            
            String id_prod;
            
            if(evt.getClickCount()==2){
                
                        if (this.jtbProductos.getRowCount() == 0 ){ 
                            JOptionPane.showMessageDialog(null,"No hay registros para leccionar","CyCo",JOptionPane.INFORMATION_MESSAGE); 
                            return;
                        }
                
                id_prod = String.valueOf(jtbProductos.getValueAt(jtbProductos.getSelectedRow(),0));
                jfProductos prd = new jfProductos();
                prd.iProceso_prod = 2;
                prd.id_producto = Integer.parseInt(id_prod);
                prd.recuperar_producto();
                //this.dispose();
                Fade.JFrameFadeOut(1f, 0f, 0.1f, 50, this,Fade.DISPOSE); 
                prd.setVisible(true);                                                                            
            }                                    
        } catch (Exception e) {
        }
        
        //iProceso_prod
    }//GEN-LAST:event_jtbProductosMouseClicked

    private void jchkTipoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jchkTipoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jchkTipoMouseClicked

    private void jchkTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkTipoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jchkTipoActionPerformed

    private void jchkMarcaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jchkMarcaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jchkMarcaMouseClicked

    private void jchkMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkMarcaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jchkMarcaActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        
         try {
            Exportar_excel excel = new Exportar_excel();
            excel.exportarExcel(jtbProductos);
        } catch (IOException ex) {
            Logger.getLogger(jfConsultaProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jcomboMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcomboMarcaActionPerformed
        idex_combo combo = new idex_combo();        
                
        int idMarca = 0;
        if(jcomboMarca.getItemAt(jcomboMarca.getSelectedIndex()).getId() == null){
            idMarca = 0;
        }else{
            idMarca = jcomboMarca.getItemAt(jcomboMarca.getSelectedIndex()).getId();
        
        }
        
        jcomboModelo.removeAllItems();
        combo.llenarCombo2(jcomboModelo, "id_modelo", "tmodelo", "txt_descripcion_modelo"," and id_marca = " + "'" + idMarca + "'");
    }//GEN-LAST:event_jcomboMarcaActionPerformed

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
            java.util.logging.Logger.getLogger(jfConsultaProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfConsultaProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfConsultaProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfConsultaProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jfConsultaProducto().setVisible(true);
            }
        });
    }
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JButton jbtnBuscar;
    private javax.swing.JCheckBox jchkEstado;
    private javax.swing.JCheckBox jchkMarca;
    private javax.swing.JCheckBox jchkModelo;
    private javax.swing.JCheckBox jchkNombre;
    private javax.swing.JCheckBox jchkTipo;
    private javax.swing.JComboBox<idex_combo> jcomboEstado;
    private javax.swing.JComboBox<idex_combo> jcomboMarca;
    private javax.swing.JComboBox<idex_combo> jcomboModelo;
    private javax.swing.JComboBox<idex_combo> jcomboTipo;
    private javax.swing.JTextField jtProd;
    private javax.swing.JTable jtbProductos;
    private javax.swing.JLabel lblMinimizar;
    private javax.swing.JLabel lblSalir;
    // End of variables declaration//GEN-END:variables
}
