create TABLE listings (
    id text NOT NULL,
    dealer_id text NOT NULL,
    code text NOT NULL,
    make text NOT NULL,
    model text NOT NULL,
    power int NOT NULL,
    year text NOT NULL,
    color text NOT NULL,
    price bigint,
    UNIQUE(dealer_id,code)
);