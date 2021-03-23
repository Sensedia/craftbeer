[Criando a imagem Docker da aplicação]: #criando-a-imagem-docker-da-aplicação
[Iniciando o conteiner da aplicação]: #inciando-o-conteiner-da-aplicação


# Conteúdo
1. [Criando a imagem Docker da aplicação][Criando a imagem Docker da aplicação]
2. [Iniciando o conteiner da aplicação][Iniciando o conteiner da aplicação]

# Criando a imagem Docker da aplicação

Considerando que você já tem o Docker-CE instalado, use o
comando a seguir para gerar uma imagem docker para a aplicação.

* Baixe o código do repositório Git com o comando a seguir.

```sh
git clone https://github.com/janainamilitao/craftbeer
cd craftbeer
./mvnw package
mv target/craft-beer.jar .
VERSION=2.0.0
docker build -t jmilitao/craft-beer:$VERSION .
docker tag jmilitao/craft-beer:$VERSION jmilitao/craft-beer:latest
```

* Envie a imagem para Docker Hub com os comandos abaixo.

```sh
docker login -u USERNAME -p PASS
docker push jmilitao/craft-beer:$VERSION
docker push jmilitao/craft-beer:latest
```

# Iniciando o contêiner da aplicação

Use o comando a seguir para iniciar um conteiner do banco de dados.

```sh
sudo mkdir -p /docker/mysql/craft-beer/data

docker run -p 3306:3306 -d --name mysql \
--restart=always \
-v /docker/mysql/craft-beer/data:/var/lib/mysql \
 -e MYSQL_HOST=172.17.0.1 \
 -e MYSQL_ROOT_PASSWORD=secret \
 -e MYSQL_DATABASE=craft_beer \
 -e MYSQL_USER=beer \
 -e MYSQL_PASSWORD='Cr4ft@b33er' \
mysql:5.7
```

Use o comando a seguir para iniciar um conteiner da aplicação.

```sh
docker run -d -p 9000:9000 --name craft-beer \
-e DATASOURCE_URL='mysql://172.17.0.1:3306/craft_beer' \
-e DATASOURCE_USERNAME="beer" \
-e DATASOURCE_PASSWORD='Cr4ft@b33er' \
jmilitao/craft-beer:2.0.0
```

É possível acessar a documentação da API na URL http://172.17.0.1:9000/beerhouse/swagger-ui.html

Ou importando o arquivo [docs/swagger-craftbeer](docs/swagger-craftbeer) no [Swagger Editor](https://editor.swagger.io).

Para importar o arquivo com as collections no [Postman](https://www.postman.com), baixe o arquivo [docs/CraftBeer.postman_collection.json](docs/CraftBeer.postman_collection.json).

# Opcional

Use os comandos a seguir para visualizar o log dos contêineres.

```sh
docker logs -f mysql
docker logs -f craft-beer
```

Se quiser fazer backup do banco criado anteriormente, basta fazer backup do diretório ``/docker/mysql/craft-beer/data``.