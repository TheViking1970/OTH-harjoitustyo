# Arkitehtuuri

## Tallennus tietokantaan
- Tietokantaan tallennettava High Score taulu ei ole vielä luotu. Tätä varten luodaan oma pakkaus "database" johon tulee kaikki tietokantaan liittyvät luokat.

## Luokat
- Pelilogiikka sijaitsee Game-luokan ainoassa oliossa
- Game-olio luo 1 tai 3 Turret-oliota ja 6 City-oliota

## Luokka/pakkauskaavio
![alt text](https://github.com/TheViking1970/oth-harjoitustyo/blob/master/dokumentointi/gfx/luokkakaavio-2.jpg)

## Sekvenssikaavio
![alt text](https://github.com/TheViking1970/oth-harjoitustyo/blob/master/dokumentointi/gfx/sekvenssikaavio.jpg)
Tulevia sekvenssikaavioita:
- Turret-oliot voivat ampua ohjuksia, Missile-olioita
- Game-olion AI luo ohjuksia, EnemyMissile-olioita, jotka liikkuvat City- ja Turret-olioita kohtaan
- Kun Missile-olio saavuttaa määränpäänsä se räjähtää ja poistetaan, tilalle luodaan Explosion-olio
- Jos Explosion-olio koskettaa EnemyMissile-oliota, niin EnemyMissile-olio poistetaa ja luodaan uusi Explode-olio
- Jos EnemyMissile-olio koskettaa City-oliota, niin City-olio merkitään ei aktiiviseksi (kuollut) ja luodaan Explosion-olio
- Jos EnemyMissile- olio koskettaa Turret-oliota, niin Turret-olio merktään ei aktiiviseksi (kuollut) ja luodaan Explosion-olio

