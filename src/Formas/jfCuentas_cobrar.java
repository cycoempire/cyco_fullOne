/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formas;

import Animacion.Fade;
import clases_varias.conectar;
import clases_varias.idex_combo;
import clases_varias.imprimir_reportes;
import clases_varias.user_logged;
import clases_varias.varias_funciones;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import static java.awt.SystemColor.text;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author wwwki
 */
public class jfCuentas_cobrar extends javax.swing.JFrame {
     Integer id_serie =0;
     Integer id_Cliente =0;
     Integer id_Producto=0;
     Integer iEstadoFac=0;
     Integer iTipoFactura=0;
     conectar cc = new conectar();     
     
    
    /**
     * Creates new form jfCuentas_cobrar
     */
    public jfCuentas_cobrar() {
        initComponents();        
        this.setIconImage(new ImageIcon(getClass().getResource("/Iconos/CyCo64.png")).getImage());
        this.setLocationRelativeTo(null);
        
        //La tabla no se autoresizable
        jtableCuentas.setAutoResizeMode(jtableCuentas.AUTO_RESIZE_OFF); 
         buscarClienteAutoC();
        idex_combo combo = new idex_combo();
        combo.llenarCombo(jcomboSerie, "id_serie", "tserie_fac", "txt_serie");
        combo.llenarCombo(jcomboTipoF, "id_tipoF", "ttipo_factura", "txt_desc");              
        
       
    }

