# communication-platform

Plataforma de gerenciamento de agendamentos de envio de comunicações

## Pré-requisitos

  <p>Para executar a aplicação, os seguintes componentes devem estar instalados na máquina, e configurados:</p>
  <ul>
    <li>Jdk (https://www.oracle.com/java/technologies/downloads/)</li>
    <li>Maven (https://maven.apache.org/install.html)</li>
    <li>Docker (https://docs.docker.com/get-docker/)</li>
  </ul>  

## Como executar

  Na raíz do projeto:
    1 - Iniciar banco de dados:
      docker-compose up -d

    2 - Iniciar aplicação Maven:
      mvn spring-boot:run

  <p>obs. A aplicação está configurada para rodar na porta 8082. Caso seja necessário utilizar outra porta, alterar o valor de "server.port" no arquivo src\main\resources\application.properties</p>

## Endpoints

  <p>Exemplos de requisição:</p>

  Endpoint 1: Registra a solicitação do agendamento do envio da comunicação, e retorna o ID do agendamento.
`
    curl --location --request POST 'localhost:8082/api/schedule' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "dateTimeToSend": "2021-12-22T22:10:00", 
        "receiver": "Nome do destinatario",
        "message": "messagem a ser enviada",
        "channel": "PUSH"
    }'
`
  Endpoint 2: Consultar o status do agendamento de envio de comunicação.
  `  
    curl --location --request GET 'localhost:8082/api/schedule/1'
  `

  Endpoint 3: Remover um agendamento de envio de comunicação.
  `
    curl --location --request DELETE 'localhost:8082/api/schedule/2'
  `
  
  Coleção com as chamadas utilizadas durante o desenvolvimento:
  https://www.getpostman.com/collections/fb5c4a92721a63e44705
