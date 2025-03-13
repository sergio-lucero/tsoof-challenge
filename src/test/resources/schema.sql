CREATE SCHEMA IF NOT EXISTS challenge;

CREATE TABLE challenge.company
(
    id                  uuid NOT NULL,
    cuit                character varying(255),
    company_name        character varying(255),
	  created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at          TIMESTAMP,
    CONSTRAINT company_pkey PRIMARY KEY (id)
);

CREATE TABLE challenge.transfer
(
    id                  uuid NOT NULL,
    amount              decimal NOT NULL,
    company_id          uuid NOT NULL,
    debit_account       character varying(255),
    credit_account      character varying(255),
	  created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at          TIMESTAMP,
    CONSTRAINT transfer_pkey PRIMARY KEY (id)
);