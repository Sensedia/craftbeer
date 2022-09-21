[Criando a imagem Docker da aplicação]: #criando-a-imagem-docker-da-aplicação
[Iniciando o conteiner da aplicação]: #inciando-o-conteiner-da-aplicação
[Visualizando os logs]: #visualizando-os-logs
[OPCIONAL: Instalando o Prometheus]: #opcional:-instalando-o-prometheus
[OPCIONAL: Instalando o Grafana]: #opcional:-instalando-o-grafana

# Conteúdo
1. [Criando a imagem Docker da aplicação][Criando a imagem Docker da aplicação]
2. [Iniciando o conteiner da aplicação][Iniciando o conteiner da aplicação]
3. [Visualizando os logs][Visualizando os logs]
4. [OPCIONAL: Instalando o Prometheus][OPCIONAL: Instalando o Prometheus]
5. [OPCIONAL: Instalando o Grafana][OPCIONAL: Instalando o Grafana]


# Criando a imagem Docker da aplicação

Considerando que você já tem o Docker-CE instalado, use o
comando a seguir para gerar uma imagem docker para a aplicação.

* Baixe o código do repositório Git com o comando a seguir.

```sh
git clone https://github.com/janainamilitao/craftbeer
cd craftbeer
./mvnw package
mv target/craft-beer.jar .
VERSION=3.1.0
docker image build -t jmilitao/craft-beer:$VERSION .
docker image tag jmilitao/craft-beer:$VERSION jmilitao/craft-beer:latest
```

* Envie a imagem para Docker Hub com os comandos abaixo.

```sh
docker login -u USERNAME -p PASS
docker image push jmilitao/craft-beer:$VERSION
docker image push jmilitao/craft-beer:latest
```

# Iniciando o contêiner da aplicação

Use o comando a seguir para iniciar um conteiner do banco de dados.

```sh
sudo mkdir -p /docker/postgresql/craft_beer/data
sudo chown -R 999:999 /docker/postgresql/craft_beer/data

docker container run -p 5433:5432 -d --name postgres \
--restart=always \
-v /docker/postgresql/scraft_beer/data:/var/lib/postgresql/data \
-e POSTGRES_DB="craft_beer" \
-e POSTGRES_PASSWORD="postgres" \
-e POSTGRES_USER="postgres" \
postgres:14.5
```

Use o comando a seguir para iniciar um conteiner da aplicação.

```sh
docker container run -d -p 9000:9000 --name craft-beer \
-e DATASOURCE_URL="postgresql://172.17.0.1:5433/craft_beer" \
-e DATASOURCE_USERNAME="postgres" \
-e DATASOURCE_PASSWORD="postgres" \
jmilitao/craft-beer:latest
```

É possível acessar a documentação da API na URL http://172.17.0.1:9000/beerhouse/swagger-ui.html

Ou importando o arquivo [docs/swagger-craftbeer](docs/swagger-craftbeer) no [Swagger Editor](https://editor.swagger.io).

Para importar o arquivo com as collections no [Postman](https://www.postman.com), baixe o arquivo [docs/CraftBeer.postman_collection.json](docs/CraftBeer.postman_collection.json).

As métricas da aplicação no padrão aceito pelo Prometheus, estarão acessíveis em:
http://172.17.0.1:9000/beerhouse/monitoring/prometheus

# Visualizando os logs

Use os comandos a seguir para visualizar o log dos contêineres.

```sh
docker container logs -f postgres
docker container logs -f craft-beer
```

Se quiser fazer backup do banco criado anteriormente, basta fazer backup do diretório ``/docker/postgresql/craft_beer/data.``.

# OPCIONAL: Instalando o Prometheus

Crie o diretório abaixo para persistir os dados fora do conteiner.

```sh
sudo mkdir -p /docker/prometheus
```

Crie o arquivo de configuração /docker/prometheus/prometheus.yml com o seguinte conteúdo. 
Pode ser que esse conteúdo mude com o tempo e mais informações sobre configuração pode ser obtida em: https://prometheus.io/docs/prometheus/latest/getting_started/

```yaml
global:
  # Intervalo de raspagem de métricas. O padrão é a cada 60s
  scrape_interval:  30s
  # Intervalo para avaliação de regras. O padrão é a cada 60s
  evaluation_interval: 30s
  # scrape_timeout é definido como o padrão global (10s).

alerting:
  alertmanagers:
    - static_configs:
        - targets:
      # - alertmanager:9093

rule_files:
# - "first_rules.yml"
# - "second_rules.yml"

scrape_configs:
  - job_name: 'prometheus'
    metrics_path:  '/metrics'
    #    scheme defaults to 'http'.
    static_configs:
      - targets: ['172.17.0.1:9090']
  - job_name: 'application'
    metrics_path: '/beerhouse/monitoring/prometheus'
    static_configs:
      - targets: ['172.17.0.1:9000']
```

Inicie o Prometheus usando o Docker com o seguinte comando.

```sh
docker container run -d -p 9090:9090 \
--name prometheus \
-v /docker/prometheus/prometheus.yml:/etc/prometheus/prometheus.yml \
prom/prometheus:v2.38.0
```

O Prometheus está acessível na URL: http://172.17.0.1:9090

Fonte: https://prometheus.io/docs/prometheus/latest/installation/

Se quiser parar o conteiner, é só executar o comando abaixo.

```sh
docker container stop prometheus
```

Para iniciá-lo novamente, execute o comando abaixo.

```sh
docker container start prometheus
```

Para visualizar os logs, execute o comando abaixo.

```sh
docker container logs -f prometheus
```

# OPCIONAL: Instalando o Grafana

Crie o diretório de dados do Grafana a serem persistidos fora do conteiner.

```sh
sudo mkdir -p /docker/grafana/data
sudo chown -R 472:472 /docker/grafana/data
sudo chmod -R 775 /docker/grafana
```

Inicie o conteiner docker do Grafana com HTTP.

```sh
docker container run -d --name=grafana \
--restart always \
-p 3000:3000 \
-e "GF_SERVER_PROTOCOL=http" \
-e "GF_SERVER_HTTP_PORT=3000" \
-v /docker/grafana/data:/var/lib/grafana \
grafana/grafana:9.1.6
```

O log pode ser visualizado com os comandos abaixo.

```sh
docker container logs grafana
```

Acesse o Grafana na URL http://172.17.0.1:3000. O login é **admin** e a senha padrão é **admin**.

Se quiser parar o conteiner, é só executar o comando abaixo.

```sh
docker container stop grafana
```

Para iniciá-lo novamente, execute o comando abaixo.

```sh
docker container start grafana
```

Mais informações sobre o Grafana e como configurá-lo, acesse os links abaixo.

* http://docs.grafana.org