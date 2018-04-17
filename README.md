# OTM-harjoitusty�

## Dokumentaatio
- [K�ytt�ohje](https://github.com/TheViking1970/oth-harjoitustyo/blob/master/dokumentointi/kayttoohje.md)
- [Vaatimusm��rittely](https://github.com/TheViking1970/oth-harjoitustyo/blob/master/dokumentointi/dokumentaatio.md)
- [Arkkitehtuurikuvaus](https://github.com/TheViking1970/oth-harjoitustyo/blob/master/dokumentointi/arkkitehtuuri.md)
- [Ty�aikakirjanpito](https://github.com/TheViking1970/oth-harjoitustyo/blob/master/dokumentointi/tyoaikakirjanpito.md)

## Komentorivitoiminnot
### Testaus
Testit suoritetaan komennolla

**mvn test**

Testikattavuusraportti luodaan komennolla

**mvn jacoco:report**

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto target/site/jacoco/index.html

### Checkstyle
Tiedostoon checkstyle.xml m��rittelem�t tarkistukset suoritetaan komennolla

**mvn jxr:jxr checkstyle:checkstyle**

Mahdolliset virheilmoitukset selvi�v�t avaamalla selaimella tiedosto target/site/checkstyle.html




