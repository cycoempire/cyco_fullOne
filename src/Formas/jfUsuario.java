/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formas;

import clases_varias.conectar;
import clases_varias.user_logged;
import java.awt.Color;
import java.sql.CallableStatement;
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
public class jfUsuario extends javax.swing.JFrame {    
    conectar cc = new conectar();
    int idUser = 0;
    public int iProceso = 0;
    int iEstado = 0;
    
    public jfUsuario() {
        initComponents();        
        this.setLocationRelativeTo(null);    
         //La tabla no se autoresizable
        jtbUsu.setAutoResizeMode(jtbUsu.AUTO_RESIZE_OFF); 
                
        cargarTabla();
    }
                    
    
    void grabarUsuario(){
    
        try {            
            Connection cn = cc.conexion();            
            if(jchkEstado.isSelected()){
                iEstado = 1;
            }else{
                iEstado = 0;
            }                        
                      
            JOptionPane.showMessageDialog(null, idUser + " - " +iProceso);
            
            CallableStatement cst = cn.prepareCall("{call sp_grabar_usu(?,?,?,?,?,?,?,?,?,?,?)}");
            cst.setInt(1, idUser); //idUsu
            cst.setString(2, jtxtCodUsu.getText()); //codUsu
            cst.setString(3, jtxtPass.getText()); //passUsu
            cst.setString(4, jtxtNombre.getText()); //nombreUsu
            cst.setString(5, jtxtApellido.getText()); //apellidoUsu
            cst.setString(6, jtxtDPI.getText()); //tele
            cst.setString(7, jtxtDireccion.getText()); //direc
            cst.setString(8, jtxtTele.getText()); //dpi
            cst.setInt(9, 1); //tipoUsu
            cst.setInt(10, iEstado); //estado
            cst.setInt(11, iProceso); //iProceso
            cst.execute();
            JOptionPane.showMessageDialog(null, "Usuario grabado con exito", "CyCo", JOptionPane.INFORMATION_MESSAGE);
            limpiar();
            cargarTabla();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al grabar usuario en; " + e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
        }            
    }
    
    
    
    void limpiar(){
        jtxtCodUsu.setText("");
        jtxtPass.setText("");
        jchkEstado.setSelected(false);        
        jtxtNombre.setText("");
        jtxtApellido.setText("");
        jtxtDPI.setText("");
        jtxtDireccion.setText("");
        jtxtTele.setText("");   
        iProceso = 1;
        idUser = 0;
    }
    
