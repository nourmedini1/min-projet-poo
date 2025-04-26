-- Script to initialize the roles in the database


-- Insert the standard roles
INSERT INTO roles (name) VALUES ('ADMIN') ON CONFLICT (name) DO NOTHING;
INSERT INTO roles (name) VALUES ('USER') ON CONFLICT (name) DO NOTHING;
INSERT INTO roles (name) VALUES ('MANAGER') ON CONFLICT (name) DO NOTHING;


-- Insert profiles
INSERT INTO profiles (label) VALUES ('BAC') ON CONFLICT (label) DO NOTHING;
INSERT INTO profiles (label) VALUES ('BAC+3') ON CONFLICT (label) DO NOTHING;
INSERT INTO profiles (label) VALUES ('BAC+5') ON CONFLICT (label) DO NOTHING;

-- Insert domains
INSERT INTO domains (label) VALUES ('interactions avec le client') ON CONFLICT (label) DO NOTHING;
INSERT INTO domains (label) VALUES ('excel') ON CONFLICT (label) DO NOTHING;
INSERT INTO domains (label) VALUES ('presentation des idées') ON CONFLICT (label) DO NOTHING;
INSERT INTO domains (label) VALUES ('leadership') ON CONFLICT (label) DO NOTHING;
INSERT INTO domains (label) VALUES ('gestion de projet') ON CONFLICT (label) DO NOTHING;

-- Insert structures
INSERT INTO structures (label) VALUES ('direction generale') ON CONFLICT (label) DO NOTHING;
INSERT INTO structures (label) VALUES ('direction regionale') ON CONFLICT (label) DO NOTHING;
INSERT INTO structures (label) VALUES ('service informatique') ON CONFLICT (label) DO NOTHING;

INSERT INTO courses (title, year, budget, duration, id_domain) VALUES
('Formation Excel Avancée', 2023, 5000.00, 5, (SELECT id FROM domains WHERE label = 'excel')),
('Leadership et Management', 2023, 8000.00, 7, (SELECT id FROM domains WHERE label = 'leadership')),
('Techniques de Presentation', 2023, 3000.00, 3, (SELECT id FROM domains WHERE label = 'presentation des idées')),
('Gestion de Projet', 2023, 6000.00, 4, (SELECT id FROM domains WHERE label = 'gestion de projet')),
('Interactivité Client', 2023, 4000.00, 6, (SELECT id FROM domains WHERE label = 'interactions avec le client'));

-- Insert employers
INSERT INTO employers (name) VALUES ('Entreprise A') ;
INSERT INTO employers (name) VALUES ('Entreprise B') ;

-- Insert instructors
INSERT INTO instructors (first_name, last_name, email, phone, type, id_employer) VALUES
                                                                                  ('John', 'Doe', 'john.doe@example.com', '51234567', 'INTERNAL', (SELECT id FROM employers WHERE name = 'Entreprise A')),
                                                                                  ('Jane', 'Smith', 'jane.smith@example.com', '61234567', 'EXTERNAL', (SELECT id FROM employers WHERE name = 'Entreprise B'));

-- Insert participants
INSERT INTO participants (first_name, last_name, email, phone, id_structure, id_profile, id_course) VALUES
                                                                                                   ('Alice', 'Brown', 'alice.brown@example.com', '71234567', (SELECT id FROM structures WHERE label = 'direction generale'), (SELECT id FROM profiles WHERE label = 'BAC+3'), (SELECT id FROM courses WHERE title = 'Formation Excel Avancée')),
                                                                                                   ('Bob', 'White', 'bob.white@example.com', '81234567', (SELECT id FROM structures WHERE label = 'direction regionale'), (SELECT id FROM profiles WHERE label = 'BAC+5'), (SELECT id FROM courses WHERE title = 'Leadership et Management'));





