package src;
// Funcao adaptada da versão do algoritimo RSA disponivel em https://pt.wikipedia.org/wiki/RSA_(sistema_criptogr%C3%A1fico)
// Documentação do BigInteger para conferência disponível em https://docs.oracle.com/javase/7/docs/api/java/math/BigInteger.html
import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;

class RSA {
  static int bitlen = 256;
  static BigInteger ZERO = BigInteger.ZERO;
  static BigInteger ONE = BigInteger.ONE;
  static BigInteger TWO = BigInteger.TWO;
  static SecureRandom secureRandom = new SecureRandom();

  public static void main(String args[]) {
    // Declaracao das variaveis
    BigInteger n, d, e;
    String MESSAGE = "Posso escrever qlq coisa aq";
    String CIPHER_MESSAGE = null;
    String DECIPHERED_MESSAGE = null;
    
    /*
      O construtor new Biginter(bitLenght,certainty,rnd) gera um numero PROVAVELMENTE primo, 
      onde essa probabilidade é de ( 1 - 1/2 ^ certainty)
    */
    Instant generate_key_start = Instant.now();
    BigInteger p = new BigInteger(bitlen /2, 100, secureRandom); 
    BigInteger q = new BigInteger(bitlen /2, 100, secureRandom); 

    // Teste de primalidade polinomial
    while(!isPrime(p) || !isPrime(q)){
      p = new BigInteger(bitlen / 2, 100, secureRandom);
      q = new BigInteger(bitlen / 2, 100, secureRandom);
    }
    
    // Valor de n = pq
    n = p.multiply(q);

    // Função Totiente de Euler phi(n) = (p-1) * (q-1)
    BigInteger phi = (p.subtract(ONE)).multiply(q.subtract(ONE));

    /*
      "e" pertencente aos inteiros, 
      1 < "e" < phi(n),
      "e" e phi(n) sao primos entre si,ou seja, MDC(e,phi(n)) = {1};
    */
    e = new BigInteger("3");
    while(phi.gcd(e).intValue() > 1) e = e.add(TWO);

    // d deve ser o inverso multiplicativo de "e"
    d = euclidian_extended(e,phi);

    Instant generate_key_end = Instant.now();
    
    System.out.println("Chave Publica (n,e)");
    System.out.println("n:"+n);
    System.out.println("e:"+e);
    System.out.println();
    System.out.println("Chave Privada (p,q,d)");
    System.out.println("p:"+p);
    System.out.println("q:"+q);
    System.out.println("d:"+d);
    System.out.println();
    
    
    // Define a mensagem cifrada
    Instant generate_cipher_start = Instant.now();
    CIPHER_MESSAGE = new BigInteger(MESSAGE.getBytes()).modPow(e, n).toString();
    System.out.println("MENSAGEM CIFRADA:\n"+ CIPHER_MESSAGE);
    Instant generate_cipher_end = Instant.now();
    
    
    // Define a mensagem decifrada
    Instant decipher_start = Instant.now();
    DECIPHERED_MESSAGE = new String(new BigInteger(CIPHER_MESSAGE).modPow(d, n).toByteArray());
    System.out.println("MENSAGEM DECIFRADA:\n" + DECIPHERED_MESSAGE);
    Instant decipher_end = Instant.now();


    Instant brute_force_start = Instant.now();
    System.out.println("TENTANDO BRUTAR A CRIPTOGRAFIA ATRAVES DA FATORACAO EM PRIMOS:");
    System.out.println("Buscando p e q atraves do metodo de Pollard`s Rho...");
    System.out.println("\nguessed_p:");
    // Comeca em 2
    BigInteger guessed_p = rho(n,TWO);
    System.out.println(guessed_p);
    
    System.out.println("\nguessed_q:");
    // Comeca em p + 1
    BigInteger guessed_q = rho(n,guessed_p.add(ONE));
    System.out.println(guessed_q);

    System.out.println("Tendo guessed_q e guessed_p, basta multiplicarmos e temos guessed_d");
    BigInteger guessed_d = guessed_p.multiply(guessed_q);
    System.out.println("guessed_d:"+guessed_d);
    

    System.out.println("Tentando decifrar a mensagem:");
    String GUESSED_DECIPHERED_MESSAGE;
    GUESSED_DECIPHERED_MESSAGE = new String(new BigInteger(CIPHER_MESSAGE).modPow(guessed_d, n).toByteArray());
    System.out.println("MENSAGEM DECIFRADA:\n" + GUESSED_DECIPHERED_MESSAGE);

    System.out.println("Funcionou? --> " + GUESSED_DECIPHERED_MESSAGE == DECIPHERED_MESSAGE);
    Instant brute_force_end = Instant.now();
    

    // file area
    File keyFile;
    File cipherFile;
    File decipherFile;
    File bruteFile;
    
    PrintWriter keyWriter = null;
    PrintWriter cipherWriter = null;
    PrintWriter decipherWriter = null;
    PrintWriter bruteWriter = null;
    try{

      // key
      keyFile = new File("../results/key.txt");
      keyWriter = new PrintWriter(keyFile);
      keyWriter.println(Duration.between(generate_key_start, generate_key_end).toString());
      
      // cipher
      cipherFile = new File("../results/cipher.txt");
      cipherWriter = new PrintWriter(cipherFile);
      cipherWriter.println(Duration.between(generate_cipher_start, generate_cipher_end).toString());

      // decipher
      decipherFile = new File("../results/decipher.txt");
      decipherWriter = new PrintWriter(decipherFile);
      decipherWriter.println(Duration.between(decipher_start, decipher_end).toString());
    
      // brute
      bruteFile = new File("../results/brute.txt");
      bruteWriter = new PrintWriter(bruteFile);
      bruteWriter.println(Duration.between(brute_force_start, brute_force_end).toString());
    
    } catch(IOException f) {
        System.err.println(f);
    } finally {
        if (keyWriter!=null) keyWriter.close();
        if (cipherWriter!=null) cipherWriter.close();
        if (bruteWriter!=null) bruteWriter.close();
    }
 }


