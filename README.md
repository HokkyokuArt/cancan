# CanCan 💃

## Sobre o projeto

O **CanCan** é um sistema simplificado de gerenciamento de tarefas para equipes de desenvolvimento.

A proposta do sistema é permitir que usuários possam **criar, editar, acompanhar e organizar tarefas dentro de projetos**, oferecendo uma base clara para controle de fluxo de trabalho, acompanhamento de demandas e colaboração entre membros de uma equipe.

Mesmo sendo uma versão enxuta, o projeto já contempla conceitos importantes de um task manager moderno, como autenticação, controle por perfil de acesso, vínculo entre usuários e projetos, organização de tarefas e preocupação com escalabilidade de estrutura tanto no front-end quanto no back-end.

Além da funcionalidade, o projeto também buscou manter uma identidade conceitual no nome **CanCan**, remetendo a diferentes ideias:
- à energia e movimento da dança *can-can*;
- à ideia de possibilidade, em referência ao “**can**” do inglês;
- e também a uma associação sutil com **Kanban**, que conversa diretamente com o domínio do produto.

---

## Tecnologias utilizadas

### Back-end
- Java 21
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- Bean Validation
- Swagger / OpenAPI
- Flyway
- PostgreSQL
- Maven

### Front-end
- React
- TypeScript
- Vite
- Material UI (MUI)
- React Router
- Axios

### Ferramentas e práticas
- Git
- GitHub
- SQL de inicialização para ambiente de desenvolvimento
- Estrutura com componentização e reaproveitamento de código
- Arquitetura baseada em abstrações para CRUDs reutilizáveis

# 1. Instruções para rodar o projeto

## Pré-requisitos
Antes de rodar o projeto, é necessário ter instalado:

- **Java 21**
- **Node.js / npm**
- **PostgreSQL**
- Git, opcionalmente, para clonar o repositório

## Estrutura do repositório

O projeto foi organizado com **front-end e back-end no mesmo repositório**, em pastas separadas. Falaremos mais sobre isso à frente.

## Configuração do banco de dados

1. Crie um banco PostgreSQL conforme as configurações esperadas pelo projeto. (veja o application.properties)
2. Ajuste a **URL do banco**, usuário e senha no arquivo de propriedades do back-end.
3. Com a aplicação iniciando corretamente, o Flyway executará as migrations necessárias.

## Dados iniciais

No back-end existe um arquivo chamado **`start.sql`**.

Esse arquivo contém inserts prontos para ambiente de desenvolvimento, incluindo:
- um usuário **admin**;
- um usuário **member**.

Atualmente, esse SQL serve como forma simples de preparação do ambiente local. Em um cenário mais maduro, isso poderia ser substituído por:
- um dump de desenvolvimento;
- seeders automatizados;
- ou um processo próprio de bootstrap do ambiente.

### Credenciais base do admin
- **Email:** `admin@admin`
- **Senha:** `13241324`

## Como rodar o back-end

1. Configure o banco no arquivo `application.properties` ou equivalente.
2. Execute o projeto back-end com Java 21.
3. Aguarde a aplicação subir na porta configurada.

Exemplo geral:

```bash
./mvnw spring-boot:run
```

Ou, se estiver usando Maven instalado:

```bash
mvn spring-boot:run
```

## Como rodar o front-end

1. Acesse a pasta do front-end.
2. Instale as dependências:

```bash
npm install
```

3. Rode o projeto:

```bash
npm run dev
```

4. Acesse a porta informada pelo Vite no terminal.

## Acesso ao sistema

Com o back-end e o front-end rodando:
- acesse o front pela porta exibida pelo Vite;
- utilize o usuário admin base para login;
- a partir dele, é possível navegar pelo sistema e utilizar os cadastros disponíveis.

# 2. Decisões técnicas tomadas e seus tradeoffs

## Escolha de manter front e back no mesmo repositório

Foi escolhido manter **front-end e back-end no mesmo repositório**, organizados em pastas separadas.

### Vantagens
- Facilita a visualização completa do projeto pelo avaliador.
- Centraliza todo o contexto técnico em um único lugar.
- Permite acompanhar os commits em ordem cronológica exata entre front e back.

### Tradeoffs
- Não é a estrutura ideal para projetos maiores ou ambientes profissionais mais complexos.
- Dificulta separar pipelines, deploys e versionamento por aplicação.
- Em times maiores, pode gerar mais acoplamento organizacional do que o desejado.

