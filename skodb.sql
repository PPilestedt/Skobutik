
drop database if exists skodb;
create database skodb;
use skodb;

create table if not exists märke(
id int not null auto_increment,
primary key (id),
namn varchar(50) not null,
created timestamp default current_timestamp,
lastupdated timestamp default current_timestamp on update current_timestamp
);

insert into märke (namn) values
('Underground'),
('Converse'),
('Nike'),
('Prada'),
('Ecco');

create table if not exists modell
(id int not null auto_increment,
namn varchar(50) not null,
primary key (id),
created timestamp DEFAULT CURRENT_TIMESTAMP,
lastUpdate timestamp default CURRENT_TIMESTAMP
ON UPDATE CURRENT_TIMESTAMP);

insert into modell (namn, id) VALUES ('barnsko', 1);
insert into modell (namn, id) VALUES ('klacksko', 2);
insert into modell (namn, id) VALUES ('sportsko', 3);
insert into modell (namn, id) VALUES ('promenadsko', 4);
insert into modell (namn, id) VALUES ('vandringskänga', 5);


create table if not exists sko
(id int not null auto_increment,
märkesid int not null,
färg varchar(20) not null,
storlek int not null,
pris int not null,
created timestamp DEFAULT CURRENT_TIMESTAMP,
lastUpdate timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
primary key (id),
foreign key (märkesid) references märke(id));

insert into sko (märkesid, färg, storlek, pris) values
(1, 'Svart', 52, 1500),
(3, 'Blå', 38, 800),
(2, 'Blå', 42, 800),
(3, 'Grön', 42, 600),
(4, 'Beige', 35, 900),
(2, 'Röd', 42, 500),
(5, 'Svart', 39, 1000),
(5, 'Brun', 45, 1200);

create table if not exists typ
(skoid int not null,
modellid int not null,
primary key (skoid, modellid),
foreign key (modellid) references modell(id),
foreign key (skoid) references sko(id),
created timestamp DEFAULT CURRENT_TIMESTAMP,
lastUpdate timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP);

insert into typ(skoid, modellid) values
(1,2),
(1,5),
(2,3),
(3,3),
(4,3),
(5,3),
(6,4),
(7,1),
(7,3);

create table if not exists lager (
    id int not null auto_increment,
    primary key (id),
    skoid int not null,
    foreign key (skoid) references sko(id) on delete cascade,
    antal int,
    created timestamp DEFAULT CURRENT_TIMESTAMP,
    lastUpdate timestamp default CURRENT_TIMESTAMP
);

insert into lager (skoid, antal) values 
(1,10),
(2,9),
(3,8),
(4,7),
(5,6),
(6,5),
(7,4),
(8,3);

create table if not exists kund
(id int not null auto_increment,
förnamn varchar(50) not null,
efternamn varchar(50) not null,
lösenord varchar(50) not null,
ort varchar(50) not null,
primary key (id),
created timestamp DEFAULT CURRENT_TIMESTAMP,
lastUpdate timestamp default CURRENT_TIMESTAMP
ON UPDATE CURRENT_TIMESTAMP);

insert into kund (förnamn, efternamn, ort, lösenord) VALUES ('Agda','Ädla','Spånga', '123');
insert into kund (förnamn, efternamn, ort, lösenord) VALUES ('Pål','Pålsson','Spånga', '123');
insert into kund (förnamn, efternamn, ort, lösenord) VALUES ('Jason','Vorhees','Camp Crystal', '123');
insert into kund (förnamn, efternamn, ort, lösenord) VALUES ('Freddy','Krueger','Springwood', '123');
insert into kund (förnamn, efternamn, ort, lösenord) VALUES ('Michael','Myers','Haddonfield', '123');
insert into kund (förnamn, efternamn, ort, lösenord) VALUES ('Nancy','Thompson','Springwood', '123');

