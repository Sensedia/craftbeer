# Instalando o Docker Compose

Considerando que você já tem o Docker instalado, execute os seguintes comandos para instalar o Docker Compose.

```sh
sudo su
COMPOSE_VERSION=1.27.4
curl -L https://github.com/docker/compose/releases/download/$COMPOSE_VERSION/docker-compose-`uname -s`-`uname -m` > /usr/bin/docker-compose
chmod +x /usr/bin/docker-compose
exit
```

## Inicie os contêineres

Use os comandos a seguir para iniciar os contêineres.

```sh
git clone https://github.com/janainamilitao/craftbeer
cd craftbeer
docker-compose -f docker-compose.yml create
docker-compose -f docker-compose.yml start
```

É possível acessar a documentação da API na URL http://172.17.0.1:9000/beerhouse/swagger-ui.html

Ou importando o arquivo [docs/swagger-craftbeer](docs/swagger-craftbeer) no [Swagger Editor](https://editor.swagger.io).

Para importar o arquivo com as collections no [Postman](https://www.postman.com), baixe o arquivo [docs/CraftBeer.postman_collection.json](docs/Craftbeer-API.postman_collection.json).

# Opcional

Para visualizar os contêineres em execução, digite o comando abaixo.

```sh
docker-compose -f docker-compose.yml ps
docker-compose -f docker-compose.yml top
```

Para visualizar os logs de todos os contêineres, use o comando abaixo.

```sh
docker-compose -f docker-compose.yml logs
```

Para visualizar os logs de um conteiner específico, digite:

```sh
docker-compose -f docker-compose.yml logs NOME_SERVICO
```

O nome do serviço é declarado no arquivo ``docker-compose.yml``. Exemplo:

```sh
docker-compose -f docker-compose.yml logs mysql
```

Use os comandos a seguir para parar e remover os contêineres.

```sh
docker-compose -f docker-compose.yml stop
docker-compose -f docker-compose.yml rm
```

Para obter mais informações sobre o Docker Compose acesse:

* [https://docs.docker.com/compose/reference/](https://docs.docker.com/compose/reference/)
* [https://docs.docker.com/compose/compose-file/](https://docs.docker.com/compose/compose-file/)