/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formas;


import static Formas.jfCompra.jtxtIdCompra;
import clases_varias.conectar;
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
public class jfCompra_detalle extends javax.swing.JFrame {
     conectar cc= new conectar();  
     public Integer idCompra;
     public Integer idProd;
     public String tipo;
     public String marca;
     public String modelo;
     public Integer idPrecio;
     public Integer iEstado_compra;
     public Integer iTipo_doc;
     public Integer idSucu;
     public Integer idBodega;     
    /**
     * Creates new form jfCompra_detalle
     */
    public jfCompra_detalle() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon(getClass().getResource("/Iconos/CyCo64.png")).getImage());
         //La tabla no se autoresizable
        jtbDet.setAutoResizeMode(jtbDet.AUTO_RESIZE_OFF); 
        Cargar_tabla();
    }
       
    
    void Cargar_tabla(){
    
        try {
                        
            DefaultTableModel prd = new DefaultTableModel();
            prd.addColumn("id_compra");
            prd.addColumn("id_podructo");
            prd.addColumn("CODIGO");
            prd.addColumn("PRODUCTO");
            prd.addColumn("TIPO");
            prd.addColumn("MARCA");
            prd.addColumn("MODELO");
            prd.addColumn("CANTIDAD");
            prd.addColumn("id_precio");
            prd.addColumn("P. COSTO");
            prd.addColumn("P. VENTA");
            prd.addColumn("SUB-TOTAL COSTO");
            prd.addColumn("SUB-TOTAL VENTA");
            jtbDet.setModel(prd);
    
    
            //ID_compra
            jtbDet.getColumnModel().getColumn(0).setMaxWidth(0);
            jtbDet.getColumnModel().getColumn(0).setMinWidth(0);
            jtbDet.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
            jtbDet.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
            
             //ID_producto
            jtbDet.getColumnModel().getColumn(1).setMaxWidth(0);
            jtbDet.getColumnModel().getColumn(1).setMinWidth(0);
            jtbDet.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(0);
            jtbDet.getTableHeader().getColumnModel().getColumn(1).setMinWidth(0);
            
             //cod prod
            jtbDet.getColumnModel().getColumn(2).setMaxWidth(150);
            jtbDet.getColumnModel().getColumn(2).setMinWidth(150);
            jtbDet.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(150);
            jtbDet.getTableHeader().getColumnModel().getColumn(2).setMinWidth(150);
            
             //producto
            jtbDet.getColumnModel().getColumn(3).setMaxWidth(350);
            jtbDet.getColumnModel().getColumn(3).setMinWidth(350);
            jtbDet.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(350);
            jtbDet.getTableHeader().getColumnModel().getColumn(3).setMinWidth(350);
            
             //TIPO
            jtbDet.getColumnModel().getColumn(4).setMaxWidth(250);
            jtbDet.getColumnModel().getColumn(4).setMinWidth(250);
            jtbDet.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(250);
            jtbDet.getTableHeader().getColumnModel().getColumn(4).setMinWidth(250);
            
            
             //MARCA
            jtbDet.getColumnModel().getColumn(5).setMaxWidth(250);
            jtbDet.getColumnModel().getColumn(5).setMinWidth(250);
            jtbDet.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(250);
            jtbDet.getTableHeader().getColumnModel().getColumn(5).setMinWidth(250);
            
            
             //MODELO
            jtbDet.getColumnModel().getColumn(6).setMaxWidth(250);
            jtbDet.getColumnModel().getColumn(6).setMinWidth(250);
            jtbDet.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(250);
            jtbDet.getTableHeader().getColumnModel().getColumn(6).setMinWidth(250);
           
             //cantidad
            jtbDet.getColumnModel().getColumn(7).setMaxWidth(150);
            jtbDet.getColumnModel().getColumn(7).setMinWidth(150);
            jtbDet.getTableHeader().getColumnModel().getColumn(7).setMaxWidth(150);
            jtbDet.getTableHeader().getColumnModel().getColumn(7).setMinWidth(150);
            
             //ID_precio
            jtbDet.getColumnModel().getColumn(8).setMaxWidth(0);
            jtbDet.getColumnModel().getColumn(8).setMinWidth(0);
            jtbDet.getTableHeader().getColumnModel().getColumn(8).setMaxWidth(0);
            jtbDet.getTableHeader().getColumnModel().getColumn(8).setMinWidth(0);
            
             //costo
            jtbDet.getColumnModel().getColumn(9).setMaxWidth(150);
            jtbDet.getColumnModel().getColumn(9).setMinWidth(150);
            jtbDet.getTableHeader().getColumnModel().getColumn(9).setMaxWidth(150);
            jtbDet.getTableHeader().getColumnModel().getColumn(9).setMinWidth(150);
            
             //venta
            jtbDet.getColumnModel().getColumn(10).setMaxWidth(150);
            jtbDet.getColumnModel().getColumn(10).setMinWidth(150);
            jtbDet.getTableHeader().getColumnModel().getColumn(10).setMaxWidth(150);
            jtbDet.getTableHeader().getColumnModel().getColumn(10).setMinWidth(150);
            
            //SUB TOTAL COSTO
            jtbDet.getColumnModel().getColumn(11).setMaxWidth(170);
            jtbDet.getColumnModel().getColumn(11).setMinWidth(170);
            jtbDet.getTableHeader().getColumnModel().getColumn(11).setMaxWidth(170);
            jtbDet.getTableHeader().getColumnModel().getColumn(11).setMinWidth(170);
            
            
            //SUB TOTAL VENTA
            jtbDet.getColumnModel().getColumn(12).setMaxWidth(170);
            jtbDet.getColumnModel().getColumn(12).setMinWidth(170);
            jtbDet.getTableHeader().getColumnModel().getColumn(12).setMaxWidth(170);
            jtbDet.getTableHeader().getColumnModel().getColumn(12).setMinWidth(170);
            
            
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
                    ssql = ssql + " select tprod.id_producto, tprod.cod_prod, tprod.txt_descripcion, tipo.txt_descripcion as tipo,";
                    ssql = ssql + " tmar.txt_descripcion_marca,";
                    ssql = ssql + " tmod.txt_descripcion_modelo,";
                    ssql = ssql + " tp.id_precio,";
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
                    ssql = ssql + " and tprod.txt_codigo = '" + jtxtCodPod.getText() + "'";
                    Statement st = cn.createStatement();
                    ResultSet rs = st.executeQuery(ssql);
                    if(rs.next()){
                        idProd = rs.getInt(1);                        
                        jtxtNomProd.setText(rs.getString(3));                        
                        jtxtCosto.setText(rs.getString(8));
                        jtxtVenta.setText(rs.getString(9));   
                        tipo = rs.getString(4);
                        marca = rs.getString(5);
                        modelo = rs.getString(6);
                        idPrecio = rs.getInt(7);
                    }                                        
                    rs.close();
                    st.close();                    
                    break;
                                                                                                                                            
                case 2:
                    ssql = "";         
                    ssql = ssql + " select tprod.id_producto, tprod.txt_codigo, tprod.txt_descripcion, tipo.txt_descripcion as tipo,";
                    ssql = ssql + " tmar.txt_descripcion_marca,";
                    ssql = ssql + " tmod.txt_descripcion_modelo,";
                    ssql = ssql + " tp.id_precio,";
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
                    ssql = ssql + " and tprod.txt_descripcion = '" + jtxtNomProd.getText() + "'";
                                                                        
                    Statement stt = cn.createStatement();
                    ResultSet rss = stt.executeQuery(ssql);
                    if(rss.next()){
                        idProd = rss.getInt(1);                        
                        jtxtCodPod.setText(rss.getString(2));                        
                        jtxtCosto.setText(rss.getString(8));
                        jtxtVenta.setText(rss.getString(9));   
                        tipo = rss.getString(4);
                        marca = rss.getString(5);
                        modelo = rss.getString(6);
                        idPrecio = rss.getInt(7);
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
                
        
     void agregarProd(){
        try {       
            
            String dSubtotal_costo="";
            String dSubtotal_venta="";
            
            
            DecimalFormat df = new DecimalFormat("#.00");                
            dSubtotal_costo = df.format(Double.parseDouble(jtxtCanti.getText()) * Double.parseDouble(jtxtCosto.getText()));
            dSubtotal_venta = df.format(Double.parseDouble(jtxtCanti.getText()) * Double.parseDouble(jtxtVenta.getText()));
            
            
            DefaultTableModel prd = (DefaultTableModel) jtbDet.getModel();                
            String datos[] = new String[13];
            datos[0] = "1";
            datos[1] = idProd.toString();
            datos[2] = jtxtCodPod.getText();
            datos[3] = jtxtNomProd.getText();
            datos[4] = tipo;
            datos[5] = marca;
            datos[6] = modelo;
            datos[7] = jtxtCanti.getText();
            datos[8] = idPrecio.toString();
            datos[9] = jtxtCosto.getText();
            datos[10] = jtxtVenta.getText();
            datos[11] = dSubtotal_costo;     
            datos[12] = dSubtotal_venta;                 
            prd.addRow(datos);
            jtbDet.setModel(prd);                                                                       
      
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en: " + e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    
    void grabar_detalle_compra(){
        String ssql = "";
        try {
            
             Connection cn= cc.conexion();                  
             CallableStatement ccc = cn.prepareCall(" delete from tcompra_detalle where id_compra = " + idCompra);
             ccc.execute();      
             ccc.close();
             for(int i = 0; i < jtbDet.getRowCount(); i++){
                 CallableStatement cst = cn.prepareCall("{ call sp_grabar_detalle_compra(?,?,?,?,?)}");
                 cst.setInt(1, idCompra); //id compra
                 cst.setInt(2, Integer.parseInt(String.valueOf(jtbDet.getValueAt(i,1)))); // id prod
                 cst.setInt(3, Integer.parseInt(String.valueOf(jtbDet.getValueAt(i,8)))); //id precio
                 cst.setInt(4, 1); //sn activo
                 cst.setDouble(5, Double.parseDouble(String.valueOf(jtbDet.getValueAt(i,7)))); //cantidad                 
                 cst.execute();
                 cst.close();
             }
             JOptionPane.showMessageDialog(null, "¡Detalle de compra grabada con exito!", "CyCo", JOptionPane.INFORMATION_MESSAGE);                                                   
             if(iEstado_compra == 1){
                 //La compra se realiza solo si el estado de la compra es activo, de lo contrario quedara como pendiente 
                 //Si la compra ya fue ingresada como activa, el detalle solo se podra consultar.
                 //Si la compra fue anulada, el detalle solo se podra consultar y automaticamente se descontara del inventario                 
                 //se graba el inventario
                 grabar_inventario();             
             }else{
                 Cargar_tabla(); //limpiamos la tabla             
             }                                                 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "¡Error al grabar detalle de la compra en: "+e, "CyCo", JOptionPane.INFORMATION_MESSAGE);            
        }        
    }
        
    void grabar_inventario(){    
     try {            
             Iterator<user_logged> itrLogg = Menu_principal.user_memory.iterator();
             user_logged ul = itrLogg.next();      
             Connection cn= cc.conexion();             
             for(int i = 0; i < jtbDet.getRowCount(); i++){
                 CallableStatement cst = cn.prepareCall("{ call sp_grabar_inventario(?,?,?,?,?,?,?,?,?)}");
                 cst.setInt(1, Integer.parseInt(String.valueOf(jtbDet.getValueAt(i,1))));               //id prod
                 cst.setInt(2, 1);                                                                      //id tipo mov
                 cst.setDouble(3, Double.parseDouble(String.valueOf(jtbDet.getValueAt(i,7))));          //cantidad
                 cst.setString(4, "ENTRADA POR COMPRA");                                                //observacione                 
                 cst.setInt(5, iTipo_doc);                                                              //id tipo doc
                 cst.setInt(6, idCompra);                                                               //id documento
                 cst.setInt(7, idSucu);                                                                 //id sucu
                 cst.setInt(8, idBodega);                                                               //id bodega
                 cst.setInt(9, ul.getidUsy());                                                           //id usu
                 cst.execute();         
                 cst.close();
             }
             JOptionPane.showMessageDialog(null, "¡Detalle de compra grabada con exito!", "CyCo", JOptionPane.INFORMATION_MESSAGE);            
             Cargar_tabla(); //limpiamos la tabla
             cn.close();
                                                                 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "¡Error al grabar detalle de la compra en: "+e, "CyCo", JOptionPane.INFORMATION_MESSAGE);            
        }                    
    }
    
    
    public void recup_detalle_compra(){
        String ssql;
        try {
            Connection cn= cc.conexion();                
            DefaultTableModel prd = new DefaultTableModel();
            prd.addColumn("id_compra");
            prd.addColumn("id_podructo");
            prd.addColumn("CODIGO");
            prd.addColumn("PRODUCTO");
            prd.addColumn("TIPO");
            prd.addColumn("MARCA");
            prd.addColumn("MODELO");
            prd.addColumn("CANTIDAD");
            prd.addColumn("id_precio");
            prd.addColumn("P. COSTO");
            prd.addColumn("P. VENTA");
            prd.addColumn("SUB-TOTAL COSTO");
            prd.addColumn("SUB-TOTAL VENTA");
            jtbDet.setModel(prd);
    
    
            //ID_compra
            jtbDet.getColumnModel().getColumn(0).setMaxWidth(0);
            jtbDet.getColumnModel().getColumn(0).setMinWidth(0);
            jtbDet.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
            jtbDet.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
            
             //ID_producto
            jtbDet.getColumnModel().getColumn(1).setMaxWidth(0);
            jtbDet.getColumnModel().getColumn(1).setMinWidth(0);
            jtbDet.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(0);
            jtbDet.getTableHeader().getColumnModel().getColumn(1).setMinWidth(0);
            
             //cod prod
            jtbDet.getColumnModel().getColumn(2).setMaxWidth(150);
            jtbDet.getColumnModel().getColumn(2).setMinWidth(150);
            jtbDet.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(150);
            jtbDet.getTableHeader().getColumnModel().getColumn(2).setMinWidth(150);
            
             //producto
            jtbDet.getColumnModel().getColumn(3).setMaxWidth(350);
            jtbDet.getColumnModel().getColumn(3).setMinWidth(350);
            jtbDet.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(350);
            jtbDet.getTableHeader().getColumnModel().getColumn(3).setMinWidth(350);
            
             //TIPO
            jtbDet.getColumnModel().getColumn(4).setMaxWidth(250);
            jtbDet.getColumnModel().getColumn(4).setMinWidth(250);
            jtbDet.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(250);
            jtbDet.getTableHeader().getColumnModel().getColumn(4).setMinWidth(250);
            
            
             //MARCA
            jtbDet.getColumnModel().getColumn(5).setMaxWidth(250);
            jtbDet.getColumnModel().getColumn(5).setMinWidth(250);
            jtbDet.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(250);
            jtbDet.getTableHeader().getColumnModel().getColumn(5).setMinWidth(250);
            
            
             //MODELO
            jtbDet.getColumnModel().getColumn(6).setMaxWidth(250);
            jtbDet.getColumnModel().getColumn(6).setMinWidth(250);
            jtbDet.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(250);
            jtbDet.getTableHeader().getColumnModel().getColumn(6).setMinWidth(250);
           
             //cantidad
            jtbDet.getColumnModel().getColumn(7).setMaxWidth(150);
            jtbDet.getColumnModel().getColumn(7).setMinWidth(150);
            jtbDet.getTableHeader().getColumnModel().getColumn(7).setMaxWidth(150);
            jtbDet.getTableHeader().getColumnModel().getColumn(7).setMinWidth(150);
            
             //ID_precio
            jtbDet.getColumnModel().getColumn(8).setMaxWidth(0);
            jtbDet.getColumnModel().getColumn(8).setMinWidth(0);
            jtbDet.getTableHeader().getColumnModel().getColumn(8).setMaxWidth(0);
            jtbDet.getTableHeader().getColumnModel().getColumn(8).setMinWidth(0);
            
             //costo
            jtbDet.getColumnModel().getColumn(9).setMaxWidth(150);
            jtbDet.getColumnModel().getColumn(9).setMinWidth(150);
            jtbDet.getTableHeader().getColumnModel().getColumn(9).setMaxWidth(150);
            jtbDet.getTableHeader().getColumnModel().getColumn(9).setMinWidth(150);
            
             //venta
            jtbDet.getColumnModel().getColumn(10).setMaxWidth(150);
            jtbDet.getColumnModel().getColumn(10).setMinWidth(150);
            jtbDet.getTableHeader().getColumnModel().getColumn(10).setMaxWidth(150);
            jtbDet.getTableHeader().getColumnModel().getColumn(10).setMinWidth(150);
            
            //SUB TOTAL COSTO
            jtbDet.getColumnModel().getColumn(11).setMaxWidth(170);
            jtbDet.getColumnModel().getColumn(11).setMinWidth(170);
            jtbDet.getTableHeader().getColumnModel().getColumn(11).setMaxWidth(170);
            jtbDet.getTableHeader().getColumnModel().getColumn(11).setMinWidth(170);
            
            
            //SUB TOTAL VENTA
            jtbDet.getColumnModel().getColumn(12).setMaxWidth(170);
            jtbDet.getColumnModel().getColumn(12).setMinWidth(170);
            jtbDet.getTableHeader().getColumnModel().getColumn(12).setMaxWidth(170);
            jtbDet.getTableHeader().getColumnModel().getColumn(12).setMinWidth(170);
            
            
            
            ssql = "";
            ssql = ssql + " select tcd.id_compra, tcd.id_producto, tp.txt_codigo, tp.txt_descripcion, tpo.txt_descripcion, tmar.txt_descripcion_marca,";
            ssql = ssql + " tmod.txt_descripcion_modelo, tcd.cantidad, tcd.id_precio, tpre.dCosto, tpre.dPventa,";
            ssql = ssql + " ROUND((tcd.cantidad*tpre.dCosto),2) as subtotal_costo, ROUND((tcd.cantidad*tpre.dPventa),2) as subtotal_venta";
            ssql = ssql + " from tcompra_detalle tcd";
            ssql = ssql + " inner join tproducto tp on tp.id_producto = tcd.id_producto";
            ssql = ssql + " inner join ttipo tpo on tpo.id_tipo = tp.id_tipo";
            ssql = ssql + " inner join tmarca tmar on tmar.id_marca = tp.id_marca";
            ssql = ssql + " inner join tmodelo tmod on tmod.id_modelo = tp.id_modelo";
            ssql = ssql + " inner join tprecio tpre on tpre.id_precio = tcd.id_precio";
            ssql= ssql + " where tcd.id_compra = " + idCompra;
            String datos[] = new String[13];                                                                         
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
                datos[9] = rs.getString(10);
                datos[10] = rs.getString(11);
                datos[11] = rs.getString(12);
                datos[12] = rs.getString(13);
                prd.addRow(datos);                
            }
            jtbDet.setModel(prd);
            rs.close();
            st.close();
            cn.close();            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al recuperar detalle en: " + e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
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
        jPanel8 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jtxtCanti = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jtxtCosto = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jtxtVenta = new javax.swing.JTextField();
        jbtnAdd = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbDet = new javax.swing.JTable(){

            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jPanel9 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jtxtCodPod = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jtxtNomProd = new javax.swing.JTextField();
        jSeparator6 = new javax.swing.JSeparator();
        jbtGrabar = new javax.swing.JButton();
        jbtnRegProd = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(41, 42, 44));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Corbel", 1, 36)); // NOI18N
        jLabel1.setForeground(java.awt.Color.gray);
        jLabel1.setText("Detalle de la Compra");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 350, 40));
        jPanel1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 540, 720, 10));

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
        jPanel1.add(lblSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 10, 40, 40));

        jPanel8.setBackground(new java.awt.Color(41, 42, 44));
        jPanel8.setBorder(new javax.swing.border.LineBorder(java.awt.Color.cyan, 1, true));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel8.setForeground(java.awt.Color.cyan);
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Cantidad:");
        jPanel8.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 150, 20));

        jtxtCanti.setBackground(new java.awt.Color(41, 42, 44));
        jtxtCanti.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jtxtCanti.setForeground(new java.awt.Color(255, 255, 255));
        jtxtCanti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtCantiActionPerformed(evt);
            }
        });
        jPanel8.add(jtxtCanti, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 35, 150, -1));

        jLabel9.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel9.setForeground(java.awt.Color.cyan);
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Precio Costo:");
        jPanel8.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 160, 20));

        jtxtCosto.setBackground(new java.awt.Color(41, 42, 44));
        jtxtCosto.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jtxtCosto.setForeground(new java.awt.Color(255, 255, 255));
        jtxtCosto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtCostoActionPerformed(evt);
            }
        });
        jPanel8.add(jtxtCosto, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 35, 160, -1));

        jLabel12.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel12.setForeground(java.awt.Color.cyan);
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("Precio Venta:");
        jPanel8.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 160, 20));

        jtxtVenta.setBackground(new java.awt.Color(41, 42, 44));
        jtxtVenta.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jtxtVenta.setForeground(new java.awt.Color(255, 255, 255));
        jtxtVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtVentaActionPerformed(evt);
            }
        });
        jPanel8.add(jtxtVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 35, 180, -1));

        jbtnAdd.setBackground(new java.awt.Color(0, 0, 0));
        jbtnAdd.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jbtnAdd.setForeground(new java.awt.Color(255, 255, 255));
        jbtnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/check32.png"))); // NOI18N
        jbtnAdd.setText("Agregar");
        jbtnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAddActionPerformed(evt);
            }
        });
        jPanel8.add(jbtnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 10, 160, 60));

        jPanel1.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 710, 80));

        jtbDet.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jtbDet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jtbDet.setRowHeight(25);
        jScrollPane1.setViewportView(jtbDet);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 710, 280));

        jPanel9.setBackground(new java.awt.Color(41, 42, 44));
        jPanel9.setBorder(new javax.swing.border.LineBorder(java.awt.Color.cyan, 1, true));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel10.setForeground(java.awt.Color.cyan);
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("Codigo:");
        jPanel9.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 150, 20));

        jtxtCodPod.setBackground(new java.awt.Color(41, 42, 44));
        jtxtCodPod.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jtxtCodPod.setForeground(new java.awt.Color(255, 255, 255));
        jtxtCodPod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtCodPodFocusLost(evt);
            }
        });
        jtxtCodPod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtCodPodActionPerformed(evt);
            }
        });
        jPanel9.add(jtxtCodPod, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 35, 150, -1));

        jLabel11.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        jLabel11.setForeground(java.awt.Color.cyan);
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("Nombre Producto:");
        jPanel9.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 270, 20));

        jtxtNomProd.setBackground(new java.awt.Color(41, 42, 44));
        jtxtNomProd.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jtxtNomProd.setForeground(new java.awt.Color(255, 255, 255));
        jtxtNomProd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtNomProdFocusLost(evt);
            }
        });
        jtxtNomProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtNomProdActionPerformed(evt);
            }
        });
        jPanel9.add(jtxtNomProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 35, 530, -1));

        jPanel1.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 710, 80));
        jPanel1.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 720, 20));

        jbtGrabar.setBackground(new java.awt.Color(0, 0, 0));
        jbtGrabar.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jbtGrabar.setForeground(new java.awt.Color(255, 255, 255));
        jbtGrabar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/guardar32_1.png"))); // NOI18N
        jbtGrabar.setText("Grabar");
        jbtGrabar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtGrabarActionPerformed(evt);
            }
        });
        jPanel1.add(jbtGrabar, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 550, 330, 50));

        jbtnRegProd.setBackground(new java.awt.Color(0, 0, 0));
        jbtnRegProd.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jbtnRegProd.setForeground(new java.awt.Color(255, 255, 255));
        jbtnRegProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/productos32.png"))); // NOI18N
        jbtnRegProd.setText("Registrar Nuevo Producto");
        jbtnRegProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnRegProdActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnRegProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 550, 300, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 610));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblSalirMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseMoved
        lblSalir.setBorder(javax.swing.BorderFactory.createLineBorder(Color.RED));
    }//GEN-LAST:event_lblSalirMouseMoved

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
//        jfRVentas rv = new jfRVentas();
//        rv.buscar(1);        
        jfCompra com = new jfCompra();
        com.jtxtIdCompra.setText(idCompra.toString());
        com.recuperar_compra();
        com.iProceso_compra = 2;
        com.setVisible(true);                
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

    private void jtxtCostoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtCostoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtCostoActionPerformed

    private void jtxtCodPodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtCodPodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtCodPodActionPerformed

    private void jtxtNomProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtNomProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtNomProdActionPerformed

    private void jtxtVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtVentaActionPerformed

    private void jtxtCodPodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtCodPodFocusLost
        recupProd_cod(1);
    }//GEN-LAST:event_jtxtCodPodFocusLost

    private void jtxtNomProdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtNomProdFocusLost
        recupProd_cod(2);
    }//GEN-LAST:event_jtxtNomProdFocusLost

    private void jbtnRegProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRegProdActionPerformed
        jfProductos pr = new jfProductos();        
        pr.iProceso_prod = 1;
        pr.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jbtnRegProdActionPerformed

    private void jbtnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAddActionPerformed
        agregarProd();
    }//GEN-LAST:event_jbtnAddActionPerformed

    private void jbtGrabarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtGrabarActionPerformed
        grabar_detalle_compra();
    }//GEN-LAST:event_jbtGrabarActionPerformed

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
            java.util.logging.Logger.getLogger(jfCompra_detalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jfCompra_detalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jfCompra_detalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jfCompra_detalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jfCompra_detalle().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    public static javax.swing.JButton jbtGrabar;
    public static javax.swing.JButton jbtnAdd;
    private javax.swing.JButton jbtnRegProd;
    private javax.swing.JTable jtbDet;
    private javax.swing.JTextField jtxtCanti;
    private javax.swing.JTextField jtxtCodPod;
    private javax.swing.JTextField jtxtCosto;
    private javax.swing.JTextField jtxtNomProd;
    private javax.swing.JTextField jtxtVenta;
    private javax.swing.JLabel lblSalir;
    // End of variables declaration//GEN-END:variables
}
