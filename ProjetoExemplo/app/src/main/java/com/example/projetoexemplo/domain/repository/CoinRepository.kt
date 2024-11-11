import com.example.projetoexemplo.domain.model.Coin
import com.example.projetoexemplo.domain.model.CoinDetail

interface CoinRepository {
    suspend fun getCoins(): List<Coin>
    suspend fun getCoinDetail(coinId: String): CoinDetail
}