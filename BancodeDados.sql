create database trimble;

create table MOVIE (
	ID_MOVIE serial primary key,
	TITLE_MOVIE varchar(60),
	RELEASE_DATE_MOVIE timestamp,
	SYNOPSIS_MOVIE text,
	USER_RATING_MOVIE int
);