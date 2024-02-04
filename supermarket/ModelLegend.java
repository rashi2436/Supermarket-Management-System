/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supermarket;

import java.awt.Color;

public class ModelLegend {

    String Name; 
    Color Color;  

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public Color getColor() {
        return Color;
    }

    public void setColor(Color color) {
        this.Color = color;
    }
    public ModelLegend(String name, Color color){
    this.Name = name;
    this.Color=color;
    }
    public ModelLegend(){
        
    }

 private String name; 
   private Color color; 
    

    
    
    
}