> Em um cenário real, normalmente front e back ficariam em repositórios separados.

## Estrutura de autenticação com admin inicial criado via banco

Atualmente, a conta administrativa principal é criada diretamente via banco de dados.

### Motivo
Essa abordagem foi escolhida por simplicidade, reduzindo complexidade de fluxo para a entrega do projeto.

### Vantagens
- Resolve rapidamente o bootstrap do sistema.
- Evita a necessidade de construir desde já um fluxo mais sofisticado de cadastro e convite.
- Facilita ambiente de desenvolvimento e demonstração.

### Tradeoffs
- Não escala bem para um produto real.
- Traz dependência de setup manual ou SQL inicial.
- Não representa o fluxo ideal de entrada de novos usuários.

### Evolução desejada
Com mais tempo, a ideia seria permitir que qualquer usuário pudesse criar conta e se tornar administrador do próprio projeto, podendo:
- convidar outros usuários;
- participar de outros projetos;
- receber convites por e-mail.

O fluxo ideal seria algo como:
1. o admin adiciona o e-mail de alguém já cadastrado ou a cadastrar;
2. o sistema envia um convite;
3. o usuário aceita e passa a integrar o projeto.

## Fluxo atual de senha

Hoje, o admin cadastra a senha dos outros usuários.

### Motivo
Foi uma solução mais simples para manter o foco na construção do sistema principal.

### Tradeoffs
- O fluxo não é ideal do ponto de vista de autonomia do usuário.
- A experiência de primeiro acesso fica limitada.
- Não existe ainda um processo robusto de recuperação de senha.

### Evolução desejada
O caminho mais adequado seria:
- o admin definir uma **senha temporária**;
- no primeiro login com essa senha, o sistema exigir:
  - nova senha;
  - confirmação da nova senha;
- após a redefinição, o usuário precisaria fazer login novamente.

Esse mesmo fluxo poderia ser reaproveitado sempre que um admin resetasse a senha de um member.

Além disso, seria interessante implementar recuperação de senha, possivelmente com:
- e-mail;
- validações adicionais do usuário;
- ou integração com algum autenticador/fornecedor terceirizado.

## Escolha do React no front-end

O front-end foi desenvolvido em **React com TypeScript**.

### Motivo
Apesar de o Angular oferecer uma estrutura que muitas vezes favorece padronização e reaproveitamento de forma mais guiada, a escolha pelo React aconteceu por preferência pessoal e por maior liberdade na construção de interface.

Outro fator importante foi a preferência pelo **Material UI**, que agrada mais do que o Angular Material no contexto deste projeto.

### Vantagens
- Grande liberdade de composição visual.
- Facilidade para componentização.
- Excelente combinação com TypeScript.
- Ecossistema maduro.

### Tradeoffs
- Exige mais cuidado arquitetural para manter consistência.
- Pode gerar mais decisões manuais de organização.
- Alguns padrões de reaproveitamento não vêm tão “prontos” quanto em frameworks mais opinativos.

## Foco em componentização e reaproveitamento no front-end

Uma decisão importante foi dedicar esforço à criação de componentes reutilizáveis e a uma estrutura mais organizada no front-end.

### Motivo
Houve preferência por investir tempo em qualidade estrutural da interface e reutilização de código, em vez de tentar cobrir tudo superficialmente.

### Vantagens
- Código mais limpo e reaproveitável.
- Facilidade de manutenção e evolução.
- Base mais sólida para crescimento do sistema.

### Tradeoffs
- Demandou tempo que poderia ter sido usado em outras frentes, como testes de front-end.
- Algumas funcionalidades visuais e refinamentos ficaram para depois.

## Ausência de testes no front-end nesta versão

Os testes do front-end não foram implementados por limitação de tempo.

### Tradeoff assumido
Foi uma decisão consciente priorizar a entrega funcional e a organização do código da interface antes da cobertura automatizada de testes no front.

Isso não significa que os testes sejam menos importantes, apenas que, nesta etapa, foi necessário priorizar.

## Escolha do PostgreSQL

O banco escolhido foi o **PostgreSQL**.

### Motivo
A escolha foi feita principalmente por familiaridade, comodidade e preferência pessoal.

