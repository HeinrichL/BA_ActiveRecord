create table rezeptionisten(
id int primary key identity(1,1),
vorname nvarchar(max),
nachname nvarchar(max))

create table trainer(
id int primary key identity(1,1),
vorname nvarchar(max),
nachname nvarchar(max))

create table kunden(
kundennummer int primary key identity(1,1),
vorname nvarchar(max),
nachname nvarchar(max),
adresse_strasse nvarchar(max),
adresse_hausnummer nvarchar(max),
adresse_plz nvarchar(max),
adresse_ort nvarchar(max),
emailadresse_email nvarchar(max),
telefonnummer nvarchar(max),
geburtsdatum datetime2,
kundenstatus nvarchar(50),
angelegtVon_id int foreign key references rezeptionisten(id))

create table kurse(
id int primary key identity(1,1),
titel nvarchar(max),
beschreibung nvarchar(max),
maximaleTeilnehmeranzahl int,
kursstatus nvarchar(max),
veranstaltungszeit_startzeitpunkt datetime2,
veranstaltungszeit_endzeitpunkt datetime2,
angelegtVon_id int foreign key references rezeptionisten(id),
kursleiter_id int foreign key references trainer(id))

create table kunden_kurse(
kundennummer int foreign key references kunden(kundennummer),
kurs_id int  foreign key references kurse(id))

create table rechnungen(
rechnungsnummer int primary key identity(1,1),
abrechnungszeitraum_monat int,
abrechnungszeitraum_jahr int,
bezahlt bit,
kunde_id int foreign key references kunden(kundennummer))

create table rechnungspositionen(
rp_id int primary key identity(1,1),
kosten decimal(5,2),
kurs_id int foreign key references kurse(id),
rechnung_id int foreign key references rechnungen(rechnungsnummer))