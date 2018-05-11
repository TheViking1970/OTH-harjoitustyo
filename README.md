# OTM-harjoitustyö

## Dokumentaatio
- [Käyttöohje](https://github.com/TheViking1970/oth-harjoitustyo/blob/master/dokumentointi/kayttoohje.md)
- [Vaatimusmäärittely](https://github.com/TheViking1970/oth-harjoitustyo/blob/master/dokumentointi/dokumentaatio.md)
- [Arkkitehtuurikuvaus](https://github.com/TheViking1970/oth-harjoitustyo/blob/master/dokumentointi/arkkitehtuuri.md)
- [Testauskuvaus](https://github.com/TheViking1970/oth-harjoitustyo/blob/master/dokumentointi/testaus.md)
- [Työaikakirjanpito](https://github.com/TheViking1970/oth-harjoitustyo/blob/master/dokumentointi/tyoaikakirjanpito.md)

## Release
- [viikko7](https://github.com/TheViking1970/OTH-harjoitustyo/releases/download/viikko7/missilecommand-1.0.jar) 
- [viikko6](https://github.com/TheViking1970/OTH-harjoitustyo/releases/download/viikko6/missilecommand2018_v_0_2.jar) 
- [viikko5](https://github.com/TheViking1970/OTH-harjoitustyo/releases/download/viikko5/missilecommand2018_v0_1.jar) 

## Komentorivitoiminnot
### Testaus
Testit suoritetaan komennolla

**mvn test**

Testikattavuusraportti luodaan komennolla

**mvn jacoco:report**

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto target/site/jacoco/index.html

### Checkstyle
Tiedoston checkstyle.xml määrittelemät tarkistukset suoritetaan komennolla

**mvn jxr:jxr checkstyle:checkstyle**

Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedosto target/site/checkstyle.html

### JavaDoc
Ohjelman koodista saa tehtyä JavaDocit seuraavasti:

**mvn javadoc:javadoc**

Luotu JavaDoc avataan selaimessa tiedostolla target/site/apidocs/index.html


