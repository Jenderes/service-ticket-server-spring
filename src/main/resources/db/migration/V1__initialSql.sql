CREATE TABLE public.ticket
(
    ticket_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    create_by_id bigint NOT NULL,
    user_assignee_id bigint ,
    update_by_id bigint NOT NULL,
    ticket_information jsonb,
    CONSTRAINT request_pkey PRIMARY KEY (ticket_id)
);
ALTER TABLE public.ticket
    OWNER to postgres;

CREATE TABLE public."user"
(
    user_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    username VARCHAR(50),
    email VARCHAR(50),
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    password VARCHAR(300),
    category VARCHAR(50),
    status VARCHAR(50),
    phone_number VARCHAR(50),
    CONSTRAINT user_id PRIMARY KEY (user_id)
);
ALTER TABLE public."user"
    OWNER to postgres;

CREATE TABLE public.role
(
    role_id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    role_name VARCHAR(50),
    CONSTRAINT role_pkey PRIMARY KEY (role_id)
);
ALTER TABLE public.role
    OWNER to postgres;

CREATE TABLE public.user_role
(
    user_id bigint NOT NULL,
    role_id bigint NOT NULL,
    CONSTRAINT role_role_id FOREIGN KEY (role_id)
        REFERENCES public.role (role_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT user_user_id FOREIGN KEY (user_id)
        REFERENCES public."user" (user_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
ALTER TABLE public.user_role
    OWNER to postgres;
