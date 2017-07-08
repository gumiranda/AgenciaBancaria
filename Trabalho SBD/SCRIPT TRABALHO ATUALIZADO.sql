
------------------------------- ESSE SCRIPT CONTÉM A CRIAÇÃO DAS TABELAS, POVOAMENTO DO BANCO  E CONSULTAS ---------------------------

CREATE SCHEMA bancoATUALIZADO;
set search_path to bancoATUALIZADO;

-------------------------------- CRIAÇÃO DAS TABELAS ------------------------------------------

CREATE TABLE agencia(
   cdagencia serial NOT NULL,
   nome varchar(50) NOT NULL,
   estado varchar(50) NOT NULL,
   cidade varchar(50) NOT NULL,  
   CONSTRAINT pk_agencia PRIMARY KEY(cdagencia)
);


CREATE TABLE funcionario(
   nro_funcional serial NOT NULL,
   nome varchar(50) NOT NULL,
   data_de_admissao date NOT NULL,
   agencia_codigo int,
   tempo_de_servico int NOT NULL,
   telefone varchar(15) NOT NULL,
   CONSTRAINT pk_funcionario PRIMARY KEY(nro_funcional),
   CONSTRAINT fk_cdagencia_funcionario FOREIGN KEY(agencia_codigo) REFERENCES agencia(cdagencia)
);

CREATE TABLE clientes(
   cdCliente serial  NOT NULL,
   nome varchar(50) NOT NULL,
   cpf varchar(50) NOT NULL,
   cidade varchar(50) NOT NULL,   
   estado varchar(50) NOT NULL,   
   endereco varchar(50) NOT NULL,
   nro_funcional_funcionario int,
   data_de_nascimento date NOT NULL,
   telefone varchar(50) NOT NULL,
   CONSTRAINT pk_clientes PRIMARY KEY(cdCliente),
   CONSTRAINT fk_nrofuncional_cliente FOREIGN KEY(nro_funcional_funcionario) REFERENCES funcionario(nro_funcional)
);

CREATE TABLE contacorrente(
   cdConta serial NOT NULL,
   saldo float ,
   ultimo_acesso date NOT NULL,
   agencia_codigo int,
   data_de_criacao date NOT NULL,
   CONSTRAINT pk_contacorrente PRIMARY KEY(cdConta, agencia_codigo),
   CONSTRAINT fk_agencia_contacorrente FOREIGN KEY(agencia_codigo) REFERENCES agencia(cdagencia)
);

CREATE TABLE contapoupanca(
   cdConta serial  NOT NULL,
   saldo float ,
   ultimo_acesso date NOT NULL,
   agencia_codigo int,
   data_de_criacao date NOT NULL,
   CONSTRAINT pk_contapoupanca PRIMARY KEY(cdConta, agencia_codigo),
   CONSTRAINT fk_agencia_contapoupanca FOREIGN KEY(agencia_codigo) REFERENCES agencia(cdagencia)
);

CREATE TABLE cliente_possui_contacorrente(
   conta_id int,
   cliente_id int,
   agencia_id int,
   CONSTRAINT pk_cliente_possui_contacorrente PRIMARY KEY(conta_id,cliente_id, agencia_id),
   CONSTRAINT fk_cliente_possui_contacorrente1 FOREIGN KEY(cliente_id) REFERENCES clientes(cdCliente),
   CONSTRAINT fk_cliente_possui_contacorrente2 FOREIGN KEY(conta_id, agencia_id) REFERENCES contacorrente(cdConta, agencia_codigo)
);

CREATE TABLE cliente_possui_contapoupanca(
   conta_id int,
   cliente_id int,
   agencia_id int,
   CONSTRAINT pk_cliente_possui_contapoupanca PRIMARY KEY(conta_id,cliente_id),
   CONSTRAINT fk_cliente_possui_contapoupanca1 FOREIGN KEY(cliente_id) REFERENCES clientes(cdCliente),
   CONSTRAINT fk_cliente_possui_contapoupanca2 FOREIGN KEY(conta_id, agencia_id) REFERENCES contapoupanca(cdConta, agencia_codigo)
);


CREATE TABLE dependente(
   nome_do_dependente varchar(50) NOT NULL,
   nro_funcional_funcionario int,
   CONSTRAINT pk_dependente PRIMARY KEY(nome_do_dependente, nro_funcional_funcionario),
   CONSTRAINT fk_nrofuncional_dependente FOREIGN KEY(nro_funcional_funcionario) REFERENCES funcionario(nro_funcional)
);

