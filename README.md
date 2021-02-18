# Bem vindo(a) ao desafio da Sensedia!<br/>Desde já, desejamos boa sorte :)

O objetivo deste projeto é avaliar o desenvolvimento de uma aplicação java utilizando Spring e APIs.
Fique atento as instruções a seguir.

## Cadastro de cervejas artesanais

## Set up environment

Para iniciar o projeto é necessário realizar o clone do repositório a seguir:

```bash
    $ git clone https://github.com/Sensedia/craftbeer.git
```

Este repositório contém um projeto base, com algumas dependências previamente adicionadas. Sinta-se a vontade para alterá-lo.
<br/><br/>Você deverá compartilhar no seu repositório do github as alterações solicitadas para o projeto. 
O endereço deste repositório deverá ser enviado para o contato do nosso RH após a conclusão do desenvolvimento.

## Especificação do projeto

A beer house é uma empresa possui um catálogo de cervejas artesanais. Esta empresa está buscando entrar no mundo digital.
Para entrar no mundo digital a beer house dicidiu começar pelas APIs. As APIs serão utilizadas para compartilhar dados com os parceiros e também para o seu sistema web.

Pra atender a esta demanda será necessário que a você implemente as APIs do projeto beer house.

Para implementar estas APIs você dever seguir a especificação do swagger que está neste projeto.

    craftbeer
    |
    |docs
    |    |___craftbeer-spec

Dica: Copie e cole o conteúdo do arquivo acima no [Swagger Editor](https://editor.swagger.io/) para visualizar melhor o que esperamos que seja implementado.

## Requisitos do projeto

1. Administrar cervejas: 

- O sistema deverá ter um cadastro de cervejas artesanais via API<br/>
- O sistema deverá ser capaz de criar, excluir e alterar (parcialmente ou completamente) as cervejas
   
2. Sistema deverá armazenar as informações em um banco de dados
 
- Poderá ser utilizada uma base de dados embbeded como H2<br/>
-- Caso opte por não utilizar a solução embbeded, não se esqueça de adicionar scripts de inicialização da base escolhida<br/>
- A comunicação entre o sistema e a base de dados deverá ser feita através de JPA

3. O sistema deve conter testes unitários

- Utilize JUnit<br/>
- Para facilitar a escrita dos testes, você pode utilizar frameworks de mock como o [Mockito](https://site.mockito.org/)

4. O sistema deve conter uma forma de validar o funcionamento
   
- Deverá ser diponibilizado uma coleção do postman para testar todos os recursos

## O que será avaliado no projeto

- Qualidade de código
- Design patterns utilizados
- A utilização correta do Spring, JPA e outros
- A criação de testes unitários

## O que você deve fazer

- Utilizar-se da linguagem e dos frameworks ao máximo para mostrar o seu conhecimento
- Entregar o projeto completo, com scripts e instruções de execução se for o caso
- Usar Java 8+ e deixar a gente bem feliz com isso!

## O que você pode fazer

- Utilizar frameworks e bibliotecas que julgar úteis
- Alterar e criar o código à vontade
- Consultar tutoriais, consultar fóruns e tirar dúvidas
- Você pode aprender com código de outras pessoas, utilizar trechos, mas não usar tudo igual
- Utilizar Docker para containerizar a aplicação (opcional, faz se te sobrar tempo)

## O que você não pode fazer

- Copiar de outros candidatos
- Pedir alguém para fazer o projeto para você

## Links de sugestão:

### [Building an Application with Spring Boot](https://spring.io/guides/gs/spring-boot/)
### [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
### [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

## Seção reservada para que você descreva brevemente como executar o seu projeto

Pode ser algo simples, como executar o arquivo Application.java. Ou talvez tenha alguma dependência como base de dados, por exemplo.

TODO