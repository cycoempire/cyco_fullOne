
package clases_varias;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author wwwki
 */
public class SetearCombo {
    conectar cc = new conectar();
    
    
    
    
    public int Set_marca_posicion_combo(int id_marca){
        int id_posicion = 0;
        String ssql;
        Connection cn = cc.conexion();
        
        
        try {
            ssql = "";
            ssql = ssql + " select id_marca from tmarca";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            while(rs.next()){                
                if(rs.getInt(1) == id_marca){
                            
                    return id_posicion;
                }                
                 id_posicion = id_posicion +1;   
            }                               
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);            
        }           
        return id_posicion;
    }       
    
    
    
    public int Set_modelo_posicion_combo(int id_marca, int id_modelo){
        int id_posicion = 0;
        String ssql;
        Connection cn = cc.conexion();
                
        try {
            ssql = "";
            ssql = ssql + " select id_modelo from tmodelo";
            ssql = ssql + " where id_marca = " + id_marca;
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            while(rs.next()){                                                                                
                if(rs.getInt(1) == id_modelo){                            
                    return id_posicion;                                    
                }                
                id_posicion = id_posicion +1;                                                
            }
                        
        } catch (Exception e) {            
            JOptionPane.showMessageDialog(null, e);
        }                                                                            
        return id_posicion;
    }    
    
    public int set_sucursal(int id_suc){
        int id_posicion = 0;
        String ssql;
        Connection cn = cc.conexion();
                
        try {
            ssql = "";
            ssql = ssql + " select id_sucursal from tsucursal";            
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            while(rs.next()){                                                                                
                if(rs.getInt(1) == id_suc){                            
                    return id_posicion;                                    
                }                
                id_posicion = id_posicion +1;                                                
            }
                        
        } catch (Exception e) {            
            JOptionPane.showMessageDialog(null, e);
        }                                                                            
        return id_posicion;
    }    
    
    
    
    
    public int set_tipo_doc(int id_doc){
        int id_posicion = 0;
        String ssql;
        Connection cn = cc.conexion();
                
        try {
            ssql = "";
            ssql = ssql + " select id_tipo_doc from ttipo_documento";            
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            while(rs.next()){                                                                                
                if(rs.getInt(1) == id_doc){                            
                    return id_posicion;                                    
                }                
                id_posicion = id_posicion +1;                                                
            }
                        
        } catch (Exception e) {            
            JOptionPane.showMessageDialog(null, e);
        }                                                                            
        return id_posicion;
    }    
    
    
     public int set_bodega(int id_suc ,int id_bodega){
        int id_posicion = 0;
        String ssql;
        Connection cn = cc.conexion();
                
        try {
            ssql = "";
            ssql = ssql + " select id_bodega from tbodega";            
            ssql = ssql + " where id_suc = " + id_suc; 
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            while(rs.next()){                                                                                
                if(rs.getInt(1) == id_bodega){                            
                    return id_posicion;                                    
                }                
                id_posicion = id_posicion +1;                                                
            }
                        
        } catch (Exception e) {            
            JOptionPane.showMessageDialog(null, e);
        }                                                                            
        return id_posicion;
    }    
    
    
    public int set_prove(int id_prove){
        int id_posicion = 0;
        String ssql;
        Connection cn = cc.conexion();
                
        try {
            ssql = "";
            ssql = ssql + " select id_prove from tprovedor";            
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(ssql);
            while(rs.next()){                                                                                
                if(rs.getInt(1) == id_prove){                            
                    return id_posicion;                                    
                }                
                id_posicion = id_posicion +1;                                                
            }
                        
        } catch (Exception e) {            
            JOptionPane.showMessageDialog(null, e);
        }                                                                            
        return id_posicion;
    }    
    
                    
//     public int Set_tipo_posicion_combo(int id_tipo){
//        int id_posicion = 0;
//        String ssql;
//        Connection cn = cc.conexion();
//                
//        try {
//            ssql = "";
//            ssql = ssql + " select id_tipo from ttipo";            
//            Statement st = cn.createStatement();
//            ResultSet rs = st.executeQuery(ssql);
//            while(rs.next()){                                                                                
//                if(rs.getInt(1) == id_modelo){                            
//                    return id_posicion;                                    
//                }                
//                id_posicion = id_posicion +1;                                                
//            }
//                        
//        } catch (Exception e) {            
//            JOptionPane.showMessageDialog(null, e);
//        }                                                                            
//        return id_posicion;
//    }                       
    
    
    
    
}
