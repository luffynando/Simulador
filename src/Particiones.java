public class Particiones {
    private int tam;
    private boolean ocupado;

    public Particiones(int x){
        tam=x;
        ocupado=false;
    }

    public int tamano(){
        return tam;
    }

    public boolean isOcupado(){
        return ocupado;
    }

    public void setOcupado(boolean x){
        ocupado=x;
    }
}