create table if not exists betyg
(id int not null auto_increment,
poäng int not null,
kommentar varchar(200),
kundid int not null,
skoid int not null,
primary key (id),
foreign key (kundid) references kund(id) on delete cascade,
-- när en kund vill raderas ska betygen försvinna för det får inte förekomma anonyma betyg
foreign key (skoid) references sko(id),

created timestamp DEFAULT CURRENT_TIMESTAMP,
lastUpdate timestamp default CURRENT_TIMESTAMP
ON UPDATE CURRENT_TIMESTAMP);

insert into betyg (kommentar, kundid, skoid, poäng) VALUES ('Tysta skor :)', 3, 2, 40);
insert into betyg (kommentar, kundid, skoid, poäng) VALUES ('Lite dyra...', 4, 1, 30);
insert into betyg (kommentar, kundid, skoid, poäng) VALUES ('Usch!', 2, 4, 10);
insert into betyg (kommentar, kundid, skoid, poäng) VALUES ('Fin färg!', 5, 2, 20);
insert into betyg (kommentar, kundid, skoid, poäng) VALUES ('Jag har väntat', 1, 5, 10);

create table if not exists beställning
(
id int not null auto_increment,
kundid int,
betald boolean DEFAULT false,
created timestamp DEFAULT CURRENT_TIMESTAMP,
lastUpdate timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
primary key (id),
foreign key (kundid) references kund(id) on delete set null
-- när en kund vill raderas ska beställningens historik ligga kvar men kundens info försvinner
);

insert into beställning (kundid,created, betald) values
(3,'2020-10-01', true),
(3,'2020-10-03', true),
(4,'2020-10-20', true),
(1,'1998-01-10', true),
(5,'2015-12-12', true),
(2,'2017-02-23', true);

create table if not exists shoppinglista
(
beställningsid int not null,
skoid int not null,
antal int not null, 
primary key (beställningsid, skoid),
foreign key (beställningsid) references beställning(id),
foreign key (skoid) references sko(id),
created timestamp DEFAULT CURRENT_TIMESTAMP,
lastUpdate timestamp default CURRENT_TIMESTAMP
ON UPDATE CURRENT_TIMESTAMP);

create table if not exists slutILager
(id int not null auto_increment,
skoid int not null,
primary key (id),
foreign key (skoid) references sko(id),
datum timestamp DEFAULT CURRENT_TIMESTAMP);


-- index på skofärgen så att det går snabbare att söka på webbshoppen
CREATE INDEX ix_sko_färg ON sko (färg);

-- multi-index på kundernas förnamn och efternamn så att butikspersonalen kan söka efter kunder snabbt
CREATE INDEX ix_kund_namn ON kund(förnamn,efternamn);

-- index på märkesnamn så att det går snabbt att sortera på webbshoppen
Create INDEX ix_märke_namn ON märke(namn);


-- on update cascade har vi valt att inte använda då vi 
-- använder oss av syntetiska id:n som nycklar. 
-- Antingen har vi on update cascade på alla FK eller så ser vi till att inte ändra på ID:n


insert into shoppinglista (beställningsid, skoid, antal) VALUES (1, 3, 1);
insert into shoppinglista (beställningsid, skoid, antal) VALUES (2, 2, 1);
insert into shoppinglista (beställningsid, skoid, antal) VALUES (4, 5, 1);
insert into shoppinglista (beställningsid, skoid, antal) VALUES (3, 1, 1);
insert into shoppinglista (beställningsid, skoid, antal) VALUES (5, 2, 1);
insert into shoppinglista (beställningsid, skoid, antal) VALUES (6, 4, 1);
insert into shoppinglista (beställningsid, skoid, antal) VALUES (6, 7, 1);


-- SELECT * from beställning;
-- SELECT * from betyg;
-- SELECT * from kund;
-- SELECT * from modell;
-- SELECT * from märke;
-- SELECT * from shoppinglista;
-- SELECT * from sko;
-- SELECT * from typ;

 -- drop procedure addToCart;
delimiter //
create procedure addToCart (IN _kundid int, IN _beställningsid int, IN _produktid int)

begin

declare skoCount int default 0;
declare beställningsCount int default 0;

declare exit handler for sqlexception
	begin
		rollback;
        select ('unknown error, rollback done') as error;
    end;    
    
