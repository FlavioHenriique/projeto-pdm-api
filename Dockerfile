FROM postgres

ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD flavio22
ENV POSTGRES_DB projeto-pdm

COPY banco/tabelas.sql /docker-entrypoint-initdb.d/
copy banco/insert.sql /docker-entrypoint-initdb.d/
