package be.kuleuven.howlongtobeat.cartridges

import junit.framework.TestCase.assertEquals
import org.junit.Test
import junit.framework.TestCase.assertNull as assertNull1

class DuckDuckGoResultParserTest {

    @Test
    fun parse_marioGolfCodeSample() {
        val html = """
            <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

            <!--[if IE 6]><html class="ie6" xmlns="http://www.w3.org/1999/xhtml"><![endif]-->
            <!--[if IE 7]><html class="lt-ie8 lt-ie9" xmlns="http://www.w3.org/1999/xhtml"><![endif]-->
            <!--[if IE 8]><html class="lt-ie9" xmlns="http://www.w3.org/1999/xhtml"><![endif]-->
            <!--[if gt IE 8]><!--><html xmlns="http://www.w3.org/1999/xhtml"><!--<![endif]-->
            <head>
              <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
              <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=3.0, user-scalable=1" />
              <meta name="referrer" content="origin" />
              <meta name="HandheldFriendly" content="true" />
              <meta name="robots" content="noindex, nofollow" />
              <title>cgb-awxp-eur-1 at DuckDuckGo</title>
              <link title="DuckDuckGo (HTML)" type="application/opensearchdescription+xml" rel="search" href="//duckduckgo.com/opensearch_html_v2.xml" />
              <link href="//duckduckgo.com/favicon.ico" rel="shortcut icon" />
              <link rel="icon" href="//duckduckgo.com/favicon.ico" type="image/x-icon" />
              <link rel="apple-touch-icon" href="//duckduckgo.com/assets/logo_icon128.v101.png"/>
              <link rel="image_src" href="//duckduckgo.com/assets/logo_homepage.normal.v101.png"/>
              <link type="text/css" media="handheld, all" href="//duckduckgo.com/h1997.css" rel="stylesheet" />
            </head>

            <body class="body--html">
              <a name="top" id="top"></a>

              <form action="/html/" method="post">
                <input type="text" name="state_hidden" id="state_hidden" />
              </form>

              <div>
                <div class="site-wrapper-border"></div>

                <div id="header" class="header cw header--html">
                    <a title="DuckDuckGo" href="/html/" class="header__logo-wrap"></a>


                <form name="x" class="header__form" action="/html/" method="post">

                  <div class="search search--header">
                      <input name="q" autocomplete="off" class="search__input" id="search_form_input_homepage" type="text" value="cgb-awxp-eur-1" />
                      <input name="b" id="search_button_homepage" class="search__button search__button--html" value="" title="Search" alt="Search" type="submit" />
                  </div>


                
                
                
                

                <div class="frm__select">
                  <select name="kl">
                  
                    <option value="" >All Regions</option>
                  
                    <option value="ar-es" >Argentina</option>
                  
                    <option value="au-en" >Australia</option>
                  
                    <option value="at-de" >Austria</option>
                  
                    <option value="be-fr" >Belgium (fr)</option>
                  
                    <option value="be-nl" >Belgium (nl)</option>
                  
                    <option value="br-pt" >Brazil</option>
                  
                    <option value="bg-bg" >Bulgaria</option>
                  
                    <option value="ca-en" >Canada (en)</option>
                  
                    <option value="ca-fr" >Canada (fr)</option>
                  
                    <option value="ct-ca" >Catalonia</option>
                  
                    <option value="cl-es" >Chile</option>
                  
                    <option value="cn-zh" >China</option>
                  
                    <option value="co-es" >Colombia</option>
                  
                    <option value="hr-hr" >Croatia</option>
                  
                    <option value="cz-cs" >Czech Republic</option>
                  
                    <option value="dk-da" >Denmark</option>
                  
                    <option value="ee-et" >Estonia</option>
                  
                    <option value="fi-fi" >Finland</option>
                  
                    <option value="fr-fr" >France</option>
                  
                    <option value="de-de" >Germany</option>
                  
                    <option value="gr-el" >Greece</option>
                  
                    <option value="hk-tzh" >Hong Kong</option>
                  
                    <option value="hu-hu" >Hungary</option>
                  
                    <option value="in-en" >India (en)</option>
                  
                    <option value="id-en" >Indonesia (en)</option>
                  
                    <option value="ie-en" >Ireland</option>
                  
                    <option value="il-en" >Israel (en)</option>
                  
                    <option value="it-it" >Italy</option>
                  
                    <option value="jp-jp" >Japan</option>
                  
                    <option value="kr-kr" >Korea</option>
                  
                    <option value="lv-lv" >Latvia</option>
                  
                    <option value="lt-lt" >Lithuania</option>
                  
                    <option value="my-en" >Malaysia (en)</option>
                  
                    <option value="mx-es" >Mexico</option>
                  
                    <option value="nl-nl" >Netherlands</option>
                  
                    <option value="nz-en" >New Zealand</option>
                  
                    <option value="no-no" >Norway</option>
                  
                    <option value="pk-en" >Pakistan (en)</option>
                  
                    <option value="pe-es" >Peru</option>
                  
                    <option value="ph-en" >Philippines (en)</option>
                  
                    <option value="pl-pl" >Poland</option>
                  
                    <option value="pt-pt" >Portugal</option>
                  
                    <option value="ro-ro" >Romania</option>
                  
                    <option value="ru-ru" >Russia</option>
                  
                    <option value="xa-ar" >Saudi Arabia</option>
                  
                    <option value="sg-en" >Singapore</option>
                  
                    <option value="sk-sk" >Slovakia</option>
                  
                    <option value="sl-sl" >Slovenia</option>
                  
                    <option value="za-en" >South Africa</option>
                  
                    <option value="es-ca" >Spain (ca)</option>
                  
                    <option value="es-es" >Spain (es)</option>
                  
                    <option value="se-sv" >Sweden</option>
                  
                    <option value="ch-de" >Switzerland (de)</option>
                  
                    <option value="ch-fr" >Switzerland (fr)</option>
                  
                    <option value="tw-tzh" >Taiwan</option>
                  
                    <option value="th-en" >Thailand (en)</option>
                  
                    <option value="tr-tr" >Turkey</option>
                  
                    <option value="us-en" >US (English)</option>
                  
                    <option value="us-es" >US (Spanish)</option>
                  
                    <option value="ua-uk" >Ukraine</option>
                  
                    <option value="uk-en" >United Kingdom</option>
                  
                    <option value="vn-en" >Vietnam (en)</option>
                  
                  </select>
                </div>

                <div class="frm__select frm__select--last">
                  <select class="" name="df">
                  
                    <option value="" selected>Any Time</option>
                  
                    <option value="d" >Past Day</option>
                  
                    <option value="w" >Past Week</option>
                  
                    <option value="m" >Past Month</option>
                  
                    <option value="y" >Past Year</option>
                  
                  </select>
                </div>

                </form>

                </div>





            <!-- Web results are present -->

              <div>
              <div class="serp__results">
              <div id="links" class="results">

                  



              


                        <div class="result results_links results_links_deep web-result ">


                      <div class="links_main links_deep result__body"> <!-- This is the visible part -->

                      <h2 class="result__title">
                      
                        <a rel="nofollow" class="result__a" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fvgdb.uk%2Fmario%2Dgolf%2Fgame%2F48484&amp;rut=b6b8b1ab81b51d84905332762ecdb6db58a60d37b266e4b6fdb2cfd7005feea0">Get information and compare prices of Mario Golf for Game Boy | VGDb</a>
                      
                      </h2>

                  

                        <div class="result__extras">
                            <div class="result__extras__url">
                              <span class="result__icon">
                                
                                  <a rel="nofollow" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fvgdb.uk%2Fmario%2Dgolf%2Fgame%2F48484&amp;rut=b6b8b1ab81b51d84905332762ecdb6db58a60d37b266e4b6fdb2cfd7005feea0">
                                    <img class="result__icon__img" width="16" height="16" alt=""
                                      src="//external-content.duckduckgo.com/ip3/vgdb.uk.ico" name="i15" />
                                  </a>
                              
                              </span>

                              <a class="result__url" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fvgdb.uk%2Fmario%2Dgolf%2Fgame%2F48484&amp;rut=b6b8b1ab81b51d84905332762ecdb6db58a60d37b266e4b6fdb2cfd7005feea0">
                              vgdb.uk/mario-golf/game/48484
                              </a>

                              

                            </div>
                        </div>

                        
                              <a class="result__snippet" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fvgdb.uk%2Fmario%2Dgolf%2Fgame%2F48484&amp;rut=b6b8b1ab81b51d84905332762ecdb6db58a60d37b266e4b6fdb2cfd7005feea0">045496730963. MPN. <b>CGB-AWXP-EUR-1</b>. Release Date.</a>
                        

                        <div class="clear"></div>
                      </div>

                    </div>

              



              


                        <div class="result results_links results_links_deep web-result ">


                      <div class="links_main links_deep result__body"> <!-- This is the visible part -->

                      <h2 class="result__title">
                      
                        <a rel="nofollow" class="result__a" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fwww.ebay.de%2Fitm%2FMario%2DGolf%2DNintendo%2DGame%2DBoy%2Dcolor%2DAdvance%2DGBC%2DGBA%2DCGB%2DAWXP%2DEUR%2F264853273128%3Fepid%3D167015543%26hash%3Ditem3daa7c3a28%3Ag%3A0U0AAOSwC%2D5fUT36&amp;rut=5d364b7b299d63d00736bfd7fd0835ac27551cd84b2424019d36a682ad982aea">Mario Golf - Nintendo Game Boy color / Advance GBC GBA... | eBay</a>
                      
                      </h2>

                  

                        <div class="result__extras">
                            <div class="result__extras__url">
                              <span class="result__icon">
                                
                                  <a rel="nofollow" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fwww.ebay.de%2Fitm%2FMario%2DGolf%2DNintendo%2DGame%2DBoy%2Dcolor%2DAdvance%2DGBC%2DGBA%2DCGB%2DAWXP%2DEUR%2F264853273128%3Fepid%3D167015543%26hash%3Ditem3daa7c3a28%3Ag%3A0U0AAOSwC%2D5fUT36&amp;rut=5d364b7b299d63d00736bfd7fd0835ac27551cd84b2424019d36a682ad982aea">
                                    <img class="result__icon__img" width="16" height="16" alt=""
                                      src="//external-content.duckduckgo.com/ip3/www.ebay.de.ico" name="i15" />
                                  </a>
                              
                              </span>

                              <a class="result__url" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fwww.ebay.de%2Fitm%2FMario%2DGolf%2DNintendo%2DGame%2DBoy%2Dcolor%2DAdvance%2DGBC%2DGBA%2DCGB%2DAWXP%2DEUR%2F264853273128%3Fepid%3D167015543%26hash%3Ditem3daa7c3a28%3Ag%3A0U0AAOSwC%2D5fUT36&amp;rut=5d364b7b299d63d00736bfd7fd0835ac27551cd84b2424019d36a682ad982aea">
                              www.ebay.de/itm/Mario-Golf-Nintendo-Game-Boy-color-Advance-GBC-GBA-CGB-AWXP-EUR/264853273128?epid=167015543&amp;hash=item3daa7c3a28:g:0U0AAOSwC-5fUT36
                              </a>

                              

                            </div>
                        </div>

                        
                              <a class="result__snippet" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fwww.ebay.de%2Fitm%2FMario%2DGolf%2DNintendo%2DGame%2DBoy%2Dcolor%2DAdvance%2DGBC%2DGBA%2DCGB%2DAWXP%2DEUR%2F264853273128%3Fepid%3D167015543%26hash%3Ditem3daa7c3a28%3Ag%3A0U0AAOSwC%2D5fUT36&amp;rut=5d364b7b299d63d00736bfd7fd0835ac27551cd84b2424019d36a682ad982aea">Super mario bros deluxe game boy color game nintendo original gbc <b>EUR-1</b> version. Golf. Herstellernummer: <b>CGB-AWXP-EUR</b>. Herausgeber: Nintendo.</a>
                        

                        <div class="clear"></div>
                      </div>

                    </div>

              



              


                        <div class="result results_links results_links_deep web-result ">


                      <div class="links_main links_deep result__body"> <!-- This is the visible part -->

                      <h2 class="result__title">
                      
                        <a rel="nofollow" class="result__a" href="//duckduckgo.com/l/?uddg=http%3A%2F%2Fpassat.neostrada.pl%2Fzabikt%2FGB.txt&amp;rut=df8a2bed4111ed3dc8458c505782b07b995d248bdb2adf11a264f0c8e216b6ee">passat.neostrada.pl/zabikt/GB.txt</a>
                      
                      </h2>

                  

                        <div class="result__extras">
                            <div class="result__extras__url">
                              <span class="result__icon">
                                
                                  <a rel="nofollow" href="//duckduckgo.com/l/?uddg=http%3A%2F%2Fpassat.neostrada.pl%2Fzabikt%2FGB.txt&amp;rut=df8a2bed4111ed3dc8458c505782b07b995d248bdb2adf11a264f0c8e216b6ee">
                                    <img class="result__icon__img" width="16" height="16" alt=""
                                      src="//external-content.duckduckgo.com/ip3/passat.neostrada.pl.ico" name="i15" />
                                  </a>
                              
                              </span>

                              <a class="result__url" href="//duckduckgo.com/l/?uddg=http%3A%2F%2Fpassat.neostrada.pl%2Fzabikt%2FGB.txt&amp;rut=df8a2bed4111ed3dc8458c505782b07b995d248bdb2adf11a264f0c8e216b6ee">
                              passat.neostrada.pl/zabikt/GB.txt
                              </a>

                              

                            </div>
                        </div>

                        
                              <a class="result__snippet" href="//duckduckgo.com/l/?uddg=http%3A%2F%2Fpassat.neostrada.pl%2Fzabikt%2FGB.txt&amp;rut=df8a2bed4111ed3dc8458c505782b07b995d248bdb2adf11a264f0c8e216b6ee">and Jerry <b>CGB</b>-AW8A-<b>EUR</b> DMG-A03-10 <b>CGB</b>-AW8A- 11&#x27;00 Nintendo Wario Land 3 <b>CGB-AWXP-EUR-1</b> DMG-A08-01 <b>CGB</b>-<b>AWXP</b>- 07&#x27;99 <b>CGB</b>-BBZD- 05&#x27;02 Banpresto DragonBall Z - Legendare Superkampfer <b>CGB</b>-BCKP-<b>EUR</b> DMG-A07-01 <b>CGB</b>-BCKP- 10&#x27;00 THQ Chicken Run...</a>
                        

                        <div class="clear"></div>
                      </div>

                    </div>

              



              


                        <div class="result results_links results_links_deep web-result ">


                      <div class="links_main links_deep result__body"> <!-- This is the visible part -->

                      <h2 class="result__title">
                      
                        <a rel="nofollow" class="result__a" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fwww.xe.com%2Fcurrencyconverter%2Fconvert%2F%3FAmount%3D1%26From%3DEUR%26To%3DXPF&amp;rut=786e13090f4b7968730f2b4700edf1855318e23a3e904b10640c077e450018b5">Convert 1 Euro to CFP Franc - EUR to XPF Exchange Rates | Xe</a>
                      
                      </h2>

                  

                        <div class="result__extras">
                            <div class="result__extras__url">
                              <span class="result__icon">
                                
                                  <a rel="nofollow" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fwww.xe.com%2Fcurrencyconverter%2Fconvert%2F%3FAmount%3D1%26From%3DEUR%26To%3DXPF&amp;rut=786e13090f4b7968730f2b4700edf1855318e23a3e904b10640c077e450018b5">
                                    <img class="result__icon__img" width="16" height="16" alt=""
                                      src="//external-content.duckduckgo.com/ip3/www.xe.com.ico" name="i15" />
                                  </a>
                              
                              </span>

                              <a class="result__url" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fwww.xe.com%2Fcurrencyconverter%2Fconvert%2F%3FAmount%3D1%26From%3DEUR%26To%3DXPF&amp;rut=786e13090f4b7968730f2b4700edf1855318e23a3e904b10640c077e450018b5">
                              www.xe.com/currencyconverter/convert/?Amount=1&amp;From=EUR&amp;To=XPF
                              </a>

                              

                            </div>
                        </div>

                        
                              <a class="result__snippet" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fwww.xe.com%2Fcurrencyconverter%2Fconvert%2F%3FAmount%3D1%26From%3DEUR%26To%3DXPF&amp;rut=786e13090f4b7968730f2b4700edf1855318e23a3e904b10640c077e450018b5">Get live mid-market exchange rates, historical rates and data &amp; currency charts for <b>EUR</b> to XPF with Xe&#x27;s free Currency Converter.</a>
                        

                        <div class="clear"></div>
                      </div>

                    </div>

              



              


                        <div class="result results_links results_links_deep web-result ">


                      <div class="links_main links_deep result__body"> <!-- This is the visible part -->

                      <h2 class="result__title">
                      
                        <a rel="nofollow" class="result__a" href="//duckduckgo.com/l/?uddg=https%3A%2F%2F1prime.ru%2FFinancial_market%2F20210818%2F834477683.html&amp;rut=98ca5523ff9959af22ddde1d49a41c29cec74571bf11f14960fb6e2753d47349">Евро сдувается: когда пора бежать в обменник - ПРАЙМ, 18.08.2021</a>
                      
                      </h2>

                  

                        <div class="result__extras">
                            <div class="result__extras__url">
                              <span class="result__icon">
                                
                                  <a rel="nofollow" href="//duckduckgo.com/l/?uddg=https%3A%2F%2F1prime.ru%2FFinancial_market%2F20210818%2F834477683.html&amp;rut=98ca5523ff9959af22ddde1d49a41c29cec74571bf11f14960fb6e2753d47349">
                                    <img class="result__icon__img" width="16" height="16" alt=""
                                      src="//external-content.duckduckgo.com/ip3/1prime.ru.ico" name="i15" />
                                  </a>
                              
                              </span>

                              <a class="result__url" href="//duckduckgo.com/l/?uddg=https%3A%2F%2F1prime.ru%2FFinancial_market%2F20210818%2F834477683.html&amp;rut=98ca5523ff9959af22ddde1d49a41c29cec74571bf11f14960fb6e2753d47349">
                              1prime.ru/Financial_market/20210818/834477683.html
                              </a>

                              

                            </div>
                        </div>

                        
                              <a class="result__snippet" href="//duckduckgo.com/l/?uddg=https%3A%2F%2F1prime.ru%2FFinancial_market%2F20210818%2F834477683.html&amp;rut=98ca5523ff9959af22ddde1d49a41c29cec74571bf11f14960fb6e2753d47349"><b>EUR</b>/RUB. <b>EUR</b>/CHF.</a>
                        

                        <div class="clear"></div>
                      </div>

                    </div>

              



              


                        <div class="result results_links results_links_deep web-result ">


                      <div class="links_main links_deep result__body"> <!-- This is the visible part -->

                      <h2 class="result__title">
                      
                        <a rel="nofollow" class="result__a" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fwww.cgb.fr%2Fespagne%2Dserie%2Deuro%2Dbrillant%2Duniversel%2D2002%2Dbu%2CEA_102_101_117_95_54_57_48_52_52_51%2Ca.html&amp;rut=4e2756edf796854d544428c15a6044ce2bba3dc48072b3b8ea2b6c465b3cd73d">ESPAGNE SÉRIE Euro BRILLANT UNIVERSEL 2002 Madrid...</a>
                      
                      </h2>

                  

                        <div class="result__extras">
                            <div class="result__extras__url">
                              <span class="result__icon">
                                
                                  <a rel="nofollow" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fwww.cgb.fr%2Fespagne%2Dserie%2Deuro%2Dbrillant%2Duniversel%2D2002%2Dbu%2CEA_102_101_117_95_54_57_48_52_52_51%2Ca.html&amp;rut=4e2756edf796854d544428c15a6044ce2bba3dc48072b3b8ea2b6c465b3cd73d">
                                    <img class="result__icon__img" width="16" height="16" alt=""
                                      src="//external-content.duckduckgo.com/ip3/www.cgb.fr.ico" name="i15" />
                                  </a>
                              
                              </span>

                              <a class="result__url" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fwww.cgb.fr%2Fespagne%2Dserie%2Deuro%2Dbrillant%2Duniversel%2D2002%2Dbu%2CEA_102_101_117_95_54_57_48_52_52_51%2Ca.html&amp;rut=4e2756edf796854d544428c15a6044ce2bba3dc48072b3b8ea2b6c465b3cd73d">
                              www.cgb.fr/espagne-serie-euro-brillant-universel-2002-bu,EA_102_101_117_95_54_57_48_52_52_51,a.html
                              </a>

                              

                            </div>
                        </div>

                        
                              <a class="result__snippet" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fwww.cgb.fr%2Fespagne%2Dserie%2Deuro%2Dbrillant%2Duniversel%2D2002%2Dbu%2CEA_102_101_117_95_54_57_48_52_52_51%2Ca.html&amp;rut=4e2756edf796854d544428c15a6044ce2bba3dc48072b3b8ea2b6c465b3cd73d"><b>cgb</b>.fr. La vente sera clôturée à l&#x27;heure indiquée sur la fiche descriptive, toute offre reçue après l&#x27;heure de clôture ne sera pas validée. <b>cgb</b>.fr utilise des cookies pour vous garantir une meilleure expérience utilisateur et réaliser des statistiques de visites.</a>
                        

                        <div class="clear"></div>
                      </div>

                    </div>

              



              


                        <div class="result results_links results_links_deep web-result ">


                      <div class="links_main links_deep result__body"> <!-- This is the visible part -->

                      <h2 class="result__title">
                      
                        <a rel="nofollow" class="result__a" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fwww.chamber%2Dinternational.com%2Fexporting%2Dchamber%2Dinternational%2Fdocumentation%2Dfor%2Dexport%2Dand%2Dimport%2Feur%2D1%2Dcertificates%2F&amp;rut=faece7b1a1828d2ac5bee57a7c3582afbbd2defc0f12b9cfdabcce29c7728229">EUR1 certificates</a>
                      
                      </h2>

                  

                        <div class="result__extras">
                            <div class="result__extras__url">
                              <span class="result__icon">
                                
                                  <a rel="nofollow" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fwww.chamber%2Dinternational.com%2Fexporting%2Dchamber%2Dinternational%2Fdocumentation%2Dfor%2Dexport%2Dand%2Dimport%2Feur%2D1%2Dcertificates%2F&amp;rut=faece7b1a1828d2ac5bee57a7c3582afbbd2defc0f12b9cfdabcce29c7728229">
                                    <img class="result__icon__img" width="16" height="16" alt=""
                                      src="//external-content.duckduckgo.com/ip3/www.chamber-international.com.ico" name="i15" />
                                  </a>
                              
                              </span>

                              <a class="result__url" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fwww.chamber%2Dinternational.com%2Fexporting%2Dchamber%2Dinternational%2Fdocumentation%2Dfor%2Dexport%2Dand%2Dimport%2Feur%2D1%2Dcertificates%2F&amp;rut=faece7b1a1828d2ac5bee57a7c3582afbbd2defc0f12b9cfdabcce29c7728229">
                              www.chamber-international.com/exporting-chamber-international/documentation-for-export-and-import/eur-1-certificates/
                              </a>

                              

                            </div>
                        </div>

                        
                              <a class="result__snippet" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fwww.chamber%2Dinternational.com%2Fexporting%2Dchamber%2Dinternational%2Fdocumentation%2Dfor%2Dexport%2Dand%2Dimport%2Feur%2D1%2Dcertificates%2F&amp;rut=faece7b1a1828d2ac5bee57a7c3582afbbd2defc0f12b9cfdabcce29c7728229"><b>EUR</b> <b>1</b> certificates are issued by Chamber International under authority from HM Customs and Excise. No matter where you are in the UK we have a dedicated service that can walk you through the <b>EUR1</b> process, to take away the confusion so you can get on with what matters to you.</a>
                        

                        <div class="clear"></div>
                      </div>

                    </div>

              



              


                        <div class="result results_links results_links_deep web-result ">


                      <div class="links_main links_deep result__body"> <!-- This is the visible part -->

                      <h2 class="result__title">
                      
                        <a rel="nofollow" class="result__a" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fen.wikipedia.org%2Fwiki%2FEUR.1_movement_certificate&amp;rut=24216430601fd3be32ae5f33be1579dd66e4e2a2b52d4e8edf1d1df49da81a76">EUR.1 movement certificate - Wikipedia</a>
                      
                      </h2>

                  

                        <div class="result__extras">
                            <div class="result__extras__url">
                              <span class="result__icon">
                                
                                  <a rel="nofollow" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fen.wikipedia.org%2Fwiki%2FEUR.1_movement_certificate&amp;rut=24216430601fd3be32ae5f33be1579dd66e4e2a2b52d4e8edf1d1df49da81a76">
                                    <img class="result__icon__img" width="16" height="16" alt=""
                                      src="//external-content.duckduckgo.com/ip3/en.wikipedia.org.ico" name="i15" />
                                  </a>
                              
                              </span>

                              <a class="result__url" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fen.wikipedia.org%2Fwiki%2FEUR.1_movement_certificate&amp;rut=24216430601fd3be32ae5f33be1579dd66e4e2a2b52d4e8edf1d1df49da81a76">
                              en.wikipedia.org/wiki/EUR.1_movement_certificate
                              </a>

                              

                            </div>
                        </div>

                        
                              <a class="result__snippet" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fen.wikipedia.org%2Fwiki%2FEUR.1_movement_certificate&amp;rut=24216430601fd3be32ae5f33be1579dd66e4e2a2b52d4e8edf1d1df49da81a76">The <b>EUR.1</b> movement certificate (also known as <b>EUR.1</b> certificate, or <b>EUR.1</b>) is a form used in international commodity traffic. The <b>EUR.1</b> is most importantly recognized as a certificate of origin in...</a>
                        

                        <div class="clear"></div>
                      </div>

                    </div>

              



              


                        <div class="result results_links results_links_deep web-result ">


                      <div class="links_main links_deep result__body"> <!-- This is the visible part -->

                      <h2 class="result__title">
                      
                        <a rel="nofollow" class="result__a" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fwww.tinko.ru%2Fcatalog%2Fproduct%2F245755%2F&amp;rut=6690f23522657070be76b9ad00b6ba1084ebed21008130b7a37e8c3fa25874ac">CGB-1U-19 (7113c): Медная шина заземления, 19&quot;</a>
                      
                      </h2>

                  

                        <div class="result__extras">
                            <div class="result__extras__url">
                              <span class="result__icon">
                                
                                  <a rel="nofollow" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fwww.tinko.ru%2Fcatalog%2Fproduct%2F245755%2F&amp;rut=6690f23522657070be76b9ad00b6ba1084ebed21008130b7a37e8c3fa25874ac">
                                    <img class="result__icon__img" width="16" height="16" alt=""
                                      src="//external-content.duckduckgo.com/ip3/www.tinko.ru.ico" name="i15" />
                                  </a>
                              
                              </span>

                              <a class="result__url" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fwww.tinko.ru%2Fcatalog%2Fproduct%2F245755%2F&amp;rut=6690f23522657070be76b9ad00b6ba1084ebed21008130b7a37e8c3fa25874ac">
                              www.tinko.ru/catalog/product/245755/
                              </a>

                              

                            </div>
                        </div>

                        
                              <a class="result__snippet" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fwww.tinko.ru%2Fcatalog%2Fproduct%2F245755%2F&amp;rut=6690f23522657070be76b9ad00b6ba1084ebed21008130b7a37e8c3fa25874ac"><b>CGB</b>-1U-19 (7113c). Избранное. Сравнить.</a>
                        

                        <div class="clear"></div>
                      </div>

                    </div>

              



              


                        <div class="result results_links results_links_deep web-result ">


                      <div class="links_main links_deep result__body"> <!-- This is the visible part -->

                      <h2 class="result__title">
                      
                        <a rel="nofollow" class="result__a" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fwww.justetf.com%2Fde%2Fetf%2Dprofile.html%3Fisin%3DIE00B3RBWM25&amp;rut=6ef75eb1798a163400e483634db05a9bb8eb3b05d1b4b99c19cd2b62b91313c8">Vanguard FTSE All-World UCITS ETF Distributing | A1JX52</a>
                      
                      </h2>

                  

                        <div class="result__extras">
                            <div class="result__extras__url">
                              <span class="result__icon">
                                
                                  <a rel="nofollow" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fwww.justetf.com%2Fde%2Fetf%2Dprofile.html%3Fisin%3DIE00B3RBWM25&amp;rut=6ef75eb1798a163400e483634db05a9bb8eb3b05d1b4b99c19cd2b62b91313c8">
                                    <img class="result__icon__img" width="16" height="16" alt=""
                                      src="//external-content.duckduckgo.com/ip3/www.justetf.com.ico" name="i15" />
                                  </a>
                              
                              </span>

                              <a class="result__url" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fwww.justetf.com%2Fde%2Fetf%2Dprofile.html%3Fisin%3DIE00B3RBWM25&amp;rut=6ef75eb1798a163400e483634db05a9bb8eb3b05d1b4b99c19cd2b62b91313c8">
                              www.justetf.com/de/etf-profile.html?isin=IE00B3RBWM25
                              </a>

                              

                            </div>
                        </div>

                        
                              <a class="result__snippet" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fwww.justetf.com%2Fde%2Fetf%2Dprofile.html%3Fisin%3DIE00B3RBWM25&amp;rut=6ef75eb1798a163400e483634db05a9bb8eb3b05d1b4b99c19cd2b62b91313c8">Ausschüttungen der letzten 12 Monate. <b>EUR</b> 1,42. <b>EUR</b> 1,42. Premium-Funktion. 2020.</a>
                        

                        <div class="clear"></div>
                      </div>

                    </div>

              



              


                        <div class="result results_links results_links_deep web-result ">


                      <div class="links_main links_deep result__body"> <!-- This is the visible part -->

                      <h2 class="result__title">
                      
                        <a rel="nofollow" class="result__a" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fwww.shiphub.co%2Feur%2D1%2Ddocument%2F&amp;rut=a7ea72c573daebf0f67f613309117c6ec11844bff0cd1b2afbb2aa37948f3186">EUR 1 document - Information about EUR 1 | ShipHub</a>
                      
                      </h2>

                  

                        <div class="result__extras">
                            <div class="result__extras__url">
                              <span class="result__icon">
                                
                                  <a rel="nofollow" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fwww.shiphub.co%2Feur%2D1%2Ddocument%2F&amp;rut=a7ea72c573daebf0f67f613309117c6ec11844bff0cd1b2afbb2aa37948f3186">
                                    <img class="result__icon__img" width="16" height="16" alt=""
                                      src="//external-content.duckduckgo.com/ip3/www.shiphub.co.ico" name="i15" />
                                  </a>
                              
                              </span>

                              <a class="result__url" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fwww.shiphub.co%2Feur%2D1%2Ddocument%2F&amp;rut=a7ea72c573daebf0f67f613309117c6ec11844bff0cd1b2afbb2aa37948f3186">
                              www.shiphub.co/eur-1-document/
                              </a>

                              

                            </div>
                        </div>

                        
                              <a class="result__snippet" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fwww.shiphub.co%2Feur%2D1%2Ddocument%2F&amp;rut=a7ea72c573daebf0f67f613309117c6ec11844bff0cd1b2afbb2aa37948f3186">The <b>EUR</b> <b>1</b> document also helps to set the rates of customs duties. This is related to the fact that goods from one region of the world in different countries The <b>EUR</b> <b>1</b> document is one of the most common certificates of origin on the international market. Well, it is issued when there is a preferential bilateral...</a>
                        

                        <div class="clear"></div>
                      </div>

                    </div>

              



              


                        <div class="result results_links results_links_deep web-result ">


                      <div class="links_main links_deep result__body"> <!-- This is the visible part -->

                      <h2 class="result__title">
                      
                        <a rel="nofollow" class="result__a" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Feur1.ch%2Feur1&amp;rut=9d288ecd9c501724cd409741f2f94aa7ae11ea635feb3abba833b9ebc779b1fa">EUR1 - eur1.ch Informationen zu EUR1 bestellen</a>
                      
                      </h2>

                  

                        <div class="result__extras">
                            <div class="result__extras__url">
                              <span class="result__icon">
                                
                                  <a rel="nofollow" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Feur1.ch%2Feur1&amp;rut=9d288ecd9c501724cd409741f2f94aa7ae11ea635feb3abba833b9ebc779b1fa">
                                    <img class="result__icon__img" width="16" height="16" alt=""
                                      src="//external-content.duckduckgo.com/ip3/eur1.ch.ico" name="i15" />
                                  </a>
                              
                              </span>

                              <a class="result__url" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Feur1.ch%2Feur1&amp;rut=9d288ecd9c501724cd409741f2f94aa7ae11ea635feb3abba833b9ebc779b1fa">
                              eur1.ch/eur1
                              </a>

                              

                            </div>
                        </div>

                        
                              <a class="result__snippet" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Feur1.ch%2Feur1&amp;rut=9d288ecd9c501724cd409741f2f94aa7ae11ea635feb3abba833b9ebc779b1fa"><b>EUR1</b> Formular online ausfüllen, <b>EUR1</b> Download, <b>EUR1</b> Fahrzeug, <b>EUR1</b> PKW, <b>EUR1</b> Export, Präferenz, <b>EUR1</b> Formular online, Ursprungserklärung Ein <b>EUR.1</b> ist ein Formular, welches im internationalen Handelswarenverkehr angewendet wird. Die Anwendung basiert auf diversen bi- und...</a>
                        

                        <div class="clear"></div>
                      </div>

                    </div>

              



              


                        <div class="result results_links results_links_deep web-result ">


                      <div class="links_main links_deep result__body"> <!-- This is the visible part -->

                      <h2 class="result__title">
                      
                        <a rel="nofollow" class="result__a" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fec.europa.eu%2Finfo%2Fstrategy%2Frecovery%2Dplan%2Deurope_en&amp;rut=4e8c965208bfb5af73f9a91301e671a1f719f15a7cb4e4f00761367ceadfcbe2">Recovery plan for Europe | European Commission</a>
                      
                      </h2>

                  

                        <div class="result__extras">
                            <div class="result__extras__url">
                              <span class="result__icon">
                                
                                  <a rel="nofollow" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fec.europa.eu%2Finfo%2Fstrategy%2Frecovery%2Dplan%2Deurope_en&amp;rut=4e8c965208bfb5af73f9a91301e671a1f719f15a7cb4e4f00761367ceadfcbe2">
                                    <img class="result__icon__img" width="16" height="16" alt=""
                                      src="//external-content.duckduckgo.com/ip3/ec.europa.eu.ico" name="i15" />
                                  </a>
                              
                              </span>

                              <a class="result__url" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fec.europa.eu%2Finfo%2Fstrategy%2Frecovery%2Dplan%2Deurope_en&amp;rut=4e8c965208bfb5af73f9a91301e671a1f719f15a7cb4e4f00761367ceadfcbe2">
                              ec.europa.eu/info/strategy/recovery-plan-europe_en
                              </a>

                              

                            </div>
                        </div>

                        
                              <a class="result__snippet" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fec.europa.eu%2Finfo%2Fstrategy%2Frecovery%2Dplan%2Deurope_en&amp;rut=4e8c965208bfb5af73f9a91301e671a1f719f15a7cb4e4f00761367ceadfcbe2"><b>EUR</b>, der skal bidrage til opbygningen af et grønnere, mere digitalt og mere modstandsdygtigt Europa). (Bugetul UE: Comisia Europeană salută acordul privind pachetul în valoare de 1,8 mii de miliarde <b>EUR</b> care va contribui la construirea unei Europe mai verzi, mai digitale și mai reziliente).</a>
                        

                        <div class="clear"></div>
                      </div>

                    </div>

              



              


                        <div class="result results_links results_links_deep web-result ">


                      <div class="links_main links_deep result__body"> <!-- This is the visible part -->

                      <h2 class="result__title">
                      
                        <a rel="nofollow" class="result__a" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%3D685J1hos%2Dzw&amp;rut=0de6e73357766837f1535544102aba3a21dfeb0ebe95183417e313e54b315bf1">EUR.1 - einfach erklärt (2020) - YouTube</a>
                      
                      </h2>

                  

                        <div class="result__extras">
                            <div class="result__extras__url">
                              <span class="result__icon">
                                
                                  <a rel="nofollow" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%3D685J1hos%2Dzw&amp;rut=0de6e73357766837f1535544102aba3a21dfeb0ebe95183417e313e54b315bf1">
                                    <img class="result__icon__img" width="16" height="16" alt=""
                                      src="//external-content.duckduckgo.com/ip3/www.youtube.com.ico" name="i15" />
                                  </a>
                              
                              </span>

                              <a class="result__url" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%3D685J1hos%2Dzw&amp;rut=0de6e73357766837f1535544102aba3a21dfeb0ebe95183417e313e54b315bf1">
                              www.youtube.com/watch?v=685J1hos-zw
                              </a>

                              

                            </div>
                        </div>

                        
                              <a class="result__snippet" href="//duckduckgo.com/l/?uddg=https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%3D685J1hos%2Dzw&amp;rut=0de6e73357766837f1535544102aba3a21dfeb0ebe95183417e313e54b315bf1">Die <b>EUR.1</b> ermöglicht Importeuren, Waren zu sogenannten Präferenzzollsätzen einzuführen. Sie gilt für fast alle Länder mit denen die EU Freihandelsabkommen abgeschlossen hat. Notwendig für eine rechtmäßigen Ausstellung ist...</a>
                        

                        <div class="clear"></div>
                      </div>

                    </div>

              




                    
                    
                            <div class="nav-link">
                    <form action="/html/" method="post">
                      <input type="submit" class='btn btn--alt' value="Next" />
                      <input type="hidden" name="q" value="cgb-awxp-eur-1" />
                      <input type="hidden" name="s" value="30" />
                      <input type="hidden" name="nextParams" value="" />
                      <input type="hidden" name="v" value="l" />
                      <input type="hidden" name="o" value="json" />
                      <input type="hidden" name="dc" value="15" />
                      <input type="hidden" name="api" value="d.js" />
                      <input type="hidden" name="vqd" value="3-170112077246644806044058132965997084141-24182048638364464238089820915205130627" />

                    
                    
                    
                      <input name="kl" value="wt-wt" type="hidden" />
                    
                    
                    
                    
                    </form>
                            </div>
                    



                    <div class=" feedback-btn">
                        <a rel="nofollow" href="//duckduckgo.com/feedback.html" target="_new">Feedback</a>
                    </div>
                    <div class="clear"></div>
              </div>
              </div> <!-- links wrapper //-->



                </div>
              </div>

                <div id="bottom_spacing2"></div>

                
                  <img src="//duckduckgo.com/t/sl_h"/>
                
            </body>
            </html>

        """.trimIndent()

        val cart = DuckDuckGoResultParser.parse(html, "cgb-awxp-eur-1")
        assertEquals("mario golf", cart?.title)
    }

    @Test
    fun parse_NoResultsFound_returnsNull() {
        val result = DuckDuckGoResultParser.parse("whooptie-doo", "some-code")
        assertNull1(result)
    }

    @Test
    fun parse_ResultWithStuffBetweenBrackets_removesThose() {
        val html = """
            <a rel="nofollow" class="result__a" href="url">My Little Pony (USA Release)!</a>
        """.trimIndent()

        val cart = DuckDuckGoResultParser.parse(html, "some-code")
        assertEquals("my little pony", cart?.title)
    }

    @Test
    fun parse_ResultWithCodeAgainInResult_removesRedundantCode() {
        val html = """
            <a rel="nofollow" class="result__a" href="url">My Little Pony (USA Release) DMG-PONY-007 ebay</a>
        """.trimIndent()

        val cart = DuckDuckGoResultParser.parse(html, "DMG-PONY-007")
        assertEquals("my little pony", cart?.title)
        assertEquals("DMG-PONY-007", cart?.code)
    }

}