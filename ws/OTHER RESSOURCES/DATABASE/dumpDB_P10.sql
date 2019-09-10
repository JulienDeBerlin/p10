--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.10
-- Dumped by pg_dump version 9.6.10

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: address; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.address (
    id integer NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    additional_address_field1 character varying(255),
    additional_address_field2 character varying(255),
    city character varying(255) NOT NULL,
    house_number integer NOT NULL,
    road character varying(255) NOT NULL,
    zip_code character varying(255) NOT NULL
);


ALTER TABLE public.address OWNER TO admin;

--
-- Name: book; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.book (
    id integer NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    date_purchase date NOT NULL,
    status character varying(255) NOT NULL,
    book_reference_id integer,
    librairy_id integer,
    reservation bytea
);


ALTER TABLE public.book OWNER TO admin;

--
-- Name: book_reference; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.book_reference (
    id integer NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    author_first_name character varying(255) NOT NULL,
    author_surname character varying(255) NOT NULL,
    isbn13 character(13) NOT NULL,
    publisher character varying(255) NOT NULL,
    summary character varying(255) NOT NULL,
    title character varying(255) NOT NULL,
    year_publication smallint NOT NULL
);


ALTER TABLE public.book_reference OWNER TO admin;

--
-- Name: book_references_tags; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.book_references_tags (
    book_reference_id integer NOT NULL,
    tag_id integer NOT NULL
);


ALTER TABLE public.book_references_tags OWNER TO admin;

--
-- Name: customer; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.customer (
    id integer NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    date_expiration_membership date NOT NULL,
    email character varying(255) NOT NULL,
    first_name character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    phone character varying(255),
    sex character(1) NOT NULL,
    surname character varying(255) NOT NULL,
    address integer
);


ALTER TABLE public.customer OWNER TO admin;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO admin;

--
-- Name: id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.id_seq OWNER TO admin;

--
-- Name: librairy; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.librairy (
    id integer NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    name character varying(255) NOT NULL,
    address_id integer
);


ALTER TABLE public.librairy OWNER TO admin;

--
-- Name: loan; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.loan (
    id integer NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    date_back date NOT NULL,
    date_begin date NOT NULL,
    date_end date NOT NULL,
    number_extensions integer NOT NULL,
    book_id integer,
    customer_id integer
);


ALTER TABLE public.loan OWNER TO admin;

--
-- Name: reservation; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.reservation (
    id integer NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    date_book_available_notification timestamp without time zone,
    date_reservation timestamp without time zone NOT NULL,
    book_reference_id integer NOT NULL,
    customer_id integer NOT NULL,
    librairy_id integer NOT NULL,
    date_end_reservation timestamp without time zone,
    book_id integer
);


ALTER TABLE public.reservation OWNER TO admin;

