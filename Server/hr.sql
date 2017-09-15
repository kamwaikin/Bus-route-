drop table routes;

Create table routes (
  routeNo varchar2(5) not null primary key,
  fares number(3,1) not null
  );
  
insert into routes values ('1',5.5,);
insert into routes values ('1A',6.9);
insert into routes values ('2',4.7);
insert into routes values ('5',5.5);
insert into routes values ('5A',4.1);
insert into routes values ('5C',5.5);
insert into routes values ('6',4.9);
insert into routes values ('7',4.7);
insert into routes values ('8',4.9);
insert into routes values ('8A',4.1);
insert into routes values ('8P',4.1);
insert into routes values ('9',5.5);
insert into routes values ('28',5.5);
insert into routes values ('N21',23.0);
insert into routes values ('N21A',23.0);

commit;