### Vantagens
- Banco robusto e amplamente utilizado.
- Boa experiência de uso.
- Excelente integração com o ecossistema Java/Spring.

### Tradeoffs
- A decisão foi mais prática do que estratégica.
- Dependendo do contexto do produto, outros bancos poderiam ser considerados com base em requisitos específicos.

## Escolha do Flyway em vez de Liquibase

Foi escolhido **Flyway** para versionamento do banco.

### Motivo
Embora já houvesse experiência anterior com Liquibase, fazia bastante tempo sem utilizá-lo. Isso implicaria reestudo. O Flyway ofereceu um caminho mais direto por trabalhar de forma muito simples com SQL puro.

### Vantagens
- Menor curva de retomada.
- Migrations em SQL direto.
- Simplicidade operacional.

### Tradeoffs
- Menos recursos declarativos do que ferramentas mais estruturadas.
- Em cenários muito complexos, pode exigir maior disciplina manual.

## Uso de Swagger e Bean Validation

O projeto também foi usado como oportunidade para estudar mais a fundo alguns temas que já haviam sido utilizados antes, mas ainda sem tanto aprofundamento, especialmente:
- **Swagger / OpenAPI**
- **Bean Validation**

### Vantagens
- API mais bem documentada.
- Melhor entendimento das validações.
- Maior clareza para consumo e manutenção do back-end.

### Tradeoffs
- Houve investimento de tempo em aprendizado durante a execução do projeto.
- Isso naturalmente compete com tempo que poderia ser direcionado a outras entregas.

## Uso de abstrações, supers e reflection no back-end

Uma decisão marcante de arquitetura foi utilizar bastante reaproveitamento através de:
- classes abstratas;
- supers;
- estruturas genéricas de CRUD;
- e alguns métodos de reflection.

### Motivo
A ideia foi maximizar reaproveitamento de código sem cair em overengineering excessivo, buscando manter os CRUDs simples e com variáveis declarativas para facilitar entendimento.

### Vantagens
- Redução de repetição.
- Centralização de comportamento comum.
- Base mais elegante para expansão futura.
- Melhor consistência entre endpoints e regras estruturais.

### Tradeoffs
- Reflection e abstrações genéricas podem dificultar leitura para quem não conhece a base.
- Se levado longe demais, esse tipo de abordagem pode gerar complexidade desnecessária.
- Exige equilíbrio entre elegância e clareza.

## Auditoria: ideia considerada, mas não implementada

Foi cogitada uma auditoria simples salvando:
- metadados do usuário;
- datas;
- e um JSON da entidade alterada.

### Visão sobre a ideia
Esse tipo de auditoria cresce exponencialmente em volume, mas para um projeto pequeno poderia funcionar bem como ponto de partida.

### Motivo de não implementar
Por questão de priorização, foi mais importante focar na construção da aplicação principal e na organização do código.

## Documentação da API com Swagger / OpenAPI

O projeto utiliza Swagger/OpenAPI para documentar os endpoints e facilitar tanto o desenvolvimento quanto a avaliação da API.

### Motivo
A ideia foi tornar o consumo da API mais claro, permitindo visualizar rotas, parâmetros, payloads, autenticação e respostas de forma centralizada.

### Vantagens
- Facilita testes manuais.
- Melhora a compreensão dos contratos da API.
- Ajuda na manutenção e evolução do back-end.
- Torna a avaliação do projeto mais simples.

### Tradeoffs
- Requer esforço adicional de documentação.
- Precisa ser mantido em conjunto com a implementação para evitar inconsistências.

### Como acessar
Com o back-end em execução, a documentação pode ser acessada no navegador pela URL:

```bash
http://localhost:8080/swagger-ui/index.html
```

# 3. O que eu faria diferente com mais tempo

Se houvesse mais tempo para evoluir o projeto, os próximos passos seriam os seguintes.

## Melhorar o fluxo de usuários e autenticação
- Permitir cadastro mais livre de usuários.
- Transformar cada usuário em potencial admin de seu próprio projeto.
- Implementar convite por e-mail.
- Criar fluxo de senha temporária no primeiro acesso.
- Repetir esse mesmo fluxo quando a senha fosse resetada por um admin.
- Implementar recuperação de senha com mais segurança.
- Talvez integrar com autenticador terceirizado.

