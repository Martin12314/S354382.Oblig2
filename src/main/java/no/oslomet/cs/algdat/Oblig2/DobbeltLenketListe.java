package no.oslomet.cs.algdat.Oblig2;


////////////////// class DobbeltLenketListe //////////////////////////////


import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;


public class DobbeltLenketListe<T> implements Liste<T> {

    /**
     * Node class
     *
     * @param <T>
     */
    private static final class Node<T> {
        private T verdi;                   // nodens verdi
        private Node<T> forrige;
        private Node<T> neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen

    public DobbeltLenketListe() {
        hode=hale=null;
        antall=0;
        endringer=0;

    }
    private Node<T> finnNode(int indeks) {
    Node<T> current;

    if (indeks<=antall/2){
        current=hode;
        for (int i=0; i<indeks; i++) {
            current = current.neste;
        }
        }else {
            current= hale;
            for (int i=antall-1; i>indeks; i--){
                current=current.forrige;
            }
        }
    return current;
    }
    private static void fratilKontroll(int antall, int fra, int til)
    {
        if (fra < 0)                                  // fra er negativ
            throw new IndexOutOfBoundsException
                    ("fra(" + fra + ") er negativ!");

        if (til > antall)                          // til er utenfor tabellen
            throw new IndexOutOfBoundsException
                    ("til(" + til + ") > antall(" + antall + ")");

        if (fra > til)                                // fra er større enn til
            throw new IllegalArgumentException
                    ("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
    }

    public DobbeltLenketListe(T[] a) {
        Objects.requireNonNull(a, "Tabellen a er null! "); //nullSjekk
        // Finner første verdi i arrayet som ikke er null
        if (a.length > 0) {
            int indeks = 0;
            for (; indeks < a.length; indeks++) {
                if (a[indeks] != null) {
                    hode = hale = new Node<>(a[indeks]);
                    antall++;
                    break;

                }
            }
            if (hode != null) {
                indeks++;
                for (; indeks < a.length; indeks++) {
                    if (a[indeks] != null) {
                        hale.neste = new Node<>(a[indeks], hale, null);
                        hale = hale.neste;
                        antall++;
                    }
                }
            }


        }
    }


        public Liste<T> subliste(int fra, int til) {
            fratilKontroll(antall,fra,til);

            Node<T> finn= finnNode(fra);
            DobbeltLenketListe<T> subliste= new DobbeltLenketListe<>();
            if(antall<1){
                return subliste;
            }
            for (; fra<til; fra++){
                subliste.leggInn(finn.verdi);
                finn=finn.neste;

            }
            return subliste;
    }

    @Override
    public int antall() {
        return antall;
    }

    @Override
    public boolean tom() {
        return antall == 0;
    }

    @Override
    public boolean leggInn(T verdi) {
       Objects.requireNonNull(verdi, "Verdien som legges inn kan ikke være null");

       if (antall==0){
           hode=hale= new Node<>(verdi,null,null);
           antall++;
           endringer++;
           return true;

       }else{
           hale=hale.neste=new Node<>(verdi,hale,null);
           antall++;
           endringer++;
           return true;
       }

    }

    @Override
    public void leggInn(int indeks, T verdi) {
     indeksKontroll(indeks,false);

     if (antall==0){
         hale=hode= new Node<>(verdi,null,null);
     }else if (indeks==0){
         hode=hode.forrige= new Node<>(verdi,null,hode);
     }else if(indeks==antall){
         hale=hale.neste= new Node<>(verdi,hale,null);
     }else{
        Node<T>p=finnNode(indeks);
        p.forrige=p.forrige.neste= new Node<>(verdi, p.forrige,p);
     }

    }

    @Override
    public boolean inneholder(T verdi) {
        int current= indeksTil(verdi);

        if (current!=-1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public T hent(int indeks) {
        indeksKontroll(indeks,false);
        return finnNode(indeks).verdi;
    }

    @Override
    public int indeksTil(T verdi) {


        Node<T>p =hode;
       for (int indeks=0; indeks<antall; indeks++, p=p.neste){
           if (p.verdi.equals(verdi)){
               return indeks;
           }
       }
       return -1;
      }



    @Override
    public T oppdater(int indeks, T nyverdi) {
        indeksKontroll(indeks, false);
        Objects.requireNonNull(nyverdi);

        T forverdi= hent(indeks);
        finnNode(indeks).verdi=nyverdi;
        endringer++;



      return forverdi;
    }

    @Override
    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T fjern(int indeks) {
        indeksKontroll(indeks, false);  // Se Liste, false: indeks = antall er ulovlig



        T temp ;                            // hjelpevariabel

        if (indeks == 0)                     // skal første verdi fjernes?
        {
            temp = hode.verdi;                 // tar vare på verdien som skal fjernes
            hode = hode.neste;                 // hode flyttes til neste node
            hode.forrige=null;
            if (antall == 1) {
                hale = null;      // det var kun en verdi i listen

            }
        }  if (indeks == antall-1) {
            temp = hale.verdi;
            hale = hale.forrige;
            hale.neste=null;
            if (antall == 1){
                hale = null;      // det var kun en verdi i listen
            }

        } else{
            Node<T> current=hode;
            for (int i=0; i<indeks; i++) {//Går gjennom listen fra start til indeksF
                current = current.neste;

            }

            temp = current.verdi;

            current=current.neste;

            current.forrige=null;



        }



        antall--;                            // reduserer antallet
        return temp;                         // returner fjernet verdi
    }

    @Override
    public void nullstill() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {

        if (antall==0){
            return "[]";
        }
        StringBuilder s=  new StringBuilder();

        Node<T> p = hode;
        s.append("[");
        s.append(p.verdi);
        p=p.neste;

        while(p!=null){
            s.append(", ");
            s.append(p.verdi);
            p=p.neste;

        }
        s.append("]");
        return s.toString();

    }

    public String omvendtString() {
        if (antall==0){
            return "[]";
        }
        StringBuilder s= new StringBuilder();

        Node<T>p =hale;
        s.append("[");
        s.append(p.verdi);
        p=p.forrige;
        while (p!=null){
            s.append(", ");
            s.append(p.verdi);
            p=p.forrige;
        }
        s.append("]");
        return s.toString();

    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    public Iterator<T> iterator(int indeks) {
        throw new UnsupportedOperationException();
    }

    private class DobbeltLenketListeIterator implements Iterator<T> {
        private final Node<T> denne;
        private final boolean fjernOK;
        private final int iteratorendringer;

        private DobbeltLenketListeIterator() {
            denne = hode;     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return denne != null;
        }

        @Override
        public T next() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new UnsupportedOperationException();
    }

} // class DobbeltLenketListe


