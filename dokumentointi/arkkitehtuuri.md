# Arkitehtuuri

## Tallennus tietokantaan
- Tietokantatallennus käyttää Database-luokkaa hyväkseen

## Luokat
- Peli alkaa Main-luokasta
- Pelilogiikka sijaitsee Game-luokan ainoassa oliossa
- Game-olio luo 1 tai 3 Turret-oliota ja 6 City-oliota
- Pelin edetessä Game-olio luo sekä Missile- että EnemyMissile-olioita
- Helper-luokka sisältää muutaman apufunktion
- Graphics-luokka on pelin graafinen API, joka huolehtii piirtämisestä JavaFX:llä

## Luokka/pakkauskaavio
![alt text](https://github.com/TheViking1970/oth-harjoitustyo/blob/master/dokumentointi/gfx/luokkakaavio-3.png)

## Sekvenssikaavio
![alt text](https://github.com/TheViking1970/oth-harjoitustyo/blob/master/dokumentointi/gfx/sekvenssikaavio.jpg)
Tulevia sekvenssikaavioita:
- Turret-oliot voivat ampua ohjuksia, Missile-olioita
- Game-olion AI luo ohjuksia, EnemyMissile-olioita, jotka liikkuvat City- ja Turret-olioita kohtaan
- Kun Missile-olio saavuttaa määränpäänsä se räjähtää ja poistetaan, tilalle luodaan Explosion-olio
- Jos Explosion-olio koskettaa EnemyMissile-oliota, niin EnemyMissile-olio poistetaa ja luodaan uusi Explode-olio
- Jos EnemyMissile-olio koskettaa City-oliota, niin City-olio merkitään ei aktiiviseksi (kuollut) ja luodaan Explosion-olio
- Jos EnemyMissile- olio koskettaa Turret-oliota, niin Turret-olio merktään ei aktiiviseksi (kuollut) ja luodaan Explosion-olio

