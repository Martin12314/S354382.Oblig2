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
        hode=hale=null; //Setter start verdier
        antall=0;
        endringer=0;

    }
    private Node<T> finnNode(int indeks) {
    Node<T> current; //Hjelpevariabel

    if (indeks<=antall/2){ //Er det første halvdel?
        current=hode;
        for (int i=0; i<indeks; i++) {//Går gjennom listen til verdi finnes
            current = current.neste;
        }
        }else { //Andre halvdel av listen
            current= hale;
            for (int i=antall-1; i>indeks; i--){//Går gjennom listen til verdi finnes
                current=current.forrige;
            }
        }
    return current;//Returnerer indeksen
    }
    private static void fratilKontroll(int antall, int fra, int til) //hentet fra kompendium, men endret litt
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
        Objects.requireNonNull(a, "Tabellen er null! "); //nullSjekk
        // Finner første verdi i arrayet som ikke er null
        if (a.length > 0) { //Så lenge listen ikke er tom
            int indeks = 0; //Hjelpevariabel
            for (; indeks < a.length; indeks++) {
                if (a[indeks] != null) {
                    hode = hale = new Node<>(a[indeks]); //legger inn verdi fra listen i første
                    antall++;
                    break;

                }
            }
            if (hode != null) {
                indeks++;
                for (; indeks < a.length; indeks++) {
                    if (a[indeks] != null) {
                        hale.neste = new Node<>(a[indeks], hale, null); //om det er flere verdier igjen i listalegges den i siste
                        hale = hale.neste; //Fikser peker så hale er den siste
                        antall++;
                    }
                }
            }


        }
    }


        public Liste<T> subliste(int fra, int til) {
            fratilKontroll(antall,fra,til); //Sjekker at verdiene fra og til er lovlige

            Node<T> finn= finnNode(fra); //finner node
            DobbeltLenketListe<T> subliste= new DobbeltLenketListe<>(); //Lager en ny liste
            if(antall<1){
                return subliste;
            }
            for (; fra<til; fra++){ //Så lenge fra ikke er lavere enn til, så fra øker til den er den nest siste som skal tas med altså fra-1
                subliste.leggInn(finn.verdi);//Legger inn verdien til den første noden som skal brukes(Fotsetter gjennom for løkken)
                finn=finn.neste;//Beveger seg til neste

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

       if (antall==0) //Om listen er tom
           hode=hale= new Node<>(verdi,null,null);
    else
           hale=hale.neste=new Node<>(verdi,hale,null); //Ellers legges den inn i siste
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

        if (antall==0){ //Om det er en tom liste
         hale=hode= new Node<>(verdi,null,null); //Alle andre pekere er null, og legger inn verduen
         antall++;
         endringer++;
     }else if (indeks==0){//Om det er den første verdien
         hode=hode.forrige= new Node<>(verdi,null,hode); //ny node hode får verdien, og den neste får verdien til hode
         antall++;
         endringer++;
     }else if(indeks==antall){ //Om det er siste
         hale=hale.neste= new Node<>(verdi,hale,null);//Legger til verdien etter hale og tidligere hale blir forrige.hale
         antall++;
         endringer++;
     }else{
        Node<T>p=finnNode(indeks); //Finner noden sin indeks
        p.forrige=p.forrige.neste= new Node<>(verdi, p.forrige,p); //Lager plass mellom indeksene som er der og legger inn verdien
         antall++;
         endringer++;
     }


    }

    @Override
    public boolean inneholder(T verdi) {
        int current= indeksTil(verdi); //Finner indeks til verdi

        if (current!=-1){ // om indekstil ikke returerner -1 finnes verdien
            return true;
        }else{
            return false;
        }
    }

    @Override
    public T hent(int indeks) {
        indeksKontroll(indeks,false);
        return finnNode(indeks).verdi; //Returnerer nodens verdi
    }

    @Override
    public int indeksTil(T verdi) {


        Node<T>p =hode; //Hjelpevariabel
       for (int indeks=0; indeks<antall; indeks++, p=p.neste){ //Teller opp og øker indeks til verdien finnes, og kan dermed returnere indeksen igjen
           if (p.verdi.equals(verdi)){ //Om verdien er funnet
               return indeks; //Returner indeksen
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

        for (Node<T> current = hode; current != null; current = current.neste) { //Går gjennom listen fra hode
            if (verdi.equals(current.verdi)) { //Om curretn har kommet til verdien
                antall--;
                endringer++;

                if(current == hode){ //Om det er den første
                    if(hode.neste == null){  //Har listen kun en verdi? Da nulles alt
                        hode = null;
                        hale = null;
                    }
                    else { //Om flere verdier nuller den første verdien
                        hode = hode.neste;
                        hode.forrige = null;
                    }
                }
                else if(current == hale) { //Om det er siste
                    if (hale.forrige == null) {//Har listen kun en verdi? Da nulles alt
                        hale = null;
                        hode = null;
                    } else { //Om flere verdier nuller den siste verdien
                        hale = hale.forrige;
                        hale.neste = null;
                    }
                }
                else {//et sted i midten
                    current.forrige.neste = current.neste; //Lager plass og riktige pekere for så å legge inn verdi
                    current.neste.forrige = current.forrige;//Lager plass og riktige pekere for så å legge inn verdi
                }

                return true;//Om noen av de nevnte oppfylles blir verdien slettet og dermed finnes den i lista og da kan sann returnes
            }


        }
        return false;//Fant ikke verdi i lista


    }

    @Override
    public T fjern(int indeks) {
        indeksKontroll(indeks,false);  // Se Liste, false: indeks = antall er ulovlig
        Node<T> temp=hode; //Tar vare på indeksen

        if (indeks == 0) { // Første node
            if(hode.neste == null){ //Om listen kun har en verdi
                temp = hode;
                hode = null;
                hale = null;
            }else {
               temp=hode;
                hode = hode.neste;
                hode.forrige = null;

            }
        } else if (indeks == antall - 1) { // Siste node
            if(hale.forrige==null){ //Om listen kun har en verdi
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
            for (int i=0; i<indeks-1; i++) { //Går til i er to mindre enn indeks;
                current = current.neste; //Fortsetter til en mindre enn indeks

            }

            temp = current.neste; //Sparer på verdi

            current.neste = current.neste.neste; //Fikser pekere
            current.neste.forrige = current; //Fikser pekere
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

    Node<T> current=hode; //Hjelpevariabel
        Node<T> temp; //Hjelpevariabel

        while (current!=null){ //Så lenge lista ikke er tom
            temp=current.neste; //Sparer på neste verdien
            current.neste=null; //Alt nulles
            current.forrige=null;
            current.verdi=null;
            current=temp; //Gir current en ny verdi
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
    public String toString() { //Hentet fra kompendium

        if (tom()){
            return "[]";
        }
        StringBuilder s=  new StringBuilder();


        s.append("["); //Starter liste
        Node<T> p = hode;
        if (p!=null) { //Om første verdi ikke er null legges første inn
            s.append(p.verdi);
            p = p.neste;
        }

        while(p!=null){ //Legger inn resten av tegnstreken, med komma og mellomrom
            s.append(", ");
            s.append(p.verdi);
            p=p.neste;

        }
        s.append("]"); //Avslutter tegnstreng
        return s.toString();

    }

    public String omvendtString() { //Helt lik metoden over, men her begynner man på siste verdi og jobber seg fra
                                    //slutt til start altså vfra hale mot hode.
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
        private  int iteratorendringer;

        private DobbeltLenketListeIterator() {
            denne = hode;     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks) {
            denne = hode;     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer

            for (int i=0; i<indeks; i++ ){
                denne=denne.neste;



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
            if (!fjernOK){
                throw new IllegalStateException();
            }
            if (iteratorendringer!=endringer){
                throw new ConcurrentModificationException();
            }
            fjernOK=false;
            if (antall==1){//Kun en verdi
                hode=hale=null;

            }else if (denne==null){ //Første
                hale=hale.forrige;
                hale.neste=null;
            }else if(denne.forrige==hode){//Siste
                hode=hode.neste;
                hode.forrige=null;
            }else{
               denne.forrige=denne.forrige.forrige; //Om det er midt i
               denne.forrige.neste=denne;
            }
            antall--;
            endringer++;
            iteratorendringer++;



        }


    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new UnsupportedOperationException();
    }

} // class DobbeltLenketListe


