import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

// Modelo de datos para la cita
data class CitaRequest(val fecha: String, val abogadoId: Int)

// Definir la interfaz de la API
interface ApiService {
    @POST("/agendarCita")
    suspend fun agendarCita(@Body citaRequest: CitaRequest): retrofit2.Response<Void>
}

// Configuración de Retrofit
object RetrofitClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://tu-api.com")  // Reemplaza con la URL de tu API
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
