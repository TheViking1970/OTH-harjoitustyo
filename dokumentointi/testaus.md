# Testausdokumentti
Ohjelman toimintaa on testattu sekä automaattisilla JUnit-testeillä, että manuaalisella tastauksella.

## Yksikkö- ja integraatiotestaus
Ykkiskkötestaukset ovat tehty testaamaan net.vikke.missilecommand:ssa olevia paketteja. Koska sovellus on intensiivinen grafiikan suhteen näitä osia ei pystytä järkevästi testaamaan, joten testauksissa testataan lähinnä sovelluslogiikkaa.
Suurin osa pelin logiikasta on game-pakkauksessa ja sen testaamisesta huolehtii CityTest, EnemyMissileTest, ExplosionTest, GameTest, MissileTest a TurretTest-luokat. Myös muita osia, paitsi graafista API:a ja main-pakkausta testaan myös, mutta ovat paljo pienempiä. Syy main jättämisestä testauksen ulkopuolelle on JavaFX-alustus joka ei toimi kunnolla testauksissa.

### Testikattavuus
Rivikattavuus on: 76%, haarautumakatavuus on: 48%. Syy haarautumakattavuuden vähyyteen on pelin sovelluslogiikassa, jota on erittäin työlästä testata kaikkia eri tiloja. (Pelissä on niin monta if-lausetta jotta saa haastavemman pelin, yksinkertaisemmalla logiikalla olisi helpompi tietenkin myös testata... mutta silloin olisikin tylsä peli).
![alt text](https://github.com/TheViking1970/oth-harjoitustyo/blob/master/dokumentointi/gfx/testikattavuus.png)

## Järjestelmätestaus
Järjestelmätestaus on suoritettu manuaalisesti pelaamalla peliä ja kokeilemalla "rikkoa" eri toimintoja.

## Asennus ja konfigurointi
Sovellus on testattu käyttöohjeen kuvaamalla tavalla Wndows ympäristössä, siten että sovelluksen käynnistyshakemistossa on ollut käyttäohjeen mukainen MC-highscores.db-tiedosto.

## Toiminnallisuus
Kaikki määrittelydokumentissa listaamat toiminnallisuudet on käyty läpi ja toimivat niinkuin suunniteltu.

## Sovellukseen jääneet laatuongelmat
Sovellus ei anna järkeviä virheilmoituksia mikäli tarvittava MC-highscore.db-tiedosto ei löydy vaadituss database-kansiossa.