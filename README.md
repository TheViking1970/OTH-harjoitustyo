# OTM-harjoitustyö

## Dokumentaatio
- [Käyttöohje](https://github.com/TheViking1970/oth-harjoitustyo/blob/master/dokumentointi/kayttoohje.md)
- [Vaatimusmäärittely](https://github.com/TheViking1970/oth-harjoitustyo/blob/master/dokumentointi/dokumentaatio.md)
- [Arkkitehtuurikuvaus](https://github.com/TheViking1970/oth-harjoitustyo/blob/master/dokumentointi/arkkitehtuuri.md)
- [Työaikakirjanpito](https://github.com/TheViking1970/oth-harjoitustyo/blob/master/dokumentointi/tyoaikakirjanpito.md)

## Komentorivitoiminnot
### Testaus
Testit suoritetaan komennolla

**mvn test**

Testikattavuusraportti luodaan komennolla

**mvn jacoco:report**

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto target/site/jacoco/index.html

### Checkstyle
Tiedostoon checkstyle.xml määrittelemät tarkistukset suoritetaan komennolla

**mvn jxr:jxr checkstyle:checkstyle**

Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedosto target/site/checkstyle.html