    void buscarClienteAutoC(){
    String ssql;
    
        try {
            Connection cn = cc.conexion();
            ssql = "";
            ssql = ssql + " select * from ";
            ssql = ssql + " tcliente ";
            ssql = ssql + " where sn_activo = 1";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            TextAutoCompleter nombreCli = new TextAutoCompleter(txtCliente);
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
   
    
    
    
    void mostrasCuentas(){
        String ssql;
    
        try {
            Connection cn = cc.conexion();
            DefaultTableModel cuentas = new DefaultTableModel();
            cuentas.addColumn("id_factura");                       
            cuentas.addColumn("FECHA");                       
            cuentas.addColumn("TIPO DOC");
            cuentas.addColumn("TIPO VENTA");
            cuentas.addColumn("NO. SERIE");                       
            cuentas.addColumn("NO. DOC");                       
            cuentas.addColumn("CLIENTE");                       
            cuentas.addColumn("TOTAL");                                                          
            cuentas.addColumn("DIAS CREDITO");                       
            cuentas.addColumn("DIAS RESTANTES");                                 
            jtableCuentas.setModel(cuentas);
            
            
            //ID_DOC
            jtableCuentas.getColumnModel().getColumn(0).setMaxWidth(0);
            jtableCuentas.getColumnModel().getColumn(0).setMinWidth(0);
            jtableCuentas.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
            jtableCuentas.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
        
            //FECHA
            jtableCuentas.getColumnModel().getColumn(1).setMaxWidth(105);
            jtableCuentas.getColumnModel().getColumn(1).setMinWidth(105);
            jtableCuentas.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(105);
            jtableCuentas.getTableHeader().getColumnModel().getColumn(1).setMinWidth(105);
            
            //TIPO DOC
            jtableCuentas.getColumnModel().getColumn(2).setMaxWidth(95);
            jtableCuentas.getColumnModel().getColumn(2).setMinWidth(95);
            jtableCuentas.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(95);
            jtableCuentas.getTableHeader().getColumnModel().getColumn(2).setMinWidth(95);
            
            //TIPO VENTA
            jtableCuentas.getColumnModel().getColumn(3).setMaxWidth(110);
            jtableCuentas.getColumnModel().getColumn(3).setMinWidth(110);
            jtableCuentas.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(110);
            jtableCuentas.getTableHeader().getColumnModel().getColumn(3).setMinWidth(110);
            
            //SERIE
            jtableCuentas.getColumnModel().getColumn(4).setMaxWidth(95);
            jtableCuentas.getColumnModel().getColumn(4).setMinWidth(95);
            jtableCuentas.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(95);
            jtableCuentas.getTableHeader().getColumnModel().getColumn(4).setMinWidth(95);
            
            //NO DOC
            jtableCuentas.getColumnModel().getColumn(5).setMaxWidth(90);
            jtableCuentas.getColumnModel().getColumn(5).setMinWidth(90);
            jtableCuentas.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(90);
            jtableCuentas.getTableHeader().getColumnModel().getColumn(5).setMinWidth(90);
            
            //CLIENTE
            jtableCuentas.getColumnModel().getColumn(6).setMaxWidth(285);
            jtableCuentas.getColumnModel().getColumn(6).setMinWidth(285);
            jtableCuentas.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(285);
            jtableCuentas.getTableHeader().getColumnModel().getColumn(6).setMinWidth(285);
            
            //TOTAL
            jtableCuentas.getColumnModel().getColumn(7).setMaxWidth(90);
            jtableCuentas.getColumnModel().getColumn(7).setMinWidth(90);
            jtableCuentas.getTableHeader().getColumnModel().getColumn(7).setMaxWidth(90);
            jtableCuentas.getTableHeader().getColumnModel().getColumn(7).setMinWidth(90);
            
            
            //DIAS CREDITO
            jtableCuentas.getColumnModel().getColumn(8).setMaxWidth(100);
            jtableCuentas.getColumnModel().getColumn(8).setMinWidth(90);
            jtableCuentas.getTableHeader().getColumnModel().getColumn(8).setMaxWidth(100);
            jtableCuentas.getTableHeader().getColumnModel().getColumn(8).setMinWidth(100);
            
            //DIAS RESTANTES
            jtableCuentas.getColumnModel().getColumn(9).setMaxWidth(113);
            jtableCuentas.getColumnModel().getColumn(9).setMinWidth(113);
            jtableCuentas.getTableHeader().getColumnModel().getColumn(9).setMaxWidth(113);
            jtableCuentas.getTableHeader().getColumnModel().getColumn(9).setMinWidth(113);
            
          
            
                                                            
            ssql = "";
            ssql = ssql + " select tf.id_doc, tf.fecha_doc,ttf.txt_descripcion, tipoven.txt_desc, tf.no_serie_doc,tf.no_doc,";
            ssql = ssql + " tcli.txt_nombre,tf.total_factura,tcli.cnt_dias_credito,";
            ssql = ssql + " (tcli.cnt_dias_credito - datediff(CURDATE(),tf.fecha_doc)) AS dias_restantes";
            ssql = ssql + " from tventa tf";
            ssql = ssql + " inner join tcliente tcli on tcli.id_cliente = tf.id_cliente";
            ssql = ssql + " inner join ttipo_documento ttf on ttf.id_tipo_doc = tf.id_tipo_doc";
            ssql = ssql + " inner join ttipo_factura tipoven on tipoven.id_tipof = tf.id_tipo_venta";
            ssql = ssql + " where tf.sn_estado = 4";
            if(jrFechas.isSelected()){
                    varias_funciones vr = new varias_funciones();
                    String fecha_desde = vr.formatoFechaTipo3(jtfDesde.getText());
                    String fecha_hasta = vr.formatoFechaTipo3(jtfHasta.getText());
                    ssql = ssql + " and tf.fecha_doc betwenn '" + fecha_desde + "' and '" + fecha_hasta + "'" ;                                                
            }                                                                       
            if(jchkSerie.isSelected()){
                    ssql = ssql + " and tf.no_serie_doc = '" + jcomboSerie.getItemAt(jcomboSerie.getSelectedIndex()).getDesc() +"'";            
            }            
            if(jrNoFac.isSelected()){
                    ssql = ssql + " and tf.no_doc = " + jtxtNoFac.getText();
            }            
            if(jchkTipoF.isSelected()){
                    ssql = ssql + " and tf.id_tipo_do = " + jcomboTipoF.getItemAt(jcomboTipoF.getSelectedIndex()).getId();              
            }    
             if(jrCliente.isSelected()){
                    ssql = ssql + " and tf.id_cliente = " + id_Cliente;
            }
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            String datos[] = new String[10];
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
                datos[9] = rs.getString(10);
                cuentas.addRow(datos);
            }            
            jtableCuentas.setModel(cuentas);   
            rs.close();
            st.close();
            cn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar cuentas en: "+e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
        }                
    }
    
    
    void imprimirCuentas(){
    
        String ssql;
        Integer ultimo_numero =0;
        
        
        try {
            Connection cn = cc.conexion();
            ssql = "";
            ssql = ssql + " select distinct max(id_timpresion_cuenta) from timpresio_cuentas_cobrar";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            if(rs.next()){
                ultimo_numero = rs.getInt(1);
            }
            
            if(ultimo_numero == 0 || ultimo_numero == null){
                ultimo_numero = 1;            
            }else{
                ultimo_numero = ultimo_numero +1;
            }
            
            ssql = "";
            ssql = ssql + " select tf.id_factura, tf.fecha_factura,tf.no_serie_fac,tf.no_factura,";
            ssql = ssql + " tcli.txt_nombre,tf.total_factura,ttf.txt_desc,tcli.cnt_dias_credito,";
            ssql = ssql + " (tcli.cnt_dias_credito - datediff(CURDATE(),tf.fecha_factura)) AS dias_restantes";
            ssql = ssql + " from tfacturacion tf";
            ssql = ssql + " inner join tcliente tcli on tcli.id_cliente = tf.id_cliente";
            ssql = ssql + " inner join ttipo_factura ttf on ttf.id_tipof = tf.tipo_fac";
            ssql = ssql + " where tf.estado_factura = 4";
            if(jrFechas.isSelected() == true){
                    varias_funciones vr = new varias_funciones();
                    String fecha_desde = vr.formatoFechaTipo3(jtfDesde.getText());
                    String fecha_hasta = vr.formatoFechaTipo3(jtfHasta.getText());
                    ssql = ssql + " and tf.fecha_factura betwenn '" + fecha_desde + "' and '" + fecha_hasta + "'" ;                                                
            }                                                                       
            if(jchkSerie.isSelected() == true ){
                    ssql = ssql + " and tf.no_serie_fac = '" + jcomboSerie.getItemAt(jcomboSerie.getSelectedIndex()).getDesc() +"'";            
            }            
            if(jrNoFac.isSelected() == true){
                    ssql = ssql + " and tf.no_factura = " + jtxtNoFac.getText();
            }            
            if(jchkTipoF.isSelected() == true){
                    ssql = ssql + " and tf.tipo_fac = " + jcomboTipoF.getItemAt(jcomboTipoF.getSelectedIndex()).getId();              
            }    
            Statement stt = cn.createStatement();
            ResultSet rss = stt.executeQuery(ssql);            
            while(rss.next()){                
                CallableStatement cst = cn.prepareCall("{ call sp_grabar_impresion_cuentas_cobrar(?,?,?,?,?,?,?,?,?,?)}");                
                cst.setInt(1, ultimo_numero);                
                cst.setInt(2, rss.getInt(1));                
                cst.setDate(3, rss.getDate(2));                
                cst.setString(4, rss.getString(3));
                cst.setString(5, rss.getString(4));
                cst.setString(6, rss.getString(5));
                cst.setDouble(7, rss.getDouble(6));
                cst.setString(8, rss.getString(7));
                cst.setInt(9, rss.getInt(8));
                cst.setInt(10, rss.getInt(9));
                cst.execute();    
                cst.close();
            }            
            imprimir_reportes ir = new imprimir_reportes();
            ir.imprimir_cuentas(ultimo_numero,"Cuentas_Cobrar");    
                        
            CallableStatement cstt = cn.prepareCall(" delete from timpresio_cuentas_cobrar where id_timpresion_cuenta = " + ultimo_numero);
            cstt.execute();  
            cstt.close();
            cn.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al imprimir cuentas en: " +e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
        }                    
    }
    
    void mostrarId(String sTabla, String sCampoMostrar, String sWhere, String sCampoCompara, Integer id_captura){    
                varias_funciones vr = new varias_funciones();
                //int id = vr.recupId("tserie_fac", "id_serie", "txt_serie", jtxtSerie.getText());
                int id = vr.recupId(sTabla,sCampoMostrar,sWhere,sCampoCompara);
                                
                switch (id_captura){
                    
                    case 1: id_serie = id;
                            break;
                    case 2: id_Cliente = id;
                            break;
                    case 3: id_Producto = id;
                            break;                                
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
        jPanel6 = new javax.swing.JPanel();
        jchkTipoF = new javax.swing.JCheckBox();
        jcomboTipoF = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jSeparator6 = new javax.swing.JSeparator();
        jtxtNoFac = new javax.swing.JTextField();
        jrNoFac = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        jchkSerie = new javax.swing.JCheckBox();
        jcomboSerie = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jtfDesde = new javax.swing.JFormattedTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jrFechas = new javax.swing.JRadioButton();
        jtfHasta = new javax.swing.JFormattedTextField();
        jbtnImprimir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtableCuentas = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jSeparator2 = new javax.swing.JSeparator();
        jbtnBuscar1 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jSeparator7 = new javax.swing.JSeparator();
        jrCliente = new javax.swing.JRadioButton();
        txtCliente = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jchkTipoVenta = new javax.swing.JCheckBox();
        jcboTipoVenta = new javax.swing.JComboBox<>();
        jPanel9 = new javax.swing.JPanel();
        jSeparator8 = new javax.swing.JSeparator();
        jtxtNoFac1 = new javax.swing.JTextField();
        jrNit = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(41, 42, 44));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Corbel", 0, 36)); // NOI18N
        jLabel1.setForeground(java.awt.Color.gray);
        jLabel1.setText("Cuentas por Cobrar");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 310, -1));

