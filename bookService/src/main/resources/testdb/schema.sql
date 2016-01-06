drop table Book if exists;

create table Book (
				ID bigint identity primary key, 
                TITLE varchar(50) not null, 	       
				AUTHOR varchar(50) not null, 
				DESCRIPTION varchar(500) not null, 
				ISBN   varchar(50));
                        
