import com.sun.imageio.plugins.common.I18N;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Dibujar extends Canvas{

    int tamPart, totalProc,totalM,ban=0,posy,poscy,procs[];
    private Particiones parts[];
    private String datos;
    JFrame ventana= new JFrame("Formulario");

    public Dibujar(){
        setBackground(Color.WHITE);
    }

    public void dibCad(String cad,int posx, int posy,int tam, Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.black);
        g2.setFont(new Font("Arial", Font.ITALIC, tam));
        g2.drawString(cad, posx, posy);
    }

    public boolean compara(){
        boolean mayor=false;
        int suma=0;
        for(int i=0;i<tamPart;i++){
            suma=suma+parts[i].tamano();
        }
        if(suma>totalM){
            mayor=true;
        }
        return mayor;
    }

    public void particionar(int x,int y){
        if(x>10){
            ban=1;
            JOptionPane.showMessageDialog(ventana,
                    "Excedio el limite de particiones",
                    "ERROR",JOptionPane.ERROR_MESSAGE);
        }else{
            ban=0;
            tamPart=x+1;
            totalM=y;
            int suma=y;
            parts=new Particiones[x+1];
            try{
                for(int i=0;i<x+1;i++){
                    if (i==0){
                        datos=JOptionPane.showInputDialog("Ingrese valor a "
                                + "la particion del sistema operativo, la memoria libre es: "+ suma);
                        parts[i]=new Particiones(Integer.parseInt(datos));
                        parts[i].setOcupado(true);
                    }else {
                        datos = JOptionPane.showInputDialog("Ingrese valor a "
                                + "la particion " + (i) +"Memoria Restante: "+suma);
                        parts[i] = new Particiones(Integer.parseInt(datos));
                    }
                    if(parts[i].tamano()<0){
                        i--;
                        JOptionPane.showMessageDialog(ventana,
                                "Ingrese numeros positivos",
                                "ERROR",JOptionPane.ERROR_MESSAGE);
                    }else{
                        suma=suma-parts[i].tamano();
                    }
                }
                if(!compara()){
                    repaint();
                }else{
                    ban=1;
                    JOptionPane.showMessageDialog(ventana,
                            "Excedio el limite de memoria",
                            "ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }catch(Exception e){
                e.printStackTrace();
                ban=1;
                JOptionPane.showMessageDialog(ventana,
                        "Solo se aceptan numeros ",
                        "ERROR",JOptionPane.ERROR_MESSAGE);
            };
        }
    }

    public void agregarProc(int x){
        totalProc=x;
        if(totalProc>10){
            ban=1;
            JOptionPane.showMessageDialog(ventana,
                    "Excedio el limite de procesos",
                    "ERROR",JOptionPane.ERROR_MESSAGE);
        }else{
            procs=new int[totalProc];
            try{
                for(int i=0;i<totalProc;i++){
                    datos=JOptionPane.showInputDialog("Ingrese valor al "
                            + "proceso "+(i+1));
                    procs[i]=Integer.parseInt(datos);
                    if(procs[i]<0){
                        i--;
                        JOptionPane.showMessageDialog(ventana,
                                "Ingrese numeros positivos",
                                "ERROR",JOptionPane.ERROR_MESSAGE);
                    }
                }
                ban=2;
                repaint();
            }catch(Exception e){
                ban=1;
                JOptionPane.showMessageDialog(ventana,
                        "Solo se aceptan numeros ",
                        "ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    void desocupar(){
        for(int i=0;i<tamPart;i++){
            parts[i].setOcupado(false);
        }
    }

    void PrimerAjuste(Graphics g){
        int py,p2y,j,ban,fi;
        g.setColor(Color.RED);
        desocupar();
        for(int i=0;i<totalProc;i++){
            j=1;
            ban=0;
            py=450;
            p2y=480;
            while(j<tamPart&&ban==0){
                if(!parts[j].isOcupado()){
                    if(procs[i]<=parts[j].tamano()){
                        fi=parts[j].tamano()-procs[i];
                        g.setColor(Color.RED);
                        g.fillRect(20, py, 120, 50);
                        g.setColor(Color.BLACK);
                        dibCad("P"+(i+1)+"= "+Integer.toString(procs[i])+"K FI="+Integer.toString(fi),20,p2y,12,g);
                        parts[j].setOcupado(true);
                        ban=1;
                    }
                }
                py-=50;
                p2y-=50;
                j++;
            }
            if(ban==0){
                JOptionPane.showMessageDialog(ventana,
                        "Error en el proceso "+(i+1),
                        "ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    void MejorAjuste(Graphics g){
        int py,p2y,j,ban,fi;
        g.setColor(Color.ORANGE);
        desocupar();
        for(int i=0;i<totalProc;i++){
            j=1;
            ban=0;
            int band=0;
            int residuo=0;
            int indice=0;
            int npy=0;
            int np2y=0;
            py=450;
            p2y=480;
            while(j<tamPart){
                if(!parts[j].isOcupado()){
                    if (band==0) {
                        if (procs[i] <= parts[j].tamano()) {
                            residuo= parts[j].tamano()-procs[i];
                            indice=j;
                            npy=py;
                            np2y=p2y;
                            ban=1;
                            band=1;
                        }
                    }else{
                        if(parts[j].tamano()-procs[i]>=0 && parts[j].tamano()-procs[i] < residuo){
                            residuo= parts[j].tamano()-procs[i];
                            indice= j;
                            np2y=p2y;
                            npy=py;
                        }
                    }
                }
                py-=50;
                p2y-=50;
                j++;
            }
            if(ban==0){
                JOptionPane.showMessageDialog(ventana,
                        "Error en el proceso "+(i+1),
                        "ERROR",JOptionPane.ERROR_MESSAGE);
            }else{
                fi=parts[indice].tamano()-procs[i];
                g.setColor(Color.RED);
                g.fillRect(350, npy, 120, 50);
                g.setColor(Color.BLACK);
                dibCad("P"+(i+1)+"= "+Integer.toString(procs[i])+"K FI="+Integer.toString(fi),350,np2y,12,g);
                parts[indice].setOcupado(true);
            }
        }
    }

    void PeorAjuste(Graphics g){
        int py,p2y,j,ban,fi;
        g.setColor(Color.ORANGE);
        desocupar();
        for(int i=0;i<totalProc;i++){
            j=1;
            ban=0;
            int band=0;
            int residuo=0;
            int indice=0;
            int npy=0;
            int np2y=0;
            py=450;
            p2y=480;
            while(j<tamPart){
                if(!parts[j].isOcupado()){
                    if (band==0) {
                        if (procs[i] <= parts[j].tamano()) {
                            residuo= parts[j].tamano();
                            indice=j;
                            npy=py;
                            np2y=p2y;
                            ban=1;
                            band=1;
                        }
                    }else{
                        if(procs[i] <= parts[j].tamano() && parts[j].tamano()>residuo){
                            residuo= parts[j].tamano();
                            indice= j;
                            np2y=p2y;
                            npy=py;
                        }
                    }
                }
                py-=50;
                p2y-=50;
                j++;
            }
            if(ban==0){
                JOptionPane.showMessageDialog(ventana,
                        "Error en el proceso "+(i+1),
                        "ERROR",JOptionPane.ERROR_MESSAGE);
            }else{
                fi=parts[indice].tamano()-procs[i];
                g.setColor(Color.ORANGE);
                g.fillRect(700, npy, 120, 50);
                g.setColor(Color.BLACK);
                dibCad("P"+(i+1)+"= "+Integer.toString(procs[i])+"K FI="+Integer.toString(fi),700,np2y,12,g);
                parts[indice].setOcupado(true);
            }
        }
    }



    public void dibparts(Graphics g){
        posy=500;
        poscy=550;
        g.setColor(Color.BLACK);
        for(int i=0;i<tamPart;i++){
            if (i==0){
                g.drawRect(20, posy, 120, 50);
                g.drawRect(350, posy, 120, 50);
                g.drawRect(700, posy, 120, 50);
                dibCad("PA SO"+ "= " + Integer.toString(parts[i].tamano()) + "K", 20, poscy, 12, g);
                dibCad("PA SO"+ "= " + Integer.toString(parts[i].tamano()) + "K", 350, poscy, 12, g);
                dibCad("PA SO"+ "= " + Integer.toString(parts[i].tamano()) + "K", 700, poscy, 12, g);
                posy -= 50;
                poscy -= 50;
            }else {
                g.drawRect(20, posy, 120, 50);
                g.drawRect(350, posy, 120, 50);
                g.drawRect(700, posy, 120, 50);
                dibCad("PA" + (i) + "= " + Integer.toString(parts[i].tamano()) + "K", 20, poscy, 12, g);
                dibCad("PA" + (i) + "= " + Integer.toString(parts[i].tamano()) + "K", 350, poscy, 12, g);
                dibCad("PA" + (i) + "= " + Integer.toString(parts[i].tamano()) + "K", 700, poscy, 12, g);
                posy -= 50;
                poscy -= 50;
            }
        }
    }

    public void paint(Graphics g){
        if(ban!=1){
            dibparts(g);
            if(ban==2){
                PrimerAjuste(g);
                MejorAjuste(g);
                PeorAjuste(g);
                dibparts(g);
            }
        }
        dibCad("Primer Ajuste",20,600,20,g);
        dibCad("Mejor Ajuste",350,600,20,g);
        dibCad("Peor Ajuste",700,600,20,g);
    }

    public void borrar(){
        if (parts!=null)
        parts= null;
        ban=1;
        repaint();
    }
}

