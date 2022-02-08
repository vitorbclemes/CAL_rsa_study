package src;

import java.math.BigInteger;
import java.security.SecureRandom;

public class PollardRho {
  private BigInteger guessed_q;
  private BigInteger guessed_p;
  private SecureRandom secureRandom;

  public PollardRho(BigInteger n){
    setGuessed_p(rho_breaker(n));
    setGuessed_q(rho_breaker(n));
    while(getGuessed_p().equals(guessed_q)){
      setGuessed_q(rho_breaker(n));
    }
  }

  public BigInteger getGuessed_q() {
    return guessed_q;
  }

  public BigInteger getGuessed_p() {
    return guessed_p;
  }

  public void setGuessed_q(BigInteger guessed_q) {
    this.guessed_q = guessed_q;
  }

  public void setGuessed_p(BigInteger guessed_p) {
    this.guessed_p = guessed_p;
  }
  
  public BigInteger rho_breaker(BigInteger n) {
    BigInteger divisor;
    BigInteger c  = new BigInteger(n.bitLength(), secureRandom);
    BigInteger x  = new BigInteger(n.bitLength(), secureRandom);
    BigInteger xx = x;

    do {
        x  =  x.multiply(x).mod(n).add(c).mod(n);
        xx = xx.multiply(xx).mod(n).add(c).mod(n);
        xx = xx.multiply(xx).mod(n).add(c).mod(n);
        divisor = x.subtract(xx).gcd(n);
    } while((divisor.compareTo(BigInteger.ONE)) == 0);
    
    return divisor;
  }
}

