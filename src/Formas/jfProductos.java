/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formas;

import Animacion.Fade;
import clases_varias.SetearCombo;
import clases_varias.conectar;
import clases_varias.idex_combo;
import clases_varias.user_logged;
import clases_varias.varias_funciones;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.ImageIcon;

/**
 *
 * @author wwwki
 */
public class jfProductos extends javax.swing.JFrame {

     public static Integer iProceso_prod;
     public static Integer id_producto;
     conectar cc = new conectar();     
     
    
    /**
     * Creates new form jfProductos
     */
    public jfProductos() {
        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("/Iconos/bender_cyco.png")).getImage());
        this.setLocationRelativeTo(null);
        
        idex_combo combo = new idex_combo();
        combo.llenarCombo(comboTipo, "id_tipo", "ttipo", "txt_descripcion");
        combo.llenarCombo(comboMarca, "id_marca", "tmarca", "txt_descripcion_marca");
        
        int idMarca = 0;
        if(comboMarca.getItemAt(comboMarca.getSelectedIndex()).getId() == null){
            idMarca = 0;
        }else{
            idMarca = comboMarca.getItemAt(comboMarca.getSelectedIndex()).getId();
        
        }
//        
        combo.llenarCombo2(comboModelo, "id_modelo", "tmodelo", "txt_descripcion_modelo"," and id_marca = " + "'" + idMarca + "'");
        //idex_combo combo = new idex_combo();
        //combo.llenarCombo(cboBodega, "id_bodega", "tbodega", "txt_desc");
    }

    void validarCampos(){
        
        String sMensaje = "";
        
        try {
            
            if(jtCodigo.getText().trim().equals("")){
                sMensaje = "* Favor completar campo Codigo \n";
            }
            
            if(jtDesc.getText().trim().equals("")){
                sMensaje = sMensaje + "* Favor completar campo Descripcion \n";
            }
            
            if(jtCosto.getText().trim().equals("")){
                sMensaje = sMensaje + "* Favor completar campo precio costo \n";
            }
            
            if(jtxtVenta.getText().trim().equals("")){
                sMensaje = sMensaje+ "* Favor completar campo precio venta \n";            
            }
                        
            
//            if(comboTipo.getItemAt(comboTipo.getSelectedIndex()).getId().equals(0)){
//                sMensaje = sMensaje + "* Favor seleccione una opcion del campo Tipo";
//            }
//            
//            if(comboMarca.getItemAt(comboMarca.getSelectedIndex()).getId().equals(0)){
//                sMensaje = sMensaje + "* Favor seleccione una opcion del campo Tipo";
//            }
//            
//            if(comboModelo.getItemAt(comboModelo.getSelectedIndex()).getId().equals(0)){
//                sMensaje = sMensaje + "* Favor seleccione una opcion del campo Tipo";
//            }                                    
            
            if(!sMensaje.equals("")){
                JOptionPane.showMessageDialog(null, sMensaje, "CyCo", JOptionPane.INFORMATION_MESSAGE);
            }else{
                validaRepetidos();
            }                                   
                                                
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }            
    }
    
    
    void validaRepetidos(){
        String ssql;
        try {
            Connection cn = cc.conexion();
            ssql = "";
            ssql = ssql + "select * from tproducto";
            ssql = ssql + " where txt_descripcion = " + "'" + jtDesc.getText() + "'";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            if(rs.next()){
              JOptionPane.showMessageDialog(null, "Existe un producto con la descripcion: " + jtDesc.getText(), "CyCo", JOptionPane.INFORMATION_MESSAGE);
            }else{
                
                grabarProducto();
            }                                                            
            rs.close();
            st.close();
            cn.close();
        } catch (Exception e) {           
            JOptionPane.showMessageDialog(null, e);
        }
    
    
    }
    
    
    void grabarProducto(){
    
        Integer iActivo;
        
        //varias_funciones ult = new varias_funciones();
          //  int ultimo = ult.recup_ult_numero("tproveedor", "id_proveedor");
        
        try {
            Connection cn = cc.conexion();
            if (chkActivo.isSelected() == true){
                iActivo = 1;
            }else{
                iActivo = 0;
            }
            
            if(iProceso_prod == 1){
                varias_funciones ultimo = new varias_funciones();
                int ulti = ultimo.recup_ult_numero("tproducto", "id_producto");
                
                                                                                                                                                                                                                                                                                                                                             
                CallableStatement cst = cn.prepareCall("{call sp_grabar_producto(?,?,?,?,?,?,?,?,?,?)}");
                cst.setInt(1,ulti);
                cst.setString(2, jtDesc.getText());
                cst.setInt(3, comboTipo.getItemAt(comboTipo.getSelectedIndex()).getId());
                cst.setInt(4, iActivo);
                cst.setInt(5, iProceso_prod);
                cst.setString(6, jtCodigo.getText());
                cst.setInt(7, comboMarca.getItemAt(comboMarca.getSelectedIndex()).getId());
                cst.setInt(8, comboModelo.getItemAt(comboModelo.getSelectedIndex()).getId());
                cst.setDouble(9, Double.parseDouble(jtCosto.getText()));
                cst.setDouble(10, Double.parseDouble(jtxtVenta.getText()));
                cst.execute();
                cst.close();
                JOptionPane.showMessageDialog(null, "Registro guardado con exito", "CyCo", JOptionPane.INFORMATION_MESSAGE);
                limpiar();                                                            
            }else{                
                CallableStatement cst = cn.prepareCall("{call sp_grabar_producto(?,?,?,?,?,?,?,?,?,?)}");                                                               
                cst.setInt(1,id_producto);
                cst.setString(2, jtDesc.getText());
                cst.setInt(3, comboTipo.getItemAt(comboTipo.getSelectedIndex()).getId());
                cst.setInt(4, iActivo);
                cst.setInt(5, iProceso_prod);
                cst.setString(6, jtCodigo.getText());
                cst.setInt(7, comboMarca.getItemAt(comboMarca.getSelectedIndex()).getId());
                cst.setInt(8, comboModelo.getItemAt(comboModelo.getSelectedIndex()).getId());
                cst.setDouble(9, Double.parseDouble(jtCosto.getText()));
                cst.setDouble(10, Double.parseDouble(jtxtVenta.getText()));
                cst.execute();
                cst.close();
                
                JOptionPane.showMessageDialog(null, "Registro modificado con exito", "CyCo", JOptionPane.INFORMATION_MESSAGE);
                limpiar();                                                                        
            }
                            
            cn.close();
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }
    
    
    void limpiar(){
        jtCodigo.setText("");
        jtCosto.setText("0.00");
        jtxtVenta.setText("0.00");        
        jtDesc.setText("");
        chkActivo.setSelected(false);
        comboTipo.setSelectedIndex(0);  
        comboMarca.setSelectedIndex(0);
        comboModelo.setSelectedIndex(0);
        iProceso_prod = 1;
        id_producto = null;
        
        
        
    }
    
    void recuperar_producto(){
        
        try {
            
            SetearCombo sCombo = new SetearCombo();
            Connection cn = cc.conexion();
            CallableStatement cst = cn.prepareCall("{ call sp_recuperar_prod (?,?,?,?,?,?,?,?,?)}");
            cst.setInt(1, id_producto);
            cst.registerOutParameter(2, java.sql.Types.VARCHAR);
            cst.registerOutParameter(3, java.sql.Types.INTEGER);
            cst.registerOutParameter(4, java.sql.Types.INTEGER);
            cst.registerOutParameter(5, java.sql.Types.VARCHAR);
            cst.registerOutParameter(6, java.sql.Types.INTEGER); //marca
            cst.registerOutParameter(7, java.sql.Types.INTEGER); //modelo
            cst.registerOutParameter(8, java.sql.Types.NUMERIC); //costo
            cst.registerOutParameter(9, java.sql.Types.NUMERIC); //venta
            cst.execute();
                        
            jtDesc.setText(cst.getString(2));
            comboTipo.setSelectedIndex(cst.getInt(3));                                    
            if(cst.getInt(4) == 1){
                chkActivo.setSelected(true);
            }else{
                chkActivo.setSelected(false);
            }           
            jtCodigo.setText(cst.getString(5));
            
            int iPosMarca = sCombo.Set_marca_posicion_combo(cst.getInt(6));                                                
            comboMarca.setSelectedIndex(iPosMarca);                                                                  
            int iPosMod = sCombo.Set_modelo_posicion_combo(cst.getInt(6), cst.getInt(7));
            comboModelo.setSelectedIndex(iPosMod);    
            
            Double dCosto = cst.getDouble(8);
            Double dVenta = cst.getDouble(9);
            
            jtCosto.setText(dCosto.toString());
            jtxtVenta.setText(dVenta.toString());
            
            cst.close();
            cn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperar productos: " + e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
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
        jLabel2 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        comboTipo = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        chkActivo = new javax.swing.JCheckBox();
        btnGuardar = new javax.swing.JButton();
        btnConsulta = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jtDesc = new javax.swing.JTextField();
        comboMarca = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jtCodigo = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        comboModelo = new javax.swing.JComboBox<>();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        jtCosto = new javax.swing.JTextField();
        jSeparator10 = new javax.swing.JSeparator();
        jtxtVenta = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(41, 42, 44));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Corbel", 0, 36)); // NOI18N
        jLabel1.setForeground(java.awt.Color.cyan);
        jLabel1.setText("Productos");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 160, 40));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 460, 720, 10));

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
        jPanel1.add(lblMinimizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 10, 50, 40));

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
        jPanel1.add(lblSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 10, 50, 40));

        jLabel2.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel2.setForeground(java.awt.Color.cyan);
        jLabel2.setText("Descripcion:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 140, 130, 40));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 120, 520, 20));

        comboTipo.setBackground(java.awt.Color.black);
        comboTipo.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        comboTipo.setForeground(java.awt.Color.cyan);
        comboTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTipoActionPerformed(evt);
            }
        });
        jPanel1.add(comboTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 250, 520, 30));

        jLabel4.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel4.setForeground(java.awt.Color.cyan);
        jLabel4.setText("Tipo:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(107, 250, -1, -1));

        chkActivo.setBackground(new java.awt.Color(41, 42, 44));
        chkActivo.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        chkActivo.setForeground(java.awt.Color.cyan);
        chkActivo.setText("Activo");
        jPanel1.add(chkActivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 420, 100, -1));

        btnGuardar.setBackground(java.awt.Color.black);
        btnGuardar.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        btnGuardar.setForeground(java.awt.Color.cyan);
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/guardar32_1.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, 150, 50));

        btnConsulta.setBackground(java.awt.Color.black);
        btnConsulta.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        btnConsulta.setForeground(java.awt.Color.cyan);
        btnConsulta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/buscar32_2.png"))); // NOI18N
        btnConsulta.setText("Consultar");
        btnConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaActionPerformed(evt);
            }
        });
        jPanel1.add(btnConsulta, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 470, 150, 50));

        jLabel3.setForeground(java.awt.Color.cyan);
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/productos32.png"))); // NOI18N
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 40, 40));

        jtDesc.setBackground(new java.awt.Color(41, 42, 44));
        jtDesc.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jtDesc.setForeground(new java.awt.Color(232, 251, 244));
        jtDesc.setBorder(null);
        jtDesc.setCaretColor(new java.awt.Color(232, 251, 244));
        jtDesc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtDescFocusGained(evt);
            }
        });
        jtDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtDescActionPerformed(evt);
            }
        });
        jPanel1.add(jtDesc, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 130, 520, 40));

        comboMarca.setBackground(java.awt.Color.black);
        comboMarca.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        comboMarca.setForeground(java.awt.Color.cyan);
        comboMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboMarcaActionPerformed(evt);
            }
        });
        jPanel1.add(comboMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 310, 520, 30));

        jLabel5.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel5.setForeground(java.awt.Color.cyan);
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Marca:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 310, 80, 30));

        jLabel6.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel6.setForeground(java.awt.Color.cyan);
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Codigo: ");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 130, 40));

        jtCodigo.setBackground(new java.awt.Color(41, 42, 44));
        jtCodigo.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jtCodigo.setForeground(new java.awt.Color(232, 251, 244));
        jtCodigo.setBorder(null);
        jtCodigo.setCaretColor(new java.awt.Color(232, 251, 244));
        jtCodigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtCodigoFocusGained(evt);
            }
        });
        jtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtCodigoActionPerformed(evt);
            }
        });
        jPanel1.add(jtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 90, 520, 40));
        jPanel1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 170, 520, 20));

        jLabel7.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel7.setForeground(java.awt.Color.cyan);
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Modelo: ");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 370, 140, 30));

        comboModelo.setBackground(java.awt.Color.black);
        comboModelo.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        comboModelo.setForeground(java.awt.Color.cyan);
        jPanel1.add(comboModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 370, 520, 30));
        jPanel1.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 730, 20));

        jLabel8.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel8.setForeground(java.awt.Color.cyan);
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Precio Costo:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 140, 40));
        jPanel1.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 220, 150, 20));

        jtCosto.setBackground(new java.awt.Color(41, 42, 44));
        jtCosto.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jtCosto.setForeground(new java.awt.Color(232, 251, 244));
        jtCosto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtCosto.setBorder(null);
        jtCosto.setCaretColor(new java.awt.Color(232, 251, 244));
        jtCosto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtCostoFocusGained(evt);
            }
        });
        jtCosto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtCostoActionPerformed(evt);
            }
        });
        jPanel1.add(jtCosto, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 190, 150, 40));
        jPanel1.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 220, 180, 20));

        jtxtVenta.setBackground(new java.awt.Color(41, 42, 44));
        jtxtVenta.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jtxtVenta.setForeground(new java.awt.Color(232, 251, 244));
        jtxtVenta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxtVenta.setBorder(null);
        jtxtVenta.setCaretColor(new java.awt.Color(232, 251, 244));
        jtxtVenta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtVentaFocusGained(evt);
            }
        });
        jtxtVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtVentaActionPerformed(evt);
            }
        });
        jPanel1.add(jtxtVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 190, 180, 40));

        jLabel9.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel9.setForeground(java.awt.Color.cyan);
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Precio de Venta:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 195, 170, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 530));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtDescActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtDescActionPerformed

    private void jtDescFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtDescFocusGained
        jtDesc.selectAll();
    }//GEN-LAST:event_jtDescFocusGained

    private void btnConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaActionPerformed
        jfConsultaProducto cp = new jfConsultaProducto();        
        this.dispose();
        cp.setVisible(true);
        Fade.JFrameFadeIn(0f, 1f, 0.2f, 50, cp);
    }//GEN-LAST:event_btnConsultaActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

        int dialog = JOptionPane.YES_NO_OPTION;
        int result = JOptionPane.showConfirmDialog(null, "¿Desea continuar?","Salir",dialog);
        jfConfigura cf = new jfConfigura();
        if(result == 0){
            if(iProceso_prod == 1){
                validarCampos();
            }else{
                grabarProducto();
            }
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void lblSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseExited
        lblSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(41,42,44)));
    }//GEN-LAST:event_lblSalirMouseExited

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        int dialog = JOptionPane.YES_NO_OPTION;
        int result = JOptionPane.showConfirmDialog(null, "¿Desea salir?","Salir",dialog);
        //jfCompra_detalle cf = new jfCompra_detalle();
        jfConfigura cf = new jfConfigura();
        if(result == 0){
            Fade.JFrameFadeOut(1f, 0f, 0.1f, 50, this,Fade.DISPOSE);         
            //this.dispose();
            cf.setVisible(true);
            //Fade.JFrameFadeIn(0f, 1f, 0.2f, 50, cf);
        }
    }//GEN-LAST:event_lblSalirMouseClicked

    private void lblSalirMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseMoved
        lblSalir.setBorder(javax.swing.BorderFactory.createLineBorder(Color.RED));
    }//GEN-LAST:event_lblSalirMouseMoved

    private void lblMinimizarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinimizarMouseExited
        lblMinimizar.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(41,42,44)));
    }//GEN-LAST:event_lblMinimizarMouseExited

    private void lblMinimizarMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinimizarMouseMoved
        lblMinimizar.setBorder(javax.swing.BorderFactory.createLineBorder(Color.CYAN));
    }//GEN-LAST:event_lblMinimizarMouseMoved

    private void jtCodigoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtCodigoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jtCodigoFocusGained

    private void jtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtCodigoActionPerformed

    private void comboMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboMarcaActionPerformed
        idex_combo combo = new idex_combo();        
                
        int idMarca = 0;
        if(comboMarca.getItemAt(comboMarca.getSelectedIndex()).getId() == null){
            idMarca = 0;
        }else{
            idMarca = comboMarca.getItemAt(comboMarca.getSelectedIndex()).getId();
        
        }
        
        comboModelo.removeAllItems();
        combo.llenarCombo2(comboModelo, "id_modelo", "tmodelo", "txt_descripcion_modelo"," and id_marca = " + "'" + idMarca + "'");
    }//GEN-LAST:event_comboMarcaActionPerformed

    private void jtCostoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtCostoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jtCostoFocusGained

    private void jtCostoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtCostoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtCostoActionPerformed

    private void jtxtVentaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtVentaFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtVentaFocusGained

    private void jtxtVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtVentaActionPerformed

    private void comboTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTipoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboTipoActionPerformed
            
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
            java.util.logging.Logger.getLogger(jfProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jfProductos().setVisible(true);
            }
        });
    }
    
    
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConsulta;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JCheckBox chkActivo;
    private javax.swing.JComboBox<idex_combo> comboMarca;
    private javax.swing.JComboBox<idex_combo> comboModelo;
    private javax.swing.JComboBox<idex_combo> comboTipo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTextField jtCodigo;
    private javax.swing.JTextField jtCosto;
    private javax.swing.JTextField jtDesc;
    private javax.swing.JTextField jtxtVenta;
    private javax.swing.JLabel lblMinimizar;
    private javax.swing.JLabel lblSalir;
    // End of variables declaration//GEN-END:variables
}
