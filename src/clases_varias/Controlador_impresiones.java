/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases_varias;

/**
 *
 * @author wwwki
 */
public class Controlador_impresiones {
    
    public void imprime_fac(int tipo_impesion, int id_factura){        
        imprimir_reportes ir = new imprimir_reportes();
        switch(tipo_impesion){            
            case 1: /*factura PRODUCTO Papa*/
                    ir.imprimir_factura(id_factura,"");
            case 2: /*factura SERVICIO Papa*/                    
                    ir.imprimir_factura(id_factura,"Factura_servicio"); 
                    break;
            case 3: /*factura PRODUCTO Jose Chang*/
                    ir.imprimir_factura(id_factura,"Factura_Jose");                                                            
                    break;
            case 4: /*factura SERVICIO Jose Chang*/                    
                    ir.imprimir_factura(id_factura,"Factura_servicio_JoseChang"); 
                    break;                        
        }
    }
}
