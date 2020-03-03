/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formas;
import java.awt.Color;
import javax.swing.JOptionPane;
import Animacion.Fade;
import clases_varias.Exportar_excel;
import clases_varias.conectar;
import clases_varias.idex_combo;
import clases_varias.user_logged;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author wwwki
 */
public class jfConsulta_Cliente extends javax.swing.JFrame {
    conectar cc = new conectar();    
    
    /**
     * Creates new form jfConsulta_Cliente
     */
    public jfConsulta_Cliente() {
        initComponents();        
        this.setIconImage(new ImageIcon(getClass().getResource("/Iconos/CyCo64.png")).getImage());
        this.setLocationRelativeTo(null);
        
        //Llenar jcombobox
        idex_combo combo = new idex_combo();
        combo.llenarCombo(comboTipo, "id_estado", "testado", "txt_desc");
                
        
        //Auto completar jtexfield
        buscarProdCodAutoC();
        
        //La tabla no se autoresizable
        jtCliente.setAutoResizeMode(jtCliente.AUTO_RESIZE_OFF); 
    }

    void buscarProdCodAutoC(){
    String ssql;
    
        try {
            Connection cn = cc.conexion();
            ssql = "";
            ssql = ssql + " select * from ";
            ssql = ssql + " tcliente ";
            ssql = ssql + " where sn_activo = 1";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            TextAutoCompleter nombreCli = new TextAutoCompleter(jtNombre);
            while(rs.next()){
                 nombreCli.addItem(rs.getString("txt_nombre"));
            }
            nombreCli.setMode(0); // infijo
             rs.close();
             st.close();
             cn.close();
        } catch (Exception e) {
        }
    }
    
