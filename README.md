# Ponto-eletronico-java
Sistema de Ponto Eletrônico em Java desenvolvido para o TRE-PB

Pré-requisitos:
* Java JRE 8.0;
* IDE Eclipse.

Informações Pertinentes ao Software e ao Desenvolvedor.

* Nome: Alan Giovanni de Menezes Targino
* E-mail: agmtargino@gmail.com

Este Software foi desenvolvido com o intuito de ser um sistema de ponto eletrônico para os estagiários do Tribunal Regional Eleitoral da Paraíba. Uma alternativa ao preenchimento dos pontos em uma folha A4 com caneta esferográfica. *Esta documentação não é a documentação oficial sendo esta apenas uma orientação para aqueles que desejem conhecer o projeto.*
 
O Software possui as seguintes funções:
* Cadastro de Usuários;
* Cadastro de Usuários Administrador do Sistema;
* Alteração de Cadastros;
* Marcar Entrada, Saída e visualizar seu ponto diário assim como visualizar o registro mensal;
* O administrador possui controle total sobre os pontos marcados pelos usuários podendo alterá-los se necessário;
* Impressão da folha de ponto;
* Gerar separadamente ou em grupos, as folhas de ponto de cada usuário no formato PDF (Atribuição específica dos usuários administradores);
* dentificação de usuário através de Login e Senha previamente cadastrados.

O Software está em fase de desenvolvimento, as seguintes funções foram pensadas e serão implementadas, se somente se houver tempo de estágio suficiente:
* Logs de Registro de cada alteração feita pelos administradores;
* Uma tela especial para supervisores dos usuários;
* Assinatura Digital pelos supervisores nas folhas de ponto dos usuários; 

Funcionamento do Software O arquivo Precise_Time.jar, não é um arquivo executável. Portanto precisa ser interpretado, dessa forma se faz necessário ter o software Java Runtime instalado na máquina que este arquivo será executado. A grande vantagem disso é a portabilidade, este software poderá ser executado em qualquer sistema operacional, desde que o sistema possua o Java instalado. 
Imagens do software As imagens do sistema ficam armazenados dentro da pasta “Images”. É necessário permanecer na mesma pasta onde estiver o arquivo .jar.

Código Fonte e Execução do Software Para ver o código fonte precisa instalar o eclipse, a versão utilizada para desenvolvimento foi o Eclipse Oxygen 64bit ( https://www.eclipse.org/downloads/download.php?file=/oomph/epp/oxygen/R2/eclipse-inst-w in64.exe ). Após instalado, dentro do eclipse instalar o seguinte Plugin: Jaspersoft Studio. Ainda será necessário importar para o projeto as dependências que está dentro do arquivo dependencias.rar no repositório.

 Obs: PARA IMPORTAR O PROJETO BASTA COPIAR A PASTA RAIZ DENTRO DO SEU DIRETÓRIO DE PROJETOS PADRÃO E DENTRO DO ECLIPSE PROCURAR A OPÇÃO IMPORTAR. 
 
OBS.: Verifique se a pasta "coleções" dentro das pastas "bin" e "src" está nomeado corretamente, pois geralmente ao compactar a pasta principal os acentos são convertidos em caracteres da tabela ascii danificando assim a integridade dos arquivos. Caso encontre alguma pasta com caracteres especiais dentro destas pastas supracitadas é provável que seja a pasta "coleções" peço que renomeie a pasta com caracteres especiais para "coleções".

Armazenamento dos dados Após todas as verificações para o perfeito funcionamento do sistema o software quando iniciado pela primeira vez irá criar uma pasta denominada “armazenamento_xml” esta pasta irá armazenar todos os dados do software. Dentro da pasta será criado inicialmente mais uma pasta, “admin”. Nesta pasta será armazenado, em um arquivo XML, todos os 
usuários administradores do sistema. Cada usuário não administrador terá suas informações armazenadas em uma pasta própria dentro de “armazenamento_xml”. Ao iniciar pela primeira vez o software, apenas o usuário administrador terá acesso a interface principal. Este usuário possui as credenciais:

* Login: admin
* Senha: admin 
 
A partir deste login é possível cadastrar os demais, usuários administradores e usuários frequentes do sistema. 
Marcando Ponto o software está programado para receber uma única vez por dia, um ponto de entrada e um ponto de saída, visto que os estagiários não possuem horas de descanso em meio a sua jornada diária.
