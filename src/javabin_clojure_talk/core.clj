(ns javabin-clojure-talk.core)



;; Kalle funksjoner; første element i lisen er funksjonen som skal kalles
;; etterfulgt av argumenter

(str "Hei JavaBin!")

(+ 1 2)

;; Prefiks notasjon og parantesene tar litt tid å bli vant til, men
;; har en rekke fordeler - mer om dette senere

(+ 1 2 3 4)

;; Ingen presendens regler
;; (2 + 2 * 5) / 2
(/ (+ 2 (* 2 5)) 2)


  ;;; ENKLE TYPER
  ;;;;;;;;;;;;;;;

;; Clojure er designet for å være et hostet språk, bruker typer fra
;; hosten hvor dette er hensiktsmessig

(class 100)
(class 2.5)
(class "JavaBin")
(class \S)
(class #"[a-z]")
(class true)

;; Clojure bruk nil istedet for null
(class nil)

;; Typer vi ikke kjenner fra Java

(class (/ 1 2))

;; Keywords er identifikatorer som altid evaluerer til seg selv. Ofte
;; brukt som nøkler i hash-maps
(class :wtf)
:wtf

;; Symboler er identifikatorer som normal refererer til noe annet.
;; Brukes som oftest for å navngi funksjoner og verdier
(class 'hello)
str

  ;;; COLLECTIONS
  ;;;;;;;;;;;;;;;

;; Liste - LinkedList
(list 1 2 3 4)
'(1 2 3 4)

;; Vector - ArrayList
(vector :a :b :c :d)
[:a :b :c :d]

;; Map - HashMap
(hash-map :name "Erik" :age 31)
{:name "Erik" :age 31}

;; Set - HashSet
(hash-set true true false)
#{true false}


;; TODO: Make slide that shows example?
;; Collections i Clojure er "persistente" og "immutable"
;;
;; Persistente betyr at de altid tar vare på tidligere versjon av seg
;; selv når de blir endret
;;
;; Immutable
(def erik {:name "Erik" :age 31})

(dissoc erik :age)

erik

(identical? (dissoc erik :age) erik)


;; Collections er også funksjoner

(def erik {:name "Erik" :age 31})

(doc ifn)

(ifn? erik)

(erik :name)


  ;;; SEQUENCES
  ;;;;;;;;;;;;;;;

(def pinlig ["Byen" "er" "bergen" "og" "laget" "er" "..."])

(doc seq?)

(seq? pinlig)

(seq? (seq pinlig))

(doc first)

(first )



  ;;; SYMBOLER og VARS (se slide)
  ;;;;;;;;;;;;;;;

;; TODO: Lag slider som illustrerer
;; Symboler er identifikatorer som vanligvis brukes til å referere til
;; verdier eller funksjoner.

(ns mitt-namespace)

*ns*

(ns-publics *ns*)

(def foo "bar")

(ns-publics *ns*)

(deref (var foo))

;;

(def min-plus-funksjon
  (fn [argument] (+ min-funksjon )))

(in-ns 'user)