CREATE TABLE operacaobancaria(
   cdOperacao serial NOT NULL,
   tipo varchar(50) NOT NULL,
   descricao varchar(50) NOT NULL,
   conta_id int,
   agencia_id int,
   data_operacao date NOT NULL,
   valor double precision NOT NULL,
   CONSTRAINT pk_operacao PRIMARY KEY(cdOperacao, conta_id, agencia_id),
   CONSTRAINT fk_conta_operacao FOREIGN KEY(conta_id, agencia_id) REFERENCES contacorrente(cdConta, agencia_codigo)
);


CREATE TABLE emprestimo(
   cdEmprestimo serial NOT NULL,
   cd_agencia int NOT NULL,
   valor DOUBLE PRECISION NOT NULL,
   qdeParcelas INT NOT NULL,
   CONSTRAINT pk_emprestimo PRIMARY KEY(cdEmprestimo),
   CONSTRAINT fk_emprestimo FOREIGN KEY(cd_agencia) REFERENCES agencia(cdagencia)
);

CREATE TABLE cupom(
   cdCupom serial NOT NULL,
   validade DATE NOT NULL, 
   operacao_cd int NOT NULL,
   conta_id int NOT NULL,
   agencia_id int NOT NULL,
   CONSTRAINT fk_cupom FOREIGN KEY(operacao_cd, conta_id, agencia_id) REFERENCES operacaobancaria(cdOperacao, conta_id, agencia_id),
    CONSTRAINT pk_cupom PRIMARY KEY(cdCupom)
);

CREATE TABLE cliente_possui_emprestimo(
   cd_emprestimo int NOT NULL,
   cd_cliente int NOT NULL,
   CONSTRAINT pk_cliente_possui_emprestimo PRIMARY KEY(cd_emprestimo, cd_cliente),
   CONSTRAINT fk_cliente_possui_emprestimo1 FOREIGN KEY(cd_emprestimo) REFERENCES emprestimo(cdEmprestimo),
   CONSTRAINT fk_cliente_possui_emprestimo2 FOREIGN KEY(cd_cliente) REFERENCES clientes(cdCliente)
);


------------- CRIACAO DE TRIGGER --------------------

CREATE OR REPLACE FUNCTION cria_cupom() RETURNS trigger AS
$$
BEGIN
if(new.valor >= 5000) THEN
INSERT INTO cupom VALUES (DEFAULT, '10-12-2017', new.cdOperacao, new.conta_id, new.agencia_id);

END IF;
RETURN NULL;
END; 
$$ language plpgsql;

CREATE TRIGGER trigger_cupom AFTER INSERT 
ON operacaobancaria FOR EACH ROW
EXECUTE PROCEDURE cria_cupom()




---------------------------------------- POVOAMENTO DO BANCO -------------------------------------------------------

-- inserindo agências

INSERT INTO agencia VALUES (DEFAULT, 'Banco do Brasil', 'Uberlandia', 'Minas Gerais'); 
INSERT INTO agencia VALUES (DEFAULT, 'Santander', 'Araguari', 'Minas Gerais'); 
INSERT INTO agencia VALUES (DEFAULT, 'Mercantil', 'Ribeirão Preto', 'São Paulo');
INSERT INTO agencia VALUES (DEFAULT, 'Bradesco', 'Uberaba', 'Minas Gerais'); 
INSERT INTO agencia VALUES (DEFAULT, 'Itaú', 'Uberlandia', 'Minas Gerais');  

-- inserindo funcionarios

INSERT INTO funcionario VALUES (DEFAULT, 'LARA CAROLINA OLIVEIRA', '03-10-2004', 1, 13, 988515938);
INSERT INTO funcionario VALUES (DEFAULT, 'GUSTAVO MIRANDA DE AGUIAR', '05-01-2010', 2, 7, 988515939);
INSERT INTO funcionario VALUES (DEFAULT, 'SANDRA LUCIANA DOS SANTOS', '07-05-2007', 3, 10, 988575938);
INSERT INTO funcionario VALUES (DEFAULT, 'JOAO DA SILVA', '07-03-2015', 4, 2, 988575935);
INSERT INTO funcionario VALUES (DEFAULT, 'MARIA DAS DORES', '07-05-2016', 5, 1, 988475938);

-- inserindo clientes

