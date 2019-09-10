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
