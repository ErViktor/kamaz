INSERT INTO roles (name)
SELECT 'ROLE_ADMIN'
WHERE NOT EXISTS (SELECT name FROM roles WHERE name = 'ROLE_ADMIN' );

INSERT INTO roles (name)
SELECT 'ROLE_MODERATOR'
WHERE NOT EXISTS (SELECT name FROM roles WHERE name = 'ROLE_MODERATOR' );

INSERT INTO roles (name)
SELECT 'ROLE_USER'
WHERE NOT EXISTS (SELECT name FROM roles WHERE name = 'ROLE_USER' );
