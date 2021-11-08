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
VERSION=3.0.0
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
sudo mkdir -p /docker/postgresql/craft_beer/data
sudo chown -R 999:999 /docker/postgresql/craft_beer/data

docker run -p 5433:5432 -d --name postgres \
--restart=always \
-v /docker/postgresql/scraft_beer/data:/var/lib/postgresql/data \
-e POSTGRES_DB="craft_beer" \
-e POSTGRES_PASSWORD="postgres" \
-e POSTGRES_USER="postgres" \
postgres
```

Use o comando a seguir para iniciar um conteiner da aplicação.

```sh
docker run -d -p 8080:8080 --name craft_beer \
-e DATASOURCE_URL="postgresql://172.17.0.1:5433/craft_beer" \
-e DATASOURCE_USERNAME="postgres" \
-e DATASOURCE_PASSWORD="postgres" \
jmilitao/craft_beer:latest
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

Se quiser fazer backup do banco criado anteriormente, basta fazer backup do diretório ``/docker/postgresql/craft_beer/data.``.