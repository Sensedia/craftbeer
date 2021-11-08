FROM openjdk:8-stretch
# Imagem base

# Informacoes sobre a imagem
LABEL mantenedor="Janaina Militao do Nascimento" \
      email="janaina.militao@gmail.com" \
      data_criacao="03/12/2020" \
      versao="2.0.0" \
      descricao="Especifica a imagem Docker de uma aplicacao Java" \
      licenca="copyright"

#---------------------------------#
# Variaveis usadas no DockerFile
#---------------------------------#
# Variaveis de ambiente (o valor eh alterado na inicializacao do conteiner,
# se vazia, recebera um valor padrao)

# Define o tipo do SGBD, host, porta e nome do database a ser usado pela aplicacao
ARG DATASOURCE_URL
ENV DATASOURCE_URL ${DATASOURCE_URL:-'postgresql://172.17.0.1:3306/craft_beer'}

# Define o nome do usuario que acessara o database
ARG DATASOURCE_USERNAME
ENV DATASOURCE_USERNAME ${DATASOURCE_USERNAME:-'postgres'}

# Define a senha do usuario que acessara o database
ARG DATASOURCE_PASSWORD
ENV DATASOURCE_PASSWORD ${DATASOURCE_PASSWORD:-'postgres'}

# Informacoes sobre a aplicacao
ARG admin_user=beer
ARG service_dir_base=/home/${admin_user}/
ARG SERVICE_APP_FILE=craft-beer.jar
ARG PORT=9000

#---------------------------------#
# MAIN
#---------------------------------#

# Instalando dependencias de pacotes para executar a aplicacao com o usuario comum
# Para reduzir vulnerabilidades de seguranca
RUN cd /tmp \
    && apt-get update \
    && apt-get -y install libfontconfig gosu sudo \
    && apt-get clean

# Criando a estrutura de diretorios
RUN mkdir -p ${service_dir_base}/app

# Copiando os arquivos da aplicacao para dentro do conteiner
COPY run.sh /run.sh
COPY ${SERVICE_APP_FILE} ${service_dir_base}/app/

# Definindo as permissoes de acesso do usuario
# Criando um userid e groupid especifico para nao confundir com
# O userid e groupid do DockerHost
RUN groupadd -r -g 50000 ${admin_user} \
    && useradd -u 50000 -g 50000 ${admin_user} \
    && chown -R ${admin_user}:${admin_user} ${service_dir_base} \
    && chmod -R 755 ${service_dir_base}

# Definindo a permissao do scripts e arquivos
RUN chown root:root /run.sh \
    && chmod 755 /run.sh

EXPOSE ${PORT}

# Iniciando o servico ao iniciar o conteiner
ENTRYPOINT ["/bin/bash", "-c", "/run.sh $DATASOURCE_URL $DATASOURCE_USERNAME $DATASOURCE_PASSWORD"]
