# dough-machine
postgresql-15.2-2-windows-x64.exe <br>
postgresql-42.6.0.jar

CREATE TABLE INGREDIENTS( </br>
&emsp; PRODUCT &emsp; VARCHAR(20) &ensp; NOT NULL, </br>
&emsp; AMOUNT &emsp;&nbsp; SMALLINT &emsp;&emsp; NOT NULL </br>
);

INSERT INTO INGREDIENTS (PRODUCT, AMOUNT) </br>
VALUES ('FLOUR', 5000); <br>

INSERT INTO INGREDIENTS (PRODUCT, AMOUNT) </br>
VALUES ('MILK', 5000); <br>

INSERT INTO INGREDIENTS (PRODUCT, AMOUNT) </br>
VALUES ('YEAST', 1000); <br>

INSERT INTO INGREDIENTS (PRODUCT, AMOUNT) </br>
VALUES ('EGGS', 200);
