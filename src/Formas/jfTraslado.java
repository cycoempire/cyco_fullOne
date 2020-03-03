/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formas;

import static Formas.jfFacturar.jtxtProd;
import clases_varias.SetearCombo;
import clases_varias.conectar;
import clases_varias.idex_combo;
import clases_varias.user_logged;
import clases_varias.varias_funciones;
import java.awt.Color;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author wwwki
 */
public class jfTraslado extends javax.swing.JFrame {
    conectar cc= new conectar();
    Integer id_Producto =0;
    /**
     * Creates new form jfTraslado
     */
    public jfTraslado() {
        initComponents();
        this.setLocationRelativeTo(null);        
        cargarTabla();

        idex_combo combo = new idex_combo();        
        combo.llenarCombo(jcboSucSale, "id_sucursal", "tsucursal", "txt_descripcion");        
        combo.llenarCombo(jcboSucEntra, "id_sucursal", "tsucursal", "txt_descripcion");        
    }
    
    
    
    void cargarTabla(){
    
        try {
            
            DefaultTableModel tra = new DefaultTableModel();
            tra.addColumn("id_prod");
            tra.addColumn("COD. PRODUCTO");
            tra.addColumn("PRODUCTO");
            tra.addColumn("CANTIDAD");
            jtbTras.setModel(tra);
            
            //ID_producto
            jtbTras.getColumnModel().getColumn(0).setMaxWidth(0);
            jtbTras.getColumnModel().getColumn(0).setMinWidth(0);
            jtbTras.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
            jtbTras.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
            
            //codigo
            jtbTras.getColumnModel().getColumn(1).setMaxWidth(150);
            jtbTras.getColumnModel().getColumn(1).setMinWidth(150);
            jtbTras.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(150);
            jtbTras.getTableHeader().getColumnModel().getColumn(1).setMinWidth(150);
            
            //producto
            jtbTras.getColumnModel().getColumn(2).setMaxWidth(600);
            jtbTras.getColumnModel().getColumn(2).setMinWidth(600);
            jtbTras.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(600);
            jtbTras.getTableHeader().getColumnModel().getColumn(2).setMinWidth(600);
            
            //cantidad
            jtbTras.getColumnModel().getColumn(3).setMaxWidth(150);
            jtbTras.getColumnModel().getColumn(3).setMinWidth(150);
            jtbTras.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(150);
            jtbTras.getTableHeader().getColumnModel().getColumn(3).setMinWidth(150);
                                                
            
        } catch (Exception e) {
        }
    
    
    }
    
    
    
    void mostrarId(String sTabla, String sCampoMostrar, String sWhere, String sCampoCompara, Integer id_captura){    
                varias_funciones vr = new varias_funciones();
                //int id = vr.recupId("tserie_fac", "id_serie", "txt_serie", jtxtSerie.getText());
                int id = vr.recupId(sTabla,sCampoMostrar,sWhere,sCampoCompara);
                                
                switch (id_captura){                    
                    case 1: id_Producto = id;
                            break;                                          
                }                         
    }            
    
