# ESTUDO DO RSA
Um estudo do algoritimo de criptografia RSA, bem como sua tentativa de quebrar utilizando metodo de fatoração em primos.
Para isso, foram realizadas diversas pesquisas. A ideia era observar a complexidade de tempo e de execução ao tentar
quebrar a criptografia atraves de um método brute-force.
# Como realizer os testes:
1)Entre na pasta "src" atraves do comando:
cd src

2)Altere o bitlen e a mensagem como desejar dentro do arquivo RSA.java. Fique ciente que
a codificação não funcionará dependendo do tamanho da mensagem e do valor do bitlen


3)Rode o arquivo JAVA pela extensao ou atraves do comando:
java RSA.java

4)A cada execucao, sera salvo um novo valor de tempo nos arquivos de resultados txt.

5)Quando quiser gerar o grafico execute:
python3 script.py

6)Os graficos serao plotados na pasta "graphs"

# Ambiente:
Ubuntu 20.04 Lts
Visual Studio Code

openjdk 16.0.1 2021-04-20
OpenJDK Runtime Environment (build 16.0.1+9-Ubuntu-120.04)
OpenJDK 64-Bit Server VM (build 16.0.1+9-Ubuntu-120.04, mixed mode, sharing)

Python 3.8.10
# Creditos
Desenvolvido por Vitor Bernstorff Clemes
