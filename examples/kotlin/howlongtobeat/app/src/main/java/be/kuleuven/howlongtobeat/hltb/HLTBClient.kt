package be.kuleuven.howlongtobeat.hltb

import be.kuleuven.howlongtobeat.cartridges.Cartridge

interface HLTBClient {
    suspend fun find(cart: Cartridge): List<HowLongToBeatResult>?
}
