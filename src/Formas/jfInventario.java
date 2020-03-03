/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formas;

import Animacion.Fade;
import clases_varias.conectar;
import clases_varias.idex_combo;
import clases_varias.user_logged;
import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author wwwki
 */
public class jfInventario extends javax.swing.JFrame {
    conectar cc = new conectar();    
    /**
     * Creates new form jfInventario
     */
    public jfInventario() {
        initComponents();
        this.setLocationRelativeTo(null);
        jtbInventario.setAutoResizeMode(jtbInventario.AUTO_RESIZE_OFF); 
        
        idex_combo combo = new idex_combo();
        combo.llenarCombo(jcboTipo, "id_tipo", "ttipo", "txt_descripcion");
        combo.llenarCombo(jcboMarca, "id_marca", "tmarca", "txt_descripcion_marca");        
//        combo.llenarCombo(jcboModelo, "id_modelo", "tmodelo", "txt_descripcion_modelo");
        combo.llenarCombo(jcboSuc, "id_sucursal", "tsucursal", "txt_descripcion");        
//        combo.llenarCombo(jcboBodega, "id_bodega", "tbodega", "txt_descripcion");  

        Mostrar_inventario();
                
    }

    
    void Mostrar_inventario(){
    String ssql;
        try {            
            DefaultTableModel inv = new DefaultTableModel();
            inv.addColumn("id_prod");
            inv.addColumn("EXISTENCIA");
            inv.addColumn("DESCRIPCION");
            inv.addColumn("CODIGO");
            inv.addColumn("TIPO");
            inv.addColumn("MARCA");
            inv.addColumn("MODELO");                                    
            inv.addColumn("SUCURSAL");
            inv.addColumn("BODEGA");            
            jtbInventario.setModel(inv);
            
            
            //ID PROD
            jtbInventario.getColumnModel().getColumn(0).setMaxWidth(0);
            jtbInventario.getColumnModel().getColumn(0).setMinWidth(0);
            jtbInventario.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
            jtbInventario.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
            
            //EXISTENCIA
            jtbInventario.getColumnModel().getColumn(1).setMaxWidth(75);
            jtbInventario.getColumnModel().getColumn(1).setMinWidth(75);
            jtbInventario.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(75);
            jtbInventario.getTableHeader().getColumnModel().getColumn(1).setMinWidth(75);
            
            //DESCRIPCION
            jtbInventario.getColumnModel().getColumn(2).setMaxWidth(250);
            jtbInventario.getColumnModel().getColumn(2).setMinWidth(250);
            jtbInventario.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(250);
            jtbInventario.getTableHeader().getColumnModel().getColumn(2).setMinWidth(250);
            
            //CODIGO
            jtbInventario.getColumnModel().getColumn(3).setMaxWidth(75);
            jtbInventario.getColumnModel().getColumn(3).setMinWidth(75);
            jtbInventario.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(75);
            jtbInventario.getTableHeader().getColumnModel().getColumn(3).setMinWidth(75);
            
            //TIPO
            jtbInventario.getColumnModel().getColumn(4).setMaxWidth(150);
            jtbInventario.getColumnModel().getColumn(4).setMinWidth(150);
            jtbInventario.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(150);
            jtbInventario.getTableHeader().getColumnModel().getColumn(4).setMinWidth(150);
            
            //MARCA
            jtbInventario.getColumnModel().getColumn(5).setMaxWidth(150);
            jtbInventario.getColumnModel().getColumn(5).setMinWidth(150);
            jtbInventario.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(150);
            jtbInventario.getTableHeader().getColumnModel().getColumn(5).setMinWidth(150);
            
            //MODELO
            jtbInventario.getColumnModel().getColumn(6).setMaxWidth(150);
            jtbInventario.getColumnModel().getColumn(6).setMinWidth(150);
            jtbInventario.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(150);
            jtbInventario.getTableHeader().getColumnModel().getColumn(6).setMinWidth(150);
            
            //SUCURSAL
            jtbInventario.getColumnModel().getColumn(7).setMaxWidth(150);
            jtbInventario.getColumnModel().getColumn(7).setMinWidth(150);
            jtbInventario.getTableHeader().getColumnModel().getColumn(7).setMaxWidth(150);
            jtbInventario.getTableHeader().getColumnModel().getColumn(7).setMinWidth(150);
             
            //BODEGA
            jtbInventario.getColumnModel().getColumn(8).setMaxWidth(150);
            jtbInventario.getColumnModel().getColumn(8).setMinWidth(150);
            jtbInventario.getTableHeader().getColumnModel().getColumn(8).setMaxWidth(150);
            jtbInventario.getTableHeader().getColumnModel().getColumn(8).setMinWidth(150);
            
             Connection cn= cc.conexion(); 
             
             ssql = "";
             ssql = ssql + " select ti.id_producto, sum(ti.cantidad) as Existenica, tpro.txt_descripcion, tpro.txt_codigo,   tipo.txt_descripcion, tmar.txt_descripcion_marca as marca,";
             ssql = ssql + " tmod.txt_descripcion_modelo as modelo, tsuc.txt_descripcion as Sucursal,tbod.txt_descripcion as Bodega";
             ssql = ssql + " from tinventario ti";
             ssql = ssql + " inner join tproducto tpro on tpro.id_producto = ti.id_producto";
             ssql = ssql + " inner join tsucursal tsuc on tsuc.id_sucursal = ti.id_sucursal";
             ssql = ssql + " inner join tbodega tbod on tbod.id_bodega = ti.id_bodega";
             ssql = ssql + " inner join tmarca tmar on tmar.id_marca = tpro.id_marca";
             ssql = ssql + " inner join tmodelo tmod on tmod.id_modelo = tpro.id_modelo";
             ssql = ssql + " inner join ttipo tipo on tipo.id_tipo = tpro.id_tipo";
             ssql = ssql + " where ti.id_producto <> 0";             
             if(jchkTipo.isSelected()){
                 ssql = ssql + " and tpro.id_tipo = " + jcboTipo.getItemAt(jcboTipo.getSelectedIndex()).getId();             
             }
             
             if(jchkMarca.isSelected()){
                 ssql = ssql + " and tpro.id_marca = " + jcboMarca.getItemAt(jcboMarca.getSelectedIndex()).getId();
             }
             
             if(jchkModelo.isSelected()){
                 ssql = ssql + " and tpro.id_modelo = " + jcboModelo.getItemAt(jcboModelo.getSelectedIndex()).getId();
             }
             
             if(jchkSuc.isSelected()){
                 ssql = ssql + " and ti.id_sucursal = " + jcboSuc.getItemAt(jcboSuc.getSelectedIndex()).getId();
             }              		 
             
             if(jchkBodega.isSelected()){
                 ssql = ssql + " and ti.id_bodega = + " + jcboBodega.getItemAt(jcboBodega.getSelectedIndex()).getId();
             }
             
             if(jchkCod.isSelected()){
                 ssql = ssql + " and tpro.txt_codigo = '" + jtxtCod.getText() + "'";
             
             }

             if(jchkNombre.isSelected()){
                 ssql = ssql + " and tpro.txt_descripcion = '" + jtxtNombre.getText() + "'";
             }             
             ssql = ssql + " group by ti.id_producto, ti.id_sucursal, ti.id_bodega,tpro.txt_codigo,tpro.txt_descripcion";             
             ssql = ssql + " order by tpro.txt_descripcion asc";
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
                inv.addRow(datos);
            }
            jtbInventario.setModel(inv);
            rs.close();
            st.close();
            cn.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar inventario en: " +e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
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
        jSeparator1 = new javax.swing.JSeparator();
        lblMinimizar = new javax.swing.JLabel();
        lblSalir = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbInventario = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }

        };
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jcboMarca = new javax.swing.JComboBox<>();
        jcboTipo = new javax.swing.JComboBox<>();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jtxtNombre = new javax.swing.JTextField();
        jcboModelo = new javax.swing.JComboBox<>();
        jbtnGuardar = new javax.swing.JButton();
        jbtnFiltrar = new javax.swing.JButton();
        jbtnGuardar2 = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        jtxtCod = new javax.swing.JTextField();
        jcboSuc = new javax.swing.JComboBox<>();
        jcboBodega = new javax.swing.JComboBox<>();
        jchkNombre = new javax.swing.JCheckBox();
        jchkTipo = new javax.swing.JCheckBox();
        jchkMarca = new javax.swing.JCheckBox();
        jchkModelo = new javax.swing.JCheckBox();
        jchkSuc = new javax.swing.JCheckBox();
        jchkBodega = new javax.swing.JCheckBox();
        jchkCod = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 70, 40, 480));

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
        jPanel1.add(lblMinimizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 10, 50, 40));

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
        jPanel1.add(lblSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 10, 50, 40));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 480, 450, 10));

        jtbInventario.setFont(new java.awt.Font("Ebrima", 1, 16)); // NOI18N
        jtbInventario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jtbInventario.setRowHeight(25);
        jScrollPane1.setViewportView(jtbInventario);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 610, 430));

        jLabel2.setFont(new java.awt.Font("Corbel", 0, 36)); // NOI18N
        jLabel2.setForeground(java.awt.Color.gray);
        jLabel2.setText("Inventario");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jLabel3.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel3.setForeground(java.awt.Color.cyan);
        jLabel3.setText("Existencias");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 120, -1));

        jcboMarca.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jcboMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboMarcaActionPerformed(evt);
            }
        });
        jPanel1.add(jcboMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 210, 210, 30));

        jcboTipo.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jPanel1.add(jcboTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 140, 430, 30));
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 1080, 20));

        jLabel4.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel4.setForeground(java.awt.Color.cyan);
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Filtros");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 80, 120, -1));

        jtxtNombre.setBackground(new java.awt.Color(0, 0, 0));
        jtxtNombre.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jtxtNombre.setForeground(new java.awt.Color(255, 255, 255));
        jtxtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtNombreActionPerformed(evt);
            }
        });
        jPanel1.add(jtxtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 350, 310, -1));

        jcboModelo.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jPanel1.add(jcboModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 210, 210, 30));

        jbtnGuardar.setBackground(java.awt.Color.black);
        jbtnGuardar.setFont(new java.awt.Font("Corbel", 1, 18)); // NOI18N
        jbtnGuardar.setForeground(new java.awt.Color(232, 251, 244));
        jbtnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/candex.png"))); // NOI18N
        jbtnGuardar.setText("Cardex");
        jbtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 500, 160, 50));

        jbtnFiltrar.setBackground(java.awt.Color.black);
        jbtnFiltrar.setFont(new java.awt.Font("Corbel", 1, 18)); // NOI18N
        jbtnFiltrar.setForeground(new java.awt.Color(232, 251, 244));
        jbtnFiltrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/buscar132.png"))); // NOI18N
        jbtnFiltrar.setText("Mostrar");
        jbtnFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnFiltrarActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnFiltrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 400, 450, 70));

        jbtnGuardar2.setBackground(java.awt.Color.black);
        jbtnGuardar2.setFont(new java.awt.Font("Corbel", 1, 18)); // NOI18N
        jbtnGuardar2.setForeground(new java.awt.Color(232, 251, 244));
        jbtnGuardar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/arriba_abajo.png"))); // NOI18N
        jbtnGuardar2.setText("Entrada y Salida Manual");
        jbtnGuardar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnGuardar2ActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnGuardar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 500, 280, 50));
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 110, 450, 10));

        jtxtCod.setBackground(new java.awt.Color(0, 0, 0));
        jtxtCod.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jtxtCod.setForeground(new java.awt.Color(255, 255, 255));
        jtxtCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtCodActionPerformed(evt);
            }
        });
        jPanel1.add(jtxtCod, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 350, 110, -1));

        jcboSuc.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jcboSuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboSucActionPerformed(evt);
            }
        });
        jPanel1.add(jcboSuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 280, 210, 30));

        jcboBodega.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jPanel1.add(jcboBodega, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 280, 210, 30));

        jchkNombre.setBackground(new java.awt.Color(0, 0, 0));
        jchkNombre.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jchkNombre.setForeground(java.awt.Color.cyan);
        jchkNombre.setText("Nombre:");
        jchkNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkNombreActionPerformed(evt);
            }
        });
        jPanel1.add(jchkNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 330, -1, 20));

        jchkTipo.setBackground(new java.awt.Color(0, 0, 0));
        jchkTipo.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jchkTipo.setForeground(java.awt.Color.cyan);
        jchkTipo.setText("Tipo:");
        jchkTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkTipoActionPerformed(evt);
            }
        });
        jPanel1.add(jchkTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 120, -1, 20));

        jchkMarca.setBackground(new java.awt.Color(0, 0, 0));
        jchkMarca.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jchkMarca.setForeground(java.awt.Color.cyan);
        jchkMarca.setText("Marca:");
        jchkMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkMarcaActionPerformed(evt);
            }
        });
        jPanel1.add(jchkMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 190, -1, 20));

        jchkModelo.setBackground(new java.awt.Color(0, 0, 0));
        jchkModelo.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jchkModelo.setForeground(java.awt.Color.cyan);
        jchkModelo.setText("Modelo:");
        jchkModelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkModeloActionPerformed(evt);
            }
        });
        jPanel1.add(jchkModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 190, -1, 20));

        jchkSuc.setBackground(new java.awt.Color(0, 0, 0));
        jchkSuc.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jchkSuc.setForeground(java.awt.Color.cyan);
        jchkSuc.setText("Sucursal:");
        jchkSuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkSucActionPerformed(evt);
            }
        });
        jPanel1.add(jchkSuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 260, -1, 20));

        jchkBodega.setBackground(new java.awt.Color(0, 0, 0));
        jchkBodega.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jchkBodega.setForeground(java.awt.Color.cyan);
        jchkBodega.setText("Bodega:");
        jchkBodega.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkBodegaActionPerformed(evt);
            }
        });
        jPanel1.add(jchkBodega, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 260, -1, 20));

        jchkCod.setBackground(new java.awt.Color(0, 0, 0));
        jchkCod.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jchkCod.setForeground(java.awt.Color.cyan);
        jchkCod.setText("Codigo:");
        jchkCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkCodActionPerformed(evt);
            }
        });
        jPanel1.add(jchkCod, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 330, -1, 20));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1110, 560));

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
        jfConfigura mp = new jfConfigura();
        int dialog = JOptionPane.YES_NO_OPTION;
        int result = JOptionPane.showConfirmDialog(null, "¿Desea salir?","Salir",dialog);
        if(result == 0){                       
            //Fade.JFrameFadeOut(1f, 0f, 0.1f, 50, this,Fade.DISPOSE);
            this.dispose();
            mp.setVisible(true);
            Fade.JFrameFadeIn(0f, 1f, 0.2f, 50, mp);
        }
    }//GEN-LAST:event_lblSalirMouseClicked

    private void lblSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseExited
        lblSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(41,42,44)));
    }//GEN-LAST:event_lblSalirMouseExited

    private void jtxtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtNombreActionPerformed

    private void jbtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGuardarActionPerformed
        jfCardex cx = new jfCardex();
        cx.setVisible(true);
        this.dispose();

    }//GEN-LAST:event_jbtnGuardarActionPerformed

    private void jbtnFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnFiltrarActionPerformed
        Mostrar_inventario();
    }//GEN-LAST:event_jbtnFiltrarActionPerformed

    private void jbtnGuardar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGuardar2ActionPerformed
        jfEntrada_Salida ensa = new jfEntrada_Salida();        
        ensa.setVisible(true);
        this.dispose();
        
    }//GEN-LAST:event_jbtnGuardar2ActionPerformed

    private void jtxtCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtCodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtCodActionPerformed

    private void jchkNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jchkNombreActionPerformed

    private void jchkTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkTipoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jchkTipoActionPerformed

    private void jchkMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkMarcaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jchkMarcaActionPerformed

    private void jchkModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkModeloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jchkModeloActionPerformed

    private void jchkSucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkSucActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jchkSucActionPerformed

    private void jchkBodegaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkBodegaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jchkBodegaActionPerformed

    private void jchkCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkCodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jchkCodActionPerformed

    private void jcboSucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcboSucActionPerformed
        idex_combo combo = new idex_combo();        
                
        int idSuc = 0;
        if(jcboSuc.getItemAt(jcboSuc.getSelectedIndex()).getId() == null){
            idSuc = 0;
        }else{
            idSuc = jcboSuc.getItemAt(jcboSuc.getSelectedIndex()).getId();        
        }        
        jcboBodega.removeAllItems();
        combo.llenarCombo2(jcboBodega, "id_bodega", "tBodega", "txt_descripcion"," and id_suc = " + "'" + idSuc + "'");
    }//GEN-LAST:event_jcboSucActionPerformed

    private void jcboMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcboMarcaActionPerformed
        idex_combo combo = new idex_combo();        
                
        int idMarca = 0;
        if(jcboMarca.getItemAt(jcboMarca.getSelectedIndex()).getId() == null){
            idMarca = 0;
        }else{
            idMarca = jcboMarca.getItemAt(jcboMarca.getSelectedIndex()).getId();
        
        }
        
        jcboModelo.removeAllItems();
        combo.llenarCombo2(jcboModelo, "id_modelo", "tmodelo", "txt_descripcion_modelo"," and id_marca = " + "'" + idMarca + "'");
    }//GEN-LAST:event_jcboMarcaActionPerformed

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
            java.util.logging.Logger.getLogger(jfInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jfInventario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JButton jbtnFiltrar;
    private javax.swing.JButton jbtnGuardar;
    private javax.swing.JButton jbtnGuardar2;
    private javax.swing.JComboBox<idex_combo> jcboBodega;
    private javax.swing.JComboBox<idex_combo> jcboMarca;
    private javax.swing.JComboBox<idex_combo> jcboModelo;
    private javax.swing.JComboBox<idex_combo> jcboSuc;
    private javax.swing.JComboBox<idex_combo> jcboTipo;
    private javax.swing.JCheckBox jchkBodega;
    private javax.swing.JCheckBox jchkCod;
    private javax.swing.JCheckBox jchkMarca;
    private javax.swing.JCheckBox jchkModelo;
    private javax.swing.JCheckBox jchkNombre;
    private javax.swing.JCheckBox jchkSuc;
    private javax.swing.JCheckBox jchkTipo;
    private javax.swing.JTable jtbInventario;
    private javax.swing.JTextField jtxtCod;
    private javax.swing.JTextField jtxtNombre;
    private javax.swing.JLabel lblMinimizar;
    private javax.swing.JLabel lblSalir;
    // End of variables declaration//GEN-END:variables
}