        jSeparator1.setBackground(java.awt.Color.cyan);
        jSeparator1.setForeground(java.awt.Color.cyan);
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 1100, 10));

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
        jPanel1.add(lblMinimizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 10, 50, 40));

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
        jPanel1.add(lblSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 10, 50, 40));

        jPanel6.setBackground(new java.awt.Color(41, 42, 44));
        jPanel6.setBorder(new javax.swing.border.LineBorder(java.awt.Color.cyan, 1, true));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jchkTipoF.setBackground(new java.awt.Color(41, 42, 44));
        jchkTipoF.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jchkTipoF.setForeground(java.awt.Color.cyan);
        jchkTipoF.setText("Tipo Documento");
        jPanel6.add(jchkTipoF, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 7, 200, -1));

        jcomboTipoF.setBackground(new java.awt.Color(41, 42, 44));
        jcomboTipoF.setFont(new java.awt.Font("Corbel", 0, 14)); // NOI18N
        jPanel6.add(jcomboTipoF, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 200, 30));

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 80, 220, 70));

        jPanel5.setBackground(new java.awt.Color(41, 42, 44));
        jPanel5.setBorder(new javax.swing.border.LineBorder(java.awt.Color.cyan, 1, true));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel5.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 150, 10));

        jtxtNoFac.setBackground(new java.awt.Color(41, 42, 44));
        jtxtNoFac.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jtxtNoFac.setForeground(new java.awt.Color(232, 251, 244));
        jtxtNoFac.setBorder(null);
        jPanel5.add(jtxtNoFac, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 37, 150, -1));

        jrNoFac.setBackground(new java.awt.Color(41, 42, 44));
        jrNoFac.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jrNoFac.setForeground(java.awt.Color.cyan);
        jrNoFac.setText("No. de Documento");
        jrNoFac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrNoFacActionPerformed(evt);
            }
        });
        jPanel5.add(jrNoFac, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 80, 170, 70));

        jPanel4.setBackground(new java.awt.Color(41, 42, 44));
        jPanel4.setBorder(new javax.swing.border.LineBorder(java.awt.Color.cyan, 1, true));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jchkSerie.setBackground(new java.awt.Color(41, 42, 44));
        jchkSerie.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jchkSerie.setForeground(java.awt.Color.cyan);
        jchkSerie.setText("Serie:");
        jchkSerie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkSerieActionPerformed(evt);
            }
        });
        jPanel4.add(jchkSerie, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        jcomboSerie.setBackground(new java.awt.Color(41, 42, 44));
        jcomboSerie.setFont(new java.awt.Font("Corbel", 0, 14)); // NOI18N
        jPanel4.add(jcomboSerie, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 190, 30));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 80, 210, 70));

        jPanel3.setBackground(new java.awt.Color(41, 42, 44));
        jPanel3.setBorder(new javax.swing.border.LineBorder(java.awt.Color.cyan, 1, true));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtfDesde.setBackground(new java.awt.Color(41, 42, 44));
        jtfDesde.setBorder(null);
        jtfDesde.setForeground(new java.awt.Color(232, 251, 244));
        jtfDesde.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        jtfDesde.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jtfDesde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfDesdeActionPerformed(evt);
            }
        });
        jPanel3.add(jtfDesde, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 37, 110, -1));
        jPanel3.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, 110, 10));
        jPanel3.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 110, 10));

        jrFechas.setBackground(new java.awt.Color(41, 42, 44));
        jrFechas.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jrFechas.setForeground(java.awt.Color.cyan);
        jrFechas.setText("Fecha Desde:         Fecha Hasta:");
        jPanel3.add(jrFechas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 8, -1, 20));

        jtfHasta.setBackground(new java.awt.Color(41, 42, 44));
        jtfHasta.setBorder(null);
        jtfHasta.setForeground(new java.awt.Color(232, 251, 244));
        jtfHasta.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        jtfHasta.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jtfHasta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfHastaActionPerformed(evt);
            }
        });
        jPanel3.add(jtfHasta, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 37, 110, -1));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 270, 70));

        jbtnImprimir.setBackground(new java.awt.Color(41, 42, 44));
        jbtnImprimir.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jbtnImprimir.setForeground(new java.awt.Color(232, 251, 244));
        jbtnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/imprimir32.png"))); // NOI18N
        jbtnImprimir.setText("Imprimir");
        jbtnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnImprimirActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnImprimir, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 270, 50));

        jtableCuentas.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jtableCuentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jtableCuentas.setRowHeight(25);
        jtableCuentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtableCuentasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtableCuentas);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 1090, 300));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 610, 1090, 10));

        jbtnBuscar1.setBackground(new java.awt.Color(41, 42, 44));
        jbtnBuscar1.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jbtnBuscar1.setForeground(new java.awt.Color(232, 251, 244));
        jbtnBuscar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/buscar32_2.png"))); // NOI18N
        jbtnBuscar1.setText("Mostrar");
        jbtnBuscar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnBuscar1ActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnBuscar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 240, 810, 50));

        jPanel7.setBackground(new java.awt.Color(41, 42, 44));
        jPanel7.setBorder(new javax.swing.border.LineBorder(java.awt.Color.cyan, 1, true));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel7.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 60, 770, 10));

        jrCliente.setBackground(new java.awt.Color(41, 42, 44));
        jrCliente.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jrCliente.setForeground(java.awt.Color.cyan);
        jrCliente.setText("Cliente:");
        jPanel7.add(jrCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 100, -1));

        txtCliente.setBackground(new java.awt.Color(41, 42, 44));
        txtCliente.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtCliente.setForeground(new java.awt.Color(232, 251, 244));
        txtCliente.setBorder(null);
        txtCliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtClienteFocusLost(evt);
            }
        });
        jPanel7.add(txtCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 40, 770, -1));

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 160, 810, 70));

        jPanel8.setBackground(new java.awt.Color(41, 42, 44));
        jPanel8.setBorder(new javax.swing.border.LineBorder(java.awt.Color.cyan, 1, true));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jchkTipoVenta.setBackground(new java.awt.Color(41, 42, 44));
        jchkTipoVenta.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jchkTipoVenta.setForeground(java.awt.Color.cyan);
        jchkTipoVenta.setText("Tipo Venta");
        jPanel8.add(jchkTipoVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 7, -1, -1));

        jcboTipoVenta.setBackground(new java.awt.Color(41, 42, 44));
        jcboTipoVenta.setFont(new java.awt.Font("Corbel", 0, 14)); // NOI18N
        jPanel8.add(jcboTipoVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 160, 30));

        jPanel1.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 80, 180, 70));

        jPanel9.setBackground(new java.awt.Color(41, 42, 44));
        jPanel9.setBorder(new javax.swing.border.LineBorder(java.awt.Color.cyan, 1, true));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel9.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 250, 10));

        jtxtNoFac1.setBackground(new java.awt.Color(41, 42, 44));
        jtxtNoFac1.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jtxtNoFac1.setForeground(new java.awt.Color(232, 251, 244));
        jtxtNoFac1.setBorder(null);
        jPanel9.add(jtxtNoFac1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 37, 250, -1));

        jrNit.setBackground(new java.awt.Color(41, 42, 44));
        jrNit.setFont(new java.awt.Font("Corbel", 0, 14)); // NOI18N
        jrNit.setForeground(java.awt.Color.cyan);
        jrNit.setText("NIT Cliente:");
        jrNit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrNitActionPerformed(evt);
            }
        });
        jPanel9.add(jrNit, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        jPanel1.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 270, 70));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1120, 630));

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
        jfReportes mp = new jfReportes();