--
-- Name: tag; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.tag (
    id integer NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.tag OWNER TO admin;

--
-- Data for Name: address; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.address (id, created_at, updated_at, additional_address_field1, additional_address_field2, city, house_number, road, zip_code) FROM stdin;
33	2019-04-21 17:17:06.187	2019-04-21 17:17:06.187	\N	\N	Paris	12	rue de Milan	75009
35	2019-04-21 18:33:08.388	2019-04-21 18:33:08.388	\N	\N	Paris	13	rue de Milan	75009
\.


--
-- Data for Name: book; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.book (id, created_at, updated_at, date_purchase, status, book_reference_id, librairy_id, reservation) FROM stdin;
4	2019-04-21 17:06:24.222166	2019-04-27 16:47:50.561	2019-04-21	BORROWED	3	2	\N
9	2019-04-21 17:06:24.222166	2019-04-27 16:47:50.608	2019-04-21	BORROWED	1	1	\N
3	2019-04-21 17:06:24.222166	2019-04-27 16:52:27.347	2019-04-21	BORROWED	1	1	\N
5	2019-04-21 17:06:24.222166	2019-04-28 01:16:10.702	2019-04-21	AVAILABLE	3	2	\N
6	2019-04-21 17:06:24.222166	2019-04-28 01:16:10.718	2019-04-21	AVAILABLE	3	2	\N
7	2019-04-21 17:06:24.222166	2019-04-28 01:16:10.719	2019-04-21	AVAILABLE	2	1	\N
147	2019-04-27 18:11:10.6	2019-04-28 01:25:44.866	2019-03-23	AVAILABLE	1	1	\N
143	2019-04-27 18:10:17.281	2019-04-28 01:26:06.758	2019-03-23	AVAILABLE	1	1	\N
8	2019-04-21 17:06:24.222166	2019-04-28 01:16:10.721	2019-04-21	BORROWED	2	1	\N
1	2019-04-21 17:06:24.222166	2019-04-28 01:18:41.877	2019-04-21	BORROWED	1	1	\N
2	2019-04-21 17:06:24.222166	2019-04-21 17:06:24.222166	2019-04-21	BOOKED	2	1	\N
148	2019-08-11 14:45:37.991054	2019-08-11 15:57:47.496	2019-07-01	BORROWED	4	3	\N
149	2019-08-17 18:38:44.27041	2019-08-17 18:38:44.27041	2019-08-01	BORROWED	5	3	\N
150	2019-08-17 18:38:44.27041	2019-08-17 18:38:44.27041	2019-08-01	BORROWED	5	3	\N
151	2019-09-02 20:09:33.73291	2019-09-02 20:09:33.73291	2019-09-02	AVAILABLE	6	1	\N
\.


--
-- Data for Name: book_reference; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.book_reference (id, created_at, updated_at, author_first_name, author_surname, isbn13, publisher, summary, title, year_publication) FROM stdin;
1	2019-04-21 16:59:57.169375	2019-04-21 16:59:57.169375	Jean	Delors	8787878787777	Flammarion	Un classique des récits de voyage.	Mon Italie	1958
3	2019-04-21 16:59:57.169375	2019-04-21 16:59:57.169375	Jean	Tricot	7776666666666	Sophis	100 recettes végétariennes	La cuisine végétarienne en Italie	2018
2	2019-04-21 16:59:57.169375	2019-04-21 16:59:57.169375	Pierre 	Sur	5555554444444	Grasset	Un roman initiatique	Les septs collines en Italie	1993
4	2019-08-11 14:43:53.00139	2019-08-11 14:43:53.00139	Virginia	Woolf	9876565656566	Gallimard	Le destin de 5 amis, le temps d'une journée	Les Vagues	2018
5	2019-08-17 18:36:29.633225	2019-08-17 18:36:29.633225	Milan	Kundera	8787878798765	Gallimard	Lore ipsem jeuwhd dwhuhdeuwd. 	L'insoutenable légèreté de l'être	2014
6	2019-09-02 20:09:07.951615	2019-09-02 20:09:07.951615	Mickael	Cunningham	9899999898989	Gallimard	Lore ipsem jeuwhd dwhuhdeuwd. 	Les Heures	1987
\.


--
-- Data for Name: book_references_tags; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.book_references_tags (book_reference_id, tag_id) FROM stdin;
1	1
1	2
1	3
2	4
2	5
3	6
3	1
3	2
2	6
\.


--
-- Data for Name: customer; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.customer (id, created_at, updated_at, date_expiration_membership, email, first_name, password, phone, sex, surname, address) FROM stdin;
23	2019-04-21 16:37:05.061	2019-09-09 11:00:51.508	2018-08-23	berthoudjulien@gmail.com	Pierre	$2a$12$7hKw7afrJEug/OJk9qnZY.6qxtm3Jn364MPWMkqGCOpPJyjORvWnK	0385303955	M	Dubuquette	33
86	2019-05-12 10:13:14.915201	2019-09-04 15:25:29.785	2021-05-12	schatzmeister@berliner-ringer.de	Malika	$2a$12$Inul40jufBwxT9Hajqp2kuFoPML8pzHT1ZiUq6PEvSlctI2DfLFea	004915781870467	M	Djarir	33
85	2019-04-23 18:20:25.321	2019-09-04 15:26:22.105	2020-06-23	aicha@yahoo.fr	Aicha	$2a$12$Inul40jufBwxT9Hajqp2kuFoPML8pzHT1ZiUq6PEvSlctI2DfLFea	0385303955	F	Djarir	33
34	2019-04-21 18:33:08.365	2019-09-04 15:26:08.653	2020-06-23	julien_berthoud@yahoo.fr	Julien	$2a$12$Inul40jufBwxT9Hajqp2kuFoPML8pzHT1ZiUq6PEvSlctI2DfLFea	0123232344	F	Berthoud	35
\.


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.hibernate_sequence', 473, true);


--
-- Name: id_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.id_seq', 1, true);


--
-- Data for Name: librairy; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.librairy (id, created_at, updated_at, name, address_id) FROM stdin;
1	2019-04-21 16:55:53.677666	2019-04-21 16:55:53.677666	Saint-Michel	\N
2	2019-04-21 16:55:53.677666	2019-04-21 16:55:53.677666	Leon Blum	\N
3	2019-05-01 18:12:46.979222	2019-05-01 18:12:46.979222	Helene Nathan	\N
\.


--
-- Data for Name: loan; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.loan (id, created_at, updated_at, date_back, date_begin, date_end, number_extensions, book_id, customer_id) FROM stdin;
118	2019-04-27 16:34:44.831	2019-04-27 16:45:41.373	2019-04-27	2019-04-27	2019-04-27	0	9	85
152	2019-04-28 01:13:50.263	2019-04-28 01:16:10.719	2019-04-28	2019-04-28	2019-05-26	0	6	85
36	2019-04-21 19:41:27.647	2019-04-28 01:16:10.72	2019-04-28	2019-04-13	2019-07-16	3	7	85
132	2019-04-27 16:57:11.309	2019-04-28 01:18:41.887	2019-04-28	2019-04-27	2019-04-27	0	1	85
151	2019-04-28 01:00:36.084	2019-04-28 01:25:44.875	2019-04-28	2019-04-28	2019-05-03	0	147	85
150	2019-04-28 00:56:09.548	2019-04-28 01:26:06.768	2019-04-28	2019-04-28	2019-05-26	0	143	85
106	2019-04-26 17:27:15.814	2019-04-27 14:51:21.405	1900-01-01	2019-04-13	2019-12-03	8	1	85
96	2019-04-23 19:44:53.832	2019-04-23 19:44:53.832	2019-04-22	2019-04-13	2019-04-23	0	1	85
92	2019-04-23 19:40:36.988	2019-04-23 19:40:36.988	2019-04-22	2019-04-13	2019-04-23	0	1	85
88	2019-04-23 18:20:25.516	2019-04-23 18:20:25.516	2019-04-22	2019-04-13	2019-04-23	0	1	85
111	2019-04-27 14:50:13.795	2019-04-27 14:50:13.795	2019-04-22	2019-04-13	2019-04-23	0	1	85
167	2019-05-12 10:16:54.879806	2019-05-12 10:16:54.879806	1900-01-01	2019-01-01	2019-05-01	1	1	86
169	2019-05-12 10:16:54.879806	2019-05-12 10:16:54.879806	1900-01-01	2019-01-01	2019-05-01	1	3	86
115	2019-04-27 16:28:01.336	2019-05-02 17:28:28.102	1900-01-01	2019-04-27	2019-06-14	5	4	34
231	2019-08-11 15:47:51.753	2019-08-11 15:47:51.753	1900-01-01	2019-08-11	2019-09-08	0	148	85
40	2019-04-21 19:46:10.713	2019-04-28 01:16:10.721	1900-01-01	2019-04-13	2028-04-23	0	8	23
232	2019-08-17 18:41:06.602731	2019-08-17 18:41:06.602731	1900-01-01	2019-08-11	2019-09-08	0	149	85
233	2019-08-17 18:41:06.602731	2019-08-17 18:41:06.602731	1900-01-01	2019-08-11	2019-09-15	0	150	23
168	2019-05-12 10:16:54.879806	2019-05-12 10:16:54.879806	1900-01-01	2019-01-01	2019-05-01	0	2	86
148	2019-04-27 18:35:47.714	2019-04-28 01:16:10.717	2019-04-28	2019-04-27	2019-12-25	0	5	85
\.


--
-- Data for Name: reservation; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.reservation (id, created_at, updated_at, date_book_available_notification, date_reservation, book_reference_id, customer_id, librairy_id, date_end_reservation, book_id) FROM stdin;
1	2019-08-11 00:00:00	2019-08-13 10:08:27.32886	\N	2019-08-11 00:00:00	4	34	3	\N	\N
\.


--
-- Data for Name: tag; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.tag (id, created_at, updated_at, name) FROM stdin;
6	2019-04-21 16:55:18.866574	2019-04-21 16:55:18.866574	écologie
5	2019-04-21 16:55:18.866574	2019-04-21 16:55:18.866574	aventure
4	2019-04-21 16:55:18.866574	2019-04-21 16:55:18.866574	sport
3	2019-04-21 16:55:18.866574	2019-04-21 16:55:18.866574	histoire
2	2019-04-21 16:55:18.866574	2019-04-21 16:55:18.866574	italie
1	2019-04-21 16:53:17.308635	2019-04-21 16:53:17.308635	architecture
\.


--
-- Name: address address_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.address
    ADD CONSTRAINT address_pkey PRIMARY KEY (id);


--
-- Name: book book_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT book_pkey PRIMARY KEY (id);


--
-- Name: book_reference book_reference_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.book_reference
    ADD CONSTRAINT book_reference_pkey PRIMARY KEY (id);


--
-- Name: book_references_tags books_tags_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.book_references_tags
    ADD CONSTRAINT books_tags_pkey PRIMARY KEY (book_reference_id, tag_id);


--
-- Name: customer customer_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (id);


--
-- Name: librairy librairy_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.librairy
    ADD CONSTRAINT librairy_pkey PRIMARY KEY (id);


--
-- Name: loan loan_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.loan
    ADD CONSTRAINT loan_pkey PRIMARY KEY (id);


--
-- Name: reservation reservation_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT reservation_pkey PRIMARY KEY (id);


--
-- Name: tag tag_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.tag
    ADD CONSTRAINT tag_pkey PRIMARY KEY (id);


--
-- Name: reservation fk41v6ueo0hiran65w8y1cta2c2; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT fk41v6ueo0hiran65w8y1cta2c2 FOREIGN KEY (customer_id) REFERENCES public.customer(id);


--
-- Name: book_references_tags fk5tndkjml6iccyj5d5oe64f6yt; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.book_references_tags
    ADD CONSTRAINT fk5tndkjml6iccyj5d5oe64f6yt FOREIGN KEY (tag_id) REFERENCES public.tag(id);


--
-- Name: reservation fk85wuo9axomae850vkikqgi85j; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT fk85wuo9axomae850vkikqgi85j FOREIGN KEY (book_reference_id) REFERENCES public.book_reference(id);


--
-- Name: loan fk88c0ydlo57pcgp137tntrgqx1; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.loan
    ADD CONSTRAINT fk88c0ydlo57pcgp137tntrgqx1 FOREIGN KEY (book_id) REFERENCES public.book(id);


--
-- Name: book fk90relwe23chfb5q2jd671g2y; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT fk90relwe23chfb5q2jd671g2y FOREIGN KEY (book_reference_id) REFERENCES public.book_reference(id);


--
-- Name: loan fkcwv05agfqwg5ndy6adbefsx7d; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.loan
    ADD CONSTRAINT fkcwv05agfqwg5ndy6adbefsx7d FOREIGN KEY (customer_id) REFERENCES public.customer(id);


--
-- Name: customer fkdcvj0ju58s9csxkii6tusumdl; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT fkdcvj0ju58s9csxkii6tusumdl FOREIGN KEY (address) REFERENCES public.address(id);


--
-- Name: librairy fkdnm0hhmy7brpok95v38it1atk; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.librairy
    ADD CONSTRAINT fkdnm0hhmy7brpok95v38it1atk FOREIGN KEY (address_id) REFERENCES public.address(id);


--
-- Name: reservation fkirxtcw4s6lhwi6l9ocrk6bjfy; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT fkirxtcw4s6lhwi6l9ocrk6bjfy FOREIGN KEY (book_id) REFERENCES public.book(id);


--
-- Name: book fkite71jfuk7sk5f6qxlraxvmy0; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT fkite71jfuk7sk5f6qxlraxvmy0 FOREIGN KEY (librairy_id) REFERENCES public.librairy(id);


--
-- Name: reservation fklw8vxju9uego279tdjo1cxq92; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT fklw8vxju9uego279tdjo1cxq92 FOREIGN KEY (librairy_id) REFERENCES public.librairy(id);


--
-- Name: book_references_tags fkpylt5h70mg0cs3w2exwejp4e3; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.book_references_tags
    ADD CONSTRAINT fkpylt5h70mg0cs3w2exwejp4e3 FOREIGN KEY (book_reference_id) REFERENCES public.book_reference(id);


--
-- PostgreSQL database dump complete
--