    void recuperar_existencias(){        
        try {
            
            String ssql;
            Connection cn= cc.conexion(); 
            
            //existencia bodega de salida
            ssql = "";
            ssql = ssql + " select sum(ti.cantidad)";
            ssql = ssql + " from tinventario ti";
            ssql = ssql + " where ti.id_sucursal = "+ jcboSucSale.getItemAt(jcboSucSale.getSelectedIndex()).getId();
            ssql = ssql + " and ti.id_bodega = " + jcboBodSale.getItemAt(jcboBodSale.getSelectedIndex()).getId();
            ssql = ssql + " and ti.id_producto = " + id_Producto;
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            if(rs.next()){                                     
                if(rs.getString(1) == null){
                    JOptionPane.showMessageDialog(null, "El producto no cuenta con existencia registrada", "CyCo", JOptionPane.INFORMATION_MESSAGE);
                    limpiar();
                    return;
                }                                
                jtxtExisSale.setText(rs.getString(1));                                
            }else{
                JOptionPane.showMessageDialog(null, "El producto no cuenta con existencia registrada", "CyCo", JOptionPane.INFORMATION_MESSAGE);
                limpiar();
            }       
                                            
            ssql = "";
            ssql = ssql + " select sum(ti.cantidad)";
            ssql = ssql + " from tinventario ti";
            ssql = ssql + " where ti.id_sucursal = "+ jcboSucEntra.getItemAt(jcboSucEntra.getSelectedIndex()).getId();
            ssql = ssql + " and ti.id_bodega = " + jcboBodEntra.getItemAt(jcboBodEntra.getSelectedIndex()).getId();
            ssql = ssql + " and ti.id_producto = " + id_Producto;
            Statement st2 = cn.createStatement();
            ResultSet rs2 = st2.executeQuery(ssql);
            if(rs2.next()){                                     
                if(rs2.getString(1) == null){
                    //jtxtCantiEntra.setText(rs2.getString(1));
                    jtxtCantiEntra.setText("0");
                    return;
                }                                
                jtxtCantiEntra.setText(rs2.getString(1));
            }else{
                JOptionPane.showMessageDialog(null, "El producto no cuenta con existencia registrada", "CyCo", JOptionPane.INFORMATION_MESSAGE);
            }                                                                   
        } catch (Exception e) {
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
                    ssql = ssql + " select tprod.txt_descripcion";                                                           
                    ssql = ssql + " from tproducto tprod";                    
                    ssql = ssql + " where tprod.id_producto <> 0";                    
                    ssql = ssql + " and tprod.txt_codigo = '" + jtxtCodProd.getText() + "'";                    
                    Statement st = cn.createStatement();
                    ResultSet rs = st.executeQuery(ssql);
                    if(rs.next()){
                        if(rs.getString(1) == null){
                            JOptionPane.showMessageDialog(null, "No se encontro el producto con ese codigo!","CyCo", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }
                        jtxtDesProd.setText(rs.getString(1)); 
                        mostrarId("tproducto","id_producto","txt_codigo",jtxtCodProd.getText(),1);                 
                        recuperar_existencias();
                    }else{
                        jtxtDesProd.setText(""); 
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
                    ssql = ssql + " and tprod.txt_descripcion = '" + jtxtDesProd.getText() + "'";                                                                        
                    Statement stt = cn.createStatement();
                    ResultSet rss = stt.executeQuery(ssql);
                    if(rss.next()){
//                        idProd = rss.getInt(1);                        
                        jtxtCodProd.setText("2");                                                                                                                                                                       
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
    
    void agregar(){
        try {            
            
            boolean bExiste = false;
                Double cantidad = Double.parseDouble(jtxtCantidadTras.getText());                
                for(int i = 0; i < jtbTras.getRowCount(); i++){                         
                    if(id_Producto == Integer.parseInt(String.valueOf(jtbTras.getValueAt(i, 0)))){
                        cantidad = (cantidad + Double.parseDouble(String.valueOf(jtbTras.getValueAt(i, 3))));                             
    //                    JTable jtbFactura = new JTable();
                        jtbTras.setValueAt(cantidad, i, 3);                         
                        bExiste = true;
                    }                       
                }          
                        
                if(!bExiste){
                    DefaultTableModel tras = (DefaultTableModel) jtbTras.getModel();                
                    String datos[] = new String[4];
                    datos[0] = id_Producto.toString();
                    datos[1] = jtxtCodProd.getText();
                    datos[2] = jtxtDesProd.getText();
                    datos[3] = jtxtCantidadTras.getText();                                        
                    tras.addRow(datos);
                    jtbTras.setModel(tras);                        
                }
                limpiar();                        

                                                
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al agregar en: "+ e, "CyCO", JOptionPane.INFORMATION_MESSAGE);
        }        
    }
    
    void validaciones_agregar(){
        try {
            
            String sMensaje = "";
            Double dCanti = 0.0;
            
            if(id_Producto == 0){
                sMensaje = " * Favor ingresar un producto existente. \n";            
            }else{
                Double dCanti_bSale = Double.parseDouble(jtxtExisSale.getText());
                Double dCanti_tras =  Double.parseDouble(jtxtCantidadTras.getText());
                if(dCanti_tras > dCanti_bSale){
                    sMensaje = " * La cantidad a trasladar es mayor a la existencia en la bodega de salida. \n";            
                }  
                
                for(int i = 0; i < jtbTras.getRowCount(); i++){                    
                    if(id_Producto == Integer.parseInt(String.valueOf(jtbTras.getValueAt(i, 0)))){                        
                        dCanti = (Double.parseDouble(jtxtCantidadTras.getText()) + Double.parseDouble(String.valueOf(jtbTras.getValueAt(i, 3))));
                    }
                }
                
                if(dCanti > dCanti_bSale){
                    sMensaje = sMensaje + " * La cantidad a trasladar junto con la cantidad previamente ingresada es mayor a la existencia en bodega de salida \n";
                }                
            }                                                           
                                                            
            if(sMensaje.equals("")){
                agregar();
            }else{
                JOptionPane.showMessageDialog(null, sMensaje);
                return;
            }                                                
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al validar para agregar en: " +e, "CyCo", JOptionPane.INFORMATION_MESSAGE);            
        }
    
    
    }
    
    void limpiar(){
        jtxtCodProd.setText("");
        jtxtDesProd.setText("");
        jtxtExisSale.setText("");
        jtxtCantiEntra.setText("");
        jtxtCantidadTras.setText("");
        jtxtObs.setText("");      
        id_Producto = 0;   
    }
    
    void limpiar_todo(){
            jtxtCodProd.setText("");
            jtxtDesProd.setText("");
            jtxtExisSale.setText("");
            jtxtCantiEntra.setText("");
            jtxtCantidadTras.setText("");
            jtxtObs.setText("");      
            id_Producto = 0;   
            
            jcboSucSale.setSelectedIndex(0);
            jcboBodSale.setSelectedIndex(0);
            jcboSucEntra.setSelectedIndex(0);
            jcboBodEntra.setSelectedIndex(0);
            jtxtObs.setText("");
            cargarTabla();
    }
    
    
    void grabar_traslado_cabecera(){
        try {
            
            varias_funciones vr = new varias_funciones();
            Integer ultimo_id = vr.recup_ult_numero("ttraslado", "id_traslado");
            Integer ultimo_no = vr.recup_ult_numero("ttraslado", "no_traslado");
            Iterator<user_logged> itrLogg = Menu_principal.user_memory.iterator();
            user_logged ul = itrLogg.next();   
            
            Connection cn= cc.conexion();             
            CallableStatement cst = cn.prepareCall("{ call sp_traslado(?,?,?,?,?,?,?)}");
            cst.setInt(1, ultimo_id);
            cst.registerOutParameter(1, java.sql.Types.INTEGER);
            cst.setInt(2, ultimo_no);
            cst.setInt(3, jcboBodSale.getItemAt(jcboBodSale.getSelectedIndex()).getId());
            cst.setInt(4, jcboBodEntra.getItemAt(jcboBodEntra.getSelectedIndex()).getId());
            cst.setString(5, jtxtObs.getText());
            cst.setInt(6, 1);            
            cst.setInt(7, ul.getidUsy());
            if(cst.execute()){
                grabar_traslado_detalle(cst.getInt(1));            
            }
            cst.close();
            cn.close();                                    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al grabar cabecera traslado en: " +e,"CyCo", JOptionPane.INFORMATION_MESSAGE);
        }            
    }
    
    void grabar_traslado_detalle(int idTras){        
        try {            
            Connection cn= cc.conexion(); 
            for(int i = 0; i < jtbTras.getRowCount(); i++){
                CallableStatement cst = cn.prepareCall("{ call sp_grabar_traslado_detalle(?,?,?)}");
                cst.setInt(1, idTras);
                cst.setInt(2, Integer.parseInt(String.valueOf(jtbTras.getValueAt(i, 0))));
                cst.setDouble(3, Double.parseDouble(String.valueOf(jtbTras.getValueAt(i, 3))));
                cst.execute();
                cst.close();
            }            
            cn.close();
            grabar_inventario(idTras);                                                                                                           
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al grabar detalle traslado en: "+ e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
        }
    
    }
    
    void grabar_inventario(int idTras){
        try {
            Iterator<user_logged> itrLogg = Menu_principal.user_memory.iterator();
            user_logged ul = itrLogg.next();     
            Connection cn= cc.conexion();             
            Double cantidad = 0.0;
            
            //salida de inventario
            for(int i = 0; i< jtbTras.getRowCount(); i++){
                 cantidad = Double.parseDouble(String.valueOf(jtbTras.getValueAt(i,3)));
                 cantidad = (cantidad * -1);
                 CallableStatement cst = cn.prepareCall("{ call sp_grabar_inventario(?,?,?,?,?,?,?,?,?)}");
                 cst.setInt(1, Integer.parseInt(String.valueOf(jtbTras.getValueAt(i,0))));              //id prod
                 cst.setInt(2, 7);                                                                      //id tipo mov
                 cst.setDouble(3, cantidad);                                                            //cantidad
                 cst.setString(4, "TRASLADO ENTRE BODEGAS - SALIDA");                                   //observacione                 
                 cst.setInt(5, 5);                                                                      //id tipo doc
                 cst.setInt(6, idTras);                                                                 //id documento
                 cst.setInt(7, jcboSucSale.getItemAt(jcboSucSale.getSelectedIndex()).getId());          //id sucu
                 cst.setInt(8, jcboBodSale.getItemAt(jcboBodSale.getSelectedIndex()).getId());          //id bodega
                 cst.setInt(9, ul.getidUsy());                                                          //id usu
                 cst.execute();      
                 cst.close();
            }
            
            
            Double cantidad_entra = 0.0;
            //entrada de inventario
            for(int i = 0; i< jtbTras.getRowCount(); i++){
                 cantidad_entra = Double.parseDouble(String.valueOf(jtbTras.getValueAt(i,3)));                 
                 CallableStatement cst = cn.prepareCall("{ call sp_grabar_inventario(?,?,?,?,?,?,?,?,?)}");
                 cst.setInt(1, Integer.parseInt(String.valueOf(jtbTras.getValueAt(i,0))));              //id prod
                 cst.setInt(2, 8);                                                                      //id tipo mov
                 cst.setDouble(3, cantidad_entra);                                                      //cantidad
                 cst.setString(4, "TRASLADO ENTRE BODEGAS - INGRESO");                                  //observacione                 
                 cst.setInt(5, 5);                                                                      //id tipo doc
                 cst.setInt(6, idTras);                                                                 //id documento
                 cst.setInt(7, jcboSucEntra.getItemAt(jcboSucEntra.getSelectedIndex()).getId());          //id sucu
                 cst.setInt(8, jcboBodEntra.getItemAt(jcboBodEntra.getSelectedIndex()).getId());          //id bodega
                 cst.setInt(9, ul.getidUsy());                                                          //id usu
                 cst.execute();      
                 cst.close();
            }
            cn.close();            
            JOptionPane.showMessageDialog(null, "Traslado entre bodegas realizado con exito!", "CyCo", JOptionPane.INFORMATION_MESSAGE);
            limpiar_todo();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al grabar inventario en: "+ e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
        }        
    }
    
    void validaciones(){
        try {
            
            String sMensaje = "";
            
            if(jcboBodSale.getItemAt(jcboBodSale.getSelectedIndex()).getId() == jcboBodEntra.getItemAt(jcboBodEntra.getSelectedIndex()).getId()){
                sMensaje = " * Las bodega de salida y bodega de ingreso no debe de ser la misma. \n";
            }
            
            if(jtxtObs.getText().trim().equals("")){
                sMensaje = sMensaje + " * Favor completar el campo Observaciones. \n";
            }
            
            if(sMensaje.trim().equals("")){
                grabar_traslado_cabecera();            
            }else{
                JOptionPane.showMessageDialog(null, sMensaje, "CyCo", JOptionPane.INFORMATION_MESSAGE);
            }                        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al validar en: " + e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
        }                    
    }
    
    
    public void recuperar_datos(int idTra){
    
        try {
            jbtnAdd.setEnabled(false);
            jtxtObs.setEditable(false);
            jbtnGrabar.setEnabled(false);
            
            Connection cn= cc.conexion();             
            String ssql;
            SetearCombo setCb = new SetearCombo();
            ssql = "";
            ssql = ssql + " select tsuc_sale.id_sucursal, tbod_sale.id_bodega,";
            ssql = ssql + " tsuc_entra.id_sucursal, tbod_entra.id_bodega,";
            ssql = ssql + " tras.txt_observaciones";
            ssql = ssql + " from ttraslado tras";
            ssql = ssql + " inner join tbodega tbod_sale on tbod_sale.id_bodega = tras.id_bodega_sale";
            ssql = ssql + " inner join tsucursal tsuc_sale on tsuc_sale.id_sucursal = tbod_sale.id_suc";
            ssql = ssql + " inner join tbodega tbod_entra on tbod_entra.id_bodega = tras.id_bodega_entra";
            ssql = ssql + " inner join tsucursal tsuc_entra on tsuc_entra.id_sucursal = tbod_entra.id_suc";
            ssql = ssql + " where tras.id_traslado = " + idTra;
            Statement st1 = cn.createStatement();
            ResultSet rs1 = st1.executeQuery(ssql);
            if(rs1.next()){
                int idSucSale = setCb.set_sucursal(rs1.getInt(1));
                int idBodSale = setCb.set_bodega(rs1.getInt(1) , rs1.getInt(2));
                int idSucEntra = setCb.set_sucursal(rs1.getInt(3));
                int idBodEntra = setCb.set_bodega(rs1.getInt(3), rs1.getInt(4));                
                jcboSucSale.setSelectedIndex(idSucSale);
                jcboBodSale.setSelectedIndex(idBodSale);
                jcboSucEntra.setSelectedIndex(idSucEntra);
                jcboBodEntra.setSelectedIndex(idBodEntra);                
                jtxtObs.setText(rs1.getString(5));
            }
            rs1.close();
            st1.close();                        
            
            DefaultTableModel tras = (DefaultTableModel) jtbTras.getModel();  
            ssql = "";
            ssql = ssql + " select td.id_producto, tprod.txt_codigo, tprod.txt_descripcion, td.cantidad";
            ssql = ssql + " from ttraslado_detalle td";
            ssql = ssql + " inner join tproducto tprod on tprod.id_producto = td.id_producto";
            ssql = ssql + " where td.id_traslado = " + idTra;
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            String datos[] = new String[4];
            while(rs.next()){
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                tras.addRow(datos);            
            }
            jtbTras.setModel(tras);
            rs.close();
            st.close();
            cn.close();
            
        } catch (Exception e) {
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
        jSeparator6 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jcboSucEntra = new javax.swing.JComboBox<>();
        jcboBodEntra = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jtxtCantidadTras = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jtxtCantiEntra = new javax.swing.JTextField();
        jtxtExisSale = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jbtnAdd = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jcboSucSale = new javax.swing.JComboBox<>();
        jcboBodSale = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jPanel4 = new javax.swing.JPanel();
        jtxtObs = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbTras = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jtxtCodProd = new javax.swing.JTextField();
        jtxtDesProd = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jbtnGrabar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(41, 42, 44));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Corbel", 1, 36)); // NOI18N
        jLabel1.setForeground(java.awt.Color.gray);
        jLabel1.setText("Traslado entre Bodegas");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 390, 40));

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
        jPanel1.add(lblSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 10, 50, 40));
        jPanel1.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 880, 10));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(java.awt.Color.cyan);
        jLabel2.setText("Bodega Ingreso");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 60, 140, -1));

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setForeground(java.awt.Color.cyan);
        jLabel3.setText("Bodega Salida");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        jPanel5.setBackground(new java.awt.Color(41, 42, 44));
        jPanel5.setBorder(new javax.swing.border.LineBorder(java.awt.Color.cyan, 1, true));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jcboSucEntra.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jcboSucEntra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboSucEntraActionPerformed(evt);
            }
        });
        jPanel5.add(jcboSucEntra, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 200, -1));

        jcboBodEntra.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jPanel5.add(jcboBodEntra, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 30, 200, -1));

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel6.setForeground(java.awt.Color.cyan);
        jLabel6.setText("Sucursal");
        jPanel5.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel7.setForeground(java.awt.Color.cyan);
        jLabel7.setText("Bodega");
        jPanel5.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, -1, -1));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 90, 430, 70));

        jPanel3.setBackground(new java.awt.Color(41, 42, 44));
        jPanel3.setBorder(new javax.swing.border.LineBorder(java.awt.Color.cyan, 1, true));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel10.setForeground(java.awt.Color.green);
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Cantidad a Trasladar");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 220, -1));

        jtxtCantidadTras.setBackground(new java.awt.Color(41, 42, 44));
        jtxtCantidadTras.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jtxtCantidadTras.setForeground(new java.awt.Color(255, 255, 255));
        jtxtCantidadTras.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxtCantidadTras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtCantidadTrasActionPerformed(evt);
            }
        });
        jPanel3.add(jtxtCantidadTras, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 170, -1));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 250, 410, 70));

        jPanel6.setBackground(new java.awt.Color(41, 42, 44));
        jPanel6.setBorder(new javax.swing.border.LineBorder(java.awt.Color.cyan, 1, true));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtxtCantiEntra.setEditable(false);
        jtxtCantiEntra.setBackground(new java.awt.Color(41, 42, 44));
        jtxtCantiEntra.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jtxtCantiEntra.setForeground(new java.awt.Color(255, 255, 255));
        jtxtCantiEntra.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxtCantiEntra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtCantiEntraActionPerformed(evt);
            }
        });
        jPanel6.add(jtxtCantiEntra, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 30, 210, -1));

        jtxtExisSale.setEditable(false);
        jtxtExisSale.setBackground(new java.awt.Color(41, 42, 44));
        jtxtExisSale.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jtxtExisSale.setForeground(new java.awt.Color(255, 255, 255));
        jtxtExisSale.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtxtExisSale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtExisSaleActionPerformed(evt);
            }
        });
        jPanel6.add(jtxtExisSale, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 200, -1));

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel11.setForeground(java.awt.Color.cyan);
        jLabel11.setText("Existencia Bodega Salida");
        jPanel6.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 230, -1));

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel12.setForeground(java.awt.Color.cyan);
        jLabel12.setText("Existencia Bodega Ingreso");
        jPanel6.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, -1, -1));

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 450, 70));

        jbtnAdd.setBackground(new java.awt.Color(0, 0, 0));
        jbtnAdd.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jbtnAdd.setForeground(new java.awt.Color(255, 255, 255));
        jbtnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/compras32.png"))); // NOI18N
        jbtnAdd.setText("Agregar");
        jbtnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAddActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 330, 200, 70));

        jPanel7.setBackground(new java.awt.Color(41, 42, 44));
        jPanel7.setBorder(new javax.swing.border.LineBorder(java.awt.Color.cyan, 1, true));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jcboSucSale.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jcboSucSale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboSucSaleActionPerformed(evt);
            }
        });
        jPanel7.add(jcboSucSale, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 200, -1));

        jcboBodSale.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jPanel7.add(jcboBodSale, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 30, 200, -1));

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel4.setForeground(java.awt.Color.cyan);
        jLabel4.setText("Bodega");
        jPanel7.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, -1, -1));

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel5.setForeground(java.awt.Color.cyan);
        jLabel5.setText("Sucursal");
        jPanel7.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 430, 70));
        jPanel1.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 880, 10));

        jPanel4.setBackground(new java.awt.Color(41, 42, 44));
        jPanel4.setBorder(new javax.swing.border.LineBorder(java.awt.Color.cyan, 1, true));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtxtObs.setBackground(new java.awt.Color(41, 42, 44));
        jtxtObs.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jtxtObs.setForeground(new java.awt.Color(255, 255, 255));
        jtxtObs.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtObsFocusLost(evt);
            }
        });
        jtxtObs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtObsActionPerformed(evt);
            }
        });
        jPanel4.add(jtxtObs, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 640, -1));

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel14.setForeground(java.awt.Color.cyan);
        jLabel14.setText("Observaciones:");
        jPanel4.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 660, 70));

        jtbTras.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jtbTras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jtbTras.setRowHeight(25);
        jScrollPane1.setViewportView(jtbTras);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 470, 870, 230));

        jPanel8.setBackground(new java.awt.Color(41, 42, 44));
        jPanel8.setBorder(new javax.swing.border.LineBorder(java.awt.Color.cyan, 1, true));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtxtCodProd.setBackground(new java.awt.Color(41, 42, 44));
        jtxtCodProd.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jtxtCodProd.setForeground(new java.awt.Color(255, 255, 255));
        jtxtCodProd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtCodProdFocusLost(evt);
            }
        });
        jPanel8.add(jtxtCodProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 100, -1));

        jtxtDesProd.setBackground(new java.awt.Color(41, 42, 44));
        jtxtDesProd.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jtxtDesProd.setForeground(new java.awt.Color(255, 255, 255));
        jPanel8.add(jtxtDesProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 740, -1));

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel15.setForeground(java.awt.Color.cyan);
        jLabel15.setText("Codigo");
        jPanel8.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel16.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel16.setForeground(java.awt.Color.cyan);
        jLabel16.setText("Producto");
        jPanel8.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, -1, -1));

        jPanel1.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 870, 70));

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
        jPanel1.add(jbtnGrabar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 870, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 902, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 708, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblSalirMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseMoved
        lblSalir.setBorder(javax.swing.BorderFactory.createLineBorder(Color.RED));
    }//GEN-LAST:event_lblSalirMouseMoved

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        jfListadoTraslado lc = new jfListadoTraslado();
        lc.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblSalirMouseClicked

    private void lblSalirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblSalirMouseEntered

    private void lblSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseExited
        lblSalir.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(41,42,44)));
    }//GEN-LAST:event_lblSalirMouseExited

    private void jtxtCantiEntraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtCantiEntraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtCantiEntraActionPerformed

    private void jtxtExisSaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtExisSaleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtExisSaleActionPerformed

    private void jcboSucEntraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcboSucEntraActionPerformed
        idex_combo combo = new idex_combo();        
                
        int idSuc = 0;
        if(jcboSucEntra.getItemAt(jcboSucEntra.getSelectedIndex()).getId() == null){
            idSuc = 0;
        }else{
            idSuc = jcboSucEntra.getItemAt(jcboSucEntra.getSelectedIndex()).getId();        
        }
        
        jcboBodEntra.removeAllItems();
        combo.llenarCombo2(jcboBodEntra, "id_bodega", "tBodega", "txt_descripcion"," and id_suc = " + "'" + idSuc + "'");
    }//GEN-LAST:event_jcboSucEntraActionPerformed

    private void jcboSucSaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcboSucSaleActionPerformed
        idex_combo combo = new idex_combo();        
                
        int idSuc = 0;
        if(jcboSucSale.getItemAt(jcboSucSale.getSelectedIndex()).getId() == null){
            idSuc = 0;
        }else{
            idSuc = jcboSucSale.getItemAt(jcboSucSale.getSelectedIndex()).getId();        
        }
        
        jcboBodSale.removeAllItems();
        combo.llenarCombo2(jcboBodSale, "id_bodega", "tBodega", "txt_descripcion"," and id_suc = " + "'" + idSuc + "'");
    }//GEN-LAST:event_jcboSucSaleActionPerformed

    private void jtxtCodProdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtCodProdFocusLost
        try {
            
            if(jcboBodSale.getItemAt(jcboBodSale.getSelectedIndex()).getId() == jcboBodEntra.getItemAt(jcboBodEntra.getSelectedIndex()).getId()){                
                JOptionPane.showMessageDialog(null, " * Las bodega de salida y bodega de ingreso no debe de ser la misma. \n","CyCo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }else{
                recupProd_cod(1);
            }                                               
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jtxtCodProdFocusLost

    private void jtxtObsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtObsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtObsActionPerformed

    private void jtxtObsFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtObsFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtObsFocusLost

    private void jbtnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAddActionPerformed
        validaciones_agregar();
    }//GEN-LAST:event_jbtnAddActionPerformed

    private void jtxtCantidadTrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtCantidadTrasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtCantidadTrasActionPerformed

    private void jbtnGrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGrabarActionPerformed
         if (this.jtbTras.getRowCount() == 0 ){ 
            JOptionPane.showMessageDialog(null,"No hay productos para realizar el traslado","CyCo",JOptionPane.INFORMATION_MESSAGE); 
            return;
        }else{
            validaciones();
        }
    }//GEN-LAST:event_jbtnGrabarActionPerformed

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
            java.util.logging.Logger.getLogger(jfTraslado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfTraslado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfTraslado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfTraslado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jfTraslado().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JButton jbtnAdd;
    private javax.swing.JButton jbtnGrabar;
    private javax.swing.JComboBox<idex_combo> jcboBodEntra;
    private javax.swing.JComboBox<idex_combo> jcboBodSale;
    private javax.swing.JComboBox<idex_combo> jcboSucEntra;
    private javax.swing.JComboBox<idex_combo> jcboSucSale;
    private javax.swing.JTable jtbTras;
    private javax.swing.JTextField jtxtCantiEntra;
    private javax.swing.JTextField jtxtCantidadTras;
    private javax.swing.JTextField jtxtCodProd;
    private javax.swing.JTextField jtxtDesProd;
    private javax.swing.JTextField jtxtExisSale;
    private javax.swing.JTextField jtxtObs;
    private javax.swing.JLabel lblSalir;
    // End of variables declaration//GEN-END:variables
}
