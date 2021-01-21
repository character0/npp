use bootcamp;
create table review (
	summary varchar,
	time bigint,
	user_id varchar,
	score double,
	helpful varchar,
	profile_name varchar,
	product_id varchar,
	text varchar,
	PRIMARY KEY ((user_id), time )
);

create table ratings (
	user_id varchar,
	score double,
	profile_name varchar,
	product_id varchar,
	PRIMARY KEY ((user_id), profile_name, product_id )
);



curl http://localhost:8983/solr/resource/bootcamp.review/solrconfig.xml --data-binary @solrconfig.lab.xml -H 'Content-type:text/xml; charset=utf-8'
curl http://localhost:8983/solr/resource/bootcamp.review/schema.xml --data-binary @finefoods.xml -H 'Content-type:text/xml; charset=utf-8'
curl "http://localhost:8983/solr/admin/cores?action=CREATE&name=bootcamp.review"