INSERT INTO clientes VALUES (DEFAULT, 'BRUNO SOARES', 13556546612, 'Araguari', 'Minas Gerais', 'Rua das Flores 510', 1, '03-07-1997', '998776853');
INSERT INTO clientes VALUES (DEFAULT, 'LAURA SANTOS REIS', 44689843848, 'Uberlandia', 'Minas Gerais', 'Rua das Pedras 502', 2, '06-07-1990', '998776863');
INSERT INTO clientes VALUES (DEFAULT, 'JOSE FERNANDES', 04450690826, 'Ribeirão Preto', 'São Paulo', 'Rua General Hozorio 5', 3, '04-09-1997', '998796853');
INSERT INTO clientes VALUES (DEFAULT, 'CAROLINA RODRIGUES CUNHA', 46732354876, 'Uberaba', 'Minas Gerais', 'Avenida Joao Naves 45', 4, '03-10-1970', '998746853');
INSERT INTO clientes VALUES (DEFAULT, 'MANOEL DE OLIVEIRA', 66773529190, 'Uberlandia', 'Minas Gerais', 'Avenida Rondon Pacheco 45', 5, '10-10-1979', '998746850');

-- inserindo contas corrente

INSERT INTO contacorrente VALUES (DEFAULT, 500, '20-06-2017', 1, '04-10-2010');
INSERT INTO contacorrente VALUES (DEFAULT, 578, '25-06-2017', 1, '10-06-2011');
INSERT INTO contacorrente VALUES (DEFAULT, 301, '09-06-2017', 2, '11-05-2012');
INSERT INTO contacorrente VALUES (DEFAULT, 2000, '04-06-2017', 3, '15-12-2013');
INSERT INTO contacorrente VALUES (DEFAULT, 5699, '03-06-2017', 4, '19-11-2017');

-- inserindo contas poupanca

INSERT INTO contapoupanca VALUES (DEFAULT, 5000, '20-06-2014', 3, '04-10-2011');
INSERT INTO contapoupanca VALUES (DEFAULT, 58, '25-11-2015', 2, '10-06-2011');
INSERT INTO contapoupanca VALUES (DEFAULT, 3041, '16-06-2017', 1, '11-05-2014');
INSERT INTO contapoupanca VALUES (DEFAULT, 2440, '14-05-2017', 5, '15-12-2015');
INSERT INTO contapoupanca VALUES (DEFAULT, 5900, '07-07-2017', 5, '19-11-2017');


-- inserindo cliente possui conta corrente

INSERT INTO cliente_possui_contacorrente VALUES (1,1, 1);
INSERT INTO cliente_possui_contacorrente VALUES (1,2, 1);
INSERT INTO cliente_possui_contacorrente VALUES (2,2, 1);
INSERT INTO cliente_possui_contacorrente VALUES (2,3, 1);
INSERT INTO cliente_possui_contacorrente VALUES (5,4, 4);


-- inserindo cliente possui conta poupanca

INSERT INTO cliente_possui_contapoupanca VALUES (1,1,3);
INSERT INTO cliente_possui_contapoupanca VALUES (1,2, 3);
INSERT INTO cliente_possui_contapoupanca VALUES (2,2, 2);
INSERT INTO cliente_possui_contapoupanca VALUES (2,3, 2);
INSERT INTO cliente_possui_contapoupanca VALUES (5,4, 5);

-- inserindo dependentes

INSERT INTO dependente VALUES ('JOANA REIS', 1);
INSERT INTO dependente VALUES ('MARIA MARQUES', 1);
INSERT INTO dependente VALUES ('VITOR SANTOS', 3);
INSERT INTO dependente VALUES ('ANDRE PEREIRA', 5);
INSERT INTO dependente VALUES ('ANTONIO MORAIS', 5);


-- inserindo operacao bancaria

INSERT INTO operacaobancaria VALUES (DEFAULT, 'Débito', 'Deposito', 1, 1, '04-05-2017', 5300);
INSERT INTO operacaobancaria VALUES (DEFAULT, 'Débito', 'Pagamento de Boleto', 1, 1, '10-05-2017', 3000);
INSERT INTO operacaobancaria VALUES (DEFAULT, 'Credito', 'Deposito', 2, 1, '04-05-2017', 700);
INSERT INTO operacaobancaria VALUES (DEFAULT, 'Débito', 'Pagamento de Luz', 3, 2, '04-10-2017', 400);
INSERT INTO operacaobancaria VALUES (DEFAULT, 'Credito', 'Pagamento de Boleto', 4, 3, '14-07-2017', 6000);


-- inserindo emprestimo

INSERT INTO emprestimo VALUES (DEFAULT, 1, 4000, 10);
INSERT INTO emprestimo VALUES (DEFAULT, 1, 5050, 15);
INSERT INTO emprestimo VALUES (DEFAULT, 2, 10500, 20);
INSERT INTO emprestimo VALUES (DEFAULT, 3, 9080, 15);
INSERT INTO emprestimo VALUES (DEFAULT, 5, 3000, 10);

-- inserindo cliente possui emprestimo