//        int dialog = JOptionPane.YES_NO_OPTION;
//        int result = JOptionPane.showConfirmDialog(null, "¿Desea salir?","Salir",dialog);
//        if(result == 0){                                        
            //this.dispose();
            mp.setVisible(true);
            this.dispose();
            //Fade.JFrameFadeIn(0f, 1f, 0.2f, 50, mp);
//        }
    }//GEN-LAST:event_lblSalirMouseClicked

    private void lblSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseExited
        lblSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(41,42,44)));
    }//GEN-LAST:event_lblSalirMouseExited

    private void jrNoFacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrNoFacActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jrNoFacActionPerformed

    private void jchkSerieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkSerieActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jchkSerieActionPerformed

    private void jtfDesdeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfDesdeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfDesdeActionPerformed

    private void jtfHastaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfHastaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfHastaActionPerformed

    private void jbtnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnImprimirActionPerformed
        imprimirCuentas();      
    }//GEN-LAST:event_jbtnImprimirActionPerformed

    private void jbtnBuscar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnBuscar1ActionPerformed
        mostrasCuentas();
    }//GEN-LAST:event_jbtnBuscar1ActionPerformed

    private void jtableCuentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtableCuentasMouseClicked
        
        try {
            if(evt.getClickCount()==2){
            
                   if (this.jtableCuentas.getRowCount() == 0 )
                        { 
                            JOptionPane.showMessageDialog(null,"No hay registros para leccionar","CyCo",JOptionPane.INFORMATION_MESSAGE); 
                            return;
                        }
                   varias_funciones vr = new varias_funciones();
                   jfPagar_cuentas pc = new jfPagar_cuentas();
                   String sId_fac = String.valueOf(jtableCuentas.getValueAt(jtableCuentas.getSelectedRow(),0));
                   pc.id_factura = Integer.parseInt(sId_fac);                                      
                   pc.lblSerie.setText(String.valueOf(jtableCuentas.getValueAt(jtableCuentas.getSelectedRow(),4)));
                   pc.lblTIpoDoc.setText(String.valueOf(jtableCuentas.getValueAt(jtableCuentas.getSelectedRow(),2)));
                   pc.lblFecha.setText(vr.MostrarformatoFecha(String.valueOf(jtableCuentas.getValueAt(jtableCuentas.getSelectedRow(),1))));
                   pc.lblTotal.setText(String.valueOf(jtableCuentas.getValueAt(jtableCuentas.getSelectedRow(),7)));
                   pc.lblNo.setText(String.valueOf(jtableCuentas.getValueAt(jtableCuentas.getSelectedRow(),5)));                   
                   pc.calculo_pagar();
                   this.dispose();
                   pc.setVisible(true);                                                                                               
        }
                                    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error en: " +e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
            
        }
        
    }//GEN-LAST:event_jtableCuentasMouseClicked

    private void txtClienteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtClienteFocusLost
        try {
            if(jrCliente.isSelected() == true){
                varias_funciones vr = new varias_funciones();
                mostrarId("tcliente","id_cliente","txt_nombre",txtCliente.getText(),2);  
                
            }else{
                return;
            }
        } catch (Exception e) {            
        } 
    }//GEN-LAST:event_txtClienteFocusLost

    private void jrNitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrNitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jrNitActionPerformed

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
            java.util.logging.Logger.getLogger(jfCuentas_cobrar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfCuentas_cobrar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfCuentas_cobrar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfCuentas_cobrar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jfCuentas_cobrar().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JButton jbtnBuscar1;
    private javax.swing.JButton jbtnImprimir;
    private javax.swing.JComboBox<idex_combo> jcboTipoVenta;
    private javax.swing.JCheckBox jchkSerie;
    private javax.swing.JCheckBox jchkTipoF;
    private javax.swing.JCheckBox jchkTipoVenta;
    private javax.swing.JComboBox<idex_combo> jcomboSerie;
    private javax.swing.JComboBox<idex_combo> jcomboTipoF;
    private javax.swing.JRadioButton jrCliente;
    private javax.swing.JRadioButton jrFechas;
    private javax.swing.JRadioButton jrNit;
    private javax.swing.JRadioButton jrNoFac;
    private javax.swing.JTable jtableCuentas;
    private javax.swing.JFormattedTextField jtfDesde;
    private javax.swing.JFormattedTextField jtfHasta;
    private javax.swing.JTextField jtxtNoFac;
    private javax.swing.JTextField jtxtNoFac1;
    private javax.swing.JLabel lblMinimizar;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JTextField txtCliente;
    // End of variables declaration//GEN-END:variables
}
