package hr.ferit.tialedencan.whosaidthat_marvel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), Communicator{

    private lateinit var recyclerAdapter: PlayersRecyclerAdapter
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startFragment = StartFragment()
        replaceFragment(startFragment)


    }
    override fun replaceFragment(fragment: Fragment){
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container,fragment)
        fragmentTransaction.commit()
    }

    override fun contactDatabase(recyclerView:RecyclerView) {

        db.collection("players")
            .get()
            .addOnSuccessListener { result ->
                val players = ArrayList<Player>()
                for(data in result.documents){
                    val player = data.toObject(Player::class.java)
                    if(player != null){
                        //player.id = data.id
                        players.add(player)
                    }
                }
                recyclerAdapter = PlayersRecyclerAdapter(players)
                recyclerView.apply {
                    layoutManager=LinearLayoutManager(this@MainActivity)
                    adapter = recyclerAdapter
                }
            }
            .addOnFailureListener{ exception ->
                Log.e("MainActivity", "Error getting documents.", exception)
            }
    }

    override fun getFragmentWithPlayerName(fragment: Fragment, playerName: String): Fragment {

            val bundle = Bundle()
            bundle.putString("PLAYER", playerName)
            fragment.arguments = bundle

            return fragment
    }

    override fun playGame(player:String) {
        val gameFragment=getFragmentWithPlayerName(GameFragment(), player)
        replaceFragment(gameFragment)
    }





}