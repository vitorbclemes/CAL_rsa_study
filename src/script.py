import matplotlib.pyplot as plt
import os
  
bitlen = []
time = []
for line in open('brute.txt', 'r'):
    lines = [i for i in line.split()]
    bitlen.append(lines[0])
    time.append(int(lines[1]))
      
plt.title("RSA Brute-forcing")
plt.xlabel('Numero de bits')
plt.ylabel('Tempo(em milissegundos)')
plt.plot(bitlen, time, marker = 'o', c = 'g')
plt.savefig('graphs/brute.png')
