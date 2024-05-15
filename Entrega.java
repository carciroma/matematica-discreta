
import java.lang.AssertionError;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

/*
 * Aquesta entrega consisteix en implementar tots els mètodes annotats amb el comentari "// TO DO".
 *
 * L'avaluació consistirà en:
 *
 * - Si el codi no compila, la nota del grup serà de 0.
 *
 * - Principalment, el correcte funcionament de cada mètode (provant amb diferents entrades). Teniu
 *   alguns exemples al mètode `main`.
 *
 * - Tendrem en compte la neteja i organització del codi. Un estandard que podeu seguir és la guia
 *   d'estil de Google per Java: https://google.github.io/styleguide/javaguide.html.  Algunes
 *   consideracions importants: indentació i espaiat consistent, bona nomenclatura de variables,
 *   declarar les variables el més aprop possible al primer ús (és a dir, evitau blocs de
 *   declaracions). També convé utilitzar el for-each (for (int x : ...)) enlloc del clàssic (for
 *   (int i = 0; ...)) sempre que no necessiteu l'índex del recorregut.
 *
 * Per com està plantejada aquesta entrega, no necessitau (ni podeu) utilitzar cap `import`
 * addicional, ni mètodes de classes que no estiguin ja importades. El que sí podeu fer és definir
 * tots els mètodes addicionals que volgueu (de manera ordenada i dins el tema que pertoqui).
 *
 * Podeu fer aquesta entrega en grups de com a màxim 3 persones, i necessitareu com a minim Java 8.
 * Per entregar, posau a continuació els vostres noms i entregau únicament aquest fitxer.
 * - Nom 1: Lucas Vidales Kartel
 * - Nom 2:
 * - Nom 3:
 *
 * L'entrega es farà a través d'una tasca a l'Aula Digital que obrirem abans de la data que se us
 * hagui comunicat i vos recomanam que treballeu amb un fork d'aquest repositori per seguir més
 * fàcilment les actualitzacions amb enunciats nous. Si no podeu visualitzar bé algun enunciat,
 * assegurau-vos de que el vostre editor de texte estigui configurat amb codificació UTF-8.
 */
