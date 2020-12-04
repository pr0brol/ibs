DROP TABLE IF EXISTS persons CASCADE;
CREATE TABLE organization (
  id                      BIGSERIAL,
  title                   VARCHAR(80) NOT NULL,
  PRIMARY KEY(id)
);

INSERT INTO organization
(title) VALUES
  ('mtc'),
  ('vtb'),
  ('nix'),
  ('magnit'),
  ('google'),
  ('apple'),
  ('ebay'),
  ('lg'),
  ('bosch'),
  ('samsung');

DROP TABLE IF EXISTS document_side CASCADE;
CREATE TABLE document_side (
  id                     BIGSERIAL,
  content                VARCHAR(1024),
  sign                   BOOLEAN,
  organization_id        BIGSERIAL NOT NULL,
  PRIMARY KEY(id),
  CONSTRAINT fk_organization FOREIGN KEY (organization_id) references organization (id)
);

DROP TABLE IF EXISTS document CASCADE;
CREATE TABLE document (
  id                        BIGSERIAL,
  first_side_id             BIGSERIAL NOT NULL,
  second_side_id            BIGSERIAL NOT NULL,
  PRIMARY KEY(id),
  CONSTRAINT fk_first_side FOREIGN KEY (first_side_id) references document_side (id),
  CONSTRAINT fk_second_side FOREIGN KEY (second_side_id) references document_side (id)
);


select * from organization;

select * from document;

select * from document_side;