# Obligatorisk oppgave 2 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 
Oppgaven er levert av følgende studenter:
* Martin Tuhus, S354382, S354382@oslomet.no

# Arbeidsfordeling

I oppgaven har vi hatt følgende arbeidsfordeling:
* Jeg jobbet alene.

# Oppgavebeskrivelse

I oppgave 1 brukte jeg til dels kompendium, men jeg endret på koden underveis. Så fikk jeg metoden tom til å returnere at antallet er 0 og antall returnerer seg selv. Metoden dobbeltlenketliste sjekker først om tabellen er null, så går en for løkke gjennom lista, om en verdi ikke er null legges en ny verdi inn, så går en ny løkke gjennom og legger inn reseterende verdier. 

I oppgave 2 A så hentet jeg Tostring fra kompendium, men la inn en sjekk på om første verdien er null, den skriver først en string med [ så legger den inn verdier så lenge hode ikke er null og avslutter så med ]. MotsattString så byttet jeg bare ut hode med hale og neste med forrige.
I oppgave 2 B så sjekker jeg først om listen er tom, isåfall nulles alle perkerne og noden legges inn i hode=hale. Ellers så legges den inn bakerst i hale.neste.

I oppgave 3 brukte jeg kompendium som referanse for finnNode, den sjekker først om verdien til noden den skal finne er i første halvdel av listen, om dette stemmer går en for løkke gjennom lista fra hode helt til den treffer verdien. Hvis ikke skjer det samme, bare at den istedenfor går gjennom fra hale mot midten. Hent returnerer verdien til finnNode sin indeks. Oppdater gjør først en nullsjekk, så tar den vare på den gamle verdien, oppdaterer til nye og returnerer den gamle. 
I oppgave 3B I subListe så finner jeg først fra verdien med finnNode og så går en for løkke gjennom fra fra til til og legger så inn verdien i en sublisten og flytter noden til neste.

I oppgave 4 Begynte jeg med å kode indeksTil, jeg bruker en for løkke hvor indeks er lavere enn antall, i øker og hjelpevariabelen p går til neste. Når verdien til p er lik verdien vi søker returneres p sin indeks, ellers om dette ikke intreffer returneres null. Så kodet jeg inneholder, først setter jeg hjelpeverdien current til å finne indeksen til verdi, om dette blir -1 så betyr det at indeksTil ikke slår ut og dermed er ikke verdien i lista så da returneres false, om den ikke blir -1 returneres true. Dette er hentet fra kompendium. 

oppgave 5 baserte jeg noe på kompendiumet, men siden det er en dobbeltlenket liste måtte jeg endre store deler. Først sjekker jeg at indeksen er lovlig. Om listen er tom settes alle pekere til null, og hale og hode blir verdien. Ellers om det er hode settes verdien til hode, pekerne legges inn og det samme for hale om indeksen er det samme som antall-1. Hvis ikke finnes indeksen til noden og noden legges inn der, pekere flyttes riktig. 

Oppgave 6 brukte jeg komepndium til deler av begge oppgavene, men endret deler av de. Den første fjerner ved hjelp av indeks. Dette gjør den ved å sjekke først om indeksen er lik hode, altså null, om dette stemmer kjøres så en sjekk for å se om dette er eneste verdi i tabellen, om den er det nulles hode og hale, men om den ikke er det slettes kun den verdien og pekeren går videre. Det samme systemet brukes for om indeksen er den siste altså hale. Om indeksen ikke matcher noen av disse og dermed er midt i et sted bruker jeg en for løkke som går fram til variabelen i er en mindre enn indeksen og flytter hjelpevariabelen current fra hodet til verdien før indeksen, så byttes pekerne om og indeks fjernes. Den andre fjern metoden fjerner med hjelp av verdi. Denne fungerer relativt likt, men først bruker jeg en for løkke til å finne den første noden som har verdien "verdi" Når denne er funnet fungerer metoden likt som fjerning ved hjelp av indeks for første og siste node altså om verdien ligger i hale eller hode. Om den derimot ligger et sted i mellom så fjernes denne og current sine pekere endres.

I oppgave 7 testet jeg hvilken metode som var raskest ved bruk av en tidtakingsmetode i kompendium, jeg valgte så å kode nullstill som går igjennom fra hode til hale. Den nuller alle verdiene til hjelpevariabelen current og forflytter seg fra hode mot hale. 

I oppgave 8 la jeg først inn det som ble bedt om, med sjekk av iteratorendringer og om hasnext er false for så å sette fjernOk til true. Så lager jeg hjelpevariabelen verdi for å ta vare på verdien og flytter så "denne" til neste for så å returnere verdi. Iterator<T> iterator() returnerer en instans av iteratorklassen.  private  DobbeltLenketListeIterator(int  indeks) har en for løkke som går gjennom fram til i er riktig indeks og flytter "denne" for å få den på riktig indeks. Iterator<T>  iterator(int  indeks) returnerer en instans av iteratorklassen ved hjelp av indeks. 
  
I oppgave 9 sjekket jeg først at fjernOk og at iteratorendringene og endringene stemte overens. Så sjekket jeg om det kun var en verdi i lista, om dette stemte satt jeg hode og hale til null. Jeg sjekket så og fjernet om det var hode eller hale som det sto i oppgaven. Så om det var den i midten skulle det være denne.forrige såvidt jeg forsto oppgaven, dermed måtte pekerne flyttes og denne fjernes fra listen. 


