(ns javabin-clojure-talk.core
  (:require [clojure.repl :refer [apropos dir doc find-doc pst source]]
            [clojure.walk :refer [macroexpand-all]]
            [clojure.string :refer [upper-case split]]
            [clojure.pprint :refer [pprint]]))


;; Funksjonskall, første element i lisen er funksjonen som skal kalles
;; etterfulgt av argumenter

(println "Hei JavaBin!")

;; Prefiks notasjon og parantesene tar litt tid å bli vant til, men
;; har en rekke fordeler

(+ 1 2 3 4)

;; Ingen presendens regler
;; (2 + 2 * 5) / 2

(/
 (+ 2
    (* 2 5))
 2)



  ;;;;;;;;;;;;;;;
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
(class (/ 1 3))

;; Keywords er identifikatorer som altid evaluerer til seg selv. Ofte
;; brukt som nøkler i hash-maps
(class :wtf)
:wtf

;; Symboler er identifikatorer som vanligvis brukes til å navngi
;; funksjoner og variabler

(class 'str)
(class str)
str



  ;;;;;;;;;;;;;;;
  ;;; NAVN
  ;;;;;;;;;;;;;;;

;; def navngir en global ting

(def speaker "Erik")

;; let brukes for å binde verdier til et symbol med lokalt scope

(let [speaker "Erik"
      age 31
      gender :male]
(str speaker " is a " age " year old " (name gender)))



  ;;;;;;;;;;;;;;;
  ;;; FUNKSJONER
  ;;;;;;;;;;;;;;;

;; Funksjoner er førsteklasses verdier i Clojure, og opprettes ved
;; hjelp av fn
(fn [x] (* x x))

( (fn [x] (* x x)) 100)

;; Alternativt #() hvor %1, %2 representerer argumenter
#(* % %)

;; defn brukes for å navngi funksjoner
(defn square [x]
  (* x x))

;; Funksjoner har ingen eksplisit return statement, returnerer verdien
;; av sisten evaluerte "form"

;; Samme funksjon kan ha ulikt antall parametre - "multiple arity"
(defn adder
  ([x] (adder 1 x))
  ([x y] (adder x y 0))
  ([x y z] (+ x y z)))

(adder 10)

(adder 10 10)

(adder 10 10 10)



  ;;;;;;;;;;;;;;;
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


;; Collections i Clojure er "persistente" og "immutable"
;;
;; Persistente betyr at de altid tar vare på tidligere versjon av seg
;; selv når de blir endret
;;
;; Immutable betyr at hver gang du gjør en endring får du tilbake en
;; ny collection
(def erik {:name "Erik" :age 31})

(dissoc erik :age)

erik


;; Collections er også funksjoner

(ifn? erik)

(erik :name)

;; Det samme gjelder forøvring for keywords
(:name erik)

  ;;;;;;;;;;;;;;;
  ;;; SEQUENCES
  ;;;;;;;;;;;;;;;

(def four ["one" "two" "three" "four"])

;; Seqs er logiske lister, og kan tenkes på som iteratorer uten intern
;; state

(seq? '(1 2 3 4))

;; Vectorer, Set og Map er collections, men ikke seqs. Må kalle seq først
(seq? (seq four))

(seq erik)

(seq #{:a :b :c :d})


;; Seq biblioteket i Clojure er veldig kraftig og inneholder
;; kjenninger som map, filter/foldl‘ og reduce
(map upper-case four)


;; Clojure er ikke et lazy språk som f.eks Haskel, men det har lazy
;; seqs.
;;
;; Lazyness betyr at funksjoner som returnerer seqs returnerer dem
;; incrementelt, etterhvert som veridene blir konsumert. Det samme
;; gjelder input til lazy funksjoner

(range 4)

(def r (range))

(class r)

(take 10 r)

(take 10 (drop 1000000 (range)))



  ;;;;;;;;;;;;;;;
  ;;; THREADING MACROS
  ;;;;;;;;;;;;;;;

;; Clojure inneholder en rekke macroer som gjør programmer enklere å
;; lese.

;; (take 10 (drop 1000000 (range)))

(->> (range)
     (drop 10000)
     (take 1))

;; ->> macroen tar verdien av det foregående form og "trere" den inn som siste
;; parameter i neste.
(->> (range)
     (drop 10000 ,,)
     (take 1 ,,))

;; -> tar verdien av det foregående form og "trere" den inn som første
;; parameter i neste
(-> "Erik Bakstad"
    (split #" ")
    (first)
    (upper-case))



  ;;;;;;;;;;;;;;;
  ;;; DESTRUCTURING
  ;;;;;;;;;;;;;;;

(def my-name {:firstname "Erik"
              :lastname "Bakstad"
              :age 31})

;; Destructuring av en hash-map
(let [{:keys [firstname lastname age]} my-name]
  (str firstname " is " age " years old and his last name is " lastname))

(def my-name ["Erik" "Bakstad" 30])

;; Destructuring av en vector
(let [[firstname lastname age] my-name]
  (str firstname " is " age " years old and his last name is " lastname))

;; Destructuring støtter nøsting
(let [[[fi] [li]] my-name]
  (str fi "." li))

;; Destructuring kan brukes i funksjonsparametre også
(defn count-args [& args]
  (count args))

(count-args 1 2 3 4)

;; Et vanlig mønster er å ta en optional map med configurasjon for å
;; endre oppførsel av funkjsonene
(defn kwargs-test [url & [{:keys [timeout retires]
                           :or {timeout 500 retires 2}}]]
  (pprint {:timeout timeout :retires retires}))


(kwargs-test "http://vg.no")

(kwargs-test "http://vg.no" {:timeout 1000 :retires 5})


  ;;;;;;;;;;;;;;;
  ;;; MACROS
  ;;;;;;;;;;;;;;;


;; Du kan tenkte på macroer som kode som skriver ny kode -  compile time


;; Vi så tidligere på "Threading macros"
(->> (range)
     (drop 10000)
     (take 1))

;; En threading macro tar cloure code (som er en liste) og "skriver
;; den om til ny kode
(macroexpand-1
 '(->> (range)
       (drop 10000)
       (take 1)))

;; Macroer evaluerer ikke parametrene sine før den kalles, noe som
;; gjør dem egnet til å skrive kontrollflyter


;; Vi er vant til at || "short circuits"
(or (do (println "First")
        true)
    (do (println "Second")
        true))

(macroexpand-1
 '(or (do (println "First")
          true)
      (do (println "Second")
          true)))


;; Clojure sin kjerne "special forms" er veldig liten, resten av
;; språket er byggd opp ved hjelp av disse kjernefunksjonene. Mye av
;; kontrollflyten er implementert ved hjelp av macroer

(doc if)

(doc when)

(source when)


;; Macroer kan kalle seg selv rekursivt, noe som ofte brukes for å
;; forenkel syntaks

(let [n 2]
  (cond
    (= 1 n) 1
    (= 2 n) 2
    (= 3 n) 3
    (= 4 n) 4
    (= 5 n) 5
    :else :unknown))


(pprint
 (macroexpand-1
  '(cond
     (= 1 n) 1
     (= 2 n) 2
     (= 3 n) 3
     (= 4 n) 4
     (= 5 n) 5
     :else :unknown)))

(pprint
 (macroexpand-all
  '(cond
     (= 1 n) 1
     (= 2 n) 2
     (= 3 n) 3
     (= 4 n) 4
     (= 5 n) 5
     :else :unknown)))

(source cond)