public class Entrega {
  /*
   * Aquí teniu els exercicis del Tema 1 (Lògica).
   *
   * Els mètodes reben de paràmetre l'univers (representat com un array) i els predicats adients
   * (per exemple, `Predicate<Integer> p`). Per avaluar aquest predicat, si `x` és un element de
   * l'univers, podeu fer-ho com `p.test(x)`, que té com resultat un booleà (true si `P(x)` és
   * cert). Els predicats de dues variables són de tipus `BiPredicate<Integer, Integer>` i
   * similarment s'avaluen com `p.test(x, y)`.
   *
   * En cada un d'aquests exercicis us demanam que donat l'univers i els predicats retorneu `true`
   * o `false` segons si la proposició donada és certa (suposau que l'univers és suficientment
   * petit com per poder provar tots els casos que faci falta).
   */
  static class Tema1 {
    /*
     * És cert que ∀x ∃!y. P(x) -> Q(x,y) ?
     */
    static boolean exercici1(int[] universe, Predicate<Integer> p, BiPredicate<Integer, Integer> q) 
    {   
        for(int x : universe)
        {   
            boolean Y = false;
            for(int y : universe)
            {
                if(!p.test(x)) return false;
                
                if(q.test(x, y))
                {
                    //si se encuentra mas de una y, es falso
                    if(!Y)
                    {
                        Y = true;
                    } else { 
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /*
     * És cert que ∃!x ∀y. P(y) -> Q(x,y) ?
     */
    static boolean exercici2(int[] universe, Predicate<Integer> p, BiPredicate<Integer, Integer> q) 
    {
        for(int x : universe)
        {
            boolean X = false;
            for(int y : universe)
            {
                if(!p.test(y)) break;
                
                if(!q.test(x, y))
                {
                    //si se encuentra mas de una x, es falso
                    if(!X)
                    {
                        X = true;
                    }else{
                        return false;
    }
                }
            }
        }
        return true;
    }

    /*
     * És cert que ∃x,y ∀z. P(x,z) ⊕ Q(y,z) ?
     */
    static boolean exercici3(int[] universe, BiPredicate<Integer, Integer> p, BiPredicate<Integer, Integer> q) 
    {
        for(int x : universe)
        {
            for(int y : universe)
            {
                boolean Z = true;
                for(int z : universe)
                {
                    if(!(p.test(x, z) ^ q.test(y, z)))
                    {
                        //si se ha encontrado una z para la cual no se cumple la
                        //expresión, se pasa la siguiente iteracion
                        Z = false;
                        break;
                    }
                }
                //si se ve que para alguna x e y se cumple para toda z, el 
                //predicado es cierto
                if(Z) return true;
            }
        }
        return false;
    }

    /*
     * És cert que (∀x. P(x)) -> (∀x. Q(x)) ?
     */
    static boolean exercici4(int[] universe, Predicate<Integer> p, Predicate<Integer> q) 
    {
        boolean Ap = true;
        for(int x : universe)
        {
            //si encontramos una x para la cual no se cumpla la expresion,
            //se pasa a la siguiente iteracion.
            if(!p.test(x))
            {
                Ap = false;
                break;
            }
        }
        //si p->q, y tenemos que p = 1, la implicacion solo sera cierta si q = 1
        if(Ap) 
        {
            for(int x : universe) if(!q.test(x)) return false;
        }

      return true;
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // ∀x ∃!y. P(x) -> Q(x,y) ?

      assertThat(
          exercici1(
              new int[] { 2, 3, 5, 6 },
              x -> x != 4,
              (x, y) -> x == y
          )
      );

      assertThat(
          !exercici1(
              new int[] { -2, -1, 0, 1, 2, 3 },
              x -> x != 0,
              (x, y) -> x * y == 1
          )
      );

      // Exercici 2
      // ∃!x ∀y. P(y) -> Q(x,y) ?

      assertThat(
          exercici2(
              new int[] { -1, 1, 2, 3, 4 },
              y -> y <= 0,
              (x, y) -> x == -y
          )
      );

      assertThat(
          !exercici2(
              new int[] { -2, -1, 1, 2, 3, 4 },
              y -> y < 0,
              (x, y) -> x * y == 1
          )
      );

      // Exercici 3
      // ∃x,y ∀z. P(x,z) ⊕ Q(y,z) ?

      assertThat(
          exercici3(
              new int[] { 2, 3, 4, 5, 6, 7, 8 },
              (x, z) -> z % x == 0,
              (y, z) -> z % y == 1
          )
      );

      assertThat(
          !exercici3(
              new int[] { 2, 3 },
              (x, z) -> z % x == 1,
              (y, z) -> z % y == 1
          )
      );

      // Exercici 4
      // (∀x. P(x)) -> (∀x. Q(x)) ?

      assertThat(
          exercici4(
              new int[] { 0, 1, 2, 3, 4, 5, 8, 9, 16 },
              x -> x % 2 == 0, // x és múltiple de 2
              x -> x % 4 == 0 // x és múltiple de 4
          )
      );

      assertThat(
          !exercici4(
              new int[] { 0, 2, 4, 6, 8, 16 },
              x -> x % 2 == 0, // x és múltiple de 2
              x -> x % 4 == 0 // x és múltiple de 4
          )
      );
    }
  }

  /*
   * Aquí teniu els exercicis del Tema 2 (Conjunts).
   *
   * Per senzillesa tractarem els conjunts com arrays (sense elements repetits). Per tant, un
   * conjunt de conjunts d'enters tendrà tipus int[][].
   *
   * Les relacions també les representarem com arrays de dues dimensions, on la segona dimensió
   * només té dos elements. Per exemple
   *   int[][] rel = {{0,0}, {1,1}, {0,1}, {2,2}};
   * i també donarem el conjunt on està definida, per exemple
   *   int[] a = {0,1,2};
   *
   * Les funcions f : A -> B (on A i B son subconjunts dels enters) les representam donant el domini
   * int[] a, el codomini int[] b, i f un objecte de tipus Function<Integer, Integer> que podeu
   * avaluar com f.apply(x) (on x és d'a i el resultat f.apply(x) és de b).
   */
  static class Tema2 {
    /*
     * Comprovau si la relació `rel` definida sobre `a` és d'equivalència.
     *
     * Podeu soposar que `a` està ordenat de menor a major.
     */
    static boolean exercici1(int[] a, int[][] rel) {
        
        return reflexiva(a,rel) && simetrica(rel) && transitiva(rel);
    }

    /*
     * Comprovau si la relació `rel` definida sobre `a` és d'equivalència. Si ho és, retornau el
     * cardinal del conjunt quocient de `a` sobre `rel`. Si no, retornau -1.
     *
     * Podeu soposar que `a` està ordenat de menor a major.
     */
    static int exercici2(int[] a, int[][] rel) {
        if(!reflexiva(a,rel) || !simetrica(rel) || !transitiva(rel))
        {
            return -1;
    }

        int cardinal = 0;
       
        if(reflexiva(a,rel)) ++cardinal;
        if(simetrica(rel)) ++cardinal;
        if(transitiva(rel)) ++cardinal; 
        return cardinal;
    }

    /*
     * Comprovau si la relació `rel` definida entre `a` i `b` és una funció.
     *
     * Podeu soposar que `a` i `b` estan ordenats de menor a major.
     */
    static boolean exercici3(int[] a, int[] b, int[][] rel) 
    {
        //si el conjunto de la relacion generada entre a y b no tiene el mismo
        //numero de elementos que a, significa que no hay una imagen para todo
        //elemento de a
        return a.length == rel.length;
    }

    /*
     * Suposau que `f` és una funció amb domini `dom` i codomini `codom`.  Retornau:
     * - Si és exhaustiva, el màxim cardinal de l'antiimatge de cada element de `codom`.
     * - Si no, si és injectiva, el cardinal de l'imatge de `f` menys el cardinal de `codom`.
     * - En qualsevol altre cas, retornau 0.
     *
     * Podeu suposar que `dom` i `codom` estàn ordenats de menor a major.
     */
    static int exercici4(int[] dom, int[] codom, Function<Integer, Integer> f) 
    {
        //si es exhaustiva, buscamos el maximo cardinal de la antimagen de codom
        if(exhaustiva(dom,codom,f))
        {
            //array que almacenará los cardinales de las antimagenes
            var freq = new ArrayList<Integer>();
            //recorremos el array[][] buscando la frecuencia de cada antiimagen
            for(int b : codom)
            {
                int count = 0;
                for(int a : dom)
                {
                    if(b == f.apply(a)) count++;
                }
                freq.add(count);
            }

            //se devuelve el mayor de los cardinales de las antiimagenes
            return max(freq);
        }
        
        //si es inyectiva, devolvemos la resta del cardinal de la imagen de f
        //menos el cardinal de codom
        if(inyectiva(dom,codom,f)) 
        {
            return dom.length - codom.length;
        }

        return 0; // TO DO
    }
    
    //metodo que devuelve el valor maximo dentro de un ArrayList<Integer>
    static int max(ArrayList<Integer> freq)
    {
        int max = 0;
        for(int i : freq) max = (max>i) ? max : i;
        return max;
    }
    
    //metodo que comprueba la propiedad reflexiva de una relacion entre conjuntos
    public static boolean reflexiva(int[] a, int[][] rel)
    {
        //recorremos el array rel[][], comprobando que para todo elemento de a[]
        //le corresponda un elemento array[][] = [a,a]
        for(int x : a)
        {
            boolean reflexiva = false;
            for(int[] sub : rel)
            {
                if(sub[0]==x && sub[1]==x)
                {
                    reflexiva = true;
                }
            }
            //si no se ha encontrado al menos un array [a,a] dentro de rel,
            //no es reflexiva
            if(!reflexiva) return false;
        }
        return true;
    }
    
    //metodo que comprueba la propiedad simetrica de una relacion entre conjuntos
    public static boolean simetrica(int [][] rel)
    {
        //recorremos el array rel[][] en busqueda de un array [a,b] y [b,a]
        for(int[] sub1 : rel)
        {
            boolean simetrica = false;
            for(int[] sub2 : rel)
            {
                if(sub1[0]==sub2[1] && sub1[1]==sub2[0])
                {
                    simetrica = true;
                }
            }
            if(!simetrica) return false;
        }
        return true;
    }
    
    //metodo que comprueba la propiedad transitiva de una relacion entre conjuntos
    public static boolean transitiva(int[][] rel)
    {
        for(int[] sub1 : rel)
        {
            int a1 = sub1[0];
            int a2 = sub1[1];
            for(int[] sub2 : rel)
            {
                int b1 = sub2[0];
                int b2 = sub2[1];
                if(a2 == b1)
                {
                   boolean transitiva = false;
                   //buscamos un array [a,b] y [b,c], y hay que encontrar uno
                   //que sea [a,c]
                   for(int[] sub3 : rel)
                   {
                       int c1 = sub3[0];
                       int c2 = sub3[1];
                       if(a1==c1 && b2==c2)
                       {
                           transitiva = true;
                           break;
                       }
                   }
                   if(!transitiva) return false;
                }
            }
        }
        return true;
    }
    
    //metodo que comprueba si una funcion es exhaustiva
    public static boolean exhaustiva(int[] dom, int[] codom, Function<Integer, Integer> f)
    {
        //si el cardinal del dominio es menor que el del codominio, significa que
        //hay mas de una imagen para cada antiimagen
        if(dom.length<codom.length) return false;
        int count = 0;
        //comprobamos que para cada imagen hay una antiimagen nomás
        for(int b : codom)
        {
            for(int a : dom)
            {
                if(b == f.apply(a)) count++;
            }
        }    
        return count == codom.length;
    }
    
     //metodo que comprueba si una funcion es inyectiva
    public static boolean inyectiva(int[] dom, int[] codom, Function<Integer, Integer> f)
    {
        var subset = new ArrayList<Integer>();
        //si el dominio es mayor que el codominio, es que hay antiimagenes que
        //no tienen imagenes, por tanto, no es inyectiva
        if(dom.length>codom.length) return false;
        //comprobamos que toda antiimagen tenga una sola imagens
        for(int b : codom)
        {
            for(int a : dom)
            {
                int x = f.apply(a);
                
                //si b es imagen de a, la añadimos al list de antiimagenes
                if(b == x) subset.add(x);
                
                //si alguno de los elementos del dominio se repite más de una vez
                //es que no es inyectiva
                if(frecuencia(x,subset)>1) return false;
            }
        }
        return true;
    }
        
    //metodo que devuelve el numero de apariciones de un entero n dentro 
    //de un ArrayList<Integer>
    static int frecuencia(int n, ArrayList<Integer> freq)
    {
        int count = 0;
        for (int i : freq) 
        {
            if (i == n) count++;
        }
        return count;
    }

    
    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // `rel` és d'equivalencia?

      assertThat(
          exercici1(
              new int[] { 0, 1, 2, 3 },
              new int[][] { {0, 0}, {1, 1}, {2, 2}, {3, 3}, {1, 3}, {3, 1} }
          )
      );

      assertThat(
          !exercici1(
              new int[] { 0, 1, 2, 3 },
              new int[][] { {0, 0}, {1, 1}, {2, 2}, {3, 3}, {1, 2}, {1, 3}, {2, 1}, {3, 1} }
          )
      );

      // Exercici 2
      // si `rel` és d'equivalència, quants d'elements té el seu quocient?

      final int[] int09 = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };

      assertThat(
          exercici2(
            int09,
            generateRel(int09, int09, (x, y) -> x % 3 == y % 3)
          )
          == 3
      );

      assertThat(
          exercici2(
              new int[] { 1, 2, 3 },
              new int[][] { {1, 1}, {2, 2} }
          )
          == -1
      );

      // Exercici 3
      // `rel` és una funció?

      final int[] int05 = { 0, 1, 2, 3, 4, 5 };

      assertThat(
          exercici3(
            int05,
            int09,
            generateRel(int05, int09, (x, y) -> x == y)
          )
      );

      assertThat(
          !exercici3(
            int05,
            int09,
            generateRel(int05, int09, (x, y) -> x == y/2)
          )
      );

      // Exercici 4
      // el major |f^-1(y)| de cada y de `codom` si f és exhaustiva
      // sino, |im f| - |codom| si és injectiva
      // sino, 0

      assertThat(
          exercici4(
            int09,
            int05,
            x -> x / 4
          )
          == 0
      );

      assertThat(
          exercici4(
            int05,
            int09,
            x -> x + 3
          )
          == int05.length - int09.length
      );

      assertThat(
          exercici4(
            int05,
            int05,
            x -> (x + 3) % 6
          )
          == 1
      );
    }

    /// Genera un array int[][] amb els elements {a, b} (a de as, b de bs) que satisfàn pred.test(a, b)
    static int[][] generateRel(int[] as, int[] bs, BiPredicate<Integer, Integer> pred) {
      ArrayList<int[]> rel = new ArrayList<>();

      for (int a : as) {
        for (int b : bs) {
          if (pred.test(a, b)) {
            rel.add(new int[] { a, b });
          }
        }
      }

      return rel.toArray(new int[][] {});
    }
  }

  /*
   * Aquí teniu els exercicis del Tema 3 (Grafs).
   *
   * Donarem els grafs en forma de diccionari d'adjacència, és a dir, un graf serà un array
   * on cada element i-èssim serà un array ordenat que contendrà els índexos dels vèrtexos adjacents
   * al i-èssim vèrtex. Per exemple, el graf cicle C_3 vendria donat per
   *
   *  int[][] g = {{1,2}, {0,2}, {0,1}}  (no dirigit: v0 -> {v1, v2}, v1 -> {v0, v2}, v2 -> {v0,v1})
   *  int[][] g = {{1}, {2}, {0}}        (dirigit: v0 -> {v1}, v1 -> {v2}, v2 -> {v0})
   *
   * Podeu suposar que cap dels grafs té llaços.
   */
  static class Tema3 {
    /*
     * Retornau l'ordre menys la mida del graf (no dirigit).
     */
    static int exercici1(int[][] g) 
    {
        
        int V = g.length;
        int E = 0;
        
        for(int[] ar : g) E += ar.length;
            //dividimos el numero de aristas totales para contarlas bien
        return V - E/2; // TO DO
    }

    /*
     * Suposau que el graf (no dirigit) és connex. És bipartit?
     */
    static boolean exercici2(int[][] g) 
    {
        
        int bot = 0;
        
        //vemos cuantos nodos inferiores tiene el grafo bipartito
        for(int[] ar : g) if(ar.length == g[0].length) bot++;
        
        //Si el grafo bipartito es Kn,m, comprobamos los nodos inferiores n
        for(int i = 0; i<bot;i++)
        {
            //si no todos los nodos inferiores no son iguales, no es bipartito
            if(!Arrays.equals(g[0],g[i])) return false;
    }

        //hacemos la misma comprobacion anterior, pero para los nodos superiores
        for(int i = bot; i<g.length;i++)
        {
            if(!Arrays.equals(g[bot],g[i])) return false;
        }
            
      return true; // TO DO
    }

    /*
     * Suposau que el graf és un DAG. Retornau el nombre de descendents amb grau de sortida 0 del
     * vèrtex i-èssim.
     */
    static int exercici3(int[][] g, int i) 
    {
        boolean[] checked = new boolean[g.length];
        int n = dg0(g,i,checked);
        return n;
    }

    static int dg0(int[][]g , int i, boolean checked[])
    {
        checked[i] = true;
        int c = 0;
        if(g[i].length == 0) c = 1;
        
        for (int a : g[i]) {
            if (!checked[a]) c += dg0(g, a, checked);
        }
        return c;
    }
        

    /*
     * Donat un arbre arrelat (dirigit, suposau que l'arrel es el vèrtex 0), trobau-ne el diàmetre
     * del graf subjacent. Suposau que totes les arestes tenen pes 1.
     */
    static int exercici4(int[][] g) 
    {
        return diametro(g,0); // TO DO
    }

    static int diametro(int[][] g, int i)
    {
        if (g[i].length == 0) return 1;

        int d = 0;
        for (int a : g[i]) 
        {
          int subd =  diametro(g, a);
          if (subd + d > d) d +=  subd;
      }

      return d;
    }
   
    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      final int[][] undirectedK6 = {
        { 1, 2, 3, 4, 5 },
        { 0, 2, 3, 4, 5 },
        { 0, 1, 3, 4, 5 },
        { 0, 1, 2, 4, 5 },
        { 0, 1, 2, 3, 5 },
        { 0, 1, 2, 3, 4 },
      };

      /*
         1
      4  0  2
         3
      */
      final int[][] undirectedW4 = {
        { 1, 2, 3, 4 },
        { 0, 2, 4 },
        { 0, 1, 3 },
        { 0, 2, 4 },
        { 0, 1, 3 },
      };

      // 0, 1, 2 | 3, 4
      final int[][] undirectedK23 = {
        { 3, 4 },
        { 3, 4 },
        { 3, 4 },
        { 0, 1, 2 },
        { 0, 1, 2 },
      };

      /*
             7
             0
           1   2
             3   8
             4
           5   6
      */
      final int[][] directedG1 = {
        { 1, 2 }, // 0
        { 3 },    // 1
        { 3, 8 }, // 2
        { 4 },    // 3
        { 5, 6 }, // 4
        {},       // 5
        {},       // 6
        { 0 },    // 7
        {},
      };


      /*
              0
         1    2     3
            4   5   6
           7 8
      */

      final int[][] directedRTree1 = {
        { 1, 2, 3 }, // 0 = r
        {},          // 1
        { 4, 5 },    // 2
        { 6 },       // 3
        { 7, 8 },    // 4
        {},          // 5
        {},          // 6
        {},          // 7
        {},          // 8
      };

      /*
            0
            1
         2     3
             4   5
                6  7
      */

      final int[][] directedRTree2 = {
        { 1 },
        { 2, 3 },
        {},
        { 4, 5 },
        {},
        { 6, 7 },
        {},
        {},
      };

      assertThat(exercici1(undirectedK6) == 6 - 5*6/2);
      assertThat(exercici1(undirectedW4) == 5 - 2*4);

      assertThat(exercici2(undirectedK23));
      assertThat(!exercici2(undirectedK6));

      assertThat(exercici3(directedG1, 0) == 3);
      assertThat(exercici3(directedRTree1, 2) == 3);

      assertThat(exercici4(directedRTree1) == 5);
      assertThat(exercici4(directedRTree2) == 4);
    }
  }

  /*
   * Aquí teniu els exercicis del Tema 4 (Aritmètica).
   *
   * Per calcular residus podeu utilitzar l'operador %, però anau alerta amb els signes.
   * Podeu suposar que cada vegada que se menciona un mòdul, és major que 1.
   */
  static class Tema4 {
    /*
     * Donau la solució de l'equació
     *
     *   ax ≡ b (mod n),
     *
     * Els paràmetres `a` i `b` poden ser negatius (`b` pot ser zero), però podeu suposar que n > 1.
     *
     * Si la solució és x ≡ c (mod m), retornau `new int[] { c, m }`, amb 0 ⩽ c < m.
     * Si no en té, retornau null.
     */
    static int[] exercici1(int a, int b, int n) 
    {
        int d = mcd(a,n);
        //si d no divide b, no tiene solucion
        if(b % d != 0) return null;

        int [] xy = euclides(n,a);

        int sol = xy[1];
        int k = Math.abs(n/d);

        //mientras x sea negativa le incrementamos n/d
        while (sol < 0) sol += k;
        
        return new int[] { sol, k };
    }

    /*
     * Donau la solució (totes) del sistema d'equacions
     *
     *  { x ≡ b[0] (mod n[0])
     *  { x ≡ b[1] (mod n[1])
     *  { x ≡ b[2] (mod n[2])
     *  { ...
     *
     * Cada b[i] pot ser negatiu o zero, però podeu suposar que n[i] > 1. També podeu suposar
     * que els dos arrays tenen la mateixa longitud.
     *
     * Si la solució és de la forma x ≡ c (mod m), retornau `new int[] { c, m }`, amb 0 ⩽ c < m.
     * Si no en té, retornau null.
     */
    static int[] exercici2a(int[] b, int[] n) {
        int length = b.length;
        int total = 1;

        for (int a : n) total *= a;

        int sol = 0;

        for (int i = 0; i < length; i++) 
        {
            int a = total / n[i];
            int inv = inv(a, n[i]);
            
            if (inv == -1) return null;

            sol += ((b[i] % n[i] + n[i]) % n[i]) * a * inv;
            sol %= total;
        }

        return new int[] { (int) sol, (int) total };
    }

    /*
     * Donau la solució (totes) del sistema d'equacions
     *
     *  { a[0]·x ≡ b[0] (mod n[0])
     *  { a[1]·x ≡ b[1] (mod n[1])
     *  { a[2]·x ≡ b[2] (mod n[2])
     *  { ...
     *
     * Cada a[i] o b[i] pot ser negatiu (b[i] pot ser zero), però podeu suposar que n[i] > 1. També
     * podeu suposar que els tres arrays tenen la mateixa longitud.
     *
     * Si la solució és de la forma x ≡ c (mod m), retornau `new int[] { c, m }`, amb 0 ⩽ c < m.
     * Si no en té, retornau null.
     */
    static int[] exercici2b(int[] a, int[] b, int[] n) {
        int [] nb = new int[b.length];
        int [] nn = new int[n.length];
        int [] aux;
        for (int i = 0; i < a.length; i++) 
        {
            aux = transform(a[i], b[i], n[i]);
            if (aux == null) return null;
            nb[i] = aux[0];
            nn[i] = aux[1];
        }
        int [] sol = exercici2a(nb,nn);
        if (sol == null) return null;

        return sol;
    }

    //metodo que hace la transformacion necesaria para resolver una ecuacion
    //diofantica
    static int[] transform(int a, int b, int n) {
        int d = mcd(a, n);
        //si d no divide b, no hay solucion 
        if (b % d != 0) {
            return null;
        }
        
        //utilizamos euclides para encontrar la solucion a x=sol(mod k)
        int sol = euclides(n / d, a / d)[1] * (b / d) % (n / d);
        //k = |n/d|
        int k = Math.abs(n / d);
        
        //si sol es negativo, le sumamos k para obtener resto positivo
        //y devuelve un array [sol,k]
        return new int[] { sol < 0 ? sol + k : sol, k };
    }

    /*
     * Suposau que n > 1. Donau-ne la seva descomposició en nombres primers, ordenada de menor a
     * major, on cada primer apareix tantes vegades com el seu ordre. Per exemple,
     *
     * exercici4a(300) --> new int[] { 2, 2, 3, 5, 5 }
     *
     * No fa falta que cerqueu algorismes avançats de factorització, podeu utilitzar la força bruta
     * (el que coneixeu com el mètode manual d'anar provant).
     */
    static ArrayList<Integer> exercici3a(int n) 
    {
        var primos = new ArrayList<Integer>();
        int i = 2;
        //vemos cuantos primos hay en n, y añadimos los primos encontrados a
        //un arraylist
        while (n > 1) 
        {
            if (n % i == 0) 
            {
                primos.add(i);
                n /= i;
            } else {
                i++;
            }
        }
        return primos;
    }

    /*
     * Retornau el nombre d'elements invertibles a Z mòdul n³.
     *
     * Alerta: podeu suposar que el resultat hi cap a un int (32 bits a Java), però n³ no té perquè.
     * De fet, no doneu per suposat que pogueu tractar res més gran que el resultat.
     *
     * No podeu utilitzar `long` per solucionar aquest problema. Necessitareu l'exercici 3a.
     * No, tampoc podeu utilitzar `double`.
     */
    static int exercici3b(int n) 
    {
        //obtebemos el arraylist de primos hasta n 
        var invertibles = exercici3a(n);
        int f = 1;
        
        //calculamos el numero de primos que hay dentro de n^3
        for (int i = 0; i < invertibles.size(); i++) {
            int p = invertibles.get(i);
            int count = 1;
            while (i + 1 < invertibles.size() && invertibles.get(i + 1) == p) 
            {
                count++;
                i++;
    }
            f *= Math.pow(p, count) - Math.pow(p, count - 1);
        }
        f *= n * n;
        return f;
    }

    //metodo que devuelve el maximo comun divisor de dos enteros
    private static int mcd(int a, int b) 
    {
        //si b divide a, se devuelve b, sino, se recurre a la funcion otra vez
        return (a%b==0)? b : mcd(b,a%b);  
    }
    
    //metodo que aplica el algoritmo de euclides 
    static int[] euclides(int ni, int ai) 
    {
        boolean neg = ai < 0;
        ai = Math.abs(ai);

        int[] n = { ni, 0, 1, 0 };
        int[] a = { ai, 0, 0, 1 };
        int[] aux;

        while (n[0] % a[0] != 0) {
            int quotient = n[0] / a[0];
            aux = Arrays.copyOf(a, 4);
            a[0] = n[0] % a[0];
            a[2] = n[2] - a[2] * quotient;
            a[3] = n[3] - a[3] * quotient;
            n = aux.clone();
        }

        if (neg) {
            a[3] *= -1;
        }

        return new int[] { a[2], a[3] };
    }
    
    //metod que busca el inverso de un numero a dentro de un modulo n
    static int inv(int a, int n) 
    {
        for (int i = 0; i < n; i++) 
        {
            //recorre el conjunto Zn en busca de algun elemento i que,
            //multiplicado por a, el resultado mod n sea 1, entonces i es inverso
            if ((((a % n) * (i % n)) % n) == 1) 
            {
                return i;
            }
        }
        //si no tiene, se devuelve un -1
        return -1;
    }
    

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      assertThat(Arrays.equals(exercici1(17, 1, 30), new int[] { 23, 30 }));
      assertThat(Arrays.equals(exercici1(-2, -4, 6), new int[] { 2, 3 }));
      assertThat(exercici1(2, 3, 6) == null);

      assertThat(
        exercici2a(
          new int[] { 1, 0 },
          new int[] { 2, 4 }
        )
        == null
      );

      assertThat(
        Arrays.equals(
          exercici2a(
            new int[] { 3, -1, 2 },
            new int[] { 5,  8, 9 }
          ),
          new int[] { 263, 360 }
        )
      );

      assertThat(
        exercici2b(
          new int[] { 1, 1 },
          new int[] { 1, 0 },
          new int[] { 2, 4 }
        )
        == null
      );

      assertThat(
        Arrays.equals(
          exercici2b(
            new int[] { 2,  -1, 5 },
            new int[] { 6,   1, 1 },
            new int[] { 10,  8, 9 }
          ),
          new int[] { 263, 360 }
        )
      );

      assertThat(exercici3a(10).equals(List.of(2, 5)));
      assertThat(exercici3a(1291).equals(List.of(1291)));
      assertThat(exercici3a(1292).equals(List.of(2, 2, 17, 19 )));

      assertThat(exercici3b(10) == 400);

      // Aquí 1292³ ocupa més de 32 bits amb el signe, però es pot resoldre sense calcular n³.
      assertThat(exercici3b(1292) == 961_496_064);

      // Aquest exemple té el resultat fora de rang
      //assertThat(exercici3b(1291) == 2_150_018_490);
    }
  }

  /*
   * Aquest mètode `main` conté alguns exemples de paràmetres i dels resultats que haurien de donar
   * els exercicis. Podeu utilitzar-los de guia i també en podeu afegir d'altres (no els tendrem en
   * compte, però és molt recomanable).
   *
   * Podeu aprofitar el mètode `assertThat` per comprovar fàcilment que un valor sigui `true`.
   */
  public static void main(String[] args) {
    Tema1.tests();
    Tema2.tests();
    Tema3.tests();
    Tema4.tests();
  }

    static void assertThat(boolean b) 
    {
    if (!b)
      throw new AssertionError();
  }
    
}

// vim: set textwidth=100 shiftwidth=2 expandtab :