 public static boolean isPrime(BigInteger NUMBER) {
    
    //Metodo nativo do BigInteger,retorna true se ele provavelmente for primo, falso se definitivamente nao for.
    if (!NUMBER.isProbablePrime(5))
        return false;

    // Verifica se é par
    if (!TWO.equals(NUMBER) && ZERO.equals(NUMBER.mod(TWO)))
        return false;

    /*
    Verificacao com Pollard's Rho, porem nao executa em tempo hábil caso o bitlen seja muito grande
    
    if(rho(NUMBER,TWO).compareTo(NUMBER)!=0){
      System.out.println("entrou");
      return false;
    };
    
    */
    // Caso passe em todos os testes
    return true;
  }

  // Algoritimo de euclides estendido
  public static BigInteger euclidian_extended(BigInteger e,BigInteger phi) {
    BigInteger d_old = ZERO;
    BigInteger r_old = phi;
    BigInteger d_new = ONE;
    BigInteger r_new = e;
    
    while(r_new.compareTo(ZERO) == 1){
      BigInteger a;
      a = r_old.divide(r_new);
      BigInteger aux_dold = d_old;
      BigInteger aux_dnew = d_new;

      d_old = aux_dnew;
      d_new = aux_dold.subtract(a.multiply(aux_dnew));
      
      BigInteger aux_rold = r_old;
      BigInteger aux_rnew = r_new;

      r_old = aux_rnew;
      r_new = aux_rold.subtract(a.multiply(aux_rnew));
    }
    
    return d_old.mod(phi);
  }

  // Algoritimo Pollard's Rho, disponivel em https://en.wikipedia.org/wiki/Pollard%27s_rho_algorithm 
  private static BigInteger rho(BigInteger n, BigInteger x) {
    BigInteger y = x;
    BigInteger d = BigInteger.ONE;
    while (d.equals(BigInteger.ONE)) {
        x = x.modPow(BigInteger.TWO, n).add(BigInteger.ONE);
        y = y.modPow(BigInteger.TWO, n).add(BigInteger.ONE);
        y = y.modPow(BigInteger.TWO, n).add(BigInteger.ONE);
        d = x.subtract(y).abs().gcd(n);
    }
    return d;
}
}