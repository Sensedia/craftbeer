FROM openjdk:11
# Imagem base

# Informacoes sobre a imagem
LABEL mantenedora="Janaina Militao do Nascimento" \
      email="janaina.militao@gmail.com" \
      data_criacao="07/11/2021" \
      versao="3.0.0" \
      descricao="Especifica a imagem Docker de uma aplicacao Java" \
      licenca="copyright"

#---------------------------------#
# Variaveis usadas no DockerFile
#---------------------------------#
# Variaveis de ambiente (o valor eh alterado na inicializacao do conteiner,
# se vazia, recebera um valor padrao)

# Define o tipo do SGBD, host, porta e nome do database a ser usado pela aplicacao
ARG DATASOURCE_URL
ENV DATASOURCE_URL ${DATASOURCE_URL:-'postgresql://172.17.0.1:5433/craft_beer'}

# Define o nome do usuario que acessara o database
ARG DATASOURCE_USERNAME
ENV DATASOURCE_USERNAME ${DATASOURCE_USERNAME:-'postgres'}

# Define a senha do usuario que acessara o database
ARG DATASOURCE_PASSWORD
ENV DATASOURCE_PASSWORD ${DATASOURCE_PASSWORD:-'postgres'}

# Informacoes sobre a aplicacao
ARG ADMIN_USER=beer
ARG SERVICE_DIR_BASE=/home/${ADMIN_USER}/
ARG SERVICE_DIR=${SERVICE_DIR_BASE}/app
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
RUN mkdir -p $SERVICE_DIR

# Copiando os arquivos da aplicacao para dentro do conteiner
COPY run.sh /run.sh
COPY $SERVICE_APP_FILE ${SERVICE_DIR}/

# Definindo as permissoes de acesso do usuario
# Criando um userid e groupid especifico para nao confundir com
# O userid e groupid do DockerHost
RUN groupadd -r -g 50000 $ADMIN_USER \
    && useradd -u 50000 -g 50000 $ADMIN_USER \
    && chown -R $ADMIN_USER:$ADMIN_USER $SERVICE_DIR_BASE \
    && chmod -R 755 $SERVICE_DIR_BASE

# Definindo a permissao do scripts e arquivos
RUN chown root:root /run.sh \
    && chmod 755 /run.sh

EXPOSE ${PORT}

# Iniciando o servico ao iniciar o conteiner
ENTRYPOINT ["/bin/bash", "-c", "/run.sh $DATASOURCE_URL $DATASOURCE_USERNAME $DATASOURCE_PASSWORD $ADMIN_USER $SERVICE_DIR $SERVICE_APP_FILE"]
