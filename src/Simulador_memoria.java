import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Simulador_memoria extends JFrame{
    private JMenu menu;
    private JMenuBar barramenu;
    private JMenuItem im1, im2, im3, im4;
    private JPanel panelIzquierdo, panelSuperior, panelCentro;
    private JButton aceptar;
    private JLabel etiquetaMem = new JLabel("  Memoria Total:  ");
    private JLabel etiquetaProc = new JLabel("No. de Procesos:  ");
    private JLabel etiquetaPart = new JLabel("No. de Particiones:  ");
    private JLabel etiquetaInfo = new JLabel("   Informacion:       ");
    private JTextField campoMem = new JTextField("", 5);
    private JTextField campoProc = new JTextField("", 5);
    private JTextField campoPart = new JTextField("", 5);
    private Dibujar dibujo = new Dibujar();
    private JFrame ventana= new JFrame("Formulario");
    private int numProc, numPart,tamT,ban=0;

    public Simulador_memoria(){
        super();
        contruyePanelSuperior();
        contruyePanelIzquierdo();
        contruyeVentana();

    }

    public void contruyePanelSuperior(){
        panelSuperior = new JPanel();
        aceptar=new JButton("Aceptar");

        panelSuperior.add(etiquetaMem); panelSuperior.add(campoMem);
        panelSuperior.add(etiquetaProc); panelSuperior.add(campoProc);
        panelSuperior.add(etiquetaPart); panelSuperior.add(campoPart);

        aceptar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                if (campoMem.getText().length()==0 ||
                        campoProc.getText().length()==0 ||
                        campoPart.getText().length()==0 ){
                    JOptionPane.showMessageDialog(ventana,
                            "Todos los campos son requeridos",
                            "ERROR",JOptionPane.ERROR_MESSAGE);
                }else{
                    try{
                        tamT=Integer.parseInt(campoMem.getText());
                        numProc=Integer.parseInt(campoProc.getText());
                        numPart=Integer.parseInt(campoPart.getText());
                        if(tamT<0 || numProc<0 || numPart<0){
                            JOptionPane.showMessageDialog(ventana,
                                    "Ingrese numeros positivos",
                                    "ERROR",JOptionPane.ERROR_MESSAGE);
                        }else{
                            dibujo.particionar(numPart,tamT);
                            if(!dibujo.compara())
                                dibujo.agregarProc(numProc);
                        }
                    }catch(Exception e){ //tratamiento a errores
                        JOptionPane.showMessageDialog(ventana,
                                "Solo se aceptan numeros ",
                                "ERROR",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }});

        panelSuperior.add(aceptar);
        panelSuperior.setBackground(Color.cyan);

    }

    public void contruyePanelIzquierdo(){
        panelIzquierdo = new JPanel();
        panelIzquierdo.add(etiquetaInfo);

        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo,BoxLayout.X_AXIS));
        panelIzquierdo.setBackground(Color.orange);
    }

    public void contruyeVentana(){
        JFrame frame = new JFrame();

        //Construimos el menu
        menu= new JMenu("Opciones");
        barramenu = new JMenuBar();
        setJMenuBar(barramenu);
        barramenu.add(menu);
        im1= new JMenuItem("Reiniciar");
        im2= new JMenuItem("Borrar datos");
        im3= new JMenuItem("Salir");
        im4= new JMenuItem("Acerca de...");
        im1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dibujo.borrar();
                campoMem.setText(null);
                campoPart.setText(null);
                campoProc.setText(null);
            }
        });
        im2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campoMem.setText(null);
                campoPart.setText(null);
                campoProc.setText(null);
            }
        });
        im3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                System.exit(0);
            }
        });
        im4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(ventana," Simulador v1.0 \n Creado por: \n Sergey Sánchez Jiménez\n  Fernando Isidro Luna\n  Yazmin Francisco Lopez");
            }
        });
        menu.add(im1);
        menu.add(im2);
        menu.add(im3);
        menu.add(im4);


        //agregamos el menu
        frame.add(barramenu);

        //agregamos los paneles al frame principal
        frame.add(dibujo);
        frame.add(panelIzquierdo,BorderLayout.WEST);
        frame.add(panelSuperior,BorderLayout.NORTH);

        //Configuramos el frame
        pack();
        frame.setTitle("Simulador");
        frame.setSize(1000, 700);           // tamano a la ventana (ancho, alto)
        frame.setLocationRelativeTo(null);  // centrar la ventana en la pantalla
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main (String [] inforux){
        Simulador_memoria simulador=new Simulador_memoria();
    }
}
