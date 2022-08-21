import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.text.*;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

class Ventana extends JFrame{
	private String[] mensaje;	
	
	public Ventana(listaFinal lista1) {
		mensaje = lista1.getLista();		
		setSize(1200, 700);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("TERCER EXAMEN PARCIAL");
		this.iniciarComponentes();
		setDefaultCloseOperation(EXIT_ON_CLOSE);		
	}
	
	public void iniciarComponentes() {
		int linea;
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.DARK_GRAY);
		panel.setPreferredSize(new Dimension(1200, 2000));
		this.getContentPane().add(panel);		
		//Escribir texto
		JLabel[] etiqueta = new JLabel[mensaje.length];				
		
		for (int i = 0; i < mensaje.length; i++) {
			linea = 10 + (i*26);
			etiqueta[i] = new JLabel();
			etiqueta[i].setForeground(Color.white);
			etiqueta[i].setFont(new Font("Arial", Font.PLAIN, 16));
			etiqueta[i].setText(mensaje[i]);
			etiqueta[i].setBounds(10, linea, 1180,16);
			panel.add(etiqueta[i]);			
		}		
	}
}

class ArbolBin{
	nodoArbol raiz;
	
	public ArbolBin(){
		raiz = null;
	}
	
	public boolean arbolVacio() {
		return raiz == null;
	}
	 public void agregaNodo(String nombre){
		 Collator ComparativaExacta = Collator.getInstance(Locale.US);
		 ComparativaExacta.setStrength(Collator.SECONDARY);
	      nodoArbol nuevo = new nodoArbol(nombre);
	      if (raiz==null){
	          raiz = nuevo;
	      }else{
	          nodoArbol auxiliar = raiz;
	          nodoArbol padre;
	          while(true){
	              padre = auxiliar;
	              if (ComparativaExacta.compare(auxiliar.Nombre, nombre)==0){
	            	 padre.Repeticion ++; 
	            	 return;
	              }
	              else if(ComparativaExacta.compare(auxiliar.Nombre, nombre)>=0){
	                  auxiliar = auxiliar.HijoIzqu;
	                  if(auxiliar == null){
	                      padre.HijoIzqu = nuevo;
	                      return;
	                  }
	              } else {
	                  auxiliar = auxiliar.HijoDer;
	                  if(auxiliar == null){
	                      padre.HijoDer = nuevo;
	                      return;
	                  }
	              }
	          }
	      }
	  }
	 
	public listaFinal inOrden(nodoArbol r, listaFinal lista1){		 
		String[] arrTemp;
		String itemTemp;
		
		if(r!=null){
			inOrden(r.HijoIzqu, lista1);			
			lista1.setIndice(lista1.getIndice() + 1);
			arrTemp = new String[lista1.getIndice()];
			arrTemp = Arrays.copyOf(lista1.getLista(), lista1.getIndice());
			arrTemp[lista1.getIndice()-1] = "Palabra: " + r.Nombre + " ---------- Frecuencia: " + r.Repeticion;
			lista1.setLista(arrTemp);
			//System.out.print("Palabra: " + r.Nombre + "\t\t\tFrecuencia: " + r.Repeticion + "\n");
			inOrden(r.HijoDer, lista1);
		}
		listaFinal L1 = lista1;
		return lista1;
	}
}

class nodoArbol{
	String Nombre;
	nodoArbol HijoIzqu, HijoDer;
	int Repeticion;
	
	public nodoArbol(String Nombre) {
		this.Nombre = Nombre;
		this.HijoIzqu = null;
		this.HijoDer = null;
		this.Repeticion = 1;
		
	}
	public String toString(){
		return "\n La palabra del nodo es:"+Nombre;
	}
}

class listaFinal {
	String[] lista;
	int indice;

	public listaFinal(int indice) {
		this.indice = indice;
		lista = new String[indice];
	}	
	
	public String[] getLista() {
		return lista;
	}

	public int getIndice() {
		return indice;
	}

	public void setLista(String[] lista) {
		this.lista = lista;
	}

	public void setIndice(int indice) {
		this.indice = indice;
	}		
}

public class ArbolBinarioTexto {

	public static void main(String[] args) {
			
	      String nombre;
	      ArbolBin arbcreado = new ArbolBin();
	      String Origen;		  
	      Origen = JOptionPane.showInputDialog(null, "Ingresa oracion a evaluar ","Agregando oracion"
                  ,JOptionPane.QUESTION_MESSAGE);
	      StringTokenizer token = new StringTokenizer(Origen, ", ?¿.!¡;:");
	      do {
	    	  nombre=token.nextToken();
	    	  arbcreado.agregaNodo(nombre);
	      
	      }while(token.hasMoreTokens());		  
		
		listaFinal listaT = new listaFinal(0);
		listaT = arbcreado.inOrden(arbcreado.raiz, listaT);
		//Ventana
		Ventana v1 = new Ventana(listaT);
		v1.setVisible(true);
	}
}
