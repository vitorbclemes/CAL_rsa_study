import matplotlib.pyplot as plt
import os

path = os.path.abspath(__file__)
  
bitlen = []
time = []
for line in open('brute.txt', 'r'):
    lines = [i for i in line.split()]
    bitlen.append(lines[0])
    time.append(int(lines[1]))
      
plt.title("RSA Brute-forcing")
plt.xlabel('Numero de bits')
plt.ylabel('Tempo(em milissegundos)')
plt.yticks(time)
plt.plot(bitlen, time, marker = 'o', c = 'g')
plt.savefig('graphs/brute.png')

bitlen = []
time = []
for line in open('cipher.txt', 'r'):
    lines = [i for i in line.split()]
    bitlen.append(lines[0])
    time.append(int(lines[1]))
      
plt.title("RSA - Ciphering")
plt.xlabel('Numero de bits')
plt.ylabel('Tempo(em nanossegundos)')
plt.yticks(time)
plt.plot(bitlen, time, marker = 'o', c = 'g')
plt.savefig('graphs/cipher.png')


bitlen = []
time = []
for line in open('key.txt', 'r'):
    lines = [i for i in line.split()]
    bitlen.append(lines[0])
    time.append(int(lines[1]))
      
plt.title("RSA - Key Generate")
plt.xlabel('Numero de bits')
plt.ylabel('Tempo(em milissegundos)')
plt.yticks(time)
plt.plot(bitlen, time, marker = 'o', c = 'g')
plt.savefig('graphs/key.png')
