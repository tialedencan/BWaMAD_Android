package hr.ferit.tialedencan.whosaidthat_marvel

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header


interface MarvelEndpoint {
    @GET(" ")
    fun getQuote(@Header("X-RapidApi-Key") key:String,
                 @Header("X-RapidApi-Host") host:String):Call<Marvel>

}