insert into station (city)
values ('New York City');
insert into station (city)
values ('Los Angeles');
insert into station (city)
values ('Chicago');
insert into house (house_nr, price, street, model, station_id)
values ('house1', 20010, 'thisho', '2BHK', 1);
insert into house (house_nr, price, street, model, station_id)
values ('house2', 20170, 'thisho', '3BHK', 2);
insert into house (house_nr, price, street, model, station_id)
values ('house3', 20160, 'thisho', '4BHK', 3);
insert into house (house_nr, price, street, model, station_id)
values ('house4', 20020, 'thisho', '2BHK', 3);
insert into house (house_nr, price, street, model, station_id)
values ('house5', 20030, 'homo', '1BHK', null);
insert into customer (customer_number, first_name, last_name)
values (111111, 'Jeff', 'Heisenberg');
insert into customer (customer_number, first_name, last_name)
values (222222, 'John', 'Doe');
insert into customer (customer_number, first_name, last_name)
values (333333, 'Richard', 'Stallman');
insert into rental (
        rental_date,
        house_house_nr,
        driver_customer_number,
        rental_station_id,
        return_date,
        return_station_id,
        km
    )
values (
        CURRENT_DATE(),
        'house1',
        111111,
        1,
        CURRENT_DATE(),
        2,
        500
    );
insert into rental (
        rental_date,
        house_house_nr,
        driver_customer_number,
        rental_station_id,
        return_date,
        return_station_id,
        km
    )
values (
        CURRENT_DATE(),
        'house2',
        222222,
        3,
        CURRENT_DATE(),
        2,
        10000
    );
insert into rental (
        rental_date,
        house_house_nr,
        driver_customer_number,
        rental_station_id,
        return_date,
        return_station_id,
        km
    )
values (
        CURRENT_DATE(),
        'house3',
        333333,
        3,
        CURRENT_DATE(),
        2,
        500
    );
insert into rental (
        rental_date,
        house_house_nr,
        driver_customer_number,
        rental_station_id,
        return_date,
        return_station_id,
        km
    )
values (
        CURRENT_DATE(),
        'house4',
        111111,
        1,
        CURRENT_DATE(),
        2,
        500
    );
insert into rental (
        rental_date,
        house_house_nr,
        driver_customer_number,
        rental_station_id,
        return_date,
        return_station_id,
        km
    )
values (
        CURRENT_DATE(),
        'house5',
        111111,
        1,
        null,
        null,
        null
    );