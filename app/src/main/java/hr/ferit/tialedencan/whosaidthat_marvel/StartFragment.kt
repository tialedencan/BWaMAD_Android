package hr.ferit.tialedencan.whosaidthat_marvel

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException


class StartFragment : Fragment() {

    private lateinit var communicator: Communicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_start, container, false)
        val communicator = activity as Communicator

        val btnStart = view.findViewById<Button>(R.id.btnStart)
        btnStart.setOnClickListener{
            val player = view.findViewById<EditText>(R.id.etPlayerNickname)
            //omoguci spremanje podataka u bazu s vrijednosti bodova =0 ili ako igrac vec postoji samo uci u igru
            val main = MainActivity()
            val database = main.db.collection("players")
            var guessPlayer : Player

            database.document(player.text.toString()).addSnapshotListener(object : EventListener<DocumentSnapshot?> {

                override fun onEvent(value: DocumentSnapshot?, error: FirebaseFirestoreException?) {
                    if (value != null) {
                        if (value.exists()) {
                            //update
                            guessPlayer = value.toObject(Player::class.java)!!
                            communicator.playGame(guessPlayer.nickname.toString())
                        } else {
                            //Insert
                            guessPlayer = Player(
                                player.text.toString(),
                                0,
                            )
                            database.document(player.text.toString()).set(guessPlayer)
                            communicator.playGame(guessPlayer.nickname.toString())
                        }
                    }
                }
            })
          /*  if(database.document(player.text.toString()).get() != null){ //postoji
                database.document(player.text.toString())
                    .get()
                    .addOnSuccessListener { result->
                      guessPlayer = result.toObject(Player::class.java)!!
                        communicator.playGame(guessPlayer.nickname.toString())
                    }
            }
            else{
                guessPlayer = Player(
                    player.text.toString(),
                    0,
                )
                database.document(player.text.toString()).set(guessPlayer)
                    .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                    .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
                communicator.playGame(guessPlayer.nickname.toString())
            }*/

           // database.orderBy("nickname").equals(player.text.toString())
           /*  database.get().addOnSuccessListener { result->
                 for(data in result.documents) {
                     val p = data.toObject(Player::class.java)
                     if (p!= null && p.nickname == player.text.toString()){
                            existingPlayer=true
                            guessPlayer = p  //moram drugacije to riješiti
                     }
                     if(existingPlayer)break

                 }
             }
            if(!existingPlayer){
               val doc = database.document()
               guessPlayer =
                doc.set(guessPlayer)
            }
*/

        }
        return view
    }

}