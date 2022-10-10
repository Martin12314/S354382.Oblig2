package no.oslomet.cs.algdat.Oblig2;


////////////////// class DobbeltLenketListe //////////////////////////////


import java.util.*;


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

       if (antall==0)
           hode=hale= new Node<>(verdi,null,null);
    else
           hale=hale.neste=new Node<>(verdi,hale,null);
           antall++;
           endringer++;
           return true;


    }

    @Override
    public void leggInn(int indeks, T verdi) {
        if (indeks>antall)
            throw new IndexOutOfBoundsException("Indeksen kan ikke være høyere enn antall");

        if (indeks<0)
            throw new IndexOutOfBoundsException("indeksen kan ikke være negativ");
        Objects.requireNonNull(verdi, "Kan ikke være null verdier!");

        if (antall==0){
         hale=hode= new Node<>(verdi,null,null);
         antall++;
         endringer++;
     }else if (indeks==0){
         hode=hode.forrige= new Node<>(verdi,null,hode);
         antall++;
         endringer++;
     }else if(indeks==antall){
         hale=hale.neste= new Node<>(verdi,hale,null);
         antall++;
         endringer++;
     }else{
        Node<T>p=finnNode(indeks);
        p.forrige=p.forrige.neste= new Node<>(verdi, p.forrige,p);
         antall++;
         endringer++;
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
        if (verdi == null) return false;

        for (Node<T> current = hode; current != null; current = current.neste) {
            if (verdi.equals(current.verdi)) {
                antall--;
                endringer++;

                if(current == hode){
                    if(hode.neste == null){
                        hode = null;
                        hale = null;
                    }
                    else {
                        hode = hode.neste;
                        hode.forrige = null;
                    }
                }
                else if(current == hale) {
                    if (hale.forrige == null) {
                        hale = null;
                        hode = null;
                    } else {
                        hale = hale.forrige;
                        hale.neste = null;
                    }
                }
                else {
                    current.forrige.neste = current.neste;
                    current.neste.forrige = current.forrige;
                }

                return true;
            }


        }
        return false;


    }

    @Override
    public T fjern(int indeks) {
        indeksKontroll(indeks,false);  // Se Liste, false: indeks = antall er ulovlig
        Node<T> temp=hode;

        if (indeks == 0) { // Første node
            if(hode.neste == null){
                temp = hode;
                hode = null;
                hale = null;
            }else {
               temp=hode;
                hode = hode.neste;
                hode.forrige = null;

            }
        } else if (indeks == antall - 1) { // Siste node
            if(hale.forrige==null){
                temp=hale;
                hode=null;
                hale=null;

            }else{
                temp = hale;
                hale = hale.forrige;
                hale.neste = null;
            }

        } else {
            Node<T>current = hode;
            for (int i=0; i<indeks-1; i++) {
                current = current.neste;

            }

            temp = current.neste;

            current.neste = current.neste.neste;
            current.neste.forrige = current;
        }


        antall--;                            // reduserer antallet
        endringer++;
        return temp.verdi;                         // returner fjernet verdi
     }

//Lagde en metode som tok tiden for metoden in test filen min og valgte å bruke en liste med et gitt antall tall
//Metode 1 brukte mellom 8 og 10 millisekunder på å nulstille listen som hadde en million tall.
//Metode 2 brukte mellom 10 og 15 millisekunder på å nullstille en liste som hadde en million tall
//Velger dermed metode 1 for denne var raskest
    @Override
    public void nullstill() {

    Node<T> current=hode;
        Node<T> temp;

        while (current!=null){
            temp=current.neste;
            current.neste=null;
            current.forrige=null;
            current.verdi=null;
            current=temp;
        }
        antall = 0;
        endringer++;




        /*  for (int i=0; i<antall; i++){
            fjern(0);

        }
        antall=0;
       endringer++;

         */



    }

    @Override
    public String toString() {

        if (tom()){
            return "[]";
        }
        StringBuilder s=  new StringBuilder();


        s.append("[");
        Node<T> p = hode;
        if (p!=null) {
            s.append(p.verdi);
            p = p.neste;
        }

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

        StringBuilder s = new StringBuilder();

        Node<T>p=hale;
        s.append('[');
        s.append(p.verdi);
        p=p.forrige;
        while(p!=null){
            s.append(", ");
            s.append(p.verdi);
            p=p.forrige;
        }
        s.append(']');
        return s.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new DobbeltLenketListeIterator();
    }

    public Iterator<T> iterator(int indeks) {
        indeksKontroll(indeks, false);
        return new DobbeltLenketListeIterator(indeks);
    }

    private class DobbeltLenketListeIterator implements Iterator<T> {
        private  Node<T> denne;
        private  boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator() {
            denne = hode;     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks) {
            denne = hode;     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer

            for (int i=0; i<=indeks; i++){
                next();
            }


        }

        @Override
        public boolean hasNext() {
            return denne != null;
        }

        @Override
        public T next() {

            if(iteratorendringer!=endringer) {
                throw new ConcurrentModificationException();
            }

            if (hasNext()==false) {
                throw new NoSuchElementException();
            }
            fjernOK=true;
            T verdi=denne.verdi;
            denne=denne.neste;
            return verdi;



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