    void buscarCliente(){
    
        String ssql;
        
        try {
            Connection cn = cc.conexion();
            DefaultTableModel tbCliente = new DefaultTableModel();
            tbCliente.addColumn("id_cliente");
            tbCliente.addColumn("NOMBRE");
            tbCliente.addColumn("NIT");
            tbCliente.addColumn("TELEFONO");
            tbCliente.addColumn("DIRECCION");
            tbCliente.addColumn("ESTADO");
            tbCliente.addColumn("ID_ESTADO");
            tbCliente.addColumn("DIAS CREDITO");
            jtCliente.setModel(tbCliente);
            
            
            //ID_CLIENTE
            jtCliente.getColumnModel().getColumn(0).setMaxWidth(0);
            jtCliente.getColumnModel().getColumn(0).setMinWidth(0);
            jtCliente.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
            jtCliente.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
        
            //NOMBRE
            jtCliente.getColumnModel().getColumn(1).setMaxWidth(230);
            jtCliente.getColumnModel().getColumn(1).setMinWidth(230);
            jtCliente.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(230);
            jtCliente.getTableHeader().getColumnModel().getColumn(1).setMinWidth(230);
            
            //NIT
            jtCliente.getColumnModel().getColumn(2).setMaxWidth(75);
            jtCliente.getColumnModel().getColumn(2).setMinWidth(75);
            jtCliente.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(75);
            jtCliente.getTableHeader().getColumnModel().getColumn(2).setMinWidth(75);
            
            //TELEFONO
            jtCliente.getColumnModel().getColumn(3).setMaxWidth(100);
            jtCliente.getColumnModel().getColumn(3).setMinWidth(100);
            jtCliente.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(100);
            jtCliente.getTableHeader().getColumnModel().getColumn(3).setMinWidth(100);
            
            
            //DIRECCION
            jtCliente.getColumnModel().getColumn(4).setMaxWidth(310);
            jtCliente.getColumnModel().getColumn(4).setMinWidth(310);
            jtCliente.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(310);
            jtCliente.getTableHeader().getColumnModel().getColumn(4).setMinWidth(310);
            
                        
            //ESTADO
            jtCliente.getColumnModel().getColumn(5).setMaxWidth(75);
            jtCliente.getColumnModel().getColumn(5).setMinWidth(75);
            jtCliente.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(75);
            jtCliente.getTableHeader().getColumnModel().getColumn(5).setMinWidth(75);
            
            //ID_ESTADO
            jtCliente.getColumnModel().getColumn(6).setMaxWidth(0);
            jtCliente.getColumnModel().getColumn(6).setMinWidth(0);
            jtCliente.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(0);
            jtCliente.getTableHeader().getColumnModel().getColumn(6).setMinWidth(0);
            
            //DIAS
            jtCliente.getColumnModel().getColumn(7).setMaxWidth(100);
            jtCliente.getColumnModel().getColumn(7).setMinWidth(100);
            jtCliente.getTableHeader().getColumnModel().getColumn(7).setMaxWidth(100);
            jtCliente.getTableHeader().getColumnModel().getColumn(7).setMinWidth(100);
            
            
            ssql = "";
            ssql = ssql + "select id_cliente,";
            ssql = ssql + " txt_nombre,";
            ssql = ssql + " txt_nit,";
            ssql = ssql + " txt_telefono,";
            ssql = ssql + " txt_direccion,";
            ssql = ssql + " ts.txt_desc,";
            ssql = ssql + " tcliente.sn_activo,";           
            ssql = ssql + " tcliente.cnt_dias_credito"; 
            ssql = ssql + " from tcliente"; 
            ssql = ssql + " inner join testado ts on ts.id_estado = tcliente.sn_activo";
            if(chkNombre.isSelected() == true && chkNit.isSelected() == false && chkEstado.isSelected() == false){
                ssql = ssql + " where txt_nombre = " + "'" + jtNombre.getText() + "'";
            }
            if(chkNombre.isSelected() == true && chkNit.isSelected() == false && chkEstado.isSelected() == true){
                ssql = ssql + " where txt_nombre = "+ "'" + jtNombre.getText() + "'";
                ssql = ssql + " and tcliente.sn_activo = " + comboTipo.getItemAt(comboTipo.getSelectedIndex()).getId();            
            }
            if(chkNombre.isSelected() == false && chkNit.isSelected() == false && chkEstado.isSelected() == true){
                ssql = ssql + " where tcliente.sn_activo = " + comboTipo.getItemAt(comboTipo.getSelectedIndex()).getId();            
            }
            if(chkNombre.isSelected() == false && chkNit.isSelected() == true && chkEstado.isSelected() == false){
                ssql = ssql + " where txt_nit = " + "'" + jtNit.getText() + "'";
            }            
            if(chkNombre.isSelected() == false && chkNit.isSelected() == true && chkEstado.isSelected() == true){
                ssql = ssql + " where txt_nit = " + "'" + jtNit.getText() + "'";
                ssql = ssql + " and tcliente.sn_activo = " + comboTipo.getItemAt(comboTipo.getSelectedIndex()).getId();            
            }
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
                tbCliente.addRow(datos);                                            
            }
                jtCliente.setModel(tbCliente);  
                rs.close();
                st.close();
                cn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al buscar cliente: " +e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
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
        lblMinimizar = new javax.swing.JLabel();
        lblSalir = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtCliente = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }

        }
        ;
        jPanel3 = new javax.swing.JPanel();
        jSeparator3 = new javax.swing.JSeparator();
        chkNit = new javax.swing.JCheckBox();
        jtNit = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jSeparator4 = new javax.swing.JSeparator();
        chkNombre = new javax.swing.JCheckBox();
        jtNombre = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        comboTipo = new javax.swing.JComboBox<>();
        chkEstado = new javax.swing.JCheckBox();
        jSeparator2 = new javax.swing.JSeparator();
        btnBuscar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jtExportar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(41, 42, 44));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Corbel", 0, 36)); // NOI18N
        jLabel1.setForeground(java.awt.Color.cyan);
        jLabel1.setText("Consultar Clientes");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 300, -1));

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
        jPanel1.add(lblMinimizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 10, 50, 40));

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
        jPanel1.add(lblSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 10, 50, 40));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 780, 10));

        jtCliente.setFont(new java.awt.Font("Ebrima", 0, 14)); // NOI18N
        jtCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jtCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtClienteMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtCliente);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 800, 200));

        jPanel3.setBackground(new java.awt.Color(41, 42, 44));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.cyan));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel3.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 160, 10));

        chkNit.setBackground(new java.awt.Color(41, 42, 44));
        chkNit.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        chkNit.setForeground(java.awt.Color.cyan);
        chkNit.setText("Nit:");
        chkNit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkNitActionPerformed(evt);
            }
        });
        jPanel3.add(chkNit, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jtNit.setBackground(new java.awt.Color(41, 42, 44));
        jtNit.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jtNit.setForeground(new java.awt.Color(232, 251, 244));
        jtNit.setBorder(null);
        jtNit.setCaretColor(java.awt.Color.cyan);
        jtNit.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtNitFocusGained(evt);
            }
        });
        jPanel3.add(jtNit, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 34, 150, 30));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 70, 180, 80));

        jPanel4.setBackground(new java.awt.Color(41, 42, 44));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.cyan));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 400, 10));

        chkNombre.setBackground(new java.awt.Color(41, 42, 44));
        chkNombre.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        chkNombre.setForeground(java.awt.Color.cyan);
        chkNombre.setText("Por Nombre:");
        chkNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkNombreActionPerformed(evt);
            }
        });
        jPanel4.add(chkNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jtNombre.setBackground(new java.awt.Color(41, 42, 44));
        jtNombre.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jtNombre.setForeground(new java.awt.Color(232, 251, 244));
        jtNombre.setBorder(null);
        jtNombre.setCaretColor(java.awt.Color.cyan);
        jtNombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtNombreFocusGained(evt);
            }
        });
        jPanel4.add(jtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 34, 400, 30));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 420, 80));

        jPanel2.setBackground(new java.awt.Color(41, 42, 44));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.cyan));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        comboTipo.setBackground(java.awt.Color.black);
        comboTipo.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        comboTipo.setForeground(java.awt.Color.cyan);
        jPanel2.add(comboTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 160, 40));

        chkEstado.setBackground(new java.awt.Color(41, 42, 44));
        chkEstado.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        chkEstado.setForeground(java.awt.Color.cyan);
        chkEstado.setText("Por Estado:");
        jPanel2.add(chkEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 70, 180, 80));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 810, 10));

        btnBuscar.setBackground(new java.awt.Color(41, 42, 44));
        btnBuscar.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(232, 251, 244));
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/buscar32_2.png"))); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        jPanel1.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 380, 150, 50));

        jButton2.setBackground(new java.awt.Color(41, 42, 44));
        jButton2.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(232, 251, 244));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/editar432.png"))); // NOI18N
        jButton2.setText("Modificar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 380, 140, 50));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/clientes132.png"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 12, -1, 50));

        jtExportar.setBackground(new java.awt.Color(41, 42, 44));
        jtExportar.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jtExportar.setForeground(new java.awt.Color(232, 251, 244));
        jtExportar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/excel32_1.png"))); // NOI18N
        jtExportar.setText("Exportar");
        jtExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtExportarActionPerformed(evt);
            }
        });
        jPanel1.add(jtExportar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, 150, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 440));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblSalirMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseMoved
        lblSalir.setBorder(javax.swing.BorderFactory.createLineBorder(Color.RED));
    }//GEN-LAST:event_lblSalirMouseMoved

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        int dialog = JOptionPane.YES_NO_OPTION;
        int result = JOptionPane.showConfirmDialog(null, "¿Desea salir?","Salir",dialog);
        if(result == 0){
            
            jfCliente cli = new jfCliente();            
            cli.iTipoProc = 1;    
            this.dispose();
            cli.setVisible(true);                        
            Fade.JFrameFadeIn(0f, 1f, 0.2f, 50, cli);
            //Fade.JFrameFadeOut(1f, 0f, 0.1f, 50, this,Fade.EXIT);
        }
    }//GEN-LAST:event_lblSalirMouseClicked

    private void lblSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseExited
        lblSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(41,42,44)));
    }//GEN-LAST:event_lblSalirMouseExited

    private void chkNitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkNitActionPerformed
        if(chkNit.isSelected() == true){
            jtNit.setEditable(true);
            jtNit.setText("");
            chkNombre.setSelected(false);
            jtNombre.setEditable(false);
            jtNombre.setText("");
        }else{
            jtNit.setEditable(false);
            jtNit.setText("");
        }
    }//GEN-LAST:event_chkNitActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void lblMinimizarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinimizarMouseExited
        lblMinimizar.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(41,42,44)));
    }//GEN-LAST:event_lblMinimizarMouseExited

    private void lblMinimizarMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinimizarMouseMoved
        lblMinimizar.setBorder(javax.swing.BorderFactory.createLineBorder(Color.CYAN));
    }//GEN-LAST:event_lblMinimizarMouseMoved

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
            buscarCliente();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void jtNombreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtNombreFocusGained
        jtNombre.selectAll();
    }//GEN-LAST:event_jtNombreFocusGained

    private void jtNitFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtNitFocusGained
        jtNit.selectAll();
    }//GEN-LAST:event_jtNitFocusGained

    private void chkNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkNombreActionPerformed
        if(chkNombre.isSelected() == true){
            jtNombre.setEditable(true);   
            jtNombre.setText("");
            chkNit.setSelected(false);
            jtNit.setEditable(false);
            jtNit.setText("");
        }else{
            jtNombre.setEditable(false);
            jtNombre.setText("");
        }
    }//GEN-LAST:event_chkNombreActionPerformed

    private void jtClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtClienteMouseClicked
           
        try {
            String id_cliente;
            
            if(evt.getClickCount()==2){
            
                   if (this.jtCliente.getRowCount() == 0 )
                        { 
                            JOptionPane.showMessageDialog(null,"No hay registros para leccionar","CyCo",JOptionPane.INFORMATION_MESSAGE); 
                            return;
                        }
                               
                   id_cliente = String.valueOf(jtCliente.getValueAt(jtCliente.getSelectedRow(),0));
                   jfCliente cl = new jfCliente();                                      
                   cl.iTipoProc = 2;    
                   cl.Id_clliente = Integer.parseInt(id_cliente);
                   cl.recuperar_cliente(Integer.parseInt(id_cliente));
                   this.dispose();
                   cl.setVisible(true);                   
                    //jfCliente.Id_clliente
            }
                                                            
        } catch (Exception e) {
        }
 
       
        
    }//GEN-LAST:event_jtClienteMouseClicked

    private void jtExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtExportarActionPerformed
        try {
            Exportar_excel excel = new Exportar_excel();
            excel.exportarExcel(jtCliente);
        } catch (IOException ex) {
            Logger.getLogger(jfConsulta_Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jtExportarActionPerformed

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
            java.util.logging.Logger.getLogger(jfConsulta_Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfConsulta_Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfConsulta_Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfConsulta_Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jfConsulta_Cliente().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JCheckBox chkEstado;
    private javax.swing.JCheckBox chkNit;
    private javax.swing.JCheckBox chkNombre;
    private javax.swing.JComboBox<idex_combo> comboTipo;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTable jtCliente;
    private javax.swing.JButton jtExportar;
    private javax.swing.JTextField jtNit;
    private javax.swing.JTextField jtNombre;
    private javax.swing.JLabel lblMinimizar;
    private javax.swing.JLabel lblSalir;
    // End of variables declaration//GEN-END:variables
}
