package pl.stubs

class JokesResponses {

    static String emptyJokeHtml() {
        """
        <html>
            <head></head>
            <body><h1>Hello World!</h1></body>
        <html>
        """
    }

    static String randomJokeHtml() {
        """
        <html>
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
            </head>
            <body>
                <script type="text/javascript" src="http://piszsuchary.pl/js/social.js"></script>
                <div id="top">
                    <div class="szerokosc">Witaj gościu! <a href="http://piszsuchary.pl/logowanie">Zaloguj się</a></div>
                </div>
                <div id="menu">
                    <div class="szerokosc">
                        <div id="menu-left">
                            <!--<a href="http://piszsuchary.pl/dodaj">Dodaj Suchara</a> | -->
                        </div>
                    </div>
                </div>  
                <div class="szerokosc2" id="trescGlownaBox">
                    <div id="trescGlowna">
                        <div class="cytat">
                            <div class="kot_na_suchara" style="height:130px;">
                                <a href="http://piszsuchary.pl/suchar/2907/jak-nazywa-sie-osma-zona-kucha">
                                <img src="http://piszsuchary.pl/upload/c0551eebe6426d0ee24032e446269046_by_suchary.jpg" 
                                    alt="alternative text"></a>
                            </div>
                            <div class="tekst">
                                <span class="tekst-click" id="odkryj-2907" style="display: inline;">Pokaż sam tekst do skopiowania</span>
                                <pre class="tekst-pokaz" id="ukryty-2907">Knock! Knock! Who’s there? Scold. Scold who? Scold outside, let me in! | PiszSuchary.pl</pre>
                            </div>
                        </div>
                        <div class="cytat">
                            <div class="kot_na_suchara" style="height:130px;">
                                <a href="http://piszsuchary.pl/suchar/8363/jaka-jest-ulubiona-slodycz-czl"><img src="http://piszsuchary.pl/upload/025cb677c6972c5e22256c38adc41607_by_5uch4r3k.jpg" alt="Jaka jest ulubiona słodycz człowieka, który lubi sam siebie? - LubiSię."></a>
                            </div>
            
                            <div class="tekst">
                                <span class="tekst-click" id="odkryj-8363" style="display: inline;">Pokaż sam tekst do skopiowania</span>
                                <pre class="tekst-pokaz" id="ukryty-8363">Jaka jest ulubiona słodycz człowieka, który lubi sam siebie? - LubiSię. | PiszSuchary.pl</pre>
                            </div>
                        </div>
                        <div id="pager"><a class="pager-standard" href="http://piszsuchary.pl/losuj">LOSUJ PONOWNIE</a></div>
                    </div>
                    <div style="clear:both;"></div>
                </div>
                <div id="stopka">
                    <div class="szerokosc">
                        <div class="stopka-blok">
                            <h3>Statystyki</h3>
                        </div>
                    </div>
                </div>
            </body>
        </html>
        """
    }
}
