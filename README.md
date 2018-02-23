Konkurentné programovanie- 2. projekt 1. zadanie (od kódu k WSDL)
------------------------------------------------------------------

Projekt pozostáva z dvoch častí: Serverovskej (Kopr-od-kodu-k-WSDL) a klientskej (OdKoduKWSDLKlient).

Databázu som robil v Mysql. Jednotlivé príkazy, ktoré som použil pri vytváraní databázy sa nachádzajú v " databaza.sql".

Serverovská časť obsahuje triedu s main metódov na spustenie servera, entity Predmet, Ucastnik, PrezencnaListina; AjsService; MysqlPredmetDao, MysqlUcastnikDao, MysqlPrezencnaListinaDao, ktoré sú implementáciou tried PredmetDao, UcastnikDao, PrezencnaListinaDao; ObjectFactory, ktorý obsahuje údaje k databáze.

Klientsku časť som si vygeneroval pomocou príkazu wsimport a to tak, že som spustil v adresári, v ktorom som chcel, aby bola klientska časť príkaz: wsimport -d bin -s src http://localhost:8888/ajs?wsdl. Do klientskej časti som si ešte doplnil triedu s main metódov, z ktorej som si spúšťal metódy, ktoré som naprogramoval v serverovskej časti.

Junit testy som spravil na metódy z MysqlPredmetDao, MysqlUcastnikDao a MysqlPrezencnaListinaDao ktoré sa nachádzajú v package "AJSServer " pod názvom MysqlPredmetDaoTest, MysqlUcastnikDaoTest a MysqlPrezencnaListinaDaoTest.

Serverovskú, aj klientsku časť som robil v Eclipse a využíval som Javu: jre1.8.0_60 ( jdk1.8.0_60 )
