-- senha 13241324
insert into usuario(nome, email, senha, role)
values ('Admin', 'admin@admin', '$2a$10$SEoCtyZuYwyjgZhlE0/Y5eRUCQxJzaDHUep5Hh6NJYGCKv/UqnZze', 'ROLE_ADMIN');

insert into usuario(nome, email, senha, role)
values ('Eu mesmo', 'eu@email', '$2a$10$SEoCtyZuYwyjgZhlE0/Y5eRUCQxJzaDHUep5Hh6NJYGCKv/UqnZze', 'ROLE_MEMBER');
