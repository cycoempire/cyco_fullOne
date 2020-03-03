/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_varias;

import java.awt.HeadlessException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author efra
 */
public class varias_funciones {
    conectar cc= new conectar();

    public int recup_ult_numero(String sTabla,String sCampoId){
        String ssql;
        Integer ult_no = 0;
        
        try {
            Connection cn= cc.conexion(); 
            ssql = "";
            ssql = ssql + " select max("+ sCampoId +") as ult_no ";
            ssql = ssql + " from "+ sTabla;
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            if(rs.next()){
                ult_no = rs.getInt("ult_no");
            }else{
                ult_no = 0;
                        
            }                     
            if (ult_no == 0){
                ult_no = 1;
            }else{
               ult_no = ult_no + 1;
            }
            rs.close();
            st.close();
            cn.close();
            
        } catch (Exception e) {
        }
    
    
    return ult_no;
    }
    
    
    public String formatoFecha(String sFecha){
        String nFecha = null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyymmdd" );
        

        try {

            java.util.Date date = formatter.parse(sFecha);
            //System.out.println(date);
            //System.out.println(formatter.format(date));
            //System.out.println("New Format :   " + targetFormat.format(date));
            nFecha = targetFormat.format(date);
            
        } catch (ParseException e) {
            e.printStackTrace();
        }
    
    
    return nFecha;
    }
    
    public String formatoFechaTipo2(String sFecha){
    String nFecha = null;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
    SimpleDateFormat targetFormat = new SimpleDateFormat("yyyymmdd" );
        

        try {

            java.util.Date date = formatter.parse(sFecha);
            //System.out.println(date);
            //System.out.println(formatter.format(date));
            //System.out.println("New Format :   " + targetFormat.format(date));
            nFecha = targetFormat.format(date);
            
        } catch (ParseException e) {
            e.printStackTrace();
        }
    
    
    return nFecha;
    }
    
    public String formatoFechaTipo3(String sFecha){
    String nFecha = null;
    SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy");
    SimpleDateFormat targetFormat = new SimpleDateFormat("yyyymmdd" );
        

        try {

            java.util.Date date = formatter.parse(sFecha);
            //System.out.println(date);
            //System.out.println(formatter.format(date));
            //System.out.println("New Format :   " + targetFormat.format(date));
            nFecha = targetFormat.format(date);
            
        } catch (ParseException e) {
            e.printStackTrace();
        }
    
    
    return nFecha;
    }
    
    
    
                        
    
    public String MostrarformatoFecha(String sFecha){
    String nFecha = null;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
    SimpleDateFormat targetFormat = new SimpleDateFormat("dd-mm-yyyy" );
        

        try {

            java.util.Date date = formatter.parse(sFecha);
            //System.out.println(date);
            //System.out.println(formatter.format(date));
            //System.out.println("New Format :   " + targetFormat.format(date));
            nFecha = targetFormat.format(date);
            
        } catch (ParseException e) {
            e.printStackTrace();
        }
    
    
    return nFecha;
    }
   
    
    public String MostrarformatoFecha2(String sFecha){
    String nFecha = null;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
    SimpleDateFormat targetFormat = new SimpleDateFormat("dd/mm/yyyy" );
        

        try {

            java.util.Date date = formatter.parse(sFecha);
            //System.out.println(date);
            //System.out.println(formatter.format(date));
            //System.out.println("New Format :   " + targetFormat.format(date));
            nFecha = targetFormat.format(date);
            
        } catch (ParseException e) {
            e.printStackTrace();
        }
    
    
    return nFecha;
    }
    
    
    public String recupFechaActual(){
    String ssql;
    String fechaAct = null;
        try {
            Connection cn= cc.conexion(); 
            ssql = "";
            ssql = ssql + "select curdate()";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            if(rs.next()){
                fechaAct = rs.getString(1);
            }
            rs.close();
            st.close();
            cn.close();
        } catch (Exception e) {
        }
        
        return fechaAct;
    }
    
    
    
    public void setSelectedValue(JComboBox comboBox, Integer value)
    {
        idex_combo item;
        for (int i = 0; i < comboBox.getItemCount(); i++)
        {
            item = (idex_combo)comboBox.getItemAt(i);
            if (item.getId()== value)
            {
                comboBox.setSelectedIndex(i);
                break;
            }
        }
    }
    
    public double recupImpuesto(Double dTotal){    
    Integer iPorc;
    Double iDecimal = null;
    Double dDiv = null;
    String ssql;
    Double dCosto;
    Double dIva = null;
    
        try {
            Connection cn= cc.conexion(); 
            ssql = "";
            ssql = ssql + " select porcentaje,porc_decimales,porc_divisioon";
            ssql = ssql + " from timpuesto";
            ssql = ssql + " where sn_activo = 1";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            if(rs.next()){
                iPorc = rs.getInt(1);
                iDecimal = rs.getDouble(2);
                dDiv = rs.getDouble(3);                
            }            
            dCosto = dTotal / dDiv;
            dIva = dCosto * iDecimal;     
            rs.close();
            st.close();
            cn.close();
        } catch (Exception e) {
        }        
    return dIva;
    }
    
    
    public String recupEmpresa(){
    String sNomEmp = null;
    String ssql;
        try {
            Connection cn= cc.conexion(); 
            ssql = "";
            ssql = ssql + "select txt_nombre_empresa from tempresa where sn_activo = 1";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            if (rs.next()){
                sNomEmp = rs.getString(1);
            }            
            rs.close();
            st.close();
            cn.close();            
        } catch (Exception e) {
        }
        return sNomEmp;
    }                
    
    
    public int recupId(String sTabla, String sID, String sWhereID, String sTexfield){
        Integer id_tabla=0;
        String ssql;
        try {
            Connection cn= cc.conexion(); 
            ssql = "";
            ssql = ssql + " select "+ sID +" as id ";
            ssql = ssql + " from "+ sTabla;
            ssql = ssql + " where "+ sWhereID + " = " + "'" + sTexfield + "'";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            if(rs.next()){
                id_tabla = rs.getInt("id");
            }else{
                id_tabla = 0;                        
            }
            rs.close();
            st.close();
            cn.close();
        } catch (Exception e) {
        }                
        return id_tabla;        
    }       
    
    
    public boolean valida_exitenciaProd(Integer idSuc, Integer idBodega, Integer idProd, Double dCantidad){
        String ssql;
        boolean existe = false;
        try {
            Connection cn= cc.conexion(); 
            ssql = "";
            ssql = ssql + " select sum(ti.cantidad)";
            ssql = ssql + " from tinventario ti";
            ssql = ssql + " where ti.id_sucursal = "+ idSuc;
            ssql = ssql + " and ti.id_bodega = " + idBodega;
            ssql = ssql + " and ti.id_producto = " + idProd;
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            if(rs.next()){                                     
                if(rs.getString(1) == null){
                    JOptionPane.showMessageDialog(null, "El producto no cuenta con existencia registrada", "CyCo", JOptionPane.INFORMATION_MESSAGE);
                    return false;
                }                                
                
                if(dCantidad > Double.parseDouble(rs.getString(1))){
                    JOptionPane.showMessageDialog(null, "No existe suficiente existenicia en el inventario de venta para este producto", "CyCo", JOptionPane.INFORMATION_MESSAGE);
                    existe = false;
                }else{
                    existe = true;
                }
                
            }else{
                JOptionPane.showMessageDialog(null, "El producto no cuenta con existencia registrada", "CyCo", JOptionPane.INFORMATION_MESSAGE);
            }           
                                               
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, e);
        }                
        return existe;        
    }
    
    
    
}






