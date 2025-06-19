#!/bin/bash

# Obtém o diretório onde o script está localizado (a pasta "home" do programa)
PROG_HOME="$(cd "$(dirname "$0")" && pwd)"

PROG_HOME="$PROG_HOME/target/NexusMod-app"

cd "$PROG_HOME"
ls -l

# Define o classpath (JAR principal + dependências na pasta lib)
CLASSPATH="$PROG_HOME/bin/osgi-0.0.1.jar"
for jar in "$PROG_HOME/lib/"*.jar; do
  CLASSPATH="$CLASSPATH:$jar"
done

# Define variáveis de ambiente específicas do programa (se necessário)
#export CONFIG_DIR="$PROG_HOME/config"
#export LOG_DIR="$PROG_HOME/logs"

# Executa o programa Java
#java -cp "$CLASSPATH" -Dconfig.dir="$CONFIG_DIR" -Dlog.dir="$LOG_DIR" com.exemplo.Main "$@"
/opt/sdk/jdk-21.0.5/bin/java -jar ./bin/osgi-0.0.1.jar