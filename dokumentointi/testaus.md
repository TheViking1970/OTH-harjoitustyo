# Testausdokumentti
Ohjelman toimintaa on testattu sek� automaattisilla JUnit-testeill�, ett� manuaalisella tastauksella.

## Yksikk�- ja integraatiotestaus
Ykkiskk�testaukset ovat tehty testaamaan net.vikke.missilecommand:ssa olevia paketteja. Koska sovellus on intensiivinen grafiikan suhteen n�it� osia ei pystyt� j�rkev�sti testaamaan, joten testauksissa testataan l�hinn� sovelluslogiikkaa.
Suurin osa pelin logiikasta on game-pakkauksessa ja sen testaamisesta huolehtii CityTest, EnemyMissileTest, ExplosionTest, GameTest, MissileTest a TurretTest-luokat. My�s muita osia, paitsi graafista API:a ja main-pakkausta testaan my�s, mutta ovat paljo pienempi�. Syy main j�tt�misest� testauksen ulkopuolelle on JavaFX-alustus joka ei toimi kunnolla testauksissa.

### Testikattavuus
Rivikattavuus on: 76%, haarautumakatavuus on: 48%. Syy haarautumakattavuuden v�hyyteen on pelin sovelluslogiikassa, jota on eritt�in ty�l�st� testata kaikkia eri tiloja. (Peliss� on niin monta if-lausetta jotta saa haastavemman pelin, yksinkertaisemmalla logiikalla olisi helpompi tietenkin my�s testata... mutta silloin olisikin tyls� peli).
![alt text](https://github.com/TheViking1970/oth-harjoitustyo/blob/master/dokumentointi/gfx/testikattavuus.png)

## J�rjestelm�testaus
J�rjestelm�testaus on suoritettu manuaalisesti pelaamalla peli� ja kokeilemalla "rikkoa" eri toimintoja.

## Asennus ja konfigurointi
Sovellus on testattu k�ytt�ohjeen kuvaamalla tavalla Wndows ymp�rist�ss�, siten ett� sovelluksen k�ynnistyshakemistossa on ollut k�ytt�ohjeen mukainen MC-highscores.db-tiedosto.

## Toiminnallisuus
Kaikki m��rittelydokumentissa listaamat toiminnallisuudet on k�yty l�pi ja toimivat niinkuin suunniteltu.

## Sovellukseen j��neet laatuongelmat
Sovellus ei anna j�rkevi� virheilmoituksia mik�li tarvittava MC-highscore.db-tiedosto ei l�ydy vaadituss database-kansiossa.