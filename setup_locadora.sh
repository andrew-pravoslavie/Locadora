#!/usr/bin/env bash
# ==============================================================================
# Script de Automação de Instalação e Configuração - Projeto Locadora
# Sistema Operacional: Arch Linux
# ==============================================================================

set -euo pipefail

# Cores para saída no terminal
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo -e "${BLUE}==========================================================${NC}"
echo -e "${BLUE}   Configuração do Ambiente para o Projeto Locadora      ${NC}"
echo -e "${BLUE}   Sistema: Arch Linux                                    ${NC}"
echo -e "${BLUE}==========================================================${NC}\n"

# 1. Verificar privilégios de sudo
if [ "$EUID" -eq 0 ]; then
    echo -e "${RED}[!] Não execute este script diretamente como root (com sudo ./script.sh).${NC}"
    echo -e "    Execute como usuário normal; o script solicitará sudo quando necessário."
    exit 1
fi

sudo -v || { echo -e "${RED}[x] Privilégios sudo são necessários para continuar.${NC}"; exit 1; }

# Mantém o sudo ativo durante a execução
while true; do sudo -n true; sleep 60; kill -0 "$$" || exit; done 2>/dev/null &

# 2. Verificar e Instalar OpenJDK
echo -e "\n${BLUE}[1/5] Verificando Java (OpenJDK)...${NC}"
if ! command -v java &>/dev/null; then
    echo -e "${YELLOW}[+] Java não encontrado. Instalando OpenJDK...${NC}"
    sudo pacman -Sy --needed --noconfirm jdk-openjdk
    echo -e "${GREEN}[✓] OpenJDK instalado com sucesso!${NC}"
else
    JAVA_VER=$(java -version 2>&1 | head -n 1)
    echo -e "${GREEN}[✓] Java já instalado: ${JAVA_VER}${NC}"
fi

# 3. Verificar e Instalar MariaDB
echo -e "\n${BLUE}[2/5] Verificando MariaDB (MySQL)...${NC}"
if ! command -v mariadb &>/dev/null; then
    echo -e "${YELLOW}[+] MariaDB não encontrado. Instalando MariaDB...${NC}"
    sudo pacman -Sy --needed --noconfirm mariadb
    echo -e "${GREEN}[✓] Pacote MariaDB instalado com sucesso!${NC}"
else
    echo -e "${GREEN}[✓] MariaDB já instalado.${NC}"
fi

# 4. Inicializar base de dados do MariaDB se necessário
echo -e "\n${BLUE}[3/5] Inicializando e configurando o serviço MariaDB...${NC}"
if [ ! -d "/var/lib/mysql/mysql" ]; then
    echo -e "${YELLOW}[+] Inicializando diretório de dados em /var/lib/mysql...${NC}"
    sudo mariadb-install-db --user=mysql --basedir=/usr --datadir=/var/lib/mysql
    echo -e "${GREEN}[✓] Diretório de dados inicializado.${NC}"
else
    echo -e "${GREEN}[✓] Diretório /var/lib/mysql já inicializado.${NC}"
fi

# Iniciar e habilitar o serviço
echo -e "${YELLOW}[+] Habilitando e iniciando o serviço mariadb.service...${NC}"
sudo systemctl enable --now mariadb
echo -e "${GREEN}[✓] Serviço MariaDB ativo e em execução.${NC}"

# 5. Configurar Usuário e Banco de Dados (locold / root:cco123)
echo -e "\n${BLUE}[4/5] Configurando usuário 'root' e banco 'locold'...${NC}"

DB_PASS="cco123"
DB_NAME="locold"

# Tenta conectar com a senha cco123; se falhar, tenta via socket sudo (instalação nova)
if mariadb -u root -p"${DB_PASS}" -e "SELECT 1;" &>/dev/null; then
    echo -e "${GREEN}[✓] Senha de root já está configurada como '${DB_PASS}'.${NC}"
    mariadb -u root -p"${DB_PASS}" -e "CREATE DATABASE IF NOT EXISTS ${DB_NAME};"
    echo -e "${GREEN}[✓] Banco de dados '${DB_NAME}' verificado/criado.${NC}"
else
    echo -e "${YELLOW}[+] Configurando senha do root e criando banco '${DB_NAME}'...${NC}"
    sudo mariadb -e "ALTER USER 'root'@'localhost' IDENTIFIED BY '${DB_PASS}'; CREATE DATABASE IF NOT EXISTS ${DB_NAME}; FLUSH PRIVILEGES;"
    echo -e "${GREEN}[✓] Senha do root definida para '${DB_PASS}' e banco '${DB_NAME}' criado com sucesso!${NC}"
fi

# 6. Configurar permissões do Maven Wrapper no projeto
echo -e "\n${BLUE}[5/5] Verificando arquivos do projeto...${NC}"
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_DIR="${SCRIPT_DIR}/Locadora"

# Se o script estiver dentro da própria pasta Locadora ou na pasta anterior:
if [ -f "${PROJECT_DIR}/mvnw" ]; then
    chmod +x "${PROJECT_DIR}/mvnw"
    echo -e "${GREEN}[✓] Permissão de execução concedida para: ${PROJECT_DIR}/mvnw${NC}"
elif [ -f "${SCRIPT_DIR}/mvnw" ]; then
    PROJECT_DIR="${SCRIPT_DIR}"
    chmod +x "${PROJECT_DIR}/mvnw"
    echo -e "${GREEN}[✓] Permissão de execução concedida para: ${PROJECT_DIR}/mvnw${NC}"
else
    echo -e "${YELLOW}[!] Executável 'mvnw' não localizado.${NC}"
fi

# Resumo e instruções finais
echo -e "\n${GREEN}==========================================================${NC}"
echo -e "${GREEN}   Instalação e Configuração Concluídas com Sucesso!     ${NC}"
echo -e "${GREEN}==========================================================${NC}"
echo -e "Configurações realizadas:"
echo -e "  - OpenJDK instalado/verificado"
echo -e "  - MariaDB instalado e serviço iniciado"
echo -e "  - Usuário: root | Senha: ${DB_PASS}"
echo -e "  - Banco de Dados: ${DB_NAME}"
echo -e ""
echo -e "Para executar o projeto:"
echo -e "  ${BLUE}cd \"${PROJECT_DIR}\"${NC}"
echo -e "  ${BLUE}./mvnw spring-boot:run${NC}"
echo -e ""
echo -e "Swagger UI estará acessível em:"
echo -e "  ${BLUE}http://localhost:8080/swagger-ui.html${NC}"
echo -e "==========================================================\n"
