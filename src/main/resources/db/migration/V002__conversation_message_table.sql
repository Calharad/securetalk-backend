create table conversations (
    id bigserial primary key unique not null,
    conversation_name varchar(255),
    creator_id int4 not null references users(id),
    owner_id int4 not null references users(id)
);

create table conversation_members (
    conversation_id int8 not null references conversations(id),
    user_id int4 not null references users(id),
    primary key (conversation_id, user_id)
);

create table messages (
    id bigserial primary key unique not null,
    content text not null,
    message_date timestamptz not null,
    creator_id int4 not null references users(id),
    conversation_id int8 not null references conversations(id)
);