    void cargarTabla(){                
        
        try {
            String ssql;
            Connection cn = cc.conexion();
            DefaultTableModel tusu = new DefaultTableModel();
            tusu.addColumn("id_usu");
            tusu.addColumn("USUARIO");
            tusu.addColumn("CONTRASEÑA");
            tusu.addColumn("ESTADO");
            tusu.addColumn("NOMBRES");
            tusu.addColumn("APELLIDOS");
            tusu.addColumn("DPI");
            tusu.addColumn("DIRECCION");
            tusu.addColumn("TELEFONO");
            jtbUsu.setModel(tusu);

            //id_doc
            jtbUsu.getColumnModel().getColumn(0).setMaxWidth(0);
            jtbUsu.getColumnModel().getColumn(0).setMinWidth(0);
            jtbUsu.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
            jtbUsu.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);

            //USUARIO
            jtbUsu.getColumnModel().getColumn(1).setMaxWidth(100);
            jtbUsu.getColumnModel().getColumn(1).setMinWidth(100);
            jtbUsu.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(100);
            jtbUsu.getTableHeader().getColumnModel().getColumn(1).setMinWidth(100);

            //CONTRASEÑA
            jtbUsu.getColumnModel().getColumn(2).setMaxWidth(100);
            jtbUsu.getColumnModel().getColumn(2).setMinWidth(100);
            jtbUsu.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(100);
            jtbUsu.getTableHeader().getColumnModel().getColumn(2).setMinWidth(100);

            //ESTADO
            jtbUsu.getColumnModel().getColumn(3).setMaxWidth(70);
            jtbUsu.getColumnModel().getColumn(3).setMinWidth(70);
            jtbUsu.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(70);
            jtbUsu.getTableHeader().getColumnModel().getColumn(3).setMinWidth(70);

            //NOMBRES
            jtbUsu.getColumnModel().getColumn(4).setMaxWidth(200);
            jtbUsu.getColumnModel().getColumn(4).setMinWidth(200);
            jtbUsu.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(200);
            jtbUsu.getTableHeader().getColumnModel().getColumn(4).setMinWidth(200);

            //APELLIDOS
            jtbUsu.getColumnModel().getColumn(5).setMaxWidth(200);
            jtbUsu.getColumnModel().getColumn(5).setMinWidth(200);
            jtbUsu.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(200);
            jtbUsu.getTableHeader().getColumnModel().getColumn(5).setMinWidth(200);

            //DPI
            jtbUsu.getColumnModel().getColumn(6).setMaxWidth(100);
            jtbUsu.getColumnModel().getColumn(6).setMinWidth(100);
            jtbUsu.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(100);
            jtbUsu.getTableHeader().getColumnModel().getColumn(6).setMinWidth(100);

            //DIRECCION
            jtbUsu.getColumnModel().getColumn(7).setMaxWidth(120);
            jtbUsu.getColumnModel().getColumn(7).setMinWidth(120);
            jtbUsu.getTableHeader().getColumnModel().getColumn(7).setMaxWidth(120);
            jtbUsu.getTableHeader().getColumnModel().getColumn(7).setMinWidth(120);

            //TELEFONO
            jtbUsu.getColumnModel().getColumn(8).setMaxWidth(100);
            jtbUsu.getColumnModel().getColumn(8).setMinWidth(100);
            jtbUsu.getTableHeader().getColumnModel().getColumn(8).setMaxWidth(100);
            jtbUsu.getTableHeader().getColumnModel().getColumn(8).setMinWidth(100);
         
            ssql = "";
            ssql = ssql + " select id_usuario, cod_usuario, pass_usuario, tes.txt_desc, txt_nombre_usu, ";
            ssql = ssql + " txt_apellidos, txt_telefono, txt_direccion, txt_dpi";
            ssql = ssql + " from tusuario";
            ssql = ssql + " inner join testado tes on tes.id_estado = tusuario.sn_activo";
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
                tusu.addRow(datos);            
            }
            jtbUsu.setModel(tusu);                                                
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar usuarios en: " + e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
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
        jLabel7 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jtxtCodUsu = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jtxtPass = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbUsu = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }

        };
        jchkEstado = new javax.swing.JCheckBox();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jtxtNombre = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        jtxtApellido = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        jtxtNombre4 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();
        jtxtDPI = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        jtxtDireccion = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jSeparator13 = new javax.swing.JSeparator();
        jtxtTele = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jbtnGrabar = new javax.swing.JButton();
        jSeparator14 = new javax.swing.JSeparator();
        jbtnLimpiar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(41, 42, 44));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Corbel", 1, 18)); // NOI18N
        jLabel1.setForeground(java.awt.Color.gray);
        jLabel1.setText("Datos Usuario");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 120, 20));

        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 60, 20, 520));

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
        jPanel1.add(lblSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 10, 50, 40));

        jLabel7.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel7.setForeground(java.awt.Color.cyan);
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Nombre Usuario:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 180, 40));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 110, 290, 20));

        jtxtCodUsu.setBackground(new java.awt.Color(41, 42, 44));
        jtxtCodUsu.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jtxtCodUsu.setForeground(new java.awt.Color(255, 255, 255));
        jtxtCodUsu.setBorder(null);
        jtxtCodUsu.setCaretColor(new java.awt.Color(232, 251, 244));
        jtxtCodUsu.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtCodUsuFocusGained(evt);
            }
        });
        jtxtCodUsu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtCodUsuActionPerformed(evt);
            }
        });
        jPanel1.add(jtxtCodUsu, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, 290, 40));
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 170, 290, 20));

        jtxtPass.setBackground(new java.awt.Color(41, 42, 44));
        jtxtPass.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jtxtPass.setForeground(new java.awt.Color(255, 255, 255));
        jtxtPass.setBorder(null);
        jtxtPass.setCaretColor(new java.awt.Color(232, 251, 244));
        jtxtPass.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtPassFocusGained(evt);
            }
        });
        jtxtPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtPassActionPerformed(evt);
            }
        });
        jPanel1.add(jtxtPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 140, 290, 40));

        jLabel8.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel8.setForeground(java.awt.Color.cyan);
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Contraseña:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 180, 40));

        jtbUsu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jtbUsu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtbUsuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtbUsu);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 60, 460, 520));

        jchkEstado.setBackground(new java.awt.Color(41, 42, 44));
        jchkEstado.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jchkEstado.setText("Activo");
        jchkEstado.setToolTipText("");
        jPanel1.add(jchkEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 190, -1, 20));
        jPanel1.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 960, 20));

        jLabel2.setFont(new java.awt.Font("Corbel", 1, 36)); // NOI18N
        jLabel2.setForeground(java.awt.Color.gray);
        jLabel2.setText("Usuario");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 210, 40));
        jPanel1.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 490, 10));
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 290, 360, 20));

        jtxtNombre.setBackground(new java.awt.Color(41, 42, 44));
        jtxtNombre.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jtxtNombre.setForeground(new java.awt.Color(232, 251, 244));
        jtxtNombre.setBorder(null);
        jtxtNombre.setCaretColor(new java.awt.Color(232, 251, 244));
        jtxtNombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtNombreFocusGained(evt);
            }
        });
        jtxtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtNombreActionPerformed(evt);
            }
        });
        jPanel1.add(jtxtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 260, 360, 40));

        jLabel9.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel9.setForeground(java.awt.Color.cyan);
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Nombres:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 110, 40));
        jPanel1.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 340, 360, 20));

        jtxtApellido.setBackground(new java.awt.Color(41, 42, 44));
        jtxtApellido.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jtxtApellido.setForeground(new java.awt.Color(232, 251, 244));
        jtxtApellido.setBorder(null);
        jtxtApellido.setCaretColor(new java.awt.Color(232, 251, 244));
        jtxtApellido.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtApellidoFocusGained(evt);
            }
        });
        jtxtApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtApellidoActionPerformed(evt);
            }
        });
        jPanel1.add(jtxtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 310, 360, 40));

        jLabel10.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel10.setForeground(java.awt.Color.cyan);
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Apellidos:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 110, 40));
        jPanel1.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 110, 290, 20));

        jtxtNombre4.setBackground(new java.awt.Color(41, 42, 44));
        jtxtNombre4.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jtxtNombre4.setForeground(new java.awt.Color(232, 251, 244));
        jtxtNombre4.setBorder(null);
        jtxtNombre4.setCaretColor(new java.awt.Color(232, 251, 244));
        jtxtNombre4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtNombre4FocusGained(evt);
            }
        });
        jtxtNombre4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtNombre4ActionPerformed(evt);
            }
        });
        jPanel1.add(jtxtNombre4, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, 290, 40));

        jLabel11.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel11.setForeground(java.awt.Color.cyan);
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Nombre Usuario:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 180, 40));
        jPanel1.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 390, 360, 20));

        jtxtDPI.setBackground(new java.awt.Color(41, 42, 44));
        jtxtDPI.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jtxtDPI.setForeground(new java.awt.Color(232, 251, 244));
        jtxtDPI.setBorder(null);
        jtxtDPI.setCaretColor(new java.awt.Color(232, 251, 244));
        jtxtDPI.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtDPIFocusGained(evt);
            }
        });
        jtxtDPI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtDPIActionPerformed(evt);
            }
        });
        jPanel1.add(jtxtDPI, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 360, 360, 40));

        jLabel12.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel12.setForeground(java.awt.Color.cyan);
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("DPI:");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 110, 40));
        jPanel1.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 440, 360, 20));

        jtxtDireccion.setBackground(new java.awt.Color(41, 42, 44));
        jtxtDireccion.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jtxtDireccion.setForeground(new java.awt.Color(232, 251, 244));
        jtxtDireccion.setBorder(null);
        jtxtDireccion.setCaretColor(new java.awt.Color(232, 251, 244));
        jtxtDireccion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtDireccionFocusGained(evt);
            }
        });
        jtxtDireccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtDireccionActionPerformed(evt);
            }
        });
        jPanel1.add(jtxtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 410, 360, 40));

        jLabel13.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel13.setForeground(java.awt.Color.cyan);
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Direccion:");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 110, 40));
        jPanel1.add(jSeparator13, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 490, 360, 20));

        jtxtTele.setBackground(new java.awt.Color(41, 42, 44));
        jtxtTele.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jtxtTele.setForeground(new java.awt.Color(232, 251, 244));
        jtxtTele.setBorder(null);
        jtxtTele.setCaretColor(new java.awt.Color(232, 251, 244));
        jtxtTele.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtTeleFocusGained(evt);
            }
        });
        jtxtTele.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtTeleActionPerformed(evt);
            }
        });
        jPanel1.add(jtxtTele, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 460, 360, 40));

        jLabel14.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel14.setForeground(java.awt.Color.cyan);
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Telefono:");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, 110, 40));

        jbtnGrabar.setBackground(new java.awt.Color(0, 0, 0));
        jbtnGrabar.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jbtnGrabar.setForeground(new java.awt.Color(255, 255, 255));
        jbtnGrabar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/guardar32_1.png"))); // NOI18N
        jbtnGrabar.setText("Grabar");
        jbtnGrabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnGrabarActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnGrabar, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 520, 320, 60));
        jPanel1.add(jSeparator14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 490, 10));

        jbtnLimpiar.setBackground(new java.awt.Color(0, 0, 0));
        jbtnLimpiar.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jbtnLimpiar.setForeground(new java.awt.Color(255, 255, 255));
        jbtnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/borrar32.png"))); // NOI18N
        jbtnLimpiar.setText("Limpiar");
        jbtnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnLimpiarActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 520, 140, 60));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 980, 590));

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
        lblSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(41,42,44)));
    }//GEN-LAST:event_lblSalirMouseExited

    private void jtxtCodUsuFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtCodUsuFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtCodUsuFocusGained

    private void jtxtCodUsuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtCodUsuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtCodUsuActionPerformed

    private void jtxtPassFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtPassFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtPassFocusGained

    private void jtxtPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtPassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtPassActionPerformed

    private void jtxtNombreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtNombreFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtNombreFocusGained

    private void jtxtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtNombreActionPerformed

    private void jtxtApellidoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtApellidoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtApellidoFocusGained

    private void jtxtApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtApellidoActionPerformed

    private void jtxtNombre4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtNombre4FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtNombre4FocusGained

    private void jtxtNombre4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtNombre4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtNombre4ActionPerformed

    private void jtxtDPIFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtDPIFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtDPIFocusGained

    private void jtxtDPIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtDPIActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtDPIActionPerformed

    private void jtxtDireccionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtDireccionFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtDireccionFocusGained

    private void jtxtDireccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtDireccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtDireccionActionPerformed

    private void jtxtTeleFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtTeleFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtTeleFocusGained

    private void jtxtTeleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtTeleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtTeleActionPerformed

    private void jbtnGrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGrabarActionPerformed
        grabarUsuario();
    }//GEN-LAST:event_jbtnGrabarActionPerformed

    private void jtbUsuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbUsuMouseClicked
        if(evt.getClickCount()==2){                 
                if (this.jtbUsu.getRowCount() == 0 ){ 
                    JOptionPane.showMessageDialog(null,"No hay registros para seleccionar","CyCo",JOptionPane.INFORMATION_MESSAGE); 
                    return;
                }                
                idUser = Integer.parseInt(String.valueOf(jtbUsu.getValueAt(jtbUsu.getSelectedRow(),0)));
                jtxtCodUsu.setText(String.valueOf(jtbUsu.getValueAt(jtbUsu.getSelectedRow(),1)));
                jtxtPass.setText(String.valueOf(jtbUsu.getValueAt(jtbUsu.getSelectedRow(),2)));
                if(String.valueOf(jtbUsu.getValueAt(jtbUsu.getSelectedRow(),3)).equals("ACTIVO")){
                    jchkEstado.setSelected(true);
                }else{
                    jchkEstado.setSelected(false);
                }                                                
                jtxtNombre.setText(String.valueOf(jtbUsu.getValueAt(jtbUsu.getSelectedRow(),4)));                
                jtxtApellido.setText(String.valueOf(jtbUsu.getValueAt(jtbUsu.getSelectedRow(),5)));
                jtxtDPI.setText(String.valueOf(jtbUsu.getValueAt(jtbUsu.getSelectedRow(),6)));
                jtxtDireccion.setText(String.valueOf(jtbUsu.getValueAt(jtbUsu.getSelectedRow(),7)));
                jtxtTele.setText(String.valueOf(jtbUsu.getValueAt(jtbUsu.getSelectedRow(),8)));                                                                                                
                iProceso = 2;                                   
            }                         
    }//GEN-LAST:event_jtbUsuMouseClicked

    private void jbtnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnLimpiarActionPerformed
        limpiar();
    }//GEN-LAST:event_jbtnLimpiarActionPerformed

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
            java.util.logging.Logger.getLogger(jfUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jfUsuario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JButton jbtnGrabar;
    private javax.swing.JButton jbtnLimpiar;
    private javax.swing.JCheckBox jchkEstado;
    private javax.swing.JTable jtbUsu;
    private javax.swing.JTextField jtxtApellido;
    private javax.swing.JTextField jtxtCodUsu;
    private javax.swing.JTextField jtxtDPI;
    private javax.swing.JTextField jtxtDireccion;
    private javax.swing.JTextField jtxtNombre;
    private javax.swing.JTextField jtxtNombre4;
    private javax.swing.JTextField jtxtPass;
    private javax.swing.JTextField jtxtTele;
    private javax.swing.JLabel lblSalir;
    // End of variables declaration//GEN-END:variables
}
