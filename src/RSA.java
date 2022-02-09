package src;
// Algorítimo adaptada da versão do algoritimo RSA disponivel em https://pt.wikipedia.org/wiki/RSA_(sistema_criptogr%C3%A1fico)
// Documentação do BigInteger para conferência disponível em https://docs.oracle.com/javase/7/docs/api/java/math/BigInteger.html
import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

class RSA {
  // Declaracao das variaveis
  private BigInteger ZERO = BigInteger.ZERO;
  private BigInteger ONE = BigInteger.ONE;
  private BigInteger TWO = BigInteger.TWO;
  private SecureRandom secureRandom = new SecureRandom();
  
  int bitlen;
  String MESSAGE;
  String CIPHER_MESSAGE = null;
  String DECIPHERED_MESSAGE = null;
  
  BigInteger n, d, e;
  BigInteger p,q;
  BigInteger phi;
  
  BigInteger guessed_p,guessed_q;
  BigInteger guessed_phi,guessed_d;
  String GUESSED_DECIPHERED_MESSAGE;

  // define os valores padroes
  public RSA(int bits,String message) {
    this.MESSAGE = message;
    this.bitlen = bits;
  }

  public void start(){
    
    //Gera as chaves
    generateKey();
    
    // Define a mensagem cifrada
    cipher_message();
    // Define a mensagem decifrada
    decipher_message();

    // Quebra a criptografia
    brute_force();

    // Exibe log no console
    printResults();
 }

  private void generateKey(){
    Instant start_keyGen,end_keyGen;
    Instant start_prime_validation,end_prime_validation;
    /*
      O construtor new Biginter(bitLenght,certainty,rnd) gera um numero PROVAVELMENTE primo, 
      onde essa probabilidade é de ( 1 - 1/2 ^ certainty)
    */
    start_keyGen = Instant.now();
    this.p = new BigInteger(bitlen /2, 100, secureRandom); 
    this.q = new BigInteger(bitlen /2, 100, secureRandom); 


    // Teste de primalidade polinomial
    start_prime_validation = Instant.now();
    while(!isPrime(p))
      this.p = new BigInteger(bitlen / 2, 100, secureRandom);
    
    while(!isPrime(q))
      this.q = new BigInteger(bitlen / 2, 100, secureRandom);
    end_prime_validation = Instant.now();

    // Valor de n = pq
    this.n = p.multiply(q);

    // Função Totiente de Euler phi(n) = (p-1) * (q-1)
    this.phi = (this.p.subtract(ONE)).multiply(this.q.subtract(ONE));

    /*
      "e" pertencente aos inteiros, 
      1 < "e" < phi(n),
      "e" e phi(n) sao primos entre si,ou seja, MDC(e,phi(n)) = {1};
    */
    this.e = new BigInteger("3");
    while(phi.gcd(e).intValue() > 1) this.e = this.e.add(TWO);

    // d deve ser o inverso multiplicativo de "e"
    this.d = euclidian_extended(this.e,this.phi);
    end_keyGen = Instant.now();

    writeOnFileInMillis(new File("keyGen.txt"), start_keyGen, end_keyGen);
    writeOnFileInMillis(new File("primeValidation.txt"), start_prime_validation, end_prime_validation);
  }

  private void brute_force(){
    Instant brute_force_start;
    Instant brute_force_end;

    brute_force_start = Instant.now(); // temporizador
    this.guessed_p = rho(n);
    this.guessed_q = this.n.divide(this.guessed_p);
    this.guessed_phi = (guessed_p.subtract(ONE)).multiply(guessed_q.subtract(ONE));
    this.guessed_d = euclidian_extended(e,guessed_phi);
    this.GUESSED_DECIPHERED_MESSAGE = new String(new BigInteger(CIPHER_MESSAGE).modPow(guessed_d, n).toByteArray());
    brute_force_end = Instant.now(); // temporizador
    
    // Escreve no arquivo
    writeOnFileInMillis(new File("brute.txt"),brute_force_start,brute_force_end);
  }