INSERT INTO cliente_possui_emprestimo VALUES (1,1);
INSERT INTO cliente_possui_emprestimo VALUES (2,1);
INSERT INTO cliente_possui_emprestimo VALUES (3,2);
INSERT INTO cliente_possui_emprestimo VALUES (4,3);
INSERT INTO cliente_possui_emprestimo VALUES (5,5);


--------------------------- ESPECIFICAÇÃO DE CONSULTAS -------------------------------------

--  2 CONSULTAS COM GROUP BY E FUNÇÕES DE AGREGAÇÃO

--1) Mostrar a quantidade de clientes que possuem conta corrente por agencia (mostrar nome da agencia e quantidade de clientes)
SELECT agencia.nome, COUNT(cliente_possui_contacorrente.cliente_id) AS qdeClientes
FROM agencia inner join cliente_possui_contacorrente
ON agencia.cdagencia = cliente_possui_contacorrente.agencia_id
GROUP BY agencia.cdagencia;

--2) Mostrar a media de saldo de clientes por agencia que possuem conta corrente
SELECT AVG(contacorrente.saldo) AS mediaSaldo
FROM cliente_possui_contacorrente INNER JOIN contacorrente
ON contacorrente.cdconta = cliente_possui_contacorrente.conta_id
inner join agencia on agencia.cdagencia = contacorrente.agencia_codigo
GROUP BY agencia.cdagencia;

-- 2 CONSULTAS COM GROUP BY, FUNÇÕES DE AGREGACAO E HAVING

--3) Mostrar a quantidade de agencias por cidade que possuem mais de uma conta poupanca (mostrar a cidade e quantidade de agencias)
SELECT agencia.cidade, COUNT(agencia.cdagencia) AS qdeAgencias
FROM agencia INNER JOIN cliente_possui_contapoupanca
ON agencia.cdagencia = cliente_possui_contapoupanca.agencia_id
GROUP BY agencia.cidade
HAVING COUNT(cliente_possui_contapoupanca.cliente_id) > 1;

--4) Mostrar a quantidade de funcionarios por agencia que tem mais de um dependente ( mostrar a agencia e a qde de funcionarios)
SELECT agencia.nome, COUNT(funcionario.nro_funcional) AS qdeFuncionarios
FROM funcionario inner join agencia
ON agencia.cdagencia = funcionario.agencia_codigo
INNER JOIN dependente 
ON dependente.nro_funcional_funcionario = funcionario.nro_funcional
GROUP BY agencia.nome
HAVING COUNT(dependente.nome_do_dependente) > 1;


--5) Mostrar todos os clientes da agencia Banco do Brasil que possuem conta corrente e  moram em Uberlandia
SELECT clientes.nome
FROM clientes INNER JOIN cliente_possui_contacorrente
ON clientes.cdcliente = cliente_possui_contacorrente.cliente_id
INNER JOIN agencia ON agencia.cdagencia = cliente_possui_contacorrente.agencia_id
WHERE clientes.cidade = 'Uberlandia';

--6) Mostrar os funcionarios que sao gerentes da agencia Santander
SELECT funcionario.nome
FROM funcionario INNER JOIN clientes
ON funcionario.nro_funcional = clientes.nro_funcional_funcionario
inner join agencia ON agencia.cdagencia = funcionario.agencia_codigo
WHERE agencia.nome = 'Santander';

--7) Mostrar as operações bancarias feitas em 2017 e os cupons gerados por elas
SELECT *
FROM operacaobancaria INNER JOIN cupom
ON operacaobancaria.cdoperacao = cupom.operacao_cd
WHERE extract( year from operacaobancaria.data_operacao) = '2017';

--8) Mostrar o valor dos emprestimos feitos por clientes que possuem idade superior a 18 anos.
SELECT emprestimo.valor
FROM emprestimo INNER JOIN cliente_possui_emprestimo
ON emprestimo.cdemprestimo = cliente_possui_emprestimo.cd_emprestimo
INNER JOIN clientes ON clientes.cdcliente = cliente_possui_emprestimo.cd_cliente
WHERE  extract(year from current_date) - extract( year from clientes.data_de_nascimento) > 18;

--9) Mostrar os funcionarios com tempo de serviço maior que 5 anos e que possuem dependentes
SELECT funcionario.nome
FROM funcionario INNER JOIN dependente
ON funcionario.nro_funcional = dependente.nro_funcional_funcionario
WHERE funcionario.tempo_de_servico > 5;

--10) Mostrar os clientes com saldo de conta corrente menor que 500 
SELECT clientes.nome
FROM clientes inner join cliente_possui_contacorrente
ON clientes.cdcliente = cliente_possui_contacorrente.cliente_id
INNER JOIN contacorrente ON contacorrente.cdconta = cliente_possui_contacorrente.conta_id
WHERE contacorrente.saldo > 500;


