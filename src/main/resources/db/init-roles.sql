-- Script to initialize the roles in the database

-- Delete existing roles first (optional, remove this if you want to keep existing roles)
-- TRUNCATE TABLE roles RESTART IDENTITY CASCADE;

-- Insert the standard roles
INSERT INTO roles (name) VALUES ('ADMIN') ON CONFLICT (name) DO NOTHING;
INSERT INTO roles (name) VALUES ('USER') ON CONFLICT (name) DO NOTHING;
INSERT INTO roles (name) VALUES ('MANAGER') ON CONFLICT (name) DO NOTHING;

TRUNCATE  TABLE users RESTART IDENTITY CASCADE;

TRUNCATE TABLE refresh_tokens RESTART IDENTITY CASCADE;

ALTER TABLE users DROP COLUMN IF EXISTS id_refresh_token;

ALTER TABLE refresh_tokens DROP COLUMN IF EXISTS id_user;

ALTER TABLE refresh_tokens ALTER COLUMN token TYPE VARCHAR(1024);
-- Create a default admin user with password 'admin123' (optional)
-- The password should be encoded with BCrypt in a real application
-- INSERT INTO users (login, password, id_role) 
-- SELECT 'admin', '$2a$10$7PtcjEnWb/ZkgyAk.RABs.EShBnWvmMiHm7IO4UjNeBWpX1uVA2a.', id 
-- FROM roles WHERE name = 'ADMIN'
-- ON CONFLICT (login) DO NOTHING;
