package be.kuleuven.recyclerview.model.net

import org.junit.Assert.assertEquals
import org.junit.Test


class HowLongToBeatResultParserTest {
    @Test
    fun parseWithEmptyStringReturnsEmptyList() {
        val result = HowLongToBeatResultParser.parse("")
        assertEquals(0, result.size)
    }

    @Test
    fun parseWithRealResponseGrabsTitlesOfGamesAndConvertsToTodoItems() {
        val html = """
    			<div class="global_padding shadow_box back_blue center">
    				<h3> We Found 10 Games for "super mario land" </h3> 			</div>
    			
    			<ul>
    				<div class="clear"></div>
    	<li class="back_darkish" 			style="background-image:linear-gradient(rgb(31, 31, 31), rgba(31, 31, 31, 0.9)), url('/games/250px-Supermariolandboxart.jpg')"> 				<div class="search_list_image">
I/System.out: 					<a aria-label="Super Mario Land" title="Super Mario Land" href="game?id=9380">
    						<img alt="Box Art" src="/games/250px-Supermariolandboxart.jpg" />
    					</a> 
    				</div> 			<div class="search_list_details">					<h3 class="shadow_text">
    						<a class="text_green" title="Super Mario Land" href="game?id=9380">Super Mario Land</a>
    											</h3> 					<div class="search_list_details_block"> 								<div>
    									<div class="search_list_tidbit text_white shadow_text">Main Story</div>
    									<div class="search_list_tidbit center time_100">1 Hours </div>
    									<div class="search_list_tidbit text_white shadow_text">Main + Extra</div>
    									<div class="search_list_tidbit center time_100">1&#189; Hours </div>
    									<div class="search_list_tidbit text_white shadow_text">Completionist</div>
    									<div class="search_list_tidbit center time_100">1&#189; Hours </div>
I/System.out: 								</div>					</div> 			</div>	</li> 
    	<li class="back_darkish" 			style="background-image:linear-gradient(rgb(31, 31, 31), rgba(31, 31, 31, 0.9)), url('/games/250px-Super-Mario-3D-Land-Logo.jpg')"> 				<div class="search_list_image">
    					<a aria-label="Super Mario 3D Land" title="Super Mario 3D Land" href="game?id=9361">
    						<img alt="Box Art" src="/games/250px-Super-Mario-3D-Land-Logo.jpg" />
    					</a> 
    				</div> 			<div class="search_list_details">					<h3 class="shadow_text">
    						<a class="text_white" title="Super Mario 3D Land" href="game?id=9361">Super Mario 3D Land</a>
    											</h3> 					<div class="search_list_details_block"> 								<div>
    									<div class="search_list_tidbit text_white shadow_text">Main Story</div>
    									<div class="search_list_tidbit center time_100">6&#189; Hours </div>
I/System.out: 									<div class="search_list_tidbit text_white shadow_text">Main + Extra</div>
    									<div class="search_list_tidbit center time_100">12&#189; Hours </div>
    									<div class="search_list_tidbit text_white shadow_text">Completionist</div>
    									<div class="search_list_tidbit center time_100">22&#189; Hours </div>
    								</div>					</div> 			</div>	</li> 						<div class="clear"></div> 
    	<li class="back_darkish" 			style="background-image:linear-gradient(rgb(31, 31, 31), rgba(31, 31, 31, 0.9)), url('/games/Yisland_box.jpg')"> 				<div class="search_list_image">
    					<a aria-label="Super Mario World 2 Yoshis Island" title="Super Mario World 2 Yoshis Island" href="game?id=9388">
    						<img alt="Box Art" src="/games/Yisland_box.jpg" />
    					</a> 
    				</div> 			<div class="search_list_details">					<h3 class="shadow_text">
    						<a class="text_white" title="Super Mario World 2 Yoshis Island" href="game?id=9388">Super Mario World 2: Yoshi's Island</a>
    											</h3> 					<div class="search_list_details_block"> 								<div>
    									<div class="search_list_tidbit text_white shadow_text">Main Story</div>
    									<div class="search_list_tidbit center time_100">8 Hours </div>
    									<div class="search_list_tidbit text_white shadow_text">Main + Extra</div>
    									<div class="search_list_tidbit center time_100">11 Hours </div>
    									<div class="search_list_tidbit text_white shadow_text">Completionist</div>
    									<div class="search_list_tidbit center time_90">16&#189; Hours </div>
    								</div>					</div> 			</div>	</li> 
    	<li class="back_darkish" 			style="background-image:linear-gradient(rgb(31, 31, 31), rgba(31, 31, 31, 0.9)), url('/games/250px-Super_Mario_Land_2_box_art.jpg')"> 				<div class="search_list_image">
    					<a aria-label="Super Mario Land 2 6 Golden Coins" title="Super Mario Land 2 6 Golden Coins" href="game?id=9381">
    						<img alt="Box Art" src="/games/250px-Super_Mario_Land_2_box_art.jpg" />
    					</a>            
        """.trimIndent()
        val result = HowLongToBeatResultParser.parse(html).map { it.title }.toList()
        assertEquals(arrayListOf("Super Mario Land", "Super Mario 3D Land", "Super Mario World 2 Yoshis Island", "Super Mario Land 2 6 Golden Coins"), result)
    }
}