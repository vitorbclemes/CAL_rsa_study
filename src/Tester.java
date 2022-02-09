package src;
import java.util.Scanner;

public class Tester {
  static int bitlenStart = 30;
  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    String message;
    int bitlenEnd;
    RSA rsa = null;

    try {
      System.out.println("Digite a mensagem a ser codificada:");
      message = s.nextLine();
      System.out.println("Digite o bitlen máximo (inicio em " + bitlenStart + "):");
      bitlenEnd = s.nextInt();
    } finally{
      s.close();
    }

    for(int bits = bitlenStart; bits <= bitlenEnd;bits++){
      rsa = new RSA(bits, message);
      rsa.start();
    }    
  }
}
