/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_varias;

import Formas.Menu_principal;
import Formas.jfConfigura;
import Formas.jfParametros;
import Formas.jfReportes;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;

/**
 *
 * @author wwwki
 */
public class permisos {
    conectar cc= new conectar();
    public int idUsu;
    
    
    public void cargar_permisos(int Opc){
    String ssql;
        try {
            Connection cn = cc.conexion();                                                                        
            switch (Opc){
            
                case 1: //Menu principal
                    
                    ssql = "";
                    ssql = ssql + " select * from tpermisos_menu";
                    ssql = ssql + " where id_usu = " + idUsu;
                    Statement st = cn.createStatement();
                    ResultSet rs = st.executeQuery(ssql);
                    if(rs.next()){
                        //facturacion
                        if(rs.getInt(2) == 1){                            
                            Menu_principal.lblFacturar.setEnabled(true);                            
                        }else{
                            
                            Menu_principal.lblFacturar.setEnabled(false);                                                        
                        }
                        
                        //reportes
                        if(rs.getInt(3) == 1){
                            Menu_principal.lblReportes.setEnabled(true);                            
                        }else{
                            Menu_principal.lblReportes.setEnabled(false);                            
                        }
                                  
                        //comfiguraciones
                        if(rs.getInt(4) == 1){
                            Menu_principal.lblConfigura.setEnabled(true);                                                        
                        }else{
                            Menu_principal.lblConfigura.setEnabled(false);                            
                        }                                                
                    }
                    rs.close();
                    st.close();
                    break;
                
                case 2:
                        //Reportes      
                        System.out.println("ID usu: " +idUsu);
                        ssql = "";
                        ssql = ssql + " select * from tpermisos_submenu";
                        ssql = ssql + " where id_usu = " + idUsu;
                        Statement st2 = cn.createStatement();
                        ResultSet rs2 = st2.executeQuery(ssql);
                        if(rs2.next()){
                            System.out.println("valor: " + rs2.getInt(3));
                            //reporte de ventas
                            if(rs2.getInt(2) == 1){
                                jfReportes.lblReportes.setEnabled(true);
                            }else{
                                jfReportes.lblReportes.setEnabled(false);
                            }
                            
                            //cuentas
                            if(rs2.getInt(3) == 1){
                                jfReportes.lblCuentas.setEnabled(true);
                            }else{
                                jfReportes.lblCuentas.setEnabled(false);
                            }                                                                                    
                        }                        
                        rs2.close();
                        st2.close();
                        break;
                case 3:
                    //configuraciones
                    
                        ssql = "";
                        ssql = ssql + " select * from tpermisos_submenu";
                        ssql = ssql + " where id_usu = " + idUsu;
                        Statement st3 = cn.createStatement();
                        ResultSet rs3 = st3.executeQuery(ssql);
                        if(rs3.next()){
                            //cleintes
                            if(rs3.getInt(4) == 1){
                                jfConfigura.lblCliente.setEnabled(true);
                            }else{
                                jfConfigura.lblCliente.setEnabled(false);
                            }
                            //productos
                            if(rs3.getInt(5) == 1){
                                jfConfigura.lblProducto.setEnabled(true);
                            }else{
                                jfConfigura.lblProducto.setEnabled(false);
                            }
                            //inventario
                            if(rs3.getInt(6) == 1){
                                jfConfigura.lblInventario.setEnabled(true);
                            }else{
                                jfConfigura.lblInventario.setEnabled(false);
                            }
                            //compras
                            if(rs3.getInt(7) == 1){
                                jfConfigura.lblCompras.setEnabled(true);
                            }else{
                                jfConfigura.lblCompras.setEnabled(false);
                            }
                            //proveedor
                            if(rs3.getInt(8) == 1){
                                jfConfigura.jlblProve.setEnabled(true);
                            }else{
                                jfConfigura.jlblProve.setEnabled(false);
                            }
                            //prametros
                            if(rs3.getInt(9) == 1){
                                jfConfigura.lblParametro.setEnabled(true);
                            }else{
                                jfConfigura.lblParametro.setEnabled(false);
                            }                                                    
                            
                            if(rs3.getInt(10) == 1){
                                jfConfigura.lblTraslado.setEnabled(true);
                            }else{
                                jfConfigura.lblTraslado.setEnabled(false);
                            }                                                    
                        }
                        rs3.close();
                        st3.close();
                        break;
                case 4:
                    //parametros
                    
                    ssql = "";
                    ssql = ssql + " select * from tpermisos_sublv2";
                    ssql = ssql + " where id_usu = " + idUsu;
                    Statement st4 = cn.createStatement();
                    ResultSet rs4 = st4.executeQuery(ssql);
                    if(rs4.next()){
                        //serie recibo
                        if(rs4.getInt(2) == 1){
                            jfParametros.lblSeries_recibo.setEnabled(true);
                        }else{
                            jfParametros.lblSeries_recibo.setEnabled(false);
                        }
                        //serie factura
                        if(rs4.getInt(3) == 1){
                            jfParametros.lblSeries.setEnabled(true);
                        }else{
                            jfParametros.lblSeries.setEnabled(false);
                        }
                        //sucursal
                        if(rs4.getInt(4) == 1){
                            jfParametros.lblSucursal.setEnabled(true);
                        }else{
                            jfParametros.lblSucursal.setEnabled(false);
                        }
                        //bodega
                        if(rs4.getInt(5) == 1){
                            jfParametros.lblBodega.setEnabled(true);
                        }else{
                            jfParametros.lblBodega.setEnabled(false);
                        }
                        //marca
                        if(rs4.getInt(6) == 1){
                            jfParametros.lblMarca.setEnabled(true);
                        }else{
                            jfParametros.lblMarca.setEnabled(false);
                        }
                        //modelo
                        if(rs4.getInt(7) == 1){
                            jfParametros.lblModelo.setEnabled(true);
                        }else{
                            jfParametros.lblModelo.setEnabled(false);
                        }
                        //usuario
                        if(rs4.getInt(8) == 1){
                            jfParametros.lblUser.setEnabled(true);
                        }else{
                            jfParametros.lblUser.setEnabled(false);
                        }
                        //permisos
                        if(rs4.getInt(9) == 1){
                            jfParametros.lblPermisos.setEnabled(true);
                        }else{
                            jfParametros.lblPermisos.setEnabled(false);
                        }                        
                    }
                    rs4.close();
                    st4.close();                                                                                                     
            }            
            cn.close();                                                                        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al activar botonces en: "+e, "CyCo", JOptionPane.INFORMATION_MESSAGE);
        }                
    }                
}