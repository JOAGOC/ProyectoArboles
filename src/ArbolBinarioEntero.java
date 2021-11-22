/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Angel
 */
public class ArbolBinarioEntero {
    
    private Nodo raiz;
    private int profundidad, nivel;
    private ArbolListener al;
    
    public void setAl(ArbolListener al) {
        this.al = al;
    }

    public ArbolBinarioEntero() {
        raiz = null;
    }

    public void agregar(int entero) {
        if (raiz == null) {
            raiz = new Nodo(entero);
        } else if (entero < raiz.getInfo()) {
            if (raiz.getIzq() != null) {
                agregar(raiz.getIzq(), entero);
            } else {
                raiz.setIzq(new Nodo(entero));
            }
        } else if (raiz.getDer() != null) {
            agregar(raiz.getDer(), entero);
        } else {
            raiz.setDer(new Nodo(entero));
        }
        notifyArbolChanged();
    }

    public void notifyArbolChanged(){
        if (al != null)
            al.onArbolChanged(this);
    }

    private void agregar(Nodo r, int entero) {
        if (entero < r.getInfo()) {
            if (r.getIzq() != null) {
                agregar(r.getIzq(), entero);
            } else {
                r.setIzq(new Nodo(entero));
            }
        } else if (r.getDer() != null) {
            agregar(r.getDer(), entero);
        } else {
            r.setDer(new Nodo(entero));
        }
    }

    public Nodo buscar(int busqueda) {
        return buscar(busqueda, raiz);
    }

    public Nodo buscar(int busqueda, Nodo n) {
        if (n == null) {
            return null;
        } else if (busqueda == n.getInfo()) {
            return n;
        } else if (busqueda < n.getInfo()) {
            return buscar(busqueda, n.getIzq());
        } else {
            return buscar(busqueda, n.getDer());
        }
    }

    public boolean editar(int valor, int cambio) {
        if (!borrar(valor)) {
            return false;
        }
        agregar(cambio);
        return true;
    }

    public boolean borrar(int valor) {
        Nodo padre = null;
        Nodo nodoAEliminar = raiz;
        boolean dirSubarbol = false;// False izq; True der
        while (valor != nodoAEliminar.getInfo()) {
            padre = nodoAEliminar;
            dirSubarbol = valor > nodoAEliminar.getInfo();
            nodoAEliminar = dirSubarbol ? nodoAEliminar.getDer() : nodoAEliminar.getIzq();
            if (nodoAEliminar == null) {
                return false;
            }
        }
        if (nodoAEliminar.getIzq() == null && nodoAEliminar.getDer() == null) {
            if (nodoAEliminar == raiz) {
                raiz = null;
            } else if (dirSubarbol) {
                padre.setDer(null);
            } else {
                padre.setIzq(null);
            }
        } else if (nodoAEliminar.getIzq() != null && nodoAEliminar.getDer() != null) {
            Nodo reemplazo = getNodoRemplazo(nodoAEliminar);
            if (nodoAEliminar == raiz) {
                raiz = reemplazo;
            } else if (dirSubarbol) {
                padre.setDer(reemplazo);
            } else {
                padre.setIzq(reemplazo);
            }
        } else if (nodoAEliminar.getIzq() != null) {
            if (nodoAEliminar == raiz) {
                raiz = raiz.getIzq();
            } else if (dirSubarbol) {
                padre.setDer(nodoAEliminar.getIzq());
            } else {
                padre.setIzq(nodoAEliminar.getIzq());
            }
        } else {
            if (nodoAEliminar == raiz) {
                raiz = raiz.getDer();
            } else if (dirSubarbol) {
                padre.setDer(nodoAEliminar.getDer());
            } else {
                padre.setIzq(nodoAEliminar.getDer());
            }
        }
        notifyArbolChanged();
        return true;
    }

    private Nodo getNodoRemplazo(Nodo nodo) {
        Nodo reemplazarPadre = nodo, reemplazo = nodo, auxiliar = nodo.getDer();
        while (auxiliar != null) {
            reemplazarPadre = reemplazo;
            reemplazo = auxiliar;
            auxiliar = auxiliar.getIzq();
        }
        System.out.println("Nodo a eliminar: " + nodo.getInfo());
        reemplazo.setIzq(nodo.getIzq());
        if (reemplazarPadre != nodo) {
            reemplazarPadre.setIzq(reemplazo.getDer());
            reemplazo.setDer(nodo.getDer());
        }
        System.out.println("Nodo de remplazo: " + reemplazo.getInfo());
        return reemplazo;
    }

    public void preorden() {
        ArbolBinarioEntero.preorden(raiz);
        System.out.println();
    }

    public static void preorden(Nodo r) {
        if (r == null) {
            return;
        }
        System.out.print("[" + r.getInfo() + "]");
        preorden(r.getIzq());
        preorden(r.getDer());
    }

    public void postorden() {
        ArbolBinarioEntero.postorden(raiz);
        System.out.println();
    }

    public static void postorden(Nodo r) {
        if (r == null) {
            return;
        }
        postorden(r.getIzq());
        postorden(r.getDer());
        System.out.print("[" + r.getInfo() + "]");
    }

    public void inorden() {
        ArbolBinarioEntero.inorden(raiz);
        System.out.println();
    }

    public static void inorden(Nodo r) {
        if (r == null) {
            return;
        }
        inorden(r.getIzq());
        System.out.print("[" + r.getInfo() + "]");
        inorden(r.getDer());
    }

    private void profundidad(Nodo nodo) {
        if (nodo != null) {
            nivel++;
            if (nivel > profundidad) {
                profundidad = nivel;
            }
            profundidad(nodo.getIzq());
            profundidad(nodo.getDer());
            nivel--;
        }
    }

    public Nodo getRaiz() {
        return raiz;
    }

    //Devuleve la altura del arbol
    public int getProfundidad() {
        profundidad = nivel = 0;
        profundidad(raiz);
        return profundidad;
    }

    public static void main(String[] args) {
        ArbolBinarioEntero arbol = new ArbolBinarioEntero();
         int[] xd = { 9, 0, 4, 0, 1, 4, 7, 8, 2, 11 };
//        int[] xd = new int[25];
        for (int a = 0; xd.length > a; a++) {
//            xd[a] = (int) (Math.random() * 50);
            arbol.agregar(xd[a]);
        }
        System.out.println(arbol.getProfundidad() + "");
        System.out.println("Inorden");
        arbol.inorden();
        System.out.println("Preorden");
        arbol.preorden();
        System.out.println("Postorden");
        arbol.postorden();
//        for (int a = xd.length - 1; a >= 0; a -= 1) {
//            System.out.println();
//            System.out.println("Borrar " + xd[a] + ": " + (arbol.borrar(xd[a]) ? "Borrado" : "No borrado"));
//            System.out.println("Preorden");
//            arbol.preorden();
//        }
    }
}
