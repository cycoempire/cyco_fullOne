
package clases_varias;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author wwwki
 */
public class TableCellRendererColor extends DefaultTableCellRenderer{
    private Component componente;
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        componente = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.        
        
        switch (table.getValueAt(row, 7).toString()) {
             case "INACTIVO":              
                //setForeground(Color.RED);
                 componente.setBackground(Color.DARK_GRAY);  
                 componente.setForeground(Color.WHITE);
                              
                break;
            case "ACTIVO":
                //setForeground(Color.BLUE);   
                componente.setBackground(Color.WHITE);  
                 componente.setForeground(Color.BLACK);
                break;
            default:
                break;
        
        }
        
        if(isSelected){
            componente.setBackground(Color.BLUE);  
        componente.setForeground(Color.WHITE);
        }
        
//        if(row%2 == 0){
//            componente.setBackground(Color.LIGHT_GRAY);  
//        }else{
//            componente.setBackground(Color.BLUE);  
//        }
        
        
                     
        return componente;
    }        
}