## Aumentar a cobertura e a qualidade dos testes
- Adicionar testes no front-end.
- Aumentar a cobertura de testes no back-end.
- Melhorar cenários de validação e fluxos negativos.
- Fortalecer a confiabilidade geral da aplicação.

## Melhorar tratamento de erros
- Refinar o tratamento de erros no back-end.
- Melhorar feedback visual e mensagens de erro no front-end.
- Tornar a experiência de falha mais clara para o usuário.

## Melhorar a experiência do front-end
- Evoluir o tema visual geral do sistema.
- Criar uma identidade de marca mais forte em torno do nome **CanCan**.
- Trabalhar melhor a ideia visual ligada a movimento, energia, produtividade e Kanban.
- Melhorar a responsividade.
- Criar uma home mais comercial, apresentando melhor o sistema e os benefícios de usar um bom task manager.

## Melhorar os disparos de consulta no front

Atualmente, um dos pontos a evoluir seria o comportamento de triggers de consulta.

Exemplo:
- em vez de disparar requisições a cada tecla digitada;
- aplicar debounce ou estratégias parecidas para reduzir chamadas desnecessárias e melhorar performance.

## Adicionar controle de visualização e edição por role no front
Seria interessante implementar no front uma camada de configuração para os componentes saberem:
- quando ficam visíveis;
- quando ficam desabilitados;
- e como devem se comportar com base na role do usuário.

Isso ajudaria bastante na consistência entre regra de negócio e experiência de interface.

## Adicionar dashboard mais completo
- Criar uma tela de dashboard customizável.
- Permitir visões mais gerenciais do sistema.
- Exibir indicadores úteis de tarefas, projetos e produtividade.

## Adicionar cache
- Cache de filtros.
- Cache de listagens.
- Otimizações para melhorar tempo de resposta e experiência de uso.

## Notificações
- Criar um webhook para disparar notificações.
- Avisar usuários sobre eventos importantes do sistema.

## Dockerização
Uma melhoria importante seria colocar o projeto em Docker para facilitar:
- setup local;
- padronização de ambiente;
- deploy;
- onboarding de novos desenvolvedores.

## Evoluir o domínio de tarefas
Hoje os status de tarefa são fixos, mas uma evolução natural seria transformar **status** em uma entidade própria.

### Possibilidades
- O admin cadastraria um fluxo de etapas da tarefa.
- O sistema passaria a ter workflows configuráveis.
- O próprio usuário ou admin poderia definir o que é obrigatório para mover uma tarefa para a próxima etapa.

Isso deixaria o produto muito mais flexível para diferentes times e processos.

## Tipos de tarefa e templates
Também seria interessante permitir:
- criar tipos de tarefa, como:
  - bug
  - story
  - task
  - improvement;
- cadastrar templates por tipo de tarefa;
- padronizar a abertura das tarefas.

Isso ajuda muito a manter qualidade e consistência no uso do sistema.

## Perfis e permissões mais refinadas
Outra evolução importante seria permitir cadastro de perfis mais específicos dentro do projeto.

Exemplo:
- um perfil “analista” poderia abrir tarefa, mas não fechar;
- outro perfil poderia mover tarefa entre etapas específicas;
- outro poderia apenas acompanhar.

Isso tornaria o sistema mais aderente a contextos reais de equipe.

## Visualização em board com drag and drop
Uma funcionalidade bastante desejada seria adicionar, como visualização padrão ou alternativa, um **board por colunas de status** com:
- cards de tarefas;
- organização por etapa;
- drag and drop para mudança de status.

Essa funcionalidade conversa diretamente com a natureza do produto e reforçaria a inspiração em Kanban.

## Auditoria
Com mais tempo, a auditoria poderia ser retomada e amadurecida:
- inicialmente com snapshot em JSON;
- depois, talvez com estrutura mais refinada de diff por campo;
- garantindo rastreabilidade das alterações relevantes.

## Resumo final

O projeto foi desenvolvido buscando equilíbrio entre:
- entrega funcional;
- clareza estrutural;
- reaproveitamento de código;
- e espaço para evolução futura.

Algumas decisões foram tomadas por pragmatismo, especialmente considerando tempo e escopo. Ainda assim, a base construída já permite enxergar caminhos claros de crescimento, tanto no produto quanto na arquitetura.

O CanCan, nesta versão, representa não apenas uma entrega técnica, mas também uma fundação sólida para se transformar em algo maior.
