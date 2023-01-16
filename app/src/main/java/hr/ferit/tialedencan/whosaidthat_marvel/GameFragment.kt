package hr.ferit.tialedencan.whosaidthat_marvel

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.firestore.FieldValue
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GameFragment : Fragment() {

    private lateinit var communicator: Communicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_game, container, false)
        val request = ServiceBuilder.buildService(MarvelEndpoint::class.java)

        val key=context?.getString(R.string.rapidapi_api_key).toString()
        val host ="marvel-quote-api.p.rapidapi.com"
        val call = request.getQuote(key,host)
        var speaker = ""

        call.enqueue(object : Callback<Marvel> {
            override fun onResponse(
                call: Call<Marvel>,
                response: Response<Marvel>
            ) {
                if (response.isSuccessful) {
                    val quote = view.findViewById<TextView>(R.id.tvQuote)
                    quote.setText(response.body()!!.Quote)

                    //correct answer
                    speaker = response.body()!!.Speaker
                }
            }

            override fun onFailure(call: Call<Marvel>, t: Throwable) {
                Log.d(
                    "FAIL", t.message.toString()
                )
            }
        })

        val btnCheck = view.findViewById<ImageButton>(R.id.ibtnCheck)
        val btnScoreboard = view.findViewById<Button>(R.id.btn_scoreboard)
        val nickname = arguments?.getString("PLAYER").toString()

        btnCheck.setOnClickListener{

            val playerAnswer = view.findViewById<EditText>(R.id.etPlayersAnswer)

           if(speaker.equals(playerAnswer.text.toString(), true)){
                val main = MainActivity()
                val player = main.db.collection("players").document(nickname)
                player.update("points", FieldValue.increment(5))
                Toast.makeText(view.context, "+5", Toast.LENGTH_LONG).show()

            }
            else{
                Toast.makeText(view.context, "Correct answer: " + speaker, Toast.LENGTH_LONG).show()
            }

            communicator = activity as Communicator
            val fragment = communicator.getFragmentWithPlayerName(GameFragment(), nickname)
            communicator.replaceFragment(fragment)

        }

        btnScoreboard.setOnClickListener {
            communicator = activity as Communicator
            val fragment = communicator.getFragmentWithPlayerName(ScoreBoardFragment(), nickname)
            communicator.replaceFragment(fragment)

        }

        return view
    }
}