package be.kuleuven.howlongtobeat.cartridges

interface CartridgesRepository {
    suspend fun find(code: String?): Cartridge?
}

suspend fun findFirstCartridgeForRepos(code: String?, repos: List<CartridgesRepository>): Cartridge? {
    for(repo in repos) {
        val result = repo.find(code)
        if(result != null) return result
    }
    return null
}