  private void cipher_message(){
    Instant start_cipher,end_cipher;
    start_cipher = Instant.now();
    this.CIPHER_MESSAGE = new BigInteger(this.MESSAGE.getBytes()).modPow(e, n).toString();
    end_cipher = Instant.now();

    writeOnFileInNanos(new File("cipher.txt"), start_cipher, end_cipher);
  }

  private void decipher_message(){
    Instant start_decipher,end_decipher;
    start_decipher = Instant.now();
    DECIPHERED_MESSAGE = new String(new BigInteger(CIPHER_MESSAGE).modPow(d, n).toByteArray());
    end_decipher = Instant.now();
    writeOnFileInNanos(new File("decipher.txt"), start_decipher, end_decipher);
  }

  public  boolean isPrime(BigInteger NUMBER) {
    //Metodo nativo do BigInteger,retorna true se ele provavelmente for primo, falso se definitivamente nao for.
    if (!NUMBER.isProbablePrime(100))
      return false;

    // Verifica se é par
    if (!TWO.equals(NUMBER) && ZERO.equals(NUMBER.mod(TWO)))
      return false;

    // Verificacao com Pollard's Rho
    if(rho(NUMBER).compareTo(NUMBER)!=0){
      return false;
    }
    return true;
  }

  // Algoritimo de euclides estendido
  public  BigInteger euclidian_extended(BigInteger e,BigInteger phi) {
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
  private  BigInteger rho(BigInteger n) {
    BigInteger divisor;
    BigInteger c  = new BigInteger(n.bitLength(), secureRandom);
    BigInteger x  = new BigInteger(n.bitLength(), secureRandom);
    BigInteger xx = x;

    do {
        x  =  x.multiply(x).mod(n).add(c).mod(n);
        xx = xx.multiply(xx).mod(n).add(c).mod(n);
        xx = xx.multiply(xx).mod(n).add(c).mod(n);
        divisor = x.subtract(xx).gcd(n);
    } while((divisor.compareTo(ONE)) == 0);
    return divisor;
  }

  private void writeOnFileInMillis(File file,Instant start,Instant stop){
    PrintWriter printWriter = null;
    try{
      printWriter = new PrintWriter(new FileWriter(file,true));
      printWriter.print(bitlen + " ");
      printWriter.println(Duration.between(start, stop).toMillis());
    
    } catch(IOException f) {
        System.err.println(f);
    } finally {
        if (printWriter!=null) printWriter.close();
    }
  }

  private void writeOnFileInNanos(File file,Instant start,Instant stop){
    PrintWriter printWriter = null;
    try{
      printWriter = new PrintWriter(new FileWriter(file,true));
      printWriter.print(bitlen + " ");
      printWriter.println(Duration.between(start, stop).toNanos());
    
    } catch(IOException f) {
        System.err.println(f);
    } finally {
        if (printWriter!=null) printWriter.close();
    }
  }

  private void printResults(){
    System.out.println("Bitlen: " + this.bitlen);
    System.out.println("Mensagem: " + this.MESSAGE);

    System.out.println("\nChave Publica(n,e):");
    System.out.println("n:" + this.n);
    System.out.println("e:" + this.e);

    System.out.println("\nChave Privada(p,q,d");
    System.out.println("p:" + this.p);
    System.out.println("q:" + this.q);
    System.out.println("d:" + this.d);

    System.out.println("\nUtilizando as chaves...");
    System.out.println("Mensagem cifrada: " + this.CIPHER_MESSAGE);
    System.out.println("Mensagem decifrada: " +this.DECIPHERED_MESSAGE);

    System.out.println("\nBrutando os valores...");
    System.out.println("q descoberto:" + this.guessed_q);
    System.out.println("p descoberto:" + this.guessed_p);
    System.out.println("d descoberto:" + this.guessed_d);
    System.out.println("Mensagem decifrada pelo brute:" +this.GUESSED_DECIPHERED_MESSAGE);
    System.out.println("------------------------------------------------------");
  }
}