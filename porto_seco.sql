create database porto_seco;
use  porto_seco;

CREATE TABLE cargas (
  id INT NOT NULL AUTO_INCREMENT,
  tipo VARCHAR(100) NOT NULL,
  quantidade INT NOT NULL,
  peso FLOAT NOT NULL,
  dimensao VARCHAR(50) NOT NULL,
  origem VARCHAR(100) NOT NULL,
  destino VARCHAR(100) NOT NULL,
  transportadora VARCHAR(100) NOT NULL,
  documentacao VARCHAR(200) NOT NULL,
  data_chegada DATE NOT NULL,
  data_saida DATE,
  status VARCHAR(50) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE usuarios (
  id INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(100) NOT NULL,
  senha VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL,
  perfil ENUM('admin', 'operador', 'empresa') NOT NULL,
  telefone VARCHAR(20) NOT NULL,
  PRIMARY KEY (id)
);

