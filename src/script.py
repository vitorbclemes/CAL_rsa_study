import matplotlib.pyplot as plt


# Brute
bitlen = []
time = []
for line in open('../brute.txt', 'r'):
    lines = [i for i in line.split()]
    bitlen.append(lines[0])
    time.append(int(lines[1]))
      
plt.title("RSA Brute-forcing")
plt.xlabel('Numero de bits')
plt.ylabel('Tempo(em milissegundos)')
plt.plot(bitlen, time, marker = 'o', c = 'g')
plt.savefig('graphs/brute.png')

# Cipher
bitlen = []
time = []
for line in open('../cipher.txt', 'r'):
    lines = [i for i in line.split()]
    bitlen.append(lines[0])
    time.append(int(lines[1]))
      
plt.title("RSA - Cipher Proccess")
plt.xlabel('Numero de bits')
plt.ylabel('Tempo(em milissegundos)')
plt.plot(bitlen, time, marker = 'o', c = 'g')
plt.savefig('graphs/cipher.png')

# Decipher with key
bitlen = []
time = []
for line in open('../decipher.txt', 'r'):
    lines = [i for i in line.split()]
    bitlen.append(lines[0])
    time.append(int(lines[1]))
      
plt.title("RSA - Decipher Proccess.txt")
plt.xlabel('Numero de bits')
plt.ylabel('Tempo(em milissegundos)')
plt.plot(bitlen, time, marker = 'o', c = 'g')
plt.savefig('graphs/decipher.png')

# Generate Key
bitlen = []
time = []
for line in open('../keyGen.txt', 'r'):
    lines = [i for i in line.split()]
    bitlen.append(lines[0])
    time.append(int(lines[1]))
      
plt.title("RSA - Cipher Proccess")
plt.xlabel('Numero de bits')
plt.ylabel('Tempo(em milissegundos)')
plt.plot(bitlen, time, marker = 'o', c = 'g')
plt.savefig('graphs/cipher.png')

#Prime validation
bitlen = []
time = []
for line in open('../primeValidation.txt', 'r'):
    lines = [i for i in line.split()]
    bitlen.append(lines[0])
    time.append(int(lines[1]))
      
plt.title("RSA - Cipher Proccess")
plt.xlabel('Numero de bits')
plt.ylabel('Tempo(em milissegundos)')
plt.plot(bitlen, time, marker = 'o', c = 'g')
plt.savefig('graphs/cipher.png')
