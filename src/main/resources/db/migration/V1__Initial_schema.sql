create table "author"
(
    name text                     not null primary key,
    dob  timestamp with time zone not null
);

create table "book"
(
    title  text not null primary key,
    genre  text not null,
    price  text not null,
    author text not null,
    constraint fk_book_written_by foreign key (author) references "author" (name)
);

create table "member"
(
    username     text not null primary key,
    email        text not null,
    address      text not null,
    phone_number text not null
);

create table "loan"
(
    member    text                     not null,
    book      text                     not null,
    lend_date timestamp with time zone not null,
    due_date  timestamp with time zone not null,
    primary key (member, book),
    constraint fk_loan_member foreign key (member) references "member" (username),
    constraint fk_loan_book foreign key (book) references "book" (title)
);