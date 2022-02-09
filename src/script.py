import matplotlib.pyplot as plt
import numpy as np

'''
# Brute
plt1 = plt;
bitlen = []
time = []
for line in open('../brute.txt', 'r'):
    lines = [i for i in line.split()]
    bitlen.append(int(lines[0]))
    time.append(int(lines[1]))

plt1.xticks(np.arange(bitlen[0], bitlen[len(bitlen)-1]+1, step=10))
plt1.yticks(np.arange(time[0], time[len(bitlen)-1]+1, step=1000))
plt1.title("RSA Brute-forcing")
plt1.xlabel('Numero de bits')
plt1.ylabel('Tempo(em milissegundos)')
plt1.plot(bitlen, time)
plt1.savefig('graphs/brute.png')


# Prime validation
bitlen = []
time = []
for line in open('../primeValidation.txt', 'r'):
    lines = [i for i in line.split()]
    bitlen.append(int(lines[0]))
    time.append(int(lines[1]))

plt2 = plt;
plt2.xticks(np.arange(bitlen[0], bitlen[len(bitlen)-1]+1, step=10))
plt2.yticks(np.arange(time[0], time[len(bitlen)-1]+1, step=1000))
plt2.title("RSA - Prime Validation")
plt2.xlabel('Numero de bits')
plt2.ylabel('Tempo(em milissegundos)')
plt2.plot(bitlen, time)
plt2.savefig('graphs/primeValidation.png')

#Cipher
bitlen = []
time = []
for line in open('../cipher.txt', 'r'):
    lines = [i for i in line.split()]
    bitlen.append(int(lines[0]))
    time.append(int(lines[1]))

plt3 = plt;
plt3.xticks(np.arange(bitlen[0], bitlen[len(bitlen)-1], step=10))
plt3.title("RSA - Cipher Proccess")
plt3.xlabel('Numero de bits')
plt3.ylabel('Tempo(em nanossegundos)')
plt3.plot(bitlen, time)
plt3.savefig('graphs/cipher.png')

# Decipher with key
bitlen = []
time = []
for line in open('../decipher.txt', 'r'):
    lines = [i for i in line.split()]
    bitlen.append(int(lines[0]))
    time.append(int(lines[1]))

plt.xticks(np.arange(bitlen[0], bitlen[len(bitlen)-1], step=10))
plt.title("RSA - Decipher Proccess.txt")
plt.xlabel('Numero de bits')
plt.ylabel('Tempo(em nanossegundo)')
plt.plot(bitlen, time)
plt.savefig('graphs/decipher.png')
'''

# Generate Key
bitlen = []
time = []
for line in open('../keyGen.txt', 'r'):
    lines = [i for i in line.split()]
    bitlen.append(int(lines[0]))
    time.append(int(lines[1]))

plt.xticks(np.arange(bitlen[0],bitlen[len(bitlen)-1], step=10))
plt.yticks(np.arange(time[0], time[len(bitlen)-1]+1, step=1000))
plt.title("RSA - Key Generate")
plt.xlabel('Numero de bits')
plt.ylabel('Tempo(em milissegundos)')
plt.plot(bitlen, time)
plt.savefig('graphs/keyGen.png')
