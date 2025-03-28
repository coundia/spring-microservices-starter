DROP TABLE IF EXISTS domainevententry CASCADE;
DROP TABLE IF EXISTS tokenentry CASCADE;


create table if not exists associationvalueentry
(
  id                bigint       not null
    primary key,
  associationkey   varchar(255) not null,
  associationvalue varchar(255),
  sagaid           varchar(255) not null,
  sagatype         varchar(255)
);

alter table associationvalueentry
  owner to pcoundia;

create index if not exists idxk45eqnxkgd8hpdn6xixn8sgft
  on associationvalueentry (sagatype, associationkey, associationvalue);

create index if not exists idxgv5k1v2mh6frxuy5c0hgbau94
  on associationvalueentry (sagaid, sagatype);

create table if not exists deadletterentry
(
  enqueuedat          timestamp(6) with time zone not null,
  lasttouched         timestamp(6) with time zone,
  processingstarted   timestamp(6) with time zone,
  sequenceindex       bigint                      not null,
  sequencenumber      bigint,
  causemessage        varchar(1023),
  aggregateidentifier varchar(255),
  causetype           varchar(255),
  deadletterid       varchar(255)                not null
    primary key,
  eventidentifier     varchar(255)                not null,
  messagetype         varchar(255)                not null,
  payloadrevision     varchar(255),
  payloadtype         varchar(255)                not null,
  processinggroup     varchar(255)                not null,
  sequenceidentifier  varchar(255)                not null,
  timestamp           varchar(255)                not null,
  tokentype           varchar(255),
  type                 varchar(255),
  diagnostics          bytea,
  metadata            bytea,
  payload              bytea                         not null,
  token                bytea,
  constraint deadletterentryprocessinggroupsequenceidentifiersequkey
    unique (processinggroup, sequenceidentifier, sequenceindex)
);

alter table deadletterentry
  owner to pcoundia;

create index if not exists idxe67wcx5fiq9hl4y4qkhlcj9cg
  on deadletterentry (processinggroup);

create index if not exists idxrwucpgs6sn93ldgoeh2q9k6bn
  on deadletterentry (processinggroup, sequenceidentifier);

create table if not exists domainevententry
(
  globalindex        bigserial,
  sequencenumber      bigint       not null,
  aggregateidentifier varchar(255) not null,
  eventidentifier     varchar(255) not null
    unique,
  payloadrevision     varchar(255),
  payloadtype         varchar(255) not null,
  timestamp           varchar(255) not null,
  type                 varchar(255),
  metadata            BYTEA,
  payload             BYTEA,
  unique (aggregateidentifier, sequencenumber)
);

alter table domainevententry
  owner to pcoundia;


create table if not exists sagaentry
(
  revision        varchar(255),
  sagaid         varchar(255) not null
    primary key,
  sagatype       varchar(255),
  serializedsaga oid
);

alter table sagaentry
  owner to pcoundia;

create table if not exists snapshotevententry
(
  sequencenumber      bigint       not null,
  aggregateidentifier varchar(255) not null,
  eventidentifier     varchar(255) not null
    unique,
  payloadrevision     varchar(255),
  payloadtype         varchar(255) not null,
  timestamp           varchar(255) not null,
  type                 varchar(255) not null,
  metadata            oid,
  payload              oid          not null,
  primary key (sequencenumber, aggregateidentifier, type)
);

alter table snapshotevententry
  owner to pcoundia;

create table if not exists tokenentry
(
  segment        bigint      not null,
  owner          varchar(255),
  processorname varchar(255) not null,
  timestamp      varchar(255) not null,
  tokentype     varchar(255),
  token          bytea,
  primary key (segment, processorname)
);

alter table tokenentry
  owner to pcoundia;

