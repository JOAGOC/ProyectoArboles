import java.util.EventListener;

public interface ArbolListener extends EventListener{
    public void onArbolChanged(ArbolBinarioEntero aBinarioEntero);
}