declare exit handler for 1452
		begin
			rollback;
            select ('cannot add or update a child row, rollback done') as error;
        end;
        
declare exit handler for 1048
		begin
			rollback;
            select ('produktid can not be null, rollback done') as error;
        end;        
	

set autocommit = 0;
start transaction;

if (_beställningsid is not null) then 
	select count(*) into beställningsCount 
	from beställning where _beställningsid = beställning.id ;
end if;

if (beställningsCount > 0) then
	select count(*) into skoCount from shoppinglista 
    join beställning on shoppinglista.beställningsid = beställning.id
    where _beställningsid = beställning.id and _produktid = shoppinglista.skoid;
		if (skoCount > 0) then
			update shoppinglista set antal = (antal +1) 
            where _beställningsid = shoppinglista.beställningsid and _produktid = shoppinglista.skoid;
		else 
			insert into shoppinglista (beställningsid, skoid, antal) values (_beställningsid, _produktid, 1);
		end if;
	update lager set lager.antal = (lager.antal- 1) where lager.skoid = _produktid;
else 
	insert into beställning (kundid) values (_kundid);
	insert into shoppinglista (beställningsid, skoid, antal) values (LAST_INSERT_ID(), _produktid, 1);
    update lager set lager.antal = (lager.antal- 1) where lager.skoid = _produktid;
end if; 

commit;
set autocommit = 1;

end //
delimiter ;



-- select * FROM shoppinglista;
-- select * FROM beställning;
-- select * FROM lager;

select * from beställning 
right join shoppinglista
on shoppinglista.beställningsid = beställning.id
join lager
on shoppinglista.skoid = lager.skoid
order by beställning.id;

 -- drop trigger after_lager_update;
delimiter //
create trigger after_lager_update
after update on lager
for each row
begin
	if (new.antal = 0) then
		insert into slutILager (skoid) values (old.skoid);
    end if;
end //
delimiter ;

call addToCart(1, 1, null);

select * from slutILager;

delimiter //
create function medelbetyg (_produktid int)
	returns int
    reads SQL data
    
	begin
		declare medelvärde int default 0;
        select avg(poäng) into medelvärde from betyg where skoid = _produktid;
        return medelvärde;
    end //
delimiter ;

select medelbetyg(2);
select medelbetyg(6);

create view medelbetygIText as
SELECT sko.id AS skoid,
    MEDELBETYG(betyg.skoid) AS medelvärde,
    (CASE
         WHEN (MEDELBETYG(skodb.betyg.skoid) <= 15) THEN 'missnöjd'
         WHEN (MEDELBETYG(skodb.betyg.skoid) <= 25) THEN 'ganska nöjd'
         WHEN (MEDELBETYG(skodb.betyg.skoid) <= 35) THEN 'nöjd'
         WHEN (MEDELBETYG(skodb.betyg.skoid) <= 40) THEN 'mycket nöjd'
        END) AS betygstext
FROM skodb.sko
LEFT JOIN skodb.betyg ON sko.id = betyg.skoid
GROUP BY sko.id;
 
 -- drop procedure rate;
 delimiter //
 
 create procedure rate (IN _kund int, IN _produktid int, IN _betyg int, IN _kommentar varchar(200))
	
    begin
		declare reviewCount int default 0;
        
		declare exit handler for sqlexception
		begin
			rollback;
			select ('unknown error, rollback done') as error;
		end; 
        
        declare exit handler for 1452
		begin
			rollback;
            select ('cannot add or update a child row, rollback done') as error;
        end;
        
		declare exit handler for 1048
			begin
				rollback;
				select ('kund, produktid and/or poäng can not be null, rollback done') as error;
			end;   
            
		set autocommit = 0;
        start transaction;
			select count(*) into reviewCount from betyg where kundid = _kund and skoid = _produktid;
			if (reviewCount = 0) then
				insert into betyg (poäng, kommentar, kundid, skoid) values (_betyg, _kommentar, _kund, _produktid);
			end if;
		
        commit;
        set autocommit = 1;
    end //
delimiter ;

call rate(3,3, 40, 'Mina skooor');

select * from betyg

