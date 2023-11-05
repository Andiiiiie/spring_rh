INSERT INTO public.service (id, name) VALUES (0, 'Informatique');
INSERT INTO public.service (id, name) VALUES (1, 'Resource Humaine');

INSERT INTO public.user_table (id, email, password, role, service_id) VALUES (0, 'user@user.user', '$2y$10$.qkbukzzX21D.bqbI.B2R.tvWP90o/Y16QRWVLodw51BHft7ZWbc.', 'USER', null);
INSERT INTO public.user_table (id, email, password, role, service_id) VALUES (1, 'rh@user.user', '$2y$10$.qkbukzzX21D.bqbI.B2R.tvWP90o/Y16QRWVLodw51BHft7ZWbc.', 'RH', 1);
INSERT INTO public.user_table (id, email, password, role, service_id) VALUES (2, 'service@user.user', '$2y$10$.qkbukzzX21D.bqbI.B2R.tvWP90o/Y16QRWVLodw51BHft7ZWbc.', 'SERVICE', 0);
INSERT INTO public.user_table (id, email, password, role, service_id) VALUES (3, 'user2@user.user', '$2y$10$.qkbukzzX21D.bqbI.B2R.tvWP90o/Y16QRWVLodw51BHft7ZWbc.', 'USER', null);

INSERT INTO public.job_category (id, name) VALUES (0, 'Informatique');
INSERT INTO public.job_category (id, name) VALUES (1, 'Ressource Humaine');
INSERT INTO public.job_category (id, name) VALUES (2, 'Comptabilité');
INSERT INTO public.job_category (id, name) VALUES (3, 'Marketing');

INSERT INTO public.requirement_type (id, name) VALUES (0, 'Diplôme');
INSERT INTO public.requirement_type (id, name) VALUES (1, 'Expérience');
INSERT INTO public.requirement_type (id, name) VALUES (2, 'Compétence');
INSERT INTO public.requirement_type (id, name) VALUES (3, 'Loisir');
